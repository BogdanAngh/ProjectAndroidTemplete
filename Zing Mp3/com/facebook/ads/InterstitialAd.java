package com.facebook.ads;

import android.content.Context;
import android.view.View;
import com.facebook.ads.internal.C0376a;
import com.facebook.ads.internal.C0488b;
import com.facebook.ads.internal.C0497c;
import com.facebook.ads.internal.DisplayAdController;
import com.facebook.ads.internal.adapters.AdAdapter;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.C0785i;

public class InterstitialAd implements Ad {
    private static final C0497c f89a;
    private final Context f90b;
    private final String f91c;
    private DisplayAdController f92d;
    private boolean f93e;
    private boolean f94f;
    private InterstitialAdListener f95g;
    private ImpressionListener f96h;

    /* renamed from: com.facebook.ads.InterstitialAd.1 */
    class C03921 extends C0376a {
        final /* synthetic */ InterstitialAd f88a;

        C03921(InterstitialAd interstitialAd) {
            this.f88a = interstitialAd;
        }

        public void m117a() {
            if (this.f88a.f95g != null) {
                this.f88a.f95g.onAdClicked(this.f88a);
            }
        }

        public void m118a(View view) {
        }

        public void m119a(AdAdapter adAdapter) {
            this.f88a.f93e = true;
            if (this.f88a.f95g != null) {
                this.f88a.f95g.onAdLoaded(this.f88a);
            }
        }

        public void m120a(C0488b c0488b) {
            if (this.f88a.f95g != null) {
                this.f88a.f95g.onError(this.f88a, c0488b.m729b());
            }
        }

        public void m121b() {
            if (this.f88a.f96h != null) {
                this.f88a.f96h.onLoggingImpression(this.f88a);
            }
            if ((this.f88a.f95g instanceof ImpressionListener) && this.f88a.f95g != this.f88a.f96h) {
                ((ImpressionListener) this.f88a.f95g).onLoggingImpression(this.f88a);
            }
        }

        public void m122d() {
            if (this.f88a.f95g != null) {
                this.f88a.f95g.onInterstitialDisplayed(this.f88a);
            }
        }

        public void m123e() {
            this.f88a.f94f = false;
            if (this.f88a.f92d != null) {
                this.f88a.f92d.m312d();
                this.f88a.f92d = null;
            }
            if (this.f88a.f95g != null) {
                this.f88a.f95g.onInterstitialDismissed(this.f88a);
            }
        }
    }

    static {
        f89a = C0497c.ADS;
    }

    public InterstitialAd(Context context, String str) {
        this.f90b = context;
        this.f91c = str;
    }

    public void destroy() {
        if (this.f92d != null) {
            this.f92d.m312d();
            this.f92d = null;
        }
    }

    public String getPlacementId() {
        return this.f91c;
    }

    public boolean isAdLoaded() {
        return this.f93e;
    }

    public void loadAd() {
        this.f93e = false;
        if (this.f94f) {
            throw new IllegalStateException("InterstitialAd cannot be loaded while being displayed. Make sure your adapter calls adapterListener.onInterstitialDismissed().");
        }
        if (this.f92d != null) {
            this.f92d.m312d();
            this.f92d = null;
        }
        AdSize adSize = AdSize.INTERSTITIAL;
        this.f92d = new DisplayAdController(this.f90b, this.f91c, C0785i.m1614a(AdSize.INTERSTITIAL), AdPlacementType.INTERSTITIAL, adSize, f89a, 1, true);
        this.f92d.m307a(new C03921(this));
        this.f92d.m310b();
    }

    public void setAdListener(InterstitialAdListener interstitialAdListener) {
        this.f95g = interstitialAdListener;
    }

    @Deprecated
    public void setImpressionListener(ImpressionListener impressionListener) {
        this.f96h = impressionListener;
    }

    public boolean show() {
        if (this.f93e) {
            this.f92d.m311c();
            this.f94f = true;
            this.f93e = false;
            return true;
        } else if (this.f95g == null) {
            return false;
        } else {
            this.f95g.onError(this, AdError.INTERNAL_ERROR);
            return false;
        }
    }
}
