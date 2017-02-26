package com.facebook.ads.internal.p004a;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.ads.internal.util.C0785i;

/* renamed from: com.facebook.ads.internal.a.f */
public class C0434f extends C0429a {
    private static final String f282a;
    private final Context f283b;
    private final String f284c;
    private final Uri f285d;

    static {
        f282a = C0434f.class.getSimpleName();
    }

    public C0434f(Context context, String str, Uri uri) {
        this.f283b = context;
        this.f284c = str;
        this.f285d = uri;
    }

    public C0775a m335a() {
        return C0775a.OPEN_LINK;
    }

    public void m336b() {
        try {
            Log.w("REDIRECTACTION: ", this.f285d.toString());
            C0785i.m1627a(this.f283b, this.f285d, this.f284c);
        } catch (Throwable e) {
            Log.d(f282a, "Failed to open link url: " + this.f285d.toString(), e);
        }
    }
}
