package edu.jas.structure;

public interface MonoidElem<C extends MonoidElem<C>> extends Element<C> {
    C divide(C c);

    C inverse();

    boolean isONE();

    boolean isUnit();

    C multiply(C c);

    C remainder(C c);
}
