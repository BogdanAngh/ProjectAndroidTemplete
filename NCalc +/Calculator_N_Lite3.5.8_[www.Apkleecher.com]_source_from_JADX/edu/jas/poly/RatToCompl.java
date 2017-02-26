package edu.jas.poly;

import edu.jas.arith.BigComplex;
import edu.jas.arith.BigRational;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RatToCompl implements UnaryFunctor<BigRational, BigComplex> {
    RatToCompl() {
    }

    public BigComplex eval(BigRational c) {
        if (c == null) {
            return new BigComplex();
        }
        return new BigComplex(c);
    }
}
