package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.exoplayer.C0989C;

/* renamed from: com.facebook.ads.internal.i.l */
public class C0736l extends TextView {
    private int f1223a;
    private float f1224b;
    private float f1225c;

    public C0736l(Context context, int i) {
        super(context);
        this.f1225c = 8.0f;
        setMaxLines(i);
        setEllipsize(TruncateAt.END);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.setMaxLines(this.f1223a + 1);
        super.setTextSize(this.f1224b);
        int i5 = i3 - i;
        int i6 = i4 - i2;
        measure(MeasureSpec.makeMeasureSpec(i5, C0989C.ENCODING_PCM_32BIT), MeasureSpec.makeMeasureSpec(i6, 0));
        if (getMeasuredHeight() > i6) {
            float f = this.f1224b;
            while (f > this.f1225c) {
                f -= 0.5f;
                super.setTextSize(f);
                measure(MeasureSpec.makeMeasureSpec(i5, C0989C.ENCODING_PCM_32BIT), 0);
                if (getMeasuredHeight() <= i6 && getLineCount() <= this.f1223a) {
                    break;
                }
            }
        }
        super.setMaxLines(this.f1223a);
        setMeasuredDimension(i5, i6);
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setMaxLines(int i) {
        this.f1223a = i;
        super.setMaxLines(i);
    }

    public void setMinTextSize(float f) {
        this.f1225c = f;
    }

    public void setTextSize(float f) {
        this.f1224b = f;
        super.setTextSize(f);
    }
}
