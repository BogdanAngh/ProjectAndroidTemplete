package edu.jas.structure;

import java.util.List;

public interface ModulElem<M extends ModulElem<M, C>, C extends RingElem<C>> extends AbelianGroupElem<M> {
    M linearCombination(M m, C c);

    M linearCombination(C c, M m, C c2);

    M scalarMultiply(C c);

    M scalarProduct(List<M> list);

    C scalarProduct(M m);
}
