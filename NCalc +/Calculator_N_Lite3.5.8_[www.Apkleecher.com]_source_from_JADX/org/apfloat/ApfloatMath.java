package org.apfloat;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import org.apfloat.spi.Util;

public class ApfloatMath {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Map<Integer, Apfloat> SHUTDOWN_MAP;
    private static Map<Integer, Apfloat> radixLog;
    private static ConcurrentMap<Integer, Integer> radixLogKeys;
    private static Map<Integer, Apfloat> radixLogPi;
    private static Map<Integer, Apfloat> radixPi;
    private static Map<Integer, PiCalculator> radixPiCalculator;
    private static Map<Integer, Apfloat> radixPiInverseRoot;
    private static ConcurrentMap<Integer, Integer> radixPiKeys;
    private static Map<Integer, Apfloat> radixPiP;
    private static Map<Integer, Apfloat> radixPiQ;
    private static Map<Integer, Apfloat> radixPiT;
    private static Map<Integer, Long> radixPiTerms;

    static {
        boolean z;
        if (ApfloatMath.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        SHUTDOWN_MAP = new ShutdownMap();
        radixPiKeys = new ConcurrentHashMap();
        radixPi = new ConcurrentSoftHashMap();
        radixPiCalculator = new Hashtable();
        radixPiT = new ConcurrentSoftHashMap();
        radixPiQ = new ConcurrentSoftHashMap();
        radixPiP = new ConcurrentSoftHashMap();
        radixPiInverseRoot = new ConcurrentSoftHashMap();
        radixPiTerms = new Hashtable();
        radixLogKeys = new ConcurrentHashMap();
        radixLog = new ConcurrentHashMap();
        radixLogPi = new ConcurrentHashMap();
    }

    private ApfloatMath() {
    }

    public static Apfloat pow(Apfloat x, long n) throws ArithmeticException, ApfloatRuntimeException {
        if (n != 0) {
            if (n < 0) {
                x = inverseRoot(x, 1);
                n = -n;
            }
            long precision = x.precision();
            x = ApfloatHelper.extendPrecision(x);
            int b2pow = 0;
            while ((1 & n) == 0) {
                b2pow++;
                n >>>= 1;
            }
            Apfloat r = x;
            while (true) {
                n >>>= 1;
                if (n <= 0) {
                    break;
                }
                x = x.multiply(x);
                if ((1 & n) != 0) {
                    r = r.multiply(x);
                }
            }
            int b2pow2 = b2pow;
            while (true) {
                b2pow = b2pow2 - 1;
                if (b2pow2 <= 0) {
                    return r.precision(precision);
                }
                r = r.multiply(r);
                b2pow2 = b2pow;
            }
        } else if (x.signum() != 0) {
            return new Apfloat(1, Long.MAX_VALUE, x.radix());
        } else {
            throw new ArithmeticException("Zero to power zero");
        }
    }

    public static Apfloat sqrt(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        return root(x, 2);
    }

    public static Apfloat cbrt(Apfloat x) throws ApfloatRuntimeException {
        return root(x, 3);
    }

    public static Apfloat root(Apfloat x, long n) throws ArithmeticException, ApfloatRuntimeException {
        if (n == 0) {
            throw new ArithmeticException("Zeroth root");
        } else if (x.signum() == 0) {
            return Apfloat.ZERO;
        } else {
            if (n == 1) {
                return x;
            }
            if (n == Long.MIN_VALUE) {
                return sqrt(inverseRoot(x, n / -2));
            }
            if (n < 0) {
                return inverseRoot(x, -n);
            }
            if (n == 2) {
                return x.multiply(inverseRoot(x, 2));
            }
            if (n == 3) {
                return x.multiply(inverseRoot(x.multiply(x), 3));
            }
            return inverseRoot(inverseRoot(x, n), 1);
        }
    }

    public static Apfloat inverseRoot(Apfloat x, long n) throws ArithmeticException, ApfloatRuntimeException {
        return inverseRoot(x, n, x.precision());
    }

    public static Apfloat inverseRoot(Apfloat x, long n, long targetPrecision) throws IllegalArgumentException, ArithmeticException, ApfloatRuntimeException {
        return inverseRoot(x, n, targetPrecision, null);
    }

    public static Apfloat inverseRoot(Apfloat x, long n, long targetPrecision, Apfloat initialGuess) throws IllegalArgumentException, ArithmeticException, ApfloatRuntimeException {
        return inverseRoot(x, n, targetPrecision, initialGuess, initialGuess == null ? 0 : initialGuess.precision());
    }

    public static Apfloat inverseRoot(Apfloat x, long n, long targetPrecision, Apfloat initialGuess, long initialPrecision) throws IllegalArgumentException, ArithmeticException, ApfloatRuntimeException {
        if (x.signum() == 0) {
            throw new ArithmeticException("Inverse root of zero");
        } else if (n == 0) {
            throw new ArithmeticException("Inverse zeroth root");
        } else if ((1 & n) == 0 && x.signum() < 0) {
            throw new ArithmeticException("Even root of negative number; result would be complex");
        } else if (targetPrecision <= 0) {
            throw new IllegalArgumentException("Target precision " + targetPrecision + " is not positive");
        } else {
            if (x.equals(Apfloat.ONE)) {
                return x.precision(targetPrecision);
            }
            if (targetPrecision == Long.MAX_VALUE) {
                throw new InfiniteExpansionException("Cannot calculate inverse root to infinite precision");
            } else if (n == Long.MIN_VALUE) {
                return inverseRoot(inverseRoot(x, n / -2), 2);
            } else if (n < 0) {
                return inverseRoot(inverseRoot(x, -n), 1);
            } else {
                Apfloat result;
                long precision;
                long doublePrecision = (long) ApfloatHelper.getDoublePrecision(x.radix());
                Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
                Apfloat divisor = new Apfloat(n, Long.MAX_VALUE, x.radix());
                if (initialGuess == null || initialPrecision < doublePrecision) {
                    long scaleQuot = x.scale() / n;
                    long scaleRem = x.scale() - (scaleQuot * n);
                    result = x.precision(doublePrecision);
                    Apfloat result2 = scale(result, -result.scale());
                    precision = doublePrecision;
                    result = scale(new Apfloat((((double) result2.signum()) * Math.pow(Math.abs(result2.doubleValue()), -1.0d / ((double) n))) * Math.pow((double) x.radix(), ((double) (-scaleRem)) / ((double) n)), precision, x.radix()), -scaleQuot);
                } else {
                    result = initialGuess;
                    precision = initialPrecision;
                }
                int iterations = 0;
                for (long maxPrec = precision; maxPrec < targetPrecision; maxPrec <<= 1) {
                    iterations++;
                }
                int precisingIteration = iterations;
                long minPrec = precision;
                while (precisingIteration > 0 && ((minPrec - 20) << precisingIteration) < targetPrecision) {
                    precisingIteration--;
                    minPrec <<= 1;
                }
                x = ApfloatHelper.extendPrecision(x);
                int iterations2 = iterations;
                while (true) {
                    iterations = iterations2 - 1;
                    if (iterations2 <= 0) {
                        return result.precision(targetPrecision);
                    }
                    precision *= 2;
                    result = result.precision(Math.min(precision, targetPrecision));
                    Apfloat t = one.subtract(x.multiply(lastIterationExtendPrecision(iterations, precisingIteration, pow(result, n))));
                    if (iterations < precisingIteration) {
                        t = t.precision(precision / 2);
                    }
                    result = lastIterationExtendPrecision(iterations, precisingIteration, result);
                    result = result.add(result.multiply(t).divide(divisor));
                    if (iterations == precisingIteration) {
                        t = lastIterationExtendPrecision(iterations, -1, pow(result, n));
                        result = lastIterationExtendPrecision(iterations, -1, result);
                        result = result.add(result.multiply(one.subtract(x.multiply(t))).divide(divisor));
                    }
                    iterations2 = iterations;
                }
            }
        }
    }

    public static Apint floor(Apfloat x) throws ApfloatRuntimeException {
        return x.floor();
    }

    public static Apint ceil(Apfloat x) throws ApfloatRuntimeException {
        return x.ceil();
    }

    public static Apint truncate(Apfloat x) throws ApfloatRuntimeException {
        return x.truncate();
    }

    public static Apfloat frac(Apfloat x) throws ApfloatRuntimeException {
        return x.frac();
    }

    public static Apfloat round(Apfloat x, long precision, RoundingMode roundingMode) throws IllegalArgumentException, ArithmeticException, ApfloatRuntimeException {
        return RoundingHelper.round(x, precision, roundingMode);
    }

    @Deprecated
    public static Apfloat negate(Apfloat x) throws ApfloatRuntimeException {
        return x.negate();
    }

    public static Apfloat abs(Apfloat x) throws ApfloatRuntimeException {
        return x.signum() >= 0 ? x : x.negate();
    }

    public static Apfloat copySign(Apfloat x, Apfloat y) throws ApfloatRuntimeException {
        if (y.signum() == 0) {
            return y;
        }
        return x.signum() != y.signum() ? x.negate() : x;
    }

    public static Apfloat scale(Apfloat x, long scale) throws ApfloatRuntimeException {
        if (scale == 0 || x.signum() == 0) {
            return x;
        }
        Apfloat radix = new Apfloat((long) x.radix(), Long.MAX_VALUE, x.radix());
        if ((Math.abs(scale) & -4611686018427387904L) != 0) {
            Apfloat scaler1 = pow(radix, Math.abs(scale) >>> 1);
            Apfloat scaler2 = (1 & scale) == 0 ? scaler1 : scaler1.multiply(radix);
            return scale >= 0 ? x.multiply(scaler1).multiply(scaler2) : x.divide(scaler1).divide(scaler2);
        } else if (x.radix() <= 14) {
            return x.multiply(new Apfloat("1e" + scale, Long.MAX_VALUE, x.radix()));
        } else {
            Apfloat scaler = pow(radix, Math.abs(scale));
            return scale >= 0 ? x.multiply(scaler) : x.divide(scaler);
        }
    }

    public static Apfloat[] modf(Apfloat x) throws ApfloatRuntimeException {
        Apfloat[] result = new Apfloat[2];
        result[0] = x.floor();
        result[1] = x.signum() >= 0 ? x.frac() : x.subtract(result[0]);
        return result;
    }

    public static Apfloat fmod(Apfloat x, Apfloat y) throws ApfloatRuntimeException {
        if (y.signum() == 0) {
            return y;
        }
        if (x.signum() == 0 || abs(x).compareTo(abs(y)) < 0) {
            return x;
        }
        if (x.precision() <= x.scale() - y.scale()) {
            return Apfloat.ZERO;
        }
        long precision = (x.scale() - y.scale()) + 20;
        Apfloat t = x.precision(precision).divide(y.precision(precision)).truncate();
        precision = Math.min(Util.ifFinite(y.precision(), (y.precision() + x.scale()) - y.scale()), x.precision());
        Apfloat tx = x.precision(precision);
        Apfloat ty = y.precision(precision);
        Apfloat a = abs(tx).subtract(abs(t.multiply(ty)));
        Apfloat b = abs(ty);
        if (a.compareTo(b) >= 0) {
            a = a.subtract(b);
        } else if (a.signum() < 0) {
            a = a.add(b);
        }
        return copySign(a, x);
    }

    public static Apfloat multiplyAdd(Apfloat a, Apfloat b, Apfloat c, Apfloat d) throws ApfloatRuntimeException {
        return multiplyAddOrSubtract(a, b, c, d, false);
    }

    public static Apfloat multiplySubtract(Apfloat a, Apfloat b, Apfloat c, Apfloat d) throws ApfloatRuntimeException {
        return multiplyAddOrSubtract(a, b, c, d, true);
    }

    private static Apfloat multiplyAddOrSubtract(Apfloat a, Apfloat b, Apfloat c, Apfloat d, boolean subtract) throws ApfloatRuntimeException {
        Apfloat ab;
        Apfloat cd;
        long[] precisions = ApfloatHelper.getMatchingPrecisions(a, b, c, d);
        if (precisions[0] == 0) {
            ab = Apfloat.ZERO;
        } else {
            ab = a.precision(precisions[0]).multiply(b.precision(precisions[0]));
        }
        if (precisions[1] == 0) {
            cd = Apfloat.ZERO;
        } else {
            cd = c.precision(precisions[1]).multiply(d.precision(precisions[1]));
        }
        Apfloat result = subtract ? ab.subtract(cd) : ab.add(cd);
        return result.signum() == 0 ? result : result.precision(precisions[2]);
    }

    public static Apfloat agm(Apfloat a, Apfloat b) throws ApfloatRuntimeException {
        if (a.signum() == 0 || b.signum() == 0) {
            return Apfloat.ZERO;
        }
        long workingPrecision = Math.min(a.precision(), b.precision());
        long targetPrecision = Math.max(a.precision(), b.precision());
        if (workingPrecision == Long.MAX_VALUE) {
            throw new InfiniteExpansionException("Cannot calculate agm to infinite precision");
        }
        workingPrecision = ApfloatHelper.extendPrecision(workingPrecision);
        a = ApfloatHelper.ensurePrecision(a, workingPrecision);
        b = ApfloatHelper.ensurePrecision(b, workingPrecision);
        long precision = 0;
        long halfWorkingPrecision = (1 + workingPrecision) / 2;
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, a.radix());
        while (precision < 1000 && precision < halfWorkingPrecision) {
            Apfloat t = a.add(b).divide(two);
            b = sqrt(a.multiply(b));
            a = ApfloatHelper.ensurePrecision(t, workingPrecision);
            b = ApfloatHelper.ensurePrecision(b, workingPrecision);
            precision = a.equalDigits(b);
        }
        while (precision <= halfWorkingPrecision) {
            t = a.add(b).divide(two);
            b = sqrt(a.multiply(b));
            a = ApfloatHelper.ensurePrecision(t, workingPrecision);
            b = ApfloatHelper.ensurePrecision(b, workingPrecision);
            precision *= 2;
        }
        return a.add(b).divide(two).precision(targetPrecision);
    }

