package edu.jas.structure;

public interface Selector<C extends RingElem<C>> {
    boolean select(C c);
}
