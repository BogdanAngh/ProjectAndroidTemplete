package com.badlogic.gdx.backends.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build;
import android.os.Build.VERSION;
import android.service.dreams.DreamService;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.BufferFormat;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.GraphicsType;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewCupcake;
import com.badlogic.gdx.backends.android.surfaceview.GdxEglConfigChooser;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy.MeasuredDimension;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.WindowedMean;
import com.google.android.gms.cast.TextTrackStyle;
import java.lang.reflect.Method;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

public final class AndroidGraphicsDaydream implements Graphics, Renderer {
    AndroidDaydream app;
    private BufferFormat bufferFormat;
    private final AndroidApplicationConfiguration config;
    volatile boolean created;
    private float deltaTime;
    private float density;
    volatile boolean destroy;
    EGLContext eglContext;
    String extensions;
    private int fps;
    private long frameStart;
    private int frames;
    GLCommon gl;
    GL10 gl10;
    GL11 gl11;
    GL20 gl20;
    int height;
    private boolean isContinuous;
    private long lastFrameTime;
    private WindowedMean mean;
    volatile boolean pause;
    private float ppcX;
    private float ppcY;
    private float ppiX;
    private float ppiY;
    volatile boolean resume;
    volatile boolean running;
    Object synch;
    int[] value;
    final View view;
    int width;

    /* renamed from: com.badlogic.gdx.backends.android.AndroidGraphicsDaydream.1 */
    class C00291 extends GLSurfaceView {
        final /* synthetic */ ResolutionStrategy val$resolutionStrategy;

        C00291(Context x0, ResolutionStrategy resolutionStrategy) {
            this.val$resolutionStrategy = resolutionStrategy;
            super(x0);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            MeasuredDimension measures = this.val$resolutionStrategy.calcMeasures(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(measures.width, measures.height);
        }
    }

    private class AndroidDisplayMode extends DisplayMode {
        protected AndroidDisplayMode(int width, int height, int refreshRate, int bitsPerPixel) {
            super(width, height, refreshRate, bitsPerPixel);
        }
    }

    public AndroidGraphicsDaydream(AndroidDaydream daydream, AndroidApplicationConfiguration config, ResolutionStrategy resolutionStrategy) {
        this.lastFrameTime = System.nanoTime();
        this.deltaTime = 0.0f;
        this.frameStart = System.nanoTime();
        this.frames = 0;
        this.mean = new WindowedMean(5);
        this.created = false;
        this.running = false;
        this.pause = false;
        this.resume = false;
        this.destroy = false;
        this.ppiX = 0.0f;
        this.ppiY = 0.0f;
        this.ppcX = 0.0f;
        this.ppcY = 0.0f;
        this.density = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.bufferFormat = new BufferFormat(5, 6, 5, 0, 16, 0, 0, false);
        this.isContinuous = true;
        this.value = new int[1];
        this.synch = new Object();
        this.config = config;
        this.view = createGLSurfaceView(daydream, config.useGL20, resolutionStrategy);
        setPreserveContext(this.view);
        this.view.setFocusable(true);
        this.view.setFocusableInTouchMode(true);
        this.app = daydream;
    }

    private void setPreserveContext(View view) {
        if (Integer.parseInt(VERSION.SDK) >= 11 && (view instanceof GLSurfaceView20)) {
            Method method = null;
            try {
                for (Method m : view.getClass().getMethods()) {
                    if (m.getName().equals("setPreserveEGLContextOnPause")) {
                        method = m;
                        break;
                    }
                }
                if (method != null) {
                    method.invoke((GLSurfaceView20) view, new Object[]{Boolean.valueOf(true)});
                }
            } catch (Exception e) {
            }
        }
    }

