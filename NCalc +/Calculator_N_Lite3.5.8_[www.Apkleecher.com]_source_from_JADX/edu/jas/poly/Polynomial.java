package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public interface Polynomial<C extends RingElem<C>> extends RingElem<Polynomial<C>> {
    Iterator<C> coefficientIterator();

    Map<ExpVector, Polynomial<C>> contract(PolynomialRing<C> polynomialRing);

    Iterator<ExpVector> exponentIterator();

    Polynomial<C> extend(PolynomialRing<C> polynomialRing, int i, long j);

    C leadingBaseCoefficient();

    ExpVector leadingExpVector();

    Entry<ExpVector, C> leadingMonomial();

    Iterator<Monomial<C>> monomialIterator();

    Polynomial<C> reductum();

    Polynomial<C> reverse(PolynomialRing<C> polynomialRing);

    C trailingBaseCoefficient();
}
