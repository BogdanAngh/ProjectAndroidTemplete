package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.FactorAbstract;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class FactorRealReal<C extends GcdRingElem<C> & Rational> extends FactorAbstract<RealAlgebraicNumber<C>> {
    private static final Logger logger;
    private final boolean debug;
    public final FactorAbstract<RealAlgebraicNumber<C>> factorAlgebraic;

    static {
        logger = Logger.getLogger(FactorRealReal.class);
    }

    protected FactorRealReal() {
        this.debug = logger.isInfoEnabled();
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorRealReal(RealAlgebraicRing<C> fac) {
        this(fac, FactorFactory.getImplementation((RingFactory) fac.realRing));
    }

    public FactorRealReal(RealAlgebraicRing<C> fac, FactorAbstract<RealAlgebraicNumber<C>> factorAlgebraic) {
        super(fac);
        this.debug = logger.isInfoEnabled();
        this.factorAlgebraic = factorAlgebraic;
    }

    public List<GenPolynomial<RealAlgebraicNumber<C>>> baseFactorsSquarefree(GenPolynomial<RealAlgebraicNumber<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<RealAlgebraicNumber<C>>> factors = new ArrayList();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.add(P);
            } else {
                GenPolynomialRing pfac = P.ring;
                if (pfac.nvar > 1) {
                    throw new IllegalArgumentException("only for univariate polynomials");
                }
                RingFactory rfac = pfac.coFac.realRing;
                RingElem ldcf = (RealAlgebraicNumber) P.leadingBaseCoefficient();
                if (!ldcf.isONE()) {
                    P = P.monic();
                    factors.add(pfac.getONE().multiply(ldcf));
                }
                for (GenPolynomial<RealAlgebraicNumber<C>> a : this.factorAlgebraic.baseFactorsSquarefree(PolyUtilApp.realAlgFromRealCoefficients(new GenPolynomialRing(rfac, pfac), P))) {
                    factors.add(PolyUtilApp.realFromRealAlgCoefficients(pfac, a));
                }
                if (this.debug) {
                    logger.info("rafactors = " + factors);
                }
            }
        }
        return factors;
    }
}
