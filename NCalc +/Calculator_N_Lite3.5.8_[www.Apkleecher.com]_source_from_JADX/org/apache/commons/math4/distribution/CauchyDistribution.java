package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class CauchyDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = 8589540077390120676L;
    private final double median;
    private final double scale;
    private final double solverAbsoluteAccuracy;

    public CauchyDistribution() {
        this(0.0d, 1.0d);
    }

    public CauchyDistribution(double median, double scale) {
        this(median, scale, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public CauchyDistribution(double median, double scale, double inverseCumAccuracy) {
        this(new Well19937c(), median, scale, inverseCumAccuracy);
    }

    public CauchyDistribution(RandomGenerator rng, double median, double scale) {
        this(rng, median, scale, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public CauchyDistribution(RandomGenerator rng, double median, double scale, double inverseCumAccuracy) {
        super(rng);
        if (scale <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(scale));
        }
        this.scale = scale;
        this.median = median;
        this.solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    public double cumulativeProbability(double x) {
        return 0.5d + (FastMath.atan((x - this.median) / this.scale) / FastMath.PI);
    }

    public double getMedian() {
        return this.median;
    }

    public double getScale() {
        return this.scale;
    }

    public double density(double x) {
        double dev = x - this.median;
        return 0.3183098861837907d * (this.scale / ((dev * dev) + (this.scale * this.scale)));
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
        } else if (p == 0.0d) {
            return Double.NEGATIVE_INFINITY;
        } else {
            if (p == 1.0d) {
                return Double.POSITIVE_INFINITY;
            }
            return this.median + (this.scale * FastMath.tan(FastMath.PI * (p - 0.5d)));
        }
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        return Double.NaN;
    }

    public double getNumericalVariance() {
        return Double.NaN;
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
}
