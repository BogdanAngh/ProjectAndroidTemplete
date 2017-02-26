package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;

public class zzbl extends zzby {
    private long startTime;

    public zzbl(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, long j, int i, int i2) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
        this.startTime = j;
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        long longValue = ((Long) this.zzajk.invoke(null, new Object[0])).longValue();
        synchronized (this.zzajb) {
            this.zzajb.zzfa = Long.valueOf(longValue);
            if (this.startTime != 0) {
                this.zzajb.zzdq = Long.valueOf(longValue - this.startTime);
                this.zzajb.zzdv = Long.valueOf(this.startTime);
            }
        }
    }
}
