package org.apache.commons.math4.analysis;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.analysis.function.Identity;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class FunctionUtils {

    class 10 implements UnivariateFunction {
        private final /* synthetic */ BivariateFunction val$f;
        private final /* synthetic */ double val$fixed;

        10(BivariateFunction bivariateFunction, double d) {
            this.val$f = bivariateFunction;
            this.val$fixed = d;
        }

        public double value(double x) {
            return this.val$f.value(x, this.val$fixed);
        }
    }

    class 1 implements UnivariateFunction {
        private final /* synthetic */ UnivariateFunction[] val$f;

        1(UnivariateFunction[] univariateFunctionArr) {
            this.val$f = univariateFunctionArr;
        }

        public double value(double x) {
            double r = x;
            for (int i = this.val$f.length - 1; i >= 0; i--) {
                r = this.val$f[i].value(r);
            }
            return r;
        }
    }

    class 2 implements UnivariateDifferentiableFunction {
        private final /* synthetic */ UnivariateDifferentiableFunction[] val$f;

        2(UnivariateDifferentiableFunction[] univariateDifferentiableFunctionArr) {
            this.val$f = univariateDifferentiableFunctionArr;
        }

        public double value(double t) {
            double r = t;
            for (int i = this.val$f.length - 1; i >= 0; i--) {
                r = this.val$f[i].value(r);
            }
            return r;
        }

        public DerivativeStructure value(DerivativeStructure t) {
            DerivativeStructure r = t;
            for (int i = this.val$f.length - 1; i >= 0; i--) {
                r = this.val$f[i].value(r);
            }
            return r;
        }
    }

    class 3 implements UnivariateFunction {
        private final /* synthetic */ UnivariateFunction[] val$f;

        3(UnivariateFunction[] univariateFunctionArr) {
            this.val$f = univariateFunctionArr;
        }

        public double value(double x) {
            double r = this.val$f[0].value(x);
            for (int i = 1; i < this.val$f.length; i++) {
                r += this.val$f[i].value(x);
            }
            return r;
        }
    }

    class 4 implements UnivariateDifferentiableFunction {
        private final /* synthetic */ UnivariateDifferentiableFunction[] val$f;

        4(UnivariateDifferentiableFunction[] univariateDifferentiableFunctionArr) {
            this.val$f = univariateDifferentiableFunctionArr;
        }

        public double value(double t) {
            double r = this.val$f[0].value(t);
            for (int i = 1; i < this.val$f.length; i++) {
                r += this.val$f[i].value(t);
            }
            return r;
        }

        public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
            DerivativeStructure r = this.val$f[0].value(t);
            for (int i = 1; i < this.val$f.length; i++) {
                r = r.add(this.val$f[i].value(t));
            }
            return r;
        }
    }

    class 5 implements UnivariateFunction {
        private final /* synthetic */ UnivariateFunction[] val$f;

        5(UnivariateFunction[] univariateFunctionArr) {
            this.val$f = univariateFunctionArr;
        }

        public double value(double x) {
            double r = this.val$f[0].value(x);
            for (int i = 1; i < this.val$f.length; i++) {
                r *= this.val$f[i].value(x);
            }
            return r;
        }
    }

    class 6 implements UnivariateDifferentiableFunction {
        private final /* synthetic */ UnivariateDifferentiableFunction[] val$f;

        6(UnivariateDifferentiableFunction[] univariateDifferentiableFunctionArr) {
            this.val$f = univariateDifferentiableFunctionArr;
        }

        public double value(double t) {
            double r = this.val$f[0].value(t);
            for (int i = 1; i < this.val$f.length; i++) {
                r *= this.val$f[i].value(t);
            }
            return r;
        }

        public DerivativeStructure value(DerivativeStructure t) {
            DerivativeStructure r = this.val$f[0].value(t);
            for (int i = 1; i < this.val$f.length; i++) {
                r = r.multiply(this.val$f[i].value(t));
            }
            return r;
        }
    }

    class 7 implements UnivariateFunction {
        private final /* synthetic */ BivariateFunction val$combiner;
        private final /* synthetic */ UnivariateFunction val$f;
        private final /* synthetic */ UnivariateFunction val$g;

        7(BivariateFunction bivariateFunction, UnivariateFunction univariateFunction, UnivariateFunction univariateFunction2) {
            this.val$combiner = bivariateFunction;
            this.val$f = univariateFunction;
            this.val$g = univariateFunction2;
        }

        public double value(double x) {
            return this.val$combiner.value(this.val$f.value(x), this.val$g.value(x));
        }
    }

    class 8 implements MultivariateFunction {
        private final /* synthetic */ BivariateFunction val$combiner;
        private final /* synthetic */ UnivariateFunction val$f;
        private final /* synthetic */ double val$initialValue;

        8(BivariateFunction bivariateFunction, double d, UnivariateFunction univariateFunction) {
            this.val$combiner = bivariateFunction;
            this.val$initialValue = d;
            this.val$f = univariateFunction;
        }

        public double value(double[] point) {
            double result = this.val$combiner.value(this.val$initialValue, this.val$f.value(point[0]));
            for (int i = 1; i < point.length; i++) {
                result = this.val$combiner.value(result, this.val$f.value(point[i]));
            }
            return result;
        }
    }

    class 9 implements UnivariateFunction {
        private final /* synthetic */ BivariateFunction val$f;
        private final /* synthetic */ double val$fixed;

        9(BivariateFunction bivariateFunction, double d) {
            this.val$f = bivariateFunction;
            this.val$fixed = d;
        }

        public double value(double x) {
            return this.val$f.value(this.val$fixed, x);
        }
    }

    private FunctionUtils() {
    }

    public static UnivariateFunction compose(UnivariateFunction... f) {
        return new 1(f);
    }

    public static UnivariateDifferentiableFunction compose(UnivariateDifferentiableFunction... f) {
        return new 2(f);
    }

    public static UnivariateFunction add(UnivariateFunction... f) {
        return new 3(f);
    }

    public static UnivariateDifferentiableFunction add(UnivariateDifferentiableFunction... f) {
        return new 4(f);
    }

    public static UnivariateFunction multiply(UnivariateFunction... f) {
        return new 5(f);
    }

    public static UnivariateDifferentiableFunction multiply(UnivariateDifferentiableFunction... f) {
        return new 6(f);
    }

    public static UnivariateFunction combine(BivariateFunction combiner, UnivariateFunction f, UnivariateFunction g) {
        return new 7(combiner, f, g);
    }

    public static MultivariateFunction collector(BivariateFunction combiner, UnivariateFunction f, double initialValue) {
        return new 8(combiner, initialValue, f);
    }

    public static MultivariateFunction collector(BivariateFunction combiner, double initialValue) {
        return collector(combiner, new Identity(), initialValue);
    }

    public static UnivariateFunction fix1stArgument(BivariateFunction f, double fixed) {
        return new 9(f, fixed);
    }

    public static UnivariateFunction fix2ndArgument(BivariateFunction f, double fixed) {
        return new 10(f, fixed);
    }

    public static double[] sample(UnivariateFunction f, double min, double max, int n) throws NumberIsTooLargeException, NotStrictlyPositiveException {
        if (n <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_NUMBER_OF_SAMPLES, Integer.valueOf(n));
        } else if (min >= max) {
            throw new NumberIsTooLargeException(Double.valueOf(min), Double.valueOf(max), false);
        } else {
            double[] s = new double[n];
            double h = (max - min) / ((double) n);
            for (int i = 0; i < n; i++) {
                s[i] = f.value((((double) i) * h) + min);
            }
            return s;
        }
    }
}
