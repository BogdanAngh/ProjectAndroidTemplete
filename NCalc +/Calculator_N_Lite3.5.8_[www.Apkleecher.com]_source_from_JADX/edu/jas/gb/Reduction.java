package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface Reduction<C extends RingElem<C>> extends Serializable {
    GenPolynomial<C> SPolynomial(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    GenPolynomial<C> SPolynomial(List<GenPolynomial<C>> list, int i, GenPolynomial<C> genPolynomial, int i2, GenPolynomial<C> genPolynomial2);

    boolean criterion4(ExpVector expVector, ExpVector expVector2, ExpVector expVector3);

    boolean criterion4(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    boolean criterion4(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2, ExpVector expVector);

    List<GenPolynomial<C>> irreducibleSet(List<GenPolynomial<C>> list);

    boolean isNormalform(List<GenPolynomial<C>> list);

    boolean isNormalform(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);

    boolean isReducible(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);

    boolean isReductionNF(List<GenPolynomial<C>> list, List<GenPolynomial<C>> list2, GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    boolean isTopReducible(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);

    boolean moduleCriterion(int i, ExpVector expVector, ExpVector expVector2);

    boolean moduleCriterion(int i, GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    GenPolynomial<C> normalform(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);

    GenPolynomial<C> normalform(List<GenPolynomial<C>> list, List<GenPolynomial<C>> list2, GenPolynomial<C> genPolynomial);

    List<GenPolynomial<C>> normalform(List<GenPolynomial<C>> list, List<GenPolynomial<C>> list2);
}
