package com.facebook.ads.internal.util;

import android.graphics.Bitmap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ac {
    static final int f1360a;
    static final ExecutorService f1361b;
    private static volatile boolean f1362c;
    private final Bitmap f1363d;
    private Bitmap f1364e;
    private final C0788l f1365f;

    static {
        f1360a = Runtime.getRuntime().availableProcessors();
        f1361b = Executors.newFixedThreadPool(f1360a);
        f1362c = true;
    }

    public ac(Bitmap bitmap) {
        this.f1363d = bitmap;
        this.f1365f = new C0800u();
    }

    public Bitmap m1580a() {
        return this.f1364e;
    }

    public Bitmap m1581a(int i) {
        this.f1364e = this.f1365f.m1642a(this.f1363d, (float) i);
        return this.f1364e;
    }
}
