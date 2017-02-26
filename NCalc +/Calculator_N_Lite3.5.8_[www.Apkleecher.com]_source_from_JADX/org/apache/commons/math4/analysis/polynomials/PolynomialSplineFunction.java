package org.apache.commons.math4.analysis.polynomials;

import java.util.Arrays;
import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathArrays;

public class PolynomialSplineFunction implements UnivariateDifferentiableFunction {
    private final double[] knots;
    private final int n;
    private final PolynomialFunction[] polynomials;

    public PolynomialSplineFunction(double[] knots, PolynomialFunction[] polynomials) throws NullArgumentException, NumberIsTooSmallException, DimensionMismatchException, NonMonotonicSequenceException {
        if (knots == null || polynomials == null) {
            throw new NullArgumentException();
        } else if (knots.length < 2) {
            throw new NumberIsTooSmallException(LocalizedFormats.NOT_ENOUGH_POINTS_IN_SPLINE_PARTITION, Integer.valueOf(2), Integer.valueOf(knots.length), false);
        } else if (knots.length - 1 != polynomials.length) {
            throw new DimensionMismatchException(polynomials.length, knots.length);
        } else {
            MathArrays.checkOrder(knots);
            this.n = knots.length - 1;
            this.knots = new double[(this.n + 1)];
            System.arraycopy(knots, 0, this.knots, 0, this.n + 1);
            this.polynomials = new PolynomialFunction[this.n];
            System.arraycopy(polynomials, 0, this.polynomials, 0, this.n);
        }
    }

    public double value(double v) {
        if (v < this.knots[0] || v > this.knots[this.n]) {
            throw new OutOfRangeException(Double.valueOf(v), Double.valueOf(this.knots[0]), Double.valueOf(this.knots[this.n]));
        }
        int i = Arrays.binarySearch(this.knots, v);
        if (i < 0) {
            i = (-i) - 2;
        }
        if (i >= this.polynomials.length) {
            i--;
        }
        return this.polynomials[i].value(v - this.knots[i]);
    }

    public PolynomialSplineFunction polynomialSplineDerivative() {
        PolynomialFunction[] derivativePolynomials = new PolynomialFunction[this.n];
        for (int i = 0; i < this.n; i++) {
            derivativePolynomials[i] = this.polynomials[i].polynomialDerivative();
        }
        return new PolynomialSplineFunction(this.knots, derivativePolynomials);
    }

    public DerivativeStructure value(DerivativeStructure t) {
        double t0 = t.getValue();
        if (t0 < this.knots[0] || t0 > this.knots[this.n]) {
            throw new OutOfRangeException(Double.valueOf(t0), Double.valueOf(this.knots[0]), Double.valueOf(this.knots[this.n]));
        }
        int i = Arrays.binarySearch(this.knots, t0);
        if (i < 0) {
            i = (-i) - 2;
        }
        if (i >= this.polynomials.length) {
            i--;
        }
        return this.polynomials[i].value(t.subtract(this.knots[i]));
    }

    public int getN() {
        return this.n;
    }

    public PolynomialFunction[] getPolynomials() {
        PolynomialFunction[] p = new PolynomialFunction[this.n];
        System.arraycopy(this.polynomials, 0, p, 0, this.n);
        return p;
    }

    public double[] getKnots() {
        double[] out = new double[(this.n + 1)];
        System.arraycopy(this.knots, 0, out, 0, this.n + 1);
        return out;
    }

    public boolean isValidPoint(double x) {
        if (x < this.knots[0] || x > this.knots[this.n]) {
            return false;
        }
        return true;
    }
}
