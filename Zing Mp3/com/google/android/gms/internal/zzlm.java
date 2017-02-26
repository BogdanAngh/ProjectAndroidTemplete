package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzlm {
    private Object zzako;
    private long zzcxi;
    private long zzcxj;

    public zzlm(long j) {
        this.zzcxj = Long.MIN_VALUE;
        this.zzako = new Object();
        this.zzcxi = j;
    }

    public boolean tryAcquire() {
        boolean z;
        synchronized (this.zzako) {
            long elapsedRealtime = zzu.zzgs().elapsedRealtime();
            if (this.zzcxj + this.zzcxi > elapsedRealtime) {
                z = false;
            } else {
                this.zzcxj = elapsedRealtime;
                z = true;
            }
        }
        return z;
    }
}
