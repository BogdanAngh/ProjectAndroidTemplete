package com.badlogic.gdx.backends.android.surfaceview;

import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.service.wallpaper.WallpaperService.Engine;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.io.Writer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;

public class GLBaseSurfaceViewLW implements Callback {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean DRAW_TWICE_AFTER_SIZE_CHANGED = true;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    private static final boolean LOG_VIEW = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private final String TAG;
    protected Engine engine;
    int mDebugFlags;
    EGLConfigChooser mEGLConfigChooser;
    EGLContextFactory mEGLContextFactory;
    EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private GLThread mGLThread;
    GLWrapper mGLWrapper;
    boolean mSizeChanged;
    final GLThreadManager sGLThreadManager;

    private static abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] configSpec) {
            this.mConfigSpec = configSpec;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY];
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

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
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
            this.mEgl.eglInitialize(this.mEglDisplay, new int[GLBaseSurfaceViewLW.DEBUG_LOG_GL_CALLS]);
            this.mEglConfig = GLBaseSurfaceViewLW.this.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
            this.mEglContext = GLBaseSurfaceViewLW.this.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                throw new RuntimeException("createContext failed");
            }
            this.mEglSurface = null;
        }

        public GL createSurface(SurfaceHolder holder) {
            if (!(this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE)) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLBaseSurfaceViewLW.this.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
            }
            this.mEglSurface = GLBaseSurfaceViewLW.this.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, holder);
            if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
                throwEglException("createWindowSurface");
            }
            if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                throwEglException("eglMakeCurrent");
            }
            GL gl = this.mEglContext.getGL();
            if (GLBaseSurfaceViewLW.this.mGLWrapper != null) {
                gl = GLBaseSurfaceViewLW.this.mGLWrapper.wrap(gl);
            }
            if ((GLBaseSurfaceViewLW.this.mDebugFlags & 3) == 0) {
                return gl;
            }
            int configFlags = 0;
            Writer log = null;
            if ((GLBaseSurfaceViewLW.this.mDebugFlags & GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY) != 0) {
                configFlags = 0 | GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY;
            }
            if ((GLBaseSurfaceViewLW.this.mDebugFlags & GLBaseSurfaceViewLW.DEBUG_LOG_GL_CALLS) != 0) {
                log = new LogWriter();
            }
            return GLDebugHelper.wrap(gl, configFlags, log);
        }

        public boolean swap() {
            this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
            return this.mEgl.eglGetError() != 12302 ? GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED : GLBaseSurfaceViewLW.LOG_VIEW;
        }

        public void destroySurface() {
            if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLBaseSurfaceViewLW.this.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                this.mEglSurface = null;
            }
        }

        public void finish() {
            if (this.mEglContext != null) {
                GLBaseSurfaceViewLW.this.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(String function) {
            throw new RuntimeException(function + " failed: " + this.mEgl.eglGetError());
        }
    }

    class GLThread extends Thread {
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue;
        boolean mExited;
        private boolean mHasSurface;
        private boolean mHaveEgl;
        private int mHeight;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode;
        private Renderer mRenderer;
        private boolean mRequestRender;
        private boolean mShouldExit;
        private boolean mWaitingForSurface;
        private int mWidth;

        GLThread(Renderer renderer) {
            this.mEventQueue = new ArrayList();
            this.mWidth = 0;
            this.mHeight = 0;
            this.mRequestRender = GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED;
            this.mRenderMode = GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY;
            this.mRenderer = renderer;
        }

        public void run() {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException e) {
            } finally {
                GLBaseSurfaceViewLW.this.sGLThreadManager.threadExiting(this);
            }
        }

        private void stopEglLocked() {
            if (this.mHaveEgl) {
                this.mHaveEgl = GLBaseSurfaceViewLW.LOG_VIEW;
                this.mEglHelper.destroySurface();
                this.mEglHelper.finish();
                GLBaseSurfaceViewLW.this.sGLThreadManager.releaseEglSurfaceLocked(this);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void guardedRun() throws java.lang.InterruptedException {
            /*
            r13 = this;
            r12 = 1;
            r9 = new com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW$EglHelper;
            r10 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;
            r9.<init>();
            r13.mEglHelper = r9;
            r4 = 0;
            r1 = 0;
            r6 = 0;
            r8 = 0;
            r2 = 0;
            r7 = 0;
            r5 = 0;
            r3 = 0;
        L_0x0012:
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00e0 }
            r10 = r9.sGLThreadManager;	 Catch:{ all -> 0x00e0 }
            monitor-enter(r10);	 Catch:{ all -> 0x00e0 }
        L_0x0017:
            r9 = r13.mShouldExit;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x0029;
        L_0x001b:
            monitor-exit(r10);	 Catch:{ all -> 0x00dd }
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;
            r10 = r9.sGLThreadManager;
            monitor-enter(r10);
            r13.stopEglLocked();	 Catch:{ all -> 0x0026 }
            monitor-exit(r10);	 Catch:{ all -> 0x0026 }
            return;
        L_0x0026:
            r9 = move-exception;
            monitor-exit(r10);	 Catch:{ all -> 0x0026 }
            throw r9;
        L_0x0029:
            r9 = r13.mEventQueue;	 Catch:{ all -> 0x00dd }
            r9 = r9.isEmpty();	 Catch:{ all -> 0x00dd }
            if (r9 != 0) goto L_0x0044;
        L_0x0031:
            r9 = r13.mEventQueue;	 Catch:{ all -> 0x00dd }
            r11 = 0;
            r9 = r9.remove(r11);	 Catch:{ all -> 0x00dd }
            r0 = r9;
            r0 = (java.lang.Runnable) r0;	 Catch:{ all -> 0x00dd }
            r3 = r0;
        L_0x003c:
            monitor-exit(r10);	 Catch:{ all -> 0x00dd }
            if (r3 == 0) goto L_0x00f8;
        L_0x003f:
            r3.run();	 Catch:{ all -> 0x00e0 }
            r3 = 0;
            goto L_0x0012;
        L_0x0044:
            r9 = r13.mHaveEgl;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x004f;
        L_0x0048:
            r9 = r13.mPaused;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x004f;
        L_0x004c:
            r13.stopEglLocked();	 Catch:{ all -> 0x00dd }
        L_0x004f:
            r9 = r13.mHasSurface;	 Catch:{ all -> 0x00dd }
            if (r9 != 0) goto L_0x0068;
        L_0x0053:
            r9 = r13.mWaitingForSurface;	 Catch:{ all -> 0x00dd }
            if (r9 != 0) goto L_0x0068;
        L_0x0057:
            r9 = r13.mHaveEgl;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x005e;
        L_0x005b:
            r13.stopEglLocked();	 Catch:{ all -> 0x00dd }
        L_0x005e:
            r9 = 1;
            r13.mWaitingForSurface = r9;	 Catch:{ all -> 0x00dd }
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.sGLThreadManager;	 Catch:{ all -> 0x00dd }
            r9.notifyAll();	 Catch:{ all -> 0x00dd }
        L_0x0068:
            r9 = r13.mHasSurface;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x007a;
        L_0x006c:
            r9 = r13.mWaitingForSurface;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x007a;
        L_0x0070:
            r9 = 0;
            r13.mWaitingForSurface = r9;	 Catch:{ all -> 0x00dd }
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.sGLThreadManager;	 Catch:{ all -> 0x00dd }
            r9.notifyAll();	 Catch:{ all -> 0x00dd }
        L_0x007a:
            if (r2 == 0) goto L_0x0088;
        L_0x007c:
            r8 = 0;
            r2 = 0;
            r9 = 1;
            r13.mRenderComplete = r9;	 Catch:{ all -> 0x00dd }
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.sGLThreadManager;	 Catch:{ all -> 0x00dd }
            r9.notifyAll();	 Catch:{ all -> 0x00dd }
        L_0x0088:
            r9 = r13.mPaused;	 Catch:{ all -> 0x00dd }
            if (r9 != 0) goto L_0x00ef;
        L_0x008c:
            r9 = r13.mHasSurface;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x00ef;
        L_0x0090:
            r9 = r13.mWidth;	 Catch:{ all -> 0x00dd }
            if (r9 <= 0) goto L_0x00ef;
        L_0x0094:
            r9 = r13.mHeight;	 Catch:{ all -> 0x00dd }
            if (r9 <= 0) goto L_0x00ef;
        L_0x0098:
            r9 = r13.mRequestRender;	 Catch:{ all -> 0x00dd }
            if (r9 != 0) goto L_0x00a0;
        L_0x009c:
            r9 = r13.mRenderMode;	 Catch:{ all -> 0x00dd }
            if (r9 != r12) goto L_0x00ef;
        L_0x00a0:
            r9 = r13.mHaveEgl;	 Catch:{ all -> 0x00dd }
            if (r9 != 0) goto L_0x00bf;
        L_0x00a4:
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.sGLThreadManager;	 Catch:{ all -> 0x00dd }
            r9 = r9.tryAcquireEglSurfaceLocked(r13);	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x00bf;
        L_0x00ae:
            r9 = 1;
            r13.mHaveEgl = r9;	 Catch:{ all -> 0x00dd }
            r9 = r13.mEglHelper;	 Catch:{ all -> 0x00dd }
            r9.start();	 Catch:{ all -> 0x00dd }
            r1 = 1;
            r6 = 1;
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.sGLThreadManager;	 Catch:{ all -> 0x00dd }
            r9.notifyAll();	 Catch:{ all -> 0x00dd }
        L_0x00bf:
            r9 = r13.mHaveEgl;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x00ef;
        L_0x00c3:
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.mSizeChanged;	 Catch:{ all -> 0x00dd }
            if (r9 == 0) goto L_0x00eb;
        L_0x00c9:
            r6 = 1;
            r7 = r13.mWidth;	 Catch:{ all -> 0x00dd }
            r5 = r13.mHeight;	 Catch:{ all -> 0x00dd }
            r8 = 1;
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r11 = 0;
            r9.mSizeChanged = r11;	 Catch:{ all -> 0x00dd }
        L_0x00d4:
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.sGLThreadManager;	 Catch:{ all -> 0x00dd }
            r9.notifyAll();	 Catch:{ all -> 0x00dd }
            goto L_0x003c;
        L_0x00dd:
            r9 = move-exception;
            monitor-exit(r10);	 Catch:{ all -> 0x00dd }
            throw r9;	 Catch:{ all -> 0x00e0 }
        L_0x00e0:
            r9 = move-exception;
            r10 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;
            r10 = r10.sGLThreadManager;
            monitor-enter(r10);
            r13.stopEglLocked();	 Catch:{ all -> 0x012e }
            monitor-exit(r10);	 Catch:{ all -> 0x012e }
            throw r9;
        L_0x00eb:
            r9 = 0;
            r13.mRequestRender = r9;	 Catch:{ all -> 0x00dd }
            goto L_0x00d4;
        L_0x00ef:
            r9 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00dd }
            r9 = r9.sGLThreadManager;	 Catch:{ all -> 0x00dd }
            r9.wait();	 Catch:{ all -> 0x00dd }
            goto L_0x0017;
        L_0x00f8:
            if (r1 == 0) goto L_0x0114;
        L_0x00fa:
            r9 = r13.mEglHelper;	 Catch:{ all -> 0x00e0 }
            r10 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x00e0 }
            r10 = r10.getHolder();	 Catch:{ all -> 0x00e0 }
            r9 = r9.createSurface(r10);	 Catch:{ all -> 0x00e0 }
            r0 = r9;
            r0 = (javax.microedition.khronos.opengles.GL10) r0;	 Catch:{ all -> 0x00e0 }
            r4 = r0;
            r9 = r13.mRenderer;	 Catch:{ all -> 0x00e0 }
            r10 = r13.mEglHelper;	 Catch:{ all -> 0x00e0 }
            r10 = r10.mEglConfig;	 Catch:{ all -> 0x00e0 }
            r9.onSurfaceCreated(r4, r10);	 Catch:{ all -> 0x00e0 }
            r1 = 0;
        L_0x0114:
            if (r6 == 0) goto L_0x011c;
        L_0x0116:
            r9 = r13.mRenderer;	 Catch:{ all -> 0x00e0 }
            r9.onSurfaceChanged(r4, r7, r5);	 Catch:{ all -> 0x00e0 }
            r6 = 0;
        L_0x011c:
            r9 = r13.mRenderer;	 Catch:{ all -> 0x00e0 }
            r9.onDrawFrame(r4);	 Catch:{ all -> 0x00e0 }
            r9 = r13.mEglHelper;	 Catch:{ all -> 0x00e0 }
            r9 = r9.swap();	 Catch:{ all -> 0x00e0 }
            if (r9 != 0) goto L_0x0129;
        L_0x0129:
            if (r8 == 0) goto L_0x0012;
        L_0x012b:
            r2 = 1;
            goto L_0x0012;
        L_0x012e:
            r9 = move-exception;
            monitor-exit(r10);	 Catch:{ all -> 0x012e }
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.GLThread.guardedRun():void");
        }

        public void setRenderMode(int renderMode) {
            if (renderMode < 0 || renderMode > GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (GLBaseSurfaceViewLW.this.sGLThreadManager) {
                this.mRenderMode = renderMode;
                GLBaseSurfaceViewLW.this.sGLThreadManager.notifyAll();
            }
        }

        public int getRenderMode() {
            int i;
            synchronized (GLBaseSurfaceViewLW.this.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (GLBaseSurfaceViewLW.this.sGLThreadManager) {
                this.mRequestRender = GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED;
                GLBaseSurfaceViewLW.this.sGLThreadManager.notifyAll();
            }
        }

        public void surfaceCreated() {
            synchronized (GLBaseSurfaceViewLW.this.sGLThreadManager) {
                this.mHasSurface = GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED;
                GLBaseSurfaceViewLW.this.sGLThreadManager.notifyAll();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void surfaceDestroyed() {
            /*
            r3 = this;
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;
            r2 = r1.sGLThreadManager;
            monitor-enter(r2);
            r1 = 0;
            r3.mHasSurface = r1;	 Catch:{ all -> 0x002e }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x002e }
            r1 = r1.sGLThreadManager;	 Catch:{ all -> 0x002e }
            r1.notifyAll();	 Catch:{ all -> 0x002e }
        L_0x000f:
            r1 = r3.mWaitingForSurface;	 Catch:{ all -> 0x002e }
            if (r1 != 0) goto L_0x0031;
        L_0x0013:
            r1 = r3.isAlive();	 Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0031;
        L_0x0019:
            r1 = r3.mExited;	 Catch:{ all -> 0x002e }
            if (r1 != 0) goto L_0x0031;
        L_0x001d:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ InterruptedException -> 0x0025 }
            r1 = r1.sGLThreadManager;	 Catch:{ InterruptedException -> 0x0025 }
            r1.wait();	 Catch:{ InterruptedException -> 0x0025 }
            goto L_0x000f;
        L_0x0025:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x002e }
            r1.interrupt();	 Catch:{ all -> 0x002e }
            goto L_0x000f;
        L_0x002e:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x002e }
            throw r1;
        L_0x0031:
            monitor-exit(r2);	 Catch:{ all -> 0x002e }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.GLThread.surfaceDestroyed():void");
        }

        public void onPause() {
            synchronized (GLBaseSurfaceViewLW.this.sGLThreadManager) {
                this.mPaused = GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED;
                GLBaseSurfaceViewLW.this.sGLThreadManager.notifyAll();
            }
        }

        public void onResume() {
            synchronized (GLBaseSurfaceViewLW.this.sGLThreadManager) {
                this.mPaused = GLBaseSurfaceViewLW.LOG_VIEW;
                this.mRequestRender = GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED;
                GLBaseSurfaceViewLW.this.sGLThreadManager.notifyAll();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onWindowResize(int r7, int r8) {
            /*
            r6 = this;
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;
            r2 = r1.sGLThreadManager;
            monitor-enter(r2);
            r6.mWidth = r7;	 Catch:{ all -> 0x003d }
            r6.mHeight = r8;	 Catch:{ all -> 0x003d }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x003d }
            r3 = 1;
            r1.mSizeChanged = r3;	 Catch:{ all -> 0x003d }
            r1 = 1;
            r6.mRequestRender = r1;	 Catch:{ all -> 0x003d }
            r1 = 0;
            r6.mRenderComplete = r1;	 Catch:{ all -> 0x003d }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x003d }
            r1 = r1.sGLThreadManager;	 Catch:{ all -> 0x003d }
            r1.notifyAll();	 Catch:{ all -> 0x003d }
        L_0x001b:
            r1 = r6.mExited;	 Catch:{ all -> 0x003d }
            if (r1 != 0) goto L_0x0040;
        L_0x001f:
            r1 = r6.mPaused;	 Catch:{ all -> 0x003d }
            if (r1 != 0) goto L_0x0040;
        L_0x0023:
            r1 = r6.mRenderComplete;	 Catch:{ all -> 0x003d }
            if (r1 != 0) goto L_0x0040;
        L_0x0027:
            r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            java.lang.Thread.sleep(r4);	 Catch:{ InterruptedException -> 0x0034 }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ InterruptedException -> 0x0034 }
            r1 = r1.sGLThreadManager;	 Catch:{ InterruptedException -> 0x0034 }
            r1.wait();	 Catch:{ InterruptedException -> 0x0034 }
            goto L_0x001b;
        L_0x0034:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x003d }
            r1.interrupt();	 Catch:{ all -> 0x003d }
            goto L_0x001b;
        L_0x003d:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x003d }
            throw r1;
        L_0x0040:
            monitor-exit(r2);	 Catch:{ all -> 0x003d }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.GLThread.onWindowResize(int, int):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void requestExitAndWait() {
            /*
            r3 = this;
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;
            r2 = r1.sGLThreadManager;
            monitor-enter(r2);
            r1 = 1;
            r3.mShouldExit = r1;	 Catch:{ all -> 0x0024 }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ all -> 0x0024 }
            r1 = r1.sGLThreadManager;	 Catch:{ all -> 0x0024 }
            r1.notifyAll();	 Catch:{ all -> 0x0024 }
        L_0x000f:
            r1 = r3.mExited;	 Catch:{ all -> 0x0024 }
            if (r1 != 0) goto L_0x0027;
        L_0x0013:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.this;	 Catch:{ InterruptedException -> 0x001b }
            r1 = r1.sGLThreadManager;	 Catch:{ InterruptedException -> 0x001b }
            r1.wait();	 Catch:{ InterruptedException -> 0x001b }
            goto L_0x000f;
        L_0x001b:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0024 }
            r1.interrupt();	 Catch:{ all -> 0x0024 }
            goto L_0x000f;
        L_0x0024:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0024 }
            throw r1;
        L_0x0027:
            monitor-exit(r2);	 Catch:{ all -> 0x0024 }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLBaseSurfaceViewLW.GLThread.requestExitAndWait():void");
        }

        public void queueEvent(Runnable r) {
            if (r == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (GLBaseSurfaceViewLW.this.sGLThreadManager) {
                this.mEventQueue.add(r);
                GLBaseSurfaceViewLW.this.sGLThreadManager.notifyAll();
            }
        }
    }

    class GLThreadManager {
        private GLThread mEglOwner;

        GLThreadManager() {
        }

        public synchronized void threadExiting(GLThread thread) {
            thread.mExited = GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED;
            if (this.mEglOwner == thread) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public synchronized boolean tryAcquireEglSurfaceLocked(GLThread thread) {
            boolean z;
            if (this.mEglOwner == thread || this.mEglOwner == null) {
                this.mEglOwner = thread;
                notifyAll();
                z = GLBaseSurfaceViewLW.DRAW_TWICE_AFTER_SIZE_CHANGED;
            } else {
                z = GLBaseSurfaceViewLW.LOG_VIEW;
            }
            return z;
        }

        public synchronized void releaseEglSurfaceLocked(GLThread thread) {
            if (this.mEglOwner == thread) {
                this.mEglOwner = null;
            }
            notifyAll();
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
            for (int i = 0; i < count; i += GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY) {
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
            this.mValue = new int[GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY];
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
            for (int i$ = 0; i$ < len$; i$ += GLBaseSurfaceViewLW.RENDERMODE_CONTINUOUSLY) {
                EGLConfig config = arr$[i$];
                int d = findConfigAttrib(egl, display, config, 12325, 0);
                int s = findConfigAttrib(egl, display, config, 12326, 0);
                if (d >= this.mDepthSize && s >= this.mStencilSize) {
                    int distance = ((Math.abs(findConfigAttrib(egl, display, config, 12324, 0) - this.mRedSize) + Math.abs(findConfigAttrib(egl, display, config, 12323, 0) - this.mGreenSize)) + Math.abs(findConfigAttrib(egl, display, config, 12322, 0) - this.mBlueSize)) + Math.abs(findConfigAttrib(egl, display, config, 12321, 0) - this.mAlphaSize);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestConfig = config;
                    }
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

    static class DefaultContextFactory implements EGLContextFactory {
        DefaultContextFactory() {
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, null);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            egl.eglDestroyContext(display, context);
        }
    }

    static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        DefaultWindowSurfaceFactory() {
        }

        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
            EGLSurface eglSurface = null;
            while (eglSurface == null) {
                try {
                    eglSurface = egl.eglCreateWindowSurface(display, config, nativeWindow, null);
                    if (eglSurface == null) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
                    }
                } catch (Throwable th) {
                    if (eglSurface == null) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e2) {
                        }
                    }
                }
            }
            return eglSurface;
        }

        public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
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

    public GLBaseSurfaceViewLW(Engine engine) {
        this.TAG = "GLBaseSurfaceView";
        this.sGLThreadManager = new GLThreadManager();
        this.mSizeChanged = DRAW_TWICE_AFTER_SIZE_CHANGED;
        this.engine = engine;
        init();
    }

    public GLBaseSurfaceViewLW(Engine engine, AttributeSet attrs) {
        this.TAG = "GLBaseSurfaceView";
        this.sGLThreadManager = new GLThreadManager();
        this.mSizeChanged = DRAW_TWICE_AFTER_SIZE_CHANGED;
        this.engine = engine;
        init();
    }

    private void init() {
        getHolder().addCallback(this);
    }

    public SurfaceHolder getHolder() {
        return this.engine.getSurfaceHolder();
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
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(DRAW_TWICE_AFTER_SIZE_CHANGED);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mGLThread = new GLThread(renderer);
        this.mGLThread.start();
    }

    public void setEGLContextFactory(ContextFactory contextFactory) {
        checkRenderThreadState();
        this.mEGLContextFactory = contextFactory;
    }

    public void setEGLWindowSurfaceFactory(ContextFactory contextFactory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = (EGLWindowSurfaceFactory) contextFactory;
    }

    public void setEGLConfigChooser(EGLConfigChooser configChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = configChooser;
    }

    public void setEGLConfigChooser(boolean needDepth) {
        setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
    }

    public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
    }

    public void setRenderMode(int renderMode) {
        this.mGLThread.setRenderMode(renderMode);
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.mGLThread.surfaceCreated();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.mGLThread.surfaceDestroyed();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        this.mGLThread.onWindowResize(w, h);
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable r) {
        this.mGLThread.queueEvent(r);
    }

    public void onDestroy() {
        this.mGLThread.requestExitAndWait();
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }
}
