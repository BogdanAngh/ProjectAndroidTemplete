package edu.jas.ufd;

import edu.jas.kern.PrettyPrint;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPair;
import edu.jas.structure.RingElem;
import org.apache.log4j.Logger;

public class Quotient<C extends GcdRingElem<C>> implements GcdRingElem<Quotient<C>>, QuotPair<GenPolynomial<C>> {
    private static final Logger logger;
    private final boolean debug;
    public final GenPolynomial<C> den;
    public final GenPolynomial<C> num;
    public final QuotientRing<C> ring;

    static {
        logger = Logger.getLogger(Quotient.class);
    }

    public Quotient(QuotientRing<C> r) {
        this(r, r.ring.getZERO());
    }

    public Quotient(QuotientRing<C> r, GenPolynomial<C> n) {
        this(r, n, r.ring.getONE(), true);
    }

    public Quotient(QuotientRing<C> r, GenPolynomial<C> n, GenPolynomial<C> d) {
        this(r, n, d, false);
    }

    protected Quotient(QuotientRing<C> r, GenPolynomial<C> n, GenPolynomial<C> d, boolean isred) {
        this.debug = logger.isDebugEnabled();
        if (d == null || d.isZERO()) {
            throw new IllegalArgumentException("denominator may not be zero");
        }
        this.ring = r;
        if (d.signum() < 0) {
            n = n.negate();
            d = d.negate();
        }
        if (!isred) {
            GenPolynomial<C> gcd = this.ring.gcd(n, d);
            if (this.debug) {
                logger.info("gcd = " + gcd);
            }
            if (!gcd.isONE()) {
                n = this.ring.divide(n, gcd);
                d = this.ring.divide(d, gcd);
            }
        }
        GcdRingElem lc = (GcdRingElem) d.leadingBaseCoefficient();
        if (!lc.isONE() && lc.isUnit()) {
            RingElem lc2 = (GcdRingElem) lc.inverse();
            n = n.multiply(lc2);
            d = d.multiply(lc2);
        }
        this.num = n;
        this.den = d;
    }

    public QuotientRing<C> factory() {
        return this.ring;
    }

    public GenPolynomial<C> numerator() {
        return this.num;
    }

    public GenPolynomial<C> denominator() {
        return this.den;
    }

    public Quotient<C> copy() {
        return new Quotient(this.ring, this.num, this.den, true);
    }

    public boolean isZERO() {
        return this.num.isZERO();
    }

    public boolean isONE() {
        return this.num.equals(this.den);
    }

    public boolean isUnit() {
        if (this.num.isZERO()) {
            return false;
        }
        return true;
    }

    public boolean isConstant() {
        return this.num.isConstant() && this.den.isConstant();
    }

    public String toString() {
        if (!PrettyPrint.isTrue()) {
            return "Quotient[ " + this.num.toString() + " | " + this.den.toString() + " ]";
        }
        String s = "{ " + this.num.toString(this.ring.ring.getVars());
        if (!this.den.isONE()) {
            s = s + " | " + this.den.toString(this.ring.ring.getVars());
        }
        return s + " }";
    }

