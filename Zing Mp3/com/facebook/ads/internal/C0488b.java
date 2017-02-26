package com.facebook.ads.internal;

import android.text.TextUtils;
import com.facebook.ads.AdError;

/* renamed from: com.facebook.ads.internal.b */
public class C0488b {
    private final AdErrorType f548a;
    private final String f549b;

    public C0488b(int i, String str) {
        this(AdErrorType.adErrorTypeFromCode(i), str);
    }

    public C0488b(AdErrorType adErrorType, String str) {
        if (TextUtils.isEmpty(str)) {
            str = adErrorType.getDefaultErrorMessage();
        }
        this.f548a = adErrorType;
        this.f549b = str;
    }

    public AdErrorType m728a() {
        return this.f548a;
    }

    public AdError m729b() {
        return this.f548a.m232a() ? new AdError(this.f548a.getErrorCode(), this.f549b) : new AdError(AdErrorType.UNKNOWN_ERROR.getErrorCode(), AdErrorType.UNKNOWN_ERROR.getDefaultErrorMessage());
    }
}
