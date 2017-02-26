package edu.jas.gb;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface SolvableReduction<C extends RingElem<C>> extends Serializable {
    boolean isLeftReductionNF(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2, GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    boolean isNormalform(List<GenSolvablePolynomial<C>> list, GenSolvablePolynomial<C> genSolvablePolynomial);

    boolean isReducible(List<GenSolvablePolynomial<C>> list, GenSolvablePolynomial<C> genSolvablePolynomial);

    boolean isTopReducible(List<GenSolvablePolynomial<C>> list, GenSolvablePolynomial<C> genSolvablePolynomial);

    List<GenSolvablePolynomial<C>> leftIrreducibleSet(List<GenSolvablePolynomial<C>> list);

    GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> list, GenSolvablePolynomial<C> genSolvablePolynomial);

    GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2, GenSolvablePolynomial<C> genSolvablePolynomial);

    List<GenSolvablePolynomial<C>> leftNormalform(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2);

    GenSolvablePolynomial<C> leftSPolynomial(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    GenSolvablePolynomial<C> leftSPolynomial(List<GenSolvablePolynomial<C>> list, int i, GenSolvablePolynomial<C> genSolvablePolynomial, int i2, GenSolvablePolynomial<C> genSolvablePolynomial2);

    GenSolvablePolynomial<C> rightNormalform(List<GenSolvablePolynomial<C>> list, GenSolvablePolynomial<C> genSolvablePolynomial);

    GenSolvablePolynomial<C> rightNormalform(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2, GenSolvablePolynomial<C> genSolvablePolynomial);

    GenSolvablePolynomial<C> rightSPolynomial(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);
}
