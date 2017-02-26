package com.facebook.ads.internal.p010h.p011a;

import com.mp3download.zingmp3.BuildConfig;

/* renamed from: com.facebook.ads.internal.h.a.l */
public abstract class C0562l {
    protected String f799a;
    protected C0564j f800b;
    protected String f801c;
    protected byte[] f802d;

    public C0562l(String str, C0569p c0569p) {
        this.f799a = BuildConfig.FLAVOR;
        if (str != null) {
            this.f799a = str;
        }
        if (c0569p != null) {
            this.f799a += "?" + c0569p.m998a();
        }
    }

    public String m982a() {
        return this.f799a;
    }

    public C0564j m983b() {
        return this.f800b;
    }

    public String m984c() {
        return this.f801c;
    }

    public byte[] m985d() {
        return this.f802d;
    }
}
