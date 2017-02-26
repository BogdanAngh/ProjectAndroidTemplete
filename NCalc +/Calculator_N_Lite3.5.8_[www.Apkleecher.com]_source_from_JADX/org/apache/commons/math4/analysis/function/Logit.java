package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.ParametricUnivariateFunction;
import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.util.FastMath;

public class Logit implements UnivariateDifferentiableFunction {
    private final double hi;
    private final double lo;

    public static class Parametric implements ParametricUnivariateFunction {
        public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException {
            validateParameters(param);
            return Logit.value(x, param[0], param[1]);
        }

        public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException {
            validateParameters(param);
            double lo = param[0];
            double hi = param[1];
            return new double[]{1.0d / (lo - x), 1.0d / (hi - x)};
        }

        private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException {
            if (param == null) {
                throw new NullArgumentException();
            } else if (param.length != 2) {
                throw new DimensionMismatchException(param.length, 2);
            }
        }
    }

    public Logit() {
        this(0.0d, 1.0d);
    }

    public Logit(double lo, double hi) {
        this.lo = lo;
        this.hi = hi;
    }

    public double value(double x) throws OutOfRangeException {
        return value(x, this.lo, this.hi);
    }

    private static double value(double x, double lo, double hi) throws OutOfRangeException {
        if (x >= lo && x <= hi) {
            return FastMath.log((x - lo) / (hi - x));
        }
        throw new OutOfRangeException(Double.valueOf(x), Double.valueOf(lo), Double.valueOf(hi));
    }

    public DerivativeStructure value(DerivativeStructure t) throws OutOfRangeException {
        double x = t.getValue();
        if (x < this.lo || x > this.hi) {
            throw new OutOfRangeException(Double.valueOf(x), Double.valueOf(this.lo), Double.valueOf(this.hi));
        }
        double[] f = new double[(t.getOrder() + 1)];
        f[0] = FastMath.log((x - this.lo) / (this.hi - x));
        int i;
        if (Double.isInfinite(f[0])) {
            if (f.length > 1) {
                f[1] = Double.POSITIVE_INFINITY;
            }
            for (i = 2; i < f.length; i++) {
                f[i] = f[i - 2];
            }
        } else {
            double invL = 1.0d / (x - this.lo);
            double xL = invL;
            double invH = 1.0d / (this.hi - x);
            double xH = invH;
            for (i = 1; i < f.length; i++) {
                f[i] = xL + xH;
                xL *= ((double) (-i)) * invL;
                xH *= ((double) i) * invH;
            }
        }
        return t.compose(f);
    }
}
