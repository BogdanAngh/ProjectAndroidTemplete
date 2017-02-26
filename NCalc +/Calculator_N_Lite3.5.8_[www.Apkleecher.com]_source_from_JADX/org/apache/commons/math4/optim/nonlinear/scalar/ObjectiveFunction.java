package org.apache.commons.math4.optim.nonlinear.scalar;

import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.optim.OptimizationData;

public class ObjectiveFunction implements OptimizationData {
    private final MultivariateFunction function;

    public ObjectiveFunction(MultivariateFunction f) {
        this.function = f;
    }

    public MultivariateFunction getObjectiveFunction() {
        return this.function;
    }
}
