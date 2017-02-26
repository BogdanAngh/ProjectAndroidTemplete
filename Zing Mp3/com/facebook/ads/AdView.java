package com.facebook.ads;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import com.facebook.ads.internal.C0376a;
import com.facebook.ads.internal.C0488b;
import com.facebook.ads.internal.C0497c;
import com.facebook.ads.internal.DisplayAdController;
import com.facebook.ads.internal.adapters.AdAdapter;
import com.facebook.ads.internal.p000i.C0636c;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.C0785i;

public class AdView extends RelativeLayout implements Ad {
    private static final C0497c f43a;
    private final DisplayMetrics f44b;
    private final AdSize f45c;
    private final String f46d;
    private DisplayAdController f47e;
    private AdListener f48f;
    private ImpressionListener f49g;
    private View f50h;
    private volatile boolean f51i;

    /* renamed from: com.facebook.ads.AdView.1 */
    class C03771 extends C0376a {
        final /* synthetic */ AdView f42a;

        C03771(AdView adView) {
            this.f42a = adView;
        }

        public void m37a() {
            if (this.f42a.f48f != null) {
                this.f42a.f48f.onAdClicked(this.f42a);
            }
        }

        public void m38a(View view) {
            if (view == null) {
                throw new IllegalStateException("Cannot present null view");
            }
            this.f42a.f50h = view;
            this.f42a.removeAllViews();
            this.f42a.addView(this.f42a.f50h);
            if (this.f42a.f50h instanceof C0636c) {
                C0785i.m1629a(this.f42a.f44b, this.f42a.f50h, this.f42a.f45c);
            }
            if (this.f42a.f48f != null) {
                this.f42a.f48f.onAdLoaded(this.f42a);
            }
        }

        public void m39a(AdAdapter adAdapter) {
            if (this.f42a.f47e != null) {
                this.f42a.f47e.m311c();
            }
        }

        public void m40a(C0488b c0488b) {
            if (this.f42a.f48f != null) {
                this.f42a.f48f.onError(this.f42a, c0488b.m729b());
            }
        }

        public void m41b() {
            if (this.f42a.f49g != null) {
                this.f42a.f49g.onLoggingImpression(this.f42a);
            }
            if ((this.f42a.f48f instanceof ImpressionListener) && this.f42a.f48f != this.f42a.f49g) {
                ((ImpressionListener) this.f42a.f48f).onLoggingImpression(this.f42a);
            }
        }
    }

    static {
        f43a = C0497c.ADS;
    }

    public AdView(Context context, String str, AdSize adSize) {
        super(context);
        if (adSize == null || adSize == AdSize.INTERSTITIAL) {
            throw new IllegalArgumentException("adSize");
        }
        this.f44b = getContext().getResources().getDisplayMetrics();
        this.f45c = adSize;
        this.f46d = str;
        this.f47e = new DisplayAdController(context, str, C0785i.m1614a(adSize), AdPlacementType.BANNER, adSize, f43a, 1, false);
        this.f47e.m307a(new C03771(this));
    }

    public void destroy() {
        if (this.f47e != null) {
            this.f47e.m312d();
            this.f47e = null;
        }
        removeAllViews();
        this.f50h = null;
    }

    public void disableAutoRefresh() {
        if (this.f47e != null) {
            this.f47e.m316h();
        }
    }

    public String getPlacementId() {
        return this.f46d;
    }

    public void loadAd() {
        if (!this.f51i) {
            this.f47e.m310b();
            this.f51i = true;
        } else if (this.f47e != null) {
            this.f47e.m315g();
        }
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.f50h != null) {
            C0785i.m1629a(this.f44b, this.f50h, this.f45c);
        }
    }

    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (this.f47e != null) {
            if (i == 0) {
                this.f47e.m314f();
            } else if (i == 8) {
                this.f47e.m313e();
            }
        }
    }

    public void setAdListener(AdListener adListener) {
        this.f48f = adListener;
    }

    @Deprecated
    public void setImpressionListener(ImpressionListener impressionListener) {
        this.f49g = impressionListener;
    }
}
