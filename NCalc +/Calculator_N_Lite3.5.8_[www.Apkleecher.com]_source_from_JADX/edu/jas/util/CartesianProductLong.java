package edu.jas.util;

import java.util.Iterator;
import java.util.List;

public class CartesianProductLong implements Iterable<List<Long>> {
    public final List<LongIterable> comps;
    public final long upperBound;

    public CartesianProductLong(List<LongIterable> comps, long ub) {
        if (comps == null) {
            throw new IllegalArgumentException("null components not allowed");
        }
        this.comps = comps;
        this.upperBound = ub;
    }

    public Iterator<List<Long>> iterator() {
        return new CartesianProductLongIterator(this.comps, this.upperBound);
    }
}
