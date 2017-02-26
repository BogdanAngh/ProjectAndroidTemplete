package edu.jas.gb;

import edu.jas.kern.ComputerThreads;
import edu.jas.kern.PreemptingException;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import org.apache.log4j.Logger;

public class GBProxy<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    private final boolean debug;
    public final GroebnerBaseAbstract<C> e1;
    public final GroebnerBaseAbstract<C> e2;
    protected transient ExecutorService pool;

    class 1 implements Callable<List<GenPolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        1(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenPolynomial<C>> call() {
            try {
                List<GenPolynomial<C>> G = GBProxy.this.e1.GB(this.val$modv, this.val$F);
                if (GBProxy.this.debug) {
                    GBProxy.logger.info("GBProxy done e1 " + GBProxy.this.e1.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("GBProxy e1 preempted " + e);
            } catch (Exception e2) {
                GBProxy.logger.info("GBProxy e1 " + e2);
                GBProxy.logger.info("Exception GBProxy F = " + this.val$F);
                throw new RuntimeException("GBProxy e1 " + e2);
            }
        }
    }

    class 2 implements Callable<List<GenPolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        2(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenPolynomial<C>> call() {
            try {
                List<GenPolynomial<C>> G = GBProxy.this.e2.GB(this.val$modv, this.val$F);
                if (GBProxy.this.debug) {
                    GBProxy.logger.info("GBProxy done e2 " + GBProxy.this.e2.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("GBProxy e2 preempted " + e);
            } catch (Exception e2) {
                GBProxy.logger.info("GBProxy e2 " + e2);
                GBProxy.logger.info("Exception GBProxy F = " + this.val$F);
                throw new RuntimeException("GBProxy e2 " + e2);
            }
        }
    }

    static {
        logger = Logger.getLogger(GBProxy.class);
    }

    public GBProxy(GroebnerBaseAbstract<C> e1, GroebnerBaseAbstract<C> e2) {
        this.debug = logger.isDebugEnabled();
        this.e1 = e1;
        this.e2 = e2;
        this.pool = ComputerThreads.getPool();
    }

    public String toString() {
        return "GBProxy[ " + this.e1.toString() + ", " + this.e2.toString() + " ]";
    }

    public void terminate() {
        this.e1.terminate();
        this.e2.terminate();
    }

    public int cancel() {
        return this.e1.cancel() + this.e2.cancel();
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        List<GenPolynomial<C>> G = null;
        List<Callable<List<GenPolynomial<C>>>> cs = new ArrayList(2);
        cs.add(new 1(modv, F));
        cs.add(new 2(modv, F));
        try {
            return (List) this.pool.invokeAny(cs);
        } catch (InterruptedException ignored) {
            logger.info("InterruptedException " + ignored);
            Thread.currentThread().interrupt();
            return G;
        } catch (ExecutionException e) {
            logger.info("ExecutionException " + e);
            Thread.currentThread().interrupt();
            return G;
        }
    }
}
