package com.facebook.ads.internal.util;

import com.facebook.ads.internal.p008e.C0514c;
import com.facebook.ads.internal.p008e.C0519f;
import com.mp3download.zingmp3.C1569R;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.facebook.ads.internal.util.e */
public class C0780e {
    private static Map<String, Long> f1417a;
    private static Map<String, Long> f1418b;
    private static Map<String, String> f1419c;

    /* renamed from: com.facebook.ads.internal.util.e.1 */
    static /* synthetic */ class C07791 {
        static final /* synthetic */ int[] f1416a;

        static {
            f1416a = new int[C0514c.values().length];
            try {
                f1416a[C0514c.BANNER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1416a[C0514c.INTERSTITIAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1416a[C0514c.NATIVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1416a[C0514c.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static {
        f1417a = new ConcurrentHashMap();
        f1418b = new ConcurrentHashMap();
        f1419c = new ConcurrentHashMap();
    }

    private static long m1600a(String str, C0514c c0514c) {
        if (f1417a.containsKey(str)) {
            return ((Long) f1417a.get(str)).longValue();
        }
        switch (C07791.f1416a[c0514c.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return 15000;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return -1000;
            default:
                return -1000;
        }
    }

    public static void m1601a(long j, C0519f c0519f) {
        f1417a.put(C0780e.m1606d(c0519f), Long.valueOf(j));
    }

    public static void m1602a(String str, C0519f c0519f) {
        f1419c.put(C0780e.m1606d(c0519f), str);
    }

    public static boolean m1603a(C0519f c0519f) {
        String d = C0780e.m1606d(c0519f);
        if (!f1418b.containsKey(d)) {
            return false;
        }
        return System.currentTimeMillis() - ((Long) f1418b.get(d)).longValue() < C0780e.m1600a(d, c0519f.m826b());
    }

    public static void m1604b(C0519f c0519f) {
        f1418b.put(C0780e.m1606d(c0519f), Long.valueOf(System.currentTimeMillis()));
    }

    public static String m1605c(C0519f c0519f) {
        return (String) f1419c.get(C0780e.m1606d(c0519f));
    }

    private static String m1606d(C0519f c0519f) {
        int i = 0;
        String str = "%s:%s:%s:%d:%d:%d";
        Object[] objArr = new Object[6];
        objArr[0] = c0519f.m825a();
        objArr[1] = c0519f.m826b();
        objArr[2] = c0519f.f678e;
        objArr[3] = Integer.valueOf(c0519f.m827c() == null ? 0 : c0519f.m827c().getHeight());
        if (c0519f.m827c() != null) {
            i = c0519f.m827c().getWidth();
        }
        objArr[4] = Integer.valueOf(i);
        objArr[5] = Integer.valueOf(c0519f.m828d());
        return String.format(str, objArr);
    }
}
