package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class UnivariatePeriodicInterpolator implements UnivariateInterpolator {
    public static final int DEFAULT_EXTEND = 5;
    private final int extend;
    private final UnivariateInterpolator interpolator;
    private final double period;

    class 1 implements UnivariateFunction {
        private final /* synthetic */ UnivariateFunction val$f;
        private final /* synthetic */ double val$offset;

        1(UnivariateFunction univariateFunction, double d) {
            this.val$f = univariateFunction;
            this.val$offset = d;
        }

        public double value(double x) throws MathIllegalArgumentException {
            return this.val$f.value(MathUtils.reduce(x, UnivariatePeriodicInterpolator.this.period, this.val$offset));
        }
    }

    public UnivariatePeriodicInterpolator(UnivariateInterpolator interpolator, double period, int extend) {
        this.interpolator = interpolator;
        this.period = period;
        this.extend = extend;
    }

    public UnivariatePeriodicInterpolator(UnivariateInterpolator interpolator, double period) {
        this(interpolator, period, DEFAULT_EXTEND);
    }

    public UnivariateFunction interpolate(double[] xval, double[] yval) throws NumberIsTooSmallException, NonMonotonicSequenceException {
        if (xval.length < this.extend) {
            throw new NumberIsTooSmallException(Integer.valueOf(xval.length), Integer.valueOf(this.extend), true);
        }
        int i;
        MathArrays.checkOrder(xval);
        double offset = xval[0];
        int len = xval.length + (this.extend * 2);
        double[] x = new double[len];
        double[] y = new double[len];
        for (i = 0; i < xval.length; i++) {
            int index = i + this.extend;
            x[index] = MathUtils.reduce(xval[i], this.period, offset);
            y[index] = yval[i];
        }
        for (i = 0; i < this.extend; i++) {
            index = (xval.length - this.extend) + i;
            x[i] = MathUtils.reduce(xval[index], this.period, offset) - this.period;
            y[i] = yval[index];
            index = (len - this.extend) + i;
            x[index] = MathUtils.reduce(xval[i], this.period, offset) + this.period;
            y[index] = yval[i];
        }
        MathArrays.sortInPlace(x, new double[][]{y});
        return new 1(this.interpolator.interpolate(x, y), offset);
    }
}
