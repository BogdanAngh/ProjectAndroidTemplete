package com.facebook.ads.internal.p008e;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.facebook.ads.internal.e.d */
public class C0515d {
    private List<C0511a> f650a;
    private int f651b;
    private C0516e f652c;
    @Nullable
    private String f653d;

    public C0515d(C0516e c0516e, @Nullable String str) {
        this.f651b = 0;
        this.f650a = new ArrayList();
        this.f652c = c0516e;
        this.f653d = str;
    }

    public C0516e m804a() {
        return this.f652c;
    }

    public void m805a(C0511a c0511a) {
        this.f650a.add(c0511a);
    }

    @Nullable
    public String m806b() {
        return this.f653d;
    }

    public int m807c() {
        return this.f650a.size();
    }

    public C0511a m808d() {
        if (this.f651b >= this.f650a.size()) {
            return null;
        }
        this.f651b++;
        return (C0511a) this.f650a.get(this.f651b - 1);
    }
}