    private View createGLSurfaceView(DreamService dream, boolean useGL2, ResolutionStrategy resolutionStrategy) {
        GLSurfaceView20 view;
        EGLConfigChooser configChooser = getEglConfigChooser();
        if (useGL2 && checkGL20()) {
            view = new GLSurfaceView20(dream, resolutionStrategy);
            if (configChooser != null) {
                view.setEGLConfigChooser(configChooser);
            } else {
                view.setEGLConfigChooser(this.config.f34r, this.config.f33g, this.config.f32b, this.config.f31a, this.config.depth, this.config.stencil);
            }
            view.setRenderer(this);
        } else {
            this.config.useGL20 = false;
            configChooser = getEglConfigChooser();
            if (Integer.parseInt(VERSION.SDK) >= 11) {
                view = new C00291(dream, resolutionStrategy);
                if (configChooser != null) {
                    view.setEGLConfigChooser(configChooser);
                } else {
                    view.setEGLConfigChooser(this.config.f34r, this.config.f33g, this.config.f32b, this.config.f31a, this.config.depth, this.config.stencil);
                }
                view.setRenderer(this);
            } else if (this.config.useGLSurfaceViewAPI18) {
                view = new GLSurfaceViewAPI18(dream, resolutionStrategy);
                if (configChooser != null) {
                    view.setEGLConfigChooser(configChooser);
                } else {
                    view.setEGLConfigChooser(this.config.f34r, this.config.f33g, this.config.f32b, this.config.f31a, this.config.depth, this.config.stencil);
                }
                view.setRenderer(this);
            } else {
                view = new GLSurfaceViewCupcake(dream, resolutionStrategy);
                if (configChooser != null) {
                    view.setEGLConfigChooser(configChooser);
                } else {
                    view.setEGLConfigChooser(this.config.f34r, this.config.f33g, this.config.f32b, this.config.f31a, this.config.depth, this.config.stencil);
                }
                view.setRenderer(this);
            }
        }
        return view;
    }

    private EGLConfigChooser getEglConfigChooser() {
        return new GdxEglConfigChooser(this.config.f34r, this.config.f33g, this.config.f32b, this.config.f31a, this.config.depth, this.config.stencil, this.config.numSamples, this.config.useGL20);
    }

    private void updatePpi() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.app.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.ppiX = metrics.xdpi;
        this.ppiY = metrics.ydpi;
        this.ppcX = metrics.xdpi / 2.54f;
        this.ppcY = metrics.ydpi / 2.54f;
        this.density = metrics.density;
    }

    private boolean checkGL20() {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl.eglInitialize(display, new int[2]);
        int[] num_config = new int[1];
        egl.eglChooseConfig(display, new int[]{12324, 4, 12323, 4, 12322, 4, 12352, 4, 12344}, new EGLConfig[10], 10, num_config);
        egl.eglTerminate(display);
        return num_config[0] > 0;
    }

    public GL10 getGL10() {
        return this.gl10;
    }

    public GL11 getGL11() {
        return this.gl11;
    }

