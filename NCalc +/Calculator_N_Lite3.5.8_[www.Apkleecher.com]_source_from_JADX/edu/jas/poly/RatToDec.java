package edu.jas.poly;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.Rational;
import edu.jas.structure.Element;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RatToDec<C extends Element<C> & Rational> implements UnaryFunctor<C, BigDecimal> {
    RatToDec() {
    }

    public BigDecimal eval(C c) {
        if (c == null) {
            return new BigDecimal();
        }
        return new BigDecimal(((Rational) c).getRational());
    }
}
