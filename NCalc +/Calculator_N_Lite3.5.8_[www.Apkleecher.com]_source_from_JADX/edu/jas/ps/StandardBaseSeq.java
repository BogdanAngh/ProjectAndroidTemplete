package edu.jas.ps;

import edu.jas.poly.ExpVector;
import edu.jas.structure.RingElem;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class StandardBaseSeq<C extends RingElem<C>> {
    private static final Logger logger;
    private final boolean debug;
    public final ReductionSeq<C> red;

    static {
        logger = Logger.getLogger(StandardBaseSeq.class);
    }

    public StandardBaseSeq() {
        this(new ReductionSeq());
    }

    public StandardBaseSeq(ReductionSeq<C> red) {
        this.debug = logger.isDebugEnabled();
        this.red = red;
    }

    public List<MultiVarPowerSeries<C>> normalizeZerosOnes(List<MultiVarPowerSeries<C>> A) {
        List<MultiVarPowerSeries<C>> N = new ArrayList(A.size());
        if (!(A == null || A.isEmpty())) {
            for (MultiVarPowerSeries<C> p : A) {
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

    public boolean isSTD(List<MultiVarPowerSeries<C>> F) {
        return isSTD(0, F);
    }

    public boolean isSTD(int modv, List<MultiVarPowerSeries<C>> F) {
        if (F == null) {
            return true;
        }
        for (int i = 0; i < F.size(); i++) {
            MultiVarPowerSeries pi = (MultiVarPowerSeries) F.get(i);
            for (int j = i + 1; j < F.size(); j++) {
                MultiVarPowerSeries pj = (MultiVarPowerSeries) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    MultiVarPowerSeries<C> s = this.red.SPolynomial(pi, pj);
                    if (s.isZERO()) {
                        continue;
                    } else {
                        MultiVarPowerSeries<C> h = this.red.normalform(F, s);
                        if (!h.isZERO()) {
                            System.out.println("pi = " + pi + ", pj = " + pj);
                            System.out.println("s  = " + s + ", h = " + h);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public List<MultiVarPowerSeries<C>> STD(List<MultiVarPowerSeries<C>> F) {
        return STD(0, F);
    }

    public List<MultiVarPowerSeries<C>> STD(int modv, List<MultiVarPowerSeries<C>> F) {
        List<MultiVarPowerSeries<C>> G = PSUtil.monic(normalizeZerosOnes(F));
        if (G.size() <= 1) {
            return G;
        }
        MultiVarPowerSeriesRing<C> ring = ((MultiVarPowerSeries) G.get(0)).ring;
        if (ring.coFac.isField()) {
            OrderedPairlist<C> pairlist = new OrderedPairlist(modv, ring);
            pairlist.put((List) G);
            logger.info("start " + pairlist);
            while (pairlist.hasNext()) {
                Pair<C> pair = pairlist.removeNext();
                if (pair != null) {
                    MultiVarPowerSeries<C> pi = pair.pi;
                    MultiVarPowerSeries<C> pj = pair.pj;
                    if (this.debug) {
                        logger.debug("pi    = " + pi);
                        logger.debug("pj    = " + pj);
                    }
                    MultiVarPowerSeries<C> S = this.red.SPolynomial(pi, pj);
                    if (S.isZERO()) {
                        pair.setZero();
                    } else {
                        if (logger.isInfoEnabled()) {
                            ExpVector es = S.orderExpVector();
                            logger.info("ht(S) = " + es.toString(S.ring.vars) + ", " + es);
                        }
                        MultiVarPowerSeries H = this.red.normalform(G, S);
                        if (H.isZERO()) {
                            pair.setZero();
                        } else {
                            if (logger.isInfoEnabled()) {
                                ExpVector eh = H.orderExpVector();
                                logger.info("ht(H) = " + eh.toString(S.ring.vars) + ", " + eh);
                            }
                            if (H.isUnit()) {
                                G.clear();
                                G.add(H);
                                return G;
                            }
                            if (logger.isDebugEnabled()) {
                                logger.info("H = " + H);
                            }
                            G.add(H);
                            pairlist.put(H);
                        }
                    }
                }
            }
            logger.debug("#sequential list = " + G.size());
            G = minimalSTD(G);
            logger.info(BuildConfig.FLAVOR + pairlist);
            return G;
        }
        throw new IllegalArgumentException("coefficients not from a field");
    }

    public List<MultiVarPowerSeries<C>> minimalSTD(List<MultiVarPowerSeries<C>> Gp) {
        if (Gp == null || Gp.size() <= 1) {
            return Gp;
        }
        List<MultiVarPowerSeries<C>> G = new ArrayList(Gp.size());
        for (MultiVarPowerSeries<C> a : Gp) {
            MultiVarPowerSeries<C> a2;
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2.monic());
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        List<MultiVarPowerSeries<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            a2 = (MultiVarPowerSeries) G.remove(0);
            if (!this.red.isTopReducible(G, a2) && !this.red.isTopReducible(F, a2)) {
                F.add(a2);
            } else if (this.debug) {
                System.out.println("dropped " + a2);
                List<MultiVarPowerSeries<C>> ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform(ff, a2);
                if (!a2.isZERO()) {
                    System.out.println("error, nf(a) " + a2);
                }
            }
        }
        return F;
    }
}
