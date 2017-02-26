package edu.jas.structure;

public interface RegularRingElem<C extends RegularRingElem<C>> extends GcdRingElem<C> {
    C fillIdempotent(C c);

    C fillOne();

    C idemComplement();

    C idempotent();

    C idempotentAnd(C c);

    C idempotentOr(C c);

    boolean isFull();

    boolean isIdempotent();
}
