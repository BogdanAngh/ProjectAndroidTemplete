package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.FastMath;

public class BisectionSolver extends AbstractUnivariateSolver {
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;

    public BisectionSolver() {
        this(DEFAULT_ABSOLUTE_ACCURACY);
    }

    public BisectionSolver(double absoluteAccuracy) {
        super(absoluteAccuracy);
    }

    public BisectionSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
    }

    protected double doSolve() throws TooManyEvaluationsException {
        double min = getMin();
        double max = getMax();
        verifyInterval(min, max);
        double absoluteAccuracy = getAbsoluteAccuracy();
        do {
            double m = UnivariateSolverUtils.midpoint(min, max);
            if (computeObjectiveValue(m) * computeObjectiveValue(min) > 0.0d) {
                min = m;
            } else {
                max = m;
            }
        } while (FastMath.abs(max - min) > absoluteAccuracy);
        return UnivariateSolverUtils.midpoint(min, max);
    }
}
