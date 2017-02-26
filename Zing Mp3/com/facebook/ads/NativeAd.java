package com.facebook.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.facebook.ads.NativeAdView.Type;
import com.facebook.ads.internal.C0376a;
import com.facebook.ads.internal.C0488b;
import com.facebook.ads.internal.C0497c;
import com.facebook.ads.internal.C0523e;
import com.facebook.ads.internal.C0604h;
import com.facebook.ads.internal.DisplayAdController;
import com.facebook.ads.internal.adapters.AdAdapter;
import com.facebook.ads.internal.adapters.C0400b;
import com.facebook.ads.internal.adapters.C0440v;
import com.facebook.ads.internal.adapters.C0480u;
import com.facebook.ads.internal.p000i.C0396r;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.C0746s;
import com.facebook.ads.internal.p000i.p016c.C0634c;
import com.facebook.ads.internal.p002c.C0393a;
import com.facebook.ads.internal.p002c.C0493b;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.ads.internal.p003j.C0749a.C0398a;
import com.facebook.ads.internal.p008e.C0516e;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0777c.C0776b;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0795p;
import com.facebook.ads.internal.util.ae;
import com.facebook.ads.internal.util.ah;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import org.json.JSONObject;

public class NativeAd implements Ad {
    private static final C0497c f132a;
    private static final String f133b;
    private static WeakHashMap<View, WeakReference<NativeAd>> f134c;
    private long f135A;
    private String f136B;
    private boolean f137C;
    private final Context f138d;
    private final String f139e;
    private final String f140f;
    private final C0493b f141g;
    private AdListener f142h;
    private ImpressionListener f143i;
    private DisplayAdController f144j;
    private volatile boolean f145k;
    private C0440v f146l;
    private C0516e f147m;
    private View f148n;
    private List<View> f149o;
    private OnTouchListener f150p;
    private C0749a f151q;
    private ae f152r;
    private C0480u f153s;
    private C0404a f154t;
    private C0405b f155u;
    private C0746s f156v;
    private Type f157w;
    private boolean f158x;
    private boolean f159y;
    @Deprecated
    private boolean f160z;

    /* renamed from: com.facebook.ads.NativeAd.1 */
    class C03951 extends C0376a {
        final /* synthetic */ EnumSet f107a;
        final /* synthetic */ NativeAd f108b;

        /* renamed from: com.facebook.ads.NativeAd.1.1 */
        class C03941 implements C0393a {
            final /* synthetic */ C0440v f105a;
            final /* synthetic */ C03951 f106b;

            C03941(C03951 c03951, C0440v c0440v) {
                this.f106b = c03951;
                this.f105a = c0440v;
            }

            public void m133a() {
                this.f106b.f108b.f146l = this.f105a;
                this.f106b.f108b.m180n();
                this.f106b.f108b.m182o();
                if (this.f106b.f108b.f142h != null) {
                    this.f106b.f108b.f142h.onAdLoaded(this.f106b.f108b);
                }
            }
        }

        C03951(NativeAd nativeAd, EnumSet enumSet) {
            this.f108b = nativeAd;
            this.f107a = enumSet;
        }

        public void m134a() {
            if (this.f108b.f142h != null) {
                this.f108b.f142h.onAdClicked(this.f108b);
            }
        }

        public void m135a(AdAdapter adAdapter) {
            if (this.f108b.f144j != null) {
                this.f108b.f144j.m311c();
            }
        }

