package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;

public class PseudoReductionEntry<C extends RingElem<C>> {
    public final C multiplicator;
    public final GenPolynomial<C> pol;

    public PseudoReductionEntry(GenPolynomial<C> pol, C multiplicator) {
        this.pol = pol;
        this.multiplicator = multiplicator;
    }
}
