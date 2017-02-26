package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p019b.C0638m;
import com.facebook.ads.internal.p000i.p018e.p019b.C0660a;
import com.facebook.ads.internal.p000i.p018e.p019b.C0662b;
import com.facebook.ads.internal.p000i.p018e.p019b.C0665c;
import com.facebook.ads.internal.p000i.p018e.p019b.C0673e;
import com.facebook.ads.internal.p000i.p018e.p019b.C0685h;
import com.facebook.ads.internal.p000i.p018e.p019b.C0687i;
import com.facebook.ads.internal.p000i.p018e.p019b.C0692k;
import com.facebook.ads.internal.p000i.p018e.p020a.C0640a;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0643d;
import com.facebook.ads.internal.p000i.p018e.p020a.C0648i;
import com.facebook.ads.internal.p005f.C0453p;
import com.facebook.ads.internal.util.C0774b;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.internal.util.ab;
import com.facebook.ads.internal.util.ad;
import com.facebook.ads.internal.util.ag;
import com.facebook.ads.p001a.C0390a;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.mp3download.zingmp3.BuildConfig;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.i */
public class C0459i extends C0458r implements ad<Bundle> {
    static final /* synthetic */ boolean f356d;
    @Nullable
    protected C0726m f357a;
    @Nullable
    protected JSONObject f358b;
    @Nullable
    protected Context f359c;
    @NonNull
    private final C0453p<C0641b> f360e;
    @NonNull
    private final C0453p<C0648i> f361f;
    @NonNull
    private final C0453p<C0643d> f362g;
    @NonNull
    private final C0453p<C0640a> f363h;
    @Nullable
    private C0390a f364i;
    @Nullable
    private C0783h f365j;
    @Nullable
    private String f366k;
    private boolean f367l;
    @Nullable
    private String f368m;
    @Nullable
    private String f369n;
    @Nullable
    private String f370o;
    @Nullable
    private ag f371p;

    /* renamed from: com.facebook.ads.internal.adapters.i.1 */
    class C04541 extends C0453p<C0641b> {
        final /* synthetic */ C0459i f352a;

        C04541(C0459i c0459i) {
            this.f352a = c0459i;
        }

        public Class<C0641b> m450a() {
            return C0641b.class;
        }

