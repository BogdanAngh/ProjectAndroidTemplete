package edu.jas.structure;

import java.util.List;

public interface AlgebraFactory<A extends AlgebraElem<A, C>, C extends RingElem<C>> extends RingFactory<A> {
    A fromList(List<List<C>> list);

    A random(int i, float f);
}
