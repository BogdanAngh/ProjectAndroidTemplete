package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;

public class UniformRealDistribution extends AbstractRealDistribution {
    private static final long serialVersionUID = 20120109;
    private final double lower;
    private final double upper;

    public UniformRealDistribution() {
        this(0.0d, 1.0d);
    }

    public UniformRealDistribution(double lower, double upper) throws NumberIsTooLargeException {
        this(new Well19937c(), lower, upper);
    }

    public UniformRealDistribution(RandomGenerator rng, double lower, double upper) throws NumberIsTooLargeException {
        super(rng);
        if (lower >= upper) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Double.valueOf(lower), Double.valueOf(upper), false);
        }
        this.lower = lower;
        this.upper = upper;
    }

    public double density(double x) {
        if (x < this.lower || x > this.upper) {
            return 0.0d;
        }
        return 1.0d / (this.upper - this.lower);
    }

    public double cumulativeProbability(double x) {
        if (x <= this.lower) {
            return 0.0d;
        }
        if (x >= this.upper) {
            return 1.0d;
        }
        return (x - this.lower) / (this.upper - this.lower);
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p >= 0.0d && p <= 1.0d) {
            return ((this.upper - this.lower) * p) + this.lower;
        }
        throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
    }

    public double getNumericalMean() {
        return 0.5d * (this.lower + this.upper);
    }

    public double getNumericalVariance() {
        double ul = this.upper - this.lower;
        return (ul * ul) / 12.0d;
    }

    public double getSupportLowerBound() {
        return this.lower;
    }

    public double getSupportUpperBound() {
        return this.upper;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public double sample() {
        double u = this.random.nextDouble();
        return (this.upper * u) + ((1.0d - u) * this.lower);
    }
}
