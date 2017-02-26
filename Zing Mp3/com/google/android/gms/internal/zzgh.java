package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.exoplayer.chunk.FormatEvaluator.AdaptiveEvaluator;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.zzaa;
import java.util.Map;

@zzji
public class zzgh {
    private final Context mContext;
    private final Object zzako;
    private final VersionInfoParcel zzanu;
    private final String zzbtp;
    private zzlg<zzge> zzbtq;
    private zzlg<zzge> zzbtr;
    @Nullable
    private zzd zzbts;
    private int zzbtt;

    /* renamed from: com.google.android.gms.internal.zzgh.1 */
    class C13491 implements Runnable {
        final /* synthetic */ zzav zzbtu;
        final /* synthetic */ zzd zzbtv;
        final /* synthetic */ zzgh zzbtw;

        /* renamed from: com.google.android.gms.internal.zzgh.1.1 */
        class C13441 implements com.google.android.gms.internal.zzge.zza {
            final /* synthetic */ zzge zzbtx;
            final /* synthetic */ C13491 zzbty;

            /* renamed from: com.google.android.gms.internal.zzgh.1.1.1 */
            class C13431 implements Runnable {
                final /* synthetic */ C13441 zzbtz;

                /* renamed from: com.google.android.gms.internal.zzgh.1.1.1.1 */
                class C13421 implements Runnable {
                    final /* synthetic */ C13431 zzbua;

                    C13421(C13431 c13431) {
                        this.zzbua = c13431;
                    }

                    public void run() {
                        this.zzbua.zzbtz.zzbtx.destroy();
                    }
                }

                C13431(C13441 c13441) {
                    this.zzbtz = c13441;
                }

                public void run() {
                    synchronized (this.zzbtz.zzbty.zzbtw.zzako) {
                        if (this.zzbtz.zzbty.zzbtv.getStatus() == -1 || this.zzbtz.zzbty.zzbtv.getStatus() == 1) {
                            return;
                        }
                        this.zzbtz.zzbty.zzbtv.reject();
                        zzu.zzgm().runOnUiThread(new C13421(this));
                        zzkx.m1697v("Could not receive loaded message in a timely manner. Rejecting.");
                    }
                }
            }

            C13441(C13491 c13491, zzge com_google_android_gms_internal_zzge) {
                this.zzbty = c13491;
                this.zzbtx = com_google_android_gms_internal_zzge;
            }

