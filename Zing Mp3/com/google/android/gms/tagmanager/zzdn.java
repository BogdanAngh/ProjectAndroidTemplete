package com.google.android.gms.tagmanager;

import android.content.Context;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zzdn extends zzdk {
    private static final String ID;
    private static final String aIa;
    private static final String aIb;
    private static final String aIc;
    private static final String aId;
    private static final String aIe;
    private static final String aIf;
    private static final String aIg;
    private static final String aIh;
    private static final String aIi;
    private static final List<String> aIj;
    private static final Pattern aIk;
    private static final Pattern aIl;
    private static Map<String, String> aIm;
    private static Map<String, String> aIn;
    private final DataLayer aDZ;
    private final Set<String> aIo;
    private final zzdj aIp;

    static {
        ID = zzag.UNIVERSAL_ANALYTICS.toString();
        aIa = zzah.ACCOUNT.toString();
        aIb = zzah.ANALYTICS_PASS_THROUGH.toString();
        aIc = zzah.ENABLE_ECOMMERCE.toString();
        aId = zzah.ECOMMERCE_USE_DATA_LAYER.toString();
        aIe = zzah.ECOMMERCE_MACRO_DATA.toString();
        aIf = zzah.ANALYTICS_FIELDS.toString();
        aIg = zzah.TRACK_TRANSACTION.toString();
        aIh = zzah.TRANSACTION_DATALAYER_MAP.toString();
        aIi = zzah.TRANSACTION_ITEM_DATALAYER_MAP.toString();
        aIj = Arrays.asList(new String[]{ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, ProductAction.ACTION_CHECKOUT_OPTION, Promotion.ACTION_CLICK, ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, ProductAction.ACTION_PURCHASE, ProductAction.ACTION_REFUND});
        aIk = Pattern.compile("dimension(\\d+)");
        aIl = Pattern.compile("metric(\\d+)");
    }

    public zzdn(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzdj(context));
    }

    zzdn(Context context, DataLayer dataLayer, zzdj com_google_android_gms_tagmanager_zzdj) {
        super(ID, new String[0]);
        this.aDZ = dataLayer;
        this.aIp = com_google_android_gms_tagmanager_zzdj;
        this.aIo = new HashSet();
        this.aIo.add(BuildConfig.FLAVOR);
        this.aIo.add(AppEventsConstants.EVENT_PARAM_VALUE_NO);
        this.aIo.add("false");
    }

    private void zza(Tracker tracker, Map<String, zza> map) {
        String zzqc = zzqc("transactionId");
        if (zzqc == null) {
            zzbo.m1698e("Cannot find transactionId in data layer.");
            return;
        }
        List<Map> linkedList = new LinkedList();
        try {
            Map zzm = zzm((zza) map.get(aIf));
            zzm.put("&t", "transaction");
            for (Entry entry : zzbk(map).entrySet()) {
                zze(zzm, (String) entry.getValue(), zzqc((String) entry.getKey()));
            }
            linkedList.add(zzm);
            List<Map> zzqd = zzqd("transactionProducts");
            if (zzqd != null) {
                for (Map map2 : zzqd) {
                    if (map2.get(ShareConstants.WEB_DIALOG_PARAM_NAME) == null) {
                        zzbo.m1698e("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map zzm2 = zzm((zza) map.get(aIf));
                    zzm2.put("&t", "item");
                    zzm2.put("&ti", zzqc);
                    for (Entry entry2 : zzbl(map).entrySet()) {
                        zze(zzm2, (String) entry2.getValue(), (String) map2.get(entry2.getKey()));
                    }
                    linkedList.add(zzm2);
                }
            }
            for (Map map22 : linkedList) {
                tracker.send(map22);
            }
        } catch (Throwable e) {
            zzbo.zzb("Unable to send transaction", e);
        }
    }

    private Double zzax(Object obj) {
        String str;
        String valueOf;
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e) {
                str = "Cannot convert the object to Double: ";
                valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        } else {
            if (obj instanceof Double) {
                return (Double) obj;
            }
            str = "Cannot convert the object to Double: ";
            valueOf = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    private Integer zzay(Object obj) {
        String str;
        String valueOf;
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                str = "Cannot convert the object to Integer: ";
                valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        } else {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            str = "Cannot convert the object to Integer: ";
            valueOf = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    private void zzb(Tracker tracker, Map<String, zza> map) {
        Object obj;
        Map map2;
        String str;
        ScreenViewBuilder screenViewBuilder = new ScreenViewBuilder();
        Map zzm = zzm((zza) map.get(aIf));
        screenViewBuilder.setAll(zzm);
        if (zzj(map, aId)) {
            obj = this.aDZ.get("ecommerce");
            map2 = obj instanceof Map ? (Map) obj : null;
        } else {
            obj = zzdm.zzl((zza) map.get(aIe));
            map2 = obj instanceof Map ? (Map) obj : null;
        }
        if (map2 != null) {
            Map map3;
            List<Map> list;
            String str2 = (String) zzm.get("&cu");
            if (str2 == null) {
                str2 = (String) map2.get("currencyCode");
            }
            if (str2 != null) {
                screenViewBuilder.set("&cu", str2);
            }
            obj = map2.get("impressions");
            if (obj instanceof List) {
                for (Map map4 : (List) obj) {
                    try {
                        screenViewBuilder.addImpression(zzbj(map4), (String) map4.get("list"));
                    } catch (RuntimeException e) {
                        str = "Failed to extract a product from DataLayer. ";
                        str2 = String.valueOf(e.getMessage());
                        zzbo.m1698e(str2.length() != 0 ? str.concat(str2) : new String(str));
                    }
                }
            }
            List list2 = map2.containsKey("promoClick") ? (List) ((Map) map2.get("promoClick")).get("promotions") : map2.containsKey("promoView") ? (List) ((Map) map2.get("promoView")).get("promotions") : null;
            if (r0 != null) {
                for (Map map42 : r0) {
                    try {
                        screenViewBuilder.addPromotion(zzbi(map42));
                    } catch (RuntimeException e2) {
                        str = "Failed to extract a promotion from DataLayer. ";
                        str2 = String.valueOf(e2.getMessage());
                        zzbo.m1698e(str2.length() != 0 ? str.concat(str2) : new String(str));
                    }
                }
                if (map2.containsKey("promoClick")) {
                    screenViewBuilder.set("&promoa", Promotion.ACTION_CLICK);
                    obj = null;
                    if (obj != null) {
                        for (String str22 : aIj) {
                            if (map2.containsKey(str22)) {
                                map3 = (Map) map2.get(str22);
                                list = (List) map3.get("products");
                                if (list != null) {
                                    for (Map map22 : list) {
                                        try {
                                            screenViewBuilder.addProduct(zzbj(map22));
                                        } catch (RuntimeException e3) {
                                            str = "Failed to extract a product from DataLayer. ";
                                            String valueOf = String.valueOf(e3.getMessage());
                                            zzbo.m1698e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                                        }
                                    }
                                }
                                try {
                                    screenViewBuilder.setProductAction(map3.containsKey("actionField") ? zzi(str22, (Map) map3.get("actionField")) : new ProductAction(str22));
                                } catch (RuntimeException e22) {
                                    String str3 = "Failed to extract a product action from DataLayer. ";
                                    str22 = String.valueOf(e22.getMessage());
                                    zzbo.m1698e(str22.length() != 0 ? str3.concat(str22) : new String(str3));
                                }
                            }
                        }
                    }
                } else {
                    screenViewBuilder.set("&promoa", Promotion.ACTION_VIEW);
                }
            }
            int i = 1;
            if (obj != null) {
                for (String str222 : aIj) {
                    if (map22.containsKey(str222)) {
                        map3 = (Map) map22.get(str222);
                        list = (List) map3.get("products");
                        if (list != null) {
                            while (r4.hasNext()) {
                                screenViewBuilder.addProduct(zzbj(map22));
                            }
                        }
                        if (map3.containsKey("actionField")) {
                        }
                        screenViewBuilder.setProductAction(map3.containsKey("actionField") ? zzi(str222, (Map) map3.get("actionField")) : new ProductAction(str222));
                    }
                }
            }
        }
        tracker.send(screenViewBuilder.build());
    }

    private Promotion zzbi(Map<String, String> map) {
        Promotion promotion = new Promotion();
        String str = (String) map.get(TtmlNode.ATTR_ID);
        if (str != null) {
            promotion.setId(String.valueOf(str));
        }
        str = (String) map.get(ShareConstants.WEB_DIALOG_PARAM_NAME);
        if (str != null) {
            promotion.setName(String.valueOf(str));
        }
        str = (String) map.get("creative");
        if (str != null) {
            promotion.setCreative(String.valueOf(str));
        }
        str = (String) map.get("position");
        if (str != null) {
            promotion.setPosition(String.valueOf(str));
        }
        return promotion;
    }

    private Product zzbj(Map<String, Object> map) {
        String str;
        Product product = new Product();
        Object obj = map.get(TtmlNode.ATTR_ID);
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        obj = map.get(ShareConstants.WEB_DIALOG_PARAM_NAME);
        if (obj != null) {
            product.setName(String.valueOf(obj));
        }
        obj = map.get("brand");
        if (obj != null) {
            product.setBrand(String.valueOf(obj));
        }
        obj = map.get("category");
        if (obj != null) {
            product.setCategory(String.valueOf(obj));
        }
        obj = map.get("variant");
        if (obj != null) {
            product.setVariant(String.valueOf(obj));
        }
        obj = map.get("coupon");
        if (obj != null) {
            product.setCouponCode(String.valueOf(obj));
        }
        obj = map.get("position");
        if (obj != null) {
            product.setPosition(zzay(obj).intValue());
        }
        obj = map.get("price");
        if (obj != null) {
            product.setPrice(zzax(obj).doubleValue());
        }
        obj = map.get("quantity");
        if (obj != null) {
            product.setQuantity(zzay(obj).intValue());
        }
        for (String str2 : map.keySet()) {
            String str22;
            Matcher matcher = aIk.matcher(str22);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(str22)));
                } catch (NumberFormatException e) {
                    str = "illegal number in custom dimension value: ";
                    str22 = String.valueOf(str22);
                    zzbo.zzdi(str22.length() != 0 ? str.concat(str22) : new String(str));
                }
            } else {
                matcher = aIl.matcher(str22);
                if (matcher.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher.group(1)), zzay(map.get(str22)).intValue());
                    } catch (NumberFormatException e2) {
                        str = "illegal number in custom metric value: ";
                        str22 = String.valueOf(str22);
                        zzbo.zzdi(str22.length() != 0 ? str.concat(str22) : new String(str));
                    }
                }
            }
        }
        return product;
    }

    private Map<String, String> zzbk(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aIh);
        if (com_google_android_gms_internal_zzaj_zza != null) {
            return zzc(com_google_android_gms_internal_zzaj_zza);
        }
        if (aIm == null) {
            Map hashMap = new HashMap();
            hashMap.put("transactionId", "&ti");
            hashMap.put("transactionAffiliation", "&ta");
            hashMap.put("transactionTax", "&tt");
            hashMap.put("transactionShipping", "&ts");
            hashMap.put("transactionTotal", "&tr");
            hashMap.put("transactionCurrency", "&cu");
            aIm = hashMap;
        }
        return aIm;
    }

    private Map<String, String> zzbl(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(aIi);
        if (com_google_android_gms_internal_zzaj_zza != null) {
            return zzc(com_google_android_gms_internal_zzaj_zza);
        }
        if (aIn == null) {
            Map hashMap = new HashMap();
            hashMap.put(ShareConstants.WEB_DIALOG_PARAM_NAME, "&in");
            hashMap.put("sku", "&ic");
            hashMap.put("category", "&iv");
            hashMap.put("price", "&ip");
            hashMap.put("quantity", "&iq");
            hashMap.put("currency", "&cu");
            aIn = hashMap;
        }
        return aIn;
    }

    private Map<String, String> zzc(zza com_google_android_gms_internal_zzaj_zza) {
        Object zzl = zzdm.zzl(com_google_android_gms_internal_zzaj_zza);
        if (!(zzl instanceof Map)) {
            return null;
        }
        Map map = (Map) zzl;
        Map<String, String> linkedHashMap = new LinkedHashMap();
        for (Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private void zze(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private ProductAction zzi(String str, Map<String, Object> map) {
        ProductAction productAction = new ProductAction(str);
        Object obj = map.get(TtmlNode.ATTR_ID);
        if (obj != null) {
            productAction.setTransactionId(String.valueOf(obj));
        }
        obj = map.get("affiliation");
        if (obj != null) {
            productAction.setTransactionAffiliation(String.valueOf(obj));
        }
        obj = map.get("coupon");
        if (obj != null) {
            productAction.setTransactionCouponCode(String.valueOf(obj));
        }
        obj = map.get("list");
        if (obj != null) {
            productAction.setProductActionList(String.valueOf(obj));
        }
        obj = map.get("option");
        if (obj != null) {
            productAction.setCheckoutOptions(String.valueOf(obj));
        }
        obj = map.get("revenue");
        if (obj != null) {
            productAction.setTransactionRevenue(zzax(obj).doubleValue());
        }
        obj = map.get("tax");
        if (obj != null) {
            productAction.setTransactionTax(zzax(obj).doubleValue());
        }
        obj = map.get("shipping");
        if (obj != null) {
            productAction.setTransactionShipping(zzax(obj).doubleValue());
        }
        obj = map.get("step");
        if (obj != null) {
            productAction.setCheckoutStep(zzay(obj).intValue());
        }
        return productAction;
    }

    private boolean zzj(Map<String, zza> map, String str) {
        zza com_google_android_gms_internal_zzaj_zza = (zza) map.get(str);
        return com_google_android_gms_internal_zzaj_zza == null ? false : zzdm.zzk(com_google_android_gms_internal_zzaj_zza).booleanValue();
    }

    private Map<String, String> zzm(zza com_google_android_gms_internal_zzaj_zza) {
        if (com_google_android_gms_internal_zzaj_zza == null) {
            return new HashMap();
        }
        Map<String, String> zzc = zzc(com_google_android_gms_internal_zzaj_zza);
        if (zzc == null) {
            return new HashMap();
        }
        String str = (String) zzc.get("&aip");
        if (str != null && this.aIo.contains(str.toLowerCase())) {
            zzc.remove("&aip");
        }
        return zzc;
    }

    private String zzqc(String str) {
        Object obj = this.aDZ.get(str);
        return obj == null ? null : obj.toString();
    }

    private List<Map<String, String>> zzqd(String str) {
        Object obj = this.aDZ.get(str);
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            for (Object obj2 : (List) obj) {
                if (!(obj2 instanceof Map)) {
                    throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
                }
            }
            return (List) obj;
        }
        throw new IllegalArgumentException("transactionProducts should be of type List.");
    }

    public void zzba(Map<String, zza> map) {
        Tracker zzpu = this.aIp.zzpu("_GTM_DEFAULT_TRACKER_");
        zzpu.enableAdvertisingIdCollection(zzj(map, "collect_adid"));
        if (zzj(map, aIc)) {
            zzb(zzpu, map);
        } else if (zzj(map, aIb)) {
            zzpu.send(zzm((zza) map.get(aIf)));
        } else if (zzj(map, aIg)) {
            zza(zzpu, map);
        } else {
            zzbo.zzdi("Ignoring unknown tag.");
        }
    }

    public /* bridge */ /* synthetic */ boolean zzcdu() {
        return super.zzcdu();
    }

    public /* bridge */ /* synthetic */ String zzcfg() {
        return super.zzcfg();
    }

    public /* bridge */ /* synthetic */ Set zzcfh() {
        return super.zzcfh();
    }
}
