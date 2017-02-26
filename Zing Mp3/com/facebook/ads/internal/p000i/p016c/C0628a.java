package com.facebook.ads.internal.p000i.p016c;

import android.util.SparseArray;

/* renamed from: com.facebook.ads.internal.i.c.a */
public class C0628a {
    private final SparseArray<int[]> f921a;

    public C0628a() {
        this.f921a = new SparseArray();
    }

    public void m1172a(int i, int[] iArr) {
        this.f921a.put(i, iArr);
    }

    public int[] m1173a(int i) {
        return (int[]) this.f921a.get(i);
    }

    public boolean m1174b(int i) {
        return this.f921a.indexOfKey(i) >= 0;
    }
}
