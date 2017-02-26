package edu.jas.gbmod;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.ModuleList;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface ModSolvableGroebnerBase<C extends RingElem<C>> extends Serializable {
    boolean isLeftGB(int i, List<GenSolvablePolynomial<C>> list);

    boolean isLeftGB(ModuleList<C> moduleList);

    boolean isRightGB(int i, List<GenSolvablePolynomial<C>> list);

    boolean isRightGB(ModuleList<C> moduleList);

    boolean isTwosidedGB(int i, List<GenSolvablePolynomial<C>> list);

    boolean isTwosidedGB(ModuleList<C> moduleList);

    ModuleList<C> leftGB(ModuleList<C> moduleList);

    List<GenSolvablePolynomial<C>> leftGB(int i, List<GenSolvablePolynomial<C>> list);

    ModuleList<C> rightGB(ModuleList<C> moduleList);

    List<GenSolvablePolynomial<C>> rightGB(int i, List<GenSolvablePolynomial<C>> list);

    ModuleList<C> twosidedGB(ModuleList<C> moduleList);

    List<GenSolvablePolynomial<C>> twosidedGB(int i, List<GenSolvablePolynomial<C>> list);
}
