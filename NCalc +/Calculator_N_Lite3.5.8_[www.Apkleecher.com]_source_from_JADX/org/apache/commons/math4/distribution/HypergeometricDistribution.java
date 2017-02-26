package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class HypergeometricDistribution extends AbstractIntegerDistribution {
    private static final long serialVersionUID = -436928820673516179L;
    private final int numberOfSuccesses;
    private double numericalVariance;
    private boolean numericalVarianceIsCalculated;
    private final int populationSize;
    private final int sampleSize;

    public HypergeometricDistribution(int populationSize, int numberOfSuccesses, int sampleSize) throws NotPositiveException, NotStrictlyPositiveException, NumberIsTooLargeException {
        this(new Well19937c(), populationSize, numberOfSuccesses, sampleSize);
    }

    public HypergeometricDistribution(RandomGenerator rng, int populationSize, int numberOfSuccesses, int sampleSize) throws NotPositiveException, NotStrictlyPositiveException, NumberIsTooLargeException {
        super(rng);
        this.numericalVariance = Double.NaN;
        this.numericalVarianceIsCalculated = false;
        if (populationSize <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.POPULATION_SIZE, Integer.valueOf(populationSize));
        } else if (numberOfSuccesses < 0) {
            throw new NotPositiveException(LocalizedFormats.NUMBER_OF_SUCCESSES, Integer.valueOf(numberOfSuccesses));
        } else if (sampleSize < 0) {
            throw new NotPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
        } else if (numberOfSuccesses > populationSize) {
            throw new NumberIsTooLargeException(LocalizedFormats.NUMBER_OF_SUCCESS_LARGER_THAN_POPULATION_SIZE, Integer.valueOf(numberOfSuccesses), Integer.valueOf(populationSize), true);
        } else if (sampleSize > populationSize) {
            throw new NumberIsTooLargeException(LocalizedFormats.SAMPLE_SIZE_LARGER_THAN_POPULATION_SIZE, Integer.valueOf(sampleSize), Integer.valueOf(populationSize), true);
        } else {
            this.numberOfSuccesses = numberOfSuccesses;
            this.populationSize = populationSize;
            this.sampleSize = sampleSize;
        }
    }

    public double cumulativeProbability(int x) {
        int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
        if (x < domain[0]) {
            return 0.0d;
        }
        if (x >= domain[1]) {
            return 1.0d;
        }
        return innerCumulativeProbability(domain[0], x, 1);
    }

    private int[] getDomain(int n, int m, int k) {
        return new int[]{getLowerDomain(n, m, k), getUpperDomain(m, k)};
    }

    private int getLowerDomain(int n, int m, int k) {
        return FastMath.max(0, m - (n - k));
    }

    public int getNumberOfSuccesses() {
        return this.numberOfSuccesses;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public int getSampleSize() {
        return this.sampleSize;
    }

    private int getUpperDomain(int m, int k) {
        return FastMath.min(k, m);
    }

    public double probability(int x) {
        double logProbability = logProbability(x);
        return logProbability == Double.NEGATIVE_INFINITY ? 0.0d : FastMath.exp(logProbability);
    }

    public double logProbability(int x) {
        int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
        if (x < domain[0] || x > domain[1]) {
            return Double.NEGATIVE_INFINITY;
        }
        double p = ((double) this.sampleSize) / ((double) this.populationSize);
        double q = ((double) (this.populationSize - this.sampleSize)) / ((double) this.populationSize);
        double p1 = SaddlePointExpansion.logBinomialProbability(x, this.numberOfSuccesses, p, q);
        double p2 = SaddlePointExpansion.logBinomialProbability(this.sampleSize - x, this.populationSize - this.numberOfSuccesses, p, q);
        return (p1 + p2) - SaddlePointExpansion.logBinomialProbability(this.sampleSize, this.populationSize, p, q);
    }

    public double upperCumulativeProbability(int x) {
        int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
        if (x <= domain[0]) {
            return 1.0d;
        }
        if (x > domain[1]) {
            return 0.0d;
        }
        return innerCumulativeProbability(domain[1], x, -1);
    }

    private double innerCumulativeProbability(int x0, int x1, int dx) {
        double ret = probability(x0);
        while (x0 != x1) {
            x0 += dx;
            ret += probability(x0);
        }
        return ret;
    }

    public double getNumericalMean() {
        return ((double) getSampleSize()) * (((double) getNumberOfSuccesses()) / ((double) getPopulationSize()));
    }

    public double getNumericalVariance() {
        if (!this.numericalVarianceIsCalculated) {
            this.numericalVariance = calculateNumericalVariance();
            this.numericalVarianceIsCalculated = true;
        }
        return this.numericalVariance;
    }

    protected double calculateNumericalVariance() {
        double N = (double) getPopulationSize();
        double m = (double) getNumberOfSuccesses();
        double n = (double) getSampleSize();
        return (((n * m) * (N - n)) * (N - m)) / ((N * N) * (N - 1.0d));
    }

    public int getSupportLowerBound() {
        return FastMath.max(0, (getSampleSize() + getNumberOfSuccesses()) - getPopulationSize());
    }

    public int getSupportUpperBound() {
        return FastMath.min(getNumberOfSuccesses(), getSampleSize());
    }

    public boolean isSupportConnected() {
        return true;
    }
}
