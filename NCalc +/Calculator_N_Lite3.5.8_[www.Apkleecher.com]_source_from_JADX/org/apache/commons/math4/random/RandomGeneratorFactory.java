package org.apache.commons.math4.random;

import java.util.Random;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;

public class RandomGeneratorFactory {

    class 1 implements RandomGenerator {
        private final /* synthetic */ Random val$rng;

        1(Random random) {
            this.val$rng = random;
        }

        public void setSeed(int seed) {
            this.val$rng.setSeed((long) seed);
        }

        public void setSeed(int[] seed) {
            this.val$rng.setSeed(RandomGeneratorFactory.convertToLong(seed));
        }

        public void setSeed(long seed) {
            this.val$rng.setSeed(seed);
        }

        public void nextBytes(byte[] bytes) {
            this.val$rng.nextBytes(bytes);
        }

        public int nextInt() {
            return this.val$rng.nextInt();
        }

        public int nextInt(int n) {
            if (n > 0) {
                return this.val$rng.nextInt(n);
            }
            throw new NotStrictlyPositiveException(Integer.valueOf(n));
        }

        public long nextLong() {
            return this.val$rng.nextLong();
        }

        public boolean nextBoolean() {
            return this.val$rng.nextBoolean();
        }

        public float nextFloat() {
            return this.val$rng.nextFloat();
        }

        public double nextDouble() {
            return this.val$rng.nextDouble();
        }

        public double nextGaussian() {
            return this.val$rng.nextGaussian();
        }
    }

    private RandomGeneratorFactory() {
    }

    public static RandomGenerator createRandomGenerator(Random rng) {
        return new 1(rng);
    }

    public static long convertToLong(int[] seed) {
        long combined = 0;
        for (int s : seed) {
            combined = (4294967291L * combined) + ((long) s);
        }
        return combined;
    }
}
