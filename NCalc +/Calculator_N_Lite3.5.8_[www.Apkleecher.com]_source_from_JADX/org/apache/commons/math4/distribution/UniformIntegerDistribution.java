package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;

public class UniformIntegerDistribution extends AbstractIntegerDistribution {
    private static final long serialVersionUID = 20120109;
    private final int lower;
    private final int upper;

    public UniformIntegerDistribution(int lower, int upper) throws NumberIsTooLargeException {
        this(new Well19937c(), lower, upper);
    }

    public UniformIntegerDistribution(RandomGenerator rng, int lower, int upper) throws NumberIsTooLargeException {
        super(rng);
        if (lower > upper) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Integer.valueOf(lower), Integer.valueOf(upper), true);
        }
        this.lower = lower;
        this.upper = upper;
    }

    public double probability(int x) {
        if (x < this.lower || x > this.upper) {
            return 0.0d;
        }
        return 1.0d / ((double) ((this.upper - this.lower) + 1));
    }

    public double cumulativeProbability(int x) {
        if (x < this.lower) {
            return 0.0d;
        }
        if (x <= this.upper) {
            return (((double) (x - this.lower)) + 1.0d) / (1.0d + ((double) (this.upper - this.lower)));
        }
        return 1.0d;
    }

    public double getNumericalMean() {
        return 0.5d * ((double) (this.lower + this.upper));
    }

    public double getNumericalVariance() {
        double n = (double) ((this.upper - this.lower) + 1);
        return ((n * n) - 1.0d) / 12.0d;
    }

    public int getSupportLowerBound() {
        return this.lower;
    }

    public int getSupportUpperBound() {
        return this.upper;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public int sample() {
        int max = (this.upper - this.lower) + 1;
        if (max > 0) {
            return this.lower + this.random.nextInt(max);
        }
        while (true) {
            int r = this.random.nextInt();
            if (r >= this.lower && r <= this.upper) {
                return r;
            }
        }
    }
}
