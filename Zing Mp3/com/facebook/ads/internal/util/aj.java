package com.facebook.ads.internal.util;

import java.lang.ref.WeakReference;

public abstract class aj<T> implements Runnable {
    private final WeakReference<T> f240a;

    public aj(T t) {
        this.f240a = new WeakReference(t);
    }

    public T m263a() {
        return this.f240a.get();
    }
}
