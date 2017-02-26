package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzai.zzc;
import com.google.android.gms.internal.zzai.zzd;
import com.google.android.gms.internal.zzai.zzi;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzal {
    private static void zza(DataLayer dataLayer, zzd com_google_android_gms_internal_zzai_zzd) {
        for (zza zzg : com_google_android_gms_internal_zzai_zzd.zzwe) {
            dataLayer.zzpb(zzdm.zzg(zzg));
        }
    }

    public static void zza(DataLayer dataLayer, zzi com_google_android_gms_internal_zzai_zzi) {
        if (com_google_android_gms_internal_zzai_zzi.zzxt == null) {
            zzbo.zzdi("supplemental missing experimentSupplemental");
            return;
        }
        zza(dataLayer, com_google_android_gms_internal_zzai_zzi.zzxt);
        zzb(dataLayer, com_google_android_gms_internal_zzai_zzi.zzxt);
        zzc(dataLayer, com_google_android_gms_internal_zzai_zzi.zzxt);
    }

    private static void zzb(DataLayer dataLayer, zzd com_google_android_gms_internal_zzai_zzd) {
        for (zza zzc : com_google_android_gms_internal_zzai_zzd.zzwd) {
            Map zzc2 = zzc(zzc);
            if (zzc2 != null) {
                dataLayer.push(zzc2);
            }
        }
    }

    private static Map<String, Object> zzc(zza com_google_android_gms_internal_zzaj_zza) {
        Object zzl = zzdm.zzl(com_google_android_gms_internal_zzaj_zza);
        if (zzl instanceof Map) {
            return (Map) zzl;
        }
        String valueOf = String.valueOf(zzl);
        zzbo.zzdi(new StringBuilder(String.valueOf(valueOf).length() + 36).append("value: ").append(valueOf).append(" is not a map value, ignored.").toString());
        return null;
    }

    private static void zzc(DataLayer dataLayer, zzd com_google_android_gms_internal_zzai_zzd) {
        for (zzc com_google_android_gms_internal_zzai_zzc : com_google_android_gms_internal_zzai_zzd.zzwf) {
            if (com_google_android_gms_internal_zzai_zzc.zzcb == null) {
                zzbo.zzdi("GaExperimentRandom: No key");
            } else {
                Object obj = dataLayer.get(com_google_android_gms_internal_zzai_zzc.zzcb);
                Long valueOf = !(obj instanceof Number) ? null : Long.valueOf(((Number) obj).longValue());
                long j = com_google_android_gms_internal_zzai_zzc.zzvz;
                long j2 = com_google_android_gms_internal_zzai_zzc.zzwa;
                if (!com_google_android_gms_internal_zzai_zzc.zzwb || valueOf == null || valueOf.longValue() < j || valueOf.longValue() > j2) {
                    if (j <= j2) {
                        obj = Long.valueOf(Math.round((Math.random() * ((double) (j2 - j))) + ((double) j)));
                    } else {
                        zzbo.zzdi("GaExperimentRandom: random range invalid");
                    }
                }
                dataLayer.zzpb(com_google_android_gms_internal_zzai_zzc.zzcb);
                Map zzo = dataLayer.zzo(com_google_android_gms_internal_zzai_zzc.zzcb, obj);
                if (com_google_android_gms_internal_zzai_zzc.zzwc > 0) {
                    if (zzo.containsKey("gtm")) {
                        Object obj2 = zzo.get("gtm");
                        if (obj2 instanceof Map) {
                            ((Map) obj2).put("lifetime", Long.valueOf(com_google_android_gms_internal_zzai_zzc.zzwc));
                        } else {
                            zzbo.zzdi("GaExperimentRandom: gtm not a map");
                        }
                    } else {
                        zzo.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(com_google_android_gms_internal_zzai_zzc.zzwc)));
                    }
                }
                dataLayer.push(zzo);
            }
        }
    }
}
