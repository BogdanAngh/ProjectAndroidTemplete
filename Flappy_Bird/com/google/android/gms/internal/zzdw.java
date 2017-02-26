package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;

@zzgd
public final class zzdw {
    private final Context mContext;
    private final zzef zzoq;
    private final Object zzqt;
    private final AdRequestInfoParcel zzxm;
    private final zzdy zzxn;
    private boolean zzxo;
    private zzeb zzxp;

    /* renamed from: com.google.android.gms.internal.zzdw.1 */
    class C02081 implements Runnable {
        final /* synthetic */ zzec zzxq;
        final /* synthetic */ zzdw zzxr;

        C02081(zzdw com_google_android_gms_internal_zzdw, zzec com_google_android_gms_internal_zzec) {
            this.zzxr = com_google_android_gms_internal_zzdw;
            this.zzxq = com_google_android_gms_internal_zzec;
        }

        public void run() {
            try {
                this.zzxq.zzya.destroy();
            } catch (Throwable e) {
                zzb.zzd("Could not destroy mediation adapter.", e);
            }
        }
    }

    public zzdw(Context context, AdRequestInfoParcel adRequestInfoParcel, zzef com_google_android_gms_internal_zzef, zzdy com_google_android_gms_internal_zzdy) {
        this.zzqt = new Object();
        this.zzxo = false;
        this.mContext = context;
        this.zzxm = adRequestInfoParcel;
        this.zzoq = com_google_android_gms_internal_zzef;
        this.zzxn = com_google_android_gms_internal_zzdy;
    }

    public void cancel() {
        synchronized (this.zzqt) {
            this.zzxo = true;
            if (this.zzxp != null) {
                this.zzxp.cancel();
            }
        }
    }

    public zzec zza(long j, long j2) {
        zzb.zzay("Starting mediation.");
        for (zzdx com_google_android_gms_internal_zzdx : this.zzxn.zzxD) {
            zzb.zzaA("Trying mediation network: " + com_google_android_gms_internal_zzdx.zzxt);
            for (String str : com_google_android_gms_internal_zzdx.zzxu) {
                synchronized (this.zzqt) {
                    if (this.zzxo) {
                        zzec com_google_android_gms_internal_zzec = new zzec(-1);
                        return com_google_android_gms_internal_zzec;
                    }
                    this.zzxp = new zzeb(this.mContext, str, this.zzoq, this.zzxn, com_google_android_gms_internal_zzdx, this.zzxm.zzCm, this.zzxm.zzpN, this.zzxm.zzpJ);
                    com_google_android_gms_internal_zzec = this.zzxp.zzb(j, j2);
                    if (com_google_android_gms_internal_zzec.zzxY == 0) {
                        zzb.zzay("Adapter succeeded.");
                        return com_google_android_gms_internal_zzec;
                    } else if (com_google_android_gms_internal_zzec.zzya != null) {
                        zzhl.zzGk.post(new C02081(this, com_google_android_gms_internal_zzec));
                    }
                }
            }
        }
        return new zzec(1);
    }
}
