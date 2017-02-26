package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.internal.zzik;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzkw;
import com.google.android.gms.internal.zzkx;
import com.google.android.gms.internal.zzlb;
import java.util.List;

@zzji
public class zzc extends zzkw implements ServiceConnection {
    private Context mContext;
    private final Object zzako;
    private zzik zzbta;
    private boolean zzcff;
    private zzb zzcfg;
    private zzh zzcfh;
    private List<zzf> zzcfi;
    private zzk zzcfj;

    /* renamed from: com.google.android.gms.ads.internal.purchase.zzc.1 */
    class C10941 implements Runnable {
        final /* synthetic */ Intent val$intent;
        final /* synthetic */ zzf zzcfk;
        final /* synthetic */ zzc zzcfl;

        C10941(zzc com_google_android_gms_ads_internal_purchase_zzc, zzf com_google_android_gms_ads_internal_purchase_zzf, Intent intent) {
            this.zzcfl = com_google_android_gms_ads_internal_purchase_zzc;
            this.zzcfk = com_google_android_gms_ads_internal_purchase_zzf;
            this.val$intent = intent;
        }

        public void run() {
            try {
                if (this.zzcfl.zzcfj.zza(this.zzcfk.zzcfu, -1, this.val$intent)) {
                    this.zzcfl.zzbta.zza(new zzg(this.zzcfl.mContext, this.zzcfk.zzcfv, true, -1, this.val$intent, this.zzcfk));
                } else {
                    this.zzcfl.zzbta.zza(new zzg(this.zzcfl.mContext, this.zzcfk.zzcfv, false, -1, this.val$intent, this.zzcfk));
                }
            } catch (RemoteException e) {
                zzb.zzdi("Fail to verify and dispatch pending transaction");
            }
        }
    }

    public zzc(Context context, zzik com_google_android_gms_internal_zzik, zzk com_google_android_gms_ads_internal_purchase_zzk) {
        this(context, com_google_android_gms_internal_zzik, com_google_android_gms_ads_internal_purchase_zzk, new zzb(context), zzh.zzq(context.getApplicationContext()));
    }

    zzc(Context context, zzik com_google_android_gms_internal_zzik, zzk com_google_android_gms_ads_internal_purchase_zzk, zzb com_google_android_gms_ads_internal_purchase_zzb, zzh com_google_android_gms_ads_internal_purchase_zzh) {
        this.zzako = new Object();
        this.zzcff = false;
        this.zzcfi = null;
        this.mContext = context;
        this.zzbta = com_google_android_gms_internal_zzik;
        this.zzcfj = com_google_android_gms_ads_internal_purchase_zzk;
        this.zzcfg = com_google_android_gms_ads_internal_purchase_zzb;
        this.zzcfh = com_google_android_gms_ads_internal_purchase_zzh;
        this.zzcfi = this.zzcfh.zzg(10);
    }

    private void zze(long j) {
        do {
            if (!zzf(j)) {
                zzkx.m1697v("Timeout waiting for pending transaction to be processed.");
            }
        } while (!this.zzcff);
    }

    private boolean zzf(long j) {
        long elapsedRealtime = HlsChunkSource.DEFAULT_PLAYLIST_BLACKLIST_MS - (SystemClock.elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzako.wait(elapsedRealtime);
        } catch (InterruptedException e) {
            zzb.zzdi("waitWithTimeout_lock interrupted");
        }
        return true;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zzako) {
            this.zzcfg.zzav(iBinder);
            zzrr();
            this.zzcff = true;
            this.zzako.notify();
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        zzb.zzdh("In-app billing service disconnected.");
        this.zzcfg.destroy();
    }

    public void onStop() {
        synchronized (this.zzako) {
            zza.zzaxr().zza(this.mContext, this);
            this.zzcfg.destroy();
        }
    }

