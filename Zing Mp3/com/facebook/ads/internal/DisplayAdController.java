package com.facebook.ads.internal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.internal.adapters.AdAdapter;
import com.facebook.ads.internal.adapters.BannerAdapter;
import com.facebook.ads.internal.adapters.BannerAdapterListener;
import com.facebook.ads.internal.adapters.C0415w;
import com.facebook.ads.internal.adapters.C0421y;
import com.facebook.ads.internal.adapters.C0440v;
import com.facebook.ads.internal.adapters.C0441t;
import com.facebook.ads.internal.adapters.C0444d;
import com.facebook.ads.internal.adapters.C0458r;
import com.facebook.ads.internal.adapters.C0470x;
import com.facebook.ads.internal.adapters.InterstitialAdapter;
import com.facebook.ads.internal.adapters.InterstitialAdapterListener;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.ads.internal.p008e.C0511a;
import com.facebook.ads.internal.p008e.C0515d;
import com.facebook.ads.internal.p008e.C0516e;
import com.facebook.ads.internal.p008e.C0519f;
import com.facebook.ads.internal.p008e.C0521h;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.server.C0756a;
import com.facebook.ads.internal.server.C0756a.C0428a;
import com.facebook.ads.internal.server.C0761e;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0794o;
import com.facebook.ads.internal.util.C0804w;
import com.facebook.ads.internal.util.C0807y;
import com.facebook.ads.internal.util.aj;
import com.facebook.ads.p001a.C0390a;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayAdController implements C0428a {
    private static final String f242b;
    private static final Handler f243h;
    private static boolean f244i;
    protected C0376a f245a;
    private final Context f246c;
    private final String f247d;
    private final AdPlacementType f248e;
    private final C0756a f249f;
    private final Handler f250g;
    private final Runnable f251j;
    private final Runnable f252k;
    private volatile boolean f253l;
    private boolean f254m;
    private volatile boolean f255n;
    private AdAdapter f256o;
    private AdAdapter f257p;
    private View f258q;
    private C0515d f259r;
    private C0519f f260s;
    private C0523e f261t;
    private C0497c f262u;
    private AdSize f263v;
    private int f264w;
    private final C0427c f265x;
    private boolean f266y;

    /* renamed from: com.facebook.ads.internal.DisplayAdController.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ InterstitialAdapter f213a;
        final /* synthetic */ DisplayAdController f214b;

        AnonymousClass10(DisplayAdController displayAdController, InterstitialAdapter interstitialAdapter) {
            this.f214b = displayAdController;
            this.f213a = interstitialAdapter;
        }

        public void run() {
            this.f214b.m273a(this.f213a);
            this.f214b.m299n();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.11 */
    class AnonymousClass11 implements InterstitialAdapterListener {
        final /* synthetic */ Runnable f215a;
        final /* synthetic */ DisplayAdController f216b;

        AnonymousClass11(DisplayAdController displayAdController, Runnable runnable) {
            this.f216b = displayAdController;
            this.f215a = runnable;
        }

        public void onInterstitialAdClicked(InterstitialAdapter interstitialAdapter, String str, boolean z) {
            this.f216b.f245a.m24a();
            Object obj = !TextUtils.isEmpty(str) ? 1 : null;
            if (z && obj != null) {
                Intent intent = new Intent("android.intent.action.VIEW");
                if (!(this.f216b.f260s.f677d instanceof Activity)) {
                    intent.addFlags(268435456);
                }
                intent.setData(Uri.parse(str));
                this.f216b.f260s.f677d.startActivity(intent);
            }
        }

        public void onInterstitialAdDismissed(InterstitialAdapter interstitialAdapter) {
            this.f216b.f245a.m32e();
        }

        public void onInterstitialAdDisplayed(InterstitialAdapter interstitialAdapter) {
            this.f216b.f245a.m31d();
        }

        public void onInterstitialAdLoaded(InterstitialAdapter interstitialAdapter) {
            if (interstitialAdapter == this.f216b.f256o) {
                this.f216b.f250g.removeCallbacks(this.f215a);
                this.f216b.f257p = interstitialAdapter;
                this.f216b.f245a.m26a((AdAdapter) interstitialAdapter);
                this.f216b.m302p();
            }
        }

        public void onInterstitialError(InterstitialAdapter interstitialAdapter, AdError adError) {
            if (interstitialAdapter == this.f216b.f256o) {
                this.f216b.f250g.removeCallbacks(this.f215a);
                this.f216b.m273a((AdAdapter) interstitialAdapter);
                this.f216b.m299n();
                this.f216b.f245a.m28a(new C0488b(adError.getErrorCode(), adError.getErrorMessage()));
            }
        }

        public void onInterstitialLoggingImpression(InterstitialAdapter interstitialAdapter) {
            this.f216b.f245a.m29b();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.12 */
    class AnonymousClass12 implements Runnable {
        final /* synthetic */ C0440v f217a;
        final /* synthetic */ long f218b;
        final /* synthetic */ C0511a f219c;
        final /* synthetic */ DisplayAdController f220d;

        AnonymousClass12(DisplayAdController displayAdController, C0440v c0440v, long j, C0511a c0511a) {
            this.f220d = displayAdController;
            this.f217a = c0440v;
            this.f218b = j;
            this.f219c = c0511a;
        }

        public void run() {
            this.f220d.m273a(this.f217a);
            if (this.f217a instanceof C0441t) {
                C0785i.m1628a(this.f220d.f246c, C0804w.m1678a(((C0441t) this.f217a).m375D()) + " Failed. Ad request timed out");
            }
            Map a = this.f220d.m269a(this.f218b);
            a.put(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, "-1");
            a.put(NotificationCompatApi24.CATEGORY_MESSAGE, "timeout");
            this.f220d.m279a(this.f219c.m798a(C0521h.REQUEST), a);
            this.f220d.m299n();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.1 */
    class C04141 implements Runnable {
        final /* synthetic */ C0761e f221a;
        final /* synthetic */ DisplayAdController f222b;

        C04141(DisplayAdController displayAdController, C0761e c0761e) {
            this.f222b = displayAdController;
            this.f221a = c0761e;
        }

        public void run() {
            C0515d b = this.f221a.m1528b();
            if (b == null || b.m804a() == null) {
                throw new IllegalStateException("invalid placement in response");
            }
            this.f222b.f259r = b;
            this.f222b.m299n();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.2 */
    class C04162 implements C0415w {
        boolean f223a;
        boolean f224b;
        boolean f225c;
        final /* synthetic */ Runnable f226d;
        final /* synthetic */ long f227e;
        final /* synthetic */ C0511a f228f;
        final /* synthetic */ DisplayAdController f229g;

        C04162(DisplayAdController displayAdController, Runnable runnable, long j, C0511a c0511a) {
            this.f229g = displayAdController;
            this.f226d = runnable;
            this.f227e = j;
            this.f228f = c0511a;
            this.f223a = false;
            this.f224b = false;
            this.f225c = false;
        }

        public void m237a(C0440v c0440v) {
            if (c0440v == this.f229g.f256o) {
                this.f229g.f250g.removeCallbacks(this.f226d);
                this.f229g.f257p = c0440v;
                this.f229g.f245a.m26a((AdAdapter) c0440v);
                if (!this.f223a) {
                    this.f223a = true;
                    this.f229g.m279a(this.f228f.m798a(C0521h.REQUEST), this.f229g.m269a(this.f227e));
                }
            }
        }

        public void m238a(C0440v c0440v, AdError adError) {
            if (c0440v == this.f229g.f256o) {
                this.f229g.f250g.removeCallbacks(this.f226d);
                this.f229g.m273a((AdAdapter) c0440v);
                if (!this.f223a) {
                    this.f223a = true;
                    Map a = this.f229g.m269a(this.f227e);
                    a.put(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, String.valueOf(adError.getErrorCode()));
                    a.put(NotificationCompatApi24.CATEGORY_MESSAGE, String.valueOf(adError.getErrorMessage()));
                    this.f229g.m279a(this.f228f.m798a(C0521h.REQUEST), a);
                }
                this.f229g.m299n();
            }
        }

        public void m239b(C0440v c0440v) {
            if (!this.f224b) {
                this.f224b = true;
                this.f229g.m279a(this.f228f.m798a(C0521h.IMPRESSION), null);
            }
        }

        public void m240c(C0440v c0440v) {
            if (!this.f225c) {
                this.f225c = true;
                this.f229g.m279a(this.f228f.m798a(C0521h.CLICK), null);
            }
            if (this.f229g.f245a != null) {
                this.f229g.f245a.m24a();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.3 */
    class C04173 implements Runnable {
        final /* synthetic */ C0488b f230a;
        final /* synthetic */ DisplayAdController f231b;

        C04173(DisplayAdController displayAdController, C0488b c0488b) {
            this.f231b = displayAdController;
            this.f230a = c0488b;
        }

        public void run() {
            this.f231b.f245a.m28a(this.f230a);
            if (!this.f231b.f254m && !this.f231b.f253l) {
                switch (this.f230a.m728a().getErrorCode()) {
                    case AdError.NETWORK_ERROR_CODE /*1000*/:
                    case AdError.LOAD_TOO_FREQUENTLY_ERROR_CODE /*1002*/:
                        switch (C04184.f232a[this.f231b.m294l().ordinal()]) {
                            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                                this.f231b.f250g.postDelayed(this.f231b.f251j, 30000);
                                this.f231b.f253l = true;
                            default:
                        }
                    default:
                }
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.4 */
    static /* synthetic */ class C04184 {
        static final /* synthetic */ int[] f232a;

        static {
            f232a = new int[AdPlacementType.values().length];
            try {
                f232a[AdPlacementType.INTERSTITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f232a[AdPlacementType.BANNER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f232a[AdPlacementType.NATIVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f232a[AdPlacementType.INSTREAM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f232a[AdPlacementType.REWARDED_VIDEO.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.5 */
    class C04195 implements Runnable {
        final /* synthetic */ DisplayAdController f233a;

        C04195(DisplayAdController displayAdController) {
            this.f233a = displayAdController;
        }

        public void run() {
            try {
                this.f233a.m301o();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.6 */
    class C04206 implements C0390a {
        final /* synthetic */ DisplayAdController f234a;

        C04206(DisplayAdController displayAdController) {
            this.f234a = displayAdController;
        }

        public void m241a(C0458r c0458r) {
            this.f234a.f257p = c0458r;
            this.f234a.f245a.m26a((AdAdapter) c0458r);
        }

        public void m242a(C0458r c0458r, View view) {
            this.f234a.f245a.m25a(view);
        }

        public void m243a(C0458r c0458r, AdError adError) {
            this.f234a.f245a.m28a(new C0488b(adError.getErrorCode(), adError.getErrorMessage()));
        }

        public void m244b(C0458r c0458r) {
            this.f234a.f245a.m24a();
        }

        public void m245c(C0458r c0458r) {
            this.f234a.f245a.m29b();
        }

        public void m246d(C0458r c0458r) {
            this.f234a.f245a.m30c();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.7 */
    class C04227 implements C0421y {
        final /* synthetic */ DisplayAdController f235a;

        C04227(DisplayAdController displayAdController) {
            this.f235a = displayAdController;
        }

        public void m255a() {
            this.f235a.f245a.m34g();
        }

        public void m256a(C0470x c0470x) {
            this.f235a.f257p = c0470x;
            this.f235a.f245a.m26a((AdAdapter) c0470x);
        }

        public void m257a(C0470x c0470x, AdError adError) {
            this.f235a.f245a.m28a(new C0488b(AdErrorType.INTERNAL_ERROR, null));
            this.f235a.m273a((AdAdapter) c0470x);
            this.f235a.m299n();
        }

        public void m258b(C0470x c0470x) {
            this.f235a.f245a.m24a();
        }

        public void m259c(C0470x c0470x) {
            this.f235a.f245a.m29b();
        }

        public void m260d(C0470x c0470x) {
            this.f235a.f245a.m33f();
        }

        public void m261e(C0470x c0470x) {
            this.f235a.f245a.m35h();
        }

        public void m262f(C0470x c0470x) {
            this.f235a.f245a.m36i();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.8 */
    class C04238 implements Runnable {
        final /* synthetic */ BannerAdapter f236a;
        final /* synthetic */ DisplayAdController f237b;

        C04238(DisplayAdController displayAdController, BannerAdapter bannerAdapter) {
            this.f237b = displayAdController;
            this.f236a = bannerAdapter;
        }

        public void run() {
            this.f237b.m273a(this.f236a);
            this.f237b.m299n();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.9 */
    class C04249 implements BannerAdapterListener {
        final /* synthetic */ Runnable f238a;
        final /* synthetic */ DisplayAdController f239b;

        C04249(DisplayAdController displayAdController, Runnable runnable) {
            this.f239b = displayAdController;
            this.f238a = runnable;
        }

        public void onBannerAdClicked(BannerAdapter bannerAdapter) {
            this.f239b.f245a.m24a();
        }

        public void onBannerAdExpanded(BannerAdapter bannerAdapter) {
            this.f239b.m303q();
        }

        public void onBannerAdLoaded(BannerAdapter bannerAdapter, View view) {
            if (bannerAdapter == this.f239b.f256o) {
                this.f239b.f250g.removeCallbacks(this.f238a);
                AdAdapter g = this.f239b.f257p;
                this.f239b.f257p = bannerAdapter;
                this.f239b.f258q = view;
                if (this.f239b.f255n) {
                    this.f239b.f245a.m25a(view);
                    this.f239b.m273a(g);
                    this.f239b.m302p();
                    return;
                }
                this.f239b.f245a.m26a((AdAdapter) bannerAdapter);
            }
        }

        public void onBannerAdMinimized(BannerAdapter bannerAdapter) {
            this.f239b.m302p();
        }

        public void onBannerError(BannerAdapter bannerAdapter, AdError adError) {
            if (bannerAdapter == this.f239b.f256o) {
                this.f239b.f250g.removeCallbacks(this.f238a);
                this.f239b.m273a((AdAdapter) bannerAdapter);
                this.f239b.m299n();
            }
        }

        public void onBannerLoggingImpression(BannerAdapter bannerAdapter) {
            this.f239b.f245a.m29b();
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.a */
    private static final class C0425a extends aj<DisplayAdController> {
        public C0425a(DisplayAdController displayAdController) {
            super(displayAdController);
        }

        public void run() {
            DisplayAdController displayAdController = (DisplayAdController) m263a();
            if (displayAdController != null) {
                displayAdController.f253l = false;
                displayAdController.m296m();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.b */
    private static final class C0426b extends aj<DisplayAdController> {
        public C0426b(DisplayAdController displayAdController) {
            super(displayAdController);
        }

        public void run() {
            DisplayAdController displayAdController = (DisplayAdController) m263a();
            if (displayAdController != null) {
                displayAdController.m302p();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.DisplayAdController.c */
    private class C0427c extends BroadcastReceiver {
        final /* synthetic */ DisplayAdController f241a;

        private C0427c(DisplayAdController displayAdController) {
            this.f241a = displayAdController;
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                this.f241a.m303q();
            } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                this.f241a.m302p();
            }
        }
    }

    static {
        f242b = DisplayAdController.class.getSimpleName();
        f243h = new Handler(Looper.getMainLooper());
        f244i = false;
    }

    public DisplayAdController(Context context, String str, C0523e c0523e, AdPlacementType adPlacementType, AdSize adSize, C0497c c0497c, int i, boolean z) {
        this.f250g = new Handler();
        this.f246c = context;
        this.f247d = str;
        this.f261t = c0523e;
        this.f248e = adPlacementType;
        this.f263v = adSize;
        this.f262u = c0497c;
        this.f264w = i;
        this.f265x = new C0427c();
        this.f249f = new C0756a(context);
        this.f249f.m1519a((C0428a) this);
        this.f251j = new C0425a(this);
        this.f252k = new C0426b(this);
        this.f254m = z;
        m291j();
        try {
            CookieManager.getInstance();
            if (VERSION.SDK_INT < 21) {
                CookieSyncManager.createInstance(context);
            }
        } catch (Exception e) {
            Log.w(f242b, "Failed to initialize CookieManager.");
        }
    }

    private Map<String, String> m269a(long j) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("delay", String.valueOf(System.currentTimeMillis() - j));
        return hashMap;
    }

    private void m273a(AdAdapter adAdapter) {
        if (adAdapter != null) {
            adAdapter.onDestroy();
        }
    }

    private void m274a(BannerAdapter bannerAdapter, C0515d c0515d, Map<String, Object> map) {
        Runnable c04238 = new C04238(this, bannerAdapter);
        this.f250g.postDelayed(c04238, (long) c0515d.m804a().m818i());
        bannerAdapter.loadBannerAd(this.f246c, this.f263v, new C04249(this, c04238), map);
    }

    private void m275a(InterstitialAdapter interstitialAdapter, C0515d c0515d, Map<String, Object> map) {
        Runnable anonymousClass10 = new AnonymousClass10(this, interstitialAdapter);
        this.f250g.postDelayed(anonymousClass10, (long) c0515d.m804a().m818i());
        interstitialAdapter.loadInterstitialAd(this.f246c, new AnonymousClass11(this, anonymousClass10), map, new C0783h());
    }

    private void m276a(C0458r c0458r, C0515d c0515d, Map<String, Object> map) {
        c0458r.m462a(this.f246c, new C04206(this), map, new C0783h());
    }

    private void m277a(C0440v c0440v, C0515d c0515d, C0511a c0511a, Map<String, Object> map) {
        long currentTimeMillis = System.currentTimeMillis();
        Runnable anonymousClass12 = new AnonymousClass12(this, c0440v, currentTimeMillis, c0511a);
        this.f250g.postDelayed(anonymousClass12, (long) c0515d.m804a().m818i());
        c0440v.m346a(this.f246c, new C04162(this, anonymousClass12, currentTimeMillis, c0511a), map);
    }

    private void m278a(C0470x c0470x, C0515d c0515d, Map<String, Object> map) {
        c0470x.m561a(this.f246c, new C04227(this), map);
    }

    private void m279a(List<String> list, Map<String, String> map) {
        if (list != null && !list.isEmpty()) {
            for (String str : list) {
                new C0807y(map).execute(new String[]{str});
            }
        }
    }

    private void m291j() {
        if (!this.f254m) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.f246c.registerReceiver(this.f265x, intentFilter);
            this.f266y = true;
        }
    }

    private void m293k() {
        if (this.f266y) {
            try {
                this.f246c.unregisterReceiver(this.f265x);
                this.f266y = false;
            } catch (Throwable e) {
                C0778d.m1599a(C0777c.m1596a(e, "Error unregistering screen state receiever"));
            }
        }
    }

    private AdPlacementType m294l() {
        return this.f248e != null ? this.f248e : this.f263v == null ? AdPlacementType.NATIVE : this.f263v == AdSize.INTERSTITIAL ? AdPlacementType.INTERSTITIAL : AdPlacementType.BANNER;
    }

    private void m296m() {
        this.f260s = new C0519f(this.f246c, this.f247d, this.f263v, this.f261t, this.f262u, this.f264w, AdSettings.isTestMode(this.f246c));
        this.f249f.m1518a(this.f260s);
    }

    private synchronized void m299n() {
        f243h.post(new C04195(this));
    }

    private void m301o() {
        this.f256o = null;
        C0515d c0515d = this.f259r;
        C0511a d = c0515d.m808d();
        if (d == null) {
            this.f245a.m28a(AdErrorType.NO_FILL.getAdErrorWrapper(BuildConfig.FLAVOR));
            m302p();
            return;
        }
        String a = d.m797a();
        AdAdapter a2 = C0444d.m423a(a, c0515d.m804a().m810a());
        if (a2 == null) {
            Log.e(f242b, "Adapter does not exist: " + a);
            m299n();
        } else if (m294l() != a2.getPlacementType()) {
            this.f245a.m28a(AdErrorType.INTERNAL_ERROR.getAdErrorWrapper(BuildConfig.FLAVOR));
        } else {
            this.f256o = a2;
            Map hashMap = new HashMap();
            C0516e a3 = c0515d.m804a();
            hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, d.m799b());
            hashMap.put("definition", a3);
            if (this.f260s == null) {
                this.f245a.m28a(AdErrorType.UNKNOWN_ERROR.getAdErrorWrapper("environment is empty"));
                return;
            }
            switch (C04184.f232a[a2.getPlacementType().ordinal()]) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    m275a((InterstitialAdapter) a2, c0515d, hashMap);
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    m274a((BannerAdapter) a2, c0515d, hashMap);
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    m277a((C0440v) a2, c0515d, d, hashMap);
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    m276a((C0458r) a2, c0515d, hashMap);
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    hashMap.put("placement_id", this.f247d);
                    m278a((C0470x) a2, c0515d, hashMap);
                default:
                    Log.e(f242b, "attempt unexpected adapter type");
            }
        }
    }

    private void m302p() {
        if (!this.f254m && !this.f253l) {
            switch (C04184.f232a[m294l().ordinal()]) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    if (!C0794o.m1657a(this.f246c)) {
                        this.f250g.postDelayed(this.f252k, 1000);
                        break;
                    }
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    boolean a = C0749a.m1475a(this.f258q, this.f259r == null ? 1 : this.f259r.m804a().m814e()).m1495a();
                    if (!(this.f258q == null || a)) {
                        this.f250g.postDelayed(this.f252k, 1000);
                        return;
                    }
                default:
                    return;
            }
            long b = this.f259r == null ? 30000 : this.f259r.m804a().m811b();
            if (b > 0) {
                this.f250g.postDelayed(this.f251j, b);
                this.f253l = true;
            }
        }
    }

    private void m303q() {
        if (this.f253l) {
            this.f250g.removeCallbacks(this.f251j);
            this.f253l = false;
        }
    }

    private Handler m304r() {
        return !m305s() ? this.f250g : f243h;
    }

    private static synchronized boolean m305s() {
        boolean z;
        synchronized (DisplayAdController.class) {
            z = f244i;
        }
        return z;
    }

    protected static synchronized void setMainThreadForced(boolean z) {
        synchronized (DisplayAdController.class) {
            Log.d(f242b, "DisplayAdController changed main thread forced from " + f244i + " to " + z);
            f244i = z;
        }
    }

    public C0516e m306a() {
        return this.f259r == null ? null : this.f259r.m804a();
    }

    public void m307a(C0376a c0376a) {
        this.f245a = c0376a;
    }

    public synchronized void m308a(C0488b c0488b) {
        m304r().post(new C04173(this, c0488b));
    }

    public synchronized void m309a(C0761e c0761e) {
        m304r().post(new C04141(this, c0761e));
    }

    public void m310b() {
        m296m();
    }

    public void m311c() {
        if (this.f257p == null) {
            throw new IllegalStateException("no adapter ready to start");
        } else if (this.f255n) {
            throw new IllegalStateException("ad already started");
        } else {
            this.f255n = true;
            switch (C04184.f232a[this.f257p.getPlacementType().ordinal()]) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    ((InterstitialAdapter) this.f257p).show();
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    if (this.f258q != null) {
                        this.f245a.m25a(this.f258q);
                        m302p();
                    }
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    C0440v c0440v = (C0440v) this.f257p;
                    if (c0440v.m350b()) {
                        this.f245a.m27a(c0440v);
                        return;
                    }
                    throw new IllegalStateException("ad is not ready or already displayed");
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    ((C0458r) this.f257p).m463d();
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    ((C0470x) this.f257p).m563b();
                default:
                    Log.e(f242b, "start unexpected adapter type");
            }
        }
    }

    public void m312d() {
        m293k();
        if (this.f255n) {
            m303q();
            m273a(this.f257p);
            this.f258q = null;
            this.f255n = false;
        }
    }

    public void m313e() {
        if (this.f255n) {
            m303q();
        }
    }

    public void m314f() {
        if (this.f255n) {
            m302p();
        }
    }

    public void m315g() {
        m303q();
        m296m();
    }

    public void m316h() {
        this.f254m = true;
        m303q();
    }

    public AdAdapter m317i() {
        return this.f257p;
    }
}
