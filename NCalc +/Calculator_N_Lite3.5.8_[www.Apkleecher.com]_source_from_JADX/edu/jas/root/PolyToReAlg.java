package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilRoot */
class PolyToReAlg<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<GenPolynomial<C>, RealAlgebraicNumber<C>> {
    protected final RealAlgebraicRing<C> afac;

    public PolyToReAlg(RealAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.afac = fac;
    }

    public RealAlgebraicNumber<C> eval(GenPolynomial<C> c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return new RealAlgebraicNumber(this.afac, (GenPolynomial) c);
    }
}
