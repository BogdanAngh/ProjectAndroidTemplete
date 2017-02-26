package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RingElem;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;

public class GroebnerBaseSeqPairSeq<C extends RingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(GroebnerBaseSeqPairSeq.class);
    }

    public GroebnerBaseSeqPairSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public GroebnerBaseSeqPairSeq(Reduction<C> red) {
        super((Reduction) red);
        this.debug = logger.isDebugEnabled();
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        List<GenPolynomial<C>> G = new ArrayList();
        if (F == null) {
            return G;
        }
        CriticalPairList<C> pairlist = null;
        int len = F.size();
        ListIterator<GenPolynomial<C>> it = F.listIterator();
        while (it.hasNext()) {
            GenPolynomial<C> p = (GenPolynomial) it.next();
            if (p.length() > 0) {
                p = p.monic();
                if (p.isONE()) {
                    G.clear();
                    G.add(p);
                    return G;
                }
                G.add(p);
                if (pairlist == null) {
                    pairlist = new CriticalPairList(modv, p.ring);
                }
                pairlist.put(p);
            } else {
                len--;
            }
        }
        if (len <= 1) {
            return G;
        }
        while (pairlist.hasNext()) {
            CriticalPair<C> pair = pairlist.getNext();
            if (pair == null) {
                pairlist.update();
            } else {
                GenPolynomial<C> pi = pair.pi;
                GenPolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenPolynomial S = this.red.SPolynomial(pi, pj);
                if (S.isZERO()) {
                    pairlist.update(pair, S);
                } else {
                    if (this.debug) {
                        logger.debug("ht(S) = " + S.leadingExpVector());
                    }
                    GenPolynomial<C> H = this.red.normalform((List) G, S);
                    if (H.isZERO()) {
                        pairlist.update(pair, H);
                    } else {
                        if (this.debug) {
                            logger.debug("ht(H) = " + H.leadingExpVector());
                        }
                        H = H.monic();
                        if (H.isONE()) {
                            G.clear();
                            G.add(H);
                            return G;
                        }
                        if (this.debug) {
                            logger.debug("H = " + H);
                        }
                        G.add(H);
                        pairlist.update(pair, H);
                    }
                }
            }
        }
        logger.debug("#sequential list = " + G.size());
        G = minimalGB(G);
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G;
    }

    public ExtendedGB<C> extGB(int modv, List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            throw new IllegalArgumentException("null or empty F not allowed");
        }
        List<GenPolynomial<C>> arrayList;
        List<GenPolynomial<C>> G = new ArrayList();
        List<List<GenPolynomial<C>>> F2G = new ArrayList();
        List<List<GenPolynomial<C>>> G2F = new ArrayList();
        CriticalPairList<C> pairlist = null;
        boolean oneInGB = false;
        int len = F.size();
        GenPolynomialRing<C> ring = null;
        int nzlen = 0;
        for (GenPolynomial<C> f : F) {
            if (f.length() > 0) {
                nzlen++;
            }
            if (ring == null) {
                ring = f.ring;
            }
        }
        GenPolynomial<C> mone = ring.getONE();
        int k = 0;
        ListIterator<GenPolynomial<C>> it = F.listIterator();
        while (it.hasNext()) {
            int j;
            GenPolynomial<C> p = (GenPolynomial) it.next();
            if (p.length() > 0) {
                arrayList = new ArrayList(nzlen);
                for (j = 0; j < nzlen; j++) {
                    arrayList.add(null);
                }
                arrayList.set(k, mone);
                k++;
                if (p.isUnit()) {
                    G.clear();
                    G.add(p);
                    G2F.clear();
                    G2F.add(arrayList);
                    oneInGB = true;
                    break;
                }
                G.add(p);
                G2F.add(arrayList);
                if (pairlist == null) {
                    CriticalPairList<C> criticalPairList = new CriticalPairList(modv, p.ring);
                }
                pairlist.put(p);
            } else {
                len--;
            }
        }
        GenPolynomial<C> H;
        if (len <= 1 || oneInGB) {
            for (GenPolynomial<C> f2 : F) {
                arrayList = new ArrayList(G.size());
                for (j = 0; j < G.size(); j++) {
                    arrayList.add(null);
                }
                H = this.red.normalform(arrayList, G, f2);
                if (!H.isZERO()) {
                    logger.error("nonzero H = " + H);
                }
                F2G.add(arrayList);
            }
            return new ExtendedGB(F, G, F2G, G2F);
        }
        ExtendedGB<C> exgb;
        while (pairlist.hasNext() && !oneInGB) {
            int m;
            CriticalPair<C> pair = pairlist.getNext();
            if (pair == null) {
                pairlist.update();
            } else {
                int i = pair.i;
                j = pair.j;
                GenPolynomial<C> pi = pair.pi;
                GenPolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.info("i, pi    = " + i + ", " + pi);
                    logger.info("j, pj    = " + j + ", " + pj);
                }
                List<GenPolynomial<C>> rows = new ArrayList(G.size());
                for (m = 0; m < G.size(); m++) {
                    rows.add(null);
                }
                GenPolynomial<C> S = this.red.SPolynomial(rows, i, pi, j, pj);
                if (this.debug) {
                    logger.debug("is reduction S = " + this.red.isReductionNF(rows, G, ring.getZERO(), S));
                }
                if (S.isZERO()) {
                    pairlist.update(pair, S);
                } else {
                    if (this.debug) {
                        logger.debug("ht(S) = " + S.leadingExpVector());
                    }
                    arrayList = new ArrayList(G.size());
                    for (m = 0; m < G.size(); m++) {
                        arrayList.add(null);
                    }
                    H = this.red.normalform(arrayList, G, S);
                    if (this.debug) {
                        logger.debug("is reduction H = " + this.red.isReductionNF(arrayList, G, S, H));
                    }
                    if (H.isZERO()) {
                        pairlist.update(pair, H);
                    } else {
                        if (this.debug) {
                            logger.debug("ht(H) = " + H.leadingExpVector());
                        }
                        arrayList = new ArrayList(G.size() + 1);
                        for (m = 0; m < G.size(); m++) {
                            GenPolynomial<C> x = (GenPolynomial) rows.get(m);
                            if (x != null) {
                                x = x.negate();
                            }
                            GenPolynomial y = (GenPolynomial) arrayList.get(m);
                            if (y != null) {
                                y = y.negate();
                            }
                            if (x == null) {
                                x = y;
                            } else {
                                x = x.sum(y);
                            }
                            arrayList.add(x);
                        }
                        if (this.debug) {
                            logger.debug("is reduction 0+sum(row,G) == H : " + this.red.isReductionNF(arrayList, G, H, ring.getZERO()));
                        }
                        arrayList.add(null);
                        RingElem c = (RingElem) H.leadingBaseCoefficient().inverse();
                        H = H.multiply(c);
                        List<GenPolynomial<C>> row = this.blas.scalarProduct(mone.multiply(c), (List) arrayList);
                        row.set(G.size(), mone);
                        if (H.isONE()) {
                            G.add(H);
                            G2F.add(row);
                            break;
                        }
                        if (this.debug) {
                            logger.debug("H = " + H);
                        }
                        G.add(H);
                        pairlist.update(pair, H);
                        G2F.add(row);
                    }
                }
            }
        }
        if (this.debug) {
            exgb = new ExtendedGB(F, G, F2G, G2F);
            logger.info("exgb unnorm = " + exgb);
        }
        G2F = normalizeMatrix(F.size(), G2F);
        if (this.debug) {
            exgb = new ExtendedGB(F, G, F2G, G2F);
            logger.info("exgb nonmin = " + exgb);
            boolean t2 = isReductionMatrix(exgb);
            logger.info("exgb t2 = " + t2);
        }
        exgb = minimalExtendedGB(F.size(), G, G2F);
        G = exgb.G;
        G2F = exgb.G2F;
        logger.debug("#sequential list = " + G.size());
        logger.info(BuildConfig.FLAVOR + pairlist);
        for (GenPolynomial<C> f22 : F) {
            arrayList = new ArrayList(G.size());
            for (m = 0; m < G.size(); m++) {
                arrayList.add(null);
            }
            H = this.red.normalform(arrayList, G, f22);
            if (!H.isZERO()) {
                logger.error("nonzero H = " + H);
            }
            F2G.add(arrayList);
        }
        exgb = new ExtendedGB(F, G, F2G, G2F);
        if (this.debug) {
            logger.info("exgb nonmin = " + exgb);
            t2 = isReductionMatrix(exgb);
            logger.info("exgb t2 = " + t2);
        }
        return exgb;
    }
}