        public void m136a(C0440v c0440v) {
            C0778d.m1599a(C0777c.m1595a(C0776b.LOADING_AD, AdPlacementType.NATIVE, System.currentTimeMillis() - this.f108b.f135A, null));
            if (c0440v != null) {
                if (this.f107a.contains(MediaCacheFlag.ICON) && c0440v.m359k() != null) {
                    this.f108b.f141g.m738a(c0440v.m359k().getUrl());
                }
                if (this.f107a.contains(MediaCacheFlag.IMAGE)) {
                    if (c0440v.m360l() != null) {
                        this.f108b.f141g.m738a(c0440v.m360l().getUrl());
                    }
                    if (c0440v.m341A() != null) {
                        for (NativeAd nativeAd : c0440v.m341A()) {
                            if (nativeAd.getAdCoverImage() != null) {
                                this.f108b.f141g.m738a(nativeAd.getAdCoverImage().getUrl());
                            }
                        }
                    }
                }
                if (this.f107a.contains(MediaCacheFlag.VIDEO) && !TextUtils.isEmpty(c0440v.m371w())) {
                    this.f108b.f141g.m739b(c0440v.m371w());
                }
                this.f108b.f141g.m737a(new C03941(this, c0440v));
                if (this.f108b.f142h != null && c0440v.m341A() != null) {
                    for (NativeAd nativeAd2 : c0440v.m341A()) {
                        nativeAd2.setAdListener(this.f108b.f142h);
                    }
                }
            }
        }

        public void m137a(C0488b c0488b) {
            if (this.f108b.f142h != null) {
                this.f108b.f142h.onError(this.f108b, c0488b.m729b());
            }
        }

        public void m138b() {
            throw new IllegalStateException("Native ads manager their own impressions.");
        }
    }

    /* renamed from: com.facebook.ads.NativeAd.2 */
    class C03972 implements C0396r {
        final /* synthetic */ NativeAd f109a;

        C03972(NativeAd nativeAd) {
            this.f109a = nativeAd;
        }

        public void m140a(int i) {
            if (this.f109a.f146l != null) {
                this.f109a.f146l.m345a(i);
            }
        }
    }

    /* renamed from: com.facebook.ads.NativeAd.3 */
    class C03993 extends C0398a {
        final /* synthetic */ NativeAd f110a;

        C03993(NativeAd nativeAd) {
            this.f110a = nativeAd;
        }

        public void m143a() {
            this.f110a.f152r.m1582a();
            this.f110a.f151q.m1493b();
            if (this.f110a.f153s != null) {
                this.f110a.f153s.m691a(this.f110a.f148n);
                this.f110a.f153s.m692a(this.f110a.f157w);
                this.f110a.f153s.m695a(this.f110a.f158x);
                this.f110a.f153s.m696b(this.f110a.f159y);
                this.f110a.f153s.m697c(this.f110a.m177l());
                this.f110a.f153s.m337a();
            } else if (this.f110a.f151q != null) {
                this.f110a.f151q.m1493b();
                this.f110a.f151q = null;
            }
        }
    }

    /* renamed from: com.facebook.ads.NativeAd.4 */
    class C04014 extends C0400b {
        final /* synthetic */ NativeAd f111a;

        C04014(NativeAd nativeAd) {
            this.f111a = nativeAd;
        }

        public boolean m149a() {
            return true;
        }
    }

    /* renamed from: com.facebook.ads.NativeAd.c */
    private class C0402c extends C0400b {
        final /* synthetic */ NativeAd f112b;

        private C0402c(NativeAd nativeAd) {
            this.f112b = nativeAd;
        }

        public boolean m150a() {
            return false;
        }

        public void m151d() {
            if (this.f112b.f143i != null) {
                this.f112b.f143i.onLoggingImpression(this.f112b);
            }
            if ((this.f112b.f142h instanceof ImpressionListener) && this.f112b.f142h != this.f112b.f143i) {
                ((ImpressionListener) this.f112b.f142h).onLoggingImpression(this.f112b);
            }
        }

        public void m152e() {
        }
    }

    /* renamed from: com.facebook.ads.NativeAd.5 */
    class C04035 extends C0402c {
        final /* synthetic */ NativeAd f113a;

        C04035(NativeAd nativeAd) {
            this.f113a = nativeAd;
            super(null);
        }

        public boolean m153b() {
            return true;
        }

        public String m154c() {
            return this.f113a.f136B;
        }
    }

    public static class Image {
        private final String f114a;
        private final int f115b;
        private final int f116c;

        public Image(String str, int i, int i2) {
            this.f114a = str;
            this.f115b = i;
            this.f116c = i2;
        }

