package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math4.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathArrays;

public class SplineInterpolator implements UnivariateInterpolator {
    public PolynomialSplineFunction interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        int length = x.length;
        int length2 = y.length;
        if (length != r0) {
            throw new DimensionMismatchException(x.length, y.length);
        } else if (x.length < 3) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(x.length), Integer.valueOf(3), true);
        } else {
            int i;
            int n = x.length - 1;
            MathArrays.checkOrder(x);
            double[] h = new double[n];
            for (i = 0; i < n; i++) {
                h[i] = x[i + 1] - x[i];
            }
            double[] mu = new double[n];
            double[] z = new double[(n + 1)];
            mu[0] = 0.0d;
            z[0] = 0.0d;
            for (i = 1; i < n; i++) {
                double g = (2.0d * (x[i + 1] - x[i - 1])) - (h[i - 1] * mu[i - 1]);
                mu[i] = h[i] / g;
                z[i] = (((3.0d * (((y[i + 1] * h[i - 1]) - (y[i] * (x[i + 1] - x[i - 1]))) + (y[i - 1] * h[i]))) / (h[i - 1] * h[i])) - (h[i - 1] * z[i - 1])) / g;
            }
            double[] b = new double[n];
            double[] c = new double[(n + 1)];
            double[] d = new double[n];
            z[n] = 0.0d;
            c[n] = 0.0d;
            for (int j = n - 1; j >= 0; j--) {
                c[j] = z[j] - (mu[j] * c[j + 1]);
                b[j] = ((y[j + 1] - y[j]) / h[j]) - ((h[j] * (c[j + 1] + (2.0d * c[j]))) / 3.0d);
                d[j] = (c[j + 1] - c[j]) / (3.0d * h[j]);
            }
            PolynomialFunction[] polynomials = new PolynomialFunction[n];
            double[] coefficients = new double[4];
            for (i = 0; i < n; i++) {
                coefficients[0] = y[i];
                coefficients[1] = b[i];
                coefficients[2] = c[i];
                coefficients[3] = d[i];
                polynomials[i] = new PolynomialFunction(coefficients);
            }
            return new PolynomialSplineFunction(x, polynomials);
        }
    }
}
