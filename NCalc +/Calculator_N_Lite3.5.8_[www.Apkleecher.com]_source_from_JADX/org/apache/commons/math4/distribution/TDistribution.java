package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Beta;
import org.apache.commons.math4.special.Gamma;
import org.apache.commons.math4.util.FastMath;

public class TDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = -5852615386664158222L;
    private final double degreesOfFreedom;
    private final double factor;
    private final double solverAbsoluteAccuracy;

    public TDistribution(double degreesOfFreedom) throws NotStrictlyPositiveException {
        this(degreesOfFreedom, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public TDistribution(double degreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        this(new Well19937c(), degreesOfFreedom, inverseCumAccuracy);
    }

    public TDistribution(RandomGenerator rng, double degreesOfFreedom) throws NotStrictlyPositiveException {
        this(rng, degreesOfFreedom, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public TDistribution(RandomGenerator rng, double degreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);
        if (degreesOfFreedom <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.DEGREES_OF_FREEDOM, Double.valueOf(degreesOfFreedom));
        }
        this.degreesOfFreedom = degreesOfFreedom;
        this.solverAbsoluteAccuracy = inverseCumAccuracy;
        double n = degreesOfFreedom;
        this.factor = (Gamma.logGamma((1.0d + n) / 2.0d) - (0.5d * (FastMath.log(FastMath.PI) + FastMath.log(n)))) - Gamma.logGamma(n / 2.0d);
    }

    public double getDegreesOfFreedom() {
        return this.degreesOfFreedom;
    }

    public double density(double x) {
        return FastMath.exp(logDensity(x));
    }

    public double logDensity(double x) {
        double n = this.degreesOfFreedom;
        return this.factor - (FastMath.log(((x * x) / n) + 1.0d) * ((n + 1.0d) / 2.0d));
    }

    public double cumulativeProbability(double x) {
        if (x == 0.0d) {
            return 0.5d;
        }
        double t = Beta.regularizedBeta(this.degreesOfFreedom / (this.degreesOfFreedom + (x * x)), 0.5d * this.degreesOfFreedom, 0.5d);
        if (x < 0.0d) {
            return 0.5d * t;
        }
        return 1.0d - (0.5d * t);
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        if (getDegreesOfFreedom() > 1.0d) {
            return 0.0d;
        }
        return Double.NaN;
    }

    public double getNumericalVariance() {
        double df = getDegreesOfFreedom();
        if (df > 2.0d) {
            return df / (df - 2.0d);
        }
        if (df <= 1.0d || df > 2.0d) {
            return Double.NaN;
        }
        return Double.POSITIVE_INFINITY;
    }

    public double getSupportLowerBound() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportConnected() {
        return true;
    }
}
