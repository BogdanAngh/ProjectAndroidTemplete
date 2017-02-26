package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.poly.GenWordPolynomialRing;
import edu.jas.structure.RingElem;
import java.util.List;

public interface WordPairList<C extends RingElem<C>> {
    WordPairList<C> create(GenWordPolynomialRing<C> genWordPolynomialRing);

    List<GenWordPolynomial<C>> getList();

    boolean hasNext();

    int put(GenWordPolynomial<C> genWordPolynomial);

    int put(List<GenWordPolynomial<C>> list);

    int putCount();

    int putOne();

    int remCount();

    WordPair<C> removeNext();

    String toString();
}
