package org.apache.commons.math4.distribution;

import java.io.Serializable;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.solvers.UnivariateSolverUtils;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.util.FastMath;

public abstract class AbstractRealDistribution implements RealDistribution, Serializable {
    public static final double SOLVER_DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;
    private static final long serialVersionUID = -38038050983108802L;
    protected final RandomGenerator random;

    class 1 implements UnivariateFunction {
        private final /* synthetic */ double val$p;

        1(double d) {
            this.val$p = d;
        }

        public double value(double x) {
            return AbstractRealDistribution.this.cumulativeProbability(x) - this.val$p;
        }
    }

    protected AbstractRealDistribution(RandomGenerator rng) {
        this.random = rng;
    }

    public double probability(double x0, double x1) {
        if (x0 <= x1) {
            return cumulativeProbability(x1) - cumulativeProbability(x0);
        }
        throw new NumberIsTooLargeException(LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, Double.valueOf(x0), Double.valueOf(x1), true);
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
        }
        double lowerBound = getSupportLowerBound();
        if (p == 0.0d) {
            return lowerBound;
        }
        double upperBound = getSupportUpperBound();
        if (p == 1.0d) {
            return upperBound;
        }
        double mu = getNumericalMean();
        double sig = FastMath.sqrt(getNumericalVariance());
        boolean chebyshevApplies = (Double.isInfinite(mu) || Double.isNaN(mu) || Double.isInfinite(sig) || Double.isNaN(sig)) ? false : true;
        if (lowerBound == Double.NEGATIVE_INFINITY) {
            if (chebyshevApplies) {
                lowerBound = mu - (FastMath.sqrt((1.0d - p) / p) * sig);
            } else {
                lowerBound = -1.0d;
                while (cumulativeProbability(lowerBound) >= p) {
                    lowerBound *= 2.0d;
                }
            }
        }
        if (upperBound == Double.POSITIVE_INFINITY) {
            if (chebyshevApplies) {
                upperBound = mu + (FastMath.sqrt(p / (1.0d - p)) * sig);
            } else {
                upperBound = 1.0d;
                while (cumulativeProbability(upperBound) < p) {
                    upperBound *= 2.0d;
                }
            }
        }
        double x = UnivariateSolverUtils.solve(new 1(p), lowerBound, upperBound, getSolverAbsoluteAccuracy());
        if (!isSupportConnected()) {
            double dx = getSolverAbsoluteAccuracy();
            if (x - dx >= getSupportLowerBound()) {
                double px = cumulativeProbability(x);
                if (cumulativeProbability(x - dx) == px) {
                    upperBound = x;
                    while (upperBound - lowerBound > dx) {
                        double midPoint = 0.5d * (lowerBound + upperBound);
                        if (cumulativeProbability(midPoint) < px) {
                            lowerBound = midPoint;
                        } else {
                            upperBound = midPoint;
                        }
                    }
                    return upperBound;
                }
            }
        }
        return x;
    }

    protected double getSolverAbsoluteAccuracy() {
        return SOLVER_DEFAULT_ABSOLUTE_ACCURACY;
    }

    public void reseedRandomGenerator(long seed) {
        this.random.setSeed(seed);
    }

    public double sample() {
        return inverseCumulativeProbability(this.random.nextDouble());
    }

    public double[] sample(int sampleSize) {
        if (sampleSize <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
        }
        double[] out = new double[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            out[i] = sample();
        }
        return out;
    }

    public double probability(double x) {
        return 0.0d;
    }

    public double logDensity(double x) {
        return FastMath.log(density(x));
    }
}
