package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;

public interface ValueAndJacobianFunction extends MultivariateJacobianFunction {
    RealMatrix computeJacobian(double[] dArr);

    RealVector computeValue(double[] dArr);
}
