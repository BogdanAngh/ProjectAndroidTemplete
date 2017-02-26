package com.facebook.ads.internal.p010h.p011a;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.exoplayer.C0989C;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: com.facebook.ads.internal.h.a.f */
public abstract class C0552f implements C0551q {
    private final C0559r f782a;

    public C0552f() {
        this(new C0560g());
    }

    public C0552f(C0559r c0559r) {
        this.f782a = c0559r;
    }

    public OutputStream m935a(HttpURLConnection httpURLConnection) {
        return httpURLConnection.getOutputStream();
    }

    public HttpURLConnection m936a(String str) {
        return (HttpURLConnection) new URL(str).openConnection();
    }

    public void m937a(OutputStream outputStream, byte[] bArr) {
        outputStream.write(bArr);
    }

    public void m938a(HttpURLConnection httpURLConnection, C0564j c0564j, String str) {
        httpURLConnection.setRequestMethod(c0564j.m988c());
        httpURLConnection.setDoOutput(c0564j.m987b());
        httpURLConnection.setDoInput(c0564j.m986a());
        if (str != null) {
            httpURLConnection.setRequestProperty("Content-Type", str);
        }
        httpURLConnection.setRequestProperty("Accept-Charset", C0989C.UTF8_NAME);
    }

    public boolean m939a(C0566m c0566m) {
        C0567n a = c0566m.m989a();
        if (this.f782a.m973a()) {
            this.f782a.m971a("BasicRequestHandler.onError got");
            c0566m.printStackTrace();
        }
        return a != null && a.m990a() > 0;
    }

    public byte[] m940a(InputStream inputStream) {
        byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_COPY];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public InputStream m941b(HttpURLConnection httpURLConnection) {
        return httpURLConnection.getInputStream();
    }
}
