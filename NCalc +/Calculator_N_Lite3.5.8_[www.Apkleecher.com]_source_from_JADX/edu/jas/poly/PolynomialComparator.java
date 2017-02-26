package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.Comparator;

public class PolynomialComparator<C extends RingElem<C>> implements Serializable, Comparator<GenPolynomial<C>> {
    public final boolean reverse;
    public final TermOrder tord;

    public PolynomialComparator(TermOrder t, boolean reverse) {
        this.tord = t;
        this.reverse = reverse;
    }

    public int compare(GenPolynomial<C> p1, GenPolynomial<C> p2) {
        int s = p1.compareTo((GenPolynomial) p2);
        if (this.reverse) {
            return -s;
        }
        return s;
    }

    public boolean equals(Object o) {
        try {
            PolynomialComparator pc = (PolynomialComparator) o;
            if (pc == null) {
                return false;
            }
            return this.tord.equals(pc.tord);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return this.tord.hashCode();
    }

    public String toString() {
        return "PolynomialComparator(" + this.tord + ")";
    }
}
