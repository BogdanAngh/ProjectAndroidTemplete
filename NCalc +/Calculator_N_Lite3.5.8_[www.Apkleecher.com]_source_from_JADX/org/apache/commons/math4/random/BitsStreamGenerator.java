package org.apache.commons.math4.random;

import android.support.v4.view.MotionEventCompat;
import com.example.duy.calculator.geom2d.util.Angle2D;
import java.io.Serializable;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.util.FastMath;

public abstract class BitsStreamGenerator implements RandomGenerator, Serializable {
    private static final long serialVersionUID = 20130104;
    private double nextGaussian;

    protected abstract int next(int i);

    public abstract void setSeed(int i);

    public abstract void setSeed(long j);

    public abstract void setSeed(int[] iArr);

    public BitsStreamGenerator() {
        this.nextGaussian = Double.NaN;
    }

    public boolean nextBoolean() {
        return next(1) != 0;
    }

    public void nextBytes(byte[] bytes) {
        int random;
        int i = 0;
        int iEnd = bytes.length - 3;
        while (i < iEnd) {
            random = next(32);
            bytes[i] = (byte) (random & MotionEventCompat.ACTION_MASK);
            bytes[i + 1] = (byte) ((random >> 8) & MotionEventCompat.ACTION_MASK);
            bytes[i + 2] = (byte) ((random >> 16) & MotionEventCompat.ACTION_MASK);
            bytes[i + 3] = (byte) ((random >> 24) & MotionEventCompat.ACTION_MASK);
            i += 4;
        }
        random = next(32);
        while (i < bytes.length) {
            int i2 = i + 1;
            bytes[i] = (byte) (random & MotionEventCompat.ACTION_MASK);
            random >>= 8;
            i = i2;
        }
    }

    public double nextDouble() {
        return ((double) (((long) next(26)) | (((long) next(26)) << 26))) * 2.220446049250313E-16d;
    }

    public float nextFloat() {
        return ((float) next(23)) * 1.1920929E-7f;
    }

    public double nextGaussian() {
        if (Double.isNaN(this.nextGaussian)) {
            double alpha = Angle2D.M_2PI * nextDouble();
            double r = FastMath.sqrt(-2.0d * FastMath.log(nextDouble()));
            double random = r * FastMath.cos(alpha);
            this.nextGaussian = FastMath.sin(alpha) * r;
            return random;
        }
        random = this.nextGaussian;
        this.nextGaussian = Double.NaN;
        return random;
    }

    public int nextInt() {
        return next(32);
    }

    public int nextInt(int n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(n));
        } else if (((-n) & n) == n) {
            return (int) ((((long) n) * ((long) next(31))) >> 31);
        } else {
            int val;
            int bits;
            do {
                bits = next(31);
                val = bits % n;
            } while ((bits - val) + (n - 1) < 0);
            return val;
        }
    }

    public long nextLong() {
        return (((long) next(32)) << 32) | (((long) next(32)) & 4294967295L);
    }

    public long nextLong(long n) throws IllegalArgumentException {
        if (n > 0) {
            long val;
            long bits;
            do {
                bits = (((long) next(31)) << 32) | (((long) next(32)) & 4294967295L);
                val = bits % n;
            } while ((bits - val) + (n - 1) < 0);
            return val;
        }
        throw new NotStrictlyPositiveException(Long.valueOf(n));
    }

    public void clear() {
        this.nextGaussian = Double.NaN;
    }
}
