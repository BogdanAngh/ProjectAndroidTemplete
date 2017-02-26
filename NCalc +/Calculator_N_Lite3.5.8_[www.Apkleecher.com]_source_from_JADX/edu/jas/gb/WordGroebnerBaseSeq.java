package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.poly.GenWordPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import io.github.kexanie.library.BuildConfig;
import java.util.List;
import org.apache.log4j.Logger;

public class WordGroebnerBaseSeq<C extends RingElem<C>> extends WordGroebnerBaseAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(WordGroebnerBaseSeq.class);
    }

    public WordGroebnerBaseSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public WordGroebnerBaseSeq(WordReduction<C> red) {
        super(red);
        this.debug = logger.isDebugEnabled();
    }

    public WordGroebnerBaseSeq(WordReduction<C> red, WordPairList<C> pl) {
        super(red, pl);
        this.debug = logger.isDebugEnabled();
    }

    public List<GenWordPolynomial<C>> GB(List<GenWordPolynomial<C>> F) {
        List<GenWordPolynomial<C>> G = PolyUtil.wordMonic(normalizeZerosOnes(F));
        if (G.size() <= 1) {
            return G;
        }
        GenWordPolynomialRing<C> ring = ((GenWordPolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            OrderedWordPairlist<C> pairlist = (OrderedWordPairlist) this.strategy.create(ring);
            pairlist.put((List) G);
            logger.info("start " + pairlist);
            while (pairlist.hasNext()) {
                WordPair<C> pair = pairlist.removeNext();
                if (pair != null) {
                    GenWordPolynomial<C> pi = pair.pi;
                    GenWordPolynomial<C> pj = pair.pj;
                    if (this.debug) {
                        logger.info("pi   = " + pi + ", pj = " + pj);
                    }
                    List<GenWordPolynomial<C>> S = this.red.SPolynomials(pi, pj);
                    if (S.isEmpty()) {
                        continue;
                    } else {
                        for (GenWordPolynomial s : S) {
                            if (!s.isZERO()) {
                                if (this.debug) {
                                    logger.info("ht(S) = " + s.leadingWord());
                                }
                                boolean t = pairlist.criterion3(pair.i, pair.j, s.leadingWord());
                                GenWordPolynomial<C> H = this.red.normalform((List) G, s);
                                if (this.debug) {
                                    logger.info("ht(H) = " + H.monic());
                                }
                                if (H.isZERO()) {
                                    continue;
                                } else {
                                    if (!t) {
                                        logger.info("criterion3(" + pair.i + "," + pair.j + ") wrong: " + s.leadingWord() + " --> " + H.leadingWord());
                                    }
                                    GenWordPolynomial H2 = H.monic();
                                    if (this.debug) {
                                        logger.info("ht(H) = " + H2.leadingWord());
                                    }
                                    if (H2.isONE()) {
                                        G.clear();
                                        G.add(H2);
                                        return G;
                                    }
                                    if (this.debug) {
                                        logger.info("H = " + H2);
                                    }
                                    if (H2.length() > 0) {
                                        G.add(H2);
                                        pairlist.put(H2);
                                    }
                                }
                            }
                        }
                        continue;
                    }
                }
            }
            G = minimalGB(G);
            logger.info(BuildConfig.FLAVOR + pairlist);
            return G;
        }
        throw new IllegalArgumentException("coefficients not from a field");
    }
}
