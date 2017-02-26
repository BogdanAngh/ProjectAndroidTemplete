package com.facebook.ads.internal.util;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.util.k */
public class C0787k {
    private final String f1430a;
    private final String f1431b;
    private final String f1432c;
    private final List<String> f1433d;
    private final String f1434e;
    private final String f1435f;

    private C0787k(String str, String str2, String str3, List<String> list, String str4, String str5) {
        this.f1430a = str;
        this.f1431b = str2;
        this.f1432c = str3;
        this.f1433d = list;
        this.f1434e = str4;
        this.f1435f = str5;
    }

    public static C0787k m1638a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        String optString = jSONObject.optString("package");
        String optString2 = jSONObject.optString("appsite");
        String optString3 = jSONObject.optString("appsite_url");
        JSONArray optJSONArray = jSONObject.optJSONArray("key_hashes");
        List arrayList = new ArrayList();
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                arrayList.add(optJSONArray.optString(i));
            }
        }
        return new C0787k(optString, optString2, optString3, arrayList, jSONObject.optString("market_uri"), jSONObject.optString("fallback_url"));
    }

    public String m1639a() {
        return this.f1430a;
    }

    public String m1640b() {
        return this.f1431b;
    }

    public String m1641c() {
        return this.f1432c;
    }
}
