package com.facebook.ads.internal.p010h.p012b;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import com.facebook.ads.internal.p010h.p012b.p013a.C0570a;
import com.facebook.ads.internal.p010h.p012b.p013a.C0573c;
import com.facebook.ads.internal.p010h.p012b.p013a.C0579f;
import com.facebook.ads.internal.p010h.p012b.p013a.C0580g;
import com.facebook.internal.Utility;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: com.facebook.ads.internal.h.b.f */
public class C0592f {
    private final Object f850a;
    private final ExecutorService f851b;
    private final Map<String, C0594g> f852c;
    private final ServerSocket f853d;
    private final int f854e;
    private final Thread f855f;
    private final C0582c f856g;
    private boolean f857h;

    /* renamed from: com.facebook.ads.internal.h.b.f.a */
    public static final class C0587a {
        private File f840a;
        private C0573c f841b;
        private C0570a f842c;

        public C0587a(Context context) {
            this.f840a = C0603o.m1117a(context);
            this.f842c = new C0580g(67108864);
            this.f841b = new C0579f();
        }

        private C0582c m1062a() {
            return new C0582c(this.f840a, this.f841b, this.f842c);
        }
    }

    /* renamed from: com.facebook.ads.internal.h.b.f.b */
    private class C0588b implements Callable<Boolean> {
        final /* synthetic */ C0592f f843a;

        private C0588b(C0592f c0592f) {
            this.f843a = c0592f;
        }

        public Boolean m1064a() {
            return Boolean.valueOf(this.f843a.m1075c());
        }

        public /* synthetic */ Object call() {
            return m1064a();
        }
    }

    /* renamed from: com.facebook.ads.internal.h.b.f.c */
    private class C0589c implements Callable<Boolean> {
        final /* synthetic */ C0592f f844a;
        private final String f845b;

        public C0589c(C0592f c0592f, String str) {
            this.f844a = c0592f;
            this.f845b = str;
        }

        public Boolean m1065a() {
            return Boolean.valueOf(this.f844a.m1076c(this.f845b));
        }

        public /* synthetic */ Object call() {
            return m1065a();
        }
    }

    /* renamed from: com.facebook.ads.internal.h.b.f.d */
    private final class C0590d implements Runnable {
        final /* synthetic */ C0592f f846a;
        private final Socket f847b;

        public C0590d(C0592f c0592f, Socket socket) {
            this.f846a = c0592f;
            this.f847b = socket;
        }

        public void run() {
            this.f846a.m1069a(this.f847b);
        }
    }

    /* renamed from: com.facebook.ads.internal.h.b.f.e */
    private final class C0591e implements Runnable {
        final /* synthetic */ C0592f f848a;
        private final CountDownLatch f849b;

        public C0591e(C0592f c0592f, CountDownLatch countDownLatch) {
            this.f848a = c0592f;
            this.f849b = countDownLatch;
        }

        public void run() {
            this.f849b.countDown();
            this.f848a.m1081e();
        }
    }

    public C0592f(Context context) {
        this(new C0587a(context).m1062a());
    }

    private C0592f(C0582c c0582c) {
        Throwable e;
        this.f850a = new Object();
        this.f851b = Executors.newFixedThreadPool(8);
        this.f852c = new ConcurrentHashMap();
        this.f856g = (C0582c) C0599j.m1107a(c0582c);
        try {
            this.f853d = new ServerSocket(0, 8, InetAddress.getByName("127.0.0.1"));
            this.f854e = this.f853d.getLocalPort();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.f855f = new Thread(new C0591e(this, countDownLatch));
            this.f855f.start();
            countDownLatch.await();
            Log.i("ProxyCache", "Proxy cache server started. Ping it...");
            m1071b();
        } catch (IOException e2) {
            e = e2;
            this.f851b.shutdown();
            throw new IllegalStateException("Error starting local proxy server", e);
        } catch (InterruptedException e3) {
            e = e3;
            this.f851b.shutdown();
            throw new IllegalStateException("Error starting local proxy server", e);
        }
    }

    private void m1068a(Throwable th) {
        Log.e("ProxyCache", "HttpProxyCacheServer error", th);
    }

    private void m1069a(Socket socket) {
        Throwable e;
        try {
            C0583d a = C0583d.m1039a(socket.getInputStream());
            Log.i("ProxyCache", "Request to cache proxy:" + a);
            String c = C0602m.m1115c(a.f826a);
            if ("ping".equals(c)) {
                m1072b(socket);
            } else {
                m1080e(c).m1093a(a, socket);
            }
            m1074c(socket);
            Log.d("ProxyCache", "Opened connections: " + m1083f());
        } catch (SocketException e2) {
            Log.d("ProxyCache", "Closing socket... Socket is closed by client.");
            m1074c(socket);
            Log.d("ProxyCache", "Opened connections: " + m1083f());
        } catch (C0597l e3) {
            e = e3;
            m1068a(new C0597l("Error processing request", e));
            m1074c(socket);
            Log.d("ProxyCache", "Opened connections: " + m1083f());
        } catch (IOException e4) {
            e = e4;
            m1068a(new C0597l("Error processing request", e));
            m1074c(socket);
            Log.d("ProxyCache", "Opened connections: " + m1083f());
        } catch (Throwable th) {
            m1074c(socket);
            Log.d("ProxyCache", "Opened connections: " + m1083f());
        }
    }

