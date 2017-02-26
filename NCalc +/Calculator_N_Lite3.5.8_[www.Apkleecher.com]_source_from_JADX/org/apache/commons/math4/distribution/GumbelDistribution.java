package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class GumbelDistribution extends AbstractRealDistribution {
    private static final double EULER = 0.5778636748954609d;
    private static final long serialVersionUID = 20141003;
    private final double beta;
    private final double mu;

    public GumbelDistribution(double mu, double beta) {
        this(new Well19937c(), mu, beta);
    }

    public GumbelDistribution(RandomGenerator rng, double mu, double beta) {
        super(rng);
        if (beta <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(beta));
        }
        this.beta = beta;
        this.mu = mu;
    }

    public double getLocation() {
        return this.mu;
    }

    public double getScale() {
        return this.beta;
    }

    public double density(double x) {
        double z = (x - this.mu) / this.beta;
        return FastMath.exp((-z) - FastMath.exp(-z)) / this.beta;
    }

    public double cumulativeProbability(double x) {
        return FastMath.exp(-FastMath.exp(-((x - this.mu) / this.beta)));
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
            return this.mu - (FastMath.log(-FastMath.log(p)) * this.beta);
        }
    }

    public double getNumericalMean() {
        return this.mu + (EULER * this.beta);
    }

    public double getNumericalVariance() {
        return 1.6449340668482264d * (this.beta * this.beta);
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
