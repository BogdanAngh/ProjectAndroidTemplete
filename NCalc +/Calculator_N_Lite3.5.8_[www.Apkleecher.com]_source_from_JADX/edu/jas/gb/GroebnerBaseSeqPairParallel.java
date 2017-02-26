package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import edu.jas.util.ThreadPool;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;

public class GroebnerBaseSeqPairParallel<C extends RingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    protected final transient ThreadPool pool;
    protected final int threads;

    static {
        logger = Logger.getLogger(GroebnerBaseSeqPairParallel.class);
    }

    public GroebnerBaseSeqPairParallel() {
        this(2);
    }

    public GroebnerBaseSeqPairParallel(int threads) {
        this(threads, new ThreadPool(threads));
    }

    public GroebnerBaseSeqPairParallel(int threads, ThreadPool pool) {
        this(threads, pool, new ReductionPar());
    }

    public GroebnerBaseSeqPairParallel(int threads, Reduction<C> red) {
        this(threads, new ThreadPool(threads), red);
    }

    public GroebnerBaseSeqPairParallel(int threads, ThreadPool pool, Reduction<C> red) {
        super((Reduction) red);
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
        List<GenPolynomial<C>> G = new ArrayList();
        CriticalPairList<C> pairlist = null;
        int l = F.size();
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
                    if (!p.ring.coFac.isField()) {
                        throw new IllegalArgumentException("coefficients not from a field");
                    }
                }
                pairlist.put(p);
            } else {
                l--;
            }
        }
        if (l <= 1) {
            return G;
        }
        Terminator fin = new Terminator(this.threads);
        for (int i = 0; i < this.threads; i++) {
            this.pool.addJob(new ReducerSeqPair(fin, G, pairlist));
        }
        fin.waitDone();
        if (Thread.currentThread().isInterrupted()) {
            throw new RuntimeException("interrupt before minimalGB");
        }
        logger.debug("#parallel list = " + G.size());
        G = minimalGB(G);
        logger.info(BuildConfig.FLAVOR + pairlist);
        return G;
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
        MiReducerSeqPair[] mirs = (MiReducerSeqPair[]) new MiReducerSeqPair[G.size()];
        int i = 0;
        F = new ArrayList(G.size());
        while (G.size() > 0) {
            a = (GenPolynomial) G.remove(0);
            List<GenPolynomial<C>> R = new ArrayList(G.size() + F.size());
            R.addAll(G);
            R.addAll(F);
            mirs[i] = new MiReducerSeqPair(R, a);
            this.pool.addJob(mirs[i]);
            i++;
            F.add(a);
        }
        List<GenPolynomial<C>> F2 = new ArrayList(F.size());
        for (MiReducerSeqPair nf : mirs) {
            F2.add(nf.getNF());
        }
        return F2;
    }
}
