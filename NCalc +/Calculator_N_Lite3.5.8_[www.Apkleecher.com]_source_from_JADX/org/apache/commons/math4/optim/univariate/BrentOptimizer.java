package org.apache.commons.math4.optim.univariate;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

public class BrentOptimizer extends UnivariateOptimizer {
    private static final double GOLDEN_SECTION;
    private static final double MIN_RELATIVE_TOLERANCE;
    private final double absoluteThreshold;
    private final double relativeThreshold;

    static {
        GOLDEN_SECTION = 0.5d * (3.0d - FastMath.sqrt(5.0d));
        MIN_RELATIVE_TOLERANCE = 2.0d * FastMath.ulp(1.0d);
    }

    public BrentOptimizer(double rel, double abs, ConvergenceChecker<UnivariatePointValuePair> checker) {
        super(checker);
        if (rel < MIN_RELATIVE_TOLERANCE) {
            throw new NumberIsTooSmallException(Double.valueOf(rel), Double.valueOf(MIN_RELATIVE_TOLERANCE), true);
        } else if (abs <= 0.0d) {
            throw new NotStrictlyPositiveException(Double.valueOf(abs));
        } else {
            this.relativeThreshold = rel;
            this.absoluteThreshold = abs;
        }
    }

    public BrentOptimizer(double rel, double abs) {
        this(rel, abs, null);
    }

    protected UnivariatePointValuePair doOptimize() {
        double a;
        double b;
        boolean isMinim = getGoalType() == GoalType.MINIMIZE;
        double lo = getMin();
        double mid = getStartValue();
        double hi = getMax();
        ConvergenceChecker<UnivariatePointValuePair> checker = getConvergenceChecker();
        if (lo < hi) {
            a = lo;
            b = hi;
        } else {
            a = hi;
            b = lo;
        }
        double x = mid;
        double v = x;
        double w = x;
        double d = 0.0d;
        double e = 0.0d;
        double fx = computeObjectiveValue(x);
        if (!isMinim) {
            fx = -fx;
        }
        double fv = fx;
        double fw = fx;
        UnivariatePointValuePair previous = null;
        UnivariatePointValuePair current = new UnivariatePointValuePair(x, isMinim ? fx : -fx);
        UnivariatePointValuePair best = current;
        while (true) {
            double m = 0.5d * (a + b);
            double tol1 = (this.relativeThreshold * FastMath.abs(x)) + this.absoluteThreshold;
            double tol2 = 2.0d * tol1;
            if (FastMath.abs(x - m) <= tol2 - (0.5d * (b - a))) {
                return best(best, best(previous, current, isMinim), isMinim);
            }
            double u;
            double d2;
            if (FastMath.abs(e) > tol1) {
                double r = (x - w) * (fx - fv);
                double q = (x - v) * (fx - fw);
                double p = ((x - v) * q) - ((x - w) * r);
                q = 2.0d * (q - r);
                if (q > 0.0d) {
                    p = -p;
                } else {
                    q = -q;
                }
                r = e;
                e = d;
                if (p <= (a - x) * q || p >= (b - x) * q || FastMath.abs(p) >= FastMath.abs((0.5d * q) * r)) {
                    if (x < m) {
                        e = b - x;
                    } else {
                        e = a - x;
                    }
                    d = GOLDEN_SECTION * e;
                } else {
                    d = p / q;
                    u = x + d;
                    if (u - a < tol2 || b - u < tol2) {
                        if (x <= m) {
                            d = tol1;
                        } else {
                            d = -tol1;
                        }
                    }
                }
            } else {
                if (x < m) {
                    e = b - x;
                } else {
                    e = a - x;
                }
                d = GOLDEN_SECTION * e;
            }
            if (FastMath.abs(d) >= tol1) {
                u = x + d;
            } else if (d >= 0.0d) {
                u = x + tol1;
            } else {
                u = x - tol1;
            }
            double fu = computeObjectiveValue(u);
            if (!isMinim) {
                fu = -fu;
            }
            previous = current;
            if (isMinim) {
                d2 = fu;
            } else {
                d2 = -fu;
            }
            current = new UnivariatePointValuePair(u, d2);
            best = best(best, best(previous, current, isMinim), isMinim);
            if (checker != null) {
                if (checker.converged(getIterations(), previous, current)) {
                    return best;
                }
            }
            if (fu <= fx) {
                if (u < x) {
                    b = x;
                } else {
                    a = x;
                }
                v = w;
                fv = fw;
                w = x;
                fw = fx;
                x = u;
                fx = fu;
            } else {
                if (u < x) {
                    a = u;
                } else {
                    b = u;
                }
                if (fu <= fw || Precision.equals(w, x)) {
                    v = w;
                    fv = fw;
                    w = u;
                    fw = fu;
                } else if (fu <= fv || Precision.equals(v, x) || Precision.equals(v, w)) {
                    v = u;
                    fv = fu;
                }
            }
            incrementIterationCount();
        }
    }

    private UnivariatePointValuePair best(UnivariatePointValuePair a, UnivariatePointValuePair b, boolean isMinim) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (!isMinim) {
            return a.getValue() < b.getValue() ? b : a;
        } else {
            if (a.getValue() > b.getValue()) {
                return b;
            }
            return a;
        }
    }
}
