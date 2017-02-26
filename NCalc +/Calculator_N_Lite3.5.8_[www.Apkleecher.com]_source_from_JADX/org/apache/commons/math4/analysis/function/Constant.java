package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;

public class Constant implements UnivariateDifferentiableFunction {
    private final double c;

    public Constant(double c) {
        this.c = c;
    }

    public double value(double x) {
        return this.c;
    }

    public DerivativeStructure value(DerivativeStructure t) {
        return new DerivativeStructure(t.getFreeParameters(), t.getOrder(), this.c);
    }
}
