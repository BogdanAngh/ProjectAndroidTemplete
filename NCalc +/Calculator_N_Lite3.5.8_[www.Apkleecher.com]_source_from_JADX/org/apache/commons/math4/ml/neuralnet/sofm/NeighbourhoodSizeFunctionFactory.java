package org.apache.commons.math4.ml.neuralnet.sofm;

import org.apache.commons.math4.ml.neuralnet.sofm.util.ExponentialDecayFunction;
import org.apache.commons.math4.ml.neuralnet.sofm.util.QuasiSigmoidDecayFunction;
import org.apache.commons.math4.util.FastMath;

public class NeighbourhoodSizeFunctionFactory {

    class 1 implements NeighbourhoodSizeFunction {
        private final ExponentialDecayFunction decay;

        1(double d, double d2, long j) {
            this.decay = new ExponentialDecayFunction(d, d2, j);
        }

        public int value(long n) {
            return (int) FastMath.rint(this.decay.value(n));
        }
    }

    class 2 implements NeighbourhoodSizeFunction {
        private final QuasiSigmoidDecayFunction decay;

        2(double d, double d2, long j) {
            this.decay = new QuasiSigmoidDecayFunction(d, d2, j);
        }

        public int value(long n) {
            return (int) FastMath.rint(this.decay.value(n));
        }
    }

    private NeighbourhoodSizeFunctionFactory() {
    }

    public static NeighbourhoodSizeFunction exponentialDecay(double initValue, double valueAtNumCall, long numCall) {
        return new 1(initValue, valueAtNumCall, numCall);
    }

    public static NeighbourhoodSizeFunction quasiSigmoidDecay(double initValue, double slope, long numCall) {
        return new 2(initValue, slope, numCall);
    }
}