    public static Apfloat pi(long precision) throws IllegalArgumentException, NumberFormatException, ApfloatRuntimeException {
        return pi(precision, ApfloatContext.getContext().getDefaultRadix());
    }

    public static Apfloat pi(long precision, int radix) throws IllegalArgumentException, NumberFormatException, ApfloatRuntimeException {
        if (precision <= 0) {
            throw new IllegalArgumentException("Precision " + precision + " is not positive");
        } else if (precision == Long.MAX_VALUE) {
            throw new InfiniteExpansionException("Cannot calculate pi to infinite precision");
        } else {
            Apfloat pi;
            Integer radixKey = getRadixPiKey(new Integer(radix));
            synchronized (radixKey) {
                pi = (Apfloat) radixPi.get(radixKey);
                if (pi == null || pi.precision() < precision) {
                    pi = calculatePi(precision, radixKey);
                } else {
                    pi = pi.precision(precision);
                }
            }
            return pi;
        }
    }

    private static Integer getRadixPiKey(Integer radix) {
        Integer radixKey = (Integer) radixPiKeys.putIfAbsent(radix, radix);
        if (radixKey == null) {
            return radix;
        }
        return radixKey;
    }

    private static Apfloat calculatePi(long precision, Integer radixKey) throws ApfloatRuntimeException {
        int radix = radixKey.intValue();
        PiCalculator piCalculator = (PiCalculator) radixPiCalculator.get(radixKey);
        if (piCalculator == null) {
            piCalculator = new PiCalculator(radix);
            radixPiCalculator.put(radixKey, piCalculator);
        }
        ApfloatHolder RT = new ApfloatHolder();
        ApfloatHolder RQ = new ApfloatHolder();
        ApfloatHolder RP = new ApfloatHolder();
        long neededTerms = (long) ((((double) precision) * Math.log((double) radix)) / 32.65445004177d);
        long workingPrecision = ApfloatHelper.extendPrecision(precision);
        Long terms = (Long) radixPiTerms.get(radixKey);
        Apfloat LT = (Apfloat) radixPiT.get(radixKey);
        Apfloat LQ = (Apfloat) radixPiQ.get(radixKey);
        Apfloat LP = (Apfloat) radixPiP.get(radixKey);
        Apfloat inverseRoot = (Apfloat) radixPiInverseRoot.get(radixKey);
        if (terms == null || LT == null || LQ == null || LP == null || inverseRoot == null) {
            piCalculator.r(0, neededTerms + 1, RT, RQ, RP);
            LT = RT.getApfloat();
            LQ = RQ.getApfloat();
            LP = RP.getApfloat();
            inverseRoot = inverseRoot(new Apfloat(1823176476672000L, workingPrecision, radix), 2);
        } else {
            long currentTerms = terms.longValue();
            if (currentTerms != 1 + neededTerms) {
                piCalculator.r(currentTerms, 1 + neededTerms, RT, RQ, RP);
                LT = RQ.getApfloat().multiply(LT).add(LP.multiply(RT.getApfloat()));
                LQ = LQ.multiply(RQ.getApfloat());
                LP = LP.multiply(RP.getApfloat());
            }
            inverseRoot = inverseRoot(new Apfloat(1823176476672000L, workingPrecision, radix), 2, workingPrecision, inverseRoot);
        }
        Apfloat pi = inverseRoot(inverseRoot.multiply(LT), 1).multiply(LQ);
        inverseRoot = inverseRoot.precision(precision);
        pi = pi.precision(precision);
        radixPiT.put(radixKey, LT);
        radixPiQ.put(radixKey, LQ);
        radixPiP.put(radixKey, LP);
        radixPiInverseRoot.put(radixKey, inverseRoot);
        radixPiTerms.put(radixKey, Long.valueOf(1 + neededTerms));
        radixPi.put(radixKey, pi);
        return pi;
    }

