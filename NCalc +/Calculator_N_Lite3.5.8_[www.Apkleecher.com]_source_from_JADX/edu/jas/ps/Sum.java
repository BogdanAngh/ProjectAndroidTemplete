package edu.jas.ps;

import edu.jas.structure.BinaryFunctor;
import edu.jas.structure.RingElem;

/* compiled from: UnivPowerSeries */
class Sum<C extends RingElem<C>> implements BinaryFunctor<C, C, C> {
    Sum() {
    }

    public C eval(C c1, C c2) {
        return (RingElem) c1.sum(c2);
    }
}
