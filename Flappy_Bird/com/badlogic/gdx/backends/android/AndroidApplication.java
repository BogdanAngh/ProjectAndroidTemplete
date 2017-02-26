package com.badlogic.gdx.backends.android;

import android.app.Activity;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewCupcake;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.android.gms.location.places.Place;
import java.lang.reflect.Method;

public class AndroidApplication extends Activity implements Application {
    protected AndroidAudio audio;
    AndroidClipboard clipboard;
    protected final Array<Runnable> executedRunnables;
    protected AndroidFiles files;
    protected boolean firstResume;
    protected AndroidGraphics graphics;
    public Handler handler;
    protected AndroidInput input;
    protected final Array<LifecycleListener> lifecycleListeners;
    protected ApplicationListener listener;
    protected int logLevel;
    protected AndroidNet net;
    protected final Array<Runnable> runnables;
    protected WakeLock wakeLock;

    /* renamed from: com.badlogic.gdx.backends.android.AndroidApplication.1 */
    class C00251 implements Runnable {
        C00251() {
        }

        public void run() {
            AndroidApplication.this.finish();
        }
    }

    public AndroidApplication() {
        this.firstResume = true;
        this.runnables = new Array();
        this.executedRunnables = new Array();
        this.lifecycleListeners = new Array();
        this.wakeLock = null;
        this.logLevel = 2;
    }

    static {
        GdxNativesLoader.load();
    }

