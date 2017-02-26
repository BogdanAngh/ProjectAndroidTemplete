package com.facebook.ads.internal.p010h.p012b;

import android.util.Log;
import java.lang.Thread.State;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.facebook.ads.internal.h.b.k */
class C0584k {
    private final C0595n f829a;
    private final C0571a f830b;
    private final Object f831c;
    private final Object f832d;
    private final AtomicInteger f833e;
    private volatile Thread f834f;
    private volatile boolean f835g;
    private volatile int f836h;

    /* renamed from: com.facebook.ads.internal.h.b.k.a */
    private class C0601a implements Runnable {
        final /* synthetic */ C0584k f871a;

        private C0601a(C0584k c0584k) {
            this.f871a = c0584k;
        }

        public void run() {
            this.f871a.m1046e();
        }
    }

    public C0584k(C0595n c0595n, C0571a c0571a) {
        this.f831c = new Object();
        this.f832d = new Object();
        this.f836h = -1;
        this.f829a = (C0595n) C0599j.m1107a(c0595n);
        this.f830b = (C0571a) C0599j.m1107a(c0571a);
        this.f833e = new AtomicInteger();
    }

    private void m1042b() {
        int i = this.f833e.get();
        if (i >= 1) {
            this.f833e.set(0);
            throw new C0597l("Error reading source " + i + " times");
        }
    }

    private void m1043b(long j, long j2) {
        m1053a(j, j2);
        synchronized (this.f831c) {
            this.f831c.notifyAll();
        }
    }

    private synchronized void m1044c() {
        Object obj = (this.f834f == null || this.f834f.getState() == State.TERMINATED) ? null : 1;
        if (!(this.f835g || this.f830b.m1009d() || obj != null)) {
            this.f834f = new Thread(new C0601a(), "Source reader for " + this.f829a);
            this.f834f.start();
        }
    }

    private void m1045d() {
        synchronized (this.f831c) {
            try {
                this.f831c.wait(1000);
            } catch (Throwable e) {
                throw new C0597l("Waiting source data is interrupted!", e);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m1046e() {
        /*
        r8 = this;
        r3 = -1;
        r1 = 0;
        r0 = r8.f830b;	 Catch:{ Throwable -> 0x006d, all -> 0x006a }
        r1 = r0.m1004a();	 Catch:{ Throwable -> 0x006d, all -> 0x006a }
        r0 = r8.f829a;	 Catch:{ Throwable -> 0x006d, all -> 0x006a }
        r0.m1097a(r1);	 Catch:{ Throwable -> 0x006d, all -> 0x006a }
        r0 = r8.f829a;	 Catch:{ Throwable -> 0x006d, all -> 0x006a }
        r2 = r0.m1095a();	 Catch:{ Throwable -> 0x006d, all -> 0x006a }
        r0 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = new byte[r0];	 Catch:{ Throwable -> 0x003f }
    L_0x0017:
        r4 = r8.f829a;	 Catch:{ Throwable -> 0x003f }
        r4 = r4.m1096a(r0);	 Catch:{ Throwable -> 0x003f }
        if (r4 == r3) goto L_0x005e;
    L_0x001f:
        r5 = r8.f832d;	 Catch:{ Throwable -> 0x003f }
        monitor-enter(r5);	 Catch:{ Throwable -> 0x003f }
        r6 = r8.m1048g();	 Catch:{ all -> 0x0051 }
        if (r6 == 0) goto L_0x0032;
    L_0x0028:
        monitor-exit(r5);	 Catch:{ all -> 0x0051 }
        r8.m1049h();
        r0 = (long) r1;
        r2 = (long) r2;
        r8.m1043b(r0, r2);
    L_0x0031:
        return;
    L_0x0032:
        r6 = r8.f830b;	 Catch:{ all -> 0x0051 }
        r6.m1006a(r0, r4);	 Catch:{ all -> 0x0051 }
        monitor-exit(r5);	 Catch:{ all -> 0x0051 }
        r1 = r1 + r4;
        r4 = (long) r1;
        r6 = (long) r2;
        r8.m1043b(r4, r6);	 Catch:{ Throwable -> 0x003f }
        goto L_0x0017;
    L_0x003f:
        r0 = move-exception;
    L_0x0040:
        r3 = r8.f833e;	 Catch:{ all -> 0x0054 }
        r3.incrementAndGet();	 Catch:{ all -> 0x0054 }
        r8.m1054a(r0);	 Catch:{ all -> 0x0054 }
        r8.m1049h();
        r0 = (long) r1;
        r2 = (long) r2;
        r8.m1043b(r0, r2);
        goto L_0x0031;
    L_0x0051:
        r0 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x0051 }
        throw r0;	 Catch:{ Throwable -> 0x003f }
    L_0x0054:
        r0 = move-exception;
    L_0x0055:
        r8.m1049h();
        r4 = (long) r1;
        r2 = (long) r2;
        r8.m1043b(r4, r2);
        throw r0;
    L_0x005e:
        r8.m1047f();	 Catch:{ Throwable -> 0x003f }
        r8.m1049h();
        r0 = (long) r1;
        r2 = (long) r2;
        r8.m1043b(r0, r2);
        goto L_0x0031;
    L_0x006a:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0055;
    L_0x006d:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.ads.internal.h.b.k.e():void");
    }

    private void m1047f() {
        synchronized (this.f832d) {
            if (!m1048g() && this.f830b.m1004a() == this.f829a.m1095a()) {
                this.f830b.m1008c();
            }
        }
    }

    private boolean m1048g() {
        return Thread.currentThread().isInterrupted() || this.f835g;
    }

    private void m1049h() {
        try {
            this.f829a.m1098b();
        } catch (Throwable e) {
            m1054a(new C0597l("Error closing source " + this.f829a, e));
        }
    }

    public int m1050a(byte[] bArr, long j, int i) {
        C0602m.m1113a(bArr, j, i);
        while (!this.f830b.m1009d() && ((long) this.f830b.m1004a()) < ((long) i) + j && !this.f835g) {
            m1044c();
            m1045d();
            m1042b();
        }
        int a = this.f830b.m1005a(bArr, j, i);
        if (this.f830b.m1009d() && this.f836h != 100) {
            this.f836h = 100;
            m1052a(100);
        }
        return a;
    }

    public void m1051a() {
        synchronized (this.f832d) {
            Log.d("ProxyCache", "Shutdown proxy for " + this.f829a);
            try {
                this.f835g = true;
                if (this.f834f != null) {
                    this.f834f.interrupt();
                }
                this.f830b.m1007b();
            } catch (Throwable e) {
                m1054a(e);
            }
        }
    }

    protected void m1052a(int i) {
    }

    protected void m1053a(long j, long j2) {
        Object obj = 1;
        int i = ((j2 > 0 ? 1 : (j2 == 0 ? 0 : -1)) == 0 ? 1 : null) != null ? 100 : (int) ((100 * j) / j2);
        Object obj2 = i != this.f836h ? 1 : null;
        if (j2 < 0) {
            obj = null;
        }
        if (!(obj == null || obj2 == null)) {
            m1052a(i);
        }
        this.f836h = i;
    }

    protected final void m1054a(Throwable th) {
        if (th instanceof C0598i) {
            Log.d("ProxyCache", "ProxyCache is interrupted");
        } else {
            Log.e("ProxyCache", "ProxyCache error", th);
        }
    }
}
