package com.facebook.ads.internal.adapters;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.internal.util.C0781f;
import com.facebook.ads.internal.util.C0782g;
import com.facebook.ads.internal.util.C0782g.C0468a;
import com.facebook.ads.internal.util.C0785i;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.o */
public class C0474o implements C0468a {
    private final String f477a;
    private final String f478b;
    private final String f479c;
    private final C0781f f480d;
    private final String f481e;
    private final Collection<String> f482f;
    private final Map<String, String> f483g;
    private final String f484h;
    private final int f485i;
    private final int f486j;
    private final int f487k;
    private final String f488l;

    private C0474o(String str, String str2, String str3, C0781f c0781f, String str4, Collection<String> collection, Map<String, String> map, String str5, int i, int i2, int i3, String str6) {
        this.f477a = str;
        this.f478b = str2;
        this.f479c = str3;
        this.f480d = c0781f;
        this.f481e = str4;
        this.f482f = collection;
        this.f483g = map;
        this.f484h = str5;
        this.f485i = i;
        this.f486j = i2;
        this.f487k = i3;
        this.f488l = str6;
    }

    public static C0474o m616a(Bundle bundle) {
        return new C0474o(bundle.getString("markup"), null, bundle.getString("native_impression_report_url"), C0781f.NONE, BuildConfig.FLAVOR, null, null, bundle.getString("request_id"), bundle.getInt("viewability_check_initial_delay"), bundle.getInt("viewability_check_interval"), bundle.getInt("skip_after_seconds", 0), bundle.getString("ct"));
    }

    public static C0474o m617a(JSONObject jSONObject) {
        int i = 0;
        if (jSONObject == null) {
            return null;
        }
        JSONArray jSONArray;
        String optString = jSONObject.optString("markup");
        String optString2 = jSONObject.optString("activation_command");
        String optString3 = jSONObject.optString("native_impression_report_url");
        String optString4 = jSONObject.optString("request_id");
        String a = C0785i.m1621a(jSONObject, "ct");
        C0781f a2 = C0781f.m1607a(jSONObject.optString("invalidation_behavior"));
        String optString5 = jSONObject.optString("invalidation_report_url");
        try {
            jSONArray = new JSONArray(jSONObject.optString("detection_strings"));
        } catch (JSONException e) {
            e.printStackTrace();
            jSONArray = null;
        }
        Collection a3 = C0782g.m1608a(jSONArray);
        JSONObject optJSONObject = jSONObject.optJSONObject(TtmlNode.TAG_METADATA);
        Map hashMap = new HashMap();
        if (optJSONObject != null) {
            Iterator keys = optJSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, optJSONObject.optString(str));
            }
        }
        int i2 = AdError.NETWORK_ERROR_CODE;
        int parseInt = hashMap.containsKey("viewability_check_initial_delay") ? Integer.parseInt((String) hashMap.get("viewability_check_initial_delay")) : 0;
        if (hashMap.containsKey("viewability_check_interval")) {
            i2 = Integer.parseInt((String) hashMap.get("viewability_check_interval"));
        }
        if (hashMap.containsKey("skip_after_seconds")) {
            i = Integer.parseInt((String) hashMap.get("skip_after_seconds"));
        }
        return new C0474o(optString, optString2, optString3, a2, optString5, a3, hashMap, optString4, parseInt, i2, i, a);
    }

    public static C0474o m618b(Intent intent) {
        return new C0474o(C0785i.m1623a(intent.getByteArrayExtra("markup")), intent.getStringExtra("activation_command"), intent.getStringExtra("native_impression_report_url"), C0781f.NONE, BuildConfig.FLAVOR, null, null, intent.getStringExtra("request_id"), intent.getIntExtra("viewability_check_initial_delay", 0), intent.getIntExtra("viewability_check_interval", AdError.NETWORK_ERROR_CODE), intent.getIntExtra(AudienceNetworkActivity.SKIP_DELAY_SECONDS_KEY, 0), intent.getStringExtra("ct"));
    }

    public String m619B() {
        return this.f488l;
    }

    public C0781f m620D() {
        return this.f480d;
    }

    public String m621E() {
        return this.f481e;
    }

    public Collection<String> m622F() {
        return this.f482f;
    }

    public String m623a() {
        return this.f477a;
    }

    public void m624a(Intent intent) {
        intent.putExtra("markup", C0785i.m1632a(this.f477a));
        intent.putExtra("activation_command", this.f478b);
        intent.putExtra("native_impression_report_url", this.f479c);
        intent.putExtra("request_id", this.f484h);
        intent.putExtra("viewability_check_initial_delay", this.f485i);
        intent.putExtra("viewability_check_interval", this.f486j);
        intent.putExtra(AudienceNetworkActivity.SKIP_DELAY_SECONDS_KEY, this.f487k);
        intent.putExtra("ct", this.f488l);
    }

    public String m625b() {
        return this.f478b;
    }

    public String m626c() {
        return this.f479c;
    }

    public String m627d() {
        return "facebookAd.sendImpression();";
    }

    public Map<String, String> m628e() {
        return this.f483g;
    }

    public String m629f() {
        return this.f484h;
    }

    public int m630g() {
        return this.f485i;
    }

    public int m631h() {
        return this.f486j;
    }

    public Bundle m632i() {
        Bundle bundle = new Bundle();
        bundle.putString("markup", this.f477a);
        bundle.putString("native_impression_report_url", this.f479c);
        bundle.putString("request_id", this.f484h);
        bundle.putInt("viewability_check_initial_delay", this.f485i);
        bundle.putInt("viewability_check_interval", this.f486j);
        bundle.putInt("skip_after_seconds", this.f487k);
        bundle.putString("ct", this.f488l);
        return bundle;
    }
}
