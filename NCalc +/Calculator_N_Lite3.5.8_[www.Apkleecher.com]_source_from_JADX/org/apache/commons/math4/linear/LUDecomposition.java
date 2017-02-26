package org.apache.commons.math4.linear;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.util.FastMath;

public class LUDecomposition {
    private static final double DEFAULT_TOO_SMALL = 1.0E-11d;
    private RealMatrix cachedL;
    private RealMatrix cachedP;
    private RealMatrix cachedU;
    private boolean even;
    private final double[][] lu;
    private final int[] pivot;
    private boolean singular;

    private static class Solver implements DecompositionSolver {
        private final double[][] lu;
        private final int[] pivot;
        private final boolean singular;

        private Solver(double[][] lu, int[] pivot, boolean singular) {
            this.lu = lu;
            this.pivot = pivot;
            this.singular = singular;
        }

        public boolean isNonSingular() {
            return !this.singular;
        }

        public RealVector solve(RealVector b) {
            int m = this.pivot.length;
            if (b.getDimension() != m) {
                throw new DimensionMismatchException(b.getDimension(), m);
            } else if (this.singular) {
                throw new SingularMatrixException();
            } else {
                int col;
                double bpCol;
                int i;
                double[] bp = new double[m];
                for (int row = 0; row < m; row++) {
                    bp[row] = b.getEntry(this.pivot[row]);
                }
                for (col = 0; col < m; col++) {
                    bpCol = bp[col];
                    for (i = col + 1; i < m; i++) {
                        bp[i] = bp[i] - (this.lu[i][col] * bpCol);
                    }
                }
                for (col = m - 1; col >= 0; col--) {
                    bp[col] = bp[col] / this.lu[col][col];
                    bpCol = bp[col];
                    for (i = 0; i < col; i++) {
                        bp[i] = bp[i] - (this.lu[i][col] * bpCol);
                    }
                }
                return new ArrayRealVector(bp, false);
            }
        }

        public RealMatrix solve(RealMatrix b) {
            int m = this.pivot.length;
            if (b.getRowDimension() != m) {
                throw new DimensionMismatchException(b.getRowDimension(), m);
            } else if (this.singular) {
                throw new SingularMatrixException();
            } else {
                int col;
                double[] bpCol;
                int i;
                double[] bpI;
                double luICol;
                int j;
                int nColB = b.getColumnDimension();
                int[] iArr = new int[]{m, nColB};
                double[][] bp = (double[][]) Array.newInstance(Double.TYPE, iArr);
                for (int row = 0; row < m; row++) {
                    double[] bpRow = bp[row];
                    int pRow = this.pivot[row];
                    for (col = 0; col < nColB; col++) {
                        bpRow[col] = b.getEntry(pRow, col);
                    }
                }
                for (col = 0; col < m; col++) {
                    bpCol = bp[col];
                    for (i = col + 1; i < m; i++) {
                        bpI = bp[i];
                        luICol = this.lu[i][col];
                        for (j = 0; j < nColB; j++) {
                            bpI[j] = bpI[j] - (bpCol[j] * luICol);
                        }
                    }
                }
                for (col = m - 1; col >= 0; col--) {
                    bpCol = bp[col];
                    double luDiag = this.lu[col][col];
                    for (j = 0; j < nColB; j++) {
                        bpCol[j] = bpCol[j] / luDiag;
                    }
                    for (i = 0; i < col; i++) {
                        bpI = bp[i];
                        luICol = this.lu[i][col];
                        for (j = 0; j < nColB; j++) {
                            bpI[j] = bpI[j] - (bpCol[j] * luICol);
                        }
                    }
                }
                return new Array2DRowRealMatrix(bp, false);
            }
        }

        public RealMatrix getInverse() {
            return solve(MatrixUtils.createRealIdentityMatrix(this.pivot.length));
        }
    }

