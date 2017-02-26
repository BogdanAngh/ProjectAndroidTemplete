package com.badlogic.gdx.backends.android.surfaceview;

import android.content.Context;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy.MeasuredDimension;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.google.android.gms.drive.ExecutionOptions;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLSurfaceViewAPI18 extends SurfaceView implements Callback {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceViewAPI18";
    private static final GLThreadManager sGLThreadManager;
    private int mDebugFlags;
    private boolean mDetached;
    private EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private Renderer mRenderer;
    private final WeakReference<GLSurfaceViewAPI18> mThisWeakRef;
    final ResolutionStrategy resolutionStrategy;

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] configSpec) {
            this.mConfigSpec = filterConfigSpec(configSpec);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY];
            if (egl.eglChooseConfig(display, this.mConfigSpec, null, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY, num_config)) {
                int numConfigs = num_config[GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY];
                if (numConfigs <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
                EGLConfig[] configs = new EGLConfig[numConfigs];
                if (egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config)) {
                    EGLConfig config = chooseConfig(egl, display, configs);
                    if (config != null) {
                        return config;
                    }
                    throw new IllegalArgumentException("No config chosen");
                }
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }

        private int[] filterConfigSpec(int[] configSpec) {
            if (GLSurfaceViewAPI18.this.mEGLContextClientVersion != GLSurfaceViewAPI18.DEBUG_LOG_GL_CALLS) {
                return configSpec;
            }
            int len = configSpec.length;
            int[] newConfigSpec = new int[(len + GLSurfaceViewAPI18.DEBUG_LOG_GL_CALLS)];
            System.arraycopy(configSpec, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY, newConfigSpec, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY, len - 1);
            newConfigSpec[len - 1] = 12352;
            newConfigSpec[len] = 4;
            newConfigSpec[len + GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY] = 12344;
            return newConfigSpec;
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

    private static class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference<GLSurfaceViewAPI18> mGLSurfaceViewWeakRef;

        public EglHelper(WeakReference<GLSurfaceViewAPI18> glSurfaceViewWeakRef) {
            this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
        }

        public void start() {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (this.mEgl.eglInitialize(this.mEglDisplay, new int[GLSurfaceViewAPI18.DEBUG_LOG_GL_CALLS])) {
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view == null) {
                    this.mEglConfig = null;
                    this.mEglContext = null;
                } else {
                    this.mEglConfig = view.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                    this.mEglContext = view.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
                }
                if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                    this.mEglContext = null;
                    throwEglException("createContext");
                }
                this.mEglSurface = null;
                return;
            }
            throw new RuntimeException("eglInitialize failed");
        }

        public boolean createSurface() {
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            } else {
                destroySurfaceImp();
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    this.mEglSurface = view.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, view.getHolder());
                } else {
                    this.mEglSurface = null;
                }
                if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
                    if (this.mEgl.eglGetError() != 12299) {
                        return GLSurfaceViewAPI18.LOG_THREADS;
                    }
                    Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    return GLSurfaceViewAPI18.LOG_THREADS;
                } else if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                    return true;
                } else {
                    logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
                    return GLSurfaceViewAPI18.LOG_THREADS;
                }
            }
        }

        GL createGL() {
            GL gl = this.mEglContext.getGL();
            GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
            if (view == null) {
                return gl;
            }
            if (view.mGLWrapper != null) {
                gl = view.mGLWrapper.wrap(gl);
            }
            if ((view.mDebugFlags & 3) == 0) {
                return gl;
            }
            int configFlags = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY;
            Writer log = null;
            if ((view.mDebugFlags & GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY) != 0) {
                configFlags = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY | GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY;
            }
            if ((view.mDebugFlags & GLSurfaceViewAPI18.DEBUG_LOG_GL_CALLS) != 0) {
                log = new LogWriter();
            }
            return GLDebugHelper.wrap(gl, configFlags, log);
        }

        public int swap() {
            if (this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return GL11.GL_CLIP_PLANE0;
            }
            return this.mEgl.eglGetError();
        }

        public void destroySurface() {
            destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    view.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public void finish() {
            if (this.mEglContext != null) {
                GLSurfaceViewAPI18 view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    view.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(String function) {
            throwEglException(function, this.mEgl.eglGetError());
        }

        public static void throwEglException(String function, int error) {
            throw new RuntimeException(formatEglError(function, error));
        }

        public static void logEglErrorAsWarning(String tag, String function, int error) {
            Log.w(tag, formatEglError(function, error));
        }

        public static String formatEglError(String function, int error) {
            return function + " failed: " + EGLLogWrapper.getErrorString(error);
        }
    }

    static class GLThread extends Thread {
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue;
        private boolean mExited;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference<GLSurfaceViewAPI18> mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private int mHeight;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode;
        private boolean mRequestPaused;
        private boolean mRequestRender;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSizeChanged;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private int mWidth;

        GLThread(WeakReference<GLSurfaceViewAPI18> glSurfaceViewWeakRef) {
            this.mEventQueue = new ArrayList();
            this.mSizeChanged = true;
            this.mWidth = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY;
            this.mHeight = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY;
            this.mRequestRender = true;
            this.mRenderMode = GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY;
            this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
        }

        public void run() {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException e) {
            } finally {
                GLSurfaceViewAPI18.sGLThreadManager.threadExiting(this);
            }
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = GLSurfaceViewAPI18.LOG_THREADS;
                this.mEglHelper.destroySurface();
            }
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = GLSurfaceViewAPI18.LOG_THREADS;
                GLSurfaceViewAPI18.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        private void guardedRun() throws InterruptedException {
            this.mEglHelper = new EglHelper(this.mGLSurfaceViewWeakRef);
            this.mHaveEglContext = GLSurfaceViewAPI18.LOG_THREADS;
            this.mHaveEglSurface = GLSurfaceViewAPI18.LOG_THREADS;
            GL10 gl = null;
            boolean createEglContext = GLSurfaceViewAPI18.LOG_THREADS;
            boolean createEglSurface = GLSurfaceViewAPI18.LOG_THREADS;
            boolean createGlInterface = GLSurfaceViewAPI18.LOG_THREADS;
            boolean lostEglContext = GLSurfaceViewAPI18.LOG_THREADS;
            boolean sizeChanged = GLSurfaceViewAPI18.LOG_THREADS;
            boolean wantRenderNotification = GLSurfaceViewAPI18.LOG_THREADS;
            boolean doRenderNotification = GLSurfaceViewAPI18.LOG_THREADS;
            boolean askedToReleaseEglContext = GLSurfaceViewAPI18.LOG_THREADS;
            int w = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY;
            int h = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY;
            Runnable event = null;
            while (true) {
                synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                    while (true) {
                        if (this.mShouldExit) {
                            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                                stopEglSurfaceLocked();
                                stopEglContextLocked();
                            }
                            return;
                        }
                        GLSurfaceViewAPI18 view;
                        if (this.mEventQueue.isEmpty()) {
                            boolean pausing = GLSurfaceViewAPI18.LOG_THREADS;
                            if (this.mPaused != this.mRequestPaused) {
                                pausing = this.mRequestPaused;
                                this.mPaused = this.mRequestPaused;
                                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                            }
                            if (this.mShouldReleaseEglContext) {
                                stopEglSurfaceLocked();
                                stopEglContextLocked();
                                this.mShouldReleaseEglContext = GLSurfaceViewAPI18.LOG_THREADS;
                                askedToReleaseEglContext = true;
                            }
                            if (lostEglContext) {
                                stopEglSurfaceLocked();
                                stopEglContextLocked();
                                lostEglContext = GLSurfaceViewAPI18.LOG_THREADS;
                            }
                            if (pausing && this.mHaveEglSurface) {
                                stopEglSurfaceLocked();
                            }
                            if (pausing && this.mHaveEglContext) {
                                view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                                if (!(view == null ? GLSurfaceViewAPI18.LOG_THREADS : view.mPreserveEGLContextOnPause) || GLSurfaceViewAPI18.sGLThreadManager.shouldReleaseEGLContextWhenPausing()) {
                                    stopEglContextLocked();
                                }
                            }
                            if (pausing && GLSurfaceViewAPI18.sGLThreadManager.shouldTerminateEGLWhenPausing()) {
                                this.mEglHelper.finish();
                            }
                            if (!(this.mHasSurface || this.mWaitingForSurface)) {
                                if (this.mHaveEglSurface) {
                                    stopEglSurfaceLocked();
                                }
                                this.mWaitingForSurface = true;
                                this.mSurfaceIsBad = GLSurfaceViewAPI18.LOG_THREADS;
                                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                            }
                            if (this.mHasSurface && this.mWaitingForSurface) {
                                this.mWaitingForSurface = GLSurfaceViewAPI18.LOG_THREADS;
                                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                            }
                            if (doRenderNotification) {
                                wantRenderNotification = GLSurfaceViewAPI18.LOG_THREADS;
                                doRenderNotification = GLSurfaceViewAPI18.LOG_THREADS;
                                this.mRenderComplete = true;
                                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                            }
                            if (readyToDraw()) {
                                if (!this.mHaveEglContext) {
                                    if (askedToReleaseEglContext) {
                                        askedToReleaseEglContext = GLSurfaceViewAPI18.LOG_THREADS;
                                    } else {
                                        if (GLSurfaceViewAPI18.sGLThreadManager.tryAcquireEglContextLocked(this)) {
                                            this.mEglHelper.start();
                                            this.mHaveEglContext = true;
                                            createEglContext = true;
                                            GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                                        }
                                    }
                                }
                                if (this.mHaveEglContext && !this.mHaveEglSurface) {
                                    this.mHaveEglSurface = true;
                                    createEglSurface = true;
                                    createGlInterface = true;
                                    sizeChanged = true;
                                }
                                if (this.mHaveEglSurface) {
                                    if (this.mSizeChanged) {
                                        sizeChanged = true;
                                        w = this.mWidth;
                                        h = this.mHeight;
                                        wantRenderNotification = true;
                                        createEglSurface = true;
                                        this.mSizeChanged = GLSurfaceViewAPI18.LOG_THREADS;
                                    }
                                    this.mRequestRender = GLSurfaceViewAPI18.LOG_THREADS;
                                    GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                                }
                            }
                            GLSurfaceViewAPI18.sGLThreadManager.wait();
                        } else {
                            event = (Runnable) this.mEventQueue.remove(GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
                        }
                        if (event != null) {
                            try {
                                event.run();
                                event = null;
                            } catch (RuntimeException t) {
                                GLSurfaceViewAPI18.sGLThreadManager.releaseEglContextLocked(this);
                                throw t;
                            } catch (Throwable th) {
                                synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                                }
                                stopEglSurfaceLocked();
                                stopEglContextLocked();
                            }
                        } else {
                            if (createEglSurface) {
                                if (this.mEglHelper.createSurface()) {
                                    synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                                        this.mFinishedCreatingEglSurface = true;
                                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                                    }
                                    createEglSurface = GLSurfaceViewAPI18.LOG_THREADS;
                                } else {
                                    synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                                        this.mFinishedCreatingEglSurface = true;
                                        this.mSurfaceIsBad = true;
                                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                                    }
                                }
                            }
                            if (createGlInterface) {
                                gl = (GL10) this.mEglHelper.createGL();
                                GLSurfaceViewAPI18.sGLThreadManager.checkGLDriver(gl);
                                createGlInterface = GLSurfaceViewAPI18.LOG_THREADS;
                            }
                            if (createEglContext) {
                                view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                                if (view != null) {
                                    view.mRenderer.onSurfaceCreated(gl, this.mEglHelper.mEglConfig);
                                }
                                createEglContext = GLSurfaceViewAPI18.LOG_THREADS;
                            }
                            if (sizeChanged) {
                                view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                                if (view != null) {
                                    view.mRenderer.onSurfaceChanged(gl, w, h);
                                }
                                sizeChanged = GLSurfaceViewAPI18.LOG_THREADS;
                            }
                            view = (GLSurfaceViewAPI18) this.mGLSurfaceViewWeakRef.get();
                            if (view != null) {
                                view.mRenderer.onDrawFrame(gl);
                            }
                            int swapError = this.mEglHelper.swap();
                            switch (swapError) {
                                case GL11.GL_CLIP_PLANE0 /*12288*/:
                                    break;
                                case 12302:
                                    lostEglContext = true;
                                    break;
                                default:
                                    EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", swapError);
                                    synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                                        this.mSurfaceIsBad = true;
                                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                                        break;
                                    }
                            }
                            if (wantRenderNotification) {
                                doRenderNotification = true;
                            }
                        }
                    }
                }
            }
        }

        public boolean ableToDraw() {
            return (this.mHaveEglContext && this.mHaveEglSurface && readyToDraw()) ? true : GLSurfaceViewAPI18.LOG_THREADS;
        }

        private boolean readyToDraw() {
            return (this.mPaused || !this.mHasSurface || this.mSurfaceIsBad || this.mWidth <= 0 || this.mHeight <= 0 || !(this.mRequestRender || this.mRenderMode == GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY)) ? GLSurfaceViewAPI18.LOG_THREADS : true;
        }

        public void setRenderMode(int renderMode) {
            if (renderMode < 0 || renderMode > GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mRenderMode = renderMode;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
            }
        }

        public int getRenderMode() {
            int i;
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mRequestRender = true;
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void surfaceCreated() {
            /*
            r3 = this;
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r2);
            r1 = 1;
            r3.mHasSurface = r1;	 Catch:{ all -> 0x002f }
            r1 = 0;
            r3.mFinishedCreatingEglSurface = r1;	 Catch:{ all -> 0x002f }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x002f }
            r1.notifyAll();	 Catch:{ all -> 0x002f }
        L_0x0012:
            r1 = r3.mWaitingForSurface;	 Catch:{ all -> 0x002f }
            if (r1 == 0) goto L_0x0032;
        L_0x0016:
            r1 = r3.mFinishedCreatingEglSurface;	 Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x0032;
        L_0x001a:
            r1 = r3.mExited;	 Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x0032;
        L_0x001e:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ InterruptedException -> 0x0026 }
            r1.wait();	 Catch:{ InterruptedException -> 0x0026 }
            goto L_0x0012;
        L_0x0026:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x002f }
            r1.interrupt();	 Catch:{ all -> 0x002f }
            goto L_0x0012;
        L_0x002f:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x002f }
            throw r1;
        L_0x0032:
            monitor-exit(r2);	 Catch:{ all -> 0x002f }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.GLThread.surfaceCreated():void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void surfaceDestroyed() {
            /*
            r3 = this;
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r2);
            r1 = 0;
            r3.mHasSurface = r1;	 Catch:{ all -> 0x0028 }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0028 }
            r1.notifyAll();	 Catch:{ all -> 0x0028 }
        L_0x000f:
            r1 = r3.mWaitingForSurface;	 Catch:{ all -> 0x0028 }
            if (r1 != 0) goto L_0x002b;
        L_0x0013:
            r1 = r3.mExited;	 Catch:{ all -> 0x0028 }
            if (r1 != 0) goto L_0x002b;
        L_0x0017:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ InterruptedException -> 0x001f }
            r1.wait();	 Catch:{ InterruptedException -> 0x001f }
            goto L_0x000f;
        L_0x001f:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0028 }
            r1.interrupt();	 Catch:{ all -> 0x0028 }
            goto L_0x000f;
        L_0x0028:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            throw r1;
        L_0x002b:
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.GLThread.surfaceDestroyed():void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onPause() {
            /*
            r3 = this;
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r2);
            r1 = 1;
            r3.mRequestPaused = r1;	 Catch:{ all -> 0x0028 }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0028 }
            r1.notifyAll();	 Catch:{ all -> 0x0028 }
        L_0x000f:
            r1 = r3.mExited;	 Catch:{ all -> 0x0028 }
            if (r1 != 0) goto L_0x002b;
        L_0x0013:
            r1 = r3.mPaused;	 Catch:{ all -> 0x0028 }
            if (r1 != 0) goto L_0x002b;
        L_0x0017:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ InterruptedException -> 0x001f }
            r1.wait();	 Catch:{ InterruptedException -> 0x001f }
            goto L_0x000f;
        L_0x001f:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0028 }
            r1.interrupt();	 Catch:{ all -> 0x0028 }
            goto L_0x000f;
        L_0x0028:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            throw r1;
        L_0x002b:
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.GLThread.onPause():void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onResume() {
            /*
            r3 = this;
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r2);
            r1 = 0;
            r3.mRequestPaused = r1;	 Catch:{ all -> 0x0032 }
            r1 = 1;
            r3.mRequestRender = r1;	 Catch:{ all -> 0x0032 }
            r1 = 0;
            r3.mRenderComplete = r1;	 Catch:{ all -> 0x0032 }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0032 }
            r1.notifyAll();	 Catch:{ all -> 0x0032 }
        L_0x0015:
            r1 = r3.mExited;	 Catch:{ all -> 0x0032 }
            if (r1 != 0) goto L_0x0035;
        L_0x0019:
            r1 = r3.mPaused;	 Catch:{ all -> 0x0032 }
            if (r1 == 0) goto L_0x0035;
        L_0x001d:
            r1 = r3.mRenderComplete;	 Catch:{ all -> 0x0032 }
            if (r1 != 0) goto L_0x0035;
        L_0x0021:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ InterruptedException -> 0x0029 }
            r1.wait();	 Catch:{ InterruptedException -> 0x0029 }
            goto L_0x0015;
        L_0x0029:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0032 }
            r1.interrupt();	 Catch:{ all -> 0x0032 }
            goto L_0x0015;
        L_0x0032:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0032 }
            throw r1;
        L_0x0035:
            monitor-exit(r2);	 Catch:{ all -> 0x0032 }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.GLThread.onResume():void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onWindowResize(int r4, int r5) {
            /*
            r3 = this;
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r2);
            r3.mWidth = r4;	 Catch:{ all -> 0x003c }
            r3.mHeight = r5;	 Catch:{ all -> 0x003c }
            r1 = 1;
            r3.mSizeChanged = r1;	 Catch:{ all -> 0x003c }
            r1 = 1;
            r3.mRequestRender = r1;	 Catch:{ all -> 0x003c }
            r1 = 0;
            r3.mRenderComplete = r1;	 Catch:{ all -> 0x003c }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x003c }
            r1.notifyAll();	 Catch:{ all -> 0x003c }
        L_0x0019:
            r1 = r3.mExited;	 Catch:{ all -> 0x003c }
            if (r1 != 0) goto L_0x003f;
        L_0x001d:
            r1 = r3.mPaused;	 Catch:{ all -> 0x003c }
            if (r1 != 0) goto L_0x003f;
        L_0x0021:
            r1 = r3.mRenderComplete;	 Catch:{ all -> 0x003c }
            if (r1 != 0) goto L_0x003f;
        L_0x0025:
            r1 = r3.ableToDraw();	 Catch:{ all -> 0x003c }
            if (r1 == 0) goto L_0x003f;
        L_0x002b:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ InterruptedException -> 0x0033 }
            r1.wait();	 Catch:{ InterruptedException -> 0x0033 }
            goto L_0x0019;
        L_0x0033:
            r0 = move-exception;
            r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x003c }
            r1.interrupt();	 Catch:{ all -> 0x003c }
            goto L_0x0019;
        L_0x003c:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x003c }
            throw r1;
        L_0x003f:
            monitor-exit(r2);	 Catch:{ all -> 0x003c }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.GLThread.onWindowResize(int, int):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void requestExitAndWait() {
            /*
            r3 = this;
            r2 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;
            monitor-enter(r2);
            r1 = 1;
            r3.mShouldExit = r1;	 Catch:{ all -> 0x0024 }
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ all -> 0x0024 }
            r1.notifyAll();	 Catch:{ all -> 0x0024 }
        L_0x000f:
            r1 = r3.mExited;	 Catch:{ all -> 0x0024 }
            if (r1 != 0) goto L_0x0027;
        L_0x0013:
            r1 = com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.sGLThreadManager;	 Catch:{ InterruptedException -> 0x001b }
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
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18.GLThread.requestExitAndWait():void");
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
        }

        public void queueEvent(Runnable r) {
            if (r == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (GLSurfaceViewAPI18.sGLThreadManager) {
                this.mEventQueue.add(r);
                GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
            }
        }
    }

    private static class GLThreadManager {
        private static String TAG = null;
        private static final int kGLES_20 = 131072;
        private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
        private GLThread mEglOwner;
        private boolean mGLESDriverCheckComplete;
        private int mGLESVersion;
        private boolean mGLESVersionCheckComplete;
        private boolean mLimitedGLESContexts;
        private boolean mMultipleGLESContextsAllowed;

        private GLThreadManager() {
        }

        static {
            TAG = "GLThreadManager";
        }

        public synchronized void threadExiting(GLThread thread) {
            thread.mExited = true;
            if (this.mEglOwner == thread) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public boolean tryAcquireEglContextLocked(GLThread thread) {
            if (this.mEglOwner == thread || this.mEglOwner == null) {
                this.mEglOwner = thread;
                notifyAll();
                return true;
            }
            checkGLESVersion();
            if (this.mMultipleGLESContextsAllowed) {
                return true;
            }
            if (this.mEglOwner != null) {
                this.mEglOwner.requestReleaseEglContextLocked();
            }
            return GLSurfaceViewAPI18.LOG_THREADS;
        }

        public void releaseEglContextLocked(GLThread thread) {
            if (this.mEglOwner == thread) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public synchronized boolean shouldReleaseEGLContextWhenPausing() {
            return this.mLimitedGLESContexts;
        }

        public synchronized boolean shouldTerminateEGLWhenPausing() {
            checkGLESVersion();
            return !this.mMultipleGLESContextsAllowed ? true : GLSurfaceViewAPI18.LOG_THREADS;
        }

        public synchronized void checkGLDriver(GL10 gl) {
            boolean z = true;
            synchronized (this) {
                if (!this.mGLESDriverCheckComplete) {
                    checkGLESVersion();
                    String renderer = gl.glGetString(GL20.GL_RENDERER);
                    if (this.mGLESVersion < kGLES_20) {
                        boolean z2;
                        if (renderer.startsWith(kMSM7K_RENDERER_PREFIX)) {
                            z2 = GLSurfaceViewAPI18.LOG_THREADS;
                        } else {
                            z2 = true;
                        }
                        this.mMultipleGLESContextsAllowed = z2;
                        notifyAll();
                    }
                    if (this.mMultipleGLESContextsAllowed) {
                        z = GLSurfaceViewAPI18.LOG_THREADS;
                    }
                    this.mLimitedGLESContexts = z;
                    this.mGLESDriverCheckComplete = true;
                }
            }
        }

        private void checkGLESVersion() {
            if (!this.mGLESVersionCheckComplete) {
                this.mGLESVersion = ExecutionOptions.MAX_TRACKING_TAG_STRING_LENGTH;
                if (this.mGLESVersion >= kGLES_20) {
                    this.mMultipleGLESContextsAllowed = true;
                }
                this.mGLESVersionCheckComplete = true;
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
            for (int i = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY; i < count; i += GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY) {
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
                this.mBuilder.delete(GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY, this.mBuilder.length());
            }
        }
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;

        public ComponentSizeChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
            super(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344});
            this.mValue = new int[GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY];
            this.mRedSize = redSize;
            this.mGreenSize = greenSize;
            this.mBlueSize = blueSize;
            this.mAlphaSize = alphaSize;
            this.mDepthSize = depthSize;
            this.mStencilSize = stencilSize;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            EGLConfig[] arr$ = configs;
            int len$ = arr$.length;
            for (int i$ = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY; i$ < len$; i$ += GLSurfaceViewAPI18.RENDERMODE_CONTINUOUSLY) {
                EGLConfig config = arr$[i$];
                int d = findConfigAttrib(egl, display, config, 12325, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
                int s = findConfigAttrib(egl, display, config, 12326, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
                if (d >= this.mDepthSize && s >= this.mStencilSize) {
                    int r = findConfigAttrib(egl, display, config, 12324, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
                    int g = findConfigAttrib(egl, display, config, 12323, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
                    int b = findConfigAttrib(egl, display, config, 12322, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
                    int a = findConfigAttrib(egl, display, config, 12321, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
                    if (r == this.mRedSize && g == this.mGreenSize && b == this.mBlueSize && a == this.mAlphaSize) {
                        return config;
                    }
                }
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
                return this.mValue[GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY];
            }
            return defaultValue;
        }
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] attrib_list = new int[]{this.EGL_CONTEXT_CLIENT_VERSION, GLSurfaceViewAPI18.this.mEGLContextClientVersion, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (GLSurfaceViewAPI18.this.mEGLContextClientVersion == 0) {
                attrib_list = null;
            }
            return egl.eglCreateContext(display, config, eGLContext, attrib_list);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            if (!egl.eglDestroyContext(display, context)) {
                Log.e("DefaultContextFactory", "display:" + display + " context: " + context);
                EglHelper.throwEglException("eglDestroyContex", egl.eglGetError());
            }
        }
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
            EGLSurface result = null;
            try {
                result = egl.eglCreateWindowSurface(display, config, nativeWindow, null);
            } catch (IllegalArgumentException e) {
                Log.e(GLSurfaceViewAPI18.TAG, "eglCreateWindowSurface", e);
            }
            return result;
        }

        public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
        }
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean withDepthBuffer) {
            int i;
            if (withDepthBuffer) {
                i = 16;
            } else {
                i = GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY;
            }
            super(8, 8, 8, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY, i, GLSurfaceViewAPI18.RENDERMODE_WHEN_DIRTY);
        }
    }

    public GLSurfaceViewAPI18(Context context, ResolutionStrategy resolutionStrategy) {
        super(context);
        this.mThisWeakRef = new WeakReference(this);
        this.resolutionStrategy = resolutionStrategy;
        init();
    }

    public GLSurfaceViewAPI18(Context context, AttributeSet attrs, ResolutionStrategy resolutionStrategy) {
        super(context, attrs);
        this.mThisWeakRef = new WeakReference(this);
        this.resolutionStrategy = resolutionStrategy;
        init();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasuredDimension measures = this.resolutionStrategy.calcMeasures(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measures.width, measures.height);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        int sdkVersion = Integer.parseInt(VERSION.SDK);
        if (sdkVersion <= 8) {
            holder.setFormat(4);
        }
        if (sdkVersion <= 4) {
            holder.setType(DEBUG_LOG_GL_CALLS);
        }
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

    public void setPreserveEGLContextOnPause(boolean preserveOnPause) {
        this.mPreserveEGLContextOnPause = preserveOnPause;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        this.mGLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void setEGLContextFactory(EGLContextFactory factory) {
        checkRenderThreadState();
        this.mEGLContextFactory = factory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = factory;
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

    public void setEGLContextClientVersion(int version) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = version;
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

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            int renderMode = RENDERMODE_CONTINUOUSLY;
            if (this.mGLThread != null) {
                renderMode = this.mGLThread.getRenderMode();
            }
            this.mGLThread = new GLThread(this.mThisWeakRef);
            if (renderMode != RENDERMODE_CONTINUOUSLY) {
                this.mGLThread.setRenderMode(renderMode);
            }
            this.mGLThread.start();
        }
        this.mDetached = LOG_THREADS;
    }

    protected void onDetachedFromWindow() {
        if (this.mGLThread != null) {
            this.mGLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    static {
        sGLThreadManager = new GLThreadManager();
    }
}
