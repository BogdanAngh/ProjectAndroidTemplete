package org.apache.commons.math4.linear;

import java.lang.reflect.Array;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

class HessenbergTransformer {
    private RealMatrix cachedH;
    private RealMatrix cachedP;
    private RealMatrix cachedPt;
    private final double[][] householderVectors;
    private final double[] ort;

    public HessenbergTransformer(RealMatrix matrix) {
        if (matrix.isSquare()) {
            int m = matrix.getRowDimension();
            this.householderVectors = matrix.getData();
            this.ort = new double[m];
            this.cachedP = null;
            this.cachedPt = null;
            this.cachedH = null;
            transform();
            return;
        }
        throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
    }

    public RealMatrix getP() {
        if (this.cachedP == null) {
            int j;
            int n = this.householderVectors.length;
            int high = n - 1;
            double[][] pa = (double[][]) Array.newInstance(Double.TYPE, new int[]{n, n});
            int i = 0;
            while (i < n) {
                j = 0;
                while (j < n) {
                    pa[i][j] = (double) (i == j ? 1 : 0);
                    j++;
                }
                i++;
            }
            for (int m = high - 1; m >= 1; m--) {
                if (this.householderVectors[m][m - 1] != 0.0d) {
                    for (i = m + 1; i <= high; i++) {
                        this.ort[i] = this.householderVectors[i][m - 1];
                    }
                    for (j = m; j <= high; j++) {
                        double g = 0.0d;
                        for (i = m; i <= high; i++) {
                            g += this.ort[i] * pa[i][j];
                        }
                        g = (g / this.ort[m]) / this.householderVectors[m][m - 1];
                        for (i = m; i <= high; i++) {
                            double[] dArr = pa[i];
                            dArr[j] = dArr[j] + (this.ort[i] * g);
                        }
                    }
                }
            }
            this.cachedP = MatrixUtils.createRealMatrix(pa);
        }
        return this.cachedP;
    }

    public RealMatrix getPT() {
        if (this.cachedPt == null) {
            this.cachedPt = getP().transpose();
        }
        return this.cachedPt;
    }

    public RealMatrix getH() {
        if (this.cachedH == null) {
            int m = this.householderVectors.length;
            double[][] h = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, m});
            for (int i = 0; i < m; i++) {
                if (i > 0) {
                    h[i][i - 1] = this.householderVectors[i][i - 1];
                }
                for (int j = i; j < m; j++) {
                    h[i][j] = this.householderVectors[i][j];
                }
            }
            this.cachedH = MatrixUtils.createRealMatrix(h);
        }
        return this.cachedH;
    }

    double[][] getHouseholderVectorsRef() {
        return this.householderVectors;
    }

    private void transform() {
        int n = this.householderVectors.length;
        int high = n - 1;
        for (int m = 1; m <= high - 1; m++) {
            int i;
            double scale = 0.0d;
            for (i = m; i <= high; i++) {
                scale += FastMath.abs(this.householderVectors[i][m - 1]);
            }
            if (!Precision.equals(scale, 0.0d)) {
                double g;
                int j;
                double f;
                double h = 0.0d;
                for (i = high; i >= m; i--) {
                    this.ort[i] = this.householderVectors[i][m - 1] / scale;
                    h += this.ort[i] * this.ort[i];
                }
                if (this.ort[m] > 0.0d) {
                    g = -FastMath.sqrt(h);
                } else {
                    g = FastMath.sqrt(h);
                }
                h -= this.ort[m] * g;
                double[] dArr = this.ort;
                dArr[m] = dArr[m] - g;
                for (j = m; j < n; j++) {
                    f = 0.0d;
                    for (i = high; i >= m; i--) {
                        f += this.ort[i] * this.householderVectors[i][j];
                    }
                    f /= h;
                    for (i = m; i <= high; i++) {
                        dArr = this.householderVectors[i];
                        dArr[j] = dArr[j] - (this.ort[i] * f);
                    }
                }
                for (i = 0; i <= high; i++) {
                    f = 0.0d;
                    for (j = high; j >= m; j--) {
                        f += this.ort[j] * this.householderVectors[i][j];
                    }
                    f /= h;
                    for (j = m; j <= high; j++) {
                        dArr = this.householderVectors[i];
                        dArr[j] = dArr[j] - (this.ort[j] * f);
                    }
                }
                this.ort[m] = this.ort[m] * scale;
                this.householderVectors[m][m - 1] = scale * g;
            }
        }
    }
}
