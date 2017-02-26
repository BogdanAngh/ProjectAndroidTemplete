package org.apfloat;

import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;

public class AprationalMath {
    private AprationalMath() {
    }

    public static Aprational pow(Aprational x, long n) throws ArithmeticException, ApfloatRuntimeException {
        if (n != 0) {
            if (n < 0) {
                x = Aprational.ONE.divide(x);
                n = -n;
            }
            int b2pow = 0;
            while ((n & 1) == 0) {
                b2pow++;
                n >>>= 1;
            }
            Aprational r = x;
            while (true) {
                n >>>= 1;
                if (n <= 0) {
                    break;
                }
                x = x.multiply(x);
                if ((n & 1) != 0) {
                    r = r.multiply(x);
                }
            }
            int b2pow2 = b2pow;
            while (true) {
                b2pow = b2pow2 - 1;
                if (b2pow2 <= 0) {
                    return r;
                }
                r = r.multiply(r);
                b2pow2 = b2pow;
            }
        } else if (x.signum() != 0) {
            return new Apint(1, x.radix());
        } else {
            throw new ArithmeticException("Zero to power zero");
        }
    }

    @Deprecated
    public static Aprational negate(Aprational x) throws ApfloatRuntimeException {
        return x.negate();
    }

    public static Aprational abs(Aprational x) throws ApfloatRuntimeException {
        return x.signum() >= 0 ? x : x.negate();
    }

    public static Aprational copySign(Aprational x, Aprational y) throws ApfloatRuntimeException {
        if (y.signum() == 0) {
            return y;
        }
        return x.signum() != y.signum() ? x.negate() : x;
    }

    public static Aprational scale(Aprational x, long scale) throws ApfloatRuntimeException {
        if (scale >= 0) {
            return new Aprational(ApintMath.scale(x.numerator(), scale), x.denominator());
        }
        if (scale != Long.MIN_VALUE) {
            return new Aprational(x.numerator(), ApintMath.scale(x.denominator(), -scale));
        }
        Apint scaler = ApintMath.pow(new Apint((long) x.radix(), x.radix()), Longs.MAX_POWER_OF_TWO);
        return new Aprational(x.numerator(), x.denominator().multiply(scaler)).divide(scaler);
    }

    public static Apfloat round(Aprational x, long precision, RoundingMode roundingMode) throws IllegalArgumentException, ArithmeticException, ApfloatRuntimeException {
        return RoundingHelper.round(x, precision, roundingMode);
    }

    public static Aprational product(Aprational... x) throws ApfloatRuntimeException {
        if (x.length == 0) {
            return Aprational.ONE;
        }
        Apint[] n = new Apint[x.length];
        Apint[] m = new Apint[x.length];
        for (int i = 0; i < x.length; i++) {
            if (x[i].signum() == 0) {
                return Aprational.ZERO;
            }
            n[i] = x[i].numerator();
            m[i] = x[i].denominator();
        }
        return new Aprational(ApintMath.product(n), ApintMath.product(m));
    }

    public static Aprational sum(Aprational... x) throws ApfloatRuntimeException {
        if (x.length == 0) {
            return Aprational.ZERO;
        }
        x = (Aprational[]) x.clone();
        Arrays.sort(x, new 1());
        return recursiveSum(x, 0, x.length - 1);
    }

    private static Aprational recursiveSum(Aprational[] x, int n, int m) throws ApfloatRuntimeException {
        if (n == m) {
            return x[n];
        }
        int k = (n + m) >>> 1;
        return recursiveSum(x, n, k).add(recursiveSum(x, k + 1, m));
    }
}
