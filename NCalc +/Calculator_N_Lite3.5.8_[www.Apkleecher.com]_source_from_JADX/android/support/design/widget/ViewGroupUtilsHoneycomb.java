package android.support.design.widget;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import edu.jas.vector.GenVectorModul;

@TargetApi(11)
@RequiresApi(11)
class ViewGroupUtilsHoneycomb {
    private static final ThreadLocal<Matrix> sMatrix;
    private static final ThreadLocal<RectF> sRectF;

    ViewGroupUtilsHoneycomb() {
    }

    static {
        sMatrix = new ThreadLocal();
        sRectF = new ThreadLocal();
    }

    public static void offsetDescendantRect(ViewGroup group, View child, Rect rect) {
        Matrix m = (Matrix) sMatrix.get();
        if (m == null) {
            m = new Matrix();
            sMatrix.set(m);
        } else {
            m.reset();
        }
        offsetDescendantMatrix(group, child, m);
        RectF rectF = (RectF) sRectF.get();
        if (rectF == null) {
            rectF = new RectF();
            sRectF.set(rectF);
        }
        rectF.set(rect);
        m.mapRect(rectF);
        rect.set((int) (rectF.left + GenVectorModul.DEFAULT_DENSITY), (int) (rectF.top + GenVectorModul.DEFAULT_DENSITY), (int) (rectF.right + GenVectorModul.DEFAULT_DENSITY), (int) (rectF.bottom + GenVectorModul.DEFAULT_DENSITY));
    }

    static void offsetDescendantMatrix(ViewParent target, View view, Matrix m) {
        ViewParent parent = view.getParent();
        if ((parent instanceof View) && parent != target) {
            View vp = (View) parent;
            offsetDescendantMatrix(target, vp, m);
            m.preTranslate((float) (-vp.getScrollX()), (float) (-vp.getScrollY()));
        }
        m.preTranslate((float) view.getLeft(), (float) view.getTop());
        if (!view.getMatrix().isIdentity()) {
            m.preConcat(view.getMatrix());
        }
    }
}
