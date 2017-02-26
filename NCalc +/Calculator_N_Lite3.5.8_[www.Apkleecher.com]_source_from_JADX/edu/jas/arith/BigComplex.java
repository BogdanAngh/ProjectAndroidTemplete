package edu.jas.arith;

import edu.jas.kern.StringUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.StarRingElem;
import io.github.kexanie.library.BuildConfig;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public final class BigComplex implements StarRingElem<BigComplex>, GcdRingElem<BigComplex>, RingFactory<BigComplex> {
    public static final BigComplex I;
    public static final BigComplex ONE;
    public static final BigComplex ZERO;
    private static final Logger logger;
    private static final Random random;
    public final BigRational im;
    public final BigRational re;

    static {
        random = new Random();
        logger = Logger.getLogger(BigComplex.class);
        ZERO = new BigComplex();
        ONE = new BigComplex(BigRational.ONE);
        I = new BigComplex(BigRational.ZERO, BigRational.ONE);
    }

    public BigComplex(BigRational r, BigRational i) {
        this.re = r;
        this.im = i;
    }

    public BigComplex(BigRational r) {
        this(r, BigRational.ZERO);
    }

    public BigComplex(long r) {
        this(new BigRational(r), BigRational.ZERO);
    }

    public BigComplex() {
        this(BigRational.ZERO);
    }

    public BigComplex(String s) throws NumberFormatException {
        if (s == null || s.length() == 0) {
            this.re = BigRational.ZERO;
            this.im = BigRational.ZERO;
            return;
        }
        s = s.trim();
        int i = s.indexOf("i");
        if (i < 0) {
            this.re = new BigRational(s);
            this.im = BigRational.ZERO;
            return;
        }
        String sr = BuildConfig.FLAVOR;
        if (i > 0) {
            sr = s.substring(0, i);
        }
        String si = BuildConfig.FLAVOR;
        if (i < s.length()) {
            si = s.substring(i + 1, s.length());
        }
        this.re = new BigRational(sr.trim());
        this.im = new BigRational(si.trim());
    }

    public BigComplex factory() {
        return this;
    }

    public List<BigComplex> generators() {
        List<BigComplex> g = new ArrayList(2);
        g.add(getONE());
        g.add(getIMAG());
        return g;
    }

    public boolean isFinite() {
        return false;
    }

    public BigComplex copy() {
        return new BigComplex(this.re, this.im);
    }

    public BigComplex copy(BigComplex c) {
        return new BigComplex(c.re, c.im);
    }

    public BigComplex getZERO() {
        return ZERO;
    }

    public BigComplex getONE() {
        return ONE;
    }

    public BigComplex getIMAG() {
        return I;
    }

    public boolean isCommutative() {
        return true;
    }

    public boolean isAssociative() {
        return true;
    }

    public boolean isField() {
        return true;
    }

    public BigInteger characteristic() {
        return BigInteger.ZERO;
    }

    public BigComplex fromInteger(BigInteger a) {
        return new BigComplex(new BigRational(a));
    }

    public BigComplex fromInteger(long a) {
        return new BigComplex(new BigRational(a));
    }

    public BigRational getRe() {
        return this.re;
    }

    public BigRational getIm() {
        return this.im;
    }

    public String toString() {
        String s = BuildConfig.FLAVOR + this.re;
        if (this.im.compareTo(BigRational.ZERO) == 0) {
            return s;
        }
        return s + "i" + this.im;
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        if (this.im.isZERO()) {
            s.append(this.re.toScript());
            return s.toString();
        }
        BigRational ii;
        if (!this.re.isZERO()) {
            s.append(this.re.toScript());
            if (this.im.signum() > 0) {
                s.append("+");
                if (!this.im.isONE()) {
                    s.append(this.im.toScript() + "*");
                }
            } else {
                s.append("-");
                ii = this.im.negate();
                if (!ii.isONE()) {
                    s.append(ii.toScript() + "*");
                }
            }
        } else if (!this.im.isONE()) {
            if (this.im.signum() > 0) {
                s.append(this.im.toScript() + "*");
            } else {
                s.append("-");
                ii = this.im.negate();
                if (!ii.isONE()) {
                    s.append(ii.toScript() + "*");
                }
            }
        }
        s.append("I");
        return s.toString();
    }

    public String toScriptFactory() {
        return "CC()";
    }

    public static boolean isCZERO(BigComplex A) {
        if (A == null) {
            return false;
        }
        return A.isZERO();
    }

    public boolean isZERO() {
        return this.re.equals(BigRational.ZERO) && this.im.equals(BigRational.ZERO);
    }

    public static boolean isCONE(BigComplex A) {
        if (A == null) {
            return false;
        }
        return A.isONE();
    }

    public boolean isONE() {
        return this.re.equals(BigRational.ONE) && this.im.equals(BigRational.ZERO);
    }

    public boolean isIMAG() {
        return this.re.equals(BigRational.ZERO) && this.im.equals(BigRational.ONE);
    }

    public boolean isUnit() {
        return !isZERO();
    }

    public boolean equals(Object b) {
        if (!(b instanceof BigComplex)) {
            return false;
        }
        BigComplex bc = (BigComplex) b;
        if (this.re.equals(bc.re) && this.im.equals(bc.im)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.re.hashCode() * 37) + this.im.hashCode();
    }

    public int compareTo(BigComplex b) {
        int s = this.re.compareTo(b.re);
        return s != 0 ? s : this.im.compareTo(b.im);
    }

    public int signum() {
        int s = this.re.signum();
        return s != 0 ? s : this.im.signum();
    }

    public BigComplex sum(BigComplex B) {
        return new BigComplex(this.re.sum(B.re), this.im.sum(B.im));
    }

    public static BigComplex CSUM(BigComplex A, BigComplex B) {
        if (A == null) {
            return null;
        }
        return A.sum(B);
    }

    public static BigComplex CDIF(BigComplex A, BigComplex B) {
        if (A == null) {
            return null;
        }
        return A.subtract(B);
    }

    public BigComplex subtract(BigComplex B) {
        return new BigComplex(this.re.subtract(B.re), this.im.subtract(B.im));
    }

    public static BigComplex CNEG(BigComplex A) {
        if (A == null) {
            return null;
        }
        return A.negate();
    }

    public BigComplex negate() {
        return new BigComplex(this.re.negate(), this.im.negate());
    }

    public static BigComplex CCON(BigComplex A) {
        if (A == null) {
            return null;
        }
        return A.conjugate();
    }

    public BigComplex conjugate() {
        return new BigComplex(this.re, this.im.negate());
    }

    public BigComplex norm() {
        return new BigComplex(this.re.multiply(this.re).sum(this.im.multiply(this.im)));
    }

    public BigComplex abs() {
        BigComplex n = norm();
        logger.error("abs() square root missing");
        return n;
    }

    public static BigRational CABS(BigComplex A) {
        if (A == null) {
            return null;
        }
        return A.abs().re;
    }

    public static BigComplex CPROD(BigComplex A, BigComplex B) {
        if (A == null) {
            return null;
        }
        return A.multiply(B);
    }

    public BigComplex multiply(BigComplex B) {
        return new BigComplex(this.re.multiply(B.re).subtract(this.im.multiply(B.im)), this.re.multiply(B.im).sum(this.im.multiply(B.re)));
    }

    public static BigComplex CINV(BigComplex A) {
        if (A == null) {
            return null;
        }
        return A.inverse();
    }

    public BigComplex inverse() {
        BigRational a = norm().re.inverse();
        return new BigComplex(this.re.multiply(a), this.im.multiply(a.negate()));
    }

    public BigComplex remainder(BigComplex S) {
        if (!S.isZERO()) {
            return ZERO;
        }
        throw new ArithmeticException("division by zero");
    }

    public static BigComplex CQ(BigComplex A, BigComplex B) {
        if (A == null) {
            return null;
        }
        return A.divide(B);
    }

    public BigComplex divide(BigComplex B) {
        return multiply(B.inverse());
    }

    public BigComplex[] quotientRemainder(BigComplex S) {
        return new BigComplex[]{divide(S), ZERO};
    }

    public BigComplex random(int n) {
        return random(n, random);
    }

    public BigComplex random(int n, Random rnd) {
        return new BigComplex(BigRational.ONE.random(n, rnd), BigRational.ONE.random(n, rnd));
    }

    public static BigComplex CRAND(int n) {
        return ONE.random(n, random);
    }

    public BigComplex parse(String s) {
        return new BigComplex(s);
    }

    public BigComplex parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public BigComplex gcd(BigComplex S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return !isZERO() ? ONE : S;
    }

    public BigComplex[] egcd(BigComplex S) {
        BigComplex[] ret = new BigComplex[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else {
            BigComplex half = new BigComplex(new BigRational(1, 2));
            ret[0] = ONE;
            ret[1] = inverse().multiply(half);
            ret[2] = S.inverse().multiply(half);
        }
        return ret;
    }
}
