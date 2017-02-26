package edu.jas.gbufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.gb.OrderedMinPairlist;
import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.OrderedSyzPairlist;
import edu.jas.gb.PairList;
import edu.jas.gb.SGBProxy;
import edu.jas.gb.SolvableGroebnerBaseAbstract;
import edu.jas.gb.SolvableGroebnerBaseParallel;
import edu.jas.gb.SolvableGroebnerBaseSeq;
import edu.jas.gb.SolvableReductionSeq;
import edu.jas.gbufd.GBFactory.Algo;
import edu.jas.kern.ComputerThreads;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPairFactory;
import edu.jas.structure.RingFactory;
import edu.jas.structure.ValueFactory;
import edu.jas.ufd.Quotient;
import edu.jas.ufd.QuotientRing;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;
import org.matheclipse.core.interfaces.IExpr;

public class SGBFactory {
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

    static {
        logger = Logger.getLogger(SGBFactory.class);
    }

    protected SGBFactory() {
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<C> getImplementation() {
        logger.warn("no coefficent factory given, assuming field coeffcients");
        return new SolvableGroebnerBaseSeq();
    }

    public static SolvableGroebnerBaseAbstract<ModLong> getImplementation(ModLongRing fac) {
        return getImplementation(fac, new OrderedPairlist());
    }

    public static SolvableGroebnerBaseAbstract<ModLong> getImplementation(ModLongRing fac, PairList<ModLong> pl) {
        if (fac.isField()) {
            return new SolvableGroebnerBaseSeq((PairList) pl);
        }
        return new SolvableGroebnerBasePseudoSeq(fac, pl);
    }

    public static SolvableGroebnerBaseAbstract<ModInteger> getImplementation(ModIntegerRing fac) {
        return getImplementation(fac, new OrderedPairlist());
    }

    public static SolvableGroebnerBaseAbstract<ModInteger> getImplementation(ModIntegerRing fac, PairList<ModInteger> pl) {
        if (fac.isField()) {
            return new SolvableGroebnerBaseSeq((PairList) pl);
        }
        return new SolvableGroebnerBasePseudoSeq(fac, pl);
    }

    public static SolvableGroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac) {
        return getImplementation(fac, Algo.igb);
    }

