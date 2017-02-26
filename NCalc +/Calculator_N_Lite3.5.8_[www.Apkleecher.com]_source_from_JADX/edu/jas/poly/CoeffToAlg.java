package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class CoeffToAlg<C extends GcdRingElem<C>> implements UnaryFunctor<C, AlgebraicNumber<C>> {
    protected final AlgebraicNumberRing<C> afac;
    protected final GenPolynomial<C> zero;

    public CoeffToAlg(AlgebraicNumberRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.afac = fac;
        this.zero = this.afac.ring.getZERO();
    }

    public AlgebraicNumber<C> eval(C c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return new AlgebraicNumber(this.afac, this.zero.sum((RingElem) c));
    }
}
