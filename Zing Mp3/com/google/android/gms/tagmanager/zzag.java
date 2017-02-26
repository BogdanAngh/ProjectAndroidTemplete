package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzag extends zzdh {
    private static final String ID;

    static {
        ID = com.google.android.gms.internal.zzag.ENDS_WITH.toString();
    }

    public zzag() {
        super(ID);
    }

    protected boolean zza(String str, String str2, Map<String, zza> map) {
        return str.endsWith(str2);
    }
}