        public static Image fromJSONObject(JSONObject jSONObject) {
            if (jSONObject == null) {
                return null;
            }
            String optString = jSONObject.optString(NativeProtocol.WEB_DIALOG_URL);
            return optString != null ? new Image(optString, jSONObject.optInt("width", 0), jSONObject.optInt("height", 0)) : null;
        }

        public int getHeight() {
            return this.f116c;
        }

        public String getUrl() {
            return this.f114a;
        }

        public int getWidth() {
            return this.f115b;
        }
    }

    public enum MediaCacheFlag {
        NONE(0),
        ICON(1),
        IMAGE(2),
        VIDEO(3);
        
        public static final EnumSet<MediaCacheFlag> ALL;
        private final long f118a;

        static {
            ALL = EnumSet.allOf(MediaCacheFlag.class);
        }

        private MediaCacheFlag(long j) {
            this.f118a = j;
        }

        public long getCacheFlagValue() {
            return this.f118a;
        }
    }

    public static class Rating {
        private final double f119a;
        private final double f120b;

        public Rating(double d, double d2) {
            this.f119a = d;
            this.f120b = d2;
        }

        public static Rating fromJSONObject(JSONObject jSONObject) {
            if (jSONObject == null) {
                return null;
            }
            double optDouble = jSONObject.optDouble("value", 0.0d);
            double optDouble2 = jSONObject.optDouble("scale", 0.0d);
            return (optDouble == 0.0d || optDouble2 == 0.0d) ? null : new Rating(optDouble, optDouble2);
        }

        public double getScale() {
            return this.f120b;
        }

        public double getValue() {
            return this.f119a;
        }
    }

    /* renamed from: com.facebook.ads.NativeAd.a */
    private class C0404a implements OnClickListener, OnTouchListener {
        final /* synthetic */ NativeAd f121a;
        private int f122b;
        private int f123c;
        private int f124d;
        private int f125e;
        private float f126f;
        private float f127g;
        private int f128h;
        private int f129i;

        private C0404a(NativeAd nativeAd) {
            this.f121a = nativeAd;
        }

        private Map<String, String> m155a() {
            Map<String, String> hashMap = new HashMap();
            hashMap.put("clickX", String.valueOf(this.f122b));
            hashMap.put("clickY", String.valueOf(this.f123c));
            hashMap.put("width", String.valueOf(this.f124d));
            hashMap.put("height", String.valueOf(this.f125e));
            hashMap.put("adPositionX", String.valueOf(this.f126f));
            hashMap.put("adPositionY", String.valueOf(this.f127g));
            hashMap.put("visibleWidth", String.valueOf(this.f129i));
            hashMap.put("visibleHeight", String.valueOf(this.f128h));
            return hashMap;
        }

        public void onClick(View view) {
            if (!this.f121a.f152r.m1587d()) {
                Log.e("FBAudienceNetworkLog", "No touch data recorded, please ensure touch events reach the ad View by returning false if you intercept the event.");
            }
            int e = C0604h.m1124e(this.f121a.f138d);
            if (e < 0 || this.f121a.f152r.m1586c() >= ((long) e)) {
                if ((view instanceof AdChoicesView) || !this.f121a.f152r.m1584a(C0604h.m1125f(this.f121a.f138d))) {
                    Map a = TextUtils.isEmpty(this.f121a.m199h()) ? m155a() : this.f121a.f152r.m1588e();
                    Map hashMap = new HashMap();
                    hashMap.put(ServerProtocol.FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH, C0785i.m1620a(a));
                    if (this.f121a.f157w != null) {
                        hashMap.put("nti", String.valueOf(this.f121a.f157w.getValue()));
                    }
                    if (this.f121a.f158x) {
                        hashMap.put("nhs", String.valueOf(this.f121a.f158x));
                    }
                    this.f121a.f151q.m1492a(hashMap);
                    this.f121a.f146l.m349b(hashMap);
                    return;
                }
                Log.e("FBAudienceNetworkLog", "Clicks are too close to the border of the view.");
            } else if (this.f121a.f152r.m1585b()) {
                Log.e("FBAudienceNetworkLog", "Clicks happened too fast.");
            } else {
                Log.e("FBAudienceNetworkLog", "Ad cannot be clicked before it is viewed.");
            }
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            this.f121a.f152r.m1583a(motionEvent, this.f121a.f148n, view);
            if (motionEvent.getAction() == 0 && this.f121a.f148n != null && TextUtils.isEmpty(this.f121a.m199h())) {
                this.f124d = this.f121a.f148n.getWidth();
                this.f125e = this.f121a.f148n.getHeight();
                int[] iArr = new int[2];
                this.f121a.f148n.getLocationInWindow(iArr);
                this.f126f = (float) iArr[0];
                this.f127g = (float) iArr[1];
                Rect rect = new Rect();
                this.f121a.f148n.getGlobalVisibleRect(rect);
                this.f129i = rect.width();
                this.f128h = rect.height();
                int[] iArr2 = new int[2];
                view.getLocationInWindow(iArr2);
                this.f122b = (((int) motionEvent.getX()) + iArr2[0]) - iArr[0];
                this.f123c = (iArr2[1] + ((int) motionEvent.getY())) - iArr[1];
            }
            return this.f121a.f150p != null && this.f121a.f150p.onTouch(view, motionEvent);
        }
    }

