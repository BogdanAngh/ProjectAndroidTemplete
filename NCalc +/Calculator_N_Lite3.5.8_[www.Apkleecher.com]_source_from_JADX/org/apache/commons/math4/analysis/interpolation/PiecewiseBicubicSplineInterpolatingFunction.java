package org.apache.commons.math4.analysis.interpolation;

import java.util.Arrays;
import org.apache.commons.math4.analysis.BivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.InsufficientDataException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.util.MathArrays;

public class PiecewiseBicubicSplineInterpolatingFunction implements BivariateFunction {
    private static final int MIN_NUM_POINTS = 5;
    private final double[][] fval;
    private final double[] xval;
    private final double[] yval;

    public PiecewiseBicubicSplineInterpolatingFunction(double[] x, double[] y, double[][] f) throws DimensionMismatchException, NullArgumentException, NoDataException, NonMonotonicSequenceException {
        if (x == null || y == null || f == null || f[0] == null) {
            throw new NullArgumentException();
        }
        int xLen = x.length;
        int yLen = y.length;
        if (xLen == 0 || yLen == 0 || f.length == 0 || f[0].length == 0) {
            throw new NoDataException();
        } else if (xLen < MIN_NUM_POINTS || yLen < MIN_NUM_POINTS || f.length < MIN_NUM_POINTS || f[0].length < MIN_NUM_POINTS) {
            throw new InsufficientDataException();
        } else if (xLen != f.length) {
            throw new DimensionMismatchException(xLen, f.length);
        } else if (yLen != f[0].length) {
            throw new DimensionMismatchException(yLen, f[0].length);
        } else {
            MathArrays.checkOrder(x);
            MathArrays.checkOrder(y);
            this.xval = (double[]) x.clone();
            this.yval = (double[]) y.clone();
            this.fval = (double[][]) f.clone();
        }
    }

    public double value(double x, double y) throws OutOfRangeException {
        int index;
        AkimaSplineInterpolator interpolator = new AkimaSplineInterpolator();
        int i = searchIndex(x, this.xval, 2, MIN_NUM_POINTS);
        int j = searchIndex(y, this.yval, 2, MIN_NUM_POINTS);
        double[] xArray = new double[MIN_NUM_POINTS];
        double[] yArray = new double[MIN_NUM_POINTS];
        double[] zArray = new double[MIN_NUM_POINTS];
        double[] interpArray = new double[MIN_NUM_POINTS];
        for (index = 0; index < MIN_NUM_POINTS; index++) {
            xArray[index] = this.xval[i + index];
            yArray[index] = this.yval[j + index];
        }
        for (int zIndex = 0; zIndex < MIN_NUM_POINTS; zIndex++) {
            for (index = 0; index < MIN_NUM_POINTS; index++) {
                zArray[index] = this.fval[i + index][j + zIndex];
            }
            interpArray[zIndex] = interpolator.interpolate(xArray, zArray).value(x);
        }
        return interpolator.interpolate(yArray, interpArray).value(y);
    }

    public boolean isValidPoint(double x, double y) {
        if (x < this.xval[0] || x > this.xval[this.xval.length - 1] || y < this.yval[0] || y > this.yval[this.yval.length - 1]) {
            return false;
        }
        return true;
    }

    private int searchIndex(double c, double[] val, int offset, int count) {
        int r = Arrays.binarySearch(val, c);
        if (r == -1 || r == (-val.length) - 1) {
            throw new OutOfRangeException(Double.valueOf(c), Double.valueOf(val[0]), Double.valueOf(val[val.length - 1]));
        }
        if (r < 0) {
            r = ((-r) - offset) - 1;
        } else {
            r -= offset;
        }
        if (r < 0) {
            r = 0;
        }
        if (r + count >= val.length) {
            return val.length - count;
        }
        return r;
    }
}
