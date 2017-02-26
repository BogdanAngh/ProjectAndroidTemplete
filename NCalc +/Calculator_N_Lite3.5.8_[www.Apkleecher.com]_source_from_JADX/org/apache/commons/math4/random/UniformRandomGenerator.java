package org.apache.commons.math4.random;

import org.apache.commons.math4.util.FastMath;

public class UniformRandomGenerator implements NormalizedRandomGenerator {
    private static final double SQRT3;
    private final RandomGenerator generator;

    static {
        SQRT3 = FastMath.sqrt(3.0d);
    }

    public UniformRandomGenerator(RandomGenerator generator) {
        this.generator = generator;
    }

    public double nextNormalizedDouble() {
        return SQRT3 * ((2.0d * this.generator.nextDouble()) - 1.0d);
    }
}
