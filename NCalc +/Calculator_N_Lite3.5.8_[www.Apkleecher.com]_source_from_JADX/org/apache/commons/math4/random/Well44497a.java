package org.apache.commons.math4.random;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

public class Well44497a extends AbstractWell {
    private static final int K = 44497;
    private static final int M1 = 23;
    private static final int M2 = 481;
    private static final int M3 = 229;
    private static final IndexTable TABLE;
    private static final long serialVersionUID = 20150223;

    static {
        TABLE = new IndexTable(K, M1, M2, M3);
    }

    public Well44497a() {
        super(K);
    }

    public Well44497a(int seed) {
        super((int) K, seed);
    }

    public Well44497a(int[] seed) {
        super((int) K, seed);
    }

    public Well44497a(long seed) {
        super((int) K, seed);
    }

    protected int next(int bits) {
        int z2Second;
        int indexRm1 = TABLE.getIndexPred(this.index);
        int indexRm2 = TABLE.getIndexPred2(this.index);
        int v0 = this.v[this.index];
        int vM1 = this.v[TABLE.getIndexM1(this.index)];
        int vM2 = this.v[TABLE.getIndexM2(this.index)];
        int z0 = (this.v[indexRm1] & -32768) ^ (this.v[indexRm2] & 32767);
        int z1 = ((v0 << 24) ^ v0) ^ ((vM1 >>> 30) ^ vM1);
        int z2 = ((vM2 << 10) ^ vM2) ^ (this.v[TABLE.getIndexM3(this.index)] << 26);
        int z3 = z1 ^ z2;
        int z2Prime = ((z2 << 9) ^ (z2 >>> M1)) & -67108865;
        if ((AccessibilityNodeInfoCompat.ACTION_SET_SELECTION & z2) != 0) {
            z2Second = z2Prime ^ -1221985044;
        } else {
            z2Second = z2Prime;
        }
        int z4 = ((((z1 >>> 20) ^ z1) ^ z0) ^ z2Second) ^ z3;
        this.v[this.index] = z3;
        this.v[indexRm1] = z4;
        int[] iArr = this.v;
        iArr[indexRm2] = iArr[indexRm2] & -32768;
        this.index = indexRm1;
        return z4 >>> (32 - bits);
    }
}
