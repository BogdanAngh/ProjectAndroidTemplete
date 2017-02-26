package com.facebook.ads.internal.p010h.p011a;

import android.os.Build.VERSION;
import android.util.Log;
import com.facebook.ads.AdError;
import com.google.android.exoplayer.upstream.UdpDataSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.net.ssl.HttpsURLConnection;

/* renamed from: com.facebook.ads.internal.h.a.a */
public class C0554a {
    private static int[] f784f;
    private static final String f785g;
    protected final C0551q f786a;
    protected final C0557d f787b;
    protected C0559r f788c;
    protected int f789d;
    protected int f790e;
    private int f791h;
    private Map<String, String> f792i;
    private boolean f793j;
    private Set<String> f794k;
    private Set<String> f795l;

    /* renamed from: com.facebook.ads.internal.h.a.a.1 */
    class C05531 extends C0552f {
        final /* synthetic */ C0554a f783a;

        C05531(C0554a c0554a) {
            this.f783a = c0554a;
        }
    }

    static {
        f784f = new int[20];
        f785g = C0554a.class.getSimpleName();
        C0554a.m943c();
        if (VERSION.SDK_INT > 8) {
            C0554a.m942a();
        }
    }

    public C0554a() {
        this.f787b = new C0558e();
        this.f788c = new C0560g();
        this.f789d = UdpDataSource.DEFAULT_MAX_PACKET_SIZE;
        this.f790e = UdpDataSource.DEAFULT_SOCKET_TIMEOUT_MILLIS;
        this.f791h = 3;
        this.f792i = new TreeMap();
        this.f786a = new C05531(this);
    }

    public static void m942a() {
        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
    }

