package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.PairList;
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

public class GroebnerBasePseudoParallel<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    protected final RingFactory<C> cofac;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<C> engine;
    protected final transient ThreadPool pool;
    protected final PseudoReduction<C> red;
    protected final int threads;

    static {
        logger = Logger.getLogger(GroebnerBasePseudoParallel.class);
    }

    public GroebnerBasePseudoParallel(int threads, RingFactory<C> rf) {
        this(threads, (RingFactory) rf, new PseudoReductionPar());
    }

    public GroebnerBasePseudoParallel(int threads, RingFactory<C> rf, PseudoReduction<C> red) {
        this(threads, rf, red, new ThreadPool(threads));
    }

    public GroebnerBasePseudoParallel(int threads, RingFactory<C> rf, PseudoReduction<C> red, ThreadPool pool) {
        this(threads, rf, red, pool, new OrderedPairlist());
    }

    public GroebnerBasePseudoParallel(int threads, RingFactory<C> rf, PairList<C> pl) {
        this(threads, rf, new PseudoReductionPar(), new ThreadPool(threads), pl);
    }

    public GroebnerBasePseudoParallel(int threads, RingFactory<C> rf, PseudoReduction<C> red, ThreadPool pool, PairList<C> pl) {
        super(red, pl);
        this.debug = logger.isDebugEnabled();
        if (!(red instanceof PseudoReductionPar)) {
            logger.warn("parallel GB should use parallel aware reduction");
        }
        this.red = red;
        this.cofac = rf;
        if (threads < 1) {
            threads = 1;
        }
        this.threads = threads;
        this.engine = GCDFactory.getImplementation((RingFactory) rf);
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

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        List G = this.engine.basePrimitivePart(normalizeZerosOnes(F));
        if (G.size() <= 1) {
            return G;
        }
        GenPolynomialRing<C> ring = ((GenPolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            throw new IllegalArgumentException("coefficients from a field");
        }
        PairList<C> pairlist = this.strategy.create(modv, ring);
        pairlist.put(G);
        logger.info("start " + pairlist);
        Terminator fin = new Terminator(this.threads);
        for (int i = 0; i < this.threads; i++) {
            this.pool.addJob(new PseudoReducer(fin, G, pairlist, this.engine));
        }
        fin.waitDone();
        if (Thread.currentThread().isInterrupted()) {
            throw new RuntimeException("interrupt before minimalGB");
        }
        logger.debug("#parallel list = " + G.size());
        List<GenPolynomial<C>> G2 = minimalGB(G);
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G2;
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
        PseudoMiReducer[] mirs = (PseudoMiReducer[]) new PseudoMiReducer[G.size()];
        int i = 0;
        F = new ArrayList(G.size());
        while (G.size() > 0) {
            a = (GenPolynomial) G.remove(0);
            List<GenPolynomial<C>> R = new ArrayList(G.size() + F.size());
            R.addAll(G);
            R.addAll(F);
            mirs[i] = new PseudoMiReducer(R, a, this.engine);
            this.pool.addJob(mirs[i]);
            i++;
            F.add(a);
        }
        F = new ArrayList(F.size());
        for (PseudoMiReducer nf : mirs) {
            F.add(nf.getNF());
        }
        return F;
    }
}
