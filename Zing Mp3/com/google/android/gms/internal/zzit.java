package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;

@zzji
public abstract class zzit extends zzkw {
    protected final Context mContext;
    protected final Object zzako;
    protected final com.google.android.gms.internal.zziu.zza zzcge;
    protected final com.google.android.gms.internal.zzko.zza zzcgf;
    protected AdResponseParcel zzcgg;
    protected final Object zzcgi;

    /* renamed from: com.google.android.gms.internal.zzit.1 */
    class C13861 implements Runnable {
        final /* synthetic */ zzit zzcgv;

        C13861(zzit com_google_android_gms_internal_zzit) {
            this.zzcgv = com_google_android_gms_internal_zzit;
        }

        public void run() {
            this.zzcgv.onStop();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzit.2 */
    class C13872 implements Runnable {
        final /* synthetic */ zzko zzamz;
        final /* synthetic */ zzit zzcgv;

        C13872(zzit com_google_android_gms_internal_zzit, zzko com_google_android_gms_internal_zzko) {
            this.zzcgv = com_google_android_gms_internal_zzit;
            this.zzamz = com_google_android_gms_internal_zzko;
        }

        public void run() {
            synchronized (this.zzcgv.zzako) {
                this.zzcgv.zzn(this.zzamz);
            }
        }
    }

    protected static final class zza extends Exception {
        private final int zzcgw;

        public zza(String str, int i) {
            super(str);
            this.zzcgw = i;
        }

        public int getErrorCode() {
            return this.zzcgw;
        }
    }

    protected zzit(Context context, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, com.google.android.gms.internal.zziu.zza com_google_android_gms_internal_zziu_zza) {
        super(true);
        this.zzako = new Object();
        this.zzcgi = new Object();
        this.mContext = context;
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzcgg = com_google_android_gms_internal_zzko_zza.zzcsu;
        this.zzcge = com_google_android_gms_internal_zziu_zza;
    }

    public void onStop() {
    }

    protected abstract zzko zzap(int i);

    public void zzfp() {
        int errorCode;
        synchronized (this.zzako) {
            zzb.zzdg("AdRendererBackgroundTask started.");
            int i = this.zzcgf.errorCode;
            try {
                zzh(SystemClock.elapsedRealtime());
            } catch (zza e) {
                errorCode = e.getErrorCode();
                if (errorCode == 3 || errorCode == -1) {
                    zzb.zzdh(e.getMessage());
                } else {
                    zzb.zzdi(e.getMessage());
                }
                if (this.zzcgg == null) {
                    this.zzcgg = new AdResponseParcel(errorCode);
                } else {
                    this.zzcgg = new AdResponseParcel(errorCode, this.zzcgg.zzbvq);
                }
                zzlb.zzcvl.post(new C13861(this));
                i = errorCode;
            }
            zzlb.zzcvl.post(new C13872(this, zzap(i)));
        }
    }

    protected abstract void zzh(long j) throws zza;

    protected void zzn(zzko com_google_android_gms_internal_zzko) {
        this.zzcge.zzb(com_google_android_gms_internal_zzko);
    }
}
