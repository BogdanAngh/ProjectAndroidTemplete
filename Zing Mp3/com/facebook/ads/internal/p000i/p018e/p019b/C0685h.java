package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.ads.AdError;
import com.facebook.ads.C0411R;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0650k;
import com.facebook.ads.internal.p005f.C0453p;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.facebook.ads.internal.i.e.b.h */
public class C0685h extends C0638m {
    @NonNull
    private final C0684a f1024b;
    private final int f1025c;
    @NonNull
    private final AtomicBoolean f1026d;
    @NonNull
    private final C0453p<C0650k> f1027e;

    /* renamed from: com.facebook.ads.internal.i.e.b.h.1 */
    class C06821 extends C0453p<C0650k> {
        final /* synthetic */ C0685h f1018a;

        C06821(C0685h c0685h) {
            this.f1018a = c0685h;
        }

        public Class<C0650k> m1266a() {
            return C0650k.class;
        }

        public void m1268a(C0650k c0650k) {
            if (!this.f1018a.f1026d.get()) {
                int b = this.f1018a.f1025c - (this.f1018a.getVideoView().getCurrentPosition() / AdError.NETWORK_ERROR_CODE);
                if (b > 0) {
                    this.f1018a.f1024b.setText(this.f1018a.getResources().getString(C0411R.string.com_facebook_skip_ad_in) + ' ' + b);
                    return;
                }
                this.f1018a.f1024b.setText(this.f1018a.getResources().getString(C0411R.string.com_facebook_skip_ad));
                this.f1018a.f1026d.set(true);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.h.2 */
    class C06832 implements OnClickListener {
        final /* synthetic */ C0726m f1019a;
        final /* synthetic */ C0685h f1020b;

        C06832(C0685h c0685h, C0726m c0726m) {
            this.f1020b = c0685h;
            this.f1019a = c0726m;
        }

        public void onClick(View view) {
            if (this.f1020b.f1026d.get()) {
                this.f1019a.m1397f();
            } else {
                Log.i("SkipPlugin", "User clicked skip before the ads is allowed to skip.");
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.h.a */
    private static class C0684a extends TextView {
        private final Paint f1021a;
        private final Paint f1022b;
        private final RectF f1023c;

        public C0684a(Context context) {
            super(context);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            setBackgroundColor(0);
            setTextColor(-3355444);
            setPadding((int) (displayMetrics.density * 9.0f), (int) (displayMetrics.density * 5.0f), (int) (displayMetrics.density * 9.0f), (int) (displayMetrics.density * 5.0f));
            setTextSize(18.0f);
            this.f1021a = new Paint();
            this.f1021a.setStyle(Style.STROKE);
            this.f1021a.setColor(-10066330);
            this.f1021a.setStrokeWidth(1.0f);
            this.f1021a.setAntiAlias(true);
            this.f1022b = new Paint();
            this.f1022b.setStyle(Style.FILL);
            this.f1022b.setColor(-1895825408);
            this.f1023c = new RectF();
        }

        protected void onDraw(Canvas canvas) {
            if (getText().length() != 0) {
                int width = getWidth();
                int height = getHeight();
                this.f1023c.set((float) null, (float) null, (float) width, (float) height);
                canvas.drawRoundRect(this.f1023c, 6.0f, 6.0f, this.f1022b);
                this.f1023c.set((float) 2, (float) 2, (float) (width - 2), (float) (height - 2));
                canvas.drawRoundRect(this.f1023c, 6.0f, 6.0f, this.f1021a);
                super.onDraw(canvas);
            }
        }
    }

    public C0685h(@NonNull Context context, @NonNull int i) {
        super(context);
        this.f1027e = new C06821(this);
        this.f1025c = i;
        this.f1026d = new AtomicBoolean(false);
        this.f1024b = new C0684a(context);
        this.f1024b.setText(getResources().getString(C0411R.string.com_facebook_skip_ad_in) + ' ' + i);
        addView(this.f1024b, new LayoutParams(-2, -2));
    }

    public void m1272a(C0726m c0726m) {
        c0726m.getEventBus().m915a(this.f1027e);
        this.f1024b.setOnClickListener(new C06832(this, c0726m));
    }

    @NonNull
    public boolean m1273a() {
        return this.f1026d.get();
    }
}
