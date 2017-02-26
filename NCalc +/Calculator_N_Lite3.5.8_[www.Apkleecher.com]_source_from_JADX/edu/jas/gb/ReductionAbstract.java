package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public abstract class ReductionAbstract<C extends RingElem<C>> implements Reduction<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(ReductionAbstract.class);
    }

    public ReductionAbstract() {
        this.debug = logger.isDebugEnabled();
    }

    public GenPolynomial<C> SPolynomial(GenPolynomial<C> A, GenPolynomial<C> B) {
        if (B == null || B.isZERO()) {
            if (A == null) {
                return B;
            }
            return A.ring.getZERO();
        } else if (A == null || A.isZERO()) {
            return B.ring.getZERO();
        } else {
            if (this.debug && !A.ring.equals(B.ring)) {
                logger.error("rings not equal " + A.ring + ", " + B.ring);
            }
            Entry<ExpVector, C> ma = A.leadingMonomial();
            Entry<ExpVector, C> mb = B.leadingMonomial();
            ExpVector e = (ExpVector) ma.getKey();
            ExpVector f = (ExpVector) mb.getKey();
            ExpVector g = e.lcm(f);
            return A.scaleSubtractMultiple((RingElem) mb.getValue(), g.subtract(e), (RingElem) ma.getValue(), g.subtract(f), B);
        }
    }

    public GenPolynomial<C> SPolynomial(List<GenPolynomial<C>> S, int i, GenPolynomial<C> A, int j, GenPolynomial<C> B) {
        if (this.debug) {
            if (B == null || B.isZERO()) {
                throw new ArithmeticException("Spol B is zero");
            } else if (A == null || A.isZERO()) {
                throw new ArithmeticException("Spol A is zero");
            } else if (!A.ring.equals(B.ring)) {
                GenPolynomialRing genPolynomialRing = A.ring;
                String str = ", ";
                genPolynomialRing = B.ring;
                logger.error("rings not equal " + genPolynomialRing + r17 + genPolynomialRing);
            }
        }
        Entry<ExpVector, C> ma = A.leadingMonomial();
        Entry<ExpVector, C> mb = B.leadingMonomial();
        ExpVector e = (ExpVector) ma.getKey();
        ExpVector f = (ExpVector) mb.getKey();
        ExpVector g = e.lcm(f);
        ExpVector e1 = g.subtract(e);
        ExpVector f1 = g.subtract(f);
        RingElem a = (RingElem) ma.getValue();
        RingElem b = (RingElem) mb.getValue();
        GenPolynomial<C> Cp = A.scaleSubtractMultiple(b, e1, a, f1, B);
        GenPolynomial<C> zero = A.ring.getZERO();
        GenPolynomial<C> As = zero.sum((RingElem) b.negate(), e1);
        GenPolynomial<C> Bs = zero.sum(a, f1);
        S.set(i, As);
        S.set(j, Bs);
        return Cp;
    }

    public boolean moduleCriterion(int modv, GenPolynomial<C> A, GenPolynomial<C> B) {
        if (modv == 0) {
            return true;
        }
        return moduleCriterion(modv, A.leadingExpVector(), B.leadingExpVector());
    }

    public boolean moduleCriterion(int modv, ExpVector ei, ExpVector ej) {
        if (modv == 0 || ei.invLexCompareTo(ej, 0, modv) == 0) {
            return true;
        }
        return false;
    }

    public boolean criterion4(GenPolynomial<C> A, GenPolynomial<C> B, ExpVector e) {
        if (logger.isInfoEnabled()) {
            if (!A.ring.equals(B.ring)) {
                logger.error("rings not equal " + A.ring + ", " + B.ring);
            }
            if (!A.ring.isCommutative()) {
                logger.error("GBCriterion4 not applicabable to non-commutative polynomials");
                return true;
            }
        }
        return criterion4(A.leadingExpVector(), B.leadingExpVector(), e);
    }

    public boolean criterion4(ExpVector ei, ExpVector ej, ExpVector e) {
        return ei.sum(ej).subtract(e).signum() != 0;
    }

    public boolean criterion4(GenPolynomial<C> A, GenPolynomial<C> B) {
        if (!logger.isInfoEnabled() || (A.ring.isCommutative() && B.ring.isCommutative())) {
            ExpVector ei = A.leadingExpVector();
            ExpVector ej = B.leadingExpVector();
            return criterion4(ei, ej, ei.lcm(ej));
        }
        logger.error("GBCriterion4 not applicabable to non-commutative polynomials");
        return true;
    }

    public List<GenPolynomial<C>> normalform(List<GenPolynomial<C>> Pp, List<GenPolynomial<C>> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isEmpty()) {
            return Ap;
        }
        ArrayList<GenPolynomial<C>> red = new ArrayList();
        for (GenPolynomial<C> A : Ap) {
            red.add(normalform((List) Pp, (GenPolynomial) A));
        }
        return red;
    }

    public boolean isTopReducible(List<GenPolynomial<C>> P, GenPolynomial<C> A) {
        if (P == null || P.isEmpty() || A == null || A.isZERO()) {
            return false;
        }
        ExpVector e = A.leadingExpVector();
        for (GenPolynomial<C> p : P) {
            if (e.multipleOf(p.leadingExpVector())) {
                return true;
            }
        }
        return false;
    }

    public boolean isReducible(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        return !isNormalform(Pp, Ap);
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
        GenPolynomial<C>[] p = new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                j++;
            }
        }
        l = j;
        for (ExpVector e : Ap.getMap().keySet()) {
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isNormalform(List<GenPolynomial<C>> Pp) {
        if (Pp == null || Pp.isEmpty()) {
            return true;
        }
        List<GenPolynomial<C>> P = new LinkedList(Pp);
        int s = P.size();
        for (int i = 0; i < s; i++) {
            GenPolynomial<C> Ap = (GenPolynomial) P.remove(i);
            if (!isNormalform(P, Ap)) {
                return false;
            }
            P.add(Ap);
        }
        return true;
    }

    public List<GenPolynomial<C>> irreducibleSet(List<GenPolynomial<C>> Pp) {
        GenPolynomial<C> a;
        ArrayList<GenPolynomial<C>> P = new ArrayList();
        for (GenPolynomial<C> a2 : Pp) {
            if (a2.length() != 0) {
                a2 = a2.monic();
                if (a2.isONE()) {
                    P.clear();
                    P.add(a2);
                    return P;
                }
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
            a2 = normalform((List) P, (GenPolynomial) a2);
            logger.debug(String.valueOf(irr));
            if (a2.length() == 0) {
                l--;
                if (l <= 1) {
                    return P;
                }
            } else {
                ExpVector f = a2.leadingExpVector();
                if (f.signum() == 0) {
                    P = new ArrayList();
                    P.add(a2.monic());
                    return P;
                }
                if (e.equals(f)) {
                    irr++;
                } else {
                    irr = 0;
                    a2 = a2.monic();
                }
                P.add(a2);
            }
        }
        return P;
    }

    public boolean isReductionNF(List<GenPolynomial<C>> row, List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap, GenPolynomial<C> Np) {
        if (row == null && Pp == null) {
            if (Ap != null) {
                return Ap.equals(Np);
            }
            if (Np == null) {
                return true;
            }
            return false;
        } else if (row == null || Pp == null) {
            return false;
        } else {
            if (row.size() != Pp.size()) {
                return false;
            }
            GenPolynomial<C> r;
            GenPolynomial<C> t = Np;
            for (int m = 0; m < Pp.size(); m++) {
                r = (GenPolynomial) row.get(m);
                GenPolynomial p = (GenPolynomial) Pp.get(m);
                if (!(r == null || p == null)) {
                    if (t == null) {
                        t = r.multiply(p);
                    } else {
                        t = t.sum(r.multiply(p));
                    }
                }
            }
            if (t != null) {
                r = t.subtract((GenPolynomial) Ap);
                boolean z = r.isZERO();
                if (!z) {
                    logger.info("t = " + t);
                    logger.info("a = " + Ap);
                    logger.info("t-a = " + r);
                }
                return z;
            } else if (Ap != null) {
                return Ap.isZERO();
            } else {
                return true;
            }
        }
    }
}
