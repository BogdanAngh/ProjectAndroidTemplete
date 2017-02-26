package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

class SchurTransformer {
    private static final int MAX_ITERATIONS = 100;
    private RealMatrix cachedP;
    private RealMatrix cachedPt;
    private RealMatrix cachedT;
    private final double epsilon;
    private final double[][] matrixP;
    private final double[][] matrixT;

    private static class ShiftInfo {
        double exShift;
        double w;
        double x;
        double y;

        private ShiftInfo() {
        }
    }

    public SchurTransformer(RealMatrix matrix) {
        this.epsilon = Precision.EPSILON;
        if (matrix.isSquare()) {
            HessenbergTransformer transformer = new HessenbergTransformer(matrix);
            this.matrixT = transformer.getH().getData();
            this.matrixP = transformer.getP().getData();
            this.cachedT = null;
            this.cachedP = null;
            this.cachedPt = null;
            transform();
            return;
        }
        throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
    }

    public RealMatrix getP() {
        if (this.cachedP == null) {
            this.cachedP = MatrixUtils.createRealMatrix(this.matrixP);
        }
        return this.cachedP;
    }

    public RealMatrix getPT() {
        if (this.cachedPt == null) {
            this.cachedPt = getP().transpose();
        }
        return this.cachedPt;
    }

    public RealMatrix getT() {
        if (this.cachedT == null) {
            this.cachedT = MatrixUtils.createRealMatrix(this.matrixT);
        }
        return this.cachedT;
    }

    private void transform() {
        int n = this.matrixT.length;
        double norm = getNorm();
        ShiftInfo shift = new ShiftInfo();
        int iteration = 0;
        int iu = n - 1;
        while (iu >= 0) {
            int il = findSmallSubDiagonalElement(iu, norm);
            double[] dArr;
            if (il == iu) {
                dArr = this.matrixT[iu];
                dArr[iu] = dArr[iu] + shift.exShift;
                iu--;
                iteration = 0;
            } else if (il == iu - 1) {
                double p = (this.matrixT[iu - 1][iu - 1] - this.matrixT[iu][iu]) / 2.0d;
                double q = (p * p) + (this.matrixT[iu][iu - 1] * this.matrixT[iu - 1][iu]);
                dArr = this.matrixT[iu];
                dArr[iu] = dArr[iu] + shift.exShift;
                dArr = this.matrixT[iu - 1];
                int i = iu - 1;
                dArr[i] = dArr[i] + shift.exShift;
                if (q >= 0.0d) {
                    int i2;
                    double z = FastMath.sqrt(FastMath.abs(q));
                    if (p >= 0.0d) {
                        z += p;
                    } else {
                        z = p - z;
                    }
                    double x = this.matrixT[iu][iu - 1];
                    double s = FastMath.abs(x) + FastMath.abs(z);
                    p = x / s;
                    q = z / s;
                    double r = FastMath.sqrt((p * p) + (q * q));
                    p /= r;
                    q /= r;
                    for (int j = iu - 1; j < n; j++) {
                        z = this.matrixT[iu - 1][j];
                        this.matrixT[iu - 1][j] = (q * z) + (this.matrixT[iu][j] * p);
                        this.matrixT[iu][j] = (this.matrixT[iu][j] * q) - (p * z);
                    }
                    for (i2 = 0; i2 <= iu; i2++) {
                        z = this.matrixT[i2][iu - 1];
                        this.matrixT[i2][iu - 1] = (q * z) + (this.matrixT[i2][iu] * p);
                        this.matrixT[i2][iu] = (this.matrixT[i2][iu] * q) - (p * z);
                    }
                    for (i2 = 0; i2 <= n - 1; i2++) {
                        z = this.matrixP[i2][iu - 1];
                        this.matrixP[i2][iu - 1] = (q * z) + (this.matrixP[i2][iu] * p);
                        this.matrixP[i2][iu] = (this.matrixP[i2][iu] * q) - (p * z);
                    }
                }
                iu -= 2;
                iteration = 0;
            } else {
                computeShift(il, iu, iteration, shift);
                iteration++;
                if (iteration > MAX_ITERATIONS) {
                    Object[] objArr = new Object[0];
                    throw new MaxCountExceededException(LocalizedFormats.CONVERGENCE_FAILED, Integer.valueOf(MAX_ITERATIONS), objArr);
                }
                double[] hVec = new double[3];
                performDoubleQRStep(il, initQRStep(il, iu, shift, hVec), iu, shift, hVec);
            }
        }
    }

    private double getNorm() {
        double norm = 0.0d;
        for (int i = 0; i < this.matrixT.length; i++) {
            for (int j = FastMath.max(i - 1, 0); j < this.matrixT.length; j++) {
                norm += FastMath.abs(this.matrixT[i][j]);
            }
        }
        return norm;
    }

    private int findSmallSubDiagonalElement(int startIdx, double norm) {
        int l = startIdx;
        while (l > 0) {
            double s = FastMath.abs(this.matrixT[l - 1][l - 1]) + FastMath.abs(this.matrixT[l][l]);
            if (s == 0.0d) {
                s = norm;
            }
            if (FastMath.abs(this.matrixT[l][l - 1]) < this.epsilon * s) {
                break;
            }
            l--;
        }
        return l;
    }

