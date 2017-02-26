package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilApp */
class CoeffToComplexReal<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<Complex<C>, Complex<RealAlgebraicNumber<C>>> {
    final RealAlgebraicRing<C> afac;
    protected final ComplexRing<RealAlgebraicNumber<C>> cfac;
    final GenPolynomialRing<C> pfac;

    public CoeffToComplexReal(ComplexRing<RealAlgebraicNumber<C>> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.cfac = fac;
        this.afac = (RealAlgebraicRing) this.cfac.ring;
        this.pfac = this.afac.univs.ideal.getRing();
    }

    public Complex<RealAlgebraicNumber<C>> eval(Complex<C> c) {
        if (c == null) {
            return this.cfac.getZERO();
        }
        GenPolynomial pr = new GenPolynomial(this.pfac, c.getRe());
        GenPolynomial pi = new GenPolynomial(this.pfac, c.getIm());
        return new Complex(this.cfac, new RealAlgebraicNumber(this.afac, pr), new RealAlgebraicNumber(this.afac, pi));
    }
}