    public static Apfloat log(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        return log(x, true);
    }

    public static Apfloat log(Apfloat x, Apfloat b) throws ArithmeticException, ApfloatRuntimeException {
        long targetPrecision = Math.min(x.precision(), b.precision());
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        return log(x.precision(Math.min(x.precision(), Util.ifFinite(targetPrecision, one.equalDigits(x) + targetPrecision))), false).divide(log(b.precision(Math.min(b.precision(), Util.ifFinite(targetPrecision, one.equalDigits(b) + targetPrecision))), false));
    }

    private static Apfloat log(Apfloat x, boolean multiplyByPi) throws ArithmeticException, ApfloatRuntimeException {
        if (x.signum() <= 0) {
            throw new ArithmeticException("Logarithm of " + (x.signum() == 0 ? "zero" : "negative number; result would be complex"));
        } else if (x.equals(Apfloat.ONE)) {
            return Apfloat.ZERO;
        } else {
            Apfloat radixPower;
            long targetPrecision = x.precision();
            long finalPrecision = Util.ifFinite(targetPrecision, targetPrecision - new Apfloat(1, Long.MAX_VALUE, x.radix()).equalDigits(x));
            long originalScale = x.scale();
            x = scale(x, -originalScale);
            if (originalScale == 0) {
                radixPower = Apfloat.ZERO;
            } else {
                radixPower = new Apfloat(originalScale, Long.MAX_VALUE, x.radix()).multiply(ApfloatHelper.extendPrecision(logRadix(targetPrecision, x.radix(), multiplyByPi)));
            }
            return ApfloatHelper.extendPrecision(rawLog(x, multiplyByPi)).add(radixPower).precision(finalPrecision);
        }
    }

