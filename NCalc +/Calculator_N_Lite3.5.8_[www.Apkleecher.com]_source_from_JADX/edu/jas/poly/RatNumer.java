package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RatNumer implements UnaryFunctor<BigRational, BigInteger> {
    RatNumer() {
    }

    public BigInteger eval(BigRational c) {
        if (c == null) {
            return new BigInteger();
        }
        return new BigInteger(c.numerator());
    }
}
