package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;

public class zzbs extends zzby {
    private final StackTraceElement[] zzajf;

    public zzbs(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2, StackTraceElement[] stackTraceElementArr) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
        this.zzajf = stackTraceElementArr;
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        if (this.zzajf != null) {
            zzba com_google_android_gms_internal_zzba = new zzba((String) this.zzajk.invoke(null, new Object[]{this.zzajf}));
            synchronized (this.zzajb) {
                this.zzajb.zzej = com_google_android_gms_internal_zzba.zzahn;
                if (com_google_android_gms_internal_zzba.zzaho.booleanValue()) {
                    this.zzajb.zzer = Integer.valueOf(com_google_android_gms_internal_zzba.zzahp.booleanValue() ? 0 : 1);
                }
            }
        }
    }
}
