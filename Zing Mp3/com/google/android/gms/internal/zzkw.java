package com.google.android.gms.internal;

import java.util.concurrent.Future;

@zzji
public abstract class zzkw implements zzld<Future> {
    private volatile Thread zzcur;
    private boolean zzcus;
    private final Runnable zzw;

    /* renamed from: com.google.android.gms.internal.zzkw.1 */
    class C14271 implements Runnable {
        final /* synthetic */ zzkw zzcut;

        C14271(zzkw com_google_android_gms_internal_zzkw) {
            this.zzcut = com_google_android_gms_internal_zzkw;
        }

        public final void run() {
            this.zzcut.zzcur = Thread.currentThread();
            this.zzcut.zzfp();
        }
    }

    public zzkw() {
        this.zzw = new C14271(this);
        this.zzcus = false;
    }

    public zzkw(boolean z) {
        this.zzw = new C14271(this);
        this.zzcus = z;
    }

    public final void cancel() {
        onStop();
        if (this.zzcur != null) {
            this.zzcur.interrupt();
        }
    }

    public abstract void onStop();

    public abstract void zzfp();

    public /* synthetic */ Object zzrz() {
        return zzvm();
    }

    public final Future zzvm() {
        return this.zzcus ? zzla.zza(1, this.zzw) : zzla.zza(this.zzw);
    }
}
