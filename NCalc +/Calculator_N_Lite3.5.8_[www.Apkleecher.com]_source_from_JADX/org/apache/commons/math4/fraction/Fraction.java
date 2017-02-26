package org.apache.commons.math4.fraction;

import com.example.duy.calculator.math_eval.Constants;
import java.io.Serializable;
import java.math.BigInteger;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.ArithmeticUtils;
import org.apache.commons.math4.util.FastMath;

public class Fraction extends Number implements FieldElement<Fraction>, Comparable<Fraction>, Serializable {
    private static final double DEFAULT_EPSILON = 1.0E-5d;
    public static final Fraction FOUR_FIFTHS;
    public static final Fraction MINUS_ONE;
    public static final Fraction ONE;
    public static final Fraction ONE_FIFTH;
    public static final Fraction ONE_HALF;
    public static final Fraction ONE_QUARTER;
    public static final Fraction ONE_THIRD;
    public static final Fraction THREE_FIFTHS;
    public static final Fraction THREE_QUARTERS;
    public static final Fraction TWO;
    public static final Fraction TWO_FIFTHS;
    public static final Fraction TWO_QUARTERS;
    public static final Fraction TWO_THIRDS;
    public static final Fraction ZERO;
    private static final long serialVersionUID = 3698073679419233275L;
    private final int denominator;
    private final int numerator;

    static {
        TWO = new Fraction(2, 1);
        ONE = new Fraction(1, 1);
        ZERO = new Fraction(0, 1);
        FOUR_FIFTHS = new Fraction(4, 5);
        ONE_FIFTH = new Fraction(1, 5);
        ONE_HALF = new Fraction(1, 2);
        ONE_QUARTER = new Fraction(1, 4);
        ONE_THIRD = new Fraction(1, 3);
        THREE_FIFTHS = new Fraction(3, 5);
        THREE_QUARTERS = new Fraction(3, 4);
        TWO_FIFTHS = new Fraction(2, 5);
        TWO_QUARTERS = new Fraction(2, 4);
        TWO_THIRDS = new Fraction(2, 3);
        MINUS_ONE = new Fraction(-1, 1);
    }

    public Fraction(double value) throws FractionConversionException {
        this(value, DEFAULT_EPSILON, 100);
    }

    public Fraction(double value, double epsilon, int maxIterations) throws FractionConversionException {
        this(value, epsilon, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, maxIterations);
    }

    public Fraction(double value, int maxDenominator) throws FractionConversionException {
        this(value, 0.0d, maxDenominator, 100);
    }

