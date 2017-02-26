package org.apache.commons.math4.distribution;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Erf;
import org.apache.commons.math4.util.FastMath;

public class LogNormalDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final double SQRT2;
    private static final double SQRT2PI;
    private static final long serialVersionUID = 20120112;
    private final double logShapePlusHalfLog2Pi;
    private final double scale;
    private final double shape;
    private final double solverAbsoluteAccuracy;

    static {
        SQRT2PI = FastMath.sqrt(Angle2D.M_2PI);
        SQRT2 = FastMath.sqrt(2.0d);
    }

    public LogNormalDistribution() {
        this(SQRT2PI, 1.0d);
    }

    public LogNormalDistribution(double scale, double shape) throws NotStrictlyPositiveException {
        this(scale, shape, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public LogNormalDistribution(double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        this(new Well19937c(), scale, shape, inverseCumAccuracy);
    }

    public LogNormalDistribution(RandomGenerator rng, double scale, double shape) throws NotStrictlyPositiveException {
        this(rng, scale, shape, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public LogNormalDistribution(RandomGenerator rng, double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);
        if (shape <= SQRT2PI) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, Double.valueOf(shape));
        }
        this.scale = scale;
        this.shape = shape;
        this.logShapePlusHalfLog2Pi = FastMath.log(shape) + (0.5d * FastMath.log(Angle2D.M_2PI));
        this.solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    public double getScale() {
        return this.scale;
    }

    public double getShape() {
        return this.shape;
    }

    public double density(double x) {
        if (x <= SQRT2PI) {
            return SQRT2PI;
        }
        double x1 = (FastMath.log(x) - this.scale) / this.shape;
        return FastMath.exp((-0.5d * x1) * x1) / ((this.shape * SQRT2PI) * x);
    }

    public double logDensity(double x) {
        if (x <= SQRT2PI) {
            return Double.NEGATIVE_INFINITY;
        }
        double logX = FastMath.log(x);
        double x1 = (logX - this.scale) / this.shape;
        return ((-0.5d * x1) * x1) - (this.logShapePlusHalfLog2Pi + logX);
    }

    public double cumulativeProbability(double x) {
        if (x <= SQRT2PI) {
            return SQRT2PI;
        }
        double dev = FastMath.log(x) - this.scale;
        if (FastMath.abs(dev) <= 40.0d * this.shape) {
            return (Erf.erf(dev / (this.shape * SQRT2)) * 0.5d) + 0.5d;
        }
        if (dev >= SQRT2PI) {
            return 1.0d;
        }
        return SQRT2PI;
    }

    public double probability(double x0, double x1) throws NumberIsTooLargeException {
        if (x0 > x1) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, Double.valueOf(x0), Double.valueOf(x1), true);
        } else if (x0 <= SQRT2PI || x1 <= SQRT2PI) {
            return super.probability(x0, x1);
        } else {
            double denom = this.shape * SQRT2;
            return 0.5d * Erf.erf((FastMath.log(x0) - this.scale) / denom, (FastMath.log(x1) - this.scale) / denom);
        }
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        double s = this.shape;
        return FastMath.exp(this.scale + ((s * s) / 2.0d));
    }

    public double getNumericalVariance() {
        double s = this.shape;
        double ss = s * s;
        return FastMath.expm1(ss) * FastMath.exp((2.0d * this.scale) + ss);
    }

    public double getSupportLowerBound() {
        return SQRT2PI;
    }

    public double getSupportUpperBound() {
        return Double.POSITIVE_INFINITY;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public double sample() {
        return FastMath.exp(this.scale + (this.shape * this.random.nextGaussian()));
    }
}
