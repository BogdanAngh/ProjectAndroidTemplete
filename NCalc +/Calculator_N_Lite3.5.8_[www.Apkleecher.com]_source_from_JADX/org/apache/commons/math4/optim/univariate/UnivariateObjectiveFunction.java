package org.apache.commons.math4.optim.univariate;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.optim.OptimizationData;

public class UnivariateObjectiveFunction implements OptimizationData {
    private final UnivariateFunction function;

    public UnivariateObjectiveFunction(UnivariateFunction f) {
        this.function = f;
    }

    public UnivariateFunction getObjectiveFunction() {
        return this.function;
    }
}
