package com.facebook.ads.internal.adapters;

import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.af;
import com.mp3download.zingmp3.C1569R;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.facebook.ads.internal.adapters.d */
public class C0444d {
    private static final Set<C0446f> f313a;
    private static final Map<AdPlacementType, String> f314b;

    /* renamed from: com.facebook.ads.internal.adapters.d.1 */
    static /* synthetic */ class C04431 {
        static final /* synthetic */ int[] f312a;

        static {
            f312a = new int[AdPlacementType.values().length];
            try {
                f312a[AdPlacementType.BANNER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f312a[AdPlacementType.INTERSTITIAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f312a[AdPlacementType.NATIVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f312a[AdPlacementType.INSTREAM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f312a[AdPlacementType.REWARDED_VIDEO.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static {
        f313a = new HashSet();
        f314b = new ConcurrentHashMap();
        for (C0446f c0446f : C0446f.m427a()) {
            Class cls;
            switch (C04431.f312a[c0446f.f334l.ordinal()]) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    cls = BannerAdapter.class;
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    cls = InterstitialAdapter.class;
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    cls = C0440v.class;
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    cls = C0458r.class;
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    cls = C0470x.class;
                    break;
                default:
                    cls = null;
                    break;
            }
            if (cls != null) {
                Class cls2 = c0446f.f331i;
                if (cls2 == null) {
                    try {
                        cls2 = Class.forName(c0446f.f332j);
                    } catch (ClassNotFoundException e) {
                    }
                }
                if (cls2 != null && cls.isAssignableFrom(cls2)) {
                    f313a.add(c0446f);
                }
            }
        }
    }

    public static AdAdapter m422a(C0445e c0445e, AdPlacementType adPlacementType) {
        try {
            C0446f b = C0444d.m425b(c0445e, adPlacementType);
            if (b != null && f313a.contains(b)) {
                Class cls = b.f331i;
                if (cls == null) {
                    cls = Class.forName(b.f332j);
                }
                return (AdAdapter) cls.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AdAdapter m423a(String str, AdPlacementType adPlacementType) {
        return C0444d.m422a(C0445e.m426a(str), adPlacementType);
    }

    public static String m424a(AdPlacementType adPlacementType) {
        if (f314b.containsKey(adPlacementType)) {
            return (String) f314b.get(adPlacementType);
        }
        Set hashSet = new HashSet();
        for (C0446f c0446f : f313a) {
            if (c0446f.f334l == adPlacementType) {
                hashSet.add(c0446f.f333k.toString());
            }
        }
        String a = af.m1589a(hashSet, ",");
        f314b.put(adPlacementType, a);
        return a;
    }

    private static C0446f m425b(C0445e c0445e, AdPlacementType adPlacementType) {
        for (C0446f c0446f : f313a) {
            if (c0446f.f333k == c0445e && c0446f.f334l == adPlacementType) {
                return c0446f;
            }
        }
        return null;
    }
}
