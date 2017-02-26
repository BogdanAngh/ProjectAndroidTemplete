package edu.jas.fd;

import edu.jas.gbmod.SolvableSyzygyAbstract;
import edu.jas.gbmod.SolvableSyzygySeq;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.RecSolvablePolynomial;
import edu.jas.poly.RecSolvablePolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class GreatestCommonDivisorAbstract<C extends GcdRingElem<C>> implements GreatestCommonDivisor<C> {
    private static final Logger logger;
    private final boolean debug;
    final SolvableSyzygyAbstract<C> syz;

    public abstract GenSolvablePolynomial<C> leftBaseGcd(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    public abstract GenSolvablePolynomial<GenPolynomial<C>> leftRecursiveUnivariateGcd(GenSolvablePolynomial<GenPolynomial<C>> genSolvablePolynomial, GenSolvablePolynomial<GenPolynomial<C>> genSolvablePolynomial2);

    public abstract GenSolvablePolynomial<C> rightBaseGcd(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    public abstract GenSolvablePolynomial<GenPolynomial<C>> rightRecursiveUnivariateGcd(GenSolvablePolynomial<GenPolynomial<C>> genSolvablePolynomial, GenSolvablePolynomial<GenPolynomial<C>> genSolvablePolynomial2);

    static {
        logger = Logger.getLogger(GreatestCommonDivisorAbstract.class);
    }

    public GreatestCommonDivisorAbstract(RingFactory<C> cf) {
        this.debug = true;
        this.syz = new SolvableSyzygySeq(cf);
    }

    public String toString() {
        return getClass().getName();
    }

    public C leftBaseContent(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return (GcdRingElem) P.ring.getZEROCoefficient();
        } else {
            C d = null;
            for (C c : P.getMap().values()) {
                if (d == null) {
                    d = c;
                } else {
                    GcdRingElem d2 = (GcdRingElem) d.gcd(c);
                }
                if (d.isONE()) {
                    return d;
                }
            }
            if (d.signum() < 0) {
                return (GcdRingElem) d.negate();
            }
            return d;
        }
    }

    public C rightBaseContent(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return (GcdRingElem) P.ring.getZEROCoefficient();
        } else {
            C d = null;
            for (C c : P.getMap().values()) {
                if (d == null) {
                    d = c;
                } else {
                    GcdRingElem d2 = (GcdRingElem) d.gcd(c);
                }
                if (d.isONE()) {
                    return d;
                }
            }
            if (d.signum() < 0) {
                return (GcdRingElem) d.negate();
            }
            return d;
        }
    }

    public GenSolvablePolynomial<C> leftBasePrimitivePart(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return P;
        } else {
            RingElem d = leftBaseContent(P);
            if (d.isONE()) {
                return P;
            }
            GenSolvablePolynomial<C> pp = (GenSolvablePolynomial) P.divide(d);
            if (pp.multiply(d).equals(P)) {
                return pp;
            }
            throw new ArithmeticException("pp(p)*cont(p) != p: ");
        }
    }

    public GenSolvablePolynomial<C> rightBasePrimitivePart(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return P;
        } else {
            RingElem d = rightBaseContent(P);
            if (d.isONE()) {
                return P;
            }
            GenSolvablePolynomial<C> pp = (GenSolvablePolynomial) P.divide(d);
            if (pp.multiplyLeft(d).equals(P)) {
                return pp;
            }
            throw new ArithmeticException("pp(p)*cont(p) != p: ");
        }
    }

    public GenSolvablePolynomial<C> recursiveContent(GenSolvablePolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if ((P instanceof RecSolvablePolynomial) && !P.ring.coeffTable.isEmpty()) {
            throw new IllegalArgumentException("P is a RecSolvablePolynomial, use recursiveContent()");
        } else if (P.isZERO()) {
            return (GenSolvablePolynomial) P.ring.getZEROCoefficient();
        } else {
            if (P.isONE()) {
                return (GenSolvablePolynomial) P.ring.getONECoefficient();
            }
            if (((GenPolynomial) P.leadingBaseCoefficient()).isONE()) {
                return (GenSolvablePolynomial) P.ring.getONECoefficient();
            }
            GenSolvablePolynomial<C> d = null;
            for (GenPolynomial<C> c : P.getMap().values()) {
                GenSolvablePolynomial<C> c2 = (GenSolvablePolynomial) c;
                if (d == null) {
                    d = c2;
                } else {
                    d = leftGcd(d, c2);
                }
                if (d.isONE()) {
                    return d;
                }
            }
            return (GenSolvablePolynomial) d.abs();
        }
    }

    public GenSolvablePolynomial<C> rightRecursiveContent(GenSolvablePolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P != null");
        } else if (P.isZERO()) {
            return (GenSolvablePolynomial) P.ring.getZEROCoefficient();
        } else {
            if (((GenPolynomial) P.leadingBaseCoefficient()).isONE()) {
                return (GenSolvablePolynomial) P.ring.getONECoefficient();
            }
            GenSolvablePolynomial<C> d = null;
            GenSolvablePolynomial<GenPolynomial<C>> Pr = P.rightRecursivePolynomial();
            logger.info("RI-recCont: P = " + P + ", right(P) = " + Pr);
            for (GenPolynomial<C> cs : Pr.getMap().values()) {
                GenSolvablePolynomial<C> cs2 = (GenSolvablePolynomial) cs;
                if (d == null) {
                    d = cs2;
                } else {
                    d = leftGcd(d, cs2);
                    logger.info("RI-recCont: cs = " + cs2 + ", d = " + d);
                }
                if (d.isONE()) {
                    return d;
                }
            }
            return (GenSolvablePolynomial) d.abs();
        }
    }

    public GenSolvablePolynomial<GenPolynomial<C>> rightRecursivePrimitivePart(GenSolvablePolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenSolvablePolynomial<C> d = rightRecursiveContent(P);
            if (d.isONE()) {
                return P;
            }
            return FDUtil.recursiveDivideRightEval(P, d);
        }
    }

    public GenSolvablePolynomial<C> leftRecursiveContent(GenSolvablePolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P != null");
        } else if (P.isZERO()) {
            return (GenSolvablePolynomial) P.ring.getZEROCoefficient();
        } else {
            if (((GenPolynomial) P.leadingBaseCoefficient()).isONE()) {
                return (GenSolvablePolynomial) P.ring.getONECoefficient();
            }
            GenSolvablePolynomial<C> d = null;
            GenSolvablePolynomial<GenPolynomial<C>> Pr = P;
            logger.info("recCont: P = " + P + ", right(P) = " + Pr);
            for (GenPolynomial<C> cs : Pr.getMap().values()) {
                GenSolvablePolynomial<C> cs2 = (GenSolvablePolynomial) cs;
                if (d == null) {
                    d = cs2;
                } else {
                    d = rightGcd(d, cs2);
                    logger.info("recCont: cs = " + cs2 + ", d = " + d);
                }
                if (d.isONE()) {
                    return d;
                }
            }
            return (GenSolvablePolynomial) d.abs();
        }
    }

    public GenSolvablePolynomial<GenPolynomial<C>> leftRecursivePrimitivePart(GenSolvablePolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenSolvablePolynomial<C> d = leftRecursiveContent(P);
            if (d.isONE()) {
                return P;
            }
            return FDUtil.recursiveDivide(P, d);
        }
    }

    public C baseRecursiveContent(GenSolvablePolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return (GcdRingElem) P.ring.coFac.coFac.getZERO();
        } else {
            C d = null;
            for (GenPolynomial<C> c : P.getMap().values()) {
                C cc = leftBaseContent((GenSolvablePolynomial) c);
                if (d == null) {
                    d = cc;
                } else {
                    d = gcd(d, cc);
                }
                if (d.isONE()) {
                    return d;
                }
            }
            return (GcdRingElem) d.abs();
        }
    }

    public GenSolvablePolynomial<GenPolynomial<C>> baseRecursivePrimitivePart(GenSolvablePolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return P;
        } else {
            C d = baseRecursiveContent(P);
            if (d.isONE()) {
                return P;
            }
            return (GenSolvablePolynomial) PolyUtil.baseRecursiveDivide(P, d);
        }
    }

    public GenSolvablePolynomial<GenPolynomial<C>> leftRecursiveGcd(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar == 1) {
            return leftRecursiveUnivariateGcd(P, S);
        }
        GenPolynomialRing dfac;
        GenPolynomialRing rfac = P.ring;
        if (rfac instanceof RecSolvablePolynomialRing) {
            dfac = RecSolvablePolynomialRing.distribute((RecSolvablePolynomialRing) rfac);
        } else {
            dfac = rfac.distribute();
        }
        return (GenSolvablePolynomial) PolyUtil.recursive(rfac, leftGcd((GenSolvablePolynomial) PolyUtil.distribute(dfac, (GenPolynomial) P), (GenSolvablePolynomial) PolyUtil.distribute(dfac, (GenPolynomial) S)));
    }

    public GenSolvablePolynomial<GenPolynomial<C>> rightRecursiveGcd(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar == 1) {
            return rightRecursiveUnivariateGcd(P, S);
        }
        GenPolynomialRing dfac;
        GenPolynomialRing rfac = P.ring;
        if (rfac instanceof RecSolvablePolynomialRing) {
            dfac = RecSolvablePolynomialRing.distribute((RecSolvablePolynomialRing) rfac);
        } else {
            dfac = rfac.distribute();
        }
        return (GenSolvablePolynomial) PolyUtil.recursive(rfac, rightGcd((GenSolvablePolynomial) PolyUtil.distribute(dfac, (GenPolynomial) P), (GenSolvablePolynomial) PolyUtil.distribute(dfac, (GenPolynomial) S)));
    }

    public GenSolvablePolynomial<C> rightContent(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        }
        GenSolvablePolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            return rightRecursiveContent((RecSolvablePolynomial) PolyUtil.recursive(pfac.recursive(1), (GenPolynomial) P));
        }
        throw new IllegalArgumentException("use baseContent() for univariate polynomials");
    }

    public GenSolvablePolynomial<C> rightPrimitivePart(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomialRing pfac = P.ring;
            if (pfac.nvar <= 1) {
                return leftBasePrimitivePart(P);
            }
            return (GenSolvablePolynomial) PolyUtil.distribute(pfac, rightRecursivePrimitivePart((RecSolvablePolynomial) PolyUtil.recursive(pfac.recursive(1), (GenPolynomial) P)));
        }
    }

    public GenSolvablePolynomial<C> leftContent(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        }
        GenSolvablePolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            return leftRecursiveContent((RecSolvablePolynomial) PolyUtil.recursive(pfac.recursive(1), (GenPolynomial) P));
        }
        throw new IllegalArgumentException("use baseContent() for univariate polynomials");
    }

    public GenSolvablePolynomial<C> leftPrimitivePart(GenSolvablePolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomialRing pfac = P.ring;
            if (pfac.nvar <= 1) {
                return leftBasePrimitivePart(P);
            }
            return (GenSolvablePolynomial) PolyUtil.distribute(pfac, leftRecursivePrimitivePart((RecSolvablePolynomial) PolyUtil.recursive(pfac.recursive(1), (GenPolynomial) P)));
        }
    }

    public GenSolvablePolynomial<C> divide(GenSolvablePolynomial<C> a, C b) {
        if (b == null || b.isZERO()) {
            throw new IllegalArgumentException("division by zero");
        } else if (a == null || a.isZERO()) {
            return a;
        } else {
            return (GenSolvablePolynomial) a.divide((RingElem) b);
        }
    }

    public C gcd(C a, C b) {
        if (b == null || b.isZERO()) {
            return a;
        }
        if (a == null || a.isZERO()) {
            return b;
        }
        return (GcdRingElem) a.gcd(b);
    }

    public GenSolvablePolynomial<C> leftGcd(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.isONE()) {
            return P;
        }
        if (S.isONE()) {
            return S;
        }
        GenPolynomialRing pfac = P.ring;
        if (pfac.nvar <= 1) {
            return leftBaseGcd(P, S);
        }
        GenPolynomialRing rfac = pfac.recursive(1);
        return (GenSolvablePolynomial) PolyUtil.distribute(pfac, leftRecursiveUnivariateGcd((RecSolvablePolynomial) PolyUtil.recursive(rfac, (GenPolynomial) P), (RecSolvablePolynomial) PolyUtil.recursive(rfac, (GenPolynomial) S)));
    }

    public GenSolvablePolynomial<C> leftLcm(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        return leftOreCond((GenSolvablePolynomial) P, (GenSolvablePolynomial) S)[0].multiply((GenSolvablePolynomial) P);
    }

    public GenSolvablePolynomial<C> rightGcd(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.isONE()) {
            return P;
        }
        if (S.isONE()) {
            return S;
        }
        GenPolynomialRing pfac = P.ring;
        if (pfac.nvar <= 1) {
            return rightBaseGcd(P, S);
        }
        GenPolynomialRing rfac = pfac.recursive(1);
        return (GenSolvablePolynomial) PolyUtil.distribute(pfac, rightRecursiveUnivariateGcd((RecSolvablePolynomial) PolyUtil.recursive(rfac, (GenPolynomial) P), (RecSolvablePolynomial) PolyUtil.recursive(rfac, (GenPolynomial) S)));
    }

    public GenSolvablePolynomial<C> rightLcm(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        return P.multiply(rightOreCond((GenSolvablePolynomial) P, (GenSolvablePolynomial) S)[0]);
    }

    public GenSolvablePolynomial<C> leftGcd(List<GenSolvablePolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            throw new IllegalArgumentException("A may not be empty");
        }
        GenSolvablePolynomial<C> g = (GenSolvablePolynomial) A.get(0);
        for (int i = 1; i < A.size(); i++) {
            g = leftGcd(g, (GenSolvablePolynomial) A.get(i));
        }
        return g;
    }

    public List<GenSolvablePolynomial<C>> leftCoPrime(List<GenSolvablePolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            return A;
        }
        List<GenSolvablePolynomial<C>> B = new ArrayList(A.size());
        GenSolvablePolynomial<C> a = (GenSolvablePolynomial) A.get(0);
        if (a.isZERO() || a.isConstant()) {
            B.addAll(A.subList(1, A.size()));
        } else {
            for (int i = 1; i < A.size(); i++) {
                GenSolvablePolynomial<C> b = (GenSolvablePolynomial) A.get(i);
                GenSolvablePolynomial<C> g = (GenSolvablePolynomial) leftGcd(a, b).abs();
                if (!g.isONE()) {
                    a = FDUtil.leftBasePseudoQuotient(a, g);
                    b = FDUtil.leftBasePseudoQuotient(b, g);
                    GenSolvablePolynomial<C> gp = leftGcd(a, g);
                    while (!gp.isONE()) {
                        a = FDUtil.leftBasePseudoQuotient(a, gp);
                        B.add(FDUtil.leftBasePseudoQuotient(g, gp));
                        g = gp;
                        gp = leftGcd(a, gp);
                    }
                    if (!(g.isZERO() || g.isConstant())) {
                        B.add(g);
                    }
                }
                if (!(b.isZERO() || b.isConstant())) {
                    B.add(b);
                }
            }
        }
        B = leftCoPrime(B);
        if (a.isZERO() || a.isConstant()) {
            return B;
        }
        B.add((GenSolvablePolynomial) a.abs());
        return B;
    }

    public List<GenSolvablePolynomial<C>> leftCoPrimeRec(List<GenSolvablePolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            return A;
        }
        List<GenSolvablePolynomial<C>> B = new ArrayList();
        for (GenSolvablePolynomial<C> a : A) {
            B = leftCoPrime(a, B);
        }
        return B;
    }

    public List<GenSolvablePolynomial<C>> leftCoPrime(GenSolvablePolynomial<C> a, List<GenSolvablePolynomial<C>> P) {
        if (a == null || a.isZERO() || a.isConstant()) {
            return P;
        }
        List<GenSolvablePolynomial<C>> B = new ArrayList(P.size() + 1);
        for (int i = 0; i < P.size(); i++) {
            GenSolvablePolynomial<C> b = (GenSolvablePolynomial) P.get(i);
            GenSolvablePolynomial<C> g = leftGcd(a, b);
            if (!g.isONE()) {
                a = FDUtil.leftBasePseudoQuotient(a, g);
                b = FDUtil.leftBasePseudoQuotient(b, g);
                GenSolvablePolynomial<C> gp = leftGcd(a, g);
                while (!gp.isONE()) {
                    a = FDUtil.leftBasePseudoQuotient(a, gp);
                    g = FDUtil.leftBasePseudoQuotient(g, gp);
                    if (!(g.isZERO() || g.isConstant())) {
                        B.add(g);
                    }
                    g = gp;
                    gp = leftGcd(a, gp);
                }
                gp = leftGcd(b, g);
                while (!gp.isONE()) {
                    b = FDUtil.leftBasePseudoQuotient(b, gp);
                    g = FDUtil.leftBasePseudoQuotient(g, gp);
                    if (!(g.isZERO() || g.isConstant())) {
                        B.add(g);
                    }
                    g = gp;
                    gp = leftGcd(b, gp);
                }
                if (!(g.isZERO() || g.isConstant())) {
                    B.add(g);
                }
            }
            if (!(b.isZERO() || b.isConstant())) {
                B.add(b);
            }
        }
        if (a.isZERO() || a.isConstant()) {
            return B;
        }
        B.add(a);
        return B;
    }

    public boolean isLeftCoPrime(List<GenSolvablePolynomial<C>> A) {
        if (A == null || A.isEmpty() || A.size() == 1) {
            return true;
        }
        for (int i = 0; i < A.size(); i++) {
            GenSolvablePolynomial<C> a = (GenSolvablePolynomial) A.get(i);
            int j = i + 1;
            while (j < A.size()) {
                GenSolvablePolynomial<C> b = (GenSolvablePolynomial) A.get(j);
                GenSolvablePolynomial<C> g = leftGcd(a, b);
                if (g.isONE()) {
                    j++;
                } else {
                    System.out.println("not co-prime, a: " + a);
                    System.out.println("not co-prime, b: " + b);
                    System.out.println("not co-prime, g: " + g);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLeftCoPrime(List<GenSolvablePolynomial<C>> P, List<GenSolvablePolynomial<C>> A) {
        if (!isLeftCoPrime(P)) {
            return false;
        }
        if (A == null || A.isEmpty()) {
            return true;
        }
        for (GenSolvablePolynomial<C> q : A) {
            if (!(q.isZERO() || q.isConstant())) {
                boolean divides = false;
                for (GenSolvablePolynomial<C> p : P) {
                    if (FDUtil.leftBaseSparsePseudoRemainder(q, p).isZERO()) {
                        divides = true;
                        break;
                    }
                }
                if (!divides) {
                    System.out.println("no divisor for: " + q);
                    return false;
                }
            }
        }
        return true;
    }

    public GenSolvablePolynomial<C>[] baseExtendedGcd(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        GenSolvablePolynomial<C>[] hegcd = baseHalfExtendedGcd(P, S);
        GenSolvablePolynomial[] ret = (GenSolvablePolynomial[]) new GenSolvablePolynomial[3];
        ret[0] = hegcd[0];
        ret[1] = hegcd[1];
        ret[2] = FDUtil.leftBasePseudoQuotientRemainder((GenSolvablePolynomial) hegcd[0].subtract(hegcd[1].multiply((GenSolvablePolynomial) P)), S)[0];
        return ret;
    }

    public GenSolvablePolynomial<C>[] baseHalfExtendedGcd(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (P == null || S == null) {
            throw new IllegalArgumentException("null P or S not allowed");
        }
        GenSolvablePolynomial[] ret = (GenSolvablePolynomial[]) new GenSolvablePolynomial[2];
        ret[0] = null;
        ret[1] = null;
        if (S == null || S.isZERO()) {
            ret[0] = P;
            ret[1] = P.ring.getONE();
        } else if (P == null || P.isZERO()) {
            ret[0] = S;
            ret[1] = S.ring.getZERO();
        } else if (P.ring.nvar != 1) {
            throw new IllegalArgumentException("for univariate polynomials only " + P.ring);
        } else {
            GenSolvablePolynomial<C> q = P;
            GenSolvablePolynomial<C> r = S;
            GenSolvablePolynomial<C> c1 = P.ring.getONE().copy();
            GenSolvablePolynomial<C> d1 = P.ring.getZERO().copy();
            while (!r.isZERO()) {
                GenSolvablePolynomial<C>[] qr = FDUtil.leftBasePseudoQuotientRemainder(q, r);
                c1 = d1;
                d1 = (GenSolvablePolynomial) c1.subtract(qr[0].multiply((GenSolvablePolynomial) d1));
                q = r;
                r = qr[1];
            }
            GcdRingElem g = (GcdRingElem) q.leadingBaseCoefficient();
            if (g.isUnit()) {
                RingElem h = (GcdRingElem) g.inverse();
                q = q.multiply(h);
                c1 = c1.multiply(h);
            }
            ret[0] = q;
            ret[1] = c1;
        }
        return ret;
    }

    public GenSolvablePolynomial<C>[] baseGcdDiophant(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S, GenSolvablePolynomial<C> c) {
        GenSolvablePolynomial<C>[] egcd = baseExtendedGcd(P, S);
        GenSolvablePolynomial<C> g = egcd[0];
        GenSolvablePolynomial<C>[] qr = FDUtil.leftBasePseudoQuotientRemainder(c, g);
        if (qr[1].isZERO()) {
            GenSolvablePolynomial q = qr[0];
            GenSolvablePolynomial<C> a = egcd[1].multiply(q);
            GenSolvablePolynomial<C> b = egcd[2].multiply(q);
            if (!a.isZERO() && a.degree(0) >= S.degree(0)) {
                qr = FDUtil.leftBasePseudoQuotientRemainder(a, S);
                a = qr[1];
                b = (GenSolvablePolynomial) b.sum(P.multiply(qr[0]));
            }
            GenSolvablePolynomial[] ret = (GenSolvablePolynomial[]) new GenSolvablePolynomial[2];
            ret[0] = a;
            ret[1] = b;
            GenSolvablePolynomial<C> y = (GenSolvablePolynomial) ret[0].multiply((GenSolvablePolynomial) P).sum(ret[1].multiply((GenSolvablePolynomial) S));
            if (y.equals(c)) {
                return ret;
            }
            System.out.println("P  = " + P);
            System.out.println("S  = " + S);
            System.out.println("c  = " + c);
            System.out.println("a  = " + a);
            System.out.println("b  = " + b);
            System.out.println("y  = " + y);
            throw new ArithmeticException("not diophant, x = " + y.subtract((GenPolynomial) c));
        }
        throw new ArithmeticException("not solvable, r = " + qr[1] + ", c = " + c + ", g = " + g);
    }

    public C[] leftOreCond(C a, C b) {
        if (a == null || a.isZERO() || b == null || b.isZERO()) {
            throw new IllegalArgumentException("a and b must be non zero");
        }
        GcdRingElem[] oc = (GcdRingElem[]) new GcdRingElem[2];
        if ((a instanceof GenSolvablePolynomial) && (b instanceof GenSolvablePolynomial)) {
            GenSolvablePolynomial[] ocp = leftOreCond((GenSolvablePolynomial) a, (GenSolvablePolynomial) b);
            oc[0] = (GcdRingElem) ocp[0];
            oc[1] = (GcdRingElem) ocp[1];
        } else {
            RingFactory<C> rf = (RingFactory) a.factory();
            if (a.equals(b)) {
                oc[0] = (GcdRingElem) rf.getONE();
                oc[1] = (GcdRingElem) rf.getONE();
            } else if (a.equals(b.negate())) {
                oc[0] = (GcdRingElem) rf.getONE();
                oc[1] = (GcdRingElem) ((GcdRingElem) rf.getONE()).negate();
            } else if (rf.isCommutative()) {
                logger.info("left Ore condition on coefficients, commutative case: " + a + ", " + b);
                GcdRingElem gcd = (GcdRingElem) a.gcd(b);
                if (gcd.isONE()) {
                    oc[0] = b;
                    oc[1] = a;
                } else {
                    GcdRingElem lcm = (GcdRingElem) ((GcdRingElem) a.multiply(b)).divide(gcd);
                    oc[0] = (GcdRingElem) lcm.divide(a);
                    oc[1] = (GcdRingElem) lcm.divide(b);
                    logger.info("Ore multiple: lcm=" + lcm + ", gcd=" + gcd + ", " + Arrays.toString(oc));
                }
            } else if (rf.isField()) {
                logger.info("left Ore condition on coefficients, field case: " + a + ", " + b);
                oc[0] = (GcdRingElem) a.inverse();
                oc[1] = (GcdRingElem) b.inverse();
                logger.info("Ore multiple: " + Arrays.toString(oc));
            } else {
                throw new UnsupportedOperationException("leftOreCond not implemented for " + rf.getClass() + ", rf = " + rf.toScript());
            }
        }
        return oc;
    }

    public GenSolvablePolynomial<C>[] leftOreCond(GenSolvablePolynomial<C> a, GenSolvablePolynomial<C> b) {
        if (a == null || a.isZERO() || b == null || b.isZERO()) {
            throw new IllegalArgumentException("a and b must be non zero");
        }
        GenSolvablePolynomialRing<C> pfac = a.ring;
        GenSolvablePolynomial<C>[] oc = (GenSolvablePolynomial[]) new GenSolvablePolynomial[2];
        if (a.equals(b)) {
            oc[0] = pfac.getONE();
            oc[1] = pfac.getONE();
            return oc;
        } else if (a.equals(b.negate())) {
            oc[0] = pfac.getONE();
            oc[1] = (GenSolvablePolynomial) pfac.getONE().negate();
            return oc;
        } else if (!pfac.isCommutative()) {
            return this.syz.leftOreCond(a, b);
        } else {
            logger.info("left Ore condition, polynomial commutative case: " + a + ", " + b);
            GenSolvablePolynomial<C> lcm = (GenSolvablePolynomial) GCDFactory.getImplementation(pfac.coFac).lcm(a, b);
            oc[0] = (GenSolvablePolynomial) PolyUtil.basePseudoDivide(lcm, a);
            oc[1] = (GenSolvablePolynomial) PolyUtil.basePseudoDivide(lcm, b);
            logger.info("Ore multiple: " + lcm + ", " + Arrays.toString(oc));
            return oc;
        }
    }

    public C[] rightOreCond(C a, C b) {
        if (a == null || a.isZERO() || b == null || b.isZERO()) {
            throw new IllegalArgumentException("a and b must be non zero");
        }
        GcdRingElem[] oc = (GcdRingElem[]) new GcdRingElem[2];
        if ((a instanceof GenSolvablePolynomial) && (b instanceof GenSolvablePolynomial)) {
            GenSolvablePolynomial[] ocp = rightOreCond((GenSolvablePolynomial) a, (GenSolvablePolynomial) b);
            oc[0] = (GcdRingElem) ocp[0];
            oc[1] = (GcdRingElem) ocp[1];
        } else {
            RingFactory<C> rf = (RingFactory) a.factory();
            if (a.equals(b)) {
                oc[0] = (GcdRingElem) rf.getONE();
                oc[1] = (GcdRingElem) rf.getONE();
            } else if (a.equals(b.negate())) {
                oc[0] = (GcdRingElem) rf.getONE();
                oc[1] = (GcdRingElem) ((GcdRingElem) rf.getONE()).negate();
            } else if (rf.isCommutative()) {
                logger.info("right Ore condition on coefficients, commutative case: " + a + ", " + b);
                GcdRingElem gcd = (GcdRingElem) a.gcd(b);
                if (gcd.isONE()) {
                    oc[0] = b;
                    oc[1] = a;
                } else {
                    GcdRingElem lcm = (GcdRingElem) ((GcdRingElem) a.multiply(b)).divide(gcd);
                    oc[0] = (GcdRingElem) lcm.divide(a);
                    oc[1] = (GcdRingElem) lcm.divide(b);
                    logger.info("Ore multiple: " + lcm + ", " + Arrays.toString(oc));
                }
            } else if (rf.isField()) {
                logger.info("right Ore condition on coefficients, field case: " + a + ", " + b);
                oc[0] = (GcdRingElem) a.inverse();
                oc[1] = (GcdRingElem) b.inverse();
                logger.info("Ore multiple: " + Arrays.toString(oc));
            } else {
                throw new UnsupportedOperationException("rightOreCond not implemented for " + rf.getClass() + ", rf = " + rf.toScript());
            }
        }
        return oc;
    }

    public GenSolvablePolynomial<C>[] rightOreCond(GenSolvablePolynomial<C> a, GenSolvablePolynomial<C> b) {
        if (a == null || a.isZERO() || b == null || b.isZERO()) {
            throw new IllegalArgumentException("a and b must be non zero");
        }
        GenSolvablePolynomialRing<C> pfac = a.ring;
        GenSolvablePolynomial<C>[] oc = (GenSolvablePolynomial[]) new GenSolvablePolynomial[2];
        if (a.equals(b)) {
            oc[0] = pfac.getONE();
            oc[1] = pfac.getONE();
            return oc;
        } else if (a.equals(b.negate())) {
            oc[0] = pfac.getONE();
            oc[1] = (GenSolvablePolynomial) pfac.getONE().negate();
            return oc;
        } else if (!pfac.isCommutative()) {
            return this.syz.rightOreCond(a, b);
        } else {
            logger.info("right Ore condition, polynomial commutative case: " + a + ", " + b);
            GenSolvablePolynomial<C> lcm = (GenSolvablePolynomial) GCDFactory.getImplementation(pfac.coFac).lcm(a, b);
            oc[0] = (GenSolvablePolynomial) PolyUtil.basePseudoDivide(lcm, a);
            oc[1] = (GenSolvablePolynomial) PolyUtil.basePseudoDivide(lcm, b);
            logger.info("Ore multiple: " + lcm + ", " + Arrays.toString(oc));
            return oc;
        }
    }
}
