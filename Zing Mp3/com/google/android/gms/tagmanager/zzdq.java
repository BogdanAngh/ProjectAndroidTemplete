package com.google.android.gms.tagmanager;

import com.google.android.exoplayer.C0989C;
import com.google.android.gms.internal.zzaj.zza;
import com.mp3download.zingmp3.C1569R;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class zzdq {
    private static zzce<zza> zza(zzce<zza> com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza) {
        try {
            return new zzce(zzdm.zzat(zzqe(zzdm.zzg((zza) com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza.getObject()))), com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza.zzcfu());
        } catch (Throwable e) {
            zzbo.zzb("Escape URI: unsupported encoding", e);
            return com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza;
        }
    }

    private static zzce<zza> zza(zzce<zza> com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza, int i) {
        if (zzn((zza) com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza.getObject())) {
            switch (i) {
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                    return zza(com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza);
                default:
                    zzbo.m1698e("Unsupported Value Escaping: " + i);
                    return com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza;
            }
        }
        zzbo.m1698e("Escaping can only be applied to strings.");
        return com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza;
    }

    static zzce<zza> zza(zzce<zza> com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza, int... iArr) {
        zzce zza;
        for (int zza2 : iArr) {
            zza = zza(zza, zza2);
        }
        return zza;
    }

    private static boolean zzn(zza com_google_android_gms_internal_zzaj_zza) {
        return zzdm.zzl(com_google_android_gms_internal_zzaj_zza) instanceof String;
    }

    static String zzqe(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, C0989C.UTF8_NAME).replaceAll("\\+", "%20");
    }
}
