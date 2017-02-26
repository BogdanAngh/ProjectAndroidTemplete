package com.facebook.ads.internal.p008e;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.facebook.ads.internal.C0547f;
import com.facebook.ads.internal.C0547f.C0527c;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0785i.C0784a;
import com.mp3download.zingmp3.BuildConfig;

/* renamed from: com.facebook.ads.internal.e.i */
public class C0522i {
    public static final String f691a;
    public static String f692b;
    public static String f693c;
    public static String f694d;
    public static String f695e;
    public static String f696f;
    public static int f697g;
    public static String f698h;
    public static String f699i;
    public static int f700j;
    public static String f701k;
    public static int f702l;
    public static String f703m;
    public static String f704n;
    public static String f705o;
    public static boolean f706p;
    public static String f707q;
    private static boolean f708r;

    static {
        f708r = false;
        f691a = VERSION.RELEASE;
        f692b = BuildConfig.FLAVOR;
        f693c = BuildConfig.FLAVOR;
        f694d = BuildConfig.FLAVOR;
        f695e = BuildConfig.FLAVOR;
        f696f = BuildConfig.FLAVOR;
        f697g = 0;
        f698h = BuildConfig.FLAVOR;
        f699i = BuildConfig.FLAVOR;
        f700j = 0;
        f701k = BuildConfig.FLAVOR;
        f702l = 0;
        f703m = BuildConfig.FLAVOR;
        f704n = BuildConfig.FLAVOR;
        f705o = BuildConfig.FLAVOR;
        f706p = false;
        f707q = BuildConfig.FLAVOR;
    }

    public static synchronized void m833a(Context context) {
        synchronized (C0522i.class) {
            if (!f708r) {
                f708r = true;
                C0522i.m835c(context);
            }
            C0522i.m836d(context);
        }
    }

    public static void m834b(Context context) {
        if (f708r) {
            try {
                C0784a a;
                C0547f a2;
                SharedPreferences sharedPreferences = context.getSharedPreferences("SDKIDFA", 0);
                if (sharedPreferences.contains("attributionId")) {
                    f704n = sharedPreferences.getString("attributionId", BuildConfig.FLAVOR);
                }
                if (sharedPreferences.contains("advertisingId")) {
                    f705o = sharedPreferences.getString("advertisingId", BuildConfig.FLAVOR);
                    f706p = sharedPreferences.getBoolean("limitAdTracking", f706p);
                    f707q = C0527c.SHARED_PREFS.name();
                }
                try {
                    a = C0785i.m1615a(context.getContentResolver());
                } catch (Throwable e) {
                    C0778d.m1599a(C0777c.m1596a(e, "Error retrieving attribution id from fb4a"));
                    a = null;
                }
                if (a != null) {
                    String str = a.f1424a;
                    if (str != null) {
                        f704n = str;
                    }
                }
                try {
                    a2 = C0547f.m918a(context, a);
                } catch (Throwable e2) {
                    C0778d.m1599a(C0777c.m1596a(e2, "Error retrieving advertising id from Google Play Services"));
                    a2 = null;
                }
                if (a2 != null) {
                    String a3 = a2.m921a();
                    Boolean valueOf = Boolean.valueOf(a2.m922b());
                    if (a3 != null) {
                        f705o = a3;
                        f706p = valueOf.booleanValue();
                        f707q = a2.m923c().name();
                    }
                }
                Editor edit = sharedPreferences.edit();
                edit.putString("attributionId", f704n);
                edit.putString("advertisingId", f705o);
                edit.putBoolean("limitAdTracking", f706p);
                edit.apply();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private static void m835c(Context context) {
        String networkOperatorName;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            f694d = packageInfo.packageName;
            f696f = packageInfo.versionName;
            f697g = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
        }
        try {
            if (f694d != null && f694d.length() >= 0) {
                String installerPackageName = packageManager.getInstallerPackageName(f694d);
                if (installerPackageName != null && installerPackageName.length() > 0) {
                    f698h = installerPackageName;
                }
            }
        } catch (Exception e2) {
        }
        try {
            CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
            if (applicationLabel != null && applicationLabel.length() > 0) {
                f695e = applicationLabel.toString();
            }
        } catch (NameNotFoundException e3) {
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null) {
            networkOperatorName = telephonyManager.getNetworkOperatorName();
            if (networkOperatorName != null && networkOperatorName.length() > 0) {
                f699i = networkOperatorName;
            }
        }
        networkOperatorName = Build.MANUFACTURER;
        if (networkOperatorName != null && networkOperatorName.length() > 0) {
            f692b = networkOperatorName;
        }
        networkOperatorName = Build.MODEL;
        if (networkOperatorName != null && networkOperatorName.length() > 0) {
            f693c = Build.MODEL;
        }
    }

    private static void m836d(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                f700j = activeNetworkInfo.getType();
                f701k = activeNetworkInfo.getTypeName();
                f702l = activeNetworkInfo.getSubtype();
                f703m = activeNetworkInfo.getSubtypeName();
            }
        } catch (Exception e) {
        }
    }
}
