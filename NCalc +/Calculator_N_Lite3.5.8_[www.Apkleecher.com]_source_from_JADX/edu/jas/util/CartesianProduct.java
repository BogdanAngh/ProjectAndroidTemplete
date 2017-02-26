package edu.jas.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartesianProduct<E> implements Iterable<List<E>> {
    public final List<Iterable<E>> comps;

    public CartesianProduct(List<Iterable<E>> comps) {
        if (comps == null) {
            throw new IllegalArgumentException("null components not allowed");
        }
        this.comps = comps;
    }

    public Iterator<List<E>> iterator() {
        return new CartesianProductIterator(this.comps);
    }

    static <E> List<Iterable<E>> listToIterable(List<List<E>> comp) {
        List<Iterable<E>> iter = new ArrayList(comp.size());
        for (List<E> list : comp) {
            iter.add(list);
        }
        return iter;
    }
}
