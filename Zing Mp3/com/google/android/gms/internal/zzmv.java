package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzg;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzmv extends zzg<zzmv> {
    private Map<Integer, Double> bY;

    public zzmv() {
        this.bY = new HashMap(4);
    }

    public String toString() {
        Map hashMap = new HashMap();
        for (Entry entry : this.bY.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            hashMap.put(new StringBuilder(String.valueOf(valueOf).length() + 6).append("metric").append(valueOf).toString(), entry.getValue());
        }
        return zzg.zzj(hashMap);
    }

    public void zza(zzmv com_google_android_gms_internal_zzmv) {
        com_google_android_gms_internal_zzmv.bY.putAll(this.bY);
    }

    public Map<Integer, Double> zzaao() {
        return Collections.unmodifiableMap(this.bY);
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzmv) com_google_android_gms_analytics_zzg);
    }
}
