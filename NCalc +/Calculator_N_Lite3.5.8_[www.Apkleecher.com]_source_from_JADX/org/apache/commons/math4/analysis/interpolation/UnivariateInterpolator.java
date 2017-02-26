package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface UnivariateInterpolator {
    UnivariateFunction interpolate(double[] dArr, double[] dArr2) throws MathIllegalArgumentException, DimensionMismatchException;
}
