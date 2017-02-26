package org.apache.commons.math4.analysis.interpolation;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.math4.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NotFiniteNumberException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class LoessInterpolator implements UnivariateInterpolator, Serializable {
    public static final double DEFAULT_ACCURACY = 1.0E-12d;
    public static final double DEFAULT_BANDWIDTH = 0.3d;
    public static final int DEFAULT_ROBUSTNESS_ITERS = 2;
    private static final long serialVersionUID = 5204927143605193821L;
    private final double accuracy;
    private final double bandwidth;
    private final int robustnessIters;

    public LoessInterpolator() {
        this.bandwidth = DEFAULT_BANDWIDTH;
        this.robustnessIters = DEFAULT_ROBUSTNESS_ITERS;
        this.accuracy = DEFAULT_ACCURACY;
    }

    public LoessInterpolator(double bandwidth, int robustnessIters) {
        this(bandwidth, robustnessIters, DEFAULT_ACCURACY);
    }

    public LoessInterpolator(double bandwidth, int robustnessIters, double accuracy) throws OutOfRangeException, NotPositiveException {
        if (bandwidth < 0.0d || bandwidth > 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.BANDWIDTH, Double.valueOf(bandwidth), Integer.valueOf(0), Integer.valueOf(1));
        }
        this.bandwidth = bandwidth;
        if (robustnessIters < 0) {
            throw new NotPositiveException(LocalizedFormats.ROBUSTNESS_ITERATIONS, Integer.valueOf(robustnessIters));
        }
        this.robustnessIters = robustnessIters;
        this.accuracy = accuracy;
    }

    public final PolynomialSplineFunction interpolate(double[] xval, double[] yval) throws NonMonotonicSequenceException, DimensionMismatchException, NoDataException, NotFiniteNumberException, NumberIsTooSmallException {
        return new SplineInterpolator().interpolate(xval, smooth(xval, yval));
    }

    public final double[] smooth(double[] xval, double[] yval, double[] weights) throws NonMonotonicSequenceException, DimensionMismatchException, NoDataException, NotFiniteNumberException, NumberIsTooSmallException {
        if (xval.length != yval.length) {
            throw new DimensionMismatchException(xval.length, yval.length);
        }
        int n = xval.length;
        if (n == 0) {
            throw new NoDataException();
        }
        checkAllFiniteReal(xval);
        checkAllFiniteReal(yval);
        checkAllFiniteReal(weights);
        MathArrays.checkOrder(xval);
        if (n == 1) {
            return new double[]{yval[0]};
        } else if (n == DEFAULT_ROBUSTNESS_ITERS) {
            r35 = new double[DEFAULT_ROBUSTNESS_ITERS];
            r35[0] = yval[0];
            r35[1] = yval[1];
            return r35;
        } else {
            int bandwidthInPoints = (int) (this.bandwidth * ((double) n));
            if (bandwidthInPoints < DEFAULT_ROBUSTNESS_ITERS) {
                throw new NumberIsTooSmallException(LocalizedFormats.BANDWIDTH, Integer.valueOf(bandwidthInPoints), Integer.valueOf(DEFAULT_ROBUSTNESS_ITERS), true);
            }
            r35 = new double[n];
            Object residuals = new double[n];
            Object sortedResiduals = new double[n];
            double[] robustnessWeights = new double[n];
            Arrays.fill(robustnessWeights, 1.0d);
            for (int iter = 0; iter <= this.robustnessIters; iter++) {
                double w;
                int[] bandwidthInterval = new int[DEFAULT_ROBUSTNESS_ITERS];
                bandwidthInterval[1] = bandwidthInPoints - 1;
                int i = 0;
                while (i < n) {
                    int edge;
                    double beta;
                    double x = xval[i];
                    if (i > 0) {
                        updateBandwidthInterval(xval, weights, i, bandwidthInterval);
                    }
                    int ileft = bandwidthInterval[0];
                    int iright = bandwidthInterval[1];
                    if (xval[i] - xval[ileft] > xval[iright] - xval[i]) {
                        edge = ileft;
                    } else {
                        edge = iright;
                    }
                    double sumWeights = 0.0d;
                    double sumX = 0.0d;
                    double sumXSquared = 0.0d;
                    double sumY = 0.0d;
                    double sumXY = 0.0d;
                    double denom = FastMath.abs(1.0d / (xval[edge] - x));
                    int k = ileft;
                    while (k <= iright) {
                        double xk = xval[k];
                        double yk = yval[k];
                        w = (tricube((k < i ? x - xk : xk - x) * denom) * robustnessWeights[k]) * weights[k];
                        double xkw = xk * w;
                        sumWeights += w;
                        sumX += xkw;
                        sumXSquared += xk * xkw;
                        sumY += yk * w;
                        sumXY += yk * xkw;
                        k++;
                    }
                    double meanX = sumX / sumWeights;
                    double meanY = sumY / sumWeights;
                    double meanXY = sumXY / sumWeights;
                    double meanXSquared = sumXSquared / sumWeights;
                    if (FastMath.sqrt(FastMath.abs(meanXSquared - (meanX * meanX))) < this.accuracy) {
                        beta = 0.0d;
                    } else {
                        beta = (meanXY - (meanX * meanY)) / (meanXSquared - (meanX * meanX));
                    }
                    r35[i] = (beta * x) + (meanY - (beta * meanX));
                    residuals[i] = FastMath.abs(yval[i] - r35[i]);
                    i++;
                }
                if (iter == this.robustnessIters) {
                    return r35;
                }
                System.arraycopy(residuals, 0, sortedResiduals, 0, n);
                Arrays.sort(sortedResiduals);
                double medianResidual = sortedResiduals[n / DEFAULT_ROBUSTNESS_ITERS];
                if (FastMath.abs(medianResidual) < this.accuracy) {
                    return r35;
                }
                for (i = 0; i < n; i++) {
                    double arg = residuals[i] / (6.0d * medianResidual);
                    if (arg >= 1.0d) {
                        robustnessWeights[i] = 0.0d;
                    } else {
                        w = 1.0d - (arg * arg);
                        robustnessWeights[i] = w * w;
                    }
                }
            }
            return r35;
        }
    }

    public final double[] smooth(double[] xval, double[] yval) throws NonMonotonicSequenceException, DimensionMismatchException, NoDataException, NotFiniteNumberException, NumberIsTooSmallException {
        if (xval.length != yval.length) {
            throw new DimensionMismatchException(xval.length, yval.length);
        }
        double[] unitWeights = new double[xval.length];
        Arrays.fill(unitWeights, 1.0d);
        return smooth(xval, yval, unitWeights);
    }

    private static void updateBandwidthInterval(double[] xval, double[] weights, int i, int[] bandwidthInterval) {
        int left = bandwidthInterval[0];
        int nextRight = nextNonzero(weights, bandwidthInterval[1]);
        if (nextRight < xval.length && xval[nextRight] - xval[i] < xval[i] - xval[left]) {
            bandwidthInterval[0] = nextNonzero(weights, bandwidthInterval[0]);
            bandwidthInterval[1] = nextRight;
        }
    }

    private static int nextNonzero(double[] weights, int i) {
        int j = i + 1;
        while (j < weights.length && weights[j] == 0.0d) {
            j++;
        }
        return j;
    }

    private static double tricube(double x) {
        double absX = FastMath.abs(x);
        if (absX >= 1.0d) {
            return 0.0d;
        }
        double tmp = 1.0d - ((absX * absX) * absX);
        return (tmp * tmp) * tmp;
    }

    private static void checkAllFiniteReal(double[] values) {
        for (double checkFinite : values) {
            MathUtils.checkFinite(checkFinite);
        }
    }
}
