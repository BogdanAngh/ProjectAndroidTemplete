package edu.jas.gbmod;

import edu.jas.gbufd.PolyGBUtil;
import edu.jas.kern.PrettyPrint;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPair;
import edu.jas.structure.RingElem;
import java.util.Arrays;
import org.apache.log4j.Logger;

public class SolvableQuotient<C extends GcdRingElem<C>> implements GcdRingElem<SolvableQuotient<C>>, QuotPair<GenPolynomial<C>> {
    private static final Logger logger;
    private final boolean debug;
    public final GenSolvablePolynomial<C> den;
    public final GenSolvablePolynomial<C> num;
    public final SolvableQuotientRing<C> ring;

    static {
        logger = Logger.getLogger(SolvableQuotient.class);
    }

    public SolvableQuotient(SolvableQuotientRing<C> r) {
        this(r, r.ring.getZERO());
    }

    public SolvableQuotient(SolvableQuotientRing<C> r, GenSolvablePolynomial<C> n) {
        this(r, n, r.ring.getONE(), true);
    }

    public SolvableQuotient(SolvableQuotientRing<C> r, GenSolvablePolynomial<C> n, GenSolvablePolynomial<C> d) {
        this(r, n, d, false);
    }

    protected SolvableQuotient(SolvableQuotientRing<C> r, GenSolvablePolynomial<C> n, GenSolvablePolynomial<C> d, boolean isred) {
        this.debug = logger.isDebugEnabled();
        if (d == null || d.isZERO()) {
            throw new IllegalArgumentException("denominator may not be zero");
        }
        this.ring = r;
        if (d.signum() < 0) {
            n = (GenSolvablePolynomial) n.negate();
            d = (GenSolvablePolynomial) d.negate();
        }
        if (isred) {
            this.num = n;
            this.den = d;
            return;
        }
        GcdRingElem lc = (GcdRingElem) d.leadingBaseCoefficient();
        if (!lc.isONE() && lc.isUnit()) {
            RingElem lc2 = (GcdRingElem) lc.inverse();
            n = n.multiply(lc2);
            d = d.multiply(lc2);
        }
        if (n.compareTo((GenPolynomial) d) == 0) {
            this.num = this.ring.ring.getONE();
            this.den = this.ring.ring.getONE();
        } else if (n.negate().compareTo((GenPolynomial) d) == 0) {
            this.num = (GenSolvablePolynomial) this.ring.ring.getONE().negate();
            this.den = this.ring.ring.getONE();
        } else if (n.isZERO()) {
            this.num = n;
            this.den = this.ring.ring.getONE();
        } else if (n.isConstant() || d.isConstant()) {
            this.num = n;
            this.den = d;
        } else {
            GenSolvablePolynomial<C>[] gcd = PolyGBUtil.syzGcdCofactors(r.ring, n, d);
            if (!gcd[0].isONE()) {
                logger.info("constructor: gcd = " + Arrays.toString(gcd));
                n = gcd[1];
                d = gcd[2];
                if (n.isConstant() || d.isConstant()) {
                    this.num = n;
                    this.den = d;
                    return;
                }
            }
            GenSolvablePolynomial<C>[] simp = this.ring.engine.leftSimplifier(n, d);
            logger.info("simp: " + Arrays.toString(simp) + ", " + n + ", " + d);
            this.num = simp[0];
            this.den = simp[1];
        }
    }

    public SolvableQuotientRing<C> factory() {
        return this.ring;
    }

    public GenSolvablePolynomial<C> numerator() {
        return this.num;
    }

    public GenSolvablePolynomial<C> denominator() {
        return this.den;
    }

    public SolvableQuotient<C> copy() {
        return new SolvableQuotient(this.ring, this.num, this.den, true);
    }

    public boolean isZERO() {
        return this.num.isZERO();
    }

    public boolean isONE() {
        return this.num.compareTo(this.den) == 0;
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
            return "SolvableQuotient[ " + this.num.toString() + " | " + this.den.toString() + " ]";
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
        return this.num.toScript() + " / " + this.den.toScript();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(SolvableQuotient<C> b) {
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
        GenSolvablePolynomial<C>[] oc = this.ring.engine.leftOreCond(this.den, b.den);
        if (this.debug) {
            System.out.println("oc[0] den =<>= oc[1] b.den: (" + oc[0] + ") (" + this.den + ") = (" + oc[1] + ") (" + b.den + ")");
        }
        return oc[0].multiply(this.num).compareTo((GenPolynomial) oc[1].multiply(b.num));
    }

    public boolean equals(Object b) {
        boolean z = true;
        if (b == null || !(b instanceof SolvableQuotient)) {
            return false;
        }
        SolvableQuotient a = (SolvableQuotient) b;
        if (this.num.equals(a.num) && this.den.equals(a.den)) {
            return true;
        }
        if (compareTo(a) != 0) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((this.ring.hashCode() * 37) + this.num.hashCode()) * 37) + this.den.hashCode();
    }

    public SolvableQuotient<C> rightFraction() {
        if (isZERO() || isONE()) {
            return this;
        }
        GenSolvablePolynomial<C>[] oc = this.ring.engine.rightOreCond(this.num, this.den);
        return new SolvableQuotient(this.ring, oc[1], oc[0], true);
    }

