package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzck extends zzam {
    private static final String ID;
    private static final String aGt;
    private static final String aGu;

    static {
        ID = zzag.RANDOM.toString();
        aGt = zzah.MIN.toString();
        aGu = zzah.MAX.toString();
    }

    public zzck() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        double doubleValue;
        double d;
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aGt);
        zza com_google_android_gms_internal_zzaj_zza2 = (zza) map.get(aGu);
        if (!(com_google_android_gms_internal_zzaj_zza == null || com_google_android_gms_internal_zzaj_zza == zzdm.zzchm() || com_google_android_gms_internal_zzaj_zza2 == null || com_google_android_gms_internal_zzaj_zza2 == zzdm.zzchm())) {
            zzdl zzh = zzdm.zzh(com_google_android_gms_internal_zzaj_zza);
            zzdl zzh2 = zzdm.zzh(com_google_android_gms_internal_zzaj_zza2);
            if (!(zzh == zzdm.zzchk() || zzh2 == zzdm.zzchk())) {
                double doubleValue2 = zzh.doubleValue();
                doubleValue = zzh2.doubleValue();
                if (doubleValue2 <= doubleValue) {
                    d = doubleValue2;
                    return zzdm.zzat(Long.valueOf(Math.round(((doubleValue - d) * Math.random()) + d)));
                }
            }
        }
        doubleValue = 2.147483647E9d;
        d = 0.0d;
        return zzdm.zzat(Long.valueOf(Math.round(((doubleValue - d) * Math.random()) + d)));
    }

    public boolean zzcdu() {
        return false;
    }
}
