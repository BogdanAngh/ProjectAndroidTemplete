package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzaa;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzmw extends zzg<zzmw> {
    private final Map<String, Object> zzbly;

    public zzmw() {
        this.zzbly = new HashMap();
    }

    private String zzed(String str) {
        zzaa.zzib(str);
        if (str != null && str.startsWith("&")) {
            str = str.substring(1);
        }
        zzaa.zzh(str, "Name can not be empty or \"&\"");
        return str;
    }

    public void set(String str, String str2) {
        this.zzbly.put(zzed(str), str2);
    }

    public String toString() {
        return zzg.zzj(this.zzbly);
    }

    public void zza(zzmw com_google_android_gms_internal_zzmw) {
        zzaa.zzy(com_google_android_gms_internal_zzmw);
        com_google_android_gms_internal_zzmw.zzbly.putAll(this.zzbly);
    }

    public Map<String, Object> zzaap() {
        return Collections.unmodifiableMap(this.zzbly);
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzmw) com_google_android_gms_analytics_zzg);
    }
}
