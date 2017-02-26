package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzha.zza;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Future;

@zzgd
public class zzgv extends zzhh implements zzgu {
    private final Context mContext;
    private final zza zzBs;
    private final String zzEO;
    private final ArrayList<Future> zzFe;
    private final ArrayList<String> zzFf;
    private final HashSet<String> zzFg;
    private final zzgo zzFh;
    private final Object zzqt;

    /* renamed from: com.google.android.gms.internal.zzgv.1 */
    class C02401 implements Runnable {
        final /* synthetic */ zzgv zzFi;
        final /* synthetic */ zzha zzpd;

        C02401(zzgv com_google_android_gms_internal_zzgv, zzha com_google_android_gms_internal_zzha) {
            this.zzFi = com_google_android_gms_internal_zzgv;
            this.zzpd = com_google_android_gms_internal_zzha;
        }

        public void run() {
            this.zzFi.zzFh.zzb(this.zzpd);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgv.2 */
    class C02412 implements Runnable {
        final /* synthetic */ zzgv zzFi;
        final /* synthetic */ zzha zzpd;

        C02412(zzgv com_google_android_gms_internal_zzgv, zzha com_google_android_gms_internal_zzha) {
            this.zzFi = com_google_android_gms_internal_zzgv;
            this.zzpd = com_google_android_gms_internal_zzha;
        }

        public void run() {
            this.zzFi.zzFh.zzb(this.zzpd);
        }
    }

    public zzgv(Context context, String str, zza com_google_android_gms_internal_zzha_zza, zzgo com_google_android_gms_internal_zzgo) {
        this.zzFe = new ArrayList();
        this.zzFf = new ArrayList();
        this.zzFg = new HashSet();
        this.zzqt = new Object();
        this.mContext = context;
        this.zzEO = str;
        this.zzBs = com_google_android_gms_internal_zzha_zza;
        this.zzFh = com_google_android_gms_internal_zzgo;
    }

    private void zzj(String str, String str2) {
        synchronized (this.zzqt) {
            zzgp zzao = this.zzFh.zzao(str);
            if (zzao == null || zzao.zzfN() == null || zzao.zzfM() == null) {
                return;
            }
            this.zzFe.add(new zzgq(this.mContext, str, this.zzEO, str2, this.zzBs, zzao, this).zzgi());
            this.zzFf.add(str);
        }
    }

    public void onStop() {
    }

    public void zzap(String str) {
        synchronized (this.zzqt) {
            this.zzFg.add(str);
        }
    }

    public void zzb(String str, int i) {
    }

    public void zzdP() {
        for (zzdx com_google_android_gms_internal_zzdx : this.zzBs.zzFm.zzxD) {
            String str = com_google_android_gms_internal_zzdx.zzxz;
            for (String zzj : com_google_android_gms_internal_zzdx.zzxu) {
                zzj(zzj, str);
            }
        }
        int i = 0;
        while (i < this.zzFe.size()) {
            try {
                ((Future) this.zzFe.get(i)).get();
                synchronized (this.zzqt) {
                    if (this.zzFg.contains(this.zzFf.get(i))) {
                        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new C02401(this, new zzha(this.zzBs.zzFr.zzCm, null, this.zzBs.zzFs.zzxF, -2, this.zzBs.zzFs.zzxG, this.zzBs.zzFs.zzCM, this.zzBs.zzFs.orientation, this.zzBs.zzFs.zzxJ, this.zzBs.zzFr.zzCp, this.zzBs.zzFs.zzCK, (zzdx) this.zzBs.zzFm.zzxD.get(i), null, (String) this.zzFf.get(i), this.zzBs.zzFm, null, this.zzBs.zzFs.zzCL, this.zzBs.zzpN, this.zzBs.zzFs.zzCJ, this.zzBs.zzFo, this.zzBs.zzFs.zzCO, this.zzBs.zzFs.zzCP, this.zzBs.zzFl, null, this.zzBs.zzFr.zzCC)));
                        return;
                    }
                    i++;
                }
            } catch (InterruptedException e) {
            } catch (Exception e2) {
            }
        }
        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new C02412(this, new zzha(this.zzBs.zzFr.zzCm, null, this.zzBs.zzFs.zzxF, 3, this.zzBs.zzFs.zzxG, this.zzBs.zzFs.zzCM, this.zzBs.zzFs.orientation, this.zzBs.zzFs.zzxJ, this.zzBs.zzFr.zzCp, this.zzBs.zzFs.zzCK, null, null, null, this.zzBs.zzFm, null, this.zzBs.zzFs.zzCL, this.zzBs.zzpN, this.zzBs.zzFs.zzCJ, this.zzBs.zzFo, this.zzBs.zzFs.zzCO, this.zzBs.zzFs.zzCP, this.zzBs.zzFl, null, this.zzBs.zzFr.zzCC)));
    }
}
