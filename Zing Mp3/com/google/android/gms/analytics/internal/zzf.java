package com.google.android.gms.analytics.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.lang.Thread.UncaughtExceptionHandler;

public class zzf {
    private static volatile zzf cT;
    private final Context cU;
    private final zzr cV;
    private final zzaf cW;
    private final zzi cX;
    private final zzb cY;
    private final zzv cZ;
    private final zzap da;
    private final zzai db;
    private final GoogleAnalytics dc;
    private final zzn dd;
    private final zza de;
    private final zzk df;
    private final zzu dg;
    private final Context mContext;
    private final zze zzaql;

    /* renamed from: com.google.android.gms.analytics.internal.zzf.1 */
    class C11571 implements UncaughtExceptionHandler {
        final /* synthetic */ zzf dh;

        C11571(zzf com_google_android_gms_analytics_internal_zzf) {
            this.dh = com_google_android_gms_analytics_internal_zzf;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            zzaf zzacm = this.dh.zzacm();
            if (zzacm != null) {
                zzacm.zze("Job execution failed", th);
            }
        }
    }

    protected zzf(zzg com_google_android_gms_analytics_internal_zzg) {
        Object applicationContext = com_google_android_gms_analytics_internal_zzg.getApplicationContext();
        zzaa.zzb(applicationContext, (Object) "Application context can't be null");
        Context zzacl = com_google_android_gms_analytics_internal_zzg.zzacl();
        zzaa.zzy(zzacl);
        this.mContext = applicationContext;
        this.cU = zzacl;
        this.zzaql = com_google_android_gms_analytics_internal_zzg.zzh(this);
        this.cV = com_google_android_gms_analytics_internal_zzg.zzg(this);
        zzaf zzf = com_google_android_gms_analytics_internal_zzg.zzf(this);
        zzf.initialize();
        this.cW = zzf;
        zzacb();
        zzf = zzaca();
        String str = zze.VERSION;
        zzf.zzeu(new StringBuilder(String.valueOf(str).length() + 134).append("Google Analytics ").append(str).append(" is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4").toString());
        zzai zzq = com_google_android_gms_analytics_internal_zzg.zzq(this);
        zzq.initialize();
        this.db = zzq;
        zzap zze = com_google_android_gms_analytics_internal_zzg.zze(this);
        zze.initialize();
        this.da = zze;
        zzb zzl = com_google_android_gms_analytics_internal_zzg.zzl(this);
        zzn zzd = com_google_android_gms_analytics_internal_zzg.zzd(this);
        zza zzc = com_google_android_gms_analytics_internal_zzg.zzc(this);
        zzk zzb = com_google_android_gms_analytics_internal_zzg.zzb(this);
        zzu zza = com_google_android_gms_analytics_internal_zzg.zza(this);
        zzi zzax = com_google_android_gms_analytics_internal_zzg.zzax(applicationContext);
        zzax.zza(zzack());
        this.cX = zzax;
        GoogleAnalytics zzi = com_google_android_gms_analytics_internal_zzg.zzi(this);
        zzd.initialize();
        this.dd = zzd;
        zzc.initialize();
        this.de = zzc;
        zzb.initialize();
        this.df = zzb;
        zza.initialize();
        this.dg = zza;
        zzv zzp = com_google_android_gms_analytics_internal_zzg.zzp(this);
        zzp.initialize();
        this.cZ = zzp;
        zzl.initialize();
        this.cY = zzl;
        zzacb();
        zzi.initialize();
        this.dc = zzi;
        zzl.start();
    }

    private void zza(zzd com_google_android_gms_analytics_internal_zzd) {
        zzaa.zzb((Object) com_google_android_gms_analytics_internal_zzd, (Object) "Analytics service not created/initialized");
        zzaa.zzb(com_google_android_gms_analytics_internal_zzd.isInitialized(), (Object) "Analytics service not initialized");
    }

    public static zzf zzaw(Context context) {
        zzaa.zzy(context);
        if (cT == null) {
            synchronized (zzf.class) {
                if (cT == null) {
                    zze zzayl = zzh.zzayl();
                    long elapsedRealtime = zzayl.elapsedRealtime();
                    zzf com_google_android_gms_analytics_internal_zzf = new zzf(new zzg(context));
                    cT = com_google_android_gms_analytics_internal_zzf;
                    GoogleAnalytics.zzzd();
                    elapsedRealtime = zzayl.elapsedRealtime() - elapsedRealtime;
                    long longValue = ((Long) zzy.fb.get()).longValue();
                    if (elapsedRealtime > longValue) {
                        com_google_android_gms_analytics_internal_zzf.zzaca().zzc("Slow initialization (ms)", Long.valueOf(elapsedRealtime), Long.valueOf(longValue));
                    }
                }
            }
        }
        return cT;
    }

    public Context getContext() {
        return this.mContext;
    }

    public zze zzabz() {
        return this.zzaql;
    }

    public zzaf zzaca() {
        zza(this.cW);
        return this.cW;
    }

    public zzr zzacb() {
        return this.cV;
    }

    public zzi zzacc() {
        zzaa.zzy(this.cX);
        return this.cX;
    }

    public zzv zzacd() {
        zza(this.cZ);
        return this.cZ;
    }

    public zzai zzace() {
        zza(this.db);
        return this.db;
    }

    public zzk zzach() {
        zza(this.df);
        return this.df;
    }

    public zzu zzaci() {
        return this.dg;
    }

    protected UncaughtExceptionHandler zzack() {
        return new C11571(this);
    }

    public Context zzacl() {
        return this.cU;
    }

    public zzaf zzacm() {
        return this.cW;
    }

    public GoogleAnalytics zzacn() {
        zzaa.zzy(this.dc);
        zzaa.zzb(this.dc.isInitialized(), (Object) "Analytics instance not initialized");
        return this.dc;
    }

    public zzai zzaco() {
        return (this.db == null || !this.db.isInitialized()) ? null : this.db;
    }

    public zza zzacp() {
        zza(this.de);
        return this.de;
    }

    public zzn zzacq() {
        zza(this.dd);
        return this.dd;
    }

    public zzb zzzg() {
        zza(this.cY);
        return this.cY;
    }

    public zzap zzzh() {
        zza(this.da);
        return this.da;
    }

    public void zzzx() {
        zzi.zzzx();
    }
}
