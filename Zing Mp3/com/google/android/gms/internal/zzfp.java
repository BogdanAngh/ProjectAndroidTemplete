package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.util.Map;

@zzji
class zzfp implements zzfe {
    zzfp() {
    }

    private int zzh(Map<String, String> map) throws NullPointerException, NumberFormatException {
        int parseInt = Integer.parseInt((String) map.get("playbackState"));
        return (parseInt < 0 || 3 < parseInt) ? 0 : parseInt;
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        Throwable e;
        if (((Boolean) zzdr.zzbhe.get()).booleanValue()) {
            zzmi com_google_android_gms_internal_zzmi;
            zzmi zzxn = com_google_android_gms_internal_zzmd.zzxn();
            if (zzxn == null) {
                try {
                    zzxn = new zzmi(com_google_android_gms_internal_zzmd, Float.parseFloat((String) map.get("duration")));
                    com_google_android_gms_internal_zzmd.zza(zzxn);
                    com_google_android_gms_internal_zzmi = zzxn;
                } catch (NullPointerException e2) {
                    e = e2;
                    zzb.zzb("Unable to parse videoMeta message.", e);
                    zzu.zzgq().zza(e, "VideoMetaGmsgHandler.onGmsg");
                    return;
                } catch (NumberFormatException e3) {
                    e = e3;
                    zzb.zzb("Unable to parse videoMeta message.", e);
                    zzu.zzgq().zza(e, "VideoMetaGmsgHandler.onGmsg");
                    return;
                }
            }
            com_google_android_gms_internal_zzmi = zzxn;
            boolean equals = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("muted"));
            float parseFloat = Float.parseFloat((String) map.get("currentTime"));
            int zzh = zzh(map);
            String str = (String) map.get("aspectRatio");
            float parseFloat2 = TextUtils.isEmpty(str) ? 0.0f : Float.parseFloat(str);
            if (zzb.zzbi(3)) {
                zzb.zzdg(new StringBuilder(String.valueOf(str).length() + 79).append("Video Meta GMSG: isMuted : ").append(equals).append(" , playbackState : ").append(zzh).append(" , aspectRatio : ").append(str).toString());
            }
            com_google_android_gms_internal_zzmi.zza(parseFloat, zzh, equals, parseFloat2);
        }
    }
}
