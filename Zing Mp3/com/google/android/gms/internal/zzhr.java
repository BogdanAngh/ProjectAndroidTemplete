package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.util.Map;

@zzji
public class zzhr {
    private final zzmd zzbnz;
    private final boolean zzbyr;
    private final String zzbys;

    public zzhr(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzbys = (String) map.get("forceOrientation");
        if (map.containsKey("allowOrientationChange")) {
            this.zzbyr = Boolean.parseBoolean((String) map.get("allowOrientationChange"));
        } else {
            this.zzbyr = true;
        }
    }

    public void execute() {
        if (this.zzbnz == null) {
            zzb.zzdi("AdWebView is null");
            return;
        }
        int zzvx = "portrait".equalsIgnoreCase(this.zzbys) ? zzu.zzgo().zzvx() : "landscape".equalsIgnoreCase(this.zzbys) ? zzu.zzgo().zzvw() : this.zzbyr ? -1 : zzu.zzgo().zzvy();
        this.zzbnz.setRequestedOrientation(zzvx);
    }
}
