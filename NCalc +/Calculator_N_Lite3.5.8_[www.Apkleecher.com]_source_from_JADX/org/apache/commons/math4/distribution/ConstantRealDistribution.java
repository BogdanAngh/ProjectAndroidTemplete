package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.OutOfRangeException;

public class ConstantRealDistribution extends AbstractRealDistribution {
    private static final long serialVersionUID = -4157745166772046273L;
    private final double value;

    public ConstantRealDistribution(double value) {
        super(null);
        this.value = value;
    }

    public double density(double x) {
        return (double) (x == this.value ? 1 : 0);
    }

    public double cumulativeProbability(double x) {
        return (double) (x < this.value ? 0 : 1);
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p >= 0.0d && p <= 1.0d) {
            return this.value;
        }
        throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
    }

    public double getNumericalMean() {
        return this.value;
    }

    public double getNumericalVariance() {
        return 0.0d;
    }

    public double getSupportLowerBound() {
        return this.value;
    }

    public double getSupportUpperBound() {
        return this.value;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public double sample() {
        return this.value;
    }

    public void reseedRandomGenerator(long seed) {
    }
}
