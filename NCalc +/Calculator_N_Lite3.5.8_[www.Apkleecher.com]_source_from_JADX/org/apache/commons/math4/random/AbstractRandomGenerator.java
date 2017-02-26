package org.apache.commons.math4.random;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.util.FastMath;

public abstract class AbstractRandomGenerator implements RandomGenerator {
    private double cachedNormalDeviate;

    public abstract double nextDouble();

    public abstract void setSeed(long j);

    public AbstractRandomGenerator() {
        this.cachedNormalDeviate = Double.NaN;
    }

    public void clear() {
        this.cachedNormalDeviate = Double.NaN;
    }

    public void setSeed(int seed) {
        setSeed((long) seed);
    }

    public void setSeed(int[] seed) {
        long combined = 0;
        for (int s : seed) {
            combined = (4294967291L * combined) + ((long) s);
        }
        setSeed(combined);
    }

    public void nextBytes(byte[] bytes) {
        int i;
        for (int bytesOut = 0; bytesOut < bytes.length; bytesOut = i) {
            int randInt = nextInt();
            int i2 = 0;
            i = bytesOut;
            while (i2 < 3) {
                if (i2 > 0) {
                    randInt >>= 8;
                }
                bytesOut = i + 1;
                bytes[i] = (byte) randInt;
                if (bytesOut != bytes.length) {
                    i2++;
                    i = bytesOut;
                } else {
                    return;
                }
            }
        }
    }

    public int nextInt() {
        return (int) (((2.0d * nextDouble()) - 1.0d) * 2.147483647E9d);
    }

    public int nextInt(int n) {
        if (n <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(n));
        }
        int result = (int) (nextDouble() * ((double) n));
        return result < n ? result : n - 1;
    }

    public long nextLong() {
        return (long) (((2.0d * nextDouble()) - 1.0d) * 9.223372036854776E18d);
    }

    public boolean nextBoolean() {
        return nextDouble() <= 0.5d;
    }

    public float nextFloat() {
        return (float) nextDouble();
    }

    public double nextGaussian() {
        if (Double.isNaN(this.cachedNormalDeviate)) {
            double v1 = 0.0d;
            double v2 = 0.0d;
            double s = 1.0d;
            while (s >= 1.0d) {
                v1 = (2.0d * nextDouble()) - 1.0d;
                v2 = (2.0d * nextDouble()) - 1.0d;
                s = (v1 * v1) + (v2 * v2);
            }
            if (s != 0.0d) {
                s = FastMath.sqrt((-2.0d * FastMath.log(s)) / s);
            }
            this.cachedNormalDeviate = v2 * s;
            return v1 * s;
        }
        double dev = this.cachedNormalDeviate;
        this.cachedNormalDeviate = Double.NaN;
        return dev;
    }
}
