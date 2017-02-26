package com.facebook.ads.internal.p006b;

import java.io.Serializable;

/* renamed from: com.facebook.ads.internal.b.c */
public class C0485c implements Serializable {
    private C0484a f540a;
    private C0484a f541b;

    /* renamed from: com.facebook.ads.internal.b.c.a */
    public static class C0484a implements Serializable {
        private double f529a;
        private double f530b;
        private double f531c;
        private double f532d;
        private double f533e;
        private double f534f;
        private double f535g;
        private int f536h;
        private double f537i;
        private double f538j;
        private double f539k;

        public C0484a(double d) {
            this.f533e = d;
        }

        public void m703a() {
            this.f529a = 0.0d;
            this.f531c = 0.0d;
            this.f532d = 0.0d;
            this.f534f = 0.0d;
            this.f536h = 0;
            this.f537i = 0.0d;
            this.f538j = 1.0d;
            this.f539k = 0.0d;
        }

        public void m704a(double d, double d2) {
            this.f536h++;
            this.f537i += d;
            this.f531c = d2;
            this.f539k += d2 * d;
            this.f529a = this.f539k / this.f537i;
            this.f538j = Math.min(this.f538j, d2);
            this.f534f = Math.max(this.f534f, d2);
            if (d2 >= this.f533e) {
                this.f532d += d;
                this.f530b += d;
                this.f535g = Math.max(this.f535g, this.f530b);
                return;
            }
            this.f530b = 0.0d;
        }

        public double m705b() {
            return this.f536h == 0 ? 0.0d : this.f538j;
        }

        public double m706c() {
            return this.f529a;
        }

        public double m707d() {
            return this.f534f;
        }

        public double m708e() {
            return this.f537i;
        }

        public double m709f() {
            return this.f532d;
        }

        public double m710g() {
            return this.f535g;
        }
    }

    public C0485c() {
        this(0.5d, 0.05d);
    }

    public C0485c(double d) {
        this(d, 0.05d);
    }

    public C0485c(double d, double d2) {
        this.f540a = new C0484a(d);
        this.f541b = new C0484a(d2);
        m711a();
    }

    void m711a() {
        this.f540a.m703a();
        this.f541b.m703a();
    }

    void m712a(double d, double d2) {
        this.f540a.m704a(d, d2);
    }

    public C0484a m713b() {
        return this.f540a;
    }

    void m714b(double d, double d2) {
        this.f541b.m704a(d, d2);
    }

    public C0484a m715c() {
        return this.f541b;
    }
}
