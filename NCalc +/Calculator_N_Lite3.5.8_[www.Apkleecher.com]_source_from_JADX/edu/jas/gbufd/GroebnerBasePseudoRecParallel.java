package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.PairList;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import edu.jas.util.Terminator;
import edu.jas.util.ThreadPool;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class GroebnerBasePseudoRecParallel<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<GenPolynomial<C>> {
    private static final Logger logger;
    protected final RingFactory<C> baseCofac;
    protected final RingFactory<GenPolynomial<C>> cofac;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final transient ThreadPool pool;
    protected final PseudoReduction<GenPolynomial<C>> red;
    protected final PseudoReduction<C> redRec;
    protected final int threads;

    static {
        logger = Logger.getLogger(GroebnerBasePseudoRecParallel.class);
    }

    public GroebnerBasePseudoRecParallel(int threads, RingFactory<GenPolynomial<C>> rf) {
        this(threads, rf, new PseudoReductionPar(), new ThreadPool(threads), new OrderedPairlist(new GenPolynomialRing((RingFactory) rf, 1)));
    }

    public GroebnerBasePseudoRecParallel(int threads, RingFactory<GenPolynomial<C>> rf, PseudoReduction<GenPolynomial<C>> red) {
        this(threads, rf, red, new ThreadPool(threads));
    }

    public GroebnerBasePseudoRecParallel(int threads, RingFactory<GenPolynomial<C>> rf, PseudoReduction<GenPolynomial<C>> red, ThreadPool pool) {
        this(threads, rf, red, pool, new OrderedPairlist(new GenPolynomialRing((RingFactory) rf, 1)));
    }

    public GroebnerBasePseudoRecParallel(int threads, RingFactory<GenPolynomial<C>> rf, PairList<GenPolynomial<C>> pl) {
        this(threads, rf, new PseudoReductionPar(), new ThreadPool(threads), pl);
    }

    public GroebnerBasePseudoRecParallel(int threads, RingFactory<GenPolynomial<C>> rf, PseudoReduction<GenPolynomial<C>> red, ThreadPool pool, PairList<GenPolynomial<C>> pl) {
        super(red, pl);
        this.debug = logger.isDebugEnabled();
        if (!(red instanceof PseudoReductionPar)) {
            logger.warn("parallel GB should use parallel aware reduction");
        }
        this.red = red;
        this.redRec = red;
        this.cofac = rf;
        if (threads < 1) {
            threads = 1;
        }
        this.threads = threads;
        this.baseCofac = this.cofac.coFac;
        this.engine = GCDFactory.getProxy(this.baseCofac);
        this.pool = pool;
    }

    public void terminate() {
        if (this.pool != null) {
            this.pool.terminate();
        }
    }

    public int cancel() {
        if (this.pool == null) {
            return 0;
        }
        return this.pool.cancel();
    }

    public List<GenPolynomial<GenPolynomial<C>>> GB(int modv, List<GenPolynomial<GenPolynomial<C>>> F) {
        List G = this.engine.recursivePrimitivePart(normalizeZerosOnes(F));
        if (G.size() <= 1) {
            return G;
        }
        GenPolynomialRing<GenPolynomial<C>> ring = ((GenPolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        PairList<GenPolynomial<C>> pairlist = this.strategy.create(modv, ring);
        pairlist.put(G);
        logger.info("start " + pairlist);
        Terminator fin = new Terminator(this.threads);
        for (int i = 0; i < this.threads; i++) {
            this.pool.addJob(new PseudoReducerRec(fin, G, pairlist, this.engine));
        }
        fin.waitDone();
        if (Thread.currentThread().isInterrupted()) {
            throw new RuntimeException("interrupt before minimalGB");
        }
        logger.debug("#parallel list = " + G.size());
        List<GenPolynomial<GenPolynomial<C>>> G2 = minimalGB(G);
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G2;
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
        PseudoMiReducerRec[] mirs = (PseudoMiReducerRec[]) new PseudoMiReducerRec[G.size()];
        int i = 0;
        F = new ArrayList(G.size());
        while (G.size() > 0) {
            a = (GenPolynomial) G.remove(0);
            List<GenPolynomial<GenPolynomial<C>>> R = new ArrayList(G.size() + F.size());
            R.addAll(G);
            R.addAll(F);
            mirs[i] = new PseudoMiReducerRec(R, a, this.engine);
            this.pool.addJob(mirs[i]);
            i++;
            F.add(a);
        }
        F = new ArrayList(F.size());
        for (PseudoMiReducerRec nf : mirs) {
            F.add(nf.getNF());
        }
        return F;
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
