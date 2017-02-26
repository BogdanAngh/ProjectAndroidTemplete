package org.apache.commons.math4.analysis.differentiation;

import org.apache.commons.math4.analysis.UnivariateFunction;

public interface UnivariateFunctionDifferentiator {
    UnivariateDifferentiableFunction differentiate(UnivariateFunction univariateFunction);
}
