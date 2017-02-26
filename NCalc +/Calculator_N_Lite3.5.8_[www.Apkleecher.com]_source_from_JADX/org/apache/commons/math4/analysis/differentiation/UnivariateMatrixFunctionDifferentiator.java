package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.UnivariateMatrixFunction;

public interface UnivariateMatrixFunctionDifferentiator {
    UnivariateDifferentiableMatrixFunction differentiate(UnivariateMatrixFunction univariateMatrixFunction);
}
