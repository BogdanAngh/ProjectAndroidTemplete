package org.apache.commons.math4.linear;

import java.lang.reflect.Array;
import org.apache.commons.math4.util.FastMath;

class BiDiagonalTransformer {
    private RealMatrix cachedB;
    private RealMatrix cachedU;
    private RealMatrix cachedV;
    private final double[][] householderVectors;
    private final double[] main;
    private final double[] secondary;

    public BiDiagonalTransformer(RealMatrix matrix) {
        int m = matrix.getRowDimension();
        int n = matrix.getColumnDimension();
        int p = FastMath.min(m, n);
        this.householderVectors = matrix.getData();
        this.main = new double[p];
        this.secondary = new double[(p - 1)];
        this.cachedU = null;
        this.cachedB = null;
        this.cachedV = null;
        if (m >= n) {
            transformToUpperBiDiagonal();
        } else {
            transformToLowerBiDiagonal();
        }
    }

    public RealMatrix getU() {
        if (this.cachedU == null) {
            int k;
            int m = this.householderVectors.length;
            int n = this.householderVectors[0].length;
            int p = this.main.length;
            int diagOffset = m >= n ? 0 : 1;
            double[] diagonal = m >= n ? this.main : this.secondary;
            double[][] ua = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, m});
            for (k = m - 1; k >= p; k--) {
                ua[k][k] = 1.0d;
            }
            for (k = p - 1; k >= diagOffset; k--) {
                double[] hK = this.householderVectors[k];
                ua[k][k] = 1.0d;
                if (hK[k - diagOffset] != 0.0d) {
                    for (int j = k; j < m; j++) {
                        int i;
                        double alpha = 0.0d;
                        for (i = k; i < m; i++) {
                            alpha -= ua[i][j] * this.householderVectors[i][k - diagOffset];
                        }
                        alpha /= diagonal[k - diagOffset] * hK[k - diagOffset];
                        for (i = k; i < m; i++) {
                            double[] dArr = ua[i];
                            dArr[j] = dArr[j] + ((-alpha) * this.householderVectors[i][k - diagOffset]);
                        }
                    }
                }
            }
            if (diagOffset > 0) {
                ua[0][0] = 1.0d;
            }
            this.cachedU = MatrixUtils.createRealMatrix(ua);
        }
        return this.cachedU;
    }

    public RealMatrix getB() {
        if (this.cachedB == null) {
            int m = this.householderVectors.length;
            int n = this.householderVectors[0].length;
            double[][] ba = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, n});
            for (int i = 0; i < this.main.length; i++) {
                ba[i][i] = this.main[i];
                if (m < n) {
                    if (i > 0) {
                        ba[i][i - 1] = this.secondary[i - 1];
                    }
                } else if (i < this.main.length - 1) {
                    ba[i][i + 1] = this.secondary[i];
                }
            }
            this.cachedB = MatrixUtils.createRealMatrix(ba);
        }
        return this.cachedB;
    }

    public RealMatrix getV() {
        if (this.cachedV == null) {
            int k;
            int m = this.householderVectors.length;
            int n = this.householderVectors[0].length;
            int p = this.main.length;
            int diagOffset = m >= n ? 1 : 0;
            double[] diagonal = m >= n ? this.secondary : this.main;
            double[][] va = (double[][]) Array.newInstance(Double.TYPE, new int[]{n, n});
            for (k = n - 1; k >= p; k--) {
                va[k][k] = 1.0d;
            }
            for (k = p - 1; k >= diagOffset; k--) {
                double[] hK = this.householderVectors[k - diagOffset];
                va[k][k] = 1.0d;
                if (hK[k] != 0.0d) {
                    for (int j = k; j < n; j++) {
                        int i;
                        double beta = 0.0d;
                        for (i = k; i < n; i++) {
                            beta -= va[i][j] * hK[i];
                        }
                        beta /= diagonal[k - diagOffset] * hK[k];
                        for (i = k; i < n; i++) {
                            double[] dArr = va[i];
                            dArr[j] = dArr[j] + ((-beta) * hK[i]);
                        }
                    }
                }
            }
            if (diagOffset > 0) {
                va[0][0] = 1.0d;
            }
            this.cachedV = MatrixUtils.createRealMatrix(va);
        }
        return this.cachedV;
    }

    double[][] getHouseholderVectorsRef() {
        return this.householderVectors;
    }

    double[] getMainDiagonalRef() {
        return this.main;
    }

    double[] getSecondaryDiagonalRef() {
        return this.secondary;
    }

    boolean isUpperBiDiagonal() {
        return this.householderVectors.length >= this.householderVectors[0].length;
    }

    private void transformToUpperBiDiagonal() {
        int m = this.householderVectors.length;
        int n = this.householderVectors[0].length;
        for (int k = 0; k < n; k++) {
            int i;
            double a;
            int j;
            double[] hI;
            double xNormSqr = 0.0d;
            for (i = k; i < m; i++) {
                double c = this.householderVectors[i][k];
                xNormSqr += c * c;
            }
            double[] hK = this.householderVectors[k];
            if (hK[k] > 0.0d) {
                a = -FastMath.sqrt(xNormSqr);
            } else {
                a = FastMath.sqrt(xNormSqr);
            }
            this.main[k] = a;
            if (a != 0.0d) {
                hK[k] = hK[k] - a;
                for (j = k + 1; j < n; j++) {
                    double alpha = 0.0d;
                    for (i = k; i < m; i++) {
                        hI = this.householderVectors[i];
                        alpha -= hI[j] * hI[k];
                    }
                    alpha /= this.householderVectors[k][k] * a;
                    for (i = k; i < m; i++) {
                        hI = this.householderVectors[i];
                        hI[j] = hI[j] - (hI[k] * alpha);
                    }
                }
            }
            if (k < n - 1) {
                double b;
                xNormSqr = 0.0d;
                for (j = k + 1; j < n; j++) {
                    c = hK[j];
                    xNormSqr += c * c;
                }
                if (hK[k + 1] > 0.0d) {
                    b = -FastMath.sqrt(xNormSqr);
                } else {
                    b = FastMath.sqrt(xNormSqr);
                }
                this.secondary[k] = b;
                if (b != 0.0d) {
                    int i2 = k + 1;
                    hK[i2] = hK[i2] - b;
                    for (i = k + 1; i < m; i++) {
                        hI = this.householderVectors[i];
                        double beta = 0.0d;
                        for (j = k + 1; j < n; j++) {
                            beta -= hI[j] * hK[j];
                        }
                        beta /= hK[k + 1] * b;
                        for (j = k + 1; j < n; j++) {
                            hI[j] = hI[j] - (hK[j] * beta);
                        }
                    }
                }
            }
        }
    }

    private void transformToLowerBiDiagonal() {
        int m = this.householderVectors.length;
        int n = this.householderVectors[0].length;
        for (int k = 0; k < m; k++) {
            int j;
            double a;
            int i;
            double[] hI;
            double[] hK = this.householderVectors[k];
            double xNormSqr = 0.0d;
            for (j = k; j < n; j++) {
                double c = hK[j];
                xNormSqr += c * c;
            }
            if (hK[k] > 0.0d) {
                a = -FastMath.sqrt(xNormSqr);
            } else {
                a = FastMath.sqrt(xNormSqr);
            }
            this.main[k] = a;
            if (a != 0.0d) {
                hK[k] = hK[k] - a;
                for (i = k + 1; i < m; i++) {
                    hI = this.householderVectors[i];
                    double alpha = 0.0d;
                    for (j = k; j < n; j++) {
                        alpha -= hI[j] * hK[j];
                    }
                    alpha /= this.householderVectors[k][k] * a;
                    for (j = k; j < n; j++) {
                        hI[j] = hI[j] - (hK[j] * alpha);
                    }
                }
            }
            if (k < m - 1) {
                double b;
                double[] hKp1 = this.householderVectors[k + 1];
                xNormSqr = 0.0d;
                for (i = k + 1; i < m; i++) {
                    c = this.householderVectors[i][k];
                    xNormSqr += c * c;
                }
                if (hKp1[k] > 0.0d) {
                    b = -FastMath.sqrt(xNormSqr);
                } else {
                    b = FastMath.sqrt(xNormSqr);
                }
                this.secondary[k] = b;
                if (b != 0.0d) {
                    hKp1[k] = hKp1[k] - b;
                    for (j = k + 1; j < n; j++) {
                        double beta = 0.0d;
                        for (i = k + 1; i < m; i++) {
                            hI = this.householderVectors[i];
                            beta -= hI[j] * hI[k];
                        }
                        beta /= hKp1[k] * b;
                        for (i = k + 1; i < m; i++) {
                            hI = this.householderVectors[i];
                            hI[j] = hI[j] - (hI[k] * beta);
                        }
                    }
                }
            }
        }
    }
}
