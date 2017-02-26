package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;
import java.util.Iterator;

/* compiled from: PolyUtil */
class AlgebToCompl<C extends GcdRingElem<C>> implements UnaryFunctor<AlgebraicNumber<C>, Complex<C>> {
    protected final ComplexRing<C> cfac;

    public AlgebToCompl(ComplexRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.cfac = fac;
    }

    public Complex<C> eval(AlgebraicNumber<C> a) {
        if (a == null || a.isZERO()) {
            return this.cfac.getZERO();
        }
        if (a.isONE()) {
            return this.cfac.getONE();
        }
        C real = (GcdRingElem) this.cfac.ring.getZERO();
        C imag = (GcdRingElem) this.cfac.ring.getZERO();
        Iterator i$ = a.getVal().iterator();
        while (i$.hasNext()) {
            Monomial<C> m = (Monomial) i$.next();
            if (m.exponent().getVal(0) == 1) {
                imag = (GcdRingElem) m.coefficient();
            } else if (m.exponent().getVal(0) == 0) {
                real = (GcdRingElem) m.coefficient();
            } else {
                throw new IllegalArgumentException("unexpected monomial " + m);
            }
        }
        return new Complex(this.cfac, real, imag);
    }
}