    public GL20 getGL20() {
        return this.gl20;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isGL11Available() {
        return this.gl11 != null;
    }

    public boolean isGL20Available() {
        return this.gl20 != null;
    }

    private static boolean isPowerOfTwo(int value) {
        return value != 0 && ((value - 1) & value) == 0;
    }

    private void setupGL(javax.microedition.khronos.opengles.GL10 gl) {
        if (this.gl10 == null && this.gl20 == null) {
            if (this.view instanceof GLSurfaceView20) {
                this.gl20 = new AndroidGL20();
                this.gl = this.gl20;
            } else {
                this.gl10 = new AndroidGL10(gl);
                this.gl = this.gl10;
                if (gl instanceof javax.microedition.khronos.opengles.GL11) {
                    String renderer = gl.glGetString(GL20.GL_RENDERER);
                    if (!(renderer == null || renderer.toLowerCase().contains("pixelflinger") || Build.MODEL.equals("MB200") || Build.MODEL.equals("MB220") || Build.MODEL.contains("Behold"))) {
                        this.gl11 = new AndroidGL11((javax.microedition.khronos.opengles.GL11) gl);
                        this.gl10 = this.gl11;
                    }
                }
            }
            Gdx.gl = this.gl;
            Gdx.gl10 = this.gl10;
            Gdx.gl11 = this.gl11;
            Gdx.gl20 = this.gl20;
            Gdx.app.log("AndroidGraphics", "OGL renderer: " + gl.glGetString(GL20.GL_RENDERER));
            Gdx.app.log("AndroidGraphics", "OGL vendor: " + gl.glGetString(GL20.GL_VENDOR));
            Gdx.app.log("AndroidGraphics", "OGL version: " + gl.glGetString(GL20.GL_VERSION));
            Gdx.app.log("AndroidGraphics", "OGL extensions: " + gl.glGetString(GL20.GL_EXTENSIONS));
        }
    }

    public void onSurfaceChanged(javax.microedition.khronos.opengles.GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        updatePpi();
        gl.glViewport(0, 0, this.width, this.height);
        if (!this.created) {
            this.app.listener.create();
            this.created = true;
            synchronized (this) {
                this.running = true;
            }
        }
        this.app.listener.resize(width, height);
    }

    public void onSurfaceCreated(javax.microedition.khronos.opengles.GL10 gl, EGLConfig config) {
        this.eglContext = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        setupGL(gl);
        logConfig(config);
        updatePpi();
        Mesh.invalidateAllMeshes(this.app);
        Texture.invalidateAllTextures(this.app);
        ShaderProgram.invalidateAllShaderPrograms(this.app);
        FrameBuffer.invalidateAllFrameBuffers(this.app);
        Gdx.app.log("AndroidGraphics", Mesh.getManagedStatus());
        Gdx.app.log("AndroidGraphics", Texture.getManagedStatus());
        Gdx.app.log("AndroidGraphics", ShaderProgram.getManagedStatus());
        Gdx.app.log("AndroidGraphics", FrameBuffer.getManagedStatus());
        Display display = this.app.getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = display.getHeight();
        this.mean = new WindowedMean(5);
        this.lastFrameTime = System.nanoTime();
        gl.glViewport(0, 0, this.width, this.height);
    }

    private void logConfig(EGLConfig config) {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        int r = getAttrib(egl, display, config, 12324, 0);
        int g = getAttrib(egl, display, config, 12323, 0);
        int b = getAttrib(egl, display, config, 12322, 0);
        int a = getAttrib(egl, display, config, 12321, 0);
        int d = getAttrib(egl, display, config, 12325, 0);
        int s = getAttrib(egl, display, config, 12326, 0);
        int samples = Math.max(getAttrib(egl, display, config, 12337, 0), getAttrib(egl, display, config, GdxEglConfigChooser.EGL_COVERAGE_SAMPLES_NV, 0));
        boolean coverageSample = getAttrib(egl, display, config, GdxEglConfigChooser.EGL_COVERAGE_SAMPLES_NV, 0) != 0;
        Gdx.app.log("AndroidGraphics", "framebuffer: (" + r + ", " + g + ", " + b + ", " + a + ")");
        Gdx.app.log("AndroidGraphics", "depthbuffer: (" + d + ")");
        Gdx.app.log("AndroidGraphics", "stencilbuffer: (" + s + ")");
        Gdx.app.log("AndroidGraphics", "samples: (" + samples + ")");
        Gdx.app.log("AndroidGraphics", "coverage sampling: (" + coverageSample + ")");
        this.bufferFormat = new BufferFormat(r, g, b, a, d, s, samples, coverageSample);
    }

    private int getAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attrib, int defValue) {
        if (egl.eglGetConfigAttrib(display, config, attrib, this.value)) {
            return this.value[0];
        }
        return defValue;
    }

