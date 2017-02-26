package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface GroebnerBase<C extends RingElem<C>> extends Serializable {
    List<GenPolynomial<C>> GB(int i, List<GenPolynomial<C>> list);

    List<GenPolynomial<C>> GB(List<GenPolynomial<C>> list);

    ExtendedGB<C> extGB(int i, List<GenPolynomial<C>> list);

    ExtendedGB<C> extGB(List<GenPolynomial<C>> list);

    boolean isGB(int i, List<GenPolynomial<C>> list);

    boolean isGB(List<GenPolynomial<C>> list);

    boolean isReductionMatrix(ExtendedGB<C> extendedGB);

    boolean isReductionMatrix(List<GenPolynomial<C>> list, List<GenPolynomial<C>> list2, List<List<GenPolynomial<C>>> list3, List<List<GenPolynomial<C>>> list4);

    List<GenPolynomial<C>> minimalGB(List<GenPolynomial<C>> list);
}
