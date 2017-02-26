package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.facebook.ads.AdError;
import com.facebook.ads.AdNetwork;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAd.Image;
import com.facebook.ads.NativeAd.Rating;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0804w;
import com.facebook.ads.internal.util.ah;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.InMobiNative.NativeAdListener;
import com.inmobi.sdk.InMobiSdk;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.q */
public class C0478q extends C0440v implements C0441t {
    private C0415w f497a;
    private InMobiNative f498b;
    private boolean f499c;
    private View f500d;
    private String f501e;
    private String f502f;
    private String f503g;
    private Rating f504h;
    private Image f505i;
    private Image f506j;

    /* renamed from: com.facebook.ads.internal.adapters.q.1 */
    class C04771 implements NativeAdListener {
        final /* synthetic */ Context f495a;
        final /* synthetic */ C0478q f496b;

        C04771(C0478q c0478q, Context context) {
            this.f496b = c0478q;
            this.f495a = context;
        }

        public void onAdDismissed(InMobiNative inMobiNative) {
        }

        public void onAdDisplayed(InMobiNative inMobiNative) {
        }

        public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
            C0785i.m1628a(this.f495a, C0804w.m1678a(this.f496b.m654D()) + " Failed with InMobi error: " + inMobiAdRequestStatus.getMessage());
            if (this.f496b.f497a != null) {
                this.f496b.f497a.m234a(this.f496b, new AdError(AdError.MEDIATION_ERROR_CODE, inMobiAdRequestStatus.getMessage()));
            }
        }

        public void onAdLoadSucceeded(InMobiNative inMobiNative) {
            try {
                int optInt;
                int optInt2;
                JSONObject jSONObject = new JSONObject((String) inMobiNative.getAdContent());
                this.f496b.f501e = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_TITLE);
                this.f496b.f502f = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_DESCRIPTION);
                this.f496b.f503g = jSONObject.optString("cta");
                JSONObject optJSONObject = jSONObject.optJSONObject("icon");
                if (optJSONObject != null) {
                    optInt = optJSONObject.optInt("width");
                    optInt2 = optJSONObject.optInt("height");
                    this.f496b.f505i = new Image(optJSONObject.optString(NativeProtocol.WEB_DIALOG_URL), optInt, optInt2);
                }
                optJSONObject = jSONObject.optJSONObject("screenshots");
                if (optJSONObject != null) {
                    optInt = optJSONObject.optInt("width");
                    optInt2 = optJSONObject.optInt("height");
                    this.f496b.f506j = new Image(optJSONObject.optString(NativeProtocol.WEB_DIALOG_URL), optInt, optInt2);
                }
                try {
                    this.f496b.f504h = new Rating(Double.parseDouble(jSONObject.optString("rating")), 5.0d);
                } catch (Exception e) {
                }
                this.f496b.f499c = true;
                if (this.f496b.f500d != null) {
                    this.f496b.f498b;
                    InMobiNative.bind(this.f496b.f500d, inMobiNative);
                }
                if (this.f496b.f497a != null) {
                    C0785i.m1628a(this.f495a, C0804w.m1678a(this.f496b.m654D()) + " Loaded");
                    this.f496b.f497a.m233a(this.f496b);
                }
            } catch (Exception e2) {
                if (this.f496b.f497a != null) {
                    C0785i.m1628a(this.f495a, C0804w.m1678a(this.f496b.m654D()) + " Failed. Internal AN SDK error");
                    this.f496b.f497a.m234a(this.f496b, AdError.INTERNAL_ERROR);
                }
            }
        }

        public void onUserLeftApplication(InMobiNative inMobiNative) {
        }
    }

    public List<NativeAd> m651A() {
        return null;
    }

    public String m652B() {
        return null;
    }

    public AdNetwork m653C() {
        return AdNetwork.INMOBI;
    }

    public C0445e m654D() {
        return C0445e.INMOBI;
    }

    public void m655a() {
        if (m661b()) {
            InMobiNative inMobiNative = this.f498b;
            InMobiNative.unbind(this.f500d);
        }
        this.f500d = null;
    }

    public void m656a(int i) {
    }

    public void m657a(Context context, C0415w c0415w, Map<String, Object> map) {
        C0785i.m1628a(context, C0804w.m1678a(m654D()) + " Loading");
        JSONObject jSONObject = (JSONObject) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA);
        Object optString = jSONObject.optString("account_id");
        Long valueOf = Long.valueOf(jSONObject.optLong("placement_id"));
        if (TextUtils.isEmpty(optString) || valueOf == null) {
            c0415w.m234a(this, AdError.MEDIATION_ERROR);
            return;
        }
        this.f497a = c0415w;
        InMobiSdk.init(context, optString);
        this.f498b = new InMobiNative(valueOf.longValue(), new C04771(this, context));
        this.f498b.load();
    }

    public void m658a(View view, List<View> list) {
        this.f500d = view;
        if (m661b()) {
            InMobiNative inMobiNative = this.f498b;
            InMobiNative.bind(this.f500d, this.f498b);
        }
    }

    public void m659a(Map<String, String> map) {
        this.f497a.m235b(this);
    }

    public void m660b(Map<String, String> map) {
        if (m661b()) {
            this.f497a.m236c(this);
            this.f498b.reportAdClickAndOpenLandingPage(null);
        }
    }

    public boolean m661b() {
        return this.f498b != null && this.f499c;
    }

    public boolean m662c() {
        return false;
    }

    public boolean m663d() {
        return false;
    }

    public boolean m664e() {
        return false;
    }

    public boolean m665f() {
        return false;
    }

    public boolean m666g() {
        return true;
    }

    public int m667h() {
        return 0;
    }

    public int m668i() {
        return 0;
    }

    public int m669j() {
        return 0;
    }

    public Image m670k() {
        return this.f505i;
    }

    public Image m671l() {
        return this.f506j;
    }

    public NativeAdViewAttributes m672m() {
        return null;
    }

    public String m673n() {
        return this.f501e;
    }

    public String m674o() {
        return null;
    }

    public void onDestroy() {
        m655a();
        this.f498b = null;
        this.f497a = null;
    }

    public String m675p() {
        return this.f502f;
    }

    public String m676q() {
        return this.f503g;
    }

    public String m677r() {
        return null;
    }

    public Rating m678s() {
        return null;
    }

    public Image m679t() {
        return null;
    }

    public String m680u() {
        return null;
    }

    public String m681v() {
        return "Ad";
    }

    public String m682w() {
        return null;
    }

    public String m683x() {
        return null;
    }

    public ah m684y() {
        return ah.UNKNOWN;
    }

    public String m685z() {
        return null;
    }
}
