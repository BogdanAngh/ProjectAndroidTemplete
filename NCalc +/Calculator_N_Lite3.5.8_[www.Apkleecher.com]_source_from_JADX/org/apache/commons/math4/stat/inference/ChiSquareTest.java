package org.apache.commons.math4.stat.inference;

import org.apache.commons.math4.distribution.ChiSquaredDistribution;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class ChiSquareTest {
    public double chiSquare(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException {
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
            double sumSq = 0.0d;
            for (i = 0; i < observed.length; i++) {
                double dev;
                if (rescale) {
                    dev = ((double) observed[i]) - (expected[i] * ratio);
                    sumSq += (dev * dev) / (expected[i] * ratio);
                } else {
                    dev = ((double) observed[i]) - expected[i];
                    sumSq += (dev * dev) / expected[i];
                }
            }
            return sumSq;
        }
    }

    public double chiSquareTest(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
        return 1.0d - new ChiSquaredDistribution(null, ((double) expected.length) - 1.0d).cumulativeProbability(chiSquare(expected, observed));
    }

    public boolean chiSquareTest(double[] expected, long[] observed, double alpha) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, OutOfRangeException, MaxCountExceededException {
        if (alpha <= 0.0d || alpha > 0.5d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5d));
        } else if (chiSquareTest(expected, observed) < alpha) {
            return true;
        } else {
            return false;
        }
    }

    public double chiSquare(long[][] counts) throws NullArgumentException, NotPositiveException, DimensionMismatchException {
        int row;
        int col;
        checkArray(counts);
        int nRows = counts.length;
        int nCols = counts[0].length;
        double[] rowSum = new double[nRows];
        double[] colSum = new double[nCols];
        double total = 0.0d;
        for (row = 0; row < nRows; row++) {
            for (col = 0; col < nCols; col++) {
                rowSum[row] = rowSum[row] + ((double) counts[row][col]);
                colSum[col] = colSum[col] + ((double) counts[row][col]);
                total += (double) counts[row][col];
            }
        }
        double sumSq = 0.0d;
        for (row = 0; row < nRows; row++) {
            for (col = 0; col < nCols; col++) {
                double expected = (rowSum[row] * colSum[col]) / total;
                sumSq += ((((double) counts[row][col]) - expected) * (((double) counts[row][col]) - expected)) / expected;
            }
        }
        return sumSq;
    }

    public double chiSquareTest(long[][] counts) throws NullArgumentException, DimensionMismatchException, NotPositiveException, MaxCountExceededException {
        checkArray(counts);
        return 1.0d - new ChiSquaredDistribution((((double) counts.length) - 1.0d) * (((double) counts[0].length) - 1.0d)).cumulativeProbability(chiSquare(counts));
    }

    public boolean chiSquareTest(long[][] counts, double alpha) throws NullArgumentException, DimensionMismatchException, NotPositiveException, OutOfRangeException, MaxCountExceededException {
        if (alpha <= 0.0d || alpha > 0.5d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5d));
        } else if (chiSquareTest(counts) < alpha) {
            return true;
        } else {
            return false;
        }
    }

    public double chiSquareDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException {
        int length = observed1.length;
        if (r0 < 2) {
            throw new DimensionMismatchException(observed1.length, 2);
        }
        if (observed1.length != observed2.length) {
            throw new DimensionMismatchException(observed1.length, observed2.length);
        }
        MathArrays.checkNonNegative(observed1);
        MathArrays.checkNonNegative(observed2);
        long countSum1 = 0;
        long countSum2 = 0;
        double weight = 0.0d;
        int i = 0;
        while (true) {
            length = observed1.length;
            if (i >= r0) {
                break;
            }
            countSum1 += observed1[i];
            countSum2 += observed2[i];
            i++;
        }
        if (countSum1 == 0 || countSum2 == 0) {
            throw new ZeroException();
        }
        boolean unequalCounts = countSum1 != countSum2;
        if (unequalCounts) {
            weight = FastMath.sqrt(((double) countSum1) / ((double) countSum2));
        }
        double sumSq = 0.0d;
        i = 0;
        while (true) {
            length = observed1.length;
            if (i < r0) {
                if (observed1[i] == 0 && observed2[i] == 0) {
                    break;
                }
                double dev;
                double obs1 = (double) observed1[i];
                double obs2 = (double) observed2[i];
                if (unequalCounts) {
                    dev = (obs1 / weight) - (obs2 * weight);
                } else {
                    dev = obs1 - obs2;
                }
                sumSq += (dev * dev) / (obs1 + obs2);
                i++;
            } else {
                return sumSq;
            }
        }
        throw new ZeroException(LocalizedFormats.OBSERVED_COUNTS_BOTTH_ZERO_FOR_ENTRY, Integer.valueOf(i));
    }

    public double chiSquareTestDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException, MaxCountExceededException {
        return 1.0d - new ChiSquaredDistribution(null, ((double) observed1.length) - 1.0d).cumulativeProbability(chiSquareDataSetsComparison(observed1, observed2));
    }

    public boolean chiSquareTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws DimensionMismatchException, NotPositiveException, ZeroException, OutOfRangeException, MaxCountExceededException {
        if (alpha <= 0.0d || alpha > 0.5d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5d));
        } else if (chiSquareTestDataSetsComparison(observed1, observed2) < alpha) {
            return true;
        } else {
            return false;
        }
    }

    private void checkArray(long[][] in) throws NullArgumentException, DimensionMismatchException, NotPositiveException {
        if (in.length < 2) {
            throw new DimensionMismatchException(in.length, 2);
        } else if (in[0].length < 2) {
            throw new DimensionMismatchException(in[0].length, 2);
        } else {
            MathArrays.checkRectangular(in);
            MathArrays.checkNonNegative(in);
        }
    }
}
