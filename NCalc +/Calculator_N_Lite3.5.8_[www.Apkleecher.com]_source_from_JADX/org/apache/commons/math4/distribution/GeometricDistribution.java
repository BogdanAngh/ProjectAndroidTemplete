package org.apache.commons.math4.distribution;

import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class GeometricDistribution extends AbstractIntegerDistribution {
    private static final long serialVersionUID = 20130507;
    private final double probabilityOfSuccess;

    public GeometricDistribution(double p) {
        this(new Well19937c(), p);
    }

    public GeometricDistribution(RandomGenerator rng, double p) {
        super(rng);
        if (p <= 0.0d || p > 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_LEFT, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
        }
        this.probabilityOfSuccess = p;
    }

    public double getProbabilityOfSuccess() {
        return this.probabilityOfSuccess;
    }

    public double probability(int x) {
        if (x < 0) {
            return 0.0d;
        }
        double p = this.probabilityOfSuccess;
        return FastMath.pow(1.0d - p, x) * p;
    }

    public double logProbability(int x) {
        if (x < 0) {
            return Double.NEGATIVE_INFINITY;
        }
        double p = this.probabilityOfSuccess;
        return (((double) x) * FastMath.log1p(-p)) + FastMath.log(p);
    }

    public double cumulativeProbability(int x) {
        if (x < 0) {
            return 0.0d;
        }
        return 1.0d - FastMath.pow(1.0d - this.probabilityOfSuccess, x + 1);
    }

    public double getNumericalMean() {
        double p = this.probabilityOfSuccess;
        return (1.0d - p) / p;
    }

    public double getNumericalVariance() {
        double p = this.probabilityOfSuccess;
        return (1.0d - p) / (p * p);
    }

    public int getSupportLowerBound() {
        return 0;
    }

    public int getSupportUpperBound() {
        return BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
    }

    public boolean isSupportConnected() {
        return true;
    }
}
