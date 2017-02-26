package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class ToComplex<C extends RingElem<C>> implements UnaryFunctor<C, Complex<C>> {
    protected final ComplexRing<C> cfac;

    public ToComplex(RingFactory<Complex<C>> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.cfac = (ComplexRing) fac;
    }

    public Complex<C> eval(C c) {
        if (c == null) {
            return this.cfac.getZERO();
        }
        return new Complex(this.cfac, (RingElem) c);
    }
}
