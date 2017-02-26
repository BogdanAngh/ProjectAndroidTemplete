package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.widget.RelativeLayout;

/* renamed from: com.facebook.ads.internal.i.o */
public class C0740o extends RelativeLayout {
    private int f1230a;
    private int f1231b;

    public C0740o(Context context) {
        super(context);
        this.f1230a = 0;
        this.f1231b = 0;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.f1231b > 0 && getMeasuredWidth() > this.f1231b) {
            setMeasuredDimension(this.f1231b, getMeasuredHeight());
        } else if (getMeasuredWidth() < this.f1230a) {
            setMeasuredDimension(this.f1230a, getMeasuredHeight());
        }
    }

    protected void setMaxWidth(int i) {
        this.f1231b = i;
    }

    public void setMinWidth(int i) {
        this.f1230a = i;
    }
}
