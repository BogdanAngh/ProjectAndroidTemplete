package org.apache.commons.math4.linear;

import com.google.common.base.Ascii;
import java.lang.reflect.Array;
import org.apache.commons.math4.complex.Complex;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.MathUnsupportedOperationException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

public class EigenDecomposition {
    private static final double EPSILON = 1.0E-12d;
    private RealMatrix cachedD;
    private RealMatrix cachedV;
    private RealMatrix cachedVt;
    private ArrayRealVector[] eigenvectors;
    private double[] imagEigenvalues;
    private final boolean isSymmetric;
    private double[] main;
    private final byte maxIter;
    private double[] realEigenvalues;
    private double[] secondary;
    private TriDiagonalTransformer transformer;

    private static class Solver implements DecompositionSolver {
        private final ArrayRealVector[] eigenvectors;
        private final double[] imagEigenvalues;
        private final double[] realEigenvalues;

        private Solver(double[] realEigenvalues, double[] imagEigenvalues, ArrayRealVector[] eigenvectors) {
            this.realEigenvalues = realEigenvalues;
            this.imagEigenvalues = imagEigenvalues;
            this.eigenvectors = eigenvectors;
        }

        public RealVector solve(RealVector b) {
            if (isNonSingular()) {
                int m = this.realEigenvalues.length;
                if (b.getDimension() != m) {
                    throw new DimensionMismatchException(b.getDimension(), m);
                }
                double[] bp = new double[m];
                for (int i = 0; i < m; i++) {
                    ArrayRealVector v = this.eigenvectors[i];
                    double[] vData = v.getDataRef();
                    double s = v.dotProduct(b) / this.realEigenvalues[i];
                    for (int j = 0; j < m; j++) {
                        bp[j] = bp[j] + (vData[j] * s);
                    }
                }
                return new ArrayRealVector(bp, false);
            }
            throw new SingularMatrixException();
        }

