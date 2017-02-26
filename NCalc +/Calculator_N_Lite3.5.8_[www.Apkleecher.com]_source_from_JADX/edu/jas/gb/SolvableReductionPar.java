package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map.Entry;

public class SolvableReductionPar<C extends RingElem<C>> extends SolvableReductionAbstract<C> {
    public GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        synchronized (Pp) {
            int l = Pp.size();
            GenSolvablePolynomial<C>[] P = (GenSolvablePolynomial[]) new GenSolvablePolynomial[l];
            for (int j = 0; j < Pp.size(); j++) {
                P[j] = (GenSolvablePolynomial) Pp.get(j);
            }
        }
        ExpVector f = null;
        boolean mt = false;
        GenSolvablePolynomial<C> Rz = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> p = null;
        GenSolvablePolynomial<C> S = Ap.copy();
        GenSolvablePolynomial S2;
        while (S2.length() > 0) {
            int i;
            if (Pp.size() != l) {
                synchronized (Pp) {
                    l = Pp.size();
                    P = new GenSolvablePolynomial[l];
                    for (i = 0; i < Pp.size(); i++) {
                        P[i] = (GenSolvablePolynomial) Pp.get(i);
                    }
                }
                S2 = Ap.copy();
                R = Rz.copy();
            }
            Entry<ExpVector, C> m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            for (GenSolvablePolynomial<C> p2 : P) {
                f = p2.leadingExpVector();
                if (f != null) {
                    mt = e.multipleOf(f);
                    if (mt) {
                        break;
                    }
                }
            }
            if (mt) {
                GenSolvablePolynomial<C> Q = p2.multiplyLeft(e.subtract(f));
                S = S.subtractMultiple((RingElem) a.divide(Q.leadingBaseCoefficient()), Q);
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2, GenSolvablePolynomial<C> genSolvablePolynomial) {
        throw new UnsupportedOperationException("normalform with recording not implemented");
    }

    public GenSolvablePolynomial<C> rightNormalform(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        synchronized (Pp) {
            int l = Pp.size();
            GenSolvablePolynomial<C>[] P = (GenSolvablePolynomial[]) new GenSolvablePolynomial[l];
            for (int j = 0; j < Pp.size(); j++) {
                P[j] = (GenSolvablePolynomial) Pp.get(j);
            }
        }
        ExpVector f = null;
        boolean mt = false;
        GenSolvablePolynomial<C> Rz = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> p = null;
        GenSolvablePolynomial<C> S = Ap.copy();
        GenSolvablePolynomial S2;
        while (S2.length() > 0) {
            if (Pp.size() != l) {
                synchronized (Pp) {
                    l = Pp.size();
                    P = new GenSolvablePolynomial[l];
                    int i;
                    for (i = 0; i < Pp.size(); i++) {
                        P[i] = (GenSolvablePolynomial) Pp.get(i);
                    }
                }
                S2 = Ap.copy();
                R = Rz.copy();
            }
            Entry<ExpVector, C> m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            for (GenSolvablePolynomial<C> p2 : P) {
                f = p2.leadingExpVector();
                if (f != null) {
                    mt = e.multipleOf(f);
                    if (mt) {
                        break;
                    }
                }
            }
            if (mt) {
                GenSolvablePolynomial<C> Q = p2.multiply(e.subtract(f));
                S = (GenSolvablePolynomial) S.subtract((GenPolynomial) Q.multiply((RingElem) a.divide(Q.leadingBaseCoefficient())));
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenSolvablePolynomial<C> rightNormalform(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2, GenSolvablePolynomial<C> genSolvablePolynomial) {
        throw new UnsupportedOperationException("normalform with recording not implemented");
    }
}
