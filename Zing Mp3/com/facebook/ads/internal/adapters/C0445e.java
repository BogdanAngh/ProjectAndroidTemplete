package com.facebook.ads.internal.adapters;

import android.text.TextUtils;
import java.util.Locale;

/* renamed from: com.facebook.ads.internal.adapters.e */
public enum C0445e {
    UNKNOWN,
    AN,
    ADMOB,
    INMOBI,
    YAHOO;

    public static C0445e m426a(String str) {
        if (TextUtils.isEmpty(str)) {
            return UNKNOWN;
        }
        try {
            return (C0445e) Enum.valueOf(C0445e.class, str.toUpperCase(Locale.getDefault()));
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
