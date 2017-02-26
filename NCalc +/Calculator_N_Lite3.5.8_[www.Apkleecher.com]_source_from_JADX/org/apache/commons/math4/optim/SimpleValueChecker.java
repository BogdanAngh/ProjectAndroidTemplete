package org.apache.commons.math4.optim;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.util.FastMath;

public class SimpleValueChecker extends AbstractConvergenceChecker<PointValuePair> {
    private static final int ITERATION_CHECK_DISABLED = -1;
    private final int maxIterationCount;

    public SimpleValueChecker(double relativeThreshold, double absoluteThreshold) {
        super(relativeThreshold, absoluteThreshold);
        this.maxIterationCount = ITERATION_CHECK_DISABLED;
    }

    public SimpleValueChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
        super(relativeThreshold, absoluteThreshold);
        if (maxIter <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
        }
        this.maxIterationCount = maxIter;
    }

    public boolean converged(int iteration, PointValuePair previous, PointValuePair current) {
        if (this.maxIterationCount != ITERATION_CHECK_DISABLED && iteration >= this.maxIterationCount) {
            return true;
        }
        double p = ((Double) previous.getValue()).doubleValue();
        double c = ((Double) current.getValue()).doubleValue();
        double difference = FastMath.abs(p - c);
        return difference <= getRelativeThreshold() * FastMath.max(FastMath.abs(p), FastMath.abs(c)) || difference <= getAbsoluteThreshold();
    }
}
