package com.facebook.ads.internal.server;

import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import java.util.Locale;

public enum AdPlacementType {
    UNKNOWN(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_UNKNOWN),
    BANNER("banner"),
    INTERSTITIAL("interstitial"),
    NATIVE(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE),
    INSTREAM("instream"),
    REWARDED_VIDEO("rewarded_video");
    
    private String f1301a;

    private AdPlacementType(String str) {
        this.f1301a = str;
    }

    public static AdPlacementType fromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return UNKNOWN;
        }
        try {
            return valueOf(str.toUpperCase(Locale.US));
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    public String toString() {
        return this.f1301a;
    }
}
