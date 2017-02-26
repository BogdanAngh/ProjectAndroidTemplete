package edu.jas.structure;

public interface MonoidFactory<C extends MonoidElem<C>> extends ElemFactory<C> {
    C getONE();

    boolean isAssociative();

    boolean isCommutative();
}
