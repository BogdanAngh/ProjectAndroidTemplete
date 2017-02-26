package org.apache.commons.math4.analysis.integration;

import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.FastMath;

public class SimpsonIntegrator extends BaseAbstractUnivariateIntegrator {
    public static final int SIMPSON_MAX_ITERATIONS_COUNT = 64;

    public SimpsonIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > SIMPSON_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(SIMPSON_MAX_ITERATIONS_COUNT), false);
        }
    }

    public SimpsonIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > SIMPSON_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(SIMPSON_MAX_ITERATIONS_COUNT), false);
        }
    }

    public SimpsonIntegrator() {
        super(3, (int) SIMPSON_MAX_ITERATIONS_COUNT);
    }

    protected double doIntegrate() throws TooManyEvaluationsException, MaxCountExceededException {
        TrapezoidIntegrator qtrap = new TrapezoidIntegrator();
        if (getMinimalIterationCount() == 1) {
            return ((4.0d * qtrap.stage(this, 1)) - qtrap.stage(this, 0)) / 3.0d;
        }
        double olds = 0.0d;
        double oldt = qtrap.stage(this, 0);
        while (true) {
            double t = qtrap.stage(this, this.iterations.getCount());
            this.iterations.incrementCount();
            double s = ((4.0d * t) - oldt) / 3.0d;
            if (this.iterations.getCount() >= getMinimalIterationCount()) {
                double delta = FastMath.abs(s - olds);
                if (delta <= (getRelativeAccuracy() * (FastMath.abs(olds) + FastMath.abs(s))) * 0.5d || delta <= getAbsoluteAccuracy()) {
                    return s;
                }
            }
            olds = s;
            oldt = t;
        }
    }
}
