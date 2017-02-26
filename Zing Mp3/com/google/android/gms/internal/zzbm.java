package com.google.android.gms.internal;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.internal.zzaf.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class zzbm extends zzby {
    public zzbm(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
    }

    private void zzdm() throws IllegalAccessException, InvocationTargetException {
        synchronized (this.zzajb) {
            this.zzajb.zzew = (String) this.zzajk.invoke(null, new Object[]{this.zzagd.getApplicationContext()});
        }
    }

    private void zzdn() {
        AdvertisingIdClient zzdc = this.zzagd.zzdc();
        if (zzdc == null) {
            zzt("E1");
            return;
        }
        try {
            Info info = zzdc.getInfo();
            String zzr = zzbe.zzr(info.getId());
            if (zzr != null) {
                synchronized (this.zzajb) {
                    this.zzajb.zzew = zzr;
                    this.zzajb.zzey = Boolean.valueOf(info.isLimitAdTrackingEnabled());
                    this.zzajb.zzex = Integer.valueOf(5);
                }
                return;
            }
            zzt("E");
        } catch (IOException e) {
            zzt("E");
        }
    }

    private void zzt(String str) {
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        if (this.zzagd.zzcr()) {
            zzdn();
        } else {
            zzdm();
        }
    }
}
