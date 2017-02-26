package edu.jas.structure;

public interface AbelianGroupFactory<C extends AbelianGroupElem<C>> extends ElemFactory<C> {
    C getZERO();
}
