package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class zzbj extends zzam {
    private static final String ID;

    static {
        ID = zzag.LANGUAGE.toString();
    }

    public zzbj() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return zzdm.zzchm();
        }
        String language = locale.getLanguage();
        return language == null ? zzdm.zzchm() : zzdm.zzat(language.toLowerCase());
    }

    public boolean zzcdu() {
        return false;
    }

    public /* bridge */ /* synthetic */ String zzcfg() {
        return super.zzcfg();
    }

    public /* bridge */ /* synthetic */ Set zzcfh() {
        return super.zzcfh();
    }
}
