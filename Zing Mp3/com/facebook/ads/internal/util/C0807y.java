package com.facebook.ads.internal.util;

import android.os.AsyncTask;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.ads.internal.p010h.p011a.C0554a;
import com.facebook.ads.internal.p010h.p011a.C0567n;
import com.facebook.ads.internal.p010h.p011a.C0569p;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: com.facebook.ads.internal.util.y */
public class C0807y extends AsyncTask<String, Void, C0808z> {
    private static final String f1498a;
    private static final Set<String> f1499b;
    private Map<String, String> f1500c;
    private Map<String, String> f1501d;
    private C0567n f1502e;

    static {
        f1498a = C0807y.class.getSimpleName();
        f1499b = new HashSet();
        f1499b.add("#");
        f1499b.add("null");
    }

    public C0807y() {
        this(null, null);
    }

    public C0807y(Map<String, String> map) {
        this(map, null);
    }

    public C0807y(Map<String, String> map, Map<String, String> map2) {
        Map map3 = null;
        this.f1500c = map != null ? new HashMap(map) : null;
        if (map2 != null) {
            map3 = new HashMap(map2);
        }
        this.f1501d = map3;
    }

    private String m1690a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return str;
        }
        return str + (str.contains("?") ? "&" : "?") + str2 + "=" + URLEncoder.encode(str3);
    }

    private boolean m1691a(String str) {
        C0554a b = C0806x.m1684b();
        try {
            if (this.f1501d == null || this.f1501d.size() == 0) {
                this.f1502e = b.m950a(str, null);
            } else {
                C0569p c0569p = new C0569p();
                c0569p.m997a(this.f1501d);
                this.f1502e = b.m959b(str, c0569p);
            }
            return this.f1502e != null && this.f1502e.m990a() == Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        } catch (Throwable e) {
            Log.e(f1498a, "Error opening url: " + str, e);
            return false;
        }
    }

    private String m1692b(String str) {
        try {
            str = m1690a(str, "analog", C0785i.m1620a(C0765a.m1531a()));
        } catch (Exception e) {
        }
        return str;
    }

    protected C0808z m1693a(String... strArr) {
        Object obj = strArr[0];
        if (TextUtils.isEmpty(obj) || f1499b.contains(obj)) {
            return null;
        }
        String b = m1692b(obj);
        if (!(this.f1500c == null || this.f1500c.isEmpty())) {
            String str = b;
            for (Entry entry : this.f1500c.entrySet()) {
                str = m1690a(str, (String) entry.getKey(), (String) entry.getValue());
            }
            b = str;
        }
        int i = 1;
        while (true) {
            int i2 = i + 1;
            if (i > 2) {
                return null;
            }
            if (m1691a(b)) {
                return new C0808z(this.f1502e);
            }
            i = i2;
        }
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m1693a((String[]) objArr);
    }
}
