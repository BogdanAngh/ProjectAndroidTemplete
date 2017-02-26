package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzeg.zza;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzel extends NativeAppInstallAd {
    private VideoController zzbbc;
    private final zzek zzboy;
    private final List<Image> zzboz;
    private final zzeh zzbpa;

    public zzel(zzek com_google_android_gms_internal_zzek) {
        zzeh com_google_android_gms_internal_zzeh;
        this.zzboz = new ArrayList();
        this.zzbbc = new VideoController();
        this.zzboy = com_google_android_gms_internal_zzek;
        try {
            List<Object> images = this.zzboy.getImages();
            if (images != null) {
                for (Object zze : images) {
                    zzeg zze2 = zze(zze);
                    if (zze2 != null) {
                        this.zzboz.add(new zzeh(zze2));
                    }
                }
            }
        } catch (Throwable e) {
            zzb.zzb("Failed to get image.", e);
        }
        try {
            zzeg zzmo = this.zzboy.zzmo();
            if (zzmo != null) {
                com_google_android_gms_internal_zzeh = new zzeh(zzmo);
                this.zzbpa = com_google_android_gms_internal_zzeh;
            }
        } catch (Throwable e2) {
            zzb.zzb("Failed to get icon.", e2);
        }
        com_google_android_gms_internal_zzeh = null;
        this.zzbpa = com_google_android_gms_internal_zzeh;
    }

    public void destroy() {
        try {
            this.zzboy.destroy();
        } catch (Throwable e) {
            zzb.zzb("Failed to destroy", e);
        }
    }

    public CharSequence getBody() {
        try {
            return this.zzboy.getBody();
        } catch (Throwable e) {
            zzb.zzb("Failed to get body.", e);
            return null;
        }
    }

    public CharSequence getCallToAction() {
        try {
            return this.zzboy.getCallToAction();
        } catch (Throwable e) {
            zzb.zzb("Failed to get call to action.", e);
            return null;
        }
    }

    public Bundle getExtras() {
        try {
            return this.zzboy.getExtras();
        } catch (Throwable e) {
            zzb.zzb("Failed to get extras", e);
            return null;
        }
    }

    public CharSequence getHeadline() {
        try {
            return this.zzboy.getHeadline();
        } catch (Throwable e) {
            zzb.zzb("Failed to get headline.", e);
            return null;
        }
    }

    public Image getIcon() {
        return this.zzbpa;
    }

    public List<Image> getImages() {
        return this.zzboz;
    }

    public CharSequence getPrice() {
        try {
            return this.zzboy.getPrice();
        } catch (Throwable e) {
            zzb.zzb("Failed to get price.", e);
            return null;
        }
    }

    public Double getStarRating() {
        Double d = null;
        try {
            double starRating = this.zzboy.getStarRating();
            if (starRating != -1.0d) {
                d = Double.valueOf(starRating);
            }
        } catch (Throwable e) {
            zzb.zzb("Failed to get star rating.", e);
        }
        return d;
    }

    public CharSequence getStore() {
        try {
            return this.zzboy.getStore();
        } catch (Throwable e) {
            zzb.zzb("Failed to get store", e);
            return null;
        }
    }

    public VideoController getVideoController() {
        try {
            if (this.zzboy.zzej() != null) {
                this.zzbbc.zza(this.zzboy.zzej());
            }
        } catch (Throwable e) {
            zzb.zzb("Exception occurred while getting video controller", e);
        }
        return this.zzbbc;
    }

    protected /* synthetic */ Object zzdy() {
        return zzmp();
    }

    zzeg zze(Object obj) {
        return obj instanceof IBinder ? zza.zzab((IBinder) obj) : null;
    }

    protected zzd zzmp() {
        try {
            return this.zzboy.zzmp();
        } catch (Throwable e) {
            zzb.zzb("Failed to retrieve native ad engine.", e);
            return null;
        }
    }
}
