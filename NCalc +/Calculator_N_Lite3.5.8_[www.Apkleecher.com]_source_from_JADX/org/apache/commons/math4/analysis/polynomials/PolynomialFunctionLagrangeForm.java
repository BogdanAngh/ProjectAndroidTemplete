package org.apache.commons.math4.analysis.polynomials;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathArrays.OrderDirection;

public class PolynomialFunctionLagrangeForm implements UnivariateFunction {
    private double[] coefficients;
    private boolean coefficientsComputed;
    private final double[] x;
    private final double[] y;

    public PolynomialFunctionLagrangeForm(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        this.x = new double[x.length];
        this.y = new double[y.length];
        System.arraycopy(x, 0, this.x, 0, x.length);
        System.arraycopy(y, 0, this.y, 0, y.length);
        this.coefficientsComputed = false;
        if (!verifyInterpolationArray(x, y, false)) {
            MathArrays.sortInPlace(this.x, new double[][]{this.y});
            verifyInterpolationArray(this.x, this.y, true);
        }
    }

    public double value(double z) {
        return evaluateInternal(this.x, this.y, z);
    }

    public int degree() {
        return this.x.length - 1;
    }

    public double[] getInterpolatingPoints() {
        double[] out = new double[this.x.length];
        System.arraycopy(this.x, 0, out, 0, this.x.length);
        return out;
    }

    public double[] getInterpolatingValues() {
        double[] out = new double[this.y.length];
        System.arraycopy(this.y, 0, out, 0, this.y.length);
        return out;
    }

    public double[] getCoefficients() {
        if (!this.coefficientsComputed) {
            computeCoefficients();
        }
        double[] out = new double[this.coefficients.length];
        System.arraycopy(this.coefficients, 0, out, 0, this.coefficients.length);
        return out;
    }

    public static double evaluate(double[] x, double[] y, double z) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        if (verifyInterpolationArray(x, y, false)) {
            return evaluateInternal(x, y, z);
        }
        double[] xNew = new double[x.length];
        double[] yNew = new double[y.length];
        System.arraycopy(x, 0, xNew, 0, x.length);
        System.arraycopy(y, 0, yNew, 0, y.length);
        MathArrays.sortInPlace(xNew, new double[][]{yNew});
        verifyInterpolationArray(xNew, yNew, true);
        return evaluateInternal(xNew, yNew, z);
    }

    private static double evaluateInternal(double[] x, double[] y, double z) {
        int i;
        int nearest = 0;
        int n = x.length;
        double[] c = new double[n];
        double[] d = new double[n];
        double min_dist = Double.POSITIVE_INFINITY;
        for (i = 0; i < n; i++) {
            c[i] = y[i];
            d[i] = y[i];
            double dist = FastMath.abs(z - x[i]);
            if (dist < min_dist) {
                nearest = i;
                min_dist = dist;
            }
        }
        double value = y[nearest];
        for (i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                double td = x[i + j] - z;
                double w = (c[j + 1] - d[j]) / (x[j] - x[i + j]);
                c[j] = (x[j] - z) * w;
                d[j] = td * w;
            }
            if (((double) nearest) < 0.5d * ((double) ((n - i) + 1))) {
                value += c[nearest];
            } else {
                nearest--;
                value += d[nearest];
            }
        }
        return value;
    }

    protected void computeCoefficients() {
        int i;
        int j;
        int n = degree() + 1;
        this.coefficients = new double[n];
        for (i = 0; i < n; i++) {
            this.coefficients[i] = 0.0d;
        }
        double[] c = new double[(n + 1)];
        c[0] = 1.0d;
        for (i = 0; i < n; i++) {
            for (j = i; j > 0; j--) {
                c[j] = c[j - 1] - (c[j] * this.x[i]);
            }
            c[0] = c[0] * (-this.x[i]);
            c[i + 1] = 1.0d;
        }
        double[] tc = new double[n];
        for (i = 0; i < n; i++) {
            double d = 1.0d;
            for (j = 0; j < n; j++) {
                if (i != j) {
                    d *= this.x[i] - this.x[j];
                }
            }
            double t = this.y[i] / d;
            tc[n - 1] = c[n];
            double[] dArr = this.coefficients;
            int i2 = n - 1;
            dArr[i2] = dArr[i2] + (tc[n - 1] * t);
            for (j = n - 2; j >= 0; j--) {
                tc[j] = c[j + 1] + (tc[j + 1] * this.x[i]);
                dArr = this.coefficients;
                dArr[j] = dArr[j] + (tc[j] * t);
            }
        }
        this.coefficientsComputed = true;
    }

    public static boolean verifyInterpolationArray(double[] x, double[] y, boolean abort) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        if (x.length != y.length) {
            throw new DimensionMismatchException(x.length, y.length);
        } else if (x.length >= 2) {
            return MathArrays.checkOrder(x, OrderDirection.INCREASING, true, abort);
        } else {
            throw new NumberIsTooSmallException(LocalizedFormats.WRONG_NUMBER_OF_POINTS, Integer.valueOf(2), Integer.valueOf(x.length), true);
        }
    }
}
