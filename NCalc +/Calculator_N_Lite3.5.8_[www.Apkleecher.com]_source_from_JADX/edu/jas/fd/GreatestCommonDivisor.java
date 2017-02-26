package edu.jas.fd;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;

public interface GreatestCommonDivisor<C extends GcdRingElem<C>> extends Serializable {
    boolean isLeftCoPrime(List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> leftCoPrime(List<GenSolvablePolynomial<C>> list);

    GenSolvablePolynomial<C> leftContent(GenSolvablePolynomial<C> genSolvablePolynomial);

    GenSolvablePolynomial<C> leftGcd(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    GenSolvablePolynomial<C> leftLcm(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    GenSolvablePolynomial<C> leftPrimitivePart(GenSolvablePolynomial<C> genSolvablePolynomial);

    GenSolvablePolynomial<C> rightContent(GenSolvablePolynomial<C> genSolvablePolynomial);

    GenSolvablePolynomial<C> rightGcd(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    GenSolvablePolynomial<C> rightLcm(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    GenSolvablePolynomial<C> rightPrimitivePart(GenSolvablePolynomial<C> genSolvablePolynomial);
}
