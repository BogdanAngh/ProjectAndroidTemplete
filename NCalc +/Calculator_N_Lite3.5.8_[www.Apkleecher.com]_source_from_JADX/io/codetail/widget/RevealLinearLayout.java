package io.codetail.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import io.codetail.animation.RevealViewGroup;
import io.codetail.animation.ViewRevealManager;

public class RevealLinearLayout extends LinearLayout implements RevealViewGroup {
    private ViewRevealManager manager;

    public RevealLinearLayout(Context context) {
        this(context, null);
    }

    public RevealLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.manager = new ViewRevealManager();
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        try {
            canvas.save();
            this.manager.transform(canvas, child);
            boolean drawChild = super.drawChild(canvas, child, drawingTime);
            return drawChild;
        } finally {
            canvas.restore();
        }
    }

    public ViewRevealManager getViewRevealManager() {
        return this.manager;
    }
}
