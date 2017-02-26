package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzha.zza;

@zzgd
public class zzgq extends zzhh implements zzgr, zzgu {
    private final Context mContext;
    private final zza zzBs;
    private int zzBv;
    private final String zzEO;
    private final zzgp zzEV;
    private final zzgu zzEW;
    private final String zzEX;
    private int zzEY;
    private final Object zzqt;
    private final String zzxQ;

    /* renamed from: com.google.android.gms.internal.zzgq.1 */
    class C02381 implements Runnable {
        final /* synthetic */ zzeg zzEZ;
        final /* synthetic */ zzgq zzFa;
        final /* synthetic */ AdRequestParcel zzoN;

        C02381(zzgq com_google_android_gms_internal_zzgq, zzeg com_google_android_gms_internal_zzeg, AdRequestParcel adRequestParcel) {
            this.zzFa = com_google_android_gms_internal_zzgq;
            this.zzEZ = com_google_android_gms_internal_zzeg;
            this.zzoN = adRequestParcel;
        }

        public void run() {
            try {
                this.zzEZ.zza(this.zzoN, this.zzFa.zzEX);
            } catch (Throwable e) {
                zzb.zzd("Fail to load ad from adapter.", e);
                this.zzFa.zzb(this.zzFa.zzxQ, 0);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgq.2 */
    class C02392 implements Runnable {
        final /* synthetic */ zzeg zzEZ;
        final /* synthetic */ zzgq zzFa;
        final /* synthetic */ zzgt zzFb;
        final /* synthetic */ AdRequestParcel zzoN;

        C02392(zzgq com_google_android_gms_internal_zzgq, zzeg com_google_android_gms_internal_zzeg, AdRequestParcel adRequestParcel, zzgt com_google_android_gms_internal_zzgt) {
            this.zzFa = com_google_android_gms_internal_zzgq;
            this.zzEZ = com_google_android_gms_internal_zzeg;
            this.zzoN = adRequestParcel;
            this.zzFb = com_google_android_gms_internal_zzgt;
        }

        public void run() {
            try {
                this.zzEZ.zza(zze.zzw(this.zzFa.mContext), this.zzoN, this.zzFa.zzEO, this.zzFb, this.zzFa.zzEX);
            } catch (Throwable e) {
                zzb.zzd("Fail to initialize adapter " + this.zzFa.zzxQ, e);
                this.zzFa.zzb(this.zzFa.zzxQ, 0);
            }
        }
    }

    public zzgq(Context context, String str, String str2, String str3, zza com_google_android_gms_internal_zzha_zza, zzgp com_google_android_gms_internal_zzgp, zzgu com_google_android_gms_internal_zzgu) {
        this.zzEY = 0;
        this.zzBv = 3;
        this.mContext = context;
        this.zzxQ = str;
        this.zzEO = str2;
        this.zzEX = str3;
        this.zzBs = com_google_android_gms_internal_zzha_zza;
        this.zzEV = com_google_android_gms_internal_zzgp;
        this.zzqt = new Object();
        this.zzEW = com_google_android_gms_internal_zzgu;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzk(long r4) {
        /*
        r3 = this;
    L_0x0000:
        r1 = r3.zzqt;
        monitor-enter(r1);
        r0 = r3.zzEY;	 Catch:{ all -> 0x0011 }
        if (r0 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ all -> 0x0011 }
    L_0x0008:
        return;
    L_0x0009:
        r0 = r3.zze(r4);	 Catch:{ all -> 0x0011 }
        if (r0 != 0) goto L_0x0014;
    L_0x000f:
        monitor-exit(r1);	 Catch:{ all -> 0x0011 }
        goto L_0x0008;
    L_0x0011:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0011 }
        throw r0;
    L_0x0014:
        monitor-exit(r1);	 Catch:{ all -> 0x0011 }
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgq.zzk(long):void");
    }

    public void onStop() {
    }

    public void zzI(int i) {
        zzb(this.zzxQ, 0);
    }

    public void zzap(String str) {
        synchronized (this.zzqt) {
            this.zzEY = 1;
            this.zzqt.notify();
        }
    }

    public void zzb(String str, int i) {
        synchronized (this.zzqt) {
            this.zzEY = 2;
            this.zzBv = i;
            this.zzqt.notify();
        }
    }

    public void zzdP() {
        if (this.zzEV != null && this.zzEV.zzfN() != null && this.zzEV.zzfM() != null) {
            zzgt zzfN = this.zzEV.zzfN();
            zzfN.zza((zzgu) this);
            zzfN.zza((zzgr) this);
            AdRequestParcel adRequestParcel = this.zzBs.zzFr.zzCm;
            zzeg zzfM = this.zzEV.zzfM();
            try {
                if (zzfM.isInitialized()) {
                    com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new C02381(this, zzfM, adRequestParcel));
                } else {
                    com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new C02392(this, zzfM, adRequestParcel, zzfN));
                }
            } catch (Throwable e) {
                zzb.zzd("Fail to check if adapter is initialized.", e);
                zzb(this.zzxQ, 0);
            }
            zzk(zzo.zzbz().elapsedRealtime());
            zzfN.zza(null);
            zzfN.zza(null);
            if (this.zzEY == 1) {
                this.zzEW.zzap(this.zzxQ);
            } else {
                this.zzEW.zzb(this.zzxQ, this.zzBv);
            }
        }
    }

    protected boolean zze(long j) {
        long elapsedRealtime = 20000 - (zzo.zzbz().elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzqt.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void zzfO() {
        this.zzEV.zzfN();
        AdRequestParcel adRequestParcel = this.zzBs.zzFr.zzCm;
        try {
            this.zzEV.zzfM().zza(adRequestParcel, this.zzEX);
        } catch (Throwable e) {
            zzb.zzd("Fail to load ad from adapter.", e);
            zzb(this.zzxQ, 0);
        }
    }
}
