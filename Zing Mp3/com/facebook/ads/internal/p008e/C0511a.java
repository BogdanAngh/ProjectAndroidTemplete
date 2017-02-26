package com.facebook.ads.internal.p008e;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.e.a */
public class C0511a {
    private final String f635a;
    private final JSONObject f636b;
    private final Map<C0521h, List<String>> f637c;

    public C0511a(String str, JSONObject jSONObject, @Nullable JSONArray jSONArray) {
        this.f637c = new HashMap();
        this.f635a = str;
        this.f636b = jSONObject;
        if (jSONArray != null && jSONArray.length() != 0) {
            int i;
            for (Object put : C0521h.values()) {
                this.f637c.put(put, new LinkedList());
            }
            for (i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    String string = jSONObject2.getString(ShareConstants.MEDIA_TYPE);
                    CharSequence string2 = jSONObject2.getString(NativeProtocol.WEB_DIALOG_URL);
                    C0521h valueOf = C0521h.valueOf(string.toUpperCase(Locale.US));
                    if (!(valueOf == null || TextUtils.isEmpty(string2))) {
                        ((List) this.f637c.get(valueOf)).add(string2);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public String m797a() {
        return this.f635a;
    }

    public List<String> m798a(C0521h c0521h) {
        return (List) this.f637c.get(c0521h);
    }

    public JSONObject m799b() {
        return this.f636b;
    }
}
