package edu.jas.poly;

import edu.jas.arith.BigComplex;
import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.StarRingElem;
import io.github.kexanie.library.BuildConfig;
import org.apache.log4j.Logger;

public class Complex<C extends RingElem<C>> implements StarRingElem<Complex<C>>, GcdRingElem<Complex<C>> {
    private static final boolean debug;
    private static final Logger logger;
    protected final C im;
    protected final C re;
    public final ComplexRing<C> ring;

    static {
        logger = Logger.getLogger(Complex.class);
        debug = logger.isDebugEnabled();
    }

    public Complex(ComplexRing<C> ring, C r, C i) {
        this.ring = ring;
        this.re = r;
        this.im = i;
    }

    public Complex(ComplexRing<C> ring, C r) {
        this(ring, r, (RingElem) ring.ring.getZERO());
    }

    public Complex(ComplexRing<C> ring, long r) {
        this((ComplexRing) ring, (RingElem) ring.ring.fromInteger(r));
    }

    public Complex(ComplexRing<C> ring) {
        this((ComplexRing) ring, (RingElem) ring.ring.getZERO());
    }

    public Complex(ComplexRing<C> ring, String s) throws NumberFormatException {
        this.ring = ring;
        if (s == null || s.length() == 0) {
            this.re = (RingElem) ring.ring.getZERO();
            this.im = (RingElem) ring.ring.getZERO();
            return;
        }
        s = s.trim();
        int i = s.indexOf("i");
        if (i < 0) {
            this.re = (RingElem) ring.ring.parse(s);
            this.im = (RingElem) ring.ring.getZERO();
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
        this.re = (RingElem) ring.ring.parse(sr.trim());
        this.im = (RingElem) ring.ring.parse(si.trim());
    }

    public ComplexRing<C> factory() {
        return this.ring;
    }

    public C getRe() {
        return this.re;
    }

    public C getIm() {
        return this.im;
    }

    public Complex<C> copy() {
        return new Complex(this.ring, this.re, this.im);
    }

    public String toString() {
        String s = this.re.toString();
        if (this.im.isZERO()) {
            return s;
        }
        return s + "i" + this.im;
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        if (this.im.isZERO()) {
            s.append(this.re.toScript());
        } else {
            C mi = this.im;
            if (!this.re.isZERO()) {
                s.append(this.re.toScript());
                if (mi.signum() > 0) {
                    s.append(" + ");
                } else {
                    s.append(" - ");
                    RingElem mi2 = (RingElem) mi.negate();
                }
            }
            if (mi.isONE()) {
                s.append("I");
            } else {
                s.append(mi.toScript()).append(" * I");
            }
            s.append(BuildConfig.FLAVOR);
        }
        return s.toString();
    }

    public String toScriptFactory() {
        return this.ring.toScript();
    }

    public boolean isZERO() {
        return this.re.isZERO() && this.im.isZERO();
    }

    public boolean isONE() {
        return this.re.isONE() && this.im.isZERO();
    }

    public boolean isIMAG() {
        return this.re.isZERO() && this.im.isONE();
    }

    public boolean isUnit() {
        if (isZERO()) {
            return false;
        }
        if (this.ring.isField()) {
            return true;
        }
        return norm().re.isUnit();
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof Complex)) {
            return false;
        }
        Complex<C> bc = (Complex) b;
        if (this.ring.equals(bc.ring) && this.re.equals(bc.re) && this.im.equals(bc.im)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.re.hashCode() * 37) + this.im.hashCode();
    }

    public int compareTo(Complex<C> b) {
        int s = this.re.compareTo(b.re);
        return s != 0 ? s : this.im.compareTo(b.im);
    }

    public int signum() {
        int s = this.re.signum();
        return s != 0 ? s : this.im.signum();
    }

    public Complex<C> sum(Complex<C> B) {
        return new Complex(this.ring, (RingElem) this.re.sum(B.re), (RingElem) this.im.sum(B.im));
    }

    public Complex<C> subtract(Complex<C> B) {
        return new Complex(this.ring, (RingElem) this.re.subtract(B.re), (RingElem) this.im.subtract(B.im));
    }

    public Complex<C> negate() {
        return new Complex(this.ring, (RingElem) this.re.negate(), (RingElem) this.im.negate());
    }

    public Complex<C> conjugate() {
        return new Complex(this.ring, this.re, (RingElem) this.im.negate());
    }

    public Complex<C> norm() {
        return new Complex(this.ring, (RingElem) ((RingElem) this.re.multiply(this.re)).sum((AbelianGroupElem) this.im.multiply(this.im)));
    }

    public Complex<C> abs() {
        Complex<C> n = norm();
        logger.error("abs() square root missing");
        return n;
    }

    public Complex<C> multiply(Complex<C> B) {
        return new Complex(this.ring, (RingElem) ((RingElem) this.re.multiply(B.re)).subtract((AbelianGroupElem) this.im.multiply(B.im)), (RingElem) ((RingElem) this.re.multiply(B.im)).sum((AbelianGroupElem) this.im.multiply(B.re)));
    }

    public Complex<C> inverse() {
        RingElem a = (RingElem) norm().re.inverse();
        return new Complex(this.ring, (RingElem) this.re.multiply(a), (RingElem) this.im.multiply((MonoidElem) a.negate()));
    }

    public Complex<C> remainder(Complex<C> S) {
        if (this.ring.isField()) {
            return this.ring.getZERO();
        }
        return quotientRemainder(S)[1];
    }

    public Complex<C> divide(Complex<C> B) {
        if (this.ring.isField()) {
            return multiply(B.inverse());
        }
        return quotientRemainder(B)[0];
    }

    public Complex<C>[] quotientRemainder(Complex<C> S) {
        Complex<C>[] ret = new Complex[2];
        C n = S.norm().re;
        Complex<C> Sp = multiply(S.conjugate());
        C qr = (RingElem) Sp.re.divide(n);
        C rr = (RingElem) Sp.re.remainder(n);
        C qi = (RingElem) Sp.im.divide(n);
        C ri = (RingElem) Sp.im.remainder(n);
        C rr1 = rr;
        C ri1 = ri;
        if (rr.signum() < 0) {
            rr = (RingElem) rr.negate();
        }
        if (ri.signum() < 0) {
            ri = (RingElem) ri.negate();
        }
        RingElem one = (RingElem) n.factory().fromInteger(1);
        if (((RingElem) rr.sum(rr)).compareTo(n) > 0) {
            if (rr1.signum() < 0) {
                qr = (RingElem) qr.subtract(one);
            } else {
                RingElem qr2 = (RingElem) qr.sum(one);
            }
        }
        if (((RingElem) ri.sum(ri)).compareTo(n) > 0) {
            if (ri1.signum() < 0) {
                qi = (RingElem) qi.subtract(one);
            } else {
                RingElem qi2 = (RingElem) qi.sum(one);
            }
        }
        Sp = new Complex(this.ring, qr, qi);
        Complex<C> Rp = subtract(Sp.multiply((Complex) S));
        if (debug) {
            if (n.compareTo(Rp.norm().re) < 0) {
                System.out.println("n = " + n);
                System.out.println("qr   = " + qr);
                System.out.println("qi   = " + qi);
                System.out.println("rr   = " + rr);
                System.out.println("ri   = " + ri);
                System.out.println("rr1  = " + rr1);
                System.out.println("ri1  = " + ri1);
                System.out.println("this = " + this);
                System.out.println("S    = " + S);
                System.out.println("Sp   = " + Sp);
                BigInteger tr = (BigInteger) this.re;
                BigInteger ti = (BigInteger) this.im;
                BigInteger sr = (BigInteger) S.re;
                BigInteger si = (BigInteger) S.im;
                BigComplex qc = new BigComplex(new BigRational(tr), new BigRational(ti)).divide(new BigComplex(new BigRational(sr), new BigRational(si)));
                System.out.println("qc   = " + qc);
                BigDecimal qrd = new BigDecimal(qc.getRe());
                BigDecimal qid = new BigDecimal(qc.getIm());
                System.out.println("qrd  = " + qrd);
                System.out.println("qid  = " + qid);
                throw new ArithmeticException("QR norm not decreasing " + Rp + ", " + Rp.norm());
            }
        }
        ret[0] = Sp;
        ret[1] = Rp;
        return ret;
    }

    @Deprecated
    public Complex<C>[] divideAndRemainder(Complex<C> S) {
        return quotientRemainder(S);
    }

    public Complex<C> gcd(Complex<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (this.ring.isField()) {
            return this.ring.getONE();
        }
        Complex<C> a;
        Complex<C> b = S;
        if (this.re.signum() < 0) {
            a = negate();
        }
        if (b.re.signum() < 0) {
            b = b.negate();
        }
        while (!b.isZERO()) {
            if (debug) {
                logger.info("norm(b), a, b = " + b.norm() + ", " + a + ", " + b);
            }
            Complex<C>[] qr = a.quotientRemainder(b);
            if (qr[0].isZERO()) {
                System.out.println("a = " + a);
            }
            a = b;
            b = qr[1];
        }
        if (a.re.signum() < 0) {
            a = a.negate();
        }
        return a;
    }

    public Complex<C>[] egcd(Complex<C> S) {
        Complex<C>[] ret = new Complex[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else if (this.ring.isField()) {
            Complex half = new Complex(this.ring, (RingElem) ((RingElem) this.ring.ring.fromInteger(1)).divide((MonoidElem) this.ring.ring.fromInteger(2)));
            ret[0] = this.ring.getONE();
            ret[1] = inverse().multiply(half);
            ret[2] = S.inverse().multiply(half);
        } else {
            Complex<C> q = this;
            Complex<C> r = S;
            Complex<C> c1 = this.ring.getONE();
            Complex<C> d1 = this.ring.getZERO();
            Complex<C> c2 = this.ring.getZERO();
            Complex<C> d2 = this.ring.getONE();
            while (!r.isZERO()) {
                if (debug) {
                    logger.info("norm(r), q, r = " + r.norm() + ", " + q + ", " + r);
                }
                Complex<C>[] qr = q.quotientRemainder(r);
                q = qr[0];
                Complex<C> x1 = c1.subtract(q.multiply((Complex) d1));
                Complex<C> x2 = c2.subtract(q.multiply((Complex) d2));
                c1 = d1;
                c2 = d2;
                d1 = x1;
                d2 = x2;
                q = r;
                r = qr[1];
            }
            if (q.re.signum() < 0) {
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
}
