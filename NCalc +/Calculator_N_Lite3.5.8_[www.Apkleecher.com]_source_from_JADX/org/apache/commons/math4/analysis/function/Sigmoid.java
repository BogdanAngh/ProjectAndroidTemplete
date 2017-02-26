package org.apache.commons.math4.analysis.function;

import java.util.Arrays;
import org.apache.commons.math4.analysis.ParametricUnivariateFunction;
import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.util.FastMath;

public class Sigmoid implements UnivariateDifferentiableFunction {
    private final double hi;
    private final double lo;

    public static class Parametric implements ParametricUnivariateFunction {
        public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException {
            validateParameters(param);
            return Sigmoid.value(x, param[0], param[1]);
        }

        public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException {
            validateParameters(param);
            double invExp1 = 1.0d / (FastMath.exp(-x) + 1.0d);
            return new double[]{1.0d - invExp1, invExp1};
        }

        private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException {
            if (param == null) {
                throw new NullArgumentException();
            } else if (param.length != 2) {
                throw new DimensionMismatchException(param.length, 2);
            }
        }
    }

    public Sigmoid() {
        this(0.0d, 1.0d);
    }

    public Sigmoid(double lo, double hi) {
        this.lo = lo;
        this.hi = hi;
    }

    public double value(double x) {
        return value(x, this.lo, this.hi);
    }

    private static double value(double x, double lo, double hi) {
        return ((hi - lo) / (1.0d + FastMath.exp(-x))) + lo;
    }

    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        double[] f = new double[(t.getOrder() + 1)];
        double exp = FastMath.exp(-t.getValue());
        if (Double.isInfinite(exp)) {
            f[0] = this.lo;
            Arrays.fill(f, 1, f.length, 0.0d);
        } else {
            double[] p = new double[f.length];
            double inv = 1.0d / (1.0d + exp);
            double coeff = this.hi - this.lo;
            for (int n = 0; n < f.length; n++) {
                double v = 0.0d;
                p[n] = 1.0d;
                for (int k = n; k >= 0; k--) {
                    v = (v * exp) + p[k];
                    if (k > 1) {
                        p[k - 1] = (((double) ((n - k) + 2)) * p[k - 2]) - (((double) (k - 1)) * p[k - 1]);
                    } else {
                        p[0] = 0.0d;
                    }
                }
                coeff *= inv;
                f[n] = coeff * v;
            }
            f[0] = f[0] + this.lo;
        }
        return t.compose(f);
    }
}
