package com.facebook.ads.internal.p000i.p014a;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.ads.internal.p000i.C0618b;
import com.facebook.ads.internal.util.C0786j;
import com.facebook.ads.internal.util.C0792n;
import java.util.HashSet;
import java.util.Set;

@TargetApi(19)
/* renamed from: com.facebook.ads.internal.i.a.d */
public class C0619d extends C0618b {
    private static final Set<String> f899a;
    private C0617a f900b;
    private C0792n f901c;
    private long f902d;
    private long f903e;
    private long f904f;
    private long f905g;

    /* renamed from: com.facebook.ads.internal.i.a.d.1 */
    class C06151 extends WebChromeClient {
        final /* synthetic */ C0619d f895a;

        C06151(C0619d c0619d) {
            this.f895a = c0619d;
        }

        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String message = consoleMessage.message();
            if (!TextUtils.isEmpty(message) && consoleMessage.messageLevel() == MessageLevel.LOG) {
                this.f895a.f901c.m1654a(message);
            }
            return true;
        }

        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            this.f895a.f901c.m1653a();
            if (this.f895a.f900b != null) {
                this.f895a.f900b.m1145a(i);
            }
        }

        public void onReceivedTitle(WebView webView, String str) {
            super.onReceivedTitle(webView, str);
            if (this.f895a.f900b != null) {
                this.f895a.f900b.m1147b(str);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.a.d.2 */
    class C06162 extends WebViewClient {
        final /* synthetic */ C0619d f896a;

        C06162(C0619d c0619d) {
            this.f896a = c0619d;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (this.f896a.f900b != null) {
                this.f896a.f900b.m1148c(str);
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            if (this.f896a.f900b != null) {
                this.f896a.f900b.m1146a(str);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Uri parse = Uri.parse(str);
            if (C0619d.f899a.contains(parse.getScheme())) {
                return false;
            }
            this.f896a.getContext().startActivity(new Intent("android.intent.action.VIEW", parse));
            return true;
        }
    }

    /* renamed from: com.facebook.ads.internal.i.a.d.a */
    public interface C0617a {
        void m1145a(int i);

        void m1146a(String str);

        void m1147b(String str);

        void m1148c(String str);
    }

    static {
        f899a = new HashSet(2);
        f899a.add("http");
        f899a.add("https");
    }

    public C0619d(Context context) {
        super(context);
        this.f902d = -1;
        this.f903e = -1;
        this.f904f = -1;
        this.f905g = -1;
        m1157e();
    }

    public static boolean m1154a(String str) {
        return f899a.contains(str);
    }

    private void m1157e() {
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setDisplayZoomControls(false);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUseWideViewPort(true);
        this.f901c = new C0792n(this);
    }

    private void m1158f() {
        if (this.f903e > -1 && this.f904f > -1 && this.f905g > -1) {
            this.f901c.m1655a(false);
        }
    }

    protected WebChromeClient m1159a() {
        return new C06151(this);
    }

    public void m1160a(long j) {
        if (this.f902d < 0) {
            this.f902d = j;
        }
    }

    protected WebViewClient m1161b() {
        return new C06162(this);
    }

    public void m1162b(long j) {
        if (this.f903e < 0) {
            this.f903e = j;
        }
        m1158f();
    }

    public void m1163b(String str) {
        try {
            evaluateJavascript(str, null);
        } catch (IllegalStateException e) {
            loadUrl("javascript:" + str);
        }
    }

    public void m1164c(long j) {
        if (this.f905g < 0) {
            this.f905g = j;
        }
        m1158f();
    }

    public void destroy() {
        C0786j.m1636a(this);
        super.destroy();
    }

    public long getDomContentLoadedMs() {
        return this.f903e;
    }

    public String getFirstUrl() {
        WebBackForwardList copyBackForwardList = copyBackForwardList();
        return copyBackForwardList.getSize() > 0 ? copyBackForwardList.getItemAtIndex(0).getUrl() : getUrl();
    }

    public long getLoadFinishMs() {
        return this.f905g;
    }

    public long getResponseEndMs() {
        return this.f902d;
    }

    public long getScrollReadyMs() {
        return this.f904f;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f904f < 0 && computeVerticalScrollRange() > getHeight()) {
            this.f904f = System.currentTimeMillis();
            m1158f();
        }
    }

    public void setListener(C0617a c0617a) {
        this.f900b = c0617a;
    }
}
