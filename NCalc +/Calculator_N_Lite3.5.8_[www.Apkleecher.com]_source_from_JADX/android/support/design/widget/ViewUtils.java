package android.support.design.widget;

import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import io.github.kexanie.library.R;
import org.apache.commons.math4.random.ValueServer;

class ViewUtils {
    static final Creator DEFAULT_ANIMATOR_CREATOR;

    /* renamed from: android.support.design.widget.ViewUtils.1 */
    static class C00581 implements Creator {
        C00581() {
        }

        public ValueAnimatorCompat createAnimator() {
            return new ValueAnimatorCompat(VERSION.SDK_INT >= 12 ? new ValueAnimatorCompatImplHoneycombMr1() : new ValueAnimatorCompatImplGingerbread());
        }
    }

    ViewUtils() {
    }

    static {
        DEFAULT_ANIMATOR_CREATOR = new C00581();
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }

    static boolean objectEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return Mode.SRC_OVER;
            case ValueServer.CONSTANT_MODE /*5*/:
                return Mode.SRC_IN;
            case R.styleable.TextAppearance_textAllCaps /*9*/:
                return Mode.SRC_ATOP;
            case R.styleable.SearchView_suggestionRowLayout /*14*/:
                return Mode.MULTIPLY;
            case R.styleable.Toolbar_titleMarginStart /*15*/:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }
}
