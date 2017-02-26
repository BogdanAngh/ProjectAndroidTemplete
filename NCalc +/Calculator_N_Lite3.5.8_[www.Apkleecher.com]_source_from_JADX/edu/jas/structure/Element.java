package edu.jas.structure;

import java.io.Serializable;

public interface Element<C extends Element<C>> extends Comparable<C>, Serializable {
    int compareTo(C c);

    C copy();

    boolean equals(Object obj);

    ElemFactory<C> factory();

    int hashCode();

    String toScript();

    String toScriptFactory();
}
