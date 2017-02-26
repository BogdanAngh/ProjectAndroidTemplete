package edu.jas.structure;

public interface QuotPair<C extends RingElem<C>> {
    C denominator();

    boolean isConstant();

    C numerator();
}
