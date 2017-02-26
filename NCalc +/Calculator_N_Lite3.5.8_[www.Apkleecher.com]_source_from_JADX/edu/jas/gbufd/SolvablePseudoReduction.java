package edu.jas.gbufd;

import edu.jas.gb.SolvableReduction;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.util.List;

public interface SolvablePseudoReduction<C extends RingElem<C>> extends SolvableReduction<C> {
    PseudoReductionEntry<C> leftNormalformFactor(List<GenSolvablePolynomial<C>> list, GenSolvablePolynomial<C> genSolvablePolynomial);

    GenSolvablePolynomial<GenPolynomial<C>> leftNormalformRecursive(List<GenSolvablePolynomial<GenPolynomial<C>>> list, GenSolvablePolynomial<GenPolynomial<C>> genSolvablePolynomial);
}
