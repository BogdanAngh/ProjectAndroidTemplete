package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.apache.log4j.Logger;

/* compiled from: GroebnerBasePseudoRecParallel */
class PseudoMiReducerRec<C extends GcdRingElem<C>> implements Runnable {
    private static final Logger logger;
    private final List<GenPolynomial<GenPolynomial<C>>> G;
    private GenPolynomial<GenPolynomial<C>> H;
    private final Semaphore done;
    private final GreatestCommonDivisorAbstract<C> engine;
    private final PseudoReductionPar<C> redRec;

    static {
        logger = Logger.getLogger(PseudoMiReducerRec.class);
    }

    PseudoMiReducerRec(List<GenPolynomial<GenPolynomial<C>>> G, GenPolynomial<GenPolynomial<C>> p, GreatestCommonDivisorAbstract<C> engine) {
        this.done = new Semaphore(0);
        this.G = G;
        this.engine = engine;
        this.H = p;
        this.redRec = new PseudoReductionPar();
    }

    public String toString() {
        return "PseudoMiReducerRec";
    }

    public GenPolynomial<GenPolynomial<C>> getNF() {
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
            this.H = this.redRec.normalformRecursive(this.G, this.H);
            this.H = this.engine.recursivePrimitivePart(this.H);
            this.H = this.H.abs();
            this.done.release();
        } catch (RuntimeException e) {
            Thread.currentThread().interrupt();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("ht(H) = " + this.H.leadingExpVector());
        }
    }
}
