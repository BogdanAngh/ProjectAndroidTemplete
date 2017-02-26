package com.facebook.ads.internal.p008e;

import com.facebook.ads.internal.C0523e;
import com.mp3download.zingmp3.C1569R;

/* renamed from: com.facebook.ads.internal.e.c */
public enum C0514c {
    UNKNOWN,
    BANNER,
    INTERSTITIAL,
    NATIVE,
    REWARDED_VIDEO;

    /* renamed from: com.facebook.ads.internal.e.c.1 */
    static /* synthetic */ class C05131 {
        static final /* synthetic */ int[] f643a;

        static {
            f643a = new int[C0523e.values().length];
            try {
                f643a[C0523e.NATIVE_UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f643a[C0523e.WEBVIEW_BANNER_50.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f643a[C0523e.WEBVIEW_BANNER_90.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f643a[C0523e.WEBVIEW_BANNER_LEGACY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f643a[C0523e.WEBVIEW_BANNER_250.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f643a[C0523e.WEBVIEW_INTERSTITIAL_HORIZONTAL.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f643a[C0523e.WEBVIEW_INTERSTITIAL_VERTICAL.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f643a[C0523e.WEBVIEW_INTERSTITIAL_TABLET.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f643a[C0523e.WEBVIEW_INTERSTITIAL_UNKNOWN.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f643a[C0523e.REWARDED_VIDEO.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    public static C0514c m803a(C0523e c0523e) {
        switch (C05131.f643a[c0523e.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return NATIVE;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return BANNER;
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                return INTERSTITIAL;
            case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                return REWARDED_VIDEO;
            default:
                return UNKNOWN;
        }
    }
}
