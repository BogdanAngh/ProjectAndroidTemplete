package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RatToInt implements UnaryFunctor<BigRational, BigInteger> {
    java.math.BigInteger lcm;

    public RatToInt(java.math.BigInteger lcm) {
        this.lcm = lcm;
    }

    public BigInteger eval(BigRational c) {
        if (c == null) {
            return new BigInteger();
        }
        return new BigInteger(c.numerator().multiply(this.lcm.divide(c.denominator())));
    }
}
