package edu.jas.poly;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.Rational;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class CompRatToDec<C extends RingElem<C> & Rational> implements UnaryFunctor<Complex<C>, Complex<BigDecimal>> {
    ComplexRing<BigDecimal> ring;

    public CompRatToDec(RingFactory<Complex<BigDecimal>> ring) {
        this.ring = (ComplexRing) ring;
    }

    public Complex<BigDecimal> eval(Complex<C> c) {
        if (c == null) {
            return this.ring.getZERO();
        }
        return new Complex(this.ring, new BigDecimal(((Rational) c.getRe()).getRational()), new BigDecimal(((Rational) c.getIm()).getRational()));
    }
}
