package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzafw;
import com.google.android.gms.internal.zzafw.zzc;
import com.google.android.gms.internal.zzafw.zzd;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class zzbh {
    private static zza zzam(Object obj) throws JSONException {
        return zzdm.zzat(zzan(obj));
    }

    static Object zzan(Object obj) throws JSONException {
        if (obj instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        } else if (JSONObject.NULL.equals(obj)) {
            throw new RuntimeException("JSON nulls are not supported");
        } else if (!(obj instanceof JSONObject)) {
            return obj;
        } else {
            JSONObject jSONObject = (JSONObject) obj;
            Map hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, zzan(jSONObject.get(str)));
            }
            return hashMap;
        }
    }

    public static zzc zzpm(String str) throws JSONException {
        zza zzam = zzam(new JSONObject(str));
        zzd zzckw = zzc.zzckw();
        for (int i = 0; i < zzam.zzxz.length; i++) {
            zzckw.zzc(zzafw.zza.zzcku().zzb(zzah.INSTANCE_NAME.toString(), zzam.zzxz[i]).zzb(zzah.FUNCTION.toString(), zzdm.zzpx(zzn.zzcdw())).zzb(zzn.zzcdx(), zzam.zzya[i]).zzckv());
        }
        return zzckw.zzcky();
    }
}
