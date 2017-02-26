package org.apache.commons.math4.random;

import org.apache.commons.math4.util.FastMath;

public class UnitSphereRandomVectorGenerator implements RandomVectorGenerator {
    private final int dimension;
    private final RandomGenerator rand;

    public UnitSphereRandomVectorGenerator(int dimension, RandomGenerator rand) {
        this.dimension = dimension;
        this.rand = rand;
    }

    public UnitSphereRandomVectorGenerator(int dimension) {
        this(dimension, new MersenneTwister());
    }

    public double[] nextVector() {
        int i;
        double[] v = new double[this.dimension];
        double normSq = 0.0d;
        for (i = 0; i < this.dimension; i++) {
            double comp = this.rand.nextGaussian();
            v[i] = comp;
            normSq += comp * comp;
        }
        double f = 1.0d / FastMath.sqrt(normSq);
        for (i = 0; i < this.dimension; i++) {
            v[i] = v[i] * f;
        }
        return v;
    }
}
