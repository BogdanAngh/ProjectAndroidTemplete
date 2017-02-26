package io.codetail.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Region.Op;
import android.os.Build.VERSION;
import android.util.Property;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class ViewRevealManager {
    public static final ClipRadiusProperty REVEAL;
    private Map<View, RevealValues> targets;

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationStart(Animator animation) {
            ViewRevealManager.getValues(animation).clip(true);
        }

        public void onAnimationEnd(Animator animation) {
            RevealValues values = ViewRevealManager.getValues(animation);
            values.clip(false);
            ViewRevealManager.this.targets.remove(values.target());
        }
    }

    static class ChangeViewLayerTypeAdapter extends AnimatorListenerAdapter {
        private int featuredLayerType;
        private int originalLayerType;
        private RevealValues viewData;

        ChangeViewLayerTypeAdapter(RevealValues viewData, int layerType) {
            this.viewData = viewData;
            this.featuredLayerType = layerType;
            this.originalLayerType = viewData.target.getLayerType();
        }

        public void onAnimationStart(Animator animation) {
            this.viewData.target().setLayerType(this.featuredLayerType, null);
        }

        public void onAnimationCancel(Animator animation) {
            this.viewData.target().setLayerType(this.originalLayerType, null);
        }

        public void onAnimationEnd(Animator animation) {
            this.viewData.target().setLayerType(this.originalLayerType, null);
        }
    }

    private static final class ClipRadiusProperty extends Property<RevealValues, Float> {
        ClipRadiusProperty() {
            super(Float.class, "supportCircularReveal");
        }

        public void set(RevealValues data, Float value) {
            data.radius(value.floatValue());
            data.target().invalidate();
        }

        public Float get(RevealValues v) {
            return Float.valueOf(v.radius());
        }
    }

    public static final class RevealValues {
        private static final Paint debugPaint;
        final int centerX;
        final int centerY;
        boolean clipping;
        final float endRadius;
        Op op;
        Path path;
        float radius;
        final float startRadius;
        View target;

        static {
            debugPaint = new Paint(1);
            debugPaint.setColor(-16711936);
            debugPaint.setStyle(Style.FILL);
            debugPaint.setStrokeWidth(2.0f);
        }

        public RevealValues(View target, int centerX, int centerY, float startRadius, float endRadius) {
            this.path = new Path();
            this.op = Op.REPLACE;
            this.target = target;
            this.centerX = centerX;
            this.centerY = centerY;
            this.startRadius = startRadius;
            this.endRadius = endRadius;
        }

        public void radius(float radius) {
            this.radius = radius;
        }

        public float radius() {
            return this.radius;
        }

        public View target() {
            return this.target;
        }

        public void clip(boolean clipping) {
            this.clipping = clipping;
        }

        public boolean isClipping() {
            return this.clipping;
        }

        public Op op() {
            return this.op;
        }

        public void op(Op op) {
            this.op = op;
        }

        boolean applyTransformation(Canvas canvas, View child) {
            if (child != this.target || !this.clipping) {
                return false;
            }
            this.path.reset();
            this.path.addCircle(child.getX() + ((float) this.centerX), child.getY() + ((float) this.centerY), this.radius, Direction.CW);
            canvas.clipPath(this.path, this.op);
            if (VERSION.SDK_INT >= 21) {
                child.invalidateOutline();
            }
            return true;
        }
    }

    static {
        REVEAL = new ClipRadiusProperty();
    }

    public ViewRevealManager() {
        this.targets = new HashMap();
    }

    protected ObjectAnimator createAnimator(RevealValues data) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(data, REVEAL, new float[]{data.startRadius, data.endRadius});
        animator.addListener(new 1());
        this.targets.put(data.target(), data);
        return animator;
    }

    private static RevealValues getValues(Animator animator) {
        return (RevealValues) ((ObjectAnimator) animator).getTarget();
    }

    public final Map<View, RevealValues> getTargets() {
        return this.targets;
    }

    protected boolean hasCustomerRevealAnimator() {
        return false;
    }

    public boolean isClipped(View child) {
        RevealValues data = (RevealValues) this.targets.get(child);
        return data != null && data.isClipping();
    }

    public boolean transform(Canvas canvas, View child) {
        RevealValues revealData = (RevealValues) this.targets.get(child);
        return revealData != null && revealData.applyTransformation(canvas, child);
    }
}
