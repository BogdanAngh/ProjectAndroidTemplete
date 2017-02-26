package org.apache.commons.math4.linear;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math4.util.FastMath;

class TriDiagonalTransformer {
    private RealMatrix cachedQ;
    private RealMatrix cachedQt;
    private RealMatrix cachedT;
    private final double[][] householderVectors;
    private final double[] main;
    private final double[] secondary;

    public TriDiagonalTransformer(RealMatrix matrix) {
        if (matrix.isSquare()) {
            int m = matrix.getRowDimension();
            this.householderVectors = matrix.getData();
            this.main = new double[m];
            this.secondary = new double[(m - 1)];
            this.cachedQ = null;
            this.cachedQt = null;
            this.cachedT = null;
            transform();
            return;
        }
        throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
    }

    public RealMatrix getQ() {
        if (this.cachedQ == null) {
            this.cachedQ = getQT().transpose();
        }
        return this.cachedQ;
    }

    public RealMatrix getQT() {
        if (this.cachedQt == null) {
            int m = this.householderVectors.length;
            double[][] qta = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, m});
            for (int k = m - 1; k >= 1; k--) {
                double[] hK = this.householderVectors[k - 1];
                qta[k][k] = 1.0d;
                if (hK[k] != 0.0d) {
                    int i;
                    double inv = 1.0d / (this.secondary[k - 1] * hK[k]);
                    double beta = 1.0d / this.secondary[k - 1];
                    qta[k][k] = 1.0d + (hK[k] * beta);
                    for (i = k + 1; i < m; i++) {
                        qta[k][i] = hK[i] * beta;
                    }
                    for (int j = k + 1; j < m; j++) {
                        beta = 0.0d;
                        for (i = k + 1; i < m; i++) {
                            beta += qta[j][i] * hK[i];
                        }
                        beta *= inv;
                        qta[j][k] = hK[k] * beta;
                        for (i = k + 1; i < m; i++) {
                            double[] dArr = qta[j];
                            dArr[i] = dArr[i] + (hK[i] * beta);
                        }
                    }
                }
            }
            qta[0][0] = 1.0d;
            this.cachedQt = MatrixUtils.createRealMatrix(qta);
        }
        return this.cachedQt;
    }

    public RealMatrix getT() {
        if (this.cachedT == null) {
            int m = this.main.length;
            double[][] ta = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, m});
            for (int i = 0; i < m; i++) {
                ta[i][i] = this.main[i];
                if (i > 0) {
                    ta[i][i - 1] = this.secondary[i - 1];
                }
                if (i < this.main.length - 1) {
                    ta[i][i + 1] = this.secondary[i];
                }
            }
            this.cachedT = MatrixUtils.createRealMatrix(ta);
        }
        return this.cachedT;
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

    private void transform() {
        int m = this.householderVectors.length;
        double[] z = new double[m];
        for (int k = 0; k < m - 1; k++) {
            int j;
            double a;
            double[] hK = this.householderVectors[k];
            this.main[k] = hK[k];
            double xNormSqr = 0.0d;
            for (j = k + 1; j < m; j++) {
                double c = hK[j];
                xNormSqr += c * c;
            }
            if (hK[k + 1] > 0.0d) {
                a = -FastMath.sqrt(xNormSqr);
            } else {
                a = FastMath.sqrt(xNormSqr);
            }
            this.secondary[k] = a;
            if (a != 0.0d) {
                int i;
                double[] hI;
                int i2 = k + 1;
                hK[i2] = hK[i2] - a;
                double beta = -1.0d / (hK[k + 1] * a);
                Arrays.fill(z, k + 1, m, 0.0d);
                for (i = k + 1; i < m; i++) {
                    hI = this.householderVectors[i];
                    double hKI = hK[i];
                    double zI = hI[i] * hKI;
                    for (j = i + 1; j < m; j++) {
                        double hIJ = hI[j];
                        zI += hK[j] * hIJ;
                        z[j] = z[j] + (hIJ * hKI);
                    }
                    z[i] = (z[i] + zI) * beta;
                }
                double gamma = 0.0d;
                for (i = k + 1; i < m; i++) {
                    gamma += z[i] * hK[i];
                }
                gamma *= beta / 2.0d;
                for (i = k + 1; i < m; i++) {
                    z[i] = z[i] - (hK[i] * gamma);
                }
                for (i = k + 1; i < m; i++) {
                    hI = this.householderVectors[i];
                    for (j = i; j < m; j++) {
                        hI[j] = hI[j] - ((hK[i] * z[j]) + (z[i] * hK[j]));
                    }
                }
            }
        }
        this.main[m - 1] = this.householderVectors[m - 1][m - 1];
    }
}
