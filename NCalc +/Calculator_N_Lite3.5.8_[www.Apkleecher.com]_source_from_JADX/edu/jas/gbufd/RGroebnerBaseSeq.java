package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.Pair;
import edu.jas.gb.Reduction;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RegularRingElem;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class RGroebnerBaseSeq<C extends RegularRingElem<C>> extends GroebnerBaseAbstract<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    protected RReduction<C> rred;

    static {
        $assertionsDisabled = !RGroebnerBaseSeq.class.desiredAssertionStatus();
        logger = Logger.getLogger(RGroebnerBaseSeq.class);
    }

    public RGroebnerBaseSeq() {
        this(new RReductionSeq());
    }

    public RGroebnerBaseSeq(RReduction<C> rred) {
        super((Reduction) rred);
        this.debug = logger.isDebugEnabled();
        this.rred = rred;
        if (!$assertionsDisabled && this.red != this.rred) {
            throw new AssertionError();
        }
    }

    public boolean isGB(int modv, List<GenPolynomial<C>> F) {
        if (F == null) {
            return true;
        }
        if (this.rred.isBooleanClosed((List) F)) {
            for (int i = 0; i < F.size(); i++) {
                GenPolynomial pi = (GenPolynomial) F.get(i);
                for (int j = i + 1; j < F.size(); j++) {
                    GenPolynomial pj = (GenPolynomial) F.get(j);
                    if (this.red.moduleCriterion(modv, pi, pj)) {
                        GenPolynomial s = this.red.SPolynomial(pi, pj);
                        if (s.isZERO()) {
                            continue;
                        } else {
                            GenPolynomial<C> s2 = this.red.normalform((List) F, s);
                            if (!s2.isZERO()) {
                                if (this.debug) {
                                    logger.debug("p" + i + " = " + pi);
                                    logger.debug("p" + j + " = " + pj);
                                    logger.debug("s-pol = " + this.red.SPolynomial(pi, pj));
                                    logger.debug("s-pol(" + i + "," + j + ") != 0: " + s2);
                                }
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        if (this.debug) {
            logger.debug("not boolean closed");
        }
        return false;
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        List<GenPolynomial<C>> bcF = this.rred.reducedBooleanClosure(F);
        logger.info("#bcF-#F = " + (bcF.size() - F.size()));
        F = bcF;
        List<GenPolynomial<C>> G = new ArrayList();
        OrderedRPairlist<C> pairlist = null;
        for (GenPolynomial<C> p : F) {
            GenPolynomial<C> p2;
            if (!p2.isZERO()) {
                p2 = p2.monic();
                if (p2.isONE()) {
                    G.clear();
                    G.add(p2);
                    return G;
                }
                G.add(p2);
                if (pairlist == null) {
                    pairlist = new OrderedRPairlist(modv, p2.ring);
                }
                pairlist.put((GenPolynomial) p2);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        while (pairlist.hasNext()) {
            Pair<C> pair = pairlist.removeNext();
            if (pair != null) {
                GenPolynomial pi = pair.pi;
                GenPolynomial pj = pair.pj;
                if (logger.isDebugEnabled()) {
                    logger.debug("pi    = " + pi);
                    logger.debug("pj    = " + pj);
                }
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    GenPolynomial S = this.red.SPolynomial(pi, pj);
                    if (S.isZERO()) {
                        pair.setZero();
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("ht(S) = " + S.leadingExpVector());
                        }
                        GenPolynomial<C> H = this.red.normalform((List) G, S);
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
                                List<GenPolynomial<C>> bcH = this.rred.reducedBooleanClosure(G, H);
                                logger.info("#bcH = " + bcH.size());
                                for (GenPolynomial<C> h : bcH) {
                                    GenPolynomial<C> h2 = h2.monic();
                                    G.add(h2);
                                    pairlist.put((GenPolynomial) h2);
                                }
                                if (this.debug && !(pair.getUseCriterion3() && pair.getUseCriterion4())) {
                                    logger.info("H != 0 but: " + pair);
                                }
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        logger.debug("#sequential list = " + G.size());
        G = minimalGB(G);
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G;
    }

    public List<GenPolynomial<C>> minimalGB(List<GenPolynomial<C>> Gp) {
        if (Gp == null || Gp.size() <= 1) {
            return Gp;
        }
        GenPolynomial<C> a;
        List<GenPolynomial<C>> G = new ArrayList(Gp.size());
        for (GenPolynomial<C> a2 : Gp) {
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2);
            }
        }
        List<GenPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            List<GenPolynomial<C>> bcH;
            GenPolynomial a3 = (GenPolynomial) G.remove(0);
            GenPolynomial<C> b = a3;
            if (!this.red.isTopReducible(G, a3) && !this.red.isTopReducible(F, a3)) {
                F.add(a3);
            } else if (logger.isInfoEnabled()) {
                List ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform(ff, a3);
                if (!a2.isZERO()) {
                    logger.info("minGB not zero " + a2);
                    bcH = this.rred.reducedBooleanClosure(G, a2);
                    if (bcH.size() > 1) {
                        System.out.println("minGB not bc: bcH size = " + bcH.size());
                        F.add(b);
                    } else {
                        F.addAll(bcH);
                    }
                }
            }
        }
        List<GenPolynomial<C>> G2 = F;
        Collections.reverse(G2);
        int len = G2.size();
        for (int el = 0; el < len; el++) {
            a3 = (GenPolynomial) G2.remove(0);
            b = a3;
            bcH = this.rred.reducedBooleanClosure(G2, this.red.normalform((List) G2, a3));
            if (bcH.size() > 1) {
                System.out.println("minGB not bc: bcH size = " + bcH.size());
                G2.add(b);
            } else {
                G2.addAll(bcH);
            }
        }
        F = new ArrayList(G2.size());
        for (GenPolynomial<C> p : G2) {
            a2 = p.monic().abs();
            if (p.length() != a2.length()) {
                System.out.println("minGB not bc: #p != #a: a = " + a2 + ", p = " + p);
                a2 = p;
            }
            F.add(a2);
        }
        G = F;
        F = new ArrayList(G.size());
        for (int i = 0; i < G.size(); i++) {
            a2 = (GenPolynomial) G.get(i);
            if (!(a2 == null || a2.isZERO())) {
                ExpVector e = a2.leadingExpVector();
                for (int j = i + 1; j < G.size(); j++) {
                    GenPolynomial b2 = (GenPolynomial) G.get(j);
                    if (!(b2 == null || b2.isZERO() || !e.equals(b2.leadingExpVector()))) {
                        a2 = a2.sum(b2);
                        G.set(j, null);
                    }
                }
                F.add(a2);
            }
        }
        return F;
    }
}
