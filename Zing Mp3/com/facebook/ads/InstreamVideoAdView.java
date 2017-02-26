package com.facebook.ads;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.facebook.ads.internal.C0376a;
import com.facebook.ads.internal.C0488b;
import com.facebook.ads.internal.C0497c;
import com.facebook.ads.internal.C0523e;
import com.facebook.ads.internal.DisplayAdController;
import com.facebook.ads.internal.adapters.AdAdapter;
import com.facebook.ads.internal.adapters.C0458r;
import com.facebook.ads.internal.adapters.C0459i;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.internal.util.ad;
import com.facebook.ads.p001a.C0390a;

public class InstreamVideoAdView extends RelativeLayout implements Ad, ad<Bundle> {
    @NonNull
    private final String f80a;
    @NonNull
    private final AdSize f81b;
    @NonNull
    private DisplayAdController f82c;
    @Nullable
    private C0459i f83d;
    private boolean f84e;
    @Nullable
    private InstreamVideoAdListener f85f;
    @Nullable
    private View f86g;
    @Nullable
    private Bundle f87h;

    /* renamed from: com.facebook.ads.InstreamVideoAdView.1 */
    class C03891 extends C0376a {
        final /* synthetic */ InstreamVideoAdView f78a;

        C03891(InstreamVideoAdView instreamVideoAdView) {
            this.f78a = instreamVideoAdView;
        }

        public void m93a() {
            if (this.f78a.f85f != null) {
                this.f78a.f85f.onAdClicked(this.f78a);
            }
        }

        public void m94a(View view) {
            if (view == null) {
                throw new IllegalStateException("Cannot present null view");
            }
            this.f78a.f86g = view;
            this.f78a.removeAllViews();
            this.f78a.f86g.setLayoutParams(new LayoutParams(-1, -1));
            this.f78a.addView(this.f78a.f86g);
        }

        public void m95a(AdAdapter adAdapter) {
            if (this.f78a.f82c != null) {
                this.f78a.f84e = true;
                if (this.f78a.f85f != null) {
                    this.f78a.f85f.onAdLoaded(this.f78a);
                }
            }
        }

        public void m96a(C0488b c0488b) {
            if (this.f78a.f85f != null) {
                this.f78a.f85f.onError(this.f78a, c0488b.m729b());
            }
        }

        public void m97b() {
        }

        public void m98c() {
            if (this.f78a.f85f != null) {
                this.f78a.f85f.onAdVideoComplete(this.f78a);
            }
        }
    }

    /* renamed from: com.facebook.ads.InstreamVideoAdView.2 */
    class C03912 implements C0390a {
        final /* synthetic */ InstreamVideoAdView f79a;

        C03912(InstreamVideoAdView instreamVideoAdView) {
            this.f79a = instreamVideoAdView;
        }

        public void m105a(C0458r c0458r) {
            this.f79a.f84e = true;
            if (this.f79a.f85f != null) {
                this.f79a.f85f.onAdLoaded(this.f79a);
            }
        }

        public void m106a(C0458r c0458r, View view) {
            if (view == null) {
                throw new IllegalStateException("Cannot present null view");
            }
            this.f79a.f86g = view;
            this.f79a.removeAllViews();
            this.f79a.f86g.setLayoutParams(new LayoutParams(-1, -1));
            this.f79a.addView(this.f79a.f86g);
        }

        public void m107a(C0458r c0458r, AdError adError) {
            if (this.f79a.f85f != null) {
                this.f79a.f85f.onError(this.f79a, adError);
            }
        }

        public void m108b(C0458r c0458r) {
            if (this.f79a.f85f != null) {
                this.f79a.f85f.onAdClicked(this.f79a);
            }
        }

        public void m109c(C0458r c0458r) {
        }

        public void m110d(C0458r c0458r) {
            if (this.f79a.f85f != null) {
                this.f79a.f85f.onAdVideoComplete(this.f79a);
            }
        }
    }

    public InstreamVideoAdView(@NonNull Context context, @NonNull Bundle bundle) {
        this(context, bundle.getString("placementID"), (AdSize) bundle.get("adSize"));
        this.f87h = bundle;
    }

    public InstreamVideoAdView(@NonNull Context context, @NonNull String str, @NonNull AdSize adSize) {
        super(context);
        this.f84e = false;
        this.f80a = str;
        this.f81b = adSize;
        this.f82c = getController();
    }

    private final void m113a() {
        if (this.f82c != null) {
            this.f82c.m312d();
            this.f82c = null;
            this.f82c = getController();
            this.f83d = null;
        }
    }

    private DisplayAdController getController() {
        this.f82c = new DisplayAdController(getContext(), this.f80a, C0523e.INSTREAM_VIDEO, AdPlacementType.INSTREAM, this.f81b, C0497c.ADS, 1, true);
        this.f82c.m307a(new C03891(this));
        return this.f82c;
    }

    public void destroy() {
        m113a();
    }

    public String getPlacementId() {
        return this.f80a;
    }

    public Bundle getSaveInstanceState() {
        C0458r c0458r = this.f83d != null ? this.f83d : (C0458r) this.f82c.m317i();
        if (c0458r == null) {
            return null;
        }
        Bundle saveInstanceState = c0458r.getSaveInstanceState();
        if (saveInstanceState == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putBundle("adapter", saveInstanceState);
        bundle.putString("placementID", this.f80a);
        bundle.putSerializable("adSize", this.f81b);
        return bundle;
    }

    public boolean isAdLoaded() {
        return this.f84e;
    }

    public void loadAd() {
        if (this.f87h != null) {
            this.f83d = new C0459i();
            this.f83d.m470a(getContext(), new C03912(this), new C0783h(), this.f87h.getBundle("adapter"));
            return;
        }
        this.f82c.m310b();
    }

    public void setAdListener(InstreamVideoAdListener instreamVideoAdListener) {
        this.f85f = instreamVideoAdListener;
    }

    public boolean show() {
        if (this.f84e && (this.f82c != null || this.f83d != null)) {
            if (this.f83d != null) {
                this.f83d.m474d();
            } else {
                this.f82c.m311c();
            }
            this.f84e = false;
            return true;
        } else if (this.f85f == null) {
            return false;
        } else {
            this.f85f.onError(this, AdError.INTERNAL_ERROR);
            return false;
        }
    }
}
