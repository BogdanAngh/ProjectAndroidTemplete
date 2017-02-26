package com.google.android.gms.internal;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzg;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzmy extends zzg<zzmy> {
    private ProductAction aP;
    private final Map<String, List<Product>> aQ;
    private final List<Promotion> aR;
    private final List<Product> aS;

    public zzmy() {
        this.aS = new ArrayList();
        this.aR = new ArrayList();
        this.aQ = new HashMap();
    }

    public String toString() {
        Map hashMap = new HashMap();
        if (!this.aS.isEmpty()) {
            hashMap.put("products", this.aS);
        }
        if (!this.aR.isEmpty()) {
            hashMap.put("promotions", this.aR);
        }
        if (!this.aQ.isEmpty()) {
            hashMap.put("impressions", this.aQ);
        }
        hashMap.put("productAction", this.aP);
        return zzg.zzj(hashMap);
    }

    public void zza(Product product, String str) {
        if (product != null) {
            Object obj;
            if (str == null) {
                obj = BuildConfig.FLAVOR;
            }
            if (!this.aQ.containsKey(obj)) {
                this.aQ.put(obj, new ArrayList());
            }
            ((List) this.aQ.get(obj)).add(product);
        }
    }

    public void zza(zzmy com_google_android_gms_internal_zzmy) {
        com_google_android_gms_internal_zzmy.aS.addAll(this.aS);
        com_google_android_gms_internal_zzmy.aR.addAll(this.aR);
        for (Entry entry : this.aQ.entrySet()) {
            String str = (String) entry.getKey();
            for (Product zza : (List) entry.getValue()) {
                com_google_android_gms_internal_zzmy.zza(zza, str);
            }
        }
        if (this.aP != null) {
            com_google_android_gms_internal_zzmy.aP = this.aP;
        }
    }

    public ProductAction zzaav() {
        return this.aP;
    }

    public List<Product> zzaaw() {
        return Collections.unmodifiableList(this.aS);
    }

    public Map<String, List<Product>> zzaax() {
        return this.aQ;
    }

    public List<Promotion> zzaay() {
        return Collections.unmodifiableList(this.aR);
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzmy) com_google_android_gms_analytics_zzg);
    }
}
