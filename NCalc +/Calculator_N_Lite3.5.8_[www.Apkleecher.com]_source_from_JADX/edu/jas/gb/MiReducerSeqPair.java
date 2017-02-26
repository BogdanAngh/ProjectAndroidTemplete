package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.apache.log4j.Logger;

/* compiled from: GroebnerBaseSeqPairParallel */
class MiReducerSeqPair<C extends RingElem<C>> implements Runnable {
    private static final Logger logger;
    private final List<GenPolynomial<C>> G;
    private GenPolynomial<C> H;
    private final Semaphore done;
    private final ReductionPar<C> red;

    static {
        logger = Logger.getLogger(MiReducerSeqPair.class);
    }

    MiReducerSeqPair(List<GenPolynomial<C>> G, GenPolynomial<C> p) {
        this.done = new Semaphore(0);
        this.G = G;
        this.H = p;
        this.red = new ReductionPar();
    }

    public String toString() {
        return "MiReducerSeqpair";
    }

    public GenPolynomial<C> getNF() {
        try {
            this.done.acquire();
            return this.H;
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupt in getNF");
        }
    }

    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("ht(H) = " + this.H.leadingExpVector());
        }
        try {
            this.H = this.red.normalform(this.G, this.H);
            this.done.release();
        } catch (RuntimeException e) {
            Thread.currentThread().interrupt();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("ht(H) = " + this.H.leadingExpVector());
        }
    }
}
