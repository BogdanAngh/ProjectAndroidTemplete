package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.internal.p000i.C0636c;
import com.facebook.ads.internal.p000i.C0636c.C0387b;
import com.facebook.ads.internal.p004a.C0429a;
import com.facebook.ads.internal.p004a.C0430b;
import com.facebook.ads.internal.p008e.C0516e;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0782g;
import com.facebook.ads.internal.util.C0782g.C0468a;
import com.facebook.ads.internal.util.C0786j;
import com.facebook.share.internal.ShareConstants;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.h */
public class C0452h extends BannerAdapter {
    private static final String f344a;
    private C0636c f345b;
    private C0476p f346c;
    private BannerAdapterListener f347d;
    private Map<String, Object> f348e;
    private Context f349f;
    private long f350g;
    private C0775a f351h;

    /* renamed from: com.facebook.ads.internal.adapters.h.1 */
    class C04501 implements C0387b {
        final /* synthetic */ C0474o f341a;
        final /* synthetic */ C0452h f342b;

        C04501(C0452h c0452h, C0474o c0474o) {
            this.f342b = c0452h;
            this.f341a = c0474o;
        }

        public void m433a() {
            this.f342b.f346c.m639b();
        }

        public void m434a(int i) {
            if (i == 0 && this.f342b.f350g > 0 && this.f342b.f351h != null) {
                C0778d.m1599a(C0777c.m1594a(this.f342b.f350g, this.f342b.f351h, this.f341a.m629f()));
                this.f342b.f350g = 0;
                this.f342b.f351h = null;
            }
        }

        public void m435a(String str, Map<String, String> map) {
            Uri parse = Uri.parse(str);
            if ("fbad".equals(parse.getScheme()) && C0430b.m322a(parse.getAuthority()) && this.f342b.f347d != null) {
                this.f342b.f347d.onBannerAdClicked(this.f342b);
            }
            C0429a a = C0430b.m321a(this.f342b.f349f, this.f341a.m619B(), parse, map);
            if (a != null) {
                try {
                    this.f342b.f351h = a.m318a();
                    this.f342b.f350g = System.currentTimeMillis();
                    a.m320b();
                } catch (Throwable e) {
                    Log.e(C0452h.f344a, "Error executing action", e);
                }
            }
        }

        public void m436b() {
            if (this.f342b.f346c != null) {
                this.f342b.f346c.m337a();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.h.2 */
    class C04512 extends C0400b {
        final /* synthetic */ C0452h f343a;

        C04512(C0452h c0452h) {
            this.f343a = c0452h;
        }

        public void m437d() {
            if (this.f343a.f347d != null) {
                this.f343a.f347d.onBannerLoggingImpression(this.f343a);
            }
        }
    }

    static {
        f344a = C0452h.class.getSimpleName();
    }

    private void m442a(C0516e c0516e) {
        this.f350g = 0;
        this.f351h = null;
        C0474o a = C0474o.m617a((JSONObject) this.f348e.get(ShareConstants.WEB_DIALOG_PARAM_DATA));
        if (C0782g.m1609a(this.f349f, (C0468a) a)) {
            this.f347d.onBannerError(this, AdError.NO_FILL);
            return;
        }
        this.f345b = new C0636c(this.f349f, new C04501(this, a), c0516e.m814e());
        this.f345b.m1197a(c0516e.m816g(), c0516e.m817h());
        this.f346c = new C0476p(this.f349f, this.f345b, this.f345b.getViewabilityChecker(), new C04512(this));
        this.f346c.m637a(a);
        this.f345b.loadDataWithBaseURL(C0786j.m1635a(), a.m623a(), AudienceNetworkActivity.WEBVIEW_MIME_TYPE, AudienceNetworkActivity.WEBVIEW_ENCODING, null);
        if (this.f347d != null) {
            this.f347d.onBannerAdLoaded(this, this.f345b);
        }
    }

    public void loadBannerAd(Context context, AdSize adSize, BannerAdapterListener bannerAdapterListener, Map<String, Object> map) {
        this.f349f = context;
        this.f347d = bannerAdapterListener;
        this.f348e = map;
        m442a((C0516e) map.get("definition"));
    }

    public void onDestroy() {
        if (this.f345b != null) {
            C0786j.m1636a(this.f345b);
            this.f345b.destroy();
            this.f345b = null;
        }
    }
}
