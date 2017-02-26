package org.apache.commons.math4.stat.regression;

import java.util.Arrays;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.Precision;

public class MillerUpdatingRegression implements UpdatingMultipleLinearRegression {
    private final double[] d;
    private final double epsilon;
    private final boolean hasIntercept;
    private final boolean[] lindep;
    private long nobs;
    private final int nvars;
    private final double[] r;
    private final double[] rhs;
    private final double[] rss;
    private boolean rss_set;
    private double sserr;
    private double sumsqy;
    private double sumy;
    private final double[] tol;
    private boolean tol_set;
    private final int[] vorder;
    private final double[] work_sing;
    private final double[] work_tolset;
    private final double[] x_sing;

    private MillerUpdatingRegression() {
        this(-1, false, Double.NaN);
    }

    public MillerUpdatingRegression(int numberOfVariables, boolean includeConstant, double errorTolerance) throws ModelSpecificationException {
        this.nobs = 0;
        this.sserr = 0.0d;
        this.rss_set = false;
        this.tol_set = false;
        this.sumy = 0.0d;
        this.sumsqy = 0.0d;
        if (numberOfVariables < 1) {
            throw new ModelSpecificationException(LocalizedFormats.NO_REGRESSORS, new Object[0]);
        }
        if (includeConstant) {
            this.nvars = numberOfVariables + 1;
        } else {
            this.nvars = numberOfVariables;
        }
        this.hasIntercept = includeConstant;
        this.nobs = 0;
        this.d = new double[this.nvars];
        this.rhs = new double[this.nvars];
        this.r = new double[((this.nvars * (this.nvars - 1)) / 2)];
        this.tol = new double[this.nvars];
        this.rss = new double[this.nvars];
        this.vorder = new int[this.nvars];
        this.x_sing = new double[this.nvars];
        this.work_sing = new double[this.nvars];
        this.work_tolset = new double[this.nvars];
        this.lindep = new boolean[this.nvars];
        for (int i = 0; i < this.nvars; i++) {
            this.vorder[i] = i;
        }
        if (errorTolerance > 0.0d) {
            this.epsilon = errorTolerance;
        } else {
            this.epsilon = -errorTolerance;
        }
    }

    public MillerUpdatingRegression(int numberOfVariables, boolean includeConstant) throws ModelSpecificationException {
        this(numberOfVariables, includeConstant, Precision.EPSILON);
    }

    public boolean hasIntercept() {
        return this.hasIntercept;
    }

    public long getN() {
        return this.nobs;
    }

    public void addObservation(double[] x, double y) throws ModelSpecificationException {
        if ((this.hasIntercept || x.length == this.nvars) && (!this.hasIntercept || x.length + 1 == this.nvars)) {
            if (this.hasIntercept) {
                double[] tmp = new double[(x.length + 1)];
                System.arraycopy(x, 0, tmp, 1, x.length);
                tmp[0] = 1.0d;
                include(tmp, 1.0d, y);
            } else {
                include(MathArrays.copyOf(x, x.length), 1.0d, y);
            }
            this.nobs++;
            return;
        }
        throw new ModelSpecificationException(LocalizedFormats.INVALID_REGRESSION_OBSERVATION, Integer.valueOf(x.length), Integer.valueOf(this.nvars));
    }

