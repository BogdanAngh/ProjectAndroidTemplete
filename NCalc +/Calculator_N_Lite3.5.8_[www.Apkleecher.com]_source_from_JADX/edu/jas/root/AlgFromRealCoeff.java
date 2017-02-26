package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilRoot */
class AlgFromRealCoeff<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<RealAlgebraicNumber<C>, AlgebraicNumber<C>> {
    protected final AlgebraicNumberRing<C> afac;

    public AlgFromRealCoeff(AlgebraicNumberRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.afac = fac;
    }

    public AlgebraicNumber<C> eval(RealAlgebraicNumber<C> c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return c.number;
    }
}
