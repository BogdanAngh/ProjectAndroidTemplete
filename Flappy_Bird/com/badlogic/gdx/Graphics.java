package com.badlogic.gdx;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;

public interface Graphics {

    public static class BufferFormat {
        public final int f1a;
        public final int f2b;
        public final boolean coverageSampling;
        public final int depth;
        public final int f3g;
        public final int f4r;
        public final int samples;
        public final int stencil;

        public BufferFormat(int r, int g, int b, int a, int depth, int stencil, int samples, boolean coverageSampling) {
            this.f4r = r;
            this.f3g = g;
            this.f2b = b;
            this.f1a = a;
            this.depth = depth;
            this.stencil = stencil;
            this.samples = samples;
            this.coverageSampling = coverageSampling;
        }

        public String toString() {
            return "r: " + this.f4r + ", g: " + this.f3g + ", b: " + this.f2b + ", a: " + this.f1a + ", depth: " + this.depth + ", stencil: " + this.stencil + ", num samples: " + this.samples + ", coverage sampling: " + this.coverageSampling;
        }
    }

    public static class DisplayMode {
        public final int bitsPerPixel;
        public final int height;
        public final int refreshRate;
        public final int width;

        protected DisplayMode(int width, int height, int refreshRate, int bitsPerPixel) {
            this.width = width;
            this.height = height;
            this.refreshRate = refreshRate;
            this.bitsPerPixel = bitsPerPixel;
        }

        public String toString() {
            return this.width + "x" + this.height + ", bpp: " + this.bitsPerPixel + ", hz: " + this.refreshRate;
        }
    }

    public enum GraphicsType {
        AndroidGL,
        LWJGL,
        Angle,
        WebGL,
        iOSGL,
        JGLFW
    }

    BufferFormat getBufferFormat();

    float getDeltaTime();

    float getDensity();

    DisplayMode getDesktopDisplayMode();

    DisplayMode[] getDisplayModes();

    int getFramesPerSecond();

    GL10 getGL10();

    GL11 getGL11();

    GL20 getGL20();

    GLCommon getGLCommon();

    int getHeight();

    float getPpcX();

    float getPpcY();

    float getPpiX();

    float getPpiY();

    float getRawDeltaTime();

    GraphicsType getType();

    int getWidth();

    boolean isContinuousRendering();

    boolean isFullscreen();

    boolean isGL11Available();

    boolean isGL20Available();

    void requestRendering();

    void setContinuousRendering(boolean z);

    boolean setDisplayMode(int i, int i2, boolean z);

    boolean setDisplayMode(DisplayMode displayMode);

    void setTitle(String str);

    void setVSync(boolean z);

    boolean supportsDisplayModeChange();

    boolean supportsExtension(String str);
}
