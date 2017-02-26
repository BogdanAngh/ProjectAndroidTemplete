package com.google.android.gms.analytics.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.zzaa;

abstract class zzt {
    private static volatile Handler ef;
    private final zzf cQ;
    private volatile long eg;
    private final Runnable zzw;

    /* renamed from: com.google.android.gms.analytics.internal.zzt.1 */
    class C11671 implements Runnable {
        final /* synthetic */ zzt eh;

        C11671(zzt com_google_android_gms_analytics_internal_zzt) {
            this.eh = com_google_android_gms_analytics_internal_zzt;
        }

        public void run() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.eh.cQ.zzacc().zzg(this);
                return;
            }
            boolean zzfy = this.eh.zzfy();
            this.eh.eg = 0;
            if (zzfy && !false) {
                this.eh.run();
            }
        }
    }

    zzt(zzf com_google_android_gms_analytics_internal_zzf) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzf);
        this.cQ = com_google_android_gms_analytics_internal_zzf;
        this.zzw = new C11671(this);
    }

    private Handler getHandler() {
        if (ef != null) {
            return ef;
        }
        Handler handler;
        synchronized (zzt.class) {
            if (ef == null) {
                ef = new Handler(this.cQ.getContext().getMainLooper());
            }
            handler = ef;
        }
        return handler;
    }

    public void cancel() {
        this.eg = 0;
        getHandler().removeCallbacks(this.zzw);
    }

    public abstract void run();

    public long zzafk() {
        return this.eg == 0 ? 0 : Math.abs(this.cQ.zzabz().currentTimeMillis() - this.eg);
    }

    public boolean zzfy() {
        return this.eg != 0;
    }

    public void zzx(long j) {
        cancel();
        if (j >= 0) {
            this.eg = this.cQ.zzabz().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzw, j)) {
                this.cQ.zzaca().zze("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public void zzy(long j) {
        long j2 = 0;
        if (!zzfy()) {
            return;
        }
        if (j < 0) {
            cancel();
            return;
        }
        long abs = j - Math.abs(this.cQ.zzabz().currentTimeMillis() - this.eg);
        if (abs >= 0) {
            j2 = abs;
        }
        getHandler().removeCallbacks(this.zzw);
        if (!getHandler().postDelayed(this.zzw, j2)) {
            this.cQ.zzaca().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
        }
    }
}
