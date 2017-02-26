package com.example.duy.calculator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;

public class RevealView extends View {
    float mCenterX;
    float mCenterY;
    final Paint mPaint;
    float mRadius;
    Path mRevealPath;
    View mTarget;

    public RevealView(Context context) {
        this(context, null);
    }

    public RevealView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPaint = new Paint();
        this.mRevealPath = new Path();
    }

    public void setRevealColor(int color) {
        this.mPaint.setColor(color);
    }

    public void onDraw(Canvas canvas) {
        if (this.mTarget == null) {
            canvas.drawColor(this.mPaint.getColor());
            return;
        }
        int state = canvas.save();
        this.mRevealPath.reset();
        this.mRevealPath.addCircle(this.mCenterX, this.mCenterY, this.mRadius, Direction.CW);
        canvas.drawPath(this.mRevealPath, this.mPaint);
        canvas.restoreToCount(state);
    }
}
