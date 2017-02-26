package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzaq extends zzam {
    private static final String ID;

    static {
        ID = zzag.GTM_VERSION.toString();
    }

    public zzaq() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat("4.00");
    }

    public boolean zzcdu() {
        return true;
    }
}
