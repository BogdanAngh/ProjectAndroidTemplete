package edu.jas.ps;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: UnivPowerSeries */
class Multiply<C extends RingElem<C>> implements UnaryFunctor<C, C> {
    C x;

    public Multiply(C x) {
        this.x = x;
    }

    public C eval(C c) {
        return (RingElem) c.multiply(this.x);
    }
}
