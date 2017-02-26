package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilRoot */
class CoeffToReal<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<C, RealAlgebraicNumber<C>> {
    protected final RealAlgebraicRing<C> rfac;
    protected final AlgebraicNumber<C> zero;

    public CoeffToReal(RealAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.rfac = fac;
        this.zero = this.rfac.algebraic.getZERO();
    }

    public RealAlgebraicNumber<C> eval(C c) {
        if (c == null) {
            return this.rfac.getZERO();
        }
        return new RealAlgebraicNumber(this.rfac, this.zero.sum((RingElem) c));
    }
}
