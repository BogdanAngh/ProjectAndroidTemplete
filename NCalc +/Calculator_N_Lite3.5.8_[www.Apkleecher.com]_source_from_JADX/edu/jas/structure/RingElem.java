package edu.jas.structure;

public interface RingElem<C extends RingElem<C>> extends AbelianGroupElem<C>, MonoidElem<C> {
    C[] egcd(C c);

    C gcd(C c);
}