    public String toScript() {
        if (this.den.isONE()) {
            return this.num.toScript();
        }
        if (this.den.length() != 1 || this.den.totalDegree() <= 1) {
            return this.num.toScript() + " / " + this.den.toScript();
        }
        return this.num.toScript() + " / (" + this.den.toScript() + " )";
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(Quotient<C> b) {
        if (b == null || b.isZERO()) {
            return signum();
        }
        if (isZERO()) {
            return -b.signum();
        }
        int t = (this.num.signum() - b.num.signum()) / 2;
        if (t != 0) {
            return t;
        }
        if (this.den.compareTo(b.den) == 0) {
            return this.num.compareTo(b.num);
        }
        return this.num.multiply(b.den).compareTo(this.den.multiply(b.num));
    }

    public boolean equals(Object b) {
        if (b != null && (b instanceof Quotient) && compareTo((Quotient) b) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.ring.hashCode() * 37) + this.num.hashCode()) * 37) + this.den.hashCode();
    }

    public Quotient<C> abs() {
        return new Quotient(this.ring, this.num.abs(), this.den, true);
    }

    public Quotient<C> sum(Quotient<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (this.den.isONE() && S.den.isONE()) {
            return new Quotient(this.ring, this.num.sum(S.num));
        } else if (this.den.isONE()) {
            return new Quotient(this.ring, this.num.multiply(S.den).sum(S.num), S.den, false);
        } else if (S.den.isONE()) {
            return new Quotient(this.ring, S.num.multiply(this.den).sum(this.num), this.den, false);
        } else if (this.den.compareTo(S.den) == 0) {
            return new Quotient(this.ring, this.num.sum(S.num), this.den, false);
        } else {
            GenPolynomial<C> d;
            GenPolynomial sd;
            GenPolynomial<C> g = this.ring.gcd(this.den, S.den);
            if (g.isONE()) {
                d = this.den;
                sd = S.den;
            } else {
                d = this.ring.divide(this.den, g);
                sd = this.ring.divide(S.den, g);
            }
            GenPolynomial<C> n = this.num.multiply(sd).sum(d.multiply(S.num));
            if (n.isZERO()) {
                return this.ring.getZERO();
            }
            GenPolynomial<C> dd = this.den;
            if (!g.isONE()) {
                GenPolynomial<C> f = this.ring.gcd(n, g);
                if (!f.isONE()) {
                    n = this.ring.divide(n, f);
                    dd = this.ring.divide(this.den, f);
                }
            }
            return new Quotient(this.ring, n, dd.multiply(sd), true);
        }
    }

    public Quotient<C> negate() {
        return new Quotient(this.ring, this.num.negate(), this.den, true);
    }

    public int signum() {
        return this.num.signum();
    }

    public Quotient<C> subtract(Quotient<C> S) {
        return sum(S.negate());
    }

    public Quotient<C> divide(Quotient<C> S) {
        return multiply(S.inverse());
    }

    public Quotient<C> inverse() {
        if (!this.num.isZERO()) {
            return new Quotient(this.ring, this.den, this.num, true);
        }
        throw new ArithmeticException("element not invertible " + this);
    }

    public Quotient<C> remainder(Quotient<C> S) {
        if (!S.isZERO()) {
            return this.ring.getZERO();
        }
        throw new ArithmeticException("element not invertible " + S);
    }

    public Quotient<C>[] quotientRemainder(Quotient<C> S) {
        return new Quotient[]{divide((Quotient) S), remainder((Quotient) S)};
    }

    public Quotient<C> multiply(Quotient<C> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (this.num.isZERO() || S.isONE()) {
            return this;
        }
        if (isONE()) {
            return S;
        }
        if (this.den.isONE() && S.den.isONE()) {
            return new Quotient(this.ring, this.num.multiply(S.num), this.den, true);
        } else if (this.den.isONE()) {
            g = this.ring.gcd(this.num, S.den);
            n = this.ring.divide(this.num, g);
            d = this.ring.divide(S.den, g);
            return new Quotient(this.ring, n.multiply(S.num), d, true);
        } else if (S.den.isONE()) {
            g = this.ring.gcd(S.num, this.den);
            n = this.ring.divide(S.num, g);
            d = this.ring.divide(this.den, g);
            return new Quotient(this.ring, n.multiply(this.num), d, true);
        } else if (this.den.compareTo(S.den) == 0) {
            d = this.den.multiply(this.den);
            return new Quotient(this.ring, this.num.multiply(S.num), d, true);
        } else {
            g = this.ring.gcd(this.num, S.den);
            n = this.ring.divide(this.num, g);
            GenPolynomial sd = this.ring.divide(S.den, g);
            GenPolynomial<C> f = this.ring.gcd(this.den, S.num);
            d = this.ring.divide(this.den, f);
            return new Quotient(this.ring, n.multiply(this.ring.divide(S.num, f)), d.multiply(sd), true);
        }
    }

    public Quotient<C> multiply(GenPolynomial<C> b) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (this.num.isZERO() || b.isONE()) {
            return this;
        }
        GenPolynomial<C> gcd = this.ring.gcd(b, this.den);
        GenPolynomial<C> d = this.den;
        if (!gcd.isONE()) {
            GenPolynomial b2 = this.ring.divide(b, gcd);
            d = this.ring.divide(d, gcd);
        }
        if (isONE()) {
            return new Quotient(this.ring, b2, d, true);
        }
        return new Quotient(this.ring, this.num.multiply(b2), d, true);
    }

    public Quotient<C> multiply(C b) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (this.num.isZERO() || b.isONE()) {
            return this;
        }
        return new Quotient(this.ring, this.num.multiply((RingElem) b), this.den, true);
    }

    public Quotient<C> monic() {
        if (this.num.isZERO()) {
            return this;
        }
        GcdRingElem lbc = (GcdRingElem) this.num.leadingBaseCoefficient();
        if (!lbc.isUnit()) {
            return this;
        }
        return new Quotient(this.ring, this.num.multiply((GcdRingElem) lbc.inverse()), this.den, true);
    }

    public Quotient<C> gcd(Quotient<C> b) {
        if (b == null || b.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return b;
        }
        return !equals(b) ? this.ring.getONE() : this;
    }

    public Quotient<C>[] egcd(Quotient<C> b) {
        Quotient[] ret = (Quotient[]) new Quotient[3];
        ret[0] = null;
        ret[1] = null;
        ret[2] = null;
        if (b == null || b.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = b;
        } else {
            GenPolynomial two = this.ring.ring.fromInteger(2);
            ret[0] = this.ring.getONE();
            ret[1] = multiply(two).inverse();
            ret[2] = b.multiply(two).inverse();
        }
        return ret;
    }
}
