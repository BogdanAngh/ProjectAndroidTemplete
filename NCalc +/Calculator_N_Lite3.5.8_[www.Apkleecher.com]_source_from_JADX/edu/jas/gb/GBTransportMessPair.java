package edu.jas.gb;

import edu.jas.structure.RingElem;

/* compiled from: GBTransportMess */
final class GBTransportMessPair<C extends RingElem<C>> extends GBTransportMess {
    public final Pair<C> pair;

    public GBTransportMessPair(Pair<C> p) {
        this.pair = p;
    }

    public String toString() {
        return super.toString() + "( " + this.pair + " )";
    }
}
