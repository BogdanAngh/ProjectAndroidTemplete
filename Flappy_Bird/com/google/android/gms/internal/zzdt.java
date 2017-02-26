package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@zzgd
public class zzdt {
    private final Context mContext;
    private final VersionInfoParcel zzoM;
    private final Object zzqt;
    private final String zzwO;
    private zzb<zzbb> zzwP;
    private zzb<zzbb> zzwQ;
    private zze zzwR;
    private int zzwS;

    /* renamed from: com.google.android.gms.internal.zzdt.1 */
    class C02061 implements Runnable {
        final /* synthetic */ zze zzwT;
        final /* synthetic */ zzdt zzwU;

        /* renamed from: com.google.android.gms.internal.zzdt.1.4 */
        class C02054 extends TimerTask {
            final /* synthetic */ zzbb zzwV;
            final /* synthetic */ C02061 zzwW;

            /* renamed from: com.google.android.gms.internal.zzdt.1.4.1 */
            class C02041 implements Runnable {
                final /* synthetic */ C02054 zzxa;

                C02041(C02054 c02054) {
                    this.zzxa = c02054;
                }

                public void run() {
                    this.zzxa.zzwV.destroy();
                }
            }

            C02054(C02061 c02061, zzbb com_google_android_gms_internal_zzbb) {
                this.zzwW = c02061;
                this.zzwV = com_google_android_gms_internal_zzbb;
            }

