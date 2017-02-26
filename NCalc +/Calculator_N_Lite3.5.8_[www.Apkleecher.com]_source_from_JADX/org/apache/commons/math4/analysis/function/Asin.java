package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.util.FastMath;

public class Asin implements UnivariateDifferentiableFunction {
    public double value(double x) {
        return FastMath.asin(x);
    }

    public DerivativeStructure value(DerivativeStructure t) {
        return t.asin();
    }
}
