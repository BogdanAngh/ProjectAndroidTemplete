package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.internal.zzu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzgv {
    public List<String> zza(JSONObject jSONObject, String str) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        List arrayList = new ArrayList(optJSONArray.length());
        for (int i = 0; i < optJSONArray.length(); i++) {
            arrayList.add(optJSONArray.getString(i));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public void zza(Context context, String str, zzko com_google_android_gms_internal_zzko, String str2, boolean z, List<String> list) {
        if (list != null && !list.isEmpty()) {
            String str3 = z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO;
            for (String replaceAll : list) {
                String replaceAll2 = replaceAll2.replaceAll("@gw_adlocid@", str2).replaceAll("@gw_adnetrefresh@", str3).replaceAll("@gw_qdata@", com_google_android_gms_internal_zzko.zzcsk.zzbvp).replaceAll("@gw_sdkver@", str).replaceAll("@gw_sessid@", zzu.zzgq().getSessionId()).replaceAll("@gw_seqnum@", com_google_android_gms_internal_zzko.zzcjx);
                if (!TextUtils.isEmpty(com_google_android_gms_internal_zzko.zzcsl)) {
                    replaceAll2 = replaceAll2.replaceAll("@gw_adnetstatus@", com_google_android_gms_internal_zzko.zzcsl);
                }
                if (com_google_android_gms_internal_zzko.zzbwm != null) {
                    replaceAll2 = replaceAll2.replaceAll("@gw_adnetid@", com_google_android_gms_internal_zzko.zzbwm.zzbut).replaceAll("@gw_allocid@", com_google_android_gms_internal_zzko.zzbwm.zzbuv);
                }
                Future future = (Future) new zzll(context, str, replaceAll2).zzrz();
            }
        }
    }
}
