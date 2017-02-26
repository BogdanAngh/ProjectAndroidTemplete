package com.nineoldandroids.animation;

import android.view.View;
import com.example.duy.calculator.math_eval.Constants;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;
import edu.jas.ps.UnivPowerSeriesRing;

final class PreHoneycombCompat {
    static Property<View, Float> ALPHA;
    static Property<View, Float> PIVOT_X;
    static Property<View, Float> PIVOT_Y;
    static Property<View, Float> ROTATION;
    static Property<View, Float> ROTATION_X;
    static Property<View, Float> ROTATION_Y;
    static Property<View, Float> SCALE_X;
    static Property<View, Float> SCALE_Y;
    static Property<View, Integer> SCROLL_X;
    static Property<View, Integer> SCROLL_Y;
    static Property<View, Float> TRANSLATION_X;
    static Property<View, Float> TRANSLATION_Y;
    static Property<View, Float> X;
    static Property<View, Float> Y;

    static class 10 extends FloatProperty<View> {
        10(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleY());
        }
    }

    static class 11 extends IntProperty<View> {
        11(String x0) {
            super(x0);
        }

        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollX(value);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollX());
        }
    }

    static class 12 extends IntProperty<View> {
        12(String x0) {
            super(x0);
        }

        public void setValue(View object, int value) {
            AnimatorProxy.wrap(object).setScrollY(value);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollY());
        }
    }

    static class 13 extends FloatProperty<View> {
        13(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getX());
        }
    }

    static class 14 extends FloatProperty<View> {
        14(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getY());
        }
    }

    static class 1 extends FloatProperty<View> {
        1(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setAlpha(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getAlpha());
        }
    }

    static class 2 extends FloatProperty<View> {
        2(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotX());
        }
    }

    static class 3 extends FloatProperty<View> {
        3(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotY());
        }
    }

    static class 4 extends FloatProperty<View> {
        4(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationX());
        }
    }

    static class 5 extends FloatProperty<View> {
        5(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationY());
        }
    }

    static class 6 extends FloatProperty<View> {
        6(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotation(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotation());
        }
    }

    static class 7 extends FloatProperty<View> {
        7(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationX());
        }
    }

    static class 8 extends FloatProperty<View> {
        8(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationY());
        }
    }

    static class 9 extends FloatProperty<View> {
        9(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleX());
        }
    }

    static {
        ALPHA = new 1("alpha");
        PIVOT_X = new 2("pivotX");
        PIVOT_Y = new 3("pivotY");
        TRANSLATION_X = new 4("translationX");
        TRANSLATION_Y = new 5("translationY");
        ROTATION = new 6("rotation");
        ROTATION_X = new 7("rotationX");
        ROTATION_Y = new 8("rotationY");
        SCALE_X = new 9("scaleX");
        SCALE_Y = new 10("scaleY");
        SCROLL_X = new 11("scrollX");
        SCROLL_Y = new 12("scrollY");
        X = new 13(UnivPowerSeriesRing.DEFAULT_NAME);
        Y = new 14(Constants.Y);
    }

    private PreHoneycombCompat() {
    }
}
