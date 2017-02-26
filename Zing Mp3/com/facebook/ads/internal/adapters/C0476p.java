package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.ads.internal.p000i.C0618b;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.ads.internal.p005f.C0537f;
import com.facebook.ads.internal.util.C0807y;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.adapters.p */
public class C0476p extends C0435a {
    private static final String f490c;
    private final C0618b f491d;
    private final Context f492e;
    private C0474o f493f;
    private boolean f494g;

    /* renamed from: com.facebook.ads.internal.adapters.p.1 */
    class C04751 implements Runnable {
        final /* synthetic */ C0476p f489a;

        C04751(C0476p c0476p) {
            this.f489a = c0476p;
        }

        public void run() {
            if (this.f489a.f491d.m1152c()) {
                Log.w(C0476p.f490c, "Webview already destroyed, cannot activate");
            } else {
                this.f489a.f491d.loadUrl("javascript:" + this.f489a.f493f.m625b());
            }
        }
    }

    static {
        f490c = C0476p.class.getSimpleName();
    }

    public C0476p(Context context, C0618b c0618b, C0749a c0749a, C0400b c0400b) {
        super(context, c0400b, c0749a);
        this.f492e = context.getApplicationContext();
        this.f491d = c0618b;
    }

    private void m635b(Map<String, String> map) {
        if (this.f493f != null) {
            if (TextUtils.isEmpty(this.f493f.m619B())) {
                if (!TextUtils.isEmpty(this.f493f.m626c())) {
                    new C0807y(map).execute(new String[]{r0});
                    return;
                }
                return;
            }
            if (map != null) {
                map.remove("evt");
            }
            C0537f.m878a(this.f492e).m885a(this.f493f.m619B(), (Map) map);
        }
    }

    public void m637a(C0474o c0474o) {
        this.f493f = c0474o;
    }

    protected void m638a(Map<String, String> map) {
        if (this.f493f != null) {
            if (!(this.f491d == null || TextUtils.isEmpty(this.f493f.m627d()))) {
                if (this.f491d.m1152c()) {
                    Log.w(f490c, "Webview already destroyed, cannot send impression");
                } else {
                    this.f491d.loadUrl("javascript:" + this.f493f.m627d());
                }
            }
            map.put("evt", "native_imp");
            m635b((Map) map);
        }
    }

    public synchronized void m639b() {
        if (!(this.f494g || this.f493f == null)) {
            this.f494g = true;
            if (!(this.f491d == null || TextUtils.isEmpty(this.f493f.m625b()))) {
                this.f491d.post(new C04751(this));
            }
        }
    }

    public void m640c() {
        Map hashMap = new HashMap();
        hashMap.put("evt", "interstitial_displayed");
        this.b.m1492a(hashMap);
        m635b(hashMap);
    }
}
