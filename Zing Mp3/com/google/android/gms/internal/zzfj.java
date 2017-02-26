package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import org.json.JSONObject;

@zzji
public class zzfj implements zzfe {
    final HashMap<String, zzlq<JSONObject>> zzbqs;

    public zzfj() {
        this.zzbqs = new HashMap();
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        zzh((String) map.get("request_id"), (String) map.get("fetched_ad"));
    }

    public Future<JSONObject> zzbd(String str) {
        Future com_google_android_gms_internal_zzlq = new zzlq();
        this.zzbqs.put(str, com_google_android_gms_internal_zzlq);
        return com_google_android_gms_internal_zzlq;
    }

    public void zzbe(String str) {
        zzlq com_google_android_gms_internal_zzlq = (zzlq) this.zzbqs.get(str);
        if (com_google_android_gms_internal_zzlq == null) {
            zzb.m1695e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        if (!com_google_android_gms_internal_zzlq.isDone()) {
            com_google_android_gms_internal_zzlq.cancel(true);
        }
        this.zzbqs.remove(str);
    }

    public void zzh(String str, String str2) {
        zzb.zzdg("Received ad from the cache.");
        zzlq com_google_android_gms_internal_zzlq = (zzlq) this.zzbqs.get(str);
        if (com_google_android_gms_internal_zzlq == null) {
            zzb.m1695e("Could not find the ad request for the corresponding ad response.");
            return;
        }
        try {
            com_google_android_gms_internal_zzlq.zzh(new JSONObject(str2));
        } catch (Throwable e) {
            zzb.zzb("Failed constructing JSON object from value passed from javascript", e);
            com_google_android_gms_internal_zzlq.zzh(null);
        } finally {
            this.zzbqs.remove(str);
        }
    }
}
