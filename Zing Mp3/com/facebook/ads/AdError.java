package com.facebook.ads;

import android.text.TextUtils;

public class AdError {
    public static final AdError INTERNAL_ERROR;
    public static final int INTERNAL_ERROR_CODE = 2001;
    public static final AdError LOAD_TOO_FREQUENTLY;
    public static final int LOAD_TOO_FREQUENTLY_ERROR_CODE = 1002;
    public static final AdError MEDIATION_ERROR;
    public static final int MEDIATION_ERROR_CODE = 3001;
    @Deprecated
    public static final AdError MISSING_PROPERTIES;
    public static final AdError NETWORK_ERROR;
    public static final int NETWORK_ERROR_CODE = 1000;
    public static final AdError NO_FILL;
    public static final int NO_FILL_ERROR_CODE = 1001;
    public static final AdError SERVER_ERROR;
    public static final int SERVER_ERROR_CODE = 2000;
    private final int f27a;
    private final String f28b;

    static {
        NETWORK_ERROR = new AdError(NETWORK_ERROR_CODE, "Network Error");
        NO_FILL = new AdError(NO_FILL_ERROR_CODE, "No Fill");
        LOAD_TOO_FREQUENTLY = new AdError(LOAD_TOO_FREQUENTLY_ERROR_CODE, "Ad was re-loaded too frequently");
        SERVER_ERROR = new AdError(SERVER_ERROR_CODE, "Server Error");
        INTERNAL_ERROR = new AdError(INTERNAL_ERROR_CODE, "Internal Error");
        MEDIATION_ERROR = new AdError(MEDIATION_ERROR_CODE, "Mediation Error");
        MISSING_PROPERTIES = new AdError(2002, "Native ad failed to load due to missing properties");
    }

    public AdError(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "unknown error";
        }
        this.f27a = i;
        this.f28b = str;
    }

    public int getErrorCode() {
        return this.f27a;
    }

    public String getErrorMessage() {
        return this.f28b;
    }
}
