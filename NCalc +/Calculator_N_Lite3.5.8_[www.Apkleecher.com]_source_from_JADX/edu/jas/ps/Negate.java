package edu.jas.ps;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: UnivPowerSeries */
class Negate<C extends RingElem<C>> implements UnaryFunctor<C, C> {
    Negate() {
    }

    public C eval(C c) {
        return (RingElem) c.negate();
    }
}
