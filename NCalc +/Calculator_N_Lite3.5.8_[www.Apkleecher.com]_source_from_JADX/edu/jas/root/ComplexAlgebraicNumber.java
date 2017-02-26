package edu.jas.root;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.kern.PrettyPrint;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;

public class ComplexAlgebraicNumber<C extends GcdRingElem<C> & Rational> implements GcdRingElem<ComplexAlgebraicNumber<C>> {
    public final AlgebraicNumber<Complex<C>> number;
    public final ComplexAlgebraicRing<C> ring;

    public ComplexAlgebraicNumber(ComplexAlgebraicRing<C> r, GenPolynomial<Complex<C>> a) {
        this.number = new AlgebraicNumber(r.algebraic, a);
        this.ring = r;
    }

    public ComplexAlgebraicNumber(ComplexAlgebraicRing<C> r, AlgebraicNumber<Complex<C>> a) {
        this.number = a;
        this.ring = r;
    }

    public ComplexAlgebraicNumber(ComplexAlgebraicRing<C> r) {
        this((ComplexAlgebraicRing) r, r.algebraic.getZERO());
    }

    public ComplexAlgebraicRing<C> factory() {
        return this.ring;
    }

    public ComplexAlgebraicNumber<C> copy() {
        return new ComplexAlgebraicNumber(this.ring, this.number);
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

    public int compareTo(ComplexAlgebraicNumber<C> b) {
        int s = 0;
        if (this.number.ring != b.number.ring) {
            s = this.number.ring.modul.compareTo(b.number.ring.modul);
            System.out.println("s_mod = " + s);
        }
        if (s != 0) {
            return s;
        }
        return this.number.compareTo(b.number);
    }

    public int compareTo(AlgebraicNumber<Complex<C>> b) {
        return this.number.compareTo((AlgebraicNumber) b);
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof ComplexAlgebraicNumber)) {
            return false;
        }
        ComplexAlgebraicNumber<C> a = (ComplexAlgebraicNumber) b;
        if (this.ring.equals(a.ring)) {
            return this.number.equals(a.number);
        }
        return false;
    }

    public int hashCode() {
        return (this.number.val.hashCode() * 37) + this.ring.hashCode();
    }

    public ComplexAlgebraicNumber<C> abs() {
        if (signum() < 0) {
            return new ComplexAlgebraicNumber(this.ring, this.number.negate());
        }
        return this;
    }

    public ComplexAlgebraicNumber<C> sum(ComplexAlgebraicNumber<C> S) {
        return new ComplexAlgebraicNumber(this.ring, this.number.sum(S.number));
    }

    public ComplexAlgebraicNumber<C> sum(GenPolynomial<Complex<C>> c) {
        return new ComplexAlgebraicNumber(this.ring, this.number.sum((GenPolynomial) c));
    }

    public ComplexAlgebraicNumber<C> sum(AlgebraicNumber<Complex<C>> c) {
        return new ComplexAlgebraicNumber(this.ring, this.number.sum((AlgebraicNumber) c));
    }

    public ComplexAlgebraicNumber<C> sum(Complex<C> c) {
        return new ComplexAlgebraicNumber(this.ring, this.number.sum((RingElem) c));
    }

    public ComplexAlgebraicNumber<C> negate() {
        return new ComplexAlgebraicNumber(this.ring, this.number.negate());
    }

    public ComplexAlgebraicNumber<C> subtract(ComplexAlgebraicNumber<C> S) {
        return new ComplexAlgebraicNumber(this.ring, this.number.subtract(S.number));
    }

    public ComplexAlgebraicNumber<C> divide(ComplexAlgebraicNumber<C> S) {
        return multiply(S.inverse());
    }

    public ComplexAlgebraicNumber<C> inverse() {
        return new ComplexAlgebraicNumber(this.ring, this.number.inverse());
    }

    public ComplexAlgebraicNumber<C> remainder(ComplexAlgebraicNumber<C> S) {
        return new ComplexAlgebraicNumber(this.ring, this.number.remainder(S.number));
    }

    public ComplexAlgebraicNumber<C>[] quotientRemainder(ComplexAlgebraicNumber<C> S) {
        return new ComplexAlgebraicNumber[]{divide((ComplexAlgebraicNumber) S), remainder((ComplexAlgebraicNumber) S)};
    }

    public ComplexAlgebraicNumber<C> multiply(ComplexAlgebraicNumber<C> S) {
        return new ComplexAlgebraicNumber(this.ring, this.number.multiply(S.number));
    }

    public ComplexAlgebraicNumber<C> multiply(Complex<C> c) {
        return new ComplexAlgebraicNumber(this.ring, this.number.multiply((RingElem) c));
    }

    public ComplexAlgebraicNumber<C> multiply(GenPolynomial<Complex<C>> c) {
        return new ComplexAlgebraicNumber(this.ring, this.number.multiply((GenPolynomial) c));
    }

    public ComplexAlgebraicNumber<C> monic() {
        return new ComplexAlgebraicNumber(this.ring, this.number.monic());
    }

    public ComplexAlgebraicNumber<C> gcd(ComplexAlgebraicNumber<C> S) {
        return new ComplexAlgebraicNumber(this.ring, this.number.gcd(S.number));
    }

    public ComplexAlgebraicNumber<C>[] egcd(ComplexAlgebraicNumber<C> S) {
        AlgebraicNumber<Complex<C>>[] aret = this.number.egcd(S.number);
        return new ComplexAlgebraicNumber[]{new ComplexAlgebraicNumber(this.ring, aret[0]), new ComplexAlgebraicNumber(this.ring, aret[1]), new ComplexAlgebraicNumber(this.ring, aret[2])};
    }

    public int signum() {
        try {
            Rectangle<C> v = this.ring.engine.invariantRectangle(this.ring.root, this.ring.algebraic.modul, this.number.val);
            this.ring.setRoot(v);
            return v.getCenter().signum();
        } catch (InvalidBoundaryException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Complex<BigRational> magnitude() {
        try {
            Rectangle<C> v = this.ring.engine.invariantMagnitudeRectangle(this.ring.root, this.ring.algebraic.modul, this.number.val, this.ring.getEps());
            this.ring.setRoot(v);
            Complex<C> ev = this.ring.engine.complexRectangleMagnitude(v, this.ring.algebraic.modul, this.number.val);
            BigRational er = ((Rational) ((GcdRingElem) ev.getRe())).getRational();
            return new Complex(new ComplexRing(er.factory()), er, ((Rational) ((GcdRingElem) ev.getIm())).getRational());
        } catch (InvalidBoundaryException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Complex<BigDecimal> decimalMagnitude() {
        Complex<BigRational> cr = magnitude();
        return new Complex(new ComplexRing(BigDecimal.ZERO), new BigDecimal((BigRational) cr.getRe()), new BigDecimal((BigRational) cr.getIm()));
    }
}
