package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;
import java.util.Set;

public abstract class zzci extends zzam {
    private static final String aFk;
    private static final String aGj;

    static {
        aFk = zzah.ARG0.toString();
        aGj = zzah.ARG1.toString();
    }

    public zzci(String str) {
        super(str, aFk, aGj);
    }

    protected abstract boolean zza(zza com_google_android_gms_internal_zzaj_zza, zza com_google_android_gms_internal_zzaj_zza2, Map<String, zza> map);

    public zza zzay(Map<String, zza> map) {
        for (zza com_google_android_gms_internal_zzaj_zza : map.values()) {
            if (com_google_android_gms_internal_zzaj_zza == zzdm.zzchm()) {
                return zzdm.zzat(Boolean.valueOf(false));
            }
        }
        zza com_google_android_gms_internal_zzaj_zza2 = (zza) map.get(aFk);
        zza com_google_android_gms_internal_zzaj_zza3 = (zza) map.get(aGj);
        boolean zza = (com_google_android_gms_internal_zzaj_zza2 == null || com_google_android_gms_internal_zzaj_zza3 == null) ? false : zza(com_google_android_gms_internal_zzaj_zza2, com_google_android_gms_internal_zzaj_zza3, map);
        return zzdm.zzat(Boolean.valueOf(zza));
    }

    public boolean zzcdu() {
        return true;
    }

    public /* bridge */ /* synthetic */ String zzcfg() {
        return super.zzcfg();
    }

    public /* bridge */ /* synthetic */ Set zzcfh() {
        return super.zzcfh();
    }
}
