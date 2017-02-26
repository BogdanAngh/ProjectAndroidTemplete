package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzbz extends zzak {
    private static final String ID;
    private static final zza zzaMB;

    static {
        ID = zzad.PLATFORM.toString();
        zzaMB = zzdf.zzI("Android");
    }

    public zzbz() {
        super(ID, new String[0]);
    }

    public zza zzE(Map<String, zza> map) {
        return zzaMB;
    }

    public boolean zzyh() {
        return true;
    }
}
