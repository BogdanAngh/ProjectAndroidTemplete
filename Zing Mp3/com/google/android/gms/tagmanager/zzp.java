package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzafv;
import com.google.android.gms.internal.zzai.zzj;
import com.google.android.gms.internal.zzqq;
import com.mp3download.zingmp3.BuildConfig;

public class zzp extends zzqq<ContainerHolder> {
    private final String aDY;
    private long aEd;
    private final TagManager aEk;
    private final zzd aEn;
    private final zzcl aEo;
    private final int aEp;
    private final zzq aEq;
    private zzf aEr;
    private zzafv aEs;
    private volatile zzo aEt;
    private volatile boolean aEu;
    private zzj aEv;
    private String aEw;
    private zze aEx;
    private zza aEy;
    private final Context mContext;
    private final Looper zzajy;
    private final com.google.android.gms.common.util.zze zzaql;

    interface zze extends Releasable {
        void zza(zzbn<zzj> com_google_android_gms_tagmanager_zzbn_com_google_android_gms_internal_zzai_zzj);

        void zzf(long j, String str);

        void zzpa(String str);
    }

    interface zzf extends Releasable {
        void zza(zzbn<com.google.android.gms.internal.zzafu.zza> com_google_android_gms_tagmanager_zzbn_com_google_android_gms_internal_zzafu_zza);

        void zzb(com.google.android.gms.internal.zzafu.zza com_google_android_gms_internal_zzafu_zza);

        void zzcej();

        com.google.android.gms.internal.zzafw.zzc zzzz(int i);
    }

    /* renamed from: com.google.android.gms.tagmanager.zzp.1 */
    class C15491 {
    }

    /* renamed from: com.google.android.gms.tagmanager.zzp.2 */
    class C15502 implements com.google.android.gms.tagmanager.zzo.zza {
        final /* synthetic */ zzp aEz;

        C15502(zzp com_google_android_gms_tagmanager_zzp) {
            this.aEz = com_google_android_gms_tagmanager_zzp;
        }

        public String zzcea() {
            return this.aEz.zzcea();
        }

        public void zzcec() {
            zzbo.zzdi("Refresh ignored: container loaded as default only.");
        }

        public void zzox(String str) {
            this.aEz.zzox(str);
        }
    }

    interface zza {
        boolean zzb(Container container);
    }

    /* renamed from: com.google.android.gms.tagmanager.zzp.3 */
    class C15513 implements zza {
        private Long aEA;
        final /* synthetic */ boolean aEB;
        final /* synthetic */ zzp aEz;

        C15513(zzp com_google_android_gms_tagmanager_zzp, boolean z) {
            this.aEz = com_google_android_gms_tagmanager_zzp;
            this.aEB = z;
        }

        private long zzceh() {
            if (this.aEA == null) {
                this.aEA = Long.valueOf(this.aEz.aEq.zzcek());
            }
            return this.aEA.longValue();
        }

        public boolean zzb(Container container) {
            return this.aEB ? container.getLastRefreshTime() + zzceh() >= this.aEz.zzaql.currentTimeMillis() : !container.isDefault();
        }
    }

    private class zzb implements zzbn<com.google.android.gms.internal.zzafu.zza> {
        final /* synthetic */ zzp aEz;

        private zzb(zzp com_google_android_gms_tagmanager_zzp) {
            this.aEz = com_google_android_gms_tagmanager_zzp;
        }

        public /* synthetic */ void onSuccess(Object obj) {
            zza((com.google.android.gms.internal.zzafu.zza) obj);
        }

        public void zza(com.google.android.gms.internal.zzafu.zza com_google_android_gms_internal_zzafu_zza) {
            zzj com_google_android_gms_internal_zzai_zzj;
            if (com_google_android_gms_internal_zzafu_zza.aMv != null) {
                com_google_android_gms_internal_zzai_zzj = com_google_android_gms_internal_zzafu_zza.aMv;
            } else {
                com.google.android.gms.internal.zzai.zzf com_google_android_gms_internal_zzai_zzf = com_google_android_gms_internal_zzafu_zza.zzxv;
                com_google_android_gms_internal_zzai_zzj = new zzj();
                com_google_android_gms_internal_zzai_zzj.zzxv = com_google_android_gms_internal_zzai_zzf;
                com_google_android_gms_internal_zzai_zzj.zzxu = null;
                com_google_android_gms_internal_zzai_zzj.zzxw = com_google_android_gms_internal_zzai_zzf.version;
            }
            this.aEz.zza(com_google_android_gms_internal_zzai_zzj, com_google_android_gms_internal_zzafu_zza.aMu, true);
        }

