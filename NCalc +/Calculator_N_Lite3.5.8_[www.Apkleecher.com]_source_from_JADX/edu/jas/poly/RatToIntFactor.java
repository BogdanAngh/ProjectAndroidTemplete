package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RatToIntFactor implements UnaryFunctor<BigRational, BigInteger> {
    final java.math.BigInteger gcd;
    final java.math.BigInteger lcm;

    public RatToIntFactor(java.math.BigInteger gcd, java.math.BigInteger lcm) {
        this.gcd = gcd;
        this.lcm = lcm;
    }

    public BigInteger eval(BigRational c) {
        if (c == null) {
            return new BigInteger();
        }
        if (!this.gcd.equals(java.math.BigInteger.ONE)) {
            return new BigInteger(c.numerator().divide(this.gcd).multiply(this.lcm.divide(c.denominator())));
        }
        return new BigInteger(c.numerator().multiply(this.lcm.divide(c.denominator())));
    }
}
