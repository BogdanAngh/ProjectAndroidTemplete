package com.facebook.ads.internal.p010h.p012b;

import android.text.TextUtils;
import com.facebook.ads.internal.p010h.p012b.p013a.C0572b;
import com.facebook.internal.Utility;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.DefaultLoadControl;
import com.mp3download.zingmp3.BuildConfig;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/* renamed from: com.facebook.ads.internal.h.b.e */
class C0585e extends C0584k {
    private final C0596h f837a;
    private final C0572b f838b;
    private C0581b f839c;

    public C0585e(C0596h c0596h, C0572b c0572b) {
        super(c0596h, c0572b);
        this.f838b = c0572b;
        this.f837a = c0596h;
    }

    private void m1055a(OutputStream outputStream, long j) {
        byte[] bArr = new byte[Utility.DEFAULT_STREAM_BUFFER_SIZE];
        while (true) {
            int a = m1050a(bArr, j, bArr.length);
            if (a != -1) {
                outputStream.write(bArr, 0, a);
                j += (long) a;
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    private boolean m1056a(C0583d c0583d) {
        int a = this.f837a.m1102a();
        boolean z = a > 0;
        int a2 = this.f838b.m1011a();
        if (z && c0583d.f828c) {
            if (((float) c0583d.f827b) > (((float) a) * DefaultLoadControl.DEFAULT_LOW_BUFFER_LOAD) + ((float) a2)) {
                return false;
            }
        }
        return true;
    }

    private String m1057b(C0583d c0583d) {
        int i = !TextUtils.isEmpty(this.f837a.m1106c()) ? 1 : 0;
        int a = this.f838b.m1016d() ? this.f838b.m1011a() : this.f837a.m1102a();
        int i2 = a >= 0 ? 1 : 0;
        long j = c0583d.f828c ? ((long) a) - c0583d.f827b : (long) a;
        int i3 = (i2 == 0 || !c0583d.f828c) ? 0 : 1;
        return (c0583d.f828c ? "HTTP/1.1 206 PARTIAL CONTENT\n" : "HTTP/1.1 200 OK\n") + "Accept-Ranges: bytes\n" + (i2 != 0 ? String.format("Content-Length: %d\n", new Object[]{Long.valueOf(j)}) : BuildConfig.FLAVOR) + (i3 != 0 ? String.format("Content-Range: bytes %d-%d/%d\n", new Object[]{Long.valueOf(c0583d.f827b), Integer.valueOf(a - 1), Integer.valueOf(a)}) : BuildConfig.FLAVOR) + (i != 0 ? String.format("Content-Type: %s\n", new Object[]{r9}) : BuildConfig.FLAVOR) + "\n";
    }

    private void m1058b(OutputStream outputStream, long j) {
        try {
            C0596h c0596h = new C0596h(this.f837a);
            c0596h.m1104a((int) j);
            byte[] bArr = new byte[Utility.DEFAULT_STREAM_BUFFER_SIZE];
            while (true) {
                int a = c0596h.m1103a(bArr);
                if (a == -1) {
                    break;
                }
                outputStream.write(bArr, 0, a);
                j += (long) a;
            }
            outputStream.flush();
        } finally {
            this.f837a.m1105b();
        }
    }

    protected void m1059a(int i) {
        if (this.f839c != null) {
            this.f839c.m1036a(this.f838b.f814a, this.f837a.f866a, i);
        }
    }

    public void m1060a(C0581b c0581b) {
        this.f839c = c0581b;
    }

    public void m1061a(C0583d c0583d, Socket socket) {
        OutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        bufferedOutputStream.write(m1057b(c0583d).getBytes(C0989C.UTF8_NAME));
        long j = c0583d.f827b;
        if (m1056a(c0583d)) {
            m1055a(bufferedOutputStream, j);
        } else {
            m1058b(bufferedOutputStream, j);
        }
    }
}
