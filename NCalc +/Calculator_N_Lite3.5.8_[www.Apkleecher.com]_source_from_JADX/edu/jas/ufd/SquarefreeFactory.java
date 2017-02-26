package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import org.apache.log4j.Logger;

public class SquarefreeFactory {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(SquarefreeFactory.class);
    }

    protected SquarefreeFactory() {
    }

    public static SquarefreeAbstract<ModInteger> getImplementation(ModIntegerRing fac) {
        return new SquarefreeFiniteFieldCharP(fac);
    }

    public static SquarefreeAbstract<ModLong> getImplementation(ModLongRing fac) {
        return new SquarefreeFiniteFieldCharP(fac);
    }

    public static SquarefreeAbstract<BigInteger> getImplementation(BigInteger fac) {
        return new SquarefreeRingChar0(fac);
    }

    public static SquarefreeAbstract<BigRational> getImplementation(BigRational fac) {
        return new SquarefreeFieldChar0(fac);
    }

    public static <C extends GcdRingElem<C>> SquarefreeAbstract<AlgebraicNumber<C>> getImplementation(AlgebraicNumberRing<C> fac) {
        PolyUfdUtil.ensureFieldProperty(fac);
        if (!fac.isField()) {
            throw new ArithmeticException("eventually no integral domain " + fac.getClass().getName());
        } else if (fac.characteristic().signum() == 0) {
            return new SquarefreeFieldChar0(fac);
        } else {
            if (fac.isFinite()) {
                return new SquarefreeFiniteFieldCharP(fac);
            }
            return new SquarefreeInfiniteAlgebraicFieldCharP(fac);
        }
    }

    public static <C extends GcdRingElem<C>> SquarefreeAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac) {
        if (fac.characteristic().signum() == 0) {
            return new SquarefreeFieldChar0(fac);
        }
        return new SquarefreeInfiniteFieldCharP(fac);
    }

    public static <C extends GcdRingElem<C>> SquarefreeAbstract<C> getImplementation(GenPolynomialRing<C> fac) {
        return getImplementationPoly(fac);
    }

    protected static <C extends GcdRingElem<C>> SquarefreeAbstract<C> getImplementationPoly(GenPolynomialRing<C> fac) {
        if (fac.characteristic().signum() == 0) {
            if (fac.coFac.isField()) {
                return new SquarefreeFieldChar0(fac.coFac);
            }
            return new SquarefreeRingChar0(fac.coFac);
        } else if (fac.coFac.isFinite()) {
            return new SquarefreeFiniteFieldCharP(fac.coFac);
        } else {
            RingFactory ocfac = fac.coFac;
            SquarefreeAbstract<C> saq = null;
            if (ocfac instanceof QuotientRing) {
                saq = new SquarefreeInfiniteFieldCharP((QuotientRing) ocfac);
            } else if (ocfac instanceof AlgebraicNumberRing) {
                saq = new SquarefreeInfiniteAlgebraicFieldCharP((AlgebraicNumberRing) ocfac);
            }
            if (saq != null) {
                return saq;
            }
            throw new IllegalArgumentException("no squarefree factorization " + fac.coFac);
        }
    }

    public static <C extends GcdRingElem<C>> SquarefreeAbstract<C> getImplementation(RingFactory<C> fac) {
        SquarefreeAbstract ufd;
        RingFactory<C> ofac = fac;
        if (ofac instanceof BigInteger) {
            ufd = new SquarefreeRingChar0(fac);
        } else if (ofac instanceof BigRational) {
            ufd = new SquarefreeFieldChar0(fac);
        } else if (ofac instanceof ModIntegerRing) {
            ufd = new SquarefreeFiniteFieldCharP(fac);
        } else if (ofac instanceof ModLongRing) {
            ufd = new SquarefreeFiniteFieldCharP(fac);
        } else if (ofac instanceof AlgebraicNumberRing) {
            ufd = getImplementation((AlgebraicNumberRing) ofac);
        } else if (ofac instanceof QuotientRing) {
            ufd = getImplementation((QuotientRing) ofac);
        } else if (ofac instanceof GenPolynomialRing) {
            ufd = getImplementationPoly((GenPolynomialRing) ofac);
        } else if (fac.isField()) {
            if (fac.characteristic().signum() == 0) {
                ufd = new SquarefreeFieldChar0(fac);
            } else if (fac.isFinite()) {
                ufd = new SquarefreeFiniteFieldCharP(fac);
            } else {
                ufd = new SquarefreeInfiniteFieldCharP(fac);
            }
        } else if (fac.characteristic().signum() == 0) {
            ufd = new SquarefreeRingChar0(fac);
        } else {
            throw new IllegalArgumentException("no squarefree factorization implementation for " + fac.getClass().getName());
        }
        logger.debug("ufd = " + ufd);
        return ufd;
    }
}