    public void initialize(ApplicationListener listener, boolean useGL2IfAvailable) {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = useGL2IfAvailable;
        initialize(listener, config);
    }

    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        this.graphics = new AndroidGraphics(this, config, config.resolutionStrategy == null ? new FillResolutionStrategy() : config.resolutionStrategy);
        this.input = AndroidInputFactory.newAndroidInput(this, this, this.graphics.view, config);
        this.audio = new AndroidAudio(this, config);
        this.files = new AndroidFiles(getAssets(), getFilesDir().getAbsolutePath());
        this.net = new AndroidNet(this);
        this.listener = listener;
        this.handler = new Handler();
        Gdx.app = this;
        Gdx.input = getInput();
        Gdx.audio = getAudio();
        Gdx.files = getFiles();
        Gdx.graphics = getGraphics();
        Gdx.net = getNet();
        try {
            requestWindowFeature(1);
        } catch (Exception ex) {
            log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
        }
        getWindow().setFlags(Place.TYPE_SUBLOCALITY_LEVEL_2, Place.TYPE_SUBLOCALITY_LEVEL_2);
        getWindow().clearFlags(GL10.GL_EXP);
        setContentView(this.graphics.getView(), createLayoutParams());
        createWakeLock(config);
        hideStatusBar(config);
    }

    protected LayoutParams createLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        return layoutParams;
    }

    protected void createWakeLock(AndroidApplicationConfiguration config) {
        if (config.useWakelock) {
            this.wakeLock = ((PowerManager) getSystemService("power")).newWakeLock(26, "libgdx wakelock");
        }
    }

    protected void hideStatusBar(AndroidApplicationConfiguration config) {
        if (config.hideStatusBar && getVersion() >= 11) {
            View rootView = getWindow().getDecorView();
            try {
                Method m = View.class.getMethod("setSystemUiVisibility", new Class[]{Integer.TYPE});
                m.invoke(rootView, new Object[]{Integer.valueOf(0)});
                m.invoke(rootView, new Object[]{Integer.valueOf(1)});
            } catch (Exception e) {
                log("AndroidApplication", "Can't hide status bar", e);
            }
        }
    }

    public View initializeForView(ApplicationListener listener, boolean useGL2IfAvailable) {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = useGL2IfAvailable;
        return initializeForView(listener, config);
    }

    public View initializeForView(ApplicationListener listener, AndroidApplicationConfiguration config) {
        this.graphics = new AndroidGraphics(this, config, config.resolutionStrategy == null ? new FillResolutionStrategy() : config.resolutionStrategy);
        this.input = AndroidInputFactory.newAndroidInput(this, this, this.graphics.view, config);
        this.audio = new AndroidAudio(this, config);
        this.files = new AndroidFiles(getAssets(), getFilesDir().getAbsolutePath());
        this.net = new AndroidNet(this);
        this.listener = listener;
        this.handler = new Handler();
        Gdx.app = this;
        Gdx.input = getInput();
        Gdx.audio = getAudio();
        Gdx.files = getFiles();
        Gdx.graphics = getGraphics();
        Gdx.net = getNet();
        createWakeLock(config);
        hideStatusBar(config);
        return this.graphics.getView();
    }

    protected void onPause() {
        int i;
        if (this.wakeLock != null) {
            this.wakeLock.release();
        }
        boolean isContinuous = this.graphics.isContinuousRendering();
        this.graphics.setContinuousRendering(true);
        this.graphics.pause();
        this.input.unregisterSensorListeners();
        int[] realId = this.input.realId;
        for (i = 0; i < realId.length; i++) {
            realId[i] = -1;
        }
        boolean[] touched = this.input.touched;
        for (i = 0; i < touched.length; i++) {
            touched[i] = false;
        }
        if (isFinishing()) {
            this.graphics.clearManagedCaches();
            this.graphics.destroy();
        }
        this.graphics.setContinuousRendering(isContinuous);
        if (!(this.graphics == null || this.graphics.view == null)) {
            if (this.graphics.view instanceof GLSurfaceViewCupcake) {
                ((GLSurfaceViewCupcake) this.graphics.view).onPause();
            }
            if (this.graphics.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.graphics.view).onPause();
            }
            if (this.graphics.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.graphics.view).onPause();
            }
        }
        super.onPause();
    }

    protected void onResume() {
        if (this.wakeLock != null) {
            this.wakeLock.acquire();
        }
        Gdx.app = this;
        Gdx.input = getInput();
        Gdx.audio = getAudio();
        Gdx.files = getFiles();
        Gdx.graphics = getGraphics();
        Gdx.net = getNet();
        ((AndroidInput) getInput()).registerSensorListeners();
        if (!(this.graphics == null || this.graphics.view == null)) {
            if (this.graphics.view instanceof GLSurfaceViewCupcake) {
                ((GLSurfaceViewCupcake) this.graphics.view).onResume();
            }
            if (this.graphics.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.graphics.view).onResume();
            }
            if (this.graphics.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.graphics.view).onResume();
            }
        }
        if (this.firstResume) {
            this.firstResume = false;
        } else {
            this.graphics.resume();
        }
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public ApplicationListener getApplicationListener() {
        return this.listener;
    }

    public Audio getAudio() {
        return this.audio;
    }

    public Files getFiles() {
        return this.files;
    }

    public Graphics getGraphics() {
        return this.graphics;
    }

    public Input getInput() {
        return this.input;
    }

    public Net getNet() {
        return this.net;
    }

    public ApplicationType getType() {
        return ApplicationType.Android;
    }

    public int getVersion() {
        return Integer.parseInt(VERSION.SDK);
    }

    public long getJavaHeap() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public long getNativeHeap() {
        return Debug.getNativeHeapAllocatedSize();
    }

    public Preferences getPreferences(String name) {
        return new AndroidPreferences(getSharedPreferences(name, 0));
    }

    public Clipboard getClipboard() {
        if (this.clipboard == null) {
            this.clipboard = new AndroidClipboard(this);
        }
        return this.clipboard;
    }

    public void postRunnable(Runnable runnable) {
        synchronized (this.runnables) {
            this.runnables.add(runnable);
            Gdx.graphics.requestRendering();
        }
    }

    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        boolean keyboardAvailable = false;
        if (config.hardKeyboardHidden == 1) {
            keyboardAvailable = true;
        }
        this.input.keyboardAvailable = keyboardAvailable;
    }

    public void exit() {
        this.handler.post(new C00251());
    }

    public void debug(String tag, String message) {
        if (this.logLevel >= 3) {
            Log.d(tag, message);
        }
    }

    public void debug(String tag, String message, Throwable exception) {
        if (this.logLevel >= 3) {
            Log.d(tag, message, exception);
        }
    }

    public void log(String tag, String message) {
        if (this.logLevel >= 2) {
            Log.i(tag, message);
        }
    }

    public void log(String tag, String message, Throwable exception) {
        if (this.logLevel >= 2) {
            Log.i(tag, message, exception);
        }
    }

    public void error(String tag, String message) {
        if (this.logLevel >= 1) {
            Log.e(tag, message);
        }
    }

    public void error(String tag, String message, Throwable exception) {
        if (this.logLevel >= 1) {
            Log.e(tag, message, exception);
        }
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void addLifecycleListener(LifecycleListener listener) {
        synchronized (this.lifecycleListeners) {
            this.lifecycleListeners.add(listener);
        }
    }

    public void removeLifecycleListener(LifecycleListener listener) {
        synchronized (this.lifecycleListeners) {
            this.lifecycleListeners.removeValue(listener, true);
        }
    }
}