            public void zznx() {
                zzlb.zzcvl.postDelayed(new C13431(this), (long) zza.zzbuf);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgh.1.2 */
        class C13452 implements zzfe {
            final /* synthetic */ zzge zzbtx;
            final /* synthetic */ C13491 zzbty;

            C13452(C13491 c13491, zzge com_google_android_gms_internal_zzge) {
                this.zzbty = c13491;
                this.zzbtx = com_google_android_gms_internal_zzge;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                synchronized (this.zzbty.zzbtw.zzako) {
                    if (this.zzbty.zzbtv.getStatus() == -1 || this.zzbty.zzbtv.getStatus() == 1) {
                        return;
                    }
                    this.zzbty.zzbtw.zzbtt = 0;
                    this.zzbty.zzbtw.zzbtq.zzd(this.zzbtx);
                    this.zzbty.zzbtv.zzg(this.zzbtx);
                    this.zzbty.zzbtw.zzbts = this.zzbty.zzbtv;
                    zzkx.m1697v("Successfully loaded JS Engine.");
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgh.1.3 */
        class C13463 implements zzfe {
            final /* synthetic */ zzge zzbtx;
            final /* synthetic */ C13491 zzbty;
            final /* synthetic */ zzln zzbub;

            C13463(C13491 c13491, zzge com_google_android_gms_internal_zzge, zzln com_google_android_gms_internal_zzln) {
                this.zzbty = c13491;
                this.zzbtx = com_google_android_gms_internal_zzge;
                this.zzbub = com_google_android_gms_internal_zzln;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                synchronized (this.zzbty.zzbtw.zzako) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzdh("JS Engine is requesting an update");
                    if (this.zzbty.zzbtw.zzbtt == 0) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzdh("Starting reload.");
                        this.zzbty.zzbtw.zzbtt = 2;
                        this.zzbty.zzbtw.zzb(this.zzbty.zzbtu);
                    }
                    this.zzbtx.zzb("/requestReload", (zzfe) this.zzbub.get());
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgh.1.4 */
        class C13484 implements Runnable {
            final /* synthetic */ zzge zzbtx;
            final /* synthetic */ C13491 zzbty;

            /* renamed from: com.google.android.gms.internal.zzgh.1.4.1 */
            class C13471 implements Runnable {
                final /* synthetic */ C13484 zzbuc;

                C13471(C13484 c13484) {
                    this.zzbuc = c13484;
                }

                public void run() {
                    this.zzbuc.zzbtx.destroy();
                }
            }

            C13484(C13491 c13491, zzge com_google_android_gms_internal_zzge) {
                this.zzbty = c13491;
                this.zzbtx = com_google_android_gms_internal_zzge;
            }

            public void run() {
                synchronized (this.zzbty.zzbtw.zzako) {
                    if (this.zzbty.zzbtv.getStatus() == -1 || this.zzbty.zzbtv.getStatus() == 1) {
                        return;
                    }
                    this.zzbty.zzbtv.reject();
                    zzu.zzgm().runOnUiThread(new C13471(this));
                    zzkx.m1697v("Could not receive loaded message in a timely manner. Rejecting.");
                }
            }
        }

        C13491(zzgh com_google_android_gms_internal_zzgh, zzav com_google_android_gms_internal_zzav, zzd com_google_android_gms_internal_zzgh_zzd) {
            this.zzbtw = com_google_android_gms_internal_zzgh;
            this.zzbtu = com_google_android_gms_internal_zzav;
            this.zzbtv = com_google_android_gms_internal_zzgh_zzd;
        }

        public void run() {
            zzge zza = this.zzbtw.zza(this.zzbtw.mContext, this.zzbtw.zzanu, this.zzbtu);
            zza.zza(new C13441(this, zza));
            zza.zza("/jsLoaded", new C13452(this, zza));
            zzln com_google_android_gms_internal_zzln = new zzln();
            zzfe c13463 = new C13463(this, zza, com_google_android_gms_internal_zzln);
            com_google_android_gms_internal_zzln.set(c13463);
            zza.zza("/requestReload", c13463);
            if (this.zzbtw.zzbtp.endsWith(".js")) {
                zza.zzbo(this.zzbtw.zzbtp);
            } else if (this.zzbtw.zzbtp.startsWith("<html>")) {
                zza.zzbq(this.zzbtw.zzbtp);
            } else {
                zza.zzbp(this.zzbtw.zzbtp);
            }
            zzlb.zzcvl.postDelayed(new C13484(this, zza), (long) zza.zzbue);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgh.2 */
    class C13502 implements com.google.android.gms.internal.zzlw.zzc<zzge> {
        final /* synthetic */ zzgh zzbtw;
        final /* synthetic */ zzd zzbud;

        C13502(zzgh com_google_android_gms_internal_zzgh, zzd com_google_android_gms_internal_zzgh_zzd) {
            this.zzbtw = com_google_android_gms_internal_zzgh;
            this.zzbud = com_google_android_gms_internal_zzgh_zzd;
        }

        public void zza(zzge com_google_android_gms_internal_zzge) {
            synchronized (this.zzbtw.zzako) {
                this.zzbtw.zzbtt = 0;
                if (!(this.zzbtw.zzbts == null || this.zzbud == this.zzbtw.zzbts)) {
                    zzkx.m1697v("New JS engine is loaded, marking previous one as destroyable.");
                    this.zzbtw.zzbts.zzob();
                }
                this.zzbtw.zzbts = this.zzbud;
            }
        }

        public /* synthetic */ void zzd(Object obj) {
            zza((zzge) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgh.3 */
    class C13513 implements com.google.android.gms.internal.zzlw.zza {
        final /* synthetic */ zzgh zzbtw;
        final /* synthetic */ zzd zzbud;

        C13513(zzgh com_google_android_gms_internal_zzgh, zzd com_google_android_gms_internal_zzgh_zzd) {
            this.zzbtw = com_google_android_gms_internal_zzgh;
            this.zzbud = com_google_android_gms_internal_zzgh_zzd;
        }

        public void run() {
            synchronized (this.zzbtw.zzako) {
                this.zzbtw.zzbtt = 1;
                zzkx.m1697v("Failed loading new engine. Marking new engine destroyable.");
                this.zzbud.zzob();
            }
        }
    }

    static class zza {
        static int zzbue;
        static int zzbuf;

        static {
            zzbue = 60000;
            zzbuf = AdaptiveEvaluator.DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS;
        }
    }

    public static class zzb<T> implements zzlg<T> {
        public void zzd(T t) {
        }
    }

    public static class zzc extends zzlx<zzgi> {
        private final Object zzako;
        private final zzd zzbug;
        private boolean zzbuh;

        /* renamed from: com.google.android.gms.internal.zzgh.zzc.1 */
        class C13521 implements com.google.android.gms.internal.zzlw.zzc<zzgi> {
            final /* synthetic */ zzc zzbui;

            C13521(zzc com_google_android_gms_internal_zzgh_zzc) {
                this.zzbui = com_google_android_gms_internal_zzgh_zzc;
            }

            public void zzb(zzgi com_google_android_gms_internal_zzgi) {
                zzkx.m1697v("Ending javascript session.");
                ((zzgj) com_google_android_gms_internal_zzgi).zzod();
            }

            public /* synthetic */ void zzd(Object obj) {
                zzb((zzgi) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgh.zzc.2 */
        class C13532 implements com.google.android.gms.internal.zzlw.zzc<zzgi> {
            final /* synthetic */ zzc zzbui;

            C13532(zzc com_google_android_gms_internal_zzgh_zzc) {
                this.zzbui = com_google_android_gms_internal_zzgh_zzc;
            }

            public void zzb(zzgi com_google_android_gms_internal_zzgi) {
                zzkx.m1697v("Releasing engine reference.");
                this.zzbui.zzbug.zzoa();
            }

            public /* synthetic */ void zzd(Object obj) {
                zzb((zzgi) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgh.zzc.3 */
        class C13543 implements com.google.android.gms.internal.zzlw.zza {
            final /* synthetic */ zzc zzbui;

            C13543(zzc com_google_android_gms_internal_zzgh_zzc) {
                this.zzbui = com_google_android_gms_internal_zzgh_zzc;
            }

            public void run() {
                this.zzbui.zzbug.zzoa();
            }
        }

        public zzc(zzd com_google_android_gms_internal_zzgh_zzd) {
            this.zzako = new Object();
            this.zzbug = com_google_android_gms_internal_zzgh_zzd;
        }

        public void release() {
            synchronized (this.zzako) {
                if (this.zzbuh) {
                    return;
                }
                this.zzbuh = true;
                zza(new C13521(this), new com.google.android.gms.internal.zzlw.zzb());
                zza(new C13532(this), new C13543(this));
            }
        }
    }

    public static class zzd extends zzlx<zzge> {
        private final Object zzako;
        private zzlg<zzge> zzbtr;
        private boolean zzbuj;
        private int zzbuk;

        /* renamed from: com.google.android.gms.internal.zzgh.zzd.1 */
        class C13551 implements com.google.android.gms.internal.zzlw.zzc<zzge> {
            final /* synthetic */ zzc zzbul;
            final /* synthetic */ zzd zzbum;

            C13551(zzd com_google_android_gms_internal_zzgh_zzd, zzc com_google_android_gms_internal_zzgh_zzc) {
                this.zzbum = com_google_android_gms_internal_zzgh_zzd;
                this.zzbul = com_google_android_gms_internal_zzgh_zzc;
            }

            public void zza(zzge com_google_android_gms_internal_zzge) {
                zzkx.m1697v("Getting a new session for JS Engine.");
                this.zzbul.zzg(com_google_android_gms_internal_zzge.zznw());
            }

            public /* synthetic */ void zzd(Object obj) {
                zza((zzge) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgh.zzd.2 */
        class C13562 implements com.google.android.gms.internal.zzlw.zza {
            final /* synthetic */ zzc zzbul;
            final /* synthetic */ zzd zzbum;

            C13562(zzd com_google_android_gms_internal_zzgh_zzd, zzc com_google_android_gms_internal_zzgh_zzc) {
                this.zzbum = com_google_android_gms_internal_zzgh_zzd;
                this.zzbul = com_google_android_gms_internal_zzgh_zzc;
            }

            public void run() {
                zzkx.m1697v("Rejecting reference for JS Engine.");
                this.zzbul.reject();
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgh.zzd.3 */
        class C13583 implements com.google.android.gms.internal.zzlw.zzc<zzge> {
            final /* synthetic */ zzd zzbum;

            /* renamed from: com.google.android.gms.internal.zzgh.zzd.3.1 */
            class C13571 implements Runnable {
                final /* synthetic */ zzge zzbun;
                final /* synthetic */ C13583 zzbuo;

                C13571(C13583 c13583, zzge com_google_android_gms_internal_zzge) {
                    this.zzbuo = c13583;
                    this.zzbun = com_google_android_gms_internal_zzge;
                }

                public void run() {
                    this.zzbuo.zzbum.zzbtr.zzd(this.zzbun);
                    this.zzbun.destroy();
                }
            }

            C13583(zzd com_google_android_gms_internal_zzgh_zzd) {
                this.zzbum = com_google_android_gms_internal_zzgh_zzd;
            }

            public void zza(zzge com_google_android_gms_internal_zzge) {
                zzu.zzgm().runOnUiThread(new C13571(this, com_google_android_gms_internal_zzge));
            }

            public /* synthetic */ void zzd(Object obj) {
                zza((zzge) obj);
            }
        }

        public zzd(zzlg<zzge> com_google_android_gms_internal_zzlg_com_google_android_gms_internal_zzge) {
            this.zzako = new Object();
            this.zzbtr = com_google_android_gms_internal_zzlg_com_google_android_gms_internal_zzge;
            this.zzbuj = false;
            this.zzbuk = 0;
        }

        public zzc zznz() {
            zzc com_google_android_gms_internal_zzgh_zzc = new zzc(this);
            synchronized (this.zzako) {
                zza(new C13551(this, com_google_android_gms_internal_zzgh_zzc), new C13562(this, com_google_android_gms_internal_zzgh_zzc));
                zzaa.zzbs(this.zzbuk >= 0);
                this.zzbuk++;
            }
            return com_google_android_gms_internal_zzgh_zzc;
        }

        protected void zzoa() {
            boolean z = true;
            synchronized (this.zzako) {
                if (this.zzbuk < 1) {
                    z = false;
                }
                zzaa.zzbs(z);
                zzkx.m1697v("Releasing 1 reference for JS Engine");
                this.zzbuk--;
                zzoc();
            }
        }

        public void zzob() {
            boolean z = true;
            synchronized (this.zzako) {
                if (this.zzbuk < 0) {
                    z = false;
                }
                zzaa.zzbs(z);
                zzkx.m1697v("Releasing root reference. JS Engine will be destroyed once other references are released.");
                this.zzbuj = true;
                zzoc();
            }
        }

        protected void zzoc() {
            synchronized (this.zzako) {
                zzaa.zzbs(this.zzbuk >= 0);
                if (this.zzbuj && this.zzbuk == 0) {
                    zzkx.m1697v("No reference is left (including root). Cleaning up engine.");
                    zza(new C13583(this), new com.google.android.gms.internal.zzlw.zzb());
                } else {
                    zzkx.m1697v("There are still references to the engine. Not destroying.");
                }
            }
        }
    }

    public static class zze extends zzlx<zzgi> {
        private zzc zzbup;

        public zze(zzc com_google_android_gms_internal_zzgh_zzc) {
            this.zzbup = com_google_android_gms_internal_zzgh_zzc;
        }

        public void finalize() {
            this.zzbup.release();
            this.zzbup = null;
        }

        public int getStatus() {
            return this.zzbup.getStatus();
        }

        public void reject() {
            this.zzbup.reject();
        }

        public void zza(com.google.android.gms.internal.zzlw.zzc<zzgi> com_google_android_gms_internal_zzlw_zzc_com_google_android_gms_internal_zzgi, com.google.android.gms.internal.zzlw.zza com_google_android_gms_internal_zzlw_zza) {
            this.zzbup.zza(com_google_android_gms_internal_zzlw_zzc_com_google_android_gms_internal_zzgi, com_google_android_gms_internal_zzlw_zza);
        }

        public void zzf(zzgi com_google_android_gms_internal_zzgi) {
            this.zzbup.zzg(com_google_android_gms_internal_zzgi);
        }

        public /* synthetic */ void zzg(Object obj) {
            zzf((zzgi) obj);
        }
    }

    public zzgh(Context context, VersionInfoParcel versionInfoParcel, String str) {
        this.zzako = new Object();
        this.zzbtt = 1;
        this.zzbtp = str;
        this.mContext = context.getApplicationContext();
        this.zzanu = versionInfoParcel;
        this.zzbtq = new zzb();
        this.zzbtr = new zzb();
    }

    public zzgh(Context context, VersionInfoParcel versionInfoParcel, String str, zzlg<zzge> com_google_android_gms_internal_zzlg_com_google_android_gms_internal_zzge, zzlg<zzge> com_google_android_gms_internal_zzlg_com_google_android_gms_internal_zzge2) {
        this(context, versionInfoParcel, str);
        this.zzbtq = com_google_android_gms_internal_zzlg_com_google_android_gms_internal_zzge;
        this.zzbtr = com_google_android_gms_internal_zzlg_com_google_android_gms_internal_zzge2;
    }

    private zzd zza(@Nullable zzav com_google_android_gms_internal_zzav) {
        zzd com_google_android_gms_internal_zzgh_zzd = new zzd(this.zzbtr);
        zzu.zzgm().runOnUiThread(new C13491(this, com_google_android_gms_internal_zzav, com_google_android_gms_internal_zzgh_zzd));
        return com_google_android_gms_internal_zzgh_zzd;
    }

    protected zzge zza(Context context, VersionInfoParcel versionInfoParcel, @Nullable zzav com_google_android_gms_internal_zzav) {
        return new zzgg(context, versionInfoParcel, com_google_android_gms_internal_zzav, null);
    }

    protected zzd zzb(@Nullable zzav com_google_android_gms_internal_zzav) {
        zzd zza = zza(com_google_android_gms_internal_zzav);
        zza.zza(new C13502(this, zza), new C13513(this, zza));
        return zza;
    }

    public zzc zzc(@Nullable zzav com_google_android_gms_internal_zzav) {
        zzc zznz;
        synchronized (this.zzako) {
            if (this.zzbts == null || this.zzbts.getStatus() == -1) {
                this.zzbtt = 2;
                this.zzbts = zzb(com_google_android_gms_internal_zzav);
                zznz = this.zzbts.zznz();
            } else if (this.zzbtt == 0) {
                zznz = this.zzbts.zznz();
            } else if (this.zzbtt == 1) {
                this.zzbtt = 2;
                zzb(com_google_android_gms_internal_zzav);
                zznz = this.zzbts.zznz();
            } else if (this.zzbtt == 2) {
                zznz = this.zzbts.zznz();
            } else {
                zznz = this.zzbts.zznz();
            }
        }
        return zznz;
    }

    public zzc zzny() {
        return zzc(null);
    }
}
