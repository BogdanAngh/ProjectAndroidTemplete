package com.google.android.exoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public final class AspectRatioFrameLayout extends FrameLayout {
    private static final float MAX_ASPECT_RATIO_DEFORMATION_FRACTION = 0.01f;
    private float videoAspectRatio;

    public AspectRatioFrameLayout(Context context) {
        super(context);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAspectRatio(float widthHeightRatio) {
        if (this.videoAspectRatio != widthHeightRatio) {
            this.videoAspectRatio = widthHeightRatio;
            requestLayout();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.videoAspectRatio != 0.0f) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            float aspectDeformation = (this.videoAspectRatio / (((float) width) / ((float) height))) - 1.0f;
            if (Math.abs(aspectDeformation) > MAX_ASPECT_RATIO_DEFORMATION_FRACTION) {
                if (aspectDeformation > 0.0f) {
                    height = (int) (((float) width) / this.videoAspectRatio);
                } else {
                    width = (int) (((float) height) * this.videoAspectRatio);
                }
                super.onMeasure(MeasureSpec.makeMeasureSpec(width, C0989C.ENCODING_PCM_32BIT), MeasureSpec.makeMeasureSpec(height, C0989C.ENCODING_PCM_32BIT));
            }
        }
    }
}
