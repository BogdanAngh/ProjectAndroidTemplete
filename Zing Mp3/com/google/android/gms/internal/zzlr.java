package com.google.android.gms.internal;

import java.util.concurrent.TimeUnit;

@zzji
public class zzlr<T> implements zzlt<T> {
    private final T zzcyd;
    private final zzlu zzcyg;

    public zzlr(T t) {
        this.zzcyd = t;
        this.zzcyg = new zzlu();
        this.zzcyg.zzwt();
    }

    public boolean cancel(boolean z) {
        return false;
    }

    public T get() {
        return this.zzcyd;
    }

    public T get(long j, TimeUnit timeUnit) {
        return this.zzcyd;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    public void zzc(Runnable runnable) {
        this.zzcyg.zzc(runnable);
    }
}
