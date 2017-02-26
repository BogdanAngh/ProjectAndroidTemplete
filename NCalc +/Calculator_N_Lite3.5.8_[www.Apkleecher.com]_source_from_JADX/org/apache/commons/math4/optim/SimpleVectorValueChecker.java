package org.apache.commons.math4.optim;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.util.FastMath;

public class SimpleVectorValueChecker extends AbstractConvergenceChecker<PointVectorValuePair> {
    private static final int ITERATION_CHECK_DISABLED = -1;
    private final int maxIterationCount;

    public SimpleVectorValueChecker(double relativeThreshold, double absoluteThreshold) {
        super(relativeThreshold, absoluteThreshold);
        this.maxIterationCount = ITERATION_CHECK_DISABLED;
    }

    public SimpleVectorValueChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
        super(relativeThreshold, absoluteThreshold);
        if (maxIter <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
        }
        this.maxIterationCount = maxIter;
    }

    public boolean converged(int iteration, PointVectorValuePair previous, PointVectorValuePair current) {
        if (this.maxIterationCount != ITERATION_CHECK_DISABLED && iteration >= this.maxIterationCount) {
            return true;
        }
        double[] p = previous.getValueRef();
        double[] c = current.getValueRef();
        for (int i = 0; i < p.length; i++) {
            double pi = p[i];
            double ci = c[i];
            double difference = FastMath.abs(pi - ci);
            if (difference > getRelativeThreshold() * FastMath.max(FastMath.abs(pi), FastMath.abs(ci)) && difference > getAbsoluteThreshold()) {
                return false;
            }
        }
        return true;
    }
}
