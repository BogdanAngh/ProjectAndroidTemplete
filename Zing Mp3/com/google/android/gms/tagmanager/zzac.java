package com.google.android.gms.tagmanager;

import android.os.Build;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzac extends zzam {
    private static final String ID;

    static {
        ID = zzag.DEVICE_NAME.toString();
    }

    public zzac() {
        super(ID, new String[0]);
    }

    public zza zzay(Map<String, zza> map) {
        String str = Build.MANUFACTURER;
        Object obj = Build.MODEL;
        if (!(obj.startsWith(str) || str.equals(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_UNKNOWN))) {
            obj = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(obj).length()).append(str).append(" ").append(obj).toString();
        }
        return zzdm.zzat(obj);
    }

    public boolean zzcdu() {
        return true;
    }
}
