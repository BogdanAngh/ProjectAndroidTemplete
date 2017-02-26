package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

public class zzah extends zzdh {
    private static final String ID;

    static {
        ID = zzag.EQUALS.toString();
    }

    public zzah() {
        super(ID);
    }

    protected boolean zza(String str, String str2, Map<String, zza> map) {
        return str.equals(str2);
    }
}
