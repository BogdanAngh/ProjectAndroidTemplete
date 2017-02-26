package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OrderedModuleList<C extends RingElem<C>> extends ModuleList<C> {

    static class 1 implements Comparator<List<GenPolynomial<C>>> {
        final /* synthetic */ Comparator val$evc;

        1(Comparator comparator) {
            this.val$evc = comparator;
        }

        public int compare(List<GenPolynomial<C>> l1, List<GenPolynomial<C>> l2) {
            int c = 0;
            for (int i = 0; i < l1.size(); i++) {
                GenPolynomial<C> p2 = (GenPolynomial) l2.get(i);
                ExpVector e1 = ((GenPolynomial) l1.get(i)).leadingExpVector();
                ExpVector e2 = p2.leadingExpVector();
                if (e1 != e2) {
                    if (e1 == null && e2 != null) {
                        return -1;
                    }
                    if (e1 != null && e2 == null) {
                        return 1;
                    }
                    if (!(e1 == null || e2 == null)) {
                        if (e1.length() == e2.length()) {
                            c = this.val$evc.compare(e1, e2);
                            if (c != 0) {
                                return c;
                            }
                        } else if (e1.length() > e2.length()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            }
            return c;
        }
    }

    public OrderedModuleList(GenPolynomialRing<C> r, List<List<GenPolynomial<C>>> l) {
        super((GenPolynomialRing) r, sort(r, ModuleList.padCols(r, l)));
    }

    public boolean equals(Object m) {
        if (!super.equals(m)) {
            return false;
        }
        OrderedModuleList<C> ml = null;
        try {
            ml = (OrderedModuleList) m;
        } catch (ClassCastException e) {
        }
        if (ml != null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public static <C extends RingElem<C>> List<List<GenPolynomial<C>>> sort(GenPolynomialRing<C> r, List<List<GenPolynomial<C>>> l) {
        if (l == null || l.size() <= 1) {
            return l;
        }
        Comparator<List<GenPolynomial<C>>> cmp = new 1(r.tord.getAscendComparator());
        try {
            List<GenPolynomial<C>>[] s = new List[l.size()];
            int i = 0;
            for (List<GenPolynomial<C>> p : l) {
                int i2 = i + 1;
                s[i] = p;
                i = i2;
            }
            Arrays.sort(s, cmp);
            return new ArrayList(Arrays.asList(s));
        } catch (ClassCastException e) {
            System.out.println("Warning: polynomials not sorted");
            return l;
        }
    }
}
