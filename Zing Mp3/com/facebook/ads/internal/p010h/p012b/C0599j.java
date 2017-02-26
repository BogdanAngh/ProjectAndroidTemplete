package com.facebook.ads.internal.p010h.p012b;

/* renamed from: com.facebook.ads.internal.h.b.j */
final class C0599j {
    static <T> T m1107a(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    static <T> T m1108a(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static void m1109a(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(str);
        }
    }
}
