package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.Complex;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilRoot */
class CoeffToComplexFromComplex<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<Complex<C>, ComplexAlgebraicNumber<C>> {
    protected final ComplexAlgebraicRing<C> cfac;
    protected final AlgebraicNumber<Complex<C>> zero;

    public CoeffToComplexFromComplex(ComplexAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.cfac = fac;
        this.zero = this.cfac.algebraic.getZERO();
    }

    public ComplexAlgebraicNumber<C> eval(Complex<C> c) {
        if (c == null) {
            return this.cfac.getZERO();
        }
        return new ComplexAlgebraicNumber(this.cfac, this.zero.sum((RingElem) c));
    }
}
