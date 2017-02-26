package com.facebook.ads.internal.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.internal.AdSdkVersion;
import com.facebook.ads.internal.C0523e;
import com.facebook.ads.internal.p008e.C0522i;
import com.facebook.ads.internal.p010h.p011a.C0554a;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.exoplayer.DefaultLoadControl;
import com.mp3download.zingmp3.C1569R;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/* renamed from: com.facebook.ads.internal.util.x */
public class C0806x {
    private static String f1495a;
    private static final Set<String> f1496b;
    private static final Set<String> f1497c;

    /* renamed from: com.facebook.ads.internal.util.x.a */
    public enum C0805a {
        UNKNOWN(0),
        NONE(0),
        MOBILE_INTERNET(1),
        MOBILE_2G(2),
        MOBILE_3G(3),
        MOBILE_4G(4);
        
        public final int f1494g;

        private C0805a(int i) {
            this.f1494g = i;
        }
    }

    static {
        f1495a = null;
        f1496b = new HashSet(1);
        f1497c = new HashSet(2);
        f1496b.add("1ww8E0AYsR2oX5lndk2hwp2Uosk=\n");
        f1497c.add("toZ2GRnRjC9P5VVUdCpOrFH8lfQ=\n");
        f1497c.add("3lKvjNsfmrn+WmfDhvr2iVh/yRs=\n");
        f1497c.add("B08QtE4yLCdli4rptyqAEczXOeA=\n");
        f1497c.add("XZXI6anZbdKf+taURdnyUH5ipgM=\n");
    }

    public static C0554a m1679a(Context context) {
        return C0806x.m1680a(context, null);
    }

    public static C0554a m1680a(Context context, C0523e c0523e) {
        C0554a c0554a = new C0554a();
        C0806x.m1682a(context, c0554a, c0523e);
        return c0554a;
    }

    private static String m1681a(Context context, String str, String str2) {
        Class cls = Class.forName(str);
        Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[]{Context.class, Class.forName(str2)});
        declaredConstructor.setAccessible(true);
        try {
            String str3 = (String) cls.getMethod("getUserAgentString", new Class[0]).invoke(declaredConstructor.newInstance(new Object[]{context, null}), new Object[0]);
            return str3;
        } finally {
            declaredConstructor.setAccessible(false);
        }
    }

    private static void m1682a(Context context, C0554a c0554a, C0523e c0523e) {
        c0554a.m964c((int) DefaultLoadControl.DEFAULT_HIGH_WATERMARK_MS);
        c0554a.m962b(3);
        c0554a.m947a("user-agent", C0806x.m1688c(context, c0523e) + " [" + "FBAN/AudienceNetworkForAndroid;" + "FBSN/" + "Android" + ";" + "FBSV/" + C0522i.f691a + ";" + "FBAB/" + C0522i.f694d + ";" + "FBAV/" + C0522i.f696f + ";" + "FBBV/" + C0522i.f697g + ";" + "FBVS/" + AdSdkVersion.BUILD + ";" + "FBLC/" + Locale.getDefault().toString() + "]");
    }

    public static boolean m1683a() {
        Object urlPrefix = AdSettings.getUrlPrefix();
        return !TextUtils.isEmpty(urlPrefix) && urlPrefix.endsWith(".sb");
    }

    public static C0554a m1684b() {
        return C0806x.m1679a(null);
    }

    public static C0554a m1685b(Context context) {
        return C0806x.m1686b(context, null);
    }

    public static C0554a m1686b(Context context, C0523e c0523e) {
        C0554a c0554a = new C0554a();
        C0806x.m1682a(context, c0554a, c0523e);
        if (!C0806x.m1683a()) {
            c0554a.m963b(f1497c);
            c0554a.m956a(f1496b);
        }
        return c0554a;
    }

    public static C0805a m1687c(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return C0805a.UNKNOWN;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return C0805a.NONE;
        }
        if (activeNetworkInfo.getType() != 0) {
            return C0805a.MOBILE_INTERNET;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
            case C1569R.styleable.Toolbar_popupTheme /*11*/:
                return C0805a.MOBILE_2G;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
            case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
            case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
            case C1569R.styleable.Toolbar_titleMargin /*14*/:
            case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                return C0805a.MOBILE_3G;
            case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                return C0805a.MOBILE_4G;
            default:
                return C0805a.UNKNOWN;
        }
    }

    private static String m1688c(Context context, C0523e c0523e) {
        if (context == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        if (c0523e == C0523e.NATIVE_250 || c0523e == C0523e.NATIVE_UNKNOWN || c0523e == null) {
            return System.getProperty("http.agent");
        }
        if (f1495a != null) {
            return f1495a;
        }
        synchronized (C0806x.class) {
            if (f1495a != null) {
                String str = f1495a;
                return str;
            }
            if (VERSION.SDK_INT >= 17) {
                try {
                    f1495a = C0806x.m1689d(context);
                    str = f1495a;
                    return str;
                } catch (Exception e) {
                }
            }
            try {
                f1495a = C0806x.m1681a(context, "android.webkit.WebSettings", "android.webkit.WebView");
            } catch (Exception e2) {
                try {
                    f1495a = C0806x.m1681a(context, "android.webkit.WebSettingsClassic", "android.webkit.WebViewClassic");
                } catch (Exception e3) {
                    WebView webView = new WebView(context.getApplicationContext());
                    f1495a = webView.getSettings().getUserAgentString();
                    webView.destroy();
                }
            }
            return f1495a;
        }
    }

    @TargetApi(17)
    private static String m1689d(Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }
}
