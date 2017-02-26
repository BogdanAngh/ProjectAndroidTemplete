package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class FromInteger<D extends RingElem<D>> implements UnaryFunctor<BigInteger, D> {
    RingFactory<D> ring;

    public FromInteger(RingFactory<D> ring) {
        this.ring = ring;
    }

    public D eval(BigInteger c) {
        if (c == null) {
            return (RingElem) this.ring.getZERO();
        }
        return (RingElem) this.ring.fromInteger(c.getVal());
    }
}
