package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzcz extends zzam {
    private static final String ID;

    static {
        ID = zzag.SDK_VERSION.toString();
    }

    public zzcz() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat(Integer.valueOf(VERSION.SDK_INT));
    }

    public boolean zzcdu() {
        return true;
    }
}
