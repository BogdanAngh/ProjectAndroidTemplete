package edu.jas.arith;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;

public interface ModularRingFactory<C extends RingElem<C> & Modular> extends RingFactory<C> {
    C chineseRemainder(C c, C c2, C c3);

    BigInteger getIntegerModul();
}
