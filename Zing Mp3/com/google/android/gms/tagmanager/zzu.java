package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class zzu extends zzam {
    private static final String ID;
    private static final String aDR;
    private static final String aED;
    private final zza aEE;

    public interface zza {
        Object zzf(String str, Map<String, Object> map);
    }

    static {
        ID = zzag.FUNCTION_CALL.toString();
        aED = zzah.FUNCTION_CALL_NAME.toString();
        aDR = zzah.ADDITIONAL_PARAMS.toString();
    }

    public zzu(zza com_google_android_gms_tagmanager_zzu_zza) {
        super(ID, aED);
        this.aEE = com_google_android_gms_tagmanager_zzu_zza;
    }

    public com.google.android.gms.internal.zzaj.zza zzay(Map<String, com.google.android.gms.internal.zzaj.zza> map) {
        String zzg = zzdm.zzg((com.google.android.gms.internal.zzaj.zza) map.get(aED));
        Map hashMap = new HashMap();
        com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza = (com.google.android.gms.internal.zzaj.zza) map.get(aDR);
        if (com_google_android_gms_internal_zzaj_zza != null) {
            Object zzl = zzdm.zzl(com_google_android_gms_internal_zzaj_zza);
            if (zzl instanceof Map) {
                for (Entry entry : ((Map) zzl).entrySet()) {
                    hashMap.put(entry.getKey().toString(), entry.getValue());
                }
            } else {
                zzbo.zzdi("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return zzdm.zzchm();
            }
        }
        try {
            return zzdm.zzat(this.aEE.zzf(zzg, hashMap));
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzbo.zzdi(new StringBuilder((String.valueOf(zzg).length() + 34) + String.valueOf(valueOf).length()).append("Custom macro/tag ").append(zzg).append(" threw exception ").append(valueOf).toString());
            return zzdm.zzchm();
        }
    }

    public boolean zzcdu() {
        return false;
    }
}
