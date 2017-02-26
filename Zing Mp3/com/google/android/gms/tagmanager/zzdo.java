package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzdo extends zzam {
    private static final String ID;
    private static final String aFk;

    static {
        ID = zzag.UPPERCASE_STRING.toString();
        aFk = zzah.ARG0.toString();
    }

    public zzdo() {
        super(ID, aFk);
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat(zzdm.zzg((zza) map.get(aFk)).toUpperCase());
    }

    public boolean zzcdu() {
        return true;
    }
}
