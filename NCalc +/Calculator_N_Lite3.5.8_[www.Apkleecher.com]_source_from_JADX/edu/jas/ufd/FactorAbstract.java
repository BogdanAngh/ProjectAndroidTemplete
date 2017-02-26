package edu.jas.ufd;

import edu.jas.kern.StringUtil;
import edu.jas.kern.TimeStatus;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.OptimizedPolynomialList;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.TermOrderOptimization;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.util.KsubSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.math4.analysis.interpolation.MicrosphereInterpolator;
import org.apache.log4j.Logger;

public abstract class FactorAbstract<C extends GcdRingElem<C>> implements Factorization<C> {
    private static final Logger logger;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final SquarefreeAbstract<C> sengine;

    public abstract List<GenPolynomial<C>> baseFactorsSquarefree(GenPolynomial<C> genPolynomial);

    static {
        logger = Logger.getLogger(FactorAbstract.class);
    }

    protected FactorAbstract() {
        this.debug = logger.isDebugEnabled();
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorAbstract(RingFactory<C> cfac) {
        this.debug = logger.isDebugEnabled();
        this.engine = GCDFactory.getProxy((RingFactory) cfac);
        this.sengine = SquarefreeFactory.getImplementation((RingFactory) cfac);
    }

    public String toString() {
        return getClass().getName();
    }

    public boolean isIrreducible(GenPolynomial<C> P) {
        if (!isSquarefree(P)) {
            return false;
        }
        List<GenPolynomial<C>> F = factorsSquarefree(P);
        if (F.size() == 1) {
            return true;
        }
        if (F.size() > 2) {
            return false;
        }
        boolean cnst = false;
        for (GenPolynomial<C> p : F) {
            if (p.isConstant()) {
                cnst = true;
            }
        }
        return cnst;
    }

    public boolean isReducible(GenPolynomial<C> P) {
        return !isIrreducible(P);
    }

    public boolean isSquarefree(GenPolynomial<C> P) {
        return this.sengine.isSquarefree((GenPolynomial) P);
    }

    public List<GenPolynomial<C>> factorsSquarefreeOptimize(GenPolynomial<C> P) {
        GenPolynomialRing pfac = P.ring;
        int i = pfac.nvar;
        if (r0 <= 1) {
            return baseFactorsSquarefree(P);
        }
        List facs;
        List<GenPolynomial<C>> topt = new ArrayList(1);
        topt.add(P);
        OptimizedPolynomialList<C> opt = TermOrderOptimization.optimizeTermOrder(pfac, topt);
        P = (GenPolynomial) opt.list.get(0);
        logger.info("optimized polynomial: " + P);
        List iperm = TermOrderOptimization.inversePermutation(opt.perm);
        logger.info("optimize perm: " + opt.perm + ", de-optimize perm: " + iperm);
        ExpVector degv = P.degreeVector();
        int[] donv = degv.dependencyOnVariables();
        if (degv.length() == donv.length) {
            logger.info("do.full factorsSquarefreeKronecker: " + P);
            facs = factorsSquarefreeKronecker(P);
        } else {
            GenPolynomial<C> pu = PolyUtil.removeUnusedUpperVariables(P);
            logger.info("do.sparse factorsSquarefreeKronecker: " + pu);
            List<GenPolynomial<C>> facs2 = factorsSquarefreeKronecker(pu);
            List<GenPolynomial<C>> fs = new ArrayList(facs2.size());
            GenPolynomialRing<C> pf = P.ring;
            for (GenPolynomial<C> p : facs2) {
                fs.add(p.extend(pf, 0, 0));
            }
            facs2 = fs;
        }
        List<GenPolynomial<C>> iopt = TermOrderOptimization.permutation(iperm, pfac, facs);
        logger.info("de-optimized polynomials: " + iopt);
        return normalizeFactorization(iopt);
    }

    public List<GenPolynomial<C>> factorsSquarefree(GenPolynomial<C> P) {
        if (logger.isInfoEnabled()) {
            logger.info(StringUtil.selectStackTrace("edu\\.jas.*"));
        }
        return factorsSquarefreeKronecker(P);
    }

    public List<GenPolynomial<C>> factorsSquarefreeKronecker(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing<C> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 == 1) {
            return baseFactorsSquarefree(P);
        }
        List<GenPolynomial<C>> factors = new ArrayList();
        if (P.isZERO()) {
            return factors;
        }
        if (P.degreeVector().totalDeg() <= 1) {
            factors.add(P);
            return factors;
        }
        long d = P.degree() + 1;
        GenPolynomial<C> kr = PolyUfdUtil.substituteKronecker((GenPolynomial) P, d);
        GenPolynomialRing<C> ufac = kr.ring;
        ufac.setVars(ufac.newVars("zz"));
        logger.info("deg(subs(P,d=" + d + ")) = " + kr.degree(0) + ", original degrees: " + P.degreeVector());
        if (this.debug) {
            logger.info("subs(P,d=" + d + ") = " + kr);
        }
        if (kr.degree(0) > 100) {
            logger.warn("Kronecker substitution has to high degree");
            TimeStatus.checkTime("degree > 100");
        }
        List<GenPolynomial<C>> ulist = new ArrayList();
        SortedMap<GenPolynomial<C>, Long> slist = baseFactors(kr);
        if (!this.debug || isFactorization((GenPolynomial) kr, (SortedMap) slist)) {
            Iterator i$;
            for (Entry<GenPolynomial<C>, Long> me : slist.entrySet()) {
                GenPolynomial<C> g = (GenPolynomial) me.getKey();
                long e = ((Long) me.getValue()).longValue();
                int i2 = 0;
                while (true) {
                    if (((long) i2) < e) {
                        ulist.add(g);
                        i2++;
                    }
                }
            }
            if (ulist.size() == 1 && ((GenPolynomial) ulist.get(0)).degree() == P.degree()) {
                factors.add(P);
                return factors;
            }
            if (logger.isInfoEnabled()) {
                logger.info("ulist = " + ulist);
            }
            int dl = ulist.size() - 1;
            int ti = 0;
            GenPolynomial<C> u = P;
            long deg = (u.degree() + 1) / 2;
            ExpVector evl = u.leadingExpVector();
            ExpVector evt = u.trailingExpVector();
            int j = 1;
            while (j <= dl) {
                i$ = new KsubSet(ulist, j).iterator();
                while (i$.hasNext()) {
                    List<GenPolynomial<C>> flist = (List) i$.next();
                    GenPolynomial<C> utrial = ufac.getONE();
                    for (int k = 0; k < flist.size(); k++) {
                        utrial = utrial.multiply((GenPolynomial) flist.get(k));
                    }
                    GenPolynomial<C> trial = PolyUfdUtil.backSubstituteKronecker((GenPolynomialRing) pfac, (GenPolynomial) utrial, d);
                    ti++;
                    if (ti % MicrosphereInterpolator.DEFAULT_MICROSPHERE_ELEMENTS == 0) {
                        System.out.print("ti(" + ti + ") ");
                        TimeStatus.checkTime(ti + " % 2000 == 0");
                    }
                    if (evl.multipleOf(trial.leadingExpVector())) {
                        if (evt.multipleOf(trial.trailingExpVector()) && trial.degree() <= deg && !trial.isConstant()) {
                            trial = trial.monic();
                            if (ti % 15000 == 0) {
                                System.out.println("\ndl   = " + dl + ", deg(u) = " + deg);
                                System.out.println("ulist = " + ulist);
                                System.out.println("kr    = " + kr);
                                System.out.println("u     = " + u);
                                System.out.println("trial = " + trial);
                            }
                            if (PolyUtil.baseSparsePseudoRemainder(u, trial).isZERO()) {
                                logger.info("trial = " + trial);
                                factors.add(trial);
                                u = PolyUtil.basePseudoDivide(u, trial);
                                evl = u.leadingExpVector();
                                evt = u.trailingExpVector();
                                if (u.isConstant()) {
                                    j = dl + 1;
                                } else {
                                    ulist = removeOnce(ulist, flist);
                                    dl = (ulist.size() + 1) / 2;
                                    j = 0;
                                }
                                j++;
                            }
                        }
                    }
                }
                j++;
            }
            if (!(u.isONE() || u.equals(P))) {
                logger.info("rest u = " + u);
                factors.add(u);
            }
            if (factors.size() == 0) {
                logger.info("irred P = " + P);
                factors.add(P);
            }
            return normalizeFactorization(factors);
        }
        System.out.println("kr    = " + kr);
        System.out.println("slist = " + slist);
        throw new ArithmeticException("no factorization");
    }

