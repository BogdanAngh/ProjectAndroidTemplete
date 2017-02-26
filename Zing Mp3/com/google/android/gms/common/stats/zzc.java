package com.google.android.gms.common.stats;

import android.content.ComponentName;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class zzc {
    public static final ComponentName FP;
    public static int FQ;
    public static int FR;
    public static int FS;
    public static int FT;
    public static int FU;
    public static int FV;
    public static int FW;
    public static int LOG_LEVEL_OFF;

    static {
        FP = new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.common.stats.GmsCoreStatsService");
        LOG_LEVEL_OFF = 0;
        FQ = 1;
        FR = 2;
        FS = 4;
        FT = 8;
        FU = 16;
        FV = 32;
        FW = 1;
    }
}
