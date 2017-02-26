package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzbq extends zzam {
    private static final String ID;
    private static final String aFk;

    static {
        ID = zzag.LOWERCASE_STRING.toString();
        aFk = zzah.ARG0.toString();
    }

    public zzbq() {
        super(ID, aFk);
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat(zzdm.zzg((zza) map.get(aFk)).toLowerCase());
    }

    public boolean zzcdu() {
        return true;
    }
}
