package edu.jas.gbufd;

import edu.jas.gb.SolvableReductionAbstract;
import edu.jas.gbmod.SolvableSyzygyAbstract;
import edu.jas.gbmod.SolvableSyzygySeq;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class SolvablePseudoReductionSeq<C extends GcdRingElem<C>> extends SolvableReductionAbstract<C> implements SolvablePseudoReduction<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;

    static {
        $assertionsDisabled = !SolvablePseudoReductionSeq.class.desiredAssertionStatus();
        logger = Logger.getLogger(SolvablePseudoReductionSeq.class);
    }

    public SolvablePseudoReductionSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenSolvablePolynomial<C>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        GcdRingElem[] lbc = (GcdRingElem[]) new GcdRingElem[l];
        GenSolvablePolynomial<C>[] p = new GenSolvablePolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            Entry<ExpVector, C> m;
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (GcdRingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (GcdRingElem) m.getValue();
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
                GenSolvablePolynomial<C> Q = p[i].multiplyLeft(e);
                RingElem c = (GcdRingElem) Q.leadingBaseCoefficient();
                ExpVector g = S.leadingExpVector();
                C ap = a;
                if (((GcdRingElem) a.remainder(c)).isZERO()) {
                    a = (GcdRingElem) a.divide(c);
                    S = S.subtractMultiple(a, Q);
                } else {
                    R = R.multiplyLeft(c);
                    S = S.scaleSubtractMultiple(c, a, Q);
                }
                ExpVector h = S.leadingExpVector();
                if (g.equals(h)) {
                    System.out.println("g = " + g + ", h = " + h);
                    System.out.println("c*ap = " + c.multiply(ap) + ", ap*c = " + ap.multiply(c));
                    throw new RuntimeException("g.equals(h): a = " + a + ", ap = " + ap + ", c = " + c);
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenSolvablePolynomial<GenPolynomial<C>> leftNormalformRecursive(List<GenSolvablePolynomial<GenPolynomial<C>>> Pp, GenSolvablePolynomial<GenPolynomial<C>> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        SolvableSyzygyAbstract<C> ssy;
        GenSolvablePolynomial<GenPolynomial<C>>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        GenPolynomial[] lbc = (GenPolynomial[]) new GenPolynomial[l];
        GenSolvablePolynomial<GenPolynomial<C>>[] p = new GenSolvablePolynomial[l];
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
        GenSolvablePolynomialRing<GenPolynomial<C>> ring = Ap.ring;
        boolean commCoeff = ring.coFac.isCommutative();
        if (commCoeff) {
            ssy = null;
        } else {
            ssy = new SolvableSyzygySeq(((GenPolynomialRing) ring.coFac).coFac);
        }
        GenSolvablePolynomial<GenPolynomial<C>> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<GenPolynomial<C>> S = Ap.copy();
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
                GenSolvablePolynomial<GenPolynomial<C>> Q = p[i].multiplyLeft(f);
                ExpVector g = S.leadingExpVector();
                GenPolynomial<C> ap = a;
                if (commCoeff) {
                    RingElem c = (GenPolynomial) Q.leadingBaseCoefficient();
                    if (!PolyUtil.baseSparsePseudoRemainder(a, c).isZERO() || c.isConstant()) {
                        R = R.multiplyLeft(c);
                        S = S.scaleSubtractMultiple(c, a, Q);
                    } else {
                        if (this.debug) {
                            logger.info("red c = " + c);
                        }
                        GenSolvablePolynomial<GenPolynomial<C>> Sp = S.subtractMultiple(PolyUtil.basePseudoDivide(a, c), Q);
                        if (e.equals(Sp.leadingExpVector())) {
                            throw new RuntimeException("degree not descending");
                        }
                        S = Sp;
                    }
                } else {
                    GenPolynomial<C>[] ore = ssy.leftOreCond((GenSolvablePolynomial) Q.leadingBaseCoefficient(), (GenSolvablePolynomial) a);
                    R = R.multiplyLeft(ore[1]);
                    S = S.scaleSubtractMultiple(ore[1], ore[0], Q);
                }
                ExpVector h = S.leadingExpVector();
                if (g.equals(h)) {
                    System.out.println("g = " + g + ", h = " + h);
                    throw new RuntimeException("g.equals(h): a = " + a + ", ap = " + ap);
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> row, List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenSolvablePolynomial<C>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        GcdRingElem[] lbc = (GcdRingElem[]) new GcdRingElem[l];
        GenSolvablePolynomial<C>[] p = new GenSolvablePolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                lbc[j] = (GcdRingElem) m.getValue();
                j++;
            }
        }
        l = j;
        boolean mt = false;
        GenSolvablePolynomial<C> zero = Ap.ring.getZERO();
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (GcdRingElem) m.getValue();
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
                GenSolvablePolynomial<C> Q = p[i].multiplyLeft(e);
                RingElem c = (GcdRingElem) Q.leadingBaseCoefficient();
                ExpVector g = S.leadingExpVector();
                C ap = a;
                if (((GcdRingElem) a.remainder(c)).isZERO()) {
                    a = (GcdRingElem) a.divide(c);
                    S = S.subtractMultiple(a, Q);
                } else {
                    R = R.multiplyLeft(c);
                    S = S.scaleSubtractMultiple(c, a, Q);
                }
                ExpVector h = S.leadingExpVector();
                if (g.equals(h)) {
                    System.out.println("g = " + g + ", h = " + h);
                    System.out.println("c*ap = " + c.multiply(ap) + ", ap*c = " + ap.multiply(c));
                    throw new RuntimeException("g.equals(h): a = " + a + ", ap = " + ap + ", c = " + c);
                }
                GenSolvablePolynomial<C> fac = (GenSolvablePolynomial) row.get(i);
                if (fac == null) {
                    fac = (GenSolvablePolynomial) zero.sum(a, e);
                } else {
                    fac = (GenSolvablePolynomial) fac.sum(a, e);
                }
                row.set(i, fac);
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public PseudoReductionEntry<C> leftNormalformFactor(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Ap == null) {
            return null;
        }
        C mfac = (GcdRingElem) Ap.ring.getONECoefficient();
        PseudoReductionEntry<C> pseudoReductionEntry = new PseudoReductionEntry(Ap, mfac);
        if (Pp == null || Pp.isEmpty() || Ap.isZERO()) {
            return pseudoReductionEntry;
        }
        int i;
        GenSolvablePolynomial<C>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        GcdRingElem[] lbc = (GcdRingElem[]) new GcdRingElem[l];
        GenSolvablePolynomial<C>[] p = new GenSolvablePolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            Entry<ExpVector, C> m;
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (GcdRingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            C a = (GcdRingElem) m.getValue();
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
                GenSolvablePolynomial<C> Q = p[i].multiplyLeft(e);
                RingElem c = (GcdRingElem) Q.leadingBaseCoefficient();
                ExpVector g = S.leadingExpVector();
                C ap = a;
                if (((GcdRingElem) a.remainder(c)).isZERO()) {
                    a = (GcdRingElem) a.divide(c);
                    S = S.subtractMultiple(a, Q);
                } else {
                    GcdRingElem mfac2 = (GcdRingElem) c.multiply(mfac);
                    R = R.multiplyLeft(c);
                    S = S.scaleSubtractMultiple(c, a, Q);
                }
                ExpVector h = S.leadingExpVector();
                if (g.equals(h)) {
                    System.out.println("g = " + g + ", h = " + h);
                    System.out.println("c*ap = " + c.multiply(ap) + ", ap*c = " + ap.multiply(c));
                    throw new RuntimeException("g.equals(h): a = " + a + ", ap = " + ap + ", c = " + c);
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.info("multiplicative factor = " + mfac);
        }
        return new PseudoReductionEntry(R, mfac);
    }

    public GenSolvablePolynomial<C> rightNormalform(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenSolvablePolynomial<C>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        GcdRingElem[] lbc = (GcdRingElem[]) new GcdRingElem[l];
        GenSolvablePolynomial<C>[] p = new GenSolvablePolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            Entry<ExpVector, C> m;
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (GcdRingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (GcdRingElem) m.getValue();
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
                GenSolvablePolynomial<C> Q = p[i].multiply(e);
                if ($assertionsDisabled || Q.multiply(a).equals(Q.multiplyLeft(a))) {
                    C a2;
                    RingElem c = (GcdRingElem) Q.leadingBaseCoefficient();
                    ExpVector g = S.leadingExpVector();
                    C ap = a;
                    if (((GcdRingElem) a.remainder(c)).isZERO()) {
                        a2 = (GcdRingElem) a.divide(c);
                        S = (GenSolvablePolynomial) S.subtract(Q.multiply((RingElem) a2));
                    } else {
                        R = R.multiply(c);
                        S = (GenSolvablePolynomial) S.multiply(c).subtract(Q.multiply(a));
                    }
                    ExpVector h = S.leadingExpVector();
                    if (g.equals(h)) {
                        System.out.println("g = " + g + ", h = " + h);
                        System.out.println("c*ap = " + c.multiply(ap) + ", ap*c = " + ap.multiply(c));
                        throw new RuntimeException("g.equals(h): a = " + a2 + ", ap = " + ap + ", c = " + c);
                    }
                } else {
                    throw new AssertionError();
                }
            }
            R.doPutToMap(e, a);
            S.doRemoveFromMap(e, a);
        }
        return R;
    }

    public GenSolvablePolynomial<GenPolynomial<C>> rightNormalformRecursive(List<GenSolvablePolynomial<GenPolynomial<C>>> list, GenSolvablePolynomial<GenPolynomial<C>> genSolvablePolynomial) {
        throw new UnsupportedOperationException();
    }

    public GenSolvablePolynomial<C> rightNormalform(List<GenSolvablePolynomial<C>> list, List<GenSolvablePolynomial<C>> list2, GenSolvablePolynomial<C> genSolvablePolynomial) {
        throw new UnsupportedOperationException();
    }

    public PseudoReductionEntry<C> rightNormalformFactor(List<GenSolvablePolynomial<C>> list, GenSolvablePolynomial<C> genSolvablePolynomial) {
        throw new UnsupportedOperationException();
    }
}
