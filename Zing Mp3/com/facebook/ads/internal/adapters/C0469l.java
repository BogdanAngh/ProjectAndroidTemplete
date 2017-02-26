package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.facebook.ads.AdError;
import com.facebook.ads.AdNetwork;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAd.Image;
import com.facebook.ads.NativeAd.Rating;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.p004a.C0429a;
import com.facebook.ads.internal.p004a.C0430b;
import com.facebook.ads.internal.p005f.C0537f;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0777c.C0775a;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0781f;
import com.facebook.ads.internal.util.C0782g;
import com.facebook.ads.internal.util.C0782g.C0468a;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0807y;
import com.facebook.ads.internal.util.ah;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.l */
public class C0469l extends C0440v implements C0468a {
    private static final String f402a;
    private String f403A;
    private String f404B;
    private ah f405C;
    private String f406D;
    private Image f407E;
    private String f408F;
    private String f409G;
    private NativeAdViewAttributes f410H;
    private List<NativeAd> f411I;
    private int f412J;
    private int f413K;
    private String f414L;
    private boolean f415M;
    private boolean f416N;
    private boolean f417O;
    private boolean f418P;
    private boolean f419Q;
    private long f420R;
    private C0775a f421S;
    private Context f422b;
    private C0415w f423c;
    private Uri f424d;
    private String f425e;
    private String f426f;
    private String f427g;
    private String f428h;
    private String f429i;
    private Image f430j;
    private Image f431k;
    private Rating f432l;
    private String f433m;
    private String f434n;
    private String f435o;
    private String f436p;
    private C0781f f437q;
    private String f438r;
    private Collection<String> f439s;
    private boolean f440t;
    private boolean f441u;
    private boolean f442v;
    private int f443w;
    private int f444x;
    private int f445y;
    private int f446z;

    /* renamed from: com.facebook.ads.internal.adapters.l.1 */
    class C04671 implements Runnable {
        final /* synthetic */ Map f399a;
        final /* synthetic */ Map f400b;
        final /* synthetic */ C0469l f401c;

        C04671(C0469l c0469l, Map map, Map map2) {
            this.f401c = c0469l;
            this.f399a = map;
            this.f400b = map2;
        }

        public void run() {
            if (TextUtils.isEmpty(this.f401c.f414L)) {
                new C0807y(this.f399a, this.f400b).execute(new String[]{this.f401c.f434n});
                return;
            }
            Map hashMap = new HashMap();
            hashMap.putAll(this.f399a);
            hashMap.putAll(this.f400b);
            C0537f.m878a(this.f401c.f422b).m893f(this.f401c.f414L, hashMap);
        }
    }

    static {
        f402a = C0469l.class.getSimpleName();
    }

    public C0469l() {
        this.f420R = 0;
        this.f421S = null;
    }

    private boolean m514G() {
        return this.f425e != null && this.f425e.length() > 0 && this.f428h != null && this.f428h.length() > 0 && ((this.f430j != null || this.f415M) && this.f431k != null);
    }

    private void m515H() {
        if (!this.f419Q) {
            new C0807y().execute(new String[]{this.f436p});
            this.f419Q = true;
        }
    }

    private void m517a(Context context, JSONObject jSONObject, String str, int i, int i2) {
        this.f415M = true;
        this.f422b = context;
        this.f412J = i;
        this.f413K = i2;
        m519a(jSONObject, str);
    }

    private void m518a(Map<String, String> map, Map<String, String> map2) {
        try {
            new Handler().postDelayed(new C04671(this, map2, m522c((Map) map)), (long) (this.f443w * AdError.NETWORK_ERROR_CODE));
        } catch (Exception e) {
        }
    }

