package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.ads.internal.util.C0786j;

/* renamed from: com.facebook.ads.internal.i.b */
public abstract class C0618b extends WebView {
    private static final String f897a;
    private boolean f898b;

    static {
        f897a = C0618b.class.getSimpleName();
    }

    public C0618b(Context context) {
        super(context);
        m1149d();
    }

    private void m1149d() {
        setWebChromeClient(m1150a());
        setWebViewClient(m1151b());
        C0786j.m1637b(this);
        getSettings().setJavaScriptEnabled(true);
        if (VERSION.SDK_INT >= 17) {
            getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        setHorizontalScrollBarEnabled(false);
        setHorizontalScrollbarOverlay(false);
        setVerticalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(false);
        if (VERSION.SDK_INT >= 21) {
            try {
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
            } catch (Exception e) {
                Log.w(f897a, "Failed to initialize CookieManager.");
            }
        }
    }

    protected WebChromeClient m1150a() {
        return new WebChromeClient();
    }

    protected WebViewClient m1151b() {
        return new WebViewClient();
    }

    public boolean m1152c() {
        return this.f898b;
    }

    public void destroy() {
        this.f898b = true;
        super.destroy();
    }
}
