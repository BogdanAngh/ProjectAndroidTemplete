package org.apache.commons.math4.ode;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathIllegalStateException;

public interface SecondOrderIntegrator extends ODEIntegrator {
    void integrate(SecondOrderDifferentialEquations secondOrderDifferentialEquations, double d, double[] dArr, double[] dArr2, double d2, double[] dArr3, double[] dArr4) throws MathIllegalStateException, MathIllegalArgumentException;
}
