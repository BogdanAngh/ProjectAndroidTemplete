package org.apache.commons.math4.random;

import java.io.Serializable;
import org.apache.commons.math4.util.FastMath;

public abstract class AbstractWell extends BitsStreamGenerator implements Serializable {
    private static final long serialVersionUID = 20150223;
    protected int index;
    protected final int[] v;

    protected static final class IndexTable {
        private final int[] i1;
        private final int[] i2;
        private final int[] i3;
        private final int[] iRm1;
        private final int[] iRm2;

        public IndexTable(int k, int m1, int m2, int m3) {
            int r = AbstractWell.calculateBlockCount(k);
            this.iRm1 = new int[r];
            this.iRm2 = new int[r];
            this.i1 = new int[r];
            this.i2 = new int[r];
            this.i3 = new int[r];
            for (int j = 0; j < r; j++) {
                this.iRm1[j] = ((j + r) - 1) % r;
                this.iRm2[j] = ((j + r) - 2) % r;
                this.i1[j] = (j + m1) % r;
                this.i2[j] = (j + m2) % r;
                this.i3[j] = (j + m3) % r;
            }
        }

        public int getIndexPred(int index) {
            return this.iRm1[index];
        }

        public int getIndexPred2(int index) {
            return this.iRm2[index];
        }

        public int getIndexM1(int index) {
            return this.i1[index];
        }

        public int getIndexM2(int index) {
            return this.i2[index];
        }

        public int getIndexM3(int index) {
            return this.i3[index];
        }
    }

    protected abstract int next(int i);

    protected AbstractWell(int k) {
        this(k, null);
    }

    protected AbstractWell(int k, int seed) {
        this(k, new int[]{seed});
    }

    protected AbstractWell(int k, int[] seed) {
        this.v = new int[calculateBlockCount(k)];
        this.index = 0;
        setSeed(seed);
    }

    protected AbstractWell(int k, long seed) {
        this(k, new int[]{(int) (seed >>> 32), (int) (4294967295L & seed)});
    }

    public void setSeed(int seed) {
        setSeed(new int[]{seed});
    }

    public void setSeed(int[] seed) {
        if (seed == null) {
            setSeed(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
            return;
        }
        System.arraycopy(seed, 0, this.v, 0, FastMath.min(seed.length, this.v.length));
        if (seed.length < this.v.length) {
            for (int i = seed.length; i < this.v.length; i++) {
                long l = (long) this.v[i - seed.length];
                this.v[i] = (int) (((1812433253 * ((l >> 30) ^ l)) + ((long) i)) & 4294967295L);
            }
        }
        this.index = 0;
        clear();
    }

    public void setSeed(long seed) {
        setSeed(new int[]{(int) (seed >>> 32), (int) (4294967295L & seed)});
    }

    private static int calculateBlockCount(int k) {
        return ((k + 32) - 1) / 32;
    }
}
