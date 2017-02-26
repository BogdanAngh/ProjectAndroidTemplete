package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Gamma;
import org.apache.commons.math4.util.FastMath;

public class NakagamiDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = 20141003;
    private final double inverseAbsoluteAccuracy;
    private final double mu;
    private final double omega;

    public NakagamiDistribution(double mu, double omega) {
        this(mu, omega, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public NakagamiDistribution(double mu, double omega, double inverseAbsoluteAccuracy) {
        this(new Well19937c(), mu, omega, inverseAbsoluteAccuracy);
    }

    public NakagamiDistribution(RandomGenerator rng, double mu, double omega, double inverseAbsoluteAccuracy) {
        super(rng);
        if (mu < 0.5d) {
            throw new NumberIsTooSmallException(Double.valueOf(mu), Double.valueOf(0.5d), true);
        } else if (omega <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_SCALE, Double.valueOf(omega));
        } else {
            this.mu = mu;
            this.omega = omega;
            this.inverseAbsoluteAccuracy = inverseAbsoluteAccuracy;
        }
    }

    public double getShape() {
        return this.mu;
    }

    public double getScale() {
        return this.omega;
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.inverseAbsoluteAccuracy;
    }

    public double density(double x) {
        if (x <= 0.0d) {
            return 0.0d;
        }
        return (((FastMath.pow(this.mu, this.mu) * 2.0d) / (Gamma.gamma(this.mu) * FastMath.pow(this.omega, this.mu))) * FastMath.pow(x, (this.mu * 2.0d) - 1.0d)) * FastMath.exp((((-this.mu) * x) * x) / this.omega);
    }

    public double cumulativeProbability(double x) {
        return Gamma.regularizedGammaP(this.mu, ((this.mu * x) * x) / this.omega);
    }

    public double getNumericalMean() {
        return (Gamma.gamma(this.mu + 0.5d) / Gamma.gamma(this.mu)) * FastMath.sqrt(this.omega / this.mu);
    }

    public double getNumericalVariance() {
        double v = Gamma.gamma(this.mu + 0.5d) / Gamma.gamma(this.mu);
        return this.omega * (1.0d - (((1.0d / this.mu) * v) * v));
    }

    public double getSupportLowerBound() {
        return 0.0d;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportConnected() {
        return true;
    }
}
