package com.facebook.ads.internal.p003j;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import com.facebook.ads.AdError;
import com.facebook.ads.internal.C0604h;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0794o;
import com.facebook.ads.internal.util.aj;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.j.a */
public class C0749a {
    private static final String f1256a;
    private final View f1257b;
    private final int f1258c;
    private final int f1259d;
    private final C0398a f1260e;
    private final Handler f1261f;
    private final Runnable f1262g;
    private final boolean f1263h;
    private int f1264i;
    private int f1265j;
    private boolean f1266k;
    private C0750b f1267l;
    private Map<String, Integer> f1268m;
    private long f1269n;
    private int f1270o;

    /* renamed from: com.facebook.ads.internal.j.a.a */
    public static abstract class C0398a {
        public abstract void m141a();

        public void m142b() {
        }
    }

    /* renamed from: com.facebook.ads.internal.j.a.b */
    private static final class C0748b extends aj<C0749a> {
        public C0748b(C0749a c0749a) {
            super(c0749a);
        }

        public void run() {
            int i = 0;
            C0749a c0749a = (C0749a) m263a();
            if (c0749a != null) {
                View a = c0749a.f1257b;
                C0398a b = c0749a.f1260e;
                if (a != null && b != null) {
                    C0750b a2 = C0749a.m1475a(a, c0749a.f1258c);
                    if (a2.m1495a()) {
                        c0749a.f1270o = c0749a.f1270o + 1;
                    } else {
                        c0749a.f1270o = 0;
                    }
                    int i2 = c0749a.f1270o > c0749a.f1259d ? 1 : 0;
                    int i3 = (c0749a.f1267l == null || !c0749a.f1267l.m1495a()) ? 0 : 1;
                    if (!(i2 == 0 && a2.m1495a())) {
                        c0749a.f1267l = a2;
                    }
                    String valueOf = String.valueOf(a2.m1496b());
                    synchronized (c0749a) {
                        if (c0749a.f1268m.containsKey(valueOf)) {
                            i = ((Integer) c0749a.f1268m.get(valueOf)).intValue();
                        }
                        c0749a.f1268m.put(valueOf, Integer.valueOf(i + 1));
                    }
                    if (i2 != 0 && i3 == 0) {
                        c0749a.f1269n = System.currentTimeMillis();
                        b.m141a();
                        if (!c0749a.f1263h) {
                            return;
                        }
                    } else if (i2 == 0 && i3 != 0) {
                        b.m142b();
                    }
                    if (!c0749a.f1266k) {
                        c0749a.f1261f.postDelayed(c0749a.f1262g, (long) c0749a.f1265j);
                    }
                }
            }
        }
    }

    static {
        f1256a = C0749a.class.getSimpleName();
    }

    public C0749a(View view, int i, int i2, boolean z, C0398a c0398a) {
        this.f1261f = new Handler();
        this.f1262g = new C0748b(this);
        this.f1264i = 0;
        this.f1265j = AdError.NETWORK_ERROR_CODE;
        this.f1266k = true;
        this.f1267l = new C0750b(C0751c.UNKNOWN);
        this.f1268m = new HashMap();
        this.f1269n = 0;
        this.f1270o = 0;
        this.f1257b = view;
        this.f1258c = i;
        this.f1260e = c0398a;
        this.f1263h = z;
        if (i2 < 0) {
            i2 = 0;
        }
        this.f1259d = i2;
    }

    public C0749a(View view, int i, C0398a c0398a) {
        this(view, i, 0, false, c0398a);
    }

    public C0749a(View view, int i, boolean z, C0398a c0398a) {
        this(view, i, 0, z, c0398a);
    }

