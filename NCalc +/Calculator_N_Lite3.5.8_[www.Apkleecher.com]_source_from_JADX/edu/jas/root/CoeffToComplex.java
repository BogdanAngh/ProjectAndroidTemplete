package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilRoot */
class CoeffToComplex<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<C, ComplexAlgebraicNumber<C>> {
    protected final ComplexAlgebraicRing<C> cfac;
    protected final ComplexRing<C> cr;
    protected final AlgebraicNumber<Complex<C>> zero;

    public CoeffToComplex(ComplexAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.cfac = fac;
        AlgebraicNumberRing<Complex<C>> afac = this.cfac.algebraic;
        this.zero = afac.getZERO();
        this.cr = (ComplexRing) afac.ring.coFac;
    }

    public ComplexAlgebraicNumber<C> eval(C c) {
        if (c == null) {
            return this.cfac.getZERO();
        }
        return new ComplexAlgebraicNumber(this.cfac, this.zero.sum(new Complex(this.cr, (RingElem) c)));
    }
}
