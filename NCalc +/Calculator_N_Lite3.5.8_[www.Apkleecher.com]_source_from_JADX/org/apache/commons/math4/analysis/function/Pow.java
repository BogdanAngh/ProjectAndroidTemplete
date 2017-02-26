package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.BivariateFunction;
import org.apache.commons.math4.util.FastMath;

public class Pow implements BivariateFunction {
    public double value(double x, double y) {
        return FastMath.pow(x, y);
    }
}
