package edu.jas.gb;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface SolvableGroebnerBase<C extends RingElem<C>> extends Serializable {
    SolvableExtendedGB<C> extLeftGB(int i, List<GenSolvablePolynomial<C>> list);

    SolvableExtendedGB<C> extLeftGB(List<GenSolvablePolynomial<C>> list);

    boolean isLeftGB(int i, List<GenSolvablePolynomial<C>> list);

    boolean isLeftGB(List<GenSolvablePolynomial<C>> list);

    boolean isLeftReductionMatrix(SolvableExtendedGB<C> solvableExtendedGB);

    boolean isLeftReductionMatrix(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2, List<List<GenSolvablePolynomial<C>>> list3, List<List<GenSolvablePolynomial<C>>> list4);

    boolean isRightGB(int i, List<GenSolvablePolynomial<C>> list);

    boolean isRightGB(List<GenSolvablePolynomial<C>> list);

    boolean isTwosidedGB(int i, List<GenSolvablePolynomial<C>> list);

    boolean isTwosidedGB(List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> leftGB(int i, List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> leftGB(List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> leftMinimalGB(List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> rightGB(int i, List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> rightGB(List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> twosidedGB(int i, List<GenSolvablePolynomial<C>> list);

    List<GenSolvablePolynomial<C>> twosidedGB(List<GenSolvablePolynomial<C>> list);
}
