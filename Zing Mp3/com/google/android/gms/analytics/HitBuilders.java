package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzae;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.tagmanager.DataLayer;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HitBuilders {

    protected static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> aO;
        ProductAction aP;
        Map<String, List<Product>> aQ;
        List<Promotion> aR;
        List<Product> aS;

        protected HitBuilder() {
            this.aO = new HashMap();
            this.aQ = new HashMap();
            this.aR = new ArrayList();
            this.aS = new ArrayList();
        }

        private T zzn(String str, String str2) {
            if (str == null) {
                zzae.zzdi("HitBuilder.setIfNonNull() called with a null paramName.");
            } else if (str2 != null) {
                this.aO.put(str, str2);
            }
            return this;
        }

        public T addImpression(Product product, String str) {
            if (product == null) {
                zzae.zzdi("product should be non-null");
            } else {
                Object obj;
                if (str == null) {
                    obj = BuildConfig.FLAVOR;
                }
                if (!this.aQ.containsKey(obj)) {
                    this.aQ.put(obj, new ArrayList());
                }
                ((List) this.aQ.get(obj)).add(product);
            }
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzae.zzdi("product should be non-null");
            } else {
                this.aS.add(product);
            }
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzae.zzdi("promotion should be non-null");
            } else {
                this.aR.add(promotion);
            }
            return this;
        }

        public Map<String, String> build() {
            Map<String, String> hashMap = new HashMap(this.aO);
            if (this.aP != null) {
                hashMap.putAll(this.aP.build());
            }
            int i = 1;
            for (Promotion zzep : this.aR) {
                hashMap.putAll(zzep.zzep(zzc.zzbr(i)));
                i++;
            }
            i = 1;
            for (Product zzep2 : this.aS) {
                hashMap.putAll(zzep2.zzep(zzc.zzbp(i)));
                i++;
            }
            int i2 = 1;
            for (Entry entry : this.aQ.entrySet()) {
                List<Product> list = (List) entry.getValue();
                String zzbu = zzc.zzbu(i2);
                int i3 = 1;
                for (Product product : list) {
                    String valueOf = String.valueOf(zzbu);
                    String valueOf2 = String.valueOf(zzc.zzbt(i3));
                    hashMap.putAll(product.zzep(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i3++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    String valueOf3 = String.valueOf(zzbu);
                    String valueOf4 = String.valueOf("nm");
                    hashMap.put(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), (String) entry.getKey());
                }
                i2++;
            }
            return hashMap;
        }

        protected String get(String str) {
            return (String) this.aO.get(str);
        }

        public final T set(String str, String str2) {
            if (str != null) {
                this.aO.put(str, str2);
            } else {
                zzae.zzdi("HitBuilder.set() called with a null paramName.");
            }
            return this;
        }

        public final T setAll(Map<String, String> map) {
            if (map != null) {
                this.aO.putAll(new HashMap(map));
            }
            return this;
        }

        public T setCampaignParamsFromUrl(String str) {
            Object zzfk = zzao.zzfk(str);
            if (!TextUtils.isEmpty(zzfk)) {
                Map zzfi = zzao.zzfi(zzfk);
                zzn("&cc", (String) zzfi.get("utm_content"));
                zzn("&cm", (String) zzfi.get("utm_medium"));
                zzn("&cn", (String) zzfi.get("utm_campaign"));
                zzn("&cs", (String) zzfi.get("utm_source"));
                zzn("&ck", (String) zzfi.get("utm_term"));
                zzn("&ci", (String) zzfi.get("utm_id"));
                zzn("&anid", (String) zzfi.get("anid"));
                zzn("&gclid", (String) zzfi.get("gclid"));
                zzn("&dclid", (String) zzfi.get("dclid"));
                zzn("&aclid", (String) zzfi.get("aclid"));
                zzn("&gmob_t", (String) zzfi.get("gmob_t"));
            }
            return this;
        }

        public T setCustomDimension(int i, String str) {
            set(zzc.zzbl(i), str);
            return this;
        }

        public T setCustomMetric(int i, float f) {
            set(zzc.zzbn(i), Float.toString(f));
            return this;
        }

        protected T setHitType(String str) {
            set("&t", str);
            return this;
        }

        public T setNewSession() {
            set("&sc", TtmlNode.START);
            return this;
        }

        public T setNonInteraction(boolean z) {
            set("&ni", zzao.zzax(z));
            return this;
        }

        public T setProductAction(ProductAction productAction) {
            this.aP = productAction;
            return this;
        }

        public T setPromotionAction(String str) {
            this.aO.put("&promoa", str);
            return this;
        }
    }

    @Deprecated
    public static class AppViewBuilder extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            set("&t", "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            set("&t", DataLayer.EVENT_KEY);
        }

        public EventBuilder(String str, String str2) {
            this();
            setCategory(str);
            setAction(str2);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public EventBuilder setAction(String str) {
            set("&ea", str);
            return this;
        }

        public EventBuilder setCategory(String str) {
            set("&ec", str);
            return this;
        }

        public EventBuilder setLabel(String str) {
            set("&el", str);
            return this;
        }

        public EventBuilder setValue(long j) {
            set("&ev", Long.toString(j));
            return this;
        }
    }

    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            set("&t", "exception");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ExceptionBuilder setDescription(String str) {
            set("&exd", str);
            return this;
        }

        public ExceptionBuilder setFatal(boolean z) {
            set("&exf", zzao.zzax(z));
            return this;
        }
    }

    @Deprecated
    public static class ItemBuilder extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            set("&t", "item");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ItemBuilder setCategory(String str) {
            set("&iv", str);
            return this;
        }

        public ItemBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        public ItemBuilder setName(String str) {
            set("&in", str);
            return this;
        }

        public ItemBuilder setPrice(double d) {
            set("&ip", Double.toString(d));
            return this;
        }

        public ItemBuilder setQuantity(long j) {
            set("&iq", Long.toString(j));
            return this;
        }

        public ItemBuilder setSku(String str) {
            set("&ic", str);
            return this;
        }

        public ItemBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }

    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            set("&t", "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    public static class SocialBuilder extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            set("&t", NotificationCompatApi24.CATEGORY_SOCIAL);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public SocialBuilder setAction(String str) {
            set("&sa", str);
            return this;
        }

        public SocialBuilder setNetwork(String str) {
            set("&sn", str);
            return this;
        }

        public SocialBuilder setTarget(String str) {
            set("&st", str);
            return this;
        }
    }

    public static class TimingBuilder extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            set("&t", "timing");
        }

        public TimingBuilder(String str, String str2, long j) {
            this();
            setVariable(str2);
            setValue(j);
            setCategory(str);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public TimingBuilder setCategory(String str) {
            set("&utc", str);
            return this;
        }

        public TimingBuilder setLabel(String str) {
            set("&utl", str);
            return this;
        }

        public TimingBuilder setValue(long j) {
            set("&utt", Long.toString(j));
            return this;
        }

        public TimingBuilder setVariable(String str) {
            set("&utv", str);
            return this;
        }
    }

    @Deprecated
    public static class TransactionBuilder extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            set("&t", "transaction");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public TransactionBuilder setAffiliation(String str) {
            set("&ta", str);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        public TransactionBuilder setRevenue(double d) {
            set("&tr", Double.toString(d));
            return this;
        }

        public TransactionBuilder setShipping(double d) {
            set("&ts", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTax(double d) {
            set("&tt", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }
}
