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
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import edu.jas.ufd.GreatestCommonDivisorFake;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class SolvableGroebnerBasePseudoSeq<C extends GcdRingElem<C>> extends SolvableGroebnerBaseAbstract<C> {
    private static final Logger logger;
    protected final RingFactory<C> cofac;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final SolvablePseudoReduction<C> sred;

    static {
        logger = Logger.getLogger(SolvableGroebnerBasePseudoSeq.class);
    }

    public SolvableGroebnerBasePseudoSeq(RingFactory<C> rf) {
        this(new SolvablePseudoReductionSeq(), rf, new OrderedPairlist());
    }

    public SolvableGroebnerBasePseudoSeq(RingFactory<C> rf, PairList<C> pl) {
        this(new SolvablePseudoReductionSeq(), rf, pl);
    }

    public SolvableGroebnerBasePseudoSeq(SolvablePseudoReduction<C> red, RingFactory<C> rf, PairList<C> pl) {
        super(red, pl);
        this.debug = logger.isDebugEnabled();
        this.sred = red;
        this.cofac = rf;
        if (this.cofac.isCommutative()) {
            this.engine = GCDFactory.getProxy((RingFactory) rf);
            return;
        }
        logger.warn("right reduction not correct for " + this.cofac.toScript());
        this.engine = new GreatestCommonDivisorFake();
    }

    public List<GenSolvablePolynomial<C>> leftGB(int modv, List<GenSolvablePolynomial<C>> F) {
        List<GenSolvablePolynomial<C>> G = PolynomialList.castToSolvableList(this.engine.basePrimitivePart(PolynomialList.castToList(normalizeZerosOnes(F))));
        if (G.size() <= 1) {
            return G;
        }
        GenSolvablePolynomialRing<C> ring = ((GenSolvablePolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        PairList<C> pairlist = this.strategy.create(modv, ring);
        pairlist.put(PolynomialList.castToList(G));
        while (pairlist.hasNext()) {
            Pair<C> pair = pairlist.removeNext();
            if (pair != null) {
                GenSolvablePolynomial<C> pi = pair.pi;
                GenSolvablePolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenSolvablePolynomial<C> S = this.sred.leftSPolynomial(pi, pj);
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.debug("ht(S) = " + S.leadingExpVector());
                    }
                    GenPolynomial H = this.sred.leftNormalform((List) G, (GenSolvablePolynomial) S);
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.debug("ht(H) = " + H.leadingExpVector());
                        }
                        H = (GenSolvablePolynomial) ((GenSolvablePolynomial) this.engine.basePrimitivePart(H)).abs();
                        if (H.isConstant()) {
                            G.clear();
                            G.add(H);
                            return G;
                        }
                        if (logger.isDebugEnabled()) {
                            logger.debug("H = " + H);
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
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G;
    }

    public List<GenSolvablePolynomial<C>> leftMinimalGB(List<GenSolvablePolynomial<C>> Gp) {
        List<GenSolvablePolynomial<C>> G = normalizeZerosOnes(Gp);
        if (G.size() <= 1) {
            return G;
        }
        List<GenSolvablePolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenSolvablePolynomial<C> a = (GenSolvablePolynomial) G.remove(0);
            if (!this.sred.isTopReducible(G, a) && !this.sred.isTopReducible(F, a)) {
                F.add(a);
            } else if (this.debug) {
                System.out.println("dropped " + a);
                List<GenSolvablePolynomial<C>> ff = new ArrayList(G);
                ff.addAll(F);
                a = this.sred.leftNormalform((List) ff, (GenSolvablePolynomial) a);
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
            G.add((GenSolvablePolynomial) ((GenSolvablePolynomial) this.engine.basePrimitivePart(this.sred.leftNormalform((List) G, (GenSolvablePolynomial) (GenSolvablePolynomial) G.remove(0)))).abs());
        }
        return G;
    }

    public List<GenSolvablePolynomial<C>> twosidedGB(int modv, List<GenSolvablePolynomial<C>> Fp) {
        List<GenSolvablePolynomial<C>> G = normalizeZerosOnes(Fp);
        G = PolynomialList.castToSolvableList(this.engine.basePrimitivePart(PolynomialList.castToList(G)));
        if (G.size() < 1) {
            return G;
        }
        GenPolynomialRing ring = ((GenSolvablePolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        List<GenSolvablePolynomial<C>> X = PolynomialList.castToSolvableList(ring.generators(modv));
        logger.info("right multipliers = " + X);
        List<GenSolvablePolynomial<C>> F = new ArrayList(G.size() * (X.size() + 1));
        F.addAll(G);
        for (int i = 0; i < F.size(); i++) {
            int j;
            GenSolvablePolynomial<C> p = (GenSolvablePolynomial) F.get(i);
            for (j = 0; j < X.size(); j++) {
                GenSolvablePolynomial<C> q = p.multiply((GenSolvablePolynomial) X.get(j));
                GenPolynomial q2 = this.sred.leftNormalform((List) F, (GenSolvablePolynomial) q);
                if (!q2.isZERO()) {
                    F.add((GenSolvablePolynomial) ((GenSolvablePolynomial) this.engine.basePrimitivePart(q2)).abs());
                }
            }
        }
        G = F;
        PairList<C> pairlist = this.strategy.create(modv, ring);
        pairlist.put(PolynomialList.castToList(G));
        while (pairlist.hasNext()) {
            Pair<C> pair = pairlist.removeNext();
            if (pair != null) {
                GenSolvablePolynomial<C> pi = pair.pi;
                GenSolvablePolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenSolvablePolynomial<C> S = this.sred.leftSPolynomial(pi, pj);
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.debug("ht(S) = " + S.leadingExpVector());
                    }
                    GenPolynomial H = this.sred.leftNormalform((List) G, (GenSolvablePolynomial) S);
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.debug("ht(H) = " + H.leadingExpVector());
                        }
                        H = (GenSolvablePolynomial) ((GenSolvablePolynomial) this.engine.basePrimitivePart(H)).abs();
                        if (H.isONE()) {
                            G.clear();
                            G.add(H);
                            return G;
                        }
                        if (this.debug) {
                            logger.debug("H = " + H);
                        }
                        if (H.length() > 0) {
                            G.add(H);
                            pairlist.put(H);
                            for (j = 0; j < X.size(); j++) {
                                p = H.multiply((GenSolvablePolynomial) X.get(j));
                                GenPolynomial p2 = this.sred.leftNormalform((List) G, (GenSolvablePolynomial) p);
                                if (!p2.isZERO()) {
                                    p2 = (GenSolvablePolynomial) ((GenSolvablePolynomial) this.engine.basePrimitivePart(p2)).abs();
                                    if (p2.isONE()) {
                                        G.clear();
                                        G.add(p2);
                                        return G;
                                    }
                                    G.add(p2);
                                    pairlist.put(p2);
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
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G;
    }

    public SolvableExtendedGB<C> extLeftGB(int modv, List<GenSolvablePolynomial<C>> list) {
        throw new UnsupportedOperationException("TODO");
    }
}
