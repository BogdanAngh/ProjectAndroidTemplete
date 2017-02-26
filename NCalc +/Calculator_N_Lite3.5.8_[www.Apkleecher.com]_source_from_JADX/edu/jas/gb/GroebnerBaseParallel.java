package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import edu.jas.util.ThreadPool;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;

public class GroebnerBaseParallel<C extends RingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    protected final transient ThreadPool pool;
    protected final int threads;

    static {
        logger = Logger.getLogger(GroebnerBaseParallel.class);
    }

    public GroebnerBaseParallel() {
        this(2);
    }

    public GroebnerBaseParallel(int threads) {
        this(threads, new ThreadPool(threads));
    }

    public GroebnerBaseParallel(int threads, Reduction<C> red) {
        this(threads, new ThreadPool(threads), (Reduction) red);
    }

    public GroebnerBaseParallel(int threads, PairList<C> pl) {
        this(threads, new ThreadPool(threads), new ReductionPar(), pl);
    }

    public GroebnerBaseParallel(int threads, ThreadPool pool) {
        this(threads, pool, new ReductionPar());
    }

    public GroebnerBaseParallel(int threads, ThreadPool pool, Reduction<C> red) {
        this(threads, pool, red, new OrderedPairlist());
    }

    public GroebnerBaseParallel(int threads, Reduction<C> red, PairList<C> pl) {
        this(threads, new ThreadPool(threads), red, pl);
    }

    public GroebnerBaseParallel(int threads, ThreadPool pool, Reduction<C> red, PairList<C> pl) {
        super(red, pl);
        if (!(red instanceof ReductionPar)) {
            logger.warn("parallel GB should use parallel aware reduction");
        }
        if (threads < 1) {
            threads = 1;
        }
        this.threads = threads;
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
        List G = PolyUtil.monic(normalizeZerosOnes(F));
        if (G.size() <= 1) {
            return G;
        }
        GenPolynomialRing<C> ring = ((GenPolynomial) G.get(0)).ring;
        if (ring.coFac.isField()) {
            PairList<C> pairlist = this.strategy.create(modv, ring);
            pairlist.put(G);
            logger.info("start " + pairlist);
            Terminator fin = new Terminator(this.threads);
            for (int i = 0; i < this.threads; i++) {
                this.pool.addJob(new Reducer(fin, G, pairlist));
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
        throw new IllegalArgumentException("coefficients not from a field");
    }

    public List<GenPolynomial<C>> minimalGB(List<GenPolynomial<C>> Fp) {
        ArrayList<GenPolynomial<C>> G = new ArrayList(Fp.size());
        ListIterator<GenPolynomial<C>> it = Fp.listIterator();
        while (it.hasNext()) {
            GenPolynomial<C> a = (GenPolynomial) it.next();
            if (a.length() != 0) {
                G.add(a);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        ArrayList<GenPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            a = (GenPolynomial) G.remove(0);
            ExpVector e = a.leadingExpVector();
            it = G.listIterator();
            boolean mt = false;
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenPolynomial) it.next()).leadingExpVector());
            }
            it = F.listIterator();
            while (it.hasNext() && !mt) {
                mt = e.multipleOf(((GenPolynomial) it.next()).leadingExpVector());
            }
            if (!mt) {
                F.add(a);
            }
        }
        G = F;
        if (G.size() <= 1) {
            return G;
        }
        Collections.reverse(G);
        MiReducer[] mirs = (MiReducer[]) new MiReducer[G.size()];
        int i = 0;
        F = new ArrayList(G.size());
        while (G.size() > 0) {
            a = (GenPolynomial) G.remove(0);
            List<GenPolynomial<C>> R = new ArrayList(G.size() + F.size());
            R.addAll(G);
            R.addAll(F);
            mirs[i] = new MiReducer(R, a);
            this.pool.addJob(mirs[i]);
            i++;
            F.add(a);
        }
        List<GenPolynomial<C>> F2 = new ArrayList(F.size());
        for (MiReducer nf : mirs) {
            F2.add(nf.getNF());
        }
        return F2;
    }
}
