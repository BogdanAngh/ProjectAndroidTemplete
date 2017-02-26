package com.facebook.ads.internal.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0650k;
import com.facebook.ads.internal.p000i.p018e.p020a.C0651l;
import com.facebook.ads.internal.p000i.p018e.p020a.C0652m;
import com.facebook.ads.internal.p005f.C0453p;

public class ab extends ag {
    @NonNull
    private final C0453p<C0652m> f1352a;
    @NonNull
    private final C0453p<C0644e> f1353b;
    @NonNull
    private final C0453p<C0646g> f1354c;
    @NonNull
    private final C0453p<C0650k> f1355d;
    @NonNull
    private final C0453p<C0641b> f1356e;
    @NonNull
    private final C0453p<C0651l> f1357f;
    @NonNull
    private final C0726m f1358g;
    private boolean f1359h;

    /* renamed from: com.facebook.ads.internal.util.ab.1 */
    class C07661 extends C0453p<C0652m> {
        static final /* synthetic */ boolean f1336a;
        final /* synthetic */ ab f1337b;

        static {
            f1336a = !ab.class.desiredAssertionStatus();
        }

        C07661(ab abVar) {
            this.f1337b = abVar;
        }

        public Class<C0652m> m1543a() {
            return C0652m.class;
        }

        public void m1545a(C0652m c0652m) {
            if (!f1336a && this.f1337b == null) {
                throw new AssertionError();
            } else if (this.f1337b != null) {
                this.f1337b.m1571b();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.util.ab.2 */
    class C07672 extends C0453p<C0644e> {
        static final /* synthetic */ boolean f1338a;
        final /* synthetic */ ab f1339b;

        static {
            f1338a = !ab.class.desiredAssertionStatus();
        }

        C07672(ab abVar) {
            this.f1339b = abVar;
        }

        public Class<C0644e> m1546a() {
            return C0644e.class;
        }

        public void m1548a(C0644e c0644e) {
            if (!f1338a && this.f1339b == null) {
                throw new AssertionError();
            } else if (this.f1339b != null) {
                this.f1339b.m1573c();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.util.ab.3 */
    class C07683 extends C0453p<C0646g> {
        static final /* synthetic */ boolean f1340a;
        final /* synthetic */ ab f1341b;

        static {
            f1340a = !ab.class.desiredAssertionStatus();
        }

        C07683(ab abVar) {
            this.f1341b = abVar;
        }

        public Class<C0646g> m1549a() {
            return C0646g.class;
        }

        public void m1551a(C0646g c0646g) {
            if (!f1340a && this.f1341b == null) {
                throw new AssertionError();
            } else if (this.f1341b != null) {
                if (this.f1341b.f1359h) {
                    this.f1341b.m1574d();
                } else {
                    this.f1341b.f1359h = true;
                }
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.util.ab.4 */
    class C07694 extends C0453p<C0650k> {
        final /* synthetic */ ab f1342a;

        C07694(ab abVar) {
            this.f1342a = abVar;
        }

        public Class<C0650k> m1552a() {
            return C0650k.class;
        }

        public void m1554a(C0650k c0650k) {
            this.f1342a.m1569a(this.f1342a.f1358g.getCurrentPosition());
        }
    }

    /* renamed from: com.facebook.ads.internal.util.ab.5 */
    class C07705 extends C0453p<C0641b> {
        final /* synthetic */ ab f1343a;

        C07705(ab abVar) {
            this.f1343a = abVar;
        }

        public Class<C0641b> m1555a() {
            return C0641b.class;
        }

        public void m1557a(C0641b c0641b) {
            this.f1343a.m1572b(this.f1343a.f1358g.getCurrentPosition());
        }
    }

    /* renamed from: com.facebook.ads.internal.util.ab.6 */
    class C07716 extends C0453p<C0651l> {
        final /* synthetic */ ab f1344a;

        C07716(ab abVar) {
            this.f1344a = abVar;
        }

        public Class<C0651l> m1558a() {
            return C0651l.class;
        }

        public void m1560a(C0651l c0651l) {
            this.f1344a.m1570a(c0651l.m1207a(), c0651l.m1208b());
        }
    }

    public ab(@NonNull Context context, @NonNull C0783h c0783h, @NonNull C0726m c0726m, @NonNull String str) {
        super(context, c0783h, c0726m, str);
        this.f1352a = new C07661(this);
        this.f1353b = new C07672(this);
        this.f1354c = new C07683(this);
        this.f1355d = new C07694(this);
        this.f1356e = new C07705(this);
        this.f1357f = new C07716(this);
        this.f1359h = false;
        this.f1358g = c0726m;
        c0726m.getEventBus().m915a(this.f1355d);
        c0726m.getEventBus().m915a(this.f1352a);
        c0726m.getEventBus().m915a(this.f1354c);
        c0726m.getEventBus().m915a(this.f1353b);
        c0726m.getEventBus().m915a(this.f1356e);
        c0726m.getEventBus().m915a(this.f1357f);
    }

    public ab(@NonNull Context context, @NonNull C0783h c0783h, @NonNull C0726m c0726m, @NonNull String str, @Nullable Bundle bundle) {
        super(context, c0783h, c0726m, str, bundle);
        this.f1352a = new C07661(this);
        this.f1353b = new C07672(this);
        this.f1354c = new C07683(this);
        this.f1355d = new C07694(this);
        this.f1356e = new C07705(this);
        this.f1357f = new C07716(this);
        this.f1359h = false;
        this.f1358g = c0726m;
        c0726m.getEventBus().m915a(this.f1355d);
        c0726m.getEventBus().m915a(this.f1352a);
        c0726m.getEventBus().m915a(this.f1354c);
        c0726m.getEventBus().m915a(this.f1353b);
        c0726m.getEventBus().m915a(this.f1356e);
    }

    public void m1579a() {
        this.f1358g.getEventBus().m916b(this.f1355d);
        this.f1358g.getEventBus().m916b(this.f1352a);
        this.f1358g.getEventBus().m916b(this.f1354c);
        this.f1358g.getEventBus().m916b(this.f1353b);
        this.f1358g.getEventBus().m916b(this.f1356e);
    }
}
