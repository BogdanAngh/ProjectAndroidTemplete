package edu.jas.gbufd;

import edu.jas.gb.ReductionAbstract;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class PseudoReductionSeq<C extends RingElem<C>> extends ReductionAbstract<C> implements PseudoReduction<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(PseudoReductionSeq.class);
    }

    public PseudoReductionSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenPolynomial<C>[] P = new GenPolynomial[0];
        synchronized (Pp) {
            P = (GenPolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        RingElem[] lbc = (RingElem[]) new RingElem[l];
        GenPolynomial<C>[] p = new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            Entry<ExpVector, C> m;
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (RingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenPolynomial<C> R = Ap.ring.getZERO().copy();
        GenPolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                e = e.subtract(htl[i]);
                RingElem c = lbc[i];
                if (((RingElem) a.remainder(c)).isZERO()) {
                    S = S.subtractMultiple((RingElem) a.divide(c), e, p[i]);
                } else {
                    R = R.multiply(c);
                    S = S.scaleSubtractMultiple(c, a, e, p[i]);
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenPolynomial<GenPolynomial<C>> normalformRecursive(List<GenPolynomial<GenPolynomial<C>>> Pp, GenPolynomial<GenPolynomial<C>> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenPolynomial<GenPolynomial<C>>[] P = new GenPolynomial[0];
        synchronized (Pp) {
            P = (GenPolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        GenPolynomial[] lbc = (GenPolynomial[]) new GenPolynomial[l];
        GenPolynomial<GenPolynomial<C>>[] p = new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            Entry<ExpVector, GenPolynomial<C>> m;
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (GenPolynomial) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenPolynomial<GenPolynomial<C>> R = Ap.ring.getZERO().copy();
        GenPolynomial<GenPolynomial<C>> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            GenPolynomial<C> a = (GenPolynomial) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                ExpVector f = e.subtract(htl[i]);
                if (this.debug) {
                    logger.info("red div = " + f);
                }
                RingElem c = lbc[i];
                if (PolyUtil.baseSparsePseudoRemainder(a, c).isZERO()) {
                    if (this.debug) {
                        logger.info("red c = " + c);
                    }
                    GenPolynomial<GenPolynomial<C>> Sp = S.subtractMultiple(PolyUtil.basePseudoDivide(a, c), f, p[i]);
                    if (e.equals(Sp.leadingExpVector())) {
                        logger.info("degree not descending: S = " + S + ", Sp = " + Sp);
                        R = R.multiply(c);
                        Sp = S.scaleSubtractMultiple(c, a, f, p[i]);
                    }
                    S = Sp;
                } else {
                    R = R.multiply(c);
                    S = S.scaleSubtractMultiple(c, a, f, p[i]);
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> row, List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenPolynomial<C>[] P = new GenPolynomial[0];
        synchronized (Pp) {
            P = (GenPolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        Object[] lbc = new Object[l];
        GenPolynomial<C>[] p = new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                lbc[j] = m.getValue();
                j++;
            }
        }
        l = j;
        boolean mt = false;
        GenPolynomial<C> zero = Ap.ring.getZERO();
        GenPolynomial<C> R = Ap.ring.getZERO().copy();
        GenPolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (RingElem) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                e = e.subtract(htl[i]);
                RingElem c = (RingElem) lbc[i];
                if (((RingElem) a.remainder(c)).isZERO()) {
                    a = (RingElem) a.divide(c);
                    S = S.subtractMultiple(a, e, p[i]);
                } else {
                    R = R.multiply(c);
                    S = S.scaleSubtractMultiple(c, a, e, p[i]);
                }
                GenPolynomial<C> fac = (GenPolynomial) row.get(i);
                if (fac == null) {
                    fac = zero.sum(a, e);
                } else {
                    fac = fac.sum(a, e);
                }
                row.set(i, fac);
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public PseudoReductionEntry<C> normalformFactor(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Ap == null) {
            return null;
        }
        C mfac = Ap.ring.getONECoefficient();
        PseudoReductionEntry<C> pseudoReductionEntry = new PseudoReductionEntry(Ap, mfac);
        if (Pp == null || Pp.isEmpty() || Ap.isZERO()) {
            return pseudoReductionEntry;
        }
        int i;
        GenPolynomial<C>[] P = new GenPolynomial[0];
        synchronized (Pp) {
            P = (GenPolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        RingElem[] lbc = (RingElem[]) new RingElem[l];
        GenPolynomial<C>[] p = new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            Entry<ExpVector, C> m;
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (RingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenPolynomial<C> R = Ap.ring.getZERO().copy();
        GenPolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                e = e.subtract(htl[i]);
                RingElem c = lbc[i];
                if (((RingElem) a.remainder(c)).isZERO()) {
                    S = S.subtractMultiple((RingElem) a.divide(c), e, p[i]);
                } else {
                    mfac = (RingElem) mfac.multiply(c);
                    R = R.multiply(c);
                    S = S.scaleSubtractMultiple(c, a, e, p[i]);
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("multiplicative factor = " + mfac);
        }
        return new PseudoReductionEntry(R, mfac);
    }
}
