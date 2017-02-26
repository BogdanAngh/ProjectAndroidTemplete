package org.apache.commons.math4.util;

import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public abstract class ContinuedFraction {
    private static final double DEFAULT_EPSILON = 1.0E-8d;

    protected abstract double getA(int i, double d);

    protected abstract double getB(int i, double d);

    protected ContinuedFraction() {
    }

    public double evaluate(double x) throws ConvergenceException {
        return evaluate(x, DEFAULT_EPSILON, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public double evaluate(double x, double epsilon) throws ConvergenceException {
        return evaluate(x, epsilon, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public double evaluate(double x, int maxIterations) throws ConvergenceException, MaxCountExceededException {
        return evaluate(x, DEFAULT_EPSILON, maxIterations);
    }

    public double evaluate(double x, double epsilon, int maxIterations) throws ConvergenceException, MaxCountExceededException {
        double hPrev = getA(0, x);
        if (Precision.equals(hPrev, 0.0d, 1.0E-50d)) {
            hPrev = 1.0E-50d;
        }
        int n = 1;
        double dPrev = 0.0d;
        double cPrev = hPrev;
        double hN = hPrev;
        while (n < maxIterations) {
            double a = getA(n, x);
            double b = getB(n, x);
            double dN = a + (b * dPrev);
            if (Precision.equals(dN, 0.0d, 1.0E-50d)) {
                dN = 1.0E-50d;
            }
            double cN = a + (b / cPrev);
            if (Precision.equals(cN, 0.0d, 1.0E-50d)) {
                cN = 1.0E-50d;
            }
            dN = 1.0d / dN;
            double deltaN = cN * dN;
            hN = hPrev * deltaN;
            if (!Double.isInfinite(hN)) {
                if (!Double.isNaN(hN)) {
                    if (FastMath.abs(deltaN - 1.0d) < epsilon) {
                        break;
                    }
                    dPrev = dN;
                    cPrev = cN;
                    hPrev = hN;
                    n++;
                } else {
                    throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_NAN_DIVERGENCE, Double.valueOf(x));
                }
            }
            throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, Double.valueOf(x));
        }
        if (n < maxIterations) {
            return hN;
        }
        throw new MaxCountExceededException(LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION, Integer.valueOf(maxIterations), Double.valueOf(x));
    }
}
