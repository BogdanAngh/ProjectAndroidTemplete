package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OrderedPolynomialList<C extends RingElem<C>> extends PolynomialList<C> {

    static class 1 implements Comparator<GenPolynomial<C>> {
        final /* synthetic */ Comparator val$evc;

        1(Comparator comparator) {
            this.val$evc = comparator;
        }

        public int compare(GenPolynomial<C> p1, GenPolynomial<C> p2) {
            ExpVector e1 = p1.leadingExpVector();
            ExpVector e2 = p2.leadingExpVector();
            if (e1 == null) {
                return -1;
            }
            if (e2 == null) {
                return 1;
            }
            if (e1.length() == e2.length()) {
                return this.val$evc.compare(e1, e2);
            }
            if (e1.length() > e2.length()) {
                return 1;
            }
            return -1;
        }
    }

    public OrderedPolynomialList(GenPolynomialRing<C> r, List<GenPolynomial<C>> l) {
        super((GenPolynomialRing) r, sort(r, l));
    }

    public boolean equals(Object p) {
        if (!super.equals(p)) {
            return false;
        }
        OrderedPolynomialList<C> pl = null;
        try {
            pl = (OrderedPolynomialList) p;
        } catch (ClassCastException e) {
        }
        if (pl != null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> sort(List<GenPolynomial<C>> L) {
        return (L != null && L.size() > 1) ? sort(((GenPolynomial) L.get(0)).ring, L) : L;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> sort(GenPolynomialRing<C> r, List<GenPolynomial<C>> L) {
        if (L == null || L.size() <= 1) {
            return L;
        }
        Comparator<GenPolynomial<C>> cmp = new 1(r.tord.getAscendComparator());
        try {
            GenPolynomial<C>[] s = new GenPolynomial[L.size()];
            int i = 0;
            for (GenPolynomial<C> p : L) {
                int i2 = i + 1;
                s[i] = p;
                i = i2;
            }
            Arrays.sort(s, cmp);
            return new ArrayList(Arrays.asList(s));
        } catch (ClassCastException e) {
            System.out.println("Warning: polynomials not sorted");
            return L;
        }
    }
}
