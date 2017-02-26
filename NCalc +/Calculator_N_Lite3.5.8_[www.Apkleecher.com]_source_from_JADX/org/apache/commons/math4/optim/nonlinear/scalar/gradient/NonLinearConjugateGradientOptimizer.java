package org.apache.commons.math4.optim.nonlinear.scalar.gradient;

import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.MathUnsupportedOperationException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.optim.OptimizationData;
import org.apache.commons.math4.optim.PointValuePair;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.optim.nonlinear.scalar.GradientMultivariateOptimizer;
import org.apache.commons.math4.optim.nonlinear.scalar.LineSearch;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class NonLinearConjugateGradientOptimizer extends GradientMultivariateOptimizer {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$optim$nonlinear$scalar$gradient$NonLinearConjugateGradientOptimizer$Formula;
    private final LineSearch line;
    private final Preconditioner preconditioner;
    private final Formula updateFormula;

    public enum Formula {
        FLETCHER_REEVES,
        POLAK_RIBIERE
    }

    public static class IdentityPreconditioner implements Preconditioner {
        public double[] precondition(double[] variables, double[] r) {
            return (double[]) r.clone();
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$optim$nonlinear$scalar$gradient$NonLinearConjugateGradientOptimizer$Formula() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$optim$nonlinear$scalar$gradient$NonLinearConjugateGradientOptimizer$Formula;
        if (iArr == null) {
            iArr = new int[Formula.values().length];
            try {
                iArr[Formula.FLETCHER_REEVES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Formula.POLAK_RIBIERE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$optim$nonlinear$scalar$gradient$NonLinearConjugateGradientOptimizer$Formula = iArr;
        }
        return iArr;
    }

    public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker) {
        this(updateFormula, checker, BOBYQAOptimizer.DEFAULT_STOPPING_RADIUS, BOBYQAOptimizer.DEFAULT_STOPPING_RADIUS, BOBYQAOptimizer.DEFAULT_STOPPING_RADIUS, new IdentityPreconditioner());
    }

    public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker, double relativeTolerance, double absoluteTolerance, double initialBracketingRange) {
        this(updateFormula, checker, relativeTolerance, absoluteTolerance, initialBracketingRange, new IdentityPreconditioner());
    }

    public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker, double relativeTolerance, double absoluteTolerance, double initialBracketingRange, Preconditioner preconditioner) {
        super(checker);
        this.updateFormula = updateFormula;
        this.preconditioner = preconditioner;
        this.line = new LineSearch(this, relativeTolerance, absoluteTolerance, initialBracketingRange);
    }

    public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
        return super.optimize(optData);
    }

    protected PointValuePair doOptimize() {
        int i;
        ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
        double[] point = getStartPoint();
        GoalType goal = getGoalType();
        int n = point.length;
        double[] r = computeObjectiveGradient(point);
        if (goal == GoalType.MINIMIZE) {
            for (i = 0; i < n; i++) {
                r[i] = -r[i];
            }
        }
        double[] steepestDescent = this.preconditioner.precondition(point, r);
        double[] searchDirection = (double[]) steepestDescent.clone();
        double delta = 0.0d;
        for (i = 0; i < n; i++) {
            delta += r[i] * searchDirection[i];
        }
        PointValuePair current = null;
        while (true) {
            incrementIterationCount();
            PointValuePair previous = current;
            current = new PointValuePair(point, computeObjectiveValue(point));
            if (previous != null) {
                if (checker.converged(getIterations(), previous, current)) {
                    return current;
                }
            }
            double step = this.line.search(point, searchDirection).getPoint();
            i = 0;
            while (true) {
                int length = point.length;
                if (i >= r0) {
                    double beta;
                    r = computeObjectiveGradient(point);
                    if (goal == GoalType.MINIMIZE) {
                        for (i = 0; i < n; i++) {
                            r[i] = -r[i];
                        }
                    }
                    double deltaOld = delta;
                    double[] newSteepestDescent = this.preconditioner.precondition(point, r);
                    delta = 0.0d;
                    for (i = 0; i < n; i++) {
                        delta += r[i] * newSteepestDescent[i];
                    }
                    switch ($SWITCH_TABLE$org$apache$commons$math4$optim$nonlinear$scalar$gradient$NonLinearConjugateGradientOptimizer$Formula()[this.updateFormula.ordinal()]) {
                        case ValueServer.REPLAY_MODE /*1*/:
                            beta = delta / deltaOld;
                            break;
                        case IExpr.DOUBLEID /*2*/:
                            double deltaMid = 0.0d;
                            i = 0;
                            while (true) {
                                length = r.length;
                                if (i >= r0) {
                                    beta = (delta - deltaMid) / deltaOld;
                                    break;
                                }
                                deltaMid += r[i] * steepestDescent[i];
                                i++;
                            }
                        default:
                            throw new MathInternalError();
                    }
                    steepestDescent = newSteepestDescent;
                    if (getIterations() % n == 0 || beta < 0.0d) {
                        searchDirection = (double[]) steepestDescent.clone();
                    } else {
                        for (i = 0; i < n; i++) {
                            searchDirection[i] = steepestDescent[i] + (searchDirection[i] * beta);
                        }
                    }
                } else {
                    point[i] = point[i] + (searchDirection[i] * step);
                    i++;
                }
            }
        }
    }

    protected void parseOptimizationData(OptimizationData... optData) {
        super.parseOptimizationData(optData);
        checkParameters();
    }

    private void checkParameters() {
        if (getLowerBound() != null || getUpperBound() != null) {
            throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
        }
    }
}
