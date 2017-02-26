package com.facebook.ads;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.facebook.ads.NativeAd.MediaCacheFlag;
import com.facebook.ads.internal.C0488b;
import com.facebook.ads.internal.C0497c;
import com.facebook.ads.internal.C0523e;
import com.facebook.ads.internal.C0747i;
import com.facebook.ads.internal.C0747i.C0409a;
import com.facebook.ads.internal.adapters.C0440v;
import com.facebook.ads.internal.p002c.C0393a;
import com.facebook.ads.internal.p002c.C0493b;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class NativeAdsManager {
    private static final C0497c f187a;
    private final Context f188b;
    private final String f189c;
    private final int f190d;
    private final List<NativeAd> f191e;
    private int f192f;
    private Listener f193g;
    private C0747i f194h;
    private boolean f195i;
    private boolean f196j;

    /* renamed from: com.facebook.ads.NativeAdsManager.1 */
    class C04101 implements C0409a {
        final /* synthetic */ EnumSet f185a;
        final /* synthetic */ NativeAdsManager f186b;

        /* renamed from: com.facebook.ads.NativeAdsManager.1.1 */
        class C04081 implements C0393a {
            final /* synthetic */ List f183a;
            final /* synthetic */ C04101 f184b;

            C04081(C04101 c04101, List list) {
                this.f184b = c04101;
                this.f183a = list;
            }

            public void m207a() {
                this.f184b.f186b.f196j = true;
                this.f184b.f186b.f191e.clear();
                this.f184b.f186b.f192f = 0;
                for (C0440v nativeAd : this.f183a) {
                    this.f184b.f186b.f191e.add(new NativeAd(this.f184b.f186b.f188b, nativeAd, null));
                }
                if (this.f184b.f186b.f193g != null) {
                    this.f184b.f186b.f193g.onAdsLoaded();
                }
            }
        }

        C04101(NativeAdsManager nativeAdsManager, EnumSet enumSet) {
            this.f186b = nativeAdsManager;
            this.f185a = enumSet;
        }

        public void m210a(C0488b c0488b) {
            if (this.f186b.f193g != null) {
                this.f186b.f193g.onAdError(c0488b.m729b());
            }
        }

        public void m211a(List<C0440v> list) {
            C0493b c0493b = new C0493b(this.f186b.f188b);
            for (C0440v c0440v : list) {
                if (this.f185a.contains(MediaCacheFlag.ICON) && c0440v.m359k() != null) {
                    c0493b.m738a(c0440v.m359k().getUrl());
                }
                if (this.f185a.contains(MediaCacheFlag.IMAGE) && c0440v.m360l() != null) {
                    c0493b.m738a(c0440v.m360l().getUrl());
                }
                if (this.f185a.contains(MediaCacheFlag.VIDEO) && !TextUtils.isEmpty(c0440v.m371w())) {
                    c0493b.m739b(c0440v.m371w());
                }
            }
            c0493b.m737a(new C04081(this, list));
        }
    }

    public interface Listener {
        void onAdError(AdError adError);

        void onAdsLoaded();
    }

    static {
        f187a = C0497c.ADS;
    }

    public NativeAdsManager(@NonNull Context context, String str, int i) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        this.f188b = context;
        this.f189c = str;
        this.f190d = Math.max(i, 0);
        this.f191e = new ArrayList(i);
        this.f192f = -1;
        this.f196j = false;
        this.f195i = false;
    }

    public void disableAutoRefresh() {
        this.f195i = true;
        if (this.f194h != null) {
            this.f194h.m1471c();
        }
    }

    public int getUniqueNativeAdCount() {
        return this.f191e.size();
    }

    public boolean isLoaded() {
        return this.f196j;
    }

    public void loadAds() {
        loadAds(EnumSet.of(MediaCacheFlag.NONE));
    }

    public void loadAds(EnumSet<MediaCacheFlag> enumSet) {
        C0523e c0523e = C0523e.NATIVE_UNKNOWN;
        int i = this.f190d;
        if (this.f194h != null) {
            this.f194h.m1470b();
        }
        this.f194h = new C0747i(this.f188b, this.f189c, c0523e, null, f187a, i, enumSet);
        if (this.f195i) {
            this.f194h.m1471c();
        }
        this.f194h.m1468a(new C04101(this, enumSet));
        this.f194h.m1466a();
    }

    public NativeAd nextNativeAd() {
        if (this.f191e.size() == 0) {
            return null;
        }
        int i = this.f192f;
        this.f192f = i + 1;
        NativeAd nativeAd = (NativeAd) this.f191e.get(i % this.f191e.size());
        return i >= this.f191e.size() ? new NativeAd(nativeAd) : nativeAd;
    }

    public void setListener(Listener listener) {
        this.f193g = listener;
    }
}
