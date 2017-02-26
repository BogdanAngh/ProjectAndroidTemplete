package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class DistToRec<C extends RingElem<C>> implements UnaryFunctor<GenPolynomial<C>, GenPolynomial<GenPolynomial<C>>> {
    GenPolynomialRing<GenPolynomial<C>> fac;

    public DistToRec(GenPolynomialRing<GenPolynomial<C>> fac) {
        this.fac = fac;
    }

    public GenPolynomial<GenPolynomial<C>> eval(GenPolynomial<C> c) {
        if (c == null) {
            return this.fac.getZERO();
        }
        return PolyUtil.recursive(this.fac, (GenPolynomial) c);
    }
}
