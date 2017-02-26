package com.facebook.ads.internal.p010h.p011a;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.h.a.g */
public class C0560g implements C0559r {
    private void m974a(Map<String, List<String>> map) {
        if (map != null) {
            for (String str : map.keySet()) {
                for (String str2 : (List) map.get(str)) {
                    m976a(str + ":" + str2);
                }
            }
        }
    }

    public void m975a(C0567n c0567n) {
        if (c0567n != null) {
            m976a("=== HTTP Response ===");
            m976a("Receive url: " + c0567n.m991b());
            m976a("Status: " + c0567n.m990a());
            m974a(c0567n.m992c());
            m976a("Content:\n" + c0567n.m994e());
        }
    }

    public void m976a(String str) {
        System.out.println(str);
    }

    public void m977a(HttpURLConnection httpURLConnection, Object obj) {
        m976a("=== HTTP Request ===");
        m976a(httpURLConnection.getRequestMethod() + " " + httpURLConnection.getURL().toString());
        if (obj instanceof String) {
            m976a("Content: " + ((String) obj));
        }
        m974a(httpURLConnection.getRequestProperties());
    }

    public boolean m978a() {
        return false;
    }
}
