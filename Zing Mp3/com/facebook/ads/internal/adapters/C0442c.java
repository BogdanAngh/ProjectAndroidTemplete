package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.facebook.ads.AdError;
import com.facebook.ads.AdNetwork;
import com.facebook.ads.NativeAd.Rating;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0804w;
import com.facebook.ads.internal.util.ah;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.mp3download.zingmp3.C1569R;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.c */
public class C0442c extends C0440v implements C0441t {
    private static final String f299a;
    private View f300b;
    private NativeAd f301c;
    private C0415w f302d;
    private NativeAdView f303e;
    private View f304f;
    private boolean f305g;
    private Uri f306h;
    private Uri f307i;
    private String f308j;
    private String f309k;
    private String f310l;
    private String f311m;

    /* renamed from: com.facebook.ads.internal.adapters.c.1 */
    class C04361 implements OnAppInstallAdLoadedListener {
        final /* synthetic */ Context f292a;
        final /* synthetic */ C0442c f293b;

        C04361(C0442c c0442c, Context context) {
            this.f293b = c0442c;
            this.f292a = context;
        }

        public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
            Uri uri = null;
            this.f293b.f301c = nativeAppInstallAd;
            this.f293b.f305g = true;
            this.f293b.f308j = nativeAppInstallAd.getHeadline() != null ? nativeAppInstallAd.getHeadline().toString() : null;
            this.f293b.f309k = nativeAppInstallAd.getBody() != null ? nativeAppInstallAd.getBody().toString() : null;
            this.f293b.f311m = nativeAppInstallAd.getStore() != null ? nativeAppInstallAd.getStore().toString() : null;
            this.f293b.f310l = nativeAppInstallAd.getCallToAction() != null ? nativeAppInstallAd.getCallToAction().toString() : null;
            List images = nativeAppInstallAd.getImages();
            C0442c c0442c = this.f293b;
            Uri uri2 = (images == null || images.size() <= 0) ? null : ((Image) images.get(0)).getUri();
            c0442c.f306h = uri2;
            C0442c c0442c2 = this.f293b;
            if (nativeAppInstallAd.getIcon() != null) {
                uri = nativeAppInstallAd.getIcon().getUri();
            }
            c0442c2.f307i = uri;
            if (this.f293b.f302d != null) {
                C0785i.m1628a(this.f292a, C0804w.m1678a(this.f293b.m390D()) + " Loaded");
                this.f293b.f302d.m233a(this.f293b);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.c.2 */
    class C04372 implements OnContentAdLoadedListener {
        final /* synthetic */ Context f294a;
        final /* synthetic */ C0442c f295b;

        C04372(C0442c c0442c, Context context) {
            this.f295b = c0442c;
            this.f294a = context;
        }

        public void onContentAdLoaded(NativeContentAd nativeContentAd) {
            Uri uri = null;
            this.f295b.f301c = nativeContentAd;
            this.f295b.f305g = true;
            this.f295b.f308j = nativeContentAd.getHeadline() != null ? nativeContentAd.getHeadline().toString() : null;
            this.f295b.f309k = nativeContentAd.getBody() != null ? nativeContentAd.getBody().toString() : null;
            this.f295b.f311m = nativeContentAd.getAdvertiser() != null ? nativeContentAd.getAdvertiser().toString() : null;
            this.f295b.f310l = nativeContentAd.getCallToAction() != null ? nativeContentAd.getCallToAction().toString() : null;
            List images = nativeContentAd.getImages();
            C0442c c0442c = this.f295b;
            Uri uri2 = (images == null || images.size() <= 0) ? null : ((Image) images.get(0)).getUri();
            c0442c.f306h = uri2;
            C0442c c0442c2 = this.f295b;
            if (nativeContentAd.getLogo() != null) {
                uri = nativeContentAd.getLogo().getUri();
            }
            c0442c2.f307i = uri;
            if (this.f295b.f302d != null) {
                C0785i.m1628a(this.f294a, C0804w.m1678a(this.f295b.m390D()) + " Loaded");
                this.f295b.f302d.m233a(this.f295b);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.c.3 */
    class C04383 extends AdListener {
        final /* synthetic */ Context f296a;
        final /* synthetic */ C0442c f297b;

        C04383(C0442c c0442c, Context context) {
            this.f297b = c0442c;
            this.f296a = context;
        }

        public void onAdFailedToLoad(int i) {
            C0785i.m1628a(this.f296a, C0804w.m1678a(this.f297b.m390D()) + " Failed with error code: " + i);
            if (this.f297b.f302d != null) {
                this.f297b.f302d.m234a(this.f297b, new AdError(AdError.MEDIATION_ERROR_CODE, "AdMob error code: " + i));
            }
        }

        public void onAdOpened() {
            if (this.f297b.f302d != null) {
                this.f297b.f302d.m236c(this.f297b);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.c.4 */
    class C04394 implements OnClickListener {
        final /* synthetic */ C0442c f298a;

        C04394(C0442c c0442c) {
            this.f298a = c0442c;
        }

        public void onClick(View view) {
            this.f298a.f304f.performClick();
        }
    }

    static {
        f299a = C0442c.class.getSimpleName();
    }

    private void m380a(View view) {
        if (view != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }
    }

    public List<com.facebook.ads.NativeAd> m387A() {
        return null;
    }

    public String m388B() {
        return null;
    }

    public AdNetwork m389C() {
        return AdNetwork.ADMOB;
    }

    public C0445e m390D() {
        return C0445e.ADMOB;
    }

    public void m391a() {
        m380a(this.f304f);
        this.f304f = null;
        if (this.f300b != null) {
            View view = (ViewGroup) this.f300b.getParent();
            if ((view instanceof NativeContentAdView) || (view instanceof NativeAppInstallAdView)) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                if (viewGroup != null) {
                    int indexOfChild = viewGroup.indexOfChild(view);
                    m380a(this.f300b);
                    m380a(view);
                    viewGroup.addView(this.f300b, indexOfChild);
                }
            }
            this.f300b = null;
        }
        this.f303e = null;
    }

    public void m392a(int i) {
    }

    public void m393a(Context context, C0415w c0415w, Map<String, Object> map) {
        boolean z;
        boolean z2;
        C0785i.m1628a(context, C0804w.m1678a(m390D()) + " Loading");
        JSONObject jSONObject = (JSONObject) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA);
        String optString = jSONObject.optString("ad_unit_id");
        JSONArray optJSONArray = jSONObject.optJSONArray("creative_types");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            int i = 0;
            z = false;
            z2 = false;
            while (i < length) {
                try {
                    String string = optJSONArray.getString(i);
                    if (string != null) {
                        boolean z3 = true;
                        switch (string.hashCode()) {
                            case 704091517:
                                if (string.equals("app_install")) {
                                    z3 = false;
                                    break;
                                }
                                break;
                            case 883765328:
                                if (string.equals("page_post")) {
                                    z3 = true;
                                    break;
                                }
                                break;
                        }
                        switch (z3) {
                            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                                z2 = true;
                                break;
                            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                                z = true;
                                break;
                            default:
                                break;
                        }
                    }
                    i++;
                } catch (JSONException e) {
                    C0785i.m1628a(context, C0804w.m1678a(m390D()) + " AN server error");
                    c0415w.m234a(this, AdError.SERVER_ERROR);
                    return;
                }
            }
        }
        z = false;
        z2 = false;
        if (TextUtils.isEmpty(optString) || !(z2 || z)) {
            C0785i.m1628a(context, C0804w.m1678a(m390D()) + " AN server error");
            c0415w.m234a(this, AdError.SERVER_ERROR);
            return;
        }
        this.f302d = c0415w;
        Builder builder = new Builder(context, optString);
        if (z2) {
            builder.forAppInstallAd(new C04361(this, context));
        }
        if (z) {
            builder.forContentAd(new C04372(this, context));
        }
        builder.withAdListener(new C04383(this, context)).withNativeAdOptions(new NativeAdOptions.Builder().setReturnUrlsForImageAssets(true).build()).build().loadAd(new AdRequest.Builder().build());
    }

    public void m394a(View view, List<View> list) {
        this.f300b = view;
        if (m397b() && view != null) {
            ViewGroup viewGroup;
            int i;
            int i2 = -1;
            ViewGroup viewGroup2 = null;
            while (true) {
                ViewGroup viewGroup3 = (ViewGroup) view.getParent();
                if (viewGroup3 == null) {
                    Log.e(f299a, "View must have valid parent for AdMob registration, skipping registration. Impressions and clicks will not be logged.");
                    return;
                }
                if (viewGroup3 instanceof NativeAdView) {
                    viewGroup = (ViewGroup) viewGroup3.getParent();
                    if (viewGroup == null) {
                        Log.e(f299a, "View must have valid parent for AdMob registration, skipping registration. Impressions and clicks will not be logged.");
                        return;
                    }
                    int indexOfChild = viewGroup.indexOfChild(viewGroup3);
                    viewGroup3.removeView(view);
                    viewGroup.removeView(viewGroup3);
                    viewGroup.addView(view, indexOfChild);
                    i = i2;
                    viewGroup = viewGroup2;
                } else {
                    viewGroup = viewGroup3;
                    i = viewGroup3.indexOfChild(view);
                }
                if (viewGroup != null) {
                    break;
                }
                i2 = i;
                viewGroup2 = viewGroup;
            }
            View nativeContentAdView = this.f301c instanceof NativeContentAd ? new NativeContentAdView(view.getContext()) : new NativeAppInstallAdView(view.getContext());
            if (view instanceof ViewGroup) {
                nativeContentAdView.setLayoutParams(view.getLayoutParams());
            }
            m380a(view);
            nativeContentAdView.addView(view);
            viewGroup.removeView(nativeContentAdView);
            viewGroup.addView(nativeContentAdView, i);
            this.f303e = nativeContentAdView;
            this.f303e.setNativeAd(this.f301c);
            this.f304f = new View(view.getContext());
            this.f303e.addView(this.f304f);
            this.f304f.setVisibility(8);
            if (this.f303e instanceof NativeContentAdView) {
                ((NativeContentAdView) this.f303e).setCallToActionView(this.f304f);
            } else if (this.f303e instanceof NativeAppInstallAdView) {
                ((NativeAppInstallAdView) this.f303e).setCallToActionView(this.f304f);
            }
            OnClickListener c04394 = new C04394(this);
            for (View onClickListener : list) {
                onClickListener.setOnClickListener(c04394);
            }
        }
    }

    public void m395a(Map<String, String> map) {
        if (m397b() && this.f302d != null) {
            this.f302d.m235b(this);
        }
    }

    public void m396b(Map<String, String> map) {
    }

    public boolean m397b() {
        return this.f305g && this.f301c != null;
    }

    public boolean m398c() {
        return false;
    }

    public boolean m399d() {
        return false;
    }

    public boolean m400e() {
        return false;
    }

    public boolean m401f() {
        return false;
    }

    public boolean m402g() {
        return false;
    }

    public int m403h() {
        return 0;
    }

    public int m404i() {
        return 0;
    }

    public int m405j() {
        return 0;
    }

    public com.facebook.ads.NativeAd.Image m406k() {
        return (!m397b() || this.f307i == null) ? null : new com.facebook.ads.NativeAd.Image(this.f307i.toString(), 50, 50);
    }

    public com.facebook.ads.NativeAd.Image m407l() {
        return (!m397b() || this.f306h == null) ? null : new com.facebook.ads.NativeAd.Image(this.f306h.toString(), 1200, 600);
    }

    public NativeAdViewAttributes m408m() {
        return null;
    }

    public String m409n() {
        return this.f308j;
    }

    public String m410o() {
        return null;
    }

    public void onDestroy() {
        m391a();
        this.f302d = null;
        this.f301c = null;
        this.f305g = false;
        this.f306h = null;
        this.f307i = null;
        this.f308j = null;
        this.f309k = null;
        this.f310l = null;
        this.f311m = null;
    }

    public String m411p() {
        return this.f309k;
    }

    public String m412q() {
        return this.f310l;
    }

    public String m413r() {
        return this.f311m;
    }

    public Rating m414s() {
        return null;
    }

    public com.facebook.ads.NativeAd.Image m415t() {
        return null;
    }

    public String m416u() {
        return null;
    }

    public String m417v() {
        return null;
    }

    public String m418w() {
        return null;
    }

    public String m419x() {
        return null;
    }

    public ah m420y() {
        return ah.UNKNOWN;
    }

    public String m421z() {
        return null;
    }
}
