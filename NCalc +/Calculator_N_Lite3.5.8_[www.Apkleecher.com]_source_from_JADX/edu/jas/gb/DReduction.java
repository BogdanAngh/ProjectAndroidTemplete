package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.util.List;

public interface DReduction<C extends RingElem<C>> extends Reduction<C> {
    GenPolynomial<C> GPolynomial(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    GenPolynomial<C> GPolynomial(List<GenPolynomial<C>> list, int i, GenPolynomial<C> genPolynomial, int i2, GenPolynomial<C> genPolynomial2);
}
