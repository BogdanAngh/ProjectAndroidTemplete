package com.facebook.ads.internal.p008e;

import com.facebook.internal.NativeProtocol;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.e.b */
public class C0512b {
    public String f638a;
    public String f639b;
    public String f640c;
    public Date f641d;
    public boolean f642e;

    public C0512b(String str, String str2, String str3, Date date) {
        this.f638a = str;
        this.f639b = str2;
        this.f640c = str3;
        this.f641d = date;
        boolean z = (str2 == null || str3 == null) ? false : true;
        this.f642e = z;
    }

    public C0512b(JSONObject jSONObject) {
        this(jSONObject.optString(NativeProtocol.WEB_DIALOG_URL), jSONObject.optString("key"), jSONObject.optString("value"), new Date(jSONObject.optLong("expiration") * 1000));
    }

    public static List<C0512b> m800a(String str) {
        if (str == null) {
            return null;
        }
        JSONArray jSONArray;
        try {
            jSONArray = new JSONArray(str);
        } catch (JSONException e) {
            jSONArray = null;
        }
        if (jSONArray == null) {
            return null;
        }
        List<C0512b> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject;
            try {
                jSONObject = jSONArray.getJSONObject(i);
            } catch (JSONException e2) {
                jSONObject = null;
            }
            if (jSONObject != null) {
                C0512b c0512b = new C0512b(jSONObject);
                if (c0512b != null) {
                    arrayList.add(c0512b);
                }
            }
        }
        return arrayList;
    }

    public String m801a() {
        Date date = this.f641d;
        if (date == null) {
            date = new Date();
            date.setTime(date.getTime() + 3600000);
        }
        DateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss zzz");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    public boolean m802b() {
        return (this.f639b == null || this.f640c == null || this.f638a == null) ? false : true;
    }
}
