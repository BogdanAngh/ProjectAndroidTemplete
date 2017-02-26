package edu.jas.gbufd;

import edu.jas.gb.Reduction;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RegularRingElem;
import java.util.List;

public interface RReduction<C extends RegularRingElem<C>> extends Reduction<C> {
    GenPolynomial<C> booleanClosure(GenPolynomial<C> genPolynomial);

    GenPolynomial<C> booleanRemainder(GenPolynomial<C> genPolynomial);

    boolean isBooleanClosed(GenPolynomial<C> genPolynomial);

    boolean isBooleanClosed(List<GenPolynomial<C>> list);

    boolean isStrongTopReducible(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);

    List<GenPolynomial<C>> reducedBooleanClosure(List<GenPolynomial<C>> list);

    List<GenPolynomial<C>> reducedBooleanClosure(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);
}