    private Fraction(double value, double epsilon, int maxDenominator, int maxIterations) throws FractionConversionException {
        double r0 = value;
        long a0 = (long) FastMath.floor(r0);
        if (FastMath.abs(a0) > 2147483647L) {
            throw new FractionConversionException(value, a0, 1);
        } else if (FastMath.abs(((double) a0) - value) < epsilon) {
            this.numerator = (int) a0;
            this.denominator = 1;
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
                if (FastMath.abs(p2) <= 2147483647L && FastMath.abs(q2) <= 2147483647L) {
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
                    this.numerator = (int) p2;
                    this.denominator = (int) q2;
                } else {
                    this.numerator = (int) p1;
                    this.denominator = (int) q1;
                }
            } while (!stop);
            if (n >= maxIterations) {
                throw new FractionConversionException(value, maxIterations);
            } else if (q2 >= ((long) maxDenominator)) {
                this.numerator = (int) p1;
                this.denominator = (int) q1;
            } else {
                this.numerator = (int) p2;
                this.denominator = (int) q2;
            }
        }
    }

    public Fraction(int num) {
        this(num, 1);
    }

    public Fraction(int num, int den) {
        if (den == 0) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR_IN_FRACTION, Integer.valueOf(num), Integer.valueOf(den));
        }
        if (den < 0) {
            if (num == RtlSpacingHelper.UNDEFINED || den == RtlSpacingHelper.UNDEFINED) {
                throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_FRACTION, Integer.valueOf(num), Integer.valueOf(den));
            }
            num = -num;
            den = -den;
        }
        int d = ArithmeticUtils.gcd(num, den);
        if (d > 1) {
            num /= d;
            den /= d;
        }
        if (den < 0) {
            num = -num;
            den = -den;
        }
        this.numerator = num;
        this.denominator = den;
    }

    public Fraction abs() {
        if (this.numerator >= 0) {
            return this;
        }
        return negate();
    }

    public int compareTo(Fraction object) {
        long nOd = ((long) this.numerator) * ((long) object.denominator);
        long dOn = ((long) this.denominator) * ((long) object.numerator);
        if (nOd < dOn) {
            return -1;
        }
        return nOd > dOn ? 1 : 0;
    }

    public double doubleValue() {
        return ((double) this.numerator) / ((double) this.denominator);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Fraction)) {
            return false;
        }
        Fraction rhs = (Fraction) other;
        if (this.numerator == rhs.numerator && this.denominator == rhs.denominator) {
            return true;
        }
        return false;
    }

    public float floatValue() {
        return (float) doubleValue();
    }

    public int getDenominator() {
        return this.denominator;
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int hashCode() {
        return ((this.numerator + 629) * 37) + this.denominator;
    }

    public int intValue() {
        return (int) doubleValue();
    }

    public long longValue() {
        return (long) doubleValue();
    }

    public Fraction negate() {
        if (this.numerator != RtlSpacingHelper.UNDEFINED) {
            return new Fraction(-this.numerator, this.denominator);
        }
        throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_FRACTION, Integer.valueOf(this.numerator), Integer.valueOf(this.denominator));
    }

    public Fraction reciprocal() {
        return new Fraction(this.denominator, this.numerator);
    }

    public Fraction add(Fraction fraction) {
        return addSub(fraction, true);
    }

    public Fraction add(int i) {
        return new Fraction(this.numerator + (this.denominator * i), this.denominator);
    }

    public Fraction subtract(Fraction fraction) {
        return addSub(fraction, false);
    }

    public Fraction subtract(int i) {
        return new Fraction(this.numerator - (this.denominator * i), this.denominator);
    }

    private Fraction addSub(Fraction fraction, boolean isAdd) {
        if (fraction == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (this.numerator == 0) {
            if (isAdd) {
                return fraction;
            }
            return fraction.negate();
        } else if (fraction.numerator == 0) {
            return this;
        } else {
            int d1 = ArithmeticUtils.gcd(this.denominator, fraction.denominator);
            if (d1 == 1) {
                int addAndCheck;
                int uvp = ArithmeticUtils.mulAndCheck(this.numerator, fraction.denominator);
                int upv = ArithmeticUtils.mulAndCheck(fraction.numerator, this.denominator);
                if (isAdd) {
                    addAndCheck = ArithmeticUtils.addAndCheck(uvp, upv);
                } else {
                    addAndCheck = ArithmeticUtils.subAndCheck(uvp, upv);
                }
                return new Fraction(addAndCheck, ArithmeticUtils.mulAndCheck(this.denominator, fraction.denominator));
            }
            BigInteger uvp2 = BigInteger.valueOf((long) this.numerator).multiply(BigInteger.valueOf((long) (fraction.denominator / d1)));
            BigInteger upv2 = BigInteger.valueOf((long) fraction.numerator).multiply(BigInteger.valueOf((long) (this.denominator / d1)));
            BigInteger t = isAdd ? uvp2.add(upv2) : uvp2.subtract(upv2);
            int tmodd1 = t.mod(BigInteger.valueOf((long) d1)).intValue();
            int d2 = tmodd1 == 0 ? d1 : ArithmeticUtils.gcd(tmodd1, d1);
            BigInteger w = t.divide(BigInteger.valueOf((long) d2));
            if (w.bitLength() <= 31) {
                return new Fraction(w.intValue(), ArithmeticUtils.mulAndCheck(this.denominator / d1, fraction.denominator / d2));
            }
            throw new MathArithmeticException(LocalizedFormats.NUMERATOR_OVERFLOW_AFTER_MULTIPLY, w);
        }
    }

    public Fraction multiply(Fraction fraction) {
        if (fraction == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (this.numerator == 0 || fraction.numerator == 0) {
            return ZERO;
        } else {
            int d1 = ArithmeticUtils.gcd(this.numerator, fraction.denominator);
            int d2 = ArithmeticUtils.gcd(fraction.numerator, this.denominator);
            return getReducedFraction(ArithmeticUtils.mulAndCheck(this.numerator / d1, fraction.numerator / d2), ArithmeticUtils.mulAndCheck(this.denominator / d2, fraction.denominator / d1));
        }
    }

    public Fraction multiply(int i) {
        return new Fraction(this.numerator * i, this.denominator);
    }

    public Fraction divide(Fraction fraction) {
        if (fraction == null) {
            throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
        } else if (fraction.numerator != 0) {
            return multiply(fraction.reciprocal());
        } else {
            throw new MathArithmeticException(LocalizedFormats.ZERO_FRACTION_TO_DIVIDE_BY, Integer.valueOf(fraction.numerator), Integer.valueOf(fraction.denominator));
        }
    }

    public Fraction divide(int i) {
        return new Fraction(this.numerator, this.denominator * i);
    }

    public double percentageValue() {
        return 100.0d * doubleValue();
    }

    public static Fraction getReducedFraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR_IN_FRACTION, Integer.valueOf(numerator), Integer.valueOf(denominator));
        } else if (numerator == 0) {
            return ZERO;
        } else {
            if (denominator == RtlSpacingHelper.UNDEFINED && (numerator & 1) == 0) {
                numerator /= 2;
                denominator /= 2;
            }
            if (denominator < 0) {
                if (numerator == RtlSpacingHelper.UNDEFINED || denominator == RtlSpacingHelper.UNDEFINED) {
                    throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_FRACTION, Integer.valueOf(numerator), Integer.valueOf(denominator));
                }
                numerator = -numerator;
                denominator = -denominator;
            }
            int gcd = ArithmeticUtils.gcd(numerator, denominator);
            return new Fraction(numerator / gcd, denominator / gcd);
        }
    }

    public String toString() {
        if (this.denominator == 1) {
            return Integer.toString(this.numerator);
        }
        if (this.numerator == 0) {
            return Constants.ZERO;
        }
        return this.numerator + " / " + this.denominator;
    }

    public FractionField getField() {
        return FractionField.getInstance();
    }
}
