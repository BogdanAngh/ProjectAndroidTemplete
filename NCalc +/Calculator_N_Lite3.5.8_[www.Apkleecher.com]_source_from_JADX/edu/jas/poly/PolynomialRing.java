package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.List;
import java.util.Random;

public interface PolynomialRing<C extends RingElem<C>> extends RingFactory<Polynomial<C>> {
    PolynomialRing<C> contract(int i);

    PolynomialRing<C> extend(int i);

    String[] getVars();

    int numberOfVariables();

    Polynomial<C> random(int i, int i2, int i3, float f);

    Polynomial<C> random(int i, int i2, int i3, float f, Random random);

    PolynomialRing<C> reverse();

    Polynomial<C> univariate(int i);

    Polynomial<C> univariate(int i, long j);

    List<? extends Polynomial<C>> univariateList();
}
