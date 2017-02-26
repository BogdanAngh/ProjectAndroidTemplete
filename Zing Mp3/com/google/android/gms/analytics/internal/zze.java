package com.google.android.gms.analytics.internal;

import com.facebook.ads.AdError;
import com.google.android.gms.common.zzc;

public class zze {
    public static final String VERSION;
    public static final String cS;

    static {
        VERSION = String.valueOf(zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE / AdError.NETWORK_ERROR_CODE).replaceAll("(\\d+)(\\d)(\\d\\d)", "$1.$2.$3");
        String str = "ma";
        String valueOf = String.valueOf(VERSION);
        cS = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }
}
