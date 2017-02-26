package com.facebook.ads.internal.p010h.p011a;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.h.a.n */
public class C0567n {
    private int f809a;
    private String f810b;
    private Map<String, List<String>> f811c;
    private byte[] f812d;

    public C0567n(HttpURLConnection httpURLConnection, byte[] bArr) {
        try {
            this.f809a = httpURLConnection.getResponseCode();
            this.f810b = httpURLConnection.getURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.f811c = httpURLConnection.getHeaderFields();
        this.f812d = bArr;
    }

    public int m990a() {
        return this.f809a;
    }

    public String m991b() {
        return this.f810b;
    }

    public Map<String, List<String>> m992c() {
        return this.f811c;
    }

    public byte[] m993d() {
        return this.f812d;
    }

    public String m994e() {
        return this.f812d != null ? new String(this.f812d) : null;
    }
}
