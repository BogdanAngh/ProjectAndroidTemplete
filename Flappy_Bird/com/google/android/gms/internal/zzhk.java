package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzo;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@zzgd
public final class zzhk {
    private static final ExecutorService zzGe;
    private static final ExecutorService zzGf;

    /* renamed from: com.google.android.gms.internal.zzhk.1 */
    static class C02431 implements Callable<Void> {
        final /* synthetic */ Runnable zzGg;

        C02431(Runnable runnable) {
            this.zzGg = runnable;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzgk();
        }

        public Void zzgk() {
            this.zzGg.run();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhk.2 */
    static class C02442 implements Callable<Void> {
        final /* synthetic */ Runnable zzGg;

        C02442(Runnable runnable) {
            this.zzGg = runnable;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzgk();
        }

        public Void zzgk() {
            this.zzGg.run();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhk.3 */
    static class C02453 implements Runnable {
        final /* synthetic */ Callable zzGh;
        final /* synthetic */ zzhs zzqX;

        C02453(zzhs com_google_android_gms_internal_zzhs, Callable callable) {
            this.zzqX = com_google_android_gms_internal_zzhs;
            this.zzGh = callable;
        }

        public void run() {
            try {
                Process.setThreadPriority(10);
                this.zzqX.zzf(this.zzGh.call());
            } catch (Throwable e) {
                zzo.zzby().zzc(e, true);
                this.zzqX.cancel(true);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhk.4 */
    static class C02464 implements ThreadFactory {
        private final AtomicInteger zzGi;
        final /* synthetic */ String zzGj;

        C02464(String str) {
            this.zzGj = str;
            this.zzGi = new AtomicInteger(1);
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AdWorker(" + this.zzGj + ") #" + this.zzGi.getAndIncrement());
        }
    }

    static {
        zzGe = Executors.newFixedThreadPool(10, zzas("Default"));
        zzGf = Executors.newFixedThreadPool(5, zzas("Loader"));
    }

    public static zzhv<Void> zza(int i, Runnable runnable) {
        return i == 1 ? zza(zzGf, new C02431(runnable)) : zza(zzGe, new C02442(runnable));
    }

    public static zzhv<Void> zza(Runnable runnable) {
        return zza(0, runnable);
    }

    public static <T> zzhv<T> zza(Callable<T> callable) {
        return zza(zzGe, (Callable) callable);
    }

    public static <T> zzhv<T> zza(ExecutorService executorService, Callable<T> callable) {
        Object com_google_android_gms_internal_zzhs = new zzhs();
        try {
            executorService.submit(new C02453(com_google_android_gms_internal_zzhs, callable));
        } catch (Throwable e) {
            zzb.zzd("Thread execution is rejected.", e);
            com_google_android_gms_internal_zzhs.cancel(true);
        }
        return com_google_android_gms_internal_zzhs;
    }

    private static ThreadFactory zzas(String str) {
        return new C02464(str);
    }
}
