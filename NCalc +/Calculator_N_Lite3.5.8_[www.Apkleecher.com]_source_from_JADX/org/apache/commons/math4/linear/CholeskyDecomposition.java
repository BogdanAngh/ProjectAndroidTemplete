package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.util.FastMath;

public class CholeskyDecomposition {
    public static final double DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD = 1.0E-10d;
    public static final double DEFAULT_RELATIVE_SYMMETRY_THRESHOLD = 1.0E-15d;
    private RealMatrix cachedL;
    private RealMatrix cachedLT;
    private final double[][] lTData;

    private static class Solver implements DecompositionSolver {
        private final double[][] lTData;

        private Solver(double[][] lTData) {
            this.lTData = lTData;
        }

        public boolean isNonSingular() {
            return true;
        }

        public RealVector solve(RealVector b) {
            int m = this.lTData.length;
            if (b.getDimension() != m) {
                throw new DimensionMismatchException(b.getDimension(), m);
            }
            int j;
            double[] x = b.toArray();
            for (j = 0; j < m; j++) {
                int i;
                double[] lJ = this.lTData[j];
                x[j] = x[j] / lJ[j];
                double xJ = x[j];
                for (i = j + 1; i < m; i++) {
                    x[i] = x[i] - (lJ[i] * xJ);
                }
            }
            for (j = m - 1; j >= 0; j--) {
                x[j] = x[j] / this.lTData[j][j];
                xJ = x[j];
                for (i = 0; i < j; i++) {
                    x[i] = x[i] - (this.lTData[i][j] * xJ);
                }
            }
            return new ArrayRealVector(x, false);
        }

        public RealMatrix solve(RealMatrix b) {
            int m = this.lTData.length;
            if (b.getRowDimension() != m) {
                throw new DimensionMismatchException(b.getRowDimension(), m);
            }
            int j;
            int k;
            int i;
            int nColB = b.getColumnDimension();
            double[][] x = b.getData();
            for (j = 0; j < m; j++) {
                double[] lJ = this.lTData[j];
                double lJJ = lJ[j];
                double[] xJ = x[j];
                for (k = 0; k < nColB; k++) {
                    xJ[k] = xJ[k] / lJJ;
                }
                for (i = j + 1; i < m; i++) {
                    double[] xI = x[i];
                    double lJI = lJ[i];
                    for (k = 0; k < nColB; k++) {
                        xI[k] = xI[k] - (xJ[k] * lJI);
                    }
                }
            }
            for (j = m - 1; j >= 0; j--) {
                lJJ = this.lTData[j][j];
                xJ = x[j];
                for (k = 0; k < nColB; k++) {
                    xJ[k] = xJ[k] / lJJ;
                }
                for (i = 0; i < j; i++) {
                    xI = x[i];
                    double lIJ = this.lTData[i][j];
                    for (k = 0; k < nColB; k++) {
                        xI[k] = xI[k] - (xJ[k] * lIJ);
                    }
                }
            }
            return new Array2DRowRealMatrix(x);
        }

        public RealMatrix getInverse() {
            return solve(MatrixUtils.createRealIdentityMatrix(this.lTData.length));
        }
    }

    public CholeskyDecomposition(RealMatrix matrix) {
        this(matrix, DEFAULT_RELATIVE_SYMMETRY_THRESHOLD, DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD);
    }

    public CholeskyDecomposition(RealMatrix matrix, double relativeSymmetryThreshold, double absolutePositivityThreshold) {
        if (matrix.isSquare()) {
            int i;
            int order = matrix.getRowDimension();
            this.lTData = matrix.getData();
            this.cachedL = null;
            this.cachedLT = null;
            for (i = 0; i < order; i++) {
                double[] lI = this.lTData[i];
                for (int j = i + 1; j < order; j++) {
                    double[] lJ = this.lTData[j];
                    double lIJ = lI[j];
                    double lJI = lJ[i];
                    if (FastMath.abs(lIJ - lJI) > relativeSymmetryThreshold * FastMath.max(FastMath.abs(lIJ), FastMath.abs(lJI))) {
                        throw new NonSymmetricMatrixException(i, j, relativeSymmetryThreshold);
                    }
                    lJ[i] = 0.0d;
                }
            }
            for (i = 0; i < order; i++) {
                double[] ltI = this.lTData[i];
                if (ltI[i] <= absolutePositivityThreshold) {
                    throw new NonPositiveDefiniteMatrixException(ltI[i], i, absolutePositivityThreshold);
                }
                ltI[i] = FastMath.sqrt(ltI[i]);
                double inverse = 1.0d / ltI[i];
                for (int q = order - 1; q > i; q--) {
                    ltI[q] = ltI[q] * inverse;
                    double[] ltQ = this.lTData[q];
                    for (int p = q; p < order; p++) {
                        ltQ[p] = ltQ[p] - (ltI[q] * ltI[p]);
                    }
                }
            }
            return;
        }
        throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
    }

    public RealMatrix getL() {
        if (this.cachedL == null) {
            this.cachedL = getLT().transpose();
        }
        return this.cachedL;
    }

    public RealMatrix getLT() {
        if (this.cachedLT == null) {
            this.cachedLT = MatrixUtils.createRealMatrix(this.lTData);
        }
        return this.cachedLT;
    }

    public double getDeterminant() {
        double determinant = 1.0d;
        for (int i = 0; i < this.lTData.length; i++) {
            double lTii = this.lTData[i][i];
            determinant *= lTii * lTii;
        }
        return determinant;
    }

    public DecompositionSolver getSolver() {
        return new Solver(null);
    }
}
