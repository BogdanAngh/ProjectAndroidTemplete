package org.apache.commons.math4.linear;

public class RRQRDecomposition extends QRDecomposition {
    private RealMatrix cachedP;
    private int[] p;

    private static class Solver implements DecompositionSolver {
        private RealMatrix p;
        private final DecompositionSolver upper;

        private Solver(DecompositionSolver upper, RealMatrix p) {
            this.upper = upper;
            this.p = p;
        }

        public boolean isNonSingular() {
            return this.upper.isNonSingular();
        }

        public RealVector solve(RealVector b) {
            return this.p.operate(this.upper.solve(b));
        }

        public RealMatrix solve(RealMatrix b) {
            return this.p.multiply(this.upper.solve(b));
        }

        public RealMatrix getInverse() {
            return solve(MatrixUtils.createRealIdentityMatrix(this.p.getRowDimension()));
        }
    }

    public RRQRDecomposition(RealMatrix matrix) {
        this(matrix, 0.0d);
    }

    public RRQRDecomposition(RealMatrix matrix, double threshold) {
        super(matrix, threshold);
    }

    protected void decompose(double[][] qrt) {
        this.p = new int[qrt.length];
        for (int i = 0; i < this.p.length; i++) {
            this.p[i] = i;
        }
        super.decompose(qrt);
    }

    protected void performHouseholderReflection(int minor, double[][] qrt) {
        double l2NormSquaredMax = 0.0d;
        int l2NormSquaredMaxIndex = minor;
        for (int i = minor; i < qrt.length; i++) {
            double l2NormSquared = 0.0d;
            for (int j = 0; j < qrt[i].length; j++) {
                l2NormSquared += qrt[i][j] * qrt[i][j];
            }
            if (l2NormSquared > l2NormSquaredMax) {
                l2NormSquaredMax = l2NormSquared;
                l2NormSquaredMaxIndex = i;
            }
        }
        if (l2NormSquaredMaxIndex != minor) {
            double[] tmp1 = qrt[minor];
            qrt[minor] = qrt[l2NormSquaredMaxIndex];
            qrt[l2NormSquaredMaxIndex] = tmp1;
            int tmp2 = this.p[minor];
            this.p[minor] = this.p[l2NormSquaredMaxIndex];
            this.p[l2NormSquaredMaxIndex] = tmp2;
        }
        super.performHouseholderReflection(minor, qrt);
    }

    public RealMatrix getP() {
        if (this.cachedP == null) {
            int n = this.p.length;
            this.cachedP = MatrixUtils.createRealMatrix(n, n);
            for (int i = 0; i < n; i++) {
                this.cachedP.setEntry(this.p[i], i, 1.0d);
            }
        }
        return this.cachedP;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getRank(double r14) {
        /*
        r13 = this;
        r1 = r13.getR();
        r7 = r1.getRowDimension();
        r0 = r1.getColumnDimension();
        r6 = 1;
        r2 = r1.getFrobeniusNorm();
        r4 = r2;
    L_0x0012:
        r10 = org.apache.commons.math4.util.FastMath.min(r7, r0);
        if (r6 < r10) goto L_0x0019;
    L_0x0018:
        return r6;
    L_0x0019:
        r10 = r7 + -1;
        r11 = r0 + -1;
        r10 = r1.getSubMatrix(r6, r10, r6, r11);
        r8 = r10.getFrobeniusNorm();
        r10 = 0;
        r10 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r10 == 0) goto L_0x0018;
    L_0x002b:
        r10 = r8 / r2;
        r10 = r10 * r4;
        r10 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1));
        if (r10 < 0) goto L_0x0018;
    L_0x0032:
        r2 = r8;
        r6 = r6 + 1;
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.linear.RRQRDecomposition.getRank(double):int");
    }

    public DecompositionSolver getSolver() {
        return new Solver(getP(), null);
    }
}
