package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.root.RealAlgebraicRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorAlgebraic;
import edu.jas.ufd.FactorComplex;
import edu.jas.ufd.FactorQuotient;
import edu.jas.ufd.Quotient;
import edu.jas.ufd.QuotientRing;
import edu.jas.ufdroot.FactorRealAlgebraic;
import org.apache.log4j.Logger;

public class FactorFactory extends edu.jas.ufd.FactorFactory {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(FactorFactory.class);
    }

    protected FactorFactory() {
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<AlgebraicNumber<C>> getImplementation(AlgebraicNumberRing<C> fac) {
        return new FactorAlgebraic(fac, getImplementation(fac.ring.coFac));
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<Complex<C>> getImplementation(ComplexRing<C> fac) {
        return new FactorComplex((ComplexRing) fac);
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<Quotient<C>> getImplementation(QuotientRing<C> fac) {
        return new FactorQuotient(fac, getImplementation(fac.ring.coFac));
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<C> getImplementation(GenPolynomialRing<C> fac) {
        return getImplementation(fac.coFac);
    }

    public static <C extends GcdRingElem<C> & Rational> FactorAbstract<RealAlgebraicNumber<C>> getImplementation(RealAlgebraicRing<C> fac) {
        return new FactorRealAlgebraic(fac, getImplementation(fac.algebraic));
    }

    public static <C extends GcdRingElem<C> & Rational> FactorAbstract<RealAlgebraicNumber<C>> getImplementation(RealAlgebraicRing<C> fac) {
        return new FactorRealReal(fac, getImplementation((RingFactory) fac.realRing));
    }

    public static <C extends GcdRingElem<C>> FactorAbstract<C> getImplementation(RingFactory<C> fac) {
        logger.info("app factor factory = " + fac.getClass().getName());
        RingFactory<C> ofac = fac;
        if (ofac instanceof RealAlgebraicRing) {
            RealAlgebraicRing rrfac = (RealAlgebraicRing) ofac;
            return new FactorRealReal(rrfac, getImplementation(rrfac.realRing));
        } else if (ofac instanceof RealAlgebraicRing) {
            RealAlgebraicRing rfac = (RealAlgebraicRing) ofac;
            return new FactorRealAlgebraic(rfac, getImplementation(rfac.algebraic));
        } else if (ofac instanceof ComplexRing) {
            ComplexRing cfac = (ComplexRing) ofac;
            return new FactorComplex(cfac, getImplementation(cfac.algebraicRing()));
        } else if (ofac instanceof AlgebraicNumberRing) {
            AlgebraicNumberRing afac = (AlgebraicNumberRing) ofac;
            return new FactorAlgebraic(afac, getImplementation(afac.ring.coFac));
        } else if (ofac instanceof QuotientRing) {
            QuotientRing qfac = (QuotientRing) ofac;
            return new FactorQuotient(qfac, getImplementation(qfac.ring.coFac));
        } else if (ofac instanceof GenPolynomialRing) {
            return getImplementation(((GenPolynomialRing) ofac).coFac);
        } else {
            return edu.jas.ufd.FactorFactory.getImplementation((RingFactory) fac);
        }
    }
}
