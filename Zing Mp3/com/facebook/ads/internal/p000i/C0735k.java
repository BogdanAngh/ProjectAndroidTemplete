package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.internal.C0752j;
import com.facebook.ads.internal.p000i.C0465d.C0379a;
import com.facebook.ads.internal.p000i.p018e.p019b.C0638m;
import com.facebook.ads.internal.p000i.p018e.p019b.C0665c;
import com.facebook.ads.internal.p000i.p018e.p019b.C0687i;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0643d;
import com.facebook.ads.internal.p000i.p018e.p020a.C0648i;
import com.facebook.ads.internal.p000i.p018e.p020a.C0650k;
import com.facebook.ads.internal.p000i.p018e.p020a.C0653n;
import com.facebook.ads.internal.p000i.p018e.p021c.C0708f;
import com.facebook.ads.internal.p002c.C0496d;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.ads.internal.p003j.C0749a.C0398a;
import com.facebook.ads.internal.p005f.C0453p;
import com.facebook.ads.internal.p005f.C0537f;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0802v;
import com.facebook.ads.internal.util.C0807y;
import com.facebook.ads.internal.util.ae;
import com.facebook.internal.ServerProtocol;
import com.mp3download.zingmp3.BuildConfig;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.i.k */
public class C0735k implements C0465d {
    private C0749a f1208a;
    private C0726m f1209b;
    private C0802v f1210c;
    private ae f1211d;
    private C0379a f1212e;
    private String f1213f;
    private C0453p<C0641b> f1214g;
    private C0453p<C0643d> f1215h;
    private C0453p<C0648i> f1216i;
    private C0453p<C0650k> f1217j;
    private C0453p<C0653n> f1218k;
    private String f1219l;
    private final Context f1220m;
    private String f1221n;
    private String f1222o;

    /* renamed from: com.facebook.ads.internal.i.k.1 */
    class C07291 extends C0453p<C0653n> {
        final /* synthetic */ C0735k f1202a;

        C07291(C0735k c0735k) {
            this.f1202a = c0735k;
        }

        public Class<C0653n> m1411a() {
            return C0653n.class;
        }

        public void m1413a(C0653n c0653n) {
            this.f1202a.f1211d.m1583a(c0653n.m1210b(), this.f1202a.f1209b, c0653n.m1209a());
        }
    }

    /* renamed from: com.facebook.ads.internal.i.k.2 */
    class C07302 extends C0453p<C0641b> {
        final /* synthetic */ C0735k f1203a;

        C07302(C0735k c0735k) {
            this.f1203a = c0735k;
        }

        public Class<C0641b> m1414a() {
            return C0641b.class;
        }

        public void m1416a(C0641b c0641b) {
            if (this.f1203a.f1212e != null) {
                this.f1203a.f1212e.m51a(C0752j.REWARDED_VIDEO_COMPLETE.m1499a(), c0641b);
            }
            if (this.f1203a.f1210c != null) {
                this.f1203a.f1210c.m1572b(this.f1203a.f1209b.getCurrentPosition());
            }
            this.f1203a.m1446i();
        }
    }

    /* renamed from: com.facebook.ads.internal.i.k.3 */
    class C07313 extends C0453p<C0643d> {
        final /* synthetic */ C0735k f1204a;

        C07313(C0735k c0735k) {
            this.f1204a = c0735k;
        }

        public Class<C0643d> m1417a() {
            return C0643d.class;
        }

        public void m1419a(C0643d c0643d) {
            if (this.f1204a.f1212e != null) {
                this.f1204a.f1212e.m50a(C0752j.REWARDED_VIDEO_ERROR.m1499a());
            }
            this.f1204a.m1446i();
        }
    }

    /* renamed from: com.facebook.ads.internal.i.k.4 */
    class C07324 extends C0453p<C0648i> {
        final /* synthetic */ C0735k f1205a;

        C07324(C0735k c0735k) {
            this.f1205a = c0735k;
        }

        public Class<C0648i> m1420a() {
            return C0648i.class;
        }

