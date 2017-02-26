package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.AbstractPendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaf.zzj;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.internal.zzqa;
import com.google.android.gms.internal.zzqe;

public class zzp extends AbstractPendingResult<ContainerHolder> {
    private final Context mContext;
    private final Looper zzWt;
    private long zzaKD;
    private final TagManager zzaKK;
    private final zzd zzaKN;
    private final zzcd zzaKO;
    private final int zzaKP;
    private zzf zzaKQ;
    private zzqa zzaKR;
    private volatile zzo zzaKS;
    private volatile boolean zzaKT;
    private zzj zzaKU;
    private String zzaKV;
    private zze zzaKW;
    private zza zzaKX;
    private final String zzaKy;
    private final zzlb zzpw;

    interface zza {
        boolean zzb(Container container);
    }

    /* renamed from: com.google.android.gms.tagmanager.zzp.1 */
    class C05321 implements com.google.android.gms.internal.zzqa.zza {
        final /* synthetic */ String zzaKY;
        final /* synthetic */ zzp zzaKZ;

        /* renamed from: com.google.android.gms.tagmanager.zzp.1.1 */
        class C05311 implements com.google.android.gms.tagmanager.zzo.zza {
            final /* synthetic */ C05321 zzaLa;

            C05311(C05321 c05321) {
                this.zzaLa = c05321;
            }

            public void zzej(String str) {
                this.zzaLa.zzaKZ.zzej(str);
            }

            public String zzyo() {
                return this.zzaLa.zzaKZ.zzyo();
            }

            public void zzyq() {
                if (this.zzaLa.zzaKZ.zzaKO.zzkb()) {
                    this.zzaLa.zzaKZ.load(this.zzaLa.zzaKY);
                }
            }
        }

        C05321(zzp com_google_android_gms_tagmanager_zzp, String str) {
            this.zzaKZ = com_google_android_gms_tagmanager_zzp;
            this.zzaKY = str;
        }

