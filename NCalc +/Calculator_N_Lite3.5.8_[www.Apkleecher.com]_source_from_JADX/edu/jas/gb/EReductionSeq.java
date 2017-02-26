package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class EReductionSeq<C extends RingElem<C>> extends DReductionSeq<C> implements EReduction<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(DReductionSeq.class);
    }

    public boolean isTopReducible(List<GenPolynomial<C>> P, GenPolynomial<C> A) {
        if (P == null || P.isEmpty()) {
            return false;
        }
        if (A == null || A.isZERO()) {
            return false;
        }
        ExpVector e = A.leadingExpVector();
        C a = A.leadingBaseCoefficient();
        for (GenPolynomial<C> p : P) {
            if (e.multipleOf(p.leadingExpVector())) {
                boolean mt;
                if (((RingElem) a.remainder(p.leadingBaseCoefficient())).equals(a)) {
                    mt = false;
                } else {
                    mt = true;
                }
                if (mt) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNormalform(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty()) {
            return true;
        }
        if (Ap == null || Ap.isZERO()) {
            return true;
        }
        int l;
        int i;
        synchronized (Pp) {
            l = Pp.size();
            GenPolynomial<C>[] P = new GenPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (GenPolynomial) Pp.get(i);
            }
        }
        ExpVector[] htl = new ExpVector[l];
        RingElem[] lbc = (RingElem[]) new RingElem[l];
        GenPolynomial<C>[] p = new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                lbc[j] = (RingElem) m.getValue();
                j++;
            }
        }
        l = j;
        for (Entry<ExpVector, C> me : Ap.getMap().entrySet()) {
            ExpVector e = (ExpVector) me.getKey();
            RingElem a = (RingElem) me.getValue();
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    if (!((RingElem) a.remainder(lbc[i])).equals(a)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int l;
        int i;
        synchronized (Pp) {
            l = Pp.size();
            GenPolynomial[] P = (GenPolynomial[]) new GenPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = ((GenPolynomial) Pp.get(i)).abs();
            }
        }
        ExpVector[] htl = new ExpVector[l];
        RingElem[] lbc = (RingElem[]) new RingElem[l];
        GenPolynomial[] p = (GenPolynomial[]) new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                lbc[j] = (RingElem) m.getValue();
                j++;
            }
        }
        l = j;
        GenPolynomial<C> R = Ap.ring.getZERO();
        GenPolynomial<C> S = Ap;
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (RingElem) m.getValue();
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    GenPolynomial Q;
                    ExpVector f = e.subtract(htl[i]);
                    C r = (RingElem) a.remainder(lbc[i]);
                    RingElem b = (RingElem) a.divide(lbc[i]);
                    if (f == null) {
                        System.out.println("f = null: " + e + ", " + htl[i]);
                        Q = p[i].multiply(b);
                    } else {
                        Q = p[i].multiply(b, f);
                    }
                    S = S.subtract(Q);
                    a = r;
                    if (r.isZERO()) {
                        break;
                    }
                }
            }
            if (!a.isZERO()) {
                R = R.sum(a, e);
                S = S.reductum();
            }
        }
        return R.abs();
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> list, List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        throw new UnsupportedOperationException("not jet implemented");
    }

    public List<GenPolynomial<C>> irreducibleSet(List<GenPolynomial<C>> Pp) {
        ArrayList<GenPolynomial<C>> P = new ArrayList();
        if (Pp == null) {
            return null;
        }
        for (GenPolynomial<C> a : Pp) {
            GenPolynomial<C> a2;
            if (!a2.isZERO()) {
                P.add(a2);
            }
        }
        int l = P.size();
        if (l <= 1) {
            return P;
        }
        int irr = 0;
        logger.debug("irr = ");
        while (irr != l) {
            a2 = (GenPolynomial) P.remove(0);
            ExpVector e = a2.leadingExpVector();
            C c = a2.leadingBaseCoefficient();
            a2 = normalform(P, a2);
            logger.debug(String.valueOf(irr));
            if (a2.isZERO()) {
                l--;
                if (l <= 1) {
                    return P;
                }
            } else {
                ExpVector f = a2.leadingExpVector();
                C d = a2.leadingBaseCoefficient();
                if (e.equals(f) && c.equals(d)) {
                    irr++;
                } else {
                    irr = 0;
                }
                P.add(a2);
            }
        }
        return P;
    }
}
