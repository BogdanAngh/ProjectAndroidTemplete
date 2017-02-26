package org.apache.commons.math4.random;

import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;

public class Well19937a extends AbstractWell {
    private static final int K = 19937;
    private static final int M1 = 70;
    private static final int M2 = 179;
    private static final int M3 = 449;
    private static final IndexTable TABLE;
    private static final long serialVersionUID = 20150223;

    static {
        TABLE = new IndexTable(K, M1, M2, M3);
    }

    public Well19937a() {
        super(K);
    }

    public Well19937a(int seed) {
        super((int) K, seed);
    }

    public Well19937a(int[] seed) {
        super((int) K, seed);
    }

    public Well19937a(long seed) {
        super((int) K, seed);
    }

    protected int next(int bits) {
        int indexRm1 = TABLE.getIndexPred(this.index);
        int indexRm2 = TABLE.getIndexPred2(this.index);
        int v0 = this.v[this.index];
        int vM1 = this.v[TABLE.getIndexM1(this.index)];
        int vM2 = this.v[TABLE.getIndexM2(this.index)];
        int vM3 = this.v[TABLE.getIndexM3(this.index)];
        int z1 = ((v0 << 25) ^ v0) ^ ((vM1 >>> 27) ^ vM1);
        int z2 = (vM2 >>> 9) ^ ((vM3 >>> 1) ^ vM3);
        int z3 = z1 ^ z2;
        int z4 = ((((z1 << 9) ^ z1) ^ ((RtlSpacingHelper.UNDEFINED & this.v[indexRm1]) ^ (BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT & this.v[indexRm2]))) ^ ((z2 << 21) ^ z2)) ^ ((z3 >>> 21) ^ z3);
        this.v[this.index] = z3;
        this.v[indexRm1] = z4;
        int[] iArr = this.v;
        iArr[indexRm2] = iArr[indexRm2] & RtlSpacingHelper.UNDEFINED;
        this.index = indexRm1;
        return z4 >>> (32 - bits);
    }
}
