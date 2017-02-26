package com.facebook.ads.internal.p004a;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.ads.internal.p005f.C0537f;
import com.facebook.ads.internal.p005f.C0538g;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.share.internal.ShareConstants;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.a.e */
public class C0433e extends C0429a {
    private static final String f277a;
    private final Context f278b;
    private final String f279c;
    private final Uri f280d;
    private final Map<String, String> f281e;

    static {
        f277a = C0433e.class.getSimpleName();
    }

    public C0433e(Context context, String str, Uri uri, Map<String, String> map) {
        this.f278b = context;
        this.f279c = str;
        this.f280d = uri;
        this.f281e = map;
    }

    public C0775a m333a() {
        return null;
    }

    public void m334b() {
        C0537f a = C0537f.m878a(this.f278b);
        if (!TextUtils.isEmpty(this.f280d.getQueryParameter(ShareConstants.WEB_DIALOG_PARAM_DATA))) {
            try {
                JSONObject jSONObject = new JSONObject(this.f280d.getQueryParameter(ShareConstants.WEB_DIALOG_PARAM_DATA));
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    this.f281e.put(str, jSONObject.getString(str));
                }
            } catch (JSONException e) {
                a.m883a("Unable to parse data in passthrough event.");
            }
        }
        C0538g c0538g = C0538g.IMMEDIATE;
        Object queryParameter = this.f280d.getQueryParameter("priority");
        if (!TextUtils.isEmpty(queryParameter)) {
            try {
                c0538g = C0538g.values()[Integer.valueOf(queryParameter).intValue()];
            } catch (Exception e2) {
            }
        }
        a.m886a(this.f279c, this.f281e, this.f280d.getQueryParameter(ShareConstants.MEDIA_TYPE), c0538g);
    }
}
