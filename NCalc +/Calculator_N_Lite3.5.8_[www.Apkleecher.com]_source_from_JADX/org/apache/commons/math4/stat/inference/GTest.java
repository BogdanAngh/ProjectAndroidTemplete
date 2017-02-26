package org.apache.commons.math4.stat.inference;

import java.lang.reflect.Array;
import org.apache.commons.math4.distribution.ChiSquaredDistribution;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class GTest {
    public double g(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException {
        if (expected.length < 2) {
            throw new DimensionMismatchException(expected.length, 2);
        } else if (expected.length != observed.length) {
            throw new DimensionMismatchException(expected.length, observed.length);
        } else {
            int i;
            MathArrays.checkPositive(expected);
            MathArrays.checkNonNegative(observed);
            double sumExpected = 0.0d;
            double sumObserved = 0.0d;
            for (i = 0; i < observed.length; i++) {
                sumExpected += expected[i];
                sumObserved += (double) observed[i];
            }
            double ratio = 1.0d;
            boolean rescale = false;
            if (FastMath.abs(sumExpected - sumObserved) > 1.0E-5d) {
                ratio = sumObserved / sumExpected;
                rescale = true;
            }
            double sum = 0.0d;
            for (i = 0; i < observed.length; i++) {
                double dev;
                if (rescale) {
                    dev = FastMath.log(((double) observed[i]) / (expected[i] * ratio));
                } else {
                    dev = FastMath.log(((double) observed[i]) / expected[i]);
                }
                sum += ((double) observed[i]) * dev;
            }
            return 2.0d * sum;
        }
    }

    public double gTest(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
        return 1.0d - new ChiSquaredDistribution(null, ((double) expected.length) - 1.0d).cumulativeProbability(g(expected, observed));
    }

    public double gTestIntrinsic(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
        return 1.0d - new ChiSquaredDistribution(null, ((double) expected.length) - 2.0d).cumulativeProbability(g(expected, observed));
    }

    public boolean gTest(double[] expected, long[] observed, double alpha) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, OutOfRangeException, MaxCountExceededException {
        if (alpha <= 0.0d || alpha > 0.5d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5d));
        } else if (gTest(expected, observed) < alpha) {
            return true;
        } else {
            return false;
        }
    }

    private double entropy(long[][] k) {
        int i;
        int j;
        double h = 0.0d;
        double sum_k = 0.0d;
        for (i = 0; i < k.length; i++) {
            for (long j2 : k[i]) {
                sum_k += (double) j2;
            }
        }
        for (i = 0; i < k.length; i++) {
            for (j = 0; j < k[i].length; j++) {
                if (k[i][j] != 0) {
                    double p_ij = ((double) k[i][j]) / sum_k;
                    h += FastMath.log(p_ij) * p_ij;
                }
            }
        }
        return -h;
    }

    private double entropy(long[] k) {
        int i;
        double h = 0.0d;
        double sum_k = 0.0d;
        for (long j : k) {
            sum_k += (double) j;
        }
        for (i = 0; i < k.length; i++) {
            if (k[i] != 0) {
                double p_i = ((double) k[i]) / sum_k;
                h += FastMath.log(p_i) * p_i;
            }
        }
        return -h;
    }

    public double gDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException {
        if (observed1.length < 2) {
            throw new DimensionMismatchException(observed1.length, 2);
        } else if (observed1.length != observed2.length) {
            throw new DimensionMismatchException(observed1.length, observed2.length);
        } else {
            MathArrays.checkNonNegative(observed1);
            MathArrays.checkNonNegative(observed2);
            long countSum1 = 0;
            long countSum2 = 0;
            long[] collSums = new long[observed1.length];
            long[][] k = (long[][]) Array.newInstance(Long.TYPE, new int[]{2, observed1.length});
            int i = 0;
            while (i < observed1.length) {
                if (observed1[i] == 0 && observed2[i] == 0) {
                    throw new ZeroException(LocalizedFormats.OBSERVED_COUNTS_BOTTH_ZERO_FOR_ENTRY, Integer.valueOf(i));
                }
                countSum1 += observed1[i];
                countSum2 += observed2[i];
                collSums[i] = observed1[i] + observed2[i];
                k[0][i] = observed1[i];
                k[1][i] = observed2[i];
                i++;
            }
            if (countSum1 == 0 || countSum2 == 0) {
                throw new ZeroException();
            }
            return (2.0d * (((double) countSum1) + ((double) countSum2))) * ((entropy(new long[]{countSum1, countSum2}) + entropy(collSums)) - entropy(k));
        }
    }

    public double rootLogLikelihoodRatio(long k11, long k12, long k21, long k22) {
        double sqrt = FastMath.sqrt(gDataSetsComparison(new long[]{k11, k12}, new long[]{k21, k22}));
        if (((double) k11) / ((double) (k11 + k12)) < ((double) k21) / ((double) (k21 + k22))) {
            return -sqrt;
        }
        return sqrt;
    }

    public double gTestDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException, MaxCountExceededException {
        return 1.0d - new ChiSquaredDistribution(null, ((double) observed1.length) - 1.0d).cumulativeProbability(gDataSetsComparison(observed1, observed2));
    }

    public boolean gTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws DimensionMismatchException, NotPositiveException, ZeroException, OutOfRangeException, MaxCountExceededException {
        if (alpha <= 0.0d || alpha > 0.5d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5d));
        } else if (gTestDataSetsComparison(observed1, observed2) < alpha) {
            return true;
        } else {
            return false;
        }
    }
}
