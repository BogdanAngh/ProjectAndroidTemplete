package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzdi extends zzam {
    private static final String ID;

    static {
        ID = zzag.TIME.toString();
    }

    public zzdi() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat(Long.valueOf(System.currentTimeMillis()));
    }

    public boolean zzcdu() {
        return false;
    }
}
