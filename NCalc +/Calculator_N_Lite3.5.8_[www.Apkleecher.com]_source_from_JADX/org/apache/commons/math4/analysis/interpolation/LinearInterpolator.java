package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math4.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathArrays;

public class LinearInterpolator implements UnivariateInterpolator {
    public PolynomialSplineFunction interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        if (x.length != y.length) {
            throw new DimensionMismatchException(x.length, y.length);
        } else if (x.length < 2) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(x.length), Integer.valueOf(2), true);
        } else {
            int i;
            int n = x.length - 1;
            MathArrays.checkOrder(x);
            double[] m = new double[n];
            for (i = 0; i < n; i++) {
                m[i] = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);
            }
            PolynomialFunction[] polynomials = new PolynomialFunction[n];
            double[] coefficients = new double[2];
            for (i = 0; i < n; i++) {
                coefficients[0] = y[i];
                coefficients[1] = m[i];
                polynomials[i] = new PolynomialFunction(coefficients);
            }
            return new PolynomialSplineFunction(x, polynomials);
        }
    }
}
