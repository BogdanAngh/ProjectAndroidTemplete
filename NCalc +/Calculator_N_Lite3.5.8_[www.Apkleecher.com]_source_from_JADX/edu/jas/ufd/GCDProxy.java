package edu.jas.ufd;

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

public class GCDProxy<C extends GcdRingElem<C>> extends GreatestCommonDivisorAbstract<C> {
    private static final Logger logger;
    private final boolean debug;
    public final GreatestCommonDivisorAbstract<C> e1;
    public final GreatestCommonDivisorAbstract<C> e2;
    protected transient ExecutorService pool;

    class 10 implements Callable<GenPolynomial<GenPolynomial<C>>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        10(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<GenPolynomial<C>> call() {
            try {
                GenPolynomial<GenPolynomial<C>> g = GCDProxy.this.e2.recursiveUnivariateResultant(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e2 " + GCDProxy.this.e2.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e2 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e2 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e2 " + e2);
            }
        }
    }

    class 11 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        11(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e1.resultant(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e1 " + GCDProxy.this.e1.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e1 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e1 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e1 " + e2);
            }
        }
    }

    class 12 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        12(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e2.resultant(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e2 " + GCDProxy.this.e2.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e2 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e2 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e2 " + e2);
            }
        }
    }

    class 1 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        1(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e1.baseGcd(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e1 " + GCDProxy.this.e1.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e1 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e1 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e1 " + e2);
            }
        }
    }

    class 2 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        2(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e2.baseGcd(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e2 " + GCDProxy.this.e2.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e2 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e2 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e2 " + e2);
            }
        }
    }

    class 3 implements Callable<GenPolynomial<GenPolynomial<C>>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        3(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<GenPolynomial<C>> call() {
            try {
                GenPolynomial<GenPolynomial<C>> g = GCDProxy.this.e1.recursiveUnivariateGcd(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e1 " + GCDProxy.this.e1.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e1 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e1 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e1 " + e2);
            }
        }
    }

    class 4 implements Callable<GenPolynomial<GenPolynomial<C>>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        4(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<GenPolynomial<C>> call() {
            try {
                GenPolynomial<GenPolynomial<C>> g = GCDProxy.this.e2.recursiveUnivariateGcd(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e2 " + GCDProxy.this.e2.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e2 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e2 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e2 " + e2);
            }
        }
    }

    class 5 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        5(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e1.gcd(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e1 " + GCDProxy.this.e1.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e1 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e1 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e1 " + e2);
            }
        }
    }

    class 6 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        6(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e2.gcd(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e2 " + GCDProxy.this.e2.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e2 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e2 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e2 " + e2);
            }
        }
    }

    class 7 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        7(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e1.baseResultant(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e1 " + GCDProxy.this.e1.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e1 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e1 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e1 " + e2);
            }
        }
    }

    class 8 implements Callable<GenPolynomial<C>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        8(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<C> call() {
            try {
                GenPolynomial<C> g = GCDProxy.this.e2.baseResultant(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e2 " + GCDProxy.this.e2.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e2 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e2 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e2 " + e2);
            }
        }
    }

    class 9 implements Callable<GenPolynomial<GenPolynomial<C>>> {
        final /* synthetic */ GenPolynomial val$P;
        final /* synthetic */ GenPolynomial val$S;

        9(GenPolynomial genPolynomial, GenPolynomial genPolynomial2) {
            this.val$P = genPolynomial;
            this.val$S = genPolynomial2;
        }

        public GenPolynomial<GenPolynomial<C>> call() {
            try {
                GenPolynomial<GenPolynomial<C>> g = GCDProxy.this.e1.recursiveUnivariateResultant(this.val$P, this.val$S);
                if (GCDProxy.this.debug) {
                    GCDProxy.logger.info("GCDProxy done e1 " + GCDProxy.this.e1.getClass().getName());
                }
                return g;
            } catch (PreemptingException e) {
                throw new RuntimeException("GCDProxy e1 pre " + e);
            } catch (Exception e2) {
                GCDProxy.logger.info("GCDProxy e1 " + e2);
                GCDProxy.logger.info("GCDProxy P = " + this.val$P);
                GCDProxy.logger.info("GCDProxy S = " + this.val$S);
                throw new RuntimeException("GCDProxy e1 " + e2);
            }
        }
    }

    static {
        logger = Logger.getLogger(GCDProxy.class);
    }

    public GCDProxy(GreatestCommonDivisorAbstract<C> e1, GreatestCommonDivisorAbstract<C> e2) {
        this.debug = logger.isDebugEnabled();
        this.e1 = e1;
        this.e2 = e2;
        this.pool = ComputerThreads.getPool();
    }

    public String toString() {
        return "GCDProxy[ " + this.e1.getClass().getName() + ", " + this.e2.getClass().getName() + " ]";
    }

    public GenPolynomial<C> baseGcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (this.debug && ComputerThreads.NO_THREADS) {
            throw new RuntimeException("this should not happen");
        } else if (S == null || S.isZERO()) {
            return P;
        } else {
            if (P == null || P.isZERO()) {
                return S;
            }
            GenPolynomial<C> g = null;
            List<Callable<GenPolynomial<C>>> cs = new ArrayList(2);
            cs.add(new 1(P, S));
            cs.add(new 2(P, S));
            try {
                return (GenPolynomial) this.pool.invokeAny(cs);
            } catch (InterruptedException ignored) {
                logger.info("InterruptedException " + ignored);
                Thread.currentThread().interrupt();
                return g;
            } catch (ExecutionException e) {
                logger.info("ExecutionException " + e);
                Thread.currentThread().interrupt();
                return g;
            }
        }
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateGcd(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (this.debug && ComputerThreads.NO_THREADS) {
            throw new RuntimeException("this should not happen");
        } else if (S == null || S.isZERO()) {
            return P;
        } else {
            if (P == null || P.isZERO()) {
                return S;
            }
            GenPolynomial<GenPolynomial<C>> g = null;
            List<Callable<GenPolynomial<GenPolynomial<C>>>> cs = new ArrayList(2);
            cs.add(new 3(P, S));
            cs.add(new 4(P, S));
            try {
                return (GenPolynomial) this.pool.invokeAny(cs);
            } catch (InterruptedException ignored) {
                logger.info("InterruptedException " + ignored);
                Thread.currentThread().interrupt();
                return g;
            } catch (ExecutionException e) {
                logger.info("ExecutionException " + e);
                Thread.currentThread().interrupt();
                return g;
            }
        }
    }

    public GenPolynomial<C> gcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (this.debug && ComputerThreads.NO_THREADS) {
            throw new RuntimeException("this should not happen");
        } else if (S == null || S.isZERO()) {
            return P;
        } else {
            if (P == null || P.isZERO()) {
                return S;
            }
            GenPolynomial<C> g = null;
            List<Callable<GenPolynomial<C>>> cs = new ArrayList(2);
            cs.add(new 5(P, S));
            cs.add(new 6(P, S));
            try {
                return (GenPolynomial) this.pool.invokeAny(cs);
            } catch (InterruptedException ignored) {
                logger.info("InterruptedException " + ignored);
                Thread.currentThread().interrupt();
                return g;
            } catch (ExecutionException e) {
                logger.info("ExecutionException " + e);
                Thread.currentThread().interrupt();
                return g;
            }
        }
    }

    public GenPolynomial<C> baseResultant(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (this.debug && ComputerThreads.NO_THREADS) {
            throw new RuntimeException("this should not happen");
        } else if (S == null || S.isZERO()) {
            return S;
        } else {
            if (P == null || P.isZERO()) {
                return P;
            }
            GenPolynomial<C> g = null;
            List<Callable<GenPolynomial<C>>> cs = new ArrayList(2);
            cs.add(new 7(P, S));
            cs.add(new 8(P, S));
            try {
                return (GenPolynomial) this.pool.invokeAny(cs);
            } catch (InterruptedException ignored) {
                logger.info("InterruptedException " + ignored);
                Thread.currentThread().interrupt();
                return g;
            } catch (ExecutionException e) {
                logger.info("ExecutionException " + e);
                Thread.currentThread().interrupt();
                return g;
            }
        }
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateResultant(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (this.debug && ComputerThreads.NO_THREADS) {
            throw new RuntimeException("this should not happen");
        } else if (S == null || S.isZERO()) {
            return S;
        } else {
            if (P == null || P.isZERO()) {
                return P;
            }
            GenPolynomial<GenPolynomial<C>> g = null;
            List<Callable<GenPolynomial<GenPolynomial<C>>>> cs = new ArrayList(2);
            cs.add(new 9(P, S));
            cs.add(new 10(P, S));
            try {
                return (GenPolynomial) this.pool.invokeAny(cs);
            } catch (InterruptedException ignored) {
                logger.info("InterruptedException " + ignored);
                Thread.currentThread().interrupt();
                return g;
            } catch (ExecutionException e) {
                logger.info("ExecutionException " + e);
                Thread.currentThread().interrupt();
                return g;
            }
        }
    }

    public GenPolynomial<C> resultant(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (this.debug && ComputerThreads.NO_THREADS) {
            throw new RuntimeException("this should not happen");
        } else if (S == null || S.isZERO()) {
            return S;
        } else {
            if (P == null || P.isZERO()) {
                return P;
            }
            GenPolynomial<C> g = null;
            List<Callable<GenPolynomial<C>>> cs = new ArrayList(2);
            cs.add(new 11(P, S));
            cs.add(new 12(P, S));
            try {
                return (GenPolynomial) this.pool.invokeAny(cs);
            } catch (InterruptedException ignored) {
                logger.info("InterruptedException " + ignored);
                Thread.currentThread().interrupt();
                return g;
            } catch (ExecutionException e) {
                logger.info("ExecutionException " + e);
                Thread.currentThread().interrupt();
                return g;
            }
        }
    }
}
