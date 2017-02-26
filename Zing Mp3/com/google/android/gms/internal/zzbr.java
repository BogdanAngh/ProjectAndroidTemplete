package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class zzbr extends zzby {
    private List<Long> zzaje;

    public zzbr(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
        this.zzaje = null;
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        this.zzajb.zzdx = Long.valueOf(-1);
        this.zzajb.zzdy = Long.valueOf(-1);
        if (this.zzaje == null) {
            this.zzaje = (List) this.zzajk.invoke(null, new Object[]{this.zzagd.getContext()});
        }
        if (this.zzaje != null && this.zzaje.size() == 2) {
            synchronized (this.zzajb) {
                this.zzajb.zzdx = Long.valueOf(((Long) this.zzaje.get(0)).longValue());
                this.zzajb.zzdy = Long.valueOf(((Long) this.zzaje.get(1)).longValue());
            }
        }
    }
}
