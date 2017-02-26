package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.List;
import java.util.Map;

class zzy extends zzdk {
    private static final String ID;
    private static final String VALUE;
    private static final String aEZ;
    private final DataLayer aDZ;

    static {
        ID = zzag.DATA_LAYER_WRITE.toString();
        VALUE = zzah.VALUE.toString();
        aEZ = zzah.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    }

    public zzy(DataLayer dataLayer) {
        super(ID, VALUE);
        this.aDZ = dataLayer;
    }

    private void zza(zza com_google_android_gms_internal_zzaj_zza) {
        if (com_google_android_gms_internal_zzaj_zza != null && com_google_android_gms_internal_zzaj_zza != zzdm.zzchg()) {
            String zzg = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
            if (zzg != zzdm.zzchl()) {
                this.aDZ.zzpb(zzg);
            }
        }
    }

    private void zzb(zza com_google_android_gms_internal_zzaj_zza) {
        if (com_google_android_gms_internal_zzaj_zza != null && com_google_android_gms_internal_zzaj_zza != zzdm.zzchg()) {
            Object zzl = zzdm.zzl(com_google_android_gms_internal_zzaj_zza);
            if (zzl instanceof List) {
                for (Object zzl2 : (List) zzl2) {
                    if (zzl2 instanceof Map) {
                        this.aDZ.push((Map) zzl2);
                    }
                }
            }
        }
    }

    public void zzba(Map<String, zza> map) {
        zzb((zza) map.get(VALUE));
        zza((zza) map.get(aEZ));
    }
}
