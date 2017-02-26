package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilApp */
class EvaluateToComplexReal<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<GenPolynomial<Complex<C>>, Complex<RealAlgebraicNumber<C>>> {
    protected final ComplexRing<RealAlgebraicNumber<C>> cfac;
    protected final GenPolynomialRing<Complex<RealAlgebraicNumber<C>>> pfac;
    protected final Complex<RealAlgebraicNumber<C>> root;

    public EvaluateToComplexReal(GenPolynomialRing<Complex<RealAlgebraicNumber<C>>> fac, Complex<RealAlgebraicNumber<C>> r) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        } else if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        } else {
            this.pfac = fac;
            this.cfac = (ComplexRing) fac.coFac;
            this.root = r;
        }
    }

    public Complex<RealAlgebraicNumber<C>> eval(GenPolynomial<Complex<C>> c) {
        if (c == null) {
            return this.cfac.getZERO();
        }
        return (Complex) PolyUtil.evaluateMain(this.cfac, PolyUtilApp.convertToComplexRealCoefficients(this.pfac, c), this.root);
    }
}
