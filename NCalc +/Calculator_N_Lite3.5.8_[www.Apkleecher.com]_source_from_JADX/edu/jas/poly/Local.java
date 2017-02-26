package edu.jas.poly;

import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPair;
import edu.jas.structure.RingElem;
import org.apache.log4j.Logger;

public class Local<C extends RingElem<C>> implements RingElem<Local<C>>, QuotPair<C> {
    private static final Logger logger;
    private final boolean debug;
    protected final C den;
    protected int isunit;
    protected final C num;
    protected final LocalRing<C> ring;

    static {
        logger = Logger.getLogger(Local.class);
    }

    public Local(LocalRing<C> r) {
        this(r, (RingElem) r.ring.getZERO());
    }

    public Local(LocalRing<C> r, C n) {
        this(r, n, (RingElem) r.ring.getONE(), true);
    }

    public Local(LocalRing<C> r, C n, C d) {
        this(r, n, d, false);
    }

    protected Local(LocalRing<C> r, C n, C d, boolean isred) {
        this.debug = logger.isDebugEnabled();
        this.isunit = -1;
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
            return;
        }
        RingElem p = (RingElem) d.remainder(this.ring.ideal);
        if (p == null || p.isZERO()) {
            throw new IllegalArgumentException("denominator may not be in ideal");
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

    public LocalRing<C> factory() {
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

    public Local<C> copy() {
        return new Local(this.ring, this.num, this.den, true);
    }

    public boolean isZERO() {
        return this.num.isZERO();
    }

    public boolean isONE() {
        return this.num.equals(this.den);
    }

    public boolean isUnit() {
        if (this.isunit > 0) {
            return true;
        }
        if (this.isunit == 0) {
            return false;
        }
        if (this.num.isZERO()) {
            this.isunit = 0;
            return false;
        }
        boolean u;
        RingElem p = (RingElem) this.num.remainder(this.ring.ideal);
        if (p == null || p.isZERO()) {
            u = false;
        } else {
            u = true;
        }
        if (u) {
            this.isunit = 1;
            return u;
        }
        this.isunit = 0;
        return u;
    }

    public String toString() {
        return "Local[ " + this.num.toString() + " / " + this.den.toString() + " ]";
    }

    public String toScript() {
        return "Local( " + this.num.toScript() + " , " + this.den.toScript() + " )";
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(Local<C> b) {
        if (b == null || b.isZERO()) {
            return signum();
        }
        return ((RingElem) ((RingElem) this.num.multiply(b.den)).subtract((RingElem) this.den.multiply(b.num))).signum();
    }

    public boolean equals(Object b) {
        if (b != null && (b instanceof Local) && compareTo((Local) b) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.ring.hashCode() * 37) + this.num.hashCode()) * 37) + this.den.hashCode();
    }

    public Local<C> abs() {
        return new Local(this.ring, (RingElem) this.num.abs(), this.den, true);
    }

    public Local<C> sum(Local<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return new Local(this.ring, (RingElem) ((RingElem) this.num.multiply(S.den)).sum((AbelianGroupElem) this.den.multiply(S.num)), (RingElem) this.den.multiply(S.den), false);
    }

    public Local<C> negate() {
        return new Local(this.ring, (RingElem) this.num.negate(), this.den, true);
    }

    public int signum() {
        return this.num.signum();
    }

    public Local<C> subtract(Local<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return new Local(this.ring, (RingElem) ((RingElem) this.num.multiply(S.den)).subtract((AbelianGroupElem) this.den.multiply(S.num)), (RingElem) this.den.multiply(S.den), false);
    }

    public Local<C> divide(Local<C> S) {
        return multiply(S.inverse());
    }

    public Local<C> inverse() {
        if (isONE()) {
            return this;
        }
        if (isUnit()) {
            return new Local(this.ring, this.den, this.num, true);
        }
        throw new ArithmeticException("element not invertible " + this);
    }

    public Local<C> remainder(Local<C> S) {
        if (this.num.isZERO()) {
            throw new ArithmeticException("element not invertible " + this);
        } else if (S.isUnit()) {
            return this.ring.getZERO();
        } else {
            throw new UnsupportedOperationException("remainder not implemented" + S);
        }
    }

    public Local<C>[] quotientRemainder(Local<C> S) {
        return new Local[]{divide((Local) S), remainder((Local) S)};
    }

    public Local<C> multiply(Local<C> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (this.num.isZERO() || S.isONE()) {
            return this;
        }
        if (isONE()) {
            return S;
        }
        return new Local(this.ring, (RingElem) this.num.multiply(S.num), (RingElem) this.den.multiply(S.den), false);
    }

    public Local<C> gcd(Local<C> local) {
        throw new UnsupportedOperationException("gcd not implemented " + getClass().getName());
    }

    public Local<C>[] egcd(Local<C> local) {
        throw new UnsupportedOperationException("egcd not implemented " + getClass().getName());
    }
}
