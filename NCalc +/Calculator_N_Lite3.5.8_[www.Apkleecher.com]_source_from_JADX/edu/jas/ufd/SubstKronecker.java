package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUfdUtil */
class SubstKronecker<C extends GcdRingElem<C>> implements UnaryFunctor<GenPolynomial<C>, GenPolynomial<C>> {
    final long d;

    public SubstKronecker(long d) {
        this.d = d;
    }

    public GenPolynomial<C> eval(GenPolynomial<C> c) {
        if (c == null) {
            return null;
        }
        return PolyUfdUtil.substituteKronecker((GenPolynomial) c, this.d);
    }
}
