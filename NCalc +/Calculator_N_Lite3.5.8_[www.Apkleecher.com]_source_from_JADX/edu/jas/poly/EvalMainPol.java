package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class EvalMainPol<C extends RingElem<C>> implements UnaryFunctor<GenPolynomial<C>, GenPolynomial<C>> {
    final C a;
    final GenPolynomialRing<C> cfac;

    public EvalMainPol(GenPolynomialRing<C> cfac, C a) {
        this.cfac = cfac;
        this.a = a;
    }

    public GenPolynomial<C> eval(GenPolynomial<C> c) {
        if (c == null) {
            return this.cfac.getZERO();
        }
        return PolyUtil.evaluateMain(this.cfac, (GenPolynomial) c, this.a);
    }
}
