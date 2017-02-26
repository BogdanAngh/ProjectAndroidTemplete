package com.google.android.gms.internal;

public class zzhu {

    /* renamed from: com.google.android.gms.internal.zzhu.1 */
    static class C02511 implements Runnable {
        final /* synthetic */ zzhs zzGN;
        final /* synthetic */ zza zzGO;
        final /* synthetic */ zzhv zzGP;

        C02511(zzhs com_google_android_gms_internal_zzhs, zza com_google_android_gms_internal_zzhu_zza, zzhv com_google_android_gms_internal_zzhv) {
            this.zzGN = com_google_android_gms_internal_zzhs;
            this.zzGO = com_google_android_gms_internal_zzhu_zza;
            this.zzGP = com_google_android_gms_internal_zzhv;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r3 = this;
            r0 = r3.zzGN;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r1 = r3.zzGO;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r2 = r3.zzGP;	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r2 = r2.get();	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r1 = r1.zze(r2);	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
            r0.zzf(r1);	 Catch:{ CancellationException -> 0x001c, InterruptedException -> 0x001a, ExecutionException -> 0x0012 }
        L_0x0011:
            return;
        L_0x0012:
            r0 = move-exception;
        L_0x0013:
            r0 = r3.zzGN;
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
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzhu.1.run():void");
        }
    }

    public interface zza<D, R> {
        R zze(D d);
    }

    public static <A, B> zzhv<B> zza(zzhv<A> com_google_android_gms_internal_zzhv_A, zza<A, B> com_google_android_gms_internal_zzhu_zza_A__B) {
        zzhv com_google_android_gms_internal_zzhs = new zzhs();
        com_google_android_gms_internal_zzhv_A.zzb(new C02511(com_google_android_gms_internal_zzhs, com_google_android_gms_internal_zzhu_zza_A__B, com_google_android_gms_internal_zzhv_A));
        return com_google_android_gms_internal_zzhs;
    }
}
