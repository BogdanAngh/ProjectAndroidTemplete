package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.exoplayer.C0989C;

/* renamed from: com.facebook.ads.internal.i.n */
public class C0739n extends TextView {
    private float f1228a;
    private float f1229b;

    public C0739n(Context context) {
        super(context);
        this.f1229b = 8.0f;
        super.setSingleLine();
        super.setMaxLines(1);
        this.f1228a = getTextSize() / context.getResources().getDisplayMetrics().density;
        setEllipsize(TruncateAt.END);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        for (float f = this.f1228a; f >= this.f1229b; f -= 0.5f) {
            super.setTextSize(f);
            measure(0, 0);
            if (getMeasuredWidth() <= i5) {
                break;
            }
        }
        if (getMeasuredWidth() > i5) {
            measure(MeasureSpec.makeMeasureSpec(measuredWidth, C0989C.ENCODING_PCM_32BIT), MeasureSpec.makeMeasureSpec(measuredHeight, C0989C.ENCODING_PCM_32BIT));
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setMaxLines(int i) {
    }

    public void setMinTextSize(float f) {
        if (f <= this.f1228a) {
            this.f1229b = f;
        }
    }

    public void setSingleLine(boolean z) {
    }

    public void setTextSize(float f) {
        this.f1228a = f;
        super.setTextSize(f);
    }
}
