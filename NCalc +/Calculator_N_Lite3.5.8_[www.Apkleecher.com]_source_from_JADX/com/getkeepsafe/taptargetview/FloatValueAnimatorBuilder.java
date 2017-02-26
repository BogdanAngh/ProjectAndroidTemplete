package com.getkeepsafe.taptargetview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

class FloatValueAnimatorBuilder {
    final ValueAnimator animator;
    EndListener endListener;

    class 1 implements AnimatorUpdateListener {
        final /* synthetic */ UpdateListener val$listener;

        1(UpdateListener updateListener) {
            this.val$listener = updateListener;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            this.val$listener.onUpdate(((Float) animation.getAnimatedValue()).floatValue());
        }
    }

    class 2 implements AnimatorListener {
        2() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            FloatValueAnimatorBuilder.this.endListener.onEnd();
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    interface EndListener {
        void onEnd();
    }

    interface UpdateListener {
        void onUpdate(float f);
    }

    protected FloatValueAnimatorBuilder() {
        this(false);
    }

    protected FloatValueAnimatorBuilder(boolean reverse) {
        if (reverse) {
            this.animator = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        } else {
            this.animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        }
    }

    public FloatValueAnimatorBuilder delayBy(long millis) {
        this.animator.setStartDelay(millis);
        return this;
    }

    public FloatValueAnimatorBuilder duration(long millis) {
        this.animator.setDuration(millis);
        return this;
    }

    public FloatValueAnimatorBuilder interpolator(TimeInterpolator lerper) {
        this.animator.setInterpolator(lerper);
        return this;
    }

    public FloatValueAnimatorBuilder repeat(int times) {
        this.animator.setRepeatCount(times);
        return this;
    }

    public FloatValueAnimatorBuilder onUpdate(UpdateListener listener) {
        this.animator.addUpdateListener(new 1(listener));
        return this;
    }

    public FloatValueAnimatorBuilder onEnd(EndListener listener) {
        this.endListener = listener;
        return this;
    }

    public ValueAnimator build() {
        if (this.endListener != null) {
            this.animator.addListener(new 2());
        }
        return this.animator;
    }
}
