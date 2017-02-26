package com.facebook.ads.internal.p010h.p012b;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.Utility;
import com.google.android.exoplayer.chunk.FormatEvaluator.AdaptiveEvaluator;
import com.google.android.exoplayer.text.Cue;
import com.mp3download.zingmp3.BuildConfig;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: com.facebook.ads.internal.h.b.h */
public class C0596h implements C0595n {
    public final String f866a;
    private HttpURLConnection f867b;
    private InputStream f868c;
    private volatile int f869d;
    private volatile String f870e;

    public C0596h(C0596h c0596h) {
        this.f869d = Cue.TYPE_UNSET;
        this.f866a = c0596h.f866a;
        this.f870e = c0596h.f870e;
        this.f869d = c0596h.f869d;
    }

    public C0596h(String str) {
        this(str, C0602m.m1110a(str));
    }

    public C0596h(String str, String str2) {
        this.f869d = Cue.TYPE_UNSET;
        this.f866a = (String) C0599j.m1107a(str);
        this.f870e = str2;
    }

    private int m1099a(HttpURLConnection httpURLConnection, int i, int i2) {
        int contentLength = httpURLConnection.getContentLength();
        return i2 == Callback.DEFAULT_DRAG_ANIMATION_DURATION ? contentLength : i2 == 206 ? contentLength + i : this.f869d;
    }

    private HttpURLConnection m1100a(int i, int i2) {
        HttpURLConnection httpURLConnection;
        String str = this.f866a;
        int i3 = 0;
        Object obj;
        do {
            Log.d("ProxyCache", "Open connection " + (i > 0 ? " with offset " + i : BuildConfig.FLAVOR) + " to " + str);
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            if (i > 0) {
                httpURLConnection.setRequestProperty("Range", "bytes=" + i + "-");
            }
            if (i2 > 0) {
                httpURLConnection.setConnectTimeout(i2);
                httpURLConnection.setReadTimeout(i2);
            }
            int responseCode = httpURLConnection.getResponseCode();
            obj = (responseCode == 301 || responseCode == 302 || responseCode == 303) ? 1 : null;
            if (obj != null) {
                str = httpURLConnection.getHeaderField("Location");
                i3++;
                httpURLConnection.disconnect();
            }
            if (i3 > 5) {
                throw new C0597l("Too many redirects: " + i3);
            }
        } while (obj != null);
        return httpURLConnection;
    }

    private void m1101d() {
        HttpURLConnection a;
        Throwable e;
        Closeable closeable = null;
        Log.d("ProxyCache", "Read content info from " + this.f866a);
        try {
            a = m1100a(0, AdaptiveEvaluator.DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS);
            try {
                this.f869d = a.getContentLength();
                this.f870e = a.getContentType();
                closeable = a.getInputStream();
                Log.i("ProxyCache", "Content info for `" + this.f866a + "`: mime: " + this.f870e + ", content-length: " + this.f869d);
                C0602m.m1112a(closeable);
                if (a != null) {
                    a.disconnect();
                }
            } catch (IOException e2) {
                e = e2;
                try {
                    Log.e("ProxyCache", "Error fetching info from " + this.f866a, e);
                    C0602m.m1112a(closeable);
                    if (a != null) {
                        a.disconnect();
                    }
                } catch (Throwable th) {
                    e = th;
                    C0602m.m1112a(closeable);
                    if (a != null) {
                        a.disconnect();
                    }
                    throw e;
                }
            }
        } catch (IOException e3) {
            e = e3;
            a = null;
            Log.e("ProxyCache", "Error fetching info from " + this.f866a, e);
            C0602m.m1112a(closeable);
            if (a != null) {
                a.disconnect();
            }
        } catch (Throwable th2) {
            e = th2;
            a = null;
            C0602m.m1112a(closeable);
            if (a != null) {
                a.disconnect();
            }
            throw e;
        }
    }

    public synchronized int m1102a() {
        if (this.f869d == Cue.TYPE_UNSET) {
            m1101d();
        }
        return this.f869d;
    }

    public int m1103a(byte[] bArr) {
        if (this.f868c == null) {
            throw new C0597l("Error reading data from " + this.f866a + ": connection is absent!");
        }
        try {
            return this.f868c.read(bArr, 0, bArr.length);
        } catch (Throwable e) {
            throw new C0598i("Reading source " + this.f866a + " is interrupted", e);
        } catch (Throwable e2) {
            throw new C0597l("Error reading data from " + this.f866a, e2);
        }
    }

    public void m1104a(int i) {
        try {
            this.f867b = m1100a(i, -1);
            this.f870e = this.f867b.getContentType();
            this.f868c = new BufferedInputStream(this.f867b.getInputStream(), Utility.DEFAULT_STREAM_BUFFER_SIZE);
            this.f869d = m1099a(this.f867b, i, this.f867b.getResponseCode());
        } catch (Throwable e) {
            throw new C0597l("Error opening connection for " + this.f866a + " with offset " + i, e);
        }
    }

    public void m1105b() {
        if (this.f867b != null) {
            try {
                this.f867b.disconnect();
            } catch (Throwable e) {
                throw new C0597l("Error disconnecting HttpUrlConnection", e);
            }
        }
    }

    public synchronized String m1106c() {
        if (TextUtils.isEmpty(this.f870e)) {
            m1101d();
        }
        return this.f870e;
    }

    public String toString() {
        return "HttpUrlSource{url='" + this.f866a + "}";
    }
}