    public static C0750b m1475a(View view, int i) {
        if (view == null) {
            C0749a.m1477a(view, false, "adView is null.");
            return new C0750b(C0751c.AD_IS_NULL);
        } else if (view.getParent() == null) {
            C0749a.m1477a(view, false, "adView has no parent.");
            return new C0750b(C0751c.INVALID_PARENT);
        } else if (view.getWindowVisibility() != 0) {
            C0749a.m1477a(view, false, "adView window is not set to VISIBLE.");
            return new C0750b(C0751c.INVALID_WINDOW);
        } else if (view.getVisibility() != 0) {
            C0749a.m1477a(view, false, "adView is not set to VISIBLE.");
            return new C0750b(C0751c.AD_IS_NOT_VISIBLE);
        } else if (view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0) {
            C0749a.m1477a(view, false, "adView has invisible dimensions (w=" + view.getMeasuredWidth() + ", h=" + view.getMeasuredHeight());
            return new C0750b(C0751c.INVALID_DIMENSIONS);
        } else if (VERSION.SDK_INT < 11 || view.getAlpha() >= 0.9f) {
            int width = view.getWidth();
            int height = view.getHeight();
            int[] iArr = new int[2];
            try {
                view.getLocationOnScreen(iArr);
                Context context = view.getContext();
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                float f = 0.0f;
                if (rect.intersect(iArr[0], iArr[1], iArr[0] + width, iArr[1] + height)) {
                    f = (((float) (rect.width() * rect.height())) * 1.0f) / ((float) (width * height));
                }
                boolean g = C0604h.m1126g(context);
                int h = C0604h.m1127h(context);
                if (g) {
                    f *= 100.0f;
                    if (f < ((float) h)) {
                        C0749a.m1477a(view, false, String.format("adView visible area is too small [%.2f%% visible, current threshold %d%%]", new Object[]{Float.valueOf(f), Integer.valueOf(h)}));
                        return new C0750b(C0751c.AD_INSUFFICIENT_VISIBLE_AREA, f);
                    }
                } else if (iArr[0] < 0 || displayMetrics.widthPixels - iArr[0] < width) {
                    C0749a.m1477a(view, false, "adView is not fully on screen horizontally.");
                    return new C0750b(C0751c.AD_OFFSCREEN_HORIZONTALLY, f);
                } else {
                    width = (int) ((((double) height) * (100.0d - ((double) i))) / 100.0d);
                    if (iArr[1] < 0 && Math.abs(iArr[1]) > width) {
                        C0749a.m1477a(view, false, "adView is not visible from the top.");
                        return new C0750b(C0751c.AD_OFFSCREEN_TOP, f);
                    } else if ((height + iArr[1]) - displayMetrics.heightPixels > width) {
                        C0749a.m1477a(view, false, "adView is not visible from the bottom.");
                        return new C0750b(C0751c.AD_OFFSCREEN_BOTTOM, f);
                    }
                }
                if (C0794o.m1657a(context)) {
                    C0749a.m1477a(view, true, "adView is visible.");
                    return new C0750b(C0751c.IS_VIEWABLE, f);
                }
                C0749a.m1477a(view, false, "Screen is not turned on.");
                return new C0750b(C0751c.SCREEN_OFF, f);
            } catch (NullPointerException e) {
                C0749a.m1477a(view, false, "Cannot get location on screen.");
                return new C0750b(C0751c.INVALID_DIMENSIONS);
            }
        } else {
            C0749a.m1477a(view, false, "adView is too transparent.");
            return new C0750b(C0751c.AD_IS_TRANSPARENT);
        }
    }

    private static void m1477a(View view, boolean z, String str) {
    }

    public void m1490a() {
        this.f1261f.postDelayed(this.f1262g, (long) this.f1264i);
        this.f1266k = false;
        this.f1270o = 0;
    }

    public void m1491a(int i) {
        this.f1264i = i;
    }

    public void m1492a(Map<String, String> map) {
        map.put("vrc", String.valueOf(this.f1267l.m1496b()));
        map.put("vp", String.valueOf(this.f1267l.m1497c()));
        map.put("vh", new JSONObject(this.f1268m).toString());
        map.put("vt", C0785i.m1618a(this.f1269n));
    }

    public void m1493b() {
        this.f1261f.removeCallbacks(this.f1262g);
        this.f1266k = true;
        this.f1270o = 0;
    }

    public void m1494b(int i) {
        this.f1265j = i;
    }
}
