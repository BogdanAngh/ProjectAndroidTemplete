package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.widget.TextView;

/* renamed from: com.facebook.ads.internal.i.e.b.j */
public class C0688j extends C0664d {
    private final TextView f1031b;
    @NonNull
    private final Paint f1032c;
    @NonNull
    private final RectF f1033d;

    public C0688j(@NonNull Context context, @NonNull String str, @NonNull int i) {
        super(context);
        this.f1031b = new TextView(context);
        this.f1031b.setGravity(17);
        this.f1031b.setText(str);
        this.f1031b.setTextSize((float) i);
        this.f1032c = new Paint();
        this.f1032c.setStyle(Style.FILL);
        this.f1032c.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f1032c.setAlpha(178);
        this.f1033d = new RectF();
        setBackgroundColor(0);
        addView(this.f1031b);
    }

    protected void onDraw(Canvas canvas) {
        this.f1033d.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        canvas.drawRoundRect(this.f1033d, 0.0f, 0.0f, this.f1032c);
        super.onDraw(canvas);
    }
}
