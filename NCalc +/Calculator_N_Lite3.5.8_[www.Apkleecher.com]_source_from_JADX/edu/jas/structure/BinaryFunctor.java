package edu.jas.structure;

public interface BinaryFunctor<C1 extends Element<C1>, C2 extends Element<C2>, D extends Element<D>> {
    D eval(C1 c1, C2 c2);
}
