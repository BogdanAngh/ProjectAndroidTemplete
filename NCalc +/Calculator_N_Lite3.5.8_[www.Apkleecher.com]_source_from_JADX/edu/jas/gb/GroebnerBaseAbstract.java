package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.PolynomialList;
import edu.jas.poly.TermOrder;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.vector.BasicLinAlg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public abstract class GroebnerBaseAbstract<C extends RingElem<C>> implements GroebnerBase<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    public final BasicLinAlg<GenPolynomial<C>> blas;
    private final boolean debug;
    public final Reduction<C> red;
    public final PairList<C> strategy;

    static {
        $assertionsDisabled = !GroebnerBaseAbstract.class.desiredAssertionStatus();
        logger = Logger.getLogger(GroebnerBaseAbstract.class);
    }

    public GroebnerBaseAbstract() {
        this(new ReductionSeq());
    }

    public GroebnerBaseAbstract(Reduction<C> red) {
        this(red, new OrderedPairlist());
    }

    public GroebnerBaseAbstract(PairList<C> pl) {
        this(new ReductionSeq(), pl);
    }

    public GroebnerBaseAbstract(Reduction<C> red, PairList<C> pl) {
        this.debug = logger.isDebugEnabled();
        if (red == null) {
            red = new ReductionSeq();
        }
        this.red = red;
        if (pl == null) {
            pl = new OrderedPairlist();
        }
        this.strategy = pl;
        this.blas = new BasicLinAlg();
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public List<GenPolynomial<C>> normalizeZerosOnes(List<GenPolynomial<C>> A) {
        List<GenPolynomial<C>> N = new ArrayList(A.size());
        if (!(A == null || A.isEmpty())) {
            for (GenPolynomial<C> p : A) {
                if (!(p == null || p.isZERO())) {
                    if (p.isUnit()) {
                        N.clear();
                        N.add(p.ring.getONE());
                        break;
                    }
                    N.add(p.abs());
                }
            }
        }
        return N;
    }

    public boolean isGB(List<GenPolynomial<C>> F) {
        return isGB(0, (List) F);
    }

    public boolean isGB(int modv, List<GenPolynomial<C>> F) {
        return isGB(modv, F, true);
    }

    public boolean isGB(List<GenPolynomial<C>> F, boolean b) {
        return isGB(0, F, b);
    }

    public boolean isGB(int modv, List<GenPolynomial<C>> F, boolean b) {
        if (b) {
            return isGBsimple(modv, F);
        }
        return isGBidem(modv, F);
    }

    public boolean isGBsimple(int modv, List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        int i = 0;
        while (i < F.size()) {
            GenPolynomial<C> pi = (GenPolynomial) F.get(i);
            ExpVector ei = pi.leadingExpVector();
            int j = i + 1;
            while (j < F.size()) {
                GenPolynomial<C> pj = (GenPolynomial) F.get(j);
                ExpVector ej = pj.leadingExpVector();
                if (this.red.moduleCriterion(modv, ei, ej)) {
                    ExpVector eij = ei.lcm(ej);
                    if (this.red.criterion4(ei, ej, eij) && criterion3(i, j, eij, F)) {
                        GenPolynomial s = this.red.SPolynomial(pi, pj);
                        if (s.isZERO()) {
                            continue;
                        } else {
                            GenPolynomial<C> h = this.red.normalform((List) F, s);
                            if (!h.isZERO()) {
                                logger.info("no GB: pi = " + pi + ", pj = " + pj);
                                logger.info("s  = " + s + ", h = " + h);
                                return false;
                            }
                        }
                    }
                }
                j++;
            }
            i++;
        }
        return true;
    }

    boolean criterion3(int i, int j, ExpVector eij, List<GenPolynomial<C>> P) {
        if ($assertionsDisabled || i < j) {
            for (int k = 0; k < i; k++) {
                if (eij.multipleOf(((GenPolynomial) P.get(k)).leadingExpVector())) {
                    return false;
                }
            }
            return true;
        }
        throw new AssertionError();
    }

    public boolean isGBidem(int modv, List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        GenPolynomialRing pring = ((GenPolynomial) F.get(0)).ring;
        return new PolynomialList(pring, (List) F).compareTo(new PolynomialList(pring, GB(modv, F))) == 0;
    }

    public int commonZeroTest(List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return 1;
        }
        GenPolynomialRing<C> pfac = ((GenPolynomial) F.get(0)).ring;
        if (pfac.nvar <= 0) {
            return -1;
        }
        Set<Integer> v = new HashSet();
        for (GenPolynomial<C> p : F) {
            if (!p.isZERO()) {
                if (p.isConstant()) {
                    return -1;
                }
                ExpVector e = p.leadingExpVector();
                if (e != null) {
                    int[] u = e.dependencyOnVariables();
                    if (u != null && u.length == 1) {
                        v.add(Integer.valueOf(u[0]));
                    }
                }
            }
        }
        if (pfac.nvar == v.size()) {
            return 0;
        }
        return 1;
    }

    public List<GenPolynomial<C>> GB(List<GenPolynomial<C>> F) {
        return GB(0, F);
    }

    public ExtendedGB<C> extGB(List<GenPolynomial<C>> F) {
        return extGB(0, F);
    }

    public ExtendedGB<C> extGB(int modv, List<GenPolynomial<C>> list) {
        throw new UnsupportedOperationException("extGB not implemented in " + getClass().getSimpleName());
    }

    public List<GenPolynomial<C>> minimalGB(List<GenPolynomial<C>> Gp) {
        if (Gp == null || Gp.size() <= 1) {
            return Gp;
        }
        List<GenPolynomial<C>> G = new ArrayList(Gp.size());
        for (GenPolynomial<C> a : Gp) {
            GenPolynomial<C> a2;
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        List<GenPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenPolynomial a3 = (GenPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a3) && !this.red.isTopReducible(F, a3)) {
                F.add(a3);
            } else if (this.debug) {
                System.out.println("dropped " + a3);
                List ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform(ff, a3);
                if (!a2.isZERO()) {
                    System.out.println("error, nf(a) " + a2);
                }
            }
        }
        List<GenPolynomial<C>> G2 = F;
        if (G2.size() <= 1) {
            return G2;
        }
        Collections.reverse(G2);
        int len = G2.size();
        if (this.debug) {
            System.out.println("#G " + len);
            for (GenPolynomial<C> aa : G2) {
                System.out.println("aa = " + aa.length() + ", lt = " + aa.getMap().keySet());
            }
        }
        for (int i = 0; i < len; i++) {
            a3 = (GenPolynomial) G2.remove(0);
            if (this.debug) {
                System.out.println("doing " + a3.length() + ", lt = " + a3.leadingExpVector());
            }
            G2.add(this.red.normalform((List) G2, a3));
        }
        return G2;
    }

    public boolean isMinimalGB(List<GenPolynomial<C>> Gp) {
        if (Gp == null || Gp.size() == 0) {
            return true;
        }
        for (GenPolynomial<C> a : Gp) {
            GenPolynomial<C> a2;
            if (a2 != null) {
                if (a2.isZERO()) {
                }
            }
            if (!this.debug) {
                return false;
            }
            logger.debug("zero polynomial " + a2);
            return false;
        }
        List<GenPolynomial<C>> G = new ArrayList(Gp);
        List<GenPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            a2 = (GenPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a2) && !this.red.isTopReducible(F, a2)) {
                F.add(a2);
            } else if (!this.debug) {
                return false;
            } else {
                logger.debug("top reducible polynomial " + a2);
                return false;
            }
        }
        G = F;
        if (G.size() <= 1) {
            return true;
        }
        int len = G.size();
        int i = 0;
        while (i < len) {
            a2 = (GenPolynomial) G.remove(0);
            if (this.red.isNormalform(G, a2)) {
                G.add(a2);
                i++;
            } else if (!this.debug) {
                return false;
            } else {
                logger.debug("reducible polynomial " + a2);
                return false;
            }
        }
        return true;
    }

    public boolean isReductionMatrix(ExtendedGB<C> exgb) {
        if (exgb == null) {
            return true;
        }
        return isReductionMatrix(exgb.F, exgb.G, exgb.F2G, exgb.G2F);
    }

    public boolean isReductionMatrix(List<GenPolynomial<C>> F, List<GenPolynomial<C>> G, List<List<GenPolynomial<C>>> Mf, List<List<GenPolynomial<C>>> Mg) {
        int k = 0;
        for (List<GenPolynomial<C>> row : Mg) {
            if (this.red.isReductionNF(row, F, (GenPolynomial) G.get(k), null)) {
                k++;
            } else {
                logger.error("F isReductionMatrix s, k = " + F.size() + ", " + k);
                return false;
            }
        }
        k = 0;
        for (List<GenPolynomial<C>> row2 : Mf) {
            if (this.red.isReductionNF(row2, G, (GenPolynomial) F.get(k), null)) {
                k++;
            } else {
                logger.error("G isReductionMatrix s, k = " + G.size() + ", " + k);
                return false;
            }
        }
        return true;
    }

    public List<List<GenPolynomial<C>>> normalizeMatrix(int flen, List<List<GenPolynomial<C>>> M) {
        if (M == null || M.size() == 0) {
            return M;
        }
        int i;
        List<List<GenPolynomial<C>>> N = new ArrayList();
        List<List<GenPolynomial<C>>> K = new ArrayList();
        int len = ((List) M.get(M.size() - 1)).size();
        for (List<GenPolynomial<C>> row : M) {
            List<GenPolynomial<C>> nrow = new ArrayList(row);
            for (i = row.size(); i < len; i++) {
                nrow.add(null);
            }
            N.add(nrow);
        }
        int k = flen;
        for (i = 0; i < N.size(); i++) {
            List row2 = (List) N.get(i);
            if (this.debug) {
                logger.info("row = " + row2);
            }
            K.add(row2);
            if (i >= flen) {
                for (int j = i + 1; j < N.size(); j++) {
                    nrow = (List) N.get(j);
                    if (k < nrow.size()) {
                        RingElem a = (GenPolynomial) nrow.get(k);
                        if (!(a == null || a.isZERO())) {
                            N.set(j, this.blas.vectorAdd(this.blas.scalarProduct(a, row2), nrow));
                        }
                    }
                }
                k++;
            }
        }
        N.clear();
        for (List<GenPolynomial<C>> row3 : K) {
            List<GenPolynomial<C>> tr = new ArrayList();
            for (i = 0; i < flen; i++) {
                tr.add(row3.get(i));
            }
            N.add(tr);
        }
        return N;
    }

    public ExtendedGB<C> minimalExtendedGB(int flen, List<GenPolynomial<C>> Gp, List<List<GenPolynomial<C>>> M) {
        if (Gp == null) {
            return null;
        }
        if (Gp.size() <= 1) {
            return new ExtendedGB(null, Gp, null, M);
        }
        List<GenPolynomial<C>> G = new ArrayList(Gp);
        List<GenPolynomial<C>> F = new ArrayList(Gp.size());
        List<List<GenPolynomial<C>>> Mg = new ArrayList(M.size());
        List<List<GenPolynomial<C>>> Mf = new ArrayList(M.size());
        for (List<GenPolynomial<C>> arrayList : M) {
            Mg.add(new ArrayList(arrayList));
        }
        ArrayList<Integer> ix = new ArrayList();
        ArrayList<Integer> jx = new ArrayList();
        int k = 0;
        while (G.size() > 0) {
            GenPolynomial<C> a = (GenPolynomial) G.remove(0);
            ExpVector e = a.leadingExpVector();
            ListIterator<GenPolynomial<C>> it = G.listIterator();
            boolean mt = false;
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenPolynomial) it.next()).leadingExpVector());
            }
            it = F.listIterator();
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenPolynomial) it.next()).leadingExpVector());
            }
            if (mt) {
                jx.add(Integer.valueOf(k));
            } else {
                F.add(a);
                ix.add(Integer.valueOf(k));
            }
            k++;
        }
        if (this.debug) {
            logger.debug("ix, #M, jx = " + ix + ", " + Mg.size() + ", " + jx);
        }
        int fix = -1;
        for (int i = 0; i < ix.size(); i++) {
            int u = ((Integer) ix.get(i)).intValue();
            if (u >= flen && fix == -1) {
                fix = Mf.size();
            }
            if (u >= 0) {
                Mf.add((List) Mg.get(u));
            }
        }
        return (F.size() <= 1 || fix == -1) ? new ExtendedGB(null, F, null, Mf) : new ExtendedGB(null, F, null, Mf);
    }

    public List<Long> univariateDegrees(List<GenPolynomial<C>> A) {
        List<Long> ud = new ArrayList();
        if (!(A == null || A.size() == 0)) {
            GenPolynomialRing<C> pfac = ((GenPolynomial) A.get(0)).ring;
            if (pfac.nvar > 0) {
                Map<Integer, Long> v = new TreeMap();
                for (GenPolynomial<C> p : A) {
                    ExpVector e = p.leadingExpVector();
                    if (e != null) {
                        int[] u = e.dependencyOnVariables();
                        if (u != null && u.length == 1 && ((Long) v.get(Integer.valueOf(u[0]))) == null) {
                            v.put(Integer.valueOf(u[0]), Long.valueOf(e.getVal(u[0])));
                        }
                    }
                }
                for (int i = 0; i < pfac.nvar; i++) {
                    ud.add((Long) v.get(Integer.valueOf(i)));
                }
            }
        }
        return ud;
    }

    public GenPolynomial<C> constructUnivariate(int i, List<GenPolynomial<C>> G) {
        if (G == null || G.size() == 0) {
            throw new IllegalArgumentException("G may not be null or empty");
        }
        List<Long> ud = univariateDegrees(G);
        if (ud.size() <= i) {
            throw new IllegalArgumentException("ideal(G) not zero dimensional " + ud);
        }
        Long di = (Long) ud.get(i);
        if (di != null) {
            int ll = (int) di.longValue();
            long vsdim = 1;
            for (Long d : ud) {
                if (d != null) {
                    vsdim *= d.longValue();
                }
            }
            logger.info("univariate construction, deg = " + ll + ", vsdim = " + vsdim);
            GenPolynomialRing<C> pfac = ((GenPolynomial) G.get(0)).ring;
            RingFactory cfac = pfac.coFac;
            String var = pfac.getVars()[(pfac.nvar - 1) - i];
            GenPolynomialRing<C> genPolynomialRing = new GenPolynomialRing(cfac, 1, new TermOrder(2), new String[]{var});
            GenPolynomialRing<C> cpfac = new GenPolynomialRing(cfac, ll, new TermOrder(2));
            GenPolynomialRing<GenPolynomial<C>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) cpfac, (GenPolynomialRing) pfac);
            GenPolynomial<GenPolynomial<C>> P = genPolynomialRing2.getZERO();
            for (int k = 0; k < ll; k++) {
                P = P.sum(genPolynomialRing2.univariate(i, (long) k).multiply(cpfac.univariate((cpfac.nvar - 1) - k)));
            }
            if (this.debug) {
                logger.info("univariate construction, P = " + P);
                logger.info("univariate construction, deg_*(G) = " + ud);
            }
            int z;
            do {
                P = P.sum(r25.univariate(i, (long) ll).multiply(cpfac.univariate((cpfac.nvar - 1) - ll)));
                GenPolynomial X = pfac.univariate(i, (long) ll);
                GenPolynomial XP = this.red.normalform((List) G, X);
                GenPolynomial XPp = PolyUtil.toRecursive((GenPolynomialRing) r25, XP);
                List<GenPolynomial<C>> arrayList = new ArrayList(XPp.sum((GenPolynomial) P).getMap().values());
                List<GenPolynomial<C>> ls = this.red.irreducibleSet(arrayList);
                z = commonZeroTest(ls);
                if (z != 0) {
                    ll++;
                    if (((long) ll) > vsdim) {
                        logger.info("univariate construction, P = " + P);
                        logger.info("univariate construction, nf(P) = " + XP);
                        logger.info("G = " + G);
                        throw new ArithmeticException("univariate polynomial degree greater than vector space dimansion");
                    }
                    cpfac = cpfac.extend(1);
                    genPolynomialRing2 = new GenPolynomialRing((RingFactory) cpfac, (GenPolynomialRing) pfac);
                    P = PolyUtil.extendCoefficients((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) P, 0, 0).sum(PolyUtil.extendCoefficients((GenPolynomialRing) genPolynomialRing2, XPp, 0, 1));
                    continue;
                }
            } while (z != 0);
            GenPolynomial<C> pol = genPolynomialRing.univariate(0, (long) ll);
            for (GenPolynomial<C> pc : ls) {
                ExpVector e = pc.leadingExpVector();
                if (e != null) {
                    int[] v = e.dependencyOnVariables();
                    if (!(v == null || v.length == 0)) {
                        int vi = v[0];
                        C lc = pc.leadingBaseCoefficient();
                        C tc = (RingElem) pc.trailingBaseCoefficient().negate();
                        if (!lc.isONE()) {
                            tc = (RingElem) tc.divide(lc);
                        }
                        pol = pol.sum((GenPolynomial) genPolynomialRing.univariate(0, (long) ((ll - 1) - vi)).multiply((RingElem) tc));
                    }
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("univariate construction, pol = " + pol);
            }
            return pol;
        }
        throw new IllegalArgumentException("ideal(G) not zero dimensional");
    }

    public void terminate() {
        logger.info("terminate not implemented");
    }

    public int cancel() {
        logger.info("cancel not implemented");
        return 0;
    }
}
