package edu.jas.application;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.kern.PrettyPrint;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;

public class RealAlgebraicNumber<C extends GcdRingElem<C> & Rational> implements GcdRingElem<RealAlgebraicNumber<C>>, Rational {
    public final edu.jas.root.RealAlgebraicNumber<edu.jas.root.RealAlgebraicNumber<C>> number;
    public final RealAlgebraicRing<C> ring;

    public RealAlgebraicNumber(RealAlgebraicRing<C> r) {
        this((RealAlgebraicRing) r, r.realRing.getZERO());
    }

    public RealAlgebraicNumber(RealAlgebraicRing<C> r, C a) {
        this((RealAlgebraicRing) r, r.realRing.parse(a.toString()));
    }

    public RealAlgebraicNumber(RealAlgebraicRing<C> r, GenPolynomial<C> a) {
        this((RealAlgebraicRing) r, r.realRing.parse(a.toString()));
    }

    public RealAlgebraicNumber(RealAlgebraicRing<C> r, edu.jas.root.RealAlgebraicNumber<edu.jas.root.RealAlgebraicNumber<C>> a) {
        this.number = a;
        this.ring = r;
    }

    public RealAlgebraicRing<C> factory() {
        return this.ring;
    }

    public RealAlgebraicNumber<C> copy() {
        return new RealAlgebraicNumber(this.ring, this.number);
    }

    public BigRational getRational() {
        return magnitude();
    }

    public boolean isZERO() {
        return this.number.isZERO();
    }

    public boolean isONE() {
        return this.number.isONE();
    }

    public boolean isUnit() {
        return this.number.isUnit();
    }

    public String toString() {
        if (PrettyPrint.isTrue()) {
            return "{ " + this.number.toString() + " }";
        }
        return "Complex" + this.number.toString();
    }

    public String toScript() {
        return this.number.toScript();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(RealAlgebraicNumber<C> b) {
        int s = 0;
        if (this.number.ring != b.number.ring) {
            s = this.number.ring.equals(b.number.ring) ? 0 : 1;
            System.out.println("s_mod = " + s);
        }
        if (s != 0) {
            return s;
        }
        return this.number.compareTo(b.number);
    }

    public int compareTo(edu.jas.root.RealAlgebraicNumber<edu.jas.root.RealAlgebraicNumber<C>> b) {
        return this.number.compareTo((edu.jas.root.RealAlgebraicNumber) b);
    }

    public boolean equals(Object b) {
        if (!(b instanceof RealAlgebraicNumber)) {
            return false;
        }
        RealAlgebraicNumber<C> a = null;
        try {
            a = (RealAlgebraicNumber) b;
        } catch (ClassCastException e) {
        }
        if (a == null || !this.ring.equals(a.ring)) {
            return false;
        }
        return this.number.equals(a.number);
    }

    public int hashCode() {
        return (this.number.hashCode() * 37) + this.ring.hashCode();
    }

    public RealAlgebraicNumber<C> abs() {
        if (signum() < 0) {
            return new RealAlgebraicNumber(this.ring, this.number.negate());
        }
        return this;
    }

    public RealAlgebraicNumber<C> sum(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber(this.ring, this.number.sum(S.number));
    }

    public RealAlgebraicNumber<C> sum(edu.jas.root.RealAlgebraicNumber<edu.jas.root.RealAlgebraicNumber<C>> c) {
        return new RealAlgebraicNumber(this.ring, this.number.sum((edu.jas.root.RealAlgebraicNumber) c));
    }

    public RealAlgebraicNumber<C> negate() {
        return new RealAlgebraicNumber(this.ring, this.number.negate());
    }

    public RealAlgebraicNumber<C> subtract(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber(this.ring, this.number.subtract(S.number));
    }

    public RealAlgebraicNumber<C> divide(RealAlgebraicNumber<C> S) {
        return multiply(S.inverse());
    }

    public RealAlgebraicNumber<C> inverse() {
        return new RealAlgebraicNumber(this.ring, this.number.inverse());
    }

    public RealAlgebraicNumber<C> remainder(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber(this.ring, this.number.remainder(S.number));
    }

    public RealAlgebraicNumber<C> multiply(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber(this.ring, this.number.multiply(S.number));
    }

    public RealAlgebraicNumber<C> multiply(edu.jas.root.RealAlgebraicNumber<edu.jas.root.RealAlgebraicNumber<C>> c) {
        return new RealAlgebraicNumber(this.ring, this.number.multiply((edu.jas.root.RealAlgebraicNumber) c));
    }

    public RealAlgebraicNumber<C> monic() {
        return new RealAlgebraicNumber(this.ring, this.number.monic());
    }

    public RealAlgebraicNumber<C> gcd(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber(this.ring, this.number.gcd(S.number));
    }

    public RealAlgebraicNumber<C>[] egcd(RealAlgebraicNumber<C> S) {
        edu.jas.root.RealAlgebraicNumber<edu.jas.root.RealAlgebraicNumber<C>>[] aret = this.number.egcd(S.number);
        return new RealAlgebraicNumber[]{new RealAlgebraicNumber(this.ring, aret[0]), new RealAlgebraicNumber(this.ring, aret[1]), new RealAlgebraicNumber(this.ring, aret[2])};
    }

    public int signum() {
        return this.number.signum();
    }

    public BigRational magnitude() {
        return this.number.magnitude();
    }

    public BigDecimal decimalMagnitude() {
        return new BigDecimal(magnitude());
    }
}
