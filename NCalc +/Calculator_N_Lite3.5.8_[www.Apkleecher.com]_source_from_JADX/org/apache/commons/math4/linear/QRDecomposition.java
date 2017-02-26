package org.apache.commons.math4.linear;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;

public class QRDecomposition {
    private RealMatrix cachedH;
    private RealMatrix cachedQ;
    private RealMatrix cachedQT;
    private RealMatrix cachedR;
    private double[][] qrt;
    private double[] rDiag;
    private final double threshold;

    private static class Solver implements DecompositionSolver {
        private final double[][] qrt;
        private final double[] rDiag;
        private final double threshold;

        private Solver(double[][] qrt, double[] rDiag, double threshold) {
            this.qrt = qrt;
            this.rDiag = rDiag;
            this.threshold = threshold;
        }

        public boolean isNonSingular() {
            return !checkSingular(this.rDiag, this.threshold, false);
        }

        public RealVector solve(RealVector b) {
            int n = this.qrt.length;
            int m = this.qrt[0].length;
            if (b.getDimension() != m) {
                throw new DimensionMismatchException(b.getDimension(), m);
            }
            checkSingular(this.rDiag, this.threshold, true);
            double[] x = new double[n];
            double[] y = b.toArray();
            for (int minor = 0; minor < FastMath.min(m, n); minor++) {
                int row;
                double[] qrtMinor = this.qrt[minor];
                double dotProduct = 0.0d;
                for (row = minor; row < m; row++) {
                    dotProduct += y[row] * qrtMinor[row];
                }
                dotProduct /= this.rDiag[minor] * qrtMinor[minor];
                for (row = minor; row < m; row++) {
                    y[row] = y[row] + (qrtMinor[row] * dotProduct);
                }
            }
            for (row = this.rDiag.length - 1; row >= 0; row--) {
                y[row] = y[row] / this.rDiag[row];
                double yRow = y[row];
                double[] qrtRow = this.qrt[row];
                x[row] = yRow;
                for (int i = 0; i < row; i++) {
                    y[i] = y[i] - (qrtRow[i] * yRow);
                }
            }
            return new ArrayRealVector(x, false);
        }

        public RealMatrix solve(RealMatrix b) {
            int n = this.qrt.length;
            int m = this.qrt[0].length;
            if (b.getRowDimension() != m) {
                throw new DimensionMismatchException(b.getRowDimension(), m);
            }
            checkSingular(this.rDiag, this.threshold, true);
            int columns = b.getColumnDimension();
            int cBlocks = ((columns + 52) - 1) / 52;
            double[][] xBlocks = BlockRealMatrix.createBlocksLayout(n, columns);
            double[][] y = (double[][]) Array.newInstance(Double.TYPE, new int[]{b.getRowDimension(), 52});
            double[] alpha = new double[52];
            for (int kBlock = 0; kBlock < cBlocks; kBlock++) {
                int kStart = kBlock * 52;
                int kEnd = FastMath.min(kStart + 52, columns);
                int kWidth = kEnd - kStart;
                b.copySubMatrix(0, m - 1, kStart, kEnd - 1, y);
                for (int minor = 0; minor < FastMath.min(m, n); minor++) {
                    int row;
                    double[] qrtMinor = this.qrt[minor];
                    double factor = 1.0d / (this.rDiag[minor] * qrtMinor[minor]);
                    Arrays.fill(alpha, 0, kWidth, 0.0d);
                    for (row = minor; row < m; row++) {
                        int k;
                        double d = qrtMinor[row];
                        double[] yRow = y[row];
                        for (k = 0; k < kWidth; k++) {
                            alpha[k] = alpha[k] + (yRow[k] * d);
                        }
                    }
                    for (k = 0; k < kWidth; k++) {
                        alpha[k] = alpha[k] * factor;
                    }
                    for (row = minor; row < m; row++) {
                        d = qrtMinor[row];
                        yRow = y[row];
                        for (k = 0; k < kWidth; k++) {
                            yRow[k] = yRow[k] + (alpha[k] * d);
                        }
                    }
                }
                for (int j = this.rDiag.length - 1; j >= 0; j--) {
                    int jBlock = j / 52;
                    int jStart = jBlock * 52;
                    factor = 1.0d / this.rDiag[j];
                    double[] yJ = y[j];
                    double[] xBlock = xBlocks[(jBlock * cBlocks) + kBlock];
                    k = 0;
                    int index = (j - jStart) * kWidth;
                    while (k < kWidth) {
                        yJ[k] = yJ[k] * factor;
                        int index2 = index + 1;
                        xBlock[index] = yJ[k];
                        k++;
                        index = index2;
                    }
                    double[] qrtJ = this.qrt[j];
                    for (int i = 0; i < j; i++) {
                        double rIJ = qrtJ[i];
                        double[] yI = y[i];
                        for (k = 0; k < kWidth; k++) {
                            yI[k] = yI[k] - (yJ[k] * rIJ);
                        }
                    }
                }
            }
            return new BlockRealMatrix(n, columns, xBlocks, false);
        }

