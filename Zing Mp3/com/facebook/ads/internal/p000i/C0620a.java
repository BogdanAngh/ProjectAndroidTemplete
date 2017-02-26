package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView.Type;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.p000i.p015b.C0622b;
import com.facebook.ads.internal.p000i.p015b.C0624d;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;

/* renamed from: com.facebook.ads.internal.i.a */
public class C0620a extends RelativeLayout {
    private final NativeAdViewAttributes f906a;
    private final NativeAd f907b;
    private final DisplayMetrics f908c;
    private ArrayList<View> f909d;

    /* renamed from: com.facebook.ads.internal.i.a.1 */
    static /* synthetic */ class C06071 {
        static final /* synthetic */ int[] f876a;

        static {
            f876a = new int[Type.values().length];
            try {
                f876a[Type.HEIGHT_400.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f876a[Type.HEIGHT_300.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f876a[Type.HEIGHT_100.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f876a[Type.HEIGHT_120.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public C0620a(Context context, NativeAd nativeAd, Type type, NativeAdViewAttributes nativeAdViewAttributes) {
        super(context);
        setBackgroundColor(nativeAdViewAttributes.getBackgroundColor());
        this.f906a = nativeAdViewAttributes;
        this.f907b = nativeAd;
        this.f908c = context.getResources().getDisplayMetrics();
        setLayoutParams(new LayoutParams(-1, Math.round(((float) type.getHeight()) * this.f908c.density)));
        View c0740o = new C0740o(context);
        c0740o.setMinWidth(Math.round(280.0f * this.f908c.density));
        c0740o.setMaxWidth(Math.round(375.0f * this.f908c.density));
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(13, -1);
        c0740o.setLayoutParams(layoutParams);
        addView(c0740o);
        ViewGroup linearLayout = new LinearLayout(context);
        this.f909d = new ArrayList();
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        c0740o.addView(linearLayout);
        switch (C06071.f876a[type.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                m1169b(linearLayout);
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                break;
        }
        m1165a(linearLayout);
        m1166a(linearLayout, type);
        nativeAd.registerViewForInteraction(this, this.f909d);
        View adChoicesView = new AdChoicesView(getContext(), nativeAd, true);
        LayoutParams layoutParams2 = (LayoutParams) adChoicesView.getLayoutParams();
        layoutParams2.addRule(11);
        layoutParams2.setMargins(Math.round(this.f908c.density * 4.0f), Math.round(this.f908c.density * 4.0f), Math.round(this.f908c.density * 4.0f), Math.round(this.f908c.density * 4.0f));
        c0740o.addView(adChoicesView);
    }

    private void m1165a(ViewGroup viewGroup) {
        View relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, Math.round(this.f908c.density * 180.0f)));
        relativeLayout.setBackgroundColor(this.f906a.getBackgroundColor());
        View mediaView = new MediaView(getContext());
        relativeLayout.addView(mediaView);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, (int) (this.f908c.density * 180.0f));
        layoutParams.addRule(13, -1);
        mediaView.setLayoutParams(layoutParams);
        mediaView.setAutoplay(this.f906a.getAutoplay());
        mediaView.setAutoplayOnMobile(this.f906a.getAutoplayOnMobile());
        mediaView.setNativeAd(this.f907b);
        viewGroup.addView(relativeLayout);
        this.f909d.add(mediaView);
    }

    private void m1166a(ViewGroup viewGroup, Type type) {
        View c0622b = new C0622b(getContext(), this.f907b, this.f906a, m1167a(type), m1168b(type));
        c0622b.setLayoutParams(new LinearLayout.LayoutParams(-1, Math.round(((float) m1168b(type)) * this.f908c.density)));
        viewGroup.addView(c0622b);
        this.f909d.add(c0622b.getIconView());
        this.f909d.add(c0622b.getCallToActionView());
    }

    private boolean m1167a(Type type) {
        return type == Type.HEIGHT_300 || type == Type.HEIGHT_120;
    }

    private int m1168b(Type type) {
        switch (C06071.f876a[type.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return (type.getHeight() - 180) / 2;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return type.getHeight() - 180;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return type.getHeight();
            default:
                return 0;
        }
    }

    private void m1169b(ViewGroup viewGroup) {
        View c0624d = new C0624d(getContext(), this.f907b, this.f906a);
        c0624d.setLayoutParams(new LinearLayout.LayoutParams(-1, Math.round(110.0f * this.f908c.density)));
        viewGroup.addView(c0624d);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f907b.unregisterView();
    }
}
