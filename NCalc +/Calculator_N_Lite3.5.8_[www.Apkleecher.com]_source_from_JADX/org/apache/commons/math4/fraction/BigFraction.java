package org.apache.commons.math4.fraction;

import android.support.v4.widget.AutoScrollHelper;
import com.example.duy.calculator.math_eval.Constants;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.ArithmeticUtils;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class BigFraction extends Number implements FieldElement<BigFraction>, Comparable<BigFraction>, Serializable {
    public static final BigFraction FOUR_FIFTHS;
    public static final BigFraction MINUS_ONE;
    public static final BigFraction ONE;
    public static final BigFraction ONE_FIFTH;
    public static final BigFraction ONE_HALF;
    private static final BigInteger ONE_HUNDRED;
    public static final BigFraction ONE_QUARTER;
    public static final BigFraction ONE_THIRD;
    public static final BigFraction THREE_FIFTHS;
    public static final BigFraction THREE_QUARTERS;
    public static final BigFraction TWO;
    public static final BigFraction TWO_FIFTHS;
    public static final BigFraction TWO_QUARTERS;
    public static final BigFraction TWO_THIRDS;
    public static final BigFraction ZERO;
    private static final long serialVersionUID = -5630213147331578515L;
    private final BigInteger denominator;
    private final BigInteger numerator;

    static {
        TWO = new BigFraction(2);
        ONE = new BigFraction(1);
        ZERO = new BigFraction(0);
        MINUS_ONE = new BigFraction(-1);
        FOUR_FIFTHS = new BigFraction(4, 5);
        ONE_FIFTH = new BigFraction(1, 5);
        ONE_HALF = new BigFraction(1, 2);
        ONE_QUARTER = new BigFraction(1, 4);
        ONE_THIRD = new BigFraction(1, 3);
        THREE_FIFTHS = new BigFraction(3, 5);
        THREE_QUARTERS = new BigFraction(3, 4);
        TWO_FIFTHS = new BigFraction(2, 5);
        TWO_QUARTERS = new BigFraction(2, 4);
        TWO_THIRDS = new BigFraction(2, 3);
        ONE_HUNDRED = BigInteger.valueOf(100);
    }

    public BigFraction(BigInteger num) {
        this(num, BigInteger.ONE);
    }

    public BigFraction(BigInteger num, BigInteger den) {
        MathUtils.checkNotNull(num, LocalizedFormats.NUMERATOR, new Object[0]);
        MathUtils.checkNotNull(den, LocalizedFormats.DENOMINATOR, new Object[0]);
        if (BigInteger.ZERO.equals(den)) {
            throw new ZeroException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
        } else if (BigInteger.ZERO.equals(num)) {
            this.numerator = BigInteger.ZERO;
            this.denominator = BigInteger.ONE;
        } else {
            BigInteger gcd = num.gcd(den);
            if (BigInteger.ONE.compareTo(gcd) < 0) {
                num = num.divide(gcd);
                den = den.divide(gcd);
            }
            if (BigInteger.ZERO.compareTo(den) > 0) {
                num = num.negate();
                den = den.negate();
            }
            this.numerator = num;
            this.denominator = den;
        }
    }

    public BigFraction(double value) throws MathIllegalArgumentException {
        if (Double.isNaN(value)) {
            throw new MathIllegalArgumentException(LocalizedFormats.NAN_VALUE_CONVERSION, new Object[0]);
        } else if (Double.isInfinite(value)) {
            throw new MathIllegalArgumentException(LocalizedFormats.INFINITE_VALUE_CONVERSION, new Object[0]);
        } else {
            long bits = Double.doubleToLongBits(value);
            long sign = bits & Long.MIN_VALUE;
            long exponent = bits & 9218868437227405312L;
            long m = bits & 4503599627370495L;
            if (exponent != 0) {
                m |= 4503599627370496L;
            }
            if (sign != 0) {
                m = -m;
            }
            int k = ((int) (exponent >> 52)) - 1075;
            while ((9007199254740990L & m) != 0 && (1 & m) == 0) {
                m >>= 1;
                k++;
            }
            if (k < 0) {
                this.numerator = BigInteger.valueOf(m);
                this.denominator = BigInteger.ZERO.flipBit(-k);
                return;
            }
            this.numerator = BigInteger.valueOf(m).multiply(BigInteger.ZERO.flipBit(k));
            this.denominator = BigInteger.ONE;
        }
    }

    public BigFraction(double value, double epsilon, int maxIterations) throws FractionConversionException {
        this(value, epsilon, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, maxIterations);
    }

    private BigFraction(double value, double epsilon, int maxDenominator, int maxIterations) throws FractionConversionException {
        double r0 = value;
        long a0 = (long) FastMath.floor(r0);
        if (FastMath.abs(a0) > 2147483647L) {
            throw new FractionConversionException(value, a0, 1);
        } else if (FastMath.abs(((double) a0) - value) < epsilon) {
            this.numerator = BigInteger.valueOf(a0);
            this.denominator = BigInteger.ONE;
        } else {
            long p2;
            long q2;
            long p0 = 1;
            long q0 = 0;
            long p1 = a0;
            long q1 = 1;
            int n = 0;
            boolean stop = false;
            do {
                n++;
                double r1 = 1.0d / (r0 - ((double) a0));
                long a1 = (long) FastMath.floor(r1);
                p2 = (a1 * p1) + p0;
                q2 = (a1 * q1) + q0;
                if (p2 <= 2147483647L && q2 <= 2147483647L) {
                    double convergent = ((double) p2) / ((double) q2);
                    if (n >= maxIterations || FastMath.abs(convergent - value) <= epsilon || q2 >= ((long) maxDenominator)) {
                        stop = true;
                        continue;
                    } else {
                        p0 = p1;
                        p1 = p2;
                        q0 = q1;
                        q1 = q2;
                        a0 = a1;
                        r0 = r1;
                        continue;
                    }
                } else if (epsilon != 0.0d || FastMath.abs(q1) >= ((long) maxDenominator)) {
                    throw new FractionConversionException(value, p2, q2);
                }
                if (n >= maxIterations) {
                    throw new FractionConversionException(value, maxIterations);
                } else if (q2 >= ((long) maxDenominator)) {
                    this.numerator = BigInteger.valueOf(p2);
                    this.denominator = BigInteger.valueOf(q2);
                } else {
                    this.numerator = BigInteger.valueOf(p1);
                    this.denominator = BigInteger.valueOf(q1);
                }
            } while (!stop);
            if (n >= maxIterations) {
                throw new FractionConversionException(value, maxIterations);
            } else if (q2 >= ((long) maxDenominator)) {
                this.numerator = BigInteger.valueOf(p1);
                this.denominator = BigInteger.valueOf(q1);
            } else {
                this.numerator = BigInteger.valueOf(p2);
                this.denominator = BigInteger.valueOf(q2);
            }
        }
    }

    public BigFraction(double value, int maxDenominator) throws FractionConversionException {
        this(value, 0.0d, maxDenominator, 100);
    }

    public BigFraction(int num) {
        this(BigInteger.valueOf((long) num), BigInteger.ONE);
    }

    public BigFraction(int num, int den) {
        this(BigInteger.valueOf((long) num), BigInteger.valueOf((long) den));
    }

    public BigFraction(long num) {
        this(BigInteger.valueOf(num), BigInteger.ONE);
    }

    public BigFraction(long num, long den) {
        this(BigInteger.valueOf(num), BigInteger.valueOf(den));
    }

    public static BigFraction getReducedFraction(int numerator, int denominator) {
        if (numerator == 0) {
            return ZERO;
        }
        return new BigFraction(numerator, denominator);
    }

    public BigFraction abs() {
        return BigInteger.ZERO.compareTo(this.numerator) <= 0 ? this : negate();
    }

    public BigFraction add(BigInteger bg) throws NullArgumentException {
        MathUtils.checkNotNull(bg);
        return new BigFraction(this.numerator.add(this.denominator.multiply(bg)), this.denominator);
    }

    public BigFraction add(int i) {
        return add(BigInteger.valueOf((long) i));
    }

    public BigFraction add(long l) {
        return add(BigInteger.valueOf(l));
    }

    public BigFraction add(BigFraction fraction) {
        if (fraction == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (ZERO.equals(fraction)) {
            return this;
        } else {
            BigInteger num;
            BigInteger den;
            if (this.denominator.equals(fraction.denominator)) {
                num = this.numerator.add(fraction.numerator);
                den = this.denominator;
            } else {
                num = this.numerator.multiply(fraction.denominator).add(fraction.numerator.multiply(this.denominator));
                den = this.denominator.multiply(fraction.denominator);
            }
            this(num, den);
            return this;
        }
    }

    public BigDecimal bigDecimalValue() {
        return new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator));
    }

    public BigDecimal bigDecimalValue(int roundingMode) {
        return new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator), roundingMode);
    }

    public BigDecimal bigDecimalValue(int scale, int roundingMode) {
        return new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator), scale, roundingMode);
    }

    public int compareTo(BigFraction object) {
        return this.numerator.multiply(object.denominator).compareTo(this.denominator.multiply(object.numerator));
    }

    public BigFraction divide(BigInteger bg) {
        if (bg == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (!BigInteger.ZERO.equals(bg)) {
            return new BigFraction(this.numerator, this.denominator.multiply(bg));
        } else {
            throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
        }
    }

    public BigFraction divide(int i) {
        return divide(BigInteger.valueOf((long) i));
    }

    public BigFraction divide(long l) {
        return divide(BigInteger.valueOf(l));
    }

    public BigFraction divide(BigFraction fraction) {
        if (fraction == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (!BigInteger.ZERO.equals(fraction.numerator)) {
            return multiply(fraction.reciprocal());
        } else {
            throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
        }
    }

    public double doubleValue() {
        double result = this.numerator.doubleValue() / this.denominator.doubleValue();
        if (!Double.isNaN(result)) {
            return result;
        }
        int shift = FastMath.max(this.numerator.bitLength(), this.denominator.bitLength()) - FastMath.getExponent(Double.MAX_VALUE);
        return this.numerator.shiftRight(shift).doubleValue() / this.denominator.shiftRight(shift).doubleValue();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BigFraction)) {
            return false;
        }
        BigFraction rhs = ((BigFraction) other).reduce();
        BigFraction thisOne = reduce();
        boolean ret = thisOne.numerator.equals(rhs.numerator) && thisOne.denominator.equals(rhs.denominator);
        return ret;
    }

    public float floatValue() {
        float result = this.numerator.floatValue() / this.denominator.floatValue();
        if (!Double.isNaN((double) result)) {
            return result;
        }
        int shift = FastMath.max(this.numerator.bitLength(), this.denominator.bitLength()) - FastMath.getExponent((float) AutoScrollHelper.NO_MAX);
        return this.numerator.shiftRight(shift).floatValue() / this.denominator.shiftRight(shift).floatValue();
    }

    public BigInteger getDenominator() {
        return this.denominator;
    }

    public int getDenominatorAsInt() {
        return this.denominator.intValue();
    }

    public long getDenominatorAsLong() {
        return this.denominator.longValue();
    }

    public BigInteger getNumerator() {
        return this.numerator;
    }

    public int getNumeratorAsInt() {
        return this.numerator.intValue();
    }

    public long getNumeratorAsLong() {
        return this.numerator.longValue();
    }

    public int hashCode() {
        return ((this.numerator.hashCode() + 629) * 37) + this.denominator.hashCode();
    }

    public int intValue() {
        return this.numerator.divide(this.denominator).intValue();
    }

    public long longValue() {
        return this.numerator.divide(this.denominator).longValue();
    }

    public BigFraction multiply(BigInteger bg) {
        if (bg != null) {
            return new BigFraction(bg.multiply(this.numerator), this.denominator);
        }
        throw new NullArgumentException();
    }

    public BigFraction multiply(int i) {
        return multiply(BigInteger.valueOf((long) i));
    }

    public BigFraction multiply(long l) {
        return multiply(BigInteger.valueOf(l));
    }

    public BigFraction multiply(BigFraction fraction) {
        if (fraction == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (this.numerator.equals(BigInteger.ZERO) || fraction.numerator.equals(BigInteger.ZERO)) {
            return ZERO;
        } else {
            return new BigFraction(this.numerator.multiply(fraction.numerator), this.denominator.multiply(fraction.denominator));
        }
    }

    public BigFraction negate() {
        return new BigFraction(this.numerator.negate(), this.denominator);
    }

    public double percentageValue() {
        return multiply(ONE_HUNDRED).doubleValue();
    }

    public BigFraction pow(int exponent) {
        if (exponent < 0) {
            return new BigFraction(this.denominator.pow(-exponent), this.numerator.pow(-exponent));
        }
        return new BigFraction(this.numerator.pow(exponent), this.denominator.pow(exponent));
    }

    public BigFraction pow(long exponent) {
        if (exponent < 0) {
            return new BigFraction(ArithmeticUtils.pow(this.denominator, -exponent), ArithmeticUtils.pow(this.numerator, -exponent));
        }
        return new BigFraction(ArithmeticUtils.pow(this.numerator, exponent), ArithmeticUtils.pow(this.denominator, exponent));
    }

    public BigFraction pow(BigInteger exponent) {
        if (exponent.compareTo(BigInteger.ZERO) >= 0) {
            return new BigFraction(ArithmeticUtils.pow(this.numerator, exponent), ArithmeticUtils.pow(this.denominator, exponent));
        }
        BigInteger eNeg = exponent.negate();
        return new BigFraction(ArithmeticUtils.pow(this.denominator, eNeg), ArithmeticUtils.pow(this.numerator, eNeg));
    }

    public double pow(double exponent) {
        return FastMath.pow(this.numerator.doubleValue(), exponent) / FastMath.pow(this.denominator.doubleValue(), exponent);
    }

    public BigFraction reciprocal() {
        return new BigFraction(this.denominator, this.numerator);
    }

    public BigFraction reduce() {
        BigInteger gcd = this.numerator.gcd(this.denominator);
        return new BigFraction(this.numerator.divide(gcd), this.denominator.divide(gcd));
    }

    public BigFraction subtract(BigInteger bg) {
        if (bg != null) {
            return new BigFraction(this.numerator.subtract(this.denominator.multiply(bg)), this.denominator);
        }
        throw new NullArgumentException();
    }

    public BigFraction subtract(int i) {
        return subtract(BigInteger.valueOf((long) i));
    }

    public BigFraction subtract(long l) {
        return subtract(BigInteger.valueOf(l));
    }

    public BigFraction subtract(BigFraction fraction) {
        if (fraction == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (ZERO.equals(fraction)) {
            return this;
        } else {
            BigInteger num;
            BigInteger den;
            if (this.denominator.equals(fraction.denominator)) {
                num = this.numerator.subtract(fraction.numerator);
                den = this.denominator;
            } else {
                num = this.numerator.multiply(fraction.denominator).subtract(fraction.numerator.multiply(this.denominator));
                den = this.denominator.multiply(fraction.denominator);
            }
            this(num, den);
            return this;
        }
    }

    public String toString() {
        if (BigInteger.ONE.equals(this.denominator)) {
            return this.numerator.toString();
        }
        if (BigInteger.ZERO.equals(this.numerator)) {
            return Constants.ZERO;
        }
        return this.numerator + " / " + this.denominator;
    }

    public BigFractionField getField() {
        return BigFractionField.getInstance();
    }
}
