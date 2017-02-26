package edu.jas.gbufd;

import edu.jas.gb.Reduction;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.util.List;

public interface PseudoReduction<C extends RingElem<C>> extends Reduction<C> {
    PseudoReductionEntry<C> normalformFactor(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);

    GenPolynomial<GenPolynomial<C>> normalformRecursive(List<GenPolynomial<GenPolynomial<C>>> list, GenPolynomial<GenPolynomial<C>> genPolynomial);
}
