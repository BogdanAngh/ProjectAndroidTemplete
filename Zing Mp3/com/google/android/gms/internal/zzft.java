package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.zzc;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Map;
import java.util.concurrent.Future;

@zzji
public class zzft implements zzfe {
    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        zzfr zzhj = zzu.zzhj();
        if (!map.containsKey("abort")) {
            String str = (String) map.get("src");
            if (str == null) {
                zzb.zzdi("Precache video action is missing the src parameter.");
                return;
            }
            int parseInt;
            try {
                parseInt = Integer.parseInt((String) map.get("player"));
            } catch (NumberFormatException e) {
                parseInt = 0;
            }
            String str2 = map.containsKey("mimetype") ? (String) map.get("mimetype") : BuildConfig.FLAVOR;
            if (zzhj.zzf(com_google_android_gms_internal_zzmd)) {
                zzb.zzdi("Precache task already running.");
                return;
            }
            zzc.zzu(com_google_android_gms_internal_zzmd.zzec());
            Future future = (Future) new zzfq(com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zzmd.zzec().zzamp.zza(com_google_android_gms_internal_zzmd, parseInt, str2), str).zzrz();
        } else if (!zzhj.zze(com_google_android_gms_internal_zzmd)) {
            zzb.zzdi("Precache abort but no preload task running.");
        }
    }
}
