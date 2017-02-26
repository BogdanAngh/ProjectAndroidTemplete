package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class zzcm extends zzam {
    private static final String ID;
    private static final String aGv;
    private static final String aGw;
    private static final String aGx;
    private static final String aGy;

    static {
        ID = zzag.REGEX_GROUP.toString();
        aGv = zzah.ARG0.toString();
        aGw = zzah.ARG1.toString();
        aGx = zzah.IGNORE_CASE.toString();
        aGy = zzah.GROUP.toString();
    }

    public zzcm() {
        super(ID, aGv, aGw);
    }

    public zza zzay(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aGv);
        zza com_google_android_gms_internal_zzaj_zza2 = (zza) map.get(aGw);
        if (com_google_android_gms_internal_zzaj_zza == null || com_google_android_gms_internal_zzaj_zza == zzdm.zzchm() || com_google_android_gms_internal_zzaj_zza2 == null || com_google_android_gms_internal_zzaj_zza2 == zzdm.zzchm()) {
            return zzdm.zzchm();
        }
        int i = 64;
        if (zzdm.zzk((zza) map.get(aGx)).booleanValue()) {
            i = 66;
        }
        zza com_google_android_gms_internal_zzaj_zza3 = (zza) map.get(aGy);
        int intValue;
        if (com_google_android_gms_internal_zzaj_zza3 != null) {
            Long zzi = zzdm.zzi(com_google_android_gms_internal_zzaj_zza3);
            if (zzi == zzdm.zzchh()) {
                return zzdm.zzchm();
            }
            intValue = zzi.intValue();
            if (intValue < 0) {
                return zzdm.zzchm();
            }
        }
        intValue = 1;
        try {
            CharSequence zzg = zzdm.zzg(com_google_android_gms_internal_zzaj_zza);
            Object obj = null;
            Matcher matcher = Pattern.compile(zzdm.zzg(com_google_android_gms_internal_zzaj_zza2), i).matcher(zzg);
            if (matcher.find() && matcher.groupCount() >= intValue) {
                obj = matcher.group(intValue);
            }
            return obj == null ? zzdm.zzchm() : zzdm.zzat(obj);
        } catch (PatternSyntaxException e) {
            return zzdm.zzchm();
        }
    }

    public boolean zzcdu() {
        return true;
    }
}