    private static Apfloat rawLog(Apfloat x, boolean multiplyByPi) throws ApfloatRuntimeException {
        if ($assertionsDisabled || x.signum() > 0) {
            long targetPrecision = x.precision();
            if (targetPrecision == Long.MAX_VALUE) {
                throw new InfiniteExpansionException("Cannot calculate logarithm to infinite precision");
            }
            Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
            long workingPrecision = ApfloatHelper.extendPrecision(targetPrecision);
            long n = (targetPrecision / 2) + 25;
            x = ApfloatHelper.extendPrecision(x, 25);
            Apfloat e = scale(one.precision(workingPrecision), -n);
            x = scale(x, -n);
            Apfloat agme = ApfloatHelper.extendPrecision(agm(one, e));
            Apfloat agmex = ApfloatHelper.extendPrecision(agm(one, x));
            Apfloat log = agmex.subtract(agme).precision(workingPrecision);
            if (multiplyByPi) {
                log = ApfloatHelper.extendPrecision(pi(targetPrecision, x.radix())).multiply(log);
            }
            return log.divide(new Apfloat(2, Long.MAX_VALUE, x.radix()).multiply(agme).multiply(agmex)).precision(targetPrecision);
        }
        throw new AssertionError();
    }

    private static Integer getRadixLogKey(Integer radix) {
        Integer radixKey = (Integer) radixLogKeys.putIfAbsent(radix, radix);
        if (radixKey == null) {
            return radix;
        }
        return radixKey;
    }

