package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzg;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzmu extends zzg<zzmu> {
    private Map<Integer, String> bX;

    public zzmu() {
        this.bX = new HashMap(4);
    }

    public String toString() {
        Map hashMap = new HashMap();
        for (Entry entry : this.bX.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            hashMap.put(new StringBuilder(String.valueOf(valueOf).length() + 9).append("dimension").append(valueOf).toString(), entry.getValue());
        }
        return zzg.zzj(hashMap);
    }

    public void zza(zzmu com_google_android_gms_internal_zzmu) {
        com_google_android_gms_internal_zzmu.bX.putAll(this.bX);
    }

    public Map<Integer, String> zzaan() {
        return Collections.unmodifiableMap(this.bX);
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzmu) com_google_android_gms_analytics_zzg);
    }
}
