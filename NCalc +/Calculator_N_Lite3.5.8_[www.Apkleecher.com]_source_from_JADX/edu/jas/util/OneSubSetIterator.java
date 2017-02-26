package edu.jas.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* compiled from: KsubSet */
class OneSubSetIterator<E> implements Iterator<List<E>> {
    private Iterator<E> iter;
    public final List<E> set;

    public OneSubSetIterator(List<E> set) {
        this.set = set;
        if (set == null || set.size() == 0) {
            this.iter = null;
        } else {
            this.iter = this.set.iterator();
        }
    }

    public boolean hasNext() {
        if (this.iter == null) {
            return false;
        }
        return this.iter.hasNext();
    }

    public List<E> next() {
        List<E> next = new LinkedList();
        next.add(this.iter.next());
        return next;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove subsets");
    }
}
