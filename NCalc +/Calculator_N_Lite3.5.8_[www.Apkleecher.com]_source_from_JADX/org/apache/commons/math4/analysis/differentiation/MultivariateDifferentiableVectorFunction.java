package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.MultivariateVectorFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface MultivariateDifferentiableVectorFunction extends MultivariateVectorFunction {
    DerivativeStructure[] value(DerivativeStructure[] derivativeStructureArr) throws MathIllegalArgumentException;
}
