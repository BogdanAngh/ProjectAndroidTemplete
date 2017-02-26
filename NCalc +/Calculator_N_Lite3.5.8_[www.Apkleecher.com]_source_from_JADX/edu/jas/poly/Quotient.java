package edu.jas.poly;

import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPair;
import edu.jas.structure.RingElem;
import org.apache.log4j.Logger;

public class Quotient<C extends RingElem<C>> implements RingElem<Quotient<C>>, QuotPair<C> {
    private static final Logger logger;
    private final boolean debug;
    public final C den;
    public final C num;
    public final QuotientRing<C> ring;

    static {
        logger = Logger.getLogger(Quotient.class);
    }

    public Quotient(QuotientRing<C> r) {
        this(r, (RingElem) r.ring.getZERO());
    }

    public Quotient(QuotientRing<C> r, C n) {
        this(r, n, (RingElem) r.ring.getONE(), true);
    }

    public Quotient(QuotientRing<C> r, C n, C d) {
        this(r, n, d, false);
    }

    protected Quotient(QuotientRing<C> r, C n, C d, boolean isred) {
        this.debug = logger.isDebugEnabled();
        if (d == null || d.isZERO()) {
            throw new IllegalArgumentException("denominator may not be zero");
        }
        this.ring = r;
        if (d.signum() < 0) {
            n = (RingElem) n.negate();
            d = (RingElem) d.negate();
        }
        if (isred) {
            this.num = n;
            this.den = d;
        } else if ((n instanceof GcdRingElem) && (d instanceof GcdRingElem)) {
            C gcd = ((GcdRingElem) n).gcd((GcdRingElem) d);
            if (this.debug) {
                logger.info("gcd = " + gcd);
            }
            if (gcd.isONE()) {
                this.num = n;
                this.den = d;
                return;
            }
            this.num = (RingElem) n.divide(gcd);
            this.den = (RingElem) d.divide(gcd);
        } else {
            logger.warn("gcd = ????");
            this.num = n;
            this.den = d;
        }
    }

    public QuotientRing<C> factory() {
        return this.ring;
    }

    public C numerator() {
        return this.num;
    }

    public C denominator() {
        return this.den;
    }

    public boolean isConstant() {
        throw new UnsupportedOperationException("isConstant not implemented");
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

    public String toString() {
        return "Quotient[ " + this.num.toString() + " / " + this.den.toString() + " ]";
    }

    public String toScript() {
        return "Quotient( " + this.num.toScript() + " , " + this.den.toScript() + " )";
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(Quotient<C> b) {
        if (b == null || b.isZERO()) {
            return signum();
        }
        return ((RingElem) ((RingElem) this.num.multiply(b.den)).subtract((RingElem) this.den.multiply(b.num))).signum();
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
        return new Quotient(this.ring, (RingElem) this.num.abs(), this.den, true);
    }

    public Quotient<C> sum(Quotient<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return new Quotient(this.ring, (RingElem) ((RingElem) this.num.multiply(S.den)).sum((AbelianGroupElem) this.den.multiply(S.num)), (RingElem) this.den.multiply(S.den), false);
    }

    public Quotient<C> negate() {
        return new Quotient(this.ring, (RingElem) this.num.negate(), this.den, true);
    }

    public int signum() {
        return this.num.signum();
    }

    public Quotient<C> subtract(Quotient<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return new Quotient(this.ring, (RingElem) ((RingElem) this.num.multiply(S.den)).subtract((AbelianGroupElem) this.den.multiply(S.num)), (RingElem) this.den.multiply(S.den), false);
    }

    public Quotient<C> divide(Quotient<C> S) {
        return multiply(S.inverse());
    }

    public Quotient<C> inverse() {
        return new Quotient(this.ring, this.den, this.num, true);
    }

    public Quotient<C> remainder(Quotient<C> quotient) {
        if (!this.num.isZERO()) {
            return this.ring.getZERO();
        }
        throw new ArithmeticException("element not invertible " + this);
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
        return new Quotient(this.ring, (RingElem) this.num.multiply(S.num), (RingElem) this.den.multiply(S.den), false);
    }

    public Quotient<C> monic() {
        logger.info("monic not implemented");
        return this;
    }

    public Quotient<C> gcd(Quotient<C> b) {
        if (b == null || b.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return b;
        }
        if ((this.num instanceof GcdRingElem) && (this.den instanceof GcdRingElem) && (b.num instanceof GcdRingElem) && (b.den instanceof GcdRingElem)) {
            return this.ring.getONE();
        }
        throw new UnsupportedOperationException("gcd not implemented " + this.num.getClass().getName());
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
        } else if ((this.num instanceof GcdRingElem) && (this.den instanceof GcdRingElem) && (b.num instanceof GcdRingElem) && (b.den instanceof GcdRingElem)) {
            Quotient two = this.ring.fromInteger(2);
            ret[0] = this.ring.getONE();
            ret[1] = multiply(two).inverse();
            ret[2] = b.multiply(two).inverse();
        } else {
            throw new UnsupportedOperationException("egcd not implemented " + this.num.getClass().getName());
        }
        return ret;
    }
}
