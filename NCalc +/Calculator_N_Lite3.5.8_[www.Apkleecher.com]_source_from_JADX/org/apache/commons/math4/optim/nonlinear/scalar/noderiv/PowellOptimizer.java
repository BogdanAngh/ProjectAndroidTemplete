package org.apache.commons.math4.optim.nonlinear.scalar.noderiv;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.MathUnsupportedOperationException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.optim.PointValuePair;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.optim.nonlinear.scalar.LineSearch;
import org.apache.commons.math4.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math4.optim.univariate.UnivariatePointValuePair;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class PowellOptimizer extends MultivariateOptimizer {
    private static final double MIN_RELATIVE_TOLERANCE;
    private final double absoluteThreshold;
    private final LineSearch line;
    private final double relativeThreshold;

    static {
        MIN_RELATIVE_TOLERANCE = 2.0d * FastMath.ulp(1.0d);
    }

    public PowellOptimizer(double rel, double abs, ConvergenceChecker<PointValuePair> checker) {
        this(rel, abs, FastMath.sqrt(rel), FastMath.sqrt(abs), checker);
    }

    public PowellOptimizer(double rel, double abs, double lineRel, double lineAbs, ConvergenceChecker<PointValuePair> checker) {
        super(checker);
        if (rel < MIN_RELATIVE_TOLERANCE) {
            throw new NumberIsTooSmallException(Double.valueOf(rel), Double.valueOf(MIN_RELATIVE_TOLERANCE), true);
        } else if (abs <= 0.0d) {
            throw new NotStrictlyPositiveException(Double.valueOf(abs));
        } else {
            this.relativeThreshold = rel;
            this.absoluteThreshold = abs;
            this.line = new LineSearch(this, lineRel, lineAbs, 1.0d);
        }
    }

    public PowellOptimizer(double rel, double abs) {
        this(rel, abs, null);
    }

    public PowellOptimizer(double rel, double abs, double lineRel, double lineAbs) {
        this(rel, abs, lineRel, lineAbs, null);
    }

    protected PointValuePair doOptimize() {
        int i;
        checkParameters();
        GoalType goal = getGoalType();
        double[] guess = getStartPoint();
        int n = guess.length;
        double[][] direc = (double[][]) Array.newInstance(Double.TYPE, new int[]{n, n});
        for (i = 0; i < n; i++) {
            direc[i][i] = 1.0d;
        }
        ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
        double[] x = guess;
        double fVal = computeObjectiveValue(x);
        double[] x1 = (double[]) x.clone();
        while (true) {
            incrementIterationCount();
            double fX = fVal;
            double delta = 0.0d;
            int bigInd = 0;
            for (i = 0; i < n; i++) {
                double[] d = MathArrays.copyOf(direc[i]);
                double fX2 = fVal;
                UnivariatePointValuePair optimum = this.line.search(x, d);
                fVal = optimum.getValue();
                x = newPointAndDirection(x, d, optimum.getPoint())[0];
                if (fX2 - fVal > delta) {
                    delta = fX2 - fVal;
                    bigInd = i;
                }
            }
            boolean stop = 2.0d * (fX - fVal) <= (this.relativeThreshold * (FastMath.abs(fX) + FastMath.abs(fVal))) + this.absoluteThreshold;
            PointValuePair pointValuePair = new PointValuePair(x1, fX);
            PointValuePair current = new PointValuePair(x, fVal);
            if (!(stop || checker == null)) {
                stop = checker.converged(getIterations(), pointValuePair, current);
            }
            if (stop) {
                break;
            }
            d = new double[n];
            double[] x2 = new double[n];
            for (i = 0; i < n; i++) {
                d[i] = x[i] - x1[i];
                x2[i] = (2.0d * x[i]) - x1[i];
            }
            x1 = (double[]) x.clone();
            fX2 = computeObjectiveValue(x2);
            if (fX > fX2) {
                double temp = (fX - fVal) - delta;
                temp = fX - fX2;
                if (((2.0d * ((fX + fX2) - (2.0d * fVal))) * (temp * temp)) - ((delta * temp) * temp) < 0.0d) {
                    optimum = this.line.search(x, d);
                    fVal = optimum.getValue();
                    double[][] result = newPointAndDirection(x, d, optimum.getPoint());
                    x = result[0];
                    int lastInd = n - 1;
                    direc[bigInd] = direc[lastInd];
                    direc[lastInd] = result[1];
                }
            }
        }
        if (goal != GoalType.MINIMIZE) {
            return fVal <= fX ? pointValuePair : current;
        } else {
            if (fVal < fX) {
                return current;
            }
            return pointValuePair;
        }
    }

    private double[][] newPointAndDirection(double[] p, double[] d, double optimum) {
        int n = p.length;
        double[] nP = new double[n];
        double[] nD = new double[n];
        for (int i = 0; i < n; i++) {
            nD[i] = d[i] * optimum;
            nP[i] = p[i] + nD[i];
        }
        return new double[][]{nP, nD};
    }

    private void checkParameters() {
        if (getLowerBound() != null || getUpperBound() != null) {
            throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
        }
    }
}
