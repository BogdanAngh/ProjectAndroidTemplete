package edu.jas.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* compiled from: KsubSet */
class ZeroSubSetIterator<E> implements Iterator<List<E>> {
    private boolean hasNext;

    public ZeroSubSetIterator(List<E> list) {
        this.hasNext = true;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public List<E> next() {
        List<E> next = new LinkedList();
        this.hasNext = false;
        return next;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove subsets");
    }
}
