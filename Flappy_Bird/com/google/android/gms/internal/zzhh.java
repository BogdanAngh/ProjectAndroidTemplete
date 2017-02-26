package com.google.android.gms.internal;

import java.util.concurrent.Future;

@zzgd
public abstract class zzhh {
    private volatile Thread zzFZ;
    private final Runnable zzx;

    /* renamed from: com.google.android.gms.internal.zzhh.1 */
    class C02421 implements Runnable {
        final /* synthetic */ zzhh zzGa;

        C02421(zzhh com_google_android_gms_internal_zzhh) {
            this.zzGa = com_google_android_gms_internal_zzhh;
        }

        public final void run() {
            this.zzGa.zzFZ = Thread.currentThread();
            this.zzGa.zzdP();
        }
    }

    public zzhh() {
        this.zzx = new C02421(this);
    }

    public final void cancel() {
        onStop();
        if (this.zzFZ != null) {
            this.zzFZ.interrupt();
        }
    }

    public abstract void onStop();

    public abstract void zzdP();

    public final Future zzgi() {
        return zzhk.zza(this.zzx);
    }

    public final void zzgj() {
        zzhk.zza(1, this.zzx);
    }
}
