package edu.jas.structure;

public interface ValueFactory<C extends RingElem<C>, D extends RingElem<D>> {
    D create(C c);

    RingFactory<C> valueFactory();
}
