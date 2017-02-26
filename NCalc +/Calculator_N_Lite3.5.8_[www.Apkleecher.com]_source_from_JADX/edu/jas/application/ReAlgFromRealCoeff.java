package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.root.RealAlgebraicRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilApp */
class ReAlgFromRealCoeff<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<RealAlgebraicNumber<C>, RealAlgebraicNumber<C>> {
    protected final RealAlgebraicRing<C> afac;

    public ReAlgFromRealCoeff(RealAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.afac = fac;
    }

    public RealAlgebraicNumber<C> eval(RealAlgebraicNumber<C> c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return c.number;
    }
}
