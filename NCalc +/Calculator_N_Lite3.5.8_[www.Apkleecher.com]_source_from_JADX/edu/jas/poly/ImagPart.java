package edu.jas.poly;

import edu.jas.arith.BigComplex;
import edu.jas.arith.BigRational;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class ImagPart implements UnaryFunctor<BigComplex, BigRational> {
    ImagPart() {
    }

    public BigRational eval(BigComplex c) {
        if (c == null) {
            return new BigRational();
        }
        return c.getIm();
    }
}
