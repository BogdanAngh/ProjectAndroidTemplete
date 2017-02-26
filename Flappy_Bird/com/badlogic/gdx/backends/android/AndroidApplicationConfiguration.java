package com.badlogic.gdx.backends.android;

import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;

public class AndroidApplicationConfiguration {
    public int f31a;
    public int f32b;
    public int depth;
    public int f33g;
    public boolean getTouchEventsForLiveWallpaper;
    public boolean hideStatusBar;
    public int maxSimultaneousSounds;
    public int numSamples;
    public int f34r;
    public ResolutionStrategy resolutionStrategy;
    public int stencil;
    public int touchSleepTime;
    public boolean useAccelerometer;
    public boolean useCompass;
    public boolean useGL20;
    public boolean useGLSurfaceViewAPI18;
    public boolean useWakelock;

    public AndroidApplicationConfiguration() {
        this.useGL20 = false;
        this.f34r = 5;
        this.f33g = 6;
        this.f32b = 5;
        this.f31a = 0;
        this.depth = 16;
        this.stencil = 0;
        this.numSamples = 0;
        this.useAccelerometer = true;
        this.useCompass = true;
        this.touchSleepTime = 0;
        this.useWakelock = false;
        this.hideStatusBar = false;
        this.maxSimultaneousSounds = 16;
        this.resolutionStrategy = new FillResolutionStrategy();
        this.getTouchEventsForLiveWallpaper = false;
        this.useGLSurfaceViewAPI18 = false;
    }
}
