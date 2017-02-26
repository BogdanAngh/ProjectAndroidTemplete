package org.apache.commons.math4.analysis.interpolation;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math4.analysis.BivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.util.MathArrays;

public class BicubicInterpolatingFunction implements BivariateFunction {
    private static final double[][] AINV;
    private static final int NUM_COEFF = 16;
    private final BicubicFunction[][] splines;
    private final double[] xval;
    private final double[] yval;

    static {
        double[][] dArr = new double[NUM_COEFF][];
        dArr[0] = new double[]{1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        dArr[1] = new double[]{0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        dArr[2] = new double[]{-3.0d, 3.0d, 0.0d, 0.0d, -2.0d, -1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        dArr[3] = new double[]{2.0d, -2.0d, 0.0d, 0.0d, 1.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        dArr[4] = new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        dArr[5] = new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d};
        dArr[6] = new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -3.0d, 3.0d, 0.0d, 0.0d, -2.0d, -1.0d, 0.0d, 0.0d};
        dArr[7] = new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 2.0d, -2.0d, 0.0d, 0.0d, 1.0d, 1.0d, 0.0d, 0.0d};
        dArr[8] = new double[]{-3.0d, 0.0d, 3.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -2.0d, 0.0d, -1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        dArr[9] = new double[]{0.0d, 0.0d, 0.0d, 0.0d, -3.0d, 0.0d, 3.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, -2.0d, 0.0d, -1.0d, 0.0d};
        dArr[10] = new double[]{9.0d, -9.0d, -9.0d, 9.0d, 6.0d, 3.0d, -6.0d, -3.0d, 6.0d, -6.0d, 3.0d, -3.0d, 4.0d, 2.0d, 2.0d, 1.0d};
        dArr[11] = new double[]{-6.0d, 6.0d, 6.0d, -6.0d, -3.0d, -3.0d, 3.0d, 3.0d, -4.0d, 4.0d, -2.0d, 2.0d, -2.0d, -2.0d, -1.0d, -1.0d};
        dArr[12] = new double[]{2.0d, 0.0d, -2.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d};
        dArr[13] = new double[]{0.0d, 0.0d, 0.0d, 0.0d, 2.0d, 0.0d, -2.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 1.0d, 0.0d};
        dArr[14] = new double[]{-6.0d, 6.0d, 6.0d, -6.0d, -4.0d, -2.0d, 4.0d, 2.0d, -3.0d, 3.0d, -3.0d, 3.0d, -2.0d, -1.0d, -2.0d, -1.0d};
        dArr[15] = new double[]{4.0d, -4.0d, -4.0d, 4.0d, 2.0d, 2.0d, -2.0d, -2.0d, 2.0d, -2.0d, 2.0d, -2.0d, 1.0d, 1.0d, 1.0d, 1.0d};
        AINV = dArr;
    }

    public BicubicInterpolatingFunction(double[] x, double[] y, double[][] f, double[][] dFdX, double[][] dFdY, double[][] d2FdXdY) throws DimensionMismatchException, NoDataException, NonMonotonicSequenceException {
        int xLen = x.length;
        int yLen = y.length;
        if (xLen == 0 || yLen == 0 || f.length == 0 || f[0].length == 0) {
            throw new NoDataException();
        } else if (xLen != f.length) {
            throw new DimensionMismatchException(xLen, f.length);
        } else if (xLen != dFdX.length) {
            throw new DimensionMismatchException(xLen, dFdX.length);
        } else if (xLen != dFdY.length) {
            throw new DimensionMismatchException(xLen, dFdY.length);
        } else if (xLen != d2FdXdY.length) {
            throw new DimensionMismatchException(xLen, d2FdXdY.length);
        } else {
            MathArrays.checkOrder(x);
            MathArrays.checkOrder(y);
            this.xval = (double[]) x.clone();
            this.yval = (double[]) y.clone();
            int lastI = xLen - 1;
            int lastJ = yLen - 1;
            this.splines = (BicubicFunction[][]) Array.newInstance(BicubicFunction.class, new int[]{lastI, lastJ});
            int i = 0;
            while (i < lastI) {
                if (f[i].length != yLen) {
                    throw new DimensionMismatchException(f[i].length, yLen);
                } else if (dFdX[i].length != yLen) {
                    throw new DimensionMismatchException(dFdX[i].length, yLen);
                } else if (dFdY[i].length != yLen) {
                    throw new DimensionMismatchException(dFdY[i].length, yLen);
                } else if (d2FdXdY[i].length != yLen) {
                    throw new DimensionMismatchException(d2FdXdY[i].length, yLen);
                } else {
                    int ip1 = i + 1;
                    double xR = this.xval[ip1] - this.xval[i];
                    for (int j = 0; j < lastJ; j++) {
                        int jp1 = j + 1;
                        double yR = this.yval[jp1] - this.yval[j];
                        double xRyR = xR * yR;
                        double[] beta = new double[NUM_COEFF];
                        beta[0] = f[i][j];
                        beta[1] = f[ip1][j];
                        beta[2] = f[i][jp1];
                        beta[3] = f[ip1][jp1];
                        beta[4] = dFdX[i][j] * xR;
                        beta[5] = dFdX[ip1][j] * xR;
                        beta[6] = dFdX[i][jp1] * xR;
                        beta[7] = dFdX[ip1][jp1] * xR;
                        beta[8] = dFdY[i][j] * yR;
                        beta[9] = dFdY[ip1][j] * yR;
                        beta[10] = dFdY[i][jp1] * yR;
                        beta[11] = dFdY[ip1][jp1] * yR;
                        beta[12] = d2FdXdY[i][j] * xRyR;
                        beta[13] = d2FdXdY[ip1][j] * xRyR;
                        beta[14] = d2FdXdY[i][jp1] * xRyR;
                        beta[15] = d2FdXdY[ip1][jp1] * xRyR;
                        this.splines[i][j] = new BicubicFunction(computeSplineCoefficients(beta));
                    }
                    i++;
                }
            }
        }
    }

    public double value(double x, double y) throws OutOfRangeException {
        int i = searchIndex(x, this.xval);
        int j = searchIndex(y, this.yval);
        return this.splines[i][j].value((x - this.xval[i]) / (this.xval[i + 1] - this.xval[i]), (y - this.yval[j]) / (this.yval[j + 1] - this.yval[j]));
    }

    public boolean isValidPoint(double x, double y) {
        if (x < this.xval[0] || x > this.xval[this.xval.length - 1] || y < this.yval[0] || y > this.yval[this.yval.length - 1]) {
            return false;
        }
        return true;
    }

    private int searchIndex(double c, double[] val) {
        int r = Arrays.binarySearch(val, c);
        if (r == -1 || r == (-val.length) - 1) {
            throw new OutOfRangeException(Double.valueOf(c), Double.valueOf(val[0]), Double.valueOf(val[val.length - 1]));
        } else if (r < 0) {
            return (-r) - 2;
        } else {
            int last = val.length - 1;
            if (r == last) {
                return last - 1;
            }
            return r;
        }
    }

    private double[] computeSplineCoefficients(double[] beta) {
        double[] a = new double[NUM_COEFF];
        for (int i = 0; i < NUM_COEFF; i++) {
            double result = 0.0d;
            double[] row = AINV[i];
            for (int j = 0; j < NUM_COEFF; j++) {
                result += row[j] * beta[j];
            }
            a[i] = result;
        }
        return a;
    }
}
