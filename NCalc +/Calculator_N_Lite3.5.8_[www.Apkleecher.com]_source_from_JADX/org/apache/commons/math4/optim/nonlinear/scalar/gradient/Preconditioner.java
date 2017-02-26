package org.apache.commons.math4.optim.nonlinear.scalar.gradient;

public interface Preconditioner {
    double[] precondition(double[] dArr, double[] dArr2);
}
