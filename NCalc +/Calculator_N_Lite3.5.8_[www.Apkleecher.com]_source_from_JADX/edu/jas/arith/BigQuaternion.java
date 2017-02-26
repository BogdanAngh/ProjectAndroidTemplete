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

public final class BigQuaternion implements StarRingElem<BigQuaternion>, GcdRingElem<BigQuaternion>, RingFactory<BigQuaternion> {
    public static final BigQuaternion I;
    public static final BigQuaternion J;
    public static final BigQuaternion K;
    public static final BigQuaternion ONE;
    public static final BigQuaternion ZERO;
    private static final Logger logger;
    private static final Random random;
    private final boolean debug;
    public final BigRational im;
    public final BigRational jm;
    public final BigRational km;
    public final BigRational re;

    static {
        random = new Random();
        logger = Logger.getLogger(BigQuaternion.class);
        ZERO = new BigQuaternion();
        ONE = new BigQuaternion(BigRational.ONE);
        I = new BigQuaternion(BigRational.ZERO, BigRational.ONE);
        J = new BigQuaternion(BigRational.ZERO, BigRational.ZERO, BigRational.ONE);
        K = new BigQuaternion(BigRational.ZERO, BigRational.ZERO, BigRational.ZERO, BigRational.ONE);
    }

    public BigQuaternion(BigRational r, BigRational i, BigRational j, BigRational k) {
        this.debug = logger.isDebugEnabled();
        this.re = r;
        this.im = i;
        this.jm = j;
        this.km = k;
    }

    public BigQuaternion(BigRational r, BigRational i, BigRational j) {
        this(r, i, j, BigRational.ZERO);
    }

    public BigQuaternion(BigRational r, BigRational i) {
        this(r, i, BigRational.ZERO);
    }

    public BigQuaternion(BigRational r) {
        this(r, BigRational.ZERO);
    }

    public BigQuaternion(BigComplex r) {
        this(r.re, r.im);
    }

    public BigQuaternion(long r) {
        this(new BigRational(r), BigRational.ZERO);
    }

    public BigQuaternion() {
        this(BigRational.ZERO);
    }

    public BigQuaternion(String s) throws NumberFormatException {
        this.debug = logger.isDebugEnabled();
        if (s == null || s.length() == 0) {
            this.re = BigRational.ZERO;
            this.im = BigRational.ZERO;
            this.jm = BigRational.ZERO;
            this.km = BigRational.ZERO;
            return;
        }
        s = s.trim();
        if ((s.indexOf("i") + s.indexOf("j")) + s.indexOf("k") == -3) {
            this.re = new BigRational(s);
            this.im = BigRational.ZERO;
            this.jm = BigRational.ZERO;
            this.km = BigRational.ZERO;
            return;
        }
        int i = s.indexOf("i");
        String sr = BuildConfig.FLAVOR;
        if (i > 0) {
            sr = s.substring(0, i);
        } else if (i < 0) {
            throw new NumberFormatException("BigQuaternion missing i");
        }
        String si = BuildConfig.FLAVOR;
        if (i < s.length()) {
            s = s.substring(i + 1, s.length());
        }
        int j = s.indexOf("j");
        if (j > 0) {
            si = s.substring(0, j);
        } else if (j < 0) {
            throw new NumberFormatException("BigQuaternion missing j");
        }
        String sj = BuildConfig.FLAVOR;
        if (j < s.length()) {
            s = s.substring(j + 1, s.length());
        }
        int k = s.indexOf("k");
        if (k > 0) {
            sj = s.substring(0, k);
        } else if (k < 0) {
            throw new NumberFormatException("BigQuaternion missing k");
        }
        String sk = BuildConfig.FLAVOR;
        if (k < s.length()) {
            s = s.substring(k + 1, s.length());
        }
        sk = s;
        this.re = new BigRational(sr.trim());
        this.im = new BigRational(si.trim());
        this.jm = new BigRational(sj.trim());
        this.km = new BigRational(sk.trim());
    }

    public BigQuaternion factory() {
        return this;
    }

    public List<BigQuaternion> generators() {
        List<BigQuaternion> g = new ArrayList(4);
        g.add(getONE());
        g.add(I);
        g.add(J);
        g.add(K);
        return g;
    }

    public boolean isFinite() {
        return false;
    }

    public BigQuaternion copy() {
        return new BigQuaternion(this.re, this.im, this.jm, this.km);
    }

    public BigQuaternion copy(BigQuaternion c) {
        return new BigQuaternion(c.re, c.im, c.jm, c.km);
    }

    public BigQuaternion getZERO() {
        return ZERO;
    }

    public BigQuaternion getONE() {
        return ONE;
    }

