package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.arith.Modular;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class ModToInt<C extends RingElem<C> & Modular> implements UnaryFunctor<C, BigInteger> {
    ModToInt() {
    }

    public BigInteger eval(C c) {
        if (c == null) {
            return new BigInteger();
        }
        return ((Modular) c).getInteger();
    }
}
