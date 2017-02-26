package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzc extends zzam {
    private static final String ID;
    private final zza aDO;

    static {
        ID = zzag.ADVERTISING_TRACKING_ENABLED.toString();
    }

    public zzc(Context context) {
        this(zza.zzdw(context));
    }

    zzc(zza com_google_android_gms_tagmanager_zza) {
        super(ID, new String[0]);
        this.aDO = com_google_android_gms_tagmanager_zza;
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat(Boolean.valueOf(!this.aDO.isLimitAdTrackingEnabled()));
    }

    public boolean zzcdu() {
        return false;
    }
}
