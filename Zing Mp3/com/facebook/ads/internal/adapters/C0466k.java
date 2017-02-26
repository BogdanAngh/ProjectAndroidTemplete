package com.facebook.ads.internal.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.AudienceNetworkActivity.BackButtonInterceptor;
import com.facebook.ads.internal.adapters.C0462j.C0461a;
import com.facebook.ads.internal.p000i.C0465d;
import com.facebook.ads.internal.p000i.C0465d.C0379a;
import com.facebook.ads.internal.p000i.p017d.C0639a;
import com.facebook.ads.internal.p000i.p018e.p019b.C0638m;
import com.facebook.ads.internal.p000i.p018e.p019b.C0660a;
import com.facebook.ads.internal.p000i.p018e.p019b.C0665c;
import com.facebook.ads.internal.p000i.p018e.p019b.C0685h;
import com.facebook.ads.internal.p000i.p018e.p019b.C0687i;
import com.facebook.ads.internal.p000i.p018e.p019b.C0688j;
import com.facebook.ads.internal.p000i.p018e.p019b.C0692k;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p005f.C0453p;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.util.MimeTypes;
import com.mp3download.zingmp3.BuildConfig;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.k */
public class C0466k extends C0459i implements C0465d {
    static final /* synthetic */ boolean f388e;
    @NonNull
    private final C0453p<C0641b> f389f;
    @Nullable
    private C0379a f390g;
    @Nullable
    private Activity f391h;
    @Nullable
    private C0685h f392i;
    private BackButtonInterceptor f393j;
    private C0461a f394k;
    private C0639a f395l;
    private C0688j f396m;
    private C0688j f397n;
    @Nullable
    private C0665c f398o;

    /* renamed from: com.facebook.ads.internal.adapters.k.1 */
    class C04631 extends C0453p<C0641b> {
        final /* synthetic */ C0466k f386a;

        C04631(C0466k c0466k) {
            this.f386a = c0466k;
        }

        public Class<C0641b> m489a() {
            return C0641b.class;
        }

