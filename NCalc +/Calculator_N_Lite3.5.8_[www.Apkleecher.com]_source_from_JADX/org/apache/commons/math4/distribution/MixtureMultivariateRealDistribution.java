package org.apache.commons.math4.distribution;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.Pair;

public class MixtureMultivariateRealDistribution<T extends MultivariateRealDistribution> extends AbstractMultivariateRealDistribution {
    private final List<T> distribution;
    private final double[] weight;

    public MixtureMultivariateRealDistribution(List<Pair<Double, T>> components) {
        this(new Well19937c(), components);
    }

    public MixtureMultivariateRealDistribution(RandomGenerator rng, List<Pair<Double, T>> components) {
        super(rng, ((MultivariateRealDistribution) ((Pair) components.get(0)).getSecond()).getDimension());
        int numComp = components.size();
        int dim = getDimension();
        double weightSum = 0.0d;
        int i = 0;
        while (i < numComp) {
            Pair<Double, T> comp = (Pair) components.get(i);
            if (((MultivariateRealDistribution) comp.getSecond()).getDimension() != dim) {
                throw new DimensionMismatchException(((MultivariateRealDistribution) comp.getSecond()).getDimension(), dim);
            } else if (((Double) comp.getFirst()).doubleValue() < 0.0d) {
                throw new NotPositiveException((Number) comp.getFirst());
            } else {
                weightSum += ((Double) comp.getFirst()).doubleValue();
                i++;
            }
        }
        if (Double.isInfinite(weightSum)) {
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
        }
        this.distribution = new ArrayList();
        this.weight = new double[numComp];
        for (i = 0; i < numComp; i++) {
            comp = (Pair) components.get(i);
            this.weight[i] = ((Double) comp.getFirst()).doubleValue() / weightSum;
            this.distribution.add((MultivariateRealDistribution) comp.getSecond());
        }
    }

    public double density(double[] values) {
        double p = 0.0d;
        for (int i = 0; i < this.weight.length; i++) {
            p += this.weight[i] * ((MultivariateRealDistribution) this.distribution.get(i)).density(values);
        }
        return p;
    }

    public double[] sample() {
        double[] vals = null;
        double randomValue = this.random.nextDouble();
        double sum = 0.0d;
        for (int i = 0; i < this.weight.length; i++) {
            sum += this.weight[i];
            if (randomValue <= sum) {
                vals = ((MultivariateRealDistribution) this.distribution.get(i)).sample();
                break;
            }
        }
        if (vals == null) {
            return ((MultivariateRealDistribution) this.distribution.get(this.weight.length - 1)).sample();
        }
        return vals;
    }

    public void reseedRandomGenerator(long seed) {
        super.reseedRandomGenerator(seed);
        for (int i = 0; i < this.distribution.size(); i++) {
            ((MultivariateRealDistribution) this.distribution.get(i)).reseedRandomGenerator(((long) (i + 1)) + seed);
        }
    }

    public List<Pair<Double, T>> getComponents() {
        List<Pair<Double, T>> list = new ArrayList(this.weight.length);
        for (int i = 0; i < this.weight.length; i++) {
            list.add(new Pair(Double.valueOf(this.weight[i]), (MultivariateRealDistribution) this.distribution.get(i)));
        }
        return list;
    }
}
