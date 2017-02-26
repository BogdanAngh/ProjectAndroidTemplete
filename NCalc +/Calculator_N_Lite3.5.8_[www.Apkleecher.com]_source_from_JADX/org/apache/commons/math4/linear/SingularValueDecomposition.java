package org.apache.commons.math4.linear;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;
import org.matheclipse.core.interfaces.IExpr;

public class SingularValueDecomposition {
    private static final double EPS = 2.220446049250313E-16d;
    private static final double TINY = 1.6033346880071782E-291d;
    private RealMatrix cachedS;
    private final RealMatrix cachedU;
    private RealMatrix cachedUt;
    private final RealMatrix cachedV;
    private RealMatrix cachedVt;
    private final int m;
    private final int n;
    private final double[] singularValues;
    private final double tol;
    private final boolean transposed;

    class 1 extends DefaultRealMatrixPreservingVisitor {
        private final /* synthetic */ double[][] val$data;

        1(double[][] dArr) {
            this.val$data = dArr;
        }

        public void visit(int row, int column, double value) {
            this.val$data[row][column] = value / SingularValueDecomposition.this.singularValues[row];
        }
    }

    private static class Solver implements DecompositionSolver {
        private final boolean nonSingular;
        private final RealMatrix pseudoInverse;

        private Solver(double[] singularValues, RealMatrix uT, RealMatrix v, boolean nonSingular, double tol) {
            double[][] suT = uT.getData();
            for (int i = 0; i < singularValues.length; i++) {
                double a;
                if (singularValues[i] > tol) {
                    a = 1.0d / singularValues[i];
                } else {
                    a = 0.0d;
                }
                double[] suTi = suT[i];
                for (int j = 0; j < suTi.length; j++) {
                    suTi[j] = suTi[j] * a;
                }
            }
            this.pseudoInverse = v.multiply(new Array2DRowRealMatrix(suT, false));
            this.nonSingular = nonSingular;
        }

        public RealVector solve(RealVector b) {
            return this.pseudoInverse.operate(b);
        }

        public RealMatrix solve(RealMatrix b) {
            return this.pseudoInverse.multiply(b);
        }

        public boolean isNonSingular() {
            return this.nonSingular;
        }

        public RealMatrix getInverse() {
            return this.pseudoInverse;
        }
    }

