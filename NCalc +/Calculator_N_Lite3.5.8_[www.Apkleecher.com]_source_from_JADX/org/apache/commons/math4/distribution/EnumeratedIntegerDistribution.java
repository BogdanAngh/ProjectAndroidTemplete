package org.apache.commons.math4.distribution;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotANumberException;
import org.apache.commons.math4.exception.NotFiniteNumberException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.Pair;

public class EnumeratedIntegerDistribution extends AbstractIntegerDistribution {
    private static final long serialVersionUID = 20130308;
    protected final EnumeratedDistribution<Integer> innerDistribution;

    public EnumeratedIntegerDistribution(int[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
        this(new Well19937c(), singletons, probabilities);
    }

    public EnumeratedIntegerDistribution(RandomGenerator rng, int[] singletons, double[] probabilities) throws DimensionMismatchException, NotPositiveException, MathArithmeticException, NotFiniteNumberException, NotANumberException {
        super(rng);
        if (singletons.length != probabilities.length) {
            throw new DimensionMismatchException(probabilities.length, singletons.length);
        }
        List<Pair<Integer, Double>> samples = new ArrayList(singletons.length);
        for (int i = 0; i < singletons.length; i++) {
            samples.add(new Pair(Integer.valueOf(singletons[i]), Double.valueOf(probabilities[i])));
        }
        this.innerDistribution = new EnumeratedDistribution(rng, samples);
    }

    public double probability(int x) {
        return this.innerDistribution.probability(Integer.valueOf(x));
    }

    public double cumulativeProbability(int x) {
        double probability = 0.0d;
        for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
            if (((Integer) sample.getKey()).intValue() <= x) {
                probability += ((Double) sample.getValue()).doubleValue();
            }
        }
        return probability;
    }

    public double getNumericalMean() {
        double mean = 0.0d;
        for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
            mean += ((Double) sample.getValue()).doubleValue() * ((double) ((Integer) sample.getKey()).intValue());
        }
        return mean;
    }

    public double getNumericalVariance() {
        double mean = 0.0d;
        double meanOfSquares = 0.0d;
        for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
            mean += ((Double) sample.getValue()).doubleValue() * ((double) ((Integer) sample.getKey()).intValue());
            meanOfSquares += (((Double) sample.getValue()).doubleValue() * ((double) ((Integer) sample.getKey()).intValue())) * ((double) ((Integer) sample.getKey()).intValue());
        }
        return meanOfSquares - (mean * mean);
    }

    public int getSupportLowerBound() {
        int min = BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
        for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
            if (((Integer) sample.getKey()).intValue() < min && ((Double) sample.getValue()).doubleValue() > 0.0d) {
                min = ((Integer) sample.getKey()).intValue();
            }
        }
        return min;
    }

    public int getSupportUpperBound() {
        int max = RtlSpacingHelper.UNDEFINED;
        for (Pair<Integer, Double> sample : this.innerDistribution.getPmf()) {
            if (((Integer) sample.getKey()).intValue() > max && ((Double) sample.getValue()).doubleValue() > 0.0d) {
                max = ((Integer) sample.getKey()).intValue();
            }
        }
        return max;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public int sample() {
        return ((Integer) this.innerDistribution.sample()).intValue();
    }
}
