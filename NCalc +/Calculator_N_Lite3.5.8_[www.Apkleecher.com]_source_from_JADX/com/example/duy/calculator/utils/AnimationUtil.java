package com.example.duy.calculator.utils;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;

@Deprecated
public class AnimationUtil {
    public static final int DEFAULT_FADE_DURATION = 200;
    public static final int DEFAULT_SHRINK_GROW_DURATION = 200;

    static class 1 implements AnimatorListener {
        final /* synthetic */ View val$view;

        1(View view) {
            this.val$view = view;
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            this.val$view.setVisibility(8);
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    static class 2 implements AnimationListener {
        final /* synthetic */ ScaleAnimation val$growAnim;
        final /* synthetic */ View val$view1;
        final /* synthetic */ View val$view2;

        2(View view, View view2, ScaleAnimation scaleAnimation) {
            this.val$view1 = view;
            this.val$view2 = view2;
            this.val$growAnim = scaleAnimation;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            this.val$view1.setVisibility(4);
            this.val$view2.setVisibility(0);
            this.val$view2.startAnimation(this.val$growAnim);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public static void fadeIn(View view, int duration) {
        if (view.getVisibility() != 0) {
            view.setAlpha(0.0f);
            view.setVisibility(0);
            ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f});
            anim.setDuration((long) duration);
            anim.start();
        }
    }

    public static void fadeIn(View view) {
        fadeIn(view, DEFAULT_SHRINK_GROW_DURATION);
    }

    public static void fadeOut(View view, int duration) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0f, 0.0f});
        anim.addListener(new 1(view));
        anim.setDuration(200);
        anim.start();
    }

    public static void fadeOut(View view) {
        fadeOut(view, DEFAULT_SHRINK_GROW_DURATION);
    }

    public static void shrinkAndGrow(View view1, View view2, int duration) {
        ScaleAnimation shrinkAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, (float) (view1.getWidth() / 2), (float) (view1.getHeight() / 2));
        ScaleAnimation growAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, (float) (view2.getWidth() / 2), (float) (view2.getHeight() / 2));
        shrinkAnim.setAnimationListener(new 2(view1, view2, growAnim));
        shrinkAnim.setDuration((long) duration);
        growAnim.setDuration((long) duration);
        view1.startAnimation(shrinkAnim);
    }

    public static void shrinkAndGrow(View view1, View view2) {
        shrinkAndGrow(view1, view2, DEFAULT_SHRINK_GROW_DURATION);
    }
}
