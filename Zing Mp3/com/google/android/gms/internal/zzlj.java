package com.google.android.gms.internal;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.google.android.gms.common.internal.zzaa;

@zzji
public class zzlj {
    private Handler mHandler;
    private final Object zzako;
    private HandlerThread zzcxc;
    private int zzcxd;

    /* renamed from: com.google.android.gms.internal.zzlj.1 */
    class C14581 implements Runnable {
        final /* synthetic */ zzlj zzcxe;

        C14581(zzlj com_google_android_gms_internal_zzlj) {
            this.zzcxe = com_google_android_gms_internal_zzlj;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r2 = this;
            r0 = r2.zzcxe;
            r1 = r0.zzako;
            monitor-enter(r1);
            r0 = "Suspending the looper thread";
            com.google.android.gms.internal.zzkx.m1697v(r0);	 Catch:{ all -> 0x002a }
        L_0x000c:
            r0 = r2.zzcxe;	 Catch:{ all -> 0x002a }
            r0 = r0.zzcxd;	 Catch:{ all -> 0x002a }
            if (r0 != 0) goto L_0x002d;
        L_0x0014:
            r0 = r2.zzcxe;	 Catch:{ InterruptedException -> 0x0023 }
            r0 = r0.zzako;	 Catch:{ InterruptedException -> 0x0023 }
            r0.wait();	 Catch:{ InterruptedException -> 0x0023 }
            r0 = "Looper thread resumed";
            com.google.android.gms.internal.zzkx.m1697v(r0);	 Catch:{ InterruptedException -> 0x0023 }
            goto L_0x000c;
        L_0x0023:
            r0 = move-exception;
            r0 = "Looper thread interrupted.";
            com.google.android.gms.internal.zzkx.m1697v(r0);	 Catch:{ all -> 0x002a }
            goto L_0x000c;
        L_0x002a:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002a }
            throw r0;
        L_0x002d:
            monitor-exit(r1);	 Catch:{ all -> 0x002a }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzlj.1.run():void");
        }
    }

    public zzlj() {
        this.zzcxc = null;
        this.mHandler = null;
        this.zzcxd = 0;
        this.zzako = new Object();
    }

    public Looper zzwj() {
        Looper looper;
        synchronized (this.zzako) {
            if (this.zzcxd != 0) {
                zzaa.zzb(this.zzcxc, (Object) "Invalid state: mHandlerThread should already been initialized.");
            } else if (this.zzcxc == null) {
                zzkx.m1697v("Starting the looper thread.");
                this.zzcxc = new HandlerThread("LooperProvider");
                this.zzcxc.start();
                this.mHandler = new Handler(this.zzcxc.getLooper());
                zzkx.m1697v("Looper thread started.");
            } else {
                zzkx.m1697v("Resuming the looper thread");
                this.zzako.notifyAll();
            }
            this.zzcxd++;
            looper = this.zzcxc.getLooper();
        }
        return looper;
    }

    public void zzwk() {
        synchronized (this.zzako) {
            zzaa.zzb(this.zzcxd > 0, (Object) "Invalid state: release() called more times than expected.");
            int i = this.zzcxd - 1;
            this.zzcxd = i;
            if (i == 0) {
                this.mHandler.post(new C14581(this));
            }
        }
    }
}
