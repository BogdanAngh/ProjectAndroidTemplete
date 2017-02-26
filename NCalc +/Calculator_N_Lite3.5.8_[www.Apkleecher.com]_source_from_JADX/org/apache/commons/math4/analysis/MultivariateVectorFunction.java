package org.apache.commons.math4.analysis;

public interface MultivariateVectorFunction {
    double[] value(double[] dArr) throws IllegalArgumentException;
}
