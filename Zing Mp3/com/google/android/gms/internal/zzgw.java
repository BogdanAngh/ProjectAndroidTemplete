package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzji
public class zzgw implements zzgo {
    private final Context mContext;
    private final Object zzako;
    private final zzgz zzamf;
    private final boolean zzasz;
    private final zzgq zzbwc;
    private final boolean zzbwe;
    private final AdRequestInfoParcel zzbws;
    private final long zzbwt;
    private final long zzbwu;
    private final int zzbwv;
    private boolean zzbww;
    private final Map<zzlt<zzgu>, zzgt> zzbwx;
    private List<zzgu> zzbwy;

    /* renamed from: com.google.android.gms.internal.zzgw.1 */
    class C13611 implements Callable<zzgu> {
        final /* synthetic */ zzgt zzbwz;
        final /* synthetic */ zzgw zzbxa;

        C13611(zzgw com_google_android_gms_internal_zzgw, zzgt com_google_android_gms_internal_zzgt) {
            this.zzbxa = com_google_android_gms_internal_zzgw;
            this.zzbwz = com_google_android_gms_internal_zzgt;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzol();
        }

        public zzgu zzol() throws Exception {
            synchronized (this.zzbxa.zzako) {
                if (this.zzbxa.zzbww) {
                    return null;
                }
                return this.zzbwz.zza(this.zzbxa.zzbwt, this.zzbxa.zzbwu);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgw.2 */
    class C13622 implements Runnable {
        final /* synthetic */ zzgw zzbxa;
        final /* synthetic */ zzlt zzbxb;

        C13622(zzgw com_google_android_gms_internal_zzgw, zzlt com_google_android_gms_internal_zzlt) {
            this.zzbxa = com_google_android_gms_internal_zzgw;
            this.zzbxb = com_google_android_gms_internal_zzlt;
        }

        public void run() {
            for (zzlt com_google_android_gms_internal_zzlt : this.zzbxa.zzbwx.keySet()) {
                if (com_google_android_gms_internal_zzlt != this.zzbxb) {
                    ((zzgt) this.zzbxa.zzbwx.get(com_google_android_gms_internal_zzlt)).cancel();
                }
            }
        }
    }

    public zzgw(Context context, AdRequestInfoParcel adRequestInfoParcel, zzgz com_google_android_gms_internal_zzgz, zzgq com_google_android_gms_internal_zzgq, boolean z, boolean z2, long j, long j2, int i) {
        this.zzako = new Object();
        this.zzbww = false;
        this.zzbwx = new HashMap();
        this.zzbwy = new ArrayList();
        this.mContext = context;
        this.zzbws = adRequestInfoParcel;
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.zzbwc = com_google_android_gms_internal_zzgq;
        this.zzasz = z;
        this.zzbwe = z2;
        this.zzbwt = j;
        this.zzbwu = j2;
        this.zzbwv = i;
    }

    private void zza(zzlt<zzgu> com_google_android_gms_internal_zzlt_com_google_android_gms_internal_zzgu) {
        zzlb.zzcvl.post(new C13622(this, com_google_android_gms_internal_zzlt_com_google_android_gms_internal_zzgu));
    }

    private zzgu zze(List<zzlt<zzgu>> list) {
        Throwable e;
        synchronized (this.zzako) {
            if (this.zzbww) {
                zzgu com_google_android_gms_internal_zzgu = new zzgu(-1);
                return com_google_android_gms_internal_zzgu;
            }
            for (zzlt com_google_android_gms_internal_zzlt : list) {
                try {
                    com_google_android_gms_internal_zzgu = (zzgu) com_google_android_gms_internal_zzlt.get();
                    this.zzbwy.add(com_google_android_gms_internal_zzgu);
                    if (com_google_android_gms_internal_zzgu != null && com_google_android_gms_internal_zzgu.zzbwl == 0) {
                        zza(com_google_android_gms_internal_zzlt);
                        return com_google_android_gms_internal_zzgu;
                    }
                } catch (InterruptedException e2) {
                    e = e2;
                    zzb.zzc("Exception while processing an adapter; continuing with other adapters", e);
                } catch (ExecutionException e3) {
                    e = e3;
                    zzb.zzc("Exception while processing an adapter; continuing with other adapters", e);
                }
            }
            zza(null);
            return new zzgu(1);
        }
    }

    private zzgu zzf(List<zzlt<zzgu>> list) {
        synchronized (this.zzako) {
            if (this.zzbww) {
                zzgu com_google_android_gms_internal_zzgu = new zzgu(-1);
                return com_google_android_gms_internal_zzgu;
            }
            long j = -1;
            zzlt com_google_android_gms_internal_zzlt = null;
            com_google_android_gms_internal_zzgu = null;
            long j2 = this.zzbwc.zzbvu != -1 ? this.zzbwc.zzbvu : 10000;
            long j3 = j2;
            for (zzlt com_google_android_gms_internal_zzlt2 : list) {
                zzgu com_google_android_gms_internal_zzgu2;
                zzhc com_google_android_gms_internal_zzhc;
                int zzok;
                zzgu com_google_android_gms_internal_zzgu3;
                zzlt com_google_android_gms_internal_zzlt3;
                zzgu com_google_android_gms_internal_zzgu4;
                RemoteException max;
                long currentTimeMillis = zzu.zzgs().currentTimeMillis();
                if (j3 == 0) {
                    try {
                        if (com_google_android_gms_internal_zzlt2.isDone()) {
                            com_google_android_gms_internal_zzgu2 = (zzgu) com_google_android_gms_internal_zzlt2.get();
                            this.zzbwy.add(com_google_android_gms_internal_zzgu2);
                            if (com_google_android_gms_internal_zzgu2 != null && com_google_android_gms_internal_zzgu2.zzbwl == 0) {
                                com_google_android_gms_internal_zzhc = com_google_android_gms_internal_zzgu2.zzbwq;
                                if (com_google_android_gms_internal_zzhc != null && com_google_android_gms_internal_zzhc.zzok() > j) {
                                    zzok = com_google_android_gms_internal_zzhc.zzok();
                                    com_google_android_gms_internal_zzgu3 = com_google_android_gms_internal_zzgu2;
                                    com_google_android_gms_internal_zzlt3 = com_google_android_gms_internal_zzlt2;
                                    com_google_android_gms_internal_zzgu4 = com_google_android_gms_internal_zzgu3;
                                    com_google_android_gms_internal_zzlt = com_google_android_gms_internal_zzlt3;
                                    com_google_android_gms_internal_zzgu3 = com_google_android_gms_internal_zzgu4;
                                    max = Math.max(j3 - (zzu.zzgs().currentTimeMillis() - currentTimeMillis), 0);
                                    j = zzok;
                                    com_google_android_gms_internal_zzgu = com_google_android_gms_internal_zzgu3;
                                    j3 = max;
                                }
                            }
                            com_google_android_gms_internal_zzgu4 = com_google_android_gms_internal_zzgu;
                            com_google_android_gms_internal_zzlt3 = com_google_android_gms_internal_zzlt;
                            zzok = j;
                            com_google_android_gms_internal_zzlt = com_google_android_gms_internal_zzlt3;
                            com_google_android_gms_internal_zzgu3 = com_google_android_gms_internal_zzgu4;
                            max = Math.max(j3 - (zzu.zzgs().currentTimeMillis() - currentTimeMillis), 0);
                            j = zzok;
                            com_google_android_gms_internal_zzgu = com_google_android_gms_internal_zzgu3;
                            j3 = max;
                        }
                    } catch (InterruptedException e) {
                        max = e;
                        try {
                            zzb.zzc("Exception while processing an adapter; continuing with other adapters", max);
                            j3 = max;
                        } finally {
                            com_google_android_gms_internal_zzgu = j3 - (zzu.zzgs().currentTimeMillis() - currentTimeMillis);
                            j = 0;
                            Math.max(com_google_android_gms_internal_zzgu, j);
                            j = j3;
                        }
                    } catch (ExecutionException e2) {
                        max = e2;
                        zzb.zzc("Exception while processing an adapter; continuing with other adapters", max);
                        j3 = max;
                    } catch (RemoteException e3) {
                        max = e3;
                        zzb.zzc("Exception while processing an adapter; continuing with other adapters", max);
                        j3 = max;
                    } catch (TimeoutException e4) {
                        max = e4;
                        zzb.zzc("Exception while processing an adapter; continuing with other adapters", max);
                        j3 = max;
                    }
                }
                com_google_android_gms_internal_zzgu2 = (zzgu) com_google_android_gms_internal_zzlt2.get(j3, TimeUnit.MILLISECONDS);
                this.zzbwy.add(com_google_android_gms_internal_zzgu2);
                com_google_android_gms_internal_zzhc = com_google_android_gms_internal_zzgu2.zzbwq;
                zzok = com_google_android_gms_internal_zzhc.zzok();
                com_google_android_gms_internal_zzgu3 = com_google_android_gms_internal_zzgu2;
                com_google_android_gms_internal_zzlt3 = com_google_android_gms_internal_zzlt2;
                com_google_android_gms_internal_zzgu4 = com_google_android_gms_internal_zzgu3;
                com_google_android_gms_internal_zzlt = com_google_android_gms_internal_zzlt3;
                com_google_android_gms_internal_zzgu3 = com_google_android_gms_internal_zzgu4;
                max = Math.max(j3 - (zzu.zzgs().currentTimeMillis() - currentTimeMillis), 0);
                j = zzok;
                com_google_android_gms_internal_zzgu = com_google_android_gms_internal_zzgu3;
                j3 = max;
            }
            zza(com_google_android_gms_internal_zzlt);
            return com_google_android_gms_internal_zzgu == null ? new zzgu(1) : com_google_android_gms_internal_zzgu;
        }
    }

    public void cancel() {
        synchronized (this.zzako) {
            this.zzbww = true;
            for (zzgt cancel : this.zzbwx.values()) {
                cancel.cancel();
            }
        }
    }

    public zzgu zzd(List<zzgp> list) {
        zzb.zzdg("Starting mediation.");
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        List arrayList = new ArrayList();
        for (zzgp com_google_android_gms_internal_zzgp : list) {
            String str = "Trying mediation network: ";
            String valueOf = String.valueOf(com_google_android_gms_internal_zzgp.zzbut);
            zzb.zzdh(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            for (String com_google_android_gms_internal_zzgt : com_google_android_gms_internal_zzgp.zzbuu) {
                zzgt com_google_android_gms_internal_zzgt2 = new zzgt(this.mContext, com_google_android_gms_internal_zzgt, this.zzamf, this.zzbwc, com_google_android_gms_internal_zzgp, this.zzbws.zzcju, this.zzbws.zzarm, this.zzbws.zzari, this.zzasz, this.zzbwe, this.zzbws.zzasa, this.zzbws.zzase);
                zzlt zza = zzla.zza(newCachedThreadPool, new C13611(this, com_google_android_gms_internal_zzgt2));
                this.zzbwx.put(zza, com_google_android_gms_internal_zzgt2);
                arrayList.add(zza);
            }
        }
        switch (this.zzbwv) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return zzf(arrayList);
            default:
                return zze(arrayList);
        }
    }

    public List<zzgu> zzoe() {
        return this.zzbwy;
    }
}
