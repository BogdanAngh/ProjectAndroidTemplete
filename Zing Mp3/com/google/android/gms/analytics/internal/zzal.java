package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zze;

class zzal {
    private final zze zzaql;
    private long zzbwt;

    public zzal(zze com_google_android_gms_common_util_zze) {
        zzaa.zzy(com_google_android_gms_common_util_zze);
        this.zzaql = com_google_android_gms_common_util_zze;
    }

    public zzal(zze com_google_android_gms_common_util_zze, long j) {
        zzaa.zzy(com_google_android_gms_common_util_zze);
        this.zzaql = com_google_android_gms_common_util_zze;
        this.zzbwt = j;
    }

    public void clear() {
        this.zzbwt = 0;
    }

    public void start() {
        this.zzbwt = this.zzaql.elapsedRealtime();
    }

    public boolean zzz(long j) {
        return this.zzbwt == 0 || this.zzaql.elapsedRealtime() - this.zzbwt > j;
    }
}