    void resume() {
        synchronized (this.synch) {
            this.running = true;
            this.resume = true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void pause() {
        /*
        r5 = this;
        r2 = r5.synch;
        monitor-enter(r2);
        r1 = r5.running;	 Catch:{ all -> 0x0024 }
        if (r1 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r2);	 Catch:{ all -> 0x0024 }
    L_0x0008:
        return;
    L_0x0009:
        r1 = 0;
        r5.running = r1;	 Catch:{ all -> 0x0024 }
        r1 = 1;
        r5.pause = r1;	 Catch:{ all -> 0x0024 }
    L_0x000f:
        r1 = r5.pause;	 Catch:{ all -> 0x0024 }
        if (r1 == 0) goto L_0x0027;
    L_0x0013:
        r1 = r5.synch;	 Catch:{ InterruptedException -> 0x0019 }
        r1.wait();	 Catch:{ InterruptedException -> 0x0019 }
        goto L_0x000f;
    L_0x0019:
        r0 = move-exception;
        r1 = com.badlogic.gdx.Gdx.app;	 Catch:{ all -> 0x0024 }
        r3 = "AndroidGraphics";
        r4 = "waiting for pause synchronization failed!";
        r1.log(r3, r4);	 Catch:{ all -> 0x0024 }
        goto L_0x000f;
    L_0x0024:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0024 }
        throw r1;
    L_0x0027:
        monitor-exit(r2);	 Catch:{ all -> 0x0024 }
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidGraphicsDaydream.pause():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void destroy() {
        /*
        r5 = this;
        r2 = r5.synch;
        monitor-enter(r2);
        r1 = 0;
        r5.running = r1;	 Catch:{ all -> 0x001e }
        r1 = 1;
        r5.destroy = r1;	 Catch:{ all -> 0x001e }
    L_0x0009:
        r1 = r5.destroy;	 Catch:{ all -> 0x001e }
        if (r1 == 0) goto L_0x0021;
    L_0x000d:
        r1 = r5.synch;	 Catch:{ InterruptedException -> 0x0013 }
        r1.wait();	 Catch:{ InterruptedException -> 0x0013 }
        goto L_0x0009;
    L_0x0013:
        r0 = move-exception;
        r1 = com.badlogic.gdx.Gdx.app;	 Catch:{ all -> 0x001e }
        r3 = "AndroidGraphics";
        r4 = "waiting for destroy synchronization failed!";
        r1.log(r3, r4);	 Catch:{ all -> 0x001e }
        goto L_0x0009;
    L_0x001e:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
        throw r1;
    L_0x0021:
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidGraphicsDaydream.destroy():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDrawFrame(javax.microedition.khronos.opengles.GL10 r19) {
        /*
        r18 = this;
        r12 = java.lang.System.nanoTime();
        r0 = r18;
        r14 = r0.lastFrameTime;
        r14 = r12 - r14;
        r11 = (float) r14;
        r14 = 1315859240; // 0x4e6e6b28 float:1.0E9 double:6.50120845E-315;
        r11 = r11 / r14;
        r0 = r18;
        r0.deltaTime = r11;
        r0 = r18;
        r0.lastFrameTime = r12;
        r0 = r18;
        r11 = r0.mean;
        r0 = r18;
        r14 = r0.deltaTime;
        r11.addValue(r14);
        r9 = 0;
        r7 = 0;
        r4 = 0;
        r8 = 0;
        r0 = r18;
        r14 = r0.synch;
        monitor-enter(r14);
        r0 = r18;
        r9 = r0.running;	 Catch:{ all -> 0x008b }
        r0 = r18;
        r7 = r0.pause;	 Catch:{ all -> 0x008b }
        r0 = r18;
        r4 = r0.destroy;	 Catch:{ all -> 0x008b }
        r0 = r18;
        r8 = r0.resume;	 Catch:{ all -> 0x008b }
        r0 = r18;
        r11 = r0.resume;	 Catch:{ all -> 0x008b }
        if (r11 == 0) goto L_0x0046;
    L_0x0041:
        r11 = 0;
        r0 = r18;
        r0.resume = r11;	 Catch:{ all -> 0x008b }
    L_0x0046:
        r0 = r18;
        r11 = r0.pause;	 Catch:{ all -> 0x008b }
        if (r11 == 0) goto L_0x0058;
    L_0x004c:
        r11 = 0;
        r0 = r18;
        r0.pause = r11;	 Catch:{ all -> 0x008b }
        r0 = r18;
        r11 = r0.synch;	 Catch:{ all -> 0x008b }
        r11.notifyAll();	 Catch:{ all -> 0x008b }
    L_0x0058:
        r0 = r18;
        r11 = r0.destroy;	 Catch:{ all -> 0x008b }
        if (r11 == 0) goto L_0x006a;
    L_0x005e:
        r11 = 0;
        r0 = r18;
        r0.destroy = r11;	 Catch:{ all -> 0x008b }
        r0 = r18;
        r11 = r0.synch;	 Catch:{ all -> 0x008b }
        r11.notifyAll();	 Catch:{ all -> 0x008b }
    L_0x006a:
        monitor-exit(r14);	 Catch:{ all -> 0x008b }
        if (r8 == 0) goto L_0x00aa;
    L_0x006d:
        r0 = r18;
        r11 = r0.app;
        r6 = r11.lifecycleListeners;
        monitor-enter(r6);
        r3 = r6.iterator();	 Catch:{ all -> 0x0088 }
    L_0x0078:
        r11 = r3.hasNext();	 Catch:{ all -> 0x0088 }
        if (r11 == 0) goto L_0x008e;
    L_0x007e:
        r5 = r3.next();	 Catch:{ all -> 0x0088 }
        r5 = (com.badlogic.gdx.LifecycleListener) r5;	 Catch:{ all -> 0x0088 }
        r5.resume();	 Catch:{ all -> 0x0088 }
        goto L_0x0078;
    L_0x0088:
        r11 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0088 }
        throw r11;
    L_0x008b:
        r11 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x008b }
        throw r11;
    L_0x008e:
        monitor-exit(r6);	 Catch:{ all -> 0x0088 }
        r0 = r18;
        r11 = r0.app;
        r11 = r11.audio;
        r11.resume();
        r0 = r18;
        r11 = r0.app;
        r11 = r11.listener;
        r11.resume();
        r11 = com.badlogic.gdx.Gdx.app;
        r14 = "AndroidGraphics";
        r15 = "resumed";
        r11.log(r14, r15);
    L_0x00aa:
        if (r9 == 0) goto L_0x010c;
    L_0x00ac:
        r0 = r18;
        r11 = r0.app;
        r14 = r11.runnables;
        monitor-enter(r14);
        r0 = r18;
        r11 = r0.app;	 Catch:{ all -> 0x00f6 }
        r11 = r11.executedRunnables;	 Catch:{ all -> 0x00f6 }
        r11.clear();	 Catch:{ all -> 0x00f6 }
        r0 = r18;
        r11 = r0.app;	 Catch:{ all -> 0x00f6 }
        r11 = r11.executedRunnables;	 Catch:{ all -> 0x00f6 }
        r0 = r18;
        r15 = r0.app;	 Catch:{ all -> 0x00f6 }
        r15 = r15.runnables;	 Catch:{ all -> 0x00f6 }
        r11.addAll(r15);	 Catch:{ all -> 0x00f6 }
        r0 = r18;
        r11 = r0.app;	 Catch:{ all -> 0x00f6 }
        r11 = r11.runnables;	 Catch:{ all -> 0x00f6 }
        r11.clear();	 Catch:{ all -> 0x00f6 }
        r2 = 0;
    L_0x00d5:
        r0 = r18;
        r11 = r0.app;	 Catch:{ all -> 0x00f6 }
        r11 = r11.executedRunnables;	 Catch:{ all -> 0x00f6 }
        r11 = r11.size;	 Catch:{ all -> 0x00f6 }
        if (r2 >= r11) goto L_0x00f9;
    L_0x00df:
        r0 = r18;
        r11 = r0.app;	 Catch:{ Throwable -> 0x00f1 }
        r11 = r11.executedRunnables;	 Catch:{ Throwable -> 0x00f1 }
        r11 = r11.get(r2);	 Catch:{ Throwable -> 0x00f1 }
        r11 = (java.lang.Runnable) r11;	 Catch:{ Throwable -> 0x00f1 }
        r11.run();	 Catch:{ Throwable -> 0x00f1 }
    L_0x00ee:
        r2 = r2 + 1;
        goto L_0x00d5;
    L_0x00f1:
        r10 = move-exception;
        r10.printStackTrace();	 Catch:{ all -> 0x00f6 }
        goto L_0x00ee;
    L_0x00f6:
        r11 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x00f6 }
        throw r11;
    L_0x00f9:
        monitor-exit(r14);	 Catch:{ all -> 0x00f6 }
        r0 = r18;
        r11 = r0.app;
        r11 = r11.input;
        r11.processEvents();
        r0 = r18;
        r11 = r0.app;
        r11 = r11.listener;
        r11.render();
    L_0x010c:
        if (r7 == 0) goto L_0x0148;
    L_0x010e:
        r0 = r18;
        r11 = r0.app;
        r6 = r11.lifecycleListeners;
        monitor-enter(r6);
        r3 = r6.iterator();	 Catch:{ all -> 0x0129 }
    L_0x0119:
        r11 = r3.hasNext();	 Catch:{ all -> 0x0129 }
        if (r11 == 0) goto L_0x012c;
    L_0x011f:
        r5 = r3.next();	 Catch:{ all -> 0x0129 }
        r5 = (com.badlogic.gdx.LifecycleListener) r5;	 Catch:{ all -> 0x0129 }
        r5.pause();	 Catch:{ all -> 0x0129 }
        goto L_0x0119;
    L_0x0129:
        r11 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0129 }
        throw r11;
    L_0x012c:
        monitor-exit(r6);	 Catch:{ all -> 0x0129 }
        r0 = r18;
        r11 = r0.app;
        r11 = r11.listener;
        r11.pause();
        r0 = r18;
        r11 = r0.app;
        r11 = r11.audio;
        r11.pause();
        r11 = com.badlogic.gdx.Gdx.app;
        r14 = "AndroidGraphics";
        r15 = "paused";
        r11.log(r14, r15);
    L_0x0148:
        if (r4 == 0) goto L_0x018b;
    L_0x014a:
        r0 = r18;
        r11 = r0.app;
        r6 = r11.lifecycleListeners;
        monitor-enter(r6);
        r3 = r6.iterator();	 Catch:{ all -> 0x0165 }
    L_0x0155:
        r11 = r3.hasNext();	 Catch:{ all -> 0x0165 }
        if (r11 == 0) goto L_0x0168;
    L_0x015b:
        r5 = r3.next();	 Catch:{ all -> 0x0165 }
        r5 = (com.badlogic.gdx.LifecycleListener) r5;	 Catch:{ all -> 0x0165 }
        r5.dispose();	 Catch:{ all -> 0x0165 }
        goto L_0x0155;
    L_0x0165:
        r11 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0165 }
        throw r11;
    L_0x0168:
        monitor-exit(r6);	 Catch:{ all -> 0x0165 }
        r0 = r18;
        r11 = r0.app;
        r11 = r11.listener;
        r11.dispose();
        r0 = r18;
        r11 = r0.app;
        r11 = r11.audio;
        r11.dispose();
        r0 = r18;
        r11 = r0.app;
        r14 = 0;
        r11.audio = r14;
        r11 = com.badlogic.gdx.Gdx.app;
        r14 = "AndroidGraphics";
        r15 = "destroyed";
        r11.log(r14, r15);
    L_0x018b:
        r0 = r18;
        r14 = r0.frameStart;
        r14 = r12 - r14;
        r16 = 1000000000; // 0x3b9aca00 float:0.0047237873 double:4.94065646E-315;
        r11 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r11 <= 0) goto L_0x01a9;
    L_0x0198:
        r0 = r18;
        r11 = r0.frames;
        r0 = r18;
        r0.fps = r11;
        r11 = 0;
        r0 = r18;
        r0.frames = r11;
        r0 = r18;
        r0.frameStart = r12;
    L_0x01a9:
        r0 = r18;
        r11 = r0.frames;
        r11 = r11 + 1;
        r0 = r18;
        r0.frames = r11;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidGraphicsDaydream.onDrawFrame(javax.microedition.khronos.opengles.GL10):void");
    }

    public float getDeltaTime() {
        return this.mean.getMean() == 0.0f ? this.deltaTime : this.mean.getMean();
    }

    public float getRawDeltaTime() {
        return this.deltaTime;
    }

    public GraphicsType getType() {
        return GraphicsType.AndroidGL;
    }

    public int getFramesPerSecond() {
        return this.fps;
    }

    public void clearManagedCaches() {
        Mesh.clearAllMeshes(this.app);
        Texture.clearAllTextures(this.app);
        ShaderProgram.clearAllShaderPrograms(this.app);
        FrameBuffer.clearAllFrameBuffers(this.app);
        Gdx.app.log("AndroidGraphics", Mesh.getManagedStatus());
        Gdx.app.log("AndroidGraphics", Texture.getManagedStatus());
        Gdx.app.log("AndroidGraphics", ShaderProgram.getManagedStatus());
        Gdx.app.log("AndroidGraphics", FrameBuffer.getManagedStatus());
    }

    public View getView() {
        return this.view;
    }

    public GLCommon getGLCommon() {
        return this.gl;
    }

    public float getPpiX() {
        return this.ppiX;
    }

    public float getPpiY() {
        return this.ppiY;
    }

    public float getPpcX() {
        return this.ppcX;
    }

    public float getPpcY() {
        return this.ppcY;
    }

    public float getDensity() {
        return this.density;
    }

    public boolean supportsDisplayModeChange() {
        return false;
    }

    public boolean setDisplayMode(DisplayMode displayMode) {
        return false;
    }

    public DisplayMode[] getDisplayModes() {
        return new DisplayMode[]{getDesktopDisplayMode()};
    }

    public boolean setDisplayMode(int width, int height, boolean fullscreen) {
        return false;
    }

    public void setTitle(String title) {
    }

    public DisplayMode getDesktopDisplayMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.app.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new AndroidDisplayMode(metrics.widthPixels, metrics.heightPixels, 0, 0);
    }

    public BufferFormat getBufferFormat() {
        return this.bufferFormat;
    }

    public void setVSync(boolean vsync) {
    }

    public boolean supportsExtension(String extension) {
        if (this.extensions == null) {
            this.extensions = Gdx.gl.glGetString(GL20.GL_EXTENSIONS);
        }
        return this.extensions.contains(extension);
    }

    public void setContinuousRendering(boolean isContinuous) {
        if (this.view != null) {
            this.isContinuous = isContinuous;
            int renderMode = isContinuous ? 1 : 0;
            if (this.view instanceof GLSurfaceViewCupcake) {
                ((GLSurfaceViewCupcake) this.view).setRenderMode(renderMode);
            }
            if (this.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.view).setRenderMode(renderMode);
            }
            if (this.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.view).setRenderMode(renderMode);
            }
            this.mean.clear();
        }
    }

    public boolean isContinuousRendering() {
        return this.isContinuous;
    }

    public void requestRendering() {
        if (this.view != null) {
            if (this.view instanceof GLSurfaceViewCupcake) {
                ((GLSurfaceViewCupcake) this.view).requestRender();
            }
            if (this.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.view).requestRender();
            }
            if (this.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.view).requestRender();
            }
        }
    }

    public boolean isFullscreen() {
        return true;
    }
}
