package edu.jas.structure;

import java.util.List;

public interface ModulFactory<M extends ModulElem<M, C>, C extends RingElem<C>> extends AbelianGroupFactory<M> {
    M fromList(List<C> list);

    M random(int i, float f);
}
