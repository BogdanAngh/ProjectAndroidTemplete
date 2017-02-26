package org.apache.commons.math4.distribution;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Erf;
import org.apache.commons.math4.util.FastMath;

public class NormalDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final double SQRT2;
    private static final long serialVersionUID = 8589540077390120676L;
    private final double logStandardDeviationPlusHalfLog2Pi;
    private final double mean;
    private final double solverAbsoluteAccuracy;
    private final double standardDeviation;

    static {
        SQRT2 = FastMath.sqrt(2.0d);
    }

    public NormalDistribution() {
        this(SQRT2, 1.0d);
    }

    public NormalDistribution(double mean, double sd) throws NotStrictlyPositiveException {
        this(mean, sd, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public NormalDistribution(double mean, double sd, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        this(new Well19937c(), mean, sd, inverseCumAccuracy);
    }

    public NormalDistribution(RandomGenerator rng, double mean, double sd) throws NotStrictlyPositiveException {
        this(rng, mean, sd, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public NormalDistribution(RandomGenerator rng, double mean, double sd, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);
        if (sd <= SQRT2) {
            throw new NotStrictlyPositiveException(LocalizedFormats.STANDARD_DEVIATION, Double.valueOf(sd));
        }
        this.mean = mean;
        this.standardDeviation = sd;
        this.logStandardDeviationPlusHalfLog2Pi = FastMath.log(sd) + (0.5d * FastMath.log(Angle2D.M_2PI));
        this.solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    public double getMean() {
        return this.mean;
    }

    public double getStandardDeviation() {
        return this.standardDeviation;
    }

    public double density(double x) {
        return FastMath.exp(logDensity(x));
    }

    public double logDensity(double x) {
        double x1 = (x - this.mean) / this.standardDeviation;
        return ((-0.5d * x1) * x1) - this.logStandardDeviationPlusHalfLog2Pi;
    }

    public double cumulativeProbability(double x) {
        double dev = x - this.mean;
        if (FastMath.abs(dev) <= 40.0d * this.standardDeviation) {
            return 0.5d * (1.0d + Erf.erf(dev / (this.standardDeviation * SQRT2)));
        }
        if (dev < SQRT2) {
            return SQRT2;
        }
        return 1.0d;
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p >= SQRT2 && p <= 1.0d) {
            return this.mean + ((this.standardDeviation * SQRT2) * Erf.erfInv((2.0d * p) - 1.0d));
        }
        throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
    }

    public double probability(double x0, double x1) throws NumberIsTooLargeException {
        if (x0 > x1) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, Double.valueOf(x0), Double.valueOf(x1), true);
        }
        double denom = this.standardDeviation * SQRT2;
        return 0.5d * Erf.erf((x0 - this.mean) / denom, (x1 - this.mean) / denom);
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        return getMean();
    }

    public double getNumericalVariance() {
        double s = getStandardDeviation();
        return s * s;
    }

    public double getSupportLowerBound() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public double sample() {
        return (this.standardDeviation * this.random.nextGaussian()) + this.mean;
    }
}
