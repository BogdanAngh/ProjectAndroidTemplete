package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import io.github.kexanie.library.BuildConfig;
import java.util.List;
import org.apache.log4j.Logger;

public class DGroebnerBaseSeq<C extends RingElem<C>> extends GroebnerBaseAbstract<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    protected DReduction<C> dred;

    static {
        $assertionsDisabled = !DGroebnerBaseSeq.class.desiredAssertionStatus();
        logger = Logger.getLogger(DGroebnerBaseSeq.class);
    }

    public DGroebnerBaseSeq() {
        this(new DReductionSeq());
    }

    public DGroebnerBaseSeq(DReduction<C> dred) {
        super((Reduction) dred);
        this.debug = logger.isDebugEnabled();
        this.dred = dred;
        if (!$assertionsDisabled && this.dred != this.red) {
            throw new AssertionError();
        }
    }

    public boolean isGB(int modv, List<GenPolynomial<C>> F) {
        for (int i = 0; i < F.size(); i++) {
            GenPolynomial pi = (GenPolynomial) F.get(i);
            for (int j = i + 1; j < F.size(); j++) {
                GenPolynomial pj = (GenPolynomial) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    GenPolynomial<C> d = this.dred.GPolynomial(pi, pj);
                    if (!d.isZERO()) {
                        d = this.red.normalform((List) F, (GenPolynomial) d);
                    }
                    if (!d.isZERO()) {
                        System.out.println("d-pol(" + i + "," + j + ") != 0: " + d);
                        return false;
                    } else if (this.red.criterion4(pi, pj)) {
                        GenPolynomial<C> s = this.red.SPolynomial(pi, pj);
                        if (!s.isZERO()) {
                            s = this.red.normalform((List) F, (GenPolynomial) s);
                        }
                        if (!s.isZERO()) {
                            System.out.println("s-pol(" + i + "," + j + ") != 0: " + s);
                            return false;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return true;
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        List<GenPolynomial<C>> G = normalizeZerosOnes(F);
        if (G.size() <= 1) {
            return G;
        }
        OrderedDPairlist<C> pairlist = new OrderedDPairlist(modv, ((GenPolynomial) G.get(0)).ring);
        pairlist.put((List) G);
        while (pairlist.hasNext()) {
            Pair<C> pair = pairlist.removeNext();
            if (pair != null) {
                GenPolynomial<C> H;
                GenPolynomial<C> pi = pair.pi;
                GenPolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenPolynomial D = this.dred.GPolynomial(pi, pj);
                if (!(D.isZERO() || this.red.isTopReducible(G, D))) {
                    H = this.red.normalform((List) G, D);
                    if (H.isONE()) {
                        G.clear();
                        G.add(H);
                        return G;
                    } else if (!H.isZERO()) {
                        logger.info("Dred = " + H);
                        G.add(H);
                        pairlist.put((GenPolynomial) H);
                    }
                }
                if (pair.getUseCriterion3() && pair.getUseCriterion4()) {
                    GenPolynomial S = this.red.SPolynomial(pi, pj);
                    if (S.isZERO()) {
                        pair.setZero();
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("ht(S) = " + S.leadingExpVector());
                        }
                        H = this.red.normalform((List) G, S);
                        if (H.isZERO()) {
                            pair.setZero();
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("ht(H) = " + H.leadingExpVector());
                            }
                            if (H.isONE()) {
                                G.clear();
                                G.add(H);
                                return G;
                            }
                            if (logger.isDebugEnabled()) {
                                logger.debug("H = " + H);
                            }
                            if (!H.isZERO()) {
                                logger.info("Sred = " + H);
                                G.add(H);
                                pairlist.put((GenPolynomial) H);
                            }
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
}