    public boolean isRightFraction(SolvableQuotient<C> s) {
        if (isZERO()) {
            return s.isZERO();
        }
        if (isONE()) {
            return s.isONE();
        }
        return this.den.multiply(s.num).compareTo((GenPolynomial) this.num.multiply(s.den)) == 0;
    }

    public SolvableQuotient<C> abs() {
        return new SolvableQuotient(this.ring, (GenSolvablePolynomial) this.num.abs(), this.den, true);
    }

    public SolvableQuotient<C> sum(SolvableQuotient<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (this.den.isONE() && S.den.isONE()) {
            return new SolvableQuotient(this.ring, (GenSolvablePolynomial) this.num.sum(S.num), this.den, true);
        } else if (this.den.compareTo(S.den) == 0) {
            return new SolvableQuotient(this.ring, (GenSolvablePolynomial) this.num.sum(S.num), this.den, false);
        } else {
            GenSolvablePolynomial<C>[] oc = this.ring.engine.leftOreCond(this.den, S.den);
            if (this.debug) {
                System.out.println("oc[0] den =sum= oc[1] S.den: (" + oc[0] + ") (" + this.den + ") = (" + oc[1] + ") (" + S.den + ")");
            }
            return new SolvableQuotient(this.ring, (GenSolvablePolynomial) oc[0].multiply(this.num).sum((GenPolynomial) oc[1].multiply(S.num)), oc[0].multiply(this.den), false);
        }
    }

    public SolvableQuotient<C> negate() {
        return new SolvableQuotient(this.ring, (GenSolvablePolynomial) this.num.negate(), this.den, true);
    }

    public int signum() {
        return this.num.signum();
    }

    public SolvableQuotient<C> subtract(SolvableQuotient<C> S) {
        return sum(S.negate());
    }

    public SolvableQuotient<C> divide(SolvableQuotient<C> S) {
        return multiply(S.inverse());
    }

    public SolvableQuotient<C> inverse() {
        if (!this.num.isZERO()) {
            return new SolvableQuotient(this.ring, this.den, this.num, true);
        }
        throw new ArithmeticException("element not invertible " + this);
    }

    public SolvableQuotient<C> remainder(SolvableQuotient<C> S) {
        if (!S.isZERO()) {
            return this.ring.getZERO();
        }
        throw new ArithmeticException("element not invertible " + S);
    }

    public SolvableQuotient<C>[] quotientRemainder(SolvableQuotient<C> S) {
        return new SolvableQuotient[]{divide((SolvableQuotient) S), remainder((SolvableQuotient) S)};
    }

    public SolvableQuotient<C> multiply(SolvableQuotient<C> S) {
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
            return new SolvableQuotient(this.ring, this.num.multiply(S.num), this.den, true);
        }
        GenSolvablePolynomial<C>[] oc = this.ring.engine.leftOreCond(this.num, S.den);
        GenSolvablePolynomial<C> n = oc[1].multiply(S.num);
        GenSolvablePolynomial<C> d = oc[0].multiply(this.den);
        if (this.debug) {
            System.out.println("oc[0] num =mult= oc[1] S.den: (" + oc[0] + ") (" + this.num + ") = (" + oc[1] + ") (" + S.den + ")");
        }
        return new SolvableQuotient(this.ring, n, d, false);
    }

    public SolvableQuotient<C> multiply(GenSolvablePolynomial<C> b) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (this.num.isZERO() || b.isONE()) {
            return this;
        }
        return new SolvableQuotient(this.ring, this.num.multiply((GenSolvablePolynomial) b), this.den, false);
    }

    public SolvableQuotient<C> multiply(C b) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (this.num.isZERO() || b.isONE()) {
            return this;
        }
        return new SolvableQuotient(this.ring, this.num.multiply((RingElem) b), this.den, false);
    }

    public SolvableQuotient<C> multiply(ExpVector e) {
        if (e == null || e.isZERO() || this.num.isZERO()) {
            return this;
        }
        return new SolvableQuotient(this.ring, this.num.multiply(e), this.den, false);
    }

    public SolvableQuotient<C> monic() {
        if (this.num.isZERO()) {
            return this;
        }
        GcdRingElem lbc = (GcdRingElem) this.num.leadingBaseCoefficient();
        if (!lbc.isUnit()) {
            return this;
        }
        return new SolvableQuotient(this.ring, this.num.multiply((GcdRingElem) lbc.inverse()), this.den, true);
    }

    public SolvableQuotient<C> gcd(SolvableQuotient<C> b) {
        if (b == null || b.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return b;
        }
        return !equals(b) ? this.ring.getONE() : this;
    }

    public SolvableQuotient<C>[] egcd(SolvableQuotient<C> b) {
        SolvableQuotient[] ret = (SolvableQuotient[]) new SolvableQuotient[3];
        ret[0] = null;
        ret[1] = null;
        ret[2] = null;
        if (b == null || b.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = b;
        } else {
            GenSolvablePolynomial two = this.ring.ring.fromInteger(2);
            ret[0] = this.ring.getONE();
            ret[1] = multiply(two).inverse();
            ret[2] = b.multiply(two).inverse();
        }
        return ret;
    }
}
