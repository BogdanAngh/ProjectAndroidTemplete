package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@zzji
public class zzls {

    public interface zza<D, R> {
        R apply(D d);
    }

    /* renamed from: com.google.android.gms.internal.zzls.1 */
    class C14591 implements Runnable {
        final /* synthetic */ zzlq zzcyh;
        final /* synthetic */ zza zzcyi;
        final /* synthetic */ zzlt zzcyj;

        C14591(zzlq com_google_android_gms_internal_zzlq, zza com_google_android_gms_internal_zzls_zza, zzlt com_google_android_gms_internal_zzlt) {
            this.zzcyh = com_google_android_gms_internal_zzlq;
            this.zzcyi = com_google_android_gms_internal_zzls_zza;
            this.zzcyj = com_google_android_gms_internal_zzlt;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r3 = this;
            r0 = r3.zzcyh;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r1 = r3.zzcyi;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r2 = r3.zzcyj;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r2 = r2.get();	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r1 = r1.apply(r2);	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r0.zzh(r1);	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
        L_0x0011:
            return;
        L_0x0012:
            r0 = move-exception;
        L_0x0013:
            r0 = r3.zzcyh;
            r1 = 1;
            r0.cancel(r1);
            goto L_0x0011;
        L_0x001a:
            r0 = move-exception;
            goto L_0x0013;
        L_0x001c:
            r0 = move-exception;
            goto L_0x0013;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzls.1.run():void");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzls.2 */
    class C14602 implements Runnable {
        final /* synthetic */ AtomicInteger zzcyk;
        final /* synthetic */ int zzcyl;
        final /* synthetic */ zzlq zzcym;
        final /* synthetic */ List zzcyn;

        C14602(AtomicInteger atomicInteger, int i, zzlq com_google_android_gms_internal_zzlq, List list) {
            this.zzcyk = atomicInteger;
            this.zzcyl = i;
            this.zzcym = com_google_android_gms_internal_zzlq;
            this.zzcyn = list;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r2 = this;
            r0 = r2.zzcyk;
            r0 = r0.incrementAndGet();
            r1 = r2.zzcyl;
            if (r0 < r1) goto L_0x0015;
        L_0x000a:
            r0 = r2.zzcym;	 Catch:{ ExecutionException -> 0x001d, InterruptedException -> 0x0016 }
            r1 = r2.zzcyn;	 Catch:{ ExecutionException -> 0x001d, InterruptedException -> 0x0016 }
            r1 = com.google.android.gms.internal.zzls.zzp(r1);	 Catch:{ ExecutionException -> 0x001d, InterruptedException -> 0x0016 }
            r0.zzh(r1);	 Catch:{ ExecutionException -> 0x001d, InterruptedException -> 0x0016 }
        L_0x0015:
            return;
        L_0x0016:
            r0 = move-exception;
        L_0x0017:
            r1 = "Unable to convert list of futures to a future of list";
            com.google.android.gms.ads.internal.util.client.zzb.zzc(r1, r0);
            goto L_0x0015;
        L_0x001d:
            r0 = move-exception;
            goto L_0x0017;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzls.2.run():void");
        }
    }

    public static <A, B> zzlt<B> zza(zzlt<A> com_google_android_gms_internal_zzlt_A, zza<A, B> com_google_android_gms_internal_zzls_zza_A__B) {
        zzlt com_google_android_gms_internal_zzlq = new zzlq();
        com_google_android_gms_internal_zzlt_A.zzc(new C14591(com_google_android_gms_internal_zzlq, com_google_android_gms_internal_zzls_zza_A__B, com_google_android_gms_internal_zzlt_A));
        return com_google_android_gms_internal_zzlq;
    }

    public static <V> zzlt<List<V>> zzo(List<zzlt<V>> list) {
        zzlt com_google_android_gms_internal_zzlq = new zzlq();
        int size = list.size();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (zzlt zzc : list) {
            zzc.zzc(new C14602(atomicInteger, size, com_google_android_gms_internal_zzlq, list));
        }
        return com_google_android_gms_internal_zzlq;
    }

    private static <V> List<V> zzp(List<zzlt<V>> list) throws ExecutionException, InterruptedException {
        List<V> arrayList = new ArrayList();
        for (zzlt com_google_android_gms_internal_zzlt : list) {
            Object obj = com_google_android_gms_internal_zzlt.get();
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
