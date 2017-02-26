package com.facebook.ads;

import android.content.Context;
import android.util.Log;
import com.facebook.ads.internal.C0376a;
import com.facebook.ads.internal.C0488b;
import com.facebook.ads.internal.C0497c;
import com.facebook.ads.internal.C0523e;
import com.facebook.ads.internal.DisplayAdController;
import com.facebook.ads.internal.adapters.AdAdapter;
import com.facebook.ads.internal.adapters.C0470x;
import com.facebook.ads.internal.p002c.C0393a;
import com.facebook.ads.internal.p002c.C0493b;
import com.facebook.ads.internal.server.AdPlacementType;

public class RewardedVideoAd implements Ad {
    private static final String f201a;
    private final Context f202b;
    private final String f203c;
    private DisplayAdController f204d;
    private boolean f205e;
    private RewardedVideoAdListener f206f;
    private C0493b f207g;
    private RewardData f208h;

    /* renamed from: com.facebook.ads.RewardedVideoAd.1 */
    class C04131 extends C0376a {
        final /* synthetic */ RewardedVideoAd f200a;

        /* renamed from: com.facebook.ads.RewardedVideoAd.1.1 */
        class C04121 implements C0393a {
            final /* synthetic */ C04131 f199a;

            C04121(C04131 c04131) {
                this.f199a = c04131;
            }

            public void m217a() {
                this.f199a.f200a.f205e = true;
                if (this.f199a.f200a.f206f != null) {
                    this.f199a.f200a.f206f.onAdLoaded(this.f199a.f200a);
                }
            }
        }

        C04131(RewardedVideoAd rewardedVideoAd) {
            this.f200a = rewardedVideoAd;
        }

        public void m218a() {
            if (this.f200a.f206f != null) {
                this.f200a.f206f.onAdClicked(this.f200a);
            }
        }

        public void m219a(AdAdapter adAdapter) {
            if (this.f200a.f208h != null) {
                ((C0470x) adAdapter).m562a(this.f200a.f208h);
            }
            this.f200a.f207g.m739b(((C0470x) adAdapter).m560a());
            this.f200a.f207g.m737a(new C04121(this));
        }

        public void m220a(C0488b c0488b) {
            if (this.f200a.f206f != null) {
                this.f200a.f206f.onError(this.f200a, c0488b.m729b());
            }
        }

        public void m221b() {
            if (this.f200a.f206f != null) {
                this.f200a.f206f.onLoggingImpression(this.f200a);
            }
        }

        public void m222f() {
            this.f200a.f206f.onRewardedVideoCompleted();
        }

        public void m223g() {
            if (this.f200a.f206f != null) {
                this.f200a.f206f.onRewardedVideoClosed();
            }
        }

        public void m224h() {
            if (this.f200a.f206f instanceof S2SRewardedVideoAdListener) {
                ((S2SRewardedVideoAdListener) this.f200a.f206f).onRewardServerFailed();
            }
        }

        public void m225i() {
            if (this.f200a.f206f instanceof S2SRewardedVideoAdListener) {
                ((S2SRewardedVideoAdListener) this.f200a.f206f).onRewardServerSuccess();
            }
        }
    }

    static {
        f201a = RewardedVideoAd.class.getSimpleName();
    }

    public RewardedVideoAd(Context context, String str) {
        this.f205e = false;
        this.f202b = context;
        this.f203c = str;
        this.f207g = new C0493b(context);
    }

    private void m227a() {
        m230b();
        this.f205e = false;
        this.f204d = new DisplayAdController(this.f202b, this.f203c, C0523e.REWARDED_VIDEO, AdPlacementType.REWARDED_VIDEO, AdSize.INTERSTITIAL, C0497c.ADS, 1, true);
        this.f204d.m307a(new C04131(this));
        this.f204d.m310b();
    }

    private final void m230b() {
        if (this.f204d != null) {
            this.f204d.m312d();
            this.f204d = null;
        }
    }

    public void destroy() {
        m230b();
    }

    public String getPlacementId() {
        return this.f203c;
    }

    public boolean isAdLoaded() {
        return this.f205e;
    }

    public void loadAd() {
        try {
            m227a();
        } catch (Throwable e) {
            Log.e(f201a, "Error loading rewarded video ad", e);
            if (this.f206f != null) {
                this.f206f.onError(this, AdError.INTERNAL_ERROR);
            }
        }
    }

    public void setAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        this.f206f = rewardedVideoAdListener;
    }

    public void setRewardData(RewardData rewardData) {
        this.f208h = rewardData;
    }

    public boolean show() {
        if (this.f205e) {
            this.f204d.m311c();
            this.f205e = false;
            return true;
        } else if (this.f206f == null) {
            return false;
        } else {
            this.f206f.onError(this, AdError.INTERNAL_ERROR);
            return false;
        }
    }
}
