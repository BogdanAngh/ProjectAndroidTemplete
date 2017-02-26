package com.facebook.ads.internal.p008e;

import android.content.Context;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.internal.AdSdkVersion;
import com.facebook.ads.internal.C0497c;
import com.facebook.ads.internal.C0510d;
import com.facebook.ads.internal.C0523e;
import com.facebook.ads.internal.adapters.C0444d;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0794o;
import com.facebook.ads.internal.util.C0794o.C0793a;
import com.facebook.ads.internal.util.C0797s;
import com.facebook.ads.internal.util.C0806x;
import com.facebook.appevents.AppEventsConstants;
import com.mp3download.zingmp3.C1569R;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.facebook.ads.internal.e.f */
public class C0519f {
    private static final ExecutorService f671g;
    private static String f672h;
    private static C0793a f673i;
    protected String f674a;
    protected AdPlacementType f675b;
    protected C0514c f676c;
    public Context f677d;
    public C0523e f678e;
    public boolean f679f;
    private C0497c f680j;
    private int f681k;
    private AdSize f682l;

    /* renamed from: com.facebook.ads.internal.e.f.1 */
    class C05171 implements Runnable {
        final /* synthetic */ Context f668a;
        final /* synthetic */ C0519f f669b;

        C05171(C0519f c0519f, Context context) {
            this.f669b = c0519f;
            this.f668a = context;
        }

        public void run() {
            if (C0519f.f672h == null) {
                C0519f.f672h = C0797s.m1666a(this.f668a, this.f668a.getPackageName());
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.e.f.2 */
    static /* synthetic */ class C05182 {
        static final /* synthetic */ int[] f670a;

        static {
            f670a = new int[C0514c.values().length];
            try {
                f670a[C0514c.INTERSTITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f670a[C0514c.BANNER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f670a[C0514c.NATIVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f670a[C0514c.REWARDED_VIDEO.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static {
        f671g = Executors.newSingleThreadExecutor();
        f672h = null;
        f673i = C0794o.m1656a();
    }

    public C0519f(Context context, String str, AdSize adSize, C0523e c0523e, C0497c c0497c, int i, boolean z) {
        this.f674a = str;
        this.f682l = adSize;
        this.f678e = c0523e;
        this.f676c = C0514c.m803a(c0523e);
        this.f680j = c0497c;
        this.f681k = i;
        this.f679f = z;
        m820a(context);
    }

    private void m820a(Context context) {
        this.f677d = context;
        C0520g.m830a();
        C0522i.m833a(context);
        m824g();
        f671g.submit(new C05171(this, context));
    }

    private void m821a(Map<String, String> map, String str, String str2) {
        map.put(str, str2);
    }

    private static Map<String, String> m822b(Context context) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("SDK", "android");
        hashMap.put("SDK_VERSION", AdSdkVersion.BUILD);
        hashMap.put("LOCALE", Locale.getDefault().toString());
        float f = context.getResources().getDisplayMetrics().density;
        int i = context.getResources().getDisplayMetrics().widthPixels;
        int i2 = context.getResources().getDisplayMetrics().heightPixels;
        hashMap.put("DENSITY", String.valueOf(f));
        hashMap.put("SCREEN_WIDTH", String.valueOf((int) (((float) i) / f)));
        hashMap.put("SCREEN_HEIGHT", String.valueOf((int) (((float) i2) / f)));
        hashMap.put("IDFA", C0522i.f705o);
        hashMap.put("IDFA_FLAG", C0522i.f706p ? AppEventsConstants.EVENT_PARAM_VALUE_NO : AppEventsConstants.EVENT_PARAM_VALUE_YES);
        hashMap.put("ATTRIBUTION_ID", C0522i.f704n);
        hashMap.put("ID_SOURCE", C0522i.f707q);
        hashMap.put("OS", "Android");
        hashMap.put("OSVERS", C0522i.f691a);
        hashMap.put("BUNDLE", C0522i.f694d);
        hashMap.put("APPNAME", C0522i.f695e);
        hashMap.put("APPVERS", C0522i.f696f);
        hashMap.put("APPBUILD", String.valueOf(C0522i.f697g));
        hashMap.put("CARRIER", C0522i.f699i);
        hashMap.put("MAKE", C0522i.f692b);
        hashMap.put("MODEL", C0522i.f693c);
        hashMap.put("ROOTED", String.valueOf(f673i.f1458d));
        hashMap.put("COPPA", String.valueOf(AdSettings.isChildDirected()));
        hashMap.put("INSTALLER", C0522i.f698h);
        hashMap.put("SDK_CAPABILITY", C0510d.m795b());
        hashMap.put("NETWORK_TYPE", String.valueOf(C0806x.m1687c(context).f1494g));
        hashMap.put("REQUEST_TIME", C0785i.m1618a(System.currentTimeMillis()));
        hashMap.put("SESSION_TIME", C0785i.m1617a(C0520g.m831b()));
        hashMap.put("SESSION_ID", C0520g.m832c());
        return hashMap;
    }

    private void m824g() {
        if (this.f676c == null) {
            this.f676c = C0514c.UNKNOWN;
        }
        switch (C05182.f670a[this.f676c.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                this.f675b = AdPlacementType.INTERSTITIAL;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                this.f675b = AdPlacementType.BANNER;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                this.f675b = AdPlacementType.NATIVE;
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                this.f675b = AdPlacementType.REWARDED_VIDEO;
            default:
                this.f675b = AdPlacementType.UNKNOWN;
        }
    }

    public String m825a() {
        return this.f674a;
    }

    public C0514c m826b() {
        return this.f676c;
    }

    public AdSize m827c() {
        return this.f682l;
    }

    public int m828d() {
        return this.f681k;
    }

    public Map<String, String> m829e() {
        Map<String, String> hashMap = new HashMap();
        m821a(hashMap, "PLACEMENT_ID", this.f674a);
        if (this.f675b != AdPlacementType.UNKNOWN) {
            m821a(hashMap, "PLACEMENT_TYPE", this.f675b.toString().toLowerCase());
        }
        for (Entry entry : C0519f.m822b(this.f677d).entrySet()) {
            m821a(hashMap, (String) entry.getKey(), (String) entry.getValue());
        }
        if (this.f682l != null) {
            m821a(hashMap, "WIDTH", String.valueOf(this.f682l.getWidth()));
            m821a(hashMap, "HEIGHT", String.valueOf(this.f682l.getHeight()));
        }
        m821a(hashMap, "ADAPTERS", C0444d.m424a(this.f675b));
        if (this.f678e != null) {
            m821a(hashMap, "TEMPLATE_ID", String.valueOf(this.f678e.m837a()));
        }
        if (this.f680j != null) {
            m821a(hashMap, "REQUEST_TYPE", String.valueOf(this.f680j.m752a()));
        }
        if (this.f679f) {
            m821a(hashMap, "TEST_MODE", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        if (this.f681k != 0) {
            m821a(hashMap, "NUM_ADS_REQUESTED", String.valueOf(this.f681k));
        }
        String mediationService = AdSettings.getMediationService();
        if (mediationService != null) {
            m821a(hashMap, "MEDIATION_SERVICE", mediationService);
        }
        m821a(hashMap, "CLIENT_EVENTS", C0778d.m1598a());
        if (f672h != null) {
            m821a(hashMap, "AFP", f672h);
        }
        m821a(hashMap, "UNITY", String.valueOf(C0785i.m1630a(this.f677d)));
        return hashMap;
    }
}
