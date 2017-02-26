package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzw extends zzam {
    private static final String ID;
    private static final String NAME;
    private static final String aEO;
    private final DataLayer aDZ;

    static {
        ID = zzag.CUSTOM_VAR.toString();
        NAME = zzah.NAME.toString();
        aEO = zzah.DEFAULT_VALUE.toString();
    }

    public zzw(DataLayer dataLayer) {
        super(ID, NAME);
        this.aDZ = dataLayer;
    }

    public zza zzay(Map<String, zza> map) {
        Object obj = this.aDZ.get(zzdm.zzg((zza) map.get(NAME)));
        if (obj != null) {
            return zzdm.zzat(obj);
        }
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aEO);
        return com_google_android_gms_internal_zzaj_zza != null ? com_google_android_gms_internal_zzaj_zza : zzdm.zzchm();
    }

    public boolean zzcdu() {
        return false;
    }
}
