package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzak extends zzam {
    private static final String ID;
    private final zzcx aEa;

    static {
        ID = zzag.EVENT.toString();
    }

    public zzak(zzcx com_google_android_gms_tagmanager_zzcx) {
        super(ID, new String[0]);
        this.aEa = com_google_android_gms_tagmanager_zzcx;
    }

    public zza zzay(Map<String, zza> map) {
        String zzcgj = this.aEa.zzcgj();
        return zzcgj == null ? zzdm.zzchm() : zzdm.zzat(zzcgj);
    }

    public boolean zzcdu() {
        return false;
    }
}