        public void m452a(C0641b c0641b) {
            this.f352a.f364i.m104d(this.f352a);
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.i.2 */
    class C04552 extends C0453p<C0648i> {
        final /* synthetic */ C0459i f353a;

        C04552(C0459i c0459i) {
            this.f353a = c0459i;
        }

        public Class<C0648i> m453a() {
            return C0648i.class;
        }

        public void m455a(C0648i c0648i) {
            this.f353a.f367l = true;
            this.f353a.f364i.m99a(this.f353a);
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.i.3 */
    class C04563 extends C0453p<C0643d> {
        final /* synthetic */ C0459i f354a;

        C04563(C0459i c0459i) {
            this.f354a = c0459i;
        }

        public Class<C0643d> m456a() {
            return C0643d.class;
        }

        public void m458a(C0643d c0643d) {
            this.f354a.f364i.m101a(this.f354a, AdError.INTERNAL_ERROR);
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.i.4 */
    class C04574 extends C0453p<C0640a> {
        final /* synthetic */ C0459i f355a;

        C04574(C0459i c0459i) {
            this.f355a = c0459i;
        }

        public Class<C0640a> m459a() {
            return C0640a.class;
        }

        public void m461a(C0640a c0640a) {
            if (this.f355a.f364i != null) {
                this.f355a.f364i.m102b(this.f355a);
            }
            this.f355a.f365j.m1611a(C0774b.BILLABLE_CLICK.m1593a(this.f355a.f370o));
        }
    }

    static {
        f356d = !C0459i.class.desiredAssertionStatus();
    }

    public C0459i() {
        this.f360e = new C04541(this);
        this.f361f = new C04552(this);
        this.f362g = new C04563(this);
        this.f363h = new C04574(this);
        this.f367l = false;
    }

    private void m465a(@NonNull Context context, @NonNull C0390a c0390a, @NonNull JSONObject jSONObject, @NonNull C0783h c0783h, @Nullable Bundle bundle) {
        this.f359c = context;
        this.f364i = c0390a;
        this.f365j = c0783h;
        this.f358b = jSONObject;
        this.f367l = false;
        JSONObject jSONObject2 = jSONObject.getJSONObject(MimeTypes.BASE_TYPE_VIDEO);
        JSONObject jSONObject3 = jSONObject.getJSONObject("trackers");
        this.f366k = jSONObject2.getString(AudienceNetworkActivity.VIDEO_URL);
        this.f368m = jSONObject3.getString("nativeImpression");
        this.f369n = jSONObject3.getString("impression");
        this.f370o = jSONObject3.getString(Promotion.ACTION_CLICK);
        this.f357a = new C0726m(context);
        m469a();
        this.f357a.getEventBus().m915a(this.f360e);
        this.f357a.getEventBus().m915a(this.f362g);
        this.f357a.getEventBus().m915a(this.f361f);
        this.f357a.getEventBus().m915a(this.f363h);
        if (bundle != null) {
            this.f371p = new ab(context, c0783h, this.f357a, jSONObject3.getString(MimeTypes.BASE_TYPE_VIDEO), bundle.getBundle("logger"));
        } else {
            this.f371p = new ab(context, c0783h, this.f357a, jSONObject3.getString(MimeTypes.BASE_TYPE_VIDEO));
        }
        this.f364i.m100a((C0458r) this, this.f357a);
        this.f357a.setVideoURI(this.f366k);
    }

    protected void m469a() {
        if (!f356d && this.f359c == null) {
            throw new AssertionError();
        } else if (f356d || this.f358b != null) {
            C0638m c0673e;
            LayoutParams layoutParams;
            JSONObject jSONObject = this.f358b.getJSONObject(MimeTypes.BASE_TYPE_VIDEO);
            this.f357a.m1390a(new C0687i(this.f359c));
            this.f357a.m1390a(new C0692k(this.f359c));
            this.f357a.m1390a(new C0662b(this.f359c));
            String b = m472b();
            if (b != null) {
                C0638m c0665c = new C0665c(this.f359c, b, true);
                LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.addRule(12);
                layoutParams2.addRule(9);
                c0665c.setLayoutParams(layoutParams2);
                this.f357a.m1390a(c0665c);
            }
            if (jSONObject.has("destinationURL") && !jSONObject.isNull("destinationURL")) {
                Object string = jSONObject.getString("destinationURL");
                if (!TextUtils.isEmpty(string)) {
                    c0673e = new C0673e(this.f359c, string, BuildConfig.FLAVOR);
                    layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                    layoutParams.addRule(10);
                    layoutParams.addRule(11);
                    c0673e.setLayoutParams(layoutParams);
                    this.f357a.m1390a(c0673e);
                }
            }
            this.f357a.m1390a(new C0660a(this.f359c, "http://m.facebook.com/ads/ad_choices", BuildConfig.FLAVOR, new float[]{0.0f, 0.0f, 8.0f, 0.0f}));
            int c = m473c();
            if (c > 0) {
                c0673e = new C0685h(this.f359c, c);
                layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams.addRule(12);
                layoutParams.addRule(11);
                c0673e.setLayoutParams(layoutParams);
                c0673e.setPadding(0, 0, 0, 30);
                this.f357a.m1390a(c0673e);
            }
        } else {
            throw new AssertionError();
        }
    }

    public final void m470a(@NonNull Context context, @NonNull C0390a c0390a, @NonNull C0783h c0783h, @NonNull Bundle bundle) {
        try {
            m465a(context, c0390a, new JSONObject(bundle.getString("ad_response")), c0783h, bundle);
        } catch (JSONException e) {
            c0390a.m101a((C0458r) this, AdError.INTERNAL_ERROR);
        }
    }

    public final void m471a(@NonNull Context context, @NonNull C0390a c0390a, @NonNull Map<String, Object> map, @NonNull C0783h c0783h) {
        try {
            m465a(context, c0390a, (JSONObject) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA), c0783h, null);
        } catch (JSONException e) {
            c0390a.m101a((C0458r) this, AdError.INTERNAL_ERROR);
        }
    }

    protected String m472b() {
        String str = null;
        if (f356d || this.f358b != null) {
            try {
                JSONObject jSONObject = this.f358b.getJSONObject("capabilities");
                if (jSONObject.has("countdown") && !jSONObject.isNull("countdown")) {
                    jSONObject = jSONObject.getJSONObject("countdown");
                    if (jSONObject.has("format")) {
                        str = jSONObject.optString("format");
                    }
                }
            } catch (Throwable e) {
                Log.w(String.valueOf(C0459i.class), "Invalid JSON", e);
            }
            return str;
        }
        throw new AssertionError();
    }

    protected int m473c() {
        int i = -1;
        if (f356d || this.f358b != null) {
            try {
                JSONObject jSONObject = this.f358b.getJSONObject("capabilities");
                if (jSONObject.has("skipButton") && !jSONObject.isNull("skipButton")) {
                    jSONObject = jSONObject.getJSONObject("skipButton");
                    if (jSONObject.has("skippableSeconds")) {
                        i = jSONObject.getInt("skippableSeconds");
                    }
                }
            } catch (Throwable e) {
                Log.w(String.valueOf(C0459i.class), "Invalid JSON", e);
            }
            return i;
        }
        throw new AssertionError();
    }

    public boolean m474d() {
        if (!this.f367l || this.f357a == null) {
            return false;
        }
        if (this.f371p.m1575e() > 0) {
            this.f357a.m1388a(this.f371p.m1575e());
            this.f357a.m1395d();
        } else {
            this.f357a.m1395d();
            this.f365j.m1611a(this.f369n);
            Map hashMap = new HashMap();
            int c = m473c();
            if (c > 0) {
                hashMap.put("skippable_seconds", String.valueOf(c));
            }
            this.f365j.m1612a(this.f368m, hashMap);
            if (this.f364i != null) {
                this.f364i.m103c(this);
            }
        }
        return true;
    }

    public Bundle getSaveInstanceState() {
        if (this.f371p == null || this.f358b == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putBundle("logger", this.f371p.getSaveInstanceState());
        bundle.putString("ad_response", this.f358b.toString());
        return bundle;
    }

    public void onDestroy() {
    }
}
