package com.facebook.ads.internal.p004a;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.ads.internal.p005f.C0537f;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0807y;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.a.a */
public abstract class C0429a {
    public abstract C0775a m318a();

    protected void m319a(Context context, String str, Uri uri, Map<String, String> map) {
        if (TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(uri.getQueryParameter("native_click_report_url"))) {
                new C0807y(map).execute(new String[]{r0});
            } else {
                return;
            }
        }
        C0537f a = C0537f.m878a(context);
        if (this instanceof C0431c) {
            a.m890c(str, map);
        } else {
            a.m891d(str, map);
        }
        C0785i.m1628a(context, "Click logged");
    }

    public abstract void m320b();
}