    private void m519a(JSONObject jSONObject, String str) {
        JSONArray jSONArray = null;
        int i = 0;
        if (this.f416N) {
            throw new IllegalStateException("Adapter already loaded data");
        } else if (jSONObject != null) {
            C0785i.m1628a(this.f422b, "Audience Network Loaded");
            this.f414L = str;
            this.f424d = Uri.parse(C0785i.m1621a(jSONObject, "fbad_command"));
            this.f425e = C0785i.m1621a(jSONObject, ShareConstants.WEB_DIALOG_PARAM_TITLE);
            this.f426f = C0785i.m1621a(jSONObject, "subtitle");
            this.f427g = C0785i.m1621a(jSONObject, TtmlNode.TAG_BODY);
            this.f428h = C0785i.m1621a(jSONObject, "call_to_action");
            this.f429i = C0785i.m1621a(jSONObject, "social_context");
            this.f430j = Image.fromJSONObject(jSONObject.optJSONObject("icon"));
            this.f431k = Image.fromJSONObject(jSONObject.optJSONObject("image"));
            this.f432l = Rating.fromJSONObject(jSONObject.optJSONObject("star_rating"));
            this.f433m = C0785i.m1621a(jSONObject, "impression_report_url");
            this.f434n = C0785i.m1621a(jSONObject, "native_view_report_url");
            this.f435o = C0785i.m1621a(jSONObject, "click_report_url");
            this.f436p = C0785i.m1621a(jSONObject, "used_report_url");
            this.f440t = jSONObject.optBoolean("manual_imp");
            this.f441u = jSONObject.optBoolean("enable_view_log");
            this.f442v = jSONObject.optBoolean("enable_snapshot_log");
            this.f443w = jSONObject.optInt("snapshot_log_delay_second", 4);
            this.f444x = jSONObject.optInt("snapshot_compress_quality", 0);
            this.f445y = jSONObject.optInt("viewability_check_initial_delay", 0);
            this.f446z = jSONObject.optInt("viewability_check_interval", AdError.NETWORK_ERROR_CODE);
            JSONObject optJSONObject = jSONObject.optJSONObject("ad_choices_icon");
            JSONObject optJSONObject2 = jSONObject.optJSONObject("native_ui_config");
            this.f410H = optJSONObject2 == null ? null : new NativeAdViewAttributes(optJSONObject2);
            if (optJSONObject != null) {
                this.f407E = Image.fromJSONObject(optJSONObject);
            }
            this.f408F = C0785i.m1621a(jSONObject, "ad_choices_link_url");
            this.f409G = C0785i.m1621a(jSONObject, "request_id");
            this.f437q = C0781f.m1607a(jSONObject.optString("invalidation_behavior"));
            this.f438r = C0785i.m1621a(jSONObject, "invalidation_report_url");
            try {
                jSONArray = new JSONArray(jSONObject.optString("detection_strings"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.f439s = C0782g.m1608a(jSONArray);
            this.f403A = C0785i.m1621a(jSONObject, "video_url");
            this.f404B = C0785i.m1621a(jSONObject, "video_mpd");
            if (jSONObject.has("video_autoplay_enabled")) {
                this.f405C = jSONObject.optBoolean("video_autoplay_enabled") ? ah.ON : ah.OFF;
            } else {
                this.f405C = ah.UNKNOWN;
            }
            this.f406D = C0785i.m1621a(jSONObject, "video_report_url");
            try {
                JSONArray optJSONArray = jSONObject.optJSONArray("carousel");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    int length = optJSONArray.length();
                    List arrayList = new ArrayList(length);
                    while (i < length) {
                        C0440v c0469l = new C0469l();
                        c0469l.m517a(this.f422b, optJSONArray.getJSONObject(i), str, i, length);
                        arrayList.add(new NativeAd(this.f422b, c0469l, null));
                        i++;
                    }
                    this.f411I = arrayList;
                }
            } catch (Throwable e2) {
                Log.e(f402a, "Unable to parse carousel data.", e2);
            }
            this.f416N = true;
            this.f417O = m514G();
        }
    }

    private Map<String, String> m522c(Map<String, String> map) {
        Map<String, String> hashMap = new HashMap();
        if (map.containsKey(Promotion.ACTION_VIEW)) {
            hashMap.put(Promotion.ACTION_VIEW, map.get(Promotion.ACTION_VIEW));
        }
        if (map.containsKey("snapshot")) {
            hashMap.put("snapshot", map.get("snapshot"));
        }
        return hashMap;
    }

    public List<NativeAd> m523A() {
        return !m535b() ? null : this.f411I;
    }

    public String m524B() {
        return this.f414L;
    }

    public AdNetwork m525C() {
        return AdNetwork.AN;
    }

    public C0781f m526D() {
        return this.f437q;
    }

    public String m527E() {
        return this.f438r;
    }

    public Collection<String> m528F() {
        return this.f439s;
    }

    public void m529a() {
    }

    public void m530a(int i) {
        if (m535b() && i == 0 && this.f420R > 0 && this.f421S != null) {
            C0778d.m1599a(C0777c.m1594a(this.f420R, this.f421S, this.f409G));
            this.f420R = 0;
            this.f421S = null;
        }
    }

    public void m531a(Context context, C0415w c0415w, Map<String, Object> map) {
        this.f422b = context;
        this.f423c = c0415w;
        JSONObject jSONObject = (JSONObject) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA);
        m519a(jSONObject, C0785i.m1621a(jSONObject, "ct"));
        if (C0782g.m1609a(context, (C0468a) this)) {
            c0415w.m234a(this, AdError.NO_FILL);
            return;
        }
        if (c0415w != null) {
            c0415w.m233a(this);
        }
        C0777c.f1410a = this.f409G;
    }

    public void m532a(View view, List<View> list) {
    }

    public void m533a(Map<String, String> map) {
        if (m535b() && !this.f418P) {
            if (this.f423c != null) {
                this.f423c.m235b(this);
            }
            Map hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            if (TextUtils.isEmpty(m524B())) {
                new C0807y(hashMap).execute(new String[]{this.f433m});
            } else {
                if (this.f415M) {
                    hashMap.put("cardind", String.valueOf(this.f412J));
                    hashMap.put("cardcnt", String.valueOf(this.f413K));
                }
                C0537f.m878a(this.f422b).m885a(m524B(), hashMap);
            }
            if (m538e() || m537d()) {
                m518a((Map) map, hashMap);
            }
            this.f418P = true;
        }
    }

    public void m534b(Map<String, String> map) {
        if (m535b()) {
            Map hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            if (TextUtils.isEmpty(this.f414L)) {
                new C0807y(hashMap).execute(new String[]{this.f435o});
            }
            C0785i.m1628a(this.f422b, "Click logged");
            if (this.f423c != null) {
                this.f423c.m236c(this);
            }
            if (this.f415M) {
                hashMap.put("cardind", String.valueOf(this.f412J));
                hashMap.put("cardcnt", String.valueOf(this.f413K));
            }
            C0429a a = C0430b.m321a(this.f422b, this.f414L, this.f424d, hashMap);
            if (a != null) {
                try {
                    this.f420R = System.currentTimeMillis();
                    this.f421S = a.m318a();
                    a.m320b();
                } catch (Throwable e) {
                    Log.e(f402a, "Error executing action", e);
                }
            }
        }
    }

    public boolean m535b() {
        return this.f416N && this.f417O;
    }

    public boolean m536c() {
        return m535b() && this.f440t;
    }

    public boolean m537d() {
        return m535b() && this.f442v;
    }

    public boolean m538e() {
        return m535b() && this.f441u;
    }

    public boolean m539f() {
        return m535b() && this.f410H != null;
    }

    public boolean m540g() {
        return true;
    }

    public int m541h() {
        return (this.f444x < 0 || this.f444x > 100) ? 0 : this.f444x;
    }

    public int m542i() {
        return this.f445y;
    }

    public int m543j() {
        return this.f446z;
    }

    public Image m544k() {
        return !m535b() ? null : this.f430j;
    }

    public Image m545l() {
        return !m535b() ? null : this.f431k;
    }

    public NativeAdViewAttributes m546m() {
        return !m535b() ? null : this.f410H;
    }

    public String m547n() {
        if (!m535b()) {
            return null;
        }
        m515H();
        return this.f425e;
    }

    public String m548o() {
        if (!m535b()) {
            return null;
        }
        m515H();
        return this.f426f;
    }

    public void onDestroy() {
    }

    public String m549p() {
        if (!m535b()) {
            return null;
        }
        m515H();
        return this.f427g;
    }

    public String m550q() {
        if (!m535b()) {
            return null;
        }
        m515H();
        return this.f428h;
    }

    public String m551r() {
        if (!m535b()) {
            return null;
        }
        m515H();
        return this.f429i;
    }

    public Rating m552s() {
        if (!m535b()) {
            return null;
        }
        m515H();
        return this.f432l;
    }

    public Image m553t() {
        return !m535b() ? null : this.f407E;
    }

    public String m554u() {
        return !m535b() ? null : this.f408F;
    }

    public String m555v() {
        return !m535b() ? null : "AdChoices";
    }

    public String m556w() {
        return !m535b() ? null : this.f403A;
    }

    public String m557x() {
        return !m535b() ? null : this.f404B;
    }

    public ah m558y() {
        return !m535b() ? ah.UNKNOWN : this.f405C;
    }

    public String m559z() {
        return this.f406D;
    }
}
