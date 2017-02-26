package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface WordGroebnerBase<C extends RingElem<C>> extends Serializable {
    List<GenWordPolynomial<C>> GB(List<GenWordPolynomial<C>> list);

    boolean isGB(List<GenWordPolynomial<C>> list);

    List<GenWordPolynomial<C>> minimalGB(List<GenWordPolynomial<C>> list);
}
