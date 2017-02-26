package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.util.FastMath;

public class Power implements UnivariateDifferentiableFunction {
    private final double p;

    public Power(double p) {
        this.p = p;
    }

    public double value(double x) {
        return FastMath.pow(x, this.p);
    }

    public DerivativeStructure value(DerivativeStructure t) {
        return t.pow(this.p);
    }
}
