package com.facebook.ads.internal.util;

import android.text.TextUtils;
import java.util.Locale;

/* renamed from: com.facebook.ads.internal.util.f */
public enum C0781f {
    NONE,
    INSTALLED,
    NOT_INSTALLED;

    public static C0781f m1607a(String str) {
        if (TextUtils.isEmpty(str)) {
            return NONE;
        }
        try {
            return C0781f.valueOf(str.toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}
