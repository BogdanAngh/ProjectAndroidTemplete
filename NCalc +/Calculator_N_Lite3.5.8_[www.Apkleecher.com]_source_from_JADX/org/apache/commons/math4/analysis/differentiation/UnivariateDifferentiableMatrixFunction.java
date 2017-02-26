package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.UnivariateMatrixFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface UnivariateDifferentiableMatrixFunction extends UnivariateMatrixFunction {
    DerivativeStructure[][] value(DerivativeStructure derivativeStructure) throws MathIllegalArgumentException;
}
