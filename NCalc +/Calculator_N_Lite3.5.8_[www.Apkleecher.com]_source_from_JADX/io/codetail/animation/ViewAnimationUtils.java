package io.codetail.animation;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.view.View;
import io.codetail.animation.ViewRevealManager.RevealValues;

public final class ViewAnimationUtils {
    private static final boolean DEBUG = false;
    private static final boolean LOLLIPOP_PLUS;

    static {
        LOLLIPOP_PLUS = VERSION.SDK_INT >= 21;
    }

    public static Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
        return createCircularReveal(view, centerX, centerY, startRadius, endRadius, 1);
    }

    public static Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius, int layerType) {
        if (view.getParent() instanceof RevealViewGroup) {
            ViewRevealManager rm = ((RevealViewGroup) view.getParent()).getViewRevealManager();
            if (!rm.hasCustomerRevealAnimator() && LOLLIPOP_PLUS) {
                return android.view.ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
            }
            RevealValues viewData = new RevealValues(view, centerX, centerY, startRadius, endRadius);
            Animator animator = rm.createAnimator(viewData);
            if (layerType == view.getLayerType()) {
                return animator;
            }
            animator.addListener(new ChangeViewLayerTypeAdapter(viewData, layerType));
            return animator;
        }
        throw new IllegalArgumentException("Parent must be instance of RevealViewGroup");
    }
}