    public SingularValueDecomposition(RealMatrix matrix) {
        double[][] A;
        double[] dArr;
        int j;
        double t;
        int i;
        if (matrix.getRowDimension() < matrix.getColumnDimension()) {
            this.transposed = true;
            A = matrix.transpose().getData();
            this.m = matrix.getColumnDimension();
            this.n = matrix.getRowDimension();
        } else {
            this.transposed = false;
            A = matrix.getData();
            this.m = matrix.getRowDimension();
            this.n = matrix.getColumnDimension();
        }
        this.singularValues = new double[this.n];
        double[][] U = (double[][]) Array.newInstance(Double.TYPE, new int[]{this.m, this.n});
        double[][] V = (double[][]) Array.newInstance(Double.TYPE, new int[]{this.n, this.n});
        double[] e = new double[this.n];
        double[] work = new double[this.m];
        int nct = FastMath.min(this.m - 1, this.n);
        int nrt = FastMath.max(0, this.n - 2);
        int k = 0;
        while (k < FastMath.max(nct, nrt)) {
            int i2;
            if (k < nct) {
                this.singularValues[k] = 0.0d;
                for (i2 = k; i2 < this.m; i2++) {
                    this.singularValues[k] = FastMath.hypot(this.singularValues[k], A[i2][k]);
                }
                if (this.singularValues[k] != 0.0d) {
                    if (A[k][k] < 0.0d) {
                        this.singularValues[k] = -this.singularValues[k];
                    }
                    for (i2 = k; i2 < this.m; i2++) {
                        dArr = A[i2];
                        dArr[k] = dArr[k] / this.singularValues[k];
                    }
                    dArr = A[k];
                    dArr[k] = dArr[k] + 1.0d;
                }
                this.singularValues[k] = -this.singularValues[k];
            }
            for (j = k + 1; j < this.n; j++) {
                if (k < nct) {
                    if (this.singularValues[k] != 0.0d) {
                        t = 0.0d;
                        for (i2 = k; i2 < this.m; i2++) {
                            t += A[i2][k] * A[i2][j];
                        }
                        t = (-t) / A[k][k];
                        for (i2 = k; i2 < this.m; i2++) {
                            dArr = A[i2];
                            dArr[j] = dArr[j] + (A[i2][k] * t);
                        }
                    }
                }
                e[j] = A[k][j];
            }
            if (k < nct) {
                for (i2 = k; i2 < this.m; i2++) {
                    U[i2][k] = A[i2][k];
                }
            }
            if (k < nrt) {
                e[k] = 0.0d;
                for (i2 = k + 1; i2 < this.n; i2++) {
                    e[k] = FastMath.hypot(e[k], e[i2]);
                }
                if (e[k] != 0.0d) {
                    if (e[k + 1] < 0.0d) {
                        e[k] = -e[k];
                    }
                    for (i2 = k + 1; i2 < this.n; i2++) {
                        e[i2] = e[i2] / e[k];
                    }
                    i = k + 1;
                    e[i] = e[i] + 1.0d;
                }
                e[k] = -e[k];
                if (k + 1 < this.m && e[k] != 0.0d) {
                    for (i2 = k + 1; i2 < this.m; i2++) {
                        work[i2] = 0.0d;
                    }
                    for (j = k + 1; j < this.n; j++) {
                        for (i2 = k + 1; i2 < this.m; i2++) {
                            work[i2] = work[i2] + (e[j] * A[i2][j]);
                        }
                    }
                    for (j = k + 1; j < this.n; j++) {
                        t = (-e[j]) / e[k + 1];
                        for (i2 = k + 1; i2 < this.m; i2++) {
                            dArr = A[i2];
                            dArr[j] = dArr[j] + (work[i2] * t);
                        }
                    }
                }
                for (i2 = k + 1; i2 < this.n; i2++) {
                    V[i2][k] = e[i2];
                }
            }
            k++;
        }
        int p = this.n;
        if (nct < this.n) {
            this.singularValues[nct] = A[nct][nct];
        }
        i = this.m;
        if (r0 < p) {
            this.singularValues[p - 1] = 0.0d;
        }
        if (nrt + 1 < p) {
            e[nrt] = A[nrt][p - 1];
        }
        e[p - 1] = 0.0d;
        for (j = nct; j < this.n; j++) {
            for (i2 = 0; i2 < this.m; i2++) {
                U[i2][j] = 0.0d;
            }
            U[j][j] = 1.0d;
        }
        for (k = nct - 1; k >= 0; k--) {
            if (this.singularValues[k] != 0.0d) {
                for (j = k + 1; j < this.n; j++) {
                    t = 0.0d;
                    for (i2 = k; i2 < this.m; i2++) {
                        t += U[i2][k] * U[i2][j];
                    }
                    t = (-t) / U[k][k];
                    for (i2 = k; i2 < this.m; i2++) {
                        dArr = U[i2];
                        dArr[j] = dArr[j] + (U[i2][k] * t);
                    }
                }
                for (i2 = k; i2 < this.m; i2++) {
                    U[i2][k] = -U[i2][k];
                }
                U[k][k] = 1.0d + U[k][k];
                for (i2 = 0; i2 < k - 1; i2++) {
                    U[i2][k] = 0.0d;
                }
            } else {
                for (i2 = 0; i2 < this.m; i2++) {
                    U[i2][k] = 0.0d;
                }
                U[k][k] = 1.0d;
            }
        }
        k = this.n - 1;
        while (k >= 0) {
            if (k < nrt && e[k] != 0.0d) {
                for (j = k + 1; j < this.n; j++) {
                    t = 0.0d;
                    for (i2 = k + 1; i2 < this.n; i2++) {
                        t += V[i2][k] * V[i2][j];
                    }
                    t = (-t) / V[k + 1][k];
                    for (i2 = k + 1; i2 < this.n; i2++) {
                        dArr = V[i2];
                        dArr[j] = dArr[j] + (V[i2][k] * t);
                    }
                }
            }
            for (i2 = 0; i2 < this.n; i2++) {
                V[i2][k] = 0.0d;
            }
            V[k][k] = 1.0d;
            k--;
        }
        int pp = p - 1;
        while (p > 0) {
            int ks;
            double c;
            double g;
            k = p - 2;
            while (k >= 0) {
                int kase;
                double f;
                double cs;
                double sn;
                double b;
                double shift;
                if (FastMath.abs(e[k]) <= TINY + (EPS * (FastMath.abs(this.singularValues[k]) + FastMath.abs(this.singularValues[k + 1])))) {
                    e[k] = 0.0d;
                    if (k != p - 2) {
                        kase = 4;
                    } else {
                        ks = p - 1;
                        while (ks >= k && ks != k) {
                            if (FastMath.abs(this.singularValues[ks]) > TINY + (EPS * ((ks == p ? FastMath.abs(e[ks]) : 0.0d) + (ks == k + 1 ? FastMath.abs(e[ks - 1]) : 0.0d)))) {
                                this.singularValues[ks] = 0.0d;
                            } else {
                                ks--;
                            }
                        }
                        if (ks != k) {
                            kase = 3;
                        } else if (ks == p - 1) {
                            kase = 1;
                        } else {
                            kase = 2;
                            k = ks;
                        }
                    }
                    k++;
                    switch (kase) {
                        case ValueServer.REPLAY_MODE /*1*/:
                            f = e[p - 2];
                            e[p - 2] = 0.0d;
                            j = p - 2;
                            while (j >= k) {
                                t = FastMath.hypot(this.singularValues[j], f);
                                cs = this.singularValues[j] / t;
                                sn = f / t;
                                this.singularValues[j] = t;
                                if (j != k) {
                                    f = (-sn) * e[j - 1];
                                    e[j - 1] = e[j - 1] * cs;
                                }
                                for (i2 = 0; i2 < this.n; i2++) {
                                    t = (V[i2][j] * cs) + (V[i2][p - 1] * sn);
                                    V[i2][p - 1] = ((-sn) * V[i2][j]) + (V[i2][p - 1] * cs);
                                    V[i2][j] = t;
                                }
                                j--;
                                break;
                            }
                            break;
                        case IExpr.DOUBLEID /*2*/:
                            f = e[k - 1];
                            e[k - 1] = 0.0d;
                            j = k;
                            while (j < p) {
                                t = FastMath.hypot(this.singularValues[j], f);
                                cs = this.singularValues[j] / t;
                                sn = f / t;
                                this.singularValues[j] = t;
                                f = (-sn) * e[j];
                                e[j] = e[j] * cs;
                                for (i2 = 0; i2 < this.m; i2++) {
                                    t = (U[i2][j] * cs) + (U[i2][k - 1] * sn);
                                    U[i2][k - 1] = ((-sn) * U[i2][j]) + (U[i2][k - 1] * cs);
                                    U[i2][j] = t;
                                }
                                j++;
                                break;
                            }
                            break;
                        case ValueServer.EXPONENTIAL_MODE /*3*/:
                            double scale = FastMath.max(FastMath.max(FastMath.max(FastMath.max(FastMath.abs(this.singularValues[p - 1]), FastMath.abs(this.singularValues[p - 2])), FastMath.abs(e[p - 2])), FastMath.abs(this.singularValues[k])), FastMath.abs(e[k]));
                            double sp = this.singularValues[p - 1] / scale;
                            double spm1 = this.singularValues[p - 2] / scale;
                            double epm1 = e[p - 2] / scale;
                            double sk = this.singularValues[k] / scale;
                            double ek = e[k] / scale;
                            b = (((spm1 + sp) * (spm1 - sp)) + (epm1 * epm1)) / 2.0d;
                            c = (sp * epm1) * (sp * epm1);
                            shift = 0.0d;
                            if (!(b == 0.0d && c == 0.0d)) {
                                shift = FastMath.sqrt((b * b) + c);
                                if (b < 0.0d) {
                                    shift = -shift;
                                }
                                shift = c / (b + shift);
                            }
                            f = ((sk + sp) * (sk - sp)) + shift;
                            g = sk * ek;
                            j = k;
                            while (j < p - 1) {
                                t = FastMath.hypot(f, g);
                                cs = f / t;
                                sn = g / t;
                                if (j != k) {
                                    e[j - 1] = t;
                                }
                                f = (this.singularValues[j] * cs) + (e[j] * sn);
                                e[j] = (e[j] * cs) - (this.singularValues[j] * sn);
                                g = sn * this.singularValues[j + 1];
                                this.singularValues[j + 1] = this.singularValues[j + 1] * cs;
                                for (i2 = 0; i2 < this.n; i2++) {
                                    t = (V[i2][j] * cs) + (V[i2][j + 1] * sn);
                                    V[i2][j + 1] = ((-sn) * V[i2][j]) + (V[i2][j + 1] * cs);
                                    V[i2][j] = t;
                                }
                                t = FastMath.hypot(f, g);
                                cs = f / t;
                                sn = g / t;
                                this.singularValues[j] = t;
                                f = (e[j] * cs) + (this.singularValues[j + 1] * sn);
                                this.singularValues[j + 1] = ((-sn) * e[j]) + (this.singularValues[j + 1] * cs);
                                g = sn * e[j + 1];
                                e[j + 1] = e[j + 1] * cs;
                                if (j < this.m - 1) {
                                    for (i2 = 0; i2 < this.m; i2++) {
                                        t = (U[i2][j] * cs) + (U[i2][j + 1] * sn);
                                        U[i2][j + 1] = ((-sn) * U[i2][j]) + (U[i2][j + 1] * cs);
                                        U[i2][j] = t;
                                    }
                                    break;
                                }
                                j++;
                                break;
                            }
                            e[p - 2] = f;
                            break;
                        default:
                            if (this.singularValues[k] <= 0.0d) {
                                this.singularValues[k] = this.singularValues[k] >= 0.0d ? -this.singularValues[k] : 0.0d;
                                for (i2 = 0; i2 <= pp; i2++) {
                                    V[i2][k] = -V[i2][k];
                                }
                            }
                            while (k < pp) {
                                if (this.singularValues[k] < this.singularValues[k + 1]) {
                                    p--;
                                    break;
                                }
                                t = this.singularValues[k];
                                this.singularValues[k] = this.singularValues[k + 1];
                                this.singularValues[k + 1] = t;
                                if (k < this.n - 1) {
                                    for (i2 = 0; i2 < this.n; i2++) {
                                        t = V[i2][k + 1];
                                        V[i2][k + 1] = V[i2][k];
                                        V[i2][k] = t;
                                    }
                                    break;
                                }
                                if (k < this.m - 1) {
                                    for (i2 = 0; i2 < this.m; i2++) {
                                        t = U[i2][k + 1];
                                        U[i2][k + 1] = U[i2][k];
                                        U[i2][k] = t;
                                    }
                                    break;
                                }
                                k++;
                            }
                            p--;
                    }
                } else {
                    k--;
                }
            }
            if (k != p - 2) {
                ks = p - 1;
                while (ks >= k) {
                    if (ks == p) {
                    }
                    if (ks == k + 1) {
                    }
                    if (FastMath.abs(this.singularValues[ks]) > TINY + (EPS * ((ks == p ? FastMath.abs(e[ks]) : 0.0d) + (ks == k + 1 ? FastMath.abs(e[ks - 1]) : 0.0d)))) {
                        ks--;
                    } else {
                        this.singularValues[ks] = 0.0d;
                        if (ks != k) {
                            kase = 3;
                        } else if (ks == p - 1) {
                            kase = 2;
                            k = ks;
                        } else {
                            kase = 1;
                        }
                    }
                }
                if (ks != k) {
                    kase = 3;
                } else if (ks == p - 1) {
                    kase = 1;
                } else {
                    kase = 2;
                    k = ks;
                }
            } else {
                kase = 4;
            }
            k++;
            switch (kase) {
                case ValueServer.REPLAY_MODE /*1*/:
                    f = e[p - 2];
                    e[p - 2] = 0.0d;
                    j = p - 2;
                    while (j >= k) {
                        t = FastMath.hypot(this.singularValues[j], f);
                        cs = this.singularValues[j] / t;
                        sn = f / t;
                        this.singularValues[j] = t;
                        if (j != k) {
                            f = (-sn) * e[j - 1];
                            e[j - 1] = e[j - 1] * cs;
                        }
                        for (i2 = 0; i2 < this.n; i2++) {
                            t = (V[i2][j] * cs) + (V[i2][p - 1] * sn);
                            V[i2][p - 1] = ((-sn) * V[i2][j]) + (V[i2][p - 1] * cs);
                            V[i2][j] = t;
                        }
                        j--;
                        break;
                    }
                    break;
                case IExpr.DOUBLEID /*2*/:
                    f = e[k - 1];
                    e[k - 1] = 0.0d;
                    j = k;
                    while (j < p) {
                        t = FastMath.hypot(this.singularValues[j], f);
                        cs = this.singularValues[j] / t;
                        sn = f / t;
                        this.singularValues[j] = t;
                        f = (-sn) * e[j];
                        e[j] = e[j] * cs;
                        for (i2 = 0; i2 < this.m; i2++) {
                            t = (U[i2][j] * cs) + (U[i2][k - 1] * sn);
                            U[i2][k - 1] = ((-sn) * U[i2][j]) + (U[i2][k - 1] * cs);
                            U[i2][j] = t;
                        }
                        j++;
                        break;
                    }
                    break;
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    double scale2 = FastMath.max(FastMath.max(FastMath.max(FastMath.max(FastMath.abs(this.singularValues[p - 1]), FastMath.abs(this.singularValues[p - 2])), FastMath.abs(e[p - 2])), FastMath.abs(this.singularValues[k])), FastMath.abs(e[k]));
                    double sp2 = this.singularValues[p - 1] / scale2;
                    double spm12 = this.singularValues[p - 2] / scale2;
                    double epm12 = e[p - 2] / scale2;
                    double sk2 = this.singularValues[k] / scale2;
                    double ek2 = e[k] / scale2;
                    b = (((spm12 + sp2) * (spm12 - sp2)) + (epm12 * epm12)) / 2.0d;
                    c = (sp2 * epm12) * (sp2 * epm12);
                    shift = 0.0d;
                    shift = FastMath.sqrt((b * b) + c);
                    if (b < 0.0d) {
                        shift = -shift;
                    }
                    shift = c / (b + shift);
                    f = ((sk2 + sp2) * (sk2 - sp2)) + shift;
                    g = sk2 * ek2;
                    j = k;
                    while (j < p - 1) {
                        t = FastMath.hypot(f, g);
                        cs = f / t;
                        sn = g / t;
                        if (j != k) {
                            e[j - 1] = t;
                        }
                        f = (this.singularValues[j] * cs) + (e[j] * sn);
                        e[j] = (e[j] * cs) - (this.singularValues[j] * sn);
                        g = sn * this.singularValues[j + 1];
                        this.singularValues[j + 1] = this.singularValues[j + 1] * cs;
                        for (i2 = 0; i2 < this.n; i2++) {
                            t = (V[i2][j] * cs) + (V[i2][j + 1] * sn);
                            V[i2][j + 1] = ((-sn) * V[i2][j]) + (V[i2][j + 1] * cs);
                            V[i2][j] = t;
                        }
                        t = FastMath.hypot(f, g);
                        cs = f / t;
                        sn = g / t;
                        this.singularValues[j] = t;
                        f = (e[j] * cs) + (this.singularValues[j + 1] * sn);
                        this.singularValues[j + 1] = ((-sn) * e[j]) + (this.singularValues[j + 1] * cs);
                        g = sn * e[j + 1];
                        e[j + 1] = e[j + 1] * cs;
                        if (j < this.m - 1) {
                            for (i2 = 0; i2 < this.m; i2++) {
                                t = (U[i2][j] * cs) + (U[i2][j + 1] * sn);
                                U[i2][j + 1] = ((-sn) * U[i2][j]) + (U[i2][j + 1] * cs);
                                U[i2][j] = t;
                            }
                            break;
                        }
                        j++;
                        break;
                    }
                    e[p - 2] = f;
                    break;
                default:
                    if (this.singularValues[k] <= 0.0d) {
                        if (this.singularValues[k] >= 0.0d) {
                        }
                        this.singularValues[k] = this.singularValues[k] >= 0.0d ? -this.singularValues[k] : 0.0d;
                        for (i2 = 0; i2 <= pp; i2++) {
                            V[i2][k] = -V[i2][k];
                        }
                    }
                    while (k < pp) {
                        if (this.singularValues[k] < this.singularValues[k + 1]) {
                            p--;
                            break;
                        }
                        t = this.singularValues[k];
                        this.singularValues[k] = this.singularValues[k + 1];
                        this.singularValues[k + 1] = t;
                        if (k < this.n - 1) {
                            for (i2 = 0; i2 < this.n; i2++) {
                                t = V[i2][k + 1];
                                V[i2][k + 1] = V[i2][k];
                                V[i2][k] = t;
                            }
                            break;
                        }
                        if (k < this.m - 1) {
                            for (i2 = 0; i2 < this.m; i2++) {
                                t = U[i2][k + 1];
                                U[i2][k + 1] = U[i2][k];
                                U[i2][k] = t;
                            }
                            break;
                        }
                        k++;
                    }
                    p--;
            }
        }
        this.tol = FastMath.max((((double) this.m) * this.singularValues[0]) * EPS, FastMath.sqrt(Precision.SAFE_MIN));
        if (this.transposed) {
            this.cachedU = MatrixUtils.createRealMatrix(V);
            this.cachedV = MatrixUtils.createRealMatrix(U);
            return;
        }
        this.cachedU = MatrixUtils.createRealMatrix(U);
        this.cachedV = MatrixUtils.createRealMatrix(V);
    }

