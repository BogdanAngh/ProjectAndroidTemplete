package edu.jas.structure;

public interface AbelianGroupElem<C extends AbelianGroupElem<C>> extends Element<C> {
    C abs();

    boolean isZERO();

    C negate();

    int signum();

    C subtract(C c);

    C sum(C c);
}
