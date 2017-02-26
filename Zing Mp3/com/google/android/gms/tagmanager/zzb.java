package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzb extends zzam {
    private static final String ID;
    private final zza aDO;

    static {
        ID = zzag.ADVERTISER_ID.toString();
    }

    public zzb(Context context) {
        this(zza.zzdw(context));
    }

    zzb(zza com_google_android_gms_tagmanager_zza) {
        super(ID, new String[0]);
        this.aDO = com_google_android_gms_tagmanager_zza;
        this.aDO.zzcdo();
    }

    public zza zzay(Map<String, zza> map) {
        String zzcdo = this.aDO.zzcdo();
        return zzcdo == null ? zzdm.zzchm() : zzdm.zzat(zzcdo);
    }

    public boolean zzcdu() {
        return false;
    }
}
