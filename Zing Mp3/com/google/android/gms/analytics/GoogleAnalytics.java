package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzae;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzan;
import com.google.android.gms.analytics.internal.zzap;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzy;
import com.google.android.gms.common.internal.zzaa;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GoogleAnalytics extends zza {
    private static List<Runnable> aH;
    private Set<zza> aI;
    private boolean aJ;
    private boolean aK;
    private volatile boolean aL;
    private boolean aM;
    private boolean zzaoz;

    interface zza {
        void zzo(Activity activity);

        void zzp(Activity activity);
    }

    @TargetApi(14)
    class zzb implements ActivityLifecycleCallbacks {
        final /* synthetic */ GoogleAnalytics aN;

        zzb(GoogleAnalytics googleAnalytics) {
            this.aN = googleAnalytics;
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            this.aN.zzm(activity);
        }

        public void onActivityStopped(Activity activity) {
            this.aN.zzn(activity);
        }
    }

    static {
        aH = new ArrayList();
    }

    public GoogleAnalytics(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.aI = new HashSet();
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static GoogleAnalytics getInstance(Context context) {
        return zzf.zzaw(context).zzacn();
    }

    public static void zzzd() {
        synchronized (GoogleAnalytics.class) {
            if (aH != null) {
                for (Runnable run : aH) {
                    run.run();
                }
                aH = null;
            }
        }
    }

    private com.google.android.gms.analytics.internal.zzb zzzg() {
        return zzyt().zzzg();
    }

    private zzap zzzh() {
        return zzyt().zzzh();
    }

    public void dispatchLocalHits() {
        zzzg().zzabs();
    }

    @TargetApi(14)
    public void enableAutoActivityReports(Application application) {
        if (VERSION.SDK_INT >= 14 && !this.aJ) {
            application.registerActivityLifecycleCallbacks(new zzb(this));
            this.aJ = true;
        }
    }

    public boolean getAppOptOut() {
        return this.aL;
    }

    @Deprecated
    public Logger getLogger() {
        return zzae.getLogger();
    }

    public void initialize() {
        zzzc();
        this.zzaoz = true;
    }

    public boolean isDryRunEnabled() {
        return this.aK;
    }

    public boolean isInitialized() {
        return this.zzaoz;
    }

    public Tracker newTracker(int i) {
        Tracker tracker;
        synchronized (this) {
            tracker = new Tracker(zzyt(), null, null);
            if (i > 0) {
                zzan com_google_android_gms_analytics_internal_zzan = (zzan) new zzam(zzyt()).zzcg(i);
                if (com_google_android_gms_analytics_internal_zzan != null) {
                    tracker.zza(com_google_android_gms_analytics_internal_zzan);
                }
            }
            tracker.initialize();
        }
        return tracker;
    }

    public Tracker newTracker(String str) {
        Tracker tracker;
        synchronized (this) {
            tracker = new Tracker(zzyt(), str, null);
            tracker.initialize();
        }
        return tracker;
    }

    public void reportActivityStart(Activity activity) {
        if (!this.aJ) {
            zzm(activity);
        }
    }

    public void reportActivityStop(Activity activity) {
        if (!this.aJ) {
            zzn(activity);
        }
    }

    public void setAppOptOut(boolean z) {
        this.aL = z;
        if (this.aL) {
            zzzg().zzabr();
        }
    }

    public void setDryRun(boolean z) {
        this.aK = z;
    }

    public void setLocalDispatchPeriod(int i) {
        zzzg().setLocalDispatchPeriod(i);
    }

    @Deprecated
    public void setLogger(Logger logger) {
        zzae.setLogger(logger);
        if (!this.aM) {
            String str = (String) zzy.en.get();
            Log.i((String) zzy.en.get(), new StringBuilder(String.valueOf(str).length() + C1569R.styleable.AppCompatTheme_spinnerStyle).append("GoogleAnalytics.setLogger() is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.").append(str).append(" DEBUG").toString());
            this.aM = true;
        }
    }

    void zza(zza com_google_android_gms_analytics_GoogleAnalytics_zza) {
        this.aI.add(com_google_android_gms_analytics_GoogleAnalytics_zza);
        Context context = zzyt().getContext();
        if (context instanceof Application) {
            enableAutoActivityReports((Application) context);
        }
    }

    void zzb(zza com_google_android_gms_analytics_GoogleAnalytics_zza) {
        this.aI.remove(com_google_android_gms_analytics_GoogleAnalytics_zza);
    }

    void zzm(Activity activity) {
        for (zza zzo : this.aI) {
            zzo.zzo(activity);
        }
    }

    void zzn(Activity activity) {
        for (zza zzp : this.aI) {
            zzp.zzp(activity);
        }
    }

    void zzzc() {
        zzap zzzh = zzzh();
        if (zzzh.zzafs()) {
            getLogger().setLogLevel(zzzh.getLogLevel());
        }
        if (zzzh.zzafw()) {
            setDryRun(zzzh.zzafx());
        }
        if (zzzh.zzafs()) {
            Logger logger = zzae.getLogger();
            if (logger != null) {
                logger.setLogLevel(zzzh.getLogLevel());
            }
        }
    }

    public String zzze() {
        zzaa.zzht("getClientId can not be called from the main thread");
        return zzyt().zzacq().zzady();
    }

    void zzzf() {
        zzzg().zzabt();
    }
}
