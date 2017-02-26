package com.facebook.ads.internal.adapters;

import com.facebook.ads.internal.p009g.C0549a;
import com.facebook.ads.internal.server.AdPlacementType;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.facebook.ads.internal.adapters.f */
public enum C0446f {
    ANBANNER(C0452h.class, C0445e.AN, AdPlacementType.BANNER),
    ANINTERSTITIAL(C0462j.class, C0445e.AN, AdPlacementType.INTERSTITIAL),
    ADMOBNATIVE(C0442c.class, C0445e.ADMOB, AdPlacementType.NATIVE),
    ANNATIVE(C0469l.class, C0445e.AN, AdPlacementType.NATIVE),
    ANINSTREAMVIDEO(C0459i.class, C0445e.AN, AdPlacementType.INSTREAM),
    ANREWARDEDVIDEO(C0471m.class, C0445e.AN, AdPlacementType.REWARDED_VIDEO),
    INMOBINATIVE(C0478q.class, C0445e.INMOBI, AdPlacementType.NATIVE),
    YAHOONATIVE(C0473n.class, C0445e.YAHOO, AdPlacementType.NATIVE);
    
    private static List<C0446f> f329m;
    public Class<?> f331i;
    public String f332j;
    public C0445e f333k;
    public AdPlacementType f334l;

    private C0446f(Class<?> cls, C0445e c0445e, AdPlacementType adPlacementType) {
        this.f331i = cls;
        this.f333k = c0445e;
        this.f334l = adPlacementType;
    }

    public static List<C0446f> m427a() {
        if (f329m == null) {
            synchronized (C0446f.class) {
                f329m = new ArrayList();
                f329m.add(ANBANNER);
                f329m.add(ANINTERSTITIAL);
                f329m.add(ANNATIVE);
                f329m.add(ANINSTREAMVIDEO);
                f329m.add(ANREWARDEDVIDEO);
                if (C0549a.m924a(C0445e.YAHOO)) {
                    f329m.add(YAHOONATIVE);
                }
                if (C0549a.m924a(C0445e.INMOBI)) {
                    f329m.add(INMOBINATIVE);
                }
                if (C0549a.m924a(C0445e.ADMOB)) {
                    f329m.add(ADMOBNATIVE);
                }
            }
        }
        return f329m;
    }
}
