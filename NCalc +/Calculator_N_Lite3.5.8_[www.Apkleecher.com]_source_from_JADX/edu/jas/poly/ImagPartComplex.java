package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class ImagPartComplex<C extends RingElem<C>> implements UnaryFunctor<Complex<C>, C> {
    ImagPartComplex() {
    }

    public C eval(Complex<C> c) {
        if (c == null) {
            return null;
        }
        return c.getIm();
    }
}
