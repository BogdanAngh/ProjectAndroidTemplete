package com.facebook.ads.internal.p006b;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.facebook.ads.internal.util.ad;

/* renamed from: com.facebook.ads.internal.b.d */
public class C0486d implements ad<Bundle> {
    private C0485c f542a;
    private final C0485c f543b;
    private final C0483b f544c;
    private boolean f545d;
    private boolean f546e;
    private boolean f547f;

    public C0486d(C0483b c0483b) {
        this.f545d = false;
        this.f546e = false;
        this.f547f = false;
        this.f544c = c0483b;
        this.f543b = new C0485c(c0483b.f525a);
        this.f542a = new C0485c(c0483b.f525a);
    }

    public C0486d(@NonNull C0483b c0483b, @NonNull Bundle bundle) {
        this.f545d = false;
        this.f546e = false;
        this.f547f = false;
        this.f544c = c0483b;
        this.f543b = (C0485c) bundle.getSerializable("testStats");
        this.f542a = (C0485c) bundle.getSerializable("viewableStats");
        this.f545d = bundle.getBoolean("ended");
        this.f546e = bundle.getBoolean("passed");
        this.f547f = bundle.getBoolean("complete");
    }

    private void m716a() {
        this.f546e = true;
        m717b();
    }

    private void m717b() {
        this.f547f = true;
        m718c();
    }

    private void m718c() {
        this.f545d = true;
        this.f544c.m702a(this.f547f, this.f546e, this.f546e ? this.f542a : this.f543b);
    }

    public void m719a(double d, double d2) {
        if (!this.f545d) {
            this.f543b.m712a(d, d2);
            this.f542a.m712a(d, d2);
            double f = this.f542a.m713b().m709f();
            if (this.f544c.f528d && d2 < this.f544c.f525a) {
                this.f542a = new C0485c(this.f544c.f525a);
            }
            if (this.f544c.f526b >= 0.0d && this.f543b.m713b().m708e() > this.f544c.f526b && f == 0.0d) {
                m717b();
            } else if (f >= this.f544c.f527c) {
                m716a();
            }
        }
    }

    public Bundle getSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("viewableStats", this.f542a);
        bundle.putSerializable("testStats", this.f543b);
        bundle.putBoolean("ended", this.f545d);
        bundle.putBoolean("passed", this.f546e);
        bundle.putBoolean("complete", this.f547f);
        return bundle;
    }
}
