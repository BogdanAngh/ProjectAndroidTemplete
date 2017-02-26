package org.apache.commons.math4.stat.descriptive;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.util.MathArrays.Function;

public interface UnivariateStatistic extends Function {
    UnivariateStatistic copy();

    double evaluate(double[] dArr) throws MathIllegalArgumentException;

    double evaluate(double[] dArr, int i, int i2) throws MathIllegalArgumentException;
}
