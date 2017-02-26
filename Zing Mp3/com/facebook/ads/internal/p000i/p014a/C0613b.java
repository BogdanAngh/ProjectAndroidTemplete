package com.facebook.ads.internal.p000i.p014a;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.google.android.exoplayer.util.NalUnitUtil;

@TargetApi(19)
/* renamed from: com.facebook.ads.internal.i.a.b */
public class C0613b extends ProgressBar {
    private static final int f888a;
    private static final int f889b;
    private Rect f890c;
    private Paint f891d;

    static {
        f888a = Color.argb(26, 0, 0, 0);
        f889b = Color.rgb(88, 144, NalUnitUtil.EXTENDED_SAR);
    }

    public C0613b(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m1142a();
    }

    private void m1142a() {
        setIndeterminate(false);
        setMax(100);
        setProgressDrawable(m1143b());
        this.f890c = new Rect();
        this.f891d = new Paint();
        this.f891d.setStyle(Style.FILL);
        this.f891d.setColor(f888a);
    }

    private Drawable m1143b() {
        ColorDrawable colorDrawable = new ColorDrawable(0);
        ClipDrawable clipDrawable = new ClipDrawable(new ColorDrawable(f889b), 3, 1);
        return new LayerDrawable(new Drawable[]{colorDrawable, clipDrawable});
    }

    protected synchronized void onDraw(Canvas canvas) {
        canvas.drawRect(this.f890c, this.f891d);
        super.onDraw(canvas);
    }

    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.f890c.set(0, 0, getMeasuredWidth(), 2);
    }

    public synchronized void setProgress(int i) {
        super.setProgress(i == 100 ? 0 : Math.max(i, 5));
    }
}
