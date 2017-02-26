package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.FastMath;

public class SecantSolver extends AbstractUnivariateSolver {
    protected static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;

    public SecantSolver() {
        super(DEFAULT_ABSOLUTE_ACCURACY);
    }

    public SecantSolver(double absoluteAccuracy) {
        super(absoluteAccuracy);
    }

    public SecantSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
    }

    protected final double doSolve() throws TooManyEvaluationsException, NoBracketingException {
        double x0 = getMin();
        double x1 = getMax();
        double f0 = computeObjectiveValue(x0);
        double f1 = computeObjectiveValue(x1);
        if (f0 == 0.0d) {
            return x0;
        }
        if (f1 == 0.0d) {
            return x1;
        }
        verifyBracketing(x0, x1);
        double ftol = getFunctionValueAccuracy();
        double atol = getAbsoluteAccuracy();
        double rtol = getRelativeAccuracy();
        do {
            double x = x1 - (((x1 - x0) * f1) / (f1 - f0));
            double fx = computeObjectiveValue(x);
            if (fx == 0.0d) {
                return x;
            }
            x0 = x1;
            f0 = f1;
            x1 = x;
            f1 = fx;
            if (FastMath.abs(f1) <= ftol) {
                return x1;
            }
        } while (FastMath.abs(x1 - x0) >= FastMath.max(FastMath.abs(x1) * rtol, atol));
        return x1;
    }
}
