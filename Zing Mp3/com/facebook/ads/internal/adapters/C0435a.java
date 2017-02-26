package com.facebook.ads.internal.adapters;

import android.content.Context;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.ads.internal.util.C0785i;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.adapters.a */
public abstract class C0435a {
    protected final C0400b f286a;
    protected final C0749a f287b;
    private final Context f288c;
    private boolean f289d;

    public C0435a(Context context, C0400b c0400b, C0749a c0749a) {
        this.f288c = context;
        this.f286a = c0400b;
        this.f287b = c0749a;
    }

    public final void m337a() {
        if (!this.f289d) {
            if (this.f286a != null) {
                this.f286a.m147d();
            }
            Map hashMap = new HashMap();
            if (this.f287b != null) {
                this.f287b.m1492a(hashMap);
            }
            m338a(hashMap);
            this.f289d = true;
            C0785i.m1628a(this.f288c, "Impression logged");
            if (this.f286a != null) {
                this.f286a.m148e();
            }
        }
    }

    protected abstract void m338a(Map<String, String> map);
}
