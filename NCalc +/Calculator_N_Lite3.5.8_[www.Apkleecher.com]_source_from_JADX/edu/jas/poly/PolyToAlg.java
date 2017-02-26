package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class PolyToAlg<C extends GcdRingElem<C>> implements UnaryFunctor<GenPolynomial<C>, AlgebraicNumber<C>> {
    protected final AlgebraicNumberRing<C> afac;

    public PolyToAlg(AlgebraicNumberRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.afac = fac;
    }

    public AlgebraicNumber<C> eval(GenPolynomial<C> c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return new AlgebraicNumber(this.afac, c);
    }
}