        public void m1422a(C0648i c0648i) {
            if (this.f1205a.f1208a != null) {
                this.f1205a.f1208a.m1490a();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.k.5 */
    class C07335 extends C0453p<C0650k> {
        final /* synthetic */ C0735k f1206a;

        C07335(C0735k c0735k) {
            this.f1206a = c0735k;
        }

        public Class<C0650k> m1423a() {
            return C0650k.class;
        }

        public void m1425a(C0650k c0650k) {
            if (this.f1206a.f1209b.getState() != C0708f.PREPARING && this.f1206a.f1210c != null) {
                this.f1206a.f1210c.m1569a(this.f1206a.f1209b.getCurrentPosition());
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.k.6 */
    class C07346 extends C0398a {
        final /* synthetic */ C0735k f1207a;

        C07346(C0735k c0735k) {
            this.f1207a = c0735k;
        }

        public void m1426a() {
            if (!this.f1207a.f1211d.m1585b()) {
                this.f1207a.f1211d.m1582a();
                Map hashMap = new HashMap();
                if (TextUtils.isEmpty(this.f1207a.f1219l)) {
                    new C0807y(hashMap).execute(new String[]{this.f1207a.m1435a()});
                } else {
                    this.f1207a.f1208a.m1492a(hashMap);
                    hashMap.put(ServerProtocol.FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH, C0785i.m1620a(this.f1207a.m1439b()));
                    C0537f.m878a(this.f1207a.f1220m).m885a(this.f1207a.f1219l, hashMap);
                }
                if (this.f1207a.f1212e != null) {
                    this.f1207a.f1212e.m50a(C0752j.REWARDED_VIDEO_IMPRESSION.m1499a());
                }
            }
        }
    }

    public C0735k(Context context, C0379a c0379a) {
        this.f1220m = context;
        this.f1212e = c0379a;
        m1434k();
    }

    private void m1434k() {
        LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        this.f1209b = new C0726m(this.f1220m);
        this.f1209b.m1399h();
        this.f1209b.setAutoplay(true);
        this.f1209b.setIsFullScreen(true);
        this.f1209b.setLayoutParams(layoutParams);
        this.f1209b.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.f1218k = new C07291(this);
        this.f1214g = new C07302(this);
        this.f1215h = new C07313(this);
        this.f1216i = new C07324(this);
        this.f1217j = new C07335(this);
        this.f1209b.getEventBus().m915a(this.f1214g);
        this.f1209b.getEventBus().m915a(this.f1215h);
        this.f1209b.getEventBus().m915a(this.f1216i);
        this.f1209b.getEventBus().m915a(this.f1217j);
        this.f1209b.getEventBus().m915a(this.f1218k);
        this.f1209b.m1390a(new C0687i(this.f1220m));
        C0638m c0665c = new C0665c(this.f1220m, BuildConfig.FLAVOR, true);
        LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        c0665c.setLayoutParams(layoutParams2);
        c0665c.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.f1209b.m1390a(c0665c);
        this.f1208a = new C0749a(this.f1209b, 1, new C07346(this));
        this.f1208a.m1491a((int) Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        this.f1211d = new ae();
        this.f1212e.m49a(this.f1209b);
    }

    public String m1435a() {
        return this.f1213f;
    }

    public void m1436a(Intent intent, Bundle bundle, AudienceNetworkActivity audienceNetworkActivity) {
        String stringExtra = intent.getStringExtra(AudienceNetworkActivity.VIDEO_URL);
        this.f1213f = intent.getStringExtra(AudienceNetworkActivity.IMPRESSION_REPORT_URL);
        this.f1219l = intent.getStringExtra(AudienceNetworkActivity.CLIENT_TOKEN);
        this.f1222o = intent.getStringExtra(AudienceNetworkActivity.CLOSE_REPORT_URL);
        String stringExtra2 = intent.getStringExtra(AudienceNetworkActivity.VIDEO_TIME_REPORT_URL);
        String stringExtra3 = intent.getStringExtra(AudienceNetworkActivity.VIDEO_PLAY_REPORT_URL);
        this.f1221n = intent.getStringExtra(AudienceNetworkActivity.CONTEXT_SWITCH_BEHAVIOR);
        this.f1210c = new C0802v(this.f1220m, this.f1209b, stringExtra2, stringExtra3);
        stringExtra2 = C0496d.m748a(this.f1220m).m751b(stringExtra);
        if (stringExtra2 == null || stringExtra2.isEmpty()) {
            stringExtra2 = stringExtra;
        }
        if (stringExtra2 != null) {
            this.f1209b.setVideoURI(stringExtra2);
        }
        this.f1209b.m1395d();
    }

    public void m1437a(Bundle bundle) {
    }

    public void m1438a(C0379a c0379a) {
    }

    public Map<String, String> m1439b() {
        return this.f1211d.m1588e();
    }

    public void m1440c() {
        this.f1209b.m1388a(1);
        this.f1209b.m1395d();
    }

    public void m1441d() {
        this.f1209b.m1396e();
    }

    public void m1442e() {
        m1441d();
    }

    public void m1443f() {
        if (!m1445h()) {
            return;
        }
        if (this.f1221n.equals("restart")) {
            m1440c();
        } else if (this.f1221n.equals("resume")) {
            m1447j();
        } else if (this.f1221n.equals("skip")) {
            this.f1212e.m51a(C0752j.REWARDED_VIDEO_COMPLETE_WITHOUT_REWARD.m1499a(), new C0641b());
            m1446i();
        } else if (this.f1221n.equals("endvideo")) {
            this.f1212e.m50a(C0752j.REWARDED_VIDEO_END_ACTIVITY.m1499a());
            Map hashMap = new HashMap();
            if (!TextUtils.isEmpty(this.f1219l)) {
                this.f1208a.m1492a(hashMap);
                hashMap.put(ServerProtocol.FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH, C0785i.m1620a(m1439b()));
                C0537f.m878a(this.f1220m).m892e(this.f1219l, hashMap);
            } else if (this.f1222o != null) {
                new C0807y(hashMap).execute(new String[]{this.f1222o});
            }
            m1446i();
        }
    }

    public void m1444g() {
        m1446i();
    }

    public boolean m1445h() {
        return this.f1209b.getState() == C0708f.PAUSED;
    }

    public void m1446i() {
        this.f1209b.m1398g();
        if (this.f1208a != null) {
            this.f1208a.m1493b();
        }
    }

    public void m1447j() {
        this.f1209b.m1388a(this.f1209b.getCurrentPosition());
        this.f1209b.m1395d();
    }
}
