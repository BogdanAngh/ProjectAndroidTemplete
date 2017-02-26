package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.util.MathArrays;

public class PiecewiseBicubicSplineInterpolator implements BivariateGridInterpolator {
    public PiecewiseBicubicSplineInterpolatingFunction interpolate(double[] xval, double[] yval, double[][] fval) throws DimensionMismatchException, NullArgumentException, NoDataException, NonMonotonicSequenceException {
        if (xval == null || yval == null || fval == null || fval[0] == null) {
            throw new NullArgumentException();
        } else if (xval.length == 0 || yval.length == 0 || fval.length == 0) {
            throw new NoDataException();
        } else {
            MathArrays.checkOrder(xval);
            MathArrays.checkOrder(yval);
            return new PiecewiseBicubicSplineInterpolatingFunction(xval, yval, fval);
        }
    }
}
