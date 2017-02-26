package com.facebook.ads;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.facebook.ads.NativeAdView.Type;
import com.google.android.exoplayer.C0989C;
import java.util.ArrayList;
import java.util.List;

public class NativeAdScrollView extends LinearLayout {
    public static final int DEFAULT_INSET = 20;
    public static final int DEFAULT_MAX_ADS = 10;
    private final Context f164a;
    private final NativeAdsManager f165b;
    private final AdViewProvider f166c;
    private final Type f167d;
    private final int f168e;
    private final C0407b f169f;
    private final NativeAdViewAttributes f170g;

    public interface AdViewProvider {
        View createView(NativeAd nativeAd, int i);

        void destroyView(NativeAd nativeAd, View view);
    }

    /* renamed from: com.facebook.ads.NativeAdScrollView.a */
    private class C0406a extends PagerAdapter {
        final /* synthetic */ NativeAdScrollView f161a;
        private List<NativeAd> f162b;

        public C0406a(NativeAdScrollView nativeAdScrollView) {
            this.f161a = nativeAdScrollView;
            this.f162b = new ArrayList();
        }

        public void m200a() {
            this.f162b.clear();
            int min = Math.min(this.f161a.f168e, this.f161a.f165b.getUniqueNativeAdCount());
            for (int i = 0; i < min; i++) {
                NativeAd nextNativeAd = this.f161a.f165b.nextNativeAd();
                nextNativeAd.m191a(true);
                this.f162b.add(nextNativeAd);
            }
            notifyDataSetChanged();
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (i < this.f162b.size()) {
                if (this.f161a.f167d != null) {
                    ((NativeAd) this.f162b.get(i)).unregisterView();
                } else {
                    this.f161a.f166c.destroyView((NativeAd) this.f162b.get(i), (View) obj);
                }
            }
            viewGroup.removeView((View) obj);
        }

        public int getCount() {
            return this.f162b.size();
        }

        public int getItemPosition(Object obj) {
            int indexOf = this.f162b.indexOf(obj);
            return indexOf >= 0 ? indexOf : -2;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View render = this.f161a.f167d != null ? NativeAdView.render(this.f161a.f164a, (NativeAd) this.f162b.get(i), this.f161a.f167d, this.f161a.f170g) : this.f161a.f166c.createView((NativeAd) this.f162b.get(i), i);
            viewGroup.addView(render);
            return render;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    /* renamed from: com.facebook.ads.NativeAdScrollView.b */
    private class C0407b extends ViewPager {
        final /* synthetic */ NativeAdScrollView f163a;

        public C0407b(NativeAdScrollView nativeAdScrollView, Context context) {
            this.f163a = nativeAdScrollView;
            super(context);
        }

        protected void onMeasure(int i, int i2) {
            int i3 = 0;
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                View childAt = getChildAt(i4);
                childAt.measure(i, MeasureSpec.makeMeasureSpec(0, 0));
                int measuredHeight = childAt.getMeasuredHeight();
                if (measuredHeight > i3) {
                    i3 = measuredHeight;
                }
            }
            super.onMeasure(i, MeasureSpec.makeMeasureSpec(i3, C0989C.ENCODING_PCM_32BIT));
        }
    }

    public NativeAdScrollView(Context context, NativeAdsManager nativeAdsManager, AdViewProvider adViewProvider) {
        this(context, nativeAdsManager, adViewProvider, null, null, DEFAULT_MAX_ADS);
    }

    public NativeAdScrollView(Context context, NativeAdsManager nativeAdsManager, AdViewProvider adViewProvider, int i) {
        this(context, nativeAdsManager, adViewProvider, null, null, i);
    }

    private NativeAdScrollView(Context context, NativeAdsManager nativeAdsManager, AdViewProvider adViewProvider, Type type, NativeAdViewAttributes nativeAdViewAttributes, int i) {
        super(context);
        if (!nativeAdsManager.isLoaded()) {
            throw new IllegalStateException("NativeAdsManager not loaded");
        } else if (type == null && adViewProvider == null) {
            throw new IllegalArgumentException("Must provide a NativeAdView.Type or AdViewProvider");
        } else {
            this.f164a = context;
            this.f165b = nativeAdsManager;
            this.f170g = nativeAdViewAttributes;
            this.f166c = adViewProvider;
            this.f167d = type;
            this.f168e = i;
            PagerAdapter c0406a = new C0406a(this);
            this.f169f = new C0407b(this, context);
            this.f169f.setAdapter(c0406a);
            setInset(DEFAULT_INSET);
            c0406a.m200a();
            addView(this.f169f);
        }
    }

    public NativeAdScrollView(Context context, NativeAdsManager nativeAdsManager, Type type) {
        this(context, nativeAdsManager, null, type, new NativeAdViewAttributes(), DEFAULT_MAX_ADS);
    }

    public NativeAdScrollView(Context context, NativeAdsManager nativeAdsManager, Type type, NativeAdViewAttributes nativeAdViewAttributes) {
        this(context, nativeAdsManager, null, type, nativeAdViewAttributes, DEFAULT_MAX_ADS);
    }

    public NativeAdScrollView(Context context, NativeAdsManager nativeAdsManager, Type type, NativeAdViewAttributes nativeAdViewAttributes, int i) {
        this(context, nativeAdsManager, null, type, nativeAdViewAttributes, i);
    }

    public void setInset(int i) {
        if (i > 0) {
            DisplayMetrics displayMetrics = this.f164a.getResources().getDisplayMetrics();
            int round = Math.round(((float) i) * displayMetrics.density);
            this.f169f.setPadding(round, 0, round, 0);
            this.f169f.setPageMargin(Math.round(displayMetrics.density * ((float) (i / 2))));
            this.f169f.setClipToPadding(false);
        }
    }
}