    static <T> List<T> removeOnce(List<T> a, List<T> b) {
        List<T> res = new ArrayList();
        res.addAll(a);
        for (T e : b) {
            res.remove(e);
        }
        return res;
    }

    public List<GenPolynomial<C>> baseFactorsRadical(GenPolynomial<C> P) {
        return new ArrayList(baseFactors(P).keySet());
    }

    public SortedMap<GenPolynomial<C>, Long> baseFactors(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing<C> pfac = P.ring;
        SortedMap<GenPolynomial<C>, Long> factors = new TreeMap(pfac.getComparator());
        if (!P.isZERO()) {
            if (pfac.nvar > 1) {
                throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
            } else if (P.isConstant()) {
                factors.put(P, Long.valueOf(1));
            } else {
                RingElem c;
                if (pfac.coFac.isField()) {
                    c = (GcdRingElem) P.leadingBaseCoefficient();
                } else {
                    c = this.engine.baseContent(P);
                    if (P.signum() < 0 && c.signum() > 0) {
                        GcdRingElem c2 = (GcdRingElem) c.negate();
                    }
                }
                if (!c.isONE()) {
                    factors.put(pfac.getONE().multiply(c), Long.valueOf(1));
                    P = P.divide(c);
                }
                if (logger.isInfoEnabled()) {
                    logger.info("base facs for P = " + P);
                }
                SortedMap<GenPolynomial<C>, Long> facs = this.sengine.baseSquarefreeFactors(P);
                if (facs == null || facs.size() == 0) {
                    facs = new TreeMap();
                    facs.put(P, Long.valueOf(1));
                }
                if (logger.isInfoEnabled() && (facs.size() > 1 || (facs.size() == 1 && ((Long) facs.get(facs.firstKey())).longValue() > 1))) {
                    logger.info("squarefree facs   = " + facs);
                }
                for (Entry<GenPolynomial<C>, Long> me : facs.entrySet()) {
                    GenPolynomial<C> g = (GenPolynomial) me.getKey();
                    Long k = (Long) me.getValue();
                    if (pfac.coFac.isField() && !((GcdRingElem) g.leadingBaseCoefficient()).isONE()) {
                        g = g.monic();
                        logger.warn("squarefree facs mon = " + g);
                    }
                    if (g.degree(0) > 1) {
                        List<GenPolynomial<C>> sfacs = baseFactorsSquarefree(g);
                        if (this.debug) {
                            logger.info("factors of squarefree = " + sfacs);
                        }
                        for (GenPolynomial<C> h : sfacs) {
                            Long j = (Long) factors.get(h);
                            if (j != null) {
                                k = Long.valueOf(k.longValue() + j.longValue());
                            }
                            if (!h.isONE()) {
                                factors.put(h, k);
                            }
                        }
                    } else if (!g.isONE()) {
                        factors.put(g, k);
                    }
                }
            }
        }
        return factors;
    }

