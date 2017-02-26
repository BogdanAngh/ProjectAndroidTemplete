package edu.jas.ps;

import edu.jas.poly.ExpVector;
import edu.jas.structure.RingElem;
import java.util.List;

public interface TaylorFunction<C extends RingElem<C>> {
    TaylorFunction<C> deriviative();

    TaylorFunction<C> deriviative(ExpVector expVector);

    C evaluate(C c);

    C evaluate(List<C> list);

    long getFacul();

    boolean isZERO();
}
