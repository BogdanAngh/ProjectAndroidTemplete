package com.badlogic.gdx.backends.android;

import android.opengl.GLSurfaceView;
import android.os.Build.VERSION;
import android.os.Debug;
import android.util.Log;
import android.view.WindowManager;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.GdxNativesLoader;
import java.lang.reflect.Method;

public class AndroidLiveWallpaper implements Application {
    protected AndroidAudio audio;
    AndroidClipboard clipboard;
    protected final Array<Runnable> executedRunnables;
    protected AndroidFiles files;
    protected boolean firstResume;
    protected AndroidGraphicsLiveWallpaper graphics;
    protected AndroidInput input;
    protected final Array<LifecycleListener> lifecycleListeners;
    protected ApplicationListener listener;
    protected int logLevel;
    protected AndroidNet net;
    protected final Array<Runnable> runnables;
    protected AndroidLiveWallpaperService service;

    static {
        GdxNativesLoader.load();
    }

    public AndroidLiveWallpaper(AndroidLiveWallpaperService service) {
        this.firstResume = true;
        this.runnables = new Array();
        this.executedRunnables = new Array();
        this.lifecycleListeners = new Array();
        this.logLevel = 2;
        this.service = service;
    }

    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        this.graphics = new AndroidGraphicsLiveWallpaper(this, config, config.resolutionStrategy == null ? new FillResolutionStrategy() : config.resolutionStrategy);
        this.input = AndroidInputFactory.newAndroidInput(this, getService(), this.graphics.view, config);
        this.audio = new AndroidAudio(getService(), config);
        this.files = new AndroidFiles(getService().getAssets(), getService().getFilesDir().getAbsolutePath());
        this.listener = listener;
        Gdx.app = this;
        Gdx.input = this.input;
        Gdx.audio = this.audio;
        Gdx.files = this.files;
        Gdx.graphics = this.graphics;
    }

    public void onPause() {
        int i;
        if (AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaper - onPause()");
        }
        this.audio.pause();
        this.input.unregisterSensorListeners();
        int[] realId = this.input.realId;
        for (i = 0; i < realId.length; i++) {
            realId[i] = -1;
        }
        boolean[] touched = this.input.touched;
        for (i = 0; i < touched.length; i++) {
            touched[i] = false;
        }
        if (!(this.graphics == null || this.graphics.view == null)) {
            if (this.graphics.view instanceof GLSurfaceViewCupcake) {
                ((GLSurfaceViewCupcake) this.graphics.view).onPause();
            } else if (this.graphics.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.graphics.view).onPause();
            } else if (this.graphics.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.graphics.view).onPause();
            } else {
                throw new RuntimeException("unimplemented");
            }
        }
        if (AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaper - onPause() done!");
        }
    }

    public void onResume() {
        Gdx.app = this;
        Gdx.input = this.input;
        Gdx.audio = this.audio;
        Gdx.files = this.files;
        Gdx.graphics = this.graphics;
        this.input.registerSensorListeners();
        if (!(this.graphics == null || this.graphics.view == null)) {
            if (this.graphics.view instanceof GLSurfaceViewCupcake) {
                ((GLSurfaceViewCupcake) this.graphics.view).onResume();
            } else if (this.graphics.view instanceof GLSurfaceViewAPI18) {
                ((GLSurfaceViewAPI18) this.graphics.view).onResume();
            } else if (this.graphics.view instanceof GLSurfaceView) {
                ((GLSurfaceView) this.graphics.view).onResume();
            } else {
                throw new RuntimeException("unimplemented");
            }
        }
        if (this.firstResume) {
            this.firstResume = false;
            return;
        }
        this.audio.resume();
        this.graphics.resume();
    }

    public void onDestroy() {
        if (!(this.graphics == null || this.graphics.view == null || !(this.graphics.view instanceof GLSurfaceView))) {
            GLSurfaceView glSurfaceView = this.graphics.view;
            Method method = null;
            try {
                for (Method m : glSurfaceView.getClass().getMethods()) {
                    if (m.getName().equals("onDestroy")) {
                        method = m;
                        break;
                    }
                }
                if (method != null) {
                    method.invoke(glSurfaceView, new Object[0]);
                    if (AndroidLiveWallpaperService.DEBUG) {
                        Log.d("WallpaperService", " > AndroidLiveWallpaper - onDestroy() stopped GLThread managed by GLSurfaceView");
                    }
                } else {
                    throw new Exception("method not found!");
                }
            } catch (Throwable t) {
                Log.e("WallpaperService", "failed to destroy GLSurfaceView's thread! GLSurfaceView.onDetachedFromWindow impl changed since API lvl 16!");
                t.printStackTrace();
            }
        }
        if (this.audio != null) {
            this.audio.dispose();
        }
    }

    public WindowManager getWindowManager() {
        return this.service.getWindowManager();
    }

    public AndroidLiveWallpaperService getService() {
        return this.service;
    }

    public ApplicationListener getListener() {
        return this.listener;
    }

    public void postRunnable(Runnable runnable) {
        synchronized (this.runnables) {
            this.runnables.add(runnable);
        }
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
        return new AndroidPreferences(this.service.getSharedPreferences(name, 0));
    }

    public Clipboard getClipboard() {
        if (this.clipboard == null) {
            this.clipboard = new AndroidClipboard(this.service);
        }
        return this.clipboard;
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

    public void exit() {
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

    public ApplicationListener getApplicationListener() {
        return this.listener;
    }
}
