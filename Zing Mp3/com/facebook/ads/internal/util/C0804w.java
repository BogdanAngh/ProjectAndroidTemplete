package com.facebook.ads.internal.util;

import com.facebook.ads.internal.adapters.C0445e;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;

/* renamed from: com.facebook.ads.internal.util.w */
public class C0804w {

    /* renamed from: com.facebook.ads.internal.util.w.1 */
    static /* synthetic */ class C08031 {
        static final /* synthetic */ int[] f1486a;

        static {
            f1486a = new int[C0445e.values().length];
            try {
                f1486a[C0445e.ADMOB.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1486a[C0445e.YAHOO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1486a[C0445e.INMOBI.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1486a[C0445e.AN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static String m1678a(C0445e c0445e) {
        switch (C08031.f1486a[c0445e.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return "AdMob";
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return "Flurry";
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return "InMobi";
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return "Audience Network";
            default:
                return BuildConfig.FLAVOR;
        }
    }
}
