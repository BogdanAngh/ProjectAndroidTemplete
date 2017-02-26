package org.apache.commons.math4.analysis.integration;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.integration.gauss.GaussIntegratorFactory;
import org.apache.commons.math4.distribution.AbstractRealDistribution;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.util.FastMath;

public class IterativeLegendreGaussIntegrator extends BaseAbstractUnivariateIntegrator {
    private static final GaussIntegratorFactory FACTORY;
    private final int numberOfPoints;

    class 1 implements UnivariateFunction {
        1() {
        }

        public double value(double x) throws MathIllegalArgumentException, TooManyEvaluationsException {
            return IterativeLegendreGaussIntegrator.this.computeObjectiveValue(x);
        }
    }

    static {
        FACTORY = new GaussIntegratorFactory();
    }

    public IterativeLegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
        super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
        if (n <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(n));
        }
        this.numberOfPoints = n;
    }

    public IterativeLegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy) throws NotStrictlyPositiveException {
        this(n, relativeAccuracy, absoluteAccuracy, 3, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public IterativeLegendreGaussIntegrator(int n, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
        this(n, AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY, CholeskyDecomposition.DEFAULT_RELATIVE_SYMMETRY_THRESHOLD, minimalIterationCount, maximalIterationCount);
    }

    protected double doIntegrate() throws MathIllegalArgumentException, TooManyEvaluationsException, MaxCountExceededException {
        double oldt = stage(1);
        int n = 2;
        while (true) {
            double t = stage(n);
            double delta = FastMath.abs(t - oldt);
            double limit = FastMath.max(getAbsoluteAccuracy(), (getRelativeAccuracy() * (FastMath.abs(oldt) + FastMath.abs(t))) * 0.5d);
            if (this.iterations.getCount() + 1 >= getMinimalIterationCount() && delta <= limit) {
                return t;
            }
            double d = (double) n;
            n = FastMath.max((int) (d * FastMath.min(4.0d, FastMath.pow(delta / limit, 0.5d / ((double) this.numberOfPoints)))), n + 1);
            oldt = t;
            this.iterations.incrementCount();
        }
    }

    private double stage(int n) throws TooManyEvaluationsException {
        UnivariateFunction f = new 1();
        double min = getMin();
        double step = (getMax() - min) / ((double) n);
        double sum = 0.0d;
        for (int i = 0; i < n; i++) {
            double a = min + (((double) i) * step);
            sum += FACTORY.legendreHighPrecision(this.numberOfPoints, a, a + step).integrate(f);
        }
        return sum;
    }
}
