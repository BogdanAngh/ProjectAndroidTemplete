package edu.jas.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: KsubSet */
class KsubSetIterator<E> implements Iterator<List<E>> {
    private E current;
    private final Iterator<E> iter;
    public final int k;
    private Iterator<List<E>> recIter;
    final List<E> rest;
    public final List<E> set;

    public KsubSetIterator(List<E> set, int k) {
        if (set == null || set.size() == 0) {
            throw new IllegalArgumentException("null or empty set not allowed");
        } else if (k < 2 || k > set.size()) {
            throw new IllegalArgumentException("k out of range");
        } else {
            this.set = set;
            this.k = k;
            this.iter = this.set.iterator();
            this.current = this.iter.next();
            this.rest = new LinkedList(this.set);
            this.rest.remove(0);
            if (k == 2) {
                this.recIter = new OneSubSetIterator(this.rest);
            } else {
                this.recIter = new KsubSetIterator(this.rest, k - 1);
            }
        }
    }

    public boolean hasNext() {
        return this.recIter.hasNext() || (this.iter.hasNext() && this.rest.size() >= this.k);
    }

    public List<E> next() {
        if (this.recIter.hasNext()) {
            List<E> next = new LinkedList((Collection) this.recIter.next());
            next.add(0, this.current);
            return next;
        } else if (this.iter.hasNext()) {
            this.current = this.iter.next();
            this.rest.remove(0);
            if (this.rest.size() < this.k - 1) {
                throw new NoSuchElementException("invalid call of next()");
            }
            if (this.k == 2) {
                this.recIter = new OneSubSetIterator(this.rest);
            } else {
                this.recIter = new KsubSetIterator(this.rest, this.k - 1);
            }
            return next();
        } else {
            throw new NoSuchElementException("invalid call of next()");
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove subsets");
    }
}
