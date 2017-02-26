package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public abstract class SolvableReductionAbstract<C extends RingElem<C>> implements SolvableReduction<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(SolvableReductionAbstract.class);
    }

    public SolvableReductionAbstract() {
        this.debug = logger.isDebugEnabled();
    }

    public GenSolvablePolynomial<C> leftSPolynomial(GenSolvablePolynomial<C> Ap, GenSolvablePolynomial<C> Bp) {
        if (logger.isInfoEnabled()) {
            if (Bp == null || Bp.isZERO()) {
                if (Ap != null) {
                    return Ap.ring.getZERO();
                }
                return null;
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
        return (GenSolvablePolynomial) Ap.multiplyLeft((RingElem) mb.getValue(), g.subtract(e)).subtract((GenPolynomial) Bp.multiplyLeft((RingElem) ma.getValue(), g.subtract(f)));
    }

    public GenSolvablePolynomial<C> leftSPolynomial(List<GenSolvablePolynomial<C>> S, int i, GenSolvablePolynomial<C> Ap, int j, GenSolvablePolynomial<C> Bp) {
        if (this.debug) {
            if (Bp == null || Bp.isZERO()) {
                throw new ArithmeticException("Spol B is zero");
            } else if (Ap == null || Ap.isZERO()) {
                throw new ArithmeticException("Spol A is zero");
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
        ExpVector e1 = g.subtract(e);
        ExpVector f1 = g.subtract(f);
        RingElem a = (RingElem) ma.getValue();
        RingElem b = (RingElem) mb.getValue();
        GenSolvablePolynomial<C> Cp = (GenSolvablePolynomial) Ap.multiplyLeft(b, e1).subtract((GenPolynomial) Bp.multiplyLeft(a, f1));
        GenSolvablePolynomial<C> zero = Ap.ring.getZERO();
        GenSolvablePolynomial<C> Bs = (GenSolvablePolynomial) zero.sum(a, f1);
        S.set(i, (GenSolvablePolynomial) zero.sum((RingElem) b.negate(), e1));
        S.set(j, Bs);
        return Cp;
    }

    public List<GenSolvablePolynomial<C>> leftNormalform(List<GenSolvablePolynomial<C>> Pp, List<GenSolvablePolynomial<C>> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isEmpty()) {
            return Ap;
        }
        ArrayList<GenSolvablePolynomial<C>> red = new ArrayList();
        for (GenSolvablePolynomial<C> A : Ap) {
            red.add(leftNormalform((List) Pp, (GenSolvablePolynomial) A));
        }
        return red;
    }

    public List<GenSolvablePolynomial<C>> leftIrreducibleSet(List<GenSolvablePolynomial<C>> Pp) {
        GenSolvablePolynomial<C> a;
        ArrayList<GenSolvablePolynomial<C>> P = new ArrayList();
        for (GenSolvablePolynomial<C> a2 : Pp) {
            if (a2.length() != 0) {
                P.add(a2.monic());
            }
        }
        int l = P.size();
        if (l <= 1) {
            return P;
        }
        int irr = 0;
        logger.debug("irr = ");
        while (irr != l) {
            a2 = (GenSolvablePolynomial) P.listIterator().next();
            P.remove(0);
            ExpVector e = a2.leadingExpVector();
            a2 = leftNormalform((List) P, (GenSolvablePolynomial) a2);
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

    public boolean isLeftReductionNF(List<GenSolvablePolynomial<C>> row, List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap, GenSolvablePolynomial<C> Np) {
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
            GenSolvablePolynomial<C> t = Np;
            for (int m = 0; m < Pp.size(); m++) {
                GenSolvablePolynomial<C> r = (GenSolvablePolynomial) row.get(m);
                GenSolvablePolynomial p = (GenSolvablePolynomial) Pp.get(m);
                if (!(r == null || p == null)) {
                    if (t == null) {
                        t = r.multiply(p);
                    } else {
                        t = (GenSolvablePolynomial) t.sum(r.multiply(p));
                    }
                }
            }
            if (this.debug) {
                logger.info("t = " + t);
                logger.info("a = " + Ap);
            }
            if (t != null) {
                return ((GenSolvablePolynomial) t.subtract((GenPolynomial) Ap)).isZERO();
            }
            if (Ap != null) {
                return Ap.isZERO();
            }
            return true;
        }
    }

    public GenSolvablePolynomial<C> rightSPolynomial(GenSolvablePolynomial<C> Ap, GenSolvablePolynomial<C> Bp) {
        if (logger.isInfoEnabled()) {
            if (Bp == null || Bp.isZERO()) {
                if (Ap != null) {
                    return Ap.ring.getZERO();
                }
                return null;
            } else if (Ap == null || Ap.isZERO()) {
                return Bp.ring.getZERO();
            } else {
                if (!Ap.ring.equals(Bp.ring)) {
                    logger.error("rings not equal");
                }
            }
        }
        ExpVector e = Ap.leadingExpVector();
        ExpVector f = Bp.leadingExpVector();
        ExpVector g = e.lcm(f);
        ExpVector e1 = g.subtract(e);
        ExpVector f1 = g.subtract(f);
        GenSolvablePolynomial<C> App = Ap.multiply(e1);
        GenSolvablePolynomial<C> Bpp = Bp.multiply(f1);
        return (GenSolvablePolynomial) App.multiply(Bpp.leadingBaseCoefficient()).subtract((GenPolynomial) Bpp.multiply(App.leadingBaseCoefficient()));
    }

    public boolean isTopReducible(List<GenSolvablePolynomial<C>> P, GenSolvablePolynomial<C> A) {
        if (P == null || P.isEmpty() || A == null || A.isZERO()) {
            return false;
        }
        ExpVector e = A.leadingExpVector();
        for (GenSolvablePolynomial<C> p : P) {
            if (e.multipleOf(p.leadingExpVector())) {
                return true;
            }
        }
        return false;
    }

    public boolean isReducible(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        return !isNormalform(Pp, Ap);
    }

    public boolean isNormalform(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty()) {
            return true;
        }
        if (Ap == null || Ap.isZERO()) {
            return true;
        }
        int i;
        GenSolvablePolynomial<C>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        GenSolvablePolynomial<C>[] p = new GenSolvablePolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            ExpVector f = p[i].leadingExpVector();
            if (f != null) {
                p[j] = p[i];
                htl[j] = f;
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
}