    public void addObservations(double[][] x, double[] y) throws ModelSpecificationException {
        int i = 0;
        if (x == null || y == null || x.length != y.length) {
            Localizable localizable = LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(x == null ? 0 : x.length);
            if (y != null) {
                i = y.length;
            }
            objArr[1] = Integer.valueOf(i);
            throw new ModelSpecificationException(localizable, objArr);
        } else if (x.length == 0) {
            throw new ModelSpecificationException(LocalizedFormats.NO_DATA, new Object[0]);
        } else if (x[0].length + 1 > x.length) {
            throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, Integer.valueOf(x.length), Integer.valueOf(x[0].length));
        } else {
            for (int i2 = 0; i2 < x.length; i2++) {
                addObservation(x[i2], y[i2]);
            }
        }
    }

    private void include(double[] x, double wi, double yi) {
        int nextr = 0;
        double w = wi;
        double y = yi;
        this.rss_set = false;
        this.sumy = smartAdd(yi, this.sumy);
        this.sumsqy = smartAdd(this.sumsqy, yi * yi);
        int i = 0;
        while (i < x.length) {
            if (w != 0.0d) {
                double xi = x[i];
                if (xi == 0.0d) {
                    nextr += (this.nvars - i) - 1;
                } else {
                    double dpi;
                    double xk;
                    double di = this.d[i];
                    double wxi = w * xi;
                    double _w = w;
                    if (di != 0.0d) {
                        dpi = smartAdd(di, wxi * xi);
                        if (FastMath.abs((wxi * xi) / di) > Precision.EPSILON) {
                            w = (di * w) / dpi;
                        }
                    } else {
                        dpi = wxi * xi;
                        w = 0.0d;
                    }
                    this.d[i] = dpi;
                    for (int k = i + 1; k < this.nvars; k++) {
                        xk = x[k];
                        x[k] = smartAdd(xk, (-xi) * this.r[nextr]);
                        if (di != 0.0d) {
                            this.r[nextr] = smartAdd(this.r[nextr] * di, (_w * xi) * xk) / dpi;
                        } else {
                            this.r[nextr] = xk / xi;
                        }
                        nextr++;
                    }
                    xk = y;
                    y = smartAdd(xk, (-xi) * this.rhs[i]);
                    if (di != 0.0d) {
                        this.rhs[i] = smartAdd(this.rhs[i] * di, wxi * xk) / dpi;
                    } else {
                        this.rhs[i] = xk / xi;
                    }
                }
                i++;
            } else {
                return;
            }
        }
        this.sserr = smartAdd(this.sserr, (w * y) * y);
    }

    private double smartAdd(double a, double b) {
        double _a = FastMath.abs(a);
        double _b = FastMath.abs(b);
        if (_a <= _b) {
            return _a > _b * Precision.EPSILON ? a + b : b;
        } else {
            if (_b > _a * Precision.EPSILON) {
                return a + b;
            }
            return a;
        }
    }

    public void clear() {
        Arrays.fill(this.d, 0.0d);
        Arrays.fill(this.rhs, 0.0d);
        Arrays.fill(this.r, 0.0d);
        Arrays.fill(this.tol, 0.0d);
        Arrays.fill(this.rss, 0.0d);
        Arrays.fill(this.work_tolset, 0.0d);
        Arrays.fill(this.work_sing, 0.0d);
        Arrays.fill(this.x_sing, 0.0d);
        Arrays.fill(this.lindep, false);
        for (int i = 0; i < this.nvars; i++) {
            this.vorder[i] = i;
        }
        this.nobs = 0;
        this.sserr = 0.0d;
        this.sumy = 0.0d;
        this.sumsqy = 0.0d;
        this.rss_set = false;
        this.tol_set = false;
    }

    private void tolset() {
        double eps = this.epsilon;
        for (int i = 0; i < this.nvars; i++) {
            this.work_tolset[i] = FastMath.sqrt(this.d[i]);
        }
        this.tol[0] = this.work_tolset[0] * eps;
        for (int col = 1; col < this.nvars; col++) {
            int pos = col - 1;
            double total = this.work_tolset[col];
            for (int row = 0; row < col; row++) {
                total += FastMath.abs(this.r[pos]) * this.work_tolset[row];
                pos += (this.nvars - row) - 2;
            }
            this.tol[col] = eps * total;
        }
        this.tol_set = true;
    }

    private double[] regcf(int nreq) throws ModelSpecificationException {
        if (nreq < 1) {
            throw new ModelSpecificationException(LocalizedFormats.NO_REGRESSORS, new Object[0]);
        } else if (nreq > this.nvars) {
            throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, Integer.valueOf(nreq), Integer.valueOf(this.nvars));
        } else {
            int i;
            if (!this.tol_set) {
                tolset();
            }
            double[] ret = new double[nreq];
            boolean rankProblem = false;
            for (i = nreq - 1; i > -1; i--) {
                if (FastMath.sqrt(this.d[i]) < this.tol[i]) {
                    ret[i] = 0.0d;
                    this.d[i] = 0.0d;
                    rankProblem = true;
                } else {
                    ret[i] = this.rhs[i];
                    int nextr = ((((this.nvars + this.nvars) - i) - 1) * i) / 2;
                    for (int j = i + 1; j < nreq; j++) {
                        ret[i] = smartAdd(ret[i], (-this.r[nextr]) * ret[j]);
                        nextr++;
                    }
                }
            }
            if (rankProblem) {
                for (i = 0; i < nreq; i++) {
                    if (this.lindep[i]) {
                        ret[i] = Double.NaN;
                    }
                }
            }
            return ret;
        }
    }

    private void singcheck() {
        for (int i = 0; i < this.nvars; i++) {
            this.work_sing[i] = FastMath.sqrt(this.d[i]);
        }
        for (int col = 0; col < this.nvars; col++) {
            double temp = this.tol[col];
            int pos = col - 1;
            for (int row = 0; row < col - 1; row++) {
                if (FastMath.abs(this.r[pos]) * this.work_sing[row] < temp) {
                    this.r[pos] = 0.0d;
                }
                pos += (this.nvars - row) - 2;
            }
            this.lindep[col] = false;
            if (this.work_sing[col] < temp) {
                this.lindep[col] = true;
                if (col < this.nvars - 1) {
                    Arrays.fill(this.x_sing, 0.0d);
                    int _pi = ((((this.nvars + this.nvars) - col) - 1) * col) / 2;
                    int _xi = col + 1;
                    while (_xi < this.nvars) {
                        this.x_sing[_xi] = this.r[_pi];
                        this.r[_pi] = 0.0d;
                        _xi++;
                        _pi++;
                    }
                    double y = this.rhs[col];
                    double weight = this.d[col];
                    this.d[col] = 0.0d;
                    this.rhs[col] = 0.0d;
                    include(this.x_sing, weight, y);
                } else {
                    this.sserr += (this.d[col] * this.rhs[col]) * this.rhs[col];
                }
            }
        }
    }

    private void ss() {
        double total = this.sserr;
        this.rss[this.nvars - 1] = this.sserr;
        for (int i = this.nvars - 1; i > 0; i--) {
            total += (this.d[i] * this.rhs[i]) * this.rhs[i];
            this.rss[i - 1] = total;
        }
        this.rss_set = true;
    }

    private double[] cov(int nreq) {
        if (this.nobs <= ((long) nreq)) {
            return null;
        }
        double rnk = 0.0d;
        for (int i = 0; i < nreq; i++) {
            if (!this.lindep[i]) {
                rnk += 1.0d;
            }
        }
        double var = this.rss[nreq - 1] / (((double) this.nobs) - rnk);
        double[] rinv = new double[(((nreq - 1) * nreq) / 2)];
        inverse(rinv, nreq);
        double[] covmat = new double[(((nreq + 1) * nreq) / 2)];
        Arrays.fill(covmat, Double.NaN);
        int start = 0;
        for (int row = 0; row < nreq; row++) {
            int pos2 = start;
            if (!this.lindep[row]) {
                for (int col = row; col < nreq; col++) {
                    if (this.lindep[col]) {
                        pos2 += (nreq - col) - 1;
                    } else {
                        double total;
                        int pos1 = (start + col) - row;
                        if (row == col) {
                            total = 1.0d / this.d[col];
                        } else {
                            total = rinv[pos1 - 1] / this.d[col];
                        }
                        for (int k = col + 1; k < nreq; k++) {
                            if (!this.lindep[k]) {
                                total += (rinv[pos1] * rinv[pos2]) / this.d[k];
                            }
                            pos1++;
                            pos2++;
                        }
                        covmat[(((col + 1) * col) / 2) + row] = total * var;
                    }
                }
            }
            start += (nreq - row) - 1;
        }
        return covmat;
    }

    private void inverse(double[] rinv, int nreq) {
        int pos = (((nreq - 1) * nreq) / 2) - 1;
        Arrays.fill(rinv, Double.NaN);
        for (int row = nreq - 1; row > 0; row--) {
            if (this.lindep[row]) {
                pos -= nreq - row;
            } else {
                int start = ((row - 1) * ((this.nvars + this.nvars) - row)) / 2;
                for (int col = nreq; col > row; col--) {
                    int pos1 = start;
                    int pos2 = pos;
                    double total = 0.0d;
                    for (int k = row; k < col - 1; k++) {
                        pos2 += (nreq - k) - 1;
                        if (!this.lindep[k]) {
                            total += (-this.r[pos1]) * rinv[pos2];
                        }
                        pos1++;
                    }
                    rinv[pos] = total - this.r[pos1];
                    pos--;
                }
            }
        }
    }

    public double[] getPartialCorrelations(int in) {
        double[] output = new double[((((this.nvars - in) + 1) * (this.nvars - in)) / 2)];
        int rms_off = -in;
        int wrk_off = -(in + 1);
        double[] rms = new double[(this.nvars - in)];
        double[] work = new double[((this.nvars - in) - 1)];
        int offXX = ((this.nvars - in) * ((this.nvars - in) - 1)) / 2;
        if (in < -1 || in >= this.nvars) {
            return null;
        }
        int pos;
        int row;
        int nvm = this.nvars - 1;
        int base_pos = this.r.length - (((nvm - in) * ((nvm - in) + 1)) / 2);
        if (this.d[in] > 0.0d) {
            rms[in + rms_off] = 1.0d / FastMath.sqrt(this.d[in]);
        }
        int col = in + 1;
        while (true) {
            int i = this.nvars;
            if (col >= r0) {
                break;
            }
            pos = ((base_pos + col) - 1) - in;
            double sumxx = this.d[col];
            for (row = in; row < col; row++) {
                sumxx += (this.d[row] * this.r[pos]) * this.r[pos];
                pos += (this.nvars - row) - 2;
            }
            if (sumxx > 0.0d) {
                rms[col + rms_off] = 1.0d / FastMath.sqrt(sumxx);
            } else {
                rms[col + rms_off] = 0.0d;
            }
            col++;
        }
        double sumyy = this.sserr;
        row = in;
        while (true) {
            i = this.nvars;
            if (row >= r0) {
                break;
            }
            sumyy += (this.d[row] * this.rhs[row]) * this.rhs[row];
            row++;
        }
        if (sumyy > 0.0d) {
            sumyy = 1.0d / FastMath.sqrt(sumyy);
        }
        pos = 0;
        int col1 = in;
        while (true) {
            i = this.nvars;
            if (col1 >= r0) {
                return output;
            }
            int pos2;
            int col2;
            double sumxy = 0.0d;
            Arrays.fill(work, 0.0d);
            int pos1 = ((base_pos + col1) - in) - 1;
            for (row = in; row < col1; row++) {
                pos2 = pos1 + 1;
                col2 = col1 + 1;
                while (true) {
                    i = this.nvars;
                    if (col2 >= r0) {
                        break;
                    }
                    i = col2 + wrk_off;
                    work[i] = work[i] + ((this.d[row] * this.r[pos1]) * this.r[pos2]);
                    pos2++;
                    col2++;
                }
                sumxy += (this.d[row] * this.r[pos1]) * this.rhs[row];
                pos1 += (this.nvars - row) - 2;
            }
            pos2 = pos1 + 1;
            col2 = col1 + 1;
            while (true) {
                i = this.nvars;
                if (col2 >= r0) {
                    break;
                }
                i = col2 + wrk_off;
                work[i] = work[i] + (this.d[col1] * this.r[pos2]);
                pos2++;
                output[(((((col2 - 1) - in) * (col2 - in)) / 2) + col1) - in] = (work[col2 + wrk_off] * rms[col1 + rms_off]) * rms[col2 + rms_off];
                pos++;
                col2++;
            }
            output[(col1 + rms_off) + offXX] = (rms[col1 + rms_off] * (sumxy + (this.d[col1] * this.rhs[col1]))) * sumyy;
            col1++;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void vmove(int r39, int r40) {
        /*
        r38 = this;
        r7 = 0;
        r0 = r39;
        r1 = r40;
        if (r0 != r1) goto L_0x0008;
    L_0x0007:
        return;
    L_0x0008:
        r0 = r38;
        r0 = r0.rss_set;
        r29 = r0;
        if (r29 != 0) goto L_0x0013;
    L_0x0010:
        r38.ss();
    L_0x0013:
        r11 = 0;
        r0 = r39;
        r1 = r40;
        if (r0 >= r1) goto L_0x01d5;
    L_0x001a:
        r20 = r39;
        r22 = 1;
        r11 = r40 - r39;
    L_0x0020:
        r23 = r20;
        r21 = 0;
    L_0x0024:
        r0 = r21;
        if (r0 >= r11) goto L_0x0007;
    L_0x0028:
        r0 = r38;
        r0 = r0.nvars;
        r29 = r0;
        r0 = r38;
        r0 = r0.nvars;
        r32 = r0;
        r29 = r29 + r32;
        r29 = r29 - r23;
        r29 = r29 + -1;
        r29 = r29 * r23;
        r24 = r29 / 2;
        r0 = r38;
        r0 = r0.nvars;
        r29 = r0;
        r29 = r29 + r24;
        r29 = r29 - r23;
        r25 = r29 + -1;
        r26 = r23 + 1;
        r0 = r38;
        r0 = r0.d;
        r29 = r0;
        r12 = r29[r23];
        r0 = r38;
        r0 = r0.d;
        r29 = r0;
        r16 = r29[r26];
        r0 = r38;
        r0 = r0.epsilon;
        r32 = r0;
        r29 = (r12 > r32 ? 1 : (r12 == r32 ? 0 : -1));
        if (r29 > 0) goto L_0x0070;
    L_0x0066:
        r0 = r38;
        r0 = r0.epsilon;
        r32 = r0;
        r29 = (r16 > r32 ? 1 : (r16 == r32 ? 0 : -1));
        if (r29 <= 0) goto L_0x0155;
    L_0x0070:
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r2 = r29[r24];
        r32 = org.apache.commons.math4.util.FastMath.abs(r2);
        r34 = org.apache.commons.math4.util.FastMath.sqrt(r12);
        r32 = r32 * r34;
        r0 = r38;
        r0 = r0.tol;
        r29 = r0;
        r34 = r29[r26];
        r29 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r29 >= 0) goto L_0x0090;
    L_0x008e:
        r2 = 0;
    L_0x0090:
        r0 = r38;
        r0 = r0.epsilon;
        r32 = r0;
        r29 = (r12 > r32 ? 1 : (r12 == r32 ? 0 : -1));
        if (r29 < 0) goto L_0x00a8;
    L_0x009a:
        r32 = org.apache.commons.math4.util.FastMath.abs(r2);
        r0 = r38;
        r0 = r0.epsilon;
        r34 = r0;
        r29 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r29 >= 0) goto L_0x0205;
    L_0x00a8:
        r0 = r38;
        r0 = r0.d;
        r29 = r0;
        r29[r23] = r16;
        r0 = r38;
        r0 = r0.d;
        r29 = r0;
        r29[r26] = r12;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r32 = 0;
        r29[r24] = r32;
        r10 = r23 + 2;
    L_0x00c4:
        r0 = r38;
        r0 = r0.nvars;
        r29 = r0;
        r0 = r29;
        if (r10 < r0) goto L_0x01dd;
    L_0x00ce:
        r0 = r38;
        r0 = r0.rhs;
        r29 = r0;
        r2 = r29[r23];
        r0 = r38;
        r0 = r0.rhs;
        r29 = r0;
        r0 = r38;
        r0 = r0.rhs;
        r32 = r0;
        r32 = r32[r26];
        r29[r23] = r32;
        r0 = r38;
        r0 = r0.rhs;
        r29 = r0;
        r29[r26] = r2;
        r7 = 1;
    L_0x00ef:
        if (r7 != 0) goto L_0x0155;
    L_0x00f1:
        r32 = r12 * r2;
        r32 = r32 * r2;
        r14 = r16 + r32;
        r8 = r16 / r14;
        r32 = r2 * r12;
        r30 = r32 / r14;
        r18 = r12 * r8;
        r0 = r38;
        r0 = r0.d;
        r29 = r0;
        r29[r23] = r14;
        r0 = r38;
        r0 = r0.d;
        r29 = r0;
        r29[r26] = r18;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r29[r24] = r30;
        r10 = r23 + 2;
    L_0x0119:
        r0 = r38;
        r0 = r0.nvars;
        r29 = r0;
        r0 = r29;
        if (r10 < r0) goto L_0x0257;
    L_0x0123:
        r0 = r38;
        r0 = r0.rhs;
        r29 = r0;
        r4 = r29[r23];
        r0 = r38;
        r0 = r0.rhs;
        r29 = r0;
        r0 = r38;
        r0 = r0.rhs;
        r32 = r0;
        r32 = r32[r26];
        r32 = r32 * r8;
        r34 = r30 * r4;
        r32 = r32 + r34;
        r29[r23] = r32;
        r0 = r38;
        r0 = r0.rhs;
        r29 = r0;
        r0 = r38;
        r0 = r0.rhs;
        r32 = r0;
        r32 = r32[r26];
        r32 = r32 * r2;
        r32 = r4 - r32;
        r29[r26] = r32;
    L_0x0155:
        if (r23 <= 0) goto L_0x0161;
    L_0x0157:
        r27 = r23;
        r28 = 0;
    L_0x015b:
        r0 = r28;
        r1 = r23;
        if (r0 < r1) goto L_0x0291;
    L_0x0161:
        r0 = r38;
        r0 = r0.vorder;
        r29 = r0;
        r24 = r29[r23];
        r0 = r38;
        r0 = r0.vorder;
        r29 = r0;
        r0 = r38;
        r0 = r0.vorder;
        r32 = r0;
        r32 = r32[r26];
        r29[r23] = r32;
        r0 = r38;
        r0 = r0.vorder;
        r29 = r0;
        r29[r26] = r24;
        r0 = r38;
        r0 = r0.tol;
        r29 = r0;
        r2 = r29[r23];
        r0 = r38;
        r0 = r0.tol;
        r29 = r0;
        r0 = r38;
        r0 = r0.tol;
        r32 = r0;
        r32 = r32[r26];
        r29[r23] = r32;
        r0 = r38;
        r0 = r0.tol;
        r29 = r0;
        r29[r26] = r2;
        r0 = r38;
        r0 = r0.rss;
        r29 = r0;
        r0 = r38;
        r0 = r0.rss;
        r32 = r0;
        r32 = r32[r26];
        r0 = r38;
        r0 = r0.d;
        r34 = r0;
        r34 = r34[r26];
        r0 = r38;
        r0 = r0.rhs;
        r36 = r0;
        r36 = r36[r26];
        r34 = r34 * r36;
        r0 = r38;
        r0 = r0.rhs;
        r36 = r0;
        r36 = r36[r26];
        r34 = r34 * r36;
        r32 = r32 + r34;
        r29[r23] = r32;
        r23 = r23 + r22;
        r21 = r21 + 1;
        goto L_0x0024;
    L_0x01d5:
        r20 = r39 + -1;
        r22 = -1;
        r11 = r39 - r40;
        goto L_0x0020;
    L_0x01dd:
        r24 = r24 + 1;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r2 = r29[r24];
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r0 = r38;
        r0 = r0.r;
        r32 = r0;
        r32 = r32[r25];
        r29[r24] = r32;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r29[r25] = r2;
        r25 = r25 + 1;
        r10 = r10 + 1;
        goto L_0x00c4;
    L_0x0205:
        r0 = r38;
        r0 = r0.epsilon;
        r32 = r0;
        r29 = (r16 > r32 ? 1 : (r16 == r32 ? 0 : -1));
        if (r29 >= 0) goto L_0x00ef;
    L_0x020f:
        r0 = r38;
        r0 = r0.d;
        r29 = r0;
        r32 = r12 * r2;
        r32 = r32 * r2;
        r29[r23] = r32;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r32 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r32 = r32 / r2;
        r29[r24] = r32;
        r6 = r24 + 1;
    L_0x0229:
        r0 = r38;
        r0 = r0.nvars;
        r29 = r0;
        r29 = r29 + r24;
        r29 = r29 - r23;
        r29 = r29 + -1;
        r0 = r29;
        if (r6 < r0) goto L_0x0248;
    L_0x0239:
        r0 = r38;
        r0 = r0.rhs;
        r29 = r0;
        r32 = r29[r23];
        r32 = r32 / r2;
        r29[r23] = r32;
        r7 = 1;
        goto L_0x00ef;
    L_0x0248:
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r32 = r29[r6];
        r32 = r32 / r2;
        r29[r6] = r32;
        r6 = r6 + 1;
        goto L_0x0229;
    L_0x0257:
        r24 = r24 + 1;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r4 = r29[r24];
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r0 = r38;
        r0 = r0.r;
        r32 = r0;
        r32 = r32[r25];
        r32 = r32 * r8;
        r34 = r30 * r4;
        r32 = r32 + r34;
        r29[r24] = r32;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r0 = r38;
        r0 = r0.r;
        r32 = r0;
        r32 = r32[r25];
        r32 = r32 * r2;
        r32 = r4 - r32;
        r29[r25] = r32;
        r25 = r25 + 1;
        r10 = r10 + 1;
        goto L_0x0119;
    L_0x0291:
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r2 = r29[r27];
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r0 = r38;
        r0 = r0.r;
        r32 = r0;
        r33 = r27 + -1;
        r32 = r32[r33];
        r29[r27] = r32;
        r0 = r38;
        r0 = r0.r;
        r29 = r0;
        r32 = r27 + -1;
        r29[r32] = r2;
        r0 = r38;
        r0 = r0.nvars;
        r29 = r0;
        r29 = r29 - r28;
        r29 = r29 + -2;
        r27 = r27 + r29;
        r28 = r28 + 1;
        goto L_0x015b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.stat.regression.MillerUpdatingRegression.vmove(int, int):void");
    }

    private int reorderRegressors(int[] list, int pos1) {
        if (list.length < 1 || list.length > (this.nvars + 1) - pos1) {
            return -1;
        }
        int next = pos1;
        int i = pos1;
        while (i < this.nvars) {
            int l = this.vorder[i];
            int j = 0;
            while (j < list.length) {
                if (l != list[j] || i <= next) {
                    j++;
                } else {
                    vmove(i, next);
                    next++;
                    if (next >= list.length + pos1) {
                        return 0;
                    }
                    i++;
                }
            }
            i++;
        }
        return 0;
    }

    public double getDiagonalOfHatMatrix(double[] row_data) {
        double[] wk = new double[this.nvars];
        if (row_data.length > this.nvars) {
            return Double.NaN;
        }
        double[] xrow;
        if (this.hasIntercept) {
            xrow = new double[(row_data.length + 1)];
            xrow[0] = 1.0d;
            System.arraycopy(row_data, 0, xrow, 1, row_data.length);
        } else {
            xrow = row_data;
        }
        double hii = 0.0d;
        for (int col = 0; col < xrow.length; col++) {
            if (FastMath.sqrt(this.d[col]) < this.tol[col]) {
                wk[col] = 0.0d;
            } else {
                int pos = col - 1;
                double total = xrow[col];
                for (int row = 0; row < col; row++) {
                    total = smartAdd(total, (-wk[row]) * this.r[pos]);
                    pos += (this.nvars - row) - 2;
                }
                wk[col] = total;
                hii = smartAdd(hii, (total * total) / this.d[col]);
            }
        }
        return hii;
    }

    public int[] getOrderOfRegressors() {
        return MathArrays.copyOf(this.vorder);
    }

    public RegressionResults regress() throws ModelSpecificationException {
        return regress(this.nvars);
    }

    public RegressionResults regress(int numberOfRegressors) throws ModelSpecificationException {
        if (this.nobs <= ((long) numberOfRegressors)) {
            throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, Long.valueOf(this.nobs), Integer.valueOf(numberOfRegressors));
        } else if (numberOfRegressors > this.nvars) {
            throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, Integer.valueOf(numberOfRegressors), Integer.valueOf(this.nvars));
        } else {
            int i;
            tolset();
            singcheck();
            double[] beta = regcf(numberOfRegressors);
            ss();
            double[] cov = cov(numberOfRegressors);
            int rnk = 0;
            for (boolean z : this.lindep) {
                if (!z) {
                    rnk++;
                }
            }
            boolean needsReorder = false;
            for (i = 0; i < numberOfRegressors; i++) {
                if (this.vorder[i] != i) {
                    needsReorder = true;
                    break;
                }
            }
            if (needsReorder) {
                int j;
                double[] betaNew = new double[beta.length];
                double[] covNew = new double[cov.length];
                int[] newIndices = new int[beta.length];
                for (i = 0; i < this.nvars; i++) {
                    for (j = 0; j < numberOfRegressors; j++) {
                        if (this.vorder[j] == i) {
                            betaNew[i] = beta[j];
                            newIndices[i] = j;
                        }
                    }
                }
                int idx1 = 0;
                for (i = 0; i < beta.length; i++) {
                    int _i = newIndices[i];
                    j = 0;
                    while (j <= i) {
                        int idx2;
                        int _j = newIndices[j];
                        if (_i > _j) {
                            idx2 = (((_i + 1) * _i) / 2) + _j;
                        } else {
                            idx2 = (((_j + 1) * _j) / 2) + _i;
                        }
                        covNew[idx1] = cov[idx2];
                        j++;
                        idx1++;
                    }
                }
                return new RegressionResults(betaNew, new double[][]{covNew}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
            }
            return new RegressionResults(beta, new double[][]{cov}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
        }
    }

    public RegressionResults regress(int[] variablesToInclude) throws ModelSpecificationException {
        if (variablesToInclude.length > this.nvars) {
            throw new ModelSpecificationException(LocalizedFormats.TOO_MANY_REGRESSORS, Integer.valueOf(variablesToInclude.length), Integer.valueOf(this.nvars));
        } else if (this.nobs <= ((long) this.nvars)) {
            throw new ModelSpecificationException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, Long.valueOf(this.nobs), Integer.valueOf(this.nvars));
        } else {
            int j;
            int[] series;
            Arrays.sort(variablesToInclude);
            int iExclude = 0;
            int i = 0;
            while (i < variablesToInclude.length) {
                if (i >= this.nvars) {
                    throw new ModelSpecificationException(LocalizedFormats.INDEX_LARGER_THAN_MAX, Integer.valueOf(i), Integer.valueOf(this.nvars));
                }
                if (i > 0 && variablesToInclude[i] == variablesToInclude[i - 1]) {
                    variablesToInclude[i] = -1;
                    iExclude++;
                }
                i++;
            }
            if (iExclude > 0) {
                j = 0;
                series = new int[(variablesToInclude.length - iExclude)];
                for (i = 0; i < variablesToInclude.length; i++) {
                    if (variablesToInclude[i] > -1) {
                        series[j] = variablesToInclude[i];
                        j++;
                    }
                }
            } else {
                series = variablesToInclude;
            }
            reorderRegressors(series, 0);
            tolset();
            singcheck();
            double[] beta = regcf(series.length);
            ss();
            double[] cov = cov(series.length);
            int rnk = 0;
            for (boolean z : this.lindep) {
                if (!z) {
                    rnk++;
                }
            }
            boolean needsReorder = false;
            for (i = 0; i < this.nvars; i++) {
                if (this.vorder[i] != series[i]) {
                    needsReorder = true;
                    break;
                }
            }
            if (needsReorder) {
                double[] betaNew = new double[beta.length];
                int[] newIndices = new int[beta.length];
                for (i = 0; i < series.length; i++) {
                    for (j = 0; j < this.vorder.length; j++) {
                        if (this.vorder[j] == series[i]) {
                            betaNew[i] = beta[j];
                            newIndices[i] = j;
                        }
                    }
                }
                double[] covNew = new double[cov.length];
                int idx1 = 0;
                for (i = 0; i < beta.length; i++) {
                    int _i = newIndices[i];
                    j = 0;
                    while (j <= i) {
                        int idx2;
                        int _j = newIndices[j];
                        if (_i > _j) {
                            idx2 = (((_i + 1) * _i) / 2) + _j;
                        } else {
                            idx2 = (((_j + 1) * _j) / 2) + _i;
                        }
                        covNew[idx1] = cov[idx2];
                        j++;
                        idx1++;
                    }
                }
                return new RegressionResults(betaNew, new double[][]{covNew}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
            }
            return new RegressionResults(beta, new double[][]{cov}, true, this.nobs, rnk, this.sumy, this.sumsqy, this.sserr, this.hasIntercept, false);
        }
    }
}
