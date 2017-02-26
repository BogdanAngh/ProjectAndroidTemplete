package com.facebook.ads.internal.p010h.p012b;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.facebook.ads.internal.p010h.p012b.p013a.C0572b;
import java.io.File;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.facebook.ads.internal.h.b.g */
final class C0594g {
    private final AtomicInteger f860a;
    private final String f861b;
    private volatile C0585e f862c;
    private final List<C0581b> f863d;
    private final C0581b f864e;
    private final C0582c f865f;

    /* renamed from: com.facebook.ads.internal.h.b.g.a */
    private static final class C0593a extends Handler implements C0581b {
        private final String f858a;
        private final List<C0581b> f859b;

        public C0593a(String str, List<C0581b> list) {
            super(Looper.getMainLooper());
            this.f858a = str;
            this.f859b = list;
        }

        public void m1088a(File file, String str, int i) {
            Message obtainMessage = obtainMessage();
            obtainMessage.arg1 = i;
            obtainMessage.obj = file;
            sendMessage(obtainMessage);
        }

        public void handleMessage(Message message) {
            for (C0581b a : this.f859b) {
                a.m1036a((File) message.obj, this.f858a, message.arg1);
            }
        }
    }

    public C0594g(String str, C0582c c0582c) {
        this.f860a = new AtomicInteger(0);
        this.f863d = new CopyOnWriteArrayList();
        this.f861b = (String) C0599j.m1107a(str);
        this.f865f = (C0582c) C0599j.m1107a(c0582c);
        this.f864e = new C0593a(str, this.f863d);
    }

    private synchronized void m1089c() {
        this.f862c = this.f862c == null ? m1091e() : this.f862c;
    }

    private synchronized void m1090d() {
        if (this.f860a.decrementAndGet() <= 0) {
            this.f862c.m1051a();
            this.f862c = null;
        }
    }

    private C0585e m1091e() {
        C0585e c0585e = new C0585e(new C0596h(this.f861b), new C0572b(this.f865f.m1037a(this.f861b), this.f865f.f823c));
        c0585e.m1060a(this.f864e);
        return c0585e;
    }

    public void m1092a() {
        this.f863d.clear();
        if (this.f862c != null) {
            this.f862c.m1060a(null);
            this.f862c.m1051a();
            this.f862c = null;
        }
        this.f860a.set(0);
    }

    public void m1093a(C0583d c0583d, Socket socket) {
        m1089c();
        try {
            this.f860a.incrementAndGet();
            this.f862c.m1061a(c0583d, socket);
        } finally {
            m1090d();
        }
    }

    public int m1094b() {
        return this.f860a.get();
    }
}
