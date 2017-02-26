package com.facebook.ads.internal.server;

import com.facebook.ads.internal.p008e.C0515d;

/* renamed from: com.facebook.ads.internal.server.d */
public class C0760d {
    private C0515d f1321a;
    private C0759a f1322b;

    /* renamed from: com.facebook.ads.internal.server.d.a */
    public enum C0759a {
        UNKNOWN,
        ERROR,
        ADS
    }

    public C0760d(C0759a c0759a, C0515d c0515d) {
        this.f1322b = c0759a;
        this.f1321a = c0515d;
    }

    public C0759a m1527a() {
        return this.f1322b;
    }

    public C0515d m1528b() {
        return this.f1321a;
    }
}
