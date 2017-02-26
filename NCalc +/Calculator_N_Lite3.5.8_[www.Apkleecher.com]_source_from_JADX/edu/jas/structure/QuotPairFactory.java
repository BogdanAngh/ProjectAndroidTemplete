package edu.jas.structure;

public interface QuotPairFactory<C extends RingElem<C>, D extends RingElem<D>> {
    D create(C c);

    D create(C c, C c2);

    RingFactory<C> pairFactory();
}
