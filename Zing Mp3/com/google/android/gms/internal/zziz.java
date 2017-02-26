package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzq;
import com.google.android.gms.internal.zziu.zza;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzji
public class zziz extends zzkw {
    private final Object zzako;
    private final zza zzcge;
    private final zzko.zza zzcgf;
    private final AdResponseParcel zzcgg;
    private final zzjb zzchg;
    private Future<zzko> zzchh;

    /* renamed from: com.google.android.gms.internal.zziz.1 */
    class C13901 implements Runnable {
        final /* synthetic */ zzko zzamz;
        final /* synthetic */ zziz zzchi;

        C13901(zziz com_google_android_gms_internal_zziz, zzko com_google_android_gms_internal_zzko) {
            this.zzchi = com_google_android_gms_internal_zziz;
            this.zzamz = com_google_android_gms_internal_zzko;
        }

        public void run() {
            this.zzchi.zzcge.zzb(this.zzamz);
        }
    }

    public zziz(Context context, zzq com_google_android_gms_ads_internal_zzq, zzko.zza com_google_android_gms_internal_zzko_zza, zzav com_google_android_gms_internal_zzav, zza com_google_android_gms_internal_zziu_zza, zzdz com_google_android_gms_internal_zzdz) {
        this(com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zziu_zza, new zzjb(context, com_google_android_gms_ads_internal_zzq, new zzli(context), com_google_android_gms_internal_zzav, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzdz));
    }

    zziz(zzko.zza com_google_android_gms_internal_zzko_zza, zza com_google_android_gms_internal_zziu_zza, zzjb com_google_android_gms_internal_zzjb) {
        this.zzako = new Object();
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzcgg = com_google_android_gms_internal_zzko_zza.zzcsu;
        this.zzcge = com_google_android_gms_internal_zziu_zza;
        this.zzchg = com_google_android_gms_internal_zzjb;
    }

    private zzko zzaq(int i) {
        return new zzko(this.zzcgf.zzcmx.zzcju, null, null, i, null, null, this.zzcgg.orientation, this.zzcgg.zzbvq, this.zzcgf.zzcmx.zzcjx, false, null, null, null, null, null, this.zzcgg.zzclc, this.zzcgf.zzarm, this.zzcgg.zzcla, this.zzcgf.zzcso, this.zzcgg.zzclf, this.zzcgg.zzclg, this.zzcgf.zzcsi, null, null, null, null, this.zzcgf.zzcsu.zzclt, this.zzcgf.zzcsu.zzclu, null, null, this.zzcgg.zzclx);
    }

    public void onStop() {
        synchronized (this.zzako) {
            if (this.zzchh != null) {
                this.zzchh.cancel(true);
            }
        }
    }

    public void zzfp() {
        zzko com_google_android_gms_internal_zzko;
        int i;
        try {
            synchronized (this.zzako) {
                this.zzchh = zzla.zza(this.zzchg);
            }
            com_google_android_gms_internal_zzko = (zzko) this.zzchh.get(HlsChunkSource.DEFAULT_PLAYLIST_BLACKLIST_MS, TimeUnit.MILLISECONDS);
            i = -2;
        } catch (TimeoutException e) {
            zzb.zzdi("Timed out waiting for native ad.");
            this.zzchh.cancel(true);
            i = 2;
            com_google_android_gms_internal_zzko = null;
        } catch (ExecutionException e2) {
            com_google_android_gms_internal_zzko = null;
            i = 0;
        } catch (InterruptedException e3) {
            com_google_android_gms_internal_zzko = null;
            i = 0;
        } catch (CancellationException e4) {
            com_google_android_gms_internal_zzko = null;
            i = 0;
        }
        if (com_google_android_gms_internal_zzko == null) {
            com_google_android_gms_internal_zzko = zzaq(i);
        }
        zzlb.zzcvl.post(new C13901(this, com_google_android_gms_internal_zzko));
    }
}
