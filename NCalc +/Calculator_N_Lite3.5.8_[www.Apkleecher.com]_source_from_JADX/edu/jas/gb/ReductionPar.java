package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ReductionPar<C extends RingElem<C>> extends ReductionAbstract<C> {
    public GenPolynomial<C> normalform(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        synchronized (Pp) {
            int l = Pp.size();
            GenPolynomial<C>[] P = (GenPolynomial[]) new GenPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (GenPolynomial) Pp.get(i);
            }
        }
        ExpVector f = null;
        boolean mt = false;
        GenPolynomial<C> Rz = Ap.ring.getZERO();
        GenPolynomial<C> R = Rz.copy();
        GenPolynomial<C> p = null;
        GenPolynomial<C> S = Ap.copy();
        GenPolynomial S2;
        while (S2.length() > 0) {
            if (Pp.size() != l) {
                synchronized (Pp) {
                    l = Pp.size();
                    P = new GenPolynomial[l];
                    for (i = 0; i < Pp.size(); i++) {
                        P[i] = (GenPolynomial) Pp.get(i);
                    }
                }
                S2 = Ap.copy();
                R = Rz.copy();
            }
            Entry<ExpVector, C> m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            for (GenPolynomial<C> p2 : P) {
                f = p2.leadingExpVector();
                if (f != null) {
                    mt = e.multipleOf(f);
                    if (mt) {
                        break;
                    }
                }
            }
            if (mt) {
                S = S.subtractMultiple((RingElem) a.divide((MonoidElem) p2.leadingMonomial().getValue()), e.subtract(f), p2);
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> list, List<GenPolynomial<C>> list2, GenPolynomial<C> genPolynomial) {
        throw new RuntimeException("normalform with recording not implemented");
    }

    public GenPolynomial<C> normalform(Map<Integer, GenPolynomial<C>> mp, GenPolynomial<C> Ap) {
        if (mp == null || mp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        Object[] P = (GenPolynomial[]) mp.values().toArray((GenPolynomial[]) new GenPolynomial[mp.size()]);
        int l = P.length;
        ExpVector f = null;
        boolean mt = false;
        GenPolynomial<C> Rz = Ap.ring.getZERO();
        GenPolynomial<C> R = Rz.copy();
        GenPolynomial<C> p = null;
        GenPolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            if (mp.size() != l) {
                P = (GenPolynomial[]) mp.values().toArray(P);
                l = P.length;
                S = Ap.copy();
                R = Rz.copy();
            }
            Entry<ExpVector, C> m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            for (GenPolynomial<C> p2 : P) {
                f = p2.leadingExpVector();
                if (f != null) {
                    mt = e.multipleOf(f);
                    if (mt) {
                        break;
                    }
                }
            }
            if (mt) {
                S = S.subtractMultiple((RingElem) a.divide((MonoidElem) p2.leadingMonomial().getValue()), e.subtract(f), p2);
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }
}
