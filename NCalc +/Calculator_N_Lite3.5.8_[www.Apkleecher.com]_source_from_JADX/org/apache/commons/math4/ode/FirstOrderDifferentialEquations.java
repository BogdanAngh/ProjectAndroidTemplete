package org.apache.commons.math4.ode;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;

public interface FirstOrderDifferentialEquations {
    void computeDerivatives(double d, double[] dArr, double[] dArr2) throws MaxCountExceededException, DimensionMismatchException;

    int getDimension();
}