    public RealMatrix getU() {
        return this.cachedU;
    }

    public RealMatrix getUT() {
        if (this.cachedUt == null) {
            this.cachedUt = getU().transpose();
        }
        return this.cachedUt;
    }

    public RealMatrix getS() {
        if (this.cachedS == null) {
            this.cachedS = MatrixUtils.createRealDiagonalMatrix(this.singularValues);
        }
        return this.cachedS;
    }

    public double[] getSingularValues() {
        return (double[]) this.singularValues.clone();
    }

    public RealMatrix getV() {
        return this.cachedV;
    }

    public RealMatrix getVT() {
        if (this.cachedVt == null) {
            this.cachedVt = getV().transpose();
        }
        return this.cachedVt;
    }

    public RealMatrix getCovariance(double minSingularValue) {
        int p = this.singularValues.length;
        int dimension = 0;
        while (dimension < p && this.singularValues[dimension] >= minSingularValue) {
            dimension++;
        }
        if (dimension == 0) {
            throw new NumberIsTooLargeException(LocalizedFormats.TOO_LARGE_CUTOFF_SINGULAR_VALUE, Double.valueOf(minSingularValue), Double.valueOf(this.singularValues[0]), true);
        }
        double[][] data = (double[][]) Array.newInstance(Double.TYPE, new int[]{dimension, p});
        getVT().walkInOptimizedOrder(new 1(data), 0, dimension - 1, 0, p - 1);
        RealMatrix jv = new Array2DRowRealMatrix(data, false);
        return jv.transpose().multiply(jv);
    }

    public double getNorm() {
        return this.singularValues[0];
    }

    public double getConditionNumber() {
        return this.singularValues[0] / this.singularValues[this.n - 1];
    }

    public double getInverseConditionNumber() {
        return this.singularValues[this.n - 1] / this.singularValues[0];
    }

    public int getRank() {
        int r = 0;
        for (double d : this.singularValues) {
            if (d > this.tol) {
                r++;
            }
        }
        return r;
    }

    public DecompositionSolver getSolver() {
        return new Solver(getUT(), getV(), getRank() == this.m, this.tol, null);
    }
}
