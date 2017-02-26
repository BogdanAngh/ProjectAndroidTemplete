package org.apache.commons.math4.analysis.function;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.Arrays;
import org.apache.commons.math4.analysis.ParametricUnivariateFunction;
import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

public class Gaussian implements UnivariateDifferentiableFunction {
    private final double i2s2;
    private final double is;
    private final double mean;
    private final double norm;

    public static class Parametric implements ParametricUnivariateFunction {
        public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
            validateParameters(param);
            return Gaussian.value(x - param[1], param[0], 1.0d / ((2.0d * param[2]) * param[2]));
        }

        public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
            validateParameters(param);
            double norm = param[0];
            double diff = x - param[1];
            double sigma = param[2];
            double i2s2 = 1.0d / ((2.0d * sigma) * sigma);
            double s = (((((norm * Gaussian.value(diff, 1.0d, i2s2)) * 2.0d) * i2s2) * diff) * diff) / sigma;
            return new double[]{Gaussian.value(diff, 1.0d, i2s2), (((norm * Gaussian.value(diff, 1.0d, i2s2)) * 2.0d) * i2s2) * diff, s};
        }

        private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
            if (param == null) {
                throw new NullArgumentException();
            } else if (param.length != 3) {
                throw new DimensionMismatchException(param.length, 3);
            } else if (param[2] <= 0.0d) {
                throw new NotStrictlyPositiveException(Double.valueOf(param[2]));
            }
        }
    }

    public Gaussian(double norm, double mean, double sigma) throws NotStrictlyPositiveException {
        if (sigma <= 0.0d) {
            throw new NotStrictlyPositiveException(Double.valueOf(sigma));
        }
        this.norm = norm;
        this.mean = mean;
        this.is = 1.0d / sigma;
        this.i2s2 = (0.5d * this.is) * this.is;
    }

    public Gaussian(double mean, double sigma) throws NotStrictlyPositiveException {
        this(1.0d / (FastMath.sqrt(Angle2D.M_2PI) * sigma), mean, sigma);
    }

    public Gaussian() {
        this(0.0d, 1.0d);
    }

    public double value(double x) {
        return value(x - this.mean, this.norm, this.i2s2);
    }

    private static double value(double xMinusMean, double norm, double i2s2) {
        return FastMath.exp(((-xMinusMean) * xMinusMean) * i2s2) * norm;
    }

    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        double u = this.is * (t.getValue() - this.mean);
        double[] f = new double[(t.getOrder() + 1)];
        double[] p = new double[f.length];
        p[0] = 1.0d;
        double u2 = u * u;
        double coeff = this.norm * FastMath.exp(-0.5d * u2);
        if (coeff <= Precision.SAFE_MIN) {
            Arrays.fill(f, 0.0d);
        } else {
            f[0] = coeff;
            for (int n = 1; n < f.length; n++) {
                double v = 0.0d;
                p[n] = -p[n - 1];
                for (int k = n; k >= 0; k -= 2) {
                    v = (v * u2) + p[k];
                    if (k > 2) {
                        p[k - 2] = (((double) (k - 1)) * p[k - 1]) - p[k - 3];
                    } else if (k == 2) {
                        p[0] = p[1];
                    }
                }
                if ((n & 1) == 1) {
                    v *= u;
                }
                coeff *= this.is;
                f[n] = coeff * v;
            }
        }
        return t.compose(f);
    }
}
