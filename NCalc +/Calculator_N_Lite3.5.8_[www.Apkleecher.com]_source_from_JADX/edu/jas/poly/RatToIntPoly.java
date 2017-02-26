package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class RatToIntPoly implements UnaryFunctor<GenPolynomial<BigRational>, GenPolynomial<BigInteger>> {
    GenPolynomialRing<BigInteger> ring;

    public RatToIntPoly(GenPolynomialRing<BigInteger> ring) {
        if (ring == null) {
            throw new IllegalArgumentException("ring must not be null");
        }
        this.ring = ring;
    }

    public GenPolynomial<BigInteger> eval(GenPolynomial<BigRational> c) {
        if (c == null) {
            return this.ring.getZERO();
        }
        return PolyUtil.integerFromRationalCoefficients(this.ring, (GenPolynomial) c);
    }
}
