package edu.jas.root;

import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface RealRoots<C extends RingElem<C> & Rational> extends Serializable {
    C realMagnitude(Interval<C> interval, GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2, C c);

    C realRootBound(GenPolynomial<C> genPolynomial);

    long realRootCount(Interval<C> interval, GenPolynomial<C> genPolynomial);

    List<Interval<C>> realRoots(GenPolynomial<C> genPolynomial);

    List<Interval<C>> realRoots(GenPolynomial<C> genPolynomial, BigRational bigRational);

    List<Interval<C>> realRoots(GenPolynomial<C> genPolynomial, C c);

    int realSign(Interval<C> interval, GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    Interval<C> refineInterval(Interval<C> interval, GenPolynomial<C> genPolynomial, C c);

    List<Interval<C>> refineIntervals(List<Interval<C>> list, GenPolynomial<C> genPolynomial, C c);

    boolean signChange(Interval<C> interval, GenPolynomial<C> genPolynomial);
}
