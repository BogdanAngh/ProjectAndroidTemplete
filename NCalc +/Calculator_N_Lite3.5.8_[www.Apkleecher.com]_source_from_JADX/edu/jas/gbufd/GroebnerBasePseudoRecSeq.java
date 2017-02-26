package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.Pair;
import edu.jas.gb.PairList;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class GroebnerBasePseudoRecSeq<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<GenPolynomial<C>> {
    private static final Logger logger;
    protected final RingFactory<C> baseCofac;
    protected final RingFactory<GenPolynomial<C>> cofac;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final PseudoReduction<GenPolynomial<C>> red;
    protected final PseudoReduction<C> redRec;

    static {
        logger = Logger.getLogger(GroebnerBasePseudoRecSeq.class);
    }

    public GroebnerBasePseudoRecSeq(RingFactory<GenPolynomial<C>> rf) {
        this(new PseudoReductionSeq(), rf, new OrderedPairlist(new GenPolynomialRing((RingFactory) rf, 1)));
    }

    public GroebnerBasePseudoRecSeq(RingFactory<GenPolynomial<C>> rf, PairList<GenPolynomial<C>> pl) {
        this(new PseudoReductionSeq(), rf, pl);
    }

    public GroebnerBasePseudoRecSeq(PseudoReduction<GenPolynomial<C>> red, RingFactory<GenPolynomial<C>> rf, PairList<GenPolynomial<C>> pl) {
        super(red, pl);
        this.debug = logger.isDebugEnabled();
        this.red = red;
        this.redRec = red;
        this.cofac = rf;
        this.baseCofac = this.cofac.coFac;
        this.engine = GCDFactory.getProxy(this.baseCofac);
    }

    public List<GenPolynomial<GenPolynomial<C>>> GB(int modv, List<GenPolynomial<GenPolynomial<C>>> F) {
        List<GenPolynomial<GenPolynomial<C>>> G = this.engine.recursivePrimitivePart(normalizeZerosOnes(F));
        if (G.size() <= 1) {
            return G;
        }
        GenPolynomialRing<GenPolynomial<C>> ring = ((GenPolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        PairList<GenPolynomial<C>> pairlist = this.strategy.create(modv, ring);
        pairlist.put((List) G);
        while (pairlist.hasNext()) {
            Pair<GenPolynomial<C>> pair = pairlist.removeNext();
            if (pair != null) {
                GenPolynomial<GenPolynomial<C>> pi = pair.pi;
                GenPolynomial<GenPolynomial<C>> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenPolynomial<GenPolynomial<C>> S = this.red.SPolynomial(pi, pj);
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.info("ht(S) = " + S.leadingExpVector());
                    }
                    GenPolynomial H = this.redRec.normalformRecursive(G, S);
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.info("ht(H) = " + H.leadingExpVector());
                        }
                        H = this.engine.recursivePrimitivePart(H).abs();
                        if (H.isConstant()) {
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
                        }
                    }
                }
            }
        }
        logger.debug("#sequential list = " + G.size());
        G = minimalGB(G);
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G;
    }

    public List<GenPolynomial<GenPolynomial<C>>> minimalGB(List<GenPolynomial<GenPolynomial<C>>> Gp) {
        List<GenPolynomial<GenPolynomial<C>>> G = normalizeZerosOnes(Gp);
        if (G.size() <= 1) {
            return G;
        }
        List<GenPolynomial<GenPolynomial<C>>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenPolynomial<GenPolynomial<C>> a = (GenPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a) && !this.red.isTopReducible(F, a)) {
                F.add(a);
            } else if (this.debug) {
                System.out.println("dropped " + a);
                List<GenPolynomial<GenPolynomial<C>>> ff = new ArrayList(G);
                ff.addAll(F);
                a = this.redRec.normalformRecursive(ff, a);
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
            G.add(this.engine.recursivePrimitivePart(this.redRec.normalformRecursive(G, (GenPolynomial) G.remove(0))).abs());
        }
        return G;
    }

    public boolean isGBsimple(int modv, List<GenPolynomial<GenPolynomial<C>>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        for (int i = 0; i < F.size(); i++) {
            GenPolynomial<GenPolynomial<C>> pi = (GenPolynomial) F.get(i);
            ExpVector ei = pi.leadingExpVector();
            for (int j = i + 1; j < F.size(); j++) {
                GenPolynomial<GenPolynomial<C>> pj = (GenPolynomial) F.get(j);
                ExpVector ej = pj.leadingExpVector();
                if (this.red.moduleCriterion(modv, ei, ej)) {
                    if (this.red.criterion4(ei, ej, ei.lcm(ej))) {
                        GenPolynomial<GenPolynomial<C>> s = this.red.SPolynomial(pi, pj);
                        if (s.isZERO()) {
                            continue;
                        } else {
                            GenPolynomial<GenPolynomial<C>> h = this.redRec.normalformRecursive(F, s);
                            if (!h.isZERO()) {
                                logger.info("no GB: pi = " + pi + ", pj = " + pj);
                                logger.info("s  = " + s + ", h = " + h);
                                return false;
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return true;
    }
}
