package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.internal.zziu.zza;

@zzji
public class zziw extends zzkw {
    private final zza zzcge;
    private final zzko.zza zzcgf;
    private final AdResponseParcel zzcgg;

    /* renamed from: com.google.android.gms.internal.zziw.1 */
    class C13881 implements Runnable {
        final /* synthetic */ zzko zzcgx;
        final /* synthetic */ zziw zzcgy;

        C13881(zziw com_google_android_gms_internal_zziw, zzko com_google_android_gms_internal_zzko) {
            this.zzcgy = com_google_android_gms_internal_zziw;
            this.zzcgx = com_google_android_gms_internal_zzko;
        }

        public void run() {
            this.zzcgy.zzcge.zzb(this.zzcgx);
        }
    }

    public zziw(zzko.zza com_google_android_gms_internal_zzko_zza, zza com_google_android_gms_internal_zziu_zza) {
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzcgg = this.zzcgf.zzcsu;
        this.zzcge = com_google_android_gms_internal_zziu_zza;
    }

    private zzko zzaq(int i) {
        return new zzko(this.zzcgf.zzcmx.zzcju, null, null, i, null, null, this.zzcgg.orientation, this.zzcgg.zzbvq, this.zzcgf.zzcmx.zzcjx, false, null, null, null, null, null, this.zzcgg.zzclc, this.zzcgf.zzarm, this.zzcgg.zzcla, this.zzcgf.zzcso, this.zzcgg.zzclf, this.zzcgg.zzclg, this.zzcgf.zzcsi, null, null, null, null, this.zzcgf.zzcsu.zzclt, this.zzcgf.zzcsu.zzclu, null, null, null);
    }

    public void onStop() {
    }

    public void zzfp() {
        zzlb.zzcvl.post(new C13881(this, zzaq(0)));
    }
}
