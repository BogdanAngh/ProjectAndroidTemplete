package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@zzji
public final class zzla {
    private static final ThreadPoolExecutor zzcvd;
    private static final ThreadPoolExecutor zzcve;

    /* renamed from: com.google.android.gms.internal.zzla.1 */
    class C14371 implements Callable<Void> {
        final /* synthetic */ Runnable zzcvf;

        C14371(Runnable runnable) {
            this.zzcvf = runnable;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzdo();
        }

        public Void zzdo() {
            this.zzcvf.run();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzla.2 */
    class C14382 implements Callable<Void> {
        final /* synthetic */ Runnable zzcvf;

        C14382(Runnable runnable) {
            this.zzcvf = runnable;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzdo();
        }

        public Void zzdo() {
            this.zzcvf.run();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzla.3 */
    class C14393 implements Runnable {
        final /* synthetic */ zzlq zzcvg;
        final /* synthetic */ Callable zzcvh;

        C14393(zzlq com_google_android_gms_internal_zzlq, Callable callable) {
            this.zzcvg = com_google_android_gms_internal_zzlq;
            this.zzcvh = callable;
        }

        public void run() {
            try {
                Process.setThreadPriority(10);
                this.zzcvg.zzh(this.zzcvh.call());
            } catch (Throwable e) {
                zzu.zzgq().zza(e, "AdThreadPool.submit");
                this.zzcvg.zze(e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzla.4 */
    class C14404 implements Runnable {
        final /* synthetic */ zzlq zzcvg;
        final /* synthetic */ Future zzcvi;

        C14404(zzlq com_google_android_gms_internal_zzlq, Future future) {
            this.zzcvg = com_google_android_gms_internal_zzlq;
            this.zzcvi = future;
        }

        public void run() {
            if (this.zzcvg.isCancelled()) {
                this.zzcvi.cancel(true);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzla.5 */
    class C14415 implements ThreadFactory {
        private final AtomicInteger zzcvj;
        final /* synthetic */ String zzcvk;

        C14415(String str) {
            this.zzcvk = str;
            this.zzcvj = new AtomicInteger(1);
        }

        public Thread newThread(Runnable runnable) {
            String str = this.zzcvk;
            return new Thread(runnable, new StringBuilder(String.valueOf(str).length() + 23).append("AdWorker(").append(str).append(") #").append(this.zzcvj.getAndIncrement()).toString());
        }
    }

    static {
        zzcvd = new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue(), zzcy("Default"));
        zzcve = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue(), zzcy("Loader"));
        zzcvd.allowCoreThreadTimeOut(true);
        zzcve.allowCoreThreadTimeOut(true);
    }

    public static zzlt<Void> zza(int i, Runnable runnable) {
        return i == 1 ? zza(zzcve, new C14371(runnable)) : zza(zzcvd, new C14382(runnable));
    }

    public static zzlt<Void> zza(Runnable runnable) {
        return zza(0, runnable);
    }

    public static <T> zzlt<T> zza(Callable<T> callable) {
        return zza(zzcvd, (Callable) callable);
    }

    public static <T> zzlt<T> zza(ExecutorService executorService, Callable<T> callable) {
        Object com_google_android_gms_internal_zzlq = new zzlq();
        try {
            com_google_android_gms_internal_zzlq.zzd(new C14404(com_google_android_gms_internal_zzlq, executorService.submit(new C14393(com_google_android_gms_internal_zzlq, callable))));
        } catch (Throwable e) {
            zzb.zzc("Thread execution is rejected.", e);
            com_google_android_gms_internal_zzlq.cancel(true);
        }
        return com_google_android_gms_internal_zzlq;
    }

    private static ThreadFactory zzcy(String str) {
        return new C14415(str);
    }
}