    public static SolvableGroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac, Algo a) {
        return getImplementation(fac, a, new OrderedPairlist());
    }

    public static SolvableGroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac, PairList<BigInteger> pl) {
        return getImplementation(fac, Algo.igb, (PairList) pl);
    }

    public static SolvableGroebnerBaseAbstract<BigInteger> getImplementation(BigInteger fac, Algo a, PairList<BigInteger> pl) {
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new SolvableGroebnerBasePseudoSeq(fac, pl);
            case IExpr.DOUBLEID /*2*/:
                throw new UnsupportedOperationException("egb algorithm not available for BigInteger " + a);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                throw new UnsupportedOperationException("dgb algorithm not available for BigInteger " + a);
            default:
                throw new IllegalArgumentException("algorithm not available for BigInteger " + a);
        }
    }

    public static SolvableGroebnerBaseAbstract<BigRational> getImplementation(BigRational fac) {
        return getImplementation(fac, Algo.qgb);
    }

    public static SolvableGroebnerBaseAbstract<BigRational> getImplementation(BigRational fac, Algo a) {
        return getImplementation(fac, a, new OrderedPairlist());
    }

    public static SolvableGroebnerBaseAbstract<BigRational> getImplementation(BigRational fac, PairList<BigRational> pl) {
        return getImplementation(fac, Algo.qgb, (PairList) pl);
    }

    public static SolvableGroebnerBaseAbstract<BigRational> getImplementation(BigRational fac, Algo a, PairList<BigRational> pl) {
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new SolvableGroebnerBaseSeq((PairList) pl);
            case ValueServer.CONSTANT_MODE /*5*/:
                throw new UnsupportedOperationException("ffgb algorithm not available for BigRational " + a);
            default:
                throw new IllegalArgumentException("algorithm not available for " + fac.toScriptFactory() + ", Algo = " + a);
        }
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac) {
        return getImplementation((QuotientRing) fac, Algo.qgb);
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac, Algo a) {
        return getImplementation((QuotientRing) fac, a, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac, PairList<Quotient<C>> pl) {
        return getImplementation((QuotientRing) fac, Algo.qgb, (PairList) pl);
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac, Algo a, PairList<Quotient<C>> pl) {
        if (logger.isInfoEnabled()) {
            logger.info("QuotientRing, fac = " + fac);
        }
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new SolvableGroebnerBaseSeq(new SolvableReductionSeq(), pl);
            case ValueServer.CONSTANT_MODE /*5*/:
                throw new UnsupportedOperationException("ffgb algorithm not available for " + a);
            default:
                throw new IllegalArgumentException("algorithm not available for Quotient " + a);
        }
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac) {
        return getImplementation((GenPolynomialRing) fac, Algo.igb);
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac, Algo a) {
        return getImplementation((GenPolynomialRing) fac, a, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac, PairList<GenPolynomial<C>> pl) {
        return getImplementation((GenPolynomialRing) fac, Algo.igb, (PairList) pl);
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<GenPolynomial<C>> getImplementation(GenPolynomialRing<C> fac, Algo a, PairList<GenPolynomial<C>> pl) {
        switch (1.$SwitchMap$edu$jas$gbufd$GBFactory$Algo[a.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new SolvableGroebnerBasePseudoRecSeq((RingFactory) fac, (PairList) pl);
            case IExpr.DOUBLEID /*2*/:
                throw new UnsupportedOperationException("egb algorithm not available for " + a);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                throw new UnsupportedOperationException("dgb algorithm not available for " + a);
            default:
                throw new IllegalArgumentException("algorithm not available for GenPolynomial<C> " + a);
        }
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<C> getImplementation(RingFactory<C> fac) {
        return getImplementation((RingFactory) fac, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<C> getImplementation(RingFactory<C> fac, PairList<C> pl) {
        logger.info("fac = " + fac.getClass().getName());
        if (fac.isField()) {
            return new SolvableGroebnerBaseSeq((PairList) pl);
        }
        if (fac instanceof ValueFactory) {
            return new SolvableGroebnerBasePseudoSeq(fac, pl);
        }
        if (fac instanceof QuotPairFactory) {
            return new SolvableGroebnerBaseSeq((PairList) pl);
        }
        SolvableGroebnerBaseAbstract<C> bba;
        RingFactory<C> ofac = fac;
        if (ofac instanceof GenPolynomialRing) {
            PairList pli;
            if (pl instanceof OrderedMinPairlist) {
                pli = new OrderedMinPairlist();
            } else if (pl instanceof OrderedSyzPairlist) {
                pli = new OrderedSyzPairlist();
            } else {
                pli = new OrderedPairlist();
            }
            bba = new SolvableGroebnerBasePseudoRecSeq((GenPolynomialRing) ofac, pli);
        } else {
            bba = new SolvableGroebnerBasePseudoSeq(fac, pl);
        }
        logger.info("bba = " + bba.getClass().getName());
        return bba;
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<C> getProxy(RingFactory<C> fac) {
        return getProxy(fac, new OrderedPairlist());
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<C> getProxy(RingFactory<C> fac, PairList<C> pl) {
        int th = 2;
        if (ComputerThreads.NO_THREADS) {
            return getImplementation((RingFactory) fac, (PairList) pl);
        }
        logger.info("proxy fac = " + fac.getClass().getName());
        if (ComputerThreads.N_CPUS > 2) {
            th = ComputerThreads.N_CPUS - 1;
        }
        if (fac.isField()) {
            return new SGBProxy(new SolvableGroebnerBaseSeq((PairList) pl), new SolvableGroebnerBaseParallel(th, (PairList) pl));
        }
        if (fac.characteristic().signum() != 0) {
            return getImplementation((RingFactory) fac, (PairList) pl);
        }
        if (fac instanceof GenPolynomialRing) {
            SolvableGroebnerBaseAbstract<C> e1 = new SolvableGroebnerBasePseudoRecSeq((GenPolynomialRing) fac, new OrderedPairlist());
            logger.warn("no parallel version available, returning sequential version");
            return e1;
        }
        SolvableGroebnerBaseAbstract<C> e12 = new SolvableGroebnerBasePseudoSeq(fac, pl);
        logger.warn("no parallel version available, returning sequential version");
        return e12;
    }

    public static <C extends GcdRingElem<C>> SolvableGroebnerBaseAbstract<GenPolynomial<C>> getProxy(GenPolynomialRing<C> fac) {
        if (ComputerThreads.NO_THREADS) {
            return getImplementation((GenPolynomialRing) fac);
        }
        logger.info("fac = " + fac.getClass().getName());
        SolvableGroebnerBaseAbstract<GenPolynomial<C>> e1 = new SolvableGroebnerBasePseudoRecSeq((RingFactory) fac, new OrderedPairlist());
        logger.warn("no parallel version available, returning sequential version");
        return e1;
    }
}