        public RealMatrix solve(RealMatrix b) {
            if (isNonSingular()) {
                int m = this.realEigenvalues.length;
                if (b.getRowDimension() != m) {
                    throw new DimensionMismatchException(b.getRowDimension(), m);
                }
                int nColB = b.getColumnDimension();
                double[][] bp = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, nColB});
                double[] tmpCol = new double[m];
                for (int k = 0; k < nColB; k++) {
                    int i;
                    for (i = 0; i < m; i++) {
                        tmpCol[i] = b.getEntry(i, k);
                        bp[i][k] = 0.0d;
                    }
                    for (i = 0; i < m; i++) {
                        int j;
                        ArrayRealVector v = this.eigenvectors[i];
                        double[] vData = v.getDataRef();
                        double s = 0.0d;
                        for (j = 0; j < m; j++) {
                            s += v.getEntry(j) * tmpCol[j];
                        }
                        s /= this.realEigenvalues[i];
                        for (j = 0; j < m; j++) {
                            double[] dArr = bp[j];
                            dArr[k] = dArr[k] + (vData[j] * s);
                        }
                    }
                }
                return new Array2DRowRealMatrix(bp, false);
            }
            throw new SingularMatrixException();
        }

        public boolean isNonSingular() {
            int i;
            double largestEigenvalueNorm = 0.0d;
            for (i = 0; i < this.realEigenvalues.length; i++) {
                largestEigenvalueNorm = FastMath.max(largestEigenvalueNorm, eigenvalueNorm(i));
            }
            if (largestEigenvalueNorm == 0.0d) {
                return false;
            }
            for (i = 0; i < this.realEigenvalues.length; i++) {
                if (Precision.equals(eigenvalueNorm(i) / largestEigenvalueNorm, 0.0d, EigenDecomposition.EPSILON)) {
                    return false;
                }
            }
            return true;
        }

        private double eigenvalueNorm(int i) {
            double re = this.realEigenvalues[i];
            double im = this.imagEigenvalues[i];
            return FastMath.sqrt((re * re) + (im * im));
        }

        public RealMatrix getInverse() {
            if (isNonSingular()) {
                int m = this.realEigenvalues.length;
                double[][] invData = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, m});
                for (int i = 0; i < m; i++) {
                    double[] invI = invData[i];
                    for (int j = 0; j < m; j++) {
                        double invIJ = 0.0d;
                        for (int k = 0; k < m; k++) {
                            double[] vK = this.eigenvectors[k].getDataRef();
                            invIJ += (vK[i] * vK[j]) / this.realEigenvalues[k];
                        }
                        invI[j] = invIJ;
                    }
                }
                return MatrixUtils.createRealMatrix(invData);
            }
            throw new SingularMatrixException();
        }
    }

    public EigenDecomposition(RealMatrix matrix) throws MathArithmeticException {
        this.maxIter = Ascii.RS;
        this.isSymmetric = MatrixUtils.isSymmetric(matrix, ((double) ((matrix.getRowDimension() * 10) * matrix.getColumnDimension())) * Precision.EPSILON);
        if (this.isSymmetric) {
            transformToTridiagonal(matrix);
            findEigenVectors(this.transformer.getQ().getData());
            return;
        }
        findEigenVectorsFromSchur(transformToSchur(matrix));
    }

    public EigenDecomposition(double[] main, double[] secondary) {
        this.maxIter = Ascii.RS;
        this.isSymmetric = true;
        this.main = (double[]) main.clone();
        this.secondary = (double[]) secondary.clone();
        this.transformer = null;
        int size = main.length;
        double[][] z = (double[][]) Array.newInstance(Double.TYPE, new int[]{size, size});
        for (int i = 0; i < size; i++) {
            z[i][i] = 1.0d;
        }
        findEigenVectors(z);
    }

    public RealMatrix getV() {
        if (this.cachedV == null) {
            int m = this.eigenvectors.length;
            this.cachedV = MatrixUtils.createRealMatrix(m, m);
            for (int k = 0; k < m; k++) {
                this.cachedV.setColumnVector(k, this.eigenvectors[k]);
            }
        }
        return this.cachedV;
    }

    public RealMatrix getD() {
        if (this.cachedD == null) {
            this.cachedD = MatrixUtils.createRealDiagonalMatrix(this.realEigenvalues);
            for (int i = 0; i < this.imagEigenvalues.length; i++) {
                if (Precision.compareTo(this.imagEigenvalues[i], 0.0d, EPSILON) > 0) {
                    this.cachedD.setEntry(i, i + 1, this.imagEigenvalues[i]);
                } else if (Precision.compareTo(this.imagEigenvalues[i], 0.0d, EPSILON) < 0) {
                    this.cachedD.setEntry(i, i - 1, this.imagEigenvalues[i]);
                }
            }
        }
        return this.cachedD;
    }

    public RealMatrix getVT() {
        if (this.cachedVt == null) {
            int m = this.eigenvectors.length;
            this.cachedVt = MatrixUtils.createRealMatrix(m, m);
            for (int k = 0; k < m; k++) {
                this.cachedVt.setRowVector(k, this.eigenvectors[k]);
            }
        }
        return this.cachedVt;
    }

    public boolean hasComplexEigenvalues() {
        for (double equals : this.imagEigenvalues) {
            if (!Precision.equals(equals, 0.0d, EPSILON)) {
                return true;
            }
        }
        return false;
    }

    public double[] getRealEigenvalues() {
        return (double[]) this.realEigenvalues.clone();
    }

    public double getRealEigenvalue(int i) {
        return this.realEigenvalues[i];
    }

    public double[] getImagEigenvalues() {
        return (double[]) this.imagEigenvalues.clone();
    }

    public double getImagEigenvalue(int i) {
        return this.imagEigenvalues[i];
    }

    public RealVector getEigenvector(int i) {
        return this.eigenvectors[i].copy();
    }

    public double getDeterminant() {
        double determinant = 1.0d;
        for (double lambda : this.realEigenvalues) {
            determinant *= lambda;
        }
        return determinant;
    }

    public RealMatrix getSquareRoot() {
        if (this.isSymmetric) {
            double[] sqrtEigenValues = new double[this.realEigenvalues.length];
            for (int i = 0; i < this.realEigenvalues.length; i++) {
                double eigen = this.realEigenvalues[i];
                if (eigen <= 0.0d) {
                    throw new MathUnsupportedOperationException();
                }
                sqrtEigenValues[i] = FastMath.sqrt(eigen);
            }
            RealMatrix sqrtEigen = MatrixUtils.createRealDiagonalMatrix(sqrtEigenValues);
            RealMatrix v = getV();
            return v.multiply(sqrtEigen).multiply(getVT());
        }
        throw new MathUnsupportedOperationException();
    }

    public DecompositionSolver getSolver() {
        if (!hasComplexEigenvalues()) {
            return new Solver(this.imagEigenvalues, this.eigenvectors, null);
        }
        throw new MathUnsupportedOperationException();
    }

    private void transformToTridiagonal(RealMatrix matrix) {
        this.transformer = new TriDiagonalTransformer(matrix);
        this.main = this.transformer.getMainDiagonalRef();
        this.secondary = this.transformer.getSecondaryDiagonalRef();
    }

    private void findEigenVectors(double[][] householderMatrix) {
        int i;
        double[][] z = (double[][]) householderMatrix.clone();
        int n = this.main.length;
        this.realEigenvalues = new double[n];
        this.imagEigenvalues = new double[n];
        double[] e = new double[n];
        for (i = 0; i < n - 1; i++) {
            this.realEigenvalues[i] = this.main[i];
            e[i] = this.secondary[i];
        }
        this.realEigenvalues[n - 1] = this.main[n - 1];
        e[n - 1] = 0.0d;
        double maxAbsoluteValue = 0.0d;
        for (i = 0; i < n; i++) {
            if (FastMath.abs(this.realEigenvalues[i]) > maxAbsoluteValue) {
                maxAbsoluteValue = FastMath.abs(this.realEigenvalues[i]);
            }
            if (FastMath.abs(e[i]) > maxAbsoluteValue) {
                maxAbsoluteValue = FastMath.abs(e[i]);
            }
        }
        if (maxAbsoluteValue != 0.0d) {
            for (i = 0; i < n; i++) {
                if (FastMath.abs(this.realEigenvalues[i]) <= Precision.EPSILON * maxAbsoluteValue) {
                    this.realEigenvalues[i] = 0.0d;
                }
                if (FastMath.abs(e[i]) <= Precision.EPSILON * maxAbsoluteValue) {
                    e[i] = 0.0d;
                }
            }
        }
        int j = 0;
        while (j < n) {
            int its = 0;
            int m;
            do {
                double p;
                m = j;
                while (m < n - 1) {
                    double delta = FastMath.abs(this.realEigenvalues[m]) + FastMath.abs(this.realEigenvalues[m + 1]);
                    if (FastMath.abs(e[m]) + delta == delta) {
                        break;
                    }
                    m++;
                }
                if (m != j) {
                    if (its == 30) {
                        throw new MaxCountExceededException(LocalizedFormats.CONVERGENCE_FAILED, Byte.valueOf(Ascii.RS), new Object[0]);
                    }
                    double[] dArr;
                    its++;
                    double q = (this.realEigenvalues[j + 1] - this.realEigenvalues[j]) / (2.0d * e[j]);
                    double t = FastMath.sqrt(1.0d + (q * q));
                    if (q < 0.0d) {
                        q = (this.realEigenvalues[m] - this.realEigenvalues[j]) + (e[j] / (q - t));
                    } else {
                        q = (this.realEigenvalues[m] - this.realEigenvalues[j]) + (e[j] / (q + t));
                    }
                    double u = 0.0d;
                    double s = 1.0d;
                    double c = 1.0d;
                    i = m - 1;
                    while (i >= j) {
                        p = s * e[i];
                        double h = c * e[i];
                        if (FastMath.abs(p) >= FastMath.abs(q)) {
                            c = q / p;
                            t = FastMath.sqrt((c * c) + 1.0d);
                            e[i + 1] = p * t;
                            s = 1.0d / t;
                            c *= s;
                        } else {
                            s = p / q;
                            t = FastMath.sqrt((s * s) + 1.0d);
                            e[i + 1] = q * t;
                            c = 1.0d / t;
                            s *= c;
                        }
                        if (e[i + 1] == 0.0d) {
                            dArr = this.realEigenvalues;
                            int i2 = i + 1;
                            dArr[i2] = dArr[i2] - u;
                            e[m] = 0.0d;
                            break;
                        }
                        q = this.realEigenvalues[i + 1] - u;
                        t = ((this.realEigenvalues[i] - q) * s) + ((2.0d * c) * h);
                        u = s * t;
                        this.realEigenvalues[i + 1] = q + u;
                        q = (c * t) - h;
                        for (int ia = 0; ia < n; ia++) {
                            p = z[ia][i + 1];
                            z[ia][i + 1] = (z[ia][i] * s) + (c * p);
                            z[ia][i] = (z[ia][i] * c) - (s * p);
                        }
                        i--;
                    }
                    if (t != 0.0d || i < j) {
                        dArr = this.realEigenvalues;
                        dArr[j] = dArr[j] - u;
                        e[j] = q;
                        e[m] = 0.0d;
                        continue;
                    }
                }
            } while (m != j);
            j++;
        }
        for (i = 0; i < n; i++) {
            int k = i;
            p = this.realEigenvalues[i];
            for (j = i + 1; j < n; j++) {
                if (this.realEigenvalues[j] > p) {
                    k = j;
                    p = this.realEigenvalues[j];
                }
            }
            if (k != i) {
                this.realEigenvalues[k] = this.realEigenvalues[i];
                this.realEigenvalues[i] = p;
                for (j = 0; j < n; j++) {
                    p = z[j][i];
                    z[j][i] = z[j][k];
                    z[j][k] = p;
                }
            }
        }
        maxAbsoluteValue = 0.0d;
        for (i = 0; i < n; i++) {
            if (FastMath.abs(this.realEigenvalues[i]) > maxAbsoluteValue) {
                maxAbsoluteValue = FastMath.abs(this.realEigenvalues[i]);
            }
        }
        if (maxAbsoluteValue != 0.0d) {
            for (i = 0; i < n; i++) {
                if (FastMath.abs(this.realEigenvalues[i]) < Precision.EPSILON * maxAbsoluteValue) {
                    this.realEigenvalues[i] = 0.0d;
                }
            }
        }
        this.eigenvectors = new ArrayRealVector[n];
        double[] tmp = new double[n];
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                tmp[j] = z[j][i];
            }
            this.eigenvectors[i] = new ArrayRealVector(tmp);
        }
    }

    private SchurTransformer transformToSchur(RealMatrix matrix) {
        SchurTransformer schurTransform = new SchurTransformer(matrix);
        double[][] matT = schurTransform.getT().getData();
        this.realEigenvalues = new double[matT.length];
        this.imagEigenvalues = new double[matT.length];
        int i = 0;
        while (i < this.realEigenvalues.length) {
            if (i == this.realEigenvalues.length - 1 || Precision.equals(matT[i + 1][i], 0.0d, EPSILON)) {
                this.realEigenvalues[i] = matT[i][i];
            } else {
                double x = matT[i + 1][i + 1];
                double p = 0.5d * (matT[i][i] - x);
                double z = FastMath.sqrt(FastMath.abs((p * p) + (matT[i + 1][i] * matT[i][i + 1])));
                this.realEigenvalues[i] = x + p;
                this.imagEigenvalues[i] = z;
                this.realEigenvalues[i + 1] = x + p;
                this.imagEigenvalues[i + 1] = -z;
                i++;
            }
            i++;
        }
        return schurTransform;
    }

    private Complex cdiv(double xr, double xi, double yr, double yi) {
        return new Complex(xr, xi).divide(new Complex(yr, yi));
    }

    private void findEigenVectorsFromSchur(SchurTransformer schur) throws MathArithmeticException {
        int i;
        int j;
        double[][] matrixT = schur.getT().getData();
        double[][] matrixP = schur.getP().getData();
        int n = matrixT.length;
        double norm = 0.0d;
        for (i = 0; i < n; i++) {
            for (j = FastMath.max(i - 1, 0); j < n; j++) {
                norm += FastMath.abs(matrixT[i][j]);
            }
        }
        if (Precision.equals(norm, 0.0d, EPSILON)) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
        }
        double r = 0.0d;
        double s = 0.0d;
        double z = 0.0d;
        for (int idx = n - 1; idx >= 0; idx--) {
            double p = this.realEigenvalues[idx];
            double q = this.imagEigenvalues[idx];
            int l;
            double w;
            double x;
            double y;
            double t;
            double[] dArr;
            if (Precision.equals(q, 0.0d)) {
                l = idx;
                matrixT[idx][idx] = 1.0d;
                for (i = idx - 1; i >= 0; i--) {
                    w = matrixT[i][i] - p;
                    r = 0.0d;
                    for (j = l; j <= idx; j++) {
                        r += matrixT[i][j] * matrixT[j][idx];
                    }
                    if (Precision.compareTo(this.imagEigenvalues[i], 0.0d, EPSILON) < 0) {
                        z = w;
                        s = r;
                    } else {
                        l = i;
                        if (!Precision.equals(this.imagEigenvalues[i], 0.0d)) {
                            x = matrixT[i][i + 1];
                            y = matrixT[i + 1][i];
                            t = ((x * s) - (z * r)) / (((this.realEigenvalues[i] - p) * (this.realEigenvalues[i] - p)) + (this.imagEigenvalues[i] * this.imagEigenvalues[i]));
                            matrixT[i][idx] = t;
                            if (FastMath.abs(x) > FastMath.abs(z)) {
                                matrixT[i + 1][idx] = ((-r) - (w * t)) / x;
                            } else {
                                matrixT[i + 1][idx] = ((-s) - (y * t)) / z;
                            }
                        } else if (w != 0.0d) {
                            matrixT[i][idx] = (-r) / w;
                        } else {
                            matrixT[i][idx] = (-r) / (Precision.EPSILON * norm);
                        }
                        t = FastMath.abs(matrixT[i][idx]);
                        if ((Precision.EPSILON * t) * t > 1.0d) {
                            for (j = i; j <= idx; j++) {
                                dArr = matrixT[j];
                                dArr[idx] = dArr[idx] / t;
                            }
                        }
                    }
                }
            } else if (q < 0.0d) {
                l = idx - 1;
                if (FastMath.abs(matrixT[idx][idx - 1]) > FastMath.abs(matrixT[idx - 1][idx])) {
                    matrixT[idx - 1][idx - 1] = q / matrixT[idx][idx - 1];
                    matrixT[idx - 1][idx] = (-(matrixT[idx][idx] - p)) / matrixT[idx][idx - 1];
                } else {
                    Complex result = cdiv(0.0d, -matrixT[idx - 1][idx], matrixT[idx - 1][idx - 1] - p, q);
                    matrixT[idx - 1][idx - 1] = result.getReal();
                    matrixT[idx - 1][idx] = result.getImaginary();
                }
                matrixT[idx][idx - 1] = 0.0d;
                matrixT[idx][idx] = 1.0d;
                for (i = idx - 2; i >= 0; i--) {
                    double ra = 0.0d;
                    double sa = 0.0d;
                    for (j = l; j <= idx; j++) {
                        ra += matrixT[i][j] * matrixT[j][idx - 1];
                        sa += matrixT[i][j] * matrixT[j][idx];
                    }
                    w = matrixT[i][i] - p;
                    if (Precision.compareTo(this.imagEigenvalues[i], 0.0d, EPSILON) < 0) {
                        z = w;
                        r = ra;
                        s = sa;
                    } else {
                        l = i;
                        Complex c;
                        if (Precision.equals(this.imagEigenvalues[i], 0.0d)) {
                            c = cdiv(-ra, -sa, w, q);
                            matrixT[i][idx - 1] = c.getReal();
                            matrixT[i][idx] = c.getImaginary();
                        } else {
                            x = matrixT[i][i + 1];
                            y = matrixT[i + 1][i];
                            double vr = (((this.realEigenvalues[i] - p) * (this.realEigenvalues[i] - p)) + (this.imagEigenvalues[i] * this.imagEigenvalues[i])) - (q * q);
                            double vi = ((this.realEigenvalues[i] - p) * 2.0d) * q;
                            if (Precision.equals(vr, 0.0d) && Precision.equals(vi, 0.0d)) {
                                vr = (Precision.EPSILON * norm) * ((((FastMath.abs(w) + FastMath.abs(q)) + FastMath.abs(x)) + FastMath.abs(y)) + FastMath.abs(z));
                            }
                            c = cdiv(((x * r) - (z * ra)) + (q * sa), ((x * s) - (z * sa)) - (q * ra), vr, vi);
                            matrixT[i][idx - 1] = c.getReal();
                            matrixT[i][idx] = c.getImaginary();
                            if (FastMath.abs(x) > FastMath.abs(z) + FastMath.abs(q)) {
                                matrixT[i + 1][idx - 1] = (((-ra) - (matrixT[i][idx - 1] * w)) + (matrixT[i][idx] * q)) / x;
                                matrixT[i + 1][idx] = (((-sa) - (matrixT[i][idx] * w)) - (matrixT[i][idx - 1] * q)) / x;
                            } else {
                                Complex c2 = cdiv((-r) - (matrixT[i][idx - 1] * y), (-s) - (matrixT[i][idx] * y), z, q);
                                matrixT[i + 1][idx - 1] = c2.getReal();
                                matrixT[i + 1][idx] = c2.getImaginary();
                            }
                        }
                        t = FastMath.max(FastMath.abs(matrixT[i][idx - 1]), FastMath.abs(matrixT[i][idx]));
                        if ((Precision.EPSILON * t) * t > 1.0d) {
                            for (j = i; j <= idx; j++) {
                                dArr = matrixT[j];
                                int i2 = idx - 1;
                                dArr[i2] = dArr[i2] / t;
                                dArr = matrixT[j];
                                dArr[idx] = dArr[idx] / t;
                            }
                        }
                    }
                }
            }
        }
        for (j = n - 1; j >= 0; j--) {
            for (i = 0; i <= n - 1; i++) {
                z = 0.0d;
                int k = 0;
                while (true) {
                    if (k > FastMath.min(j, n - 1)) {
                        break;
                    }
                    z += matrixP[i][k] * matrixT[k][j];
                    k++;
                }
                matrixP[i][j] = z;
            }
        }
        this.eigenvectors = new ArrayRealVector[n];
        double[] tmp = new double[n];
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                tmp[j] = matrixP[j][i];
            }
            this.eigenvectors[i] = new ArrayRealVector(tmp);
        }
    }
}
