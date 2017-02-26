package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class GreatestCommonDivisorAbstract<C extends GcdRingElem<C>> implements GreatestCommonDivisor<C> {
    private static final Logger logger;
    private final boolean debug;

    public abstract GenPolynomial<C> baseGcd(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    public abstract GenPolynomial<GenPolynomial<C>> recursiveUnivariateGcd(GenPolynomial<GenPolynomial<C>> genPolynomial, GenPolynomial<GenPolynomial<C>> genPolynomial2);

    public GreatestCommonDivisorAbstract() {
        this.debug = logger.isDebugEnabled();
    }

    static {
        logger = Logger.getLogger(GreatestCommonDivisorAbstract.class);
    }

    public String toString() {
        return getClass().getName();
    }

    public C baseContent(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
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

    public GenPolynomial<C> basePrimitivePart(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            RingElem d = baseContent(P);
            if (d.isONE()) {
                return P;
            }
            GenPolynomial<C> pp = P.divide(d);
            if (!this.debug || pp.multiply(d).equals(P)) {
                return pp;
            }
            throw new ArithmeticException("pp(p)*cont(p) != p: ");
        }
    }

    public List<GenPolynomial<C>> basePrimitivePart(List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        List<GenPolynomial<C>> Pp = new ArrayList(F.size());
        for (GenPolynomial f : F) {
            Pp.add(basePrimitivePart(f));
        }
        return Pp;
    }

    public GenPolynomial<C> recursiveContent(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return (GenPolynomial) P.ring.getZEROCoefficient();
        } else {
            GenPolynomial d = null;
            for (GenPolynomial c : P.getMap().values()) {
                if (d == null) {
                    d = c;
                } else {
                    d = gcd(d, c);
                }
                if (d.isONE()) {
                    return d;
                }
            }
            return d.abs();
        }
    }

    public GenPolynomial<GenPolynomial<C>> recursivePrimitivePart(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomial d = recursiveContent(P);
            if (d.isONE()) {
                return P;
            }
            return PolyUtil.recursiveDivide((GenPolynomial) P, d);
        }
    }

    public List<GenPolynomial<GenPolynomial<C>>> recursivePrimitivePart(List<GenPolynomial<GenPolynomial<C>>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        List<GenPolynomial<GenPolynomial<C>>> Pp = new ArrayList(F.size());
        for (GenPolynomial f : F) {
            Pp.add(recursivePrimitivePart(f));
        }
        return Pp;
    }

    public C baseRecursiveContent(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return (GcdRingElem) P.ring.coFac.coFac.getZERO();
        } else {
            GcdRingElem d = null;
            for (GenPolynomial<C> c : P.getMap().values()) {
                GcdRingElem cc = baseContent(c);
                if (d == null) {
                    d = cc;
                } else {
                    d = gcd(d, cc);
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

    public GenPolynomial<GenPolynomial<C>> baseRecursivePrimitivePart(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            C d = baseRecursiveContent(P);
            if (d.isONE()) {
                return P;
            }
            return PolyUtil.baseRecursiveDivide(P, d);
        }
    }

    public GenPolynomial<GenPolynomial<C>> recursiveGcd(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar <= 1) {
            return recursiveUnivariateGcd(P, S);
        }
        GenPolynomialRing rfac = P.ring;
        GenPolynomialRing dfac = ((GenPolynomialRing) rfac.coFac).extend(rfac.nvar);
        return PolyUtil.recursive(rfac, gcd(PolyUtil.distribute(dfac, (GenPolynomial) P), PolyUtil.distribute(dfac, (GenPolynomial) S)));
    }

    public GenPolynomial<C> content(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            return recursiveContent(PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P));
        }
        throw new IllegalArgumentException(getClass().getName() + " use baseContent for univariate polynomials");
    }

    public GenPolynomial<C> primitivePart(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomialRing pfac = P.ring;
            if (pfac.nvar <= 1) {
                return basePrimitivePart((GenPolynomial) P);
            }
            return PolyUtil.distribute(pfac, recursivePrimitivePart(PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P)));
        }
    }

    public GenPolynomial<C> divide(GenPolynomial<C> a, C b) {
        if (b != null && !b.isZERO()) {
            return (a == null || a.isZERO()) ? a : a.divide((RingElem) b);
        } else {
            throw new IllegalArgumentException("division by zero");
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

    public GenPolynomial<C> gcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        GenPolynomialRing pfac = P.ring;
        if (pfac.nvar <= 1) {
            return baseGcd(P, S);
        }
        GenPolynomialRing rfac;
        RingFactory cfac = pfac.contract(1);
        if (pfac.getVars() == null || pfac.getVars().length <= 0) {
            rfac = new GenPolynomialRing(cfac, 1);
        } else {
            rfac = new GenPolynomialRing(cfac, 1, new String[]{pfac.getVars()[pfac.nvar - 1]});
        }
        return PolyUtil.distribute(pfac, recursiveUnivariateGcd(PolyUtil.recursive(rfac, (GenPolynomial) P), PolyUtil.recursive(rfac, (GenPolynomial) S)));
    }

    public GenPolynomial<C> lcm(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        return PolyUtil.basePseudoDivide(P.multiply((GenPolynomial) S), gcd((GenPolynomial) P, (GenPolynomial) S));
    }

    public GenPolynomial<C> gcd(List<GenPolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            throw new IllegalArgumentException("A may not be empty");
        }
        GenPolynomial g = (GenPolynomial) A.get(0);
        for (int i = 1; i < A.size(); i++) {
            g = gcd(g, (GenPolynomial) A.get(i));
        }
        return g;
    }

    public GenPolynomial<C> baseResultant(GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2) {
        throw new UnsupportedOperationException("not implmented");
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateResultant(GenPolynomial<GenPolynomial<C>> genPolynomial, GenPolynomial<GenPolynomial<C>> genPolynomial2) {
        throw new UnsupportedOperationException("not implmented");
    }

    public GenPolynomial<GenPolynomial<C>> recursiveResultant(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing rfac = P.ring;
        GenPolynomialRing dfac = rfac.coFac.extend(rfac.getVars());
        return PolyUtil.recursive(rfac, resultant(PolyUtil.distribute(dfac, (GenPolynomial) P), PolyUtil.distribute(dfac, (GenPolynomial) S)));
    }

    public GenPolynomial<C> resultant(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing pfac = P.ring;
        if (pfac.nvar <= 1) {
            return baseResultant(P, S);
        }
        GenPolynomialRing rfac = new GenPolynomialRing(pfac.contract(1), 1);
        return PolyUtil.distribute(pfac, recursiveUnivariateResultant(PolyUtil.recursive(rfac, (GenPolynomial) P), PolyUtil.recursive(rfac, (GenPolynomial) S)));
    }

    public List<GenPolynomial<C>> coPrime(List<GenPolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            return A;
        }
        List<GenPolynomial<C>> B = new ArrayList(A.size());
        GenPolynomial<C> a = (GenPolynomial) A.get(0);
        if (a.isZERO() || a.isConstant()) {
            B.addAll(A.subList(1, A.size()));
        } else {
            for (int i = 1; i < A.size(); i++) {
                GenPolynomial<C> b = (GenPolynomial) A.get(i);
                GenPolynomial<C> g = gcd((GenPolynomial) a, (GenPolynomial) b).abs();
                if (!g.isONE()) {
                    a = PolyUtil.basePseudoDivide(a, g);
                    b = PolyUtil.basePseudoDivide(b, g);
                    GenPolynomial gp = gcd((GenPolynomial) a, (GenPolynomial) g).abs();
                    while (!gp.isONE()) {
                        GenPolynomial a2 = PolyUtil.basePseudoDivide(a, gp);
                        B.add(PolyUtil.basePseudoDivide(g, gp));
                        g = gp;
                        gp = gcd(a2, gp).abs();
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
        B = coPrime(B);
        if (a.isZERO() || a.isConstant()) {
            return B;
        }
        B.add(a.abs());
        return B;
    }

    public List<GenPolynomial<C>> coPrimeRec(List<GenPolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            return A;
        }
        List<GenPolynomial<C>> B = new ArrayList();
        for (GenPolynomial<C> a : A) {
            B = coPrime(a, B);
        }
        return B;
    }

    public List<GenPolynomial<C>> coPrime(GenPolynomial<C> a, List<GenPolynomial<C>> P) {
        if (a == null || a.isZERO() || a.isConstant()) {
            return P;
        }
        List<GenPolynomial<C>> B = new ArrayList(P.size() + 1);
        for (int i = 0; i < P.size(); i++) {
            GenPolynomial<C> b = (GenPolynomial) P.get(i);
            GenPolynomial<C> g = gcd((GenPolynomial) a, (GenPolynomial) b).abs();
            if (!g.isONE()) {
                a = PolyUtil.basePseudoDivide(a, g);
                b = PolyUtil.basePseudoDivide(b, g);
                GenPolynomial gp = gcd((GenPolynomial) a, (GenPolynomial) g).abs();
                while (!gp.isONE()) {
                    GenPolynomial a2 = PolyUtil.basePseudoDivide(a, gp);
                    g = PolyUtil.basePseudoDivide(g, gp);
                    if (!(g.isZERO() || g.isConstant())) {
                        B.add(g);
                    }
                    g = gp;
                    gp = gcd(a2, gp).abs();
                }
                gp = gcd((GenPolynomial) b, (GenPolynomial) g).abs();
                while (!gp.isONE()) {
                    GenPolynomial b2 = PolyUtil.basePseudoDivide(b, gp);
                    g = PolyUtil.basePseudoDivide(g, gp);
                    if (!(g.isZERO() || g.isConstant())) {
                        B.add(g);
                    }
                    g = gp;
                    gp = gcd(b2, gp).abs();
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

    public boolean isCoPrime(List<GenPolynomial<C>> A) {
        if (A == null || A.isEmpty() || A.size() == 1) {
            return true;
        }
        for (int i = 0; i < A.size(); i++) {
            GenPolynomial a = (GenPolynomial) A.get(i);
            int j = i + 1;
            while (j < A.size()) {
                GenPolynomial b = (GenPolynomial) A.get(j);
                GenPolynomial<C> g = gcd(a, b);
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

    public boolean isCoPrime(List<GenPolynomial<C>> P, List<GenPolynomial<C>> A) {
        if (!isCoPrime(P)) {
            return false;
        }
        if (A == null || A.isEmpty()) {
            return true;
        }
        for (GenPolynomial<C> q : A) {
            if (!(q.isZERO() || q.isConstant())) {
                boolean divides = false;
                for (GenPolynomial<C> p : P) {
                    if (PolyUtil.baseSparsePseudoRemainder(q, p).isZERO()) {
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

    public GenPolynomial<C>[] baseExtendedGcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        GenPolynomial<C>[] hegcd = baseHalfExtendedGcd(P, S);
        GenPolynomial[] ret = (GenPolynomial[]) new GenPolynomial[3];
        ret[0] = hegcd[0];
        ret[1] = hegcd[1];
        ret[2] = PolyUtil.basePseudoQuotientRemainder(hegcd[0].subtract(hegcd[1].multiply((GenPolynomial) P)), S)[0];
        return ret;
    }

    public GenPolynomial<C>[] baseHalfExtendedGcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        GenPolynomial[] ret = (GenPolynomial[]) new GenPolynomial[2];
        ret[0] = null;
        ret[1] = null;
        if (S == null || S.isZERO()) {
            ret[0] = P;
            ret[1] = P.ring.getONE();
        } else if (P == null || P.isZERO()) {
            ret[0] = S;
            ret[1] = S.ring.getZERO();
        } else if (P.ring.nvar != 1) {
            throw new IllegalArgumentException(getClass().getName() + " not univariate polynomials " + P.ring);
        } else {
            GenPolynomial<C> q = P;
            GenPolynomial<C> r = S;
            GenPolynomial<C> c1 = P.ring.getONE().copy();
            GenPolynomial<C> d1 = P.ring.getZERO().copy();
            while (!r.isZERO()) {
                GenPolynomial<C>[] qr = PolyUtil.basePseudoQuotientRemainder(q, r);
                GenPolynomial<C> x = c1.subtract(qr[0].multiply((GenPolynomial) d1));
                c1 = d1;
                d1 = x;
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

    public GenPolynomial<C>[] baseGcdDiophant(GenPolynomial<C> P, GenPolynomial<C> S, GenPolynomial<C> c) {
        GenPolynomial<C>[] egcd = baseExtendedGcd(P, S);
        GenPolynomial<C> g = egcd[0];
        GenPolynomial<C>[] qr = PolyUtil.basePseudoQuotientRemainder(c, g);
        if (qr[1].isZERO()) {
            GenPolynomial q = qr[0];
            GenPolynomial<C> a = egcd[1].multiply(q);
            GenPolynomial<C> b = egcd[2].multiply(q);
            if (!a.isZERO() && a.degree(0) >= S.degree(0)) {
                qr = PolyUtil.basePseudoQuotientRemainder(a, S);
                a = qr[1];
                b = b.sum(P.multiply(qr[0]));
            }
            GenPolynomial[] ret = (GenPolynomial[]) new GenPolynomial[2];
            ret[0] = a;
            ret[1] = b;
            if (this.debug) {
                GenPolynomial<C> y = ret[0].multiply((GenPolynomial) P).sum(ret[1].multiply((GenPolynomial) S));
                if (!y.equals(c)) {
                    System.out.println("P  = " + P);
                    System.out.println("S  = " + S);
                    System.out.println("c  = " + c);
                    System.out.println("a  = " + a);
                    System.out.println("b  = " + b);
                    System.out.println("y  = " + y);
                    throw new ArithmeticException("not diophant, x = " + y.subtract((GenPolynomial) c));
                }
            }
            return ret;
        }
        throw new ArithmeticException("not solvable, r = " + qr[1] + ", c = " + c + ", g = " + g);
    }

    public GenPolynomial<C>[] basePartialFraction(GenPolynomial<C> A, GenPolynomial<C> P, GenPolynomial<C> S) {
        GenPolynomial[] ret = (GenPolynomial[]) new GenPolynomial[3];
        ret[0] = null;
        ret[1] = null;
        ret[2] = null;
        GenPolynomial<C>[] qr = PolyUtil.basePseudoQuotientRemainder(A, P.multiply((GenPolynomial) S));
        ret[0] = qr[0];
        GenPolynomial<C>[] diop = baseGcdDiophant(S, P, qr[1]);
        ret[1] = diop[0];
        ret[2] = diop[1];
        if (ret[1].degree(0) >= P.degree(0)) {
            qr = PolyUtil.basePseudoQuotientRemainder(ret[1], P);
            ret[0] = ret[0].sum(qr[0]);
            ret[1] = qr[1];
        }
        if (ret[2].degree(0) >= S.degree(0)) {
            qr = PolyUtil.basePseudoQuotientRemainder(ret[2], S);
            ret[0] = ret[0].sum(qr[0]);
            ret[2] = qr[1];
        }
        return ret;
    }

    public List<GenPolynomial<C>> basePartialFraction(GenPolynomial<C> A, GenPolynomial<C> P, int e) {
        if (A == null || P == null || e == 0) {
            throw new IllegalArgumentException("null A, P or e = 0 not allowed");
        }
        List<GenPolynomial<C>> pf = new ArrayList(e);
        if (A.isZERO()) {
            for (int i = 0; i < e; i++) {
                pf.add(A);
            }
        } else if (e == 1) {
            qr = PolyUtil.basePseudoQuotientRemainder(A, P);
            pf.add(qr[0]);
            pf.add(qr[1]);
        } else {
            GenPolynomial<C> a = A;
            for (int j = e; j > 0; j--) {
                qr = PolyUtil.basePseudoQuotientRemainder(a, P);
                a = qr[0];
                pf.add(0, qr[1]);
            }
            pf.add(0, a);
        }
        return pf;
    }

    public List<GenPolynomial<C>> basePartialFraction(GenPolynomial<C> A, List<GenPolynomial<C>> D) {
        if (D == null || A == null) {
            throw new IllegalArgumentException("null A or D not allowed");
        }
        List<GenPolynomial<C>> pf = new ArrayList(D.size() + 1);
        if (A.isZERO() || D.size() == 0) {
            pf.add(A);
            for (int i = 0; i < D.size(); i++) {
                pf.add(A);
            }
        } else {
            List<GenPolynomial<C>> Dp = new ArrayList(D.size() - 1);
            GenPolynomial<C> P = A.ring.getONE();
            GenPolynomial<C> d1 = null;
            for (GenPolynomial d : D) {
                if (d1 == null) {
                    d1 = d;
                } else {
                    P = P.multiply(d);
                    Dp.add(d);
                }
            }
            GenPolynomial<C>[] qr = PolyUtil.basePseudoQuotientRemainder(A, P.multiply((GenPolynomial) d1));
            GenPolynomial<C> A0 = qr[0];
            GenPolynomial<C> r = qr[1];
            if (D.size() == 1) {
                pf.add(A0);
                pf.add(r);
            } else {
                GenPolynomial<C>[] diop = baseGcdDiophant(P, d1, r);
                GenPolynomial<C> A1 = diop[0];
                List<GenPolynomial<C>> Fr = basePartialFraction(diop[1], Dp);
                pf.add(A0.sum((GenPolynomial) Fr.remove(0)));
                pf.add(A1);
                pf.addAll(Fr);
            }
        }
        return pf;
    }

    public boolean isBasePartialFraction(GenPolynomial<C> A, List<GenPolynomial<C>> D, List<GenPolynomial<C>> F) {
        if (D == null || A == null || F == null) {
            throw new IllegalArgumentException("null A, F or D not allowed");
        } else if (D.size() != F.size() - 1) {
            return false;
        } else {
            GenPolynomial P = A.ring.getONE();
            for (GenPolynomial d : D) {
                P = P.multiply(d);
            }
            List<GenPolynomial<C>> Fp = new ArrayList(F);
            GenPolynomial<C> A0 = ((GenPolynomial) Fp.remove(0)).multiply(P);
            int j = 0;
            for (GenPolynomial<C> Fi : Fp) {
                P = A.ring.getONE();
                int i = 0;
                for (GenPolynomial d2 : D) {
                    if (i != j) {
                        P = P.multiply(d2);
                    }
                    i++;
                }
                A0 = A0.sum(Fi.multiply(P));
                j++;
            }
            boolean t = A.equals(A0);
            if (t) {
                return t;
            }
            System.out.println("not isPartFrac = " + A0);
            return t;
        }
    }

    public boolean isBasePartialFraction(GenPolynomial<C> A, GenPolynomial<C> P, int e, List<GenPolynomial<C>> F) {
        if (A == null || P == null || F == null || e == 0) {
            throw new IllegalArgumentException("null A, P, F or e = 0 not allowed");
        }
        GenPolynomial<C> A0 = basePartialFractionValue(P, e, F);
        boolean t = A.equals(A0);
        if (!t) {
            System.out.println("not isPartFrac = " + A0);
        }
        return t;
    }

    public GenPolynomial<C> basePartialFractionValue(GenPolynomial<C> P, int e, List<GenPolynomial<C>> F) {
        if (P == null || F == null || e == 0) {
            throw new IllegalArgumentException("null P, F or e = 0 not allowed");
        }
        GenPolynomial<C> A0 = P.ring.getZERO();
        for (GenPolynomial Fi : F) {
            A0 = A0.multiply((GenPolynomial) P).sum(Fi);
        }
        return A0;
    }
}
