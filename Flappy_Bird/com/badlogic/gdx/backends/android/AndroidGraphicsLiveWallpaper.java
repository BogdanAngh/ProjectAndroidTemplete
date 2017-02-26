package com.badlogic.gdx.backends.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
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

public final class AndroidGraphicsLiveWallpaper implements Graphics, Renderer {
    AndroidLiveWallpaper app;
    private BufferFormat bufferFormat;
    private final AndroidApplicationConfiguration config;
    boolean configLogged;
    volatile boolean created;
    protected float deltaTime;
    protected float density;
    volatile boolean destroy;
    protected EGLContext eglContext;
    protected String extensions;
    protected int fps;
    protected long frameStart;
    protected int frames;
    protected GLCommon gl;
    protected GL10 gl10;
    protected GL11 gl11;
    protected GL20 gl20;
    protected GLU glu;
    int height;
    protected boolean isContinuous;
    protected long lastFrameTime;
    protected WindowedMean mean;
    volatile boolean pause;
    protected float ppcX;
    protected float ppcY;
    protected float ppiX;
    protected float ppiY;
    volatile boolean resume;
    volatile boolean running;
    Object synch;
    int[] value;
    final View view;
    int width;

    /* renamed from: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.2 */
    class C00302 extends GLSurfaceView {
        final /* synthetic */ ResolutionStrategy val$resolutionStrategy;

        C00302(Context x0, ResolutionStrategy resolutionStrategy) {
            this.val$resolutionStrategy = resolutionStrategy;
            super(x0);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            MeasuredDimension measures = this.val$resolutionStrategy.calcMeasures(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(measures.width, measures.height);
        }

        public SurfaceHolder getHolder() {
            return AndroidGraphicsLiveWallpaper.this.getSurfaceHolder();
        }

        public void onDestroy() {
            onDetachedFromWindow();
        }
    }

    /* renamed from: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.1 */
    class C03431 extends GLSurfaceView20 {
        C03431(Context x0, ResolutionStrategy x1) {
            super(x0, x1);
        }

        public SurfaceHolder getHolder() {
            return AndroidGraphicsLiveWallpaper.this.getSurfaceHolder();
        }

        public void onDestroy() {
            onDetachedFromWindow();
        }
    }

    /* renamed from: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.3 */
    class C03443 extends GLSurfaceViewAPI18 {
        C03443(Context x0, ResolutionStrategy x1) {
            super(x0, x1);
        }

        public SurfaceHolder getHolder() {
            return AndroidGraphicsLiveWallpaper.this.getSurfaceHolder();
        }
    }

    /* renamed from: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.4 */
    class C03454 extends GLSurfaceViewCupcake {
        C03454(Context x0, ResolutionStrategy x1) {
            super(x0, x1);
        }

        public SurfaceHolder getHolder() {
            return AndroidGraphicsLiveWallpaper.this.getSurfaceHolder();
        }
    }

    private class AndroidDisplayMode extends DisplayMode {
        protected AndroidDisplayMode(int width, int height, int refreshRate, int bitsPerPixel) {
            super(width, height, refreshRate, bitsPerPixel);
        }
    }

    public AndroidGraphicsLiveWallpaper(AndroidLiveWallpaper app, AndroidApplicationConfiguration config, ResolutionStrategy resolutionStrategy) {
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
        this.configLogged = false;
        this.value = new int[1];
        this.synch = new Object();
        this.config = config;
        this.app = app;
        this.view = createGLSurfaceView(app.service, config.useGL20, resolutionStrategy);
        setPreserveContext(this.view);
    }

    private void setPreserveContext(Object view) {
        if (Integer.parseInt(VERSION.SDK) >= 11 && (view instanceof GLSurfaceView)) {
            Method method = null;
            try {
                for (Method m : view.getClass().getMethods()) {
                    if (m.getName().equals("setPreserveEGLContextOnPause")) {
                        method = m;
                        break;
                    }
                }
                if (method != null) {
                    method.invoke((GLSurfaceView) view, new Object[]{Boolean.valueOf(true)});
                }
            } catch (Exception e) {
            }
        }
    }

