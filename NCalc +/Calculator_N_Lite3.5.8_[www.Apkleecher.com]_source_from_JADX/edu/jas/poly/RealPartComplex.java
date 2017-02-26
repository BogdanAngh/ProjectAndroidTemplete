package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RealPartComplex<C extends RingElem<C>> implements UnaryFunctor<Complex<C>, C> {
    RealPartComplex() {
    }

    public C eval(Complex<C> c) {
        if (c == null) {
            return null;
        }
        return c.getRe();
    }
}
