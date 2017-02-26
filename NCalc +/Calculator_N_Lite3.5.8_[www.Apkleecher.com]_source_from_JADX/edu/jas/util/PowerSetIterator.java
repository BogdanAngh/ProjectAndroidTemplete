package edu.jas.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* compiled from: PowerSet */
class PowerSetIterator<E> implements Iterator<List<E>> {
    final E current;
    Mode mode;
    private PowerSetIterator<E> recIter;
    final List<E> rest;
    public final List<E> set;

    /* compiled from: PowerSet */
    enum Mode {
        copy,
        extend,
        first,
        done
    }

    public PowerSetIterator(List<E> set) {
        this.set = set;
        if (set == null || set.size() == 0) {
            this.current = null;
            this.recIter = null;
            this.rest = null;
            this.mode = Mode.first;
            return;
        }
        this.mode = Mode.copy;
        this.current = this.set.get(0);
        this.rest = new LinkedList(this.set);
        this.rest.remove(0);
        this.recIter = new PowerSetIterator(this.rest);
    }

    public boolean hasNext() {
        if (this.mode == Mode.first) {
            return true;
        }
        if (this.recIter == null) {
            return false;
        }
        if (this.recIter.hasNext() || this.mode == Mode.copy) {
            return true;
        }
        return false;
    }

    public List<E> next() {
        if (this.mode == Mode.first) {
            this.mode = Mode.done;
            return new LinkedList();
        } else if (this.mode == Mode.extend && this.recIter.hasNext()) {
            List<E> next = new LinkedList(this.recIter.next());
            next.add(this.current);
            return next;
        } else if (this.mode != Mode.copy) {
            return null;
        } else {
            if (this.recIter.hasNext()) {
                return this.recIter.next();
            }
            this.mode = Mode.extend;
            this.recIter = new PowerSetIterator(this.rest);
            return next();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove subsets");
    }
}
