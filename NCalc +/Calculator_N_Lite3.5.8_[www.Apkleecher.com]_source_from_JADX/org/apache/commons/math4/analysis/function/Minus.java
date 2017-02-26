package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;

public class Minus implements UnivariateDifferentiableFunction {
    public double value(double x) {
        return -x;
    }

    public DerivativeStructure value(DerivativeStructure t) {
        return t.negate();
    }
}
