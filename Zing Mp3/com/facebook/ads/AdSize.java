package com.facebook.ads;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import java.io.Serializable;

public class AdSize implements Serializable {
    @Deprecated
    public static final AdSize BANNER_320_50;
    public static final AdSize BANNER_HEIGHT_50;
    public static final AdSize BANNER_HEIGHT_90;
    public static final AdSize INTERSTITIAL;
    public static final AdSize RECTANGLE_HEIGHT_250;
    private final int f40a;
    private final int f41b;

    static {
        BANNER_320_50 = new AdSize(320, 50);
        INTERSTITIAL = new AdSize(0, 0);
        BANNER_HEIGHT_50 = new AdSize(-1, 50);
        BANNER_HEIGHT_90 = new AdSize(-1, 90);
        RECTANGLE_HEIGHT_250 = new AdSize(-1, Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
    }

    public AdSize(int i, int i2) {
        this.f40a = i;
        this.f41b = i2;
    }

    public static AdSize fromWidthAndHeight(int i, int i2) {
        return (INTERSTITIAL.f41b == i2 && INTERSTITIAL.f40a == i) ? INTERSTITIAL : (BANNER_320_50.f41b == i2 && BANNER_320_50.f40a == i) ? BANNER_320_50 : (BANNER_HEIGHT_50.f41b == i2 && BANNER_HEIGHT_50.f40a == i) ? BANNER_HEIGHT_50 : (BANNER_HEIGHT_90.f41b == i2 && BANNER_HEIGHT_90.f40a == i) ? BANNER_HEIGHT_90 : (RECTANGLE_HEIGHT_250.f41b == i2 && RECTANGLE_HEIGHT_250.f40a == i) ? RECTANGLE_HEIGHT_250 : null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdSize adSize = (AdSize) obj;
        return this.f40a != adSize.f40a ? false : this.f41b == adSize.f41b;
    }

    public int getHeight() {
        return this.f41b;
    }

    public int getWidth() {
        return this.f40a;
    }

    public int hashCode() {
        return (this.f40a * 31) + this.f41b;
    }
}
