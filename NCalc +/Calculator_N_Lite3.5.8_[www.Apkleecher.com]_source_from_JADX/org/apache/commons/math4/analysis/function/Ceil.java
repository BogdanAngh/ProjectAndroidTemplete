package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.util.FastMath;

public class Ceil implements UnivariateFunction {
    public double value(double x) {
        return FastMath.ceil(x);
    }
}
