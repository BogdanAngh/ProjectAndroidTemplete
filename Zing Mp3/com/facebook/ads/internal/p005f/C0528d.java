package com.facebook.ads.internal.p005f;

import com.facebook.ads.internal.util.C0765a;
import com.facebook.ads.internal.util.C0785i;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.facebook.ads.internal.f.d */
public abstract class C0528d {
    protected final String f733a;
    protected final double f734b;
    protected final double f735c;
    protected final String f736d;
    protected final Map<String, String> f737e;

    public C0528d(String str, double d, String str2, Map<String, String> map) {
        this.f733a = str;
        this.f734b = ((double) System.currentTimeMillis()) / 1000.0d;
        this.f735c = d;
        this.f736d = str2;
        Map hashMap = new HashMap();
        if (!(map == null || map.isEmpty())) {
            hashMap.putAll(map);
        }
        if (m844c()) {
            hashMap.put("analog", C0785i.m1620a(C0765a.m1531a()));
        }
        this.f737e = C0528d.m841a(hashMap);
    }

    private static Map<String, String> m841a(Map<String, String> map) {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (str2 != null) {
                hashMap.put(str, str2);
            }
        }
        return hashMap;
    }

    public abstract C0538g m842a();

    public abstract String m843b();

    public abstract boolean m844c();

    public String m845d() {
        return this.f733a;
    }

    public double m846e() {
        return this.f734b;
    }

    public double m847f() {
        return this.f735c;
    }

    public String m848g() {
        return this.f736d;
    }

    public Map<String, String> m849h() {
        return this.f737e;
    }

    public final boolean m850i() {
        return m842a() == C0538g.IMMEDIATE;
    }
}
