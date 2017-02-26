package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzs extends zzdh {
    private static final String ID;

    static {
        ID = zzag.CONTAINS.toString();
    }

    public zzs() {
        super(ID);
    }

    protected boolean zza(String str, String str2, Map<String, zza> map) {
        return str.contains(str2);
    }
}
