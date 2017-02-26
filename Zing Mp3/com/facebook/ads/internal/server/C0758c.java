package com.facebook.ads.internal.server;

import android.text.TextUtils;
import com.facebook.ads.internal.p008e.C0511a;
import com.facebook.ads.internal.p008e.C0515d;
import com.facebook.ads.internal.p008e.C0516e;
import com.facebook.ads.internal.server.C0760d.C0759a;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.server.c */
public class C0758c {
    private static C0758c f1316a;

    static {
        f1316a = new C0758c();
    }

    public static synchronized C0758c m1522a() {
        C0758c c0758c;
        synchronized (C0758c.class) {
            c0758c = f1316a;
        }
        return c0758c;
    }

    private C0761e m1523a(JSONObject jSONObject) {
        int i = 0;
        JSONObject jSONObject2 = jSONObject.getJSONArray("placements").getJSONObject(0);
        C0515d c0515d = new C0515d(C0516e.m809a(jSONObject2.getJSONObject("definition")), jSONObject2.optString("feature_config"));
        if (jSONObject2.has("ads")) {
            JSONArray jSONArray = jSONObject2.getJSONArray("ads");
            while (i < jSONArray.length()) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                c0515d.m805a(new C0511a(jSONObject3.optString("adapter"), jSONObject3.optJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA), jSONObject3.optJSONArray("trackers")));
                i++;
            }
        }
        return new C0761e(c0515d);
    }

    private C0762f m1524b(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONArray("placements").getJSONObject(0);
            return new C0762f(jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, BuildConfig.FLAVOR), jSONObject.optInt("code", 0), new C0515d(C0516e.m809a(jSONObject2.getJSONObject("definition")), jSONObject2.optString("feature_config")));
        } catch (JSONException e) {
            return m1525c(jSONObject);
        }
    }

    private C0762f m1525c(JSONObject jSONObject) {
        return new C0762f(jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, BuildConfig.FLAVOR), jSONObject.optInt("code", 0), null);
    }

    public C0760d m1526a(String str) {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString(ShareConstants.MEDIA_TYPE);
            Object obj = -1;
            switch (optString.hashCode()) {
                case 96432:
                    if (optString.equals("ads")) {
                        obj = null;
                        break;
                    }
                    break;
                case 96784904:
                    if (optString.equals(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE)) {
                        obj = 1;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    return m1523a(jSONObject);
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    return m1524b(jSONObject);
                default:
                    JSONObject optJSONObject = jSONObject.optJSONObject(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE);
                    if (optJSONObject != null) {
                        return m1525c(optJSONObject);
                    }
                    break;
            }
        }
        return new C0760d(C0759a.UNKNOWN, null);
    }
}
