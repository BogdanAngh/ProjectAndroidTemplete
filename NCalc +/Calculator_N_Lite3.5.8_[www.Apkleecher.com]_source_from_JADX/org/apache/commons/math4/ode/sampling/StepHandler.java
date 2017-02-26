package org.apache.commons.math4.ode.sampling;

import org.apache.commons.math4.exception.MaxCountExceededException;

public interface StepHandler {
    void handleStep(StepInterpolator stepInterpolator, boolean z) throws MaxCountExceededException;

    void init(double d, double[] dArr, double d2);
}
