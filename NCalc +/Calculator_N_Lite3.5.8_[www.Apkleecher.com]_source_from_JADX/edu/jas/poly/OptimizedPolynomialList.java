package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.List;

public class OptimizedPolynomialList<C extends RingElem<C>> extends PolynomialList<C> {
    public final List<Integer> perm;

    public OptimizedPolynomialList(List<Integer> P, GenPolynomialRing<C> R, List<GenPolynomial<C>> L) {
        super((GenPolynomialRing) R, (List) L);
        this.perm = P;
    }

    public String toString() {
        return "permutation = " + this.perm + "\n" + super.toString();
    }

    public boolean equals(Object B) {
        if (B instanceof OptimizedPolynomialList) {
            return super.equals(B);
        }
        return false;
    }
}
