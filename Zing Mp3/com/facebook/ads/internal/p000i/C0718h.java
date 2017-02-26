package com.facebook.ads.internal.p000i;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout.LayoutParams;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.internal.adapters.C0400b;
import com.facebook.ads.internal.adapters.C0474o;
import com.facebook.ads.internal.adapters.C0476p;
import com.facebook.ads.internal.p000i.C0465d.C0379a;
import com.facebook.ads.internal.p000i.C0636c.C0387b;
import com.facebook.ads.internal.p004a.C0429a;
import com.facebook.ads.internal.p004a.C0430b;
import com.facebook.ads.internal.p005f.C0537f;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0786j;
import com.facebook.internal.ServerProtocol;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.i.h */
public class C0718h implements C0465d {
    private static final String f1154a;
    private final C0379a f1155b;
    private final C0636c f1156c;
    private final C0476p f1157d;
    private C0474o f1158e;
    private long f1159f;
    private long f1160g;
    private C0775a f1161h;

    /* renamed from: com.facebook.ads.internal.i.h.1 */
    class C07161 implements C0387b {
        final /* synthetic */ AudienceNetworkActivity f1151a;
        final /* synthetic */ C0718h f1152b;

        C07161(C0718h c0718h, AudienceNetworkActivity audienceNetworkActivity) {
            this.f1152b = c0718h;
            this.f1151a = audienceNetworkActivity;
        }

        public void m1354a() {
            this.f1152b.f1157d.m639b();
        }

        public void m1355a(int i) {
        }

        public void m1356a(String str, Map<String, String> map) {
            Uri parse = Uri.parse(str);
            if ("fbad".equals(parse.getScheme()) && "close".equals(parse.getAuthority())) {
                this.f1151a.finish();
                return;
            }
            if ("fbad".equals(parse.getScheme()) && C0430b.m322a(parse.getAuthority())) {
                this.f1152b.f1155b.m50a("com.facebook.ads.interstitial.clicked");
            }
            C0429a a = C0430b.m321a(this.f1151a, this.f1152b.f1158e.m619B(), parse, map);
            if (a != null) {
                try {
                    this.f1152b.f1161h = a.m318a();
                    this.f1152b.f1160g = System.currentTimeMillis();
                    a.m320b();
                } catch (Throwable e) {
                    Log.e(C0718h.f1154a, "Error executing action", e);
                }
            }
        }

        public void m1357b() {
            this.f1152b.f1157d.m337a();
        }
    }

    /* renamed from: com.facebook.ads.internal.i.h.2 */
    class C07172 extends C0400b {
        final /* synthetic */ C0718h f1153a;

        C07172(C0718h c0718h) {
            this.f1153a = c0718h;
        }

        public void m1358d() {
            this.f1153a.f1155b.m50a("com.facebook.ads.interstitial.impression.logged");
        }
    }

    static {
        f1154a = C0718h.class.getSimpleName();
    }

    public C0718h(AudienceNetworkActivity audienceNetworkActivity, C0379a c0379a) {
        this.f1155b = c0379a;
        this.f1159f = System.currentTimeMillis();
        this.f1156c = new C0636c(audienceNetworkActivity, new C07161(this, audienceNetworkActivity), 1);
        this.f1156c.setLayoutParams(new LayoutParams(-1, -1));
        this.f1157d = new C0476p(audienceNetworkActivity, this.f1156c, this.f1156c.getViewabilityChecker(), new C07172(this));
        this.f1157d.m640c();
        c0379a.m49a(this.f1156c);
    }

    public void m1365a(Intent intent, Bundle bundle, AudienceNetworkActivity audienceNetworkActivity) {
        if (bundle == null || !bundle.containsKey("dataModel")) {
            this.f1158e = C0474o.m618b(intent);
            if (this.f1158e != null) {
                this.f1157d.m637a(this.f1158e);
                this.f1156c.loadDataWithBaseURL(C0786j.m1635a(), this.f1158e.m623a(), AudienceNetworkActivity.WEBVIEW_MIME_TYPE, AudienceNetworkActivity.WEBVIEW_ENCODING, null);
                this.f1156c.m1197a(this.f1158e.m630g(), this.f1158e.m631h());
                return;
            }
            return;
        }
        this.f1158e = C0474o.m616a(bundle.getBundle("dataModel"));
        if (this.f1158e != null) {
            this.f1156c.loadDataWithBaseURL(C0786j.m1635a(), this.f1158e.m623a(), AudienceNetworkActivity.WEBVIEW_MIME_TYPE, AudienceNetworkActivity.WEBVIEW_ENCODING, null);
            this.f1156c.m1197a(this.f1158e.m630g(), this.f1158e.m631h());
        }
    }

    public void m1366a(Bundle bundle) {
        if (this.f1158e != null) {
            bundle.putBundle("dataModel", this.f1158e.m632i());
        }
    }

    public void m1367a(C0379a c0379a) {
    }

    public void m1368e() {
        this.f1156c.onPause();
    }

    public void m1369f() {
        if (!(this.f1160g <= 0 || this.f1161h == null || this.f1158e == null)) {
            C0778d.m1599a(C0777c.m1594a(this.f1160g, this.f1161h, this.f1158e.m629f()));
        }
        this.f1156c.onResume();
    }

    public void m1370g() {
        if (this.f1158e != null) {
            C0778d.m1599a(C0777c.m1594a(this.f1159f, C0775a.XOUT, this.f1158e.m629f()));
            if (!TextUtils.isEmpty(this.f1158e.m619B())) {
                Map hashMap = new HashMap();
                this.f1156c.getViewabilityChecker().m1492a(hashMap);
                hashMap.put(ServerProtocol.FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH, C0785i.m1620a(this.f1156c.getTouchData()));
                C0537f.m878a(this.f1156c.getContext()).m892e(this.f1158e.m619B(), hashMap);
            }
        }
        C0786j.m1636a(this.f1156c);
        this.f1156c.destroy();
    }
}
