package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;

public class SolvableGroebnerBaseSeq<C extends RingElem<C>> extends SolvableGroebnerBaseAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(SolvableGroebnerBaseSeq.class);
    }

    public SolvableGroebnerBaseSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public SolvableGroebnerBaseSeq(SolvableReduction<C> sred) {
        super((SolvableReduction) sred);
        this.debug = logger.isDebugEnabled();
    }

    public SolvableGroebnerBaseSeq(PairList<C> pl) {
        super((PairList) pl);
        this.debug = logger.isDebugEnabled();
    }

    public SolvableGroebnerBaseSeq(SolvableReduction<C> sred, PairList<C> pl) {
        super(sred, pl);
        this.debug = logger.isDebugEnabled();
    }

    public List<GenSolvablePolynomial<C>> leftGB(int modv, List<GenSolvablePolynomial<C>> F) {
        List<GenSolvablePolynomial<C>> G = PolynomialList.castToSolvableList(PolyUtil.monic(PolynomialList.castToList(normalizeZerosOnes(F))));
        if (G.size() <= 1) {
            return G;
        }
        GenSolvablePolynomialRing<C> ring = ((GenSolvablePolynomial) G.get(0)).ring;
        if (ring.coFac.isField() || !ring.coFac.isCommutative()) {
            PairList<C> pairlist = this.strategy.create(modv, ring);
            pairlist.put(PolynomialList.castToList(G));
            logger.info("start " + pairlist);
            while (pairlist.hasNext()) {
                Pair<C> pair = pairlist.removeNext();
                if (pair != null) {
                    GenSolvablePolynomial<C> pi = pair.pi;
                    GenSolvablePolynomial<C> pj = pair.pj;
                    if (this.debug) {
                        logger.info("pi    = " + pi.leadingExpVector());
                        logger.info("pj    = " + pj.leadingExpVector());
                    }
                    GenSolvablePolynomial S = this.sred.leftSPolynomial(pi, pj);
                    if (S.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.info("ht(S) = " + S.leadingExpVector());
                        }
                        GenSolvablePolynomial<C> H = this.sred.leftNormalform((List) G, S);
                        if (H.isZERO()) {
                            pair.setZero();
                        } else {
                            if (this.debug) {
                                logger.info("ht(H) = " + H.leadingExpVector());
                            }
                            GenPolynomial H2 = H.monic();
                            if (H2.isONE()) {
                                G.clear();
                                G.add(H2);
                                return G;
                            }
                            if (this.debug) {
                                logger.info("#monic(H) = " + H2.length());
                            }
                            if (H2.length() > 0) {
                                G.add(H2);
                                pairlist.put(H2);
                            }
                        }
                    }
                }
            }
            logger.debug("#sequential list = " + G.size());
            G = leftMinimalGB(G);
            logger.info("end " + pairlist);
            return G;
        }
        throw new IllegalArgumentException("coefficients not from a field: " + ring.coFac.toScript());
    }

    public SolvableExtendedGB<C> extLeftGB(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            throw new IllegalArgumentException("null or empty F not allowed");
        }
        List<GenSolvablePolynomial<C>> arrayList;
        int j;
        List<GenSolvablePolynomial<C>> G = new ArrayList();
        List<List<GenSolvablePolynomial<C>>> F2G = new ArrayList();
        List<List<GenSolvablePolynomial<C>>> G2F = new ArrayList();
        PairList<C> pairlist = null;
        boolean oneInGB = false;
        int len = F.size();
        GenSolvablePolynomialRing<C> ring = null;
        int nzlen = 0;
        for (GenSolvablePolynomial<C> f : F) {
            if (f.length() > 0) {
                nzlen++;
            }
            if (ring == null) {
                ring = f.ring;
            }
        }
        GenSolvablePolynomial<C> mone = ring.getONE();
        int k = 0;
        ListIterator<GenSolvablePolynomial<C>> it = F.listIterator();
        while (it.hasNext()) {
            GenPolynomial p = (GenSolvablePolynomial) it.next();
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
                    pairlist = this.strategy.create(modv, p.ring);
                }
                pairlist.put(p);
            } else {
                len--;
            }
        }
        GenSolvablePolynomial<C> H;
        if (len <= 1 || oneInGB) {
            for (GenSolvablePolynomial<C> f2 : F) {
                arrayList = new ArrayList(G.size());
                for (j = 0; j < G.size(); j++) {
                    arrayList.add(null);
                }
                H = this.sred.leftNormalform(arrayList, G, f2);
                if (!H.isZERO()) {
                    logger.error("nonzero H = " + H);
                }
                F2G.add(arrayList);
            }
            return new SolvableExtendedGB(F, G, F2G, G2F);
        }
        int m;
        SolvableExtendedGB<C> exgb;
        logger.info("start " + pairlist);
        while (pairlist.hasNext() && !oneInGB) {
            Pair<C> pair = pairlist.removeNext();
            if (pair != null) {
                int i = pair.i;
                j = pair.j;
                GenSolvablePolynomial<C> pi = pair.pi;
                GenSolvablePolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.info("i, pi    = " + i + ", " + pi);
                    logger.info("j, pj    = " + j + ", " + pj);
                }
                List<GenSolvablePolynomial<C>> rows = new ArrayList(G.size());
                for (m = 0; m < G.size(); m++) {
                    rows.add(null);
                }
                GenSolvablePolynomial<C> S = this.sred.leftSPolynomial(rows, i, pi, j, pj);
                if (this.debug) {
                    logger.debug("is reduction S = " + this.sred.isLeftReductionNF(rows, G, ring.getZERO(), S));
                }
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.debug("ht(S) = " + S.leadingExpVector());
                    }
                    arrayList = new ArrayList(G.size());
                    for (m = 0; m < G.size(); m++) {
                        arrayList.add(null);
                    }
                    H = this.sred.leftNormalform(arrayList, G, S);
                    if (this.debug) {
                        logger.debug("is reduction H = " + this.sred.isLeftReductionNF(arrayList, G, S, H));
                    }
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.debug("ht(H) = " + H.leadingExpVector());
                        }
                        arrayList = new ArrayList(G.size() + 1);
                        for (m = 0; m < G.size(); m++) {
                            GenSolvablePolynomial<C> x = (GenSolvablePolynomial) rows.get(m);
                            if (x != null) {
                                x = (GenSolvablePolynomial) x.negate();
                            }
                            GenSolvablePolynomial<C> y = (GenSolvablePolynomial) arrayList.get(m);
                            if (y != null) {
                                y = (GenSolvablePolynomial) y.negate();
                            }
                            if (x == null) {
                                x = y;
                            } else {
                                x = (GenSolvablePolynomial) x.sum((GenPolynomial) y);
                            }
                            arrayList.add(x);
                        }
                        if (this.debug) {
                            logger.debug("is reduction 0+sum(row,G) == H : " + this.sred.isLeftReductionNF(arrayList, G, H, ring.getZERO()));
                        }
                        arrayList.add(null);
                        RingElem c = (RingElem) H.leadingBaseCoefficient().inverse();
                        GenPolynomial H2 = H.multiply(c);
                        List<GenSolvablePolynomial<C>> row = PolynomialList.castToSolvableList(this.blas.scalarProduct((RingElem) mone.multiply(c), PolynomialList.castToList(arrayList)));
                        row.set(G.size(), mone);
                        if (H2.isONE()) {
                            G.add(H2);
                            G2F.add(row);
                            break;
                        }
                        if (this.debug) {
                            logger.debug("H = " + H2);
                        }
                        G.add(H2);
                        pairlist.put(H2);
                        G2F.add(row);
                    }
                }
            }
        }
        if (this.debug) {
            exgb = new SolvableExtendedGB(F, G, F2G, G2F);
            logger.info("exgb unnorm = " + exgb);
        }
        G2F = normalizeMatrix(F.size(), G2F);
        if (this.debug) {
            exgb = new SolvableExtendedGB(F, G, F2G, G2F);
            logger.info("exgb nonmin = " + exgb);
            boolean t2 = isLeftReductionMatrix(exgb);
            logger.debug("exgb t2 = " + t2);
        }
        exgb = minimalSolvableExtendedGB(F.size(), G, G2F);
        G = exgb.G;
        G2F = exgb.G2F;
        logger.debug("#sequential list = " + G.size());
        logger.info("end " + pairlist);
        for (GenSolvablePolynomial<C> f22 : F) {
            arrayList = new ArrayList(G.size());
            for (m = 0; m < G.size(); m++) {
                arrayList.add(null);
            }
            H = this.sred.leftNormalform(arrayList, G, f22);
            if (!H.isZERO()) {
                logger.error("nonzero H = " + H);
            }
            F2G.add(arrayList);
        }
        logger.info("extGB end");
        return new SolvableExtendedGB(F, G, F2G, G2F);
    }

    public List<GenSolvablePolynomial<C>> twosidedGB(int modv, List<GenSolvablePolynomial<C>> Fp) {
        List<GenSolvablePolynomial<C>> F = PolynomialList.castToSolvableList(PolyUtil.monic(PolynomialList.castToList(normalizeZerosOnes(Fp))));
        if (F.size() < 1) {
            return F;
        }
        if (F.size() == 1 && ((GenSolvablePolynomial) F.get(0)).isONE()) {
            return F;
        }
        GenSolvablePolynomialRing<C> ring = ((GenSolvablePolynomial) F.get(0)).ring;
        if (!ring.coFac.isField()) {
            if (ring.coFac.isCommutative()) {
                throw new IllegalArgumentException("coefficients not from a field");
            }
        }
        List<GenSolvablePolynomial<C>> X = PolynomialList.castToSolvableList(ring.generators(modv));
        logger.info("right multipliers = " + X);
        List G = new ArrayList(F.size() * (X.size() + 1));
        G.addAll(F);
        for (int i = 0; i < G.size(); i++) {
            GenSolvablePolynomial<C> p = (GenSolvablePolynomial) G.get(i);
            for (GenSolvablePolynomial<C> x : X) {
                if (!x.isONE()) {
                    GenSolvablePolynomial q = p.multiply((GenSolvablePolynomial) x);
                    GenSolvablePolynomial<C> q2 = this.sred.leftNormalform(G, q).monic();
                    if (q2.isZERO()) {
                        continue;
                    } else if (q2.isONE()) {
                        G.clear();
                        G.add(q2);
                        return G;
                    } else {
                        G.add(q2);
                    }
                }
            }
        }
        PairList<C> pairlist = this.strategy.create(modv, ring);
        pairlist.put(PolynomialList.castToList(G));
        if (G.size() <= 1) {
            return G;
        }
        logger.info("twosided start " + pairlist);
        while (pairlist.hasNext()) {
            Pair<C> pair = pairlist.removeNext();
            if (pair != null) {
                GenSolvablePolynomial<C> pi = pair.pi;
                GenSolvablePolynomial<C> pj = pair.pj;
                if (this.debug) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                GenSolvablePolynomial S = this.sred.leftSPolynomial(pi, pj);
                if (S.isZERO()) {
                    pair.setZero();
                } else {
                    if (this.debug) {
                        logger.debug("ht(S) = " + S.leadingExpVector());
                    }
                    GenSolvablePolynomial<C> H = this.sred.leftNormalform(G, S);
                    if (H.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.debug("ht(H) = " + H.leadingExpVector());
                        }
                        GenPolynomial H2 = H.monic();
                        if (H2.isONE()) {
                            G.clear();
                            G.add(H2);
                            return G;
                        }
                        if (this.debug) {
                            logger.debug("H = " + H2);
                        }
                        if (H2.length() > 0) {
                            G.add(H2);
                            pairlist.put(H2);
                            for (GenSolvablePolynomial<C> x2 : X) {
                                if (!x2.isONE()) {
                                    q = H2.multiply((GenSolvablePolynomial) x2);
                                    p = this.sred.leftNormalform(G, q);
                                    if (p.isZERO()) {
                                        continue;
                                    } else {
                                        GenPolynomial p2 = p.monic();
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
        List<GenSolvablePolynomial<C>> G2 = leftMinimalGB(G);
        logger.info("twosided end " + pairlist);
        return G2;
    }

    public List<List<GenSolvablePolynomial<C>>> normalizeMatrix(int flen, List<List<GenSolvablePolynomial<C>>> M) {
        if (M == null || M.size() == 0) {
            return M;
        }
        int i;
        List<List<GenSolvablePolynomial<C>>> N = new ArrayList();
        List<List<GenSolvablePolynomial<C>>> K = new ArrayList();
        int len = ((List) M.get(M.size() - 1)).size();
        for (List<GenSolvablePolynomial<C>> row : M) {
            List<GenSolvablePolynomial<C>> row2;
            List<GenSolvablePolynomial<C>> nrow = new ArrayList(row2);
            for (i = row2.size(); i < len; i++) {
                nrow.add(null);
            }
            N.add(nrow);
        }
        int k = flen;
        for (i = 0; i < N.size(); i++) {
            row2 = (List) N.get(i);
            if (this.debug) {
                logger.info("row = " + row2);
            }
            K.add(row2);
            if (i >= flen) {
                for (int j = i + 1; j < N.size(); j++) {
                    nrow = (List) N.get(j);
                    if (k < nrow.size()) {
                        RingElem a = (GenSolvablePolynomial) nrow.get(k);
                        if (!(a == null || a.isZERO())) {
                            N.set(j, PolynomialList.castToSolvableList(this.blas.vectorAdd(this.blas.scalarProduct(a, PolynomialList.castToList(row2)), PolynomialList.castToList(nrow))));
                        }
                    }
                }
                k++;
            }
        }
        N.clear();
        for (List<GenSolvablePolynomial<C>> row22 : K) {
            List<GenSolvablePolynomial<C>> tr = new ArrayList();
            for (i = 0; i < flen; i++) {
                tr.add(row22.get(i));
            }
            N.add(tr);
        }
        return N;
    }

    public boolean isLeftReductionMatrix(SolvableExtendedGB<C> exgb) {
        if (exgb == null) {
            return true;
        }
        return isLeftReductionMatrix(exgb.F, exgb.G, exgb.F2G, exgb.G2F);
    }

    public SolvableExtendedGB<C> minimalSolvableExtendedGB(int flen, List<GenSolvablePolynomial<C>> Gp, List<List<GenSolvablePolynomial<C>>> M) {
        if (Gp == null) {
            return null;
        }
        if (Gp.size() <= 1) {
            return new SolvableExtendedGB(null, Gp, null, M);
        }
        List<GenSolvablePolynomial<C>> G = new ArrayList(Gp);
        List<GenSolvablePolynomial<C>> F = new ArrayList(Gp.size());
        List<List<GenSolvablePolynomial<C>>> Mg = new ArrayList(M.size());
        List<List<GenSolvablePolynomial<C>>> Mf = new ArrayList(M.size());
        for (List<GenSolvablePolynomial<C>> arrayList : M) {
            Mg.add(new ArrayList(arrayList));
        }
        ArrayList<Integer> ix = new ArrayList();
        ArrayList<Integer> jx = new ArrayList();
        int k = 0;
        while (G.size() > 0) {
            GenSolvablePolynomial<C> a = (GenSolvablePolynomial) G.remove(0);
            ExpVector e = a.leadingExpVector();
            ListIterator<GenSolvablePolynomial<C>> it = G.listIterator();
            boolean mt = false;
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenSolvablePolynomial) it.next()).leadingExpVector());
            }
            it = F.listIterator();
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenSolvablePolynomial) it.next()).leadingExpVector());
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
        return (F.size() <= 1 || fix == -1) ? new SolvableExtendedGB(null, F, null, Mf) : new SolvableExtendedGB(null, F, null, Mf);
    }

    public List<GenSolvablePolynomial<C>> rightGB(int modv, List<GenSolvablePolynomial<C>> F) {
        List<GenSolvablePolynomial<C>> G = PolynomialList.castToSolvableList(PolyUtil.monic(PolynomialList.castToList(normalizeZerosOnes(F))));
        if (G.size() <= 1) {
            return G;
        }
        GenSolvablePolynomialRing<C> ring = ((GenSolvablePolynomial) G.get(0)).ring;
        if (ring.coFac.isField() || !ring.coFac.isCommutative()) {
            PairList<C> pairlist = this.strategy.create(modv, ring);
            pairlist.put(PolynomialList.castToList(G));
            logger.info("start " + pairlist);
            while (pairlist.hasNext()) {
                Pair<C> pair = pairlist.removeNext();
                if (pair != null) {
                    GenSolvablePolynomial<C> pi = pair.pi;
                    GenSolvablePolynomial<C> pj = pair.pj;
                    if (this.debug) {
                        logger.info("pi    = " + pi);
                        logger.info("pj    = " + pj);
                    }
                    GenSolvablePolynomial<C> S = this.sred.rightSPolynomial(pi, pj);
                    if (S.isZERO()) {
                        pair.setZero();
                    } else {
                        if (this.debug) {
                            logger.info("ht(S) = " + S.leadingExpVector());
                        }
                        GenSolvablePolynomial<C> H = this.sred.rightNormalform(G, S);
                        if (H.isZERO()) {
                            pair.setZero();
                        } else {
                            if (this.debug) {
                                logger.info("ht(H) = " + H.leadingExpVector());
                            }
                            GenPolynomial H2 = H.monic();
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
            }
            logger.debug("#sequential list = " + G.size());
            G = rightMinimalGB(G);
            logger.info("end " + pairlist);
            return G;
        }
        throw new IllegalArgumentException("coefficients not from a field");
    }
}
