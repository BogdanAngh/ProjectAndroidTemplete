package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.util.zze;

public class zzad {
    private final String cd;
    private final long fo;
    private final int fp;
    private double fq;
    private long fr;
    private final Object fs;
    private final zze zzaql;

    public zzad(int i, long j, String str, zze com_google_android_gms_common_util_zze) {
        this.fs = new Object();
        this.fp = i;
        this.fq = (double) this.fp;
        this.fo = j;
        this.cd = str;
        this.zzaql = com_google_android_gms_common_util_zze;
    }

    public zzad(String str, zze com_google_android_gms_common_util_zze) {
        this(60, 2000, str, com_google_android_gms_common_util_zze);
    }

    public boolean zzagf() {
        boolean z;
        synchronized (this.fs) {
            long currentTimeMillis = this.zzaql.currentTimeMillis();
            if (this.fq < ((double) this.fp)) {
                double d = ((double) (currentTimeMillis - this.fr)) / ((double) this.fo);
                if (d > 0.0d) {
                    this.fq = Math.min((double) this.fp, d + this.fq);
                }
            }
            this.fr = currentTimeMillis;
            if (this.fq >= 1.0d) {
                this.fq -= 1.0d;
                z = true;
            } else {
                String str = this.cd;
                zzae.zzdi(new StringBuilder(String.valueOf(str).length() + 34).append("Excessive ").append(str).append(" detected; call ignored.").toString());
                z = false;
            }
        }
        return z;
    }
}
