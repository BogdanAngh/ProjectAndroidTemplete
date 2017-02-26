package com.facebook.ads.internal.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.Window;
import java.io.File;

/* renamed from: com.facebook.ads.internal.util.o */
public class C0794o {
    private static final String f1459a;

    /* renamed from: com.facebook.ads.internal.util.o.a */
    public enum C0793a {
        UNKNOWN(0),
        UNROOTED(1),
        ROOTED(2);
        
        public final int f1458d;

        private C0793a(int i) {
            this.f1458d = i;
        }
    }

    static {
        f1459a = C0794o.class.getSimpleName();
    }

    public static C0793a m1656a() {
        try {
            Object obj = (C0794o.m1660c() || C0794o.m1659b() || C0794o.m1658a("su")) ? 1 : null;
            return obj != null ? C0793a.ROOTED : C0793a.UNROOTED;
        } catch (Throwable th) {
            return C0793a.UNKNOWN;
        }
    }

    public static boolean m1657a(Context context) {
        if (context == null) {
            return true;
        }
        try {
            if (!((PowerManager) context.getSystemService("power")).isScreenOn()) {
                return false;
            }
            boolean z;
            if (context instanceof Activity) {
                Window window = ((Activity) context).getWindow();
                if (window != null) {
                    int i = window.getAttributes().flags;
                    boolean z2 = ((AccessibilityEventCompat.TYPE_WINDOWS_CHANGED & i) == 0 && (i & AccessibilityNodeInfoCompat.ACTION_COLLAPSE) == 0) ? false : true;
                    z = z2;
                    return ((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode() || z;
                }
            }
            z = false;
            if (((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean m1658a(String str) {
        for (String file : System.getenv("PATH").split(":")) {
            File file2 = new File(file);
            if (file2.exists() && file2.isDirectory()) {
                File[] listFiles = file2.listFiles();
                if (listFiles != null) {
                    for (File name : listFiles) {
                        if (name.getName().equals(str)) {
                            return true;
                        }
                    }
                    continue;
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    private static boolean m1659b() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    private static boolean m1660c() {
        return new File("/system/app/Superuser.apk").exists();
    }
}