    protected void zza(zzf com_google_android_gms_ads_internal_purchase_zzf, String str, String str2) {
        Intent intent = new Intent();
        zzu.zzha();
        intent.putExtra("RESPONSE_CODE", 0);
        zzu.zzha();
        intent.putExtra("INAPP_PURCHASE_DATA", str);
        zzu.zzha();
        intent.putExtra("INAPP_DATA_SIGNATURE", str2);
        zzlb.zzcvl.post(new C10941(this, com_google_android_gms_ads_internal_purchase_zzf, intent));
    }

    public void zzfp() {
        synchronized (this.zzako) {
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
            zza.zzaxr().zza(this.mContext, intent, (ServiceConnection) this, 1);
            zze(SystemClock.elapsedRealtime());
            zza.zzaxr().zza(this.mContext, this);
            this.zzcfg.destroy();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void zzrr() {
        /*
        r12 = this;
        r0 = r12.zzcfi;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r6 = new java.util.HashMap;
        r6.<init>();
        r0 = r12.zzcfi;
        r1 = r0.iterator();
    L_0x0014:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0026;
    L_0x001a:
        r0 = r1.next();
        r0 = (com.google.android.gms.ads.internal.purchase.zzf) r0;
        r2 = r0.zzcfv;
        r6.put(r2, r0);
        goto L_0x0014;
    L_0x0026:
        r0 = 0;
    L_0x0027:
        r1 = r12.zzcfg;
        r2 = r12.mContext;
        r2 = r2.getPackageName();
        r0 = r1.zzm(r2, r0);
        if (r0 != 0) goto L_0x0055;
    L_0x0035:
        r0 = r6.keySet();
        r1 = r0.iterator();
    L_0x003d:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0008;
    L_0x0043:
        r0 = r1.next();
        r0 = (java.lang.String) r0;
        r2 = r12.zzcfh;
        r0 = r6.get(r0);
        r0 = (com.google.android.gms.ads.internal.purchase.zzf) r0;
        r2.zza(r0);
        goto L_0x003d;
    L_0x0055:
        r1 = com.google.android.gms.ads.internal.zzu.zzha();
        r1 = r1.zzd(r0);
        if (r1 != 0) goto L_0x0035;
    L_0x005f:
        r1 = "INAPP_PURCHASE_ITEM_LIST";
        r7 = r0.getStringArrayList(r1);
        r1 = "INAPP_PURCHASE_DATA_LIST";
        r8 = r0.getStringArrayList(r1);
        r1 = "INAPP_DATA_SIGNATURE_LIST";
        r9 = r0.getStringArrayList(r1);
        r1 = "INAPP_CONTINUATION_TOKEN";
        r5 = r0.getString(r1);
        r0 = 0;
        r4 = r0;
    L_0x0079:
        r0 = r7.size();
        if (r4 >= r0) goto L_0x00bb;
    L_0x007f:
        r0 = r7.get(r4);
        r0 = r6.containsKey(r0);
        if (r0 == 0) goto L_0x00b7;
    L_0x0089:
        r0 = r7.get(r4);
        r0 = (java.lang.String) r0;
        r1 = r8.get(r4);
        r1 = (java.lang.String) r1;
        r2 = r9.get(r4);
        r2 = (java.lang.String) r2;
        r3 = r6.get(r0);
        r3 = (com.google.android.gms.ads.internal.purchase.zzf) r3;
        r10 = com.google.android.gms.ads.internal.zzu.zzha();
        r10 = r10.zzcg(r1);
        r11 = r3.zzcfu;
        r10 = r11.equals(r10);
        if (r10 == 0) goto L_0x00b7;
    L_0x00b1:
        r12.zza(r3, r1, r2);
        r6.remove(r0);
    L_0x00b7:
        r0 = r4 + 1;
        r4 = r0;
        goto L_0x0079;
    L_0x00bb:
        if (r5 == 0) goto L_0x0035;
    L_0x00bd:
        r0 = r6.isEmpty();
        if (r0 != 0) goto L_0x0035;
    L_0x00c3:
        r0 = r5;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.purchase.zzc.zzrr():void");
    }
}
