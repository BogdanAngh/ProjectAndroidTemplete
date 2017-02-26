package com.facebook.ads.internal.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.AudienceNetworkActivity.Type;
import com.facebook.ads.InterstitialAdActivity;
import com.facebook.ads.internal.p000i.C0465d;
import com.facebook.ads.internal.util.C0782g;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.p001a.C0390a;
import com.facebook.share.internal.ShareConstants;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.j */
public class C0462j extends InterstitialAdapter {
    private static final ConcurrentMap<String, C0465d> f378a;
    private final String f379b;
    private Context f380c;
    private C0479s f381d;
    private InterstitialAdapterListener f382e;
    private boolean f383f;
    private C0474o f384g;
    private C0461a f385h;

    /* renamed from: com.facebook.ads.internal.adapters.j.1 */
    class C04601 implements C0390a {
        final /* synthetic */ C0466k f372a;
        final /* synthetic */ C0462j f373b;

        C04601(C0462j c0462j, C0466k c0466k) {
            this.f373b = c0462j;
            this.f372a = c0466k;
        }

        public void m475a(C0458r c0458r) {
            this.f373b.f383f = true;
            if (this.f373b.f382e != null) {
                this.f373b.f382e.onInterstitialAdLoaded(this.f373b);
            }
        }

        public void m476a(C0458r c0458r, View view) {
            this.f373b.f385h = this.f372a.m509h();
            C0462j.f378a.put(this.f373b.f379b, this.f372a);
        }

        public void m477a(C0458r c0458r, AdError adError) {
            this.f373b.f382e.onInterstitialError(this.f373b, adError);
        }

        public void m478b(C0458r c0458r) {
            this.f373b.f382e.onInterstitialAdClicked(this.f373b, BuildConfig.FLAVOR, true);
        }

        public void m479c(C0458r c0458r) {
            this.f373b.f382e.onInterstitialLoggingImpression(this.f373b);
            this.f373b.f382e.onInterstitialAdDisplayed(this.f373b);
        }

        public void m480d(C0458r c0458r) {
            C0462j.f378a.remove(this.f373b.f379b);
            this.f373b.f382e.onInterstitialAdDismissed(this.f373b);
        }
    }

    /* renamed from: com.facebook.ads.internal.adapters.j.a */
    public enum C0461a {
        UNSPECIFIED,
        VERTICAL,
        HORIZONTAL;

        public static C0461a m481a(int i) {
            return i == 2 ? HORIZONTAL : VERTICAL;
        }
    }

    static {
        f378a = new ConcurrentHashMap();
    }

    public C0462j() {
        this.f379b = UUID.randomUUID().toString();
        this.f383f = false;
    }

    public static C0465d m484a(String str) {
        return (C0465d) f378a.get(str);
    }

    private int m487b() {
        int rotation = ((WindowManager) this.f380c.getSystemService("window")).getDefaultDisplay().getRotation();
        if (this.f385h == C0461a.UNSPECIFIED) {
            return -1;
        }
        if (this.f385h == C0461a.HORIZONTAL) {
            switch (rotation) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    return 8;
                default:
                    return 0;
            }
        }
        switch (rotation) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return 9;
            default:
                return 1;
        }
    }

    public void loadInterstitialAd(@NonNull Context context, @NonNull InterstitialAdapterListener interstitialAdapterListener, @NonNull Map<String, Object> map, @NonNull C0783h c0783h) {
        this.f380c = context;
        this.f382e = interstitialAdapterListener;
        JSONObject jSONObject = (JSONObject) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA);
        if (jSONObject.has("markup")) {
            this.f384g = C0474o.m617a(jSONObject);
            if (C0782g.m1609a(context, this.f384g)) {
                interstitialAdapterListener.onInterstitialError(this, AdError.NO_FILL);
                return;
            }
            this.f381d = new C0479s(context, this.f379b, this, this.f382e);
            this.f381d.m686a();
            Map e = this.f384g.m628e();
            if (e.containsKey("orientation")) {
                this.f385h = C0461a.m481a(Integer.parseInt((String) e.get("orientation")));
            }
            this.f383f = true;
            if (this.f382e != null) {
                this.f382e.onInterstitialAdLoaded(this);
                return;
            }
            return;
        }
        C0466k c0466k = new C0466k();
        c0466k.m471a(context, new C04601(this, c0466k), (Map) map, c0783h);
    }

    public void onDestroy() {
        if (this.f381d != null) {
            this.f381d.m687b();
        }
    }

    public boolean show() {
        if (this.f383f) {
            Intent intent = new Intent(this.f380c, AudienceNetworkActivity.class);
            intent.putExtra(AudienceNetworkActivity.PREDEFINED_ORIENTATION_KEY, m487b());
            intent.putExtra(AudienceNetworkActivity.AUDIENCE_NETWORK_UNIQUE_ID_EXTRA, this.f379b);
            if (f378a.containsKey(this.f379b)) {
                intent.putExtra(AudienceNetworkActivity.VIEW_TYPE, Type.NATIVE);
            } else {
                intent.putExtra(AudienceNetworkActivity.VIEW_TYPE, Type.DISPLAY);
                this.f384g.m624a(intent);
            }
            intent.addFlags(268435456);
            try {
                this.f380c.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                intent.setClass(this.f380c, InterstitialAdActivity.class);
                this.f380c.startActivity(intent);
            }
            return true;
        }
        if (this.f382e != null) {
            this.f382e.onInterstitialError(this, AdError.INTERNAL_ERROR);
        }
        return false;
    }
}
