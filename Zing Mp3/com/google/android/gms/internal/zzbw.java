package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.internal.zzaf.zza;
import com.google.android.gms.internal.zzaf.zza.zzb;
import java.lang.reflect.InvocationTargetException;

public class zzbw extends zzby {
    private final View zzajh;

    public zzbw(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2, View view) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
        this.zzajh = view;
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        if (this.zzajh != null) {
            zzbf com_google_android_gms_internal_zzbf = new zzbf((String) this.zzajk.invoke(null, new Object[]{this.zzajh}));
            synchronized (this.zzajb) {
                this.zzajb.zzeu = new zzb();
                this.zzajb.zzeu.zzfn = com_google_android_gms_internal_zzbf.zzaiv;
            }
        }
    }
}
