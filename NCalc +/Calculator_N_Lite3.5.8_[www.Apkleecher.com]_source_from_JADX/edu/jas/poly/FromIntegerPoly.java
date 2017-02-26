package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class FromIntegerPoly<D extends RingElem<D>> implements UnaryFunctor<GenPolynomial<BigInteger>, GenPolynomial<D>> {
    FromInteger<D> fi;
    GenPolynomialRing<D> ring;

    public FromIntegerPoly(GenPolynomialRing<D> ring) {
        if (ring == null) {
            throw new IllegalArgumentException("ring must not be null");
        }
        this.ring = ring;
        this.fi = new FromInteger(ring.coFac);
    }

    public GenPolynomial<D> eval(GenPolynomial<BigInteger> c) {
        if (c == null) {
            return this.ring.getZERO();
        }
        return PolyUtil.map(this.ring, c, this.fi);
    }
}