    SurfaceHolder getSurfaceHolder() {
        SurfaceHolder surfaceHolder;
        synchronized (this.app.service.sync) {
            surfaceHolder = this.app.service.getSurfaceHolder();
        }
        return surfaceHolder;
    }

    private View createGLSurfaceView(Context context, boolean useGL2, ResolutionStrategy resolutionStrategy) {
        GLSurfaceView20 view;
        EGLConfigChooser configChooser = getEglConfigChooser();
        if (useGL2 && checkGL20()) {
            view = new C03431(context, resolutionStrategy);
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
                view = new C00302(context, resolutionStrategy);
                if (configChooser != null) {
                    view.setEGLConfigChooser(configChooser);
                } else {
                    view.setEGLConfigChooser(this.config.f34r, this.config.f33g, this.config.f32b, this.config.f31a, this.config.depth, this.config.stencil);
                }
                view.setRenderer(this);
            } else if (this.config.useGLSurfaceViewAPI18) {
                view = new C03443(context, resolutionStrategy);
                if (configChooser != null) {
                    view.setEGLConfigChooser(configChooser);
                } else {
                    view.setEGLConfigChooser(this.config.f34r, this.config.f33g, this.config.f32b, this.config.f31a, this.config.depth, this.config.stencil);
                }
                view.setRenderer(this);
            } else {
                view = new C03454(context, resolutionStrategy);
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

    protected boolean checkGL20() {
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
        if (AndroidLiveWallpaperService.DEBUG) {
            Gdx.app.debug("AndroidGraphics", Mesh.getManagedStatus());
            Gdx.app.debug("AndroidGraphics", Texture.getManagedStatus());
            Gdx.app.debug("AndroidGraphics", ShaderProgram.getManagedStatus());
            Gdx.app.debug("AndroidGraphics", FrameBuffer.getManagedStatus());
        }
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
        if (!this.configLogged) {
            if (this.gl != null) {
                Gdx.app.log("AndroidGraphics", "OGL renderer: " + this.gl.glGetString(GL20.GL_RENDERER));
                Gdx.app.log("AndroidGraphics", "OGL vendor: " + this.gl.glGetString(GL20.GL_VENDOR));
                Gdx.app.log("AndroidGraphics", "OGL version: " + this.gl.glGetString(GL20.GL_VERSION));
                Gdx.app.log("AndroidGraphics", "OGL extensions: " + this.gl.glGetString(GL20.GL_EXTENSIONS));
                this.configLogged = true;
            }
            Gdx.app.log("AndroidGraphics", "framebuffer: (" + r + ", " + g + ", " + b + ", " + a + ")");
            Gdx.app.log("AndroidGraphics", "depthbuffer: (" + d + ")");
            Gdx.app.log("AndroidGraphics", "stencilbuffer: (" + s + ")");
            Gdx.app.log("AndroidGraphics", "samples: (" + samples + ")");
            Gdx.app.log("AndroidGraphics", "coverage sampling: (" + coverageSample + ")");
        }
        this.bufferFormat = new BufferFormat(r, g, b, a, d, s, samples, coverageSample);
    }

    private int getAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attrib, int defValue) {
        if (egl.eglGetConfigAttrib(display, config, attrib, this.value)) {
            return this.value[0];
        }
        return defValue;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void resume() {
        /*
        r5 = this;
        r2 = r5.synch;
        monitor-enter(r2);
        r1 = 1;
        r5.running = r1;	 Catch:{ all -> 0x001e }
        r1 = 1;
        r5.resume = r1;	 Catch:{ all -> 0x001e }
    L_0x0009:
        r1 = r5.resume;	 Catch:{ all -> 0x001e }
        if (r1 == 0) goto L_0x0021;
    L_0x000d:
        r1 = r5.synch;	 Catch:{ InterruptedException -> 0x0013 }
        r1.wait();	 Catch:{ InterruptedException -> 0x0013 }
        goto L_0x0009;
    L_0x0013:
        r0 = move-exception;
        r1 = com.badlogic.gdx.Gdx.app;	 Catch:{ all -> 0x001e }
        r3 = "AndroidGraphics";
        r4 = "waiting for resume synchronization failed!";
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
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.resume():void");
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
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.pause():void");
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
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.destroy():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDrawFrame(javax.microedition.khronos.opengles.GL10 r14) {
        /*
        r13 = this;
        r12 = 0;
        r6 = java.lang.System.nanoTime();
        r8 = r13.lastFrameTime;
        r8 = r6 - r8;
        r8 = (float) r8;
        r9 = 1315859240; // 0x4e6e6b28 float:1.0E9 double:6.50120845E-315;
        r8 = r8 / r9;
        r13.deltaTime = r8;
        r13.lastFrameTime = r6;
        r9 = r13.mean;
        r8 = r13.resume;
        if (r8 == 0) goto L_0x00b3;
    L_0x0018:
        r8 = 0;
    L_0x0019:
        r9.addValue(r8);
        r4 = 0;
        r2 = 0;
        r1 = 0;
        r3 = 0;
        r9 = r13.synch;
        monitor-enter(r9);
        r4 = r13.running;	 Catch:{ all -> 0x00b7 }
        r2 = r13.pause;	 Catch:{ all -> 0x00b7 }
        r1 = r13.destroy;	 Catch:{ all -> 0x00b7 }
        r3 = r13.resume;	 Catch:{ all -> 0x00b7 }
        r8 = r13.resume;	 Catch:{ all -> 0x00b7 }
        if (r8 == 0) goto L_0x0037;
    L_0x002f:
        r8 = 0;
        r13.resume = r8;	 Catch:{ all -> 0x00b7 }
        r8 = r13.synch;	 Catch:{ all -> 0x00b7 }
        r8.notifyAll();	 Catch:{ all -> 0x00b7 }
    L_0x0037:
        r8 = r13.pause;	 Catch:{ all -> 0x00b7 }
        if (r8 == 0) goto L_0x0043;
    L_0x003b:
        r8 = 0;
        r13.pause = r8;	 Catch:{ all -> 0x00b7 }
        r8 = r13.synch;	 Catch:{ all -> 0x00b7 }
        r8.notifyAll();	 Catch:{ all -> 0x00b7 }
    L_0x0043:
        r8 = r13.destroy;	 Catch:{ all -> 0x00b7 }
        if (r8 == 0) goto L_0x004f;
    L_0x0047:
        r8 = 0;
        r13.destroy = r8;	 Catch:{ all -> 0x00b7 }
        r8 = r13.synch;	 Catch:{ all -> 0x00b7 }
        r8.notifyAll();	 Catch:{ all -> 0x00b7 }
    L_0x004f:
        monitor-exit(r9);	 Catch:{ all -> 0x00b7 }
        if (r3 == 0) goto L_0x0062;
    L_0x0052:
        r8 = r13.app;
        r8 = r8.listener;
        r8.resume();
        r8 = com.badlogic.gdx.Gdx.app;
        r9 = "AndroidGraphics";
        r10 = "resumed";
        r8.log(r9, r10);
    L_0x0062:
        if (r4 == 0) goto L_0x00d1;
    L_0x0064:
        r8 = com.badlogic.gdx.Gdx.graphics;
        r8 = r8.getGL10();
        if (r8 != 0) goto L_0x007c;
    L_0x006c:
        r8 = com.badlogic.gdx.Gdx.graphics;
        r8 = r8.getGL11();
        if (r8 != 0) goto L_0x007c;
    L_0x0074:
        r8 = com.badlogic.gdx.Gdx.graphics;
        r8 = r8.getGL20();
        if (r8 == 0) goto L_0x00d1;
    L_0x007c:
        r8 = r13.app;
        r9 = r8.runnables;
        monitor-enter(r9);
        r8 = r13.app;	 Catch:{ all -> 0x00bf }
        r8 = r8.executedRunnables;	 Catch:{ all -> 0x00bf }
        r8.clear();	 Catch:{ all -> 0x00bf }
        r8 = r13.app;	 Catch:{ all -> 0x00bf }
        r8 = r8.executedRunnables;	 Catch:{ all -> 0x00bf }
        r10 = r13.app;	 Catch:{ all -> 0x00bf }
        r10 = r10.runnables;	 Catch:{ all -> 0x00bf }
        r8.addAll(r10);	 Catch:{ all -> 0x00bf }
        r8 = r13.app;	 Catch:{ all -> 0x00bf }
        r8 = r8.runnables;	 Catch:{ all -> 0x00bf }
        r8.clear();	 Catch:{ all -> 0x00bf }
        r0 = 0;
    L_0x009b:
        r8 = r13.app;	 Catch:{ all -> 0x00bf }
        r8 = r8.executedRunnables;	 Catch:{ all -> 0x00bf }
        r8 = r8.size;	 Catch:{ all -> 0x00bf }
        if (r0 >= r8) goto L_0x00c2;
    L_0x00a3:
        r8 = r13.app;	 Catch:{ Throwable -> 0x00ba }
        r8 = r8.executedRunnables;	 Catch:{ Throwable -> 0x00ba }
        r8 = r8.get(r0);	 Catch:{ Throwable -> 0x00ba }
        r8 = (java.lang.Runnable) r8;	 Catch:{ Throwable -> 0x00ba }
        r8.run();	 Catch:{ Throwable -> 0x00ba }
    L_0x00b0:
        r0 = r0 + 1;
        goto L_0x009b;
    L_0x00b3:
        r8 = r13.deltaTime;
        goto L_0x0019;
    L_0x00b7:
        r8 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x00b7 }
        throw r8;
    L_0x00ba:
        r5 = move-exception;
        r5.printStackTrace();	 Catch:{ all -> 0x00bf }
        goto L_0x00b0;
    L_0x00bf:
        r8 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x00bf }
        throw r8;
    L_0x00c2:
        monitor-exit(r9);	 Catch:{ all -> 0x00bf }
        r8 = r13.app;
        r8 = r8.input;
        r8.processEvents();
        r8 = r13.app;
        r8 = r8.listener;
        r8.render();
    L_0x00d1:
        if (r2 == 0) goto L_0x00e3;
    L_0x00d3:
        r8 = r13.app;
        r8 = r8.listener;
        r8.pause();
        r8 = com.badlogic.gdx.Gdx.app;
        r9 = "AndroidGraphics";
        r10 = "paused";
        r8.log(r9, r10);
    L_0x00e3:
        if (r1 == 0) goto L_0x00f5;
    L_0x00e5:
        r8 = r13.app;
        r8 = r8.listener;
        r8.dispose();
        r8 = com.badlogic.gdx.Gdx.app;
        r9 = "AndroidGraphics";
        r10 = "destroyed";
        r8.log(r9, r10);
    L_0x00f5:
        r8 = r13.frameStart;
        r8 = r6 - r8;
        r10 = 1000000000; // 0x3b9aca00 float:0.0047237873 double:4.94065646E-315;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 <= 0) goto L_0x0108;
    L_0x0100:
        r8 = r13.frames;
        r13.fps = r8;
        r13.frames = r12;
        r13.frameStart = r6;
    L_0x0108:
        r8 = r13.frames;
        r8 = r8 + 1;
        r13.frames = r8;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper.onDrawFrame(javax.microedition.khronos.opengles.GL10):void");
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
        if (AndroidLiveWallpaperService.DEBUG) {
            Gdx.app.debug("AndroidGraphics", Mesh.getManagedStatus());
            Gdx.app.debug("AndroidGraphics", Texture.getManagedStatus());
            Gdx.app.debug("AndroidGraphics", ShaderProgram.getManagedStatus());
            Gdx.app.debug("AndroidGraphics", FrameBuffer.getManagedStatus());
        }
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
            } else if (this.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.view).setRenderMode(renderMode);
            } else if (this.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.view).setRenderMode(renderMode);
            } else {
                throw new RuntimeException("unimplemented");
            }
            this.mean.clear();
        }
    }

    public boolean isContinuousRendering() {
        return this.isContinuous;
    }

    public void requestRendering() {
        if (this.view == null) {
            return;
        }
        if (this.view instanceof GLSurfaceViewCupcake) {
            ((GLSurfaceViewCupcake) this.view).requestRender();
        } else if (this.view instanceof GLSurfaceViewAPI18) {
            ((GLSurfaceViewAPI18) this.view).requestRender();
        } else if (this.view instanceof GLSurfaceView) {
            ((GLSurfaceView) this.view).requestRender();
        } else {
            throw new RuntimeException("unimplemented");
        }
    }

    public boolean isFullscreen() {
        return true;
    }
}
