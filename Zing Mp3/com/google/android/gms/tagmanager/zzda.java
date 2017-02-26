package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;

class zzda implements zzcl {
    private long aHj;
    private final long fo;
    private final int fp;
    private double fq;
    private final Object fs;
    private final zze zzaql;

    public zzda() {
        this(60, 2000);
    }

    public zzda(int i, long j) {
        this.fs = new Object();
        this.fp = i;
        this.fq = (double) this.fp;
        this.fo = j;
        this.zzaql = zzh.zzayl();
    }

    public boolean zzagf() {
        boolean z;
        synchronized (this.fs) {
            long currentTimeMillis = this.zzaql.currentTimeMillis();
            if (this.fq < ((double) this.fp)) {
                double d = ((double) (currentTimeMillis - this.aHj)) / ((double) this.fo);
                if (d > 0.0d) {
                    this.fq = Math.min((double) this.fp, d + this.fq);
                }
            }
            this.aHj = currentTimeMillis;
            if (this.fq >= 1.0d) {
                this.fq -= 1.0d;
                z = true;
            } else {
                zzbo.zzdi("No more tokens available.");
                z = false;
            }
        }
        return z;
    }
}
