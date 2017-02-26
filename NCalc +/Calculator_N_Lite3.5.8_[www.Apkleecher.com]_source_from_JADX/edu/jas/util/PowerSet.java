package edu.jas.util;

import java.util.Iterator;
import java.util.List;

public class PowerSet<E> implements Iterable<List<E>> {
    public final List<E> set;

    public PowerSet(List<E> set) {
        this.set = set;
    }

    public Iterator<List<E>> iterator() {
        return new PowerSetIterator(this.set);
    }
}
