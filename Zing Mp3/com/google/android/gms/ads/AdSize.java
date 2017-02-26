package com.google.android.gms.ads;

import android.content.Context;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.google.android.exoplayer.SampleSource;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzm;

public final class AdSize {
    public static final int AUTO_HEIGHT = -2;
    public static final AdSize BANNER;
    public static final AdSize FLUID;
    public static final AdSize FULL_BANNER;
    public static final int FULL_WIDTH = -1;
    public static final AdSize LARGE_BANNER;
    public static final AdSize LEADERBOARD;
    public static final AdSize MEDIUM_RECTANGLE;
    public static final AdSize SEARCH;
    public static final AdSize SMART_BANNER;
    public static final AdSize WIDE_SKYSCRAPER;
    private final int zzakh;
    private final int zzaki;
    private final String zzakj;

    static {
        BANNER = new AdSize(320, 50, "320x50_mb");
        FULL_BANNER = new AdSize(468, 60, "468x60_as");
        LARGE_BANNER = new AdSize(320, 100, "320x100_as");
        LEADERBOARD = new AdSize(728, 90, "728x90_as");
        MEDIUM_RECTANGLE = new AdSize(300, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, "300x250_as");
        WIDE_SKYSCRAPER = new AdSize(160, 600, "160x600_as");
        SMART_BANNER = new AdSize(FULL_WIDTH, AUTO_HEIGHT, "smart_banner");
        FLUID = new AdSize(-3, -4, "fluid");
        SEARCH = new AdSize(-3, 0, "search_v2");
    }

    public AdSize(int i, int i2) {
        String valueOf = i == FULL_WIDTH ? "FULL" : String.valueOf(i);
        String valueOf2 = i2 == AUTO_HEIGHT ? "AUTO" : String.valueOf(i2);
        String valueOf3 = String.valueOf("_as");
        this(i, i2, new StringBuilder(((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()).append(valueOf).append("x").append(valueOf2).append(valueOf3).toString());
    }

    AdSize(int i, int i2, String str) {
        if (i < 0 && i != FULL_WIDTH && i != -3) {
            throw new IllegalArgumentException("Invalid width for AdSize: " + i);
        } else if (i2 >= 0 || i2 == AUTO_HEIGHT || i2 == -4) {
            this.zzakh = i;
            this.zzaki = i2;
            this.zzakj = str;
        } else {
            throw new IllegalArgumentException("Invalid height for AdSize: " + i2);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdSize)) {
            return false;
        }
        AdSize adSize = (AdSize) obj;
        return this.zzakh == adSize.zzakh && this.zzaki == adSize.zzaki && this.zzakj.equals(adSize.zzakj);
    }

    public int getHeight() {
        return this.zzaki;
    }

    public int getHeightInPixels(Context context) {
        switch (this.zzaki) {
            case SampleSource.FORMAT_READ /*-4*/:
            case SampleSource.SAMPLE_READ /*-3*/:
                return FULL_WIDTH;
            case AUTO_HEIGHT /*-2*/:
                return AdSizeParcel.zzb(context.getResources().getDisplayMetrics());
            default:
                return zzm.zzkr().zzb(context, this.zzaki);
        }
    }

    public int getWidth() {
        return this.zzakh;
    }

    public int getWidthInPixels(Context context) {
        switch (this.zzakh) {
            case SampleSource.FORMAT_READ /*-4*/:
            case SampleSource.SAMPLE_READ /*-3*/:
                return FULL_WIDTH;
            case FULL_WIDTH /*-1*/:
                return AdSizeParcel.zza(context.getResources().getDisplayMetrics());
            default:
                return zzm.zzkr().zzb(context, this.zzakh);
        }
    }

    public int hashCode() {
        return this.zzakj.hashCode();
    }

    public boolean isAutoHeight() {
        return this.zzaki == AUTO_HEIGHT;
    }

    public boolean isFluid() {
        return this.zzakh == -3 && this.zzaki == -4;
    }

    public boolean isFullWidth() {
        return this.zzakh == FULL_WIDTH;
    }

    public String toString() {
        return this.zzakj;
    }
}
