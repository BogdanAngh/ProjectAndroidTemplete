package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzc;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzaa;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Product {
    Map<String, String> cB;

    public Product() {
        this.cB = new HashMap();
    }

    void put(String str, String str2) {
        zzaa.zzb((Object) str, (Object) "Name should be non-null");
        this.cB.put(str, str2);
    }

    public Product setBrand(String str) {
        put(TtmlNode.TAG_BR, str);
        return this;
    }

    public Product setCategory(String str) {
        put("ca", str);
        return this;
    }

    public Product setCouponCode(String str) {
        put("cc", str);
        return this;
    }

    public Product setCustomDimension(int i, String str) {
        put(zzc.zzbw(i), str);
        return this;
    }

    public Product setCustomMetric(int i, int i2) {
        put(zzc.zzbx(i), Integer.toString(i2));
        return this;
    }

    public Product setId(String str) {
        put(TtmlNode.ATTR_ID, str);
        return this;
    }

    public Product setName(String str) {
        put("nm", str);
        return this;
    }

    public Product setPosition(int i) {
        put("ps", Integer.toString(i));
        return this;
    }

    public Product setPrice(double d) {
        put("pr", Double.toString(d));
        return this;
    }

    public Product setQuantity(int i) {
        put("qt", Integer.toString(i));
        return this;
    }

    public Product setVariant(String str) {
        put("va", str);
        return this;
    }

    public String toString() {
        return zzg.zzar(this.cB);
    }

    public Map<String, String> zzep(String str) {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : this.cB.entrySet()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf((String) entry.getKey());
            hashMap.put(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), (String) entry.getValue());
        }
        return hashMap;
    }
}
