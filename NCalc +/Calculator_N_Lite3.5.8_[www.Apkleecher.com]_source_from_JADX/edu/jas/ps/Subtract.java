package edu.jas.ps;

import edu.jas.structure.BinaryFunctor;
import edu.jas.structure.RingElem;

/* compiled from: UnivPowerSeries */
class Subtract<C extends RingElem<C>> implements BinaryFunctor<C, C, C> {
    Subtract() {
    }

    public C eval(C c1, C c2) {
        return (RingElem) c1.subtract(c2);
    }
}
