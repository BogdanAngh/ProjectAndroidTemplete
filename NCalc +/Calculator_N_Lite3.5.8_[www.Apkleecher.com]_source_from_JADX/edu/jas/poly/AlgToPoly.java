package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class AlgToPoly<C extends GcdRingElem<C>> implements UnaryFunctor<AlgebraicNumber<C>, GenPolynomial<C>> {
    AlgToPoly() {
    }

    public GenPolynomial<C> eval(AlgebraicNumber<C> c) {
        if (c == null) {
            return null;
        }
        return c.val;
    }
}
