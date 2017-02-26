package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzaf extends zzam {
    private static final String ID;
    private static final String aFk;
    private static final String aFl;
    private static final String aFm;
    private static final String aFn;

    static {
        ID = zzag.ENCODE.toString();
        aFk = zzah.ARG0.toString();
        aFl = zzah.NO_PADDING.toString();
        aFm = zzah.INPUT_FORMAT.toString();
        aFn = zzah.OUTPUT_FORMAT.toString();
    }

    public zzaf() {
        super(ID, aFk);
    }

    public zza zzay(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aFk);
        if (com_google_android_gms_internal_zzaj_zza == null || com_google_android_gms_internal_zzaj_zza == zzdm.zzchm()) {
            return zzdm.zzchm();
        }
        String zzg = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        com_google_android_gms_internal_zzaj_zza = (zza) map.get(aFm);
        if (com_google_android_gms_internal_zzaj_zza == null) {
            Object obj = MimeTypes.BASE_TYPE_TEXT;
        } else {
            String zzg2 = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        }
        com_google_android_gms_internal_zzaj_zza = (zza) map.get(aFn);
        if (com_google_android_gms_internal_zzaj_zza == null) {
            Object obj2 = "base16";
        } else {
            String zzg3 = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        }
        com_google_android_gms_internal_zzaj_zza = (zza) map.get(aFl);
        int i = (com_google_android_gms_internal_zzaj_zza == null || !zzdm.zzk(com_google_android_gms_internal_zzaj_zza).booleanValue()) ? 2 : 3;
        try {
            byte[] bytes;
            String valueOf;
            Object zzq;
            if (MimeTypes.BASE_TYPE_TEXT.equals(obj)) {
                bytes = zzg.getBytes();
            } else if ("base16".equals(obj)) {
                bytes = zzk.zzos(zzg);
            } else if ("base64".equals(obj)) {
                bytes = Base64.decode(zzg, i);
            } else if ("base64url".equals(obj)) {
                bytes = Base64.decode(zzg, i | 8);
            } else {
                zzg3 = "Encode: unknown input format: ";
                valueOf = String.valueOf(obj);
                zzbo.m1698e(valueOf.length() != 0 ? zzg3.concat(valueOf) : new String(zzg3));
                return zzdm.zzchm();
            }
            if ("base16".equals(obj2)) {
                zzq = zzk.zzq(bytes);
            } else if ("base64".equals(obj2)) {
                zzq = Base64.encodeToString(bytes, i);
            } else if ("base64url".equals(obj2)) {
                zzq = Base64.encodeToString(bytes, i | 8);
            } else {
                zzg2 = "Encode: unknown output format: ";
                valueOf = String.valueOf(obj2);
                zzbo.m1698e(valueOf.length() != 0 ? zzg2.concat(valueOf) : new String(zzg2));
                return zzdm.zzchm();
            }
            return zzdm.zzat(zzq);
        } catch (IllegalArgumentException e) {
            zzbo.m1698e("Encode: invalid input:");
            return zzdm.zzchm();
        }
    }

    public boolean zzcdu() {
        return true;
    }
}
