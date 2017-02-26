package org.apache.commons.math4.random;

public class Well1024a extends AbstractWell {
    private static final int K = 1024;
    private static final int M1 = 3;
    private static final int M2 = 24;
    private static final int M3 = 10;
    private static final IndexTable TABLE;
    private static final long serialVersionUID = 20150223;

    static {
        TABLE = new IndexTable(K, M1, M2, M3);
    }

    public Well1024a() {
        super(K);
    }

    public Well1024a(int seed) {
        super((int) K, seed);
    }

    public Well1024a(int[] seed) {
        super((int) K, seed);
    }

    public Well1024a(long seed) {
        super((int) K, seed);
    }

    protected int next(int bits) {
        int indexRm1 = TABLE.getIndexPred(this.index);
        int v0 = this.v[this.index];
        int vM1 = this.v[TABLE.getIndexM1(this.index)];
        int vM2 = this.v[TABLE.getIndexM2(this.index)];
        int vM3 = this.v[TABLE.getIndexM3(this.index)];
        int z0 = this.v[indexRm1];
        int z1 = v0 ^ ((vM1 >>> 8) ^ vM1);
        int z2 = ((vM2 << 19) ^ vM2) ^ ((vM3 << 14) ^ vM3);
        int z4 = (((z0 << 11) ^ z0) ^ ((z1 << 7) ^ z1)) ^ ((z2 << 13) ^ z2);
        this.v[this.index] = z1 ^ z2;
        this.v[indexRm1] = z4;
        this.index = indexRm1;
        return z4 >>> (32 - bits);
    }
}
