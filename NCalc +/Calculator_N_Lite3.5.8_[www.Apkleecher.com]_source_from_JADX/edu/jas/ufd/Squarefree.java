package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;
import java.util.SortedMap;

public interface Squarefree<C extends GcdRingElem<C>> extends Serializable {
    List<GenPolynomial<C>> coPrimeSquarefree(GenPolynomial<C> genPolynomial, List<GenPolynomial<C>> list);

    List<GenPolynomial<C>> coPrimeSquarefree(List<GenPolynomial<C>> list);

    boolean isCoPrimeSquarefree(List<GenPolynomial<C>> list);

    boolean isFactorization(GenPolynomial<C> genPolynomial, List<GenPolynomial<C>> list);

    boolean isFactorization(GenPolynomial<C> genPolynomial, SortedMap<GenPolynomial<C>, Long> sortedMap);

    boolean isSquarefree(GenPolynomial<C> genPolynomial);

    boolean isSquarefree(List<GenPolynomial<C>> list);

    SortedMap<GenPolynomial<C>, Long> squarefreeFactors(GenPolynomial<C> genPolynomial);

    GenPolynomial<C> squarefreePart(GenPolynomial<C> genPolynomial);
}