    public boolean isCommutative() {
        return false;
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

    public BigQuaternion fromInteger(BigInteger a) {
        return new BigQuaternion(new BigRational(a));
    }

    public BigQuaternion fromInteger(long a) {
        return new BigQuaternion(new BigRational(a));
    }

    public BigRational getRe() {
        return this.re;
    }

    public BigRational getIm() {
        return this.im;
    }

    public BigRational getJm() {
        return this.jm;
    }

    public BigRational getKm() {
        return this.km;
    }

    public String toString() {
        String s = BuildConfig.FLAVOR + this.re;
        int i = this.im.compareTo(BigRational.ZERO);
        int j = this.jm.compareTo(BigRational.ZERO);
        int k = this.km.compareTo(BigRational.ZERO);
        if (this.debug) {
            logger.debug("compareTo " + this.im + " ? 0 = " + i);
            logger.debug("compareTo " + this.jm + " ? 0 = " + j);
            logger.debug("compareTo " + this.km + " ? 0 = " + k);
        }
        if (i == 0 && j == 0 && k == 0) {
            return s;
        }
        return ((s + "i" + this.im) + "j" + this.jm) + "k" + this.km;
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        boolean i = this.im.isZERO();
        boolean j = this.jm.isZERO();
        boolean k = this.km.isZERO();
        if (!i || !j || !k) {
            if (!this.re.isZERO()) {
                if (!this.re.isONE()) {
                    s.append(this.re.toScript() + "*");
                }
                s.append("oneQ ");
            }
            if (!i) {
                if (s.length() > 0) {
                    s.append("+ ");
                }
                if (!this.im.isONE()) {
                    s.append(this.im.toScript() + "*");
                }
                s.append("IQ ");
            }
            if (!j) {
                if (s.length() > 0) {
                    s.append("+ ");
                }
                if (!this.jm.isONE()) {
                    s.append(this.jm.toScript() + "*");
                }
                s.append("JQ ");
            }
            if (!k) {
                if (s.length() > 0) {
                    s.append("+ ");
                }
                if (!this.km.isONE()) {
                    s.append(this.km.toScript() + "*");
                }
                s.append("KQ ");
            }
            return s.toString();
        } else if (this.re.isZERO()) {
            return "0 ";
        } else {
            if (!this.re.isONE()) {
                s.append(this.re.toScript() + "*");
            }
            s.append("oneQ ");
            return s.toString();
        }
    }

    public String toScriptFactory() {
        return "Quat()";
    }

    public static boolean isQZERO(BigQuaternion A) {
        if (A == null) {
            return false;
        }
        return A.isZERO();
    }

    public boolean isZERO() {
        return this.re.equals(BigRational.ZERO) && this.im.equals(BigRational.ZERO) && this.jm.equals(BigRational.ZERO) && this.km.equals(BigRational.ZERO);
    }

    public static boolean isQONE(BigQuaternion A) {
        if (A == null) {
            return false;
        }
        return A.isONE();
    }

    public boolean isONE() {
        return this.re.equals(BigRational.ONE) && this.im.equals(BigRational.ZERO) && this.jm.equals(BigRational.ZERO) && this.km.equals(BigRational.ZERO);
    }

    public boolean isIMAG() {
        return this.re.equals(BigRational.ZERO) && this.im.equals(BigRational.ONE) && this.jm.equals(BigRational.ZERO) && this.km.equals(BigRational.ZERO);
    }

    public boolean isUnit() {
        return !isZERO();
    }

    public boolean equals(Object b) {
        if (!(b instanceof BigQuaternion)) {
            return false;
        }
        BigQuaternion B = (BigQuaternion) b;
        if (this.re.equals(B.re) && this.im.equals(B.im) && this.jm.equals(B.jm) && this.km.equals(B.km)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.re.hashCode() * 37) + (this.im.hashCode() * 37)) + (this.jm.hashCode() * 37)) + (this.km.hashCode() * 37);
    }

    public int compareTo(BigQuaternion b) {
        int s = this.re.compareTo(b.re);
        if (s != 0) {
            return s;
        }
        s = this.im.compareTo(b.im);
        if (s != 0) {
            return s;
        }
        s = this.jm.compareTo(b.jm);
        if (s != 0) {
            return s;
        }
        return this.km.compareTo(b.km);
    }

    public int signum() {
        int s = this.re.signum();
        if (s != 0) {
            return s;
        }
        s = this.im.signum();
        if (s != 0) {
            return s;
        }
        s = this.jm.signum();
        if (s != 0) {
            return s;
        }
        return this.km.signum();
    }

    public BigQuaternion sum(BigQuaternion B) {
        return new BigQuaternion(this.re.sum(B.re), this.im.sum(B.im), this.jm.sum(B.jm), this.km.sum(B.km));
    }

    public static BigQuaternion QSUM(BigQuaternion A, BigQuaternion B) {
        if (A == null) {
            return null;
        }
        return A.sum(B);
    }

    public static BigQuaternion QDIF(BigQuaternion A, BigQuaternion B) {
        if (A == null) {
            return null;
        }
        return A.subtract(B);
    }

    public BigQuaternion subtract(BigQuaternion B) {
        return new BigQuaternion(this.re.subtract(B.re), this.im.subtract(B.im), this.jm.subtract(B.jm), this.km.subtract(B.km));
    }

    public static BigQuaternion QNEG(BigQuaternion A) {
        if (A == null) {
            return null;
        }
        return A.negate();
    }

    public BigQuaternion negate() {
        return new BigQuaternion(this.re.negate(), this.im.negate(), this.jm.negate(), this.km.negate());
    }

    public static BigQuaternion QCON(BigQuaternion A) {
        if (A == null) {
            return null;
        }
        return A.conjugate();
    }

    public BigQuaternion conjugate() {
        return new BigQuaternion(this.re, this.im.negate(), this.jm.negate(), this.km.negate());
    }

    public BigQuaternion norm() {
        return new BigQuaternion(this.re.multiply(this.re).sum(this.im.multiply(this.im)).sum(this.jm.multiply(this.jm)).sum(this.km.multiply(this.km)));
    }

    public BigQuaternion abs() {
        BigQuaternion n = norm();
        logger.error("abs() square root missing");
        return n;
    }

    public static BigRational QABS(BigQuaternion A) {
        if (A == null) {
            return null;
        }
        return A.abs().re;
    }

    public static BigQuaternion QPROD(BigQuaternion A, BigQuaternion B) {
        if (A == null) {
            return null;
        }
        return A.multiply(B);
    }

    public BigQuaternion multiply(BigQuaternion B) {
        return new BigQuaternion(this.re.multiply(B.re).subtract(this.im.multiply(B.im)).subtract(this.jm.multiply(B.jm)).subtract(this.km.multiply(B.km)), this.re.multiply(B.im).sum(this.im.multiply(B.re)).sum(this.jm.multiply(B.km)).subtract(this.km.multiply(B.jm)), this.re.multiply(B.jm).subtract(this.im.multiply(B.km)).sum(this.jm.multiply(B.re)).sum(this.km.multiply(B.im)), this.re.multiply(B.km).sum(this.im.multiply(B.jm)).subtract(this.jm.multiply(B.im)).sum(this.km.multiply(B.re)));
    }

    public static BigQuaternion QINV(BigQuaternion A) {
        if (A == null) {
            return null;
        }
        return A.inverse();
    }

    public BigQuaternion inverse() {
        BigRational a = norm().re.inverse();
        return new BigQuaternion(this.re.multiply(a), this.im.multiply(a.negate()), this.jm.multiply(a.negate()), this.km.multiply(a.negate()));
    }

    public BigQuaternion remainder(BigQuaternion S) {
        if (!S.isZERO()) {
            return ZERO;
        }
        throw new ArithmeticException("division by zero");
    }

    public static BigQuaternion QQ(BigQuaternion A, BigQuaternion B) {
        if (A == null) {
            return null;
        }
        return A.divide(B);
    }

    public BigQuaternion divide(BigQuaternion b) {
        return multiply(b.inverse());
    }

    public BigQuaternion divide(BigRational b) {
        BigRational bi = b.inverse();
        return new BigQuaternion(this.re.multiply(bi), this.im.multiply(bi), this.jm.multiply(bi), this.km.multiply(bi));
    }

    public BigQuaternion[] quotientRemainder(BigQuaternion S) {
        return new BigQuaternion[]{divide(S), ZERO};
    }

    public BigQuaternion random(int n) {
        return random(n, random);
    }

    public BigQuaternion random(int n, Random rnd) {
        return new BigQuaternion(BigRational.ONE.random(n, rnd), BigRational.ONE.random(n, rnd), BigRational.ONE.random(n, rnd), BigRational.ONE.random(n, rnd));
    }

    public static BigQuaternion QRAND(int n) {
        return ONE.random(n, random);
    }

    public BigQuaternion parse(String s) {
        return new BigQuaternion(s);
    }

    public BigQuaternion parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public BigQuaternion gcd(BigQuaternion S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return !isZERO() ? ONE : S;
    }

    public BigQuaternion[] egcd(BigQuaternion S) {
        BigQuaternion[] ret = new BigQuaternion[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else {
            BigQuaternion half = new BigQuaternion(new BigRational(1, 2));
            ret[0] = ONE;
            ret[1] = inverse().multiply(half);
            ret[2] = S.inverse().multiply(half);
        }
        return ret;
    }
}
