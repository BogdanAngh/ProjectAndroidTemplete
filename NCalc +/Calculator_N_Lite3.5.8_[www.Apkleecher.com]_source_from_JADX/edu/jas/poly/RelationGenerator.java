package edu.jas.poly;

import edu.jas.structure.RingElem;

public interface RelationGenerator<C extends RingElem<C>> {
    void generate(GenSolvablePolynomialRing<C> genSolvablePolynomialRing);
}
