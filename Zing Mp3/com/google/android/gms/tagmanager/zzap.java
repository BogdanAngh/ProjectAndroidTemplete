package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzap extends zzcd {
    private static final String ID;

    static {
        ID = zzag.GREATER_THAN.toString();
    }

    public zzap() {
        super(ID);
    }

    protected boolean zza(zzdl com_google_android_gms_tagmanager_zzdl, zzdl com_google_android_gms_tagmanager_zzdl2, Map<String, zza> map) {
        return com_google_android_gms_tagmanager_zzdl.zza(com_google_android_gms_tagmanager_zzdl2) > 0;
    }
}
