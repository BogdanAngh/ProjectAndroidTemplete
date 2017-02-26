package com.google.android.gms.ads.internal.overlay;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzlb;
import java.util.concurrent.TimeUnit;

@zzji
@TargetApi(14)
public class zzv {
    private final long zzccp;
    private long zzccq;
    private boolean zzccr;

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzv.1 */
    class C10921 implements Runnable {
        final /* synthetic */ zzh zzccs;
        final /* synthetic */ zzv zzcct;

        C10921(zzv com_google_android_gms_ads_internal_overlay_zzv, zzh com_google_android_gms_ads_internal_overlay_zzh) {
            this.zzcct = com_google_android_gms_ads_internal_overlay_zzv;
            this.zzccs = com_google_android_gms_ads_internal_overlay_zzh;
        }

        public void run() {
            this.zzccs.zzqg();
        }
    }

    zzv() {
        this.zzccp = TimeUnit.MILLISECONDS.toNanos(((Long) zzdr.zzbdy.get()).longValue());
        this.zzccr = true;
    }

    public void zza(SurfaceTexture surfaceTexture, zzh com_google_android_gms_ads_internal_overlay_zzh) {
        if (com_google_android_gms_ads_internal_overlay_zzh != null) {
            long timestamp = surfaceTexture.getTimestamp();
            if (this.zzccr || Math.abs(timestamp - this.zzccq) >= this.zzccp) {
                this.zzccr = false;
                this.zzccq = timestamp;
                zzlb.zzcvl.post(new C10921(this, com_google_android_gms_ads_internal_overlay_zzh));
            }
        }
    }

    public void zzqd() {
        this.zzccr = true;
    }
}
