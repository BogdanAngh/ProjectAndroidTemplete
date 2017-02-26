package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUfdUtil */
class BackSubstKronecker<C extends GcdRingElem<C>> implements UnaryFunctor<GenPolynomial<C>, GenPolynomial<C>> {
    final long d;
    final GenPolynomialRing<C> fac;

    public BackSubstKronecker(GenPolynomialRing<C> fac, long d) {
        this.d = d;
        this.fac = fac;
    }

    public GenPolynomial<C> eval(GenPolynomial<C> c) {
        if (c == null) {
            return null;
        }
        return PolyUfdUtil.backSubstituteKronecker(this.fac, (GenPolynomial) c, this.d);
    }
}
