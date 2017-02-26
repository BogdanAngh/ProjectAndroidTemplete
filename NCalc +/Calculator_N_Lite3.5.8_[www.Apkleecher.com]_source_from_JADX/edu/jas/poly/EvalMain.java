package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class EvalMain<C extends RingElem<C>> implements UnaryFunctor<GenPolynomial<C>, C> {
    final C a;
    final RingFactory<C> cfac;

    public EvalMain(RingFactory<C> cfac, C a) {
        this.cfac = cfac;
        this.a = a;
    }

    public C eval(GenPolynomial<C> c) {
        if (c == null) {
            return (RingElem) this.cfac.getZERO();
        }
        return PolyUtil.evaluateMain(this.cfac, (GenPolynomial) c, this.a);
    }
}
