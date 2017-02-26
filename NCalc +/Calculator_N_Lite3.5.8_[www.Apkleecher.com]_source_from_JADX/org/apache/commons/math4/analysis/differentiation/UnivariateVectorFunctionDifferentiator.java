package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.UnivariateVectorFunction;

public interface UnivariateVectorFunctionDifferentiator {
    UnivariateDifferentiableVectorFunction differentiate(UnivariateVectorFunction univariateVectorFunction);
}
