package com.facebook.ads.internal.p008e;

import java.util.UUID;

/* renamed from: com.facebook.ads.internal.e.g */
public class C0520g {
    private static final String f683a;
    private static volatile boolean f684b;
    private static double f685c;
    private static String f686d;

    static {
        f683a = C0520g.class.getSimpleName();
        f684b = false;
    }

    public static void m830a() {
        if (!f684b) {
            synchronized (f683a) {
                if (!f684b) {
                    f684b = true;
                    f685c = ((double) System.currentTimeMillis()) / 1000.0d;
                    f686d = UUID.randomUUID().toString();
                }
            }
        }
    }

    public static double m831b() {
        return f685c;
    }

    public static String m832c() {
        return f686d;
    }
}
