package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.zza;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;

@zzji
public class AdSizeParcel extends AbstractSafeParcelable {
    public static final Creator<AdSizeParcel> CREATOR;
    public final int height;
    public final int heightPixels;
    public final int versionCode;
    public final int width;
    public final int widthPixels;
    public final String zzazq;
    public final boolean zzazr;
    public final AdSizeParcel[] zzazs;
    public final boolean zzazt;
    public final boolean zzazu;
    public boolean zzazv;

    static {
        CREATOR = new zzi();
    }

    public AdSizeParcel() {
        this(5, "interstitial_mb", 0, 0, true, 0, 0, null, false, false, false);
    }

    AdSizeParcel(int i, String str, int i2, int i3, boolean z, int i4, int i5, AdSizeParcel[] adSizeParcelArr, boolean z2, boolean z3, boolean z4) {
        this.versionCode = i;
        this.zzazq = str;
        this.height = i2;
        this.heightPixels = i3;
        this.zzazr = z;
        this.width = i4;
        this.widthPixels = i5;
        this.zzazs = adSizeParcelArr;
        this.zzazt = z2;
        this.zzazu = z3;
        this.zzazv = z4;
    }

    public AdSizeParcel(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    public AdSizeParcel(Context context, AdSize[] adSizeArr) {
        int i;
        int i2;
        AdSize adSize = adSizeArr[0];
        this.versionCode = 5;
        this.zzazr = false;
        this.zzazu = adSize.isFluid();
        if (this.zzazu) {
            this.width = AdSize.BANNER.getWidth();
            this.height = AdSize.BANNER.getHeight();
        } else {
            this.width = adSize.getWidth();
            this.height = adSize.getHeight();
        }
        boolean z = this.width == -1;
        boolean z2 = this.height == -2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (z) {
            if (zzm.zzkr().zzaq(context) && zzm.zzkr().zzar(context)) {
                this.widthPixels = zza(displayMetrics) - zzm.zzkr().zzas(context);
            } else {
                this.widthPixels = zza(displayMetrics);
            }
            double d = (double) (((float) this.widthPixels) / displayMetrics.density);
            i = (int) d;
            if (d - ((double) ((int) d)) >= 0.01d) {
                i++;
            }
            i2 = i;
        } else {
            i = this.width;
            this.widthPixels = zzm.zzkr().zza(displayMetrics, this.width);
            i2 = i;
        }
        i = z2 ? zzc(displayMetrics) : this.height;
        this.heightPixels = zzm.zzkr().zza(displayMetrics, i);
        if (z || z2) {
            this.zzazq = i2 + "x" + i + "_as";
        } else if (this.zzazu) {
            this.zzazq = "320x50_mb";
        } else {
            this.zzazq = adSize.toString();
        }
        if (adSizeArr.length > 1) {
            this.zzazs = new AdSizeParcel[adSizeArr.length];
            for (int i3 = 0; i3 < adSizeArr.length; i3++) {
                this.zzazs[i3] = new AdSizeParcel(context, adSizeArr[i3]);
            }
        } else {
            this.zzazs = null;
        }
        this.zzazt = false;
        this.zzazv = false;
    }

    public AdSizeParcel(AdSizeParcel adSizeParcel, AdSizeParcel[] adSizeParcelArr) {
        this(5, adSizeParcel.zzazq, adSizeParcel.height, adSizeParcel.heightPixels, adSizeParcel.zzazr, adSizeParcel.width, adSizeParcel.widthPixels, adSizeParcelArr, adSizeParcel.zzazt, adSizeParcel.zzazu, adSizeParcel.zzazv);
    }

    public static int zza(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int zzb(DisplayMetrics displayMetrics) {
        return (int) (((float) zzc(displayMetrics)) * displayMetrics.density);
    }

    private static int zzc(DisplayMetrics displayMetrics) {
        int i = (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
        return i <= 400 ? 32 : i <= 720 ? 50 : 90;
    }

    public static AdSizeParcel zzj(Context context) {
        return new AdSizeParcel(5, "320x50_mb", 0, 0, false, 0, 0, null, true, false, false);
    }

    public static AdSizeParcel zzkc() {
        return new AdSizeParcel(5, "reward_mb", 0, 0, true, 0, 0, null, false, false, false);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }

    public AdSize zzkd() {
        return zza.zza(this.width, this.height, this.zzazq);
    }

    public void zzl(boolean z) {
        this.zzazv = z;
    }
}
