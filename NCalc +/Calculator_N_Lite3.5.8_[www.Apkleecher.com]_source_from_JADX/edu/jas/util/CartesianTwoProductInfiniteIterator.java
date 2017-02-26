package edu.jas.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: CartesianProductInfinite */
class CartesianTwoProductInfiniteIterator<E> implements Iterator<List<E>> {
    final Iterator<E> compit0;
    final Iterator<E> compit1;
    List<E> current;
    boolean empty;
    Iterator<E> fincompit0;
    Iterator<E> fincompit1;
    final List<E> fincomps0;
    final List<E> fincomps1;
    long level;

    public CartesianTwoProductInfiniteIterator(Iterable<E> comps0, Iterable<E> comps1) {
        if (comps0 == null || comps1 == null) {
            throw new IllegalArgumentException("null comps not allowed");
        }
        this.empty = false;
        this.level = 0;
        this.current = new ArrayList(2);
        this.compit0 = comps0.iterator();
        E e = this.compit0.next();
        this.current.add(e);
        this.fincomps0 = new ArrayList();
        this.fincomps0.add(e);
        this.fincompit0 = this.fincomps0.iterator();
        this.fincompit0.next();
        this.compit1 = comps1.iterator();
        e = this.compit1.next();
        this.current.add(e);
        this.fincomps1 = new ArrayList();
        this.fincomps1.add(e);
        this.fincompit1 = this.fincomps1.iterator();
        this.fincompit1.next();
    }

    public synchronized boolean hasNext() {
        return !this.empty;
    }

    public synchronized List<E> next() {
        List<E> res;
        if (this.empty) {
            throw new NoSuchElementException("invalid call of next()");
        }
        res = this.current;
        E e0;
        E e1;
        if (this.fincompit0.hasNext() && this.fincompit1.hasNext()) {
            e0 = this.fincompit0.next();
            e1 = this.fincompit1.next();
            this.current = new ArrayList();
            this.current.add(e0);
            this.current.add(e1);
        } else {
            this.level++;
            if (this.level % 2 == 1) {
                Collections.reverse(this.fincomps0);
            } else {
                Collections.reverse(this.fincomps1);
            }
            if (this.compit0.hasNext() && this.compit1.hasNext()) {
                this.fincomps0.add(this.compit0.next());
                this.fincomps1.add(this.compit1.next());
                if (this.level % 2 == 0) {
                    Collections.reverse(this.fincomps0);
                } else {
                    Collections.reverse(this.fincomps1);
                }
                this.fincompit0 = this.fincomps0.iterator();
                this.fincompit1 = this.fincomps1.iterator();
                e0 = this.fincompit0.next();
                e1 = this.fincompit1.next();
                this.current = new ArrayList();
                this.current.add(e0);
                this.current.add(e1);
            } else {
                this.empty = true;
            }
        }
        return res;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove tuples");
    }
}
