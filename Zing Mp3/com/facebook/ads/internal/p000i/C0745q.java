package com.facebook.ads.internal.p000i;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.internal.p000i.C0465d.C0379a;
import com.facebook.ads.internal.p000i.p018e.p019b.C0662b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0642c;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0645f;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0647h;
import com.facebook.ads.internal.p000i.p018e.p020a.C0651l;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.internal.util.ab;

/* renamed from: com.facebook.ads.internal.i.q */
public class C0745q implements C0465d {
    private final C0647h f1235a;
    private final C0645f f1236b;
    private final C0642c f1237c;
    private final AudienceNetworkActivity f1238d;
    private final C0726m f1239e;
    private final C0379a f1240f;
    private ab f1241g;
    private int f1242h;

    /* renamed from: com.facebook.ads.internal.i.q.1 */
    class C07421 extends C0647h {
        final /* synthetic */ C0745q f1232a;

        C07421(C0745q c0745q) {
            this.f1232a = c0745q;
        }

        public void m1449a(C0646g c0646g) {
            this.f1232a.f1240f.m51a("videoInterstitalEvent", c0646g);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.q.2 */
    class C07432 extends C0645f {
        final /* synthetic */ C0745q f1233a;

        C07432(C0745q c0745q) {
            this.f1233a = c0745q;
        }

        public void m1451a(C0644e c0644e) {
            this.f1233a.f1240f.m51a("videoInterstitalEvent", c0644e);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.q.3 */
    class C07443 extends C0642c {
        final /* synthetic */ C0745q f1234a;

        C07443(C0745q c0745q) {
            this.f1234a = c0745q;
        }

        public void m1453a(C0641b c0641b) {
            this.f1234a.f1240f.m51a("videoInterstitalEvent", c0641b);
        }
    }

    public C0745q(AudienceNetworkActivity audienceNetworkActivity, C0379a c0379a) {
        this.f1235a = new C07421(this);
        this.f1236b = new C07432(this);
        this.f1237c = new C07443(this);
        this.f1238d = audienceNetworkActivity;
        this.f1239e = new C0726m(audienceNetworkActivity);
        this.f1239e.m1390a(new C0662b(audienceNetworkActivity));
        this.f1239e.getEventBus().m915a(this.f1235a);
        this.f1239e.getEventBus().m915a(this.f1236b);
        this.f1239e.getEventBus().m915a(this.f1237c);
        this.f1240f = c0379a;
        this.f1239e.setIsFullScreen(true);
        this.f1239e.setVolume(1.0f);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(15);
        this.f1239e.setLayoutParams(layoutParams);
        c0379a.m49a(this.f1239e);
    }

    public void m1455a(Intent intent, Bundle bundle, AudienceNetworkActivity audienceNetworkActivity) {
        boolean booleanExtra = intent.getBooleanExtra(AudienceNetworkActivity.AUTOPLAY, false);
        String stringExtra = intent.getStringExtra(AudienceNetworkActivity.VIDEO_URL);
        String stringExtra2 = intent.getStringExtra(AudienceNetworkActivity.VIDEO_MPD);
        Bundle bundleExtra = intent.getBundleExtra(AudienceNetworkActivity.VIDEO_LOGGER);
        String stringExtra3 = intent.getStringExtra(AudienceNetworkActivity.VIDEO_REPORT_URL);
        this.f1242h = intent.getIntExtra(AudienceNetworkActivity.VIDEO_SEEK_TIME, 0);
        this.f1239e.setAutoplay(booleanExtra);
        this.f1241g = new ab(audienceNetworkActivity, new C0783h(), this.f1239e, stringExtra3, bundleExtra);
        this.f1239e.setVideoMPD(stringExtra2);
        this.f1239e.setVideoURI(stringExtra);
        if (this.f1242h > 0) {
            this.f1239e.m1388a(this.f1242h);
        }
        this.f1239e.m1395d();
    }

    public void m1456a(Bundle bundle) {
    }

    public void m1457a(View view) {
        this.f1239e.setControlsAnchorView(view);
    }

    public void m1458a(C0379a c0379a) {
    }

    public void m1459e() {
        this.f1239e.m1396e();
    }

    public void m1460f() {
        this.f1239e.m1395d();
    }

    public void m1461g() {
        this.f1240f.m51a("videoInterstitalEvent", new C0651l(this.f1242h, this.f1239e.getCurrentPosition()));
        this.f1241g.m1572b(this.f1239e.getCurrentPosition());
        this.f1239e.m1398g();
    }
}
