package com.facebook.ads.internal.p009g;

import com.facebook.ads.internal.adapters.C0445e;
import com.mp3download.zingmp3.C1569R;

/* renamed from: com.facebook.ads.internal.g.a */
public class C0549a {
    private static final String[] f774a;
    private static final String[] f775b;
    private static final String[] f776c;

    /* renamed from: com.facebook.ads.internal.g.a.1 */
    static /* synthetic */ class C05481 {
        static final /* synthetic */ int[] f773a;

        static {
            f773a = new int[C0445e.values().length];
            try {
                f773a[C0445e.AN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f773a[C0445e.YAHOO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f773a[C0445e.INMOBI.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f773a[C0445e.ADMOB.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static {
        f774a = new String[]{"com.flurry.android.FlurryAgent", "com.flurry.android.ads.FlurryAdErrorType", "com.flurry.android.ads.FlurryAdNative", "com.flurry.android.ads.FlurryAdNativeAsset", "com.flurry.android.ads.FlurryAdNativeListener"};
        f775b = new String[]{"com.inmobi.ads.InMobiNative", "com.inmobi.sdk.InMobiSdk"};
        f776c = new String[]{"com.google.android.gms.ads.formats.NativeAdView"};
    }

    public static boolean m924a(C0445e c0445e) {
        switch (C05481.f773a[c0445e.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return true;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return C0549a.m926a(f774a);
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return C0549a.m926a(f775b);
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return C0549a.m926a(f776c);
            default:
                return false;
        }
    }

    private static boolean m925a(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static boolean m926a(String[] strArr) {
        if (strArr == null) {
            return false;
        }
        for (String a : strArr) {
            if (!C0549a.m925a(a)) {
                return false;
            }
        }
        return true;
    }
}
