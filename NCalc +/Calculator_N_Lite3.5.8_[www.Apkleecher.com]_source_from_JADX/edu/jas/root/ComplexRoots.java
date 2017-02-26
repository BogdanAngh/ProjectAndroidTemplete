package edu.jas.root;

import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface ComplexRoots<C extends RingElem<C> & Rational> extends Serializable {
    long complexRootCount(Rectangle<C> rectangle, GenPolynomial<Complex<C>> genPolynomial) throws InvalidBoundaryException;

    Rectangle<C> complexRootRefinement(Rectangle<C> rectangle, GenPolynomial<Complex<C>> genPolynomial, BigRational bigRational) throws InvalidBoundaryException;

    List<Rectangle<C>> complexRoots(GenPolynomial<Complex<C>> genPolynomial);

    List<Rectangle<C>> complexRoots(Rectangle<C> rectangle, GenPolynomial<Complex<C>> genPolynomial) throws InvalidBoundaryException;

    Complex<C> rootBound(GenPolynomial<Complex<C>> genPolynomial);
}
