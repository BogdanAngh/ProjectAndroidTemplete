package org.apache.commons.math4.analysis;

public interface MultivariateMatrixFunction {
    double[][] value(double[] dArr) throws IllegalArgumentException;
}
