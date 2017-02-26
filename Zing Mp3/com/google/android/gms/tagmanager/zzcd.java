package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

abstract class zzcd extends zzci {
    public zzcd(String str) {
        super(str);
    }

    protected boolean zza(zza com_google_android_gms_internal_zzaj_zza, zza com_google_android_gms_internal_zzaj_zza2, Map<String, zza> map) {
        zzdl zzh = zzdm.zzh(com_google_android_gms_internal_zzaj_zza);
        zzdl zzh2 = zzdm.zzh(com_google_android_gms_internal_zzaj_zza2);
        return (zzh == zzdm.zzchk() || zzh2 == zzdm.zzchk()) ? false : zza(zzh, zzh2, (Map) map);
    }

    protected abstract boolean zza(zzdl com_google_android_gms_tagmanager_zzdl, zzdl com_google_android_gms_tagmanager_zzdl2, Map<String, zza> map);
}
