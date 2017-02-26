package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.linear.RealVector;

public interface ParameterValidator {
    RealVector validate(RealVector realVector);
}
