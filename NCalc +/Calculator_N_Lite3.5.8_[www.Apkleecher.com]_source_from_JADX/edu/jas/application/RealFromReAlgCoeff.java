package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilApp */
class RealFromReAlgCoeff<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<RealAlgebraicNumber<C>, RealAlgebraicNumber<C>> {
    protected final RealAlgebraicRing<C> rfac;

    public RealFromReAlgCoeff(RealAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.rfac = fac;
    }

    public RealAlgebraicNumber<C> eval(RealAlgebraicNumber<C> c) {
        if (c == null) {
            return this.rfac.getZERO();
        }
        return new RealAlgebraicNumber(this.rfac, (RealAlgebraicNumber) c);
    }
}
