package com.facebook.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.ads.internal.util.AdInternalSettings;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0785i.C0784a;
import com.facebook.ads.internal.util.C0797s;
import com.facebook.internal.ServerProtocol;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class AdSettings {
    public static final boolean DEBUG = false;
    static volatile boolean f30a;
    private static final String f31b;
    private static final Collection<String> f32c;
    private static final Collection<String> f33d;
    private static String f34e;
    private static boolean f35f;
    private static boolean f36g;
    private static String f37h;
    private static boolean f38i;
    private static String f39j;

    static {
        f31b = AdSettings.class.getSimpleName();
        f32c = new HashSet();
        f33d = new HashSet();
        f33d.add(ServerProtocol.DIALOG_PARAM_SDK_VERSION);
        f33d.add("google_sdk");
        f33d.add("vbox86p");
        f33d.add("vbox86tp");
        f30a = false;
    }

    private static void m23a(String str) {
        if (!f30a) {
            f30a = true;
            Log.d(f31b, "Test mode device hash: " + str);
            Log.d(f31b, "When testing your app with Facebook's ad units you must specify the device hashed ID to ensure the delivery of test ads, add the following code before loading an ad: AdSettings.addTestDevice(\"" + str + "\");");
        }
    }

    public static void addTestDevice(String str) {
        f32c.add(str);
    }

    public static void addTestDevices(Collection<String> collection) {
        f32c.addAll(collection);
    }

    public static void clearTestDevices() {
        f32c.clear();
    }

    public static String getMediationService() {
        return f37h;
    }

    public static String getUrlPrefix() {
        return f34e;
    }

    public static boolean isChildDirected() {
        return f38i;
    }

    public static boolean isTestMode(Context context) {
        if (AdInternalSettings.f1325a || f33d.contains(Build.PRODUCT)) {
            return true;
        }
        if (f39j == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("FBAdPrefs", 0);
            f39j = sharedPreferences.getString("deviceIdHash", null);
            if (TextUtils.isEmpty(f39j)) {
                C0784a a = C0785i.m1615a(context.getContentResolver());
                if (!TextUtils.isEmpty(a.f1425b)) {
                    f39j = C0797s.m1668a(a.f1425b);
                } else if (TextUtils.isEmpty(a.f1424a)) {
                    f39j = C0797s.m1668a(UUID.randomUUID().toString());
                } else {
                    f39j = C0797s.m1668a(a.f1424a);
                }
                sharedPreferences.edit().putString("deviceIdHash", f39j).apply();
            }
        }
        if (f32c.contains(f39j)) {
            return true;
        }
        m23a(f39j);
        return false;
    }

    public static boolean isVideoAutoplay() {
        return f35f;
    }

    public static boolean isVideoAutoplayOnMobile() {
        return f36g;
    }

    public static void setIsChildDirected(boolean z) {
        f38i = z;
    }

    public static void setMediationService(String str) {
        f37h = str;
    }

    public static void setUrlPrefix(String str) {
        f34e = str;
    }

    public static void setVideoAutoplay(boolean z) {
        f35f = z;
    }

    public static void setVideoAutoplayOnMobile(boolean z) {
        f36g = z;
    }
}
