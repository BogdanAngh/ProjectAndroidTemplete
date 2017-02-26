package org.apache.commons.math4.special;

import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.ContinuedFraction;
import org.apache.commons.math4.util.FastMath;

public class Beta {
    private static final double DEFAULT_EPSILON = 1.0E-14d;
    private static final double[] DELTA;
    private static final double HALF_LOG_TWO_PI = 0.9189385332046727d;

    class 1 extends ContinuedFraction {
        private final /* synthetic */ double val$a;
        private final /* synthetic */ double val$b;

        1(double d, double d2) {
            this.val$b = d;
            this.val$a = d2;
        }

        protected double getB(int n, double x) {
            if (n % 2 == 0) {
                double m = ((double) n) / 2.0d;
                return (((this.val$b - m) * m) * x) / (((this.val$a + (2.0d * m)) - 1.0d) * (this.val$a + (2.0d * m)));
            }
            m = (((double) n) - 1.0d) / 2.0d;
            return (-(((this.val$a + m) * ((this.val$a + this.val$b) + m)) * x)) / ((this.val$a + (2.0d * m)) * ((this.val$a + (2.0d * m)) + 1.0d));
        }

        protected double getA(int n, double x) {
            return 1.0d;
        }
    }

    static {
        DELTA = new double[]{0.08333333333333333d, -2.777777777777778E-5d, 7.936507936507937E-8d, -5.952380952380953E-10d, 8.417508417508329E-12d, -1.917526917518546E-13d, 6.410256405103255E-15d, -2.955065141253382E-16d, 1.7964371635940225E-17d, -1.3922896466162779E-18d, 1.338028550140209E-19d, -1.542460098679661E-20d, 1.9770199298095743E-21d, -2.3406566479399704E-22d, 1.713480149663986E-23d};
    }

    private Beta() {
    }

