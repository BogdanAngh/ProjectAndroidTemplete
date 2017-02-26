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

public final class BigOctonion implements StarRingElem<BigOctonion>, GcdRingElem<BigOctonion>, RingFactory<BigOctonion> {
    public static final BigOctonion I;
    public static final BigOctonion ONE;
    public static final BigOctonion ZERO;
    private static final Logger logger;
    private static final Random random;
    private final boolean debug;
    public final BigQuaternion oi;
    public final BigQuaternion or;

    static {
        random = new Random();
        logger = Logger.getLogger(BigOctonion.class);
        ZERO = new BigOctonion();
        ONE = new BigOctonion(BigQuaternion.ONE);
        I = new BigOctonion(BigQuaternion.ZERO, BigQuaternion.ONE);
    }

    public BigOctonion(BigQuaternion r, BigQuaternion i) {
        this.debug = logger.isDebugEnabled();
        this.or = r;
        this.oi = i;
    }

    public BigOctonion(BigQuaternion r) {
        this(r, BigQuaternion.ZERO);
    }

    public BigOctonion(BigComplex r) {
        this(new BigQuaternion(r));
    }

    public BigOctonion(BigRational r) {
        this(new BigQuaternion(r));
    }

    public BigOctonion(long r) {
        this(new BigQuaternion(r));
    }

    public BigOctonion() {
        this(BigQuaternion.ZERO);
    }

    public BigOctonion(String s) throws NumberFormatException {
        this.debug = logger.isDebugEnabled();
        if (s == null || s.length() == 0) {
            this.or = ZERO.or;
            this.oi = ZERO.oi;
            return;
        }
        s = s.trim();
        int o = s.indexOf("o");
        if (o == -1) {
            this.or = new BigQuaternion(s);
            this.oi = ZERO.oi;
            return;
        }
        String sr = s.substring(0, o - 1);
        String so = s.substring(o + 1, s.length());
        this.or = new BigQuaternion(sr.trim());
        this.oi = new BigQuaternion(so.trim());
    }

    public BigOctonion factory() {
        return this;
    }

    public List<BigOctonion> generators() {
        List<BigQuaternion> qg = this.or.generators();
        List<BigOctonion> g = new ArrayList(qg.size() * 2);
        for (BigQuaternion q : qg) {
            g.add(new BigOctonion(q));
        }
        for (BigQuaternion q2 : qg) {
            g.add(new BigOctonion(BigQuaternion.ZERO, q2));
        }
        return g;
    }

    public boolean isFinite() {
        return false;
    }

    public BigOctonion copy() {
        return new BigOctonion(this.or, this.oi);
    }

    public BigOctonion copy(BigOctonion c) {
        if (c == null) {
            return new BigOctonion();
        }
        return new BigOctonion(c.or, c.oi);
    }

    public BigOctonion getZERO() {
        return ZERO;
    }

    public BigOctonion getONE() {
        return ONE;
    }

    public boolean isCommutative() {
        return false;
    }

    public boolean isAssociative() {
        return false;
    }

    public boolean isField() {
        return true;
    }

    public BigInteger characteristic() {
        return BigInteger.ZERO;
    }

    public BigOctonion fromInteger(BigInteger a) {
        return new BigOctonion(ONE.or.fromInteger(a));
    }

    public BigOctonion fromInteger(long a) {
        return new BigOctonion(ONE.or.fromInteger(a));
    }

    public BigQuaternion getR() {
        return this.or;
    }

    public BigQuaternion getI() {
        return this.oi;
    }

    public String toString() {
        String s = BuildConfig.FLAVOR + this.or;
        int i = this.oi.compareTo(BigQuaternion.ZERO);
        if (this.debug) {
            logger.debug("compareTo " + i + " ? 0 = " + this.oi);
        }
        if (i == 0) {
            return s;
        }
        return s + "o" + this.oi;
    }

    public String toScript() {
        boolean i = this.oi.isZERO();
        if (i && this.or.isZERO()) {
            return "0 ";
        }
        StringBuffer s = new StringBuffer();
        if (!this.or.isZERO()) {
            s.append(this.or.toScript().replaceAll("Q", "OR"));
            s.append(" ");
        }
        if (!i) {
            if (s.length() > 0) {
                s.append("+ ");
            }
            s.append(this.oi.toScript().replaceAll("Q", "OI"));
        }
        return s.toString();
    }

    public String toScriptFactory() {
        return "Oct()";
    }

    public static boolean isOZERO(BigOctonion A) {
        if (A == null) {
            return false;
        }
        return A.isZERO();
    }

    public boolean isZERO() {
        return this.or.equals(BigQuaternion.ZERO) && this.oi.equals(BigQuaternion.ZERO);
    }

