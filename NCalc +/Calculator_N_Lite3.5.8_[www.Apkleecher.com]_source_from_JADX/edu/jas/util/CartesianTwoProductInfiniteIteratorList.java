package edu.jas.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: CartesianProductInfinite */
class CartesianTwoProductInfiniteIteratorList<E> implements Iterator<List<E>> {
    final Iterator<List<E>> compit0;
    final Iterator<List<E>> compit1;
    List<E> current;
    boolean empty;
    Iterator<List<E>> fincompit0;
    Iterator<List<E>> fincompit1;
    final List<List<E>> fincomps0;
    final List<List<E>> fincomps1;
    long level;

    public CartesianTwoProductInfiniteIteratorList(Iterable<List<E>> comps0, Iterable<List<E>> comps1) {
        if (comps0 == null || comps1 == null) {
            throw new IllegalArgumentException("null comps not allowed");
        }
        this.current = new ArrayList();
        this.empty = false;
        this.level = 0;
        this.compit0 = comps0.iterator();
        List<E> e = (List) this.compit0.next();
        this.current.addAll(e);
        this.fincomps0 = new ArrayList();
        this.fincomps0.add(e);
        this.fincompit0 = this.fincomps0.iterator();
        this.fincompit0.next();
        this.compit1 = comps1.iterator();
        e = (List) this.compit1.next();
        this.current.addAll(e);
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
        List<E> e0;
        List<E> e1;
        if (this.fincompit0.hasNext() && this.fincompit1.hasNext()) {
            e0 = (List) this.fincompit0.next();
            e1 = (List) this.fincompit1.next();
            this.current = new ArrayList();
            this.current.addAll(e0);
            this.current.addAll(e1);
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
                e0 = (List) this.fincompit0.next();
                e1 = (List) this.fincompit1.next();
                this.current = new ArrayList();
                this.current.addAll(e0);
                this.current.addAll(e1);
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
