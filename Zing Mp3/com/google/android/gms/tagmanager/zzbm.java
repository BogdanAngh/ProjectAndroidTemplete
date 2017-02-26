package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;

class zzbm implements zzcl {
    private final long aFN;
    private final String cd;
    private final long fo;
    private final int fp;
    private double fq;
    private long fr;
    private final Object fs;
    private final zze zzaql;

    public zzbm(int i, int i2, long j, long j2, String str, zze com_google_android_gms_common_util_zze) {
        this.fs = new Object();
        this.fp = i2;
        this.fq = (double) Math.min(i, i2);
        this.fo = j;
        this.aFN = j2;
        this.cd = str;
        this.zzaql = com_google_android_gms_common_util_zze;
    }

    public boolean zzagf() {
        boolean z = false;
        synchronized (this.fs) {
            long currentTimeMillis = this.zzaql.currentTimeMillis();
            String str;
            if (currentTimeMillis - this.fr < this.aFN) {
                str = this.cd;
                zzbo.zzdi(new StringBuilder(String.valueOf(str).length() + 34).append("Excessive ").append(str).append(" detected; call ignored.").toString());
            } else {
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
                    str = this.cd;
                    zzbo.zzdi(new StringBuilder(String.valueOf(str).length() + 34).append("Excessive ").append(str).append(" detected; call ignored.").toString());
                }
            }
        }
        return z;
    }
}
