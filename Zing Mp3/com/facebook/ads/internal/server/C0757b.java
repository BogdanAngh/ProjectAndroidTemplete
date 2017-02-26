package com.facebook.ads.internal.server;

import android.text.TextUtils;
import com.facebook.ads.AdSettings;

/* renamed from: com.facebook.ads.internal.server.b */
public class C0757b {
    public static String m1520a() {
        if (TextUtils.isEmpty(AdSettings.getUrlPrefix())) {
            return "https://graph.facebook.com/network_ads_common";
        }
        return String.format("https://graph.%s.facebook.com/network_ads_common", new Object[]{AdSettings.getUrlPrefix()});
    }

    public static String m1521b() {
        if (TextUtils.isEmpty(AdSettings.getUrlPrefix())) {
            return "https://www.facebook.com/adnw_logging/";
        }
        return String.format("https://www.%s.facebook.com/adnw_logging/", new Object[]{AdSettings.getUrlPrefix()});
    }
}
