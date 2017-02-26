package org.apache.commons.math4.ml.neuralnet.sofm.util;

import org.apache.commons.math4.analysis.function.Logistic;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;

public class QuasiSigmoidDecayFunction {
    private final double scale;
    private final Logistic sigmoid;

    public QuasiSigmoidDecayFunction(double initValue, double slope, long numCall) {
        if (initValue <= 0.0d) {
            throw new NotStrictlyPositiveException(Double.valueOf(initValue));
        } else if (slope >= 0.0d) {
            throw new NumberIsTooLargeException(Double.valueOf(slope), Integer.valueOf(0), false);
        } else if (numCall <= 1) {
            throw new NotStrictlyPositiveException(Long.valueOf(numCall));
        } else {
            double k = initValue;
            this.sigmoid = new Logistic(k, (double) numCall, (4.0d * slope) / initValue, 1.0d, 0.0d, 1.0d);
            this.scale = k / this.sigmoid.value(0.0d);
        }
    }

    public double value(long numCall) {
        return this.scale * this.sigmoid.value((double) numCall);
    }
}
