package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Beta;
import org.apache.commons.math4.special.Gamma;
import org.apache.commons.math4.util.FastMath;

public class BetaDistribution extends AbstractRealDistribution {
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9d;
    private static final long serialVersionUID = -1221965979403477668L;
    private final double alpha;
    private final double beta;
    private final double solverAbsoluteAccuracy;
    private double z;

    public BetaDistribution(double alpha, double beta) {
        this(alpha, beta, (double) DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public BetaDistribution(double alpha, double beta, double inverseCumAccuracy) {
        this(new Well19937c(), alpha, beta, inverseCumAccuracy);
    }

    public BetaDistribution(RandomGenerator rng, double alpha, double beta) {
        this(rng, alpha, beta, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public BetaDistribution(RandomGenerator rng, double alpha, double beta, double inverseCumAccuracy) {
        super(rng);
        this.alpha = alpha;
        this.beta = beta;
        this.z = Double.NaN;
        this.solverAbsoluteAccuracy = inverseCumAccuracy;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public double getBeta() {
        return this.beta;
    }

    private void recomputeZ() {
        if (Double.isNaN(this.z)) {
            this.z = (Gamma.logGamma(this.alpha) + Gamma.logGamma(this.beta)) - Gamma.logGamma(this.alpha + this.beta);
        }
    }

    public double density(double x) {
        double logDensity = logDensity(x);
        return logDensity == Double.NEGATIVE_INFINITY ? 0.0d : FastMath.exp(logDensity);
    }

    public double logDensity(double x) {
        recomputeZ();
        if (x < 0.0d || x > 1.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        if (x == 0.0d) {
            if (this.alpha >= 1.0d) {
                return Double.NEGATIVE_INFINITY;
            }
            throw new NumberIsTooSmallException(LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_0_FOR_SOME_ALPHA, Double.valueOf(this.alpha), Integer.valueOf(1), false);
        } else if (x != 1.0d) {
            return (((this.alpha - 1.0d) * FastMath.log(x)) + ((this.beta - 1.0d) * FastMath.log1p(-x))) - this.z;
        } else if (this.beta >= 1.0d) {
            return Double.NEGATIVE_INFINITY;
        } else {
            throw new NumberIsTooSmallException(LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_1_FOR_SOME_BETA, Double.valueOf(this.beta), Integer.valueOf(1), false);
        }
    }

    public double cumulativeProbability(double x) {
        if (x <= 0.0d) {
            return 0.0d;
        }
        if (x >= 1.0d) {
            return 1.0d;
        }
        return Beta.regularizedBeta(x, this.alpha, this.beta);
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double getNumericalMean() {
        double a = getAlpha();
        return a / (getBeta() + a);
    }

    public double getNumericalVariance() {
        double a = getAlpha();
        double b = getBeta();
        double alphabetasum = a + b;
        return (a * b) / ((alphabetasum * alphabetasum) * (1.0d + alphabetasum));
    }

    public double getSupportLowerBound() {
        return 0.0d;
    }

    public double getSupportUpperBound() {
        return 1.0d;
    }

    public boolean isSupportConnected() {
        return true;
    }
}
