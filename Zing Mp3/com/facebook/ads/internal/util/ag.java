package com.facebook.ads.internal.util;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.facebook.ads.internal.p006b.C0482a;
import com.facebook.ads.internal.p006b.C0483b;
import com.facebook.ads.internal.p006b.C0485c;
import com.facebook.ads.internal.p006b.C0485c.C0484a;
import com.google.android.exoplayer.ExoPlayer.Factory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ag implements ad<Bundle> {
    @NonNull
    private final Context f1345a;
    @NonNull
    private final C0783h f1346b;
    @NonNull
    private final C0725a f1347c;
    @NonNull
    private final String f1348d;
    @NonNull
    private final C0482a f1349e;
    private int f1350f;
    private int f1351g;

    /* renamed from: com.facebook.ads.internal.util.ag.a */
    public interface C0725a {
        boolean m1381a();

        boolean m1382b();

        boolean m1383c();

        boolean getGlobalVisibleRect(Rect rect);

        long getInitialBufferTime();

        int getMeasuredHeight();

        int getMeasuredWidth();

        float getVolume();
    }

    /* renamed from: com.facebook.ads.internal.util.ag.1 */
    class C07721 extends C0483b {
        final /* synthetic */ C0783h f1381e;
        final /* synthetic */ ag f1382f;

        C07721(ag agVar, double d, double d2, double d3, boolean z, C0783h c0783h) {
            this.f1382f = agVar;
            this.f1381e = c0783h;
            super(d, d2, d3, z);
        }

        protected void m1590a(boolean z, boolean z2, @NonNull C0485c c0485c) {
            if (this.f1382f.m1568a(C0773b.MRC) != null) {
                this.f1381e.m1612a(this.f1382f.m1568a(C0773b.MRC), this.f1382f.m1561a());
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.util.ag.b */
    protected enum C0773b {
        PLAY(0),
        SKIP(1),
        TIME(2),
        MRC(3),
        PAUSE(4),
        RESUME(5);
        
        public final int f1390g;

        private C0773b(int i) {
            this.f1390g = i;
        }
    }

    public ag(@NonNull Context context, @NonNull C0783h c0783h, @NonNull C0725a c0725a, @NonNull String str) {
        this(context, c0783h, c0725a, str, null);
    }

    public ag(@NonNull Context context, @NonNull C0783h c0783h, @NonNull C0725a c0725a, @NonNull String str, @Nullable Bundle bundle) {
        this.f1350f = 0;
        this.f1351g = 0;
        this.f1345a = context;
        this.f1346b = c0783h;
        this.f1347c = c0725a;
        this.f1348d = str;
        List arrayList = new ArrayList();
        arrayList.add(new C07721(this, 0.5d, -1.0d, 2.0d, true, c0783h));
        if (bundle != null) {
            this.f1349e = new C0482a(context, (View) c0725a, arrayList, bundle.getBundle("adQualityManager"));
            this.f1350f = bundle.getInt("lastProgressTimeMS");
            this.f1351g = bundle.getInt("lastBoundaryTimeMS");
            return;
        }
        this.f1349e = new C0482a(context, (View) c0725a, arrayList);
    }

    private Map<String, String> m1561a() {
        Map hashMap = new HashMap();
        ai.m1592a(hashMap, this.f1347c.m1381a(), !this.f1347c.m1383c());
        m1564a(hashMap);
        m1565b(hashMap);
        m1566c(hashMap);
        m1567d(hashMap);
        return hashMap;
    }

    private void m1563a(int i, boolean z) {
        if (((double) i) > 0.0d || i <= this.f1350f) {
            this.f1349e.m700a((double) (((float) (i - this.f1350f)) / 1000.0f), (double) (ai.m1591a(this.f1345a) * this.f1347c.getVolume()));
            this.f1350f = i;
            if (i - this.f1351g >= Factory.DEFAULT_MIN_REBUFFER_MS) {
                if (m1568a(C0773b.TIME) != null) {
                    this.f1346b.m1612a(m1568a(C0773b.TIME), m1561a());
                }
                this.f1351g += Factory.DEFAULT_MIN_REBUFFER_MS;
                this.f1349e.m699a();
            }
            if (z && m1568a(C0773b.TIME) != null) {
                Map a = m1561a();
                a.put("time", String.valueOf(((double) i) / 1000.0d));
                this.f1346b.m1612a(m1568a(C0773b.TIME), a);
            }
        }
    }

    private void m1564a(Map<String, String> map) {
        map.put("exoplayer", String.valueOf(this.f1347c.m1382b()));
        map.put("prep", Long.toString(this.f1347c.getInitialBufferTime()));
    }

    private void m1565b(Map<String, String> map) {
        C0485c b = this.f1349e.m701b();
        C0484a b2 = b.m713b();
        map.put("vwa", String.valueOf(b2.m706c()));
        map.put("vwm", String.valueOf(b2.m705b()));
        map.put("vwmax", String.valueOf(b2.m707d()));
        map.put("vtime_ms", String.valueOf(b2.m709f() * 1000.0d));
        map.put("mcvt_ms", String.valueOf(b2.m710g() * 1000.0d));
        C0484a c = b.m715c();
        map.put("vla", String.valueOf(c.m706c()));
        map.put("vlm", String.valueOf(c.m705b()));
        map.put("vlmax", String.valueOf(c.m707d()));
        map.put("atime_ms", String.valueOf(c.m709f() * 1000.0d));
        map.put("mcat_ms", String.valueOf(c.m710g() * 1000.0d));
    }

    private void m1566c(Map<String, String> map) {
        map.put("ptime", String.valueOf(((float) this.f1351g) / 1000.0f));
        map.put("time", String.valueOf(((float) (this.f1351g + Factory.DEFAULT_MIN_REBUFFER_MS)) / 1000.0f));
    }

    private void m1567d(Map<String, String> map) {
        Rect rect = new Rect();
        this.f1347c.getGlobalVisibleRect(rect);
        map.put("pt", String.valueOf(rect.top));
        map.put("pl", String.valueOf(rect.left));
        map.put("ph", String.valueOf(this.f1347c.getMeasuredHeight()));
        map.put("pw", String.valueOf(this.f1347c.getMeasuredWidth()));
        WindowManager windowManager = (WindowManager) this.f1345a.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        map.put("vph", String.valueOf(displayMetrics.heightPixels));
        map.put("vpw", String.valueOf(displayMetrics.widthPixels));
    }

    protected String m1568a(C0773b c0773b) {
        return this.f1348d + "&action=" + c0773b.f1390g;
    }

    public void m1569a(int i) {
        m1563a(i, false);
    }

    public void m1570a(int i, int i2) {
        m1563a(i, true);
        this.f1351g = i2;
        this.f1350f = i2;
        this.f1349e.m699a();
    }

    public void m1571b() {
        this.f1346b.m1612a(m1568a(C0773b.SKIP), m1561a());
    }

    public void m1572b(int i) {
        m1563a(i, true);
        this.f1351g = 0;
        this.f1350f = 0;
        this.f1349e.m699a();
    }

    public void m1573c() {
        this.f1346b.m1612a(m1568a(C0773b.PAUSE), m1561a());
    }

    public void m1574d() {
        this.f1346b.m1612a(m1568a(C0773b.RESUME), m1561a());
    }

    public int m1575e() {
        return this.f1350f;
    }

    public Bundle getSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt("lastProgressTimeMS", this.f1350f);
        bundle.putInt("lastBoundaryTimeMS", this.f1351g);
        bundle.putBundle("adQualityManager", this.f1349e.getSaveInstanceState());
        return bundle;
    }
}
