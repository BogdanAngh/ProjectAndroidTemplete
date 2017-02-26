package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzr extends zzam {
    private static final String ID;
    private final String um;

    static {
        ID = zzag.CONTAINER_VERSION.toString();
    }

    public zzr(String str) {
        super(ID, new String[0]);
        this.um = str;
    }

    public zza zzay(Map<String, zza> map) {
        return this.um == null ? zzdm.zzchm() : zzdm.zzat(this.um);
    }

    public boolean zzcdu() {
        return true;
    }
}
