package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import edu.hws.jcm.data.ExpressionProgram;
import edu.jas.ps.UnivPowerSeriesRing;
import io.github.kexanie.library.R;
import java.math.RoundingMode;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.stat.descriptive.DescriptiveStatistics;
import org.matheclipse.core.interfaces.IExpr;

@GwtCompatible(emulated = true)
public final class LongMath {
    @VisibleForTesting
    static final long FLOOR_SQRT_MAX_LONG = 3037000499L;
    @VisibleForTesting
    static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;
    static final int[] biggestBinomials;
    @VisibleForTesting
    static final int[] biggestSimpleBinomials;
    static final long[] factorials;
    @GwtIncompatible("TODO")
    @VisibleForTesting
    static final long[] halfPowersOf10;
    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros;
    @GwtIncompatible("TODO")
    @VisibleForTesting
    static final long[] powersOf10;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            $SwitchMap$java$math$RoundingMode = new int[RoundingMode.values().length];
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public static boolean isPowerOfTwo(long x) {
        int i = 1;
        int i2 = x > 0 ? 1 : 0;
        if (((x - 1) & x) != 0) {
            i = 0;
        }
        return i & i2;
    }

    @VisibleForTesting
    static int lessThanBranchFree(long x, long y) {
        return (int) ((((x - y) ^ -1) ^ -1) >>> 63);
    }

    public static int log2(long x, RoundingMode mode) {
        MathPreconditions.checkPositive(UnivPowerSeriesRing.DEFAULT_NAME, x);
        switch (1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
                break;
            case IExpr.DOUBLEID /*2*/:
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
            case ValueServer.CONSTANT_MODE /*5*/:
                return 64 - Long.numberOfLeadingZeros(x - 1);
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
            case IExpr.INTEGERID /*8*/:
                int leadingZeros = Long.numberOfLeadingZeros(x);
                return lessThanBranchFree(MAX_POWER_OF_SQRT2_UNSIGNED >>> leadingZeros, x) + (63 - leadingZeros);
            default:
                throw new AssertionError("impossible");
        }
        return 63 - Long.numberOfLeadingZeros(x);
    }

