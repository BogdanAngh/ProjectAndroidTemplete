package com.badlogic.gdx.backends.android.surfaceview;

import android.content.Context;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy.MeasuredDimension;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.io.Writer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;

public class GLSurfaceViewCupcake extends SurfaceView implements Callback {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    static final Object sEglLock;
    private int mDebugFlags;
    EGLConfigChooser mEGLConfigChooser;
    private GLThread mGLThread;
    GLWrapper mGLWrapper;
    private boolean mHasSurface;
    private int mRenderMode;
    private Renderer mRenderer;
    private int mSurfaceHeight;
    private int mSurfaceWidth;
    final ResolutionStrategy resolutionStrategy;

    private static abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] configSpec) {
            this.mConfigSpec = configSpec;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY];
            egl.eglChooseConfig(display, this.mConfigSpec, null, 0, num_config);
            int numConfigs = num_config[0];
            if (numConfigs <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config);
            EGLConfig config = chooseConfig(egl, display, configs);
            if (config != null) {
                return config;
            }
            throw new IllegalArgumentException("No config chosen");
        }
    }

    private class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;

        public void start() {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.mEgl.eglInitialize(this.mEglDisplay, new int[GLSurfaceViewCupcake.DEBUG_LOG_GL_CALLS]);
            this.mEglConfig = GLSurfaceViewCupcake.this.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
            this.mEglContext = this.mEgl.eglCreateContext(this.mEglDisplay, this.mEglConfig, EGL10.EGL_NO_CONTEXT, null);
            this.mEglSurface = null;
        }

        public GL createSurface(SurfaceHolder holder) {
            if (this.mEglSurface != null) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            }
            this.mEglSurface = this.mEgl.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, holder, null);
            this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext);
            GL gl = this.mEglContext.getGL();
            if (GLSurfaceViewCupcake.this.mGLWrapper != null) {
                return GLSurfaceViewCupcake.this.mGLWrapper.wrap(gl);
            }
            return gl;
        }

        public boolean swap() {
            this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
            return this.mEgl.eglGetError() != 12302;
        }

        public void finish() {
            if (this.mEglSurface != null) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
                this.mEglSurface = null;
            }
            if (this.mEglContext != null) {
                this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }
    }

    class GLThread extends Thread {
        private boolean mDone;
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue;
        private boolean mHasSurface;
        private int mHeight;
        private boolean mPaused;
        private int mRenderMode;
        private Renderer mRenderer;
        private boolean mRequestRender;
        private boolean mSizeChanged;
        private int mWidth;

        GLThread(Renderer renderer) {
            this.mEventQueue = new ArrayList();
            this.mDone = false;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mRequestRender = true;
            this.mRenderMode = GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY;
            this.mRenderer = renderer;
            this.mSizeChanged = true;
            setName("GLThread");
        }

        public void run() {
            try {
                synchronized (GLSurfaceViewCupcake.sEglLock) {
                    guardedRun();
                }
            } catch (InterruptedException e) {
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void guardedRun() throws java.lang.InterruptedException {
            /*
            r10 = this;
            r8 = new com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewCupcake$EglHelper;
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewCupcake.this;
            r8.<init>();
            r10.mEglHelper = r8;
            r8 = r10.mEglHelper;
            r8.start();
            r1 = 0;
            r6 = 1;
            r5 = 1;
        L_0x0011:
            r8 = r10.mDone;
            if (r8 != 0) goto L_0x003d;
        L_0x0015:
            r3 = 0;
            monitor-enter(r10);
        L_0x0017:
            r4 = r10.getEvent();	 Catch:{ all -> 0x0021 }
            if (r4 == 0) goto L_0x0024;
        L_0x001d:
            r4.run();	 Catch:{ all -> 0x0021 }
            goto L_0x0017;
        L_0x0021:
            r8 = move-exception;
            monitor-exit(r10);	 Catch:{ all -> 0x0021 }
            throw r8;
        L_0x0024:
            r8 = r10.mPaused;	 Catch:{ all -> 0x0021 }
            if (r8 == 0) goto L_0x002e;
        L_0x0028:
            r8 = r10.mEglHelper;	 Catch:{ all -> 0x0021 }
            r8.finish();	 Catch:{ all -> 0x0021 }
            r3 = 1;
        L_0x002e:
            r8 = r10.needToWait();	 Catch:{ all -> 0x0021 }
            if (r8 == 0) goto L_0x0038;
        L_0x0034:
            r10.wait();	 Catch:{ all -> 0x0021 }
            goto L_0x002e;
        L_0x0038:
            r8 = r10.mDone;	 Catch:{ all -> 0x0021 }
            if (r8 == 0) goto L_0x0043;
        L_0x003c:
            monitor-exit(r10);	 Catch:{ all -> 0x0021 }
        L_0x003d:
            r8 = r10.mEglHelper;
            r8.finish();
            return;
        L_0x0043:
            r0 = r10.mSizeChanged;	 Catch:{ all -> 0x0021 }
            r7 = r10.mWidth;	 Catch:{ all -> 0x0021 }
            r2 = r10.mHeight;	 Catch:{ all -> 0x0021 }
            r8 = 0;
            r10.mSizeChanged = r8;	 Catch:{ all -> 0x0021 }
            r8 = 0;
            r10.mRequestRender = r8;	 Catch:{ all -> 0x0021 }
            monitor-exit(r10);	 Catch:{ all -> 0x0021 }
            if (r3 == 0) goto L_0x0059;
        L_0x0052:
            r8 = r10.mEglHelper;
            r8.start();
            r6 = 1;
            r0 = 1;
        L_0x0059:
            if (r0 == 0) goto L_0x006a;
        L_0x005b:
            r8 = r10.mEglHelper;
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewCupcake.this;
            r9 = r9.getHolder();
            r1 = r8.createSurface(r9);
            r1 = (javax.microedition.khronos.opengles.GL10) r1;
            r5 = 1;
        L_0x006a:
            if (r6 == 0) goto L_0x0076;
        L_0x006c:
            r8 = r10.mRenderer;
            r9 = r10.mEglHelper;
            r9 = r9.mEglConfig;
            r8.onSurfaceCreated(r1, r9);
            r6 = 0;
        L_0x0076:
            if (r5 == 0) goto L_0x007e;
        L_0x0078:
            r8 = r10.mRenderer;
            r8.onSurfaceChanged(r1, r7, r2);
            r5 = 0;
        L_0x007e:
            if (r7 <= 0) goto L_0x0011;
        L_0x0080:
            if (r2 <= 0) goto L_0x0011;
        L_0x0082:
            r8 = r10.mRenderer;
            r8.onDrawFrame(r1);
            r8 = r10.mEglHelper;
            r8.swap();
            goto L_0x0011;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewCupcake.GLThread.guardedRun():void");
        }

        private boolean needToWait() {
            if (this.mDone) {
                return false;
            }
            if (this.mPaused || !this.mHasSurface) {
                return true;
            }
            if (this.mWidth <= 0 || this.mHeight <= 0 || (!this.mRequestRender && this.mRenderMode != GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY)) {
                return true;
            }
            return false;
        }

        public void setRenderMode(int renderMode) {
            if (renderMode < 0 || renderMode > GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (this) {
                this.mRenderMode = renderMode;
                if (renderMode == GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY) {
                    notify();
                }
            }
        }

        public int getRenderMode() {
            int i;
            synchronized (this) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (this) {
                this.mRequestRender = true;
                notify();
            }
        }

        public void surfaceCreated() {
            synchronized (this) {
                this.mHasSurface = true;
                notify();
            }
        }

        public void surfaceDestroyed() {
            synchronized (this) {
                this.mHasSurface = false;
                notify();
            }
        }

        public void onPause() {
            synchronized (this) {
                this.mPaused = true;
            }
        }

        public void onResume() {
            synchronized (this) {
                this.mPaused = false;
                notify();
            }
        }

        public void onWindowResize(int w, int h) {
            synchronized (this) {
                this.mWidth = w;
                this.mHeight = h;
                this.mSizeChanged = true;
                notify();
            }
        }

        public void requestExitAndWait() {
            synchronized (this) {
                this.mDone = true;
                notify();
            }
            try {
                join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void queueEvent(Runnable r) {
            synchronized (this) {
                this.mEventQueue.add(r);
            }
        }

        private Runnable getEvent() {
            synchronized (this) {
                if (this.mEventQueue.size() > 0) {
                    Runnable runnable = (Runnable) this.mEventQueue.remove(0);
                    return runnable;
                }
                return null;
            }
        }
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    static class LogWriter extends Writer {
        private StringBuilder mBuilder;

        LogWriter() {
            this.mBuilder = new StringBuilder();
        }

        public void close() {
            flushBuilder();
        }

        public void flush() {
            flushBuilder();
        }

        public void write(char[] buf, int offset, int count) {
            for (int i = 0; i < count; i += GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY) {
                char c = buf[offset + i];
                if (c == '\n') {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v("GLSurfaceView", this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }
    }

    private static class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;

        public ComponentSizeChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
            super(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344});
            this.mValue = new int[GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY];
            this.mRedSize = redSize;
            this.mGreenSize = greenSize;
            this.mBlueSize = blueSize;
            this.mAlphaSize = alphaSize;
            this.mDepthSize = depthSize;
            this.mStencilSize = stencilSize;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            EGLConfig closestConfig = null;
            int closestDistance = GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE;
            EGLConfig[] arr$ = configs;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; i$ += GLSurfaceViewCupcake.RENDERMODE_CONTINUOUSLY) {
                EGLConfig config = arr$[i$];
                int distance = ((((Math.abs(findConfigAttrib(egl, display, config, 12324, 0) - this.mRedSize) + Math.abs(findConfigAttrib(egl, display, config, 12323, 0) - this.mGreenSize)) + Math.abs(findConfigAttrib(egl, display, config, 12322, 0) - this.mBlueSize)) + Math.abs(findConfigAttrib(egl, display, config, 12321, 0) - this.mAlphaSize)) + Math.abs(findConfigAttrib(egl, display, config, 12325, 0) - this.mDepthSize)) + Math.abs(findConfigAttrib(egl, display, config, 12326, 0) - this.mStencilSize);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestConfig = config;
                }
            }
            return closestConfig;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
                return this.mValue[0];
            }
            return defaultValue;
        }
    }

    private static class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean withDepthBuffer) {
            super(4, 4, 4, 0, withDepthBuffer ? 16 : 0, 0);
            this.mRedSize = 5;
            this.mGreenSize = 6;
            this.mBlueSize = 5;
        }
    }

    public GLSurfaceViewCupcake(Context context, ResolutionStrategy resolutionStrategy) {
        super(context);
        this.resolutionStrategy = resolutionStrategy;
        init();
    }

    public GLSurfaceViewCupcake(Context context, AttributeSet attrs, ResolutionStrategy resolutionStrategy) {
        super(context, attrs);
        this.resolutionStrategy = resolutionStrategy;
        init();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasuredDimension measures = this.resolutionStrategy.calcMeasures(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measures.width, measures.height);
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(DEBUG_LOG_GL_CALLS);
        this.mRenderMode = RENDERMODE_CONTINUOUSLY;
    }

    public void setGLWrapper(GLWrapper glWrapper) {
        this.mGLWrapper = glWrapper;
    }

    public void setDebugFlags(int debugFlags) {
        this.mDebugFlags = debugFlags;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setRenderer(Renderer renderer) {
        if (this.mRenderer != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
        this.mRenderer = renderer;
    }

    public void setEGLConfigChooser(EGLConfigChooser configChooser) {
        if (this.mRenderer != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
        this.mEGLConfigChooser = configChooser;
    }

    public void setEGLConfigChooser(boolean needDepth) {
        setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
    }

    public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
    }

    public void setRenderMode(int renderMode) {
        this.mRenderMode = renderMode;
        if (this.mGLThread != null) {
            this.mGLThread.setRenderMode(renderMode);
        }
    }

    public int getRenderMode() {
        return this.mRenderMode;
    }

    public void requestRender() {
        GLThread thread = this.mGLThread;
        if (thread != null) {
            thread.requestRender();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (this.mGLThread != null) {
            this.mGLThread.surfaceCreated();
        }
        this.mHasSurface = true;
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mGLThread != null) {
            this.mGLThread.surfaceDestroyed();
        }
        this.mHasSurface = false;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (this.mGLThread != null) {
            this.mGLThread.onWindowResize(w, h);
        }
        this.mSurfaceWidth = w;
        this.mSurfaceHeight = h;
    }

    public void onPause() {
        this.mGLThread.onPause();
        this.mGLThread.requestExitAndWait();
        this.mGLThread = null;
    }

    public void onResume() {
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        this.mGLThread = new GLThread(this.mRenderer);
        this.mGLThread.start();
        this.mGLThread.setRenderMode(this.mRenderMode);
        if (this.mHasSurface) {
            this.mGLThread.surfaceCreated();
        }
        if (this.mSurfaceWidth > 0 && this.mSurfaceHeight > 0) {
            this.mGLThread.onWindowResize(this.mSurfaceWidth, this.mSurfaceHeight);
        }
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable r) {
        if (this.mGLThread != null) {
            this.mGLThread.queueEvent(r);
        }
    }

    static {
        sEglLock = new Object();
    }
}
