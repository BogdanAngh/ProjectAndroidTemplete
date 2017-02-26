package edu.jas.gbufd;

import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.Pair;
import edu.jas.gb.PairList;
import edu.jas.gb.SolvableExtendedGB;
import edu.jas.gb.SolvableGroebnerBaseAbstract;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import edu.jas.ufd.GreatestCommonDivisorFake;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class SolvableGroebnerBasePseudoRecSeq<C extends GcdRingElem<C>> extends SolvableGroebnerBaseAbstract<GenPolynomial<C>> {
    private static final Logger logger;
    protected final GenPolynomialRing<C> cofac;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final SolvablePseudoReduction<GenPolynomial<C>> sred;
    protected final SolvablePseudoReduction<C> sredRec;

    static {
        logger = Logger.getLogger(SolvableGroebnerBasePseudoRecSeq.class);
    }

    public SolvableGroebnerBasePseudoRecSeq(RingFactory<GenPolynomial<C>> rf) {
        this((RingFactory) rf, new SolvablePseudoReductionSeq());
    }

    public SolvableGroebnerBasePseudoRecSeq(RingFactory<GenPolynomial<C>> rf, PairList<GenPolynomial<C>> pl) {
        this(rf, new SolvablePseudoReductionSeq(), pl);
    }

    public SolvableGroebnerBasePseudoRecSeq(RingFactory<GenPolynomial<C>> rf, SolvablePseudoReduction<C> red) {
        this(rf, red, new OrderedPairlist());
    }

    public SolvableGroebnerBasePseudoRecSeq(RingFactory<GenPolynomial<C>> rf, SolvablePseudoReduction<C> red, PairList<GenPolynomial<C>> pl) {
        super(red, pl);
        this.debug = logger.isDebugEnabled();
        this.sred = red;
        this.sredRec = red;
        this.cofac = (GenPolynomialRing) rf;
        if (this.cofac.isCommutative()) {
            this.engine = GCDFactory.getProxy(this.cofac.coFac);
            return;
        }
        logger.warn("right reduction not correct for " + this.cofac.toScript());
        this.engine = new GreatestCommonDivisorFake();
    }

    public List<GenSolvablePolynomial<GenPolynomial<C>>> leftGB(int modv, List<GenSolvablePolynomial<GenPolynomial<C>>> F) {
        List<GenSolvablePolynomial<GenPolynomial<C>>> G = PolynomialList.castToSolvableList(PolyUtil.monicRec(this.engine.recursivePrimitivePart(PolynomialList.castToList(normalizeZerosOnes(F)))));
        if (G.size() <= 1) {
            return G;
        }
        GenSolvablePolynomialRing<GenPolynomial<C>> ring = ((GenSolvablePolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        PairList<GenPolynomial<C>> pairlist = this.strategy.create(modv, ring);
        pairlist.put(PolynomialList.castToList(G));
        logger.info("leftGB start " + pairlist);
        while (pairlist.hasNext()) {
            Pair<GenPolynomial<C>> pair = pairlist.removeNext();
            if (pair != null) {
                GenSolvablePolynomial<GenPolynomial<C>> pi = pair.pi;
                GenSolvablePolynomial<GenPolynomial<C>> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenSolvablePolynomial<GenPolynomial<C>> S = this.sred.leftSPolynomial(pi, pj);
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.info("ht(S) = " + S.leadingExpVector());
                    }
                    GenPolynomial H = this.sredRec.leftNormalformRecursive(G, S);
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.info("ht(H) = " + H.leadingExpVector() + ", #(H) = " + H.length());
                        }
                        H = PolyUtil.monic((GenSolvablePolynomial) this.engine.recursivePrimitivePart(H));
                        if (H.isConstant()) {
                            G.clear();
                            G.add(H);
                            return G;
                        }
                        if (this.debug) {
                            logger.info("lc(pp(H)) = " + ((GenPolynomial) H.leadingBaseCoefficient()).toScript());
                        }
                        if (H.length() > 0) {
                            G.add(H);
                            pairlist.put(H);
                        }
                    }
                }
            }
        }
        logger.debug("#sequential list = " + G.size());
        G = leftMinimalGB(G);
        logger.info("leftGB end  " + pairlist);
        return G;
    }

    public List<GenSolvablePolynomial<GenPolynomial<C>>> leftMinimalGB(List<GenSolvablePolynomial<GenPolynomial<C>>> Gp) {
        List<GenSolvablePolynomial<GenPolynomial<C>>> G = normalizeZerosOnes(Gp);
        if (G.size() <= 1) {
            return G;
        }
        List<GenSolvablePolynomial<GenPolynomial<C>>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenSolvablePolynomial<GenPolynomial<C>> a = (GenSolvablePolynomial) G.remove(0);
            if (!this.sred.isTopReducible(G, a) && !this.sred.isTopReducible(F, a)) {
                F.add(a);
            } else if (this.debug) {
                System.out.println("dropped " + a);
                List<GenSolvablePolynomial<GenPolynomial<C>>> ff = new ArrayList(G);
                ff.addAll(F);
                a = this.sredRec.leftNormalformRecursive(ff, a);
                if (!a.isZERO()) {
                    System.out.println("error, nf(a) " + a);
                }
            }
        }
        G = F;
        if (G.size() <= 1) {
            return G;
        }
        Collections.reverse(G);
        int len = G.size();
        for (int i = 0; i < len; i++) {
            G.add(PolyUtil.monic((GenSolvablePolynomial) this.engine.recursivePrimitivePart(this.sredRec.leftNormalformRecursive(G, (GenSolvablePolynomial) G.remove(0)))));
        }
        return G;
    }

    public List<GenSolvablePolynomial<GenPolynomial<C>>> twosidedGB(int modv, List<GenSolvablePolynomial<GenPolynomial<C>>> Fp) {
        List<GenSolvablePolynomial<GenPolynomial<C>>> G = normalizeZerosOnes(Fp);
        G = PolynomialList.castToSolvableList(PolyUtil.monicRec(this.engine.recursivePrimitivePart(PolynomialList.castToList(G))));
        if (G.size() < 1) {
            return G;
        }
        GenPolynomialRing ring = ((GenSolvablePolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        int j;
        List<GenSolvablePolynomial<GenPolynomial<C>>> X = PolynomialList.castToSolvableList(ring.generators(modv));
        logger.info("right multipliers = " + X);
        List<GenSolvablePolynomial<GenPolynomial<C>>> F = new ArrayList(G.size() * (X.size() + 1));
        F.addAll(G);
        for (int i = 0; i < F.size(); i++) {
            GenSolvablePolynomial<GenPolynomial<C>> p = (GenSolvablePolynomial) F.get(i);
            for (j = 0; j < X.size(); j++) {
                GenSolvablePolynomial<GenPolynomial<C>> x = (GenSolvablePolynomial) X.get(j);
                if (!x.isONE()) {
                    GenSolvablePolynomial<GenPolynomial<C>> q = p.multiply((GenSolvablePolynomial) x);
                    GenPolynomial q2 = this.sredRec.leftNormalformRecursive(F, q);
                    if (!q2.isZERO()) {
                        F.add(PolyUtil.monic((GenSolvablePolynomial) this.engine.recursivePrimitivePart(q2)));
                    }
                }
            }
        }
        G = F;
        PairList<GenPolynomial<C>> pairlist = this.strategy.create(modv, ring);
        pairlist.put(PolynomialList.castToList(G));
        logger.info("twosidedGB start " + pairlist);
        while (pairlist.hasNext()) {
            Pair<GenPolynomial<C>> pair = pairlist.removeNext();
            if (pair != null) {
                GenSolvablePolynomial<GenPolynomial<C>> pi = pair.pi;
                GenSolvablePolynomial<GenPolynomial<C>> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenSolvablePolynomial<GenPolynomial<C>> S = this.sred.leftSPolynomial(pi, pj);
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.info("ht(S) = " + S.leadingExpVector());
                    }
                    GenPolynomial H = this.sredRec.leftNormalformRecursive(G, S);
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.info("ht(H) = " + H.leadingExpVector());
                        }
                        H = PolyUtil.monic((GenSolvablePolynomial) this.engine.recursivePrimitivePart(H));
                        if (H.isONE()) {
                            G.clear();
                            G.add(H);
                            return G;
                        }
                        if (this.debug) {
                            logger.info("lc(pp(H)) = " + H.leadingBaseCoefficient());
                        }
                        if (H.length() > 0) {
                            G.add(H);
                            pairlist.put(H);
                            for (j = 0; j < X.size(); j++) {
                                x = (GenSolvablePolynomial) X.get(j);
                                if (!x.isONE()) {
                                    p = H.multiply((GenSolvablePolynomial) x);
                                    GenPolynomial p2 = this.sredRec.leftNormalformRecursive(G, p);
                                    if (p2.isZERO()) {
                                        continue;
                                    } else {
                                        p2 = PolyUtil.monic((GenSolvablePolynomial) this.engine.recursivePrimitivePart(p2));
                                        if (p2.isONE()) {
                                            G.clear();
                                            G.add(p2);
                                            return G;
                                        }
                                        G.add(p2);
                                        pairlist.put(p2);
                                    }
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        logger.debug("#sequential list = " + G.size());
        G = leftMinimalGB(G);
        logger.info("twosidedGB end  " + pairlist);
        return G;
    }

    public boolean isLeftGBsimple(int modv, List<GenSolvablePolynomial<GenPolynomial<C>>> F) {
        for (int i = 0; i < F.size(); i++) {
            GenPolynomial pi = (GenSolvablePolynomial) F.get(i);
            for (int j = i + 1; j < F.size(); j++) {
                GenPolynomial pj = (GenSolvablePolynomial) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    GenSolvablePolynomial<GenPolynomial<C>> s = this.sred.leftSPolynomial(pi, pj);
                    if (!(s.isZERO() || this.sredRec.leftNormalformRecursive(F, s).isZERO())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isLeftGBidem(int modv, List<GenSolvablePolynomial<GenPolynomial<C>>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        GenSolvablePolynomialRing pring = ((GenSolvablePolynomial) F.get(0)).ring;
        return new PolynomialList(pring, (List) F).compareTo(new PolynomialList(pring, leftGB(modv, F))) == 0;
    }

    public boolean isTwosidedGB(int modv, List<GenSolvablePolynomial<GenPolynomial<C>>> Fp) {
        if (Fp == null || Fp.size() == 0) {
            return true;
        }
        int i;
        GenSolvablePolynomialRing<GenPolynomial<C>> ring = ((GenSolvablePolynomial) Fp.get(0)).ring;
        List<GenSolvablePolynomial<GenPolynomial<C>>> X = PolynomialList.castToSolvableList(ring.generators());
        List<GenSolvablePolynomial<GenPolynomial<C>>> Y = new ArrayList();
        for (GenSolvablePolynomial<GenPolynomial<C>> x : X) {
            if (x.isConstant()) {
                Y.add(x);
            }
        }
        X = Y;
        X.addAll(ring.univariateList(modv));
        logger.info("right multipliers = " + X);
        List<GenSolvablePolynomial<GenPolynomial<C>>> F = new ArrayList(Fp.size() * (X.size() + 1));
        F.addAll(Fp);
        for (i = 0; i < Fp.size(); i++) {
            int j;
            GenSolvablePolynomial<GenPolynomial<C>> p = (GenSolvablePolynomial) Fp.get(i);
            for (j = 0; j < X.size(); j++) {
                GenSolvablePolynomial x2 = (GenSolvablePolynomial) X.get(j);
                if (!x2.isONE()) {
                    p = this.sredRec.leftNormalformRecursive(F, p.multiply(x2));
                    if (!p.isZERO()) {
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
                    GenSolvablePolynomial<GenPolynomial<C>> s = this.sred.leftSPolynomial(pi, pj);
                    if (s.isZERO()) {
                        continue;
                    } else {
                        GenSolvablePolynomial<GenPolynomial<C>> h = this.sredRec.leftNormalformRecursive(F, s);
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

    public SolvableExtendedGB<GenPolynomial<C>> extLeftGB(int modv, List<GenSolvablePolynomial<GenPolynomial<C>>> list) {
        throw new UnsupportedOperationException();
    }
}
