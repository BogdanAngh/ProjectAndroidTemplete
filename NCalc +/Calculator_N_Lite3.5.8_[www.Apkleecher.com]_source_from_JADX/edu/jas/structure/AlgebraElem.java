package edu.jas.structure;

public interface AlgebraElem<A extends AlgebraElem<A, C>, C extends RingElem<C>> extends RingElem<A> {
    A linearCombination(A a, C c);

    A linearCombination(C c, A a, C c2);

    A scalarMultiply(C c);
}
