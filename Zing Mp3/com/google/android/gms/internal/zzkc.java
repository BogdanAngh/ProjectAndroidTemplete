package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzko.zza;

@zzji
public class zzkc extends zzkw implements zzke, zzkh {
    private final Context mContext;
    private final Object zzako;
    private final String zzbwa;
    private final zza zzcgf;
    private int zzcgw;
    private final zzkj zzcre;
    private final zzkh zzcrf;
    private final String zzcrg;
    private final zzgp zzcrh;
    private final long zzcri;
    private int zzcrj;
    private zzkd zzcrk;

    /* renamed from: com.google.android.gms.internal.zzkc.1 */
    class C14221 implements Runnable {
        final /* synthetic */ AdRequestParcel zzanw;
        final /* synthetic */ zzha zzcrl;
        final /* synthetic */ zzkc zzcrm;

        C14221(zzkc com_google_android_gms_internal_zzkc, AdRequestParcel adRequestParcel, zzha com_google_android_gms_internal_zzha) {
            this.zzcrm = com_google_android_gms_internal_zzkc;
            this.zzanw = adRequestParcel;
            this.zzcrl = com_google_android_gms_internal_zzha;
        }

        public void run() {
            this.zzcrm.zza(this.zzanw, this.zzcrl);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkc.2 */
    class C14232 implements Runnable {
        final /* synthetic */ AdRequestParcel zzanw;
        final /* synthetic */ zzha zzcrl;
        final /* synthetic */ zzkc zzcrm;
        final /* synthetic */ zzkg zzcrn;

        C14232(zzkc com_google_android_gms_internal_zzkc, zzha com_google_android_gms_internal_zzha, AdRequestParcel adRequestParcel, zzkg com_google_android_gms_internal_zzkg) {
            this.zzcrm = com_google_android_gms_internal_zzkc;
            this.zzcrl = com_google_android_gms_internal_zzha;
            this.zzanw = adRequestParcel;
            this.zzcrn = com_google_android_gms_internal_zzkg;
        }

        public void run() {
            try {
                this.zzcrl.zza(zze.zzac(this.zzcrm.mContext), this.zzanw, null, this.zzcrn, this.zzcrm.zzcrg);
            } catch (Throwable e) {
                Throwable th = e;
                String str = "Fail to initialize adapter ";
                String valueOf = String.valueOf(this.zzcrm.zzbwa);
                zzb.zzc(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), th);
                this.zzcrm.zza(this.zzcrm.zzbwa, 0);
            }
        }
    }

    public zzkc(Context context, String str, String str2, zzgp com_google_android_gms_internal_zzgp, zza com_google_android_gms_internal_zzko_zza, zzkj com_google_android_gms_internal_zzkj, zzkh com_google_android_gms_internal_zzkh, long j) {
        this.zzcrj = 0;
        this.zzcgw = 3;
        this.mContext = context;
        this.zzbwa = str;
        this.zzcrg = str2;
        this.zzcrh = com_google_android_gms_internal_zzgp;
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzcre = com_google_android_gms_internal_zzkj;
        this.zzako = new Object();
        this.zzcrf = com_google_android_gms_internal_zzkh;
        this.zzcri = j;
    }

