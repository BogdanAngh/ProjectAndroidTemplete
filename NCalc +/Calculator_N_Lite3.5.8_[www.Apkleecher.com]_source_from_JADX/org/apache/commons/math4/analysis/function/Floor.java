package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.util.FastMath;

public class Floor implements UnivariateFunction {
    public double value(double x) {
        return FastMath.floor(x);
    }
}
