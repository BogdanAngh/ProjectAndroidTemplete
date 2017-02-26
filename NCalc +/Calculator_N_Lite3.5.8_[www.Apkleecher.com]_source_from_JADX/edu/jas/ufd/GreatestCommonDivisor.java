package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;

public interface GreatestCommonDivisor<C extends GcdRingElem<C>> extends Serializable {
    List<GenPolynomial<C>> coPrime(List<GenPolynomial<C>> list);

    GenPolynomial<C> content(GenPolynomial<C> genPolynomial);

    GenPolynomial<C> gcd(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    boolean isCoPrime(List<GenPolynomial<C>> list);

    GenPolynomial<C> lcm(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    GenPolynomial<C> primitivePart(GenPolynomial<C> genPolynomial);

    GenPolynomial<C> resultant(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);
}
