package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class LogisticDistribution extends AbstractRealDistribution {
    private static final long serialVersionUID = 20141003;
    private final double mu;
    private final double s;

    public LogisticDistribution(double mu, double s) {
        this(new Well19937c(), mu, s);
    }

    public LogisticDistribution(RandomGenerator rng, double mu, double s) {
        super(rng);
        if (s <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_SCALE, Double.valueOf(s));
        }
        this.mu = mu;
        this.s = s;
    }

    public double getLocation() {
        return this.mu;
    }

    public double getScale() {
        return this.s;
    }

    public double density(double x) {
        double v = FastMath.exp(-((x - this.mu) / this.s));
        return ((1.0d / this.s) * v) / ((1.0d + v) * (1.0d + v));
    }

    public double cumulativeProbability(double x) {
        return 1.0d / (FastMath.exp(-((1.0d / this.s) * (x - this.mu))) + 1.0d);
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0d), Double.valueOf(1.0d));
        } else if (p == 0.0d) {
            return 0.0d;
        } else {
            if (p == 1.0d) {
                return Double.POSITIVE_INFINITY;
            }
            return (this.s * Math.log(p / (1.0d - p))) + this.mu;
        }
    }

    public double getNumericalMean() {
        return this.mu;
    }

    public double getNumericalVariance() {
        return 3.289868133696453d * (1.0d / (this.s * this.s));
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
