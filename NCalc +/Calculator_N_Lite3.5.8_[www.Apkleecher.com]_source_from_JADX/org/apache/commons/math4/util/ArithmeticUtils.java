package org.apache.commons.math4.util;

import java.math.BigInteger;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public final class ArithmeticUtils {
    private ArithmeticUtils() {
    }

    public static int addAndCheck(int x, int y) throws MathArithmeticException {
        long s = ((long) x) + ((long) y);
        if (s >= -2147483648L && s <= 2147483647L) {
            return (int) s;
        }
        throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, Integer.valueOf(x), Integer.valueOf(y));
    }

    public static long addAndCheck(long a, long b) throws MathArithmeticException {
        return addAndCheck(a, b, LocalizedFormats.OVERFLOW_IN_ADDITION);
    }

    public static int gcd(int p, int q) throws MathArithmeticException {
        int a = p;
        int b = q;
        if (a != 0 && b != 0) {
            long al = (long) a;
            long bl = (long) b;
            boolean useLong = false;
            if (a < 0) {
                if (RtlSpacingHelper.UNDEFINED == a) {
                    useLong = true;
                } else {
                    a = -a;
                }
                al = -al;
            }
            if (b < 0) {
                if (RtlSpacingHelper.UNDEFINED == b) {
                    useLong = true;
                } else {
                    b = -b;
                }
                bl = -bl;
            }
            if (useLong) {
                if (al == bl) {
                    throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, Integer.valueOf(p), Integer.valueOf(q));
                }
                long blbu = bl;
                bl = al;
                al = blbu % al;
                if (al != 0) {
                    b = (int) al;
                    a = (int) (bl % al);
                } else if (bl <= 2147483647L) {
                    return (int) bl;
                } else {
                    throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, Integer.valueOf(p), Integer.valueOf(q));
                }
            }
            return gcdPositive(a, b);
        } else if (a != RtlSpacingHelper.UNDEFINED && b != RtlSpacingHelper.UNDEFINED) {
            return FastMath.abs(a + b);
        } else {
            throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, Integer.valueOf(p), Integer.valueOf(q));
        }
    }

    private static int gcdPositive(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        int aTwos = Integer.numberOfTrailingZeros(a);
        a >>= aTwos;
        int bTwos = Integer.numberOfTrailingZeros(b);
        b >>= bTwos;
        int shift = FastMath.min(aTwos, bTwos);
        while (a != b) {
            int delta = a - b;
            b = Math.min(a, b);
            a = Math.abs(delta);
            a >>= Integer.numberOfTrailingZeros(a);
        }
        return a << shift;
    }

    public static long gcd(long p, long q) throws MathArithmeticException {
        long u = p;
        long v = q;
        if (u != 0 && v != 0) {
            if (u > 0) {
                u = -u;
            }
            if (v > 0) {
                v = -v;
            }
            int k = 0;
            while ((1 & u) == 0 && (1 & v) == 0 && k < 63) {
                u /= 2;
                v /= 2;
                k++;
            }
            if (k == 63) {
                throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, Long.valueOf(p), Long.valueOf(q));
            }
            long t = (1 & u) == 1 ? v : -(u / 2);
            while (true) {
                if ((1 & t) != 0) {
                    if (t > 0) {
                        u = -t;
                    } else {
                        v = t;
                    }
                    t = (v - u) / 2;
                    if (t == 0) {
                        return (-u) * (1 << k);
                    }
                } else {
                    t /= 2;
                }
            }
        } else if (u != Long.MIN_VALUE && v != Long.MIN_VALUE) {
            return FastMath.abs(u) + FastMath.abs(v);
        } else {
            throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, Long.valueOf(p), Long.valueOf(q));
        }
    }

    public static int lcm(int a, int b) throws MathArithmeticException {
        if (a == 0 || b == 0) {
            return 0;
        }
        int lcm = FastMath.abs(mulAndCheck(a / gcd(a, b), b));
        if (lcm != RtlSpacingHelper.UNDEFINED) {
            return lcm;
        }
        throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_32_BITS, Integer.valueOf(a), Integer.valueOf(b));
    }

    public static long lcm(long a, long b) throws MathArithmeticException {
        long j = 0;
        if (!(a == 0 || b == 0)) {
            j = FastMath.abs(mulAndCheck(a / gcd(a, b), b));
            if (j == Long.MIN_VALUE) {
                throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_64_BITS, Long.valueOf(a), Long.valueOf(b));
            }
        }
        return j;
    }

    public static int mulAndCheck(int x, int y) throws MathArithmeticException {
        long m = ((long) x) * ((long) y);
        if (m >= -2147483648L && m <= 2147483647L) {
            return (int) m;
        }
        throw new MathArithmeticException();
    }

    public static long mulAndCheck(long a, long b) throws MathArithmeticException {
        if (a > b) {
            return mulAndCheck(b, a);
        }
        if (a < 0) {
            if (b < 0) {
                if (a >= Long.MAX_VALUE / b) {
                    return a * b;
                }
                throw new MathArithmeticException();
            } else if (b <= 0) {
                return 0;
            } else {
                if (Long.MIN_VALUE / b <= a) {
                    return a * b;
                }
                throw new MathArithmeticException();
            }
        } else if (a <= 0) {
            return 0;
        } else {
            if (a <= Long.MAX_VALUE / b) {
                return a * b;
            }
            throw new MathArithmeticException();
        }
    }

    public static int subAndCheck(int x, int y) throws MathArithmeticException {
        long s = ((long) x) - ((long) y);
        if (s >= -2147483648L && s <= 2147483647L) {
            return (int) s;
        }
        throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, Integer.valueOf(x), Integer.valueOf(y));
    }

    public static long subAndCheck(long a, long b) throws MathArithmeticException {
        if (b != Long.MIN_VALUE) {
            return addAndCheck(a, -b, LocalizedFormats.OVERFLOW_IN_ADDITION);
        }
        if (a < 0) {
            return a - b;
        }
        throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, Long.valueOf(a), Long.valueOf(-b));
    }

    public static int pow(int k, int e) throws NotPositiveException, MathArithmeticException {
        if (e < 0) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, Integer.valueOf(e));
        }
        int exp = e;
        int result = 1;
        int k2p = k;
        while (true) {
            if ((exp & 1) != 0) {
                try {
                    result = mulAndCheck(result, k2p);
                } catch (MathArithmeticException mae) {
                    mae.getContext().addMessage(LocalizedFormats.OVERFLOW, new Object[0]);
                    mae.getContext().addMessage(LocalizedFormats.BASE, Integer.valueOf(k));
                    mae.getContext().addMessage(LocalizedFormats.EXPONENT, Integer.valueOf(e));
                    throw mae;
                }
            }
            exp >>= 1;
            if (exp == 0) {
                return result;
            }
            k2p = mulAndCheck(k2p, k2p);
        }
    }

    public static long pow(long k, int e) throws NotPositiveException, MathArithmeticException {
        if (e < 0) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, Integer.valueOf(e));
        }
        int exp = e;
        long result = 1;
        long k2p = k;
        while (true) {
            if ((exp & 1) != 0) {
                try {
                    result = mulAndCheck(result, k2p);
                } catch (MathArithmeticException mae) {
                    mae.getContext().addMessage(LocalizedFormats.OVERFLOW, new Object[0]);
                    mae.getContext().addMessage(LocalizedFormats.BASE, Long.valueOf(k));
                    mae.getContext().addMessage(LocalizedFormats.EXPONENT, Integer.valueOf(e));
                    throw mae;
                }
            }
            exp >>= 1;
            if (exp == 0) {
                return result;
            }
            k2p = mulAndCheck(k2p, k2p);
        }
    }

    public static BigInteger pow(BigInteger k, int e) throws NotPositiveException {
        if (e >= 0) {
            return k.pow(e);
        }
        throw new NotPositiveException(LocalizedFormats.EXPONENT, Integer.valueOf(e));
    }

    public static BigInteger pow(BigInteger k, long e) throws NotPositiveException {
        if (e < 0) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, Long.valueOf(e));
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = k;
        while (e != 0) {
            if ((1 & e) != 0) {
                result = result.multiply(k2p);
            }
            k2p = k2p.multiply(k2p);
            e >>= 1;
        }
        return result;
    }

    public static BigInteger pow(BigInteger k, BigInteger e) throws NotPositiveException {
        if (e.compareTo(BigInteger.ZERO) < 0) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = k;
        while (!BigInteger.ZERO.equals(e)) {
            if (e.testBit(0)) {
                result = result.multiply(k2p);
            }
            k2p = k2p.multiply(k2p);
            e = e.shiftRight(1);
        }
        return result;
    }

    private static long addAndCheck(long a, long b, Localizable pattern) throws MathArithmeticException {
        int i;
        long result = a + b;
        if ((a ^ b) < 0) {
            i = 1;
        } else {
            i = 0;
        }
        if ((((a ^ result) >= 0 ? 1 : 0) | i) != 0) {
            return result;
        }
        throw new MathArithmeticException(pattern, Long.valueOf(a), Long.valueOf(b));
    }

    public static boolean isPowerOfTwo(long n) {
        return n > 0 && ((n - 1) & n) == 0;
    }
}
