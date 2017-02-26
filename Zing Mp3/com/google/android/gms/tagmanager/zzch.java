package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzch extends zzam {
    private static final String ID;
    private static final zza aGi;

    static {
        ID = zzag.PLATFORM.toString();
        aGi = zzdm.zzat("Android");
    }

    public zzch() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        return aGi;
    }

    public boolean zzcdu() {
        return true;
    }
}
