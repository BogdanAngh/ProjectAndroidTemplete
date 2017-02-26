package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;

public class Inverse implements UnivariateDifferentiableFunction {
    public double value(double x) {
        return 1.0d / x;
    }

    public DerivativeStructure value(DerivativeStructure t) {
        return t.reciprocal();
    }
}
