package com.facebook.ads.internal;

import org.json.JSONArray;

/* renamed from: com.facebook.ads.internal.d */
public enum C0510d {
    APP_AD(0),
    LINK_AD(1),
    APP_AD_V2(2),
    LINK_AD_V2(3),
    APP_ENGAGEMENT_AD(4),
    AD_CHOICES(5),
    JS_TRIGGER(6),
    JS_TRIGGER_NO_AUTO_IMP_LOGGING(7),
    VIDEO_AD(8),
    INLINE_VIDEO_AD(9),
    BANNER_TO_INTERSTITIAL(10),
    NATIVE_CLOSE_BUTTON(11),
    UNIFIED_LOGGING(16),
    HTTP_LINKS(17);
    
    public static final C0510d[] f631o;
    private static final String f632q;
    private final int f634p;

    static {
        f631o = new C0510d[]{LINK_AD_V2, APP_ENGAGEMENT_AD, AD_CHOICES, JS_TRIGGER_NO_AUTO_IMP_LOGGING, NATIVE_CLOSE_BUTTON, UNIFIED_LOGGING, HTTP_LINKS};
        JSONArray jSONArray = new JSONArray();
        C0510d[] c0510dArr = f631o;
        int length = c0510dArr.length;
        int i;
        while (i < length) {
            jSONArray.put(c0510dArr[i].m796a());
            i++;
        }
        f632q = jSONArray.toString();
    }

    private C0510d(int i) {
        this.f634p = i;
    }

    public static String m795b() {
        return f632q;
    }

    public int m796a() {
        return this.f634p;
    }

    public String toString() {
        return String.valueOf(this.f634p);
    }
}
