package com.facebook.ads.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import com.facebook.ads.internal.util.C0785i;
import com.google.android.exoplayer.BuildConfig;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.h */
public class C0604h {
    private static C0604h f872a;
    private final SharedPreferences f873b;

    public C0604h(Context context) {
        this.f873b = context.getApplicationContext().getSharedPreferences("com.facebook.ads.FEATURE_CONFIG", 0);
    }

    public static boolean m1120a(Context context) {
        return VERSION.SDK_INT >= 14 && C0785i.m1631a(BuildConfig.APPLICATION_ID, "ExoPlayer") && C0604h.m1128i(context).m1132a("adnw_enable_exoplayer", false);
    }

    public static boolean m1121b(Context context) {
        return VERSION.SDK_INT >= 19 && C0604h.m1128i(context).m1132a("adnw_enable_iab", false);
    }

    public static long m1122c(Context context) {
        return C0604h.m1128i(context).m1130a("unified_logging_immediate_delay_ms", 500);
    }

    public static long m1123d(Context context) {
        return ((long) C0604h.m1128i(context).m1129a("unified_logging_dispatch_interval_seconds", 300)) * 1000;
    }

    public static int m1124e(Context context) {
        return C0604h.m1128i(context).m1129a("minimum_elapsed_time_after_impression", -1);
    }

    public static int m1125f(Context context) {
        return C0604h.m1128i(context).m1129a("ad_viewability_tap_margin", 0);
    }

    public static boolean m1126g(Context context) {
        return C0604h.m1128i(context).m1132a("visible_area_check_enabled", false);
    }

    public static int m1127h(Context context) {
        return C0604h.m1128i(context).m1129a("visible_area_percentage", 50);
    }

    private static C0604h m1128i(Context context) {
        if (f872a == null) {
            synchronized (C0604h.class) {
                if (f872a == null) {
                    f872a = new C0604h(context);
                }
            }
        }
        return f872a;
    }

    public int m1129a(String str, int i) {
        String string = this.f873b.getString(str, String.valueOf(i));
        return (string == null || string.equals("null")) ? i : Integer.valueOf(string).intValue();
    }

    public long m1130a(String str, long j) {
        String string = this.f873b.getString(str, String.valueOf(j));
        return (string == null || string.equals("null")) ? j : Long.valueOf(string).longValue();
    }

    public void m1131a(@Nullable String str) {
        if (str != null && !str.isEmpty() && !str.equals("[]")) {
            Editor edit = this.f873b.edit();
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                edit.putString(str2, jSONObject.getString(str2));
            }
            edit.commit();
        }
    }

    public boolean m1132a(String str, boolean z) {
        String string = this.f873b.getString(str, String.valueOf(z));
        return (string == null || string.equals("null")) ? z : Boolean.valueOf(string).booleanValue();
    }
}
