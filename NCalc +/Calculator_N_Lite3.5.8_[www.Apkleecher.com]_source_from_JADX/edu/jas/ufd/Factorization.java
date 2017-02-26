package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;
import java.util.SortedMap;

public interface Factorization<C extends GcdRingElem<C>> extends Serializable {
    SortedMap<GenPolynomial<C>, Long> factors(GenPolynomial<C> genPolynomial);

    List<GenPolynomial<C>> factorsRadical(GenPolynomial<C> genPolynomial);

    List<GenPolynomial<C>> factorsSquarefree(GenPolynomial<C> genPolynomial);

    boolean isFactorization(GenPolynomial<C> genPolynomial, List<GenPolynomial<C>> list);

    boolean isFactorization(GenPolynomial<C> genPolynomial, SortedMap<GenPolynomial<C>, Long> sortedMap);

    boolean isIrreducible(GenPolynomial<C> genPolynomial);

    boolean isReducible(GenPolynomial<C> genPolynomial);

    boolean isSquarefree(GenPolynomial<C> genPolynomial);

    SortedMap<GenPolynomial<C>, Long> squarefreeFactors(GenPolynomial<C> genPolynomial);

    GenPolynomial<C> squarefreePart(GenPolynomial<C> genPolynomial);
}
