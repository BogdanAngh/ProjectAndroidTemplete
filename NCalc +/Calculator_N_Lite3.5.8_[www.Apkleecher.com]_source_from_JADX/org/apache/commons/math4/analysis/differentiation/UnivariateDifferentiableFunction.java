package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;

public interface UnivariateDifferentiableFunction extends UnivariateFunction {
    DerivativeStructure value(DerivativeStructure derivativeStructure) throws DimensionMismatchException;
}
