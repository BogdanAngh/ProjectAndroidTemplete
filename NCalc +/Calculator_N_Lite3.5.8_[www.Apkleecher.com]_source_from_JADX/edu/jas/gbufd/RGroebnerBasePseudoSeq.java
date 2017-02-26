package edu.jas.gbufd;

import edu.jas.gb.Pair;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RegularRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class RGroebnerBasePseudoSeq<C extends RegularRingElem<C>> extends RGroebnerBaseSeq<C> {
    private static final Logger logger;
    protected final RingFactory<C> cofac;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final RPseudoReduction<C> red;

    static {
        logger = Logger.getLogger(RGroebnerBasePseudoSeq.class);
    }

    public RGroebnerBasePseudoSeq(RingFactory<C> rf) {
        this(new RPseudoReductionSeq(), rf);
    }

    public RGroebnerBasePseudoSeq(RPseudoReduction<C> red, RingFactory<C> rf) {
        super(red);
        this.debug = logger.isDebugEnabled();
        this.red = red;
        this.cofac = rf;
        this.engine = GCDFactory.getImplementation((RingFactory) rf);
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        if (F == null) {
            return F;
        }
        List<GenPolynomial<C>> bcF = this.red.reducedBooleanClosure(F);
        logger.info("#bcF-#F = " + (bcF.size() - F.size()));
        F = bcF;
        List<GenPolynomial<C>> G = new ArrayList();
        OrderedRPairlist<C> pairlist = null;
        for (GenPolynomial p : F) {
            if (!p.isZERO()) {
                GenPolynomial<C> p2 = this.engine.basePrimitivePart(p).abs();
                if (p2.isConstant() && ((RegularRingElem) p2.leadingBaseCoefficient()).isFull()) {
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
                GenPolynomial<C> pi = pair.pi;
                GenPolynomial<C> pj = pair.pj;
                if (logger.isDebugEnabled()) {
                    logger.info("pi    = " + pi);
                    logger.info("pj    = " + pj);
                }
                if (this.red.moduleCriterion(modv, (GenPolynomial) pi, (GenPolynomial) pj)) {
                    GenPolynomial<C> S = this.red.SPolynomial(pi, pj);
                    if (S.isZERO()) {
                        pair.setZero();
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("ht(S) = " + S.leadingExpVector());
                        }
                        GenPolynomial H = this.red.normalform((List) G, (GenPolynomial) S);
                        if (H.isZERO()) {
                            pair.setZero();
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("ht(H) = " + H.leadingExpVector());
                            }
                            GenPolynomial<C> H2 = this.engine.basePrimitivePart(H).abs();
                            if (H2.isConstant() && ((RegularRingElem) H2.leadingBaseCoefficient()).isFull()) {
                                G.clear();
                                G.add(H2);
                                return G;
                            }
                            if (logger.isDebugEnabled()) {
                                logger.debug("H = " + H2);
                            }
                            if (!H2.isZERO()) {
                                for (GenPolynomial h : this.red.reducedBooleanClosure(G, H2)) {
                                    GenPolynomial<C> h2 = this.engine.basePrimitivePart(h).abs();
                                    logger.info("bc(Sred) = " + h2);
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
        List<GenPolynomial<C>> ff;
        List<GenPolynomial<C>> G = new ArrayList(Gp.size());
        for (GenPolynomial<C> a2 : Gp) {
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2.abs());
            }
        }
        logger.info("minGB start with " + G.size());
        List<GenPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            a2 = (GenPolynomial) G.remove(0);
            GenPolynomial<C> b = a2;
            if (this.red.isTopReducible(G, a2) || this.red.isTopReducible(F, a2)) {
                ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform((List) ff, (GenPolynomial) a2);
                if (!a2.isZERO()) {
                    logger.info("minGB not zero " + a2);
                    F.add(a2);
                } else if (this.debug) {
                    logger.debug("minGB dropped " + b);
                }
            } else {
                F.add(a2);
            }
        }
        G = F;
        Collections.reverse(G);
        int len = G.size();
        int el = 0;
        while (el < len) {
            el++;
            a2 = (GenPolynomial) G.remove(0);
            b = a2;
            a2 = this.engine.basePrimitivePart(this.red.normalform((List) G, (GenPolynomial) a2)).abs();
            if (this.red.isBooleanClosed((GenPolynomial) a2)) {
                if (this.debug) {
                    logger.debug("minGB reduced " + b + " to " + a2);
                }
                G.add(a2);
            } else {
                logger.info("minGB not boolean closed " + a2);
                G.add(b);
            }
        }
        F = new ArrayList(G.size());
        ff = new ArrayList(G);
        for (int i = 0; i < ff.size(); i++) {
            a2 = (GenPolynomial) ff.get(i);
            if (!(a2 == null || a2.isZERO())) {
                ExpVector e = a2.leadingExpVector();
                for (int j = i + 1; j < ff.size(); j++) {
                    GenPolynomial b2 = (GenPolynomial) ff.get(j);
                    if (!(b2 == null || b2.isZERO() || !e.equals(b2.leadingExpVector()))) {
                        a2 = a2.sum(b2);
                        ff.set(j, null);
                    }
                }
                F.add(a2);
            }
        }
        G = F;
        logger.info("minGB end with #G = " + G.size());
        return G;
    }

    List<GenPolynomial<C>> minimalGBtesting(List<GenPolynomial<C>> Gp) {
        if (Gp == null || Gp.size() <= 1) {
            return Gp;
        }
        GenPolynomial<C> a;
        List<GenPolynomial<C>> bcH;
        List<GenPolynomial<C>> G = new ArrayList(Gp.size());
        for (GenPolynomial<C> a2 : Gp) {
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2);
            }
        }
        List<GenPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            List<GenPolynomial<C>> ff;
            a2 = (GenPolynomial) G.remove(0);
            GenPolynomial<C> b = a2;
            if (!this.red.isTopReducible(G, a2)) {
                if (!this.red.isTopReducible(F, a2)) {
                    F.add(a2);
                }
            }
            if (logger.isInfoEnabled()) {
                ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform((List) ff, (GenPolynomial) a2);
                if (!a2.isZERO()) {
                    System.out.println("minGB nf(a) != 0 " + a2);
                    bcH = this.red.reducedBooleanClosure(G, a2);
                    if (bcH.size() > 1) {
                        System.out.println("minGB not bc: bcH size = " + bcH.size());
                        F.add(b);
                    } else {
                        F.add(b);
                    }
                } else if (isGB(ff)) {
                    System.out.println("minGB dropped " + b);
                } else {
                    System.out.println("minGB not dropped " + b);
                    F.add(b);
                }
            }
        }
        G = F;
        Collections.reverse(G);
        int len = G.size();
        int el = 0;
        while (el < len) {
            el++;
            a2 = (GenPolynomial) G.remove(0);
            b = a2;
            GenPolynomial a3 = this.red.normalform((List) G, (GenPolynomial) a2);
            a2 = this.engine.basePrimitivePart(a3);
            if (this.red.isBooleanClosed((GenPolynomial) a2)) {
                ff = new ArrayList(G);
                ff.add(a2);
                if (isGB(ff)) {
                    System.out.println("minGB reduced " + b + " to " + a2);
                    G.add(a2);
                } else {
                    System.out.println("minGB not reduced " + b + " to " + a2);
                    G.add(b);
                }
            } else {
                System.out.println("minGB not bc: a = " + a2 + "\n BC(a) = " + this.red.booleanClosure(a2) + ", BR(a) = " + this.red.booleanRemainder(a2));
                bcH = this.red.reducedBooleanClosure(G, a2);
                if (bcH.size() > 1) {
                    System.out.println("minGB not bc: bcH size = " + bcH.size());
                    G.add(b);
                } else {
                    G.add(b);
                    for (GenPolynomial h : bcH) {
                        this.engine.basePrimitivePart(h).abs();
                    }
                }
            }
        }
        F = new ArrayList(G.size());
        for (GenPolynomial<C> p : G) {
            F.add(p.abs());
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
        G = F;
        Map<C, List<GenPolynomial<C>>> bd = new TreeMap();
        for (GenPolynomial<C> p2 : G) {
            C cf = ((RegularRingElem) p2.leadingBaseCoefficient()).idempotent();
            List<GenPolynomial<C>> block = (List) bd.get(cf);
            if (block == null) {
                block = new ArrayList();
            }
            block.add(p2);
            bd.put(cf, block);
        }
        System.out.println("\nminGB bd:");
        for (Entry<C, List<GenPolynomial<C>>> me : bd.entrySet()) {
            System.out.println("\nkey = " + me.getKey() + ":");
            System.out.println("val = " + me.getValue());
        }
        System.out.println();
        return G;
    }
}
