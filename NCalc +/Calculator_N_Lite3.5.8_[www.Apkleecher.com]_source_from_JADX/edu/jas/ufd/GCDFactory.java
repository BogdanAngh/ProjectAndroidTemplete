package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.kern.ComputerThreads;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import org.apache.log4j.Logger;

public class GCDFactory {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(GCDFactory.class);
    }

    protected GCDFactory() {
    }

    public static GreatestCommonDivisorAbstract<ModLong> getImplementation(ModLongRing fac) {
        if (fac.isField()) {
            return new GreatestCommonDivisorModEval();
        }
        return new GreatestCommonDivisorSubres();
    }

    public static GreatestCommonDivisorAbstract<ModLong> getProxy(ModLongRing fac) {
        GreatestCommonDivisorAbstract<ModLong> ufd2;
        GreatestCommonDivisorAbstract<ModLong> ufd1 = new GreatestCommonDivisorSubres();
        if (fac.isField()) {
            ufd2 = new GreatestCommonDivisorModEval();
        } else {
            ufd2 = new GreatestCommonDivisorSimple();
        }
        return new GCDProxy(ufd1, ufd2);
    }

    public static GreatestCommonDivisorAbstract<ModInteger> getImplementation(ModIntegerRing fac) {
        if (fac.isField()) {
            return new GreatestCommonDivisorModEval();
        }
        return new GreatestCommonDivisorSubres();
    }

    public static GreatestCommonDivisorAbstract<ModInteger> getProxy(ModIntegerRing fac) {
        GreatestCommonDivisorAbstract<ModInteger> ufd2;
        GreatestCommonDivisorAbstract<ModInteger> ufd1 = new GreatestCommonDivisorSubres();
        if (fac.isField()) {
            ufd2 = new GreatestCommonDivisorModEval();
        } else {
            ufd2 = new GreatestCommonDivisorSimple();
        }
        return new GCDProxy(ufd1, ufd2);
    }

    public static GreatestCommonDivisorAbstract<BigInteger> getImplementation(BigInteger fac) {
        return new GreatestCommonDivisorModular();
    }

    public static GreatestCommonDivisorAbstract<BigInteger> getProxy(BigInteger fac) {
        return new GCDProxy(new GreatestCommonDivisorSubres(), new GreatestCommonDivisorModular());
    }

    public static GreatestCommonDivisorAbstract<BigRational> getImplementation(BigRational fac) {
        return new GreatestCommonDivisorPrimitive();
    }

    public static GreatestCommonDivisorAbstract<BigRational> getProxy(BigRational fac) {
        return new GCDProxy(new GreatestCommonDivisorSubres(), new GreatestCommonDivisorSimple());
    }

    public static <C extends GcdRingElem<C>> GreatestCommonDivisorAbstract<C> getImplementation(RingFactory<C> fac) {
        GreatestCommonDivisorAbstract ufd;
        logger.debug("fac = " + fac.getClass().getName());
        RingFactory<C> ofac = fac;
        if (ofac instanceof BigInteger) {
            ufd = new GreatestCommonDivisorModular();
        } else if (ofac instanceof ModIntegerRing) {
            ufd = new GreatestCommonDivisorModEval();
        } else if (ofac instanceof ModLongRing) {
            ufd = new GreatestCommonDivisorModEval();
        } else if (ofac instanceof BigRational) {
            ufd = new GreatestCommonDivisorSubres();
        } else if (fac.isField()) {
            ufd = new GreatestCommonDivisorSimple();
        } else {
            ufd = new GreatestCommonDivisorSubres();
        }
        logger.debug("implementation = " + ufd);
        return ufd;
    }

    public static <C extends GcdRingElem<C>> GreatestCommonDivisorAbstract<C> getProxy(RingFactory<C> fac) {
        if (ComputerThreads.NO_THREADS) {
            return getImplementation((RingFactory) fac);
        }
        GreatestCommonDivisorAbstract<C> ufd;
        logger.debug("fac = " + fac.getClass().getName());
        RingFactory<C> ofac = fac;
        if (ofac instanceof BigInteger) {
            ufd = new GCDProxy(new GreatestCommonDivisorSubres(), new GreatestCommonDivisorModular());
        } else if (ofac instanceof ModIntegerRing) {
            ufd = new GCDProxy(new GreatestCommonDivisorSimple(), new GreatestCommonDivisorModEval());
        } else if (ofac instanceof ModLongRing) {
            ufd = new GCDProxy(new GreatestCommonDivisorSimple(), new GreatestCommonDivisorModEval());
        } else if (ofac instanceof BigRational) {
            ufd = new GCDProxy(new GreatestCommonDivisorSubres(), new GreatestCommonDivisorSimple());
        } else if (fac.isField()) {
            ufd = new GCDProxy(new GreatestCommonDivisorSimple(), new GreatestCommonDivisorSubres());
        } else {
            ufd = new GCDProxy(new GreatestCommonDivisorSubres(), new GreatestCommonDivisorPrimitive());
        }
        logger.debug("ufd = " + ufd);
        return ufd;
    }
}
