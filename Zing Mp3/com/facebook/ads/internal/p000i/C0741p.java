package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import com.google.android.exoplayer.C0989C;

/* renamed from: com.facebook.ads.internal.i.p */
public class C0741p extends ImageView {
    public C0741p(Context context) {
        super(context);
    }

    protected void onMeasure(int i, int i2) {
        if (MeasureSpec.getMode(i) == C0989C.ENCODING_PCM_32BIT) {
            i2 = i;
        } else if (MeasureSpec.getMode(i2) == C0989C.ENCODING_PCM_32BIT) {
            i = i2;
        }
        super.onMeasure(i, i2);
    }
}
