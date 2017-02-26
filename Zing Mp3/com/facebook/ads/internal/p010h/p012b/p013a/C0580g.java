package com.facebook.ads.internal.p010h.p012b.p013a;

import java.io.File;

/* renamed from: com.facebook.ads.internal.h.b.a.g */
public class C0580g extends C0578e {
    private final long f820a;

    public C0580g(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("Max size must be positive number!");
        }
        this.f820a = j;
    }

    public /* bridge */ /* synthetic */ void m1034a(File file) {
        super.m1030a(file);
    }

    protected boolean m1035a(File file, long j, int i) {
        return j <= this.f820a;
    }
}
