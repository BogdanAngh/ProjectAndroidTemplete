package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.util.Pair;

public interface MultivariateJacobianFunction {
    Pair<RealVector, RealMatrix> value(RealVector realVector);
}