    public LUDecomposition(RealMatrix matrix) {
        this(matrix, DEFAULT_TOO_SMALL);
    }

    public LUDecomposition(RealMatrix matrix, double singularityThreshold) {
        if (matrix.isSquare()) {
            int row;
            int m = matrix.getColumnDimension();
            this.lu = matrix.getData();
            this.pivot = new int[m];
            this.cachedL = null;
            this.cachedU = null;
            this.cachedP = null;
            for (row = 0; row < m; row++) {
                this.pivot[row] = row;
            }
            this.even = true;
            this.singular = false;
            for (int col = 0; col < m; col++) {
                double[] luRow;
                double sum;
                int i;
                for (row = 0; row < col; row++) {
                    luRow = this.lu[row];
                    sum = luRow[col];
                    for (i = 0; i < row; i++) {
                        sum -= luRow[i] * this.lu[i][col];
                    }
                    luRow[col] = sum;
                }
                int max = col;
                double largest = Double.NEGATIVE_INFINITY;
                for (row = col; row < m; row++) {
                    luRow = this.lu[row];
                    sum = luRow[col];
                    for (i = 0; i < col; i++) {
                        sum -= luRow[i] * this.lu[i][col];
                    }
                    luRow[col] = sum;
                    if (FastMath.abs(sum) > largest) {
                        largest = FastMath.abs(sum);
                        max = row;
                    }
                }
                if (FastMath.abs(this.lu[max][col]) < singularityThreshold) {
                    this.singular = true;
                    return;
                }
                if (max != col) {
                    double[] luMax = this.lu[max];
                    double[] luCol = this.lu[col];
                    for (i = 0; i < m; i++) {
                        double tmp = luMax[i];
                        luMax[i] = luCol[i];
                        luCol[i] = tmp;
                    }
                    int temp = this.pivot[max];
                    this.pivot[max] = this.pivot[col];
                    this.pivot[col] = temp;
                    this.even = !this.even;
                }
                double luDiag = this.lu[col][col];
                for (row = col + 1; row < m; row++) {
                    double[] dArr = this.lu[row];
                    dArr[col] = dArr[col] / luDiag;
                }
            }
            return;
        }
        throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
    }

    public RealMatrix getL() {
        if (this.cachedL == null && !this.singular) {
            int m = this.pivot.length;
            this.cachedL = MatrixUtils.createRealMatrix(m, m);
            for (int i = 0; i < m; i++) {
                double[] luI = this.lu[i];
                for (int j = 0; j < i; j++) {
                    this.cachedL.setEntry(i, j, luI[j]);
                }
                this.cachedL.setEntry(i, i, 1.0d);
            }
        }
        return this.cachedL;
    }

    public RealMatrix getU() {
        if (this.cachedU == null && !this.singular) {
            int m = this.pivot.length;
            this.cachedU = MatrixUtils.createRealMatrix(m, m);
            for (int i = 0; i < m; i++) {
                double[] luI = this.lu[i];
                for (int j = i; j < m; j++) {
                    this.cachedU.setEntry(i, j, luI[j]);
                }
            }
        }
        return this.cachedU;
    }

    public RealMatrix getP() {
        if (this.cachedP == null && !this.singular) {
            int m = this.pivot.length;
            this.cachedP = MatrixUtils.createRealMatrix(m, m);
            for (int i = 0; i < m; i++) {
                this.cachedP.setEntry(i, this.pivot[i], 1.0d);
            }
        }
        return this.cachedP;
    }

    public int[] getPivot() {
        return (int[]) this.pivot.clone();
    }

    public double getDeterminant() {
        if (this.singular) {
            return 0.0d;
        }
        double determinant = (double) (this.even ? 1 : -1);
        for (int i = 0; i < this.pivot.length; i++) {
            determinant *= this.lu[i][i];
        }
        return determinant;
    }

    public DecompositionSolver getSolver() {
        return new Solver(this.pivot, this.singular, null);
    }
}