        public void zza(com.google.android.gms.tagmanager.zzbn.zza com_google_android_gms_tagmanager_zzbn_zza) {
            if (!this.aEz.aEu) {
                this.aEz.zzbt(0);
            }
        }

        public void zzcei() {
        }
    }

    private class zzc implements zzbn<zzj> {
        final /* synthetic */ zzp aEz;

        private zzc(zzp com_google_android_gms_tagmanager_zzp) {
            this.aEz = com_google_android_gms_tagmanager_zzp;
        }

        public /* synthetic */ void onSuccess(Object obj) {
            zzb((zzj) obj);
        }

        public void zza(com.google.android.gms.tagmanager.zzbn.zza com_google_android_gms_tagmanager_zzbn_zza) {
            if (com_google_android_gms_tagmanager_zzbn_zza == com.google.android.gms.tagmanager.zzbn.zza.SERVER_UNAVAILABLE_ERROR) {
                this.aEz.aEq.zzcem();
            }
            synchronized (this.aEz) {
                if (!this.aEz.isReady()) {
                    if (this.aEz.aEt != null) {
                        this.aEz.zzc(this.aEz.aEt);
                    } else {
                        this.aEz.zzc(this.aEz.zzel(Status.yc));
                    }
                }
            }
            this.aEz.zzbt(this.aEz.aEq.zzcel());
        }

        public void zzb(zzj com_google_android_gms_internal_zzai_zzj) {
            this.aEz.aEq.zzcen();
            synchronized (this.aEz) {
                if (com_google_android_gms_internal_zzai_zzj.zzxv == null) {
                    if (this.aEz.aEv.zzxv == null) {
                        zzbo.m1698e("Current resource is null; network resource is also null");
                        this.aEz.zzbt(this.aEz.aEq.zzcel());
                        return;
                    }
                    com_google_android_gms_internal_zzai_zzj.zzxv = this.aEz.aEv.zzxv;
                }
                this.aEz.zza(com_google_android_gms_internal_zzai_zzj, this.aEz.zzaql.currentTimeMillis(), false);
                zzbo.m1699v("setting refresh time to current time: " + this.aEz.aEd);
                if (!this.aEz.zzceg()) {
                    this.aEz.zza(com_google_android_gms_internal_zzai_zzj);
                }
            }
        }

        public void zzcei() {
        }
    }

    private class zzd implements com.google.android.gms.tagmanager.zzo.zza {
        final /* synthetic */ zzp aEz;

        private zzd(zzp com_google_android_gms_tagmanager_zzp) {
            this.aEz = com_google_android_gms_tagmanager_zzp;
        }

        public String zzcea() {
            return this.aEz.zzcea();
        }

        public void zzcec() {
            if (this.aEz.aEo.zzagf()) {
                this.aEz.zzbt(0);
            }
        }

        public void zzox(String str) {
            this.aEz.zzox(str);
        }
    }

    zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzf com_google_android_gms_tagmanager_zzp_zzf, zze com_google_android_gms_tagmanager_zzp_zze, zzafv com_google_android_gms_internal_zzafv, com.google.android.gms.common.util.zze com_google_android_gms_common_util_zze, zzcl com_google_android_gms_tagmanager_zzcl, zzq com_google_android_gms_tagmanager_zzq) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.aEk = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzajy = looper;
        this.aDY = str;
        this.aEp = i;
        this.aEr = com_google_android_gms_tagmanager_zzp_zzf;
        this.aEx = com_google_android_gms_tagmanager_zzp_zze;
        this.aEs = com_google_android_gms_internal_zzafv;
        this.aEn = new zzd();
        this.aEv = new zzj();
        this.zzaql = com_google_android_gms_common_util_zze;
        this.aEo = com_google_android_gms_tagmanager_zzcl;
        this.aEq = com_google_android_gms_tagmanager_zzq;
        if (zzceg()) {
            zzox(zzcj.zzcfz().zzcgb());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzt com_google_android_gms_tagmanager_zzt) {
        zzcv com_google_android_gms_tagmanager_zzcv = new zzcv(context, str);
        zzcu com_google_android_gms_tagmanager_zzcu = new zzcu(context, str, com_google_android_gms_tagmanager_zzt);
        Context context2 = context;
        TagManager tagManager2 = tagManager;
        Looper looper2 = looper;
        String str2 = str;
        int i2 = i;
        zzcv com_google_android_gms_tagmanager_zzcv2 = com_google_android_gms_tagmanager_zzcv;
        zzcu com_google_android_gms_tagmanager_zzcu2 = com_google_android_gms_tagmanager_zzcu;
        zzbm com_google_android_gms_tagmanager_zzbm = new zzbm(1, 5, 900000, HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS, "refreshing", zzh.zzayl());
        this(context2, tagManager2, looper2, str2, i2, com_google_android_gms_tagmanager_zzcv2, com_google_android_gms_tagmanager_zzcu2, new zzafv(context), zzh.zzayl(), r5, new zzq(context, str));
        this.aEs.zzqy(com_google_android_gms_tagmanager_zzt.zzcep());
    }

    private synchronized void zza(zzj com_google_android_gms_internal_zzai_zzj) {
        if (this.aEr != null) {
            com.google.android.gms.internal.zzafu.zza com_google_android_gms_internal_zzafu_zza = new com.google.android.gms.internal.zzafu.zza();
            com_google_android_gms_internal_zzafu_zza.aMu = this.aEd;
            com_google_android_gms_internal_zzafu_zza.zzxv = new com.google.android.gms.internal.zzai.zzf();
            com_google_android_gms_internal_zzafu_zza.aMv = com_google_android_gms_internal_zzai_zzj;
            this.aEr.zzb(com_google_android_gms_internal_zzafu_zza);
        }
    }

    private synchronized void zza(zzj com_google_android_gms_internal_zzai_zzj, long j, boolean z) {
        if (z) {
            boolean z2 = this.aEu;
        }
        if (!(isReady() && this.aEt == null)) {
            this.aEv = com_google_android_gms_internal_zzai_zzj;
            this.aEd = j;
            long zzcek = this.aEq.zzcek();
            zzbt(Math.max(0, Math.min(zzcek, (this.aEd + zzcek) - this.zzaql.currentTimeMillis())));
            Container container = new Container(this.mContext, this.aEk.getDataLayer(), this.aDY, j, com_google_android_gms_internal_zzai_zzj);
            if (this.aEt == null) {
                this.aEt = new zzo(this.aEk, this.zzajy, container, this.aEn);
            } else {
                this.aEt.zza(container);
            }
            if (!isReady() && this.aEy.zzb(container)) {
                zzc(this.aEt);
            }
        }
    }

    private synchronized void zzbt(long j) {
        if (this.aEx == null) {
            zzbo.zzdi("Refresh requested, but no network load scheduler.");
        } else {
            this.aEx.zzf(j, this.aEv.zzxw);
        }
    }

    private boolean zzceg() {
        zzcj zzcfz = zzcj.zzcfz();
        return (zzcfz.zzcga() == zza.CONTAINER || zzcfz.zzcga() == zza.CONTAINER_DEBUG) && this.aDY.equals(zzcfz.getContainerId());
    }

    private void zzcm(boolean z) {
        this.aEr.zza(new zzb());
        this.aEx.zza(new zzc());
        com.google.android.gms.internal.zzafw.zzc zzzz = this.aEr.zzzz(this.aEp);
        if (zzzz != null) {
            this.aEt = new zzo(this.aEk, this.zzajy, new Container(this.mContext, this.aEk.getDataLayer(), this.aDY, 0, zzzz), this.aEn);
        }
        this.aEy = new C15513(this, z);
        if (zzceg()) {
            this.aEx.zzf(0, BuildConfig.FLAVOR);
        } else {
            this.aEr.zzcej();
        }
    }

    protected /* synthetic */ Result zzc(Status status) {
        return zzel(status);
    }

    synchronized String zzcea() {
        return this.aEw;
    }

    public void zzced() {
        com.google.android.gms.internal.zzafw.zzc zzzz = this.aEr.zzzz(this.aEp);
        if (zzzz != null) {
            zzc(new zzo(this.aEk, this.zzajy, new Container(this.mContext, this.aEk.getDataLayer(), this.aDY, 0, zzzz), new C15502(this)));
        } else {
            String str = "Default was requested, but no default container was found";
            zzbo.m1698e(str);
            zzc(zzel(new Status(10, str, null)));
        }
        this.aEx = null;
        this.aEr = null;
    }

    public void zzcee() {
        zzcm(false);
    }

    public void zzcef() {
        zzcm(true);
    }

    protected ContainerHolder zzel(Status status) {
        if (this.aEt != null) {
            return this.aEt;
        }
        if (status == Status.yc) {
            zzbo.m1698e("timer expired: setting result to failure");
        }
        return new zzo(status);
    }

    synchronized void zzox(String str) {
        this.aEw = str;
        if (this.aEx != null) {
            this.aEx.zzpa(str);
        }
    }
}
