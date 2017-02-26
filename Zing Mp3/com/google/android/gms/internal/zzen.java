package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzeg.zza;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzen extends NativeContentAd {
    private final List<Image> zzboz;
    private final zzem zzbpb;
    private final zzeh zzbpc;

    public zzen(zzem com_google_android_gms_internal_zzem) {
        zzeh com_google_android_gms_internal_zzeh;
        this.zzboz = new ArrayList();
        this.zzbpb = com_google_android_gms_internal_zzem;
        try {
            List<Object> images = this.zzbpb.getImages();
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
            zzeg zzmt = this.zzbpb.zzmt();
            if (zzmt != null) {
                com_google_android_gms_internal_zzeh = new zzeh(zzmt);
                this.zzbpc = com_google_android_gms_internal_zzeh;
            }
        } catch (Throwable e2) {
            zzb.zzb("Failed to get icon.", e2);
        }
        com_google_android_gms_internal_zzeh = null;
        this.zzbpc = com_google_android_gms_internal_zzeh;
    }

    public void destroy() {
        try {
            this.zzbpb.destroy();
        } catch (Throwable e) {
            zzb.zzb("Failed to destroy", e);
        }
    }

    public CharSequence getAdvertiser() {
        try {
            return this.zzbpb.getAdvertiser();
        } catch (Throwable e) {
            zzb.zzb("Failed to get attribution.", e);
            return null;
        }
    }

    public CharSequence getBody() {
        try {
            return this.zzbpb.getBody();
        } catch (Throwable e) {
            zzb.zzb("Failed to get body.", e);
            return null;
        }
    }

    public CharSequence getCallToAction() {
        try {
            return this.zzbpb.getCallToAction();
        } catch (Throwable e) {
            zzb.zzb("Failed to get call to action.", e);
            return null;
        }
    }

    public Bundle getExtras() {
        try {
            return this.zzbpb.getExtras();
        } catch (Throwable e) {
            zzb.zzc("Failed to get extras", e);
            return null;
        }
    }

    public CharSequence getHeadline() {
        try {
            return this.zzbpb.getHeadline();
        } catch (Throwable e) {
            zzb.zzb("Failed to get headline.", e);
            return null;
        }
    }

    public List<Image> getImages() {
        return this.zzboz;
    }

    public Image getLogo() {
        return this.zzbpc;
    }

    protected /* synthetic */ Object zzdy() {
        return zzmp();
    }

    zzeg zze(Object obj) {
        return obj instanceof IBinder ? zza.zzab((IBinder) obj) : null;
    }

    protected zzd zzmp() {
        try {
            return this.zzbpb.zzmp();
        } catch (Throwable e) {
            zzb.zzb("Failed to retrieve native ad engine.", e);
            return null;
        }
    }
}
