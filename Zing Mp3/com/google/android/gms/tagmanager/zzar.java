package com.google.android.gms.tagmanager;

import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class zzar extends zzam {
    private static final String ID;
    private static final String aFk;
    private static final String aFm;
    private static final String aFq;

    static {
        ID = zzag.HASH.toString();
        aFk = zzah.ARG0.toString();
        aFq = zzah.ALGORITHM.toString();
        aFm = zzah.INPUT_FORMAT.toString();
    }

    public zzar() {
        super(ID, aFk);
    }

    private byte[] zzf(String str, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(str);
        instance.update(bArr);
        return instance.digest();
    }

    public zza zzay(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aFk);
        if (com_google_android_gms_internal_zzaj_zza == null || com_google_android_gms_internal_zzaj_zza == zzdm.zzchm()) {
            return zzdm.zzchm();
        }
        byte[] bytes;
        String zzg = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        com_google_android_gms_internal_zzaj_zza = (zza) map.get(aFq);
        String zzg2 = com_google_android_gms_internal_zzaj_zza == null ? "MD5" : zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        com_google_android_gms_internal_zzaj_zza = (zza) map.get(aFm);
        Object zzg3 = com_google_android_gms_internal_zzaj_zza == null ? MimeTypes.BASE_TYPE_TEXT : zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
        if (MimeTypes.BASE_TYPE_TEXT.equals(zzg3)) {
            bytes = zzg.getBytes();
        } else if ("base16".equals(zzg3)) {
            bytes = zzk.zzos(zzg);
        } else {
            zzg2 = "Hash: unknown input format: ";
            String valueOf = String.valueOf(zzg3);
            zzbo.m1698e(valueOf.length() != 0 ? zzg2.concat(valueOf) : new String(zzg2));
            return zzdm.zzchm();
        }
        try {
            return zzdm.zzat(zzk.zzq(zzf(zzg2, bytes)));
        } catch (NoSuchAlgorithmException e) {
            zzg = "Hash: unknown algorithm: ";
            valueOf = String.valueOf(zzg2);
            zzbo.m1698e(valueOf.length() != 0 ? zzg.concat(valueOf) : new String(zzg));
            return zzdm.zzchm();
        }
    }

    public boolean zzcdu() {
        return true;
    }
}