    @GwtIncompatible("TODO")
    public static int log10(long x, RoundingMode mode) {
        MathPreconditions.checkPositive(UnivPowerSeriesRing.DEFAULT_NAME, x);
        int logFloor = log10Floor(x);
        long floorPow = powersOf10[logFloor];
        switch (1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                boolean z;
                if (x == floorPow) {
                    z = true;
                } else {
                    z = false;
                }
                MathPreconditions.checkRoundingUnnecessary(z);
                return logFloor;
            case IExpr.DOUBLEID /*2*/:
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return logFloor;
            case IExpr.DOUBLECOMPLEXID /*4*/:
            case ValueServer.CONSTANT_MODE /*5*/:
                return logFloor + lessThanBranchFree(floorPow, x);
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
            case IExpr.INTEGERID /*8*/:
                return logFloor + lessThanBranchFree(halfPowersOf10[logFloor], x);
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible("TODO")
    static int log10Floor(long x) {
        int y = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(x)];
        return y - lessThanBranchFree(x, powersOf10[y]);
    }

    static {
        maxLog10ForLeadingZeros = new byte[]{Ascii.XOFF, Ascii.DC2, Ascii.DC2, Ascii.DC2, Ascii.DC2, Ascii.XON, Ascii.XON, Ascii.XON, Ascii.DLE, Ascii.DLE, Ascii.DLE, Ascii.SI, Ascii.SI, Ascii.SI, Ascii.SI, Ascii.SO, Ascii.SO, Ascii.SO, Ascii.CR, Ascii.CR, Ascii.CR, Ascii.FF, Ascii.FF, Ascii.FF, Ascii.FF, Ascii.VT, Ascii.VT, Ascii.VT, (byte) 10, (byte) 10, (byte) 10, (byte) 9, (byte) 9, (byte) 9, (byte) 9, (byte) 8, (byte) 8, (byte) 8, (byte) 7, (byte) 7, (byte) 7, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 5, (byte) 5, (byte) 4, (byte) 4, (byte) 4, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0};
        powersOf10 = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};
        halfPowersOf10 = new long[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
        factorials = new long[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
        biggestBinomials = new int[]{BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_seekBarStyle, com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_autoCompleteTextViewStyle, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
        biggestSimpleBinomials = new int[]{BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, 169, 139, 119, com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_checkedTextViewStyle, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
    }

    @GwtIncompatible("TODO")
    public static long pow(long b, int k) {
        long j = 0;
        MathPreconditions.checkNonNegative("exponent", k);
        if (-2 > b || b > 2) {
            long accum = 1;
            while (true) {
                switch (k) {
                    case ValueServer.DIGEST_MODE /*0*/:
                        return accum;
                    case ValueServer.REPLAY_MODE /*1*/:
                        return accum * b;
                    default:
                        if ((k & 1) == 0) {
                            j = 1;
                        } else {
                            j = b;
                        }
                        accum *= j;
                        b *= b;
                        k >>= 1;
                }
            }
        } else {
            switch ((int) b) {
                case ExpressionProgram.MINUS /*-2*/:
                    if (k < 64) {
                        return (k & 1) == 0 ? 1 << k : -(1 << k);
                    } else {
                        return 0;
                    }
                case DescriptiveStatistics.INFINITE_WINDOW /*-1*/:
                    if ((k & 1) != 0) {
                        return -1;
                    }
                    return 1;
                case ValueServer.DIGEST_MODE /*0*/:
                    if (k == 0) {
                        return 1;
                    }
                    return 0;
                case ValueServer.REPLAY_MODE /*1*/:
                    return 1;
                case IExpr.DOUBLEID /*2*/:
                    if (k < 64) {
                        j = 1 << k;
                    }
                    return j;
                default:
                    throw new AssertionError();
            }
        }
    }

    @GwtIncompatible("TODO")
    public static long sqrt(long x, RoundingMode mode) {
        MathPreconditions.checkNonNegative(UnivPowerSeriesRing.DEFAULT_NAME, x);
        if (fitsInInt(x)) {
            return (long) IntMath.sqrt((int) x, mode);
        }
        long guess = (long) Math.sqrt((double) x);
        long guessSquared = guess * guess;
        switch (1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                MathPreconditions.checkRoundingUnnecessary(guessSquared == x);
                return guess;
            case IExpr.DOUBLEID /*2*/:
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                if (x < guessSquared) {
                    return guess - 1;
                }
                return guess;
            case IExpr.DOUBLECOMPLEXID /*4*/:
            case ValueServer.CONSTANT_MODE /*5*/:
                if (x > guessSquared) {
                    return guess + 1;
                }
                return guess;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
            case IExpr.INTEGERID /*8*/:
                long sqrtFloor = guess - ((long) (x < guessSquared ? 1 : 0));
                return sqrtFloor + ((long) lessThanBranchFree((sqrtFloor * sqrtFloor) + sqrtFloor, x));
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible("TODO")
    public static long divide(long p, long q, RoundingMode mode) {
        Preconditions.checkNotNull(mode);
        long div = p / q;
        long rem = p - (q * div);
        if (rem == 0) {
            return div;
        }
        boolean increment;
        int signum = ((int) ((p ^ q) >> 63)) | 1;
        switch (1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                MathPreconditions.checkRoundingUnnecessary(rem == 0);
                break;
            case IExpr.DOUBLEID /*2*/:
                break;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                increment = signum < 0;
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                increment = true;
                break;
            case ValueServer.CONSTANT_MODE /*5*/:
                increment = signum > 0;
                break;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
            case IExpr.INTEGERID /*8*/:
                long absRem = Math.abs(rem);
                long cmpRemToHalfDivisor = absRem - (Math.abs(q) - absRem);
                if (cmpRemToHalfDivisor != 0) {
                    increment = cmpRemToHalfDivisor > 0;
                    break;
                }
                increment = (mode == RoundingMode.HALF_UP ? 1 : 0) | (((1 & div) != 0 ? 1 : 0) & (mode == RoundingMode.HALF_EVEN ? 1 : 0));
                break;
            default:
                throw new AssertionError();
        }
        increment = false;
        if (increment) {
            return div + ((long) signum);
        }
        return div;
    }

    @GwtIncompatible("TODO")
    public static int mod(long x, int m) {
        return (int) mod(x, (long) m);
    }

    @GwtIncompatible("TODO")
    public static long mod(long x, long m) {
        if (m <= 0) {
            throw new ArithmeticException("Modulus must be positive");
        }
        long result = x % m;
        return result >= 0 ? result : result + m;
    }

    public static long gcd(long a, long b) {
        MathPreconditions.checkNonNegative("a", a);
        MathPreconditions.checkNonNegative("b", b);
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        int aTwos = Long.numberOfTrailingZeros(a);
        a >>= aTwos;
        int bTwos = Long.numberOfTrailingZeros(b);
        b >>= bTwos;
        while (a != b) {
            long delta = a - b;
            long minDeltaOrZero = delta & (delta >> 63);
            a = (delta - minDeltaOrZero) - minDeltaOrZero;
            b += minDeltaOrZero;
            a >>= Long.numberOfTrailingZeros(a);
        }
        return a << Math.min(aTwos, bTwos);
    }

    @GwtIncompatible("TODO")
    public static long checkedAdd(long a, long b) {
        int i;
        int i2 = 1;
        long result = a + b;
        if ((a ^ b) < 0) {
            i = 1;
        } else {
            i = 0;
        }
        if ((a ^ result) < 0) {
            i2 = 0;
        }
        MathPreconditions.checkNoOverflow(i2 | i);
        return result;
    }

    @GwtIncompatible("TODO")
    public static long checkedSubtract(long a, long b) {
        int i;
        int i2 = 1;
        long result = a - b;
        if ((a ^ b) >= 0) {
            i = 1;
        } else {
            i = 0;
        }
        if ((a ^ result) < 0) {
            i2 = 0;
        }
        MathPreconditions.checkNoOverflow(i2 | i);
        return result;
    }

    @GwtIncompatible("TODO")
    public static long checkedMultiply(long a, long b) {
        boolean z = false;
        int leadingZeros = ((Long.numberOfLeadingZeros(a) + Long.numberOfLeadingZeros(a ^ -1)) + Long.numberOfLeadingZeros(b)) + Long.numberOfLeadingZeros(b ^ -1);
        if (leadingZeros > 65) {
            return a * b;
        }
        boolean z2;
        int i;
        if (leadingZeros >= 64) {
            z2 = true;
        } else {
            z2 = false;
        }
        MathPreconditions.checkNoOverflow(z2);
        if (a >= 0) {
            i = 1;
        } else {
            i = 0;
        }
        MathPreconditions.checkNoOverflow((b != Long.MIN_VALUE ? 1 : 0) | i);
        long result = a * b;
        if (a == 0 || result / a == b) {
            z = true;
        }
        MathPreconditions.checkNoOverflow(z);
        return result;
    }

    @GwtIncompatible("TODO")
    public static long checkedPow(long b, int k) {
        boolean z = true;
        MathPreconditions.checkNonNegative("exponent", k);
        if (((b <= 2 ? 1 : 0) & (b >= -2 ? 1 : 0)) != 0) {
            switch ((int) b) {
                case ExpressionProgram.MINUS /*-2*/:
                    if (k >= 64) {
                        z = false;
                    }
                    MathPreconditions.checkNoOverflow(z);
                    return (k & 1) == 0 ? 1 << k : -1 << k;
                case DescriptiveStatistics.INFINITE_WINDOW /*-1*/:
                    if ((k & 1) != 0) {
                        return -1;
                    }
                    return 1;
                case ValueServer.DIGEST_MODE /*0*/:
                    if (k == 0) {
                        return 1;
                    }
                    return 0;
                case ValueServer.REPLAY_MODE /*1*/:
                    return 1;
                case IExpr.DOUBLEID /*2*/:
                    boolean z2;
                    if (k < 63) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    MathPreconditions.checkNoOverflow(z2);
                    return 1 << k;
                default:
                    throw new AssertionError();
            }
        }
        long accum = 1;
        while (true) {
            switch (k) {
                case ValueServer.DIGEST_MODE /*0*/:
                    return accum;
                case ValueServer.REPLAY_MODE /*1*/:
                    return checkedMultiply(accum, b);
                default:
                    if ((k & 1) != 0) {
                        accum = checkedMultiply(accum, b);
                    }
                    k >>= 1;
                    if (k > 0) {
                        boolean z3;
                        if (b <= FLOOR_SQRT_MAX_LONG) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        MathPreconditions.checkNoOverflow(z3);
                        b *= b;
                    }
            }
        }
    }

    @GwtIncompatible("TODO")
    public static long factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        return n < factorials.length ? factorials[n] : Long.MAX_VALUE;
    }

    public static long binomial(int n, int k) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", k);
        Preconditions.checkArgument(k <= n, "k (%s) > n (%s)", Integer.valueOf(k), Integer.valueOf(n));
        if (k > (n >> 1)) {
            k = n - k;
        }
        switch (k) {
            case ValueServer.DIGEST_MODE /*0*/:
                return 1;
            case ValueServer.REPLAY_MODE /*1*/:
                return (long) n;
            default:
                if (n < factorials.length) {
                    return factorials[n] / (factorials[k] * factorials[n - k]);
                }
                if (k >= biggestBinomials.length || n > biggestBinomials[k]) {
                    return Long.MAX_VALUE;
                }
                long result;
                int i;
                if (k >= biggestSimpleBinomials.length || n > biggestSimpleBinomials[k]) {
                    int nBits = log2((long) n, RoundingMode.CEILING);
                    result = 1;
                    long numerator = (long) n;
                    long denominator = 1;
                    int numeratorBits = nBits;
                    i = 2;
                    n--;
                    while (i <= k) {
                        if (numeratorBits + nBits < 63) {
                            numerator *= (long) n;
                            denominator *= (long) i;
                            numeratorBits += nBits;
                        } else {
                            result = multiplyFraction(result, numerator, denominator);
                            numerator = (long) n;
                            denominator = (long) i;
                            numeratorBits = nBits;
                        }
                        i++;
                        n--;
                    }
                    return multiplyFraction(result, numerator, denominator);
                }
                result = (long) n;
                n--;
                for (i = 2; i <= k; i++) {
                    result = (result * ((long) n)) / ((long) i);
                    n--;
                }
                return result;
        }
    }

    static long multiplyFraction(long x, long numerator, long denominator) {
        if (x == 1) {
            return numerator / denominator;
        }
        long commonDivisor = gcd(x, denominator);
        return (numerator / (denominator / commonDivisor)) * (x / commonDivisor);
    }

    static boolean fitsInInt(long x) {
        return ((long) ((int) x)) == x;
    }

    public static long mean(long x, long y) {
        return (x & y) + ((x ^ y) >> 1);
    }

    private LongMath() {
    }
}
