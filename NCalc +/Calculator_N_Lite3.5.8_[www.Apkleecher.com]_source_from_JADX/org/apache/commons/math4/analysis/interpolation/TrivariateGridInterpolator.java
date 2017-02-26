package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.TrivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;

public interface TrivariateGridInterpolator {
    TrivariateFunction interpolate(double[] dArr, double[] dArr2, double[] dArr3, double[][][] dArr4) throws NoDataException, NumberIsTooSmallException, DimensionMismatchException, NonMonotonicSequenceException;
}
