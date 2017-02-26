package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RecToDist<C extends RingElem<C>> implements UnaryFunctor<GenPolynomial<GenPolynomial<C>>, GenPolynomial<C>> {
    GenPolynomialRing<C> fac;

    public RecToDist(GenPolynomialRing<C> fac) {
        this.fac = fac;
    }

    public GenPolynomial<C> eval(GenPolynomial<GenPolynomial<C>> c) {
        if (c == null) {
            return this.fac.getZERO();
        }
        return PolyUtil.distribute(this.fac, (GenPolynomial) c);
    }
}