        public void m491a(C0641b c0641b) {
            if (this.f386a.f391h != null) {
                this.f386a.f391h.finish();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.k.2 */
    class C04642 implements BackButtonInterceptor {
        final /* synthetic */ C0466k f387a;

        C04642(C0466k c0466k) {
            this.f387a = c0466k;
        }

        public boolean interceptBackButton() {
            return (this.f387a.f392i == null || this.f387a.f392i.m1273a()) ? false : true;
        }
    }

    static {
        f388e = !C0466k.class.desiredAssertionStatus();
    }

    public C0466k() {
        this.f389f = new C04631(this);
        this.f393j = new C04642(this);
        this.f394k = C0461a.UNSPECIFIED;
    }

    private void m499a(int i) {
        float f = this.c.getResources().getDisplayMetrics().density;
        int id = this.a.getVideoView().getId();
        if (i == 1) {
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.topMargin = (int) (-54.0f * f);
            layoutParams.addRule(3, id);
            layoutParams.addRule(11);
            this.f392i.setLayoutParams(layoutParams);
            this.f392i.setPadding(0, 0, 0, 48);
            if (this.f395l != null) {
                layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams.topMargin = (int) (82.0f * f);
                layoutParams.addRule(14);
                layoutParams.addRule(3, id);
                this.f395l.setLayoutParams(layoutParams);
            }
            if (this.f396m != null) {
                layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams.bottomMargin = (int) (12.0f * f);
                layoutParams.addRule(14);
                layoutParams.addRule(2, id);
                this.f396m.setPadding(0, 0, 0, 0);
                this.f396m.setLayoutParams(layoutParams);
            }
            if (this.f397n != null) {
                layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams.topMargin = (int) (32.0f * f);
                layoutParams.addRule(14);
                layoutParams.addRule(3, id);
                this.f397n.setPadding(0, 0, 0, 0);
                this.f397n.setLayoutParams(layoutParams);
            }
            layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.topMargin = (int) (8.0f * f);
            layoutParams.leftMargin = (int) (f * 8.0f);
            layoutParams.addRule(9);
            layoutParams.addRule(3, id);
            if (this.f398o != null) {
                this.f398o.setLayoutParams(layoutParams);
                if (this.f398o.getParent() == null) {
                    this.a.addView(this.f398o);
                    return;
                }
                return;
            }
            return;
        }
        LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        this.f392i.setLayoutParams(layoutParams2);
        this.f392i.setPadding(0, 0, 0, 48);
        if (this.f395l != null) {
            layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(10);
            layoutParams2.addRule(11);
            layoutParams2.topMargin = (int) (12.0f * f);
            layoutParams2.rightMargin = (int) (12.0f * f);
            this.f395l.setLayoutParams(layoutParams2);
        }
        if (this.f396m != null) {
            layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.topMargin = (int) (12.0f * f);
            layoutParams2.addRule(10);
            layoutParams2.addRule(9);
            this.f396m.setPadding(32, 12, 16, 12);
            this.f396m.setLayoutParams(layoutParams2);
        }
        if (this.f397n != null) {
            layoutParams2 = new RelativeLayout.LayoutParams((int) (f * 400.0f), -2);
            layoutParams2.addRule(12);
            layoutParams2.addRule(9);
            this.f397n.setPadding(32, 24, 32, 24);
            this.f397n.setLayoutParams(layoutParams2);
        }
        if (this.f398o != null) {
            this.a.removeViewInLayout(this.f398o);
        }
    }

    @TargetApi(17)
    protected void m501a() {
        JSONObject jSONObject;
        this.a.getEventBus().m915a(this.f389f);
        this.a.m1390a(new C0687i(this.c));
        this.a.m1390a(new C0692k(this.c));
        String optString = this.b.getJSONObject("context").optString("orientation");
        if (!optString.isEmpty()) {
            this.f394k = C0461a.m481a(Integer.parseInt(optString));
            this.f394k = C0461a.UNSPECIFIED;
        }
        this.a.getVideoView().setId(View.generateViewId());
        int c = m473c();
        if (c >= 0) {
            this.f392i = new C0685h(this.c, c);
            this.a.m1390a(this.f392i);
        }
        if (this.b.has("cta") && !this.b.isNull("cta")) {
            jSONObject = this.b.getJSONObject("cta");
            this.f395l = new C0639a(this.c, jSONObject.getString(NativeProtocol.WEB_DIALOG_URL), jSONObject.getString(MimeTypes.BASE_TYPE_TEXT));
            this.a.m1390a(this.f395l);
        }
        jSONObject = this.b.getJSONObject(MimeTypes.BASE_TYPE_TEXT);
        String optString2 = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_TITLE);
        if (!optString2.isEmpty()) {
            this.f396m = new C0688j(this.c, optString2, 18);
            this.a.m1390a(this.f396m);
        }
        optString = jSONObject.optString("subtitle");
        if (!optString.isEmpty()) {
            this.f397n = new C0688j(this.c, optString, 16);
            this.a.m1390a(this.f397n);
        }
        optString = m472b();
        if (optString != null) {
            this.f398o = new C0665c(this.c, optString);
            this.a.m1390a(this.f398o);
        }
        C0638m c0660a = new C0660a(this.c, "http://m.facebook.com/ads/ad_choices", BuildConfig.FLAVOR, new float[]{0.0f, 8.0f, 0.0f, 0.0f});
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12);
        layoutParams.addRule(9);
        c0660a.setLayoutParams(layoutParams);
        this.a.m1390a(c0660a);
        this.a.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    }

    @TargetApi(17)
    public void m502a(Intent intent, Bundle bundle, AudienceNetworkActivity audienceNetworkActivity) {
        this.f391h = audienceNetworkActivity;
        if (!f388e && this.f390g == null) {
            throw new AssertionError();
        } else if (f388e || this.f392i != null) {
            audienceNetworkActivity.addBackButtonInterceptor(this.f393j);
            this.f390g.m49a(this.a);
            m499a(this.f391h.getResources().getConfiguration().orientation);
            m474d();
        } else {
            throw new AssertionError();
        }
    }

    public void m503a(Configuration configuration) {
        m499a(configuration.orientation);
    }

    public void m504a(Bundle bundle) {
    }

    public void m505a(C0379a c0379a) {
        this.f390g = c0379a;
    }

    public void m506e() {
    }

    public void m507f() {
    }

    public void m508g() {
    }

    public C0461a m509h() {
        return this.f394k;
    }
}
