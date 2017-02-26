package edu.jas.gb;

import edu.jas.poly.TermOrder;
import edu.jas.poly.TermOrder.EVComparator;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.Comparator;

public class CriticalPairComparator<C extends RingElem<C>> implements Serializable, Comparator<AbstractPair<C>> {
    protected final EVComparator ec;
    public final TermOrder tord;

    public CriticalPairComparator(TermOrder t) {
        this.tord = t;
        this.ec = this.tord.getAscendComparator();
    }

    public int compare(AbstractPair<C> p1, AbstractPair<C> p2) {
        int s = this.ec.compare(p1.e, p2.e);
        if (s != 0) {
            return s;
        }
        if (p1.j > p2.j) {
            return -1;
        }
        if (p1.j < p2.j) {
            return 1;
        }
        if (p1.i > p2.i) {
            return -1;
        }
        if (p1.i < p2.i) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return "CriticalPairComparator(" + this.tord + ")";
    }
}
