package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface PairList<C extends RingElem<C>> extends Serializable {
    PairList<C> create(int i, GenPolynomialRing<C> genPolynomialRing);

    PairList<C> create(GenPolynomialRing<C> genPolynomialRing);

    boolean criterion3(int i, int i2, ExpVector expVector);

    List<GenPolynomial<C>> getList();

    boolean hasNext();

    int put(GenPolynomial<C> genPolynomial);

    int put(List<GenPolynomial<C>> list);

    int putCount();

    int putOne();

    int remCount();

    Pair<C> removeNext();

    int size();

    String toString();
}
