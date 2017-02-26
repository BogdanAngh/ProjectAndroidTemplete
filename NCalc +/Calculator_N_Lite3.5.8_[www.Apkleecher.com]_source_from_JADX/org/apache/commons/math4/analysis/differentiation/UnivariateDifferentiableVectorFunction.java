package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.UnivariateVectorFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface UnivariateDifferentiableVectorFunction extends UnivariateVectorFunction {
    DerivativeStructure[] value(DerivativeStructure derivativeStructure) throws MathIllegalArgumentException;
}
