package org.apache.commons.math4.ml.neuralnet.sofm;

import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.ml.neuralnet.sofm.util.ExponentialDecayFunction;
import org.apache.commons.math4.ml.neuralnet.sofm.util.QuasiSigmoidDecayFunction;

public class LearningFactorFunctionFactory {

    class 1 implements LearningFactorFunction {
        private final ExponentialDecayFunction decay;

        1(double d, double d2, long j) {
            this.decay = new ExponentialDecayFunction(d, d2, j);
        }

        public double value(long n) {
            return this.decay.value(n);
        }
    }

    class 2 implements LearningFactorFunction {
        private final QuasiSigmoidDecayFunction decay;

        2(double d, double d2, long j) {
            this.decay = new QuasiSigmoidDecayFunction(d, d2, j);
        }

        public double value(long n) {
            return this.decay.value(n);
        }
    }

    private LearningFactorFunctionFactory() {
    }

    public static LearningFactorFunction exponentialDecay(double initValue, double valueAtNumCall, long numCall) {
        if (initValue > 0.0d && initValue <= 1.0d) {
            return new 1(initValue, valueAtNumCall, numCall);
        }
        throw new OutOfRangeException(Double.valueOf(initValue), Integer.valueOf(0), Integer.valueOf(1));
    }

    public static LearningFactorFunction quasiSigmoidDecay(double initValue, double slope, long numCall) {
        if (initValue > 0.0d && initValue <= 1.0d) {
            return new 2(initValue, slope, numCall);
        }
        throw new OutOfRangeException(Double.valueOf(initValue), Integer.valueOf(0), Integer.valueOf(1));
    }
}
