package edu.jas.structure;

public interface Value<C extends RingElem<C>> {
    boolean isConstant();

    C value();
}
