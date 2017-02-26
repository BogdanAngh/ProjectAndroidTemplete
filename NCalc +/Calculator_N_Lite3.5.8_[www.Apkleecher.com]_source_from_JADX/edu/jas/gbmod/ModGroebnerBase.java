package edu.jas.gbmod;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.ModuleList;
import edu.jas.structure.RingElem;
import java.util.List;

public interface ModGroebnerBase<C extends RingElem<C>> {
    ModuleList<C> GB(ModuleList<C> moduleList);

    List<GenPolynomial<C>> GB(int i, List<GenPolynomial<C>> list);

    boolean isGB(int i, List<GenPolynomial<C>> list);

    boolean isGB(ModuleList<C> moduleList);
}
