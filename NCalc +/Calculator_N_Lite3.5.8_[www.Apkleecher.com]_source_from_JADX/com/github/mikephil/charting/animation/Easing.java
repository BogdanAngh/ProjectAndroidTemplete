package com.github.mikephil.charting.animation;

import com.example.duy.calculator.geom2d.util.Angle2D;
import edu.jas.vector.GenVectorModul;
import io.github.kexanie.library.R;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.matheclipse.core.interfaces.IExpr;

public class Easing {

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption;

        static {
            $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption = new int[EasingOption.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.Linear.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInQuad.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutQuad.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutQuad.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInCubic.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutCubic.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutCubic.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInQuart.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutQuart.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutQuart.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInSine.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutSine.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutSine.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInExpo.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutExpo.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutExpo.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInCirc.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutCirc.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutCirc.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInElastic.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutElastic.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutElastic.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInBack.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutBack.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutBack.ordinal()] = 25;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInBounce.ordinal()] = 26;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseOutBounce.ordinal()] = 27;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[EasingOption.EaseInOutBounce.ordinal()] = 28;
            } catch (NoSuchFieldError e28) {
            }
        }
    }

    private static class EasingFunctions {
        public static final EasingFunction EaseInBack;
        public static final EasingFunction EaseInBounce;
        public static final EasingFunction EaseInCirc;
        public static final EasingFunction EaseInCubic;
        public static final EasingFunction EaseInElastic;
        public static final EasingFunction EaseInExpo;
        public static final EasingFunction EaseInOutBack;
        public static final EasingFunction EaseInOutBounce;
        public static final EasingFunction EaseInOutCirc;
        public static final EasingFunction EaseInOutCubic;
        public static final EasingFunction EaseInOutElastic;
        public static final EasingFunction EaseInOutExpo;
        public static final EasingFunction EaseInOutQuad;
        public static final EasingFunction EaseInOutQuart;
        public static final EasingFunction EaseInOutSine;
        public static final EasingFunction EaseInQuad;
        public static final EasingFunction EaseInQuart;
        public static final EasingFunction EaseInSine;
        public static final EasingFunction EaseOutBack;
        public static final EasingFunction EaseOutBounce;
        public static final EasingFunction EaseOutCirc;
        public static final EasingFunction EaseOutCubic;
        public static final EasingFunction EaseOutElastic;
        public static final EasingFunction EaseOutExpo;
        public static final EasingFunction EaseOutQuad;
        public static final EasingFunction EaseOutQuart;
        public static final EasingFunction EaseOutSine;
        public static final EasingFunction Linear;

        static class 10 implements EasingFunction {
            10() {
            }

            public float getInterpolation(float input) {
                float position = input / GenVectorModul.DEFAULT_DENSITY;
                if (position < 1.0f) {
                    return (((GenVectorModul.DEFAULT_DENSITY * position) * position) * position) * position;
                }
                position -= 2.0f;
                return -0.5f * ((((position * position) * position) * position) - 2.0f);
            }
        }

        static class 11 implements EasingFunction {
            11() {
            }

            public float getInterpolation(float input) {
                return (-((float) Math.cos(((double) input) * Angle2D.M_PI_2))) + 1.0f;
            }
        }

        static class 12 implements EasingFunction {
            12() {
            }

            public float getInterpolation(float input) {
                return (float) Math.sin(((double) input) * Angle2D.M_PI_2);
            }
        }

        static class 13 implements EasingFunction {
            13() {
            }

            public float getInterpolation(float input) {
                return -0.5f * (((float) Math.cos(FastMath.PI * ((double) input))) - 1.0f);
            }
        }

        static class 14 implements EasingFunction {
            14() {
            }

            public float getInterpolation(float input) {
                return input == 0.0f ? 0.0f : (float) Math.pow(2.0d, (double) (10.0f * (input - 1.0f)));
            }
        }

        static class 15 implements EasingFunction {
            15() {
            }

            public float getInterpolation(float input) {
                return input == 1.0f ? 1.0f : -((float) Math.pow(2.0d, (double) ((1.0f + input) * -10.0f)));
            }
        }

        static class 16 implements EasingFunction {
            16() {
            }

            public float getInterpolation(float input) {
                if (input == 0.0f) {
                    return 0.0f;
                }
                if (input == 1.0f) {
                    return 1.0f;
                }
                float position = input / GenVectorModul.DEFAULT_DENSITY;
                if (position < 1.0f) {
                    return ((float) Math.pow(2.0d, (double) (10.0f * (position - 1.0f)))) * GenVectorModul.DEFAULT_DENSITY;
                }
                return ((-((float) Math.pow(2.0d, (double) (-10.0f * (position - 1.0f))))) + 2.0f) * GenVectorModul.DEFAULT_DENSITY;
            }
        }

        static class 17 implements EasingFunction {
            17() {
            }

            public float getInterpolation(float input) {
                return -(((float) Math.sqrt((double) (1.0f - (input * input)))) - 1.0f);
            }
        }

        static class 18 implements EasingFunction {
            18() {
            }

            public float getInterpolation(float input) {
                input -= 1.0f;
                return (float) Math.sqrt((double) (1.0f - (input * input)));
            }
        }

        static class 19 implements EasingFunction {
            19() {
            }

            public float getInterpolation(float input) {
                float position = input / GenVectorModul.DEFAULT_DENSITY;
                if (position < 1.0f) {
                    return -0.5f * (((float) Math.sqrt((double) (1.0f - (position * position)))) - 1.0f);
                }
                position -= 2.0f;
                return (((float) Math.sqrt((double) (1.0f - (position * position)))) + 1.0f) * GenVectorModul.DEFAULT_DENSITY;
            }
        }

        static class 1 implements EasingFunction {
            1() {
            }

            public float getInterpolation(float input) {
                return input;
            }
        }

        static class 20 implements EasingFunction {
            20() {
            }

            public float getInterpolation(float input) {
                if (input == 0.0f) {
                    return 0.0f;
                }
                float position = input;
                if (position == 1.0f) {
                    return 1.0f;
                }
                position -= 1.0f;
                return -(((float) Math.pow(2.0d, (double) (10.0f * position))) * ((float) Math.sin((((double) (position - ((0.3f / 6.2831855f) * ((float) Math.asin(1.0d))))) * Angle2D.M_2PI) / ((double) 1050253722))));
            }
        }

        static class 21 implements EasingFunction {
            21() {
            }

            public float getInterpolation(float input) {
                if (input == 0.0f) {
                    return 0.0f;
                }
                float position = input;
                if (position == 1.0f) {
                    return 1.0f;
                }
                return (((float) Math.pow(2.0d, (double) (-10.0f * position))) * ((float) Math.sin((((double) (position - ((0.3f / 6.2831855f) * ((float) Math.asin(1.0d))))) * Angle2D.M_2PI) / ((double) 1050253722)))) + 1.0f;
            }
        }

        static class 22 implements EasingFunction {
            22() {
            }

            public float getInterpolation(float input) {
                if (input == 0.0f) {
                    return 0.0f;
                }
                float position = input / GenVectorModul.DEFAULT_DENSITY;
                if (position == 2.0f) {
                    return 1.0f;
                }
                float s = (0.45000002f / 6.2831855f) * ((float) Math.asin(1.0d));
                if (position < 1.0f) {
                    position -= 1.0f;
                    return -0.5f * (((float) Math.sin((((double) ((1.0f * position) - s)) * Angle2D.M_2PI) / ((double) 1055286887))) * ((float) Math.pow(2.0d, (double) (10.0f * position))));
                }
                position -= 1.0f;
                return ((((float) Math.pow(2.0d, (double) (-10.0f * position))) * ((float) Math.sin((((double) ((position * 1.0f) - s)) * Angle2D.M_2PI) / ((double) 1055286887)))) * GenVectorModul.DEFAULT_DENSITY) + 1.0f;
            }
        }

        static class 23 implements EasingFunction {
            23() {
            }

            public float getInterpolation(float input) {
                float position = input;
                return (position * position) * ((2.70158f * position) - 1.70158f);
            }
        }

        static class 24 implements EasingFunction {
            24() {
            }

            public float getInterpolation(float input) {
                float position = input - 1.0f;
                return ((position * position) * ((2.70158f * position) + 1.70158f)) + 1.0f;
            }
        }

        static class 25 implements EasingFunction {
            25() {
            }

            public float getInterpolation(float input) {
                float position = input / GenVectorModul.DEFAULT_DENSITY;
                if (position < 1.0f) {
                    float s = 1.70158f * 1.525f;
                    return ((position * position) * (((1.0f + s) * position) - s)) * GenVectorModul.DEFAULT_DENSITY;
                }
                position -= 2.0f;
                s = 1.70158f * 1.525f;
                return (((position * position) * (((1.0f + s) * position) + s)) + 2.0f) * GenVectorModul.DEFAULT_DENSITY;
            }
        }

        static class 26 implements EasingFunction {
            26() {
            }

            public float getInterpolation(float input) {
                return 1.0f - EasingFunctions.EaseOutBounce.getInterpolation(1.0f - input);
            }
        }

        static class 27 implements EasingFunction {
            27() {
            }

            public float getInterpolation(float input) {
                float position = input;
                if (position < 0.36363637f) {
                    return (7.5625f * position) * position;
                }
                if (position < 0.72727275f) {
                    position -= 0.54545456f;
                    return ((7.5625f * position) * position) + 0.75f;
                } else if (position < 0.90909094f) {
                    position -= 0.8181818f;
                    return ((7.5625f * position) * position) + 0.9375f;
                } else {
                    position -= 0.95454544f;
                    return ((7.5625f * position) * position) + 0.984375f;
                }
            }
        }

        static class 28 implements EasingFunction {
            28() {
            }

            public float getInterpolation(float input) {
                if (input < GenVectorModul.DEFAULT_DENSITY) {
                    return EasingFunctions.EaseInBounce.getInterpolation(2.0f * input) * GenVectorModul.DEFAULT_DENSITY;
                }
                return (EasingFunctions.EaseOutBounce.getInterpolation((2.0f * input) - 1.0f) * GenVectorModul.DEFAULT_DENSITY) + GenVectorModul.DEFAULT_DENSITY;
            }
        }

        static class 2 implements EasingFunction {
            2() {
            }

            public float getInterpolation(float input) {
                return input * input;
            }
        }

        static class 3 implements EasingFunction {
            3() {
            }

            public float getInterpolation(float input) {
                return (-input) * (input - 2.0f);
            }
        }

        static class 4 implements EasingFunction {
            4() {
            }

            public float getInterpolation(float input) {
                float position = input / GenVectorModul.DEFAULT_DENSITY;
                if (position < 1.0f) {
                    return (GenVectorModul.DEFAULT_DENSITY * position) * position;
                }
                position -= 1.0f;
                return -0.5f * (((position - 2.0f) * position) - 1.0f);
            }
        }

        static class 5 implements EasingFunction {
            5() {
            }

            public float getInterpolation(float input) {
                return (input * input) * input;
            }
        }

        static class 6 implements EasingFunction {
            6() {
            }

            public float getInterpolation(float input) {
                input -= 1.0f;
                return ((input * input) * input) + 1.0f;
            }
        }

        static class 7 implements EasingFunction {
            7() {
            }

            public float getInterpolation(float input) {
                float position = input / GenVectorModul.DEFAULT_DENSITY;
                if (position < 1.0f) {
                    return ((GenVectorModul.DEFAULT_DENSITY * position) * position) * position;
                }
                position -= 2.0f;
                return (((position * position) * position) + 2.0f) * GenVectorModul.DEFAULT_DENSITY;
            }
        }

        static class 8 implements EasingFunction {
            8() {
            }

            public float getInterpolation(float input) {
                return ((input * input) * input) * input;
            }
        }

        static class 9 implements EasingFunction {
            9() {
            }

            public float getInterpolation(float input) {
                input -= 1.0f;
                return -((((input * input) * input) * input) - 1.0f);
            }
        }

        private EasingFunctions() {
        }

        static {
            Linear = new 1();
            EaseInQuad = new 2();
            EaseOutQuad = new 3();
            EaseInOutQuad = new 4();
            EaseInCubic = new 5();
            EaseOutCubic = new 6();
            EaseInOutCubic = new 7();
            EaseInQuart = new 8();
            EaseOutQuart = new 9();
            EaseInOutQuart = new 10();
            EaseInSine = new 11();
            EaseOutSine = new 12();
            EaseInOutSine = new 13();
            EaseInExpo = new 14();
            EaseOutExpo = new 15();
            EaseInOutExpo = new 16();
            EaseInCirc = new 17();
            EaseOutCirc = new 18();
            EaseInOutCirc = new 19();
            EaseInElastic = new 20();
            EaseOutElastic = new 21();
            EaseInOutElastic = new 22();
            EaseInBack = new 23();
            EaseOutBack = new 24();
            EaseInOutBack = new 25();
            EaseInBounce = new 26();
            EaseOutBounce = new 27();
            EaseInOutBounce = new 28();
        }
    }

    public enum EasingOption {
        Linear,
        EaseInQuad,
        EaseOutQuad,
        EaseInOutQuad,
        EaseInCubic,
        EaseOutCubic,
        EaseInOutCubic,
        EaseInQuart,
        EaseOutQuart,
        EaseInOutQuart,
        EaseInSine,
        EaseOutSine,
        EaseInOutSine,
        EaseInExpo,
        EaseOutExpo,
        EaseInOutExpo,
        EaseInCirc,
        EaseOutCirc,
        EaseInOutCirc,
        EaseInElastic,
        EaseOutElastic,
        EaseInOutElastic,
        EaseInBack,
        EaseOutBack,
        EaseInOutBack,
        EaseInBounce,
        EaseOutBounce,
        EaseInOutBounce
    }

    public static EasingFunction getEasingFunctionFromOption(EasingOption easing) {
        switch (1.$SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[easing.ordinal()]) {
            case IExpr.DOUBLEID /*2*/:
                return EasingFunctions.EaseInQuad;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return EasingFunctions.EaseOutQuad;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return EasingFunctions.EaseInOutQuad;
            case ValueServer.CONSTANT_MODE /*5*/:
                return EasingFunctions.EaseInCubic;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                return EasingFunctions.EaseOutCubic;
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
                return EasingFunctions.EaseInOutCubic;
            case IExpr.INTEGERID /*8*/:
                return EasingFunctions.EaseInQuart;
            case R.styleable.TextAppearance_textAllCaps /*9*/:
                return EasingFunctions.EaseOutQuart;
            case R.styleable.SwitchCompat_switchMinWidth /*10*/:
                return EasingFunctions.EaseInOutQuart;
            case R.styleable.Toolbar_popupTheme /*11*/:
                return EasingFunctions.EaseInSine;
            case R.styleable.Toolbar_titleTextAppearance /*12*/:
                return EasingFunctions.EaseOutSine;
            case R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                return EasingFunctions.EaseInOutSine;
            case R.styleable.SearchView_suggestionRowLayout /*14*/:
                return EasingFunctions.EaseInExpo;
            case R.styleable.Toolbar_titleMarginStart /*15*/:
                return EasingFunctions.EaseOutExpo;
            case IExpr.FRACTIONID /*16*/:
                return EasingFunctions.EaseInOutExpo;
            case R.styleable.Toolbar_titleMarginTop /*17*/:
                return EasingFunctions.EaseInCirc;
            case R.styleable.Toolbar_titleMarginBottom /*18*/:
                return EasingFunctions.EaseOutCirc;
            case R.styleable.Toolbar_titleMargins /*19*/:
                return EasingFunctions.EaseInOutCirc;
            case R.styleable.Toolbar_maxButtonHeight /*20*/:
                return EasingFunctions.EaseInElastic;
            case R.styleable.ActionBar_contentInsetEnd /*21*/:
                return EasingFunctions.EaseOutElastic;
            case R.styleable.Toolbar_collapseIcon /*22*/:
                return EasingFunctions.EaseInOutElastic;
            case R.styleable.Toolbar_collapseContentDescription /*23*/:
                return EasingFunctions.EaseInBack;
            case R.styleable.Toolbar_navigationIcon /*24*/:
                return EasingFunctions.EaseOutBack;
            case R.styleable.Toolbar_navigationContentDescription /*25*/:
                return EasingFunctions.EaseInOutBack;
            case R.styleable.Toolbar_logoDescription /*26*/:
                return EasingFunctions.EaseInBounce;
            case R.styleable.Toolbar_titleTextColor /*27*/:
                return EasingFunctions.EaseOutBounce;
            case R.styleable.Toolbar_subtitleTextColor /*28*/:
                return EasingFunctions.EaseInOutBounce;
            default:
                return EasingFunctions.Linear;
        }
    }
}