    private static void m943c() {
        if (VERSION.SDK_INT < 8) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private void m944c(HttpURLConnection httpURLConnection) {
        for (String str : this.f792i.keySet()) {
            httpURLConnection.setRequestProperty(str, (String) this.f792i.get(str));
        }
    }

    protected int m945a(int i) {
        return f784f[i + 2] * AdError.NETWORK_ERROR_CODE;
    }

    protected int m946a(HttpURLConnection httpURLConnection, byte[] bArr) {
        OutputStream outputStream = null;
        try {
            outputStream = this.f786a.m928a(httpURLConnection);
            if (outputStream != null) {
                this.f786a.m930a(outputStream, bArr);
            }
            int responseCode = httpURLConnection.getResponseCode();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                }
            }
            return responseCode;
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    public C0554a m947a(String str, String str2) {
        this.f792i.put(str, str2);
        return this;
    }

    public C0567n m948a(C0562l c0562l) {
        int i = 0;
        long currentTimeMillis = System.currentTimeMillis();
        while (i < this.f791h) {
            try {
                m964c(m945a(i));
                if (this.f788c.m973a()) {
                    this.f788c.m971a((i + 1) + "of" + this.f791h + ", trying " + c0562l.m982a());
                }
                currentTimeMillis = System.currentTimeMillis();
                C0567n a = m949a(c0562l.m982a(), c0562l.m983b(), c0562l.m984c(), c0562l.m985d());
                if (a != null) {
                    return a;
                }
                i++;
            } catch (C0566m e) {
                if (m957a((Throwable) e, currentTimeMillis) && i < this.f791h - 1) {
                    continue;
                } else if (!this.f786a.m932a(e) || i >= this.f791h - 1) {
                    throw e;
                } else {
                    try {
                        Thread.sleep((long) this.f789d);
                    } catch (InterruptedException e2) {
                        throw e;
                    }
                }
            }
        }
        return null;
    }

    protected C0567n m949a(String str, C0564j c0564j, String str2, byte[] bArr) {
        HttpURLConnection a;
        Throwable e;
        HttpURLConnection httpURLConnection;
        Exception exception;
        C0567n c0567n = null;
        Object obj = 1;
        C0567n c0567n2 = null;
        C0567n a2;
        try {
            this.f793j = false;
            a = m952a(str);
            try {
                m955a(a, c0564j, str2);
                m944c(a);
                if (this.f788c.m973a()) {
                    this.f788c.m972a(a, bArr);
                }
                a.connect();
                this.f793j = true;
                Object obj2 = (this.f795l == null || this.f795l.isEmpty()) ? null : 1;
                if (this.f794k == null || this.f794k.isEmpty()) {
                    obj = null;
                }
                if ((a instanceof HttpsURLConnection) && !(obj2 == null && r1 == null)) {
                    try {
                        C0568o.m996a((HttpsURLConnection) a, this.f795l, this.f794k);
                    } catch (Throwable e2) {
                        Log.e(f785g, "Unable to validate SSL certificates.", e2);
                    } catch (Throwable th) {
                        e2 = th;
                        if (this.f788c.m973a()) {
                            this.f788c.m970a(c0567n);
                        }
                        if (a != null) {
                            a.disconnect();
                        }
                        throw e2;
                    }
                }
                if (a.getDoOutput() && bArr != null) {
                    m946a(a, bArr);
                }
                a2 = a.getDoInput() ? m951a(a) : new C0567n(a, null);
                if (this.f788c.m973a()) {
                    this.f788c.m970a(a2);
                }
                if (a == null) {
                    return a2;
                }
                a.disconnect();
                return a2;
            } catch (Exception e3) {
                httpURLConnection = a;
                exception = e3;
                try {
                    a2 = m960b(httpURLConnection);
                    if (a2 != null) {
                        try {
                            if (a2.m990a() > 0) {
                                if (this.f788c.m973a()) {
                                    this.f788c.m970a(a2);
                                }
                                if (httpURLConnection != null) {
                                    return a2;
                                }
                                httpURLConnection.disconnect();
                                return a2;
                            }
                        } catch (Throwable th2) {
                            c0567n = a2;
                            e2 = th2;
                            a = httpURLConnection;
                            if (this.f788c.m973a()) {
                                this.f788c.m970a(c0567n);
                            }
                            if (a != null) {
                                a.disconnect();
                            }
                            throw e2;
                        }
                    }
                    throw new C0566m(exception, a2);
                } catch (Exception e4) {
                    exception.printStackTrace();
                    if (null != null) {
                        if (c0567n2.m990a() > 0) {
                            if (this.f788c.m973a()) {
                                this.f788c.m970a(null);
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            return null;
                        }
                    }
                    throw new C0566m(exception, c0567n2);
                } catch (Throwable th3) {
                    e2 = th3;
                    a = httpURLConnection;
                    if (this.f788c.m973a()) {
                        this.f788c.m970a(c0567n);
                    }
                    if (a != null) {
                        a.disconnect();
                    }
                    throw e2;
                }
            } catch (Throwable th4) {
                e2 = th4;
                if (this.f788c.m973a()) {
                    this.f788c.m970a(c0567n);
                }
                if (a != null) {
                    a.disconnect();
                }
                throw e2;
            }
        } catch (Exception e32) {
            exception = e32;
            httpURLConnection = null;
            a2 = m960b(httpURLConnection);
            if (a2 != null) {
                if (a2.m990a() > 0) {
                    if (this.f788c.m973a()) {
                        this.f788c.m970a(a2);
                    }
                    if (httpURLConnection != null) {
                        return a2;
                    }
                    httpURLConnection.disconnect();
                    return a2;
                }
            }
            throw new C0566m(exception, a2);
        } catch (Throwable th5) {
            e2 = th5;
            a = null;
            if (this.f788c.m973a()) {
                this.f788c.m970a(c0567n);
            }
            if (a != null) {
                a.disconnect();
            }
            throw e2;
        }
    }

    public C0567n m950a(String str, C0569p c0569p) {
        return m958b(new C0563i(str, c0569p));
    }

    protected C0567n m951a(HttpURLConnection httpURLConnection) {
        Throwable th;
        byte[] bArr = null;
        InputStream b;
        try {
            b = this.f786a.m934b(httpURLConnection);
            if (b != null) {
                try {
                    bArr = this.f786a.m933a(b);
                } catch (Throwable th2) {
                    th = th2;
                    if (b != null) {
                        try {
                            b.close();
                        } catch (Exception e) {
                        }
                    }
                    throw th;
                }
            }
            C0567n c0567n = new C0567n(httpURLConnection, bArr);
            if (b != null) {
                try {
                    b.close();
                } catch (Exception e2) {
                }
            }
            return c0567n;
        } catch (Throwable th3) {
            th = th3;
            b = null;
            if (b != null) {
                b.close();
            }
            throw th;
        }
    }

    protected HttpURLConnection m952a(String str) {
        try {
            URL url = new URL(str);
            return this.f786a.m929a(str);
        } catch (Throwable e) {
            throw new IllegalArgumentException(str + " is not a valid URL", e);
        }
    }

    protected void m953a(C0562l c0562l, C0555b c0555b) {
        this.f787b.m968a(this, c0555b).m967a(c0562l);
    }

    public void m954a(String str, C0569p c0569p, C0555b c0555b) {
        m953a(new C0565k(str, c0569p), c0555b);
    }

    protected void m955a(HttpURLConnection httpURLConnection, C0564j c0564j, String str) {
        httpURLConnection.setConnectTimeout(this.f789d);
        httpURLConnection.setReadTimeout(this.f790e);
        this.f786a.m931a(httpURLConnection, c0564j, str);
    }

    public void m956a(Set<String> set) {
        this.f795l = set;
    }

    protected boolean m957a(Throwable th, long j) {
        long currentTimeMillis = (System.currentTimeMillis() - j) + 10;
        if (this.f788c.m973a()) {
            this.f788c.m971a("ELAPSED TIME = " + currentTimeMillis + ", CT = " + this.f789d + ", RT = " + this.f790e);
        }
        return this.f793j ? currentTimeMillis >= ((long) this.f790e) : currentTimeMillis >= ((long) this.f789d);
    }

    public C0567n m958b(C0562l c0562l) {
        C0567n c0567n = null;
        try {
            c0567n = m949a(c0562l.m982a(), c0562l.m983b(), c0562l.m984c(), c0562l.m985d());
        } catch (C0566m e) {
            this.f786a.m932a(e);
        } catch (Exception e2) {
            this.f786a.m932a(new C0566m(e2, c0567n));
        }
        return c0567n;
    }

    public C0567n m959b(String str, C0569p c0569p) {
        return m958b(new C0565k(str, c0569p));
    }

    protected C0567n m960b(HttpURLConnection httpURLConnection) {
        Throwable th;
        byte[] bArr = null;
        InputStream errorStream;
        try {
            errorStream = httpURLConnection.getErrorStream();
            if (errorStream != null) {
                try {
                    bArr = this.f786a.m933a(errorStream);
                } catch (Throwable th2) {
                    th = th2;
                    if (errorStream != null) {
                        try {
                            errorStream.close();
                        } catch (Exception e) {
                        }
                    }
                    throw th;
                }
            }
            C0567n c0567n = new C0567n(httpURLConnection, bArr);
            if (errorStream != null) {
                try {
                    errorStream.close();
                } catch (Exception e2) {
                }
            }
            return c0567n;
        } catch (Throwable th3) {
            th = th3;
            errorStream = null;
            if (errorStream != null) {
                errorStream.close();
            }
            throw th;
        }
    }

    public C0569p m961b() {
        return new C0569p();
    }

    public void m962b(int i) {
        if (i < 1 || i > 18) {
            throw new IllegalArgumentException("Maximum retries must be between 1 and 18");
        }
        this.f791h = i;
    }

    public void m963b(Set<String> set) {
        this.f794k = set;
    }

    public void m964c(int i) {
        this.f789d = i;
    }
}
