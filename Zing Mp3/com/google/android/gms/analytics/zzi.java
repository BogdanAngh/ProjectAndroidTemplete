package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzms;
import com.google.android.gms.internal.zzmx;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzi {
    private static volatile zzi bj;
    private final List<zzj> bk;
    private final zzd bl;
    private final zza bm;
    private volatile zzms bn;
    private UncaughtExceptionHandler bo;
    private final Context mContext;

    /* renamed from: com.google.android.gms.analytics.zzi.1 */
    class C11691 implements Runnable {
        final /* synthetic */ zze bp;
        final /* synthetic */ zzi bq;

        C11691(zzi com_google_android_gms_analytics_zzi, zze com_google_android_gms_analytics_zze) {
            this.bq = com_google_android_gms_analytics_zzi;
            this.bp = com_google_android_gms_analytics_zze;
        }

        public void run() {
            this.bp.zzzp().zza(this.bp);
            for (zzj zza : this.bq.bk) {
                zza.zza(this.bp);
            }
            this.bq.zzb(this.bp);
        }
    }

    private class zza extends ThreadPoolExecutor {
        final /* synthetic */ zzi bq;

        /* renamed from: com.google.android.gms.analytics.zzi.zza.1 */
        class C11701 extends FutureTask<T> {
            final /* synthetic */ zza br;

            C11701(zza com_google_android_gms_analytics_zzi_zza, Runnable runnable, Object obj) {
                this.br = com_google_android_gms_analytics_zzi_zza;
                super(runnable, obj);
            }

            protected void setException(Throwable th) {
                UncaughtExceptionHandler zzb = this.br.bq.bo;
                if (zzb != null) {
                    zzb.uncaughtException(Thread.currentThread(), th);
                } else if (Log.isLoggable("GAv4", 6)) {
                    String valueOf = String.valueOf(th);
                    Log.e("GAv4", new StringBuilder(String.valueOf(valueOf).length() + 37).append("MeasurementExecutor: job failed with ").append(valueOf).toString());
                }
                super.setException(th);
            }
        }

        public zza(zzi com_google_android_gms_analytics_zzi) {
            this.bq = com_google_android_gms_analytics_zzi;
            super(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());
            setThreadFactory(new zzb());
            allowCoreThreadTimeOut(true);
        }

        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
            return new C11701(this, runnable, t);
        }
    }

    private static class zzb implements ThreadFactory {
        private static final AtomicInteger bs;

        static {
            bs = new AtomicInteger();
        }

        private zzb() {
        }

        public Thread newThread(Runnable runnable) {
            return new zzc(runnable, "measurement-" + bs.incrementAndGet());
        }
    }

    private static class zzc extends Thread {
        zzc(Runnable runnable, String str) {
            super(runnable, str);
        }

        public void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    zzi(Context context) {
        Context applicationContext = context.getApplicationContext();
        zzaa.zzy(applicationContext);
        this.mContext = applicationContext;
        this.bm = new zza(this);
        this.bk = new CopyOnWriteArrayList();
        this.bl = new zzd();
    }

    public static zzi zzav(Context context) {
        zzaa.zzy(context);
        if (bj == null) {
            synchronized (zzi.class) {
                if (bj == null) {
                    bj = new zzi(context);
                }
            }
        }
        return bj;
    }

    private void zzb(zze com_google_android_gms_analytics_zze) {
        zzaa.zzht("deliver should be called from worker thread");
        zzaa.zzb(com_google_android_gms_analytics_zze.zzzn(), (Object) "Measurement must be submitted");
        List<zzk> zzzk = com_google_android_gms_analytics_zze.zzzk();
        if (!zzzk.isEmpty()) {
            Set hashSet = new HashSet();
            for (zzk com_google_android_gms_analytics_zzk : zzzk) {
                Uri zzyx = com_google_android_gms_analytics_zzk.zzyx();
                if (!hashSet.contains(zzyx)) {
                    hashSet.add(zzyx);
                    com_google_android_gms_analytics_zzk.zzb(com_google_android_gms_analytics_zze);
                }
            }
        }
    }

    public static void zzzx() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void zza(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.bo = uncaughtExceptionHandler;
    }

    public <V> Future<V> zzc(Callable<V> callable) {
        zzaa.zzy(callable);
        if (!(Thread.currentThread() instanceof zzc)) {
            return this.bm.submit(callable);
        }
        Future futureTask = new FutureTask(callable);
        futureTask.run();
        return futureTask;
    }

    void zze(zze com_google_android_gms_analytics_zze) {
        if (com_google_android_gms_analytics_zze.zzzr()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        } else if (com_google_android_gms_analytics_zze.zzzn()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        } else {
            zze zzzi = com_google_android_gms_analytics_zze.zzzi();
            zzzi.zzzo();
            this.bm.execute(new C11691(this, zzzi));
        }
    }

    public void zzg(Runnable runnable) {
        zzaa.zzy(runnable);
        this.bm.submit(runnable);
    }

    public zzms zzzv() {
        if (this.bn == null) {
            synchronized (this) {
                if (this.bn == null) {
                    zzms com_google_android_gms_internal_zzms = new zzms();
                    PackageManager packageManager = this.mContext.getPackageManager();
                    String packageName = this.mContext.getPackageName();
                    com_google_android_gms_internal_zzms.setAppId(packageName);
                    com_google_android_gms_internal_zzms.setAppInstallerId(packageManager.getInstallerPackageName(packageName));
                    String str = null;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.mContext.getPackageName(), 0);
                        if (packageInfo != null) {
                            CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            if (!TextUtils.isEmpty(applicationLabel)) {
                                packageName = applicationLabel.toString();
                            }
                            str = packageInfo.versionName;
                        }
                    } catch (NameNotFoundException e) {
                        String str2 = "GAv4";
                        String str3 = "Error retrieving package info: appName set to ";
                        String valueOf = String.valueOf(packageName);
                        Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                    }
                    com_google_android_gms_internal_zzms.setAppName(packageName);
                    com_google_android_gms_internal_zzms.setAppVersion(str);
                    this.bn = com_google_android_gms_internal_zzms;
                }
            }
        }
        return this.bn;
    }

    public zzmx zzzw() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        zzmx com_google_android_gms_internal_zzmx = new zzmx();
        com_google_android_gms_internal_zzmx.setLanguage(zzao.zza(Locale.getDefault()));
        com_google_android_gms_internal_zzmx.zzbz(displayMetrics.widthPixels);
        com_google_android_gms_internal_zzmx.zzca(displayMetrics.heightPixels);
        return com_google_android_gms_internal_zzmx;
    }
}
