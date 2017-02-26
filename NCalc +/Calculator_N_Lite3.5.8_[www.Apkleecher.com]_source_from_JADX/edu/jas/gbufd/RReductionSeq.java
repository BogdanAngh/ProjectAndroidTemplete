package edu.jas.gbufd;

import edu.jas.gb.ReductionAbstract;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RegularRingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class RReductionSeq<C extends RegularRingElem<C>> extends ReductionAbstract<C> implements RReduction<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(RReductionSeq.class);
    }

    public RReductionSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public boolean isTopReducible(List<GenPolynomial<C>> P, GenPolynomial<C> A) {
        if (P == null || P.isEmpty()) {
            return false;
        }
        if (A == null || A.isZERO()) {
            return false;
        }
        ExpVector e = A.leadingExpVector();
        C a = ((RegularRingElem) A.leadingBaseCoefficient()).idempotent();
        for (GenPolynomial<C> p : P) {
            if (e.multipleOf(p.leadingExpVector())) {
                boolean mt;
                if (a.idempotentAnd((RegularRingElem) p.leadingBaseCoefficient()).isZERO()) {
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

    public boolean isStrongTopReducible(List<GenPolynomial<C>> P, GenPolynomial<C> A) {
        if (P == null || P.isEmpty() || A == null || A.isZERO()) {
            return false;
        }
        ExpVector e = A.leadingExpVector();
        C a = ((RegularRingElem) A.leadingBaseCoefficient()).idempotent();
        for (GenPolynomial<C> p : P) {
            if (e.multipleOf(p.leadingExpVector()) && a.equals(((RegularRingElem) p.leadingBaseCoefficient()).idempotent())) {
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
        RegularRingElem[] lbc = (RegularRingElem[]) new RegularRingElem[l];
        GenPolynomial<C>[] p = new GenPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            if (P[i] != null) {
                p[i] = P[i];
                Entry<ExpVector, C> m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (RegularRingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        for (Entry<ExpVector, C> me : Ap.getMap().entrySet()) {
            ExpVector e = (ExpVector) me.getKey();
            RegularRingElem a = (RegularRingElem) me.getValue();
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    if (!a.idempotentAnd(lbc[i]).isZERO()) {
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
        synchronized (Pp) {
            int i;
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
            Entry<ExpVector, C> m;
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
            if (this.debug && a.isZERO()) {
                throw new RuntimeException("a.isZERO(): S = " + S);
            }
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    C r = a.idempotentAnd(lbc[i]);
                    if (!r.isZERO()) {
                        RegularRingElem b = (RegularRingElem) a.divide(lbc[i]);
                        if (b.isZERO()) {
                            System.out.println("b == zero: r = " + r);
                        } else {
                            S = S.subtract(p[i].multiply(b, e.subtract(htl[i])));
                            if (!e.equals(S.leadingExpVector())) {
                                a = (RegularRingElem) Ap.ring.coFac.getZERO();
                                break;
                            }
                            RegularRingElem a2 = (RegularRingElem) S.leadingBaseCoefficient();
                        }
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
        if (A.leadingExpVector().sum(B.leadingExpVector()).subtract(e).signum() == 0 && ((RegularRingElem) ((RegularRingElem) A.leadingBaseCoefficient()).multiply((RegularRingElem) B.leadingBaseCoefficient())).isZERO()) {
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
        if (ei.sum(ej).subtract(ei.lcm(ej)).signum() == 0 && ((RegularRingElem) ((RegularRingElem) A.leadingBaseCoefficient()).multiply((RegularRingElem) B.leadingBaseCoefficient())).isZERO()) {
            return false;
        }
        return true;
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
                    C r = a.idempotentAnd(lbc[i]);
                    if (!r.isZERO()) {
                        a = (RegularRingElem) a.divide(lbc[i]);
                        if (a.isZERO()) {
                            System.out.println("b == zero: r = " + r);
                        } else {
                            ExpVector f = e.subtract(htl[i]);
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
                            RegularRingElem a2 = (RegularRingElem) S.leadingBaseCoefficient();
                        }
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

    public List<GenPolynomial<C>> irreducibleSet(List<GenPolynomial<C>> Pp) {
        ArrayList<GenPolynomial<C>> P = new ArrayList();
        if (Pp == null) {
            return null;
        }
        GenPolynomial<C> a;
        for (GenPolynomial<C> a2 : Pp) {
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

    public boolean isBooleanClosed(GenPolynomial<C> A) {
        if (A == null || A.isZERO() || A.equals(A.multiply(((RegularRingElem) A.leadingBaseCoefficient()).idempotent()))) {
            return true;
        }
        return false;
    }

    public boolean isBooleanClosed(List<GenPolynomial<C>> F) {
        if (F == null || F.size() == 0) {
            return true;
        }
        for (GenPolynomial a : F) {
            if (a != null && !a.isZERO() && !isBooleanClosed(a)) {
                return false;
            }
        }
        return true;
    }

    public boolean isReducedBooleanClosed(List<GenPolynomial<C>> F) {
        if (F == null || F.size() == 0) {
            return true;
        }
        for (GenPolynomial a : F) {
            if (a != null) {
                while (!a.isZERO()) {
                    if (!isBooleanClosed(a) && !normalform(F, booleanClosure(a)).isZERO()) {
                        return false;
                    }
                    GenPolynomial<C> r = normalform(F, booleanRemainder(a));
                    if (!r.isZERO()) {
                        return false;
                    }
                    GenPolynomial<C> a2 = r;
                }
                continue;
            }
        }
        return true;
    }

    public GenPolynomial<C> booleanClosure(GenPolynomial<C> A) {
        if (A == null || A.isZERO()) {
            return A;
        }
        return A.multiply(((RegularRingElem) A.leadingBaseCoefficient()).idempotent());
    }

    public GenPolynomial<C> booleanRemainder(GenPolynomial<C> A) {
        if (A == null || A.isZERO()) {
            return A;
        }
        return A.multiply(((RegularRingElem) A.leadingBaseCoefficient()).idemComplement());
    }

    public List<GenPolynomial<C>> booleanClosure(List<GenPolynomial<C>> F) {
        if (F == null || F.size() == 0) {
            return F;
        }
        List<GenPolynomial<C>> B = new ArrayList(F.size());
        for (GenPolynomial a : F) {
            GenPolynomial a2;
            if (a2 != null) {
                while (!a2.isZERO()) {
                    B.add(booleanClosure(a2));
                    a2 = booleanRemainder(a2);
                }
            }
        }
        return B;
    }

    public List<GenPolynomial<C>> reducedBooleanClosure(List<GenPolynomial<C>> F) {
        if (F == null || F.size() == 0) {
            return F;
        }
        List<GenPolynomial<C>> B = new ArrayList(F);
        int len = B.size();
        for (int i = 0; i < len; i++) {
            GenPolynomial a = (GenPolynomial) B.remove(0);
            if (a != null) {
                while (!a.isZERO()) {
                    GenPolynomial b = booleanClosure(normalform(B, booleanClosure(a)));
                    if (b.isZERO()) {
                        break;
                    }
                    B.add(b);
                    a = normalform(B, a.subtract(b));
                }
            }
        }
        return B;
    }

    public List<GenPolynomial<C>> reducedBooleanClosure(List<GenPolynomial<C>> F, GenPolynomial<C> A) {
        List<GenPolynomial<C>> B = new ArrayList();
        if (!(A == null || A.isZERO())) {
            GenPolynomial a = A;
            while (!a.isZERO()) {
                GenPolynomial b = booleanClosure(normalform(F, booleanClosure(a)));
                if (b.isZERO()) {
                    break;
                }
                B.add(b);
                GenPolynomial<C> a2 = normalform(F, a.subtract(b));
            }
        }
        return B;
    }
}
