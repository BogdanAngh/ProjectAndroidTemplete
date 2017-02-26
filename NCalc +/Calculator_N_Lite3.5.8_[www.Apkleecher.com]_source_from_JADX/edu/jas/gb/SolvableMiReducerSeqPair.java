package edu.jas.gb;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.apache.log4j.Logger;

/* compiled from: SolvableGroebnerBaseSeqPairParallel */
class SolvableMiReducerSeqPair<C extends RingElem<C>> implements Runnable {
    private static final boolean debug;
    private static final Logger logger;
    private final List<GenSolvablePolynomial<C>> G;
    private GenSolvablePolynomial<C> H;
    private final Semaphore done;
    private final SolvableReductionPar<C> sred;

    static {
        logger = Logger.getLogger(SolvableMiReducerSeqPair.class);
        debug = logger.isDebugEnabled();
    }

    SolvableMiReducerSeqPair(List<GenSolvablePolynomial<C>> G, GenSolvablePolynomial<C> p) {
        this.done = new Semaphore(0);
        this.G = G;
        this.H = p;
        this.sred = new SolvableReductionPar();
    }

    public GenSolvablePolynomial<C> getNF() {
        try {
            this.done.acquire();
        } catch (InterruptedException e) {
        }
        return this.H;
    }

    public void run() {
        if (debug) {
            logger.debug("ht(H) = " + this.H.leadingExpVector());
        }
        this.H = this.sred.leftNormalform(this.G, this.H);
        this.done.release();
        if (debug) {
            logger.debug("ht(H) = " + this.H.leadingExpVector());
        }
    }
}
