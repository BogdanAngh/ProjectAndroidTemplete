package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface MultivariateDifferentiableFunction extends MultivariateFunction {
    DerivativeStructure value(DerivativeStructure[] derivativeStructureArr) throws MathIllegalArgumentException;
}
