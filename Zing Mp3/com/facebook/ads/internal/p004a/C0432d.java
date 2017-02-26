package com.facebook.ads.internal.p004a;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.share.internal.ShareConstants;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.a.d */
public class C0432d extends C0429a {
    private static final String f272a;
    private final Context f273b;
    private final String f274c;
    private final Uri f275d;
    private final Map<String, String> f276e;

    static {
        f272a = C0432d.class.getSimpleName();
    }

    public C0432d(Context context, String str, Uri uri, Map<String, String> map) {
        this.f273b = context;
        this.f274c = str;
        this.f275d = uri;
        this.f276e = map;
    }

    public C0775a m331a() {
        return C0775a.OPEN_LINK;
    }

    public void m332b() {
        m319a(this.f273b, this.f274c, this.f275d, this.f276e);
        try {
            C0785i.m1627a(this.f273b, Uri.parse(this.f275d.getQueryParameter(ShareConstants.WEB_DIALOG_PARAM_LINK)), this.f274c);
        } catch (Throwable e) {
            Log.d(f272a, "Failed to open link url: " + this.f275d.toString(), e);
        }
    }
}