        public RealMatrix getInverse() {
            return solve(MatrixUtils.createRealIdentityMatrix(this.qrt[0].length));
        }

        private static boolean checkSingular(double[] diag, double min, boolean raise) {
            int len = diag.length;
            int i = 0;
            while (i < len) {
                if (FastMath.abs(diag[i]) > min) {
                    i++;
                } else if (!raise) {
                    return true;
                } else {
                    SingularMatrixException e = new SingularMatrixException();
                    e.getContext().addMessage(LocalizedFormats.NUMBER_TOO_SMALL, Double.valueOf(d), Double.valueOf(min));
                    e.getContext().addMessage(LocalizedFormats.INDEX, Integer.valueOf(i));
                    throw e;
                }
            }
            return false;
        }
    }

    public QRDecomposition(RealMatrix matrix) {
        this(matrix, 0.0d);
    }

    public QRDecomposition(RealMatrix matrix, double threshold) {
        this.threshold = threshold;
        int m = matrix.getRowDimension();
        int n = matrix.getColumnDimension();
        this.qrt = matrix.transpose().getData();
        this.rDiag = new double[FastMath.min(m, n)];
        this.cachedQ = null;
        this.cachedQT = null;
        this.cachedR = null;
        this.cachedH = null;
        decompose(this.qrt);
    }

    protected void decompose(double[][] matrix) {
        for (int minor = 0; minor < FastMath.min(matrix.length, matrix[0].length); minor++) {
            performHouseholderReflection(minor, matrix);
        }
    }

    protected void performHouseholderReflection(int minor, double[][] matrix) {
        int row;
        double[] qrtMinor = matrix[minor];
        double xNormSqr = 0.0d;
        for (row = minor; row < qrtMinor.length; row++) {
            double c = qrtMinor[row];
            xNormSqr += c * c;
        }
        double a = qrtMinor[minor] > 0.0d ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
        this.rDiag[minor] = a;
        if (a != 0.0d) {
            qrtMinor[minor] = qrtMinor[minor] - a;
            for (int col = minor + 1; col < matrix.length; col++) {
                double[] qrtCol = matrix[col];
                double alpha = 0.0d;
                for (row = minor; row < qrtCol.length; row++) {
                    alpha -= qrtCol[row] * qrtMinor[row];
                }
                alpha /= qrtMinor[minor] * a;
                for (row = minor; row < qrtCol.length; row++) {
                    qrtCol[row] = qrtCol[row] - (qrtMinor[row] * alpha);
                }
            }
        }
    }

    public RealMatrix getR() {
        if (this.cachedR == null) {
            int n = this.qrt.length;
            int m = this.qrt[0].length;
            double[][] ra = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, n});
            for (int row = FastMath.min(m, n) - 1; row >= 0; row--) {
                ra[row][row] = this.rDiag[row];
                for (int col = row + 1; col < n; col++) {
                    ra[row][col] = this.qrt[col][row];
                }
            }
            this.cachedR = MatrixUtils.createRealMatrix(ra);
        }
        return this.cachedR;
    }

    public RealMatrix getQ() {
        if (this.cachedQ == null) {
            this.cachedQ = getQT().transpose();
        }
        return this.cachedQ;
    }

    public RealMatrix getQT() {
        if (this.cachedQT == null) {
            int minor;
            int n = this.qrt.length;
            int m = this.qrt[0].length;
            double[][] qta = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, m});
            for (minor = m - 1; minor >= FastMath.min(m, n); minor--) {
                qta[minor][minor] = 1.0d;
            }
            for (minor = FastMath.min(m, n) - 1; minor >= 0; minor--) {
                double[] qrtMinor = this.qrt[minor];
                qta[minor][minor] = 1.0d;
                if (qrtMinor[minor] != 0.0d) {
                    for (int col = minor; col < m; col++) {
                        int row;
                        double alpha = 0.0d;
                        for (row = minor; row < m; row++) {
                            alpha -= qta[col][row] * qrtMinor[row];
                        }
                        alpha /= this.rDiag[minor] * qrtMinor[minor];
                        for (row = minor; row < m; row++) {
                            double[] dArr = qta[col];
                            dArr[row] = dArr[row] + ((-alpha) * qrtMinor[row]);
                        }
                    }
                }
            }
            this.cachedQT = MatrixUtils.createRealMatrix(qta);
        }
        return this.cachedQT;
    }

    public RealMatrix getH() {
        if (this.cachedH == null) {
            int n = this.qrt.length;
            int m = this.qrt[0].length;
            double[][] ha = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, n});
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < FastMath.min(i + 1, n); j++) {
                    ha[i][j] = this.qrt[j][i] / (-this.rDiag[j]);
                }
            }
            this.cachedH = MatrixUtils.createRealMatrix(ha);
        }
        return this.cachedH;
    }

    public DecompositionSolver getSolver() {
        return new Solver(this.rDiag, this.threshold, null);
    }
}
