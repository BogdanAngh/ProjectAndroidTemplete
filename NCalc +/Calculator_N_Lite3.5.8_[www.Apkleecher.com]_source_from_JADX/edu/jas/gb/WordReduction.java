package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.poly.Word;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface WordReduction<C extends RingElem<C>> extends Serializable {
    GenWordPolynomial<C> SPolynomial(C c, Word word, GenWordPolynomial<C> genWordPolynomial, Word word2, C c2, Word word3, GenWordPolynomial<C> genWordPolynomial2, Word word4);

    List<GenWordPolynomial<C>> SPolynomials(GenWordPolynomial<C> genWordPolynomial, GenWordPolynomial<C> genWordPolynomial2);

    List<GenWordPolynomial<C>> irreducibleSet(List<GenWordPolynomial<C>> list);

    boolean isNormalform(List<GenWordPolynomial<C>> list);

    boolean isNormalform(List<GenWordPolynomial<C>> list, GenWordPolynomial<C> genWordPolynomial);

    boolean isReducible(List<GenWordPolynomial<C>> list, GenWordPolynomial<C> genWordPolynomial);

    boolean isReductionNF(List<GenWordPolynomial<C>> list, List<GenWordPolynomial<C>> list2, List<GenWordPolynomial<C>> list3, GenWordPolynomial<C> genWordPolynomial, GenWordPolynomial<C> genWordPolynomial2);

    boolean isTopReducible(List<GenWordPolynomial<C>> list, GenWordPolynomial<C> genWordPolynomial);

    GenWordPolynomial<C> normalform(List<GenWordPolynomial<C>> list, GenWordPolynomial<C> genWordPolynomial);

    GenWordPolynomial<C> normalform(List<GenWordPolynomial<C>> list, List<GenWordPolynomial<C>> list2, List<GenWordPolynomial<C>> list3, GenWordPolynomial<C> genWordPolynomial);

    List<GenWordPolynomial<C>> normalform(List<GenWordPolynomial<C>> list, List<GenWordPolynomial<C>> list2);
}
