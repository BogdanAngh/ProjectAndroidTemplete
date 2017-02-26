package com.facebook.ads.internal.p010h.p011a;

import com.google.android.exoplayer.C0989C;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: com.facebook.ads.internal.h.a.p */
public class C0569p implements Map<String, String> {
    private Map<String, String> f813a;

    public C0569p() {
        this.f813a = new HashMap();
    }

    public C0569p m997a(Map<? extends String, ? extends String> map) {
        putAll(map);
        return this;
    }

    public String m998a() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.f813a.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(str);
            String str2 = (String) this.f813a.get(str2);
            if (str2 != null) {
                stringBuilder.append("=");
                try {
                    stringBuilder.append(URLEncoder.encode(str2, C0989C.UTF8_NAME));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    public String m999a(Object obj) {
        return (String) this.f813a.get(obj);
    }

    public String m1000a(String str, String str2) {
        return (String) this.f813a.put(str, str2);
    }

    public String m1001b(Object obj) {
        return (String) this.f813a.remove(obj);
    }

    public byte[] m1002b() {
        byte[] bArr = null;
        try {
            bArr = m998a().getBytes(C0989C.UTF8_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bArr;
    }

    public void clear() {
        this.f813a.clear();
    }

    public boolean containsKey(Object obj) {
        return this.f813a.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return this.f813a.containsValue(obj);
    }

    public Set<Entry<String, String>> entrySet() {
        return this.f813a.entrySet();
    }

    public /* synthetic */ Object get(Object obj) {
        return m999a(obj);
    }

    public boolean isEmpty() {
        return this.f813a.isEmpty();
    }

    public Set<String> keySet() {
        return this.f813a.keySet();
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        return m1000a((String) obj, (String) obj2);
    }

    public void putAll(Map<? extends String, ? extends String> map) {
        this.f813a.putAll(map);
    }

    public /* synthetic */ Object remove(Object obj) {
        return m1001b(obj);
    }

    public int size() {
        return this.f813a.size();
    }

    public Collection<String> values() {
        return this.f813a.values();
    }
}
