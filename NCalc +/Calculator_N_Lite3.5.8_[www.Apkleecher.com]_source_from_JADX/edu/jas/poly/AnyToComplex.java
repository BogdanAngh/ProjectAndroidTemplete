package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class AnyToComplex<C extends GcdRingElem<C>> implements UnaryFunctor<C, Complex<C>> {
    protected final ComplexRing<C> cfac;

    public AnyToComplex(ComplexRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.cfac = fac;
    }

    public AnyToComplex(RingFactory<C> fac) {
        this(new ComplexRing(fac));
    }

    public Complex<C> eval(C a) {
        if (a == null || a.isZERO()) {
            return this.cfac.getZERO();
        }
        if (a.isONE()) {
            return this.cfac.getONE();
        }
        return new Complex(this.cfac, (RingElem) a);
    }
}
