package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.SolvableGroebnerBaseAbstract;
import edu.jas.gb.SolvableGroebnerBaseSeq;
import edu.jas.gb.SolvableReductionSeq;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PolyGBUtil {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(PolyGBUtil.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends GcdRingElem<C>> boolean isResultant(GenPolynomial<C> A, GenPolynomial<C> B, GenPolynomial<C> r) {
        if (r == null || r.isZERO()) {
            return true;
        }
        GroebnerBaseAbstract<C> bb = GBFactory.getImplementation(r.ring.coFac);
        List<GenPolynomial<C>> F = new ArrayList(2);
        F.add(A);
        F.add(B);
        return bb.red.normalform(bb.GB(F), (GenPolynomial) r).isZERO();
    }

    public static <C extends RingElem<C>> GenPolynomial<C> topPseudoRemainder(List<GenPolynomial<C>> A, GenPolynomial<C> P) {
        if (A == null || A.isEmpty()) {
            return P.monic();
        }
        if (P.isZERO()) {
            return P;
        }
        GenPolynomialRing pfac = ((GenPolynomial) A.get(0)).ring;
        if (pfac.nvar <= 1) {
            return PolyUtil.baseSparsePseudoRemainder(P, (GenPolynomial) A.get(0)).monic();
        }
        GenPolynomialRing rfac = pfac.recursive(1);
        GenPolynomial<GenPolynomial<C>> qr = PolyUtil.recursive(rfac, (GenPolynomial) A.get(0));
        GenPolynomial<GenPolynomial<C>> pr = PolyUtil.recursive(rfac, (GenPolynomial) P);
        if (qr.isONE()) {
            return P.ring.getZERO();
        }
        if (qr.degree(0) > 0) {
            GenPolynomial rr = PolyUtil.recursiveSparsePseudoRemainder(pr, qr);
        } else {
            GenPolynomial<GenPolynomial<C>> rr2 = pr;
        }
        if (rr.degree(0) > 0) {
            return PolyUtil.distribute(pfac, rr).monic();
        }
        return topPseudoRemainder(zeroDegrees(A), (GenPolynomial) rr.leadingBaseCoefficient()).extend(pfac, 0, 0).monic();
    }

    public static <C extends RingElem<C>> GenPolynomial<C> topCoefficientPseudoRemainder(List<GenPolynomial<C>> A, GenPolynomial<C> P) {
        if (A == null || A.isEmpty()) {
            return P.monic();
        }
        if (P.isZERO()) {
            return P;
        }
        GenPolynomialRing pfac = P.ring;
        GenPolynomialRing<C> pfac1 = ((GenPolynomial) A.get(0)).ring;
        int i = pfac1.nvar;
        if (r0 <= 1) {
            return PolyUtil.distribute(pfac, coefficientPseudoRemainderBase(PolyUtil.recursive(pfac.recursive(pfac.nvar - 1), (GenPolynomial) P), (GenPolynomial) A.get(0))).monic();
        }
        GenPolynomialRing rfac1 = pfac1.recursive(1);
        int nv = pfac.nvar - pfac1.nvar;
        GenPolynomialRing rfac = pfac.recursive(nv + 1);
        GenPolynomialRing rfac2 = rfac.recursive(nv);
        if (debug) {
            logger.info("rfac =" + rfac);
        }
        GenPolynomial<GenPolynomial<GenPolynomial<C>>> pr2 = PolyUtil.recursive(rfac2, PolyUtil.recursive(rfac, (GenPolynomial) P));
        GenPolynomial<GenPolynomial<C>> qr = PolyUtil.recursive(rfac1, (GenPolynomial) A.get(0));
        if (qr.isONE()) {
            return P.ring.getZERO();
        }
        GenPolynomial rr;
        if (qr.degree(0) > 0) {
            rr = coefficientPseudoRemainder(pr2, qr);
        } else {
            GenPolynomial<GenPolynomial<GenPolynomial<C>>> rr2 = pr2;
        }
        return topCoefficientPseudoRemainder(zeroDegrees(A), PolyUtil.distribute(pfac, PolyUtil.distribute(rfac, rr))).monic();
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<GenPolynomial<C>>> coefficientPseudoRemainder(GenPolynomial<GenPolynomial<GenPolynomial<C>>> P, GenPolynomial<GenPolynomial<C>> A) {
        if (A == null || A.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + A);
        } else if (A.isONE()) {
            return P.ring.getZERO();
        } else if (P.isZERO() || P.isONE()) {
            return P;
        } else {
            GenPolynomialRing<GenPolynomial<GenPolynomial<C>>> pfac = P.ring;
            GenPolynomialRing<GenPolynomial<C>> afac = A.ring;
            GenPolynomial<GenPolynomial<GenPolynomial<C>>> r = P;
            long m = ((GenPolynomial) P.leadingBaseCoefficient()).degree(0);
            long n = A.degree(0);
            RingElem c = (GenPolynomial) A.leadingBaseCoefficient();
            RingElem cc = afac.getZERO().sum(c);
            ExpVector e = A.leadingExpVector();
            for (long i = m; i >= n; i--) {
                if (r.isZERO()) {
                    return r;
                }
                GenPolynomial<GenPolynomial<C>> p = (GenPolynomial) r.leadingBaseCoefficient();
                ExpVector g = r.leadingExpVector();
                if (i == p.degree(0)) {
                    GenPolynomial<C> pl = (GenPolynomial) p.leadingBaseCoefficient();
                    ExpVector f = p.leadingExpVector().subtract(e);
                    r = r.multiply(cc).subtract(new GenPolynomial(pfac, A.multiply(pl, f), g));
                } else {
                    r = r.multiply(cc);
                }
            }
            if (r.degree(0) < P.degree(0)) {
                return coefficientPseudoRemainder(r, A);
            }
            return r;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> coefficientPseudoRemainderBase(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<C> A) {
        if (A == null || A.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + A);
        } else if (A.isONE()) {
            return P.ring.getZERO();
        } else if (P.isZERO() || P.isONE()) {
            return P;
        } else {
            GenPolynomialRing<GenPolynomial<C>> pfac = P.ring;
            GenPolynomialRing<C> afac = A.ring;
            GenPolynomial<GenPolynomial<C>> r = P;
            long m = ((GenPolynomial) P.leadingBaseCoefficient()).degree(0);
            long n = A.degree(0);
            RingElem c = A.leadingBaseCoefficient();
            RingElem cc = afac.getZERO().sum(c);
            ExpVector e = A.leadingExpVector();
            for (long i = m; i >= n; i--) {
                if (r.isZERO()) {
                    return r;
                }
                GenPolynomial<C> p = (GenPolynomial) r.leadingBaseCoefficient();
                ExpVector g = r.leadingExpVector();
                if (i == p.degree(0)) {
                    C pl = p.leadingBaseCoefficient();
                    ExpVector f = p.leadingExpVector().subtract(e);
                    r = r.multiply(cc).subtract(new GenPolynomial(pfac, A.multiply(pl, f), g));
                } else {
                    r = r.multiply(cc);
                }
            }
            if (r.degree(0) < P.degree(0)) {
                return coefficientPseudoRemainderBase(r, A);
            }
            return r;
        }
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> zeroDegrees(List<GenPolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            return A;
        }
        GenPolynomialRing rfac = ((GenPolynomial) A.get(0)).ring.recursive(1);
        List<GenPolynomial<C>> zeroDeg = new ArrayList(A.size());
        for (int i = 0; i < A.size(); i++) {
            GenPolynomial<GenPolynomial<C>> fr = PolyUtil.recursive(rfac, (GenPolynomial) A.get(i));
            if (fr.degree(0) == 0) {
                zeroDeg.add(fr.leadingBaseCoefficient());
            }
        }
        return zeroDeg;
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<C>> intersect(GenPolynomialRing<C> pfac, List<GenPolynomial<C>> A, List<GenPolynomial<C>> B) {
        if (A == null || A.isEmpty()) {
            return B;
        }
        if (B == null || B.isEmpty()) {
            return A;
        }
        List<GenPolynomial<C>> c = new ArrayList(A.size() + B.size());
        GenPolynomialRing<C> tfac = pfac.extend(1);
        for (GenPolynomial<C> p : A) {
            c.add(p.extend(tfac, 0, 1));
        }
        for (GenPolynomial<C> p2 : B) {
            c.add(p2.extend(tfac, 0, 0).subtract(p2.extend(tfac, 0, 1)));
        }
        GroebnerBaseAbstract<C> bb = GBFactory.getImplementation(tfac.coFac);
        logger.warn("intersect computing GB");
        List G = bb.GB(c);
        if (debug) {
            logger.debug("intersect GB = " + G);
        }
        return PolyUtil.intersect((GenPolynomialRing) pfac, G);
    }

    public static <C extends GcdRingElem<C>> List<GenSolvablePolynomial<C>> intersect(GenSolvablePolynomialRing<C> pfac, List<GenSolvablePolynomial<C>> A, List<GenSolvablePolynomial<C>> B) {
        if (A == null || A.isEmpty()) {
            return B;
        }
        if (B == null || B.isEmpty()) {
            return A;
        }
        List<GenSolvablePolynomial<C>> c = new ArrayList(A.size() + B.size());
        GenSolvablePolynomialRing<C> tfac = pfac.extend(1);
        for (GenSolvablePolynomial<C> p : A) {
            c.add((GenSolvablePolynomial) p.extend(tfac, 0, 1));
        }
        for (GenSolvablePolynomial<C> p2 : B) {
            c.add((GenSolvablePolynomial) ((GenSolvablePolynomial) p2.extend(tfac, 0, 0)).subtract((GenPolynomial) (GenSolvablePolynomial) p2.extend(tfac, 0, 1)));
        }
        SolvableGroebnerBaseAbstract<C> sbb = new SolvableGroebnerBaseSeq();
        logger.warn("intersect computing GB");
        List g = sbb.leftGB(c);
        if (debug) {
            logger.debug("intersect GB = " + g);
        }
        return PolyUtil.intersect((GenSolvablePolynomialRing) pfac, g);
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C> syzLcm(GenSolvablePolynomialRing<C> r, GenSolvablePolynomial<C> n, GenSolvablePolynomial<C> d) {
        if (n.isZERO()) {
            return n;
        }
        if (d.isZERO()) {
            return d;
        }
        if (n.isONE()) {
            return d;
        }
        if (d.isONE()) {
            return n;
        }
        List A = new ArrayList(1);
        A.add(n);
        List B = new ArrayList(1);
        B.add(d);
        List<GenSolvablePolynomial<C>> c = intersect((GenSolvablePolynomialRing) r, A, B);
        GenSolvablePolynomial<C> lcm = null;
        for (GenSolvablePolynomial<C> p : c) {
            if (!(p == null || p.isZERO())) {
                if (lcm == null) {
                    lcm = p;
                } else if (lcm.compareTo((GenPolynomial) p) > 0) {
                    lcm = p;
                }
            }
        }
        if (lcm != null) {
            return lcm;
        }
        throw new RuntimeException("this cannot happen: lcm == null: " + c);
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C> syzGcd(GenSolvablePolynomialRing<C> r, GenSolvablePolynomial<C> n, GenSolvablePolynomial<C> d) {
        if (n.isZERO()) {
            return d;
        }
        if (d.isZERO()) {
            return n;
        }
        if (n.isConstant()) {
            return r.getONE();
        }
        if (d.isConstant()) {
            return r.getONE();
        }
        if (n.totalDegree() > 3 || d.totalDegree() > 3) {
            logger.warn("skipping GB computation: degs = " + n.totalDegree() + ", " + d.totalDegree());
            return r.getONE();
        }
        List<GenSolvablePolynomial<C>> A = new ArrayList(2);
        A.add(n);
        A.add(d);
        SolvableGroebnerBaseAbstract<C> sbb = new SolvableGroebnerBaseSeq();
        logger.warn("syzGcd computing GB: " + A);
        List<GenSolvablePolynomial<C>> G = sbb.rightGB(A);
        if (logger.isDebugEnabled()) {
            logger.info("G = " + G);
        }
        if (G.size() == 1) {
            return (GenSolvablePolynomial) G.get(0);
        }
        logger.warn("gcd not determined, set to 1: " + G);
        return r.getONE();
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C>[] quotientRemainder(GenSolvablePolynomial<C> n, GenSolvablePolynomial<C> d) {
        GenSolvablePolynomial[] res = (GenSolvablePolynomial[]) new GenSolvablePolynomial[2];
        if (d.isZERO()) {
            throw new RuntimeException("division by zero: " + n + "/" + d);
        }
        if (n.isZERO()) {
            res[0] = n;
            res[1] = n;
        } else {
            GenSolvablePolynomialRing<C> r = n.ring;
            if (d.isONE()) {
                res[0] = n;
                res[1] = r.getZERO();
            } else {
                List<GenSolvablePolynomial<C>> Q = new ArrayList(1);
                Q.add(r.getZERO());
                List<GenSolvablePolynomial<C>> D = new ArrayList(1);
                D.add(d);
                res[1] = new SolvableReductionSeq().rightNormalform(Q, D, n);
                res[0] = (GenSolvablePolynomial) Q.get(0);
            }
        }
        return res;
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C>[] syzGcdCofactors(GenSolvablePolynomialRing<C> r, GenSolvablePolynomial<C> n, GenSolvablePolynomial<C> d) {
        GenSolvablePolynomial[] res = (GenSolvablePolynomial[]) new GenSolvablePolynomial[3];
        res[0] = syzGcd((GenSolvablePolynomialRing) r, (GenSolvablePolynomial) n, (GenSolvablePolynomial) d);
        res[1] = n;
        res[2] = d;
        if (!res[0].isONE()) {
            GenSolvablePolynomial<C>[] nqr = quotientRemainder(n, res[0]);
            if (nqr[1].isZERO()) {
                GenSolvablePolynomial<C>[] dqr = quotientRemainder(d, res[0]);
                if (dqr[1].isZERO()) {
                    res[1] = nqr[0];
                    res[2] = dqr[0];
                } else {
                    res[0] = r.getONE();
                }
            } else {
                res[0] = r.getONE();
            }
        }
        return res;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<C> syzLcm(GenPolynomialRing<C> r, GenPolynomial<C> n, GenPolynomial<C> d) {
        List A = new ArrayList(1);
        A.add(n);
        List B = new ArrayList(1);
        B.add(d);
        List<GenPolynomial<C>> c = intersect((GenPolynomialRing) r, A, B);
        if (c.size() != 1) {
            logger.warn("lcm not uniqe: " + c);
        }
        return (GenPolynomial) c.get(0);
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<C> syzGcd(GenPolynomialRing<C> r, GenPolynomial<C> n, GenPolynomial<C> d) {
        if (n.isZERO()) {
            return d;
        }
        if (d.isZERO()) {
            return n;
        }
        if (n.isONE()) {
            return n;
        }
        if (d.isONE()) {
            return d;
        }
        return PolyUtil.basePseudoDivide(n.multiply((GenPolynomial) d), syzLcm((GenPolynomialRing) r, (GenPolynomial) n, (GenPolynomial) d));
    }
}