    public static boolean isOONE(BigOctonion A) {
        if (A == null) {
            return false;
        }
        return A.isONE();
    }

    public boolean isONE() {
        return this.or.equals(BigQuaternion.ONE) && this.oi.equals(BigQuaternion.ZERO);
    }

    public boolean isIMAG() {
        return this.or.equals(BigQuaternion.ZERO) && this.oi.equals(BigQuaternion.ONE);
    }

    public boolean isUnit() {
        return !isZERO();
    }

    public boolean equals(Object b) {
        if (!(b instanceof BigOctonion)) {
            return false;
        }
        BigOctonion B = (BigOctonion) b;
        if (this.or.equals(B.or) && this.oi.equals(B.oi)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.or.hashCode() * 41) + (this.oi.hashCode() * 41);
    }

    public int compareTo(BigOctonion b) {
        int s = this.or.compareTo(b.or);
        return s != 0 ? s : this.oi.compareTo(b.oi);
    }

    public int signum() {
        int s = this.or.signum();
        return s != 0 ? s : this.oi.signum();
    }

    public BigOctonion sum(BigOctonion B) {
        return new BigOctonion(this.or.sum(B.or), this.oi.sum(B.oi));
    }

    public static BigOctonion OSUM(BigOctonion A, BigOctonion B) {
        if (A == null) {
            return null;
        }
        return A.sum(B);
    }

    public static BigOctonion ODIF(BigOctonion A, BigOctonion B) {
        if (A == null) {
            return null;
        }
        return A.subtract(B);
    }

    public BigOctonion subtract(BigOctonion B) {
        return new BigOctonion(this.or.subtract(B.or), this.oi.subtract(B.oi));
    }

    public static BigOctonion ONEG(BigOctonion A) {
        if (A == null) {
            return null;
        }
        return A.negate();
    }

    public BigOctonion negate() {
        return new BigOctonion(this.or.negate(), this.oi.negate());
    }

    public static BigOctonion OCON(BigOctonion A) {
        if (A == null) {
            return null;
        }
        return A.conjugate();
    }

    public BigOctonion conjugate() {
        return new BigOctonion(this.or.conjugate(), this.oi.negate());
    }

    public BigOctonion norm() {
        return new BigOctonion(this.or.norm().sum(this.oi.norm()));
    }

    public BigOctonion abs() {
        BigOctonion n = norm();
        logger.error("abs() square root missing");
        return n;
    }

    public static BigRational OABS(BigOctonion A) {
        if (A == null) {
            return null;
        }
        return A.abs().or.re;
    }

    public static BigOctonion OPROD(BigOctonion A, BigOctonion B) {
        if (A == null) {
            return null;
        }
        return A.multiply(B);
    }

    public BigOctonion multiply(BigOctonion B) {
        return new BigOctonion(this.or.multiply(B.or).subtract(B.oi.multiply(this.oi.conjugate())), this.or.conjugate().multiply(B.oi).sum(B.or.multiply(this.oi)));
    }

    public static BigOctonion OINV(BigOctonion A) {
        if (A == null) {
            return null;
        }
        return A.inverse();
    }

    public BigOctonion inverse() {
        return conjugate().divide(norm().or.re);
    }

    public BigOctonion remainder(BigOctonion S) {
        if (!S.isZERO()) {
            return ZERO;
        }
        throw new ArithmeticException("division by zero");
    }

    public static BigOctonion OQ(BigOctonion A, BigOctonion B) {
        if (A == null) {
            return null;
        }
        return A.divide(B);
    }

    public BigOctonion divide(BigOctonion b) {
        return multiply(b.inverse());
    }

    public BigOctonion divide(BigRational b) {
        return new BigOctonion(this.or.divide(b), this.oi.divide(b));
    }

    public BigOctonion[] quotientRemainder(BigOctonion S) {
        return new BigOctonion[]{divide(S), ZERO};
    }

    public BigOctonion random(int n) {
        return random(n, random);
    }

    public BigOctonion random(int n, Random rnd) {
        return new BigOctonion(BigQuaternion.ONE.random(n, rnd), BigQuaternion.ONE.random(n, rnd));
    }

    public static BigOctonion ORAND(int n) {
        return ONE.random(n, random);
    }

    public BigOctonion parse(String s) {
        return new BigOctonion(s);
    }

    public BigOctonion parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public BigOctonion gcd(BigOctonion S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return !isZERO() ? ONE : S;
    }

    public BigOctonion[] egcd(BigOctonion S) {
        BigOctonion[] ret = new BigOctonion[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else {
            BigOctonion half = new BigOctonion(new BigRational(1, 2));
            ret[0] = ONE;
            ret[1] = inverse().multiply(half);
            ret[2] = S.inverse().multiply(half);
        }
        return ret;
    }
}
