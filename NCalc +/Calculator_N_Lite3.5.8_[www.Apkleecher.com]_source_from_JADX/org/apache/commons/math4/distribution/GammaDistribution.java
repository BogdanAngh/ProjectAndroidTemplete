package org.apache.commons.math4.distribution;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Gamma;
import org.apache.commons.math4.util.FastMath;

public class GammaDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = 20120524;
    private final double densityPrefactor1;
    private final double densityPrefactor2;
    private final double logDensityPrefactor1;
    private final double logDensityPrefactor2;
    private final double maxLogY;
    private final double minY;
    private final double scale;
    private final double shape;
    private final double shiftedShape;
    private final double solverAbsoluteAccuracy;

    public GammaDistribution(double shape, double scale) throws NotStrictlyPositiveException {
        this(shape, scale, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public GammaDistribution(double shape, double scale, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        this(new Well19937c(), shape, scale, inverseCumAccuracy);
    }

    public GammaDistribution(RandomGenerator rng, double shape, double scale) throws NotStrictlyPositiveException {
        this(rng, shape, scale, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public GammaDistribution(RandomGenerator rng, double shape, double scale, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng);
        if (shape <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, Double.valueOf(shape));
        } else if (scale <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(scale));
        } else {
            this.shape = shape;
            this.scale = scale;
            this.solverAbsoluteAccuracy = inverseCumAccuracy;
            this.shiftedShape = (Gamma.LANCZOS_G + shape) + 0.5d;
            double aux = FastMath.E / (Angle2D.M_2PI * this.shiftedShape);
            this.densityPrefactor2 = (FastMath.sqrt(aux) * shape) / Gamma.lanczos(shape);
            this.logDensityPrefactor2 = (FastMath.log(shape) + (0.5d * FastMath.log(aux))) - FastMath.log(Gamma.lanczos(shape));
            this.densityPrefactor1 = ((this.densityPrefactor2 / scale) * FastMath.pow(this.shiftedShape, -shape)) * FastMath.exp(Gamma.LANCZOS_G + shape);
            this.logDensityPrefactor1 = (((this.logDensityPrefactor2 - FastMath.log(scale)) - (FastMath.log(this.shiftedShape) * shape)) + shape) + Gamma.LANCZOS_G;
            this.minY = (Gamma.LANCZOS_G + shape) - FastMath.log(Double.MAX_VALUE);
            this.maxLogY = FastMath.log(Double.MAX_VALUE) / (shape - 1.0d);
        }
    }

    public double getShape() {
        return this.shape;
    }

    public double getScale() {
        return this.scale;
    }

    public double density(double x) {
        if (x < 0.0d) {
            return 0.0d;
        }
        double y = x / this.scale;
        if (y > this.minY && FastMath.log(y) < this.maxLogY) {
            return (this.densityPrefactor1 * FastMath.exp(-y)) * FastMath.pow(y, this.shape - 1.0d);
        }
        double aux1 = (y - this.shiftedShape) / this.shiftedShape;
        return (this.densityPrefactor2 / x) * FastMath.exp(((((-y) * 5.2421875d) / this.shiftedShape) + Gamma.LANCZOS_G) + (this.shape * (FastMath.log1p(aux1) - aux1)));
    }

    public double logDensity(double x) {
        if (x < 0.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        double y = x / this.scale;
        if (y > this.minY && FastMath.log(y) < this.maxLogY) {
            return (this.logDensityPrefactor1 - y) + (FastMath.log(y) * (this.shape - 1.0d));
        }
        double aux1 = (y - this.shiftedShape) / this.shiftedShape;
        return (this.logDensityPrefactor2 - FastMath.log(x)) + (((((-y) * 5.2421875d) / this.shiftedShape) + Gamma.LANCZOS_G) + (this.shape * (FastMath.log1p(aux1) - aux1)));
    }

    public double cumulativeProbability(double x) {
        if (x <= 0.0d) {
            return 0.0d;
        }
        return Gamma.regularizedGammaP(this.shape, x / this.scale);
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        return this.shape * this.scale;
    }

    public double getNumericalVariance() {
        return (this.shape * this.scale) * this.scale;
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

    public double sample() {
        double u;
        double x;
        if (this.shape < 1.0d) {
            while (true) {
                u = this.random.nextDouble();
                double bGS = 1.0d + (this.shape / FastMath.E);
                double p = bGS * u;
                if (p <= 1.0d) {
                    x = FastMath.pow(p, 1.0d / this.shape);
                    if (this.random.nextDouble() <= FastMath.exp(-x)) {
                        return this.scale * x;
                    }
                } else {
                    x = -1.0d * FastMath.log((bGS - p) / this.shape);
                    if (this.random.nextDouble() <= FastMath.pow(x, this.shape - 1.0d)) {
                        return this.scale * x;
                    }
                }
            }
        } else {
            double d = this.shape - 0.3333333333333333d;
            double c = 1.0d / (3.0d * FastMath.sqrt(d));
            while (true) {
                x = this.random.nextGaussian();
                double v = ((1.0d + (c * x)) * (1.0d + (c * x))) * (1.0d + (c * x));
                if (v > 0.0d) {
                    double x2 = x * x;
                    u = this.random.nextDouble();
                    if (u < 1.0d - ((0.0331d * x2) * x2)) {
                        return (this.scale * d) * v;
                    } else if (FastMath.log(u) < (0.5d * x2) + (((1.0d - v) + FastMath.log(v)) * d)) {
                        return (this.scale * d) * v;
                    }
                }
            }
        }
    }
}
