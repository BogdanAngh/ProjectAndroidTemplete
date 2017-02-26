package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.PolynomialList;
import edu.jas.poly.TermOrder;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.vector.BasicLinAlg;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;

public abstract class SolvableGroebnerBaseAbstract<C extends RingElem<C>> implements SolvableGroebnerBase<C> {
    private static final Logger logger;
    protected final BasicLinAlg<GenPolynomial<C>> blas;
    public final GroebnerBaseAbstract<C> cbb;
    private final boolean debug;
    public final Reduction<C> red;
    public SolvableReduction<C> sred;
    public final PairList<C> strategy;

    static {
        logger = Logger.getLogger(SolvableGroebnerBaseAbstract.class);
    }

    public SolvableGroebnerBaseAbstract() {
        this(new SolvableReductionSeq());
    }

    public SolvableGroebnerBaseAbstract(SolvableReduction<C> sred) {
        this(sred, new OrderedPairlist());
    }

    public SolvableGroebnerBaseAbstract(PairList<C> pl) {
        this(new SolvableReductionSeq(), pl);
    }

    public SolvableGroebnerBaseAbstract(SolvableReduction<C> sred, PairList<C> pl) {
        this.debug = logger.isDebugEnabled();
        this.red = new ReductionSeq();
        this.sred = sred;
        this.strategy = pl;
        this.blas = new BasicLinAlg();
        this.cbb = new GroebnerBaseSeq();
    }

    public List<GenSolvablePolynomial<C>> normalizeZerosOnes(List<GenSolvablePolynomial<C>> A) {
        List<GenSolvablePolynomial<C>> N = new ArrayList(A.size());
        if (!(A == null || A.isEmpty())) {
            for (GenSolvablePolynomial<C> p : A) {
                if (!(p == null || p.isZERO())) {
                    if (p.isUnit()) {
                        N.clear();
                        N.add(p.ring.getONE());
                        break;
                    }
                    N.add((GenSolvablePolynomial) p.abs());
                }
            }
        }
        return N;
    }

    public boolean isLeftGB(List<GenSolvablePolynomial<C>> F) {
        return isLeftGB(0, F, true);
    }

    public boolean isLeftGB(List<GenSolvablePolynomial<C>> F, boolean b) {
        return isLeftGB(0, F, b);
    }

    public boolean isLeftGB(int modv, List<GenSolvablePolynomial<C>> F) {
        return isLeftGB(modv, F, true);
    }

    public boolean isLeftGB(int modv, List<GenSolvablePolynomial<C>> F, boolean b) {
        if (b) {
            return isLeftGBsimple(modv, F);
        }
        return isLeftGBidem(modv, F);
    }

