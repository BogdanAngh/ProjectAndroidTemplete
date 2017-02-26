package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.Pair;
import edu.jas.gb.PairList;
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

public class GroebnerBasePseudoSeq<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    protected final RingFactory<C> cofac;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final PseudoReduction<C> red;

    static {
        logger = Logger.getLogger(GroebnerBasePseudoSeq.class);
    }

    public GroebnerBasePseudoSeq(RingFactory<C> rf) {
        this(new PseudoReductionSeq(), rf, new OrderedPairlist());
    }

    public GroebnerBasePseudoSeq(RingFactory<C> rf, PairList<C> pl) {
        this(new PseudoReductionSeq(), rf, pl);
    }

    public GroebnerBasePseudoSeq(PseudoReduction<C> red, RingFactory<C> rf, PairList<C> pl) {
        super(red, pl);
        this.debug = logger.isDebugEnabled();
        this.red = red;
        this.cofac = rf;
        this.engine = GCDFactory.getImplementation((RingFactory) rf);
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        List<GenPolynomial<C>> G = this.engine.basePrimitivePart(normalizeZerosOnes(F));
        if (G.size() <= 1) {
            return G;
        }
        GenPolynomialRing<C> ring = ((GenPolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        PairList<C> pairlist = this.strategy.create(modv, ring);
        pairlist.put((List) G);
        while (pairlist.hasNext()) {
            Pair<C> pair = pairlist.removeNext();
            if (pair != null) {
                GenPolynomial<C> pi = pair.pi;
                GenPolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenPolynomial<C> S = this.red.SPolynomial(pi, pj);
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.debug("ht(S) = " + S.leadingExpVector());
                    }
                    GenPolynomial H = this.red.normalform((List) G, (GenPolynomial) S);
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.debug("ht(H) = " + H.leadingExpVector());
                        }
                        H = this.engine.basePrimitivePart(H).abs();
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
        G = minimalGB(G);
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G;
    }

    public List<GenPolynomial<C>> minimalGB(List<GenPolynomial<C>> Gp) {
        List<GenPolynomial<C>> G = normalizeZerosOnes(Gp);
        if (G.size() <= 1) {
            return G;
        }
        List<GenPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenPolynomial<C> a = (GenPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a) && !this.red.isTopReducible(F, a)) {
                F.add(a);
            } else if (this.debug) {
                System.out.println("dropped " + a);
                List<GenPolynomial<C>> ff = new ArrayList(G);
                ff.addAll(F);
                a = this.red.normalform((List) ff, (GenPolynomial) a);
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
            G.add(this.engine.basePrimitivePart(this.red.normalform((List) G, (GenPolynomial) (GenPolynomial) G.remove(0))).abs());
        }
        return G;
    }
}
