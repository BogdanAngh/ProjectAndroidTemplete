package org.apache.commons.math4.analysis.interpolation;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.util.MathArrays;

public class BicubicInterpolator implements BivariateGridInterpolator {

    class 1 extends BicubicInterpolatingFunction {
        private final /* synthetic */ double[] val$xval;
        private final /* synthetic */ double[] val$yval;

        1(double[] $anonymous0, double[] $anonymous1, double[][] $anonymous2, double[][] $anonymous3, double[][] $anonymous4, double[][] $anonymous5, double[] dArr, double[] dArr2) throws DimensionMismatchException, NoDataException, NonMonotonicSequenceException {
            this.val$xval = dArr;
            this.val$yval = dArr2;
            super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4, $anonymous5);
        }

        public boolean isValidPoint(double x, double y) {
            if (x < this.val$xval[1] || x > this.val$xval[this.val$xval.length - 2] || y < this.val$yval[1] || y > this.val$yval[this.val$yval.length - 2]) {
                return false;
            }
            return true;
        }
    }

    public BicubicInterpolatingFunction interpolate(double[] xval, double[] yval, double[][] fval) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException, NumberIsTooSmallException {
        if (xval.length == 0 || yval.length == 0 || fval.length == 0) {
            throw new NoDataException();
        } else if (xval.length != fval.length) {
            throw new DimensionMismatchException(xval.length, fval.length);
        } else {
            MathArrays.checkOrder(xval);
            MathArrays.checkOrder(yval);
            int xLen = xval.length;
            int yLen = yval.length;
            double[][] dFdX = (double[][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen});
            double[][] dFdY = (double[][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen});
            double[][] d2FdXdY = (double[][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen});
            for (int i = 1; i < xLen - 1; i++) {
                int nI = i + 1;
                int pI = i - 1;
                double deltaX = xval[nI] - xval[pI];
                for (int j = 1; j < yLen - 1; j++) {
                    int nJ = j + 1;
                    int pJ = j - 1;
                    double deltaY = yval[nJ] - yval[pJ];
                    dFdX[i][j] = (fval[nI][j] - fval[pI][j]) / deltaX;
                    dFdY[i][j] = (fval[i][nJ] - fval[i][pJ]) / deltaY;
                    d2FdXdY[i][j] = (((fval[nI][nJ] - fval[nI][pJ]) - fval[pI][nJ]) + fval[pI][pJ]) / (deltaX * deltaY);
                }
            }
            return new 1(xval, yval, fval, dFdX, dFdY, d2FdXdY, xval, yval);
        }
    }
}
