package com.google.android.gms.internal;

import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

@zzji
public class zzfh implements zzfe {
    private final zzfi zzbqr;

    public zzfh(zzfi com_google_android_gms_internal_zzfi) {
        this.zzbqr = com_google_android_gms_internal_zzfi;
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        float parseFloat;
        boolean equals = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("transparentBackground"));
        boolean equals2 = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("blur"));
        try {
            if (map.get("blurRadius") != null) {
                parseFloat = Float.parseFloat((String) map.get("blurRadius"));
                this.zzbqr.zzg(equals);
                this.zzbqr.zza(equals2, parseFloat);
            }
        } catch (Throwable e) {
            zzb.zzb("Fail to parse float", e);
        }
        parseFloat = 0.0f;
        this.zzbqr.zzg(equals);
        this.zzbqr.zza(equals2, parseFloat);
    }
}
