package edu.jas.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: CartesianProductInfinite */
class CartesianOneProductInfiniteIterator<E> implements Iterator<List<E>> {
    final Iterator<E> compit;

    public CartesianOneProductInfiniteIterator(Iterable<E> comps) {
        if (comps == null) {
            throw new IllegalArgumentException("null comps not allowed");
        }
        this.compit = comps.iterator();
    }

    public synchronized boolean hasNext() {
        return this.compit.hasNext();
    }

    public synchronized List<E> next() {
        List<E> res;
        res = new ArrayList(1);
        res.add(this.compit.next());
        return res;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove tuples");
    }
}
