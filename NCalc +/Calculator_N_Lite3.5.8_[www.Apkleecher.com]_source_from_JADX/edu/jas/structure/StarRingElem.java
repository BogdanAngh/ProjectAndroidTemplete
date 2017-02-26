package edu.jas.structure;

public interface StarRingElem<C extends StarRingElem<C>> extends RingElem<C> {
    C conjugate();

    C norm();
}
