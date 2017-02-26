package edu.jas.arith;

import edu.jas.kern.StringUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BigDecimal implements GcdRingElem<BigDecimal>, RingFactory<BigDecimal> {
    public static final MathContext DEFAULT_CONTEXT;
    public static final int DEFAULT_PRECISION;
    public static final boolean EXACT_EQUAL = true;
    public static final BigDecimal ONE;
    public static final BigDecimal ZERO;
    private static final Random random;
    public final MathContext context;
    public final java.math.BigDecimal val;

    static {
        random = new Random();
        DEFAULT_CONTEXT = MathContext.DECIMAL128;
        DEFAULT_PRECISION = DEFAULT_CONTEXT.getPrecision();
        ZERO = new BigDecimal(java.math.BigDecimal.ZERO);
        ONE = new BigDecimal(java.math.BigDecimal.ONE);
    }

    public BigDecimal(java.math.BigDecimal a) {
        this(a, DEFAULT_CONTEXT);
    }

    public BigDecimal(java.math.BigDecimal a, MathContext mc) {
        this.val = a;
        this.context = mc;
    }

    public BigDecimal(long a) {
        this(a, DEFAULT_CONTEXT);
    }

    public BigDecimal(long a, MathContext mc) {
        this(new java.math.BigDecimal(String.valueOf(a)), mc);
    }

    public BigDecimal(double a) {
        this(a, DEFAULT_CONTEXT);
    }

    public BigDecimal(double a, MathContext mc) {
        this(new java.math.BigDecimal(a, mc), mc);
    }

    public BigDecimal(BigInteger a) {
        this(a, DEFAULT_CONTEXT);
    }

    public BigDecimal(BigInteger a, MathContext mc) {
        this(new java.math.BigDecimal(a), mc);
    }

    public BigDecimal(BigRational a) {
        this(a, DEFAULT_CONTEXT);
    }

    public BigDecimal(BigRational a, MathContext mc) {
        this(new java.math.BigDecimal(a.num, mc).divide(new java.math.BigDecimal(a.den, mc), mc), mc);
    }

    public BigDecimal(String s) {
        this(s, DEFAULT_CONTEXT);
    }

    public BigDecimal(String s, MathContext mc) {
        this(new java.math.BigDecimal(s.trim()), mc);
    }

    public BigDecimal() {
        this(java.math.BigDecimal.ZERO, DEFAULT_CONTEXT);
    }

    public BigDecimal factory() {
        return this;
    }

    public List<BigDecimal> generators() {
        List<BigDecimal> g = new ArrayList(1);
        g.add(getONE());
        return g;
    }

    public boolean isFinite() {
        return false;
    }

    public BigDecimal copy() {
        return new BigDecimal(this.val, this.context);
    }

    public BigDecimal copy(BigDecimal c) {
        return new BigDecimal(c.val, c.context);
    }

    public BigDecimal getZERO() {
        return ZERO;
    }

    public BigDecimal getONE() {
        return ONE;
    }

    public boolean isCommutative() {
        return EXACT_EQUAL;
    }

    public boolean isAssociative() {
        return EXACT_EQUAL;
    }

    public boolean isField() {
        return EXACT_EQUAL;
    }

    public BigInteger characteristic() {
        return BigInteger.ZERO;
    }

    public BigDecimal fromInteger(BigInteger a) {
        return new BigDecimal(new java.math.BigDecimal(a), this.context);
    }

    public static BigDecimal valueOf(java.math.BigDecimal a) {
        return new BigDecimal(a, DEFAULT_CONTEXT);
    }

    public BigDecimal fromInteger(long a) {
        return new BigDecimal(a, this.context);
    }

    public static BigDecimal valueOf(long a) {
        return new BigDecimal(a, DEFAULT_CONTEXT);
    }

    public boolean isZERO() {
        return this.val.compareTo(java.math.BigDecimal.ZERO) == 0 ? EXACT_EQUAL : false;
    }

    public boolean isONE() {
        return this.val.compareTo(java.math.BigDecimal.ONE) == 0 ? EXACT_EQUAL : false;
    }

    public boolean isUnit() {
        return !isZERO() ? EXACT_EQUAL : false;
    }

    public String toString() {
        return this.val.toString();
    }

    public double doubleValue() {
        return this.val.doubleValue();
    }

    public String toScript() {
        return toString();
    }

    public String toScriptFactory() {
        return "DD()";
    }

    public int compareTo(BigDecimal b) {
        return compareToRelative(b);
    }

    public int compareToAbsolute(BigDecimal b) {
        java.math.BigDecimal eps;
        java.math.BigDecimal s = this.val.subtract(b.val, this.context);
        java.math.BigDecimal u1 = this.val.ulp();
        java.math.BigDecimal u2 = b.val.ulp();
        if (Math.min(u1.scale(), u2.scale()) <= 0) {
            eps = u1.max(u2);
        } else {
            eps = u1.min(u2);
        }
        if (s.abs().compareTo(eps) < 1) {
            return DEFAULT_PRECISION;
        }
        return s.signum();
    }

    public int compareToRelative(BigDecimal b) {
        java.math.BigDecimal eps;
        int t;
        java.math.BigDecimal s = this.val.subtract(b.val, this.context);
        java.math.BigDecimal u1 = this.val.ulp();
        java.math.BigDecimal u2 = b.val.ulp();
        if (Math.min(u1.scale(), u2.scale()) <= 0) {
            eps = u1.max(u2);
        } else {
            eps = u1.min(u2);
        }
        eps = eps.movePointRight(1);
        java.math.BigDecimal m = this.val.abs().max(b.val.abs());
        if (m.compareTo(java.math.BigDecimal.ONE) <= 1) {
            t = s.abs().compareTo(eps);
        } else {
            t = s.abs().divide(m, this.context).compareTo(eps);
        }
        if (t < 1) {
            return DEFAULT_PRECISION;
        }
        return s.signum();
    }

    public boolean equals(Object b) {
        if (!(b instanceof BigDecimal)) {
            return false;
        }
        return this.val.equals(((BigDecimal) b).val);
    }

    public int hashCode() {
        return this.val.hashCode();
    }

    public BigDecimal abs() {
        return new BigDecimal(this.val.abs(), this.context);
    }

    public BigDecimal negate() {
        return new BigDecimal(this.val.negate(), this.context);
    }

    public int signum() {
        return this.val.signum();
    }

    public BigDecimal subtract(BigDecimal S) {
        return new BigDecimal(this.val.subtract(S.val, this.context), this.context);
    }

    public BigDecimal divide(BigDecimal S) {
        return new BigDecimal(this.val.divide(S.val, this.context), this.context);
    }

    public BigDecimal inverse() {
        return ONE.divide(this);
    }

    public BigDecimal remainder(BigDecimal S) {
        return new BigDecimal(this.val.remainder(S.val, this.context), this.context);
    }

    public BigDecimal[] quotientRemainder(BigDecimal S) {
        qr = new BigDecimal[2];
        java.math.BigDecimal[] C = this.val.divideAndRemainder(S.val, this.context);
        qr[DEFAULT_PRECISION] = new BigDecimal(C[DEFAULT_PRECISION], this.context);
        qr[1] = new BigDecimal(C[1], this.context);
        return qr;
    }

    @Deprecated
    public BigDecimal[] divideAndRemainder(BigDecimal S) {
        return quotientRemainder(S);
    }

    public BigDecimal gcd(BigDecimal S) {
        throw new UnsupportedOperationException("BigDecimal.gcd() not implemented");
    }

    public BigDecimal[] egcd(BigDecimal S) {
        throw new UnsupportedOperationException("BigDecimal.egcd() not implemented");
    }

    public BigDecimal random(int n) {
        return random(n, random);
    }

    public BigDecimal random(int n, Random rnd) {
        return random(n, 10, rnd);
    }

    public BigDecimal random(int n, int e) {
        return random(n, e, random);
    }

    public BigDecimal random(int n, int e, Random rnd) {
        BigInteger r = new BigInteger(n, rnd);
        if (rnd.nextBoolean()) {
            r = r.negate();
        }
        int scale = rnd.nextInt(e);
        if (rnd.nextBoolean()) {
            scale = -scale;
        }
        return new BigDecimal(new java.math.BigDecimal(r, scale, this.context), this.context);
    }

    public BigDecimal multiply(BigDecimal S) {
        return new BigDecimal(this.val.multiply(S.val, this.context), this.context);
    }

    public BigDecimal sum(BigDecimal S) {
        return new BigDecimal(this.val.add(S.val, this.context), this.context);
    }

    public BigDecimal parse(String s) {
        return new BigDecimal(s, this.context);
    }

    public BigDecimal parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }
}
