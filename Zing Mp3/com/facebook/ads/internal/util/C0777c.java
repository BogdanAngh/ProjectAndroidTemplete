package com.facebook.ads.internal.util;

import android.support.annotation.Nullable;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.util.c */
public class C0777c {
    public static String f1410a;
    private String f1411b;
    private Map<String, Object> f1412c;
    private int f1413d;
    private String f1414e;

    /* renamed from: com.facebook.ads.internal.util.c.a */
    public enum C0775a {
        OPEN_STORE(0),
        OPEN_LINK(1),
        XOUT(2),
        OPEN_URL(3),
        SHOW_INTERSTITIAL(4);
        
        int f1406f;

        private C0775a(int i) {
            this.f1406f = i;
        }
    }

    /* renamed from: com.facebook.ads.internal.util.c.b */
    public enum C0776b {
        LOADING_AD(0);
        
        int f1409b;

        private C0776b(int i) {
            this.f1409b = i;
        }
    }

    static {
        f1410a = null;
    }

    public C0777c(String str, Map<String, Object> map, int i, String str2) {
        this.f1411b = str;
        this.f1412c = map;
        this.f1413d = i;
        this.f1414e = str2;
    }

    public static C0777c m1594a(long j, C0775a c0775a, String str) {
        long currentTimeMillis = System.currentTimeMillis();
        Map hashMap = new HashMap();
        hashMap.put("Time", String.valueOf(currentTimeMillis - j));
        hashMap.put("AdAction", String.valueOf(c0775a.f1406f));
        return new C0777c("bounceback", hashMap, (int) (currentTimeMillis / 1000), str);
    }

    public static C0777c m1595a(C0776b c0776b, AdPlacementType adPlacementType, long j, String str) {
        Map hashMap = new HashMap();
        hashMap.put("LatencyType", String.valueOf(c0776b.f1409b));
        hashMap.put("AdPlacementType", adPlacementType.toString());
        hashMap.put("Time", String.valueOf(j));
        String str2 = "latency";
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        if (str == null) {
            str = f1410a;
        }
        return new C0777c(str2, hashMap, currentTimeMillis, str);
    }

    public static C0777c m1596a(@Nullable Throwable th, @Nullable String str) {
        Map hashMap = new HashMap();
        if (th != null) {
            hashMap.put("ex", th.getClass().getSimpleName());
            hashMap.put("ex_msg", th.getMessage());
        }
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        String str2 = NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE;
        if (str == null) {
            str = f1410a;
        }
        return new C0777c(str2, hashMap, currentTimeMillis, str);
    }

    public JSONObject m1597a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_NAME, this.f1411b);
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_DATA, new JSONObject(this.f1412c));
            jSONObject.put("time", this.f1413d);
            jSONObject.put("request_id", this.f1414e);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
