package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilRoot */
class CoeffToReAlg<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<C, RealAlgebraicNumber<C>> {
    protected final RealAlgebraicRing<C> afac;
    protected final GenPolynomial<C> zero;

    public CoeffToReAlg(RealAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.afac = fac;
        this.zero = this.afac.algebraic.ring.getZERO();
    }

    public RealAlgebraicNumber<C> eval(C c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return new RealAlgebraicNumber(this.afac, this.zero.sum((RingElem) c));
    }
}