    private void computeShift(int l, int idx, int iteration, ShiftInfo shift) {
        int i;
        shift.x = this.matrixT[idx][idx];
        shift.w = 0.0d;
        shift.y = 0.0d;
        if (l < idx) {
            shift.y = this.matrixT[idx - 1][idx - 1];
            shift.w = this.matrixT[idx][idx - 1] * this.matrixT[idx - 1][idx];
        }
        if (iteration == 10) {
            shift.exShift += shift.x;
            for (i = 0; i <= idx; i++) {
                double[] dArr = this.matrixT[i];
                dArr[i] = dArr[i] - shift.x;
            }
            double s = FastMath.abs(this.matrixT[idx][idx - 1]) + FastMath.abs(this.matrixT[idx - 1][idx - 2]);
            shift.x = 0.75d * s;
            shift.y = 0.75d * s;
            shift.w = (-0.4375d * s) * s;
        }
        if (iteration == 30) {
            s = (shift.y - shift.x) / 2.0d;
            s = (s * s) + shift.w;
            if (s > 0.0d) {
                s = FastMath.sqrt(s);
                if (shift.y < shift.x) {
                    s = -s;
                }
                s = shift.x - (shift.w / (((shift.y - shift.x) / 2.0d) + s));
                for (i = 0; i <= idx; i++) {
                    dArr = this.matrixT[i];
                    dArr[i] = dArr[i] - s;
                }
                shift.exShift += s;
                shift.w = 0.964d;
                shift.y = 0.964d;
                shift.x = 0.964d;
            }
        }
    }

    private int initQRStep(int il, int iu, ShiftInfo shift, double[] hVec) {
        int im = iu - 2;
        while (im >= il) {
            double z = this.matrixT[im][im];
            double r = shift.x - z;
            double s = shift.y - z;
            hVec[0] = (((r * s) - shift.w) / this.matrixT[im + 1][im]) + this.matrixT[im][im + 1];
            hVec[1] = ((this.matrixT[im + 1][im + 1] - z) - r) - s;
            hVec[2] = this.matrixT[im + 2][im + 1];
            if (im == il) {
                break;
            }
            if (FastMath.abs(this.matrixT[im][im - 1]) * (FastMath.abs(hVec[1]) + FastMath.abs(hVec[2])) < this.epsilon * (FastMath.abs(hVec[0]) * ((FastMath.abs(this.matrixT[im - 1][im - 1]) + FastMath.abs(z)) + FastMath.abs(this.matrixT[im + 1][im + 1])))) {
                break;
            }
            im--;
        }
        return im;
    }

    private void performDoubleQRStep(int il, int im, int iu, ShiftInfo shift, double[] hVec) {
        int n = this.matrixT.length;
        double p = hVec[0];
        double q = hVec[1];
        double r = hVec[2];
        int k = im;
        while (k <= iu - 1) {
            int i;
            boolean notlast = k != iu + -1;
            if (k != im) {
                p = this.matrixT[k][k - 1];
                q = this.matrixT[k + 1][k - 1];
                r = notlast ? this.matrixT[k + 2][k - 1] : 0.0d;
                shift.x = (FastMath.abs(p) + FastMath.abs(q)) + FastMath.abs(r);
                if (Precision.equals(shift.x, 0.0d, this.epsilon)) {
                    k++;
                } else {
                    p /= shift.x;
                    q /= shift.x;
                    r /= shift.x;
                }
            }
            double s = FastMath.sqrt(((p * p) + (q * q)) + (r * r));
            if (p < 0.0d) {
                s = -s;
            }
            if (s != 0.0d) {
                double[] dArr;
                int i2;
                if (k != im) {
                    this.matrixT[k][k - 1] = (-s) * shift.x;
                } else if (il != im) {
                    this.matrixT[k][k - 1] = -this.matrixT[k][k - 1];
                }
                p += s;
                shift.x = p / s;
                shift.y = q / s;
                double z = r / s;
                q /= p;
                r /= p;
                for (int j = k; j < n; j++) {
                    p = this.matrixT[k][j] + (this.matrixT[k + 1][j] * q);
                    if (notlast) {
                        p += this.matrixT[k + 2][j] * r;
                        dArr = this.matrixT[k + 2];
                        dArr[j] = dArr[j] - (p * z);
                    }
                    dArr = this.matrixT[k];
                    dArr[j] = dArr[j] - (shift.x * p);
                    dArr = this.matrixT[k + 1];
                    dArr[j] = dArr[j] - (shift.y * p);
                }
                i = 0;
                while (true) {
                    if (i > FastMath.min(iu, k + 3)) {
                        break;
                    }
                    p = (shift.x * this.matrixT[i][k]) + (shift.y * this.matrixT[i][k + 1]);
                    if (notlast) {
                        p += this.matrixT[i][k + 2] * z;
                        dArr = this.matrixT[i];
                        i2 = k + 2;
                        dArr[i2] = dArr[i2] - (p * r);
                    }
                    dArr = this.matrixT[i];
                    dArr[k] = dArr[k] - p;
                    dArr = this.matrixT[i];
                    i2 = k + 1;
                    dArr[i2] = dArr[i2] - (p * q);
                    i++;
                }
                int high = this.matrixT.length - 1;
                for (i = 0; i <= high; i++) {
                    p = (shift.x * this.matrixP[i][k]) + (shift.y * this.matrixP[i][k + 1]);
                    if (notlast) {
                        p += this.matrixP[i][k + 2] * z;
                        dArr = this.matrixP[i];
                        i2 = k + 2;
                        dArr[i2] = dArr[i2] - (p * r);
                    }
                    dArr = this.matrixP[i];
                    dArr[k] = dArr[k] - p;
                    dArr = this.matrixP[i];
                    i2 = k + 1;
                    dArr[i2] = dArr[i2] - (p * q);
                }
            }
            k++;
        }
        for (i = im + 2; i <= iu; i++) {
            this.matrixT[i][i - 2] = 0.0d;
            if (i > im + 2) {
                this.matrixT[i][i - 3] = 0.0d;
            }
        }
    }
}
