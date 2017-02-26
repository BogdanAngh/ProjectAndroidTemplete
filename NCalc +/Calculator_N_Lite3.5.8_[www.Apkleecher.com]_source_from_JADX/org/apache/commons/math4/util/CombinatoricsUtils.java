package org.apache.commons.math4.util;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public final class CombinatoricsUtils {
    static final long[] FACTORIALS;
    static final AtomicReference<long[][]> STIRLING_S2;

    static {
        FACTORIALS = new long[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
        STIRLING_S2 = new AtomicReference(null);
    }

    private CombinatoricsUtils() {
    }

    public static long binomialCoefficient(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
        checkBinomial(n, k);
        if (n == k || k == 0) {
            return 1;
        }
        if (k == 1 || k == n - 1) {
            return (long) n;
        }
        if (k > n / 2) {
            return binomialCoefficient(n, n - k);
        }
        long result = 1;
        int i;
        int j;
        if (n <= 61) {
            i = (n - k) + 1;
            for (j = 1; j <= k; j++) {
                result = (((long) i) * result) / ((long) j);
                i++;
            }
            return result;
        } else if (n <= 66) {
            i = (n - k) + 1;
            for (j = 1; j <= k; j++) {
                d = (long) ArithmeticUtils.gcd(i, j);
                result = (result / (((long) j) / d)) * (((long) i) / d);
                i++;
            }
            return result;
        } else {
            i = (n - k) + 1;
            for (j = 1; j <= k; j++) {
                d = (long) ArithmeticUtils.gcd(i, j);
                result = ArithmeticUtils.mulAndCheck(result / (((long) j) / d), ((long) i) / d);
                i++;
            }
            return result;
        }
    }

    public static double binomialCoefficientDouble(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
        checkBinomial(n, k);
        if (n == k || k == 0) {
            return 1.0d;
        }
        if (k == 1 || k == n - 1) {
            return (double) n;
        }
        if (k > n / 2) {
            return binomialCoefficientDouble(n, n - k);
        }
        if (n < 67) {
            return (double) binomialCoefficient(n, k);
        }
        double result = 1.0d;
        for (int i = 1; i <= k; i++) {
            result *= ((double) ((n - k) + i)) / ((double) i);
        }
        return FastMath.floor(0.5d + result);
    }

    public static double binomialCoefficientLog(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
        checkBinomial(n, k);
        if (n == k || k == 0) {
            return 0.0d;
        }
        if (k == 1 || k == n - 1) {
            return FastMath.log((double) n);
        }
        if (n < 67) {
            return FastMath.log((double) binomialCoefficient(n, k));
        }
        if (n < 1030) {
            return FastMath.log(binomialCoefficientDouble(n, k));
        }
        if (k > n / 2) {
            return binomialCoefficientLog(n, n - k);
        }
        int i;
        double logSum = 0.0d;
        for (i = (n - k) + 1; i <= n; i++) {
            logSum += FastMath.log((double) i);
        }
        for (i = 2; i <= k; i++) {
            logSum -= FastMath.log((double) i);
        }
        return logSum;
    }

    public static long factorial(int n) throws NotPositiveException, MathArithmeticException {
        if (n < 0) {
            throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, Integer.valueOf(n));
        } else if (n <= 20) {
            return FACTORIALS[n];
        } else {
            throw new MathArithmeticException();
        }
    }

    public static double factorialDouble(int n) throws NotPositiveException {
        if (n < 0) {
            throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, Integer.valueOf(n));
        } else if (n < 21) {
            return (double) FACTORIALS[n];
        } else {
            return FastMath.floor(FastMath.exp(factorialLog(n)) + 0.5d);
        }
    }

    public static double factorialLog(int n) throws NotPositiveException {
        if (n < 0) {
            throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, Integer.valueOf(n));
        } else if (n < 21) {
            return FastMath.log((double) FACTORIALS[n]);
        } else {
            double logSum = 0.0d;
            for (int i = 2; i <= n; i++) {
                logSum += FastMath.log((double) i);
            }
            return logSum;
        }
    }

    public static long stirlingS2(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
        if (k < 0) {
            throw new NotPositiveException(Integer.valueOf(k));
        } else if (k > n) {
            throw new NumberIsTooLargeException(Integer.valueOf(k), Integer.valueOf(n), true);
        } else {
            int j;
            long[][] stirlingS2 = (long[][]) STIRLING_S2.get();
            if (stirlingS2 == null) {
                stirlingS2 = new long[26][];
                stirlingS2[0] = new long[]{1};
                for (int i = 1; i < stirlingS2.length; i++) {
                    stirlingS2[i] = new long[(i + 1)];
                    stirlingS2[i][0] = 0;
                    stirlingS2[i][1] = 1;
                    stirlingS2[i][i] = 1;
                    for (j = 2; j < i; j++) {
                        stirlingS2[i][j] = (((long) j) * stirlingS2[i - 1][j]) + stirlingS2[i - 1][j - 1];
                    }
                }
                STIRLING_S2.compareAndSet(null, stirlingS2);
            }
            if (n < stirlingS2.length) {
                return stirlingS2[n][k];
            }
            if (k == 0) {
                return 0;
            }
            if (k == 1 || k == n) {
                return 1;
            }
            if (k == 2) {
                return (1 << (n - 1)) - 1;
            }
            if (k == n - 1) {
                return binomialCoefficient(n, 2);
            }
            long sum = 0;
            long sign = (long) ((k & 1) == 0 ? 1 : -1);
            for (j = 1; j <= k; j++) {
                sign = -sign;
                sum += (binomialCoefficient(k, j) * sign) * ((long) ArithmeticUtils.pow(j, n));
                if (sum < 0) {
                    throw new MathArithmeticException(LocalizedFormats.ARGUMENT_OUTSIDE_DOMAIN, Integer.valueOf(n), Integer.valueOf(0), Integer.valueOf(stirlingS2.length - 1));
                }
            }
            return sum / factorial(k);
        }
    }

    public static Iterator<int[]> combinationsIterator(int n, int k) {
        return new Combinations(n, k).iterator();
    }

    public static void checkBinomial(int n, int k) throws NumberIsTooLargeException, NotPositiveException {
        if (n < k) {
            throw new NumberIsTooLargeException(LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, Integer.valueOf(k), Integer.valueOf(n), true);
        } else if (n < 0) {
            throw new NotPositiveException(LocalizedFormats.BINOMIAL_NEGATIVE_PARAMETER, Integer.valueOf(n));
        }
    }
}
