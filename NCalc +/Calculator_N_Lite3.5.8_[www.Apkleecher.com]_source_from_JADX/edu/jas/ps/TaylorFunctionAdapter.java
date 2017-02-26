package edu.jas.ps;

import edu.jas.poly.ExpVector;
import edu.jas.structure.RingElem;
import java.util.List;

public abstract class TaylorFunctionAdapter<C extends RingElem<C>> implements TaylorFunction<C> {
    public long getFacul() {
        return 1;
    }

    public boolean isZERO() {
        throw new UnsupportedOperationException("not implemented");
    }

    public TaylorFunction<C> deriviative() {
        throw new UnsupportedOperationException("not implemented");
    }

    public TaylorFunction<C> deriviative(ExpVector i) {
        throw new UnsupportedOperationException("not implemented");
    }

    public C evaluate(C c) {
        throw new UnsupportedOperationException("not implemented");
    }

    public C evaluate(List<C> list) {
        throw new UnsupportedOperationException("not implemented");
    }
}
