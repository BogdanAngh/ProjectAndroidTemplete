package com.facebook.ads.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

/* renamed from: com.facebook.ads.internal.g */
public class C0550g extends View {
    private Paint f777a;
    private Paint f778b;
    private Paint f779c;
    private int f780d;
    private boolean f781e;

    public C0550g(Context context) {
        this(context, 60, true);
    }

    public C0550g(Context context, int i, boolean z) {
        super(context);
        this.f780d = i;
        this.f781e = z;
        if (z) {
            this.f777a = new Paint();
            this.f777a.setColor(-3355444);
            this.f777a.setStyle(Style.STROKE);
            this.f777a.setStrokeWidth(3.0f);
            this.f777a.setAntiAlias(true);
            this.f778b = new Paint();
            this.f778b.setColor(-1287371708);
            this.f778b.setStyle(Style.FILL);
            this.f778b.setAntiAlias(true);
            this.f779c = new Paint();
            this.f779c.setColor(-1);
            this.f779c.setStyle(Style.STROKE);
            this.f779c.setStrokeWidth(6.0f);
            this.f779c.setAntiAlias(true);
        }
        m927a();
    }

    private void m927a() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (((float) this.f780d) * displayMetrics.density), (int) (displayMetrics.density * ((float) this.f780d)));
        layoutParams.addRule(10);
        layoutParams.addRule(11);
        setLayoutParams(layoutParams);
    }

    protected void onDraw(Canvas canvas) {
        if (this.f781e) {
            if (canvas.isHardwareAccelerated() && VERSION.SDK_INT < 17) {
                setLayerType(1, null);
            }
            int min = Math.min(canvas.getWidth(), canvas.getHeight());
            int i = min / 2;
            int i2 = min / 2;
            int i3 = (i * 2) / 3;
            canvas.drawCircle((float) i, (float) i2, (float) i3, this.f777a);
            canvas.drawCircle((float) i, (float) i2, (float) (i3 - 2), this.f778b);
            int i4 = min / 3;
            int i5 = min / 3;
            canvas.drawLine((float) i4, (float) i5, (float) (i4 * 2), (float) (i5 * 2), this.f779c);
            canvas.drawLine((float) (i4 * 2), (float) i5, (float) i4, (float) (i5 * 2), this.f779c);
        }
        super.onDraw(canvas);
    }
}
