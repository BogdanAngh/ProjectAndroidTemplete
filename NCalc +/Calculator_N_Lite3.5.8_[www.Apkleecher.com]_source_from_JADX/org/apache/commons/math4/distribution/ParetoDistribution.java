package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class ParetoDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = 20130424;
    private final double scale;
    private final double shape;
    private final double solverAbsoluteAccuracy;

    public ParetoDistribution() {
        this(1.0d, 1.0d);
    }

    public ParetoDistribution(double scale, double shape) throws NotStrictlyPositiveException {
        this(scale, shape, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public ParetoDistribution(double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        this(new Well19937c(), scale, shape, inverseCumAccuracy);
    }

    public ParetoDistribution(RandomGenerator rng, double scale, double shape) throws NotStrictlyPositiveException {
        this(rng, scale, shape, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public ParetoDistribution(RandomGenerator rng, double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);
        if (scale <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(scale));
        } else if (shape <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, Double.valueOf(shape));
        } else {
            this.scale = scale;
            this.shape = shape;
            this.solverAbsoluteAccuracy = inverseCumAccuracy;
        }
    }

    public double getScale() {
        return this.scale;
    }

    public double getShape() {
        return this.shape;
    }

    public double density(double x) {
        if (x < this.scale) {
            return 0.0d;
        }
        return (FastMath.pow(this.scale, this.shape) / FastMath.pow(x, this.shape + 1.0d)) * this.shape;
    }

    public double logDensity(double x) {
        if (x < this.scale) {
            return Double.NEGATIVE_INFINITY;
        }
        return ((FastMath.log(this.scale) * this.shape) - (FastMath.log(x) * (this.shape + 1.0d))) + FastMath.log(this.shape);
    }

    public double cumulativeProbability(double x) {
        if (x <= this.scale) {
            return 0.0d;
        }
        return 1.0d - FastMath.pow(this.scale / x, this.shape);
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        if (this.shape <= 1.0d) {
            return Double.POSITIVE_INFINITY;
        }
        return (this.shape * this.scale) / (this.shape - 1.0d);
    }

    public double getNumericalVariance() {
        if (this.shape <= 2.0d) {
            return Double.POSITIVE_INFINITY;
        }
        double s = this.shape - 1.0d;
        return (((this.scale * this.scale) * this.shape) / (s * s)) / (this.shape - 2.0d);
    }

    public double getSupportLowerBound() {
        return this.scale;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public double sample() {
        return this.scale / FastMath.pow(this.random.nextDouble(), 1.0d / this.shape);
    }
}
