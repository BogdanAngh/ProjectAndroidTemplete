package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p005f.C0453p;
import com.facebook.ads.internal.util.C0795p;

/* renamed from: com.facebook.ads.internal.i.e.b.f */
public class C0676f extends C0638m {
    private final ImageView f1006b;
    private final C0453p<C0646g> f1007c;
    private final C0453p<C0641b> f1008d;

    /* renamed from: com.facebook.ads.internal.i.e.b.f.1 */
    class C06741 extends C0453p<C0646g> {
        final /* synthetic */ C0676f f1004a;

        C06741(C0676f c0676f) {
            this.f1004a = c0676f;
        }

        public Class<C0646g> m1249a() {
            return C0646g.class;
        }

        public void m1251a(C0646g c0646g) {
            this.f1004a.setVisibility(8);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.f.2 */
    class C06752 extends C0453p<C0641b> {
        final /* synthetic */ C0676f f1005a;

        C06752(C0676f c0676f) {
            this.f1005a = c0676f;
        }

        public Class<C0641b> m1252a() {
            return C0641b.class;
        }

        public void m1254a(C0641b c0641b) {
            this.f1005a.setVisibility(0);
        }
    }

    public C0676f(Context context) {
        super(context);
        this.f1007c = new C06741(this);
        this.f1008d = new C06752(this);
        this.f1006b = new ImageView(context);
        this.f1006b.setScaleType(ScaleType.CENTER_CROP);
        this.f1006b.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        addView(this.f1006b, new LayoutParams(-1, -1));
    }

    protected void m1255a(@NonNull C0726m c0726m) {
        c0726m.getEventBus().m915a(this.f1007c);
        c0726m.getEventBus().m915a(this.f1008d);
        super.m1199a(c0726m);
    }

    public void setImage(@Nullable String str) {
        if (str == null) {
            setVisibility(8);
            return;
        }
        setVisibility(0);
        new C0795p(this.f1006b).m1663a(str);
    }
}
