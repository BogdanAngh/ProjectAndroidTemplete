package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

@zzji
public class zzeh extends Image {
    private final Drawable mDrawable;
    private final Uri mUri;
    private final double zzbmx;
    private final zzeg zzbox;

    public zzeh(zzeg com_google_android_gms_internal_zzeg) {
        Drawable drawable;
        double d;
        Uri uri = null;
        this.zzbox = com_google_android_gms_internal_zzeg;
        try {
            zzd zzmn = this.zzbox.zzmn();
            if (zzmn != null) {
                drawable = (Drawable) zze.zzae(zzmn);
                this.mDrawable = drawable;
                uri = this.zzbox.getUri();
                this.mUri = uri;
                d = 1.0d;
                d = this.zzbox.getScale();
                this.zzbmx = d;
            }
        } catch (Throwable e) {
            zzb.zzb("Failed to get drawable.", e);
        }
        Object obj = uri;
        this.mDrawable = drawable;
        try {
            uri = this.zzbox.getUri();
        } catch (Throwable e2) {
            zzb.zzb("Failed to get uri.", e2);
        }
        this.mUri = uri;
        d = 1.0d;
        try {
            d = this.zzbox.getScale();
        } catch (Throwable e3) {
            zzb.zzb("Failed to get scale.", e3);
        }
        this.zzbmx = d;
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    public double getScale() {
        return this.zzbmx;
    }

    public Uri getUri() {
        return this.mUri;
    }
}
