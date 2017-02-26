package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NullArgumentException;

public interface MultivariateInterpolator {
    MultivariateFunction interpolate(double[][] dArr, double[] dArr2) throws MathIllegalArgumentException, DimensionMismatchException, NoDataException, NullArgumentException;
}
