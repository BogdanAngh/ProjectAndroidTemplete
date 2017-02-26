package edu.jas.gbufd;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RegularRingElem;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class RPseudoReductionSeq<C extends RegularRingElem<C>> extends RReductionSeq<C> implements RPseudoReduction<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(RPseudoReductionSeq.class);
    }

    public RPseudoReductionSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int l;
        int i;
        Entry<ExpVector, C> m;
        synchronized (Pp) {
            l = Pp.size();
            GenPolynomial[] P = (GenPolynomial[]) new GenPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (GenPolynomial) Pp.get(i);
            }
        }
        ExpVector[] htl = new ExpVector[l];
        RegularRingElem[] lbc = (RegularRingElem[]) new RegularRingElem[l];
        GenPolynomial[] p = (GenPolynomial[]) new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            if (P[i] != null) {
                p[i] = P[i].abs();
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (RegularRingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        GenPolynomial<C> R = Ap.ring.getZERO();
        GenPolynomial<C> S = Ap;
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (RegularRingElem) m.getValue();
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    C c = lbc[i];
                    if (!a.idempotentAnd(c).isZERO()) {
                        ExpVector f = e.subtract(htl[i]);
                        if (((RegularRingElem) a.remainder(c)).isZERO()) {
                            a = (RegularRingElem) a.divide(c);
                            if (a.isZERO()) {
                                throw new ArithmeticException("a.isZERO()");
                            }
                        }
                        RingElem c2 = c.fillOne();
                        S = S.multiply(c2);
                        R = R.multiply(c2);
                        S = S.subtract(p[i].multiply(a, f));
                        if (!e.equals(S.leadingExpVector())) {
                            a = (RegularRingElem) Ap.ring.coFac.getZERO();
                            break;
                        }
                        a = (RegularRingElem) S.leadingBaseCoefficient();
                    } else {
                        continue;
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

    public PseudoReductionEntry<C> normalformFactor(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Ap == null) {
            return null;
        }
        C mfac = (RegularRingElem) Ap.ring.getONECoefficient();
        PseudoReductionEntry<C> pseudoReductionEntry = new PseudoReductionEntry(Ap, mfac);
        if (Pp == null || Pp.isEmpty() || Ap.isZERO()) {
            return pseudoReductionEntry;
        }
        int l;
        int i;
        Entry<ExpVector, C> m;
        synchronized (Pp) {
            l = Pp.size();
            GenPolynomial[] P = (GenPolynomial[]) new GenPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (GenPolynomial) Pp.get(i);
            }
        }
        ExpVector[] htl = new ExpVector[l];
        RegularRingElem[] lbc = (RegularRingElem[]) new RegularRingElem[l];
        GenPolynomial[] p = (GenPolynomial[]) new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            if (P[i] != null) {
                p[i] = P[i].abs();
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (RegularRingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        GenPolynomial<C> R = Ap.ring.getZERO();
        GenPolynomial<C> S = Ap;
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (RegularRingElem) m.getValue();
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    C c = lbc[i];
                    if (!a.idempotentAnd(c).isZERO()) {
                        ExpVector f = e.subtract(htl[i]);
                        if (((RegularRingElem) a.remainder(c)).isZERO()) {
                            a = (RegularRingElem) a.divide(c);
                            if (a.isZERO()) {
                                throw new ArithmeticException("a.isZERO()");
                            }
                        }
                        RingElem c2 = c.fillOne();
                        S = S.multiply(c2);
                        R = R.multiply(c2);
                        mfac = (RegularRingElem) mfac.multiply(c2);
                        S = S.subtract(p[i].multiply(a, f));
                        if (!e.equals(S.leadingExpVector())) {
                            a = (RegularRingElem) Ap.ring.coFac.getZERO();
                            break;
                        }
                        a = (RegularRingElem) S.leadingBaseCoefficient();
                    } else {
                        continue;
                    }
                }
            }
            if (!a.isZERO()) {
                R = R.sum(a, e);
                S = S.reductum();
            }
        }
        return new PseudoReductionEntry(R, mfac);
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> row, List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int l;
        int i;
        synchronized (Pp) {
            l = Pp.size();
            GenPolynomial[] P = (GenPolynomial[]) new GenPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (GenPolynomial) Pp.get(i);
            }
        }
        ExpVector[] htl = new ExpVector[l];
        RegularRingElem[] lbc = (RegularRingElem[]) new RegularRingElem[l];
        GenPolynomial[] p = (GenPolynomial[]) new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                lbc[j] = (RegularRingElem) m.getValue();
                j++;
            }
        }
        l = j;
        GenPolynomial<C> zero = Ap.ring.getZERO();
        GenPolynomial<C> R = Ap.ring.getZERO();
        GenPolynomial<C> S = Ap;
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (RegularRingElem) m.getValue();
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    C c = lbc[i];
                    if (!a.idempotentAnd(c).isZERO()) {
                        if (((RegularRingElem) a.remainder(c)).isZERO()) {
                            a = (RegularRingElem) a.divide(c);
                            if (a.isZERO()) {
                                throw new ArithmeticException("a.isZERO()");
                            }
                        }
                        RingElem c2 = c.fillOne();
                        S = S.multiply(c2);
                        R = R.multiply(c2);
                        ExpVector f = e.subtract(htl[i]);
                        if (this.debug) {
                            logger.info("red div = " + f);
                        }
                        S = S.subtract(p[i].multiply(a, f));
                        GenPolynomial<C> fac = (GenPolynomial) row.get(i);
                        if (fac == null) {
                            fac = zero.sum(a, f);
                        } else {
                            fac = fac.sum(a, f);
                        }
                        row.set(i, fac);
                        if (!e.equals(S.leadingExpVector())) {
                            a = (RegularRingElem) Ap.ring.coFac.getZERO();
                            break;
                        }
                        a = (RegularRingElem) S.leadingBaseCoefficient();
                    } else {
                        continue;
                    }
                }
            }
            if (!a.isZERO()) {
                R = R.sum(a, e);
                S = S.reductum();
            }
        }
        return R;
    }

    public GenPolynomial<GenPolynomial<C>> normalformRecursive(List<GenPolynomial<GenPolynomial<C>>> list, GenPolynomial<GenPolynomial<C>> genPolynomial) {
        throw new UnsupportedOperationException("not implemented");
    }
}
