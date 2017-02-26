package org.apache.commons.math4.optim.univariate;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.optim.AbstractConvergenceChecker;
import org.apache.commons.math4.util.FastMath;

public class SimpleUnivariateValueChecker extends AbstractConvergenceChecker<UnivariatePointValuePair> {
    private static final int ITERATION_CHECK_DISABLED = -1;
    private final int maxIterationCount;

    public SimpleUnivariateValueChecker(double relativeThreshold, double absoluteThreshold) {
        super(relativeThreshold, absoluteThreshold);
        this.maxIterationCount = ITERATION_CHECK_DISABLED;
    }

    public SimpleUnivariateValueChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
        super(relativeThreshold, absoluteThreshold);
        if (maxIter <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
        }
        this.maxIterationCount = maxIter;
    }

    public boolean converged(int iteration, UnivariatePointValuePair previous, UnivariatePointValuePair current) {
        if (this.maxIterationCount != ITERATION_CHECK_DISABLED && iteration >= this.maxIterationCount) {
            return true;
        }
        double p = previous.getValue();
        double c = current.getValue();
        double difference = FastMath.abs(p - c);
        return difference <= getRelativeThreshold() * FastMath.max(FastMath.abs(p), FastMath.abs(c)) || difference <= getAbsoluteThreshold();
    }
}
