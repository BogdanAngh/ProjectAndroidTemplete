package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;

public class zzbh extends zzby {
    private static volatile String zzaiw;
    private static final Object zzaix;

    static {
        zzaiw = null;
        zzaix = new Object();
    }

    public zzbh(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        this.zzajb.zzdw = "E";
        if (zzaiw == null) {
            synchronized (zzaix) {
                if (zzaiw == null) {
                    zzaiw = (String) this.zzajk.invoke(null, new Object[]{this.zzagd.getContext()});
                }
            }
        }
        synchronized (this.zzajb) {
            this.zzajb.zzdw = zzal.zza(zzaiw.getBytes(), true);
        }
    }
}
