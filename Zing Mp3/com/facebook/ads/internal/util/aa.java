package com.facebook.ads.internal.util;

import android.support.annotation.NonNull;
import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class aa implements ThreadFactory {
    protected final AtomicLong f1334a;
    private int f1335b;

    public aa() {
        this.f1334a = new AtomicLong();
        this.f1335b = Thread.currentThread().getPriority();
    }

    protected String m1542a() {
        return String.format(Locale.ENGLISH, "com.facebook.ads thread-%d %tF %<tT", new Object[]{Long.valueOf(this.f1334a.incrementAndGet()), Long.valueOf(System.currentTimeMillis())});
    }

    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(null, runnable, m1542a(), 0);
        thread.setPriority(this.f1335b);
        return thread;
    }
}
