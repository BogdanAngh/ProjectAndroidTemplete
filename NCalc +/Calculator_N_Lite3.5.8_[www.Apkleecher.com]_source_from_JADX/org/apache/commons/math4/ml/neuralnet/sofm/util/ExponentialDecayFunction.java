package org.apache.commons.math4.ml.neuralnet.sofm.util;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.util.FastMath;

public class ExponentialDecayFunction {
    private final double a;
    private final double oneOverB;

    public ExponentialDecayFunction(double initValue, double valueAtNumCall, long numCall) {
        if (initValue <= 0.0d) {
            throw new NotStrictlyPositiveException(Double.valueOf(initValue));
        } else if (valueAtNumCall <= 0.0d) {
            throw new NotStrictlyPositiveException(Double.valueOf(valueAtNumCall));
        } else if (valueAtNumCall >= initValue) {
            throw new NumberIsTooLargeException(Double.valueOf(valueAtNumCall), Double.valueOf(initValue), false);
        } else if (numCall <= 0) {
            throw new NotStrictlyPositiveException(Long.valueOf(numCall));
        } else {
            this.a = initValue;
            this.oneOverB = (-FastMath.log(valueAtNumCall / initValue)) / ((double) numCall);
        }
    }

    public double value(long numCall) {
        return this.a * FastMath.exp(((double) (-numCall)) * this.oneOverB);
    }
}