    private void zza(AdRequestParcel adRequestParcel, zzha com_google_android_gms_internal_zzha) {
        this.zzcre.zzud().zza((zzkh) this);
        try {
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzbwa)) {
                com_google_android_gms_internal_zzha.zza(adRequestParcel, this.zzcrg, this.zzcrh.zzbus);
            } else {
                com_google_android_gms_internal_zzha.zzc(adRequestParcel, this.zzcrg);
            }
        } catch (Throwable e) {
            zzb.zzc("Fail to load ad from adapter.", e);
            zza(this.zzbwa, 0);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzk(long r6) {
        /*
        r5 = this;
    L_0x0000:
        r1 = r5.zzako;
        monitor-enter(r1);
        r0 = r5.zzcrj;	 Catch:{ all -> 0x0070 }
        if (r0 == 0) goto L_0x003c;
    L_0x0007:
        r0 = new com.google.android.gms.internal.zzkd$zza;	 Catch:{ all -> 0x0070 }
        r0.<init>();	 Catch:{ all -> 0x0070 }
        r2 = com.google.android.gms.ads.internal.zzu.zzgs();	 Catch:{ all -> 0x0070 }
        r2 = r2.elapsedRealtime();	 Catch:{ all -> 0x0070 }
        r2 = r2 - r6;
        r2 = r0.zzl(r2);	 Catch:{ all -> 0x0070 }
        r0 = 1;
        r3 = r5.zzcrj;	 Catch:{ all -> 0x0070 }
        if (r0 != r3) goto L_0x0039;
    L_0x001e:
        r0 = 6;
    L_0x001f:
        r0 = r2.zzbc(r0);	 Catch:{ all -> 0x0070 }
        r2 = r5.zzbwa;	 Catch:{ all -> 0x0070 }
        r0 = r0.zzcr(r2);	 Catch:{ all -> 0x0070 }
        r2 = r5.zzcrh;	 Catch:{ all -> 0x0070 }
        r2 = r2.zzbuv;	 Catch:{ all -> 0x0070 }
        r0 = r0.zzcs(r2);	 Catch:{ all -> 0x0070 }
        r0 = r0.zztz();	 Catch:{ all -> 0x0070 }
        r5.zzcrk = r0;	 Catch:{ all -> 0x0070 }
        monitor-exit(r1);	 Catch:{ all -> 0x0070 }
    L_0x0038:
        return;
    L_0x0039:
        r0 = r5.zzcgw;	 Catch:{ all -> 0x0070 }
        goto L_0x001f;
    L_0x003c:
        r0 = r5.zzf(r6);	 Catch:{ all -> 0x0070 }
        if (r0 != 0) goto L_0x0073;
    L_0x0042:
        r0 = new com.google.android.gms.internal.zzkd$zza;	 Catch:{ all -> 0x0070 }
        r0.<init>();	 Catch:{ all -> 0x0070 }
        r2 = r5.zzcgw;	 Catch:{ all -> 0x0070 }
        r0 = r0.zzbc(r2);	 Catch:{ all -> 0x0070 }
        r2 = com.google.android.gms.ads.internal.zzu.zzgs();	 Catch:{ all -> 0x0070 }
        r2 = r2.elapsedRealtime();	 Catch:{ all -> 0x0070 }
        r2 = r2 - r6;
        r0 = r0.zzl(r2);	 Catch:{ all -> 0x0070 }
        r2 = r5.zzbwa;	 Catch:{ all -> 0x0070 }
        r0 = r0.zzcr(r2);	 Catch:{ all -> 0x0070 }
        r2 = r5.zzcrh;	 Catch:{ all -> 0x0070 }
        r2 = r2.zzbuv;	 Catch:{ all -> 0x0070 }
        r0 = r0.zzcs(r2);	 Catch:{ all -> 0x0070 }
        r0 = r0.zztz();	 Catch:{ all -> 0x0070 }
        r5.zzcrk = r0;	 Catch:{ all -> 0x0070 }
        monitor-exit(r1);	 Catch:{ all -> 0x0070 }
        goto L_0x0038;
    L_0x0070:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0070 }
        throw r0;
    L_0x0073:
        monitor-exit(r1);	 Catch:{ all -> 0x0070 }
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzkc.zzk(long):void");
    }

    public void onStop() {
    }

    public void zza(String str, int i) {
        synchronized (this.zzako) {
            this.zzcrj = 2;
            this.zzcgw = i;
            this.zzako.notify();
        }
    }

    public void zzbb(int i) {
        zza(this.zzbwa, 0);
    }

    public void zzcq(String str) {
        synchronized (this.zzako) {
            this.zzcrj = 1;
            this.zzako.notify();
        }
    }

    protected boolean zzf(long j) {
        long elapsedRealtime = this.zzcri - (zzu.zzgs().elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            this.zzcgw = 4;
            return false;
        }
        try {
            this.zzako.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.zzcgw = 5;
            return false;
        }
    }

    public void zzfp() {
        if (this.zzcre != null && this.zzcre.zzud() != null && this.zzcre.zzuc() != null) {
            zzkg zzud = this.zzcre.zzud();
            zzud.zza(null);
            zzud.zza((zzke) this);
            AdRequestParcel adRequestParcel = this.zzcgf.zzcmx.zzcju;
            zzha zzuc = this.zzcre.zzuc();
            try {
                if (zzuc.isInitialized()) {
                    com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C14221(this, adRequestParcel, zzuc));
                } else {
                    com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C14232(this, zzuc, adRequestParcel, zzud));
                }
            } catch (Throwable e) {
                zzb.zzc("Fail to check if adapter is initialized.", e);
                zza(this.zzbwa, 0);
            }
            zzk(zzu.zzgs().elapsedRealtime());
            zzud.zza(null);
            zzud.zza(null);
            if (this.zzcrj == 1) {
                this.zzcrf.zzcq(this.zzbwa);
            } else {
                this.zzcrf.zza(this.zzbwa, this.zzcgw);
            }
        }
    }

    public zzkd zztw() {
        zzkd com_google_android_gms_internal_zzkd;
        synchronized (this.zzako) {
            com_google_android_gms_internal_zzkd = this.zzcrk;
        }
        return com_google_android_gms_internal_zzkd;
    }

    public zzgp zztx() {
        return this.zzcrh;
    }

    public void zzty() {
        zza(this.zzcgf.zzcmx.zzcju, this.zzcre.zzuc());
    }
}
