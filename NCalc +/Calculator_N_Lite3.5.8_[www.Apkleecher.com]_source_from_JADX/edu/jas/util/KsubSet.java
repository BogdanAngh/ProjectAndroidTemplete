package edu.jas.util;

import java.util.Iterator;
import java.util.List;

public class KsubSet<E> implements Iterable<List<E>> {
    public final int k;
    public final List<E> set;

    public KsubSet(List<E> set, int k) {
        if (set == null) {
            throw new IllegalArgumentException("null set not allowed");
        } else if (k < 0 || k > set.size()) {
            throw new IllegalArgumentException("k out of range");
        } else {
            this.set = set;
            this.k = k;
        }
    }

    public Iterator<List<E>> iterator() {
        if (this.k == 0) {
            return new ZeroSubSetIterator(this.set);
        }
        if (this.k == 1) {
            return new OneSubSetIterator(this.set);
        }
        return new KsubSetIterator(this.set, this.k);
    }
}
