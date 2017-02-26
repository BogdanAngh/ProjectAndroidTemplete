package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.BivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;

public interface BivariateGridInterpolator {
    BivariateFunction interpolate(double[] dArr, double[] dArr2, double[][] dArr3) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException, NumberIsTooSmallException;
}
