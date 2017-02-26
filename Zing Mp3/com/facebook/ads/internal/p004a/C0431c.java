package com.facebook.ads.internal.p004a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.ads.internal.util.C0782g;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0787k;
import com.facebook.internal.NativeProtocol;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.a.c */
public class C0431c extends C0429a {
    private static final String f267a;
    private final Context f268b;
    private final String f269c;
    private final Uri f270d;
    private final Map<String, String> f271e;

    static {
        f267a = C0431c.class.getSimpleName();
    }

    public C0431c(Context context, String str, Uri uri, Map<String, String> map) {
        this.f268b = context;
        this.f269c = str;
        this.f270d = uri;
        this.f271e = map;
    }

    private Intent m323a(C0787k c0787k) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        if (!(TextUtils.isEmpty(c0787k.m1639a()) || TextUtils.isEmpty(c0787k.m1640b()))) {
            intent.setComponent(new ComponentName(c0787k.m1639a(), c0787k.m1640b()));
        }
        if (!TextUtils.isEmpty(c0787k.m1641c())) {
            intent.setData(Uri.parse(c0787k.m1641c()));
        }
        return intent;
    }

    private Intent m324b(C0787k c0787k) {
        if (TextUtils.isEmpty(c0787k.m1639a())) {
            return null;
        }
        if (!C0782g.m1610a(this.f268b, c0787k.m1639a())) {
            return null;
        }
        CharSequence c = c0787k.m1641c();
        if (!TextUtils.isEmpty(c) && (c.startsWith("tel:") || c.startsWith("telprompt:"))) {
            return new Intent("android.intent.action.CALL", Uri.parse(c));
        }
        PackageManager packageManager = this.f268b.getPackageManager();
        if (TextUtils.isEmpty(c0787k.m1640b()) && TextUtils.isEmpty(c)) {
            return packageManager.getLaunchIntentForPackage(c0787k.m1639a());
        }
        Intent a = m323a(c0787k);
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(a, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        if (a.getComponent() == null) {
            for (ResolveInfo resolveInfo : queryIntentActivities) {
                if (resolveInfo.activityInfo.packageName.equals(c0787k.m1639a())) {
                    a.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
                    break;
                }
            }
        }
        return (queryIntentActivities.isEmpty() || a.getComponent() == null) ? null : a;
    }

    private List<C0787k> m325f() {
        Object queryParameter = this.f270d.getQueryParameter("appsite_data");
        if (TextUtils.isEmpty(queryParameter) || "[]".equals(queryParameter)) {
            return null;
        }
        List<C0787k> arrayList = new ArrayList();
        try {
            JSONArray optJSONArray = new JSONObject(queryParameter).optJSONArray("android");
            if (optJSONArray == null) {
                return arrayList;
            }
            for (int i = 0; i < optJSONArray.length(); i++) {
                C0787k a = C0787k.m1638a(optJSONArray.optJSONObject(i));
                if (a != null) {
                    arrayList.add(a);
                }
            }
            return arrayList;
        } catch (Throwable e) {
            Log.w(f267a, "Error parsing appsite_data", e);
            return arrayList;
        }
    }

    public C0775a m326a() {
        return C0775a.OPEN_STORE;
    }

    public void m327b() {
        m319a(this.f268b, this.f269c, this.f270d, this.f271e);
        List<Intent> d = m329d();
        if (d != null) {
            for (Intent startActivity : d) {
                try {
                    this.f268b.startActivity(startActivity);
                    return;
                } catch (Throwable e) {
                    Log.d(f267a, "Failed to open app intent, falling back", e);
                }
            }
        }
        m330e();
    }

    protected Uri m328c() {
        Object queryParameter = this.f270d.getQueryParameter("store_url");
        if (!TextUtils.isEmpty(queryParameter)) {
            return Uri.parse(queryParameter);
        }
        String queryParameter2 = this.f270d.getQueryParameter("store_id");
        return Uri.parse(String.format("market://details?id=%s", new Object[]{queryParameter2}));
    }

    protected List<Intent> m329d() {
        List<C0787k> f = m325f();
        List<Intent> arrayList = new ArrayList();
        if (f != null) {
            for (C0787k b : f) {
                Intent b2 = m324b(b);
                if (b2 != null) {
                    arrayList.add(b2);
                }
            }
        }
        return arrayList;
    }

    public void m330e() {
        try {
            C0785i.m1627a(this.f268b, m328c(), this.f269c);
        } catch (Throwable e) {
            Log.d(f267a, "Failed to open market url: " + this.f270d.toString(), e);
            String queryParameter = this.f270d.getQueryParameter("store_url_web_fallback");
            if (queryParameter != null && queryParameter.length() > 0) {
                try {
                    C0785i.m1627a(this.f268b, Uri.parse(queryParameter), this.f269c);
                } catch (Throwable e2) {
                    Log.d(f267a, "Failed to open fallback url: " + queryParameter, e2);
                }
            }
        }
    }
}
