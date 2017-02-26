package com.facebook.ads.internal.p000i;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.AudienceNetworkActivity.BackButtonInterceptor;
import com.facebook.ads.internal.p000i.C0465d.C0379a;
import com.facebook.ads.internal.p000i.p014a.C0612a;
import com.facebook.ads.internal.p000i.p014a.C0612a.C0611a;
import com.facebook.ads.internal.p000i.p014a.C0613b;
import com.facebook.ads.internal.p000i.p014a.C0619d;
import com.facebook.ads.internal.p000i.p014a.C0619d.C0617a;
import com.facebook.ads.internal.p005f.C0537f;
import com.facebook.ads.internal.util.C0786j;
import com.facebook.ads.internal.util.C0791m.C0790a;

@TargetApi(19)
/* renamed from: com.facebook.ads.internal.i.f */
public class C0714f implements C0465d {
    private static final String f1138a;
    private final AudienceNetworkActivity f1139b;
    private final C0612a f1140c;
    private final C0619d f1141d;
    private final C0613b f1142e;
    private final BackButtonInterceptor f1143f;
    private String f1144g;
    private String f1145h;
    private long f1146i;
    private boolean f1147j;
    private long f1148k;
    private boolean f1149l;

    /* renamed from: com.facebook.ads.internal.i.f.1 */
    class C07111 implements BackButtonInterceptor {
        final /* synthetic */ C0714f f1134a;

        C07111(C0714f c0714f) {
            this.f1134a = c0714f;
        }

        public boolean interceptBackButton() {
            if (!this.f1134a.f1141d.canGoBack()) {
                return false;
            }
            this.f1134a.f1141d.goBack();
            return true;
        }
    }

    /* renamed from: com.facebook.ads.internal.i.f.2 */
    class C07122 implements C0611a {
        final /* synthetic */ AudienceNetworkActivity f1135a;
        final /* synthetic */ C0714f f1136b;

        C07122(C0714f c0714f, AudienceNetworkActivity audienceNetworkActivity) {
            this.f1136b = c0714f;
            this.f1135a = audienceNetworkActivity;
        }

        public void m1338a() {
            this.f1135a.finish();
        }
    }

    /* renamed from: com.facebook.ads.internal.i.f.3 */
    class C07133 implements C0617a {
        final /* synthetic */ C0714f f1137a;

        C07133(C0714f c0714f) {
            this.f1137a = c0714f;
        }

        public void m1339a(int i) {
            if (this.f1137a.f1147j) {
                this.f1137a.f1142e.setProgress(i);
            }
        }

        public void m1340a(String str) {
            this.f1137a.f1147j = true;
            this.f1137a.f1140c.setUrl(str);
        }

        public void m1341b(String str) {
            this.f1137a.f1140c.setTitle(str);
        }

        public void m1342c(String str) {
            this.f1137a.f1142e.setProgress(100);
            this.f1137a.f1147j = false;
        }
    }

    static {
        f1138a = C0714f.class.getSimpleName();
    }

    public C0714f(AudienceNetworkActivity audienceNetworkActivity, C0379a c0379a) {
        this.f1143f = new C07111(this);
        this.f1147j = true;
        this.f1148k = -1;
        this.f1149l = true;
        this.f1139b = audienceNetworkActivity;
        int i = (int) (2.0f * audienceNetworkActivity.getResources().getDisplayMetrics().density);
        this.f1140c = new C0612a(audienceNetworkActivity);
        this.f1140c.setId(View.generateViewId());
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(10);
        this.f1140c.setLayoutParams(layoutParams);
        this.f1140c.setListener(new C07122(this, audienceNetworkActivity));
        c0379a.m49a(this.f1140c);
        this.f1141d = new C0619d(audienceNetworkActivity);
        layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(3, this.f1140c.getId());
        layoutParams.addRule(12);
        this.f1141d.setLayoutParams(layoutParams);
        this.f1141d.setListener(new C07133(this));
        c0379a.m49a(this.f1141d);
        this.f1142e = new C0613b(audienceNetworkActivity, null, 16842872);
        layoutParams = new RelativeLayout.LayoutParams(-1, i);
        layoutParams.addRule(3, this.f1140c.getId());
        this.f1142e.setLayoutParams(layoutParams);
        this.f1142e.setProgress(0);
        c0379a.m49a(this.f1142e);
        audienceNetworkActivity.addBackButtonInterceptor(this.f1143f);
    }

    public void m1348a(Intent intent, Bundle bundle, AudienceNetworkActivity audienceNetworkActivity) {
        if (this.f1148k < 0) {
            this.f1148k = System.currentTimeMillis();
        }
        if (bundle == null) {
            this.f1144g = intent.getStringExtra(AudienceNetworkActivity.BROWSER_URL);
            this.f1145h = intent.getStringExtra(AudienceNetworkActivity.CLIENT_TOKEN);
            this.f1146i = intent.getLongExtra(AudienceNetworkActivity.HANDLER_TIME, -1);
        } else {
            this.f1144g = bundle.getString(AudienceNetworkActivity.BROWSER_URL);
            this.f1145h = bundle.getString(AudienceNetworkActivity.CLIENT_TOKEN);
            this.f1146i = bundle.getLong(AudienceNetworkActivity.HANDLER_TIME, -1);
        }
        this.f1141d.loadUrl(this.f1144g != null ? this.f1144g : "about:blank");
    }

    public void m1349a(Bundle bundle) {
        bundle.putString(AudienceNetworkActivity.BROWSER_URL, this.f1144g);
    }

    public void m1350a(C0379a c0379a) {
    }

    public void m1351e() {
        this.f1141d.onPause();
        if (this.f1149l) {
            this.f1149l = false;
            C0537f.m878a(this.f1139b).m884a(this.f1145h, new C0790a(this.f1141d.getFirstUrl()).m1643a(this.f1146i).m1645b(this.f1148k).m1646c(this.f1141d.getResponseEndMs()).m1647d(this.f1141d.getDomContentLoadedMs()).m1648e(this.f1141d.getScrollReadyMs()).m1649f(this.f1141d.getLoadFinishMs()).m1650g(System.currentTimeMillis()).m1644a());
        }
    }

    public void m1352f() {
        this.f1141d.onResume();
    }

    public void m1353g() {
        this.f1139b.removeBackButtonInterceptor(this.f1143f);
        C0786j.m1636a(this.f1141d);
        this.f1141d.destroy();
    }
}
