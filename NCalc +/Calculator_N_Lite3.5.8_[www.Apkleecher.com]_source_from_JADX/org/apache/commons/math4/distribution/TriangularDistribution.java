package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;

public class TriangularDistribution extends AbstractRealDistribution {
    private static final long serialVersionUID = 20120112;
    private final double a;
    private final double b;
    private final double c;
    private final double solverAbsoluteAccuracy;

    public TriangularDistribution(double a, double c, double b) throws NumberIsTooLargeException, NumberIsTooSmallException {
        this(new Well19937c(), a, c, b);
    }

    public TriangularDistribution(RandomGenerator rng, double a, double c, double b) throws NumberIsTooLargeException, NumberIsTooSmallException {
        super(rng);
        if (a >= b) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Double.valueOf(a), Double.valueOf(b), false);
        } else if (c < a) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_TOO_SMALL, Double.valueOf(c), Double.valueOf(a), true);
        } else if (c > b) {
            throw new NumberIsTooLargeException(LocalizedFormats.NUMBER_TOO_LARGE, Double.valueOf(c), Double.valueOf(b), true);
        } else {
            this.a = a;
            this.c = c;
            this.b = b;
            this.solverAbsoluteAccuracy = FastMath.max(FastMath.ulp(a), FastMath.ulp(b));
        }
    }

    public double getMode() {
        return this.c;
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }

    public double density(double x) {
        if (x < this.a) {
            return 0.0d;
        }
        if (this.a <= x && x < this.c) {
            return (2.0d * (x - this.a)) / ((this.b - this.a) * (this.c - this.a));
        }
        if (x == this.c) {
            return 2.0d / (this.b - this.a);
        }
        if (this.c >= x || x > this.b) {
            return 0.0d;
        }
        return (2.0d * (this.b - x)) / ((this.b - this.a) * (this.b - this.c));
    }

    public double cumulativeProbability(double x) {
        if (x < this.a) {
            return 0.0d;
        }
        if (this.a <= x && x < this.c) {
            return ((x - this.a) * (x - this.a)) / ((this.b - this.a) * (this.c - this.a));
        }
        if (x == this.c) {
            return (this.c - this.a) / (this.b - this.a);
        }
        if (this.c >= x || x > this.b) {
            return 1.0d;
        }
        return 1.0d - (((this.b - x) * (this.b - x)) / ((this.b - this.a) * (this.b - this.c)));
    }

    public double getNumericalMean() {
        return ((this.a + this.b) + this.c) / 3.0d;
    }

    public double getNumericalVariance() {
        return ((((((this.a * this.a) + (this.b * this.b)) + (this.c * this.c)) - (this.a * this.b)) - (this.a * this.c)) - (this.b * this.c)) / 18.0d;
    }

    public double getSupportLowerBound() {
        return this.a;
    }

    public double getSupportUpperBound() {
        return this.b;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
        } else if (p == 0.0d) {
            return this.a;
        } else {
            if (p == 1.0d) {
                return this.b;
            }
            if (p < (this.c - this.a) / (this.b - this.a)) {
                return this.a + FastMath.sqrt(((this.b - this.a) * p) * (this.c - this.a));
            }
            return this.b - FastMath.sqrt(((1.0d - p) * (this.b - this.a)) * (this.b - this.c));
        }
    }
}
