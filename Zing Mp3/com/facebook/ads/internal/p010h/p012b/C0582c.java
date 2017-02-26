package com.facebook.ads.internal.p010h.p012b;

import com.facebook.ads.internal.p010h.p012b.p013a.C0570a;
import com.facebook.ads.internal.p010h.p012b.p013a.C0573c;
import java.io.File;

/* renamed from: com.facebook.ads.internal.h.b.c */
class C0582c {
    public final File f821a;
    public final C0573c f822b;
    public final C0570a f823c;

    C0582c(File file, C0573c c0573c, C0570a c0570a) {
        this.f821a = file;
        this.f822b = c0573c;
        this.f823c = c0570a;
    }

    File m1037a(String str) {
        return new File(this.f821a, this.f822b.m1017a(str));
    }
}
