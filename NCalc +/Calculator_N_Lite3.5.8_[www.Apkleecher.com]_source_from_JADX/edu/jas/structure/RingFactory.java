package edu.jas.structure;

import java.math.BigInteger;

public interface RingFactory<C extends RingElem<C>> extends AbelianGroupFactory<C>, MonoidFactory<C> {
    BigInteger characteristic();

    boolean isField();
}
