package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zze extends zzam {
    private static final String ID;
    private static final String aDP;
    private static final String aDQ;
    private final Context zzahs;

    static {
        ID = zzag.ADWORDS_CLICK_REFERRER.toString();
        aDP = zzah.COMPONENT.toString();
        aDQ = zzah.CONVERSION_ID.toString();
    }

    public zze(Context context) {
        super(ID, aDQ);
        this.zzahs = context;
    }

    public zza zzay(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aDQ);
        if (com_google_android_gms_internal_zzaj_zza == null) {
            return zzdm.zzchm();
        }
        String zzg = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        com_google_android_gms_internal_zzaj_zza = (zza) map.get(aDP);
        String zzj = zzbf.zzj(this.zzahs, zzg, com_google_android_gms_internal_zzaj_zza != null ? zzdm.zzg(com_google_android_gms_internal_zzaj_zza) : null);
        return zzj != null ? zzdm.zzat(zzj) : zzdm.zzchm();
    }

    public boolean zzcdu() {
        return true;
    }
}
