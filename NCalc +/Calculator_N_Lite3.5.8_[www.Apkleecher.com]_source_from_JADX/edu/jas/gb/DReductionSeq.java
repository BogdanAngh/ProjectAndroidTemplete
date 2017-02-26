package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class DReductionSeq<C extends RingElem<C>> extends ReductionAbstract<C> implements DReduction<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(DReductionSeq.class);
    }

    public boolean isTopReducible(List<GenPolynomial<C>> P, GenPolynomial<C> A) {
        if (P == null || P.isEmpty() || A == null || A.isZERO()) {
            return false;
        }
        ExpVector e = A.leadingExpVector();
        C a = A.leadingBaseCoefficient();
        for (GenPolynomial<C> p : P) {
            if (e.multipleOf(p.leadingExpVector()) && ((RingElem) a.remainder(p.leadingBaseCoefficient())).isZERO()) {
                return true;
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
                    if (((RingElem) a.remainder(lbc[i])).isZERO()) {
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
        C r = null;
        boolean mt = false;
        GenPolynomial<C> R = Ap.ring.getZERO();
        GenPolynomial<C> S = Ap;
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    r = (RingElem) a.remainder(lbc[i]);
                    mt = r.isZERO();
                }
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                ExpVector f = e.subtract(htl[i]);
                RingElem b = (RingElem) a.divide(lbc[i]);
                R = R.sum(r, e);
                S = S.reductum().subtract(p[i].multiply(b, f).reductum());
            } else {
                R = R.sum(a, e);
                S = S.reductum();
            }
        }
        return R.abs();
    }

    public GenPolynomial<C> SPolynomial(GenPolynomial<C> Ap, GenPolynomial<C> Bp) {
        if (logger.isInfoEnabled()) {
            if (Bp == null || Bp.isZERO()) {
                return Ap.ring.getZERO();
            } else if (Ap == null || Ap.isZERO()) {
                return Bp.ring.getZERO();
            } else {
                if (!Ap.ring.equals(Bp.ring)) {
                    logger.error("rings not equal");
                }
            }
        }
        Entry<ExpVector, C> ma = Ap.leadingMonomial();
        Entry<ExpVector, C> mb = Bp.leadingMonomial();
        ExpVector e = (ExpVector) ma.getKey();
        ExpVector f = (ExpVector) mb.getKey();
        ExpVector g = e.lcm(f);
        RingElem a = (RingElem) ma.getValue();
        RingElem b = (RingElem) mb.getValue();
        RingElem l = (RingElem) ((RingElem) a.multiply(b)).divide(a.gcd(b));
        return Ap.multiply((RingElem) l.divide(a), g.subtract(e)).subtract(Bp.multiply((RingElem) l.divide(b), g.subtract(f)));
    }

    public GenPolynomial<C> GPolynomial(GenPolynomial<C> Ap, GenPolynomial<C> Bp) {
        if (logger.isInfoEnabled()) {
            if (Bp == null || Bp.isZERO()) {
                return Ap.ring.getZERO();
            }
            if (Ap == null || Ap.isZERO()) {
                return Bp.ring.getZERO();
            }
            if (!Ap.ring.equals(Bp.ring)) {
                logger.error("rings not equal");
            }
        }
        Entry<ExpVector, C> ma = Ap.leadingMonomial();
        Entry<ExpVector, C> mb = Bp.leadingMonomial();
        ExpVector e = (ExpVector) ma.getKey();
        ExpVector f = (ExpVector) mb.getKey();
        ExpVector g = e.lcm(f);
        ExpVector e1 = g.subtract(e);
        ExpVector f1 = g.subtract(f);
        C[] c = ((RingElem) ma.getValue()).egcd((RingElem) mb.getValue());
        return Ap.multiply(c[1], e1).sum(Bp.multiply(c[2], f1));
    }

    public GenPolynomial<C> GPolynomial(List<GenPolynomial<C>> list, int i, GenPolynomial<C> genPolynomial, int j, GenPolynomial<C> genPolynomial2) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public boolean criterion4(GenPolynomial<C> A, GenPolynomial<C> B, ExpVector e) {
        if (logger.isInfoEnabled()) {
            if (!A.ring.equals(B.ring)) {
                logger.error("rings equal");
            }
            if ((A instanceof GenSolvablePolynomial) || (B instanceof GenSolvablePolynomial)) {
                logger.error("GBCriterion4 not applicabable to SolvablePolynomials");
                return true;
            }
        }
        if (A.leadingExpVector().sum(B.leadingExpVector()).subtract(e).signum() == 0 && A.leadingBaseCoefficient().gcd(B.leadingBaseCoefficient()).isONE()) {
            return false;
        }
        return true;
    }

    public boolean criterion4(GenPolynomial<C> A, GenPolynomial<C> B) {
        if (logger.isInfoEnabled() && ((A instanceof GenSolvablePolynomial) || (B instanceof GenSolvablePolynomial))) {
            logger.error("GBCriterion4 not applicabable to SolvablePolynomials");
            return true;
        }
        ExpVector ei = A.leadingExpVector();
        ExpVector ej = B.leadingExpVector();
        if (ei.sum(ej).subtract(ei.lcm(ej)).signum() == 0 && A.leadingBaseCoefficient().gcd(B.leadingBaseCoefficient()).isONE()) {
            return false;
        }
        return true;
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> list, List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        throw new UnsupportedOperationException("not yet implemented");
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
            a2 = normalform(P, a2);
            logger.debug(String.valueOf(irr));
            if (a2.isZERO()) {
                l--;
                if (l <= 1) {
                    return P;
                }
            } else {
                if (e.equals(a2.leadingExpVector())) {
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
