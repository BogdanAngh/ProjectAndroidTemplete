package edu.jas.gbufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.arith.Product;
import edu.jas.arith.ProductRing;
import edu.jas.gb.DGroebnerBaseSeq;
import edu.jas.gb.EGroebnerBaseSeq;
import edu.jas.gb.GBProxy;
import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.GroebnerBaseParallel;
import edu.jas.gb.GroebnerBaseSeq;
import edu.jas.gb.OrderedMinPairlist;
import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.OrderedSyzPairlist;
import edu.jas.gb.PairList;
import edu.jas.gb.ReductionSeq;
import edu.jas.kern.ComputerThreads;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.Quotient;
import edu.jas.ufd.QuotientRing;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;
import org.matheclipse.core.interfaces.IExpr;

public class GBFactory {
    private static final Logger logger;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$gbufd$GBFactory$Algo;

        static {
            $SwitchMap$edu$jas$gbufd$GBFactory$Algo = new int[Algo.values().length];
            try {
                $SwitchMap$edu$jas$gbufd$GBFactory$Algo[Algo.igb.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$gbufd$GBFactory$Algo[Algo.egb.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$edu$jas$gbufd$GBFactory$Algo[Algo.dgb.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$edu$jas$gbufd$GBFactory$Algo[Algo.qgb.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$edu$jas$gbufd$GBFactory$Algo[Algo.ffgb.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public enum Algo {
        igb,
        egb,
        dgb,
        qgb,
        ffgb
    }

    static {
        logger = Logger.getLogger(GBFactory.class);
    }

    protected GBFactory() {
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<C> getImplementation() {
        logger.warn("no coefficent factory given, assuming field coeffcients");
        return new GroebnerBaseSeq();
    }

    public static GroebnerBaseAbstract<ModLong> getImplementation(ModLongRing fac) {
        return getImplementation(fac, new OrderedPairlist());
    }

    public static GroebnerBaseAbstract<ModLong> getImplementation(ModLongRing fac, PairList<ModLong> pl) {
        if (fac.isField()) {
            return new GroebnerBaseSeq((PairList) pl);
        }
        return new GroebnerBasePseudoSeq(fac, pl);
    }

    public static GroebnerBaseAbstract<ModInteger> getImplementation(ModIntegerRing fac) {
        return getImplementation(fac, new OrderedPairlist());
    }

    public static GroebnerBaseAbstract<ModInteger> getImplementation(ModIntegerRing fac, PairList<ModInteger> pl) {
        if (fac.isField()) {
            return new GroebnerBaseSeq((PairList) pl);
        }
        return new GroebnerBasePseudoSeq(fac, pl);
    }

    public static GroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac) {
        return getImplementation(fac, Algo.igb);
    }

    public static GroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac, Algo a) {
        return getImplementation(fac, a, new OrderedPairlist());
    }

    public static GroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac, PairList<BigInteger> pl) {
        return getImplementation(fac, Algo.igb, (PairList) pl);
    }

    public static GroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac, Algo a, PairList<BigInteger> pl) {
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new GroebnerBasePseudoSeq(fac, pl);
            case IExpr.DOUBLEID /*2*/:
                return new EGroebnerBaseSeq();
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return new DGroebnerBaseSeq();
            default:
                throw new IllegalArgumentException("algorithm not available for BigInteger " + a);
        }
    }

    public static GroebnerBaseAbstract<BigRational> getImplementation(BigRational fac) {
        return getImplementation(fac, Algo.qgb);
    }

    public static GroebnerBaseAbstract<BigRational> getImplementation(BigRational fac, Algo a) {
        return getImplementation(fac, a, new OrderedPairlist());
    }

    public static GroebnerBaseAbstract<BigRational> getImplementation(BigRational fac, PairList<BigRational> pl) {
        return getImplementation(fac, Algo.qgb, (PairList) pl);
    }

    public static GroebnerBaseAbstract<BigRational> getImplementation(BigRational fac, Algo a, PairList<BigRational> pl) {
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new GroebnerBaseSeq((PairList) pl);
            case ValueServer.CONSTANT_MODE /*5*/:
                PairList pli;
                if (pl instanceof OrderedMinPairlist) {
                    pli = new OrderedMinPairlist();
                } else if (pl instanceof OrderedSyzPairlist) {
                    pli = new OrderedSyzPairlist();
                } else {
                    pli = new OrderedPairlist();
                }
                return new GroebnerBaseRational(pli);
            default:
                throw new IllegalArgumentException("algorithm not available for " + fac.toScriptFactory() + ", Algo = " + a);
        }
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac) {
        return getImplementation((QuotientRing) fac, Algo.qgb);
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac, Algo a) {
        return getImplementation((QuotientRing) fac, a, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac, PairList<Quotient<C>> pl) {
        return getImplementation((QuotientRing) fac, Algo.qgb, (PairList) pl);
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac, Algo a, PairList<Quotient<C>> pl) {
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new GroebnerBaseSeq(new ReductionSeq(), pl);
            case ValueServer.CONSTANT_MODE /*5*/:
                PairList pli;
                if (pl instanceof OrderedMinPairlist) {
                    pli = new OrderedMinPairlist();
                } else if (pl instanceof OrderedSyzPairlist) {
                    pli = new OrderedSyzPairlist();
                } else {
                    pli = new OrderedPairlist();
                }
                return new GroebnerBaseQuotient((QuotientRing) fac, pli);
            default:
                throw new IllegalArgumentException("algorithm not available for Quotient " + a);
        }
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac) {
        return getImplementation((GenPolynomialRing) fac, Algo.igb);
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac, Algo a) {
        return getImplementation((GenPolynomialRing) fac, a, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac, PairList<GenPolynomial<C>> pl) {
        return getImplementation((GenPolynomialRing) fac, Algo.igb, (PairList) pl);
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac, Algo a, PairList<GenPolynomial<C>> pl) {
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new GroebnerBasePseudoRecSeq(fac, pl);
            case IExpr.DOUBLEID /*2*/:
                if (fac.nvar <= 1 && fac.coFac.isField()) {
                    return new EGroebnerBaseSeq();
                }
                throw new IllegalArgumentException("coefficients not univariate or not over a field" + fac);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                if (fac.nvar <= 1 && fac.coFac.isField()) {
                    return new DGroebnerBaseSeq();
                }
                throw new IllegalArgumentException("coefficients not univariate or not over a field" + fac);
            default:
                throw new IllegalArgumentException("algorithm not available for GenPolynomial<C> " + a);
        }
    }

    public static <C extends RingElem<C>> GroebnerBaseAbstract<Product<C>> getImplementation(ProductRing<C> fac) {
        if (fac.onlyFields()) {
            return new RGroebnerBaseSeq();
        }
        return new RGroebnerBasePseudoSeq(fac);
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<C> getImplementation(RingFactory<C> fac) {
        return getImplementation((RingFactory) fac, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<C> getImplementation(RingFactory<C> fac, PairList<C> pl) {
        logger.debug("fac = " + fac.getClass().getName());
        if (fac.isField()) {
            return new GroebnerBaseSeq((PairList) pl);
        }
        GroebnerBaseAbstract<C> bba;
        RingFactory<C> ofac = fac;
        if (ofac instanceof GenPolynomialRing) {
            PairList<GenPolynomial<C>> pli;
            if (pl instanceof OrderedMinPairlist) {
                pli = new OrderedMinPairlist();
            } else if (pl instanceof OrderedSyzPairlist) {
                pli = new OrderedSyzPairlist();
            } else {
                pli = new OrderedPairlist();
            }
            bba = new GroebnerBasePseudoRecSeq((GenPolynomialRing) ofac, pli);
        } else if (ofac instanceof ProductRing) {
            ProductRing pfac = (ProductRing) ofac;
            if (pfac.onlyFields()) {
                bba = new RGroebnerBaseSeq();
            } else {
                bba = new RGroebnerBasePseudoSeq(pfac);
            }
        } else {
            bba = new GroebnerBasePseudoSeq(fac, pl);
        }
        logger.debug("bba = " + bba.getClass().getName());
        return bba;
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<C> getProxy(RingFactory<C> fac) {
        return getProxy(fac, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<C> getProxy(RingFactory<C> fac, PairList<C> pl) {
        int th = 2;
        if (ComputerThreads.NO_THREADS) {
            return getImplementation((RingFactory) fac, (PairList) pl);
        }
        logger.debug("fac = " + fac.getClass().getName());
        if (ComputerThreads.N_CPUS > 2) {
            th = ComputerThreads.N_CPUS - 1;
        }
        if (fac.isField()) {
            return new GBProxy(new GroebnerBaseSeq((PairList) pl), new GroebnerBaseParallel(th, (PairList) pl));
        }
        if (fac.characteristic().signum() != 0) {
            return getImplementation((RingFactory) fac, (PairList) pl);
        }
        if (!(fac instanceof GenPolynomialRing)) {
            return new GBProxy(new GroebnerBasePseudoSeq(fac, pl), new GroebnerBasePseudoParallel(th, (RingFactory) fac, (PairList) pl));
        }
        RingFactory pfac = (GenPolynomialRing) fac;
        PairList ppl = new OrderedPairlist();
        return new GBProxy(new GroebnerBasePseudoRecSeq(pfac, ppl), new GroebnerBasePseudoRecParallel(th, pfac, ppl));
    }

    public static <C extends GcdRingElem<C>> GroebnerBaseAbstract<GenPolynomial<C>> getProxy(GenPolynomialRing<C> fac) {
        int th = 2;
        if (ComputerThreads.NO_THREADS) {
            return getImplementation((GenPolynomialRing) fac);
        }
        logger.debug("fac = " + fac.getClass().getName());
        if (ComputerThreads.N_CPUS > 2) {
            th = ComputerThreads.N_CPUS - 1;
        }
        PairList ppl = new OrderedPairlist();
        return new GBProxy(new GroebnerBasePseudoRecSeq(fac, ppl), new GroebnerBasePseudoRecParallel(th, (RingFactory) fac, ppl));
    }
}
