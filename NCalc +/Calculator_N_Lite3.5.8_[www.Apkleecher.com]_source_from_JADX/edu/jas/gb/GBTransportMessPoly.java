package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;

/* compiled from: GBTransportMess */
final class GBTransportMessPoly<C extends RingElem<C>> extends GBTransportMess {
    public final GenPolynomial<C> pol;

    public GBTransportMessPoly(GenPolynomial<C> p) {
        this.pol = p;
    }

    public String toString() {
        return super.toString() + "( " + this.pol + " )";
    }
}
