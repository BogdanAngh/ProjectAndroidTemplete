package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Erf;
import org.apache.commons.math4.util.FastMath;

public class LevyDistribution extends AbstractRealDistribution {
    private static final long serialVersionUID = 20130314;
    private final double c;
    private final double halfC;
    private final double mu;

    public LevyDistribution(double mu, double c) {
        this(new Well19937c(), mu, c);
    }

    public LevyDistribution(RandomGenerator rng, double mu, double c) {
        super(rng);
        this.mu = mu;
        this.c = c;
        this.halfC = 0.5d * c;
    }

    public double density(double x) {
        if (x < this.mu) {
            return Double.NaN;
        }
        double delta = x - this.mu;
        double f = this.halfC / delta;
        return (FastMath.sqrt(f / FastMath.PI) * FastMath.exp(-f)) / delta;
    }

    public double logDensity(double x) {
        if (x < this.mu) {
            return Double.NaN;
        }
        double delta = x - this.mu;
        double f = this.halfC / delta;
        return ((0.5d * FastMath.log(f / FastMath.PI)) - f) - FastMath.log(delta);
    }

    public double cumulativeProbability(double x) {
        if (x < this.mu) {
            return Double.NaN;
        }
        return Erf.erfc(FastMath.sqrt(this.halfC / (x - this.mu)));
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
        }
        double t = Erf.erfcInv(p);
        return this.mu + (this.halfC / (t * t));
    }

    public double getScale() {
        return this.c;
    }

    public double getLocation() {
        return this.mu;
    }

    public double getNumericalMean() {
        return Double.POSITIVE_INFINITY;
    }

    public double getNumericalVariance() {
        return Double.POSITIVE_INFINITY;
    }

    public double getSupportLowerBound() {
        return this.mu;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportConnected() {
        return true;
    }
}
