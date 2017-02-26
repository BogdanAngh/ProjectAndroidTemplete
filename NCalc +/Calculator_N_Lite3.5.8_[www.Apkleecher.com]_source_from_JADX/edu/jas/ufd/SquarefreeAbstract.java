package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class SquarefreeAbstract<C extends GcdRingElem<C>> implements Squarefree<C> {
    protected final GreatestCommonDivisorAbstract<C> engine;

    public abstract SortedMap<GenPolynomial<C>, Long> baseSquarefreeFactors(GenPolynomial<C> genPolynomial);

    public abstract GenPolynomial<C> baseSquarefreePart(GenPolynomial<C> genPolynomial);

    public abstract SortedMap<GenPolynomial<GenPolynomial<C>>, Long> recursiveUnivariateSquarefreeFactors(GenPolynomial<GenPolynomial<C>> genPolynomial);

    public abstract GenPolynomial<GenPolynomial<C>> recursiveUnivariateSquarefreePart(GenPolynomial<GenPolynomial<C>> genPolynomial);

    public abstract SortedMap<GenPolynomial<C>, Long> squarefreeFactors(GenPolynomial<C> genPolynomial);

    public abstract SortedMap<C, Long> squarefreeFactors(C c);

    public abstract GenPolynomial<C> squarefreePart(GenPolynomial<C> genPolynomial);

    public SquarefreeAbstract(GreatestCommonDivisorAbstract<C> engine) {
        this.engine = engine;
    }

    public boolean isSquarefree(GenPolynomial<C> P) {
        GenPolynomial<C> Ps;
        GenPolynomial<C> S = squarefreePart((GenPolynomial) P);
        GenPolynomial Ps2 = P;
        if (P.ring.coFac.isField()) {
            Ps = Ps2.monic();
        } else {
            Ps = this.engine.basePrimitivePart(Ps2);
        }
        return Ps.equals(S);
    }

    public boolean isSquarefree(List<GenPolynomial<C>> L) {
        if (L == null || L.isEmpty()) {
            return true;
        }
        for (GenPolynomial P : L) {
            if (!isSquarefree(P)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRecursiveSquarefree(GenPolynomial<GenPolynomial<C>> P) {
        GenPolynomial<GenPolynomial<C>> S = recursiveUnivariateSquarefreePart(P);
        boolean f = P.equals(S);
        if (!f) {
            System.out.println("\nisSquarefree: " + f);
            System.out.println("S = " + S);
            System.out.println("P = " + P);
        }
        return f;
    }

    public List<GenPolynomial<C>> coPrimeSquarefree(List<GenPolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            return A;
        }
        List<GenPolynomial<C>> S = new ArrayList();
        for (GenPolynomial g : A) {
            S.addAll(squarefreeFactors(g).keySet());
        }
        return this.engine.coPrime(S);
    }

    public List<GenPolynomial<C>> coPrimeSquarefree(GenPolynomial<C> a, List<GenPolynomial<C>> P) {
        if (a == null || a.isZERO() || a.isConstant()) {
            return P;
        }
        List<GenPolynomial<C>> B = P;
        for (GenPolynomial<C> f : squarefreeFactors((GenPolynomial) a).keySet()) {
            B = this.engine.coPrime(f, B);
        }
        return B;
    }

    public boolean isCoPrimeSquarefree(List<GenPolynomial<C>> B) {
        if (B == null || B.isEmpty()) {
            return true;
        }
        if (this.engine.isCoPrime(B)) {
            return isSquarefree((List) B);
        }
        return false;
    }

    public SortedMap<GenPolynomial<C>, Long> normalizeFactorization(SortedMap<GenPolynomial<C>, Long> F) {
        if (F == null || F.size() <= 1) {
            return F;
        }
        List<GenPolynomial<C>> Fp = new ArrayList(F.keySet());
        GenPolynomial<C> f0 = (GenPolynomial) Fp.get(0);
        if (f0.ring.characteristic().signum() != 0) {
            return F;
        }
        long e0 = ((Long) F.get(f0)).longValue();
        SortedMap<GenPolynomial<C>, Long> Sp = new TreeMap();
        for (int i = 1; i < Fp.size(); i++) {
            GenPolynomial<C> fi = (GenPolynomial) Fp.get(i);
            long ei = ((Long) F.get(fi)).longValue();
            if (fi.signum() < 0) {
                fi = fi.negate();
                if (ei % 2 != 0) {
                    f0 = f0.negate();
                }
            }
            Sp.put(fi, Long.valueOf(ei));
        }
        if (!f0.isONE()) {
            Sp.put(f0, Long.valueOf(e0));
        }
        return Sp;
    }

    public boolean isFactorization(GenPolynomial<C> P, List<GenPolynomial<C>> F) {
        if (P == null || F == null) {
            throw new IllegalArgumentException("P and F may not be null");
        }
        GenPolynomial<C> t = P.ring.getONE();
        for (GenPolynomial f : F) {
            t = t.multiply(f);
        }
        boolean f2 = P.equals(t) || P.equals(t.negate());
        if (!f2) {
            System.out.println("\nfactorization(list): " + f2);
            System.out.println("F = " + F);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
        }
        return f2;
    }

    public long factorCount(SortedMap<GenPolynomial<C>, Long> F) {
        if (F == null || F.isEmpty()) {
            return 0;
        }
        long f = 0;
        for (Long e : F.values()) {
            f += e.longValue();
        }
        return f;
    }

    public boolean isFactorization(GenPolynomial<C> P, SortedMap<GenPolynomial<C>, Long> F) {
        if (P == null || F == null) {
            throw new IllegalArgumentException("P and F may not be null");
        } else if (P.isZERO() && F.size() == 0) {
            return true;
        } else {
            boolean f;
            GenPolynomial<C> t = P.ring.getONE();
            for (Entry<GenPolynomial<C>, Long> me : F.entrySet()) {
                t = t.multiply((GenPolynomial) Power.positivePower((GenPolynomial) me.getKey(), ((Long) me.getValue()).longValue()));
            }
            if (P.equals(t) || P.equals(t.negate())) {
                f = true;
            } else {
                f = false;
            }
            if (f) {
                return f;
            }
            P = P.monic();
            t = t.monic();
            if (P.equals(t) || P.equals(t.negate())) {
                f = true;
            } else {
                f = false;
            }
            if (f) {
                return f;
            }
            System.out.println("\nfactorization(map): " + f);
            System.out.println("F = " + F);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
            return f;
        }
    }

    public boolean isRecursiveFactorization(GenPolynomial<GenPolynomial<C>> P, SortedMap<GenPolynomial<GenPolynomial<C>>, Long> F) {
        if (P == null || F == null) {
            throw new IllegalArgumentException("P and F may not be null");
        } else if (P.isZERO() && F.size() == 0) {
            return true;
        } else {
            boolean f;
            GreatestCommonDivisorAbstract<C> engine;
            GenPolynomial<GenPolynomial<C>> Pp;
            GenPolynomial<GenPolynomial<C>> tp;
            GenPolynomial t = P.ring.getONE();
            for (Entry<GenPolynomial<GenPolynomial<C>>, Long> me : F.entrySet()) {
                t = t.multiply((GenPolynomial) Power.positivePower((GenPolynomial) me.getKey(), ((Long) me.getValue()).longValue()));
            }
            if (!P.equals(t)) {
                if (!P.equals(t.negate())) {
                    f = false;
                    if (!f) {
                        return f;
                    }
                    engine = GCDFactory.getProxy(P.ring.coFac.coFac);
                    Pp = PolyUtil.monic(engine.recursivePrimitivePart((GenPolynomial) P));
                    tp = PolyUtil.monic(engine.recursivePrimitivePart(t));
                    f = Pp.equals(tp) || Pp.equals(tp.negate());
                    if (!f) {
                        return f;
                    }
                    System.out.println("\nfactorization(map): " + f);
                    System.out.println("F  = " + F);
                    System.out.println("P  = " + P);
                    System.out.println("t  = " + t);
                    System.out.println("Pp = " + Pp);
                    System.out.println("tp = " + tp);
                    return f;
                }
            }
            f = true;
            if (!f) {
                return f;
            }
            engine = GCDFactory.getProxy(P.ring.coFac.coFac);
            Pp = PolyUtil.monic(engine.recursivePrimitivePart((GenPolynomial) P));
            tp = PolyUtil.monic(engine.recursivePrimitivePart(t));
            if (!Pp.equals(tp)) {
            }
            if (!f) {
                return f;
            }
            System.out.println("\nfactorization(map): " + f);
            System.out.println("F  = " + F);
            System.out.println("P  = " + P);
            System.out.println("t  = " + t);
            System.out.println("Pp = " + Pp);
            System.out.println("tp = " + tp);
            return f;
        }
    }

    public GenPolynomial<GenPolynomial<C>> recursiveSquarefreePart(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        if (P.ring.nvar <= 1) {
            return recursiveUnivariateSquarefreePart(P);
        }
        GenPolynomialRing rfac = P.ring;
        return PolyUtil.recursive(rfac, squarefreePart(PolyUtil.distribute(((GenPolynomialRing) rfac.coFac).extend(rfac.nvar), (GenPolynomial) P)));
    }

    public SortedMap<GenPolynomial<GenPolynomial<C>>, Long> recursiveSquarefreeFactors(GenPolynomial<GenPolynomial<C>> P) {
        SortedMap<GenPolynomial<GenPolynomial<C>>, Long> factors = new TreeMap();
        if (P == null || P.isZERO()) {
            return factors;
        }
        if (P.ring.nvar <= 1) {
            return recursiveUnivariateSquarefreeFactors(P);
        }
        GenPolynomialRing rfac = P.ring;
        for (Entry<GenPolynomial<C>, Long> Dm : squarefreeFactors(PolyUtil.distribute(((GenPolynomialRing) rfac.coFac).extend(rfac.nvar), (GenPolynomial) P)).entrySet()) {
            Long e = (Long) Dm.getValue();
            factors.put(PolyUtil.recursive(rfac, (GenPolynomial) Dm.getKey()), e);
        }
        return factors;
    }

    public List<List<GenPolynomial<C>>> basePartialFraction(GenPolynomial<C> A, SortedMap<GenPolynomial<C>, Long> D) {
        if (D == null || A == null) {
            throw new IllegalArgumentException("null A or D not allowed");
        }
        List<List<GenPolynomial<C>>> pf = new ArrayList(D.size() + 1);
        if (D.size() != 0) {
            List<GenPolynomial<C>> fi;
            int i;
            if (A.isZERO()) {
                for (Entry<GenPolynomial<C>, Long> me : D.entrySet()) {
                    int e1 = ((int) ((Long) me.getValue()).longValue()) + 1;
                    fi = new ArrayList(e1);
                    for (i = 0; i < e1; i++) {
                        fi.add(A);
                    }
                    pf.add(fi);
                }
                fi = new ArrayList(1);
                fi.add(A);
                pf.add(0, fi);
            } else {
                List<GenPolynomial<C>> Dp = new ArrayList(D.size());
                for (Entry<GenPolynomial<C>, Long> me2 : D.entrySet()) {
                    Dp.add((GenPolynomial) Power.positivePower((GenPolynomial) me2.getKey(), ((Long) me2.getValue()).longValue()));
                }
                List<GenPolynomial<C>> F = this.engine.basePartialFraction(A, Dp);
                GenPolynomial<C> A0 = (GenPolynomial) F.remove(0);
                fi = new ArrayList(1);
                fi.add(A0);
                pf.add(fi);
                i = 0;
                for (Entry<GenPolynomial<C>, Long> me22 : D.entrySet()) {
                    GenPolynomial d = (GenPolynomial) me22.getKey();
                    int ei = (int) ((Long) me22.getValue()).longValue();
                    GenPolynomial gi = (GenPolynomial) F.get(i);
                    pf.add(this.engine.basePartialFraction(gi, d, ei));
                    i++;
                }
            }
        }
        return pf;
    }

    public boolean isBasePartialFraction(GenPolynomial<C> A, SortedMap<GenPolynomial<C>, Long> D, List<List<GenPolynomial<C>>> F) {
        if (D == null || A == null || F == null) {
            throw new IllegalArgumentException("null A, D or F not allowed");
        } else if (D.isEmpty() && F.isEmpty()) {
            return true;
        } else {
            if (D.isEmpty() || F.isEmpty()) {
                return false;
            }
            List<GenPolynomial<C>> Dp = new ArrayList(D.size());
            for (Entry<GenPolynomial<C>, Long> me : D.entrySet()) {
                Dp.add((GenPolynomial) Power.positivePower((GenPolynomial) me.getKey(), ((Long) me.getValue()).longValue()));
            }
            List<GenPolynomial<C>> fi = (List) F.get(0);
            if (fi.size() != 1) {
                System.out.println("size(fi) != 1 " + fi);
                return false;
            }
            GenPolynomial<C> A0 = (GenPolynomial) fi.get(0);
            List<GenPolynomial<C>> Qp = new ArrayList(D.size() + 1);
            Qp.add(A0);
            int i = 0;
            for (Entry<GenPolynomial<C>, Long> me2 : D.entrySet()) {
                GenPolynomial<C> d = (GenPolynomial) me2.getKey();
                int ei = (int) ((Long) me2.getValue()).longValue();
                List<GenPolynomial<C>> Fi = (List) F.get(i + 1);
                Qp.add(this.engine.basePartialFractionValue(d, ei, Fi));
                i++;
            }
            boolean t = this.engine.isBasePartialFraction(A, Dp, Qp);
            if (t) {
                return t;
            }
            System.out.println("not final isPartFrac " + Qp);
            return t;
        }
    }

    public C squarefreePart(C P) {
        if (P == null) {
            return null;
        }
        C s = null;
        SortedMap<C, Long> factors = squarefreeFactors((GcdRingElem) P);
        System.out.println("sqfPart,factors = " + factors);
        for (C sp : factors.keySet()) {
            if (s == null) {
                s = sp;
            } else {
                s = (GcdRingElem) s.multiply(sp);
            }
        }
        return s;
    }
}
