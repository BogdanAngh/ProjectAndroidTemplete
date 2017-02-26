package com.badlogic.gdx.graphics;

import android.support.v4.view.MotionEventCompat;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.TextTrackStyle;

public class Color {
    public static final Color BLACK;
    public static final Color BLUE;
    public static final Color CLEAR;
    public static final Color CYAN;
    public static final Color DARK_GRAY;
    public static final Color GRAY;
    public static final Color GREEN;
    public static final Color LIGHT_GRAY;
    public static final Color MAGENTA;
    public static final Color ORANGE;
    public static final Color PINK;
    public static final Color RED;
    public static final Color WHITE;
    public static final Color YELLOW;
    @Deprecated
    public static Color tmp;
    public float f37a;
    public float f38b;
    public float f39g;
    public float f40r;

    static {
        CLEAR = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        WHITE = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        BLACK = new Color(0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        RED = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        GREEN = new Color(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        BLUE = new Color(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f, TextTrackStyle.DEFAULT_FONT_SCALE);
        GRAY = new Color(0.5f, 0.5f, 0.5f, TextTrackStyle.DEFAULT_FONT_SCALE);
        DARK_GRAY = new Color(0.25f, 0.25f, 0.25f, TextTrackStyle.DEFAULT_FONT_SCALE);
        PINK = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, 0.68f, 0.68f, TextTrackStyle.DEFAULT_FONT_SCALE);
        ORANGE = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, 0.78f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        YELLOW = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        MAGENTA = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        CYAN = new Color(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        tmp = new Color();
    }

    public Color(int rgba8888) {
        rgba8888ToColor(this, rgba8888);
    }

    public Color(float r, float g, float b, float a) {
        this.f40r = r;
        this.f39g = g;
        this.f38b = b;
        this.f37a = a;
        clamp();
    }

    public Color(Color color) {
        set(color);
    }

    public Color set(Color color) {
        this.f40r = color.f40r;
        this.f39g = color.f39g;
        this.f38b = color.f38b;
        this.f37a = color.f37a;
        return this;
    }

    public Color mul(Color color) {
        this.f40r *= color.f40r;
        this.f39g *= color.f39g;
        this.f38b *= color.f38b;
        this.f37a *= color.f37a;
        return clamp();
    }

    public Color mul(float value) {
        this.f40r *= value;
        this.f39g *= value;
        this.f38b *= value;
        this.f37a *= value;
        return clamp();
    }

    public Color add(Color color) {
        this.f40r += color.f40r;
        this.f39g += color.f39g;
        this.f38b += color.f38b;
        this.f37a += color.f37a;
        return clamp();
    }

    public Color sub(Color color) {
        this.f40r -= color.f40r;
        this.f39g -= color.f39g;
        this.f38b -= color.f38b;
        this.f37a -= color.f37a;
        return clamp();
    }

    public Color clamp() {
        if (this.f40r < 0.0f) {
            this.f40r = 0.0f;
        } else if (this.f40r > TextTrackStyle.DEFAULT_FONT_SCALE) {
            this.f40r = TextTrackStyle.DEFAULT_FONT_SCALE;
        }
        if (this.f39g < 0.0f) {
            this.f39g = 0.0f;
        } else if (this.f39g > TextTrackStyle.DEFAULT_FONT_SCALE) {
            this.f39g = TextTrackStyle.DEFAULT_FONT_SCALE;
        }
        if (this.f38b < 0.0f) {
            this.f38b = 0.0f;
        } else if (this.f38b > TextTrackStyle.DEFAULT_FONT_SCALE) {
            this.f38b = TextTrackStyle.DEFAULT_FONT_SCALE;
        }
        if (this.f37a < 0.0f) {
            this.f37a = 0.0f;
        } else if (this.f37a > TextTrackStyle.DEFAULT_FONT_SCALE) {
            this.f37a = TextTrackStyle.DEFAULT_FONT_SCALE;
        }
        return this;
    }

    public Color set(float r, float g, float b, float a) {
        this.f40r = r;
        this.f39g = g;
        this.f38b = b;
        this.f37a = a;
        return clamp();
    }

    public Color set(int rgba) {
        rgba8888ToColor(this, rgba);
        return this;
    }

    public Color add(float r, float g, float b, float a) {
        this.f40r += r;
        this.f39g += g;
        this.f38b += b;
        this.f37a += a;
        return clamp();
    }

    public Color sub(float r, float g, float b, float a) {
        this.f40r -= r;
        this.f39g -= g;
        this.f38b -= b;
        this.f37a -= a;
        return clamp();
    }

    public Color mul(float r, float g, float b, float a) {
        this.f40r *= r;
        this.f39g *= g;
        this.f38b *= b;
        this.f37a *= a;
        return clamp();
    }

    public Color lerp(Color target, float t) {
        this.f40r += (target.f40r - this.f40r) * t;
        this.f39g += (target.f39g - this.f39g) * t;
        this.f38b += (target.f38b - this.f38b) * t;
        this.f37a += (target.f37a - this.f37a) * t;
        return clamp();
    }

    public Color lerp(float r, float g, float b, float a, float t) {
        this.f40r += (r - this.f40r) * t;
        this.f39g += (g - this.f39g) * t;
        this.f38b += (b - this.f38b) * t;
        this.f37a += (a - this.f37a) * t;
        return clamp();
    }

    public Color premultiplyAlpha() {
        this.f40r *= this.f37a;
        this.f39g *= this.f37a;
        this.f38b *= this.f37a;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (toIntBits() != ((Color) o).toIntBits()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int floatToIntBits;
        int i = 0;
        if (this.f40r != 0.0f) {
            result = NumberUtils.floatToIntBits(this.f40r);
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.f39g != 0.0f) {
            floatToIntBits = NumberUtils.floatToIntBits(this.f39g);
        } else {
            floatToIntBits = 0;
        }
        i2 = (i2 + floatToIntBits) * 31;
        if (this.f38b != 0.0f) {
            floatToIntBits = NumberUtils.floatToIntBits(this.f38b);
        } else {
            floatToIntBits = 0;
        }
        floatToIntBits = (i2 + floatToIntBits) * 31;
        if (this.f37a != 0.0f) {
            i = NumberUtils.floatToIntBits(this.f37a);
        }
        return floatToIntBits + i;
    }

    public float toFloatBits() {
        return NumberUtils.intToFloatColor((((((int) (this.f37a * 255.0f)) << 24) | (((int) (this.f38b * 255.0f)) << 16)) | (((int) (this.f39g * 255.0f)) << 8)) | ((int) (this.f40r * 255.0f)));
    }

    public int toIntBits() {
        return (((((int) (this.f37a * 255.0f)) << 24) | (((int) (this.f38b * 255.0f)) << 16)) | (((int) (this.f39g * 255.0f)) << 8)) | ((int) (this.f40r * 255.0f));
    }

    public String toString() {
        String value = Integer.toHexString((((((int) (this.f40r * 255.0f)) << 24) | (((int) (this.f39g * 255.0f)) << 16)) | (((int) (this.f38b * 255.0f)) << 8)) | ((int) (this.f37a * 255.0f)));
        while (value.length() < 8) {
            value = "0" + value;
        }
        return value;
    }

    public static Color valueOf(String hex) {
        return new Color(((float) Integer.valueOf(hex.substring(0, 2), 16).intValue()) / 255.0f, ((float) Integer.valueOf(hex.substring(2, 4), 16).intValue()) / 255.0f, ((float) Integer.valueOf(hex.substring(4, 6), 16).intValue()) / 255.0f, ((float) (hex.length() != 8 ? Keys.F12 : Integer.valueOf(hex.substring(6, 8), 16).intValue())) / 255.0f);
    }

    public static float toFloatBits(int r, int g, int b, int a) {
        return NumberUtils.intToFloatColor((((a << 24) | (b << 16)) | (g << 8)) | r);
    }

    public static float toFloatBits(float r, float g, float b, float a) {
        return NumberUtils.intToFloatColor((((((int) (255.0f * a)) << 24) | (((int) (255.0f * b)) << 16)) | (((int) (255.0f * g)) << 8)) | ((int) (255.0f * r)));
    }

    public static int toIntBits(int r, int g, int b, int a) {
        return (((a << 24) | (b << 16)) | (g << 8)) | r;
    }

    public static int alpha(float alpha) {
        return (int) (255.0f * alpha);
    }

    public static int luminanceAlpha(float luminance, float alpha) {
        return (((int) (luminance * 255.0f)) << 8) | ((int) (255.0f * alpha));
    }

    public static int rgb565(float r, float g, float b) {
        return ((((int) (r * 31.0f)) << 11) | (((int) (63.0f * g)) << 5)) | ((int) (b * 31.0f));
    }

    public static int rgba4444(float r, float g, float b, float a) {
        return (((((int) (r * 15.0f)) << 12) | (((int) (g * 15.0f)) << 8)) | (((int) (b * 15.0f)) << 4)) | ((int) (a * 15.0f));
    }

    public static int rgb888(float r, float g, float b) {
        return ((((int) (r * 255.0f)) << 16) | (((int) (g * 255.0f)) << 8)) | ((int) (b * 255.0f));
    }

    public static int rgba8888(float r, float g, float b, float a) {
        return (((((int) (r * 255.0f)) << 24) | (((int) (g * 255.0f)) << 16)) | (((int) (b * 255.0f)) << 8)) | ((int) (a * 255.0f));
    }

    public static int rgb565(Color color) {
        return ((((int) (color.f40r * 31.0f)) << 11) | (((int) (color.f39g * 63.0f)) << 5)) | ((int) (color.f38b * 31.0f));
    }

    public static int rgba4444(Color color) {
        return (((((int) (color.f40r * 15.0f)) << 12) | (((int) (color.f39g * 15.0f)) << 8)) | (((int) (color.f38b * 15.0f)) << 4)) | ((int) (color.f37a * 15.0f));
    }

    public static int rgb888(Color color) {
        return ((((int) (color.f40r * 255.0f)) << 16) | (((int) (color.f39g * 255.0f)) << 8)) | ((int) (color.f38b * 255.0f));
    }

    public static int rgba8888(Color color) {
        return (((((int) (color.f40r * 255.0f)) << 24) | (((int) (color.f39g * 255.0f)) << 16)) | (((int) (color.f38b * 255.0f)) << 8)) | ((int) (color.f37a * 255.0f));
    }

    public static void rgb565ToColor(Color color, int value) {
        color.f40r = ((float) ((63488 & value) >>> 11)) / 31.0f;
        color.f39g = ((float) ((value & 2016) >>> 5)) / 63.0f;
        color.f38b = ((float) ((value & 31) >>> 0)) / 31.0f;
    }

    public static void rgba4444ToColor(Color color, int value) {
        color.f40r = ((float) ((61440 & value) >>> 12)) / 15.0f;
        color.f39g = ((float) ((value & 3840) >>> 8)) / 15.0f;
        color.f38b = ((float) ((value & TweenCallback.ANY_BACKWARD) >>> 4)) / 15.0f;
        color.f37a = ((float) (value & 15)) / 15.0f;
    }

    public static void rgb888ToColor(Color color, int value) {
        color.f40r = ((float) ((16711680 & value) >>> 16)) / 255.0f;
        color.f39g = ((float) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & value) >>> 8)) / 255.0f;
        color.f38b = ((float) (value & Keys.F12)) / 255.0f;
    }

    public static void rgba8888ToColor(Color color, int value) {
        color.f40r = ((float) ((-16777216 & value) >>> 24)) / 255.0f;
        color.f39g = ((float) ((16711680 & value) >>> 16)) / 255.0f;
        color.f38b = ((float) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & value) >>> 8)) / 255.0f;
        color.f37a = ((float) (value & Keys.F12)) / 255.0f;
    }

    public Color tmp() {
        return tmp.set(this);
    }

    public Color cpy() {
        return new Color(this);
    }
}
