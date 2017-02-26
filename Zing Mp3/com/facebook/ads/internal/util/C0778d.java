package com.facebook.ads.internal.util;

import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/* renamed from: com.facebook.ads.internal.util.d */
public class C0778d {
    private static final List<C0777c> f1415a;

    static {
        f1415a = new ArrayList();
    }

    public static String m1598a() {
        synchronized (f1415a) {
            if (f1415a.isEmpty()) {
                String str = BuildConfig.FLAVOR;
                return str;
            }
            List<C0777c> arrayList = new ArrayList(f1415a);
            f1415a.clear();
            JSONArray jSONArray = new JSONArray();
            for (C0777c a : arrayList) {
                jSONArray.put(a.m1597a());
            }
            return jSONArray.toString();
        }
    }

    public static void m1599a(C0777c c0777c) {
        synchronized (f1415a) {
            f1415a.add(c0777c);
        }
    }
}
