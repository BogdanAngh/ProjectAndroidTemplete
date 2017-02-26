package com.google.android.gms.tagmanager;

import android.text.TextUtils;

class zzas {
    private final long aFr;
    private final long aFs;
    private String aFt;
    private final long fk;

    zzas(long j, long j2, long j3) {
        this.aFr = j;
        this.fk = j2;
        this.aFs = j3;
    }

    long zzcfi() {
        return this.aFr;
    }

    long zzcfj() {
        return this.aFs;
    }

    String zzcfk() {
        return this.aFt;
    }

    void zzpj(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim())) {
            this.aFt = str;
        }
    }
}
