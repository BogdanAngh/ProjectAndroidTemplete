package org.apache.commons.math4.random;

import java.io.Serializable;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.util.FastMath;

public class MersenneTwister extends BitsStreamGenerator implements Serializable {
    private static final int M = 397;
    private static final int[] MAG01;
    private static final int N = 624;
    private static final long serialVersionUID = 8661194735290153518L;
    private int[] mt;
    private int mti;

    static {
        int[] iArr = new int[2];
        iArr[1] = -1727483681;
        MAG01 = iArr;
    }

    public MersenneTwister() {
        this.mt = new int[N];
        setSeed(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
    }

    public MersenneTwister(int seed) {
        this.mt = new int[N];
        setSeed(seed);
    }

    public MersenneTwister(int[] seed) {
        this.mt = new int[N];
        setSeed(seed);
    }

    public MersenneTwister(long seed) {
        this.mt = new int[N];
        setSeed(seed);
    }

    public void setSeed(int seed) {
        long longMT = (long) seed;
        this.mt[0] = (int) longMT;
        this.mti = 1;
        while (this.mti < N) {
            longMT = ((1812433253 * ((longMT >> 30) ^ longMT)) + ((long) this.mti)) & 4294967295L;
            this.mt[this.mti] = (int) longMT;
            this.mti++;
        }
        clear();
    }

    public void setSeed(int[] seed) {
        if (seed == null) {
            setSeed(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
            return;
        }
        int k;
        setSeed(19650218);
        int i = 1;
        int j = 0;
        for (k = FastMath.max((int) N, seed.length); k != 0; k--) {
            long l1 = (2147483647L & ((long) this.mt[i - 1])) | (this.mt[i + -1] < 0 ? 2147483648L : 0);
            this.mt[i] = (int) (4294967295L & ((((((l1 >> 30) ^ l1) * 1664525) ^ ((2147483647L & ((long) this.mt[i])) | (this.mt[i] < 0 ? 2147483648L : 0))) + ((long) seed[j])) + ((long) j)));
            i++;
            j++;
            if (i >= N) {
                this.mt[0] = this.mt[623];
                i = 1;
            }
            if (j >= seed.length) {
                j = 0;
            }
        }
        for (k = 623; k != 0; k--) {
            l1 = (2147483647L & ((long) this.mt[i - 1])) | (this.mt[i + -1] < 0 ? 2147483648L : 0);
            this.mt[i] = (int) (4294967295L & (((((l1 >> 30) ^ l1) * 1566083941) ^ ((2147483647L & ((long) this.mt[i])) | (this.mt[i] < 0 ? 2147483648L : 0))) - ((long) i)));
            i++;
            if (i >= N) {
                this.mt[0] = this.mt[623];
                i = 1;
            }
        }
        this.mt[0] = RtlSpacingHelper.UNDEFINED;
        clear();
    }

    public void setSeed(long seed) {
        setSeed(new int[]{(int) (seed >>> 32), (int) (4294967295L & seed)});
    }

    protected int next(int bits) {
        int y;
        if (this.mti >= N) {
            int k;
            int mtCurr;
            int mtNext = this.mt[0];
            for (k = 0; k < 227; k++) {
                mtCurr = mtNext;
                mtNext = this.mt[k + 1];
                y = (mtCurr & RtlSpacingHelper.UNDEFINED) | (mtNext & BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
                this.mt[k] = (this.mt[k + M] ^ (y >>> 1)) ^ MAG01[y & 1];
            }
            for (k = 227; k < 623; k++) {
                mtCurr = mtNext;
                mtNext = this.mt[k + 1];
                y = (mtCurr & RtlSpacingHelper.UNDEFINED) | (mtNext & BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
                this.mt[k] = (this.mt[k - 227] ^ (y >>> 1)) ^ MAG01[y & 1];
            }
            y = (mtNext & RtlSpacingHelper.UNDEFINED) | (this.mt[0] & BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
            this.mt[623] = (this.mt[396] ^ (y >>> 1)) ^ MAG01[y & 1];
            this.mti = 0;
        }
        int[] iArr = this.mt;
        int i = this.mti;
        this.mti = i + 1;
        y = iArr[i];
        y ^= y >>> 11;
        y ^= (y << 7) & -1658038656;
        y ^= (y << 15) & -272236544;
        return (y ^ (y >>> 18)) >>> (32 - bits);
    }
}
