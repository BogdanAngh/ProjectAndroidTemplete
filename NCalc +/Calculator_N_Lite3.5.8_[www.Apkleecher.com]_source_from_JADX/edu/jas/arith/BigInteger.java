package edu.jas.arith;

import edu.jas.kern.StringUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class BigInteger implements GcdRingElem<BigInteger>, RingFactory<BigInteger>, Iterable<BigInteger>, Rational {
    public static final BigInteger ONE;
    public static final BigInteger ZERO;
    private static final Random random;
    private boolean nonNegative;
    public final java.math.BigInteger val;

    static {
        random = new Random();
        ZERO = new BigInteger(java.math.BigInteger.ZERO);
        ONE = new BigInteger(java.math.BigInteger.ONE);
    }

    public BigInteger(java.math.BigInteger a) {
        this.nonNegative = true;
        this.val = a;
    }

    public BigInteger(long a) {
        this.nonNegative = true;
        this.val = new java.math.BigInteger(String.valueOf(a));
    }

    public BigInteger(String s) {
        this.nonNegative = true;
        this.val = new java.math.BigInteger(s.trim());
    }

    public BigInteger() {
        this.nonNegative = true;
        this.val = java.math.BigInteger.ZERO;
    }

    public java.math.BigInteger getVal() {
        return this.val;
    }

    public long longValue() {
        return this.val.longValue();
    }

    public BigInteger factory() {
        return this;
    }

    public List<BigInteger> generators() {
        List<BigInteger> g = new ArrayList(1);
        g.add(getONE());
        return g;
    }

    public boolean isFinite() {
        return false;
    }

    public BigInteger copy() {
        return new BigInteger(this.val);
    }

    public BigInteger copy(BigInteger c) {
        return new BigInteger(c.val);
    }

    public BigInteger getZERO() {
        return ZERO;
    }

    public BigInteger getONE() {
        return ONE;
    }

    public boolean isCommutative() {
        return true;
    }

    public boolean isAssociative() {
        return true;
    }

    public boolean isField() {
        return false;
    }

    public java.math.BigInteger characteristic() {
        return java.math.BigInteger.ZERO;
    }

    public BigInteger fromInteger(java.math.BigInteger a) {
        return new BigInteger(a);
    }

    public static BigInteger valueOf(java.math.BigInteger a) {
        return new BigInteger(a);
    }

    public BigInteger fromInteger(long a) {
        return new BigInteger(a);
    }

    public static BigInteger valueOf(long a) {
        return new BigInteger(a);
    }

    public boolean isZERO() {
        return this.val.equals(java.math.BigInteger.ZERO);
    }

    public boolean isONE() {
        return this.val.equals(java.math.BigInteger.ONE);
    }

    public boolean isUnit() {
        return isONE() || negate().isONE();
    }

    public String toString() {
        return this.val.toString();
    }

    public String toScript() {
        return toString();
    }

    public String toScriptFactory() {
        return "ZZ()";
    }

    public int compareTo(BigInteger b) {
        return this.val.compareTo(b.val);
    }

    public static int ICOMP(BigInteger A, BigInteger B) {
        if (A == null) {
            return -B.signum();
        }
        return A.compareTo(B);
    }

    public boolean equals(Object b) {
        if (!(b instanceof BigInteger)) {
            return false;
        }
        return this.val.equals(((BigInteger) b).val);
    }

    public int hashCode() {
        return this.val.hashCode();
    }

    public BigInteger abs() {
        return new BigInteger(this.val.abs());
    }

    public static BigInteger IABS(BigInteger A) {
        if (A != null) {
            return A.abs();
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public BigInteger negate() {
        return new BigInteger(this.val.negate());
    }

    public static BigInteger INEG(BigInteger A) {
        if (A != null) {
            return A.negate();
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public int signum() {
        return this.val.signum();
    }

    public static int ISIGN(BigInteger A) {
        if (A == null) {
            return 0;
        }
        return A.signum();
    }

    public BigInteger subtract(BigInteger S) {
        return new BigInteger(this.val.subtract(S.val));
    }

    public static BigInteger IDIF(BigInteger A, BigInteger B) {
        if (A == null) {
            return B.negate();
        }
        return A.subtract(B);
    }

    public BigInteger divide(BigInteger S) {
        return new BigInteger(this.val.divide(S.val));
    }

    public static BigInteger IQ(BigInteger A, BigInteger B) {
        if (A != null) {
            return A.divide(B);
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public BigInteger inverse() {
        if (isONE() || negate().isONE()) {
            return this;
        }
        throw new NotInvertibleException("element not invertible " + this + " :: BigInteger");
    }

    public BigInteger remainder(BigInteger S) {
        return new BigInteger(this.val.remainder(S.val));
    }

    public static BigInteger IREM(BigInteger A, BigInteger B) {
        if (A != null) {
            return A.remainder(B);
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public BigInteger[] quotientRemainder(BigInteger S) {
        qr = new BigInteger[2];
        java.math.BigInteger[] C = this.val.divideAndRemainder(S.val);
        qr[0] = new BigInteger(C[0]);
        qr[1] = new BigInteger(C[1]);
        return qr;
    }

    @Deprecated
    public BigInteger[] divideAndRemainder(BigInteger S) {
        return quotientRemainder(S);
    }

    public static BigInteger[] IQR(BigInteger A, BigInteger B) {
        if (A != null) {
            return A.quotientRemainder(B);
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public BigInteger gcd(BigInteger S) {
        return new BigInteger(this.val.gcd(S.val));
    }

    public BigInteger[] egcd(BigInteger S) {
        BigInteger[] ret = new BigInteger[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else {
            BigInteger q = this;
            BigInteger r = S;
            BigInteger c1 = ONE;
            BigInteger d1 = ZERO;
            BigInteger c2 = ZERO;
            BigInteger d2 = ONE;
            while (!r.isZERO()) {
                BigInteger[] qr = q.quotientRemainder(r);
                q = qr[0];
                BigInteger x1 = c1.subtract(q.multiply(d1));
                BigInteger x2 = c2.subtract(q.multiply(d2));
                c1 = d1;
                c2 = d2;
                d1 = x1;
                d2 = x2;
                q = r;
                r = qr[1];
            }
            if (q.signum() < 0) {
                q = q.negate();
                c1 = c1.negate();
                c2 = c2.negate();
            }
            ret[0] = q;
            ret[1] = c1;
            ret[2] = c2;
        }
        return ret;
    }

    public static BigInteger IGCD(BigInteger A, BigInteger B) {
        if (A != null) {
            return A.gcd(B);
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public BigInteger random(int n) {
        return random(n, random);
    }

    public BigInteger random(int n, Random rnd) {
        java.math.BigInteger r = new java.math.BigInteger(n, rnd);
        if (rnd.nextBoolean()) {
            r = r.negate();
        }
        return new BigInteger(r);
    }

    public static BigInteger IRAND(int NL) {
        return ONE.random(NL, random);
    }

    public BigInteger multiply(BigInteger S) {
        return new BigInteger(this.val.multiply(S.val));
    }

    public BigInteger shiftLeft(int n) {
        return new BigInteger(this.val.shiftLeft(n));
    }

    public static BigInteger IPROD(BigInteger A, BigInteger B) {
        if (A != null) {
            return A.multiply(B);
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public BigInteger sum(BigInteger S) {
        return new BigInteger(this.val.add(S.val));
    }

    public static BigInteger ISUM(BigInteger A, BigInteger B) {
        if (A != null) {
            return A.sum(B);
        }
        throw new IllegalArgumentException("null A not allowed");
    }

    public BigInteger parse(String s) {
        return new BigInteger(s);
    }

    public BigInteger parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public BigRational getRational() {
        return new BigRational(this.val);
    }

    public void setAllIterator() {
        this.nonNegative = false;
    }

    public void setNonNegativeIterator() {
        this.nonNegative = true;
    }

    public Iterator<BigInteger> iterator() {
        return new BigIntegerIterator(this.nonNegative);
    }
}
