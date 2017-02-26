package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;

public class zzbp extends zzby {
    private long zzajd;

    public zzbp(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
        this.zzajd = -1;
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        this.zzajb.zzdl = Long.valueOf(-1);
        if (this.zzajd == -1) {
            this.zzajd = (long) ((Integer) this.zzajk.invoke(null, new Object[]{this.zzagd.getContext()})).intValue();
        }
        synchronized (this.zzajb) {
            this.zzajb.zzdl = Long.valueOf(this.zzajd);
        }
    }
}