    public static Apfloat logRadix(long precision, int radix) throws ApfloatRuntimeException {
        return logRadix(precision, radix, true);
    }

    private static Apfloat logRadix(long precision, int radix, boolean multiplyByPi) throws ApfloatRuntimeException {
        Apfloat logRadix;
        Integer radixKey = getRadixLogKey(new Integer(radix));
        synchronized (radixKey) {
            Map<Integer, Apfloat> cache = multiplyByPi ? radixLogPi : radixLog;
            logRadix = (Apfloat) cache.get(radixKey);
            if (logRadix == null || logRadix.precision() < precision) {
                if (multiplyByPi) {
                    logRadix = ApfloatHelper.extendPrecision(logRadix(precision, radix, false)).multiply(ApfloatHelper.extendPrecision(pi(precision, radix))).precision(precision);
                } else {
                    logRadix = rawLog(new Apfloat("0.1", precision, radix), multiplyByPi).negate();
                }
                cache.put(radixKey, logRadix);
            } else {
                logRadix = logRadix.precision(precision);
            }
        }
        return logRadix;
    }

    public static Apfloat exp(Apfloat x) throws ApfloatRuntimeException {
        int radix = x.radix();
        if (x.signum() == 0) {
            return new Apfloat(1, Long.MAX_VALUE, radix);
        }
        long targetPrecision = x.precision();
        long doublePrecision = (long) ApfloatHelper.getDoublePrecision(radix);
        targetPrecision = Util.ifFinite(targetPrecision, Math.max(1 - x.scale(), 0) + targetPrecision);
        if (targetPrecision == Long.MAX_VALUE) {
            throw new InfiniteExpansionException("Cannot calculate exponent to infinite precision");
        }
        if (x.compareTo(new Apfloat(9.223372036854776E18d * Math.log((double) radix), doublePrecision, radix)) >= 0) {
            throw new OverflowException("Overflow");
        }
        if (x.compareTo(new Apfloat(-9.223372036854776E18d * Math.log((double) radix), doublePrecision, radix)) <= 0) {
            return Apfloat.ZERO;
        }
        if (x.scale() <= -4611686018427387884L) {
            return new Apfloat(1, Long.MAX_VALUE, radix).add(x).precision(Long.MAX_VALUE);
        }
        long precision;
        Apfloat result;
        if (x.scale() < (-doublePrecision) / 2) {
            precision = -2 * x.scale();
            result = new Apfloat(1, precision, radix).add(x);
        } else {
            long scaledXPrecision = Math.max(0, x.scale()) + doublePrecision;
            Apfloat scaledX = x.precision(scaledXPrecision).divide(log(new Apfloat((double) radix, scaledXPrecision, radix)));
            result = scale(new Apfloat(Math.pow((double) radix, scaledX.frac().doubleValue()), doublePrecision, radix), scaledX.truncate().longValue());
            if (result.signum() == 0) {
                return Apfloat.ZERO;
            }
            precision = doublePrecision;
        }
        int iterations = 0;
        for (long maxPrec = precision; maxPrec < targetPrecision; maxPrec <<= 1) {
            iterations++;
        }
        int precisingIteration = iterations;
        long minPrec = precision;
        while (precisingIteration > 0 && ((minPrec - 20) << precisingIteration) < targetPrecision) {
            precisingIteration--;
            minPrec <<= 1;
        }
        if (iterations > 0) {
            logRadix(targetPrecision, radix);
        }
        x = ApfloatHelper.extendPrecision(x);
        int iterations2 = iterations;
        while (true) {
            iterations = iterations2 - 1;
            if (iterations2 <= 0) {
                return result.precision(targetPrecision);
            }
            precision *= 2;
            result = result.precision(Math.min(precision, targetPrecision));
            Apfloat t = x.subtract(lastIterationExtendPrecision(iterations, precisingIteration, log(result)));
            if (iterations < precisingIteration) {
                t = t.precision(precision / 2);
            }
            result = lastIterationExtendPrecision(iterations, precisingIteration, result);
            result = result.add(result.multiply(t));
            if (iterations == precisingIteration) {
                t = lastIterationExtendPrecision(iterations, -1, log(result));
                result = lastIterationExtendPrecision(iterations, -1, result);
                result = result.add(result.multiply(x.subtract(t)));
            }
            iterations2 = iterations;
        }
    }

