package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.ParametricUnivariateFunction;
import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.util.FastMath;

public class HarmonicOscillator implements UnivariateDifferentiableFunction {
    private final double amplitude;
    private final double omega;
    private final double phase;

    public static class Parametric implements ParametricUnivariateFunction {
        public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException {
            validateParameters(param);
            return HarmonicOscillator.value((param[1] * x) + param[2], param[0]);
        }

        public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException {
            validateParameters(param);
            double amplitude = param[0];
            double xTimesOmegaPlusPhase = (param[1] * x) + param[2];
            double a = HarmonicOscillator.value(xTimesOmegaPlusPhase, 1.0d);
            double w = ((-amplitude) * FastMath.sin(xTimesOmegaPlusPhase)) * x;
            return new double[]{a, w, (-amplitude) * FastMath.sin(xTimesOmegaPlusPhase)};
        }

        private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException {
            if (param == null) {
                throw new NullArgumentException();
            } else if (param.length != 3) {
                throw new DimensionMismatchException(param.length, 3);
            }
        }
    }

    public HarmonicOscillator(double amplitude, double omega, double phase) {
        this.amplitude = amplitude;
        this.omega = omega;
        this.phase = phase;
    }

    public double value(double x) {
        return value((this.omega * x) + this.phase, this.amplitude);
    }

    private static double value(double xTimesOmegaPlusPhase, double amplitude) {
        return FastMath.cos(xTimesOmegaPlusPhase) * amplitude;
    }

    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        double[] f = new double[(t.getOrder() + 1)];
        double alpha = (this.omega * t.getValue()) + this.phase;
        f[0] = this.amplitude * FastMath.cos(alpha);
        if (f.length > 1) {
            f[1] = ((-this.amplitude) * this.omega) * FastMath.sin(alpha);
            double mo2 = (-this.omega) * this.omega;
            for (int i = 2; i < f.length; i++) {
                f[i] = f[i - 2] * mo2;
            }
        }
        return t.compose(f);
    }
}