    /* renamed from: com.facebook.ads.NativeAd.b */
    private class C0405b extends BroadcastReceiver {
        final /* synthetic */ NativeAd f130a;
        private boolean f131b;

        private C0405b(NativeAd nativeAd) {
            this.f130a = nativeAd;
        }

        public void m156a() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.facebook.ads.native.impression:" + this.f130a.f140f);
            intentFilter.addAction("com.facebook.ads.native.click:" + this.f130a.f140f);
            LocalBroadcastManager.getInstance(this.f130a.f138d).registerReceiver(this, intentFilter);
            this.f131b = true;
        }

        public void m157b() {
            if (this.f131b) {
                try {
                    LocalBroadcastManager.getInstance(this.f130a.f138d).unregisterReceiver(this);
                } catch (Exception e) {
                }
            }
        }

        public void onReceive(Context context, Intent intent) {
            Object obj = intent.getAction().split(":")[0];
            if ("com.facebook.ads.native.impression".equals(obj)) {
                this.f130a.f153s.m337a();
            } else if ("com.facebook.ads.native.click".equals(obj)) {
                Map hashMap = new HashMap();
                hashMap.put("mil", String.valueOf(true));
                this.f130a.f146l.m349b(hashMap);
            }
        }
    }

    static {
        f132a = C0497c.ADS;
        f133b = NativeAd.class.getSimpleName();
        f134c = new WeakHashMap();
    }

    public NativeAd(Context context, C0440v c0440v, C0516e c0516e) {
        this(context, null);
        this.f147m = c0516e;
        this.f145k = true;
        this.f146l = c0440v;
    }

    public NativeAd(Context context, String str) {
        this.f140f = UUID.randomUUID().toString();
        this.f149o = new ArrayList();
        this.f152r = new ae();
        this.f137C = false;
        this.f138d = context;
        this.f139e = str;
        this.f141g = new C0493b(context);
    }

    NativeAd(NativeAd nativeAd) {
        this(nativeAd.f138d, null);
        this.f147m = nativeAd.f147m;
        this.f145k = true;
        this.f146l = nativeAd.f146l;
    }

    private void m161a(View view) {
        this.f149o.add(view);
        view.setOnClickListener(this.f154t);
        view.setOnTouchListener(this.f154t);
    }

    private void m162a(List<View> list, View view) {
        if (!(view instanceof C0726m) && !(view instanceof AdChoicesView) && !(view instanceof C0634c)) {
            list.add(view);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    m162a((List) list, viewGroup.getChildAt(i));
                }
            }
        }
    }

    public static void downloadAndDisplayImage(Image image, ImageView imageView) {
        if (image != null && imageView != null) {
            new C0795p(imageView).m1663a(image.getUrl());
        }
    }

    private int getMinViewabilityPercentage() {
        return this.f147m != null ? this.f147m.m814e() : (this.f144j == null || this.f144j.m306a() == null) ? 1 : this.f144j.m306a().m814e();
    }

    private int m170i() {
        return this.f147m != null ? this.f147m.m815f() : (this.f144j == null || this.f144j.m306a() == null) ? 0 : this.f144j.m306a().m815f();
    }

    private int m172j() {
        return this.f147m != null ? this.f147m.m816g() : this.f146l != null ? this.f146l.m357i() : (this.f144j == null || this.f144j.m306a() == null) ? 0 : this.f144j.m306a().m816g();
    }

    private int m174k() {
        return this.f147m != null ? this.f147m.m817h() : this.f146l != null ? this.f146l.m358j() : (this.f144j == null || this.f144j.m306a() == null) ? AdError.NETWORK_ERROR_CODE : this.f144j.m306a().m817h();
    }

    private boolean m177l() {
        return m197f() == ah.UNKNOWN ? this.f160z : m197f() == ah.ON;
    }

    private void logExternalClick(String str) {
        Map hashMap = new HashMap();
        hashMap.put("eil", String.valueOf(true));
        hashMap.put("eil_source", str);
        this.f146l.m349b(hashMap);
    }

    private void logExternalImpression() {
        this.f153s.m337a();
    }

    private void m178m() {
        for (View view : this.f149o) {
            view.setOnClickListener(null);
            view.setOnTouchListener(null);
        }
        this.f149o.clear();
    }

    private void m180n() {
        if (this.f146l != null && this.f146l.m351c()) {
            this.f155u = new C0405b();
            this.f155u.m156a();
            this.f153s = new C0480u(this.f138d, new C04014(this), this.f151q, this.f146l);
        }
    }

    private void m182o() {
        if (this.f137C) {
            this.f153s = new C0480u(this.f138d, new C04035(this), this.f151q, this.f146l);
        }
    }

    private void registerExternalLogReceiver(String str) {
        this.f137C = true;
        this.f136B = str;
    }

    C0440v m189a() {
        return this.f146l;
    }

    void m190a(Type type) {
        this.f157w = type;
    }

    void m191a(boolean z) {
        this.f158x = z;
    }

    String m192b() {
        return !isAdLoaded() ? null : this.f146l.m370v();
    }

    void m193b(boolean z) {
        this.f159y = z;
    }

    String m194c() {
        return (!isAdLoaded() || TextUtils.isEmpty(this.f146l.m371w())) ? null : this.f141g.m740c(this.f146l.m371w());
    }

    String m195d() {
        return !isAdLoaded() ? null : this.f146l.m372x();
    }

    public void destroy() {
        if (this.f155u != null) {
            this.f155u.m157b();
            this.f155u = null;
        }
        if (this.f144j != null) {
            this.f144j.m312d();
            this.f144j = null;
        }
    }

    String m196e() {
        return !isAdLoaded() ? null : this.f146l.m374z();
    }

    ah m197f() {
        return !isAdLoaded() ? ah.UNKNOWN : this.f146l.m373y();
    }

    List<NativeAd> m198g() {
        return !isAdLoaded() ? null : this.f146l.m341A();
    }

    public String getAdBody() {
        return !isAdLoaded() ? null : this.f146l.m364p();
    }

    public String getAdCallToAction() {
        return !isAdLoaded() ? null : this.f146l.m365q();
    }

    public Image getAdChoicesIcon() {
        return !isAdLoaded() ? null : this.f146l.m368t();
    }

    public String getAdChoicesLinkUrl() {
        return !isAdLoaded() ? null : this.f146l.m369u();
    }

    public Image getAdCoverImage() {
        return !isAdLoaded() ? null : this.f146l.m360l();
    }

    public Image getAdIcon() {
        return !isAdLoaded() ? null : this.f146l.m359k();
    }

    @Nullable
    public AdNetwork getAdNetwork() {
        return (!isAdLoaded() || this.f146l == null) ? null : this.f146l.m343C();
    }

    public String getAdSocialContext() {
        return !isAdLoaded() ? null : this.f146l.m366r();
    }

    @Deprecated
    public Rating getAdStarRating() {
        return !isAdLoaded() ? null : this.f146l.m367s();
    }

    public String getAdSubtitle() {
        return !isAdLoaded() ? null : this.f146l.m363o();
    }

    public String getAdTitle() {
        return !isAdLoaded() ? null : this.f146l.m362n();
    }

    public NativeAdViewAttributes getAdViewAttributes() {
        return !isAdLoaded() ? null : this.f146l.m361m();
    }

    public String getId() {
        return !isAdLoaded() ? null : this.f140f;
    }

    public String getPlacementId() {
        return this.f139e;
    }

    String m199h() {
        return !isAdLoaded() ? null : this.f146l.m342B();
    }

    public boolean isAdLoaded() {
        return this.f146l != null && this.f146l.m350b();
    }

    public boolean isNativeConfigEnabled() {
        return isAdLoaded() && this.f146l.m354f();
    }

    public void loadAd() {
        loadAd(EnumSet.of(MediaCacheFlag.NONE));
    }

    public void loadAd(EnumSet<MediaCacheFlag> enumSet) {
        if (this.f145k) {
            throw new IllegalStateException("loadAd cannot be called more than once");
        }
        this.f135A = System.currentTimeMillis();
        this.f145k = true;
        this.f144j = new DisplayAdController(this.f138d, this.f139e, C0523e.NATIVE_UNKNOWN, AdPlacementType.NATIVE, null, f132a, 1, true);
        this.f144j.m307a(new C03951(this, enumSet));
        this.f144j.m310b();
    }

    public void registerViewForInteraction(View view) {
        List arrayList = new ArrayList();
        m162a(arrayList, view);
        registerViewForInteraction(view, arrayList);
    }

    public void registerViewForInteraction(View view, List<View> list) {
        if (view == null) {
            throw new IllegalArgumentException("Must provide a View");
        } else if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("Invalid set of clickable views");
        } else if (isAdLoaded()) {
            if (this.f148n != null) {
                Log.w(f133b, "Native Ad was already registered with a View. Auto unregistering and proceeding.");
                unregisterView();
            }
            if (f134c.containsKey(view)) {
                Log.w(f133b, "View already registered with a NativeAd. Auto unregistering and proceeding.");
                ((NativeAd) ((WeakReference) f134c.get(view)).get()).unregisterView();
            }
            this.f154t = new C0404a();
            this.f148n = view;
            if (view instanceof ViewGroup) {
                this.f156v = new C0746s(view.getContext(), new C03972(this));
                ((ViewGroup) view).addView(this.f156v);
            }
            for (View a : list) {
                m161a(a);
            }
            this.f146l.m347a(view, list);
            this.f151q = new C0749a(this.f148n, getMinViewabilityPercentage(), m170i(), true, new C03993(this));
            this.f151q.m1491a(m172j());
            this.f151q.m1494b(m174k());
            this.f151q.m1490a();
            this.f153s = new C0480u(this.f138d, new C0402c(), this.f151q, this.f146l);
            this.f153s.m693a((List) list);
            f134c.put(view, new WeakReference(this));
        } else {
            Log.e(f133b, "Ad not loaded");
        }
    }

    public void setAdListener(AdListener adListener) {
        this.f142h = adListener;
    }

    @Deprecated
    public void setImpressionListener(ImpressionListener impressionListener) {
        this.f143i = impressionListener;
    }

    @Deprecated
    public void setMediaViewAutoplay(boolean z) {
        this.f160z = z;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.f150p = onTouchListener;
    }

    public void unregisterView() {
        if (this.f148n != null) {
            if (f134c.containsKey(this.f148n) && ((WeakReference) f134c.get(this.f148n)).get() == this) {
                if ((this.f148n instanceof ViewGroup) && this.f156v != null) {
                    ((ViewGroup) this.f148n).removeView(this.f156v);
                    this.f156v = null;
                }
                if (this.f146l != null) {
                    this.f146l.m344a();
                }
                f134c.remove(this.f148n);
                m178m();
                this.f148n = null;
                if (this.f151q != null) {
                    this.f151q.m1493b();
                    this.f151q = null;
                }
                this.f153s = null;
                return;
            }
            throw new IllegalStateException("View not registered with this NativeAd");
        }
    }
}
