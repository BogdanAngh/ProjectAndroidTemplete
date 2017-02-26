package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzcf extends zzam {
    private static final String ID;

    static {
        ID = zzag.OS_VERSION.toString();
    }

    public zzcf() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat(VERSION.RELEASE);
    }

    public boolean zzcdu() {
        return true;
    }
}