    public static Apfloat pow(Apfloat x, Apfloat y) throws ArithmeticException, ApfloatRuntimeException {
        long targetPrecision = Math.min(x.precision(), y.precision());
        Apfloat result = ApfloatHelper.checkPow(x, y, targetPrecision);
        if (result != null) {
            return result;
        }
        logRadix(targetPrecision, x.radix());
        result = log(x.precision(Math.min(x.precision(), Util.ifFinite(targetPrecision, new Apfloat(1, Long.MAX_VALUE, x.radix()).equalDigits(x) + targetPrecision))));
        return exp(ApfloatHelper.extendPrecision(y).multiply(ApfloatHelper.extendPrecision(result)).precision(Math.min(y.precision(), result.precision())));
    }

    public static Apfloat acosh(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        return log(x.add(sqrt(x.multiply(x).subtract(new Apfloat(1, Long.MAX_VALUE, x.radix())))));
    }

    public static Apfloat asinh(Apfloat x) throws ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        if (x.signum() >= 0) {
            return log(sqrt(x.multiply(x).add(one)).add(x));
        }
        return log(sqrt(x.multiply(x).add(one)).subtract(x)).negate();
    }

    public static Apfloat atanh(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        return log(one.add(x).divide(one.subtract(x))).divide(new Apfloat(2, Long.MAX_VALUE, x.radix()));
    }

    public static Apfloat cosh(Apfloat x) throws ApfloatRuntimeException {
        Apfloat y = exp(x);
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        return y.add(one.divide(y)).divide(new Apfloat(2, Long.MAX_VALUE, x.radix()));
    }

    public static Apfloat sinh(Apfloat x) throws ApfloatRuntimeException {
        Apfloat y = exp(x);
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        return y.subtract(one.divide(y)).divide(new Apfloat(2, Long.MAX_VALUE, x.radix()));
    }

    public static Apfloat tanh(Apfloat x) throws ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        Apfloat y = exp(new Apfloat(2, Long.MAX_VALUE, x.radix()).multiply(abs(x)));
        y = y.subtract(one).divide(y.add(one));
        return x.signum() < 0 ? y.negate() : y;
    }

    public static Apfloat acos(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        return ApcomplexMath.log(x.add(new Apcomplex(Apfloat.ZERO, one).multiply(sqrt(one.subtract(x.multiply(x)))))).imag();
    }

    public static Apfloat asin(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        return ApcomplexMath.log(sqrt(one.subtract(x.multiply(x))).subtract(new Apcomplex(Apfloat.ZERO, one).multiply(x))).imag().negate();
    }

    public static Apfloat atan(Apfloat x) throws ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, x.radix());
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, x.radix());
        Apcomplex i = new Apcomplex(Apfloat.ZERO, one);
        return ApcomplexMath.log(i.subtract(x).divide(i.add(x))).imag().divide(two);
    }

    public static Apfloat atan2(Apfloat x, Apfloat y) throws ArithmeticException, ApfloatRuntimeException {
        if (y.signum() == 0) {
            if (x.signum() == 0) {
                throw new ArithmeticException("Angle of (0, 0)");
            }
            Apfloat pi = pi(x.precision(), x.radix());
            return new Apfloat((long) x.signum(), Long.MAX_VALUE, x.radix()).multiply(pi).divide(new Apfloat(2, Long.MAX_VALUE, x.radix()));
        } else if (x.signum() == 0) {
            if (y.signum() > 0) {
                return Apfloat.ZERO;
            }
            return pi(y.precision(), y.radix());
        } else if (Math.min(x.precision(), y.precision()) == Long.MAX_VALUE) {
            throw new InfiniteExpansionException("Cannot calculate atan2 to infinite precision");
        } else {
            long maxScale = Math.max(x.scale(), y.scale());
            return ApcomplexMath.log(new Apcomplex(scale(y, -maxScale), scale(x, -maxScale))).imag();
        }
    }

    public static Apfloat cos(Apfloat x) throws ApfloatRuntimeException {
        return ApcomplexMath.exp(new Apcomplex(Apfloat.ZERO, x)).real();
    }

    public static Apfloat sin(Apfloat x) throws ApfloatRuntimeException {
        return ApcomplexMath.exp(new Apcomplex(Apfloat.ZERO, x)).imag();
    }

    public static Apfloat tan(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        Apcomplex w = ApcomplexMath.exp(new Apcomplex(Apfloat.ZERO, x));
        return w.imag().divide(w.real());
    }

    public static Apfloat w(Apfloat x) throws ArithmeticException, ApfloatRuntimeException {
        return LambertWHelper.w(x);
    }

    public static Apfloat toDegrees(Apfloat x) throws ApfloatRuntimeException {
        return x.multiply(new Apfloat(180, Long.MAX_VALUE, x.radix())).divide(pi(x.precision(), x.radix()));
    }

    public static Apfloat toRadians(Apfloat x) throws ApfloatRuntimeException {
        return x.divide(new Apfloat(180, Long.MAX_VALUE, x.radix())).multiply(pi(x.precision(), x.radix()));
    }

    public static Apfloat product(Apfloat... x) throws ApfloatRuntimeException {
        if (x.length == 0) {
            return Apfloat.ONE;
        }
        int i;
        long maxPrec = Long.MAX_VALUE;
        for (i = 0; i < x.length; i++) {
            if (x[i].signum() == 0) {
                return Apfloat.ZERO;
            }
            maxPrec = Math.min(maxPrec, x[i].precision());
        }
        Apfloat[] tmp = new Apfloat[x.length];
        long destPrec = ApfloatHelper.extendPrecision(maxPrec, (long) Math.sqrt((double) x.length));
        for (i = 0; i < x.length; i++) {
            tmp[i] = x[i].precision(destPrec);
        }
        x = tmp;
        Queue<Apfloat> heap = new PriorityQueue(x.length, new 1());
        ParallelHelper.parallelProduct(x, heap, new 2());
        return ((Apfloat) heap.remove()).precision(maxPrec);
    }

    public static Apfloat sum(Apfloat... x) throws ApfloatRuntimeException {
        if (x.length == 0) {
            return Apfloat.ZERO;
        }
        int length;
        long maxScale = -9223372036854775807L;
        long maxPrec = Long.MAX_VALUE;
        int i = 0;
        while (true) {
            length = x.length;
            if (i >= r0) {
                break;
            }
            long oldScale = maxScale;
            long oldPrec = maxPrec;
            long newScale = x[i].scale();
            long newPrec = x[i].precision();
            maxScale = Math.max(oldScale, newScale);
            maxPrec = Math.min(Util.ifFinite(oldPrec, oldPrec + (maxScale - oldScale < 0 ? Long.MAX_VALUE : maxScale - oldScale)), Util.ifFinite(newPrec, newPrec + (maxScale - newScale < 0 ? Long.MAX_VALUE : maxScale - newScale)));
            i++;
        }
        Apfloat[] tmp = new Apfloat[x.length];
        i = 0;
        while (true) {
            length = x.length;
            if (i >= r0) {
                break;
            }
            long scale = x[i].scale();
            long scaleDiff = maxScale - scale < 0 ? Long.MAX_VALUE : maxScale - scale;
            long destPrec = maxPrec - scaleDiff <= 0 ? 0 : Util.ifFinite(maxPrec, maxPrec - scaleDiff);
            if (destPrec > 0) {
                tmp[i] = x[i].precision(destPrec);
            } else {
                tmp[i] = Apfloat.ZERO;
            }
            i++;
        }
        x = tmp;
        Comparator<Apfloat> comparator = new 3();
        Arrays.sort(x, comparator);
        length = x.length;
        List<Apfloat> list;
        if (r0 >= 1000) {
            ApfloatContext ctx = ApfloatContext.getContext();
            long maxSize = (long) ((((double) ctx.getMemoryThreshold()) * 5.0d) / Math.log((double) ctx.getDefaultRadix()));
            Queue<Apfloat> queue = new ConcurrentLinkedQueue();
            list = new ArrayList();
            for (Apfloat a : x) {
                Queue<Apfloat> queue2;
                if (a.size() <= maxSize) {
                    queue2 = queue;
                } else {
                    Object obj = list;
                }
                queue2.add(a);
            }
            ParallelHelper.runParallel(new 4(queue));
            list.addAll(queue);
            Collections.sort(list, comparator);
        } else {
            list = Arrays.asList(x);
        }
        Apfloat s = Apfloat.ZERO;
        for (Apfloat a2 : list) {
            s = s.add(a2);
        }
        return s;
    }

    private static Apfloat lastIterationExtendPrecision(int iterations, int precisingIteration, Apfloat x) throws ApfloatRuntimeException {
        return (iterations != 0 || precisingIteration == 0) ? x : ApfloatHelper.extendPrecision(x);
    }

    static Apfloat factorial(long n, long precision) throws ArithmeticException, NumberFormatException, ApfloatRuntimeException {
        return factorial(n, precision, ApfloatContext.getContext().getDefaultRadix());
    }

    static Apfloat factorial(long n, long precision, int radix) throws ArithmeticException, NumberFormatException, ApfloatRuntimeException {
        if (n < 0) {
            throw new ArithmeticException("Factorial of negative number");
        } else if (n < 2) {
            return new Apfloat(1, precision, radix);
        } else {
            long targetPrecision = precision;
            precision = ApfloatHelper.extendPrecision(precision);
            Apfloat oddProduct = new Apfloat(1, precision, radix);
            Apfloat factorialProduct = oddProduct;
            long exponentOfTwo = 0;
            for (int i = 62 - Long.numberOfLeadingZeros(n); i >= 0; i--) {
                long m = n >>> i;
                long k = m >>> 1;
                exponentOfTwo += k;
                oddProduct = oddProduct.multiply(oddProduct(1 + k, m, precision, radix));
                factorialProduct = factorialProduct.multiply(oddProduct);
            }
            return factorialProduct.multiply(pow(new Apfloat(2, precision, radix), exponentOfTwo)).precision(targetPrecision);
        }
    }

    private static Apfloat oddProduct(long n, long m, long precision, int radix) throws ApfloatRuntimeException {
        n |= 1;
        m = (m - 1) | 1;
        if (n > m) {
            return new Apfloat(1, precision, radix);
        }
        if (n == m) {
            return new Apfloat(n, precision, radix);
        }
        long k = (n + m) >>> 1;
        return oddProduct(n, k, precision, radix).multiply(oddProduct(1 + k, m, precision, radix));
    }

    static void cleanUp() {
        radixPi = SHUTDOWN_MAP;
        radixPiT = SHUTDOWN_MAP;
        radixPiQ = SHUTDOWN_MAP;
        radixPiP = SHUTDOWN_MAP;
        radixPiInverseRoot = SHUTDOWN_MAP;
        radixLog = SHUTDOWN_MAP;
        radixLogPi = SHUTDOWN_MAP;
    }
}
