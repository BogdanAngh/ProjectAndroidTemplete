package edu.jas.root;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.kern.PrettyPrint;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;

public class RealAlgebraicNumber<C extends GcdRingElem<C> & Rational> implements GcdRingElem<RealAlgebraicNumber<C>>, Rational {
    public final AlgebraicNumber<C> number;
    public final RealAlgebraicRing<C> ring;

    public RealAlgebraicNumber(RealAlgebraicRing<C> r, GenPolynomial<C> a) {
        this.number = new AlgebraicNumber(r.algebraic, a);
        this.ring = r;
    }

    public RealAlgebraicNumber(RealAlgebraicRing<C> r, AlgebraicNumber<C> a) {
        this.number = a;
        this.ring = r;
    }

    public RealAlgebraicNumber(RealAlgebraicRing<C> r) {
        this((RealAlgebraicRing) r, r.algebraic.getZERO());
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
        return "Real" + this.number.toString();
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
            s = this.number.ring.modul.compareTo(b.number.ring.modul);
            System.out.println("s_mod = " + s);
        }
        if (s != 0) {
            return s;
        }
        return subtract((RealAlgebraicNumber) b).signum();
    }

    public int compareTo(AlgebraicNumber<C> b) {
        int s = this.number.compareTo((AlgebraicNumber) b);
        System.out.println("s_algeb = " + s);
        return s;
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof RealAlgebraicNumber)) {
            return false;
        }
        RealAlgebraicNumber<C> a = (RealAlgebraicNumber) b;
        if (this.ring.equals(a.ring)) {
            return this.number.equals(a.number);
        }
        return false;
    }

    public int hashCode() {
        return (this.number.val.hashCode() * 37) + this.ring.hashCode();
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

    public RealAlgebraicNumber<C> sum(GenPolynomial<C> c) {
        return new RealAlgebraicNumber(this.ring, this.number.sum((GenPolynomial) c));
    }

    public RealAlgebraicNumber<C> sum(C c) {
        return new RealAlgebraicNumber(this.ring, this.number.sum((RingElem) c));
    }

    public RealAlgebraicNumber<C> negate() {
        return new RealAlgebraicNumber(this.ring, this.number.negate());
    }

    public int signum() {
        Interval<C> v = this.ring.engine.invariantSignInterval(this.ring.root, this.ring.algebraic.modul, this.number.val);
        this.ring.setRoot(v);
        return this.ring.engine.realIntervalSign(v, this.ring.algebraic.modul, this.number.val);
    }

    public void halfInterval() {
        this.ring.setRoot(this.ring.engine.halfInterval(this.ring.root, this.ring.algebraic.modul));
    }

    public BigRational magnitude() {
        Interval<C> v = this.ring.engine.invariantMagnitudeInterval(this.ring.root, this.ring.algebraic.modul, this.number.val, this.ring.getEps());
        this.ring.setRoot(v);
        return ((Rational) ((GcdRingElem) this.ring.engine.realIntervalMagnitude(v, this.ring.algebraic.modul, this.number.val))).getRational();
    }

    public BigDecimal decimalMagnitude() {
        return new BigDecimal(magnitude());
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

    public RealAlgebraicNumber<C>[] quotientRemainder(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber[]{divide((RealAlgebraicNumber) S), remainder((RealAlgebraicNumber) S)};
    }

    public RealAlgebraicNumber<C> multiply(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber(this.ring, this.number.multiply(S.number));
    }

    public RealAlgebraicNumber<C> multiply(C c) {
        return new RealAlgebraicNumber(this.ring, this.number.multiply((RingElem) c));
    }

    public RealAlgebraicNumber<C> multiply(GenPolynomial<C> c) {
        return new RealAlgebraicNumber(this.ring, this.number.multiply((GenPolynomial) c));
    }

    public RealAlgebraicNumber<C> monic() {
        return new RealAlgebraicNumber(this.ring, this.number.monic());
    }

    public RealAlgebraicNumber<C> gcd(RealAlgebraicNumber<C> S) {
        return new RealAlgebraicNumber(this.ring, this.number.gcd(S.number));
    }

    public RealAlgebraicNumber<C>[] egcd(RealAlgebraicNumber<C> S) {
        AlgebraicNumber<C>[] aret = this.number.egcd(S.number);
        return new RealAlgebraicNumber[]{new RealAlgebraicNumber(this.ring, aret[0]), new RealAlgebraicNumber(this.ring, aret[1]), new RealAlgebraicNumber(this.ring, aret[2])};
    }
}