    public List<GenPolynomial<C>> factorsRadical(GenPolynomial<C> P) {
        return new ArrayList(factors(P).keySet());
    }

    public List<GenPolynomial<C>> factorsRadical(List<GenPolynomial<C>> L) {
        SortedSet<GenPolynomial<C>> facs = new TreeSet();
        for (GenPolynomial p : L) {
            facs.addAll(factorsRadical(p));
        }
        return new ArrayList(facs);
    }

    public SortedMap<GenPolynomial<C>, Long> factors(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing<C> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 == 1) {
            return baseFactors(P);
        }
        SortedMap<GenPolynomial<C>, Long> factors = new TreeMap(pfac.getComparator());
        if (P.isZERO()) {
            return factors;
        }
        if (P.isConstant()) {
            factors.put(P, Long.valueOf(1));
            return factors;
        }
        RingElem c;
        if (pfac.coFac.isField()) {
            c = (GcdRingElem) P.leadingBaseCoefficient();
        } else {
            c = this.engine.baseContent(P);
            if (P.signum() < 0 && c.signum() > 0) {
                GcdRingElem c2 = (GcdRingElem) c.negate();
            }
        }
        if (!c.isONE()) {
            GenPolynomial one = pfac.getONE();
            factors.put(r17.multiply(c), Long.valueOf(1));
            P = P.divide(c);
        }
        if (logger.isInfoEnabled()) {
            logger.info("squarefree mfacs P = " + P);
        }
        SortedMap<GenPolynomial<C>, Long> facs = this.sengine.squarefreeFactors((GenPolynomial) P);
        if (facs == null || facs.size() == 0) {
            facs = new TreeMap();
            facs.put(P, Long.valueOf(1));
            throw new RuntimeException("this should not happen, facs is empty: " + facs);
        }
        if (logger.isInfoEnabled()) {
            if (facs.size() > 1) {
                logger.info("squarefree mfacs      = " + facs);
            } else {
                if (facs.size() == 1) {
                    if (((Long) facs.get(facs.firstKey())).longValue() > 1) {
                        logger.info("squarefree #mfacs 1-n = " + facs);
                    }
                }
                logger.info("squarefree #mfacs 1-1 = " + facs);
            }
        }
        for (Entry<GenPolynomial<C>, Long> me : facs.entrySet()) {
            GenPolynomial<C> g = (GenPolynomial) me.getKey();
            if (!g.isONE()) {
                Long d = (Long) me.getValue();
                List<GenPolynomial<C>> sfacs = factorsSquarefree(g);
                if (logger.isInfoEnabled()) {
                    logger.info("factors of squarefree ^" + d + " = " + sfacs);
                }
                for (GenPolynomial<C> h : sfacs) {
                    long dd = d.longValue();
                    Long j = (Long) factors.get(h);
                    if (j != null) {
                        dd += j.longValue();
                    }
                    factors.put(h, Long.valueOf(dd));
                }
            }
        }
        return factors;
    }

    public GenPolynomial<C> squarefreePart(GenPolynomial<C> P) {
        return this.sengine.squarefreePart((GenPolynomial) P);
    }

    public GenPolynomial<C> primitivePart(GenPolynomial<C> P) {
        return this.engine.primitivePart(P);
    }

    public GenPolynomial<C> basePrimitivePart(GenPolynomial<C> P) {
        return this.engine.basePrimitivePart((GenPolynomial) P);
    }

    public SortedMap<GenPolynomial<C>, Long> squarefreeFactors(GenPolynomial<C> P) {
        return this.sengine.squarefreeFactors((GenPolynomial) P);
    }

    public boolean isFactorization(GenPolynomial<C> P, List<GenPolynomial<C>> F) {
        return this.sengine.isFactorization((GenPolynomial) P, (List) F);
    }

    public boolean isFactorization(GenPolynomial<C> P, SortedMap<GenPolynomial<C>, Long> F) {
        return this.sengine.isFactorization((GenPolynomial) P, (SortedMap) F);
    }

    public long factorsDegree(SortedMap<GenPolynomial<C>, Long> F) {
        long d = 0;
        for (Entry<GenPolynomial<C>, Long> me : F.entrySet()) {
            GenPolynomial<C> p = (GenPolynomial) me.getKey();
            d += p.degree() * ((Long) me.getValue()).longValue();
        }
        return d;
    }

    public boolean isRecursiveFactorization(GenPolynomial<GenPolynomial<C>> P, SortedMap<GenPolynomial<GenPolynomial<C>>, Long> F) {
        return this.sengine.isRecursiveFactorization(P, F);
    }

    public List<GenPolynomial<GenPolynomial<C>>> recursiveFactorsSquarefree(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<GenPolynomial<C>>> factors = new ArrayList();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.add(P);
            } else {
                GenPolynomialRing pfac = P.ring;
                GenPolynomial<C> Pi = PolyUtil.distribute(pfac.coFac.extend(pfac.getVars()), (GenPolynomial) P);
                RingElem ldcf = (GcdRingElem) Pi.leadingBaseCoefficient();
                if (!ldcf.isONE() && ldcf.isUnit()) {
                    Pi = Pi.monic();
                }
                List ifacts = factorsSquarefree(Pi);
                if (logger.isInfoEnabled()) {
                    logger.info("ifacts = " + ifacts);
                }
                if (ifacts.size() <= 1) {
                    factors.add(P);
                } else {
                    if (!ldcf.isONE() && ldcf.isUnit()) {
                        GenPolynomial<C> r = (GenPolynomial) ifacts.get(0);
                        ifacts.remove(r);
                        ifacts.add(0, r.multiply(ldcf));
                    }
                    List<GenPolynomial<GenPolynomial<C>>> rfacts = PolyUtil.recursive(pfac, ifacts);
                    if (logger.isDebugEnabled()) {
                        logger.info("recfacts = " + rfacts);
                    }
                    factors.addAll(rfacts);
                }
            }
        }
        return factors;
    }

    public SortedMap<GenPolynomial<GenPolynomial<C>>, Long> recursiveFactors(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing pfac = P.ring;
        SortedMap<GenPolynomial<GenPolynomial<C>>, Long> factors = new TreeMap(pfac.getComparator());
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.put(P, Long.valueOf(1));
            } else {
                GenPolynomial<C> Pi = PolyUtil.distribute(pfac.coFac.extend(pfac.getVars()), (GenPolynomial) P);
                RingElem ldcf = (GcdRingElem) Pi.leadingBaseCoefficient();
                if (!ldcf.isONE() && ldcf.isUnit()) {
                    Pi = Pi.monic();
                }
                SortedMap<GenPolynomial<C>, Long> dfacts = factors(Pi);
                if (logger.isInfoEnabled()) {
                    logger.info("dfacts = " + dfacts);
                }
                if (!ldcf.isONE() && ldcf.isUnit()) {
                    GenPolynomial<C> r = (GenPolynomial) dfacts.firstKey();
                    dfacts.put(r.multiply(ldcf), (Long) dfacts.remove(r));
                }
                for (Entry<GenPolynomial<C>, Long> me : dfacts.entrySet()) {
                    Long E = (Long) me.getValue();
                    factors.put(PolyUtil.recursive(pfac, (GenPolynomial) me.getKey()), E);
                }
                if (logger.isInfoEnabled()) {
                    logger.info("recursive factors = " + factors);
                }
            }
        }
        return factors;
    }

    public List<GenPolynomial<C>> normalizeFactorization(List<GenPolynomial<C>> F) {
        if (F == null || F.size() <= 1) {
            return F;
        }
        List<GenPolynomial<C>> Fp = new ArrayList(F.size());
        GenPolynomial<C> f0 = (GenPolynomial) F.get(0);
        for (int i = 1; i < F.size(); i++) {
            GenPolynomial<C> fi = (GenPolynomial) F.get(i);
            if (fi.signum() < 0) {
                fi = fi.negate();
                f0 = f0.negate();
            }
            Fp.add(fi);
        }
        if (f0.isONE()) {
            return Fp;
        }
        Fp.add(0, f0);
        return Fp;
    }
}
