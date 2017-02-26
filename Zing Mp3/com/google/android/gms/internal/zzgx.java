package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzgx implements zzgo {
    private final Context mContext;
    private final Object zzako;
    private final zzdz zzalt;
    private final zzgz zzamf;
    private final boolean zzasz;
    private final zzgq zzbwc;
    private final boolean zzbwe;
    private final AdRequestInfoParcel zzbws;
    private final long zzbwt;
    private final long zzbwu;
    private boolean zzbww;
    private List<zzgu> zzbwy;
    private zzgt zzbxc;

    /* renamed from: com.google.android.gms.internal.zzgx.1 */
    class C13631 implements Runnable {
        final /* synthetic */ zzgu zzbxd;
        final /* synthetic */ zzgx zzbxe;

        C13631(zzgx com_google_android_gms_internal_zzgx, zzgu com_google_android_gms_internal_zzgu) {
            this.zzbxe = com_google_android_gms_internal_zzgx;
            this.zzbxd = com_google_android_gms_internal_zzgu;
        }

        public void run() {
            try {
                this.zzbxd.zzbwn.destroy();
            } catch (Throwable e) {
                zzb.zzc("Could not destroy mediation adapter.", e);
            }
        }
    }

    public zzgx(Context context, AdRequestInfoParcel adRequestInfoParcel, zzgz com_google_android_gms_internal_zzgz, zzgq com_google_android_gms_internal_zzgq, boolean z, boolean z2, long j, long j2, zzdz com_google_android_gms_internal_zzdz) {
        this.zzako = new Object();
        this.zzbww = false;
        this.zzbwy = new ArrayList();
        this.mContext = context;
        this.zzbws = adRequestInfoParcel;
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.zzbwc = com_google_android_gms_internal_zzgq;
        this.zzasz = z;
        this.zzbwe = z2;
        this.zzbwt = j;
        this.zzbwu = j2;
        this.zzalt = com_google_android_gms_internal_zzdz;
    }

    public void cancel() {
        synchronized (this.zzako) {
            this.zzbww = true;
            if (this.zzbxc != null) {
                this.zzbxc.cancel();
            }
        }
    }

    public zzgu zzd(List<zzgp> list) {
        zzb.zzdg("Starting mediation.");
        Iterable arrayList = new ArrayList();
        zzdx zzlz = this.zzalt.zzlz();
        for (zzgp com_google_android_gms_internal_zzgp : list) {
            String str = "Trying mediation network: ";
            String valueOf = String.valueOf(com_google_android_gms_internal_zzgp.zzbut);
            zzb.zzdh(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            for (String str2 : com_google_android_gms_internal_zzgp.zzbuu) {
                zzdx zzlz2 = this.zzalt.zzlz();
                synchronized (this.zzako) {
                    if (this.zzbww) {
                        zzgu com_google_android_gms_internal_zzgu = new zzgu(-1);
                        return com_google_android_gms_internal_zzgu;
                    }
                    this.zzbxc = new zzgt(this.mContext, str2, this.zzamf, this.zzbwc, com_google_android_gms_internal_zzgp, this.zzbws.zzcju, this.zzbws.zzarm, this.zzbws.zzari, this.zzasz, this.zzbwe, this.zzbws.zzasa, this.zzbws.zzase);
                    com_google_android_gms_internal_zzgu = this.zzbxc.zza(this.zzbwt, this.zzbwu);
                    this.zzbwy.add(com_google_android_gms_internal_zzgu);
                    if (com_google_android_gms_internal_zzgu.zzbwl == 0) {
                        zzb.zzdg("Adapter succeeded.");
                        this.zzalt.zzg("mediation_network_succeed", str2);
                        if (!arrayList.isEmpty()) {
                            this.zzalt.zzg("mediation_networks_fail", TextUtils.join(",", arrayList));
                        }
                        this.zzalt.zza(zzlz2, "mls");
                        this.zzalt.zza(zzlz, "ttm");
                        return com_google_android_gms_internal_zzgu;
                    }
                    arrayList.add(str2);
                    this.zzalt.zza(zzlz2, "mlf");
                    if (com_google_android_gms_internal_zzgu.zzbwn != null) {
                        zzlb.zzcvl.post(new C13631(this, com_google_android_gms_internal_zzgu));
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            this.zzalt.zzg("mediation_networks_fail", TextUtils.join(",", arrayList));
        }
        return new zzgu(1);
    }

    public List<zzgu> zzoe() {
        return this.zzbwy;
    }
}
