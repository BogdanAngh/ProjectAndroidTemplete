package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.List;

public class OptimizedModuleList<C extends RingElem<C>> extends ModuleList<C> {
    public final List<Integer> perm;

    public OptimizedModuleList(List<Integer> P, GenPolynomialRing<C> R, List<List<GenPolynomial<C>>> L) {
        super((GenPolynomialRing) R, (List) L);
        this.perm = P;
    }

    public String toString() {
        return "permutation = " + this.perm + "\n" + super.toString();
    }

    public boolean equals(Object B) {
        if (B instanceof OptimizedModuleList) {
            return super.equals(B);
        }
        return false;
    }
}
