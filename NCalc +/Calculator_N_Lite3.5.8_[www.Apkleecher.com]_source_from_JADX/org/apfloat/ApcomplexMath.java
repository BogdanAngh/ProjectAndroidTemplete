package org.apfloat;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import org.apache.commons.math4.util.FastMath;
import org.apfloat.spi.Util;

public class ApcomplexMath {
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ApcomplexMath.class.desiredAssertionStatus();
    }

    private ApcomplexMath() {
    }

    @Deprecated
    public static Apcomplex negate(Apcomplex z) throws ApfloatRuntimeException {
        return z.negate();
    }

    public static Apfloat abs(Apcomplex z) throws ApfloatRuntimeException {
        if (z.real().signum() == 0) {
            return ApfloatMath.abs(z.imag());
        }
        if (z.imag().signum() == 0) {
            return ApfloatMath.abs(z.real());
        }
        return ApfloatMath.sqrt(norm(z));
    }

    public static Apfloat norm(Apcomplex z) throws ApfloatRuntimeException {
        return ApfloatMath.multiplyAdd(z.real(), z.real(), z.imag(), z.imag());
    }

    public static Apfloat arg(Apcomplex z) throws ArithmeticException, ApfloatRuntimeException {
        return ApfloatMath.atan2(z.imag(), z.real());
    }

    public static Apcomplex scale(Apcomplex z, long scale) throws ApfloatRuntimeException {
        return new Apcomplex(ApfloatMath.scale(z.real(), scale), ApfloatMath.scale(z.imag(), scale));
    }

    public static Apcomplex pow(Apcomplex z, long n) throws ArithmeticException, ApfloatRuntimeException {
        if (n != 0) {
            if (n < 0) {
                z = Apcomplex.ONE.divide(z);
                n = -n;
            }
            return powAbs(z, n);
        } else if (z.real().signum() != 0 || z.imag().signum() != 0) {
            return new Apcomplex(new Apfloat(1, Long.MAX_VALUE, z.radix()));
        } else {
            throw new ArithmeticException("Zero to power zero");
        }
    }

    private static Apcomplex powAbs(Apcomplex z, long n) throws ArithmeticException, ApfloatRuntimeException {
        long precision = z.precision();
        z = ApfloatHelper.extendPrecision(z);
        int b2pow = 0;
        while ((1 & n) == 0) {
            b2pow++;
            n >>>= 1;
        }
        Apcomplex r = z;
        while (true) {
            n >>>= 1;
            if (n <= 0) {
                break;
            }
            z = z.multiply(z);
            if ((1 & n) != 0) {
                r = r.multiply(z);
            }
        }
        int b2pow2 = b2pow;
        while (true) {
            b2pow = b2pow2 - 1;
            if (b2pow2 <= 0) {
                return ApfloatHelper.setPrecision(r, precision);
            }
            r = r.multiply(r);
            b2pow2 = b2pow;
        }
    }

    public static Apcomplex sqrt(Apcomplex z) throws ApfloatRuntimeException {
        return root(z, 2);
    }

    public static Apcomplex cbrt(Apcomplex z) throws ApfloatRuntimeException {
        return root(z, 3);
    }

    public static Apcomplex root(Apcomplex z, long n) throws ArithmeticException, ApfloatRuntimeException {
        return root(z, n, 0);
    }

    public static Apcomplex root(Apcomplex z, long n, long k) throws ArithmeticException, ApfloatRuntimeException {
        if (n == 0) {
            throw new ArithmeticException("Zeroth root");
        } else if (z.real().signum() == 0 && z.imag().signum() == 0) {
            if (n >= 0) {
                return Apcomplex.ZERO;
            }
            throw new ArithmeticException("Inverse root of zero");
        } else if (n == 1) {
            return z;
        } else {
            k %= n;
            if (z.imag().signum() == 0 && z.real().signum() > 0 && k == 0) {
                return new Apcomplex(ApfloatMath.root(z.real(), n));
            }
            if (n < 0) {
                return inverseRootAbs(z, -n, k);
            }
            if (n == 2) {
                return z.multiply(inverseRootAbs(z, 2, k));
            }
            if (n != 3) {
                return inverseRootAbs(inverseRootAbs(z, n, k), 1, 0);
            }
            if (z.real().signum() < 0) {
                k = (z.imag().signum() == 0 ? 1 - k : k - 1) % n;
            } else {
                k = -k;
            }
            return z.multiply(inverseRootAbs(z.multiply(z), 3, k));
        }
    }

    public static Apcomplex inverseRoot(Apcomplex z, long n) throws ArithmeticException, ApfloatRuntimeException {
        return inverseRoot(z, n, 0);
    }

    public static Apcomplex inverseRoot(Apcomplex z, long n, long k) throws ArithmeticException, ApfloatRuntimeException {
        if (z.real().signum() == 0 && z.imag().signum() == 0) {
            throw new ArithmeticException("Inverse root of zero");
        } else if (n == 0) {
            throw new ArithmeticException("Inverse zeroth root");
        } else {
            k %= n;
            if (z.imag().signum() == 0 && z.real().signum() > 0 && k == 0) {
                return new Apcomplex(ApfloatMath.inverseRoot(z.real(), n));
            }
            if (n < 0) {
                return inverseRootAbs(inverseRootAbs(z, -n, k), 1, 0);
            }
            return inverseRootAbs(z, n, k);
        }
    }

    private static Apcomplex inverseRootAbs(Apcomplex z, long n, long k) throws ArithmeticException, ApfloatRuntimeException {
        if (z.equals(Apcomplex.ONE) && k == 0) {
            return z;
        }
        if (n == 2 && z.imag().signum() == 0 && z.real().signum() < 0) {
            Apfloat y = ApfloatMath.inverseRoot(z.real().negate(), n);
            Apint apint = Apfloat.ZERO;
            if (k == 0) {
                y = y.negate();
            }
            return new Apcomplex(apint, y);
        }
        long targetPrecision = z.precision();
        if (targetPrecision == Long.MAX_VALUE) {
            throw new InfiniteExpansionException("Cannot calculate inverse root to infinite precision");
        }
        double angle;
        Apcomplex result;
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat divisor = ApfloatMath.abs(new Apfloat(n, Long.MAX_VALUE, z.radix()));
        double doubleN = Math.abs((double) n);
        long realScale = z.real().scale();
        long imagScale = z.imag().scale();
        long scale = Math.max(realScale, imagScale);
        long scaleDiff = scale - Math.min(realScale, imagScale);
        long doublePrecision = (long) ApfloatHelper.getDoublePrecision(z.radix());
        long precision = doublePrecision;
        long scaleQuot = scale / n;
        double scaleRemFactor = Math.pow((double) z.radix(), ((double) (-(scale - (scaleQuot * n)))) / doubleN);
        Apfloat tmpReal;
        Apfloat tmpImag;
        Apcomplex apcomplex;
        double magnitude;
        double doubleReal;
        double doubleImag;
        if (z.imag().signum() == 0 || ((scaleDiff > doublePrecision / 2 || scaleDiff < 0) && realScale > imagScale)) {
            tmpReal = z.real().precision(doublePrecision);
            tmpImag = z.imag().precision(doublePrecision);
            apcomplex = new Apcomplex(Apfloat.ZERO, tmpImag.divide(divisor.multiply(tmpReal)));
            magnitude = ApfloatMath.scale(tmpReal, -tmpReal.scale()).doubleValue();
            if (magnitude >= 0.0d) {
                doubleReal = Math.pow(magnitude, -1.0d / doubleN) * scaleRemFactor;
                doubleImag = 0.0d;
            } else {
                magnitude = Math.pow(-magnitude, -1.0d / doubleN) * scaleRemFactor;
                angle = (tmpImag.signum() >= 0 ? -3.141592653589793d : FastMath.PI) / doubleN;
                doubleReal = magnitude * Math.cos(angle);
                doubleImag = magnitude * Math.sin(angle);
            }
            apcomplex = new Apcomplex(ApfloatMath.scale(new Apfloat(doubleReal, doublePrecision, z.radix()), -scaleQuot), ApfloatMath.scale(new Apfloat(doubleImag, doublePrecision, z.radix()), -scaleQuot));
            result = apcomplex.subtract(apcomplex.multiply(apcomplex));
        } else if (z.real().signum() == 0 || ((scaleDiff > doublePrecision / 2 || scaleDiff < 0) && imagScale > realScale)) {
            tmpReal = z.real().precision(doublePrecision);
            tmpImag = z.imag().precision(doublePrecision);
            apcomplex = new Apcomplex(Apfloat.ZERO, tmpReal.divide(divisor.multiply(tmpImag)));
            magnitude = ApfloatMath.scale(tmpImag, -tmpImag.scale()).doubleValue();
            if (magnitude >= 0.0d) {
                magnitude = Math.pow(magnitude, -1.0d / doubleN) * scaleRemFactor;
                angle = -3.141592653589793d / (2.0d * doubleN);
            } else {
                magnitude = Math.pow(-magnitude, -1.0d / doubleN) * scaleRemFactor;
                angle = FastMath.PI / (2.0d * doubleN);
            }
            apcomplex = new Apcomplex(ApfloatMath.scale(new Apfloat(magnitude * Math.cos(angle), doublePrecision, z.radix()), -scaleQuot), ApfloatMath.scale(new Apfloat(magnitude * Math.sin(angle), doublePrecision, z.radix()), -scaleQuot));
            result = apcomplex.add(apcomplex.multiply(apcomplex));
        } else {
            tmpReal = z.real().precision(doublePrecision);
            tmpImag = z.imag().precision(doublePrecision);
            tmpReal = ApfloatMath.scale(tmpReal, -scale);
            tmpImag = ApfloatMath.scale(tmpImag, -scale);
            doubleReal = tmpReal.doubleValue();
            doubleImag = tmpImag.doubleValue();
            magnitude = Math.pow((doubleReal * doubleReal) + (doubleImag * doubleImag), -1.0d / (2.0d * doubleN)) * scaleRemFactor;
            angle = (-Math.atan2(doubleImag, doubleReal)) / doubleN;
            apcomplex = new Apcomplex(ApfloatMath.scale(new Apfloat(magnitude * Math.cos(angle), doublePrecision, z.radix()), -scaleQuot), ApfloatMath.scale(new Apfloat(magnitude * Math.sin(angle), doublePrecision, z.radix()), -scaleQuot));
        }
        if (k != 0) {
            Apcomplex branch;
            if (k < 0) {
                k += n;
            }
            if (n % 4 == 0 && (n >>> 2) == k) {
                branch = new Apcomplex(Apfloat.ZERO, one);
            } else if (n % 4 == 0 && (n >>> 2) * 3 == k) {
                branch = new Apcomplex(Apfloat.ZERO, one.negate());
            } else if (n % 2 == 0 && (n >>> 1) == k) {
                branch = one.negate();
            } else {
                angle = (Angle2D.M_2PI * ((double) k)) / doubleN;
                branch = new Apcomplex(new Apfloat(Math.cos(angle), doublePrecision, z.radix()), new Apfloat(Math.sin(angle), doublePrecision, z.radix()));
            }
            if (z.imag().signum() >= 0) {
                branch = branch.conj();
            }
            result = result.multiply(branch);
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
        z = ApfloatHelper.extendPrecision(z);
        int iterations2 = iterations;
        while (true) {
            iterations = iterations2 - 1;
            if (iterations2 <= 0) {
                return ApfloatHelper.setPrecision(result, targetPrecision);
            }
            precision *= 2;
            result = ApfloatHelper.setPrecision(result, Math.min(precision, targetPrecision));
            Apcomplex t = one.subtract(z.multiply(lastIterationExtendPrecision(iterations, precisingIteration, powAbs(result, n))));
            if (iterations < precisingIteration) {
                t = new Apcomplex(t.real().precision(precision / 2), t.imag().precision(precision / 2));
            }
            result = lastIterationExtendPrecision(iterations, precisingIteration, result);
            result = result.add(result.multiply(t).divide(divisor));
            if (iterations == precisingIteration) {
                t = lastIterationExtendPrecision(iterations, -1, powAbs(result, n));
                result = lastIterationExtendPrecision(iterations, -1, result);
                result = result.add(result.multiply(one.subtract(z.multiply(t))).divide(divisor));
            }
            iterations2 = iterations;
        }
    }

    public static Apcomplex[] allRoots(Apcomplex z, int n) throws ArithmeticException, ApfloatRuntimeException {
        if (n == 0) {
            throw new ArithmeticException("Zeroth root");
        } else if (n == 1) {
            return new Apcomplex[]{z};
        } else if (n == RtlSpacingHelper.UNDEFINED) {
            throw new ApfloatRuntimeException("Maximum array size exceeded");
        } else if (z.real().signum() != 0 || z.imag().signum() != 0) {
            boolean inverse = n < 0;
            n = Math.abs(n);
            long precision = z.precision();
            z = ApfloatHelper.extendPrecision(z);
            Apcomplex w = inverseRootAbs(new Apfloat(1, precision, z.radix()), (long) n, 1);
            if (((z.imag().signum() >= 0 ? 1 : 0) ^ inverse) != 0) {
                w = w.conj();
            }
            allRoots = new Apcomplex[n];
            Apcomplex root = inverse ? inverseRootAbs(z, (long) n, 0) : root(z, (long) n);
            allRoots[0] = ApfloatHelper.setPrecision(root, precision);
            for (int i = 1; i < n; i++) {
                root = root.multiply(w);
                allRoots[i] = ApfloatHelper.setPrecision(root, precision);
            }
            return allRoots;
        } else if (n < 0) {
            throw new ArithmeticException("Inverse root of zero");
        } else {
            allRoots = new Apcomplex[n];
            Arrays.fill(allRoots, Apcomplex.ZERO);
            return allRoots;
        }
    }

    public static Apcomplex agm(Apcomplex a, Apcomplex b) throws ApfloatRuntimeException {
        if ((a.real().signum() == 0 && a.imag().signum() == 0) || (b.real().signum() == 0 && b.imag().signum() == 0)) {
            return Apcomplex.ZERO;
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
            Apcomplex t = a.add(b).divide(two);
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
        return ApfloatHelper.setPrecision(a.add(b).divide(two), targetPrecision);
    }

    public static Apcomplex log(Apcomplex z) throws ArithmeticException, ApfloatRuntimeException {
        if (z.real().signum() >= 0 && z.imag().signum() == 0) {
            return ApfloatMath.log(z.real());
        }
        long targetPrecision = z.precision();
        if (targetPrecision == Long.MAX_VALUE) {
            throw new InfiniteExpansionException("Cannot calculate logarithm to infinite precision");
        }
        Apfloat imagBias;
        Apfloat radixPower;
        if (z.real().signum() < 0) {
            Apfloat pi = ApfloatHelper.extendPrecision(ApfloatMath.pi(targetPrecision, z.radix()), z.radix() <= 3 ? 1 : 0);
            if (z.imag().signum() >= 0) {
                imagBias = pi;
            } else {
                imagBias = pi.negate();
            }
            z = z.negate();
        } else {
            imagBias = Apfloat.ZERO;
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat x = abs(z);
        long originalScale = z.scale();
        z = scale(z, -originalScale);
        if (originalScale == 0) {
            radixPower = Apfloat.ZERO;
        } else {
            radixPower = new Apfloat(originalScale, Long.MAX_VALUE, z.radix()).multiply(ApfloatHelper.extendPrecision(ApfloatMath.logRadix(targetPrecision, z.radix())));
        }
        Apcomplex result = ApfloatHelper.extendPrecision(rawLog(z)).add(radixPower);
        return new Apcomplex(result.real().precision(Math.max(targetPrecision - one.equalDigits(x), 1)), result.imag().precision(Math.max((targetPrecision - 1) + result.imag().scale(), 1)).add(imagBias));
    }

    public static Apcomplex log(Apcomplex z, Apcomplex w) throws ArithmeticException, ApfloatRuntimeException {
        if (z.real().signum() >= 0 && z.imag().signum() == 0 && w.real().signum() >= 0 && w.imag().signum() == 0) {
            return ApfloatMath.log(z.real(), w.real());
        }
        long targetPrecision = Math.min(z.precision(), w.precision());
        if (z.real().signum() >= 0 && z.imag().signum() == 0) {
            Apfloat x = z.real();
            return ApfloatMath.log(x.precision(Math.min(x.precision(), Util.ifFinite(targetPrecision, new Apfloat(1, Long.MAX_VALUE, x.radix()).equalDigits(x) + targetPrecision)))).divide(log(w));
        } else if (w.real().signum() < 0 || w.imag().signum() != 0) {
            return log(z).divide(log(w));
        } else {
            Apfloat y = w.real();
            return log(z).divide(ApfloatMath.log(y.precision(Math.min(y.precision(), Util.ifFinite(targetPrecision, new Apfloat(1, Long.MAX_VALUE, y.radix()).equalDigits(y) + targetPrecision)))));
        }
    }

    private static Apcomplex rawLog(Apcomplex z) throws ApfloatRuntimeException {
        if (!$assertionsDisabled && z.real().signum() == 0 && z.imag().signum() == 0) {
            throw new AssertionError();
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        long targetPrecision = z.precision();
        long workingPrecision = ApfloatHelper.extendPrecision(targetPrecision);
        long n = (targetPrecision / 2) + 25;
        z = ApfloatHelper.extendPrecision(z, 25);
        Apfloat e = ApfloatMath.scale(one.precision(workingPrecision), -n);
        z = scale(z, -n);
        Apfloat agme = ApfloatHelper.extendPrecision(ApfloatMath.agm(one, e));
        Apcomplex agmez = ApfloatHelper.extendPrecision(agm(one, z));
        return ApfloatHelper.setPrecision(ApfloatHelper.extendPrecision(ApfloatMath.pi(targetPrecision, z.radix())).multiply(agmez.subtract(agme)).divide(new Apfloat(2, Long.MAX_VALUE, z.radix()).multiply(agme).multiply(agmez)), targetPrecision);
    }

    public static Apcomplex exp(Apcomplex z) throws ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.exp(z.real());
        }
        int radix = z.radix();
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, radix);
        long doublePrecision = (long) ApfloatHelper.getDoublePrecision(radix);
        long targetPrecision = z.imag().precision() >= z.imag().scale() ? Math.min(Util.ifFinite(z.real().precision(), z.real().precision() + Math.max(1 - z.real().scale(), 0)), Util.ifFinite(z.imag().precision(), (1 + z.imag().precision()) - z.imag().scale())) : 0;
        if (targetPrecision == Long.MAX_VALUE) {
            throw new InfiniteExpansionException("Cannot calculate exponent to infinite precision");
        } else if (z.real().compareTo(new Apfloat(Math.log((double) radix) * 9.223372036854776E18d, doublePrecision, radix)) >= 0) {
            throw new OverflowException("Overflow");
        } else if (z.real().compareTo(new Apfloat(Math.log((double) radix) * -9.223372036854776E18d, doublePrecision, radix)) <= 0) {
            return Apcomplex.ZERO;
        } else {
            if (targetPrecision == 0) {
                throw new LossOfPrecisionException("Complete loss of accurate digits in imaginary part");
            }
            Apfloat zImag;
            Apfloat resultReal;
            Apcomplex resultImag;
            boolean negateResult = false;
            if (z.imag().scale() > 0) {
                Apfloat pi = ApfloatMath.pi(Util.ifFinite(targetPrecision, z.imag().scale() + targetPrecision), radix);
                Apfloat twoPi = pi.add(pi);
                Apfloat halfPi = pi.divide(new Apfloat(2, targetPrecision, radix));
                zImag = ApfloatMath.fmod(z.imag(), twoPi);
                if (zImag.compareTo(pi) > 0) {
                    zImag = zImag.subtract(twoPi);
                } else {
                    if (zImag.compareTo(pi.negate()) <= 0) {
                        zImag = zImag.add(twoPi);
                    }
                }
                if (zImag.compareTo(halfPi) > 0) {
                    zImag = zImag.subtract(pi);
                    negateResult = true;
                } else {
                    if (zImag.compareTo(halfPi.negate()) <= 0) {
                        zImag = zImag.add(pi);
                        negateResult = true;
                    }
                }
            } else {
                zImag = z.imag();
            }
            Apcomplex apcomplex = new Apcomplex(z.real(), zImag);
            if (apcomplex.real().signum() == 0) {
                resultReal = one;
            } else if (apcomplex.real().scale() < (-doublePrecision) / 2) {
                resultReal = one.precision(Util.ifFinite(-apcomplex.real().scale(), -2 * apcomplex.real().scale())).add(apcomplex.real());
            } else {
                long scaledRealPrecision = Math.max(0, apcomplex.real().scale()) + doublePrecision;
                Apfloat scaledReal = apcomplex.real().precision(scaledRealPrecision).divide(ApfloatMath.log(new Apfloat((double) radix, scaledRealPrecision, radix)));
                resultReal = ApfloatMath.scale(new Apfloat(Math.pow((double) radix, scaledReal.frac().doubleValue()), doublePrecision, radix), scaledReal.truncate().longValue());
                if (resultReal.signum() == 0) {
                    return Apcomplex.ZERO;
                }
            }
            if (zImag.signum() == 0) {
                resultImag = one;
            } else if (zImag.scale() < (-doublePrecision) / 2) {
                apcomplex = new Apcomplex(one.precision(Util.ifFinite(-zImag.scale(), -2 * zImag.scale())), zImag.precision(-zImag.scale()));
            } else {
                double doubleImag = zImag.doubleValue();
                apcomplex = new Apcomplex(new Apfloat(Math.cos(doubleImag), doublePrecision, radix), new Apfloat(Math.sin(doubleImag), doublePrecision, radix));
            }
            Apcomplex result = resultReal.multiply(resultImag);
            long precision = result.precision();
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
                ApfloatMath.logRadix(targetPrecision, radix);
            }
            z = ApfloatHelper.extendPrecision(apcomplex);
            int iterations2 = iterations;
            while (true) {
                iterations = iterations2 - 1;
                if (iterations2 <= 0) {
                    break;
                }
                precision *= 2;
                result = ApfloatHelper.setPrecision(result, Math.min(precision, targetPrecision));
                Apcomplex t = z.subtract(lastIterationExtendPrecision(iterations, precisingIteration, log(result)));
                if (iterations < precisingIteration) {
                    t = new Apcomplex(t.real().precision(precision / 2), t.imag().precision(precision / 2));
                }
                result = lastIterationExtendPrecision(iterations, precisingIteration, result);
                result = result.add(result.multiply(t));
                if (iterations == precisingIteration) {
                    t = lastIterationExtendPrecision(iterations, -1, log(result));
                    result = lastIterationExtendPrecision(iterations, -1, result);
                    result = result.add(result.multiply(z.subtract(t)));
                }
                iterations2 = iterations;
            }
            if (negateResult) {
                result = result.negate();
            }
            return ApfloatHelper.setPrecision(result, targetPrecision);
        }
    }

    public static Apcomplex pow(Apcomplex z, Apcomplex w) throws ApfloatRuntimeException {
        long targetPrecision = Math.min(z.precision(), w.precision());
        Apcomplex result = ApfloatHelper.checkPow(z, w, targetPrecision);
        if (result != null) {
            return result;
        }
        if (z.real().signum() < 0 || z.imag().signum() != 0) {
            return exp(w.multiply(log(z)));
        }
        Apfloat x = z.real();
        return exp(w.multiply(ApfloatMath.log(x.precision(Math.min(x.precision(), Util.ifFinite(targetPrecision, new Apfloat(1, Long.MAX_VALUE, x.radix()).equalDigits(x) + targetPrecision))))));
    }

    public static Apcomplex acos(Apcomplex z) throws ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        if (z.imag().signum() == 0 && ApfloatMath.abs(z.real()).compareTo(one) <= 0) {
            return ApfloatMath.acos(z.real());
        }
        Apcomplex w = new Apcomplex(Apfloat.ZERO, one).multiply(log(z.add(sqrt(z.multiply(z).subtract(one)))));
        if (z.real().signum() * z.imag().signum() >= 0) {
            return w.negate();
        }
        return w;
    }

    public static Apcomplex acosh(Apcomplex z) throws ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        if (z.real().signum() >= 0) {
            return log(z.add(sqrt(z.multiply(z).subtract(one))));
        }
        return log(z.subtract(sqrt(z.multiply(z).subtract(one))));
    }

    public static Apcomplex asin(Apcomplex z) throws ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        if (z.imag().signum() == 0 && ApfloatMath.abs(z.real()).compareTo(one) <= 0) {
            return ApfloatMath.asin(z.real());
        }
        Apcomplex i = new Apcomplex(Apfloat.ZERO, one);
        if (z.imag().signum() >= 0) {
            return i.multiply(log(sqrt(one.subtract(z.multiply(z))).subtract(i.multiply(z))));
        }
        return i.multiply(log(i.multiply(z).add(sqrt(one.subtract(z.multiply(z)))))).negate();
    }

    public static Apcomplex asinh(Apcomplex z) throws ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        if (z.real().signum() >= 0) {
            return log(sqrt(z.multiply(z).add(one)).add(z));
        }
        return log(sqrt(z.multiply(z).add(one)).subtract(z)).negate();
    }

    public static Apcomplex atan(Apcomplex z) throws ArithmeticException, ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.atan(z.real());
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, z.radix());
        Apcomplex i = new Apcomplex(Apfloat.ZERO, one);
        return log(i.add(z).divide(i.subtract(z))).multiply(i).divide(two);
    }

    public static Apcomplex atanh(Apcomplex z) throws ArithmeticException, ApfloatRuntimeException {
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        return log(one.add(z).divide(one.subtract(z))).divide(new Apfloat(2, Long.MAX_VALUE, z.radix()));
    }

    public static Apcomplex cos(Apcomplex z) throws ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.cos(z.real());
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, z.radix());
        Apcomplex w = exp(new Apcomplex(Apfloat.ZERO, one).multiply(z));
        return w.add(one.divide(w)).divide(two);
    }

    public static Apcomplex cosh(Apcomplex z) throws ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.cosh(z.real());
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, z.radix());
        Apcomplex w = exp(z);
        return w.add(one.divide(w)).divide(two);
    }

    public static Apcomplex sin(Apcomplex z) throws ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.sin(z.real());
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, z.radix());
        Apcomplex i = new Apcomplex(Apfloat.ZERO, one);
        Apcomplex w = exp(i.multiply(z));
        return one.divide(w).subtract(w).multiply(i).divide(two);
    }

    public static Apcomplex sinh(Apcomplex z) throws ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.sinh(z.real());
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, z.radix());
        Apcomplex w = exp(z);
        return w.subtract(one.divide(w)).divide(two);
    }

    public static Apcomplex tan(Apcomplex z) throws ArithmeticException, ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.tan(z.real());
        }
        boolean negate = z.imag().signum() > 0;
        if (negate) {
            z = z.negate();
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apfloat two = new Apfloat(2, Long.MAX_VALUE, z.radix());
        Apcomplex i = new Apcomplex(Apfloat.ZERO, one);
        Apcomplex w = exp(two.multiply(i).multiply(z));
        w = i.multiply(one.subtract(w)).divide(one.add(w));
        if (negate) {
            return w.negate();
        }
        return w;
    }

    public static Apcomplex tanh(Apcomplex z) throws ArithmeticException, ApfloatRuntimeException {
        if (z.imag().signum() == 0) {
            return ApfloatMath.tanh(z.real());
        }
        boolean negate = z.real().signum() < 0;
        if (negate) {
            z = z.negate();
        }
        Apfloat one = new Apfloat(1, Long.MAX_VALUE, z.radix());
        Apcomplex w = exp(new Apfloat(2, Long.MAX_VALUE, z.radix()).multiply(z));
        w = w.subtract(one).divide(w.add(one));
        if (negate) {
            return w.negate();
        }
        return w;
    }

    public static Apcomplex w(Apcomplex z) throws ApfloatRuntimeException {
        return LambertWHelper.w(z);
    }

    public static Apcomplex w(Apcomplex z, long k) throws ArithmeticException, ApfloatRuntimeException {
        return LambertWHelper.w(z, k);
    }

    public static Apcomplex product(Apcomplex... z) throws ApfloatRuntimeException {
        if (z.length == 0) {
            return Apcomplex.ONE;
        }
        long maxPrec = Long.MAX_VALUE;
        int i = 0;
        while (i < z.length) {
            if (z[i].real().signum() == 0 && z[i].imag().signum() == 0) {
                return Apcomplex.ZERO;
            }
            maxPrec = Math.min(maxPrec, z[i].precision());
            i++;
        }
        Apcomplex[] tmp = new Apcomplex[z.length];
        long destPrec = ApfloatHelper.extendPrecision(maxPrec, (long) Math.sqrt((double) z.length));
        for (i = 0; i < z.length; i++) {
            tmp[i] = z[i].precision(destPrec);
        }
        z = tmp;
        Queue<Apcomplex> heap = new PriorityQueue(z.length, new 1());
        ParallelHelper.parallelProduct(z, heap, new 2());
        return ApfloatHelper.setPrecision((Apcomplex) heap.remove(), maxPrec);
    }

    public static Apcomplex sum(Apcomplex... z) throws ApfloatRuntimeException {
        if (z.length == 0) {
            return Apcomplex.ZERO;
        }
        Apfloat[] x = new Apfloat[z.length];
        Apfloat[] y = new Apfloat[z.length];
        for (int i = 0; i < z.length; i++) {
            x[i] = z[i].real();
            y[i] = z[i].imag();
        }
        return new Apcomplex(ApfloatMath.sum(x), ApfloatMath.sum(y));
    }

    private static Apcomplex lastIterationExtendPrecision(int iterations, int precisingIteration, Apcomplex z) {
        return (iterations != 0 || precisingIteration == 0) ? z : ApfloatHelper.extendPrecision(z);
    }
}
