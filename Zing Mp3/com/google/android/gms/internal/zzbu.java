package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;

public class zzbu extends zzby {
    private static final Object zzaix;
    private static volatile Long zzajg;

    static {
        zzajg = null;
        zzaix = new Object();
    }

    public zzbu(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        if (zzajg == null) {
            synchronized (zzaix) {
                if (zzajg == null) {
                    zzajg = (Long) this.zzajk.invoke(null, new Object[0]);
                }
            }
        }
        synchronized (this.zzajb) {
            this.zzajb.zzdz = zzajg;
        }
    }
}
