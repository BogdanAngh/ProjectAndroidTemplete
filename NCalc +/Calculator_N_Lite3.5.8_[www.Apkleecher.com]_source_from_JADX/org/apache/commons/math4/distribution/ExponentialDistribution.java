package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.CombinatoricsUtils;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.ResizableDoubleArray;

public class ExponentialDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final double[] EXPONENTIAL_SA_QI;
    private static final long serialVersionUID = 2401296428283614780L;
    private final double logMean;
    private final double mean;
    private final double solverAbsoluteAccuracy;

    static {
        double LN2 = FastMath.log(2.0d);
        double qi = 0.0d;
        int i = 1;
        ResizableDoubleArray ra = new ResizableDoubleArray(20);
        while (qi < 1.0d) {
            qi += FastMath.pow(LN2, i) / ((double) CombinatoricsUtils.factorial(i));
            ra.addElement(qi);
            i++;
        }
        EXPONENTIAL_SA_QI = ra.getElements();
    }

    public ExponentialDistribution(double mean) {
        this(mean, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public ExponentialDistribution(double mean, double inverseCumAccuracy) {
        this(new Well19937c(), mean, inverseCumAccuracy);
    }

    public ExponentialDistribution(RandomGenerator rng, double mean) throws NotStrictlyPositiveException {
        this(rng, mean, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public ExponentialDistribution(RandomGenerator rng, double mean, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);
        if (mean <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.MEAN, Double.valueOf(mean));
        }
        this.mean = mean;
        this.logMean = FastMath.log(mean);
        this.solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    public double getMean() {
        return this.mean;
    }

    public double density(double x) {
        double logDensity = logDensity(x);
        return logDensity == Double.NEGATIVE_INFINITY ? 0.0d : FastMath.exp(logDensity);
    }

    public double logDensity(double x) {
        if (x < 0.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        return ((-x) / this.mean) - this.logMean;
    }

    public double cumulativeProbability(double x) {
        if (x <= 0.0d) {
            return 0.0d;
        }
        return 1.0d - FastMath.exp((-x) / this.mean);
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0d), Double.valueOf(1.0d));
        } else if (p == 1.0d) {
            return Double.POSITIVE_INFINITY;
        } else {
            return (-this.mean) * FastMath.log(1.0d - p);
        }
    }

    public double sample() {
        double a = 0.0d;
        double u = this.random.nextDouble();
        while (u < 0.5d) {
            a += EXPONENTIAL_SA_QI[0];
            u *= 2.0d;
        }
        u += u - 1.0d;
        if (u <= EXPONENTIAL_SA_QI[0]) {
            return this.mean * (a + u);
        }
        int i = 0;
        double umin = this.random.nextDouble();
        do {
            i++;
            double u2 = this.random.nextDouble();
            if (u2 < umin) {
                umin = u2;
            }
        } while (u > EXPONENTIAL_SA_QI[i]);
        return this.mean * ((EXPONENTIAL_SA_QI[0] * umin) + a);
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        return getMean();
    }

    public double getNumericalVariance() {
        double m = getMean();
        return m * m;
    }

    public double getSupportLowerBound() {
        return 0.0d;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportConnected() {
        return true;
    }
}
