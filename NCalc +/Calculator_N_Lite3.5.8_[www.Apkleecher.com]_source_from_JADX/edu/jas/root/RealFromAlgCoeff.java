package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilRoot */
class RealFromAlgCoeff<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<AlgebraicNumber<C>, RealAlgebraicNumber<C>> {
    protected final RealAlgebraicRing<C> rfac;

    public RealFromAlgCoeff(RealAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.rfac = fac;
    }

    public RealAlgebraicNumber<C> eval(AlgebraicNumber<C> c) {
        if (c == null) {
            return this.rfac.getZERO();
        }
        return new RealAlgebraicNumber(this.rfac, (AlgebraicNumber) c);
    }
}
