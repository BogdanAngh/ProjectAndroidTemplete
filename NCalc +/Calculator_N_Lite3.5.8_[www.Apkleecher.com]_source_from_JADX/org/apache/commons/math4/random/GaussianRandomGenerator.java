package org.apache.commons.math4.random;

public class GaussianRandomGenerator implements NormalizedRandomGenerator {
    private final RandomGenerator generator;

    public GaussianRandomGenerator(RandomGenerator generator) {
        this.generator = generator;
    }

    public double nextNormalizedDouble() {
        return this.generator.nextGaussian();
    }
}
