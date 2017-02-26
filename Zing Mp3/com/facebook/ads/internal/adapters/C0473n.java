package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.view.View;
import com.facebook.ads.AdError;
import com.facebook.ads.AdNetwork;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAd.Image;
import com.facebook.ads.NativeAd.Rating;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0804w;
import com.facebook.ads.internal.util.ah;
import com.facebook.share.internal.ShareConstants;
import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdErrorType;
import com.flurry.android.ads.FlurryAdNative;
import com.flurry.android.ads.FlurryAdNativeAsset;
import com.flurry.android.ads.FlurryAdNativeListener;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.n */
public class C0473n extends C0440v implements C0441t {
    private static volatile boolean f465a;
    private C0415w f466b;
    private FlurryAdNative f467c;
    private boolean f468d;
    private String f469e;
    private String f470f;
    private String f471g;
    private String f472h;
    private String f473i;
    private Image f474j;
    private Image f475k;
    private Image f476l;

    /* renamed from: com.facebook.ads.internal.adapters.n.1 */
    class C04721 implements FlurryAdNativeListener {
        final /* synthetic */ Context f463a;
        final /* synthetic */ C0473n f464b;

        C04721(C0473n c0473n, Context context) {
            this.f464b = c0473n;
            this.f463a = context;
        }

        public void onAppExit(FlurryAdNative flurryAdNative) {
        }

        public void onClicked(FlurryAdNative flurryAdNative) {
            if (this.f464b.f466b != null) {
                this.f464b.f466b.m236c(this.f464b);
            }
        }

        public void onCloseFullscreen(FlurryAdNative flurryAdNative) {
        }

        public void onCollapsed(FlurryAdNative flurryAdNative) {
        }

        public void onError(FlurryAdNative flurryAdNative, FlurryAdErrorType flurryAdErrorType, int i) {
            C0785i.m1628a(this.f463a, C0804w.m1678a(this.f464b.m584D()) + " Failed with FlurryError: " + flurryAdErrorType.toString());
            if (this.f464b.f466b != null) {
                this.f464b.f466b.m234a(this.f464b, new AdError(AdError.MEDIATION_ERROR_CODE, flurryAdErrorType.toString()));
            }
        }

        public void onExpanded(FlurryAdNative flurryAdNative) {
        }

        public void onFetched(FlurryAdNative flurryAdNative) {
            if (this.f464b.f466b != null) {
                if (flurryAdNative.isVideoAd()) {
                    C0785i.m1628a(this.f463a, C0804w.m1678a(this.f464b.m584D()) + " Failed. AN does not support Flurry video ads");
                    this.f464b.f466b.m234a(this.f464b, new AdError(AdError.MEDIATION_ERROR_CODE, "video ad"));
                    return;
                }
                this.f464b.f468d = true;
                FlurryAdNativeAsset asset = flurryAdNative.getAsset("headline");
                if (asset != null) {
                    this.f464b.f469e = asset.getValue();
                }
                asset = flurryAdNative.getAsset("summary");
                if (asset != null) {
                    this.f464b.f470f = asset.getValue();
                }
                asset = flurryAdNative.getAsset(ShareConstants.FEED_SOURCE_PARAM);
                if (asset != null) {
                    this.f464b.f471g = asset.getValue();
                }
                asset = flurryAdNative.getAsset("appCategory");
                if (asset != null) {
                    this.f464b.f473i = asset.getValue();
                }
                asset = flurryAdNative.getAsset("callToAction");
                if (asset != null) {
                    this.f464b.f472h = asset.getValue();
                } else if (flurryAdNative.getAsset("appRating") != null) {
                    this.f464b.f472h = "Install Now";
                } else {
                    this.f464b.f472h = "Learn More";
                }
                asset = flurryAdNative.getAsset("secImage");
                if (asset != null) {
                    this.f464b.f474j = new Image(asset.getValue(), 82, 82);
                }
                asset = flurryAdNative.getAsset("secHqImage");
                if (asset != null) {
                    this.f464b.f475k = new Image(asset.getValue(), 1200, 627);
                }
                asset = flurryAdNative.getAsset("secBrandingLogo");
                if (asset != null) {
                    this.f464b.f476l = new Image(asset.getValue(), 20, 20);
                }
                C0785i.m1628a(this.f463a, C0804w.m1678a(this.f464b.m584D()) + " Loaded");
                this.f464b.f466b.m233a(this.f464b);
            }
        }

        public void onImpressionLogged(FlurryAdNative flurryAdNative) {
            if (this.f464b.f466b != null) {
                this.f464b.f466b.m235b(this.f464b);
            }
        }

        public void onShowFullscreen(FlurryAdNative flurryAdNative) {
        }
    }

    public List<NativeAd> m581A() {
        return null;
    }

    public String m582B() {
        return null;
    }

    public AdNetwork m583C() {
        return AdNetwork.FLURRY;
    }

    public C0445e m584D() {
        return C0445e.YAHOO;
    }

    public void m585a() {
        if (this.f467c != null) {
            this.f467c.removeTrackingView();
        }
    }

    public void m586a(int i) {
    }

    public void m587a(Context context, C0415w c0415w, Map<String, Object> map) {
        JSONObject jSONObject = (JSONObject) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA);
        String optString = jSONObject.optString("api_key");
        String optString2 = jSONObject.optString("placement_id");
        synchronized (C0473n.class) {
            if (!f465a) {
                C0785i.m1628a(context, C0804w.m1678a(m584D()) + " Initializing");
                FlurryAgent.setLogEnabled(true);
                FlurryAgent.init(context, optString);
                f465a = true;
            }
        }
        C0785i.m1628a(context, C0804w.m1678a(m584D()) + " Loading");
        this.f466b = c0415w;
        this.f467c = new FlurryAdNative(context, optString2);
        this.f467c.setListener(new C04721(this, context));
        this.f467c.fetchAd();
    }

    public void m588a(View view, List<View> list) {
        if (this.f467c != null) {
            this.f467c.setTrackingView(view);
        }
    }

    public void m589a(Map<String, String> map) {
    }

    public void m590b(Map<String, String> map) {
    }

    public boolean m591b() {
        return this.f468d;
    }

    public boolean m592c() {
        return false;
    }

    public boolean m593d() {
        return false;
    }

    public boolean m594e() {
        return false;
    }

    public boolean m595f() {
        return false;
    }

    public boolean m596g() {
        return true;
    }

    public int m597h() {
        return 0;
    }

    public int m598i() {
        return 0;
    }

    public int m599j() {
        return 0;
    }

    public Image m600k() {
        return this.f474j;
    }

    public Image m601l() {
        return this.f475k;
    }

    public NativeAdViewAttributes m602m() {
        return null;
    }

    public String m603n() {
        return this.f469e;
    }

    public String m604o() {
        return this.f471g;
    }

    public void onDestroy() {
        m585a();
        this.f466b = null;
        if (this.f467c != null) {
            this.f467c.destroy();
            this.f467c = null;
        }
    }

    public String m605p() {
        return this.f470f;
    }

    public String m606q() {
        return this.f472h;
    }

    public String m607r() {
        return this.f473i;
    }

    public Rating m608s() {
        return null;
    }

    public Image m609t() {
        return this.f476l;
    }

    public String m610u() {
        return null;
    }

    public String m611v() {
        return "Ad";
    }

    public String m612w() {
        return null;
    }

    public String m613x() {
        return null;
    }

    public ah m614y() {
        return ah.UNKNOWN;
    }

    public String m615z() {
        return null;
    }
}
