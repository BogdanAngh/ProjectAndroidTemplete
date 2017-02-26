package org.apache.commons.math4.random;

public class Well512a extends AbstractWell {
    private static final int K = 512;
    private static final int M1 = 13;
    private static final int M2 = 9;
    private static final int M3 = 5;
    private static final IndexTable TABLE;
    private static final long serialVersionUID = 20150223;

    static {
        TABLE = new IndexTable(K, M1, M2, M3);
    }

    public Well512a() {
        super(K);
    }

    public Well512a(int seed) {
        super((int) K, seed);
    }

    public Well512a(int[] seed) {
        super((int) K, seed);
    }

    public Well512a(long seed) {
        super((int) K, seed);
    }

    protected int next(int bits) {
        int indexRm1 = TABLE.getIndexPred(this.index);
        int vi = this.v[this.index];
        int vi1 = this.v[TABLE.getIndexM1(this.index)];
        int vi2 = this.v[TABLE.getIndexM2(this.index)];
        int z0 = this.v[indexRm1];
        int z1 = ((vi << 16) ^ vi) ^ ((vi1 << 15) ^ vi1);
        int z2 = vi2 ^ (vi2 >>> 11);
        int z3 = z1 ^ z2;
        int z4 = ((((z0 << 2) ^ z0) ^ ((z1 << 18) ^ z1)) ^ (z2 << 28)) ^ (((z3 << M3) & -633066204) ^ z3);
        this.v[this.index] = z3;
        this.v[indexRm1] = z4;
        this.index = indexRm1;
        return z4 >>> (32 - bits);
    }
}
