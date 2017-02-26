package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.util.FastMath;

public class Cosh implements UnivariateDifferentiableFunction {
    public double value(double x) {
        return FastMath.cosh(x);
    }

    public DerivativeStructure value(DerivativeStructure t) {
        return t.cosh();
    }
}
