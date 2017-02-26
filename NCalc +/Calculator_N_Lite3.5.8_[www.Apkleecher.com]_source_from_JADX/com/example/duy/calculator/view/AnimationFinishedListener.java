package com.example.duy.calculator.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;

public abstract class AnimationFinishedListener implements AnimatorListener {
    public abstract void onAnimationFinished();

    public void onAnimationCancel(Animator animation) {
        onAnimationFinished();
    }

    public void onAnimationRepeat(Animator animation) {
    }

    public void onAnimationStart(Animator animation) {
    }

    public void onAnimationEnd(Animator animation) {
        onAnimationFinished();
    }
}
