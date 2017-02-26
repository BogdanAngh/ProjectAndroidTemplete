package edu.jas.application;

import edu.jas.kern.PrettyPrint;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPair;
import edu.jas.structure.RingElem;
import org.apache.log4j.Logger;

public class Local<C extends GcdRingElem<C>> implements RingElem<Local<C>>, QuotPair<GenPolynomial<C>> {
    private static final Logger logger;
    private final boolean debug;
    protected final GenPolynomial<C> den;
    protected int isunit;
    protected final GenPolynomial<C> num;
    public final LocalRing<C> ring;

    static {
        logger = Logger.getLogger(Local.class);
    }

    public Local(LocalRing<C> r) {
        this(r, r.ring.getZERO());
    }

    public Local(LocalRing<C> r, GenPolynomial<C> n) {
        this(r, n, r.ring.getONE(), true);
    }

    public Local(LocalRing<C> r, GenPolynomial<C> n, GenPolynomial<C> d) {
        this(r, n, d, false);
    }

    protected Local(LocalRing<C> r, GenPolynomial<C> n, GenPolynomial<C> d, boolean isred) {
        this.debug = logger.isDebugEnabled();
        this.isunit = -1;
        if (d == null || d.isZERO()) {
            throw new IllegalArgumentException("denominator may not be zero");
        }
        this.ring = r;
        if (d.signum() < 0) {
            n = n.negate();
            GenPolynomial d2 = d.negate();
        }
        if (isred) {
            this.num = n;
            this.den = d2;
            return;
        }
        GenPolynomial<C> p = this.ring.ideal.normalform(d2);
        if (p == null || p.isZERO()) {
            throw new IllegalArgumentException("denominator may not be in ideal");
        }
        GcdRingElem lc = (GcdRingElem) d2.leadingBaseCoefficient();
        if (!lc.isONE() && lc.isUnit()) {
            RingElem lc2 = (GcdRingElem) lc.inverse();
            n = n.multiply(lc2);
            d2 = d2.multiply(lc2);
        }
        if (n.compareTo(d2) == 0) {
            this.num = this.ring.ring.getONE();
            this.den = this.ring.ring.getONE();
        } else if (n.negate().compareTo(d2) == 0) {
            this.num = this.ring.ring.getONE().negate();
            this.den = this.ring.ring.getONE();
        } else if (n.isZERO()) {
            this.num = n;
            this.den = this.ring.ring.getONE();
        } else {
            GenPolynomial gcd = this.ring.engine.gcd(n, d2);
            if (this.debug) {
                logger.info("gcd = " + gcd);
            }
            if (gcd.isONE()) {
                this.num = n;
                this.den = d2;
                return;
            }
            this.num = n.divide(gcd);
            this.den = d2.divide(gcd);
        }
    }

    public LocalRing<C> factory() {
        return this.ring;
    }

    public GenPolynomial<C> numerator() {
        return this.num;
    }

    public GenPolynomial<C> denominator() {
        return this.den;
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
        GenPolynomial<C> p = this.ring.ideal.normalform(this.num);
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

    public boolean isConstant() {
        return this.num.isConstant() && this.den.isConstant();
    }

    public String toString() {
        if (!PrettyPrint.isTrue()) {
            return "Local[ " + this.num.toString() + " | " + this.den.toString() + " ]";
        }
        String s = "{ " + this.num.toString(this.ring.ring.getVars());
        if (this.den.isONE()) {
            return s + " }";
        }
        return s + "| " + this.den.toString(this.ring.ring.getVars()) + " }";
    }

    public String toScript() {
        if (this.den.isONE()) {
            return this.num.toScript();
        }
        return this.num.toScript() + " / " + this.den.toScript();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(Local<C> b) {
        if (b == null || b.isZERO()) {
            return signum();
        }
        if (isZERO()) {
            return -b.signum();
        }
        int t = (this.num.signum() - b.num.signum()) / 2;
        if (t != 0) {
            System.out.println("compareTo: t = " + t);
            return t;
        } else if (this.den.compareTo(b.den) == 0) {
            return this.num.compareTo(b.num);
        } else {
            return this.num.multiply(b.den).compareTo(this.den.multiply(b.num));
        }
    }

    public boolean equals(Object b) {
        if (!(b instanceof Local)) {
            return false;
        }
        Local a = null;
        try {
            a = (Local) b;
        } catch (ClassCastException e) {
        }
        if (a == null || compareTo(a) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((this.ring.hashCode() * 37) + this.num.hashCode()) * 37) + this.den.hashCode();
    }

    public Local<C> abs() {
        return new Local(this.ring, this.num.abs(), this.den, true);
    }

    public Local<C> sum(Local<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return new Local(this.ring, this.num.multiply(S.den).sum(this.den.multiply(S.num)), this.den.multiply(S.den), false);
    }

    public Local<C> negate() {
        return new Local(this.ring, this.num.negate(), this.den, true);
    }

    public int signum() {
        return this.num.signum();
    }

    public Local<C> subtract(Local<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return new Local(this.ring, this.num.multiply(S.den).subtract(this.den.multiply(S.num)), this.den.multiply(S.den), false);
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
        if (S.isUnit()) {
            return this.ring.getZERO();
        }
        throw new UnsupportedOperationException("remainder not implemented" + S);
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
        return new Local(this.ring, this.num.multiply(S.num), this.den.multiply(S.den), false);
    }

    public Local<C> multiply(GenPolynomial<C> b) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (this.num.isZERO() || b.isONE()) {
            return this;
        }
        return new Local(this.ring, this.num.multiply((GenPolynomial) b), this.den, false);
    }

    public Local<C> multiply(C b) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (this.num.isZERO() || b.isONE()) {
            return this;
        }
        return new Local(this.ring, this.num.multiply((RingElem) b), this.den, false);
    }

    public Local<C> multiply(ExpVector e) {
        if (e == null || e.isZERO() || this.num.isZERO()) {
            return this;
        }
        return new Local(this.ring, this.num.multiply(e), this.den, false);
    }

    public Local<C> monic() {
        return this.num.isZERO() ? this : this;
    }

    public Local<C> gcd(Local<C> b) {
        return new Local(this.ring, this.ring.engine.gcd(this.num, b.num), this.ring.engine.gcd(this.den, b.den), true);
    }

    public Local<C>[] egcd(Local<C> local) {
        throw new UnsupportedOperationException("egcd not implemented " + getClass().getName());
    }
}
