package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

abstract class zzdh extends zzci {
    public zzdh(String str) {
        super(str);
    }

    protected boolean zza(zza com_google_android_gms_internal_zzaj_zza, zza com_google_android_gms_internal_zzaj_zza2, Map<String, zza> map) {
        String zzg = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        String zzg2 = zzdm.zzg(com_google_android_gms_internal_zzaj_zza2);
        return (zzg == zzdm.zzchl() || zzg2 == zzdm.zzchl()) ? false : zza(zzg, zzg2, (Map) map);
    }

    protected abstract boolean zza(String str, String str2, Map<String, zza> map);
}
