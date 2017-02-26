package edu.jas.util;

import java.util.Iterator;
import java.util.List;

public class CartesianProductInfinite<E> implements Iterable<List<E>> {
    public final List<Iterable<E>> comps;

    public CartesianProductInfinite(List<Iterable<E>> comps) {
        if (comps == null || comps.size() == 0) {
            throw new IllegalArgumentException("null components not allowed");
        }
        this.comps = comps;
    }

    public Iterator<List<E>> iterator() {
        if (this.comps.size() == 1) {
            return new CartesianOneProductInfiniteIterator((Iterable) this.comps.get(0));
        }
        int n = this.comps.size();
        int k = (n / 2) + (n % 2);
        return new CartesianTwoProductInfiniteIteratorList(new CartesianProductInfinite(this.comps.subList(0, k)), new CartesianProductInfinite(this.comps.subList(k, n)));
    }
}
