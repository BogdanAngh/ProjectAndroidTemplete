package edu.jas.gb;

import edu.jas.kern.ComputerThreads;
import edu.jas.kern.PreemptingException;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.GcdRingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import org.apache.log4j.Logger;

public class SGBProxy<C extends GcdRingElem<C>> extends SolvableGroebnerBaseAbstract<C> {
    private static final Logger logger;
    private final boolean debug;
    public final SolvableGroebnerBaseAbstract<C> e1;
    public final SolvableGroebnerBaseAbstract<C> e2;
    protected transient ExecutorService pool;

    class 1 implements Callable<List<GenSolvablePolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        1(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenSolvablePolynomial<C>> call() {
            try {
                List<GenSolvablePolynomial<C>> G = SGBProxy.this.e1.leftGB(this.val$modv, this.val$F);
                if (SGBProxy.this.debug) {
                    SGBProxy.logger.info("SGBProxy done e1 " + SGBProxy.this.e1.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("SGBProxy e1 preempted " + e);
            } catch (Exception e2) {
                SGBProxy.logger.info("SGBProxy e1 " + e2);
                SGBProxy.logger.info("Exception SGBProxy F = " + this.val$F);
                throw new RuntimeException("SGBProxy e1 " + e2);
            }
        }
    }

    class 2 implements Callable<List<GenSolvablePolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        2(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenSolvablePolynomial<C>> call() {
            try {
                List<GenSolvablePolynomial<C>> G = SGBProxy.this.e2.leftGB(this.val$modv, this.val$F);
                if (SGBProxy.this.debug) {
                    SGBProxy.logger.info("SGBProxy done e2 " + SGBProxy.this.e2.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("SGBProxy e2 preempted " + e);
            } catch (Exception e2) {
                SGBProxy.logger.info("SGBProxy e2 " + e2);
                SGBProxy.logger.info("Exception SGBProxy F = " + this.val$F);
                throw new RuntimeException("SGBProxy e2 " + e2);
            }
        }
    }

    class 3 implements Callable<List<GenSolvablePolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        3(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenSolvablePolynomial<C>> call() {
            try {
                List<GenSolvablePolynomial<C>> G = SGBProxy.this.e1.rightGB(this.val$modv, this.val$F);
                if (SGBProxy.this.debug) {
                    SGBProxy.logger.info("SGBProxy done e1 " + SGBProxy.this.e1.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("SGBProxy e1 preempted " + e);
            } catch (Exception e2) {
                SGBProxy.logger.info("SGBProxy e1 " + e2);
                SGBProxy.logger.info("Exception SGBProxy F = " + this.val$F);
                throw new RuntimeException("SGBProxy e1 " + e2);
            }
        }
    }

    class 4 implements Callable<List<GenSolvablePolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        4(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenSolvablePolynomial<C>> call() {
            try {
                List<GenSolvablePolynomial<C>> G = SGBProxy.this.e2.rightGB(this.val$modv, this.val$F);
                if (SGBProxy.this.debug) {
                    SGBProxy.logger.info("SGBProxy done e2 " + SGBProxy.this.e2.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("SGBProxy e2 preempted " + e);
            } catch (Exception e2) {
                SGBProxy.logger.info("SGBProxy e2 " + e2);
                SGBProxy.logger.info("Exception SGBProxy F = " + this.val$F);
                throw new RuntimeException("SGBProxy e2 " + e2);
            }
        }
    }

    class 5 implements Callable<List<GenSolvablePolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        5(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenSolvablePolynomial<C>> call() {
            try {
                List<GenSolvablePolynomial<C>> G = SGBProxy.this.e1.twosidedGB(this.val$modv, this.val$F);
                if (SGBProxy.this.debug) {
                    SGBProxy.logger.info("SGBProxy done e1 " + SGBProxy.this.e1.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("SGBProxy e1 preempted " + e);
            } catch (Exception e2) {
                SGBProxy.logger.info("SGBProxy e1 " + e2);
                SGBProxy.logger.info("Exception SGBProxy F = " + this.val$F);
                throw new RuntimeException("SGBProxy e1 " + e2);
            }
        }
    }

    class 6 implements Callable<List<GenSolvablePolynomial<C>>> {
        final /* synthetic */ List val$F;
        final /* synthetic */ int val$modv;

        6(int i, List list) {
            this.val$modv = i;
            this.val$F = list;
        }

        public List<GenSolvablePolynomial<C>> call() {
            try {
                List<GenSolvablePolynomial<C>> G = SGBProxy.this.e2.twosidedGB(this.val$modv, this.val$F);
                if (SGBProxy.this.debug) {
                    SGBProxy.logger.info("SGBProxy done e2 " + SGBProxy.this.e2.getClass().getName());
                }
                return G;
            } catch (PreemptingException e) {
                throw new RuntimeException("SGBProxy e2 preempted " + e);
            } catch (Exception e2) {
                SGBProxy.logger.info("SGBProxy e2 " + e2);
                SGBProxy.logger.info("Exception SGBProxy F = " + this.val$F);
                throw new RuntimeException("SGBProxy e2 " + e2);
            }
        }
    }

    static {
        logger = Logger.getLogger(SGBProxy.class);
    }

    public SGBProxy(SolvableGroebnerBaseAbstract<C> e1, SolvableGroebnerBaseAbstract<C> e2) {
        this.debug = logger.isDebugEnabled();
        this.e1 = e1;
        this.e2 = e2;
        this.pool = ComputerThreads.getPool();
    }

    public String toString() {
        return "SGBProxy[ " + this.e1.toString() + ", " + this.e2.toString() + " ]";
    }

    public void terminate() {
        this.e1.terminate();
        this.e2.terminate();
    }

    public int cancel() {
        return this.e1.cancel() + this.e2.cancel();
    }

    public List<GenSolvablePolynomial<C>> leftGB(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        List<GenSolvablePolynomial<C>> G = null;
        List<Callable<List<GenSolvablePolynomial<C>>>> cs = new ArrayList(2);
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

    public List<GenSolvablePolynomial<C>> rightGB(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        List<GenSolvablePolynomial<C>> G = null;
        List<Callable<List<GenSolvablePolynomial<C>>>> cs = new ArrayList(2);
        cs.add(new 3(modv, F));
        cs.add(new 4(modv, F));
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

    public List<GenSolvablePolynomial<C>> twosidedGB(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        List<GenSolvablePolynomial<C>> G = null;
        List<Callable<List<GenSolvablePolynomial<C>>>> cs = new ArrayList(2);
        cs.add(new 5(modv, F));
        cs.add(new 6(modv, F));
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
