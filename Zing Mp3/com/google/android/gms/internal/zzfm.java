package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzfm implements zzfe {
    private final Object zzako;
    private final Map<String, zza> zzbqx;

    public interface zza {
        void zzbf(String str);

        void zzc(JSONObject jSONObject);
    }

    public zzfm() {
        this.zzako = new Object();
        this.zzbqx = new HashMap();
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get(TtmlNode.ATTR_ID);
        String str2 = (String) map.get("fail");
        String str3 = (String) map.get("fail_reason");
        String str4 = (String) map.get("result");
        synchronized (this.zzako) {
            zza com_google_android_gms_internal_zzfm_zza = (zza) this.zzbqx.remove(str);
            if (com_google_android_gms_internal_zzfm_zza == null) {
                str2 = "Received result for unexpected method invocation: ";
                str = String.valueOf(str);
                zzb.zzdi(str.length() != 0 ? str2.concat(str) : new String(str2));
            } else if (!TextUtils.isEmpty(str2)) {
                com_google_android_gms_internal_zzfm_zza.zzbf(str3);
            } else if (str4 == null) {
                com_google_android_gms_internal_zzfm_zza.zzbf("No result.");
            } else {
                try {
                    com_google_android_gms_internal_zzfm_zza.zzc(new JSONObject(str4));
                } catch (JSONException e) {
                    com_google_android_gms_internal_zzfm_zza.zzbf(e.getMessage());
                }
            }
        }
    }
}
