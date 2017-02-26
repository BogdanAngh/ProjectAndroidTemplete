package edu.jas.structure;

public interface UnaryFunctor<C extends Element<C>, D extends Element<D>> {
    D eval(C c);
}
