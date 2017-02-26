package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0648i;
import com.facebook.ads.internal.p005f.C0453p;

/* renamed from: com.facebook.ads.internal.i.e.b.i */
public class C0687i extends C0638m {
    @NonNull
    private final ProgressBar f1029b;
    @NonNull
    private final C0453p<C0648i> f1030c;

    /* renamed from: com.facebook.ads.internal.i.e.b.i.1 */
    class C06861 extends C0453p<C0648i> {
        final /* synthetic */ C0687i f1028a;

        C06861(C0687i c0687i) {
            this.f1028a = c0687i;
        }

        public Class<C0648i> m1274a() {
            return C0648i.class;
        }

        public void m1276a(C0648i c0648i) {
            this.f1028a.setVisibility(8);
        }
    }

    public C0687i(Context context) {
        this(context, null);
    }

    public C0687i(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public C0687i(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f1030c = new C06861(this);
        int applyDimension = (int) TypedValue.applyDimension(1, 40.0f, getResources().getDisplayMetrics());
        this.f1029b = new ProgressBar(getContext());
        this.f1029b.setIndeterminate(true);
        this.f1029b.getIndeterminateDrawable().setColorFilter(-1, Mode.SRC_IN);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(applyDimension, applyDimension);
        layoutParams.addRule(13);
        addView(this.f1029b, layoutParams);
    }

    protected void m1277a(C0726m c0726m) {
        setVisibility(0);
        c0726m.getEventBus().m915a(this.f1030c);
    }
}
