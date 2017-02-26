package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.mp3download.zingmp3.BuildConfig;
import java.util.HashMap;
import java.util.Map;

public class zzbf {
    private static String aFA;
    static Map<String, String> aFB;

    static {
        aFB = new HashMap();
    }

    static void zzaf(Context context, String str) {
        zzdd.zzc(context, "gtm_install_referrer", "referrer", str);
        zzah(context, str);
    }

    public static String zzag(Context context, String str) {
        if (aFA == null) {
            synchronized (zzbf.class) {
                if (aFA == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        aFA = sharedPreferences.getString("referrer", BuildConfig.FLAVOR);
                    } else {
                        aFA = BuildConfig.FLAVOR;
                    }
                }
            }
        }
        return zzbg(aFA, str);
    }

    public static void zzah(Context context, String str) {
        String zzbg = zzbg(str, "conv");
        if (zzbg != null && zzbg.length() > 0) {
            aFB.put(zzbg, str);
            zzdd.zzc(context, "gtm_click_referrers", zzbg, str);
        }
    }

    public static String zzbg(String str, String str2) {
        if (str2 == null) {
            return str.length() > 0 ? str : null;
        } else {
            String str3 = "http://hostname/?";
            String valueOf = String.valueOf(str);
            return Uri.parse(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3)).getQueryParameter(str2);
        }
    }

    public static String zzj(Context context, String str, String str2) {
        String str3 = (String) aFB.get(str);
        if (str3 == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            str3 = sharedPreferences != null ? sharedPreferences.getString(str, BuildConfig.FLAVOR) : BuildConfig.FLAVOR;
            aFB.put(str, str3);
        }
        return zzbg(str3, str2);
    }

    public static void zzpl(String str) {
        synchronized (zzbf.class) {
            aFA = str;
        }
    }
}
