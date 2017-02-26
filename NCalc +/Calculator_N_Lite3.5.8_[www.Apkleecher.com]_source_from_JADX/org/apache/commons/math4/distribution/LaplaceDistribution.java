package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class LaplaceDistribution extends AbstractRealDistribution {
    private static final long serialVersionUID = 20141003;
    private final double beta;
    private final double mu;

    public LaplaceDistribution(double mu, double beta) {
        this(new Well19937c(), mu, beta);
    }

    public LaplaceDistribution(RandomGenerator rng, double mu, double beta) {
        super(rng);
        if (beta <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_SCALE, Double.valueOf(beta));
        }
        this.mu = mu;
        this.beta = beta;
    }

    public double getLocation() {
        return this.mu;
    }

    public double getScale() {
        return this.beta;
    }

    public double density(double x) {
        return FastMath.exp((-FastMath.abs(x - this.mu)) / this.beta) / (2.0d * this.beta);
    }

    public double cumulativeProbability(double x) {
        if (x <= this.mu) {
            return FastMath.exp((x - this.mu) / this.beta) / 2.0d;
        }
        return 1.0d - (FastMath.exp((this.mu - x) / this.beta) / 2.0d);
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0d), Double.valueOf(1.0d));
        } else if (p == 0.0d) {
            return Double.NEGATIVE_INFINITY;
        } else {
            if (p == 1.0d) {
                return Double.POSITIVE_INFINITY;
            }
            return this.mu + (this.beta * (p > 0.5d ? -Math.log(2.0d - (2.0d * p)) : Math.log(2.0d * p)));
        }
    }

    public double getNumericalMean() {
        return this.mu;
    }

    public double getNumericalVariance() {
        return (2.0d * this.beta) * this.beta;
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
