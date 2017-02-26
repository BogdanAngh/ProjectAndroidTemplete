package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.widget.Button;

/* renamed from: com.facebook.ads.internal.i.e.b.l */
public class C0694l extends Button {
    private final Path f1044a;
    private final Path f1045b;
    private final Paint f1046c;
    private final Path f1047d;
    private boolean f1048e;

    /* renamed from: com.facebook.ads.internal.i.e.b.l.1 */
    class C06931 extends Paint {
        final /* synthetic */ C0694l f1043a;

        C06931(C0694l c0694l) {
            this.f1043a = c0694l;
            setStyle(Style.FILL_AND_STROKE);
            setStrokeCap(Cap.ROUND);
            setStrokeWidth(3.0f);
            setAntiAlias(true);
            setColor(-1);
        }
    }

    public C0694l(Context context) {
        super(context);
        this.f1048e = false;
        this.f1044a = new Path();
        this.f1045b = new Path();
        this.f1047d = new Path();
        this.f1046c = new C06931(this);
        setClickable(true);
        setBackgroundColor(0);
    }

    protected void onDraw(@NonNull Canvas canvas) {
        if (canvas.isHardwareAccelerated() && VERSION.SDK_INT < 17) {
            setLayerType(1, null);
        }
        float max = ((float) Math.max(canvas.getWidth(), canvas.getHeight())) / 100.0f;
        if (this.f1048e) {
            this.f1047d.rewind();
            this.f1047d.moveTo(26.5f * max, 15.5f * max);
            this.f1047d.lineTo(26.5f * max, 84.5f * max);
            this.f1047d.lineTo(82.5f * max, 50.5f * max);
            this.f1047d.lineTo(26.5f * max, max * 15.5f);
            this.f1047d.close();
            canvas.drawPath(this.f1047d, this.f1046c);
        } else {
            this.f1044a.rewind();
            this.f1044a.moveTo(29.0f * max, 21.0f * max);
            this.f1044a.lineTo(29.0f * max, 79.0f * max);
            this.f1044a.lineTo(45.0f * max, 79.0f * max);
            this.f1044a.lineTo(45.0f * max, 21.0f * max);
            this.f1044a.lineTo(29.0f * max, 21.0f * max);
            this.f1044a.close();
            this.f1045b.rewind();
            this.f1045b.moveTo(55.0f * max, 21.0f * max);
            this.f1045b.lineTo(55.0f * max, 79.0f * max);
            this.f1045b.lineTo(71.0f * max, 79.0f * max);
            this.f1045b.lineTo(71.0f * max, 21.0f * max);
            this.f1045b.lineTo(55.0f * max, max * 21.0f);
            this.f1045b.close();
            canvas.drawPath(this.f1044a, this.f1046c);
            canvas.drawPath(this.f1045b, this.f1046c);
        }
        super.onDraw(canvas);
    }

    public void setChecked(boolean z) {
        this.f1048e = z;
        refreshDrawableState();
        invalidate();
    }
}
