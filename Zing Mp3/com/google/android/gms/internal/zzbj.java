package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;

public class zzbj extends zzby {
    public zzbj(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        this.zzajb.zzde = Long.valueOf(-1);
        this.zzajb.zzdf = Long.valueOf(-1);
        int[] iArr = (int[]) this.zzajk.invoke(null, new Object[]{this.zzagd.getContext()});
        synchronized (this.zzajb) {
            this.zzajb.zzde = Long.valueOf((long) iArr[0]);
            this.zzajb.zzdf = Long.valueOf((long) iArr[1]);
        }
    }
}
