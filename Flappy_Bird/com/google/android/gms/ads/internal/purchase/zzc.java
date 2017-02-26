package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzfj;
import com.google.android.gms.internal.zzgd;
import com.google.android.gms.internal.zzhh;
import com.google.android.gms.internal.zzhl;
import java.util.List;

@zzgd
public class zzc extends zzhh implements ServiceConnection {
    private Context mContext;
    private boolean zzAC;
    private zzfj zzAD;
    private zzb zzAE;
    private zzh zzAF;
    private List<zzf> zzAG;
    private zzk zzAH;
    private final Object zzqt;

    /* renamed from: com.google.android.gms.ads.internal.purchase.zzc.1 */
    class C00851 implements Runnable {
        final /* synthetic */ Intent val$intent;
        final /* synthetic */ zzf zzAI;
        final /* synthetic */ zzc zzAJ;

        C00851(zzc com_google_android_gms_ads_internal_purchase_zzc, zzf com_google_android_gms_ads_internal_purchase_zzf, Intent intent) {
            this.zzAJ = com_google_android_gms_ads_internal_purchase_zzc;
            this.zzAI = com_google_android_gms_ads_internal_purchase_zzf;
            this.val$intent = intent;
        }

        public void run() {
            try {
                if (this.zzAJ.zzAH.zza(this.zzAI.zzAS, -1, this.val$intent)) {
                    this.zzAJ.zzAD.zza(new zzg(this.zzAJ.mContext, this.zzAI.zzAT, true, -1, this.val$intent, this.zzAI));
                } else {
                    this.zzAJ.zzAD.zza(new zzg(this.zzAJ.mContext, this.zzAI.zzAT, false, -1, this.val$intent, this.zzAI));
                }
            } catch (RemoteException e) {
                zzb.zzaC("Fail to verify and dispatch pending transaction");
            }
        }
    }

    public zzc(Context context, zzfj com_google_android_gms_internal_zzfj, zzk com_google_android_gms_ads_internal_purchase_zzk) {
        this(context, com_google_android_gms_internal_zzfj, com_google_android_gms_ads_internal_purchase_zzk, new zzb(context), zzh.zzy(context.getApplicationContext()));
    }

    zzc(Context context, zzfj com_google_android_gms_internal_zzfj, zzk com_google_android_gms_ads_internal_purchase_zzk, zzb com_google_android_gms_ads_internal_purchase_zzb, zzh com_google_android_gms_ads_internal_purchase_zzh) {
        this.zzqt = new Object();
        this.zzAC = false;
        this.zzAG = null;
        this.mContext = context;
        this.zzAD = com_google_android_gms_internal_zzfj;
        this.zzAH = com_google_android_gms_ads_internal_purchase_zzk;
        this.zzAE = com_google_android_gms_ads_internal_purchase_zzb;
        this.zzAF = com_google_android_gms_ads_internal_purchase_zzh;
        this.zzAG = this.zzAF.zzf(10);
    }

    private void zzd(long j) {
        do {
            if (!zze(j)) {
                zzb.zzaB("Timeout waiting for pending transaction to be processed.");
            }
        } while (!this.zzAC);
    }

    private boolean zze(long j) {
        long elapsedRealtime = 60000 - (SystemClock.elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzqt.wait(elapsedRealtime);
        } catch (InterruptedException e) {
            zzb.zzaC("waitWithTimeout_lock interrupted");
        }
        return true;
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        synchronized (this.zzqt) {
            this.zzAE.zzK(service);
            zzfe();
            this.zzAC = true;
            this.zzqt.notify();
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        zzb.zzaA("In-app billing service disconnected.");
        this.zzAE.destroy();
    }

    public void onStop() {
        synchronized (this.zzqt) {
            com.google.android.gms.common.stats.zzb.zzoO().zza(this.mContext, (ServiceConnection) this);
            this.zzAE.destroy();
        }
    }

    protected void zza(zzf com_google_android_gms_ads_internal_purchase_zzf, String str, String str2) {
        Intent intent = new Intent();
        zzo.zzbF();
        intent.putExtra("RESPONSE_CODE", 0);
        zzo.zzbF();
        intent.putExtra("INAPP_PURCHASE_DATA", str);
        zzo.zzbF();
        intent.putExtra("INAPP_DATA_SIGNATURE", str2);
        zzhl.zzGk.post(new C00851(this, com_google_android_gms_ads_internal_purchase_zzf, intent));
    }

    public void zzdP() {
        synchronized (this.zzqt) {
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
            com.google.android.gms.common.stats.zzb.zzoO().zza(this.mContext, intent, (ServiceConnection) this, 1);
            zzd(SystemClock.elapsedRealtime());
            com.google.android.gms.common.stats.zzb.zzoO().zza(this.mContext, (ServiceConnection) this);
            this.zzAE.destroy();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void zzfe() {
        /*
        r12 = this;
        r0 = r12.zzAG;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r6 = new java.util.HashMap;
        r6.<init>();
        r0 = r12.zzAG;
        r1 = r0.iterator();
    L_0x0014:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0026;
    L_0x001a:
        r0 = r1.next();
        r0 = (com.google.android.gms.ads.internal.purchase.zzf) r0;
        r2 = r0.zzAT;
        r6.put(r2, r0);
        goto L_0x0014;
    L_0x0026:
        r0 = 0;
    L_0x0027:
        r1 = r12.zzAE;
        r2 = r12.mContext;
        r2 = r2.getPackageName();
        r0 = r1.zzi(r2, r0);
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
        r2 = r12.zzAF;
        r0 = r6.get(r0);
        r0 = (com.google.android.gms.ads.internal.purchase.zzf) r0;
        r2.zza(r0);
        goto L_0x003d;
    L_0x0055:
        r1 = com.google.android.gms.ads.internal.zzo.zzbF();
        r1 = r1.zzb(r0);
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
        r10 = com.google.android.gms.ads.internal.zzo.zzbF();
        r10 = r10.zzai(r1);
        r11 = r3.zzAS;
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.purchase.zzc.zzfe():void");
    }
}
