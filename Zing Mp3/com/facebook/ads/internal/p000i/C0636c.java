package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.ads.internal.p003j.C0749a.C0398a;
import com.facebook.ads.internal.util.C0765a;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0786j;
import com.facebook.ads.internal.util.ae;
import com.facebook.internal.ServerProtocol;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.i.c */
public class C0636c extends C0618b {
    private final C0387b f944a;
    private ae f945b;
    private C0749a f946c;

    /* renamed from: com.facebook.ads.internal.i.c.b */
    public interface C0387b {
        void m67a();

        void m68a(int i);

        void m69a(String str, Map<String, String> map);

        void m70b();
    }

    /* renamed from: com.facebook.ads.internal.i.c.1 */
    class C06251 extends C0398a {
        final /* synthetic */ C0387b f916a;
        final /* synthetic */ C0636c f917b;

        C06251(C0636c c0636c, C0387b c0387b) {
            this.f917b = c0636c;
            this.f916a = c0387b;
        }

        public void m1171a() {
            this.f917b.f945b.m1582a();
            this.f916a.m70b();
        }
    }

    /* renamed from: com.facebook.ads.internal.i.c.2 */
    class C06262 extends WebViewClient {
        final /* synthetic */ C0636c f918a;

        C06262(C0636c c0636c) {
            this.f918a = c0636c;
        }

        public void onReceivedSslError(WebView webView, @NonNull SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Map hashMap = new HashMap();
            this.f918a.f946c.m1492a(hashMap);
            hashMap.put(ServerProtocol.FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH, C0785i.m1620a(this.f918a.getTouchData()));
            this.f918a.f944a.m69a(str, hashMap);
            return true;
        }
    }

    /* renamed from: com.facebook.ads.internal.i.c.a */
    public class C0627a {
        final /* synthetic */ C0636c f919a;
        private final String f920b;

        public C0627a(C0636c c0636c) {
            this.f919a = c0636c;
            this.f920b = C0627a.class.getSimpleName();
        }

        @JavascriptInterface
        public void alert(String str) {
            Log.e(this.f920b, str);
        }

        @JavascriptInterface
        public String getAnalogInfo() {
            return C0785i.m1620a(C0765a.m1531a());
        }

        @JavascriptInterface
        public void onPageInitialized() {
            if (!this.f919a.m1152c()) {
                this.f919a.f944a.m67a();
                if (this.f919a.f946c != null) {
                    this.f919a.f946c.m1490a();
                }
            }
        }
    }

    public C0636c(Context context, C0387b c0387b, int i) {
        super(context);
        this.f944a = c0387b;
        getSettings().setSupportZoom(false);
        addJavascriptInterface(new C0627a(this), "AdControl");
        this.f945b = new ae();
        this.f946c = new C0749a(this, i, new C06251(this, c0387b));
    }

    public void m1197a(int i, int i2) {
        this.f946c.m1491a(i);
        this.f946c.m1494b(i2);
    }

    protected WebViewClient m1198b() {
        return new C06262(this);
    }

    public void destroy() {
        if (this.f946c != null) {
            this.f946c.m1493b();
            this.f946c = null;
        }
        C0786j.m1636a(this);
        super.destroy();
    }

    public Map<String, String> getTouchData() {
        return this.f945b.m1588e();
    }

    public C0749a getViewabilityChecker() {
        return this.f946c;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.f945b.m1583a(motionEvent, this, this);
        return super.onTouchEvent(motionEvent);
    }

    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (this.f944a != null) {
            this.f944a.m68a(i);
        }
        if (this.f946c == null) {
            return;
        }
        if (i == 0) {
            this.f946c.m1490a();
        } else if (i == 8) {
            this.f946c.m1493b();
        }
    }
}
