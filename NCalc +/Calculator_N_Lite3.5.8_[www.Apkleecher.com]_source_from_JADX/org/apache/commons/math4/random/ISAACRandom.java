package org.apache.commons.math4.random;

import android.support.v4.view.MotionEventCompat;
import java.io.Serializable;
import org.apache.commons.math4.util.FastMath;

public class ISAACRandom extends BitsStreamGenerator implements Serializable {
    private static final int GLD_RATIO = -1640531527;
    private static final int H_SIZE = 128;
    private static final int MASK = 1020;
    private static final int SIZE = 256;
    private static final int SIZE_L = 8;
    private static final long serialVersionUID = 7288197941165002400L;
    private final int[] arr;
    private int count;
    private int isaacA;
    private int isaacB;
    private int isaacC;
    private int isaacI;
    private int isaacJ;
    private int isaacX;
    private final int[] mem;
    private final int[] rsl;

    public ISAACRandom() {
        this.rsl = new int[SIZE];
        this.mem = new int[SIZE];
        this.arr = new int[SIZE_L];
        setSeed(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
    }

    public ISAACRandom(long seed) {
        this.rsl = new int[SIZE];
        this.mem = new int[SIZE];
        this.arr = new int[SIZE_L];
        setSeed(seed);
    }

    public ISAACRandom(int[] seed) {
        this.rsl = new int[SIZE];
        this.mem = new int[SIZE];
        this.arr = new int[SIZE_L];
        setSeed(seed);
    }

    public void setSeed(int seed) {
        setSeed(new int[]{seed});
    }

    public void setSeed(long seed) {
        setSeed(new int[]{(int) (seed >>> 32), (int) (4294967295L & seed)});
    }

    public void setSeed(int[] seed) {
        if (seed == null) {
            setSeed(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
            return;
        }
        int seedLen = seed.length;
        int rslLen = this.rsl.length;
        System.arraycopy(seed, 0, this.rsl, 0, FastMath.min(seedLen, rslLen));
        if (seedLen < rslLen) {
            for (int j = seedLen; j < rslLen; j++) {
                long k = (long) this.rsl[j - seedLen];
                this.rsl[j] = (int) (((1812433253 * ((k >> 30) ^ k)) + ((long) j)) & 4294967295L);
            }
        }
        initState();
    }

    protected int next(int bits) {
        if (this.count < 0) {
            isaac();
            this.count = MotionEventCompat.ACTION_MASK;
        }
        int[] iArr = this.rsl;
        int i = this.count;
        this.count = i - 1;
        return iArr[i] >>> (32 - bits);
    }

    private void isaac() {
        this.isaacI = 0;
        this.isaacJ = H_SIZE;
        int i = this.isaacB;
        int i2 = this.isaacC + 1;
        this.isaacC = i2;
        this.isaacB = i + i2;
        while (this.isaacI < H_SIZE) {
            isaac2();
        }
        this.isaacJ = 0;
        while (this.isaacJ < H_SIZE) {
            isaac2();
        }
    }

    private void isaac2() {
        this.isaacX = this.mem[this.isaacI];
        this.isaacA ^= this.isaacA << 13;
        int i = this.isaacA;
        int[] iArr = this.mem;
        int i2 = this.isaacJ;
        this.isaacJ = i2 + 1;
        this.isaacA = i + iArr[i2];
        isaac3();
        this.isaacX = this.mem[this.isaacI];
        this.isaacA ^= this.isaacA >>> 6;
        i = this.isaacA;
        iArr = this.mem;
        i2 = this.isaacJ;
        this.isaacJ = i2 + 1;
        this.isaacA = i + iArr[i2];
        isaac3();
        this.isaacX = this.mem[this.isaacI];
        this.isaacA ^= this.isaacA << 2;
        i = this.isaacA;
        iArr = this.mem;
        i2 = this.isaacJ;
        this.isaacJ = i2 + 1;
        this.isaacA = i + iArr[i2];
        isaac3();
        this.isaacX = this.mem[this.isaacI];
        this.isaacA ^= this.isaacA >>> 16;
        i = this.isaacA;
        iArr = this.mem;
        i2 = this.isaacJ;
        this.isaacJ = i2 + 1;
        this.isaacA = i + iArr[i2];
        isaac3();
    }

    private void isaac3() {
        this.mem[this.isaacI] = (this.mem[(this.isaacX & MASK) >> 2] + this.isaacA) + this.isaacB;
        this.isaacB = this.mem[((this.mem[this.isaacI] >> SIZE_L) & MASK) >> 2] + this.isaacX;
        int[] iArr = this.rsl;
        int i = this.isaacI;
        this.isaacI = i + 1;
        iArr[i] = this.isaacB;
    }

    private void initState() {
        int j;
        this.isaacA = 0;
        this.isaacB = 0;
        this.isaacC = 0;
        for (j = 0; j < this.arr.length; j++) {
            this.arr[j] = GLD_RATIO;
        }
        for (j = 0; j < 4; j++) {
            shuffle();
        }
        for (j = 0; j < SIZE; j += SIZE_L) {
            int[] iArr = this.arr;
            iArr[0] = iArr[0] + this.rsl[j];
            iArr = this.arr;
            iArr[1] = iArr[1] + this.rsl[j + 1];
            iArr = this.arr;
            iArr[2] = iArr[2] + this.rsl[j + 2];
            iArr = this.arr;
            iArr[3] = iArr[3] + this.rsl[j + 3];
            iArr = this.arr;
            iArr[4] = iArr[4] + this.rsl[j + 4];
            iArr = this.arr;
            iArr[5] = iArr[5] + this.rsl[j + 5];
            iArr = this.arr;
            iArr[6] = iArr[6] + this.rsl[j + 6];
            iArr = this.arr;
            iArr[7] = iArr[7] + this.rsl[j + 7];
            shuffle();
            setState(j);
        }
        for (j = 0; j < SIZE; j += SIZE_L) {
            iArr = this.arr;
            iArr[0] = iArr[0] + this.mem[j];
            iArr = this.arr;
            iArr[1] = iArr[1] + this.mem[j + 1];
            iArr = this.arr;
            iArr[2] = iArr[2] + this.mem[j + 2];
            iArr = this.arr;
            iArr[3] = iArr[3] + this.mem[j + 3];
            iArr = this.arr;
            iArr[4] = iArr[4] + this.mem[j + 4];
            iArr = this.arr;
            iArr[5] = iArr[5] + this.mem[j + 5];
            iArr = this.arr;
            iArr[6] = iArr[6] + this.mem[j + 6];
            iArr = this.arr;
            iArr[7] = iArr[7] + this.mem[j + 7];
            shuffle();
            setState(j);
        }
        isaac();
        this.count = MotionEventCompat.ACTION_MASK;
        clear();
    }

    private void shuffle() {
        int[] iArr = this.arr;
        iArr[0] = iArr[0] ^ (this.arr[1] << 11);
        iArr = this.arr;
        iArr[3] = iArr[3] + this.arr[0];
        iArr = this.arr;
        iArr[1] = iArr[1] + this.arr[2];
        iArr = this.arr;
        iArr[1] = iArr[1] ^ (this.arr[2] >>> 2);
        iArr = this.arr;
        iArr[4] = iArr[4] + this.arr[1];
        iArr = this.arr;
        iArr[2] = iArr[2] + this.arr[3];
        iArr = this.arr;
        iArr[2] = iArr[2] ^ (this.arr[3] << SIZE_L);
        iArr = this.arr;
        iArr[5] = iArr[5] + this.arr[2];
        iArr = this.arr;
        iArr[3] = iArr[3] + this.arr[4];
        iArr = this.arr;
        iArr[3] = iArr[3] ^ (this.arr[4] >>> 16);
        iArr = this.arr;
        iArr[6] = iArr[6] + this.arr[3];
        iArr = this.arr;
        iArr[4] = iArr[4] + this.arr[5];
        iArr = this.arr;
        iArr[4] = iArr[4] ^ (this.arr[5] << 10);
        iArr = this.arr;
        iArr[7] = iArr[7] + this.arr[4];
        iArr = this.arr;
        iArr[5] = iArr[5] + this.arr[6];
        iArr = this.arr;
        iArr[5] = iArr[5] ^ (this.arr[6] >>> 4);
        iArr = this.arr;
        iArr[0] = iArr[0] + this.arr[5];
        iArr = this.arr;
        iArr[6] = iArr[6] + this.arr[7];
        iArr = this.arr;
        iArr[6] = iArr[6] ^ (this.arr[7] << SIZE_L);
        iArr = this.arr;
        iArr[1] = iArr[1] + this.arr[6];
        iArr = this.arr;
        iArr[7] = iArr[7] + this.arr[0];
        iArr = this.arr;
        iArr[7] = iArr[7] ^ (this.arr[0] >>> 9);
        iArr = this.arr;
        iArr[2] = iArr[2] + this.arr[7];
        iArr = this.arr;
        iArr[0] = iArr[0] + this.arr[1];
    }

    private void setState(int start) {
        this.mem[start] = this.arr[0];
        this.mem[start + 1] = this.arr[1];
        this.mem[start + 2] = this.arr[2];
        this.mem[start + 3] = this.arr[3];
        this.mem[start + 4] = this.arr[4];
        this.mem[start + 5] = this.arr[5];
        this.mem[start + 6] = this.arr[6];
        this.mem[start + 7] = this.arr[7];
    }
}