    public static double regularizedBeta(double x, double a, double b) {
        return regularizedBeta(x, a, b, DEFAULT_EPSILON, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public static double regularizedBeta(double x, double a, double b, double epsilon) {
        return regularizedBeta(x, a, b, epsilon, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public static double regularizedBeta(double x, double a, double b, int maxIterations) {
        return regularizedBeta(x, a, b, DEFAULT_EPSILON, maxIterations);
    }

    public static double regularizedBeta(double x, double a, double b, double epsilon, int maxIterations) {
        if (Double.isNaN(x) || Double.isNaN(a) || Double.isNaN(b) || x < 0.0d || x > 1.0d || a <= 0.0d || b <= 0.0d) {
            return Double.NaN;
        }
        if (x > (1.0d + a) / ((2.0d + b) + a) && 1.0d - x <= (1.0d + b) / ((2.0d + b) + a)) {
            return 1.0d - regularizedBeta(1.0d - x, b, a, epsilon, maxIterations);
        }
        return (FastMath.exp((((FastMath.log(x) * a) + (FastMath.log1p(-x) * b)) - FastMath.log(a)) - logBeta(a, b)) * 1.0d) / new 1(b, a).evaluate(x, epsilon, maxIterations);
    }

    private static double logGammaSum(double a, double b) throws OutOfRangeException {
        if (a < 1.0d || a > 2.0d) {
            throw new OutOfRangeException(Double.valueOf(a), Double.valueOf(1.0d), Double.valueOf(2.0d));
        } else if (b < 1.0d || b > 2.0d) {
            throw new OutOfRangeException(Double.valueOf(b), Double.valueOf(1.0d), Double.valueOf(2.0d));
        } else {
            double x = (a - 1.0d) + (b - 1.0d);
            if (x <= 0.5d) {
                return Gamma.logGamma1p(1.0d + x);
            }
            if (x <= 1.5d) {
                return Gamma.logGamma1p(x) + FastMath.log1p(x);
            }
            return Gamma.logGamma1p(x - 1.0d) + FastMath.log((1.0d + x) * x);
        }
    }

    private static double logGammaMinusLogGammaSum(double a, double b) throws NumberIsTooSmallException {
        if (a < 0.0d) {
            throw new NumberIsTooSmallException(Double.valueOf(a), Double.valueOf(0.0d), true);
        } else if (b < BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
            throw new NumberIsTooSmallException(Double.valueOf(b), Double.valueOf(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS), true);
        } else {
            double d;
            double w;
            if (a <= b) {
                d = b + (a - 0.5d);
                w = deltaMinusDeltaSum(a, b);
            } else {
                d = a + (b - 0.5d);
                w = deltaMinusDeltaSum(b, a);
            }
            double u = d * FastMath.log1p(a / b);
            double v = a * (FastMath.log(b) - 1.0d);
            if (u <= v) {
                return (w - u) - v;
            }
            return (w - v) - u;
        }
    }

    private static double deltaMinusDeltaSum(double a, double b) throws OutOfRangeException, NumberIsTooSmallException {
        if (a < 0.0d || a > b) {
            throw new OutOfRangeException(Double.valueOf(a), Integer.valueOf(0), Double.valueOf(b));
        } else if (b < BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
            throw new NumberIsTooSmallException(Double.valueOf(b), Integer.valueOf(10), true);
        } else {
            double h = a / b;
            double p = h / (1.0d + h);
            double q = 1.0d / (1.0d + h);
            double q2 = q * q;
            double[] s = new double[DELTA.length];
            s[0] = 1.0d;
            int i = 1;
            while (true) {
                int length = s.length;
                if (i >= r0) {
                    break;
                }
                s[i] = 1.0d + ((s[i - 1] * q2) + q);
                i++;
            }
            double sqrtT = BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS / b;
            double t = sqrtT * sqrtT;
            double w = DELTA[DELTA.length - 1] * s[s.length - 1];
            for (i = DELTA.length - 2; i >= 0; i--) {
                w = (t * w) + (DELTA[i] * s[i]);
            }
            return (w * p) / b;
        }
    }

    private static double sumDeltaMinusDeltaSum(double p, double q) {
        if (p < BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
            throw new NumberIsTooSmallException(Double.valueOf(p), Double.valueOf(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS), true);
        } else if (q < BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
            throw new NumberIsTooSmallException(Double.valueOf(q), Double.valueOf(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS), true);
        } else {
            double a = FastMath.min(p, q);
            double b = FastMath.max(p, q);
            double sqrtT = BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS / a;
            double t = sqrtT * sqrtT;
            double z = DELTA[DELTA.length - 1];
            for (int i = DELTA.length - 2; i >= 0; i--) {
                z = (t * z) + DELTA[i];
            }
            return (z / a) + deltaMinusDeltaSum(a, b);
        }
    }

    public static double logBeta(double p, double q) {
        if (Double.isNaN(p) || Double.isNaN(q) || p <= 0.0d || q <= 0.0d) {
            return Double.NaN;
        }
        double a = FastMath.min(p, q);
        double b = FastMath.max(p, q);
        double h;
        if (a >= BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
            double w = sumDeltaMinusDeltaSum(a, b);
            h = a / b;
            double d = -(a - 0.5d);
            double u = r0 * FastMath.log(h / (1.0d + h));
            double v = b * FastMath.log1p(h);
            if (u <= v) {
                return ((((-0.5d * FastMath.log(b)) + HALF_LOG_TWO_PI) + w) - u) - v;
            }
            return ((((-0.5d * FastMath.log(b)) + HALF_LOG_TWO_PI) + w) - v) - u;
        } else if (a > 2.0d) {
            double ared;
            if (b > 1000.0d) {
                int n = (int) FastMath.floor(a - 1.0d);
                prod = 1.0d;
                ared = a;
                for (int i = 0; i < n; i++) {
                    ared -= 1.0d;
                    prod *= ared / (1.0d + (ared / b));
                }
                return (FastMath.log(prod) - (((double) n) * FastMath.log(b))) + (Gamma.logGamma(ared) + logGammaMinusLogGammaSum(ared, b));
            }
            double prod1 = 1.0d;
            ared = a;
            while (ared > 2.0d) {
                ared -= 1.0d;
                h = ared / b;
                prod1 *= h / (1.0d + h);
            }
            if (b >= BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
                return (FastMath.log(prod1) + Gamma.logGamma(ared)) + logGammaMinusLogGammaSum(ared, b);
            }
            double prod2 = 1.0d;
            bred = b;
            while (bred > 2.0d) {
                bred -= 1.0d;
                prod2 *= bred / (ared + bred);
            }
            return (FastMath.log(prod1) + FastMath.log(prod2)) + (Gamma.logGamma(ared) + (Gamma.logGamma(bred) - logGammaSum(ared, bred)));
        } else if (a >= 1.0d) {
            if (b <= 2.0d) {
                return (Gamma.logGamma(a) + Gamma.logGamma(b)) - logGammaSum(a, b);
            }
            if (b >= BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
                return Gamma.logGamma(a) + logGammaMinusLogGammaSum(a, b);
            }
            prod = 1.0d;
            bred = b;
            while (bred > 2.0d) {
                bred -= 1.0d;
                prod *= bred / (a + bred);
            }
            return FastMath.log(prod) + (Gamma.logGamma(a) + (Gamma.logGamma(bred) - logGammaSum(a, bred)));
        } else if (b >= BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) {
            return Gamma.logGamma(a) + logGammaMinusLogGammaSum(a, b);
        } else {
            return FastMath.log((Gamma.gamma(a) * Gamma.gamma(b)) / Gamma.gamma(a + b));
        }
    }
}