    private void m1071b() {
        int i = 300;
        int i2 = 0;
        while (i2 < 3) {
            try {
                this.f857h = ((Boolean) this.f851b.submit(new C0588b()).get((long) i, TimeUnit.MILLISECONDS)).booleanValue();
                if (!this.f857h) {
                    SystemClock.sleep((long) i);
                    i *= 2;
                    i2++;
                } else {
                    return;
                }
            } catch (InterruptedException e) {
                Throwable e2 = e;
                Log.e("ProxyCache", "Error pinging server [attempt: " + i2 + ", timeout: " + i + "]. ", e2);
                i *= 2;
                i2++;
            } catch (ExecutionException e3) {
                e2 = e3;
                Log.e("ProxyCache", "Error pinging server [attempt: " + i2 + ", timeout: " + i + "]. ", e2);
                i *= 2;
                i2++;
            } catch (TimeoutException e4) {
                e2 = e4;
                Log.e("ProxyCache", "Error pinging server [attempt: " + i2 + ", timeout: " + i + "]. ", e2);
                i *= 2;
                i2++;
            }
        }
        Log.e("ProxyCache", "Shutdown server... Error pinging server [attempts: " + i2 + ", max timeout: " + (i / 2) + "].");
        m1085a();
    }

    private void m1072b(Socket socket) {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("HTTP/1.1 200 OK\n\n".getBytes());
        outputStream.write("ping ok".getBytes());
    }

    private void m1074c(Socket socket) {
        m1079d(socket);
        m1082e(socket);
        m1084f(socket);
    }

    private boolean m1075c() {
        C0596h c0596h = new C0596h(m1077d("ping"));
        boolean equals;
        try {
            byte[] bytes = "ping ok".getBytes();
            c0596h.m1104a(0);
            byte[] bArr = new byte[bytes.length];
            c0596h.m1103a(bArr);
            equals = Arrays.equals(bytes, bArr);
            Log.d("ProxyCache", "Ping response: `" + new String(bArr) + "`, pinged? " + equals);
            return equals;
        } catch (C0597l e) {
            equals = e;
            Log.e("ProxyCache", "Error reading ping response", equals);
            return false;
        } finally {
            c0596h.m1105b();
        }
    }

    private boolean m1076c(String str) {
        C0596h c0596h = new C0596h(m1077d(str));
        try {
            c0596h.m1104a(0);
            while (true) {
                if (c0596h.m1103a(new byte[Utility.DEFAULT_STREAM_BUFFER_SIZE]) == -1) {
                    break;
                }
            }
            return true;
        } catch (Throwable e) {
            Log.e("ProxyCache", "Error reading url", e);
            return false;
        } finally {
            c0596h.m1105b();
        }
    }

    private String m1077d(String str) {
        return String.format("http://%s:%d/%s", new Object[]{"127.0.0.1", Integer.valueOf(this.f854e), C0602m.m1114b(str)});
    }

    private void m1078d() {
        synchronized (this.f850a) {
            for (C0594g a : this.f852c.values()) {
                a.m1092a();
            }
            this.f852c.clear();
        }
    }

    private void m1079d(Socket socket) {
        try {
            if (!socket.isInputShutdown()) {
                socket.shutdownInput();
            }
        } catch (SocketException e) {
            Log.d("ProxyCache", "Releasing input stream... Socket is closed by client.");
        } catch (Throwable e2) {
            m1068a(new C0597l("Error closing socket input stream", e2));
        }
    }

    private C0594g m1080e(String str) {
        C0594g c0594g;
        synchronized (this.f850a) {
            c0594g = (C0594g) this.f852c.get(str);
            if (c0594g == null) {
                c0594g = new C0594g(str, this.f856g);
                this.f852c.put(str, c0594g);
            }
        }
        return c0594g;
    }

    private void m1081e() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket accept = this.f853d.accept();
                Log.d("ProxyCache", "Accept new socket " + accept);
                this.f851b.submit(new C0590d(this, accept));
            } catch (Throwable e) {
                m1068a(new C0597l("Error during waiting connection", e));
                return;
            }
        }
    }

    private void m1082e(Socket socket) {
        try {
            if (socket.isOutputShutdown()) {
                socket.shutdownOutput();
            }
        } catch (Throwable e) {
            m1068a(new C0597l("Error closing socket output stream", e));
        }
    }

    private int m1083f() {
        int i;
        synchronized (this.f850a) {
            i = 0;
            for (C0594g b : this.f852c.values()) {
                i = b.m1094b() + i;
            }
        }
        return i;
    }

    private void m1084f(Socket socket) {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Throwable e) {
            m1068a(new C0597l("Error closing socket", e));
        }
    }

    public void m1085a() {
        Log.i("ProxyCache", "Shutdown proxy server");
        m1078d();
        this.f855f.interrupt();
        try {
            if (!this.f853d.isClosed()) {
                this.f853d.close();
            }
        } catch (Throwable e) {
            m1068a(new C0597l("Error shutting down proxy server", e));
        }
    }

    public void m1086a(String str) {
        int i = 300;
        int i2 = 0;
        while (i2 < 3) {
            try {
                if (!((Boolean) this.f851b.submit(new C0589c(this, str)).get()).booleanValue()) {
                    SystemClock.sleep((long) i);
                    i *= 2;
                    i2++;
                } else {
                    return;
                }
            } catch (InterruptedException e) {
                Throwable e2 = e;
                Log.e("ProxyCache", "Error precaching url [attempt: " + i2 + ", url: " + str + "]. ", e2);
                i *= 2;
                i2++;
            } catch (ExecutionException e3) {
                e2 = e3;
                Log.e("ProxyCache", "Error precaching url [attempt: " + i2 + ", url: " + str + "]. ", e2);
                i *= 2;
                i2++;
            }
        }
        Log.e("ProxyCache", "Shutdown server... Error precaching url [attempts: " + i2 + ", url: " + str + "].");
        m1085a();
    }

    public String m1087b(String str) {
        if (!this.f857h) {
            Log.e("ProxyCache", "Proxy server isn't pinged. Caching doesn't work.");
        }
        return this.f857h ? m1077d(str) : str;
    }
}
