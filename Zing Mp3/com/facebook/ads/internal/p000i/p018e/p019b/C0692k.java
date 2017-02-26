package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p021c.C0708f;
import com.facebook.ads.internal.p005f.C0453p;

/* renamed from: com.facebook.ads.internal.i.e.b.k */
public class C0692k extends C0664d {
    @NonNull
    private final C0694l f1038b;
    @NonNull
    private final C0453p<C0644e> f1039c;
    @NonNull
    private final C0453p<C0646g> f1040d;
    @NonNull
    private final Paint f1041e;
    @NonNull
    private final RectF f1042f;

    /* renamed from: com.facebook.ads.internal.i.e.b.k.1 */
    class C06891 extends C0453p<C0644e> {
        final /* synthetic */ C0692k f1034a;

        C06891(C0692k c0692k) {
            this.f1034a = c0692k;
        }

        public Class<C0644e> m1278a() {
            return C0644e.class;
        }

        public void m1280a(C0644e c0644e) {
            this.f1034a.f1038b.setChecked(true);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.k.2 */
    class C06902 extends C0453p<C0646g> {
        final /* synthetic */ C0692k f1035a;

        C06902(C0692k c0692k) {
            this.f1035a = c0692k;
        }

        public Class<C0646g> m1281a() {
            return C0646g.class;
        }

        public void m1283a(C0646g c0646g) {
            this.f1035a.f1038b.setChecked(false);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.k.3 */
    class C06913 implements OnTouchListener {
        final /* synthetic */ C0726m f1036a;
        final /* synthetic */ C0692k f1037b;

        C06913(C0692k c0692k, C0726m c0726m) {
            this.f1037b = c0692k;
            this.f1036a = c0726m;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (this.f1036a.getState() == C0708f.PREPARED) {
                this.f1036a.m1395d();
            } else if (this.f1036a.getState() == C0708f.PAUSED) {
                this.f1036a.m1395d();
            } else if (this.f1036a.getState() != C0708f.STARTED) {
                return false;
            } else {
                this.f1036a.m1396e();
            }
            return true;
        }
    }

    public C0692k(Context context) {
        super(context);
        this.f1039c = new C06891(this);
        this.f1040d = new C06902(this);
        this.f1038b = new C0694l(context);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (displayMetrics.density * 50.0f), (int) (displayMetrics.density * 50.0f));
        layoutParams.addRule(13);
        this.f1038b.setLayoutParams(layoutParams);
        this.f1041e = new Paint();
        this.f1041e.setStyle(Style.FILL);
        this.f1041e.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f1041e.setAlpha(119);
        this.f1042f = new RectF();
        setBackgroundColor(0);
        addView(this.f1038b);
        setGravity(17);
        layoutParams = new RelativeLayout.LayoutParams((int) (((double) displayMetrics.density) * 75.0d), (int) (((double) displayMetrics.density) * 75.0d));
        layoutParams.addRule(13);
        setLayoutParams(layoutParams);
    }

    protected void m1285a(C0726m c0726m) {
        c0726m.getEventBus().m915a(this.f1039c);
        c0726m.getEventBus().m915a(this.f1040d);
        this.f1038b.setOnTouchListener(new C06913(this, c0726m));
        super.m1231a(c0726m);
    }

    protected void onDraw(Canvas canvas) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.f1042f.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        canvas.drawRoundRect(this.f1042f, 5.0f * f, f * 5.0f, this.f1041e);
        super.onDraw(canvas);
    }
}
