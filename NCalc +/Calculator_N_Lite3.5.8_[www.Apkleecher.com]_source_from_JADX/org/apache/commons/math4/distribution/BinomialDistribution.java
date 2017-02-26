package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Beta;
import org.apache.commons.math4.util.FastMath;

public class BinomialDistribution extends AbstractIntegerDistribution {
    private static final long serialVersionUID = 6751309484392813623L;
    private final int numberOfTrials;
    private final double probabilityOfSuccess;

    public BinomialDistribution(int trials, double p) {
        this(new Well19937c(), trials, p);
    }

    public BinomialDistribution(RandomGenerator rng, int trials, double p) {
        super(rng);
        if (trials < 0) {
            throw new NotPositiveException(LocalizedFormats.NUMBER_OF_TRIALS, Integer.valueOf(trials));
        } else if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
        } else {
            this.probabilityOfSuccess = p;
            this.numberOfTrials = trials;
        }
    }

    public int getNumberOfTrials() {
        return this.numberOfTrials;
    }

    public double getProbabilityOfSuccess() {
        return this.probabilityOfSuccess;
    }

    public double probability(int x) {
        double logProbability = logProbability(x);
        return logProbability == Double.NEGATIVE_INFINITY ? 0.0d : FastMath.exp(logProbability);
    }

    public double logProbability(int x) {
        if (this.numberOfTrials == 0) {
            return x == 0 ? 0.0d : Double.NEGATIVE_INFINITY;
        } else {
            double ret;
            if (x < 0 || x > this.numberOfTrials) {
                ret = Double.NEGATIVE_INFINITY;
            } else {
                ret = SaddlePointExpansion.logBinomialProbability(x, this.numberOfTrials, this.probabilityOfSuccess, 1.0d - this.probabilityOfSuccess);
            }
            return ret;
        }
    }

    public double cumulativeProbability(int x) {
        if (x < 0) {
            return 0.0d;
        }
        if (x >= this.numberOfTrials) {
            return 1.0d;
        }
        return 1.0d - Beta.regularizedBeta(this.probabilityOfSuccess, ((double) x) + 1.0d, (double) (this.numberOfTrials - x));
    }

    public double getNumericalMean() {
        return ((double) this.numberOfTrials) * this.probabilityOfSuccess;
    }

    public double getNumericalVariance() {
        double p = this.probabilityOfSuccess;
        return (((double) this.numberOfTrials) * p) * (1.0d - p);
    }

    public int getSupportLowerBound() {
        return this.probabilityOfSuccess < 1.0d ? 0 : this.numberOfTrials;
    }

    public int getSupportUpperBound() {
        return this.probabilityOfSuccess > 0.0d ? this.numberOfTrials : 0;
    }

    public boolean isSupportConnected() {
        return true;
    }
}