    public boolean isLeftGBsimple(int modv, List<GenSolvablePolynomial<C>> F) {
        for (int i = 0; i < F.size(); i++) {
            GenPolynomial pi = (GenSolvablePolynomial) F.get(i);
            for (int j = i + 1; j < F.size(); j++) {
                GenPolynomial pj = (GenSolvablePolynomial) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    GenSolvablePolynomial s = this.sred.leftSPolynomial(pi, pj);
                    if (s.isZERO()) {
                        continue;
                    } else {
                        GenSolvablePolynomial<C> h = this.sred.leftNormalform((List) F, s);
                        if (!h.isZERO()) {
                            logger.info("no left GB: pi = " + pi + ", pj = " + pj);
                            logger.info("s  = " + s + ", h = " + h);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isLeftGBidem(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        GenSolvablePolynomialRing pring = ((GenSolvablePolynomial) F.get(0)).ring;
        return new PolynomialList(pring, (List) F).compareTo(new PolynomialList(pring, leftGB(modv, F))) == 0;
    }

    public boolean isTwosidedGB(List<GenSolvablePolynomial<C>> Fp) {
        return isTwosidedGB(0, Fp);
    }

    public boolean isTwosidedGB(int modv, List<GenSolvablePolynomial<C>> Fp) {
        if (Fp == null || Fp.size() == 0) {
            return true;
        }
        if (Fp.size() == 1 && ((GenSolvablePolynomial) Fp.get(0)).isONE()) {
            return true;
        }
        int i;
        List<GenSolvablePolynomial<C>> X = PolynomialList.castToSolvableList(((GenSolvablePolynomial) Fp.get(0)).ring.generators(modv));
        logger.info("right multipliers = " + X);
        List F = new ArrayList(Fp.size() * (X.size() + 1));
        F.addAll(Fp);
        for (i = 0; i < Fp.size(); i++) {
            int j;
            GenSolvablePolynomial<C> p = (GenSolvablePolynomial) Fp.get(i);
            for (j = 0; j < X.size(); j++) {
                GenSolvablePolynomial x = (GenSolvablePolynomial) X.get(j);
                if (!x.isONE()) {
                    if (!this.sred.leftNormalform(F, p.multiply(x)).isZERO()) {
                        return false;
                    }
                }
            }
        }
        for (i = 0; i < F.size(); i++) {
            GenPolynomial pi = (GenSolvablePolynomial) F.get(i);
            for (j = i + 1; j < F.size(); j++) {
                GenPolynomial pj = (GenSolvablePolynomial) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    GenSolvablePolynomial s = this.sred.leftSPolynomial(pi, pj);
                    if (s.isZERO()) {
                        continue;
                    } else {
                        GenSolvablePolynomial<C> h = this.sred.leftNormalform(F, s);
                        if (!h.isZERO()) {
                            logger.info("is not TwosidedGB: " + h);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isTwosidedGBidem(List<GenSolvablePolynomial<C>> F) {
        return isTwosidedGBidem(0, F);
    }

    public boolean isTwosidedGBidem(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        GenSolvablePolynomialRing pring = ((GenSolvablePolynomial) F.get(0)).ring;
        return new PolynomialList(pring, (List) F).compareTo(new PolynomialList(pring, twosidedGB(modv, F))) == 0;
    }

    public boolean isRightGB(List<GenSolvablePolynomial<C>> F) {
        return isRightGB(0, F);
    }

    public boolean isRightGB(int modv, List<GenSolvablePolynomial<C>> F) {
        for (int i = 0; i < F.size(); i++) {
            GenPolynomial pi = (GenSolvablePolynomial) F.get(i);
            for (int j = i + 1; j < F.size(); j++) {
                GenPolynomial pj = (GenSolvablePolynomial) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    GenSolvablePolynomial<C> s = this.sred.rightSPolynomial(pi, pj);
                    if (s.isZERO()) {
                        continue;
                    } else {
                        GenSolvablePolynomial<C> h = this.sred.rightNormalform(F, s);
                        if (!h.isZERO()) {
                            logger.info("isRightGB non zero h = " + h + " :: " + h.ring);
                            logger.info("p" + i + " = " + pi + ", p" + j + " = " + pj);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isRightGBidem(List<GenSolvablePolynomial<C>> F) {
        return isRightGBidem(0, F);
    }

    public boolean isRightGBidem(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        GenSolvablePolynomialRing pring = ((GenSolvablePolynomial) F.get(0)).ring;
        return new PolynomialList(pring, (List) F).compareTo(new PolynomialList(pring, rightGB(modv, F))) == 0;
    }

    public List<GenSolvablePolynomial<C>> leftGB(List<GenSolvablePolynomial<C>> F) {
        return leftGB(0, F);
    }

    public SolvableExtendedGB<C> extLeftGB(List<GenSolvablePolynomial<C>> F) {
        return extLeftGB(0, F);
    }

    public SolvableExtendedGB<C> extLeftGB(int modv, List<GenSolvablePolynomial<C>> list) {
        throw new UnsupportedOperationException("extLeftGB not implemented in " + getClass().getSimpleName());
    }

    public List<GenSolvablePolynomial<C>> leftMinimalGB(List<GenSolvablePolynomial<C>> Gp) {
        GenSolvablePolynomial<C> a;
        ArrayList<GenSolvablePolynomial<C>> G = new ArrayList();
        ListIterator<GenSolvablePolynomial<C>> it = Gp.listIterator();
        for (GenSolvablePolynomial<C> a2 : Gp) {
            if (a2.length() != 0) {
                G.add(a2);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        ArrayList<GenSolvablePolynomial<C>> F = new ArrayList();
        while (G.size() > 0) {
            a2 = (GenSolvablePolynomial) G.remove(0);
            ExpVector e = a2.leadingExpVector();
            it = G.listIterator();
            boolean mt = false;
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenSolvablePolynomial) it.next()).leadingExpVector());
            }
            it = F.listIterator();
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenSolvablePolynomial) it.next()).leadingExpVector());
            }
            if (!mt) {
                F.add(a2);
            }
        }
        List G2 = F;
        if (G2.size() <= 1) {
            return G2;
        }
        List F2 = new ArrayList();
        while (G2.size() > 0) {
            F2.add(this.sred.leftNormalform(F2, this.sred.leftNormalform(G2, (GenSolvablePolynomial) G2.remove(0))));
        }
        return F2;
    }

    public List<GenSolvablePolynomial<C>> rightMinimalGB(List<GenSolvablePolynomial<C>> Gp) {
        GenSolvablePolynomial<C> a;
        ArrayList<GenSolvablePolynomial<C>> G = new ArrayList();
        ListIterator<GenSolvablePolynomial<C>> it = Gp.listIterator();
        for (GenSolvablePolynomial<C> a2 : Gp) {
            if (a2.length() != 0) {
                G.add(a2);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        ArrayList<GenSolvablePolynomial<C>> F = new ArrayList();
        while (G.size() > 0) {
            a2 = (GenSolvablePolynomial) G.remove(0);
            ExpVector e = a2.leadingExpVector();
            it = G.listIterator();
            boolean mt = false;
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenSolvablePolynomial) it.next()).leadingExpVector());
            }
            it = F.listIterator();
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenSolvablePolynomial) it.next()).leadingExpVector());
            }
            if (!mt) {
                F.add(a2);
            }
        }
        G = F;
        if (G.size() <= 1) {
            return G;
        }
        List<GenSolvablePolynomial<C>> F2 = new ArrayList();
        while (G.size() > 0) {
            F2.add(this.sred.rightNormalform(F2, this.sred.rightNormalform(G, (GenSolvablePolynomial) G.remove(0))));
        }
        return F2;
    }

    public List<GenSolvablePolynomial<C>> twosidedGB(List<GenSolvablePolynomial<C>> Fp) {
        return twosidedGB(0, Fp);
    }

    public List<GenSolvablePolynomial<C>> rightGB(List<GenSolvablePolynomial<C>> F) {
        return rightGB(0, F);
    }

    public List<GenSolvablePolynomial<C>> rightGB(int modv, List<GenSolvablePolynomial<C>> F) {
        List<GenSolvablePolynomial<C>> G = normalizeZerosOnes(F);
        if (G.size() <= 1) {
            return G;
        }
        GenSolvablePolynomialRing rring = ((GenSolvablePolynomial) G.get(0)).ring.reverse(true);
        List rF = new ArrayList(F.size());
        for (GenSolvablePolynomial<C> p : F) {
            if (p != null) {
                rF.add((GenSolvablePolynomial) p.reverse(rring));
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("reversed problem = " + new PolynomialList(rring, rF).toScript());
        }
        List<GenSolvablePolynomial<C>> rG = leftGB(modv, rF);
        if (this.debug) {
            long t = System.currentTimeMillis();
            boolean isit = isLeftGB(rG);
            t = System.currentTimeMillis() - t;
            logger.info("is left GB = " + isit + ", in " + t + " milliseconds");
        }
        GenSolvablePolynomialRing<C> ring = rring.reverse(true);
        G = new ArrayList(rG.size());
        for (GenSolvablePolynomial<C> p2 : rG) {
            if (p2 != null) {
                G.add((GenSolvablePolynomial) p2.reverse(ring));
            }
        }
        if (this.debug) {
            t = System.currentTimeMillis();
            isit = isRightGB(G);
            t = System.currentTimeMillis() - t;
            logger.info("is right GB = " + isit + ", in " + t + " milliseconds");
        }
        return G;
    }

    public boolean isLeftReductionMatrix(SolvableExtendedGB<C> exgb) {
        if (exgb == null) {
            return true;
        }
        return isLeftReductionMatrix(exgb.F, exgb.G, exgb.F2G, exgb.G2F);
    }

    public boolean isLeftReductionMatrix(List<GenSolvablePolynomial<C>> F, List<GenSolvablePolynomial<C>> G, List<List<GenSolvablePolynomial<C>>> Mf, List<List<GenSolvablePolynomial<C>>> Mg) {
        int k = 0;
        for (List<GenSolvablePolynomial<C>> row : Mg) {
            if (this.sred.isLeftReductionNF(row, F, (GenSolvablePolynomial) G.get(k), null)) {
                k++;
            } else {
                System.out.println("row = " + row);
                System.out.println("F   = " + F);
                System.out.println("Gk  = " + G.get(k));
                logger.info("F isLeftReductionMatrix s, k = " + F.size() + ", " + k);
                return false;
            }
        }
        k = 0;
        for (List<GenSolvablePolynomial<C>> row2 : Mf) {
            if (this.sred.isLeftReductionNF(row2, G, (GenSolvablePolynomial) F.get(k), null)) {
                k++;
            } else {
                logger.error("G isLeftReductionMatrix s, k = " + G.size() + ", " + k);
                return false;
            }
        }
        return true;
    }

    public int commonZeroTest(List<GenSolvablePolynomial<C>> A) {
        return this.cbb.commonZeroTest(PolynomialList.castToList(A));
    }

    public List<Long> univariateDegrees(List<GenSolvablePolynomial<C>> A) {
        return this.cbb.univariateDegrees(PolynomialList.castToList(A));
    }

    public GenSolvablePolynomial<C> constructUnivariate(int i, List<GenSolvablePolynomial<C>> G) {
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
            GenPolynomialRing pfac = ((GenSolvablePolynomial) G.get(0)).ring;
            RingFactory cfac = pfac.coFac;
            GenPolynomialRing<C> cpfac = new GenPolynomialRing(cfac, ll, new TermOrder(2));
            GenSolvablePolynomialRing<GenPolynomial<C>> genSolvablePolynomialRing = new GenSolvablePolynomialRing((RingFactory) cpfac, pfac);
            GenSolvablePolynomial<GenPolynomial<C>> P = genSolvablePolynomialRing.getZERO();
            for (int k = 0; k < ll; k++) {
                P = (GenSolvablePolynomial) P.sum((GenPolynomial) genSolvablePolynomialRing.univariate(i, (long) k).multiply(cpfac.univariate((cpfac.nvar - 1) - k)));
            }
            if (this.debug) {
                logger.info("univariate construction, P = " + P);
                logger.info("univariate construction, deg_*(G) = " + ud);
            }
            GroebnerBaseAbstract<C> bbc = new GroebnerBaseSeq();
            int z;
            do {
                P = (GenSolvablePolynomial) P.sum((GenPolynomial) r25.univariate(i, (long) ll).multiply(cpfac.univariate((cpfac.nvar - 1) - ll)));
                GenSolvablePolynomial X = pfac.univariate(i, (long) ll);
                GenSolvablePolynomial XP = this.sred.leftNormalform((List) G, X);
                GenSolvablePolynomial XPp = PolyUtil.toRecursive((GenSolvablePolynomialRing) r25, XP);
                List<GenPolynomial<C>> arrayList = new ArrayList(((GenSolvablePolynomial) XPp.sum((GenPolynomial) P)).getMap().values());
                List<GenPolynomial<C>> ls = this.red.irreducibleSet(arrayList);
                z = bbc.commonZeroTest(ls);
                if (z != 0) {
                    ll++;
                    if (((long) ll) > vsdim) {
                        logger.info("univariate construction, P = " + P);
                        logger.info("univariate construction, nf(P) = " + XP);
                        logger.info("G = " + G);
                        throw new ArithmeticException("univariate polynomial degree greater than vector space dimansion");
                    }
                    cpfac = cpfac.extend(1);
                    genSolvablePolynomialRing = new GenSolvablePolynomialRing((RingFactory) cpfac, pfac);
                    P = (GenSolvablePolynomial) PolyUtil.extendCoefficients((GenSolvablePolynomialRing) genSolvablePolynomialRing, (GenSolvablePolynomial) P, 0, 0).sum((GenPolynomial) PolyUtil.extendCoefficients((GenSolvablePolynomialRing) genSolvablePolynomialRing, XPp, 0, 1));
                    continue;
                }
            } while (z != 0);
            String var = pfac.getVars()[(pfac.nvar - 1) - i];
            GenSolvablePolynomialRing<C> genSolvablePolynomialRing2 = new GenSolvablePolynomialRing(cfac, 1, new TermOrder(2), new String[]{var});
            GenSolvablePolynomial<C> pol = genSolvablePolynomialRing2.univariate(0, (long) ll);
            for (GenPolynomial<C> pc : ls) {
                ExpVector e = pc.leadingExpVector();
                if (e != null) {
                    int[] v = e.dependencyOnVariables();
                    if (!(v == null || v.length == 0)) {
                        RingElem tc = (RingElem) pc.trailingBaseCoefficient().negate();
                        pol = (GenSolvablePolynomial) pol.sum(genSolvablePolynomialRing2.univariate(0, (long) ((ll - 1) - v[0])).multiply(tc));
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

    public List<GenSolvablePolynomial<C>> constructUnivariate(List<GenSolvablePolynomial<C>> G) {
        List<GenSolvablePolynomial<C>> univs = new ArrayList();
        if (!(G == null || G.isEmpty())) {
            for (int i = ((GenSolvablePolynomial) G.get(0)).ring.nvar - 1; i >= 0; i--) {
                univs.add(constructUnivariate(i, G));
            }
        }
        return univs;
    }

    public void terminate() {
        logger.info("terminate not implemented");
    }

    public int cancel() {
        logger.info("cancel not implemented");
        return 0;
    }
}
