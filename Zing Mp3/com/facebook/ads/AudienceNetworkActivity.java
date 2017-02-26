package com.facebook.ads;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.facebook.ads.internal.C0550g;
import com.facebook.ads.internal.C0752j;
import com.facebook.ads.internal.adapters.C0462j;
import com.facebook.ads.internal.adapters.C0466k;
import com.facebook.ads.internal.p000i.C0465d;
import com.facebook.ads.internal.p000i.C0465d.C0379a;
import com.facebook.ads.internal.p000i.C0636c;
import com.facebook.ads.internal.p000i.C0636c.C0387b;
import com.facebook.ads.internal.p000i.C0714f;
import com.facebook.ads.internal.p000i.C0718h;
import com.facebook.ads.internal.p000i.C0735k;
import com.facebook.ads.internal.p000i.C0745q;
import com.facebook.ads.internal.p004a.C0429a;
import com.facebook.ads.internal.p004a.C0430b;
import com.facebook.ads.internal.p005f.C0545n;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0786j;
import com.facebook.ads.internal.util.C0807y;
import com.facebook.ads.internal.util.C0808z;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AudienceNetworkActivity extends Activity {
    public static final String AUDIENCE_NETWORK_UNIQUE_ID_EXTRA = "uniqueId";
    public static final String AUTOPLAY = "autoplay";
    public static final String BROWSER_URL = "browserURL";
    public static final String CLIENT_TOKEN = "clientToken";
    public static final String CLOSE_REPORT_URL = "closeReportURL";
    public static final String CONTEXT_SWITCH_BEHAVIOR = "contextSwitchBehavior";
    public static final String END_CARD_ACTIVATION_COMMAND = "facebookRewardedVideoEndCardActivationCommand";
    public static final String END_CARD_MARKUP = "facebookRewardedVideoEndCardMarkup";
    public static final String HANDLER_TIME = "handlerTime";
    public static final String IMPRESSION_REPORT_URL = "impressionReportURL";
    public static final String PREDEFINED_ORIENTATION_KEY = "predefinedOrientationKey";
    public static final String REWARD_SERVER_URL = "rewardServerURL";
    public static final String SKIP_DELAY_SECONDS_KEY = "skipAfterSeconds";
    public static final String VIDEO_LOGGER = "videoLogger";
    public static final String VIDEO_MPD = "videoMPD";
    public static final String VIDEO_PLAY_REPORT_URL = "videoPlayReportURL";
    public static final String VIDEO_REPORT_URL = "videoReportURL";
    public static final String VIDEO_SEEK_TIME = "videoSeekTime";
    public static final String VIDEO_TIME_REPORT_URL = "videoTimeReportURL";
    public static final String VIDEO_URL = "videoURL";
    public static final String VIEW_TYPE = "viewType";
    public static final String WEBVIEW_ENCODING = "utf-8";
    public static final String WEBVIEW_MIME_TYPE = "text/html";
    private static final String f62a;
    private String f63b;
    private String f64c;
    private C0636c f65d;
    private boolean f66e;
    private RelativeLayout f67f;
    private Intent f68g;
    private C0550g f69h;
    private int f70i;
    private String f71j;
    private Type f72k;
    private long f73l;
    private long f74m;
    private int f75n;
    private C0465d f76o;
    private List<BackButtonInterceptor> f77p;

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.1 */
    class C03781 implements OnClickListener {
        final /* synthetic */ AudienceNetworkActivity f52a;

        C03781(AudienceNetworkActivity audienceNetworkActivity) {
            this.f52a = audienceNetworkActivity;
        }

        public void onClick(View view) {
            this.f52a.finish();
        }
    }

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.2 */
    class C03802 implements C0379a {
        final /* synthetic */ AudienceNetworkActivity f53a;

        C03802(AudienceNetworkActivity audienceNetworkActivity) {
            this.f53a = audienceNetworkActivity;
        }

        public void m52a(View view) {
            this.f53a.f67f.addView(view);
            if (this.f53a.f69h != null) {
                this.f53a.f67f.addView(this.f53a.f69h);
            }
        }

        public void m53a(String str) {
            this.f53a.m80a(str);
        }

        public void m54a(String str, C0545n c0545n) {
            this.f53a.m81a(str, c0545n);
        }
    }

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.3 */
    class C03813 implements C0379a {
        final /* synthetic */ AudienceNetworkActivity f54a;

        C03813(AudienceNetworkActivity audienceNetworkActivity) {
            this.f54a = audienceNetworkActivity;
        }

        public void m55a(View view) {
            this.f54a.f67f.addView(view);
        }

        public void m56a(String str) {
            this.f54a.m80a(str);
            if (str.equals(C0752j.REWARDED_VIDEO_END_ACTIVITY)) {
                this.f54a.finish();
            }
        }

        public void m57a(String str, C0545n c0545n) {
            this.f54a.m80a(str);
            if (str.startsWith(C0752j.REWARDED_VIDEO_COMPLETE.m1499a())) {
                this.f54a.m87d();
                if (!str.equals(C0752j.REWARDED_VIDEO_COMPLETE_WITHOUT_REWARD.m1499a())) {
                    this.f54a.m84b();
                }
                this.f54a.f66e = true;
            }
        }
    }

    public interface BackButtonInterceptor {
        boolean interceptBackButton();
    }

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.4 */
    class C03824 implements BackButtonInterceptor {
        final /* synthetic */ AudienceNetworkActivity f55a;

        C03824(AudienceNetworkActivity audienceNetworkActivity) {
            this.f55a = audienceNetworkActivity;
        }

        public boolean interceptBackButton() {
            return !this.f55a.f66e;
        }
    }

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.5 */
    class C03835 implements C0379a {
        final /* synthetic */ AudienceNetworkActivity f56a;

        C03835(AudienceNetworkActivity audienceNetworkActivity) {
            this.f56a = audienceNetworkActivity;
        }

        public void m58a(View view) {
            this.f56a.f67f.addView(view);
            if (this.f56a.f69h != null) {
                this.f56a.f67f.addView(this.f56a.f69h);
            }
        }

        public void m59a(String str) {
            this.f56a.m80a(str);
        }

        public void m60a(String str, C0545n c0545n) {
            this.f56a.m81a(str, c0545n);
        }
    }

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.6 */
    class C03846 implements C0379a {
        final /* synthetic */ AudienceNetworkActivity f57a;

        C03846(AudienceNetworkActivity audienceNetworkActivity) {
            this.f57a = audienceNetworkActivity;
        }

        public void m61a(View view) {
            this.f57a.f67f.addView(view);
            if (this.f57a.f69h != null) {
                this.f57a.f67f.addView(this.f57a.f69h);
            }
        }

        public void m62a(String str) {
            this.f57a.m80a(str);
        }

        public void m63a(String str, C0545n c0545n) {
            this.f57a.m81a(str, c0545n);
        }
    }

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.7 */
    class C03857 implements C0379a {
        final /* synthetic */ AudienceNetworkActivity f58a;

        C03857(AudienceNetworkActivity audienceNetworkActivity) {
            this.f58a = audienceNetworkActivity;
        }

        public void m64a(View view) {
            this.f58a.f67f.removeAllViews();
            this.f58a.f67f.addView(view);
        }

        public void m65a(String str) {
            this.f58a.m80a(str);
        }

        public void m66a(String str, C0545n c0545n) {
            this.f58a.m81a(str, c0545n);
        }
    }

    /* renamed from: com.facebook.ads.AudienceNetworkActivity.8 */
    class C03888 implements C0387b {
        final /* synthetic */ AudienceNetworkActivity f60a;

        /* renamed from: com.facebook.ads.AudienceNetworkActivity.8.1 */
        class C03861 implements Runnable {
            final /* synthetic */ C03888 f59a;

            C03861(C03888 c03888) {
                this.f59a = c03888;
            }

            public void run() {
                if (this.f59a.f60a.f65d.m1152c()) {
                    Log.w(AudienceNetworkActivity.f62a, "Webview already destroyed, cannot activate");
                } else {
                    this.f59a.f60a.f65d.loadUrl("javascript:" + this.f59a.f60a.f64c);
                }
            }
        }

        C03888(AudienceNetworkActivity audienceNetworkActivity) {
            this.f60a = audienceNetworkActivity;
        }

        public void m71a() {
            if (this.f60a.f65d != null && !TextUtils.isEmpty(this.f60a.f64c)) {
                this.f60a.f65d.post(new C03861(this));
            }
        }

        public void m72a(int i) {
        }

        public void m73a(String str, Map<String, String> map) {
            Uri parse = Uri.parse(str);
            if ("fbad".equals(parse.getScheme()) && parse.getAuthority().equals("close")) {
                this.f60a.finish();
                return;
            }
            if ("fbad".equals(parse.getScheme()) && C0430b.m322a(parse.getAuthority())) {
                this.f60a.m80a(C0752j.REWARDED_VIDEO_AD_CLICK.m1499a());
            }
            C0429a a = C0430b.m321a(this.f60a, this.f60a.f63b, parse, map);
            if (a != null) {
                try {
                    a.m320b();
                } catch (Throwable e) {
                    Log.e(AudienceNetworkActivity.f62a, "Error executing action", e);
                }
            }
        }

        public void m74b() {
        }
    }

    public enum Type {
        DISPLAY,
        VIDEO,
        REWARDED_VIDEO,
        NATIVE,
        BROWSER
    }

    static {
        f62a = AudienceNetworkActivity.class.getSimpleName();
    }

    public AudienceNetworkActivity() {
        this.f66e = false;
        this.f70i = -1;
        this.f77p = new ArrayList();
    }

    private void m77a(Intent intent, Bundle bundle) {
        if (bundle != null) {
            this.f70i = bundle.getInt(PREDEFINED_ORIENTATION_KEY, -1);
            this.f71j = bundle.getString(AUDIENCE_NETWORK_UNIQUE_ID_EXTRA);
            this.f72k = (Type) bundle.getSerializable(VIEW_TYPE);
            return;
        }
        this.f70i = intent.getIntExtra(PREDEFINED_ORIENTATION_KEY, -1);
        this.f71j = intent.getStringExtra(AUDIENCE_NETWORK_UNIQUE_ID_EXTRA);
        this.f72k = (Type) intent.getSerializableExtra(VIEW_TYPE);
        this.f75n = intent.getIntExtra(SKIP_DELAY_SECONDS_KEY, 0) * AdError.NETWORK_ERROR_CODE;
    }

    private void m80a(String str) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(str + ":" + this.f71j));
    }

    private void m81a(String str, C0545n c0545n) {
        Intent intent = new Intent(str + ":" + this.f71j);
        intent.putExtra(DataLayer.EVENT_KEY, c0545n);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void m84b() {
        if (!TextUtils.isEmpty(this.f68g.getStringExtra(REWARD_SERVER_URL))) {
            try {
                C0808z c0808z = (C0808z) new C0807y(new HashMap()).execute(new String[]{r0}).get();
                if (c0808z == null || !c0808z.m1694a()) {
                    m80a(C0752j.REWARD_SERVER_FAILED.m1499a());
                } else {
                    m80a(C0752j.REWARD_SERVER_SUCCESS.m1499a());
                }
            } catch (InterruptedException e) {
                m80a(C0752j.REWARD_SERVER_FAILED.m1499a());
            } catch (ExecutionException e2) {
                m80a(C0752j.REWARD_SERVER_FAILED.m1499a());
            }
        }
    }

    private void m85c() {
        this.f65d = new C0636c(this, new C03888(this), 1);
        String stringExtra = this.f68g.getStringExtra(END_CARD_MARKUP);
        this.f64c = this.f68g.getStringExtra(END_CARD_ACTIVATION_COMMAND);
        this.f65d.loadDataWithBaseURL(C0786j.m1635a(), stringExtra, WEBVIEW_MIME_TYPE, WEBVIEW_ENCODING, null);
    }

    private void m87d() {
        if (this.f65d != null) {
            this.f67f.removeAllViews();
            this.f76o = null;
            setContentView(this.f65d);
        }
    }

    public void addBackButtonInterceptor(BackButtonInterceptor backButtonInterceptor) {
        this.f77p.add(backButtonInterceptor);
    }

    public void onBackPressed() {
        long currentTimeMillis = System.currentTimeMillis();
        this.f74m += currentTimeMillis - this.f73l;
        this.f73l = currentTimeMillis;
        if (this.f74m > ((long) this.f75n)) {
            Object obj = null;
            for (BackButtonInterceptor interceptBackButton : this.f77p) {
                obj = interceptBackButton.interceptBackButton() ? 1 : obj;
            }
            if (obj == null) {
                super.onBackPressed();
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (this.f76o instanceof C0466k) {
            ((C0466k) this.f76o).m503a(configuration);
        }
        super.onConfigurationChanged(configuration);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        this.f67f = new RelativeLayout(this);
        this.f67f.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        setContentView(this.f67f, new LayoutParams(-1, -1));
        this.f68g = getIntent();
        if (this.f68g.getBooleanExtra("useNativeCloseButton", false)) {
            this.f69h = new C0550g(this);
            this.f69h.setId(100002);
            this.f69h.setOnClickListener(new C03781(this));
        }
        this.f63b = this.f68g.getStringExtra(CLIENT_TOKEN);
        m77a(this.f68g, bundle);
        if (this.f72k == Type.VIDEO) {
            C0465d c0745q = new C0745q(this, new C03802(this));
            c0745q.m1457a(this.f67f);
            this.f76o = c0745q;
        } else if (this.f72k == Type.REWARDED_VIDEO) {
            m85c();
            this.f76o = new C0735k(this, new C03813(this));
            addBackButtonInterceptor(new C03824(this));
        } else if (this.f72k == Type.DISPLAY) {
            this.f76o = new C0718h(this, new C03835(this));
        } else if (this.f72k == Type.BROWSER) {
            this.f76o = new C0714f(this, new C03846(this));
        } else if (this.f72k == Type.NATIVE) {
            this.f76o = C0462j.m484a(this.f68g.getStringExtra(AUDIENCE_NETWORK_UNIQUE_ID_EXTRA));
            if (this.f76o == null) {
                C0778d.m1599a(C0777c.m1596a(null, "Unable to find view"));
                m80a("com.facebook.ads.interstitial.error");
                finish();
                return;
            }
            this.f76o.m494a(new C03857(this));
        } else {
            C0778d.m1599a(C0777c.m1596a(null, "Unable to infer viewType from intent or savedInstanceState"));
            m80a("com.facebook.ads.interstitial.error");
            finish();
            return;
        }
        this.f76o.m492a(this.f68g, bundle, this);
        m80a("com.facebook.ads.interstitial.displayed");
        this.f73l = System.currentTimeMillis();
    }

    protected void onDestroy() {
        if (this.f76o != null) {
            this.f76o.m497g();
            this.f76o = null;
        }
        this.f67f.removeAllViews();
        if (this.f72k == Type.REWARDED_VIDEO) {
            m80a(C0752j.REWARDED_VIDEO_CLOSED.m1499a());
        } else {
            m80a("com.facebook.ads.interstitial.dismissed");
        }
        super.onDestroy();
    }

    public void onPause() {
        this.f74m += System.currentTimeMillis() - this.f73l;
        if (!(this.f76o == null || this.f66e)) {
            this.f76o.m495e();
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.f73l = System.currentTimeMillis();
        if (this.f76o != null) {
            this.f76o.m496f();
        }
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.f76o != null) {
            this.f76o.m493a(bundle);
        }
        bundle.putInt(PREDEFINED_ORIENTATION_KEY, this.f70i);
        bundle.putString(AUDIENCE_NETWORK_UNIQUE_ID_EXTRA, this.f71j);
        bundle.putSerializable(VIEW_TYPE, this.f72k);
    }

    public void onStart() {
        super.onStart();
        if (this.f70i != -1) {
            setRequestedOrientation(this.f70i);
        }
    }

    public void removeBackButtonInterceptor(BackButtonInterceptor backButtonInterceptor) {
        this.f77p.remove(backButtonInterceptor);
    }
}
