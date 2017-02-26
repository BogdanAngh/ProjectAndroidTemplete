package com.facebook.ads.internal;

import android.content.Context;
import android.os.Handler;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.NativeAd.MediaCacheFlag;
import com.facebook.ads.internal.adapters.AdAdapter;
import com.facebook.ads.internal.adapters.C0415w;
import com.facebook.ads.internal.adapters.C0440v;
import com.facebook.ads.internal.adapters.C0444d;
import com.facebook.ads.internal.p008e.C0511a;
import com.facebook.ads.internal.p008e.C0515d;
import com.facebook.ads.internal.p008e.C0519f;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.server.C0756a;
import com.facebook.ads.internal.server.C0756a.C0428a;
import com.facebook.ads.internal.server.C0761e;
import com.facebook.ads.internal.util.C0794o;
import com.facebook.ads.internal.util.aj;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.i */
public class C0747i implements C0428a {
    private final Context f1244a;
    private final String f1245b;
    private final C0756a f1246c;
    private final C0523e f1247d;
    private final C0497c f1248e;
    private final AdSize f1249f;
    private final int f1250g;
    private boolean f1251h;
    private final Handler f1252i;
    private final Runnable f1253j;
    private C0409a f1254k;
    private C0515d f1255l;

    /* renamed from: com.facebook.ads.internal.i.a */
    public interface C0409a {
        void m208a(C0488b c0488b);

        void m209a(List<C0440v> list);
    }

    /* renamed from: com.facebook.ads.internal.i.1 */
    class C06051 implements C0415w {
        final /* synthetic */ List f874a;
        final /* synthetic */ C0747i f875b;

        C06051(C0747i c0747i, List list) {
            this.f875b = c0747i;
            this.f874a = list;
        }

        public void m1133a(C0440v c0440v) {
            this.f874a.add(c0440v);
        }

        public void m1134a(C0440v c0440v, AdError adError) {
        }

        public void m1135b(C0440v c0440v) {
        }

        public void m1136c(C0440v c0440v) {
        }
    }

    /* renamed from: com.facebook.ads.internal.i.b */
    private static final class C0606b extends aj<C0747i> {
        public C0606b(C0747i c0747i) {
            super(c0747i);
        }

        public void run() {
            C0747i c0747i = (C0747i) m263a();
            if (c0747i != null) {
                if (C0794o.m1657a(c0747i.f1244a)) {
                    c0747i.m1466a();
                } else {
                    c0747i.f1252i.postDelayed(c0747i.f1253j, HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS);
                }
            }
        }
    }

    public C0747i(Context context, String str, C0523e c0523e, AdSize adSize, C0497c c0497c, int i, EnumSet<MediaCacheFlag> enumSet) {
        this.f1244a = context;
        this.f1245b = str;
        this.f1247d = c0523e;
        this.f1249f = adSize;
        this.f1248e = c0497c;
        this.f1250g = i;
        this.f1246c = new C0756a(context);
        this.f1246c.m1519a((C0428a) this);
        this.f1251h = true;
        this.f1252i = new Handler();
        this.f1253j = new C0606b(this);
    }

    private List<C0440v> m1465d() {
        C0515d c0515d = this.f1255l;
        C0511a d = c0515d.m808d();
        List<C0440v> arrayList = new ArrayList(c0515d.m807c());
        for (C0511a c0511a = d; c0511a != null; c0511a = c0515d.m808d()) {
            AdAdapter a = C0444d.m423a(c0511a.m797a(), AdPlacementType.NATIVE);
            if (a != null && a.getPlacementType() == AdPlacementType.NATIVE) {
                Map hashMap = new HashMap();
                hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, c0511a.m799b());
                hashMap.put("definition", c0515d.m804a());
                ((C0440v) a).m346a(this.f1244a, new C06051(this, arrayList), hashMap);
            }
        }
        return arrayList;
    }

    public void m1466a() {
        this.f1246c.m1518a(new C0519f(this.f1244a, this.f1245b, this.f1249f, this.f1247d, this.f1248e, this.f1250g, AdSettings.isTestMode(this.f1244a)));
    }

    public void m1467a(C0488b c0488b) {
        if (this.f1251h) {
            this.f1252i.postDelayed(this.f1253j, 1800000);
        }
        if (this.f1254k != null) {
            this.f1254k.m208a(c0488b);
        }
    }

    public void m1468a(C0409a c0409a) {
        this.f1254k = c0409a;
    }

    public void m1469a(C0761e c0761e) {
        C0515d b = c0761e.m1528b();
        if (b == null) {
            throw new IllegalStateException("no placement in response");
        }
        if (this.f1251h) {
            long b2 = b.m804a().m811b();
            if (b2 == 0) {
                b2 = 1800000;
            }
            this.f1252i.postDelayed(this.f1253j, b2);
        }
        this.f1255l = b;
        List d = m1465d();
        if (this.f1254k == null) {
            return;
        }
        if (d.isEmpty()) {
            this.f1254k.m208a(AdErrorType.NO_FILL.getAdErrorWrapper(BuildConfig.FLAVOR));
        } else {
            this.f1254k.m209a(d);
        }
    }

    public void m1470b() {
    }

    public void m1471c() {
        this.f1251h = false;
        this.f1252i.removeCallbacks(this.f1253j);
    }
}