        public void zza(zzqe com_google_android_gms_internal_zzqe) {
            if (com_google_android_gms_internal_zzqe.getStatus() != Status.zzXP) {
                zzbg.zzaz("Load request failed for the container " + this.zzaKZ.zzaKy);
                this.zzaKZ.setResult(this.zzaKZ.zzaU(Status.zzXR));
                return;
            }
            com.google.android.gms.internal.zzqf.zzc zzAk = com_google_android_gms_internal_zzqe.zzAg().zzAk();
            if (zzAk == null) {
                String str = "Response doesn't have the requested container";
                zzbg.zzaz(str);
                this.zzaKZ.setResult(this.zzaKZ.zzaU(new Status(8, str, null)));
                return;
            }
            this.zzaKZ.zzaKS = new zzo(this.zzaKZ.zzaKK, this.zzaKZ.zzWt, new Container(this.zzaKZ.mContext, this.zzaKZ.zzaKK.getDataLayer(), this.zzaKZ.zzaKy, com_google_android_gms_internal_zzqe.zzAg().zzAl(), zzAk), new C05311(this));
            this.zzaKZ.setResult(this.zzaKZ.zzaKS);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzp.2 */
    class C05332 implements com.google.android.gms.tagmanager.zzo.zza {
        final /* synthetic */ zzp zzaKZ;

        C05332(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzaKZ = com_google_android_gms_tagmanager_zzp;
        }

        public void zzej(String str) {
            this.zzaKZ.zzej(str);
        }

        public String zzyo() {
            return this.zzaKZ.zzyo();
        }

        public void zzyq() {
            zzbg.zzaC("Refresh ignored: container loaded as default only.");
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzp.3 */
    class C05343 implements zza {
        final /* synthetic */ zzp zzaKZ;
        final /* synthetic */ boolean zzaLb;

        C05343(zzp com_google_android_gms_tagmanager_zzp, boolean z) {
            this.zzaKZ = com_google_android_gms_tagmanager_zzp;
            this.zzaLb = z;
        }

        public boolean zzb(Container container) {
            return this.zzaLb ? container.getLastRefreshTime() + 43200000 >= this.zzaKZ.zzpw.currentTimeMillis() : !container.isDefault();
        }
    }

    private class zzb implements zzbf<com.google.android.gms.internal.zzpx.zza> {
        final /* synthetic */ zzp zzaKZ;

        private zzb(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzaKZ = com_google_android_gms_tagmanager_zzp;
        }

        public void zza(com.google.android.gms.internal.zzpx.zza com_google_android_gms_internal_zzpx_zza) {
            zzj com_google_android_gms_internal_zzaf_zzj;
            if (com_google_android_gms_internal_zzpx_zza.zzaPa != null) {
                com_google_android_gms_internal_zzaf_zzj = com_google_android_gms_internal_zzpx_zza.zzaPa;
            } else {
                com.google.android.gms.internal.zzaf.zzf com_google_android_gms_internal_zzaf_zzf = com_google_android_gms_internal_zzpx_zza.zziO;
                com_google_android_gms_internal_zzaf_zzj = new zzj();
                com_google_android_gms_internal_zzaf_zzj.zziO = com_google_android_gms_internal_zzaf_zzf;
                com_google_android_gms_internal_zzaf_zzj.zziN = null;
                com_google_android_gms_internal_zzaf_zzj.zziP = com_google_android_gms_internal_zzaf_zzf.version;
            }
            this.zzaKZ.zza(com_google_android_gms_internal_zzaf_zzj, com_google_android_gms_internal_zzpx_zza.zzaOZ, true);
        }

        public void zza(com.google.android.gms.tagmanager.zzbf.zza com_google_android_gms_tagmanager_zzbf_zza) {
            if (!this.zzaKZ.zzaKT) {
                this.zzaKZ.zzR(0);
            }
        }

        public void zzyv() {
        }

        public /* synthetic */ void zzz(Object obj) {
            zza((com.google.android.gms.internal.zzpx.zza) obj);
        }
    }

    private class zzc implements zzbf<zzj> {
        final /* synthetic */ zzp zzaKZ;

        private zzc(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzaKZ = com_google_android_gms_tagmanager_zzp;
        }

        public void zza(com.google.android.gms.tagmanager.zzbf.zza com_google_android_gms_tagmanager_zzbf_zza) {
            synchronized (this.zzaKZ) {
                if (!this.zzaKZ.isReady()) {
                    if (this.zzaKZ.zzaKS != null) {
                        this.zzaKZ.setResult(this.zzaKZ.zzaKS);
                    } else {
                        this.zzaKZ.setResult(this.zzaKZ.zzaU(Status.zzXS));
                    }
                }
            }
            this.zzaKZ.zzR(3600000);
        }

        public void zzb(zzj com_google_android_gms_internal_zzaf_zzj) {
            synchronized (this.zzaKZ) {
                if (com_google_android_gms_internal_zzaf_zzj.zziO == null) {
                    if (this.zzaKZ.zzaKU.zziO == null) {
                        zzbg.zzaz("Current resource is null; network resource is also null");
                        this.zzaKZ.zzR(3600000);
                        return;
                    }
                    com_google_android_gms_internal_zzaf_zzj.zziO = this.zzaKZ.zzaKU.zziO;
                }
                this.zzaKZ.zza(com_google_android_gms_internal_zzaf_zzj, this.zzaKZ.zzpw.currentTimeMillis(), false);
                zzbg.zzaB("setting refresh time to current time: " + this.zzaKZ.zzaKD);
                if (!this.zzaKZ.zzyu()) {
                    this.zzaKZ.zza(com_google_android_gms_internal_zzaf_zzj);
                }
            }
        }

        public void zzyv() {
        }

        public /* synthetic */ void zzz(Object obj) {
            zzb((zzj) obj);
        }
    }

    private class zzd implements com.google.android.gms.tagmanager.zzo.zza {
        final /* synthetic */ zzp zzaKZ;

        private zzd(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzaKZ = com_google_android_gms_tagmanager_zzp;
        }

        public void zzej(String str) {
            this.zzaKZ.zzej(str);
        }

        public String zzyo() {
            return this.zzaKZ.zzyo();
        }

        public void zzyq() {
            if (this.zzaKZ.zzaKO.zzkb()) {
                this.zzaKZ.zzR(0);
            }
        }
    }

    interface zze extends Releasable {
        void zza(zzbf<zzj> com_google_android_gms_tagmanager_zzbf_com_google_android_gms_internal_zzaf_zzj);

        void zzem(String str);

        void zzf(long j, String str);
    }

    interface zzf extends Releasable {
        void zza(zzbf<com.google.android.gms.internal.zzpx.zza> com_google_android_gms_tagmanager_zzbf_com_google_android_gms_internal_zzpx_zza);

        void zzb(com.google.android.gms.internal.zzpx.zza com_google_android_gms_internal_zzpx_zza);

        com.google.android.gms.internal.zzqf.zzc zziR(int i);

        void zzyw();
    }

    zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzf com_google_android_gms_tagmanager_zzp_zzf, zze com_google_android_gms_tagmanager_zzp_zze, zzqa com_google_android_gms_internal_zzqa, zzlb com_google_android_gms_internal_zzlb, zzcd com_google_android_gms_tagmanager_zzcd) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.zzaKK = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzWt = looper;
        this.zzaKy = str;
        this.zzaKP = i;
        this.zzaKQ = com_google_android_gms_tagmanager_zzp_zzf;
        this.zzaKW = com_google_android_gms_tagmanager_zzp_zze;
        this.zzaKR = com_google_android_gms_internal_zzqa;
        this.zzaKN = new zzd();
        this.zzaKU = new zzj();
        this.zzpw = com_google_android_gms_internal_zzlb;
        this.zzaKO = com_google_android_gms_tagmanager_zzcd;
        if (zzyu()) {
            zzej(zzcb.zzzf().zzzh());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzs com_google_android_gms_tagmanager_zzs) {
        this(context, tagManager, looper, str, i, new zzcn(context, str), new zzcm(context, str, com_google_android_gms_tagmanager_zzs), new zzqa(context), zzld.zzoQ(), new zzbe(30, 900000, 5000, "refreshing", zzld.zzoQ()));
        this.zzaKR.zzeU(com_google_android_gms_tagmanager_zzs.zzyx());
    }

    private synchronized void zzR(long j) {
        if (this.zzaKW == null) {
            zzbg.zzaC("Refresh requested, but no network load scheduler.");
        } else {
            this.zzaKW.zzf(j, this.zzaKU.zziP);
        }
    }

    private synchronized void zza(zzj com_google_android_gms_internal_zzaf_zzj) {
        if (this.zzaKQ != null) {
            com.google.android.gms.internal.zzpx.zza com_google_android_gms_internal_zzpx_zza = new com.google.android.gms.internal.zzpx.zza();
            com_google_android_gms_internal_zzpx_zza.zzaOZ = this.zzaKD;
            com_google_android_gms_internal_zzpx_zza.zziO = new com.google.android.gms.internal.zzaf.zzf();
            com_google_android_gms_internal_zzpx_zza.zzaPa = com_google_android_gms_internal_zzaf_zzj;
            this.zzaKQ.zzb(com_google_android_gms_internal_zzpx_zza);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void zza(com.google.android.gms.internal.zzaf.zzj r9, long r10, boolean r12) {
        /*
        r8 = this;
        r6 = 43200000; // 0x2932e00 float:2.1626111E-37 double:2.1343636E-316;
        monitor-enter(r8);
        if (r12 == 0) goto L_0x000c;
    L_0x0006:
        r0 = r8.zzaKT;	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r8);
        return;
    L_0x000c:
        r0 = r8.isReady();	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r8.zzaKS;	 Catch:{ all -> 0x006a }
        if (r0 != 0) goto L_0x0016;
    L_0x0016:
        r8.zzaKU = r9;	 Catch:{ all -> 0x006a }
        r8.zzaKD = r10;	 Catch:{ all -> 0x006a }
        r0 = 0;
        r2 = 43200000; // 0x2932e00 float:2.1626111E-37 double:2.1343636E-316;
        r4 = r8.zzaKD;	 Catch:{ all -> 0x006a }
        r4 = r4 + r6;
        r6 = r8.zzpw;	 Catch:{ all -> 0x006a }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x006a }
        r4 = r4 - r6;
        r2 = java.lang.Math.min(r2, r4);	 Catch:{ all -> 0x006a }
        r0 = java.lang.Math.max(r0, r2);	 Catch:{ all -> 0x006a }
        r8.zzR(r0);	 Catch:{ all -> 0x006a }
        r0 = new com.google.android.gms.tagmanager.Container;	 Catch:{ all -> 0x006a }
        r1 = r8.mContext;	 Catch:{ all -> 0x006a }
        r2 = r8.zzaKK;	 Catch:{ all -> 0x006a }
        r2 = r2.getDataLayer();	 Catch:{ all -> 0x006a }
        r3 = r8.zzaKy;	 Catch:{ all -> 0x006a }
        r4 = r10;
        r6 = r9;
        r0.<init>(r1, r2, r3, r4, r6);	 Catch:{ all -> 0x006a }
        r1 = r8.zzaKS;	 Catch:{ all -> 0x006a }
        if (r1 != 0) goto L_0x006d;
    L_0x0049:
        r1 = new com.google.android.gms.tagmanager.zzo;	 Catch:{ all -> 0x006a }
        r2 = r8.zzaKK;	 Catch:{ all -> 0x006a }
        r3 = r8.zzWt;	 Catch:{ all -> 0x006a }
        r4 = r8.zzaKN;	 Catch:{ all -> 0x006a }
        r1.<init>(r2, r3, r0, r4);	 Catch:{ all -> 0x006a }
        r8.zzaKS = r1;	 Catch:{ all -> 0x006a }
    L_0x0056:
        r1 = r8.isReady();	 Catch:{ all -> 0x006a }
        if (r1 != 0) goto L_0x000a;
    L_0x005c:
        r1 = r8.zzaKX;	 Catch:{ all -> 0x006a }
        r0 = r1.zzb(r0);	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x000a;
    L_0x0064:
        r0 = r8.zzaKS;	 Catch:{ all -> 0x006a }
        r8.setResult(r0);	 Catch:{ all -> 0x006a }
        goto L_0x000a;
    L_0x006a:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
    L_0x006d:
        r1 = r8.zzaKS;	 Catch:{ all -> 0x006a }
        r1.zza(r0);	 Catch:{ all -> 0x006a }
        goto L_0x0056;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzp.zza(com.google.android.gms.internal.zzaf$zzj, long, boolean):void");
    }

    private void zzam(boolean z) {
        this.zzaKQ.zza(new zzb());
        this.zzaKW.zza(new zzc());
        com.google.android.gms.internal.zzqf.zzc zziR = this.zzaKQ.zziR(this.zzaKP);
        if (zziR != null) {
            this.zzaKS = new zzo(this.zzaKK, this.zzWt, new Container(this.mContext, this.zzaKK.getDataLayer(), this.zzaKy, 0, zziR), this.zzaKN);
        }
        this.zzaKX = new C05343(this, z);
        if (zzyu()) {
            this.zzaKW.zzf(0, "");
        } else {
            this.zzaKQ.zzyw();
        }
    }

    private boolean zzyu() {
        zzcb zzzf = zzcb.zzzf();
        return (zzzf.zzzg() == zza.CONTAINER || zzzf.zzzg() == zza.CONTAINER_DEBUG) && this.zzaKy.equals(zzzf.getContainerId());
    }

    protected /* synthetic */ Result createFailedResult(Status x0) {
        return zzaU(x0);
    }

    public void load(String resourceIdParameterName) {
        this.zzaKR.zza(this.zzaKy, this.zzaKP != -1 ? Integer.valueOf(this.zzaKP) : null, resourceIdParameterName, new C05321(this, resourceIdParameterName));
    }

    protected ContainerHolder zzaU(Status status) {
        if (this.zzaKS != null) {
            return this.zzaKS;
        }
        if (status == Status.zzXS) {
            zzbg.zzaz("timer expired: setting result to failure");
        }
        return new zzo(status);
    }

    synchronized void zzej(String str) {
        this.zzaKV = str;
        if (this.zzaKW != null) {
            this.zzaKW.zzem(str);
        }
    }

    synchronized String zzyo() {
        return this.zzaKV;
    }

    public void zzyr() {
        com.google.android.gms.internal.zzqf.zzc zziR = this.zzaKQ.zziR(this.zzaKP);
        if (zziR != null) {
            setResult(new zzo(this.zzaKK, this.zzWt, new Container(this.mContext, this.zzaKK.getDataLayer(), this.zzaKy, 0, zziR), new C05332(this)));
        } else {
            String str = "Default was requested, but no default container was found";
            zzbg.zzaz(str);
            setResult(zzaU(new Status(10, str, null)));
        }
        this.zzaKW = null;
        this.zzaKQ = null;
    }

    public void zzys() {
        zzam(false);
    }

    public void zzyt() {
        zzam(true);
    }
}
