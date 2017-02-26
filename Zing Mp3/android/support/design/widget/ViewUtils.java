package android.support.design.widget;

import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import com.mp3download.zingmp3.C1569R;

class ViewUtils {
    static final Creator DEFAULT_ANIMATOR_CREATOR;

    /* renamed from: android.support.design.widget.ViewUtils.1 */
    static class C00611 implements Creator {
        C00611() {
        }

        public ValueAnimatorCompat createAnimator() {
            return new ValueAnimatorCompat(VERSION.SDK_INT >= 12 ? new ValueAnimatorCompatImplHoneycombMr1() : new ValueAnimatorCompatImplGingerbread());
        }
    }

    ViewUtils() {
    }

    static {
        DEFAULT_ANIMATOR_CREATOR = new C00611();
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }

    static boolean objectEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return Mode.SRC_OVER;
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return Mode.SRC_IN;
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                return Mode.SRC_ATOP;
            case C1569R.styleable.Toolbar_titleMargin /*14*/:
                return Mode.MULTIPLY;
            case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }
}
