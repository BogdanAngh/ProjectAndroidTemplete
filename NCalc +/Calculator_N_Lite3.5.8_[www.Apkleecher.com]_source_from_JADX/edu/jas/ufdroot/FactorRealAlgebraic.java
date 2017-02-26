package edu.jas.ufdroot;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.root.PolyUtilRoot;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.root.RealAlgebraicRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class FactorRealAlgebraic<C extends GcdRingElem<C> & Rational> extends FactorAbstract<RealAlgebraicNumber<C>> {
    private static final Logger logger;
    public final FactorAbstract<AlgebraicNumber<C>> factorAlgebraic;

    static {
        logger = Logger.getLogger(FactorRealAlgebraic.class);
    }

    protected FactorRealAlgebraic() {
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorRealAlgebraic(RealAlgebraicRing<C> fac) {
        this(fac, FactorFactory.getImplementation(fac.algebraic));
    }

    public FactorRealAlgebraic(RealAlgebraicRing<C> fac, FactorAbstract<AlgebraicNumber<C>> factorAlgebraic) {
        super(fac);
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
                RealAlgebraicRing<C> rfac = pfac.coFac;
                RingElem ldcf = (RealAlgebraicNumber) P.leadingBaseCoefficient();
                if (!ldcf.isONE()) {
                    P = P.monic();
                    factors.add(pfac.getONE().multiply(ldcf));
                }
                for (GenPolynomial<AlgebraicNumber<C>> a : this.factorAlgebraic.baseFactorsSquarefree(PolyUtilRoot.algebraicFromRealCoefficients(new GenPolynomialRing(rfac.algebraic, pfac), P))) {
                    factors.add(PolyUtilRoot.realFromAlgebraicCoefficients(pfac, a));
                }
                logger.info("real algebraic factors = " + factors);
            }
        }
        return factors;
    }
}