            public void run() {
                synchronized (this.zzwW.zzwU.zzqt) {
                    if (this.zzwW.zzwT.getStatus() == -1 || this.zzwW.zzwT.getStatus() == 1) {
                        return;
                    }
                    this.zzwW.zzwT.reject();
                    zzhl.runOnUiThread(new C02041(this));
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("Could not receive loaded message in a timely manner. Rejecting.");
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdt.1.1 */
        class C04531 implements com.google.android.gms.internal.zzbb.zza {
            final /* synthetic */ zzbb zzwV;
            final /* synthetic */ C02061 zzwW;

            /* renamed from: com.google.android.gms.internal.zzdt.1.1.1 */
            class C02031 extends TimerTask {
                final /* synthetic */ C04531 zzwX;

                /* renamed from: com.google.android.gms.internal.zzdt.1.1.1.1 */
                class C02021 implements Runnable {
                    final /* synthetic */ C02031 zzwY;

                    C02021(C02031 c02031) {
                        this.zzwY = c02031;
                    }

                    public void run() {
                        this.zzwY.zzwX.zzwV.destroy();
                    }
                }

                C02031(C04531 c04531) {
                    this.zzwX = c04531;
                }

                public void run() {
                    synchronized (this.zzwX.zzwW.zzwU.zzqt) {
                        if (this.zzwX.zzwW.zzwT.getStatus() == -1 || this.zzwX.zzwW.zzwT.getStatus() == 1) {
                            return;
                        }
                        this.zzwX.zzwW.zzwT.reject();
                        zzhl.runOnUiThread(new C02021(this));
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("Could not receive loaded message in a timely manner. Rejecting.");
                    }
                }
            }

            C04531(C02061 c02061, zzbb com_google_android_gms_internal_zzbb) {
                this.zzwW = c02061;
                this.zzwV = com_google_android_gms_internal_zzbb;
            }

            public void zzcf() {
                new Timer().schedule(new C02031(this), (long) zza.zzxd);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdt.1.2 */
        class C04542 implements zzdg {
            final /* synthetic */ zzbb zzwV;
            final /* synthetic */ C02061 zzwW;

            C04542(C02061 c02061, zzbb com_google_android_gms_internal_zzbb) {
                this.zzwW = c02061;
                this.zzwV = com_google_android_gms_internal_zzbb;
            }

            public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
                synchronized (this.zzwW.zzwU.zzqt) {
                    if (this.zzwW.zzwT.getStatus() == -1 || this.zzwW.zzwT.getStatus() == 1) {
                        return;
                    }
                    this.zzwW.zzwU.zzwS = 0;
                    this.zzwW.zzwU.zzwP.zzc(this.zzwV);
                    this.zzwW.zzwT.zzg(this.zzwV);
                    this.zzwW.zzwU.zzwR = this.zzwW.zzwT;
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("Successfully loaded JS Engine.");
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdt.1.3 */
        class C04553 implements zzdg {
            final /* synthetic */ zzbb zzwV;
            final /* synthetic */ C02061 zzwW;
            final /* synthetic */ zzhr zzwZ;

            C04553(C02061 c02061, zzbb com_google_android_gms_internal_zzbb, zzhr com_google_android_gms_internal_zzhr) {
                this.zzwW = c02061;
                this.zzwV = com_google_android_gms_internal_zzbb;
                this.zzwZ = com_google_android_gms_internal_zzhr;
            }

            public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
                synchronized (this.zzwW.zzwU.zzqt) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaA("JS Engine is requesting an update");
                    if (this.zzwW.zzwU.zzwS == 0) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaA("Starting reload.");
                        this.zzwW.zzwU.zzwS = 2;
                        this.zzwW.zzwU.zzdT();
                    }
                    this.zzwV.zzb("/requestReload", (zzdg) this.zzwZ.get());
                }
            }
        }

        C02061(zzdt com_google_android_gms_internal_zzdt, zze com_google_android_gms_internal_zzdt_zze) {
            this.zzwU = com_google_android_gms_internal_zzdt;
            this.zzwT = com_google_android_gms_internal_zzdt_zze;
        }

        public void run() {
            zzbb zza = this.zzwU.zza(this.zzwU.mContext, this.zzwU.zzoM);
            zza.zza(new C04531(this, zza));
            zza.zza("/jsLoaded", new C04542(this, zza));
            zzhr com_google_android_gms_internal_zzhr = new zzhr();
            zzdg c04553 = new C04553(this, zza, com_google_android_gms_internal_zzhr);
            com_google_android_gms_internal_zzhr.set(c04553);
            zza.zza("/requestReload", c04553);
            if (this.zzwU.zzwO.endsWith(".js")) {
                zza.zzr(this.zzwU.zzwO);
            } else if (this.zzwU.zzwO.startsWith("<html>")) {
                zza.zzt(this.zzwU.zzwO);
            } else {
                zza.zzs(this.zzwU.zzwO);
            }
            new Timer().schedule(new C02054(this, zza), (long) zza.zzxc);
        }
    }

    static class zza {
        static int zzxc;
        static int zzxd;

        static {
            zzxc = 60000;
            zzxd = StatusCodes.AUTH_DISABLED;
        }
    }

    public interface zzb<T> {
        void zzc(T t);
    }

    /* renamed from: com.google.android.gms.internal.zzdt.2 */
    class C04562 implements com.google.android.gms.internal.zzhx.zzc<zzbb> {
        final /* synthetic */ zzdt zzwU;
        final /* synthetic */ zze zzxb;

        C04562(zzdt com_google_android_gms_internal_zzdt, zze com_google_android_gms_internal_zzdt_zze) {
            this.zzwU = com_google_android_gms_internal_zzdt;
            this.zzxb = com_google_android_gms_internal_zzdt_zze;
        }

        public void zza(zzbb com_google_android_gms_internal_zzbb) {
            synchronized (this.zzwU.zzqt) {
                this.zzwU.zzwS = 0;
                if (!(this.zzwU.zzwR == null || this.zzxb == this.zzwU.zzwR)) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("New JS engine is loaded, marking previous one as destroyable.");
                    this.zzwU.zzwR.zzdX();
                }
                this.zzwU.zzwR = this.zzxb;
            }
        }

        public /* synthetic */ void zzc(Object obj) {
            zza((zzbb) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdt.3 */
    class C04573 implements com.google.android.gms.internal.zzhx.zza {
        final /* synthetic */ zzdt zzwU;
        final /* synthetic */ zze zzxb;

        C04573(zzdt com_google_android_gms_internal_zzdt, zze com_google_android_gms_internal_zzdt_zze) {
            this.zzwU = com_google_android_gms_internal_zzdt;
            this.zzxb = com_google_android_gms_internal_zzdt_zze;
        }

        public void run() {
            synchronized (this.zzwU.zzqt) {
                this.zzwU.zzwS = 1;
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Failed loading new engine. Marking new engine destroyable.");
                this.zzxb.zzdX();
            }
        }
    }

    public static class zzc<T> implements zzb<T> {
        public void zzc(T t) {
        }
    }

    public static class zzd extends zzhy<zzbe> {
        private final Object zzqt;
        private final zze zzxe;
        private boolean zzxf;

        /* renamed from: com.google.android.gms.internal.zzdt.zzd.1 */
        class C04581 implements com.google.android.gms.internal.zzhx.zzc<zzbe> {
            final /* synthetic */ zzd zzxg;

            C04581(zzd com_google_android_gms_internal_zzdt_zzd) {
                this.zzxg = com_google_android_gms_internal_zzdt_zzd;
            }

            public void zzb(zzbe com_google_android_gms_internal_zzbe) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Ending javascript session.");
                ((zzbf) com_google_android_gms_internal_zzbe).zzcg();
            }

            public /* synthetic */ void zzc(Object obj) {
                zzb((zzbe) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdt.zzd.2 */
        class C04592 implements com.google.android.gms.internal.zzhx.zzc<zzbe> {
            final /* synthetic */ zzd zzxg;

            C04592(zzd com_google_android_gms_internal_zzdt_zzd) {
                this.zzxg = com_google_android_gms_internal_zzdt_zzd;
            }

            public void zzb(zzbe com_google_android_gms_internal_zzbe) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Releasing engine reference.");
                this.zzxg.zzxe.zzdW();
            }

            public /* synthetic */ void zzc(Object obj) {
                zzb((zzbe) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdt.zzd.3 */
        class C04603 implements com.google.android.gms.internal.zzhx.zza {
            final /* synthetic */ zzd zzxg;

            C04603(zzd com_google_android_gms_internal_zzdt_zzd) {
                this.zzxg = com_google_android_gms_internal_zzdt_zzd;
            }

            public void run() {
                this.zzxg.zzxe.zzdW();
            }
        }

        public zzd(zze com_google_android_gms_internal_zzdt_zze) {
            this.zzqt = new Object();
            this.zzxe = com_google_android_gms_internal_zzdt_zze;
        }

        public void release() {
            synchronized (this.zzqt) {
                if (this.zzxf) {
                    return;
                }
                this.zzxf = true;
                zza(new C04581(this), new com.google.android.gms.internal.zzhx.zzb());
                zza(new C04592(this), new C04603(this));
            }
        }
    }

    public static class zze extends zzhy<zzbb> {
        private final Object zzqt;
        private zzb<zzbb> zzwQ;
        private boolean zzxh;
        private int zzxi;

        /* renamed from: com.google.android.gms.internal.zzdt.zze.1 */
        class C04611 implements com.google.android.gms.internal.zzhx.zzc<zzbb> {
            final /* synthetic */ zzd zzxj;
            final /* synthetic */ zze zzxk;

            C04611(zze com_google_android_gms_internal_zzdt_zze, zzd com_google_android_gms_internal_zzdt_zzd) {
                this.zzxk = com_google_android_gms_internal_zzdt_zze;
                this.zzxj = com_google_android_gms_internal_zzdt_zzd;
            }

            public void zza(zzbb com_google_android_gms_internal_zzbb) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Getting a new session for JS Engine.");
                this.zzxj.zzg(com_google_android_gms_internal_zzbb.zzce());
            }

            public /* synthetic */ void zzc(Object obj) {
                zza((zzbb) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdt.zze.2 */
        class C04622 implements com.google.android.gms.internal.zzhx.zza {
            final /* synthetic */ zzd zzxj;
            final /* synthetic */ zze zzxk;

            C04622(zze com_google_android_gms_internal_zzdt_zze, zzd com_google_android_gms_internal_zzdt_zzd) {
                this.zzxk = com_google_android_gms_internal_zzdt_zze;
                this.zzxj = com_google_android_gms_internal_zzdt_zzd;
            }

            public void run() {
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Rejecting reference for JS Engine.");
                this.zzxj.reject();
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdt.zze.3 */
        class C04633 implements com.google.android.gms.internal.zzhx.zzc<zzbb> {
            final /* synthetic */ zze zzxk;

            /* renamed from: com.google.android.gms.internal.zzdt.zze.3.1 */
            class C02071 implements Runnable {
                final /* synthetic */ zzbb zzra;
                final /* synthetic */ C04633 zzxl;

                C02071(C04633 c04633, zzbb com_google_android_gms_internal_zzbb) {
                    this.zzxl = c04633;
                    this.zzra = com_google_android_gms_internal_zzbb;
                }

                public void run() {
                    this.zzxl.zzxk.zzwQ.zzc(this.zzra);
                    this.zzra.destroy();
                }
            }

            C04633(zze com_google_android_gms_internal_zzdt_zze) {
                this.zzxk = com_google_android_gms_internal_zzdt_zze;
            }

            public void zza(zzbb com_google_android_gms_internal_zzbb) {
                zzhl.runOnUiThread(new C02071(this, com_google_android_gms_internal_zzbb));
            }

            public /* synthetic */ void zzc(Object obj) {
                zza((zzbb) obj);
            }
        }

        public zze(zzb<zzbb> com_google_android_gms_internal_zzdt_zzb_com_google_android_gms_internal_zzbb) {
            this.zzqt = new Object();
            this.zzwQ = com_google_android_gms_internal_zzdt_zzb_com_google_android_gms_internal_zzbb;
            this.zzxh = false;
            this.zzxi = 0;
        }

        public zzd zzdV() {
            zzd com_google_android_gms_internal_zzdt_zzd = new zzd(this);
            synchronized (this.zzqt) {
                zza(new C04611(this, com_google_android_gms_internal_zzdt_zzd), new C04622(this, com_google_android_gms_internal_zzdt_zzd));
                zzu.zzU(this.zzxi >= 0);
                this.zzxi++;
            }
            return com_google_android_gms_internal_zzdt_zzd;
        }

        protected void zzdW() {
            boolean z = true;
            synchronized (this.zzqt) {
                if (this.zzxi < 1) {
                    z = false;
                }
                zzu.zzU(z);
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Releasing 1 reference for JS Engine");
                this.zzxi--;
                zzdY();
            }
        }

        public void zzdX() {
            boolean z = true;
            synchronized (this.zzqt) {
                if (this.zzxi < 0) {
                    z = false;
                }
                zzu.zzU(z);
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Releasing root reference. JS Engine will be destroyed once other references are released.");
                this.zzxh = true;
                zzdY();
            }
        }

        protected void zzdY() {
            synchronized (this.zzqt) {
                zzu.zzU(this.zzxi >= 0);
                if (this.zzxh && this.zzxi == 0) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("No reference is left (including root). Cleaning up engine.");
                    zza(new C04633(this), new com.google.android.gms.internal.zzhx.zzb());
                } else {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("There are still references to the engine. Not destroying.");
                }
            }
        }
    }

    public zzdt(Context context, VersionInfoParcel versionInfoParcel, String str) {
        this.zzqt = new Object();
        this.zzwS = 1;
        this.zzwO = str;
        this.mContext = context.getApplicationContext();
        this.zzoM = versionInfoParcel;
        this.zzwP = new zzc();
        this.zzwQ = new zzc();
    }

    public zzdt(Context context, VersionInfoParcel versionInfoParcel, String str, zzb<zzbb> com_google_android_gms_internal_zzdt_zzb_com_google_android_gms_internal_zzbb, zzb<zzbb> com_google_android_gms_internal_zzdt_zzb_com_google_android_gms_internal_zzbb2) {
        this(context, versionInfoParcel, str);
        this.zzwP = com_google_android_gms_internal_zzdt_zzb_com_google_android_gms_internal_zzbb;
        this.zzwQ = com_google_android_gms_internal_zzdt_zzb_com_google_android_gms_internal_zzbb2;
    }

    private zze zzdS() {
        zze com_google_android_gms_internal_zzdt_zze = new zze(this.zzwQ);
        zzhl.runOnUiThread(new C02061(this, com_google_android_gms_internal_zzdt_zze));
        return com_google_android_gms_internal_zzdt_zze;
    }

    protected zzbb zza(Context context, VersionInfoParcel versionInfoParcel) {
        return new zzbd(context, versionInfoParcel);
    }

    protected zze zzdT() {
        zze zzdS = zzdS();
        zzdS.zza(new C04562(this, zzdS), new C04573(this, zzdS));
        return zzdS;
    }

    public zzd zzdU() {
        zzd zzdV;
        synchronized (this.zzqt) {
            if (this.zzwR == null || this.zzwR.getStatus() == -1) {
                this.zzwS = 2;
                this.zzwR = zzdT();
                zzdV = this.zzwR.zzdV();
            } else if (this.zzwS == 0) {
                zzdV = this.zzwR.zzdV();
            } else if (this.zzwS == 1) {
                this.zzwS = 2;
                zzdT();
                zzdV = this.zzwR.zzdV();
            } else if (this.zzwS == 2) {
                zzdV = this.zzwR.zzdV();
            } else {
                zzdV = this.zzwR.zzdV();
            }
        }
        return zzdV;
    }
}
