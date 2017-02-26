package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import org.apache.log4j.Logger;

public class FactorFactory {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(FactorFactory.class);
    }

    protected FactorFactory() {
    }

    public static FactorAbstract<ModInteger> getImplementation(ModIntegerRing fac) {
        return new FactorModular(fac);
    }

    public static FactorAbstract<ModLong> getImplementation(ModLongRing fac) {
        return new FactorModular(fac);
    }

    public static FactorAbstract<BigInteger> getImplementation(BigInteger fac) {
        return new FactorInteger();
    }

    public static FactorAbstract<BigRational> getImplementation(BigRational fac) {
        return new FactorRational();
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<AlgebraicNumber<C>> getImplementation(AlgebraicNumberRing<C> fac) {
        return new FactorAlgebraic(fac);
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<Complex<C>> getImplementation(ComplexRing<C> fac) {
        return new FactorComplex((ComplexRing) fac);
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac) {
        return new FactorQuotient(fac);
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<C> getImplementation(GenPolynomialRing<C> fac) {
        return getImplementation(fac.coFac);
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<C> getImplementation(RingFactory<C> fac) {
        logger.info("factor factory = " + fac.getClass().getName());
        RingFactory<C> ofac = fac;
        if (ofac instanceof BigInteger) {
            return new FactorInteger();
        }
        if (ofac instanceof BigRational) {
            return new FactorRational();
        }
        if (ofac instanceof ModIntegerRing) {
            return new FactorModular(fac);
        }
        if (ofac instanceof ModLongRing) {
            return new FactorModular(fac);
        }
        if (ofac instanceof ComplexRing) {
            return new FactorComplex((ComplexRing) ofac);
        }
        if (ofac instanceof AlgebraicNumberRing) {
            return new FactorAlgebraic((AlgebraicNumberRing) ofac);
        }
        if (ofac instanceof QuotientRing) {
            return new FactorQuotient((QuotientRing) ofac);
        }
        if (ofac instanceof GenPolynomialRing) {
            return getImplementation(((GenPolynomialRing) ofac).coFac);
        }
        throw new IllegalArgumentException("no factorization implementation for " + fac.getClass().getName());
    }
}
