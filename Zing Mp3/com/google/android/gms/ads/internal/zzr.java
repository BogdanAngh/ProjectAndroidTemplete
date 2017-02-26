package com.google.android.gms.ads.internal;

import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzlb;
import java.lang.ref.WeakReference;

@zzji
public class zzr {
    private final zza zzapi;
    @Nullable
    private AdRequestParcel zzapj;
    private boolean zzapk;
    private boolean zzapl;
    private long zzapm;
    private final Runnable zzw;

    /* renamed from: com.google.android.gms.ads.internal.zzr.1 */
    class C11361 implements Runnable {
        final /* synthetic */ WeakReference zzapn;
        final /* synthetic */ zzr zzapo;

        C11361(zzr com_google_android_gms_ads_internal_zzr, WeakReference weakReference) {
            this.zzapo = com_google_android_gms_ads_internal_zzr;
            this.zzapn = weakReference;
        }

        public void run() {
            this.zzapo.zzapk = false;
            zza com_google_android_gms_ads_internal_zza = (zza) this.zzapn.get();
            if (com_google_android_gms_ads_internal_zza != null) {
                com_google_android_gms_ads_internal_zza.zzd(this.zzapo.zzapj);
            }
        }
    }

    public static class zza {
        private final Handler mHandler;

        public zza(Handler handler) {
            this.mHandler = handler;
        }

        public boolean postDelayed(Runnable runnable, long j) {
            return this.mHandler.postDelayed(runnable, j);
        }

        public void removeCallbacks(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

    public zzr(zza com_google_android_gms_ads_internal_zza) {
        this(com_google_android_gms_ads_internal_zza, new zza(zzlb.zzcvl));
    }

    zzr(zza com_google_android_gms_ads_internal_zza, zza com_google_android_gms_ads_internal_zzr_zza) {
        this.zzapk = false;
        this.zzapl = false;
        this.zzapm = 0;
        this.zzapi = com_google_android_gms_ads_internal_zzr_zza;
        this.zzw = new C11361(this, new WeakReference(com_google_android_gms_ads_internal_zza));
    }

    public void cancel() {
        this.zzapk = false;
        this.zzapi.removeCallbacks(this.zzw);
    }

    public void pause() {
        this.zzapl = true;
        if (this.zzapk) {
            this.zzapi.removeCallbacks(this.zzw);
        }
    }

    public void resume() {
        this.zzapl = false;
        if (this.zzapk) {
            this.zzapk = false;
            zza(this.zzapj, this.zzapm);
        }
    }

    public void zza(AdRequestParcel adRequestParcel, long j) {
        if (this.zzapk) {
            zzb.zzdi("An ad refresh is already scheduled.");
            return;
        }
        this.zzapj = adRequestParcel;
        this.zzapk = true;
        this.zzapm = j;
        if (!this.zzapl) {
            zzb.zzdh("Scheduling ad refresh " + j + " milliseconds from now.");
            this.zzapi.postDelayed(this.zzw, j);
        }
    }

    public boolean zzfy() {
        return this.zzapk;
    }

    public void zzg(AdRequestParcel adRequestParcel) {
        this.zzapj = adRequestParcel;
    }

    public void zzh(AdRequestParcel adRequestParcel) {
        zza(adRequestParcel, (long) HlsChunkSource.DEFAULT_PLAYLIST_BLACKLIST_MS);
    }
}
