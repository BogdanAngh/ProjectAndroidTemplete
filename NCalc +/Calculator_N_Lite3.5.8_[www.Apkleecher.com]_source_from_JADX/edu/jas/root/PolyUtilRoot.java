package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.Complex;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import org.apache.log4j.Logger;

public class PolyUtilRoot {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(PolyUtilRoot.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<RealAlgebraicNumber<C>> convertToAlgebraicCoefficients(GenPolynomialRing<RealAlgebraicNumber<C>> pfac, GenPolynomial<C> A) {
        RealAlgebraicRing<C> afac = pfac.coFac;
        if (debug) {
            logger.info("afac = " + afac);
        }
        return PolyUtil.map(pfac, A, new CoeffToReAlg(afac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<RealAlgebraicNumber<C>> convertToRecAlgebraicCoefficients(int depth, GenPolynomialRing<RealAlgebraicNumber<C>> pfac, GenPolynomial<C> A) {
        return PolyUtil.map(pfac, A, new CoeffToRecReAlg(depth, pfac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<RealAlgebraicNumber<C>> convertRecursiveToAlgebraicCoefficients(GenPolynomialRing<RealAlgebraicNumber<C>> pfac, GenPolynomial<GenPolynomial<C>> A) {
        return PolyUtil.map(pfac, A, new PolyToReAlg(pfac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<AlgebraicNumber<C>> algebraicFromRealCoefficients(GenPolynomialRing<AlgebraicNumber<C>> afac, GenPolynomial<RealAlgebraicNumber<C>> A) {
        return PolyUtil.map(afac, A, new AlgFromRealCoeff(afac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<RealAlgebraicNumber<C>> realFromAlgebraicCoefficients(GenPolynomialRing<RealAlgebraicNumber<C>> rfac, GenPolynomial<AlgebraicNumber<C>> A) {
        return PolyUtil.map(rfac, A, new RealFromAlgCoeff(rfac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<RealAlgebraicNumber<C>> convertToRealCoefficients(GenPolynomialRing<RealAlgebraicNumber<C>> pfac, GenPolynomial<C> A) {
        return PolyUtil.map(pfac, A, new CoeffToReal(pfac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<ComplexAlgebraicNumber<C>> convertToComplexCoefficients(GenPolynomialRing<ComplexAlgebraicNumber<C>> pfac, GenPolynomial<C> A) {
        return PolyUtil.map(pfac, A, new CoeffToComplex(pfac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<ComplexAlgebraicNumber<C>> convertToComplexCoefficientsFromComplex(GenPolynomialRing<ComplexAlgebraicNumber<C>> pfac, GenPolynomial<Complex<C>> A) {
        return PolyUtil.map(pfac, A, new CoeffToComplexFromComplex(pfac.coFac));
    }
}
