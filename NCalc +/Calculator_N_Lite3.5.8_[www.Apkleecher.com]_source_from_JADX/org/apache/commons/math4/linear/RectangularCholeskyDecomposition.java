package org.apache.commons.math4.linear;

import java.lang.reflect.Array;
import org.apache.commons.math4.util.FastMath;

public class RectangularCholeskyDecomposition {
    private int rank;
    private final RealMatrix root;

    public RectangularCholeskyDecomposition(RealMatrix matrix) throws NonPositiveDefiniteMatrixException {
        this(matrix, 0.0d);
    }

    public RectangularCholeskyDecomposition(RealMatrix matrix, double small) throws NonPositiveDefiniteMatrixException {
        int i;
        int j;
        int order = matrix.getRowDimension();
        double[][] c = matrix.getData();
        double[][] b = (double[][]) Array.newInstance(Double.TYPE, new int[]{order, order});
        int[] index = new int[order];
        for (i = 0; i < order; i++) {
            index[i] = i;
        }
        int r = 0;
        boolean loop = true;
        while (loop) {
            int swapR = r;
            for (i = r + 1; i < order; i++) {
                int ii = index[i];
                int isr = index[swapR];
                if (c[ii][ii] > c[isr][isr]) {
                    swapR = i;
                }
            }
            if (swapR != r) {
                int tmpIndex = index[r];
                index[r] = index[swapR];
                index[swapR] = tmpIndex;
                double[] tmpRow = b[r];
                b[r] = b[swapR];
                b[swapR] = tmpRow;
            }
            int ir = index[r];
            if (c[ir][ir] > small) {
                double sqrt = FastMath.sqrt(c[ir][ir]);
                b[r][r] = sqrt;
                double inverse = 1.0d / sqrt;
                double inverse2 = 1.0d / c[ir][ir];
                for (i = r + 1; i < order; i++) {
                    ii = index[i];
                    double e = inverse * c[ii][ir];
                    b[i][r] = e;
                    double[] dArr = c[ii];
                    dArr[ii] = dArr[ii] - ((c[ii][ir] * c[ii][ir]) * inverse2);
                    for (j = r + 1; j < i; j++) {
                        int ij = index[j];
                        double f = c[ii][ij] - (b[j][r] * e);
                        c[ii][ij] = f;
                        c[ij][ii] = f;
                    }
                }
                r++;
                if (r < order) {
                    loop = true;
                } else {
                    loop = false;
                }
            } else if (r == 0) {
                throw new NonPositiveDefiniteMatrixException(c[ir][ir], ir, small);
            } else {
                for (i = r; i < order; i++) {
                    if (c[index[i]][index[i]] < (-small)) {
                        throw new NonPositiveDefiniteMatrixException(c[index[i]][index[i]], i, small);
                    }
                }
                loop = false;
            }
        }
        this.rank = r;
        this.root = MatrixUtils.createRealMatrix(order, r);
        for (i = 0; i < order; i++) {
            for (j = 0; j < r; j++) {
                this.root.setEntry(index[i], j, b[i][j]);
            }
        }
    }

    public RealMatrix getRootMatrix() {
        return this.root;
    }

    public int getRank() {
        return this.rank;
    }
}
