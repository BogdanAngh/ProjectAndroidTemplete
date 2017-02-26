package org.apache.commons.math4.analysis.interpolation;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.util.MathArrays;

public class TricubicInterpolator implements TrivariateGridInterpolator {

    class 1 extends TricubicInterpolatingFunction {
        private final /* synthetic */ double[] val$xval;
        private final /* synthetic */ double[] val$yval;
        private final /* synthetic */ double[] val$zval;

        1(double[] $anonymous0, double[] $anonymous1, double[] $anonymous2, double[][][] $anonymous3, double[][][] $anonymous4, double[][][] $anonymous5, double[][][] $anonymous6, double[][][] $anonymous7, double[][][] $anonymous8, double[][][] $anonymous9, double[][][] $anonymous10, double[] dArr, double[] dArr2, double[] dArr3) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException {
            this.val$xval = dArr;
            this.val$yval = dArr2;
            this.val$zval = dArr3;
            super($anonymous0, $anonymous1, $anonymous2, $anonymous3, $anonymous4, $anonymous5, $anonymous6, $anonymous7, $anonymous8, $anonymous9, $anonymous10);
        }

        public boolean isValidPoint(double x, double y, double z) {
            if (x < this.val$xval[1] || x > this.val$xval[this.val$xval.length - 2] || y < this.val$yval[1] || y > this.val$yval[this.val$yval.length - 2] || z < this.val$zval[1] || z > this.val$zval[this.val$zval.length - 2]) {
                return false;
            }
            return true;
        }
    }

    public TricubicInterpolatingFunction interpolate(double[] xval, double[] yval, double[] zval, double[][][] fval) throws NoDataException, NumberIsTooSmallException, DimensionMismatchException, NonMonotonicSequenceException {
        if (xval.length == 0 || yval.length == 0 || zval.length == 0 || fval.length == 0) {
            throw new NoDataException();
        } else if (xval.length != fval.length) {
            throw new DimensionMismatchException(xval.length, fval.length);
        } else {
            MathArrays.checkOrder(xval);
            MathArrays.checkOrder(yval);
            MathArrays.checkOrder(zval);
            int xLen = xval.length;
            int yLen = yval.length;
            int zLen = zval.length;
            double[][][] dFdX = (double[][][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen, zLen});
            double[][][] dFdY = (double[][][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen, zLen});
            double[][][] dFdZ = (double[][][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen, zLen});
            double[][][] d2FdXdY = (double[][][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen, zLen});
            double[][][] d2FdXdZ = (double[][][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen, zLen});
            double[][][] d2FdYdZ = (double[][][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen, zLen});
            double[][][] d3FdXdYdZ = (double[][][]) Array.newInstance(Double.TYPE, new int[]{xLen, yLen, zLen});
            for (int i = 1; i < xLen - 1; i++) {
                if (yval.length != fval[i].length) {
                    throw new DimensionMismatchException(yval.length, fval[i].length);
                }
                int nI = i + 1;
                int pI = i - 1;
                double deltaX = xval[nI] - xval[pI];
                for (int j = 1; j < yLen - 1; j++) {
                    if (zval.length != fval[i][j].length) {
                        throw new DimensionMismatchException(zval.length, fval[i][j].length);
                    }
                    int nJ = j + 1;
                    int pJ = j - 1;
                    double deltaY = yval[nJ] - yval[pJ];
                    double deltaXY = deltaX * deltaY;
                    for (int k = 1; k < zLen - 1; k++) {
                        int nK = k + 1;
                        int pK = k - 1;
                        double deltaZ = zval[nK] - zval[pK];
                        dFdX[i][j][k] = (fval[nI][j][k] - fval[pI][j][k]) / deltaX;
                        dFdY[i][j][k] = (fval[i][nJ][k] - fval[i][pJ][k]) / deltaY;
                        dFdZ[i][j][k] = (fval[i][j][nK] - fval[i][j][pK]) / deltaZ;
                        double deltaXZ = deltaX * deltaZ;
                        double deltaYZ = deltaY * deltaZ;
                        d2FdXdY[i][j][k] = (((fval[nI][nJ][k] - fval[nI][pJ][k]) - fval[pI][nJ][k]) + fval[pI][pJ][k]) / deltaXY;
                        d2FdXdZ[i][j][k] = (((fval[nI][j][nK] - fval[nI][j][pK]) - fval[pI][j][nK]) + fval[pI][j][pK]) / deltaXZ;
                        d2FdYdZ[i][j][k] = (((fval[i][nJ][nK] - fval[i][nJ][pK]) - fval[i][pJ][nK]) + fval[i][pJ][pK]) / deltaYZ;
                        d3FdXdYdZ[i][j][k] = (((((((fval[nI][nJ][nK] - fval[nI][pJ][nK]) - fval[pI][nJ][nK]) + fval[pI][pJ][nK]) - fval[nI][nJ][pK]) + fval[nI][pJ][pK]) + fval[pI][nJ][pK]) - fval[pI][pJ][pK]) / (deltaXY * deltaZ);
                    }
                }
            }
            return new 1(xval, yval, zval, fval, dFdX, dFdY, dFdZ, d2FdXdY, d2FdXdZ, d2FdYdZ, d3FdXdYdZ, xval, yval, zval);
        }
    }
}
